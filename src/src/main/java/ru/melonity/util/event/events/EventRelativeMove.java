package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_243;
import ru.melonity.util.event.CancelableEvent;

@Environment(EnvType.CLIENT)
public class EventRelativeMove extends CancelableEvent {
   public class_243 movementInput;
   public float speed;
   public float yaw;
   public class_243 velocity;

   @Generated
   public EventRelativeMove(class_243 movementInput, float speed, float yaw, class_243 velocity) {
      this.movementInput = movementInput;
      this.speed = speed;
      this.yaw = yaw;
      this.velocity = velocity;
   }

   @Generated
   public class_243 getMovementInput() {
      return this.movementInput;
   }

   @Generated
   public float getSpeed() {
      return this.speed;
   }

   @Generated
   public float getYaw() {
      return this.yaw;
   }

   @Generated
   public class_243 getVelocity() {
      return this.velocity;
   }

   @Generated
   public void setMovementInput(class_243 movementInput) {
      this.movementInput = movementInput;
   }

   @Generated
   public void setSpeed(float speed) {
      this.speed = speed;
   }

   @Generated
   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   @Generated
   public void setVelocity(class_243 velocity) {
      this.velocity = velocity;
   }
}
