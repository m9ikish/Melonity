package ru.melonity.util.render.shader;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ShaderManager {
   private final Map<String, ShaderProgram> shaders = Maps.newLinkedHashMap();

   public void registerShader(String name, ShaderProgram program) {
      this.shaders.put(name, program);
   }

   public void reloadShader(String name, ShaderProgram program) {
      this.shaders.replace(name, program);
   }

   public Optional<ShaderProgram> getShader(String name) {
      return Optional.ofNullable(this.shaders.get(name));
   }
}
