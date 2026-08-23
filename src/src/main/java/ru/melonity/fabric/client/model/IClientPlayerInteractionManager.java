package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_638;
import net.minecraft.class_7204;

@Environment(EnvType.CLIENT)
public interface IClientPlayerInteractionManager {
   void trySendSequencedPacket(class_638 var1, class_7204 var2);
}
