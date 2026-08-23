package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_315;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public class EventPreInput implements Event {
   class_315 settings;
   boolean forward;
   boolean back;
   boolean left;
   boolean right;

   @Generated
   public EventPreInput(class_315 settings, boolean forward, boolean back, boolean left, boolean right) {
      this.settings = settings;
      this.forward = forward;
      this.back = back;
      this.left = left;
      this.right = right;
   }

   @Generated
   public class_315 getSettings() {
      return this.settings;
   }

   @Generated
   public boolean isForward() {
      return this.forward;
   }

   @Generated
   public boolean isBack() {
      return this.back;
   }

   @Generated
   public boolean isLeft() {
      return this.left;
   }

   @Generated
   public boolean isRight() {
      return this.right;
   }

   @Generated
   public void setSettings(class_315 settings) {
      this.settings = settings;
   }

   @Generated
   public void setForward(boolean forward) {
      this.forward = forward;
   }

   @Generated
   public void setBack(boolean back) {
      this.back = back;
   }

   @Generated
   public void setLeft(boolean left) {
      this.left = left;
   }

   @Generated
   public void setRight(boolean right) {
      this.right = right;
   }
}
