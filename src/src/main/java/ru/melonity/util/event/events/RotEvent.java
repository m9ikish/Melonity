package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public class RotEvent implements Event {
   private float yaw;
   private float pitch;
   private float partialTicks;

   @Generated
   public float getYaw() {
      return this.yaw;
   }

   @Generated
   public float getPitch() {
      return this.pitch;
   }

   @Generated
   public float getPartialTicks() {
      return this.partialTicks;
   }

   @Generated
   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   @Generated
   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   @Generated
   public void setPartialTicks(float partialTicks) {
      this.partialTicks = partialTicks;
   }

   @Generated
   public RotEvent(float yaw, float pitch, float partialTicks) {
      this.yaw = yaw;
      this.pitch = pitch;
      this.partialTicks = partialTicks;
   }
}
