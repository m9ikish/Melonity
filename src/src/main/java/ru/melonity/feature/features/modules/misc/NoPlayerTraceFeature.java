package ru.melonity.feature.features.modules.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;

@Environment(EnvType.CLIENT)
public class NoPlayerTraceFeature extends SimpleFeature {
   public NoPlayerTraceFeature() {
      super("NoPlayerTrace", MenuCategory.MISC);
   }
}
