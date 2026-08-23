package ru.melonity.util.minecraft;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1738;
import net.minecraft.class_1799;
import net.minecraft.class_243;
import net.minecraft.class_266;
import net.minecraft.class_268;
import net.minecraft.class_3532;
import net.minecraft.class_742;
import net.minecraft.class_8646;
import org.apache.commons.lang3.StringUtils;
import ru.melonity.util.event.events.MoveEvent;

@Environment(EnvType.CLIENT)
public class PlayerUtil extends MinecraftInstance {
   public int getPing(class_742 player) {
      return mc.method_1562() != null && mc.method_1562().method_2871(player.method_5667()) != null
         ? mc.method_1562().method_2871(player.method_5667()).method_2959()
         : -1;
   }

   public static void setMotion(MoveEvent move, double motion) {
      double local3_0 = (double)mc.field_1724.field_3913.field_3905;
      double local5_1 = (double)mc.field_1724.field_3913.field_3907;
      float local7_2 = mc.field_1724.method_36454();
      if (local3_0 == 0.0 && local5_1 == 0.0) {
         move.motion = new class_243(0.0, move.motion.field_1351, 0.0);
      } else {
         if (local3_0 != 0.0) {
            if (local5_1 > 0.0) {
               local7_2 += (float)(local3_0 > 0.0 ? -45 : 45);
            } else if (local5_1 < 0.0) {
               local7_2 += (float)(local3_0 > 0.0 ? 45 : -45);
            }

            local5_1 = 0.0;
            if (local3_0 > 0.0) {
               local3_0 = 1.0;
            } else if (local3_0 < 0.0) {
               local3_0 = -1.0;
            }
         }

         move.motion = new class_243(
            local3_0 * motion * (double)class_3532.method_15362((float)Math.toRadians((double)(local7_2 + 90.0F)))
               + local5_1 * motion * (double)class_3532.method_15374((float)Math.toRadians((double)(local7_2 + 90.0F))),
            move.motion.field_1351,
            local3_0 * motion * (double)class_3532.method_15374((float)Math.toRadians((double)(local7_2 + 90.0F)))
               - local5_1 * motion * (double)class_3532.method_15362((float)Math.toRadians((double)(local7_2 + 90.0F)))
         );
      }
   }

   public static class_243 calculateDirection(double speed) {
      float local2_0 = mc.field_1724.method_36454();
      float local3_1 = mc.field_1724.field_3913.field_3905;
      float local4_2 = -mc.field_1724.field_3913.field_3907;
      float local5_3 = local3_1 * local3_1 + local4_2 * local4_2;
      if (local5_3 < 1.0E-4F) {
         return class_243.field_1353;
      } else {
         local5_3 = (float)Math.sqrt((double)local5_3);
         if (local5_3 < 1.0F) {
            local5_3 = 1.0F;
         }

         float local6_4 = (float)Math.sin(Math.toRadians((double)(local2_0 + 90.0F)));
         float local7_5 = (float)Math.cos(Math.toRadians((double)(local2_0 + 90.0F)));
         local3_1 /= local5_3;
         local4_2 /= local5_3;
         return new class_243((double)(local3_1 * local7_5 - local4_2 * local6_4) * speed, 0.0, (double)(local3_1 * local6_4 + local4_2 * local7_5) * speed);
      }
   }

   public static boolean isheight(class_1309 player, int y) {
      int local2_1 = 999;

      for (int local3_0 = 0; local3_0 < 50; local3_0++) {
         if (!mc.field_1687.method_8320(player.method_24515().method_10069(0, -local3_0, 0)).method_26215()) {
            local2_1 = local3_0;
            break;
         }
      }

      return local2_1 <= y;
   }

   public static int getBalance() {
      class_266 local0_2 = mc.field_1687.method_8428().method_1189(class_8646.field_45157);
      if (local0_2 == null) {
         return -1;
      } else {
         for (class_268 local2_1 : local0_2.method_1117().method_1159()) {
            if (local2_1.method_1197().startsWith("TAB-Sidebar-")) {
               String local3_0 = local2_1.method_1144().getString().toLowerCase();
               if (local3_0.contains("монеты") || local3_0.contains("$")) {
                  return Integer.parseInt(local3_0.replaceAll("\\D", ""));
               }
            }
         }

         return -1;
      }
   }

   public static double getEntityHealth(class_1297 entity) {
      if (entity instanceof class_1657 local1_1) {
         double local3_0 = (double)getArmor(local1_1) / 20.0;
         return (double)(HealthResolveUtil.resolve(local1_1) + local1_1.method_6067()) * local3_0;
      } else {
         return entity instanceof class_1309 local2_2 ? (double)(local2_2.method_6032() + local2_2.method_6067()) : 0.0;
      }
   }

   public static int getArmor(class_1309 livingEntity) {
      int local1_2 = 0;

      for (class_1799 local3_1 : livingEntity.method_5661()) {
         if (local3_1.method_7909() instanceof class_1738 local4_0) {
            local1_2 += local4_0.method_7687();
         }
      }

      return local1_2;
   }

   public static void sendPlayerMessage(String message) {
      message = normalizeChatMessage(message);
      if (!message.isEmpty()) {
         mc.field_1705.method_1743().method_1803(message);
         if (message.startsWith("/")) {
            mc.field_1724.field_3944.method_45730(message.substring(1));
         } else {
            mc.field_1724.field_3944.method_45729(message);
         }
      }
   }

   private static String truncateStringIfNecessary(String p_144999_) {
      return p_144999_.length() <= 256 ? p_144999_ : p_144999_.substring(0, 256);
   }

   private static String trimChatMessage(String p_216470_) {
      return truncateStringIfNecessary(p_216470_);
   }

   private static String normalizeChatMessage(String p_232707_) {
      return trimChatMessage(StringUtils.normalizeSpace(p_232707_.trim()));
   }
}
