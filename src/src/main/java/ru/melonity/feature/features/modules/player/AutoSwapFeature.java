package ru.melonity.feature.features.modules.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.KeyEvent;
import ru.melonity.util.inventory.InventoryUtil;

@Environment(EnvType.CLIENT)
public class AutoSwapFeature extends SimpleFeature {
   private final BindSetting keybind = new BindSetting(-1);
   private final EventListener<KeyEvent> onKey = event -> {
      if (event.key() == this.keybind.bind) {
         this.performSwap();
      }
   };

   public AutoSwapFeature() {
      super("AutoSwap", MenuCategory.PLAYER);
      this.settingClear();
      this.setting(this.keybind);
   }

   private void performSwap() {
      if (mc.field_1724 != null) {
         class_1799 local1_2 = mc.field_1724.method_6079();
         if (!local1_2.method_7960()) {
            String local2_3 = local1_2.method_7964().getString().toLowerCase();
            if (local2_3.contains("талисман")) {
               int local3_0 = InventoryUtil.getInventoryItem(mc.field_1724, itemStack -> itemStack.method_7964().getString().toLowerCase().contains("сфера"));
               if (local3_0 < 9) {
                  local3_0 += 36;
               }

               if (local3_0 != -1) {
                  this.swapWithOffhand(local3_0);
               }
            } else if (local2_3.contains("сфера")) {
               int local3_1 = InventoryUtil.getInventoryItem(mc.field_1724, itemStack -> itemStack.method_7964().getString().toLowerCase().contains("талисман"));
               if (local3_1 < 9) {
                  local3_1 += 36;
               }

               if (local3_1 != -1) {
                  this.swapWithOffhand(local3_1);
               }
            }
         }
      }
   }

   private void swapWithOffhand(int slot) {
      mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 45, 0, class_1713.field_7790, mc.field_1724);
      mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, slot, 0, class_1713.field_7790, mc.field_1724);
      mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, 45, 0, class_1713.field_7790, mc.field_1724);
   }
}
