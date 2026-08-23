package ru.melonity.util.inventory.old.clicker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1713;
import net.minecraft.class_310;

@Environment(EnvType.CLIENT)
public class Clicker {
   private final class_310 mc = class_310.method_1551();
   private final int syncId;

   public Clicker(int syncId) {
      this.syncId = syncId;
   }

   public Clicker() {
      this(0);
   }

   public void click(int slotId, Clicker.Button button, class_1713 action) {
      this.mc.field_1761.method_2906(this.syncId, slotId, button.index, action, this.mc.field_1724);
   }

   public void clickFixed(int slotId, Clicker.Button button, class_1713 action) {
      if (slotId < 9) {
         slotId += 36;
      }

      this.click(slotId, button, action);
   }

   public void move(int from, int to) {
      if (from != to) {
         this.click(from, Clicker.Button.LMB_CLICK, class_1713.field_7790);
         this.click(to, Clicker.Button.LMB_CLICK, class_1713.field_7790);
         this.click(from, Clicker.Button.LMB_CLICK, class_1713.field_7790);
      }
   }

   public void moveFixed(int from, int to) {
      if (from != to) {
         this.clickFixed(from, Clicker.Button.LMB_CLICK, class_1713.field_7790);
         this.clickFixed(to, Clicker.Button.LMB_CLICK, class_1713.field_7790);
         this.clickFixed(from, Clicker.Button.LMB_CLICK, class_1713.field_7790);
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum Button {
      LMB_CLICK(0),
      RMB_CLICK(1);

      private final int index;

      private Button(int index) {
         this.index = index;
      }
   }
}
