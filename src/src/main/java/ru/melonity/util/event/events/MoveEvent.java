package ru.melonity.util.event.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_238;
import net.minecraft.class_243;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public class MoveEvent implements Event {
   public class_243 from;
   public class_243 to;
   public class_243 motion;
   private final boolean toGround;
   private final class_238 aabbFrom;
   public boolean ignoreHorizontal;
   public boolean ignoreVertical;
   public boolean collidedHorizontal;
   public boolean collidedVertical;

   public MoveEvent(
      class_243 from, class_243 to, class_243 motion, boolean toGround, boolean isCollidedHorizontal, boolean isCollidedVertical, class_238 aabbFrom
   ) {
      this.from = from;
      this.to = to;
      this.motion = motion;
      this.toGround = toGround;
      this.collidedHorizontal = isCollidedHorizontal;
      this.collidedVertical = isCollidedVertical;
      this.aabbFrom = aabbFrom;
   }

   public class_243 getFrom() {
      return this.from;
   }

   public class_243 getTo() {
      return this.to;
   }

   public class_243 getMotion() {
      return this.motion;
   }

   public boolean isToGround() {
      return this.toGround;
   }

   public class_238 getAabbFrom() {
      return this.aabbFrom;
   }

   public void setIgnoreHorizontalCollision() {
      this.ignoreHorizontal = true;
   }

   public void setIgnoreVerticalCollision() {
      this.ignoreVertical = true;
   }
}
