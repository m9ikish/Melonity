package ru.melonity.feature.features.modules.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;

@Environment(EnvType.CLIENT)
public class GrayFilterFeature extends SimpleFeature {
   public GrayFilterFeature() {
      super("GrayFilter", MenuCategory.RENDER);
   }
}
