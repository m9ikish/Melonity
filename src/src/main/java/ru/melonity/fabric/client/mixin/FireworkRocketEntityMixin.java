package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_1671;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_746;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.melonity.Melonity;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.util.event.events.FireWorkRocketEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_1671.class})
public class FireworkRocketEntityMixin {
   @Shadow
   @Nullable
   private class_1309 field_7616;

   @Redirect(
      method = {"tick"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;getRotationVector()Lnet/minecraft/util/math/Vec3d;"
      )
   )
   public class_243 tick(class_1309 ent) {
      return ent != class_310.method_1551().field_1724 ? ent.method_5720() : RotationManager.INSTANCE.toVector();
   }

   @Redirect(
      method = {"tick"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"
      )
   )
   public void tick(class_1309 instance, class_243 origin) {
      if (instance instanceof class_746) {
         FireWorkRocketEvent rocketBoost = new FireWorkRocketEvent(0.1, 0.1);
         Melonity.INSTANCE.getEventBus().fire(rocketBoost);
         class_243 vec3d = this.field_7616.method_5720();
         class_243 vec3d2 = this.field_7616.method_18798();
         this.field_7616
            .method_18799(
               vec3d2.method_1031(
                  vec3d.field_1352 * rocketBoost.getSpeed() + (vec3d.field_1352 * 1.5 - vec3d2.field_1352) * 0.5,
                  vec3d.field_1351 * rocketBoost.getYSpeed() + (vec3d.field_1351 * 1.5 - vec3d2.field_1351) * 0.5,
                  vec3d.field_1350 * rocketBoost.getSpeed() + (vec3d.field_1350 * 1.5 - vec3d2.field_1350) * 0.5
               )
            );
      } else {
         instance.method_18799(origin);
      }
   }
}
