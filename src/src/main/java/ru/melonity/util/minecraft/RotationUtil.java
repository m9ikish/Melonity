package ru.melonity.util.minecraft;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_2350;
import net.minecraft.class_241;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class RotationUtil {
   private static final class_310 mc = class_310.method_1551();

   public static class_241 calculate(class_243 fromVec, class_243 toVec) {
      class_243 local4_0 = toVec.method_1020(fromVec);
      double local5_1 = Math.hypot(local4_0.field_1352, local4_0.field_1350);
      float local7_2 = (float)(class_3532.method_15349(local4_0.field_1350, local4_0.field_1352) * (180.0 / Math.PI)) - 90.0F;
      float local8_3 = (float)(-(class_3532.method_15349(local4_0.field_1351, local5_1) * (180.0 / Math.PI)));
      return new class_241(local7_2, local8_3);
   }

   public static class_241 calculate(class_1297 entity) {
      return calculate(entity.method_19538().method_1031(0.0, (double)entity.method_18381(entity.method_18376()), 0.0));
   }

   public static class_241 calculate(class_243 toVec) {
      return calculate(mc.field_1724.method_19538().method_1031(0.0, (double)mc.field_1724.method_18381(mc.field_1724.method_18376()), 0.0), toVec);
   }

   public static class_241 calculate(class_243 toVec, class_2350 dir) {
      double local2_0 = toVec.field_1352 + 0.5;
      double local4_1 = toVec.field_1351 + 0.5;
      double local6_2 = toVec.field_1350 + 0.5;
      local2_0 += (double)dir.method_10163().method_10263() * 0.5;
      local4_1 += (double)dir.method_10163().method_10264() * 0.5;
      local6_2 += (double)dir.method_10163().method_10260() * 0.5;
      return calculate(new class_243(local2_0, local4_1, local6_2));
   }

   public static float getAngleDifference(float dir, float yaw) {
      float local2_0 = Math.abs(yaw - dir) % 360.0F;
      return local2_0 > 180.0F ? 360.0F - local2_0 : local2_0;
   }

   public static class_243 getEyesPos(class_1297 entity) {
      return entity.method_19538().method_1031(0.0, (double)entity.method_18381(entity.method_18376()), 0.0);
   }

   public static float[] calculateAngle(class_243 to) {
      return calculateAngle(getEyesPos(mc.field_1724), to);
   }

   public static float[] calculateAngle(class_243 from, class_243 to) {
      double local2_0 = to.field_1352 - from.field_1352;
      double local4_1 = (to.field_1351 - from.field_1351) * -1.0;
      double local6_2 = to.field_1350 - from.field_1350;
      double local8_3 = (double)class_3532.method_15355((float)(local2_0 * local2_0 + local6_2 * local6_2));
      float local10_4 = (float)class_3532.method_15338(Math.toDegrees(Math.atan2(local6_2, local2_0)) - 90.0);
      float local11_5 = (float)class_3532.method_15350(class_3532.method_15338(Math.toDegrees(Math.atan2(local4_1, local8_3))), -90.0, 90.0);
      return new float[]{local10_4, local11_5};
   }

   public static Vector3f getDirectionVector(float yaw, float pitch) {
      float local2_0 = (float)Math.toRadians((double)yaw);
      float local3_1 = (float)Math.toRadians((double)pitch);
      float local4_2 = -class_3532.method_15362(local3_1) * class_3532.method_15374(local2_0);
      float local5_3 = -class_3532.method_15374(local3_1);
      float local6_4 = class_3532.method_15362(local3_1) * class_3532.method_15362(local2_0);
      return new Vector3f(local4_2, local5_3, local6_4);
   }

   public static float calculateFov(float cameraYaw, float cameraPitch, float targetYaw, float targetPitch) {
      Vector3f local4_0 = getDirectionVector(cameraYaw, cameraPitch);
      Vector3f local5_1 = getDirectionVector(targetYaw, targetPitch);
      float local6_2 = local4_0.dot(local5_1);
      local6_2 = class_3532.method_15363(local6_2, -1.0F, 1.0F);
      float local7_3 = (float)Math.acos((double)local6_2);
      return (float)Math.toDegrees((double)local7_3);
   }
}
