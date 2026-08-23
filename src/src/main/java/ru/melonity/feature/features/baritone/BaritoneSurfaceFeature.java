package ru.melonity.feature.features.baritone;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;

@Environment(EnvType.CLIENT)
public class BaritoneSurfaceFeature extends SimpleFeature {
   public BaritoneSurfaceFeature() {
      super("Top", MenuCategory.BARITONE);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      class_310.method_1551().field_1724.field_3944.method_45729("#stop");
      if (value) {
         class_310.method_1551().field_1724.field_3944.method_45729("#surface");
         mc.method_1507(null);
      }
   }
}
