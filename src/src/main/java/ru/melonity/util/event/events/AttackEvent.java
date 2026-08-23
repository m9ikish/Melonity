package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import ru.melonity.util.event.CancelableEvent;

@Environment(EnvType.CLIENT)
public class AttackEvent extends CancelableEvent {
   private final class_1297 target;

   @Generated
   public AttackEvent(class_1297 target) {
      this.target = target;
   }

   @Generated
   public class_1297 target() {
      return this.target;
   }
}
