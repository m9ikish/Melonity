package ru.melonity.util.math;

import java.lang.management.ManagementFactory;
import java.security.SecureRandom;
import java.util.UUID;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_241;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import ru.melonity.Melonity;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public final class MathUtil {
   public static float deg2rad = (float) (Math.PI / 180.0);
   static SecureRandom secureRandom = new SecureRandom();

   public static boolean range(double num, double min, double max) {
      return num >= min && num <= max;
   }

   public static double round(double value, int increment) {
      double local3_0 = Math.pow(10.0, (double)increment);
      return (double)Math.round(value * local3_0) / local3_0;
   }

   public static double logicalRandom(double first, double second) {
      return new SecureRandom().nextBoolean() ? first : second;
   }

   public static float logicalRandom(float first, float second) {
      return new SecureRandom().nextBoolean() ? first : second;
   }

   public static float logicalRandom(float first, float second, float three) {
      return new SecureRandom().nextBoolean() ? (new SecureRandom().nextBoolean() ? first : second) : three;
   }

   public static int logicalRandom(int first, int second) {
      return new SecureRandom().nextBoolean() ? first : second;
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
      return (float)local0_0.nextGaussian();
   }

   public static float random(float min, float max) {
      SecureRandom local2_0 = new SecureRandom();
      local2_0.setSeed(generateUniqueSeed());
      return (float)(local2_0.nextGaussian() * (double)(max - min) + (double)min);
   }

   public static float clamp(float value, float number) {
      return Math.min(Math.max(value, -number), number);
   }

   public static double interpolate(double current, double old, double scale) {
      return old + (current - old) * scale;
   }

   public static double deltaTime() {
      return class_310.method_1551().method_47599() > 0 ? 1.0 / (double)class_310.method_1551().method_47599() : 1.0;
   }

   public static float fast(float end, float start, float multiple) {
      return (1.0F - class_3532.method_15363((float)(deltaTime() * (double)multiple), 0.0F, 1.0F)) * end
         + class_3532.method_15363((float)(deltaTime() * (double)multiple), 0.0F, 1.0F) * start;
   }

   public static float random(double min, double max) {
      return (float)(Math.random() * (max - min) + min);
   }

   public static float interpolate(float current, float old, float scale) {
      return old + (current - old) * scale;
   }

   public static class_243 interpolate(class_243 end, class_243 start, float multiple) {
      return new class_243(
         interpolate(end.field_1352, start.field_1352, (double)multiple),
         interpolate(end.field_1351, start.field_1351, (double)multiple),
         interpolate(end.field_1350, start.field_1350, (double)multiple)
      );
   }

   public static class_241 rotationToVec(class_243 target) {
      class_243 local1_0 = target.method_1020(class_310.method_1551().field_1724.method_19538());
      double local2_1 = Math.hypot(local1_0.field_1352, local1_0.field_1350);
      return new class_241(
         (float)Math.toDegrees(Math.atan2(local1_0.field_1350, local1_0.field_1352)) - 90.0F,
         (float)(-Math.toDegrees(Math.atan2(local1_0.field_1351, local2_1)))
      );
   }

   public static class_241 rotationToEntity(class_1297 target) {
      class_243 local1_0 = target.method_19538().method_1020(class_310.method_1551().field_1724.method_19538());
      double local2_1 = Math.hypot(local1_0.field_1352, local1_0.field_1350);
      return new class_241(
         (float)Math.toDegrees(Math.atan2(local1_0.field_1350, local1_0.field_1352)) - 90.0F,
         (float)(-Math.toDegrees(Math.atan2(local1_0.field_1351, local2_1)))
      );
   }

   public static boolean isMouseHoveringRect(double x, double y, double width, double height, double mouseX, double mouseY) {
      RenderSystem local12_0 = Melonity.INSTANCE.getRenderSystem();
      float local13_1 = local12_0.getFakeScaling();
      x *= (double)local13_1;
      y *= (double)local13_1;
      width *= (double)local13_1;
      height *= (double)local13_1;
      return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height;
   }

   public static double sigmoid(double x) {
      return 1.0 / (1.0 + Math.exp(-x));
   }

   public static double parametricSigmoid(double x, double mean, double std) {
      double local6_0 = 1.0 / std;
      return 1.0 / (1.0 + Math.exp(-local6_0 * (x - mean)));
   }

   @Generated
   private MathUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
