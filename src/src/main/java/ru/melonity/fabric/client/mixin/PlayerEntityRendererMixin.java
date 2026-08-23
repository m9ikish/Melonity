package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1007;
import net.minecraft.class_2561;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_742;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.ESPFeature;
import ru.melonity.feature.features.modules.render.ShaderEspFeature;

@Environment(EnvType.CLIENT)
@Mixin({class_1007.class})
public class PlayerEntityRendererMixin {
   @Inject(
      method = {"renderLabelIfPresent(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   protected void renderLabelIfPresent(
      class_742 abstractClientPlayerEntity,
      class_2561 text,
      class_4587 matrixStack,
      class_4597 vertexConsumerProvider,
      int i,
      float f,
      CallbackInfo callbackInfo
   ) {
      if (!Melonity.renderLabel
         || Melonity.INSTANCE.getFeatureManager() != null
            && (
               Melonity.INSTANCE.getFeatureManager().getFeature(ShaderEspFeature.class).get().isEnabled()
                  || Melonity.INSTANCE.getFeatureManager().getFeature(ESPFeature.class).get().isEnabled()
            )) {
         callbackInfo.cancel();
      }
   }
}
