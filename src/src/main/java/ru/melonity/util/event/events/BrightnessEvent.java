package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2874;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public class BrightnessEvent implements Event {
   private float brightness;
   private final class_2874 type;
   private final int lightLevel;

   public BrightnessEvent(class_2874 type, int lightLevel, float brightness) {
      this.type = type;
      this.lightLevel = lightLevel;
      this.brightness = brightness;
   }

   @Generated
   public float brightness() {
      return this.brightness;
   }

   @Generated
   public class_2874 type() {
      return this.type;
   }

   @Generated
   public int lightLevel() {
      return this.lightLevel;
   }

   @Generated
   public BrightnessEvent brightness(float brightness) {
      this.brightness = brightness;
      return this;
   }
}
