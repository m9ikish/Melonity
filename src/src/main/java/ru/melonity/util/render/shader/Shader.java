package ru.melonity.util.render.shader;

import java.util.function.IntConsumer;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.GL33;

@Environment(EnvType.CLIENT)
public class Shader {
   private static final IntConsumer NON_ACTION_PROGRAM_COMPILE_CALLBACK = program -> {
   };
   private final ShaderSource source;
   private IntConsumer programCompileCallback = NON_ACTION_PROGRAM_COMPILE_CALLBACK;
   private Integer program;
   private int previousProgram;

   public void bind() {
      this.previousProgram = GL33.glGetInteger(35725);
      GL33.glUseProgram(this.program());
   }

   public void unbind() {
      GL33.glUseProgram(this.previousProgram);
   }

   public Uniform uniform(String name) {
      Uniform local2_0 = new Uniform(name);
      this.newProgramCompileCallback(local2_0.programCompileCallback());
      return local2_0;
   }

   public void reload() {
      GL33.glDeleteProgram(this.program);
      this.program = null;
   }

   private int program() {
      if (this.program != null) {
         return this.program;
      } else {
         int local1_0 = this.buildProgram();
         this.programCompileCallback.accept(local1_0);
         return this.program = local1_0;
      }
   }

   private int buildProgram() {
      int local1_0 = GL33.glCreateProgram();
      int local2_1 = this.buildShader(35633, this.source.vertex());
      int local3_2 = this.buildShader(35632, this.source.fragment());
      GL33.glAttachShader(local1_0, local2_1);
      GL33.glAttachShader(local1_0, local3_2);
      GL33.glLinkProgram(local1_0);
      if (GL33.glGetProgrami(local1_0, 35714) != 1) {
         throw new IllegalStateException("An error occurred during linking program" + GL33.glGetProgramInfoLog(local1_0));
      } else {
         GL33.glDeleteShader(local2_1);
         GL33.glDeleteShader(local3_2);
         return local1_0;
      }
   }

   private int buildShader(int type, String source) {
      int local3_0 = GL33.glCreateShader(type);
      GL33.glShaderSource(local3_0, source);
      GL33.glCompileShader(local3_0);
      if (GL33.glGetShaderi(local3_0, 35713) != 1) {
         throw new IllegalStateException("An error occurred during compiling shader " + GL33.glGetShaderInfoLog(local3_0));
      } else {
         return local3_0;
      }
   }

   private void newProgramCompileCallback(IntConsumer callback) {
      IntConsumer local2_0 = this.programCompileCallback;
      this.programCompileCallback = program -> {
         local2_0.accept(program);
         callback.accept(program);
      };
   }

   @Generated
   public Shader(ShaderSource source) {
      this.source = source;
   }
}
