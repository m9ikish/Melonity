package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.event.CancelableEvent;

@Environment(EnvType.CLIENT)
public class LookEvent extends CancelableEvent {
   public double yaw;
   public double pitch;

   @Generated
   public LookEvent(double yaw, double pitch) {
      this.yaw = yaw;
      this.pitch = pitch;
   }

   @Generated
   public double getYaw() {
      return this.yaw;
   }

   @Generated
   public double getPitch() {
      return this.pitch;
   }
}
