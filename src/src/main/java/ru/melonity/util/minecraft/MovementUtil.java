package ru.melonity.util.minecraft;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import ru.melonity.util.event.events.EventInput;

@Environment(EnvType.CLIENT)
public class MovementUtil {
   private static final class_310 mc = class_310.method_1551();

   public static boolean isMoving() {
      return mc.field_1724.field_6250 != 0.0F || mc.field_1724.field_6212 != 0.0F;
   }

   public static float getSpeedSQRT(class_1657 entity) {
      float local1_0 = (float)(entity.method_23317() - entity.field_6014);
      float local2_1 = (float)(entity.method_23321() - entity.field_5969);
      return (float)Math.sqrt((double)(local1_0 * local1_0 + local2_1 * local2_1));
   }

   public static double getMotion() {
      return Math.hypot(mc.field_1724.method_18798().field_1352, mc.field_1724.method_18798().field_1350);
   }

   public static void setMotion(double speed) {
      if (isMoving()) {
         double local2_0 = getDirection(true);
         mc.field_1724.method_18800(-Math.sin(local2_0) * speed, mc.field_1724.method_18798().field_1351, Math.cos(local2_0) * speed);
      }
   }

   public static double getDirection(boolean toRadians) {
      float local1_0 = mc.field_1724.method_36454();
      if (mc.field_1724.field_6250 < 0.0F) {
         local1_0 += 180.0F;
      }

      float local2_1 = 1.0F;
      if (mc.field_1724.field_6250 < 0.0F) {
         local2_1 = -0.5F;
      } else if (mc.field_1724.field_6250 > 0.0F) {
         local2_1 = 0.5F;
      }

      if (mc.field_1724.field_6212 > 0.0F) {
         local1_0 -= 90.0F * local2_1;
      }

      if (mc.field_1724.field_6212 < 0.0F) {
         local1_0 += 90.0F * local2_1;
      }

      return toRadians ? Math.toRadians((double)local1_0) : (double)local1_0;
   }

   public static double direction(float rotationYaw, double zza, double xxa) {
      if (zza < 0.0) {
         rotationYaw += 180.0F;
      }

      float local5_0 = 1.0F;
      if (zza < 0.0) {
         local5_0 = -0.5F;
      } else if (zza > 0.0) {
         local5_0 = 0.5F;
      }

      if (xxa > 0.0) {
         rotationYaw -= 90.0F * local5_0;
      }

      if (xxa < 0.0) {
         rotationYaw += 90.0F * local5_0;
      }

      return Math.toRadians((double)rotationYaw);
   }

   public static class_243 fix(float yaw, class_243 movementInput, float speed) {
      double local3_0 = movementInput.method_1027();
      if (local3_0 < 1.0E-7) {
         return class_243.field_1353;
      } else {
         class_243 local5_1 = (local3_0 > 1.0 ? movementInput.method_1029() : movementInput).method_1021((double)speed);
         float local6_2 = class_3532.method_15374(yaw * (float) (Math.PI / 180.0));
         float local7_3 = class_3532.method_15362(yaw * (float) (Math.PI / 180.0));
         return new class_243(
            local5_1.field_1352 * (double)local7_3 - local5_1.field_1350 * (double)local6_2,
            local5_1.field_1351,
            local5_1.field_1350 * (double)local7_3 + local5_1.field_1352 * (double)local6_2
         );
      }
   }

   public static void fixMovement(EventInput event, float yaw) {
      float local2_4 = event.getForward();
      float local3_5 = event.getStrafe();
      double local4_6 = class_3532.method_15338(Math.toDegrees(direction(class_310.method_1551().field_1724.field_6241, (double)local2_4, (double)local3_5)));
      if (local2_4 != 0.0F || local3_5 != 0.0F) {
         float local6_7 = 0.0F;
         float local7_8 = 0.0F;
         float local8_9 = Float.MAX_VALUE;

         for (float local9_3 = -1.0F; local9_3 <= 1.0F; local9_3++) {
            for (float local10_2 = -1.0F; local10_2 <= 1.0F; local10_2++) {
               if (local10_2 != 0.0F || local9_3 != 0.0F) {
                  double local11_0 = class_3532.method_15338(Math.toDegrees(direction(yaw, (double)local9_3, (double)local10_2)));
                  double local13_1 = Math.abs(local4_6 - local11_0);
                  if (local13_1 < (double)local8_9) {
                     local8_9 = (float)local13_1;
                     local6_7 = local9_3;
                     local7_8 = local10_2;
                  }
               }
            }
         }

         event.setForward(local6_7);
         event.setStrafe(local7_8);
      }
   }

   public static double[] forward(double d) {
      float local2_0 = mc.field_1724.field_3913.field_3905;
      float local3_1 = mc.field_1724.field_3913.field_3907;
      float local4_2 = mc.field_1724.method_36454();
      if (local2_0 != 0.0F) {
         if (local3_1 > 0.0F) {
            local4_2 += (float)(local2_0 > 0.0F ? -45 : 45);
         } else if (local3_1 < 0.0F) {
            local4_2 += (float)(local2_0 > 0.0F ? 45 : -45);
         }

         local3_1 = 0.0F;
         if (local2_0 > 0.0F) {
            local2_0 = 1.0F;
         } else if (local2_0 < 0.0F) {
            local2_0 = -1.0F;
         }
      }

      double local5_3 = Math.sin(Math.toRadians((double)(local4_2 + 90.0F)));
      double local7_4 = Math.cos(Math.toRadians((double)(local4_2 + 90.0F)));
      double local9_5 = (double)local2_0 * d * local7_4 + (double)local3_1 * d * local5_3;
      double local11_6 = (double)local2_0 * d * local5_3 - (double)local3_1 * d * local7_4;
      return new double[]{local9_5, local11_6};
   }
}
