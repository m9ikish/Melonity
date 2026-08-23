package ru.melonity.ui.menu.clickui.style.elements;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.ui.menu.clickui.style.ElementStyle;

@Environment(EnvType.CLIENT)
public interface TabStyle extends ElementStyle {
   Color textColor();

   Color disabledTextColor();

   Color[] background();
}
