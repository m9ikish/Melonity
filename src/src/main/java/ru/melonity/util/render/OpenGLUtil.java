package ru.melonity.util.render;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class OpenGLUtil {
   public static void blend(Runnable runnable) {
      com.mojang.blaze3d.systems.RenderSystem.enableBlend();
      com.mojang.blaze3d.systems.RenderSystem.defaultBlendFunc();
      runnable.run();
      com.mojang.blaze3d.systems.RenderSystem.disableBlend();
   }

   @Generated
   private OpenGLUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
