package ru.melonity.util.render.shader;

import java.nio.FloatBuffer;
import java.util.function.IntConsumer;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

@Environment(EnvType.CLIENT)
public class Uniform {
   private static final FloatBuffer BUFFER = MemoryUtil.memAllocFloat(16);
   private final String name;
   private int location;

   public IntConsumer programCompileCallback() {
      return this::programCompileCallback;
   }

   public void uploadMatrix4f(Matrix4f matrix) {
      matrix.get(BUFFER);
      GL33.glUniformMatrix4fv(this.location, false, BUFFER);
   }

   public void uploadFloat(float value) {
      GL33.glUniform1f(this.location, value);
   }

   public void uploadVec3(float x, float y, float z) {
      GL33.glUniform3f(this.location, x, y, z);
   }

   public void uploadInt(int value) {
      GL33.glUniform1i(this.location, value);
   }

   public void uploadVec4(float x, float y, float z, float w) {
      GL33.glUniform4f(this.location, x, y, z, w);
   }

   private void programCompileCallback(int program) {
      this.location = GL33.glGetUniformLocation(program, this.name);
   }

   @Generated
   public Uniform(String name) {
      this.name = name;
   }
}
