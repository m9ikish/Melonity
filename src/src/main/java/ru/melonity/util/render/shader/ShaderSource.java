package ru.melonity.util.render.shader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.Generated;
import lombok.SneakyThrows;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.io.IOUtils;

@Environment(EnvType.CLIENT)
public interface ShaderSource {
   String vertex();

   String fragment();

   static ShaderSource namedFilePair(String path) {
      return new ShaderSource.FileShaderSource(Path.of(path + ".vsh"), Path.of(path + ".fsh"));
   }

   static ShaderSource namedInputStreamPair(String name) {
      return new ShaderSource.InputStreamShaderSource("/assets/minecraft/shaders/core/" + name + ".vsh", "/assets/minecraft/shaders/core/" + name + ".fsh");
   }

   @Environment(EnvType.CLIENT)
   public static class FileShaderSource implements ShaderSource {
      private final Path vertexFilePath;
      private final Path fragmentFilePath;

      @Override
      @SneakyThrows
      public String vertex() {
         return Files.readString(this.vertexFilePath);
      }

      @Override
      @SneakyThrows
      public String fragment() {
         return Files.readString(this.fragmentFilePath);
      }

      @Generated
      public FileShaderSource(Path vertexFilePath, Path fragmentFilePath) {
         this.vertexFilePath = vertexFilePath;
         this.fragmentFilePath = fragmentFilePath;
      }
   }

   @Environment(EnvType.CLIENT)
   public static class InputStreamShaderSource implements ShaderSource {
      private final String vertexPath;
      private final String fragmentPath;

      @Override
      @SneakyThrows
      public String vertex() {
         return IOUtils.toString(ShaderSource.class.getResourceAsStream(this.vertexPath), StandardCharsets.UTF_8);
      }

      @Override
      @SneakyThrows
      public String fragment() {
         return IOUtils.toString(ShaderSource.class.getResourceAsStream(this.fragmentPath), StandardCharsets.UTF_8);
      }

      @Generated
      public InputStreamShaderSource(String vertexPath, String fragmentPath) {
         this.vertexPath = vertexPath;
         this.fragmentPath = fragmentPath;
      }
   }
}
