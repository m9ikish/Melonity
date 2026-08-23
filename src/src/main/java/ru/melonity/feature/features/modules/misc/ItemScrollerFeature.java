package ru.melonity.feature.features.modules.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;

@Environment(EnvType.CLIENT)
public class ItemScrollerFeature extends SimpleFeature {
   public ItemScrollerFeature() {
      super("ItemScroller", MenuCategory.MISC);
   }
}
