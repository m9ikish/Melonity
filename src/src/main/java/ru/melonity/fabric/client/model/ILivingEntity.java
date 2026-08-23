package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface ILivingEntity {
   void resetJumpDelay();

   void setPitchHead(float var1);

   float prevPitchHead();

   float pitchHead();

   double serverX();

   double serverY();

   double serverZ();

   void setServerX(double var1);

   void setServerY(double var1);

   void setServerZ(double var1);

   void setServerYaw(float var1);

   void setServerPitch(float var1);

   void setBodyTrackingIncrements(int var1);
}
