package ru.melonity.ui.menu.clickui.style;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import ru.melonity.ui.menu.clickui.style.elements.FeatureStyle;
import ru.melonity.ui.menu.clickui.style.elements.GiraStyle;
import ru.melonity.ui.menu.clickui.style.elements.KeybindStyle;
import ru.melonity.ui.menu.clickui.style.elements.TabStyle;

@Environment(EnvType.CLIENT)
public interface MenuStyle {
   class_2960 preview();

   String name();

   Color ascent();

   Color background();

   boolean headerBlocks();

   Color blocksColor();

   Color[] header();

   TabStyle tab();

   FeatureStyle feature();

   Color[] slider();

   Color booleanElement();

   Color previewValues();

   Color[] modeElement();

   GiraStyle gira();

   KeybindStyle keybind();

   Color autobuyPanel();

   Color unload();

   Color unloadHovered();
}
