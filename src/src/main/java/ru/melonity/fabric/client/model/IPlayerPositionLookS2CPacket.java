package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface IPlayerPositionLookS2CPacket {
   void setYaw(float var1);

   void setPitch(float var1);
}
