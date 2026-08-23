package ru.melonity.util.language.provider;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.language.Language;
import ru.melonity.util.language.impl.LangData;

@Environment(EnvType.CLIENT)
public interface LanguageProvider {
   LangData parse(Language var1);
}
