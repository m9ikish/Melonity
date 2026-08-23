package ru.melonity.util.client;

import java.lang.management.ManagementFactory;
import java.security.SecureRandom;
import java.util.UUID;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_238;
import net.minecraft.class_241;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.joml.Vector4f;
import ru.melonity.util.minecraft.EntityRaycastUtil;
import ru.melonity.util.minecraft.RotationUtil;

@Environment(EnvType.CLIENT)
public final class AuraUtil {
   private static final class_310 mc = class_310.method_1551();
   private static final SecureRandom secureRandom = new SecureRandom();

   public static class_243 getClosestVec(class_243 vec, class_238 AABB) {
      return new class_243(
         class_3532.method_15350(vec.method_10216(), AABB.field_1323, AABB.field_1320),
         class_3532.method_15350(vec.method_10214(), AABB.field_1322, AABB.field_1325),
         class_3532.method_15350(vec.method_10215(), AABB.field_1321, AABB.field_1324)
      );
   }

   public static boolean canReachTarget(class_1309 target, double reachDistance) {
      if (mc.field_1724 != null && target != null && mc.field_1687 != null) {
         class_238 local3_0 = target.method_5829().method_1014(0.3);
         if (local3_0.method_1006(mc.field_1724.method_33571())) {
            return true;
         } else {
            class_243 local4_1 = target.method_19538().method_1031(0.0, (double)target.method_17682() / 2.0, 0.0);
            if (mc.field_1724.method_33571().method_1022(local4_1) > reachDistance + 0.5) {
               return false;
            } else {
               new class_3959(mc.field_1724.method_33571(), local4_1, class_3960.field_17558, class_242.field_1348, mc.field_1724);
               return local3_0.method_992(mc.field_1724.method_33571(), local4_1).isPresent();
            }
         }
      } else {
         return false;
      }
   }

   public static class_241 applySensitivityPatch(class_241 rotation, class_241 prevRotation) {
      double local2_0 = (Double)mc.field_1690.method_42495().method_41753() * 0.6 + 0.2;
      double local4_1 = local2_0 * local2_0 * local2_0 * 8.0;
      double local6_2 = Math.ceil((double)(rotation.field_1343 - prevRotation.field_1343) / local4_1 / 0.15) * local4_1 * 0.15;
      double local8_3 = Math.ceil((double)(rotation.field_1342 - prevRotation.field_1342) / local4_1 / 0.15) * local4_1 * 0.15;
      return new class_241((float)((double)prevRotation.field_1343 + local6_2), (float)((double)prevRotation.field_1342 + local8_3));
   }

   public static long generateUniqueSeed() {
      long local0_0 = System.currentTimeMillis();
      long local2_1 = System.nanoTime();
      long local4_2 = (long)ManagementFactory.getRuntimeMXBean().getName().hashCode();
      long local6_3 = (long)UUID.randomUUID().toString().hashCode();
      SecureRandom local8_4 = new SecureRandom();
      long local9_5 = local8_4.nextLong();
      long local11_6 = local0_0 ^ local2_1;
      local11_6 ^= local4_2;
      local11_6 ^= local6_3;
      local11_6 ^= local9_5;
      return (long)((float)local11_6 * secureRandom.nextFloat());
   }

   public static float random() {
      SecureRandom local0_0 = new SecureRandom();
      local0_0.setSeed(generateUniqueSeed());
      return local0_0.nextFloat();
   }

   public static float random(float min, float max) {
      SecureRandom local2_0 = new SecureRandom();
      local2_0.setSeed(generateUniqueSeed());
      return local2_0.nextFloat() * (max - min) + min;
   }

   public static class_243 getClosestVec(class_243 vec, class_1297 entity) {
      return getClosestVec(vec, entity.method_5829());
   }

   public static class_243 getClosestVec(class_1297 entity) {
      class_243 local1_0 = mc.field_1724.method_5836(mc.method_60646().method_60637(true));
      return getClosestVec(local1_0, entity).method_1020(local1_0);
   }

   public static double getStrictDistance(class_1297 entity) {
      return getClosestVec(entity).method_1033();
   }

   public static double getStrictDistance(class_1309 entity) {
      return getClosestVec(entity).method_1033();
   }

   public static class_243 getClosestTargetPoint(class_1297 entity) {
      return getClosestTargetPoint(
         mc.field_1724.method_5836(mc.method_60646().method_60637(true)), entity, Math.min(entity.method_17681(), entity.method_17682()) / 4.0F
      );
   }

   public static class_243 getClosestTargetPoint(class_243 vec, class_1297 entity, float point) {
      if (entity == null) {
         return class_243.field_1353;
      } else {
         class_238 local3_13 = entity.method_5829().method_1014((double)(-point));
         class_243 local4_14 = local3_13.method_1005();
         class_243 local5_15 = class_243.field_1353;
         double local6_16 = Double.MAX_VALUE;

         for (double local8_12 = 0.0; local8_12 <= (local3_13.field_1320 - local3_13.field_1323) / 2.0; local8_12 += 0.1) {
            for (double local10_11 = 0.0; local10_11 <= (local3_13.field_1325 - local3_13.field_1322) / 2.0; local10_11 += 0.1) {
               for (double local12_10 = 0.0; local12_10 <= (local3_13.field_1324 - local3_13.field_1321) / 2.0; local12_10 += 0.1) {
                  for (int local17_9 : new int[]{-1, 1}) {
                     for (int local21_8 : new int[]{-1, 1}) {
                        for (int local25_7 : new int[]{-1, 1}) {
                           double local26_1 = local4_14.field_1352 + (double)local17_9 * local8_12;
                           double local28_2 = local4_14.field_1351 + (double)local21_8 * local10_11;
                           double local30_3 = local4_14.field_1350 + (double)local25_7 * local12_10;
                           class_243 local32_4 = new class_243(local26_1, local28_2, local30_3);
                           class_241 local33_5 = RotationUtil.calculate(local32_4);
                           class_1297 local34_6 = EntityRaycastUtil.getMouseOver(entity, local33_5.field_1343, local33_5.field_1342, 3.0);
                           if (local34_6 != null && local34_6.method_5628() == entity.method_5628()) {
                              double local35_0 = vec.method_1022(local32_4);
                              if (local35_0 < local6_16) {
                                 local6_16 = local35_0;
                                 local5_15 = local32_4;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         if (!local5_15.equals(class_243.field_1353)) {
            return local5_15;
         } else {
            double local8_17 = class_3532.method_15350(vec.field_1352, local3_13.field_1323, local3_13.field_1320);
            double local10_18 = class_3532.method_15350(vec.field_1351, local3_13.field_1322, local3_13.field_1325);
            double local12_19 = class_3532.method_15350(vec.field_1350, local3_13.field_1321, local3_13.field_1324);
            return new class_243(local8_17, local10_18, local12_19);
         }
      }
   }

   public static Vector4f calculateRotation(class_1297 target) {
      class_243 local1_0 = getClosestTargetPoint(target).method_1020(mc.field_1724.method_5836(mc.method_60646().method_60637(true)));
      float local2_1 = (float)class_3532.method_15338(Math.toDegrees(Math.atan2(local1_0.field_1350, local1_0.field_1352)) - 90.0);
      float local3_2 = (float)(
         -Math.toDegrees(Math.atan2(local1_0.field_1351, Math.sqrt(Math.pow(local1_0.field_1352, 2.0) + Math.pow(local1_0.field_1350, 2.0))))
      );
      float local4_3 = class_3532.method_15393(local2_1 - mc.field_1724.method_36454());
      float local5_4 = local3_2 - mc.field_1724.method_36455();
      return new Vector4f(local2_1, local3_2, local4_3, local5_4);
   }

   public static Vector4f calculateRotationFromCamera(class_1309 target) {
      class_243 local1_0 = getClosestTargetPoint(target).method_1020(mc.field_1724.method_5836(mc.method_60646().method_60637(true)));
      float local2_1 = (float)class_3532.method_15338(Math.toDegrees(Math.atan2(local1_0.field_1350, local1_0.field_1352)) - 90.0);
      float local3_2 = (float)(
         -Math.toDegrees(Math.atan2(local1_0.field_1351, Math.sqrt(Math.pow(local1_0.field_1352, 2.0) + Math.pow(local1_0.field_1350, 2.0))))
      );
      float local4_3 = class_3532.method_15393(local2_1 - mc.field_1724.method_36454());
      float local5_4 = local3_2 - mc.field_1724.method_36455();
      return new Vector4f(local2_1, local3_2, local4_3, local5_4);
   }

   public static double calculateFOV(class_1309 target) {
      Vector4f local1_0 = calculateRotation(target);
      float local2_1 = local1_0.z;
      float local3_2 = local1_0.w;
      return Math.sqrt((double)(local2_1 * local2_1 + local3_2 * local3_2));
   }

   public static double calculateFOVFromCamera(class_1309 target) {
      Vector4f local1_0 = calculateRotationFromCamera(target);
      float local2_1 = local1_0.z;
      float local3_2 = local1_0.w;
      return Math.sqrt((double)(local2_1 * local2_1 + local3_2 * local3_2));
   }

   public static class_243 getVecForElytra(class_1309 target) {
      double local1_1 = mc.field_1724.method_23320() - target.method_23320();
      double local3_2 = class_3532.method_15350(local1_1, (double)target.method_17682() / 2.5, (double)target.method_17682() / 1.2);
      double local5_3 = (double)mc.field_1724.method_5739(target);
      if (local5_3 > 4.0) {
         local3_2 *= 0.75;
      } else if (local5_3 < 2.0) {
         local3_2 = (double)target.method_17682() / 1.8;
      }

      class_243 local7_4 = new class_243(target.method_53830(), target.method_53827(), target.method_53828());
      class_243 local8_5 = target.method_18798();
      class_243 local9_6 = new class_243(
         target.method_23317() - target.field_6014, target.method_23318() - target.field_6036, target.method_23321() - target.field_5969
      );
      class_243 local10_7 = local8_5.method_35590(local9_6, 0.5);
      class_243 local11_8 = target.method_5631(mc.field_1724.method_36454(), mc.field_1724.method_36455());
      float local12_9 = target.method_36455() * (float) (Math.PI / 180.0);
      double local13_10 = Math.sqrt(local11_8.field_1352 * local11_8.field_1352 + local11_8.field_1350 * local11_8.field_1350);
      double local15_11 = local10_7.method_1033();
      double local17_12 = 0.08;
      double local19_13 = 0.98;
      double local21_14 = (double)class_3532.method_15362(local12_9);
      double local23_15 = local21_14 * local21_14;
      if (local10_7.field_1351 < 0.0) {
         local10_7 = local10_7.method_1031(0.0, -local17_12 * (1.0 - local23_15 * 0.75), 0.0);
      }

      if ((double)local12_9 < 0.0 && local13_10 > 0.0) {
         double local25_0 = local15_11 * (double)(-class_3532.method_15374(local12_9)) * 0.05;
         local10_7 = local10_7.method_1031(-local11_8.field_1352 * local25_0 / local13_10, local25_0 * 3.5, -local11_8.field_1350 * local25_0 / local13_10);
      }

      if (local13_10 > 0.0) {
         local10_7 = local10_7.method_1031(
            (local11_8.field_1352 / local13_10 * local15_11 - local10_7.field_1352) * 0.12,
            0.0,
            (local11_8.field_1350 / local13_10 * local15_11 - local10_7.field_1350) * 0.12
         );
      }

      local10_7 = local10_7.method_18805(local19_13, 1.0, local19_13);
      class_243 local25_16 = new class_243(local10_7.field_1352, local3_2 + 0.15, local10_7.field_1350)
         .method_1021(class_3532.method_15350(local15_11, 0.0, 1.2));
      return local7_4.method_1019(local25_16);
   }

   @Generated
   private AuraUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
