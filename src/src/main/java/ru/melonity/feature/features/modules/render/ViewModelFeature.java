package ru.melonity.feature.features.modules.render;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;

@Environment(EnvType.CLIENT)
public class ViewModelFeature extends SimpleFeature {
   private final NumberSetting handX = new NumberSetting("viewmodel.x", value -> String.format("%.1f", value.floatValue()), -1, 1, 0);
   private final NumberSetting handY = new NumberSetting("viewmodel.y", value -> String.format("%.1f", value.floatValue()), -1, 1, 0);
   private final NumberSetting handZ = new NumberSetting("viewmodel.z", value -> String.format("%.1f", value.floatValue()), -1, 1, 0);

   public ViewModelFeature() {
      super("ViewModel", MenuCategory.RENDER);
      this.settings(new MenuFeatureView[]{this.handX, this.handY, this.handZ});
   }

   @Generated
   public NumberSetting getHandX() {
      return this.handX;
   }

   @Generated
   public NumberSetting getHandY() {
      return this.handY;
   }

   @Generated
   public NumberSetting getHandZ() {
      return this.handZ;
   }
}
