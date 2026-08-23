package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public class FireWorkRocketEvent implements Event {
   double speed;
   double ySpeed;

   @Generated
   public void setSpeed(double speed) {
      this.speed = speed;
   }

   @Generated
   public void setYSpeed(double ySpeed) {
      this.ySpeed = ySpeed;
   }

   @Generated
   public double getSpeed() {
      return this.speed;
   }

   @Generated
   public double getYSpeed() {
      return this.ySpeed;
   }

   @Generated
   public FireWorkRocketEvent(double speed, double ySpeed) {
      this.speed = speed;
      this.ySpeed = ySpeed;
   }
}
