package ru.melonity.util.event.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_243;
import ru.melonity.util.event.CancelableEvent;

@Environment(EnvType.CLIENT)
public class StrafeEvent extends CancelableEvent {
   public class_243 movementInput;
   public float speed;
   public float yaw;

   public StrafeEvent(class_243 movementInput, float speed, float yaw) {
      this.movementInput = movementInput;
      this.speed = speed;
      this.yaw = yaw;
   }
}
