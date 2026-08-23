package ru.melonity.util.render.shader;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.io.IOException;
import java.nio.FloatBuffer;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_293;
import net.minecraft.class_5912;
import net.minecraft.class_5944;

@Environment(EnvType.CLIENT)
public class ShaderProgram {
   private final class_5944 shaderInstance;

   // The obfuscator wrapped the body in catch(Throwable){ throw; }, which is just a propagate.
   public ShaderProgram(class_5912 factory, String name, class_293 format) throws IOException {
      this.shaderInstance = new class_5944(factory, name, format);
   }

   public void use() {
      RenderSystem.setShader(() -> this.shaderInstance);
   }

   public void deuse() {
      this.shaderInstance.method_34585();
   }

   public void setSampler(String name, int textureId) {
      this.shaderInstance.method_34583(name, textureId);
   }

   public void setUniform1f(String name, float f1) {
      this.shaderInstance.method_35785(name).method_1251(f1);
   }

   public void setUniform2f(String name, float f1, float f2) {
      this.shaderInstance.method_35785(name).method_1255(f1, f2);
   }

   public void setUniform3f(String name, float f1, float f2, float f3) {
      this.shaderInstance.method_35785(name).method_1249(f1, f2, f3);
   }

   public void setUniform4f(String name, float f1, float f2, float f3, float f4) {
      this.shaderInstance.method_35785(name).method_35657(f1, f2, f3, f4);
   }

   public void setUniform4fv(String name, Color color) {
      this.setUniform4f(
         name, (float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F
      );
   }

   public void setUniform1fv(String name, FloatBuffer buffer) {
      float[] local3_1 = new float[256];
      if (buffer != null) {
         for (int local4_0 = 0; local4_0 < 256; local4_0++) {
            local3_1[local4_0] = buffer.get(local4_0);
         }
      }

      this.shaderInstance.method_35785(name).method_1253(local3_1);
   }

   public void setUniform1i(String name, int f1) {
      this.shaderInstance.method_35785(name).method_35649(f1);
   }

   public void setUniform2i(String name, int f1, int f2) {
      this.shaderInstance.method_35785(name).method_35650(f1, f2);
   }

   public void setUniform3i(String name, int f1, int f2, int f3) {
      this.shaderInstance.method_35785(name).method_35651(f1, f2, f3);
   }

   public void setUniform4i(String name, int f1, int f2, int f3, int f4) {
      this.shaderInstance.method_35785(name).method_35656(f1, f2, f3, f4);
   }

   public void setUniformMatrix4fv(String name, FloatBuffer matrix) {
      this.shaderInstance.method_35785(name).method_1253(matrix.array());
   }

   public void setUniformMatrix4fv(String name, float[] matrix) {
      this.shaderInstance.method_35785(name).method_1253(matrix);
   }

   @Generated
   public class_5944 getShaderInstance() {
      return this.shaderInstance;
   }
}
