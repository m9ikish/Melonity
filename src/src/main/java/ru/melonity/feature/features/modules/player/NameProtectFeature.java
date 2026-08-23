package ru.melonity.feature.features.modules.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;

@Environment(EnvType.CLIENT)
public class NameProtectFeature extends SimpleFeature {
   public NameProtectFeature() {
      super("NameProtect", MenuCategory.PLAYER);
   }
}
