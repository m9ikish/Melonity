package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2248;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import ru.melonity.fabric.client.model.IBlock;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;

@Environment(EnvType.CLIENT)
@Mixin({class_2248.class})
public abstract class BlockMixin implements IBlock {
   @Unique
   private FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(300L);
   @Unique
   private StateAnimation alphaAnimation = new StateAnimation();

   @Override
   public FrameWeightCalculator fwc() {
      if (this.fwc == null) {
         this.fwc = FrameWeightCalculator.milliseconds(300L);
      }

      return this.fwc;
   }

   @Override
   public StateAnimation alphaAnimation() {
      if (this.alphaAnimation == null) {
         this.alphaAnimation = new StateAnimation();
      }

      return this.alphaAnimation;
   }
}
