package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public class EventPlayerJump implements Event {
   private boolean pre;

   @Generated
   public EventPlayerJump(boolean pre) {
      this.pre = pre;
   }

   @Generated
   public boolean isPre() {
      return this.pre;
   }

   @Generated
   public void setPre(boolean pre) {
      this.pre = pre;
   }
}
