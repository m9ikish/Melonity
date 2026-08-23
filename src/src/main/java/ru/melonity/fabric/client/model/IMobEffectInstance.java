package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;

@Environment(EnvType.CLIENT)
public interface IMobEffectInstance {
   StateAnimation getScale();

   FrameWeightCalculator getFWC();

   long getTimes();

   void setTimes(long var1);
}
