package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1007;
import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1829;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_742;
import net.minecraft.class_759;
import net.minecraft.class_7833;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.SwingAnimationFeature;
import ru.melonity.feature.features.modules.render.ViewModelFeature;

@Environment(EnvType.CLIENT)
@Mixin({class_759.class})
public class HeldItemRendererMixin {
   @Inject(
      method = {"applyEquipOffset"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void transformSideFirstPerson(class_4587 matrices, class_1306 arm, float equipProgress, CallbackInfo callbackInfo) {
      SwingAnimationFeature swingAnimation = Melonity.INSTANCE.getFeatureManager().swingAnimationFeature;
      if (swingAnimation.isEnabled()) {
         int i = arm == class_1306.field_6183 ? 1 : -1;
         matrices.method_46416((float)i * 0.56F, -0.52F + equipProgress * (swingAnimation.isEnabled() ? 0.0F : -0.6F), -0.72F);
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"applySwingOffset"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void transformFirstPerson(class_4587 poseStack, class_1306 handIn, float swingProgress, CallbackInfo callbackInfo) {
      SwingAnimationFeature swingAnimation = Melonity.INSTANCE.getFeatureManager().swingAnimationFeature;
      if (class_310.method_1551().field_1724.method_5998(handIn == class_1306.field_6183 ? class_1268.field_5808 : class_1268.field_5810).method_7909() instanceof class_1829
         || !swingAnimation.getOnlyOnSword().get()) {
         if (swingAnimation.isEnabled() && handIn == class_1306.field_6183) {
            swingAnimation.applyAnimation(poseStack, swingProgress, 1);
            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"renderFirstPersonItem"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingItem()Z",
         shift = Shift.BEFORE,
         ordinal = 1
      )}
   )
   private void renderFirstPersonItem(
      class_742 player,
      float tickDelta,
      float pitch,
      class_1268 hand,
      float swingProgress,
      class_1799 item,
      float equipProgress,
      class_4587 matrices,
      class_4597 vertexConsumers,
      int light,
      CallbackInfo callbackInfo
   ) {
      boolean flag3 = hand == class_1268.field_5808;
      ViewModelFeature viewModelFeature = Melonity.INSTANCE.getFeatureManager().viewModelFeature;
      if (viewModelFeature.isEnabled()) {
         if (flag3) {
            matrices.method_46416(
               -viewModelFeature.getHandX().getValue().floatValue(),
               viewModelFeature.getHandY().getValue().floatValue(),
               viewModelFeature.getHandZ().getValue().floatValue()
            );
         } else {
            matrices.method_46416(
               viewModelFeature.getHandX().getValue().floatValue(),
               viewModelFeature.getHandY().getValue().floatValue(),
               viewModelFeature.getHandZ().getValue().floatValue()
            );
         }
      }
   }

   @Inject(
      method = {"applyEatOrDrinkTransformation"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void applyEatOrDrinkTransformation(
      class_4587 matrices, float tickDelta, class_1306 arm, class_1799 stack, class_1657 player, CallbackInfo callbackInfo
   ) {
      ViewModelFeature viewModelFeature = Melonity.INSTANCE.getFeatureManager().viewModelFeature;
      if (viewModelFeature.isEnabled()) {
         float f = (float)player.method_6014() - tickDelta + 1.0F;
         float g = f / (float)stack.method_7935(player);
         if (g < 0.8F) {
            float h = class_3532.method_15379(class_3532.method_15362(f / 4.0F * (float) Math.PI) * 0.1F);
            matrices.method_46416(0.0F, h, 0.0F);
         }

         float h = 1.0F - (float)Math.pow((double)g, 27.0);
         int i = arm == class_1306.field_6183 ? 1 : -1;
         if (arm == class_1306.field_6182) {
            matrices.method_46416(
               -viewModelFeature.getHandX().getValue().floatValue(),
               viewModelFeature.getHandY().getValue().floatValue(),
               viewModelFeature.getHandZ().getValue().floatValue()
            );
         } else {
            matrices.method_46416(
               viewModelFeature.getHandX().getValue().floatValue(),
               viewModelFeature.getHandY().getValue().floatValue(),
               viewModelFeature.getHandZ().getValue().floatValue()
            );
         }

         matrices.method_46416(h * 0.6F * (float)i, h * -0.5F, h * 0.0F);
         matrices.method_22907(class_7833.field_40716.rotationDegrees((float)i * h * 90.0F));
         matrices.method_22907(class_7833.field_40714.rotationDegrees(h * 10.0F));
         matrices.method_22907(class_7833.field_40718.rotationDegrees((float)i * h * 30.0F));
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderFirstPersonItem"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V",
         shift = Shift.AFTER,
         ordinal = 12
      )}
   )
   private void renderFirstPersonItem_noMatricesTranslate(
      class_742 player,
      float tickDelta,
      float pitch,
      class_1268 hand,
      float swingProgress,
      class_1799 item,
      float equipProgress,
      class_4587 matrices,
      class_4597 vertexConsumers,
      int light,
      CallbackInfo callbackInfo
   ) {
      if (Melonity.INSTANCE.getFeatureManager().swingAnimationFeature.isEnabled()) {
         boolean bl2 = hand == class_1268.field_5808;
         float n = -0.4F * class_3532.method_15374(class_3532.method_15355(swingProgress) * (float) Math.PI);
         float mxx = 0.2F * class_3532.method_15374(class_3532.method_15355(swingProgress) * (float) (Math.PI * 2));
         float fxxx = -0.2F * class_3532.method_15374(swingProgress * (float) Math.PI);
         int o = bl2 ? 1 : -1;
         matrices.method_46416(-((float)o * n), -mxx, -fxxx);
      }
   }

   @Inject(
      method = {"renderArmHoldingItem"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderArmHoldingItem(
      class_4587 matrices, class_4597 vertexConsumers, int light, float equipProgress, float swingProgress, class_1306 arm, CallbackInfo ci
   ) {
      ViewModelFeature viewModelFeature = Melonity.INSTANCE.getFeatureManager().viewModelFeature;
      if (viewModelFeature.isEnabled()) {
         boolean bl = arm != class_1306.field_6182;
         float f = bl ? 1.0F : -1.0F;
         float g = class_3532.method_15355(swingProgress);
         float h = -0.3F * class_3532.method_15374(g * (float) Math.PI);
         float i = 0.4F * class_3532.method_15374(g * (float) (Math.PI * 2));
         float j = -0.4F * class_3532.method_15374(swingProgress * (float) Math.PI);
         matrices.method_46416(
            f * (h + 0.64000005F) + (bl ? -viewModelFeature.getHandX().getValue().floatValue() : viewModelFeature.getHandX().getValue().floatValue()),
            i + -0.6F + equipProgress * -0.6F + viewModelFeature.getHandY().getValue().floatValue(),
            j + -0.71999997F + viewModelFeature.getHandZ().getValue().floatValue()
         );
         matrices.method_22907(class_7833.field_40716.rotationDegrees(f * 45.0F));
         float k = class_3532.method_15374(swingProgress * swingProgress * (float) Math.PI);
         float l = class_3532.method_15374(g * (float) Math.PI);
         matrices.method_22907(class_7833.field_40716.rotationDegrees(f * l * 70.0F));
         matrices.method_22907(class_7833.field_40718.rotationDegrees(f * k * -20.0F));
         class_742 abstractClientPlayerEntity = class_310.method_1551().field_1724;
         matrices.method_46416(f * -1.0F, 3.6F, 3.5F);
         matrices.method_22907(class_7833.field_40718.rotationDegrees(f * 120.0F));
         matrices.method_22907(class_7833.field_40714.rotationDegrees(200.0F));
         matrices.method_22907(class_7833.field_40716.rotationDegrees(f * -135.0F));
         matrices.method_46416(f * 5.6F, 0.0F, 0.0F);
         class_1007 playerEntityRenderer = (class_1007)class_310.method_1551().method_1561().method_3953(abstractClientPlayerEntity);
         if (bl) {
            playerEntityRenderer.method_4220(matrices, vertexConsumers, light, abstractClientPlayerEntity);
         } else {
            playerEntityRenderer.method_4221(matrices, vertexConsumers, light, abstractClientPlayerEntity);
         }

         ci.cancel();
      }
   }
}
