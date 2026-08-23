package ru.melonity.util.modules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2561;
import net.minecraft.class_6880;
import net.minecraft.class_9334;

@Environment(EnvType.CLIENT)
public final class ServerAssistUtil {
   public static final class_2248[] SHULKER_BOXES = new class_2248[]{
      class_2246.field_10600,
      class_2246.field_10068,
      class_2246.field_10268,
      class_2246.field_10140,
      class_2246.field_10320,
      class_2246.field_10203,
      class_2246.field_10532
   };
   public static final Map<class_2248, Color> SHULKER_COLORS = Map.of(
      class_2246.field_10600,
      Color.YELLOW,
      class_2246.field_10068,
      Color.RED,
      class_2246.field_10268,
      Color.MAGENTA,
      class_2246.field_10140,
      Color.GRAY,
      class_2246.field_10320,
      Color.LIGHT_GRAY,
      class_2246.field_10203,
      Color.BLUE,
      class_2246.field_10532,
      Color.CYAN
   );
   public static final String[] BAN_WORDS = new String[]{
      "экспа",
      "экспенсив",
      "экспой",
      "нуриком",
      "целкой",
      "нурлан",
      "нурсултан",
      "целестиал",
      "целка",
      "нурик",
      "атернос",
      "expa",
      "celka",
      "nurik",
      "expensive",
      "celestial",
      "nursultan",
      "фанпей",
      "funpay",
      "fluger",
      "акриен",
      "akrien",
      "фантайм",
      "ft",
      "funtime",
      "безмамный",
      "rich",
      "рич",
      "без мамный",
      "wild",
      "вилд",
      "excellent",
      "экселлент",
      "hvh",
      "хвх",
      "matix",
      "impact",
      "матикс",
      "excellent",
      "екселент",
      "excellent upgrade",
      "екселент апргейд",
      "excellent upd",
      "екселент апд",
      "импакт",
      "wurst"
   };

   public static boolean hasEffectWithLevel(class_1657 player, class_6880<class_1291> effect, int minLevel) {
      if (!player.method_6059(effect)) {
         return false;
      } else {
         class_1293 local3_0 = player.method_6112(effect);
         return local3_0 != null && local3_0.method_5578() >= minLevel;
      }
   }

   public static boolean hasGodAuraEffects(class_1657 player) {
      return hasEffectWithLevel(player, class_1294.field_5911, 1)
         && hasEffectWithLevel(player, class_1294.field_5909, 1)
         && hasEffectWithLevel(player, class_1294.field_5920, 1)
         && hasEffectWithLevel(player, class_1294.field_5899, 1);
   }

   public static boolean hasItemWithName(class_1657 player, class_1792 item, String name) {
      if (player == null) {
         return false;
      } else {
         for (int local3_1 = 0; local3_1 < player.method_31548().method_5439(); local3_1++) {
            class_1799 local4_0 = player.method_31548().method_5438(local3_1);
            if (matchesItemAndName(local4_0, item, name)) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean matchesItemAndName(class_1799 stack, class_1792 item, String name) {
      if (stack.method_7960()) {
         return false;
      } else if (stack.method_7909() != item) {
         return false;
      } else {
         class_2561 local3_0 = (class_2561)stack.method_57824(class_9334.field_49631);
         if (local3_0 == null) {
            return false;
         } else {
            String local4_1 = stack.method_7964().getString().toLowerCase();
            return local4_1.contains(name.toLowerCase());
         }
      }
   }

   public static boolean containsBanWord(String text) {
      if (text != null && !text.isEmpty()) {
         String local1_1 = text.toLowerCase();

         for (String local5_0 : BAN_WORDS) {
            if (local1_1.contains(local5_0.toLowerCase())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static List<class_2338> findNearbyShulkers(class_1937 world, class_2338 center, int horizontalRadius, int verticalRadius) {
      List<class_2338> local4_5 = new ArrayList<>();
      Set<class_2248> local5_6 = new HashSet<>(Arrays.asList(SHULKER_BOXES));

      for (int local6_4 = -horizontalRadius; local6_4 <= horizontalRadius; local6_4++) {
         for (int local7_3 = -verticalRadius; local7_3 <= verticalRadius; local7_3++) {
            for (int local8_2 = -horizontalRadius; local8_2 <= horizontalRadius; local8_2++) {
               class_2338 local9_0 = center.method_10069(local6_4, local7_3, local8_2);
               class_2248 local10_1 = world.method_8320(local9_0).method_26204();
               if (local5_6.contains(local10_1)) {
                  local4_5.add(local9_0);
               }
            }
         }
      }

      return local4_5;
   }

   public static boolean isShulkerBox(class_2248 block) {
      return SHULKER_COLORS.containsKey(block);
   }

   public static Color getShulkerColor(class_2248 block) {
      return SHULKER_COLORS.getOrDefault(block, Color.WHITE);
   }

   public static int findItemSlotInInventory(class_1657 player, class_1792 item, String name, boolean hotbarOnly) {
      if (player == null) {
         return -1;
      } else {
         for (int local4_1 = 0; local4_1 < 9; local4_1++) {
            class_1799 local5_0 = player.method_31548().method_5438(local4_1);
            if (matchesItemAndName(local5_0, item, name)) {
               return local4_1;
            }
         }

         if (!hotbarOnly) {
            for (int local4_3 = 9; local4_3 < 36; local4_3++) {
               class_1799 local5_2 = player.method_31548().method_5438(local4_3);
               if (matchesItemAndName(local5_2, item, name)) {
                  return local4_3;
               }
            }
         }

         return -1;
      }
   }

   public static int findEmptyHotbarSlot(class_1657 player) {
      if (player == null) {
         return -1;
      } else {
         for (int local1_0 = 0; local1_0 < 9; local1_0++) {
            if (player.method_31548().method_5438(local1_0).method_7960()) {
               return local1_0;
            }
         }

         return -1;
      }
   }
}
