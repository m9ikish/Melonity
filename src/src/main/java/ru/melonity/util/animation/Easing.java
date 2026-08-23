package ru.melonity.util.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@FunctionalInterface
@Environment(EnvType.CLIENT)
public interface Easing {
   double ease(double var1);
}
