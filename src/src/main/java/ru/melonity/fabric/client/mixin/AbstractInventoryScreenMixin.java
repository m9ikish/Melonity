package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_332;
import net.minecraft.class_485;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin({class_485.class})
public class AbstractInventoryScreenMixin {
   @Inject(
      method = {"drawStatusEffects"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void drawStatusEffects(class_332 context, int mouseX, int mouseY, CallbackInfo callbackInfo) {
      callbackInfo.cancel();
   }
}
