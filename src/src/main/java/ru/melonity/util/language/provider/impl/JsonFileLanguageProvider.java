package ru.melonity.util.language.provider.impl;

import com.google.common.collect.Maps;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import ru.melonity.util.language.Language;
import ru.melonity.util.language.impl.LangData;
import ru.melonity.util.language.provider.LanguageProvider;

@Environment(EnvType.CLIENT)
public class JsonFileLanguageProvider implements LanguageProvider {
   @Override
   public LangData parse(Language language) {
      Map<String, String> local2_6 = Maps.newLinkedHashMap();

      try {
         InputStream local3_2 = this.getClass().getResourceAsStream("/assets/minecraft/melonity/languages/".concat(language.getFileName()));
         JSONObject local4_3 = new JSONObject(IOUtils.toString(local3_2, StandardCharsets.UTF_8));
         JSONObject local5_4 = local4_3.getJSONObject("keys");

         for (String local7_1 : local5_4.keySet()) {
            String local8_0 = local5_4.getString(local7_1);
            local2_6.put(local7_1, local8_0);
         }

         local3_2.close();
      } catch (Exception var9) {
         var9.printStackTrace();
         System.exit(0);
      }

      return new LangData(language, local2_6);
   }
}
