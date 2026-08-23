package ru.melonity.fabric.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4184;
import net.minecraft.class_638;
import net.minecraft.class_758;
import net.minecraft.class_758.class_4596;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.FogColorFeature;

@Environment(EnvType.CLIENT)
@Mixin({class_758.class})
public class FogRendererMixin {
   @Shadow
   private static float field_4034;
   @Shadow
   private static float field_4033;
   @Shadow
   private static float field_4032;

   @Inject(
      method = {"render"},
      at = {@At(
         value = "INVOKE",
         target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V"
      )},
      cancellable = true
   )
   private static void overrideClearColor(class_4184 camera, float tickDelta, class_638 world, int viewDistance, float skyDarkness, CallbackInfo callbackInfo) {
      Melonity.INSTANCE.getFeatureManager().getFeature(FogColorFeature.class).ifPresent(fogColorFeature -> {
         if (fogColorFeature.isEnabled()) {
            Color color = fogColorFeature.getColorSetting().getColor();
            RenderSystem.clearColor((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, 0.0F);
            callbackInfo.cancel();
         }
      });
   }

   @Inject(
      method = {"applyFogColor"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void applyFogColor(CallbackInfo callbackInfo) {
      Melonity.INSTANCE
         .getFeatureManager()
         .getFeature(FogColorFeature.class)
         .ifPresent(
            fogColorFeature -> {
               if (fogColorFeature.isEnabled()) {
                  RenderSystem.setShaderFogColor(
                     (float)fogColorFeature.getColorSetting().getColor().getRed() / 255.0F,
                     (float)fogColorFeature.getColorSetting().getColor().getGreen() / 255.0F,
                     (float)fogColorFeature.getColorSetting().getColor().getBlue() / 255.0F
                  );
                  callbackInfo.cancel();
               }
            }
         );
   }

   @ModifyVariable(
      method = {"applyFog"},
      at = @At("HEAD"),
      ordinal = 0,
      argsOnly = true
   )
   private static float modifyViewDistance(
      float viewDistance, class_4184 camera, class_4596 fogType, float originalViewDistance, boolean thickFog, float tickDelta
   ) {
      FogColorFeature fogColorFeature = Melonity.INSTANCE.getFeatureManager().getFeature(FogColorFeature.class).orElse(null);
      return fogColorFeature != null && fogColorFeature.isEnabled()
         ? viewDistance - (fogColorFeature.getDistanceSetting().getMax().floatValue() - fogColorFeature.getDistanceSetting().getValue().floatValue())
         : viewDistance;
   }
}
