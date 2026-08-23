package ru.melonity.util.minecraft;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.glfw.GLFW;
import oshi.annotation.concurrent.ThreadSafe;

@Environment(EnvType.CLIENT)
@ThreadSafe
public final class KeyUtil {
   public static String getKeyName(int bind) {
      String local1_0 = getLogicalKeyName(bind);
      if (local1_0 != null) {
         return local1_0;
      } else {
         String local2_1 = KeyNameUtil.getKeyName(bind);
         if (!"?".equals(local2_1)) {
            return local2_1;
         } else {
            String local3_2 = GLFW.glfwGetKeyName(bind, 0);
            return local3_2 == null ? "?" : local3_2.toUpperCase();
         }
      }
   }

   private static String getLogicalKeyName(int keyCode) {
      return switch (keyCode) {
         case 39 -> "'";
         case 44 -> ",";
         case 45 -> "-";
         case 46 -> ".";
         case 47 -> "/";
         case 59 -> ";";
         case 61 -> "=";
         case 91 -> "[";
         case 92 -> "\\";
         case 93 -> "]";
         case 96 -> "`";
         default -> null;
      };
   }

   @Generated
   private KeyUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
