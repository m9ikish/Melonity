package ru.melonity.util.inventory;

import java.util.Locale;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import org.jetbrains.annotations.Nullable;
import ru.melonity.feature.Feature;
import ru.melonity.util.inventory.old.switcher.Switcher;

@Environment(EnvType.CLIENT)
public class InventoryUtil {
   public static int getSlotInInventoryOrHotbar(class_1792 item, boolean inHotBar) {
      int local2_1 = inHotBar ? 0 : 9;
      int local3_2 = inHotBar ? 9 : 36;
      int local4_3 = -1;

      for (int local5_0 = local2_1; local5_0 < local3_2; local5_0++) {
         if (Feature.mc.field_1724.method_31548().method_5438(local5_0).method_7909() == item) {
            local4_3 = local5_0;
         }
      }

      return local4_3;
   }

   public static int getHotbarItem(class_1657 player, Predicate<class_1799> predicate) {
      for (int local2_0 = 0; local2_0 < 9; local2_0++) {
         if (predicate.test(player.method_31548().method_5438(local2_0))) {
            return local2_0;
         }
      }

      return -1;
   }

   public static class_1735 getSlot(class_1792 item) {
      if (Feature.mc.field_1724 != null && Feature.mc.field_1724.field_7512 != null) {
         for (class_1735 local2_0 : Feature.mc.field_1724.field_7512.field_7761) {
            if (local2_0.method_7681() && local2_0.method_7677().method_7909().equals(item)) {
               return local2_0;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static int getHotbarItem(class_1657 player, class_1792 item) {
      for (int local2_0 = 0; local2_0 < 9; local2_0++) {
         if (player.method_31548().method_5438(local2_0).method_7909().equals(item)) {
            return local2_0;
         }
      }

      return -1;
   }

   public static int getHotbarItem(class_1657 player, String name) {
      for (int local2_0 = 0; local2_0 < 9; local2_0++) {
         if (player.method_31548().method_5438(local2_0).method_7964().getString().equalsIgnoreCase(name)) {
            return local2_0;
         }
      }

      return -1;
   }

   public static int getHotbarItem(class_1657 player, Class<? extends class_1792> item) {
      for (int local2_0 = 0; local2_0 < 9; local2_0++) {
         if (player.method_31548().method_5438(local2_0).method_7909().getClass().equals(item)) {
            return local2_0;
         }
      }

      return -1;
   }

   public static void moveItem(int from, int to) {
      if (from != to && from != -1) {
         from = from < 9 ? from + 36 : from;
         clickSlotId(from, 0, class_1713.field_7791);
         clickSlotId(to, 0, class_1713.field_7791);
         clickSlotId(from, 0, class_1713.field_7791);
      }
   }

   public static void clickSlotId(int slotId, int buttonId, class_1713 clickType) {
      Feature.mc.field_1761.method_2906(Feature.mc.field_1724.field_7498.field_7763, slotId, buttonId, clickType, Feature.mc.field_1724);
   }

   public static int getInventoryItem(class_1657 player, class_1792 item) {
      for (class_1799 local3_0 : player.method_5661()) {
         if (local3_0.method_7909().equals(item)) {
            return -1;
         }
      }

      for (int local2_1 = 0; local2_1 < 9; local2_1++) {
         if (player.method_31548().method_5438(local2_1).method_7909().equals(item)) {
            return 36 + local2_1;
         }
      }

      for (int local2_2 = 0; local2_2 < player.method_31548().method_5439(); local2_2++) {
         if (player.method_31548().method_5438(local2_2).method_7909() == item) {
            return local2_2;
         }
      }

      return -1;
   }

   public static int getInventoryItem(class_1657 player, Predicate<class_1799> predicate) {
      for (int local2_0 = 0; local2_0 < player.method_31548().method_5439(); local2_0++) {
         if (predicate.test(player.method_31548().method_5438(local2_0))) {
            return local2_0;
         }
      }

      return -1;
   }

   public static int inventoryFreeSlot(class_1657 player) {
      for (int local1_0 = 9; local1_0 < player.method_31548().method_5439(); local1_0++) {
         if (player.method_31548().method_5438(local1_0).method_7960()) {
            return local1_0;
         }
      }

      return -1;
   }

   public static void use(Switcher switcher, Predicate<class_1799> predicate) {
      switcher.auto(predicate).then(() -> {
         class_1269 local1_0 = Feature.mc.field_1761.method_2919(Feature.mc.field_1724, class_1268.field_5808);
         if (local1_0.method_23665() && local1_0.method_23666()) {
            Feature.mc.field_1724.method_6104(class_1268.field_5808);
         }

         switcher.returnHotbar();
      });
   }

   @Nullable
   public static Integer findItem(String itemName, boolean onlyHotbar) {
      for (class_1735 local3_2 : Feature.mc.field_1724.field_7512.field_7761) {
         class_1799 local4_0 = local3_2.method_7677();
         boolean local5_1 = similar(local4_0, itemName);
         if (onlyHotbar && (local3_2.field_7874 - 36 < 0 || local3_2.field_7874 - 36 > 8)) {
            local5_1 = false;
         }

         if (local5_1) {
            return local3_2.field_7874;
         }
      }

      return null;
   }

   private static boolean similar(class_1799 stack, String itemName) {
      String local2_0 = itemName.toLowerCase(Locale.ROOT);
      return stack.method_7909().method_7848().getString().toLowerCase(Locale.ROOT).contains(local2_0)
         || stack.method_7964().getString().toLowerCase(Locale.ROOT).contains(local2_0)
         || stack.method_7909().method_7876().toLowerCase(Locale.ROOT).contains(local2_0);
   }
}
