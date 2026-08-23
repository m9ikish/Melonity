package ru.melonity.util.font;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public record DrawEntry(float atX, float atY, int color, Glyph toDraw) {
}
