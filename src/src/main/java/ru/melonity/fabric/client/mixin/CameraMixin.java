package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1922;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4184;
import net.minecraft.class_2338.class_2339;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.player.FreeCamFeature;
import ru.melonity.util.event.events.RotEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_4184.class})
public class CameraMixin {
   @Shadow
   private class_243 field_18712;
   @Shadow
   @Final
   private class_2339 field_18713;
   @Shadow
   private boolean field_18709;
   @Shadow
   private class_1922 field_18710;
   @Shadow
   private class_1297 field_18711;
   @Shadow
   private boolean field_18719;
   @Shadow
   private float field_47549;
   @Shadow
   private float field_18717;
   @Shadow
   private float field_18718;
   @Shadow
   private float field_18722;
   @Shadow
   private float field_18721;

   @Inject(
      method = {"setPos(Lnet/minecraft/util/math/Vec3d;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   protected void setPosition(class_243 vector, CallbackInfo callbackInfo) {
      FreeCamFeature feature = Melonity.INSTANCE.getFeatureManager().getFeature(FreeCamFeature.class).orElse(null);
      if (feature != null && feature.isEnabled() && feature.prevRenderPos != null) {
         float partialTicks = class_310.method_1551().method_60646().method_60637(true);
         class_243 renderPos = new class_243(
            class_3532.method_16436((double)partialTicks, feature.prevRenderPos.field_1352, feature.renderPos.field_1352),
            class_3532.method_16436((double)partialTicks, feature.prevRenderPos.field_1351, feature.renderPos.field_1351),
            class_3532.method_16436((double)partialTicks, feature.prevRenderPos.field_1350, feature.renderPos.field_1350)
         );
         this.field_18712 = renderPos;
         this.field_18713.method_10102(renderPos.field_1352, renderPos.field_1351, renderPos.field_1350);
         callbackInfo.cancel();
      }
   }

   @Shadow
   protected void method_19325(float yaw, float pitch) {
   }

   @Shadow
   protected void method_19327(double x, double y, double z) {
   }

   @Shadow
   protected void method_19324(float f, float g, float h) {
   }

   @Shadow
   private float method_19318(float f) {
      return 0.0F;
   }

   @Inject(
      method = {"update"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void update(class_1922 area, class_1297 focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo callbackInfo) {
      this.field_18709 = true;
      this.field_18710 = area;
      this.field_18711 = focusedEntity;
      this.field_18719 = thirdPerson;
      this.field_47549 = tickDelta;
      RotEvent event = new RotEvent(focusedEntity.method_5705(tickDelta), focusedEntity.method_5695(tickDelta), tickDelta);
      Melonity.INSTANCE.getEventBus().fire(event);
      this.method_19325(event.getYaw(), event.getPitch());
      this.method_19327(
         class_3532.method_16436((double)tickDelta, focusedEntity.field_6014, focusedEntity.method_23317()),
         class_3532.method_16436((double)tickDelta, focusedEntity.field_6036, focusedEntity.method_23318())
            + (double)class_3532.method_16439(tickDelta, this.field_18722, this.field_18721),
         class_3532.method_16436((double)tickDelta, focusedEntity.field_5969, focusedEntity.method_23321())
      );
      if (thirdPerson) {
         if (inverseView) {
            this.method_19325(event.getYaw() + 180.0F, -event.getPitch());
         }

         float f = focusedEntity instanceof class_1309 livingEntity ? livingEntity.method_55693() : 1.0F;
         this.method_19324(-this.method_19318(4.0F * f), 0.0F, 0.0F);
      } else if (focusedEntity instanceof class_1309 && ((class_1309)focusedEntity).method_6113()) {
         class_2350 direction = ((class_1309)focusedEntity).method_18401();
         this.method_19325(direction != null ? direction.method_10144() - 180.0F : 0.0F, 0.0F);
         this.method_19324(0.0F, 0.3F, 0.0F);
      }

      callbackInfo.cancel();
   }
}
