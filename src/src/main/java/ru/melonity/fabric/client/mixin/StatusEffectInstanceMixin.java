package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1293;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ru.melonity.fabric.client.model.IMobEffectInstance;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;

@Environment(EnvType.CLIENT)
@Mixin({class_1293.class})
public class StatusEffectInstanceMixin implements IMobEffectInstance {
   @Unique
   private long times = -1L;
   @Unique
   private StateAnimation scale = null;
   @Unique
   private FrameWeightCalculator fwc;

   @Override
   public StateAnimation getScale() {
      if (this.scale == null) {
         this.scale = new StateAnimation();
      }

      return this.scale;
   }

   @Override
   public FrameWeightCalculator getFWC() {
      if (this.fwc == null) {
         this.fwc = FrameWeightCalculator.milliseconds(400L);
      }

      return this.fwc;
   }

   @Override
   public long getTimes() {
      return this.times;
   }

   @Override
   public void setTimes(long times) {
      this.times = times;
   }
}
