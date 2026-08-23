package ru.melonity.util.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1041;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import org.joml.Matrix4f;
import org.joml.Vector2i;

@Environment(EnvType.CLIENT)
public final class ScaleUtils {
   private static final class_310 mc = class_310.method_1551();
   private static float currentScale = 1.0F;

   public static float getCurrentScale() {
      return currentScale;
   }

   public static void rescale(double factor) {
      currentScale = (float)factor;
      class_1041 local2_0 = mc.method_22683();
      rescale((double)local2_0.method_4489() / factor, (double)local2_0.method_4506() / factor);
   }

   public static void rescaleMC() {
      class_1041 local0_0 = mc.method_22683();
      currentScale = (float)local0_0.method_4495();
      rescale((double)local0_0.method_4489() / local0_0.method_4495(), (double)local0_0.method_4506() / local0_0.method_4495());
   }

   public static void rescale(double width, double height) {
      com.mojang.blaze3d.systems.RenderSystem.clear(256, true);
      Matrix4f local4_0 = new Matrix4f().setOrtho(0.0F, (float)width, (float)height, 0.0F, 1000.0F, 3000.0F);
      com.mojang.blaze3d.systems.RenderSystem.setProjectionMatrix(local4_0, com.mojang.blaze3d.systems.RenderSystem.getVertexSorting());
      class_4587 local5_1 = new class_4587();
      local5_1.method_46416(0.0F, 0.0F, -2000.0F);
      com.mojang.blaze3d.systems.RenderSystem.getModelViewStack().identity();
      com.mojang.blaze3d.systems.RenderSystem.getModelViewStack().mul(local5_1.method_23760().method_23761());
      com.mojang.blaze3d.systems.RenderSystem.applyModelViewMatrix();
   }

   public static Vector2i getMouse(int mouseX, int mouseY) {
      return getMouseDouble((double)mouseX, (double)mouseY);
   }

   public static Vector2i getMouseDouble(double mouseX, double mouseY) {
      double local4_0 = 2.0;
      double local6_1 = mc.method_22683().method_4495();
      double local8_2 = local6_1 / local4_0;
      return new Vector2i((int)(mouseX * local8_2), (int)(mouseY * local8_2));
   }

   public static void pushMatrix() {
      com.mojang.blaze3d.systems.RenderSystem.getModelViewStack().pushMatrix();
   }

   public static void popMatrix() {
      com.mojang.blaze3d.systems.RenderSystem.getModelViewStack().popMatrix();
      com.mojang.blaze3d.systems.RenderSystem.applyModelViewMatrix();
   }

   public static void scale(float x, float y, float z) {
      com.mojang.blaze3d.systems.RenderSystem.getModelViewStack().scale(x, y, z);
      com.mojang.blaze3d.systems.RenderSystem.applyModelViewMatrix();
   }

   public static void translate(float x, float y, float z) {
      com.mojang.blaze3d.systems.RenderSystem.getModelViewStack().translate(x, y, z);
      com.mojang.blaze3d.systems.RenderSystem.applyModelViewMatrix();
   }
}
