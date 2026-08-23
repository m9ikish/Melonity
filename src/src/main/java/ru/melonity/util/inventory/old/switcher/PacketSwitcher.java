package ru.melonity.util.inventory.old.switcher;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1661;
import net.minecraft.class_2868;
import net.minecraft.class_310;
import ru.melonity.util.inventory.old.clicker.Clicker;

@Environment(EnvType.CLIENT)
public class PacketSwitcher implements Switcher {
   private final class_310 mc = class_310.method_1551();
   private final Clicker clicker = new Clicker();
   private int lastSlot = -1;
   private int lastHotbar = -1;

   @Override
   public void fromHotbar(int slot) {
      this.lastHotbar = this.mc.field_1724.method_31548().field_7545;
      this.mc.field_1724.field_3944.method_52787(new class_2868(slot));
   }

   @Override
   public void fromInventory(int slot, Runnable callback) {
      class_1661 local3_0 = this.mc.field_1724.method_31548();
      int local4_1 = local3_0.field_7545;
      int local5_2 = 36 + local4_1;
      this.lastSlot = slot;
      this.lastHotbar = -1;
      this.clicker.move(slot, local5_2);
      if (callback != null) {
         callback.run();
      }
   }

   @Override
   public void tick() {
   }

   @Override
   public void returnHotbar() {
      if (this.lastSlot != -1) {
         int local1_0 = this.mc.field_1724.method_31548().field_7545 + 36;
         this.clicker.move(local1_0, this.lastSlot);
         this.lastSlot = -1;
      }

      if (this.lastHotbar != -1) {
         this.mc.field_1724.field_3944.method_52787(new class_2868(this.lastHotbar));
         this.lastHotbar = -1;
      }
   }
}
