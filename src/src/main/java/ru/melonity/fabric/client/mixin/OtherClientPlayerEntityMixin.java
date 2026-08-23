package ru.melonity.fabric.client.mixin;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_238;
import net.minecraft.class_310;
import net.minecraft.class_745;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IOtherClientPlayerEntity;
import ru.melonity.feature.features.modules.combat.ResolverFeature;

@Environment(EnvType.CLIENT)
@Mixin({class_745.class})
public class OtherClientPlayerEntityMixin implements IOtherClientPlayerEntity {
   @Unique
   private double backUpX;
   @Unique
   private double backUpY;
   @Unique
   private double backUpZ;
   @Unique
   private final List<ResolverFeature.Position> positionHistory = new ArrayList<>();

   @Unique
   @Override
   public void resolve() {
      class_745 ent = (class_745)(Object)this;
      class_310 mc = class_310.method_1551();
      ResolverFeature aura = Melonity.INSTANCE.getFeatureManager().getFeature(ResolverFeature.class).get();
      this.backUpX = ent.method_23317();
      this.backUpY = ent.method_23318();
      this.backUpZ = ent.method_23321();
      double minDst = Double.MAX_VALUE;
      ResolverFeature.Position bestPos = null;

      for (ResolverFeature.Position p : this.positionHistory) {
         double dst = mc.field_1724.method_5649(p.getX(), p.getY(), p.getZ());
         if (dst < minDst) {
            minDst = dst;
            bestPos = p;
         }
      }

      if (bestPos != null) {
         ent.method_5814(bestPos.getX(), bestPos.getY(), bestPos.getZ());
         aura.resolvedBox = this.getResolvedBoundingBox();
      }
   }

   @Unique
   @Override
   public void releaseResolver() {
      if (this.backUpY != -999.0) {
         ((class_745)(Object)this).method_5814(this.backUpX, this.backUpY, this.backUpZ);
         this.backUpY = -999.0;
      }
   }

   @Unique
   @Override
   public class_238 getResolvedBoundingBox() {
      if (this.backUpY == -999.0) {
         return this.makeBoundingBox((class_745)(Object)this, ((class_745)(Object)this).method_23317(), ((class_745)(Object)this).method_23318(), ((class_745)(Object)this).method_23321());
      } else {
         ResolverFeature aura = Melonity.INSTANCE.getFeatureManager().getFeature(ResolverFeature.class).get();
         double minDst = Double.MAX_VALUE;
         ResolverFeature.Position bestPos = null;

         for (ResolverFeature.Position p : this.positionHistory) {
            double dst = class_310.method_1551().field_1724.method_5649(p.getX(), p.getY(), p.getZ());
            if (dst < minDst) {
               minDst = dst;
               bestPos = p;
            }
         }

         return bestPos != null
            ? this.makeBoundingBox((class_745)(Object)this, bestPos.getX(), bestPos.getY(), bestPos.getZ())
            : (
               aura.resolvedBox = this.makeBoundingBox(
                  (class_745)(Object)this, ((class_745)(Object)this).method_23317(), ((class_745)(Object)this).method_23318(), ((class_745)(Object)this).method_23321()
               )
            );
      }
   }

   @Unique
   public class_238 makeBoundingBox(class_1297 entity, double pX, double pY, double pZ) {
      float f = entity.method_17681() / 2.0F;
      float f1 = entity.method_17682();
      return new class_238(pX - (double)f, pY, pZ - (double)f, pX + (double)f, pY + (double)f1, pZ + (double)f);
   }
}
