package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1313;
import ru.melonity.util.event.CancelableEvent;

@Environment(EnvType.CLIENT)
public class EventPlayerMove extends CancelableEvent {
   public class_1313 type;
   public double x;
   public double y;
   public double z;

   @Generated
   public EventPlayerMove(class_1313 type, double x, double y, double z) {
      this.type = type;
      this.x = x;
      this.y = y;
      this.z = z;
   }
}
