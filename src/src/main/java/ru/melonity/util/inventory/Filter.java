package ru.melonity.util.inventory;

import java.util.Map;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1792;

@Environment(EnvType.CLIENT)
public class Filter {
   private String name;
   private Map<Integer, class_1792> slots;

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public Map<Integer, class_1792> getSlots() {
      return this.slots;
   }

   @Generated
   public void setName(String name) {
      this.name = name;
   }

   @Generated
   public void setSlots(Map<Integer, class_1792> slots) {
      this.slots = slots;
   }

   @Generated
   public Filter(String name, Map<Integer, class_1792> slots) {
      this.name = name;
      this.slots = slots;
   }
}
