package ru.melonity.util.altmanager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum ProfileType {
   MOJANG,
   MICROSOFT,
   CRACK;

   public static ProfileType fromName(String name) {
      String var1 = name.toLowerCase();

      return switch (var1) {
         case "mojang" -> MOJANG;
         case "microsoft" -> MICROSOFT;
         case "crack" -> CRACK;
         default -> null;
      };
   }
}
