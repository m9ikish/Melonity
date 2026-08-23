package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_3675.class_306;

@Environment(EnvType.CLIENT)
public interface IKeyBinding {
   class_306 boundKey();
}
