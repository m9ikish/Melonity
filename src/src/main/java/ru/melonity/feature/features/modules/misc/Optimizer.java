package ru.melonity.feature.features.modules.misc;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;

@Environment(EnvType.CLIENT)
public class Optimizer extends SimpleFeature {
   private final ListSetting setting = new ListSetting("Work if", ListSetting.ModeType.MULTI, "NameTags", "Vegetation", "NameTags");

   public Optimizer() {
      super("Optimizer", MenuCategory.MISC);
   }

   @Generated
   public ListSetting getSetting() {
      return this.setting;
   }
}
