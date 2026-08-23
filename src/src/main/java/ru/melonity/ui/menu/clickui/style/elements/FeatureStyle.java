package ru.melonity.ui.menu.clickui.style.elements;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.ui.menu.clickui.style.ElementStyle;

@Environment(EnvType.CLIENT)
public interface FeatureStyle extends ElementStyle {
   Color[] enabledToggler();

   Color[] disabledToggler();

   Color disabledCircle();

   Color enabledCircle();

   Color background();
}
