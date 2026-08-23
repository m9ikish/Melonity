package ru.melonity.util.modules;

import java.util.Arrays;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1511;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_238;
import net.minecraft.class_310;
import ru.melonity.util.inventory.InventoryUtil;

@Environment(EnvType.CLIENT)
public final class AutoTotemUtil {
   private static final class_310 mc = class_310.method_1551();
   public static final int OFFHAND_SLOT = 45;
   private static final List<String> TALISMAN_KEYWORDS = Arrays.asList("талисман", "talisman", "lucky");
   private static final List<String> BALL_KEYWORDS = Arrays.asList("шар", "head", "ball", "голова", "skull");

   private AutoTotemUtil() {
   }

   public static boolean isTotem(class_1799 stack) {
      return stack != null && !stack.method_7960() && stack.method_7909() == class_1802.field_8288;
   }

   public static boolean hasTotemInOffhand() {
      return mc.field_1724 != null && isTotem(mc.field_1724.method_6079());
   }

   public static int findTotemSlot(boolean avoidTalisman) {
      if (mc.field_1724 == null) {
         return -1;
      } else {
         for (int local1_1 = 0; local1_1 < 9; local1_1++) {
            class_1799 local2_0 = mc.field_1724.method_31548().method_5438(local1_1);
            if (isTotem(local2_0) && isValidTotem(local2_0, avoidTalisman)) {
               return local1_1;
            }
         }

         for (int local1_3 = 9; local1_3 < 36; local1_3++) {
            class_1799 local2_2 = mc.field_1724.method_31548().method_5438(local1_3);
            if (isTotem(local2_2) && isValidTotem(local2_2, avoidTalisman)) {
               return local1_3;
            }
         }

         return -1;
      }
   }

   public static int findTotemInHotbar(boolean avoidTalisman) {
      if (mc.field_1724 == null) {
         return -1;
      } else {
         for (int local1_1 = 0; local1_1 < 9; local1_1++) {
            class_1799 local2_0 = mc.field_1724.method_31548().method_5438(local1_1);
            if (isTotem(local2_0) && isValidTotem(local2_0, avoidTalisman)) {
               return local1_1;
            }
         }

         return -1;
      }
   }

   public static boolean isValidTotem(class_1799 stack, boolean avoidTalisman) {
      if (!isTotem(stack)) {
         return false;
      } else {
         return !avoidTalisman ? true : !isTalisman(stack);
      }
   }

   public static boolean isTalisman(class_1799 stack) {
      if (stack != null && !stack.method_7960()) {
         String local1_1 = stack.method_7964().getString().toLowerCase();

         for (String local3_0 : TALISMAN_KEYWORDS) {
            if (local1_1.contains(local3_0)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static void swapToOffhand(int slot) {
      if (slot >= 0 && slot <= 44 && mc.field_1724 != null) {
         InventoryUtil.moveItem(slot, 45);
      }
   }

   public static void swapFromOffhand(int targetSlot) {
      if (targetSlot >= 0 && targetSlot <= 44 && mc.field_1724 != null) {
         InventoryUtil.moveItem(45, targetSlot);
      }
   }

   public static void swapSlots(int slotA, int slotB) {
      if (slotA >= 0 && slotA <= 44 && slotB >= 0 && slotB <= 44 && mc.field_1724 != null) {
         InventoryUtil.moveItem(slotA, slotB);
      }
   }

   public static float getHealth(boolean includeAbsorption) {
      if (mc.field_1724 == null) {
         return 0.0F;
      } else {
         float local1_0 = mc.field_1724.method_6032();
         if (includeAbsorption) {
            local1_0 += mc.field_1724.method_6067();
         }

         return local1_0;
      }
   }

   public static boolean isWearingElytra() {
      return mc.field_1724 == null ? false : mc.field_1724.method_31548().method_7372(2).method_7909() == class_1802.field_8833;
   }

   public static boolean isFalling(float minDistance) {
      return mc.field_1724 == null ? false : mc.field_1724.field_6017 >= minDistance;
   }

   public static boolean isCrystalNearby(float range) {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         class_238 local1_0 = new class_238(
            mc.field_1724.method_23317() - (double)range,
            mc.field_1724.method_23318() - (double)range,
            mc.field_1724.method_23321() - (double)range,
            mc.field_1724.method_23317() + (double)range,
            mc.field_1724.method_23318() + (double)range,
            mc.field_1724.method_23321() + (double)range
         );
         return !mc.field_1687.method_8390(class_1511.class, local1_0, crystal -> true).isEmpty();
      } else {
         return false;
      }
   }

   public static boolean isBallInOffhand() {
      return mc.field_1724 == null ? false : isBall(mc.field_1724.method_6079());
   }

   public static boolean isBall(class_1799 stack) {
      if (stack != null && !stack.method_7960()) {
         String local1_1 = stack.method_7964().getString().toLowerCase();

         for (String local3_0 : BALL_KEYWORDS) {
            if (local1_1.contains(local3_0)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
