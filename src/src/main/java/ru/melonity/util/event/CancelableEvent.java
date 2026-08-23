package ru.melonity.util.event;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public abstract class CancelableEvent implements Event {
   boolean cancelled;

   public void cancel() {
      this.cancelled = true;
   }

   @Generated
   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   @Generated
   public boolean isCancelled() {
      return this.cancelled;
   }
}
