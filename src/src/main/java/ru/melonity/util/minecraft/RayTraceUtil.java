package ru.melonity.util.minecraft;

import java.util.Optional;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1675;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.joml.Vector2f;

@Environment(EnvType.CLIENT)
public class RayTraceUtil {
   private static final class_310 mc = class_310.method_1551();

   public static class_239 calculateRayTrace(double distance, float yaw, float pitch, class_1297 entity, boolean ignoreBlocks) {
      class_243 local6_0 = mc.field_1724.method_33571();
      class_243 local7_1 = getVectorForRotation(pitch, yaw);
      class_243 local8_2 = local6_0.method_1019(local7_1.method_1021(distance));
      class_3965 local9_3 = mc.field_1687.method_17742(new class_3959(local6_0, local8_2, class_3960.field_17558, class_242.field_1348, mc.field_1724));
      double local10_4 = local9_3.method_17784().method_1025(local6_0);
      class_238 local12_5 = entity.method_5829()
         .method_1009(
            local7_1.method_1021(distance).method_10216(), local7_1.method_1021(distance).method_10214(), local7_1.method_1021(distance).method_10215()
         )
         .method_1014(1.0);
      class_3966 local13_6 = rayTraceEntities(entity, local6_0, local8_2, local12_5, e -> !e.method_7325() && e.method_5805() && e.method_5863(), distance);
      return (class_239)(local13_6 == null || !ignoreBlocks && !(local13_6.method_17784().method_1025(local6_0) < local10_4) ? local9_3 : local13_6);
   }

   public static boolean findCrosshairTarget(class_1297 camera, class_1297 target, double blockInteractionRange, double entityInteractionRange, float tickDelta) {
      double local7_0 = Math.max(blockInteractionRange, entityInteractionRange);
      double local9_1 = class_3532.method_33723(local7_0);
      class_243 local11_2 = camera.method_33571();
      class_239 local12_3 = camera.method_5745(local7_0, tickDelta, false);
      double local13_4 = local12_3.method_17784().method_1025(local11_2);
      if (local12_3.method_17783() != class_240.field_1333) {
         local9_1 = local13_4;
         local7_0 = Math.sqrt(local13_4);
      }

      class_243 local15_5 = camera.method_5828(tickDelta);
      class_243 local16_6 = local11_2.method_1031(local15_5.field_1352 * local7_0, local15_5.field_1351 * local7_0, local15_5.field_1350 * local7_0);
      class_238 local18_7 = camera.method_5829().method_18804(local15_5.method_1021(local7_0)).method_1009(1.0, 1.0, 1.0);
      class_3966 local19_8 = class_1675.method_18075(camera, local11_2, local16_6, local18_7, entity -> entity.method_5863() && entity == target, local9_1);
      if (local19_8 != null && local19_8.method_17783() == class_240.field_1332) {
         return true;
      } else {
         return local19_8 != null && local19_8.method_17784().method_1025(local11_2) < local13_4
            ? ensureTargetInRange(local19_8, local11_2, entityInteractionRange).method_17783() == class_240.field_1331
            : ensureTargetInRange(local12_3, local11_2, blockInteractionRange).method_17783() == class_240.field_1331;
      }
   }

   private static class_239 ensureTargetInRange(class_239 hitResult, class_243 cameraPos, double interactionRange) {
      class_243 local4_2 = hitResult.method_17784();
      if (!local4_2.method_24802(cameraPos, interactionRange)) {
         class_243 local5_0 = hitResult.method_17784();
         class_2350 local6_1 = class_2350.method_10142(
            local5_0.field_1352 - cameraPos.field_1352, local5_0.field_1351 - cameraPos.field_1351, local5_0.field_1350 - cameraPos.field_1350
         );
         return class_3965.method_17778(local5_0, local6_1, class_2338.method_49638(local5_0));
      } else {
         return hitResult;
      }
   }

   public static class_243 getRotationVector2(float yaw, float pitch) {
      float local2_0 = pitch * (float) (Math.PI / 180.0);
      float local3_1 = -yaw * (float) (Math.PI / 180.0);
      float local4_2 = class_3532.method_15362(local3_1);
      float local5_3 = class_3532.method_15374(local3_1);
      float local6_4 = class_3532.method_15362(local2_0);
      float local7_5 = class_3532.method_15374(local2_0);
      return new class_243((double)(local5_3 * local6_4), (double)(-local7_5), (double)(local4_2 * local6_4));
   }

   public static boolean isViewEntity(class_1297 target, Vector2f rotation, double distance) {
      class_1297 local4_0 = mc.method_1560();
      if (local4_0 != null && mc.field_1687 != null) {
         double local5_1 = distance * distance;
         class_243 local7_2 = local4_0.method_33571();
         class_243 local8_3 = getRotationVector2(rotation.x, rotation.y);
         class_243 local9_4 = local7_2.method_1031(local8_3.field_1352 * distance, local8_3.field_1351 * distance, local8_3.field_1350 * distance);
         double local12_6 = class_3532.method_33723(distance);
         class_238 local14_7 = target.method_5829().method_1014(distance);
         float local15_8 = rotation.y * (float) (Math.PI / 180.0);
         float local16_9 = -rotation.x * (float) (Math.PI / 180.0);
         float local17_10 = class_3532.method_15362(local16_9);
         float local18_11 = class_3532.method_15374(local16_9);
         float local19_12 = class_3532.method_15362(local15_8);
         float local20_13 = class_3532.method_15374(local15_8);
         class_243 local21_14 = new class_243((double)(local18_11 * local19_12), (double)(-local20_13), (double)(local17_10 * local19_12));
         class_238 local22_15 = target.method_5829().method_18804(local21_14.method_1021(distance)).method_1009(1.0, 1.0, 1.0);
         class_3966 local23_16 = class_1675.method_18075(
            local4_0, local7_2, local9_4, local22_15, entityIn -> !entityIn.method_7325() && entityIn.method_5863() && entityIn == target, local5_1
         );
         return local23_16 != null;
      } else {
         return false;
      }
   }

   public static class_239 point(class_243 start, class_243 end) {
      return mc.field_1687.method_17742(new class_3959(start, end, class_3960.field_17559, class_242.field_1348, mc.field_1724));
   }

   public static class_243 getVectorForRotation(float pitch, float yaw) {
      float local2_0 = -yaw * (float) (Math.PI / 180.0) - (float) Math.PI;
      float local3_1 = -pitch * (float) (Math.PI / 180.0);
      float local4_2 = class_3532.method_15362(local2_0);
      float local5_3 = class_3532.method_15374(local2_0);
      float local6_4 = -class_3532.method_15362(local3_1);
      float local7_5 = class_3532.method_15374(local3_1);
      return new class_243((double)(local5_3 * local6_4), (double)local7_5, (double)(local4_2 * local6_4));
   }

   public static class_239 rayTrace(double dst, float yaw, float pitch) {
      class_243 local4_0 = mc.field_1724.method_5836(1.0F);
      class_243 local5_1 = getVectorForRotation(pitch, yaw);
      class_243 local6_2 = local4_0.method_1031(local5_1.field_1352 * dst, local5_1.field_1351 * dst, local5_1.field_1350 * dst);
      return mc.field_1687.method_17742(new class_3959(local4_0, local6_2, class_3960.field_17559, class_242.field_1348, mc.field_1724));
   }

   public static class_1297 getTargetedEntity(class_1297 target, float targetYaw, float targetPitch, double distance) {
      class_1297 local5_0 = mc.method_1560();
      if (local5_0 != null && mc.field_1687 != null) {
         class_243 local6_1 = local5_0.method_5836(mc.method_60646().method_60637(true));
         class_243 local7_2 = getVectorForRotation(targetPitch, targetYaw);
         class_243 local8_3 = local6_1.method_1019(local7_2.method_1021(distance));
         class_238 local9_4 = target.method_5829().method_1014((double)target.method_5871());
         class_3966 local10_5 = rayTraceEntities(local5_0, local6_1, local8_3, local9_4, entity -> !entity.method_7325(), distance);
         return local10_5 != null ? local10_5.method_17782() : null;
      } else {
         return null;
      }
   }

   public static boolean rayTraceEntity(float yaw, float pitch, double distance, class_1297 entity) {
      class_243 local5_0 = mc.field_1724.method_5836(mc.method_60646().method_60637(true));
      class_243 local6_1 = getVectorForRotation(pitch, yaw);
      class_243 local7_2 = local5_0.method_1019(local6_1.method_1021(distance));
      class_238 local8_3 = entity.method_5829();
      return local8_3.method_1006(local5_0) || local8_3.method_992(local5_0, local7_2).isPresent();
   }

   public static class_1297 rayTrace(class_1297 target, float yaw, float pitch, double distance, boolean ignoreWalls) {
      class_1297 local7_8 = class_310.method_1551().method_1560();
      if (local7_8 != null && class_310.method_1551().field_1687 != null) {
         class_239 local6_7 = null;
         class_243 local8_1 = local7_8.method_5836(1.0F);
         boolean local9_2 = distance > 3.0;
         class_243 local10_3 = getVectorForRotation(pitch, yaw);
         class_243 local11_4 = local8_1.method_1031(local10_3.field_1352 * distance, local10_3.field_1351 * distance, local10_3.field_1350 * distance);
         class_238 local12_5 = target.method_5829().method_1014((double)target.method_5871());
         class_3966 local13_6 = rayTraceEntities(
            local7_8, local8_1, local11_4, local12_5, p_lambda$getMouseOver$0_0_ -> !p_lambda$getMouseOver$0_0_.method_7325(), distance
         );
         if (local13_6 != null) {
            if (local9_2 && local8_1.method_1022(local8_1) > distance) {
               local6_7 = class_3965.method_17778(
                  local8_1, null, new class_2338(new class_2382((int)local8_1.field_1352, (int)local8_1.field_1351, (int)local8_1.field_1350))
               );
            }

            if (distance < distance || local6_7 == null) {
               local6_7 = local13_6;
            }
         }

         if (local6_7 == null) {
            return null;
         } else {
            try {
               return ((class_3966)local6_7).method_17782();
            } catch (ClassCastException var15) {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   public static class_3966 rayTraceEntities(
      class_1297 shooter, class_243 startVec, class_243 endVec, class_238 boundingBox, Predicate<class_1297> filter, double distance
   ) {
      class_1937 local7_6 = shooter.method_37908();
      double local8_7 = distance;
      class_1297 local10_8 = null;
      class_243 local11_9 = null;

      for (class_1297 local13_5 : local7_6.method_8333(shooter, boundingBox, filter)) {
         class_238 local14_3 = local13_5.method_5829().method_1014((double)local13_5.method_5871());
         Optional<class_243> local15_4 = local14_3.method_992(startVec, endVec);
         if (local14_3.method_1006(startVec)) {
            if (local8_7 >= 0.0) {
               local10_8 = local13_5;
               local11_9 = local15_4.orElse(startVec);
               local8_7 = 0.0;
            }
         } else if (local15_4.isPresent()) {
            class_243 local16_1 = local15_4.get();
            double local17_2 = startVec.method_1022(local15_4.get());
            if (local17_2 < local8_7 || local8_7 == 0.0) {
               boolean local19_0 = false;
               if (local19_0 || local13_5.method_5668() != shooter.method_5668()) {
                  local10_8 = local13_5;
                  local11_9 = local16_1;
                  local8_7 = local17_2;
               } else if (local8_7 == 0.0) {
                  local10_8 = local13_5;
                  local11_9 = local16_1;
               }
            }
         }
      }

      return local10_8 == null ? null : new class_3966(local10_8, local11_9);
   }
}
