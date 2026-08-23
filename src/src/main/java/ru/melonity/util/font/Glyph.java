package ru.melonity.util.font;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public record Glyph(int u, int v, int width, int height, char value, GlyphMap owner) {
}
