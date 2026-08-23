package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_238;

@Environment(EnvType.CLIENT)
public interface IOtherClientPlayerEntity {
   void resolve();

   void releaseResolver();

   class_238 getResolvedBoundingBox();
}
