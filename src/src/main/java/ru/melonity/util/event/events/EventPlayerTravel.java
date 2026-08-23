package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_243;
import ru.melonity.util.event.CancelableEvent;

@Environment(EnvType.CLIENT)
public class EventPlayerTravel extends CancelableEvent {
   private class_243 mVec;
   private boolean pre;

   @Generated
   public EventPlayerTravel(class_243 mVec, boolean pre) {
      this.mVec = mVec;
      this.pre = pre;
   }

   @Generated
   public class_243 getMVec() {
      return this.mVec;
   }

   @Generated
   public boolean isPre() {
      return this.pre;
   }

   @Generated
   public void setMVec(class_243 mVec) {
      this.mVec = mVec;
   }

   @Generated
   public void setPre(boolean pre) {
      this.pre = pre;
   }
}
