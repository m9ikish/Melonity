package ru.melonity.feature.features.baritone;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import ru.melonity.feature.features.baritone.setting.MenuCoordSetting;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;

@Environment(EnvType.CLIENT)
public class BaritoneTunnelFeature extends SimpleFeature {
   private final MenuCoordSetting setting = new MenuCoordSetting("Parameters", "H", "W", "D");

   public BaritoneTunnelFeature() {
      super("Tunnel", MenuCategory.BARITONE);
      this.setting(this.setting);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      class_310.method_1551().field_1724.field_3944.method_45729("#stop");
      if (value) {
         class_310.method_1551().field_1724.field_3944.method_45729("#tunnel " + this.setting.getX() + " " + this.setting.getY() + " " + this.setting.getZ());
         mc.method_1507(null);
      }
   }
}
