package ru.melonity.util.language;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum Language {
   RU("russian.png", "ru_RU", "russian.json"),
   EN("english.png", "en_US", "english.json");

   private final String icon;
   private final String locale;
   private final String fileName;

   private Language(String icon, String locale, String fileName) {
      this.icon = icon;
      this.locale = locale;
      this.fileName = fileName;
   }

   @Generated
   public String getIcon() {
      return this.icon;
   }

   @Generated
   public String getLocale() {
      return this.locale;
   }

   @Generated
   public String getFileName() {
      return this.fileName;
   }
}
