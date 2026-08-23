package ru.melonity.util.language.impl;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.language.Language;
import ru.melonity.util.language.LanguageService;
import ru.melonity.util.language.provider.LanguageProvider;

@Environment(EnvType.CLIENT)
public class LanguageManager implements LanguageService {
   private final List<LangData> datas = Lists.newLinkedList();
   private final LanguageProvider provider;
   private Language language = Language.RU;

   public LanguageManager(LanguageProvider provider) {
      this.provider = provider;
   }

   @Override
   public void parseLanguage(Language language) {
      this.datas.add(this.provider.parse(language));
   }

   @Override
   public void put(Language language, String key, String message) {
      for (LangData local5_0 : this.datas) {
         if (local5_0.language().equals(language)) {
            local5_0.messages().put(key, message);
         }
      }
   }

   @Override
   public String get(String key, Object... args) {
      for (LangData local4_0 : this.datas) {
         if (local4_0.language().equals(this.language)) {
            if (!local4_0.messages().containsKey(key)) {
               return key;
            }

            return String.format(local4_0.messages().get(key), args);
         }
      }

      return key;
   }

   @Generated
   @Override
   public Language getLanguage() {
      return this.language;
   }

   @Generated
   @Override
   public void setLanguage(Language language) {
      this.language = language;
   }
}
