package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2874;
import net.minecraft.class_765;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.melonity.Melonity;
import ru.melonity.util.event.events.BrightnessEvent;
import ru.melonity.util.event.events.DarknessFactorEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_765.class})
public class LightmapTextureManagerMixin {
   @Inject(
      method = {"getDarknessFactor"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getDarknessFactor(float delta, CallbackInfoReturnable<Float> cir) {
      DarknessFactorEvent event = new DarknessFactorEvent();
      Melonity.INSTANCE.getEventBus().fire(event);
      if (event.isCancelled()) {
         cir.setReturnValue(0.0F);
      }
   }

   @Inject(
      method = {"getBrightness"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private static void getBrightness(class_2874 type, int lightLevel, CallbackInfoReturnable<Float> cir) {
      BrightnessEvent event = new BrightnessEvent(type, lightLevel, cir.getReturnValue());
      Melonity.INSTANCE.getEventBus().fire(event);
      cir.setReturnValue(event.brightness());
   }
}
