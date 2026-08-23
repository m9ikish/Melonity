package ru.melonity.util.minecraft;

import java.util.Optional;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3965;
import net.minecraft.class_3966;

@Environment(EnvType.CLIENT)
public class EntityRaycastUtil {
   private static final class_310 mc = class_310.method_1551();

   public static class_1297 getMouseOver(class_1297 target, float yaw, float pitch, double distance) {
      class_1297 local6_8 = mc.method_1560();
      if (local6_8 != null && mc.field_1687 != null) {
         class_239 local5_7 = null;
         boolean local7_1 = distance > 3.0;
         class_243 local8_2 = local6_8.method_5836(1.0F);
         class_243 local9_3 = getVectorForRotation(pitch, yaw);
         class_243 local10_4 = local8_2.method_1031(local9_3.field_1352 * distance, local9_3.field_1351 * distance, local9_3.field_1350 * distance);
         class_238 local11_5 = target.method_5829().method_1014((double)target.method_5871());
         class_3966 local12_6 = rayTraceEntities(
            local6_8, local8_2, local10_4, local11_5, p_lambda$getMouseOver$0_0_ -> !p_lambda$getMouseOver$0_0_.method_7325(), distance
         );
         if (local12_6 != null) {
            if (local7_1 && local8_2.method_1022(local8_2) > distance) {
               local5_7 = class_3965.method_17778(
                  local8_2, null, new class_2338(new class_2382((int)local8_2.field_1352, (int)local8_2.field_1351, (int)local8_2.field_1350))
               );
            }

            if (distance < distance || local5_7 == null) {
               local5_7 = local12_6;
            }
         }

         if (local5_7 == null) {
            return null;
         }

         if (local5_7 instanceof class_3966 local13_0) {
            return local13_0.method_17782();
         }
      }

      return null;
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
               local11_9 = startVec;
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

   public static class_243 getVectorForRotation(float pitch, float yaw) {
      float local2_0 = -yaw * (float) (Math.PI / 180.0) - (float) Math.PI;
      float local3_1 = -pitch * (float) (Math.PI / 180.0);
      float local4_2 = class_3532.method_15362(local2_0);
      float local5_3 = class_3532.method_15374(local2_0);
      float local6_4 = -class_3532.method_15362(local3_1);
      float local7_5 = class_3532.method_15374(local3_1);
      return new class_243((double)(local5_3 * local6_4), (double)local7_5, (double)(local4_2 * local6_4));
   }
}
