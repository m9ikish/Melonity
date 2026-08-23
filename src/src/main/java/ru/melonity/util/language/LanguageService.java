package ru.melonity.util.language;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface LanguageService {
   void parseLanguage(Language var1);

   void put(Language var1, String var2, String var3);

   String get(String var1, Object... var2);

   void setLanguage(Language var1);

   Language getLanguage();
}
