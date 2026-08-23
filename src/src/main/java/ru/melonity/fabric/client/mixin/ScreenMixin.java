package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1735;
import net.minecraft.class_332;
import net.minecraft.class_465;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.util.event.events.EventRenderSlot;

@Environment(EnvType.CLIENT)
@Mixin({class_465.class})
public class ScreenMixin {
   @Inject(
      method = {"drawSlot"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/DrawContext;drawItem(Lnet/minecraft/item/ItemStack;III)V",
         shift = Shift.BEFORE
      )}
   )
   private void draw(class_332 context, class_1735 slot, CallbackInfo ci) {
      Melonity.INSTANCE.getEventBus().fire(new EventRenderSlot(slot, context));
   }

   @Inject(
      method = {"drawSlot"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/DrawContext;drawItemWithoutEntity(Lnet/minecraft/item/ItemStack;III)V",
         shift = Shift.BEFORE
      )}
   )
   private void draw2(class_332 context, class_1735 slot, CallbackInfo ci) {
      Melonity.INSTANCE.getEventBus().fire(new EventRenderSlot(slot, context));
   }
}
