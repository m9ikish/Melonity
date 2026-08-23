package ru.melonity.feature.rotation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;

@Environment(EnvType.CLIENT)
public interface RotationStrategy {
   Rotation executeRot(class_1297 var1);
}
