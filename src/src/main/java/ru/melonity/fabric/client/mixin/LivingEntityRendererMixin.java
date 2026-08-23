package ru.melonity.fabric.client.mixin;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_1921;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_583;
import net.minecraft.class_746;
import net.minecraft.class_922;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.SeeInvisiblesFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.util.event.events.RenderLivingEntityModelEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_922.class})
public class LivingEntityRendererMixin {
   @Shadow
   protected class_583 field_4737;

   @Shadow
   protected float method_4045(class_1309 entity, float tickDelta) {
      return 0.0F;
   }

   @Inject(
      method = {"render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/entity/model/EntityModel;setAngles(Lnet/minecraft/entity/Entity;FFFFF)V",
         shift = Shift.AFTER
      )}
   )
   public void render(class_1309 livingEntity, float f, float g, class_4587 matrixStack, class_4597 vertexConsumerProvider, int i, CallbackInfo callbackInfo) {
      if (livingEntity instanceof class_746 && RotationManager.INSTANCE.isRotating()) {
         livingEntity.field_6283 = RotationManager.INSTANCE.getVisualYaw();
      }

      float h = class_3532.method_17821(g, livingEntity.field_6220, livingEntity.field_6283);
      float j = livingEntity instanceof class_746 && RotationManager.aiming && !RotationManager.INSTANCE.isInInventory()
         ? RotationManager.INSTANCE.getVisualYaw()
         : class_3532.method_17821(g, livingEntity.field_6259, livingEntity.field_6241);
      float k = j - h;
      if (livingEntity.method_5765() && livingEntity.method_5854() instanceof class_1309 livingEntity2) {
         h = class_3532.method_17821(g, livingEntity2.field_6220, livingEntity2.field_6283);
         k = j - h;
         float l = class_3532.method_15393(k);
         if (l < -85.0F) {
            l = -85.0F;
         }

         if (l >= 85.0F) {
            l = 85.0F;
         }

         h = j - l;
         if (l * l > 2500.0F) {
            h += l * 0.2F;
         }

         k = j - h;
      }

      float n = this.method_4045(livingEntity, g);
      float o = 0.0F;
      float p = 0.0F;
      if (!livingEntity.method_5765() && livingEntity.method_5805()) {
         o = livingEntity.field_42108.method_48570(g);
         p = livingEntity.field_42108.method_48572(g);
         if (livingEntity.method_6109()) {
            p *= 3.0F;
         }

         if (o > 1.0F) {
            o = 1.0F;
         }
      }

      float m = livingEntity instanceof class_746 && RotationManager.aiming
         ? RotationManager.INSTANCE.getVisualPitch()
         : class_3532.method_16439(g, livingEntity.field_6004, livingEntity.method_36455());
      if (method_38563(livingEntity)) {
         m *= -1.0F;
         k *= -1.0F;
      }

      this.field_4737.method_2819(livingEntity, p, o, n, k, m);
   }

   @Inject(
      method = {"render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V"
      )}
   )
   private void onRenderEntity(class_1309 livingEntity, float f, float g, class_4587 matrixStack, class_4597 vertexConsumerProvider, int i, CallbackInfo ci) {
      Melonity.INSTANCE.getEventBus().fire(new RenderLivingEntityModelEvent(this.field_4737, matrixStack, livingEntity));
   }

   @Shadow
   protected boolean method_4056(class_1309 entity) {
      return false;
   }

   @Shadow
   protected class_1921 method_24302(class_1309 entity, boolean showBody, boolean translucent, boolean showOutline) {
      return null;
   }

   @Shadow
   protected float method_23185(class_1309 entity, float tickDelta) {
      return 0.0F;
   }

   @Inject(
      method = {"render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getRenderLayer(Lnet/minecraft/entity/LivingEntity;ZZZ)Lnet/minecraft/client/render/RenderLayer;"
      )},
      cancellable = true
   )
   public void render_transparency(
      class_1309 livingEntity, float f, float g, class_4587 matrixStack, class_4597 vertexConsumerProvider, int i, CallbackInfo callbackInfo
   ) {
      class_310 mc = class_310.method_1551();
      class_922 entityRenderer = (class_922)(Object)this;
      SeeInvisiblesFeature feature = Melonity.INSTANCE.getFeatureManager().getFeature(SeeInvisiblesFeature.class).get();
      boolean bl = this.method_4056(livingEntity);
      if (!bl && !livingEntity.method_5756(mc.field_1724)) {
         boolean var17 = true;
      } else {
         boolean var10000 = false;
      }

      boolean bl3 = mc.method_27022(livingEntity);
      class_1921 renderLayer = class_1921.method_29379(entityRenderer.method_3931(livingEntity));
      if (renderLayer != null) {
         class_4588 vertexConsumer = vertexConsumerProvider.getBuffer(renderLayer);
         int overlay = class_922.method_23622(livingEntity, this.method_23185(livingEntity, g));
         if (feature.isEnabled() && livingEntity.method_5767()) {
            this.field_4737
               .method_2828(matrixStack, vertexConsumer, i, overlay, new Color(255, 255, 255, 255 * feature.getOpacity().getValue().intValue()).getRGB());
         }
      }
   }

   @Shadow
   public static boolean method_38563(class_1309 entity) {
      return false;
   }
}
