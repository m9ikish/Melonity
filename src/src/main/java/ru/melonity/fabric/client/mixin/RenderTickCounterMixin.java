package ru.melonity.fabric.client.mixin;

import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_9779.class_9781;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.melonity.Melonity;

@Environment(EnvType.CLIENT)
@Mixin({class_9781.class})
public class RenderTickCounterMixin {
   @Shadow
   private float field_51958;
   @Shadow
   private float field_51959;
   @Shadow
   private long field_51962;
   @Shadow
   @Final
   private FloatUnaryOperator field_51965;
   @Shadow
   @Final
   private float field_51964;

   @Inject(
      method = {"beginRenderTick(J)I"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void beginRenderTick(long timeMillis, CallbackInfoReturnable<Integer> cir) {
      this.field_51958 = (float)(timeMillis - this.field_51962) / this.field_51965.apply(this.field_51964) * Melonity.timerSpeed;
      this.field_51962 = timeMillis;
      this.field_51959 = this.field_51959 + this.field_51958;
      int i = (int)this.field_51959;
      this.field_51959 -= (float)i;
      cir.setReturnValue(i);
   }
}
