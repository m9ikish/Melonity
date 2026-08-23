package ru.melonity.util.language.impl;

import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.language.Language;

@Environment(EnvType.CLIENT)
public record LangData(Language language, Map<String, String> messages) {
}
