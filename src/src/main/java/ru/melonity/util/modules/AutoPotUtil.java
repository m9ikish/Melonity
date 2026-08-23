package ru.melonity.util.modules;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1828;
import net.minecraft.class_1844;
import net.minecraft.class_310;
import net.minecraft.class_6880;
import net.minecraft.class_9334;

@Environment(EnvType.CLIENT)
public final class AutoPotUtil {
   private static final class_310 mc = class_310.method_1551();

   public static boolean isSplashPotion(class_1799 stack) {
      return stack != null && stack.method_7909() instanceof class_1828;
   }

   public static boolean isStackPotion(class_1799 stack, AutoPotUtil.PotionType type) {
      if (!isSplashPotion(stack)) {
         return false;
      } else {
         class_1844 local2_1 = (class_1844)stack.method_57825(class_9334.field_49651, class_1844.field_49274);

         for (class_1293 local4_0 : local2_1.method_57397()) {
            if (local4_0.method_5579() == type.getEffect()) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean hasActiveEffect(AutoPotUtil.PotionType type) {
      return mc.field_1724 == null ? false : mc.field_1724.method_6059(type.getEffect());
   }

   public static int findPotionSlot(AutoPotUtil.PotionType type) {
      if (mc.field_1724 == null) {
         return -1;
      } else {
         for (int local1_1 = 0; local1_1 < 9; local1_1++) {
            class_1799 local2_0 = mc.field_1724.method_31548().method_5438(local1_1);
            if (isStackPotion(local2_0, type)) {
               return local1_1;
            }
         }

         return -1;
      }
   }

   public static boolean canThrow() {
      if (mc.field_1724 == null) {
         return false;
      } else {
         return mc.field_1761 == null
            ? false
            : !mc.field_1724.method_6115() && !mc.field_1724.method_7357().method_7904(class_1802.field_8436) && mc.field_1724.method_24828();
      }
   }

   public static void switchToSlot(int slot) {
      if (mc.field_1724 != null) {
         if (slot >= 0 && slot <= 8) {
            mc.field_1724.method_31548().field_7545 = slot;
         }
      }
   }

   public static void useItem() {
      if (mc.field_1724 != null && mc.field_1761 != null) {
         class_1269 local0_0 = mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
         // field_5811 is PASS, and shouldSwingHand() is true only for SUCCESS /
         // SUCCESS_NO_ITEM_USED - the old conjunction could never hold, so the swing
         // packet was never sent and the potion went out with no arm animation.
         if (local0_0.method_23665() && local0_0.method_23666()) {
            mc.field_1724.method_6104(class_1268.field_5808);
         }
      }
   }

   public static void restoreSlot(int slot) {
      if (mc.field_1724 != null) {
         if (slot >= 0 && slot <= 8) {
            mc.field_1724.method_31548().field_7545 = slot;
         }
      }
   }

   public static float getTotalHealth() {
      return mc.field_1724 == null ? 0.0F : mc.field_1724.method_6032() + mc.field_1724.method_6067();
   }

   public static boolean needsHeal(float threshold) {
      return getTotalHealth() < threshold;
   }

   @Environment(EnvType.CLIENT)
   public static enum PotionType {
      STRENGTH(class_1294.field_5910),
      SPEED(class_1294.field_5904),
      FIRE_RESISTANCE(class_1294.field_5918),
      INSTANT_HEALTH(class_1294.field_5915);

      private final class_6880<class_1291> effect;

      private PotionType(class_6880<class_1291> effect) {
         this.effect = effect;
      }

      public class_6880<class_1291> getEffect() {
         return this.effect;
      }
   }
}
