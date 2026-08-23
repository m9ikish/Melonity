package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1735;
import net.minecraft.class_332;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public class EventRenderSlot implements Event {
   public class_1735 slot;
   public class_332 context;

   @Generated
   public EventRenderSlot(class_1735 slot, class_332 context) {
      this.slot = slot;
      this.context = context;
   }

   @Generated
   public class_1735 getSlot() {
      return this.slot;
   }

   @Generated
   public class_332 getContext() {
      return this.context;
   }

   @Generated
   public void setSlot(class_1735 slot) {
      this.slot = slot;
   }

   @Generated
   public void setContext(class_332 context) {
      this.context = context;
   }
}
