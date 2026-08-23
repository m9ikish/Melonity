package ru.melonity.util.minecraft;

import java.util.Locale;
import java.util.Map;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_3675.class_306;
import net.minecraft.class_3675.class_307;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class KeyNameUtil {
   private static final Locale DISPLAY_LOCALE = Locale.ROOT;
   private static final String KEYBOARD_PREFIX = "key.keyboard.";
   private static final String MOUSE_PREFIX = "key.mouse.";
   private static final String GENERIC_PREFIX = "key.";
   private static final Map<String, String> KEYBOARD_OVERRIDES = Map.ofEntries(
      Map.entry("left.shift", "SHIFT"),
      Map.entry("right.shift", "SHIFT"),
      Map.entry("left.control", "CTRL"),
      Map.entry("right.control", "CTRL"),
      Map.entry("left.alt", "ALT"),
      Map.entry("right.alt", "ALT"),
      Map.entry("unknown", "NONE"),
      Map.entry("insert", "INS"),
      Map.entry("delete", "DEL"),
      Map.entry("page.up", "PAGE_UP"),
      Map.entry("page.down", "PAGE_DOWN")
   );

   @NotNull
   public static String getKeyName(class_306 key) {
      return key == null ? "?" : formatTranslationKey(key.method_1441());
   }

   @NotNull
   public static String getKeyName(int keyCode) {
      if (keyCode == -1) {
         return "NONE";
      } else if (isMouseButton(keyCode)) {
         return mapMouseButton(keyCode);
      } else {
         try {
            class_306 local1_0 = class_307.field_1668.method_1447(keyCode);
            return getKeyName(local1_0);
         } catch (RuntimeException var2) {
            return "?";
         }
      }
   }

   private static boolean isMouseButton(int keyCode) {
      return keyCode >= 0 && keyCode <= 7;
   }

   private static String mapMouseButton(int button) {
      return switch (button) {
         case 2 -> "MB3";
         case 3 -> "MB4";
         case 4 -> "MB5";
         default -> "?";
      };
   }

   private static String formatTranslationKey(String translationKey) {
      if (translationKey != null && !translationKey.isEmpty()) {
         String local1_0 = stripPrefix(translationKey);
         String local2_1 = KEYBOARD_OVERRIDES.get(local1_0);
         if (local2_1 != null) {
            return local2_1;
         } else if (translationKey.startsWith("key.mouse.")) {
            return formatMouseName(local1_0);
         } else {
            return local1_0.startsWith("keypad.")
               ? ("NUM " + local1_0.substring("keypad.".length()).replace('.', ' ')).toUpperCase(DISPLAY_LOCALE)
               : local1_0.replace('.', ' ').replace('_', ' ').toUpperCase(DISPLAY_LOCALE);
         }
      } else {
         return "?";
      }
   }

   private static String formatMouseName(String translationKey) {
      return switch (translationKey) {
         case "left" -> "LMB";
         case "right" -> "RMB";
         case "middle" -> "MB3";
         case "button4" -> "MB4";
         case "button5" -> "MB5";
         default -> translationKey.replace('.', ' ').replace('_', ' ').toUpperCase(DISPLAY_LOCALE);
      };
   }

   private static String stripPrefix(String translationKey) {
      if (translationKey.startsWith("key.keyboard.")) {
         return translationKey.substring("key.keyboard.".length());
      } else if (translationKey.startsWith("key.mouse.")) {
         return translationKey.substring("key.mouse.".length());
      } else {
         return translationKey.startsWith("key.") ? translationKey.substring("key.".length()) : translationKey;
      }
   }

   @Generated
   private KeyNameUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
