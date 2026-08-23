package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.event.CancelableEvent;

@Environment(EnvType.CLIENT)
public class PreMotionEvent extends CancelableEvent {
   private final double posX;
   private final double posY;
   private final double posZ;
   private boolean onGround;
   private float yaw;
   private float pitch;

   public PreMotionEvent(float yaw, float pitch, double posX, double posY, double posZ, boolean onGround) {
      this.yaw = yaw;
      this.pitch = pitch;
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.onGround = onGround;
   }

   public float setPitch(float pitch) {
      this.pitch = pitch;
      return this.pitch;
   }

   public float setYaw(float yaw) {
      this.yaw = yaw;
      return this.yaw;
   }

   public void setOnGround(boolean onGround) {
      this.onGround = onGround;
   }

   @Generated
   public double getPosX() {
      return this.posX;
   }

   @Generated
   public double getPosY() {
      return this.posY;
   }

   @Generated
   public double getPosZ() {
      return this.posZ;
   }

   @Generated
   public boolean isOnGround() {
      return this.onGround;
   }

   @Generated
   public float getYaw() {
      return this.yaw;
   }

   @Generated
   public float getPitch() {
      return this.pitch;
   }
}
