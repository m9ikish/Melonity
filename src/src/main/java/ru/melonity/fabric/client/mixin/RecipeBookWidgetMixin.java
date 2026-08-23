package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_507;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin({class_507.class})
public class RecipeBookWidgetMixin {
   @Inject(
      method = {"setOpen"},
      at = {@At("HEAD")},
      cancellable = true
   )
   protected void setOpen(boolean opened, CallbackInfo callbackInfo) {
      callbackInfo.cancel();
   }
}
