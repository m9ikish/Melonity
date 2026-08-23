package ru.melonity.util.render;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1041;
import net.minecraft.class_310;
import net.minecraft.class_4184;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

@Environment(EnvType.CLIENT)
public final class ProjectUtil {
   public static final Matrix4f lastProjMat = new Matrix4f();
   public static final Matrix4f lastModMat = new Matrix4f();
   public static final Matrix4f lastWorldSpaceMatrix = new Matrix4f();
   private static final int[] VIEWPORT = new int[4];
   private static final Vector4f SCRATCH_VEC4 = new Vector4f();
   private static final Vector3f SCRATCH_TARGET = new Vector3f();
   private static volatile boolean viewportValid = false;

   public static void updateMatrices(Matrix4f projection, Matrix4f model, Matrix4f world, class_1041 window) {
      lastProjMat.set(projection);
      lastModMat.set(model);
      lastWorldSpaceMatrix.set(world);
      if (window != null) {
         VIEWPORT[0] = 0;
         VIEWPORT[1] = 0;
         VIEWPORT[2] = window.method_4489();
         VIEWPORT[3] = window.method_4506();
         viewportValid = VIEWPORT[2] > 0 && VIEWPORT[3] > 0;
      } else {
         viewportValid = false;
      }
   }

   public static double[] project(double x, double y, double z) {
      class_310 local6_0 = class_310.method_1551();
      class_4184 local7_1 = local6_0.method_1561().field_4686;
      int local8_2 = local6_0.method_22683().method_4507();
      if (!viewportValid) {
         return null;
      } else {
         double local9_3 = x - local7_1.method_19326().field_1352;
         double local11_4 = y - local7_1.method_19326().field_1351;
         double local13_5 = z - local7_1.method_19326().field_1350;
         SCRATCH_VEC4.set((float)local9_3, (float)local11_4, (float)local13_5, 1.0F).mul(lastWorldSpaceMatrix);
         Matrix4f local15_6 = new Matrix4f(lastProjMat);
         Matrix4f local16_7 = new Matrix4f(lastModMat);
         Vector3f local17_8 = local15_6.mul(local16_7).project(SCRATCH_VEC4.x(), SCRATCH_VEC4.y(), SCRATCH_VEC4.z(), VIEWPORT, SCRATCH_TARGET);
         return new double[]{
            (double)local17_8.x / local6_0.method_22683().method_4495(),
            (double)((float)local8_2 - local17_8.y) / local6_0.method_22683().method_4495(),
            (double)local17_8.z
         };
      }
   }

   @Generated
   private ProjectUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
