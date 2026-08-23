package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_315;
import net.minecraft.class_743;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.EventPreInput;

@Environment(EnvType.CLIENT)
@Mixin({class_743.class})
public class KeyboardInputMixin {
   @Shadow
   @Final
   private class_315 field_3902;

   private static float getMovementMultiplier(boolean positive, boolean negative) {
      if (positive == negative) {
         return 0.0F;
      } else {
         return positive ? 1.0F : -1.0F;
      }
   }

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void tick(boolean slowDown, float slowDownFactor, CallbackInfo callbackInfo) {
      class_743 input = (class_743)(Object)this;
      EventPreInput eventPreInput = new EventPreInput(
         this.field_3902,
         this.field_3902.field_1894.method_1434(),
         this.field_3902.field_1881.method_1434(),
         this.field_3902.field_1913.method_1434(),
         this.field_3902.field_1849.method_1434()
      );
      Melonity.INSTANCE.getEventBus().fire(eventPreInput);
      input.field_3910 = eventPreInput.isForward();
      input.field_3909 = eventPreInput.isBack();
      input.field_3908 = eventPreInput.isLeft();
      input.field_3906 = eventPreInput.isRight();
      input.field_3905 = getMovementMultiplier(input.field_3910, input.field_3909);
      input.field_3907 = getMovementMultiplier(input.field_3908, input.field_3906);
      input.field_3904 = this.field_3902.field_1903.method_1434();
      input.field_3903 = this.field_3902.field_1832.method_1434();
      EventInput eventInput = new EventInput(input.field_3905, input.field_3907, input.field_3904, input.field_3903);
      Melonity.INSTANCE.getEventBus().fire(eventInput);
      input.field_3905 = eventInput.getForward();
      input.field_3907 = eventInput.getStrafe();
      input.field_3904 = eventInput.isJump();
      input.field_3903 = eventInput.isSneaking();
      if (slowDown && !eventInput.isCancelled()) {
         input.field_3907 *= slowDownFactor;
         input.field_3905 *= slowDownFactor;
      }

      callbackInfo.cancel();
   }
}
