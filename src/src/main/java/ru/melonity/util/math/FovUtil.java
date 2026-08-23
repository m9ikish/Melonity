package ru.melonity.util.math;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;

@Environment(EnvType.CLIENT)
public final class FovUtil {
   private static final class_310 mc = class_310.method_1551();

   private FovUtil() {
   }

   public static float getFovDistance(class_1297 entity) {
      return mc.field_1724 != null && entity != null ? getFovDistance(entity.method_33571()) : 180.0F;
   }

   public static float getFovDistance(class_243 targetPos) {
      if (mc.field_1724 == null) {
         return 180.0F;
      } else {
         class_243 local1_0 = mc.field_1724.method_33571();
         double local2_1 = targetPos.field_1352 - local1_0.field_1352;
         double local4_2 = targetPos.field_1350 - local1_0.field_1350;
         float local6_3 = (float)Math.toDegrees(Math.atan2(-local2_1, local4_2));
         float local7_4 = class_3532.method_15393(local6_3 - mc.field_1724.method_36454());
         return Math.abs(local7_4);
      }
   }

   public static float getFovDistanceFull(class_1297 entity) {
      return mc.field_1724 != null && entity != null ? getFovDistanceFull(entity.method_33571()) : 180.0F;
   }

   public static float getFovDistanceFull(class_243 targetPos) {
      if (mc.field_1724 == null) {
         return 180.0F;
      } else {
         class_243 local1_0 = mc.field_1724.method_33571();
         double local2_1 = targetPos.field_1352 - local1_0.field_1352;
         double local4_2 = targetPos.field_1351 - local1_0.field_1351;
         double local6_3 = targetPos.field_1350 - local1_0.field_1350;
         double local8_4 = Math.sqrt(local2_1 * local2_1 + local6_3 * local6_3);
         float local10_5 = (float)Math.toDegrees(Math.atan2(-local2_1, local6_3));
         float local11_6 = (float)Math.toDegrees(-Math.atan2(local4_2, local8_4));
         float local12_7 = class_3532.method_15393(local10_5 - mc.field_1724.method_36454());
         float local13_8 = local11_6 - mc.field_1724.method_36455();
         return (float)Math.sqrt((double)(local12_7 * local12_7 + local13_8 * local13_8));
      }
   }

   public static boolean isInFov(class_1297 entity, float fov) {
      return getFovDistance(entity) <= fov;
   }

   public static boolean isInFovFull(class_1297 entity, float fov) {
      return getFovDistanceFull(entity) <= fov;
   }

   public static float[] getDeltaAngles(class_1297 entity) {
      return mc.field_1724 != null && entity != null ? getDeltaAngles(entity.method_33571()) : new float[]{0.0F, 0.0F};
   }

   public static float[] getDeltaAngles(class_243 targetPos) {
      if (mc.field_1724 == null) {
         return new float[]{0.0F, 0.0F};
      } else {
         class_243 local1_0 = mc.field_1724.method_33571();
         double local2_1 = targetPos.field_1352 - local1_0.field_1352;
         double local4_2 = targetPos.field_1351 - local1_0.field_1351;
         double local6_3 = targetPos.field_1350 - local1_0.field_1350;
         double local8_4 = Math.sqrt(local2_1 * local2_1 + local6_3 * local6_3);
         float local10_5 = (float)Math.toDegrees(Math.atan2(-local2_1, local6_3));
         float local11_6 = (float)Math.toDegrees(-Math.atan2(local4_2, local8_4));
         float local12_7 = class_3532.method_15393(local10_5 - mc.field_1724.method_36454());
         float local13_8 = local11_6 - mc.field_1724.method_36455();
         return new float[]{local12_7, local13_8};
      }
   }

   public static float[] getTargetAngles(class_1297 entity) {
      return mc.field_1724 != null && entity != null ? getTargetAngles(entity.method_33571()) : new float[]{0.0F, 0.0F};
   }

   public static float[] getTargetAngles(class_243 targetPos) {
      if (mc.field_1724 == null) {
         return new float[]{0.0F, 0.0F};
      } else {
         class_243 local1_0 = mc.field_1724.method_33571();
         double local2_1 = targetPos.field_1352 - local1_0.field_1352;
         double local4_2 = targetPos.field_1351 - local1_0.field_1351;
         double local6_3 = targetPos.field_1350 - local1_0.field_1350;
         double local8_4 = Math.sqrt(local2_1 * local2_1 + local6_3 * local6_3);
         float local10_5 = (float)Math.toDegrees(Math.atan2(-local2_1, local6_3));
         float local11_6 = (float)Math.toDegrees(-Math.atan2(local4_2, local8_4));
         return new float[]{local10_5, local11_6};
      }
   }

   public static float normalizeAngle(float angle) {
      return class_3532.method_15393(angle);
   }
}
