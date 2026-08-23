package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_276;
import net.minecraft.class_4604;

@Environment(EnvType.CLIENT)
public interface ILevelRenderer {
   class_4604 getFrustum();

   class_276 entitiesFramebuffer();
}
