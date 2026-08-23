package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4603;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.NoRenderFeature;

@Environment(EnvType.CLIENT)
@Mixin({class_4603.class})
public class InGameOverlayRendererMixin {
   @Inject(
      method = {"renderFireOverlay"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void renderFireOverlay(class_310 client, class_4587 matrices, CallbackInfo callbackInfo) {
      if (Melonity.INSTANCE.getFeatureManager() != null && Melonity.INSTANCE.getFeatureManager().getFeature(NoRenderFeature.class).isPresent()) {
         NoRenderFeature feature = Melonity.INSTANCE.getFeatureManager().getFeature(NoRenderFeature.class).get();
         if (feature.isEnabled() && feature.getModeSetting().contains("norender.fire")) {
            callbackInfo.cancel();
         }
      }
   }
}
