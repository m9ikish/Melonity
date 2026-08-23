package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4184;

@Environment(EnvType.CLIENT)
public interface IGameRenderer {
   double getFovSafe(class_4184 var1, float var2, boolean var3);
}
