package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_239;
import ru.melonity.util.event.CancelableEvent;

@Environment(EnvType.CLIENT)
public class DoAttackEvent extends CancelableEvent {
   private boolean cancelled;
   private final class_239 crosshairTarget;

   public DoAttackEvent(class_239 crosshairTarget) {
      this.crosshairTarget = crosshairTarget;
   }

   @Generated
   @Override
   public boolean isCancelled() {
      return this.cancelled;
   }

   @Generated
   public class_239 getCrosshairTarget() {
      return this.crosshairTarget;
   }

   @Generated
   @Override
   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }
}
