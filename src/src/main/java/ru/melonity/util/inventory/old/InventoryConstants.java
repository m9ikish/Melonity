package ru.melonity.util.inventory.old;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum InventoryConstants {
   HOTBAR_CLICK_START(36);

   private final int constant;

   private InventoryConstants(int constant) {
      this.constant = constant;
   }
}
