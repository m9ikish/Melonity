package ru.melonity.feature.features.modules.render;

import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.MenuColorSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;

@Environment(EnvType.CLIENT)
public class FogColorFeature extends SimpleFeature {
   private final MenuColorSetting colorSetting = new MenuColorSetting("global.color", Color.MAGENTA);
   private final NumberSetting distanceSetting = new NumberSetting("global.range", value -> String.format("%d", value.intValue()), 1, 45, 20);

   public FogColorFeature() {
      super("FogColor", MenuCategory.RENDER);
      this.settings(new MenuFeatureView[]{this.colorSetting, this.distanceSetting});
   }

   @Generated
   public MenuColorSetting getColorSetting() {
      return this.colorSetting;
   }

   @Generated
   public NumberSetting getDistanceSetting() {
      return this.distanceSetting;
   }
}
