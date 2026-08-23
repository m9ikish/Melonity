package ru.melonity.util.inventory.old.switcher;

import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import ru.melonity.util.inventory.InventoryUtil;

@Environment(EnvType.CLIENT)
public interface Switcher {
   void fromHotbar(int var1);

   default void fromInventory(int slot) {
      this.fromInventory(slot, null);
   }

   void fromInventory(int var1, Runnable var2);

   void tick();

   default Switcher.SwitcherAction auto(Predicate<class_1799> predicate) {
      int hotbarSlot = InventoryUtil.getHotbarItem(class_310.method_1551().field_1724, predicate);
      int inventorySlot = InventoryUtil.getInventoryItem(class_310.method_1551().field_1724, predicate);
      if (hotbarSlot != -1) {
         this.fromHotbar(hotbarSlot);
         return new Switcher.SwitcherAction(true);
      } else {
         return inventorySlot != -1 ? new Switcher.SwitcherAction(this, inventorySlot) : new Switcher.SwitcherAction(false);
      }
   }

   default Switcher.SwitcherAction auto(class_1799 itemStack) {
      return this.auto((Predicate<class_1799>)(it -> !it.method_7960() && it.method_7909().equals(itemStack.method_7909())));
   }

   void returnHotbar();

   @Environment(EnvType.CLIENT)
   public static class SwitcherAction {
      private final boolean immediate;
      private final Switcher switcher;
      private final int slot;

      public SwitcherAction(boolean immediate) {
         this.immediate = immediate;
         this.switcher = null;
         this.slot = -1;
      }

      public SwitcherAction(Switcher switcher, int slot) {
         this.immediate = false;
         this.switcher = switcher;
         this.slot = slot;
      }

      public void then(Runnable callback) {
         if (this.immediate) {
            callback.run();
         } else if (this.switcher != null && this.slot != -1) {
            this.switcher.fromInventory(this.slot, callback);
         }
      }
   }
}
