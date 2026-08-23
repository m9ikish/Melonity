package ru.melonity.ui.menu.clickui.view.feature;

import java.util.Arrays;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum MenuFeaturePrefix {
   NONE(""),
   Funtime("FT"),
   ReallyWorld("RW"),
   HolyWorld("HW"),
   SpookyTime("ST"),
   MineBlaze("MB");

   private final String name;

   private MenuFeaturePrefix(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public boolean isPresent() {
      return this != NONE;
   }

   public static String combineShortNames(MenuFeaturePrefix... prefixes) {
      if (prefixes != null && prefixes.length != 0) {
         StringBuilder result = new StringBuilder();

         for (MenuFeaturePrefix prefix : prefixes) {
            if (prefix != null && prefix.isPresent()) {
               if (!result.isEmpty()) {
                  result.append(" ");
               }

               result.append(prefix.getName());
            }
         }

         return result.toString();
      } else {
         return "";
      }
   }

   public static boolean hasAnyPresent(MenuFeaturePrefix... prefixes) {
      return prefixes == null ? false : Arrays.stream(prefixes).anyMatch(MenuFeaturePrefix::isPresent);
   }
}
