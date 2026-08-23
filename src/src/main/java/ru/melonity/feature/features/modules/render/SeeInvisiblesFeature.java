package ru.melonity.feature.features.modules.render;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;

@Environment(EnvType.CLIENT)
public class SeeInvisiblesFeature extends SimpleFeature {
   private final NumberSetting opacity = new NumberSetting("global.opacity", value -> String.format("%.2f", value.floatValue()), 0.1, 1.0F, 0.5F);

   public SeeInvisiblesFeature() {
      super("SeeInvisibles", MenuCategory.RENDER);
      this.settingClear();
      this.setting(this.opacity);
   }

   @Generated
   public NumberSetting getOpacity() {
      return this.opacity;
   }
}
