package ru.melonity.feature.features.modules.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;

@Environment(EnvType.CLIENT)
public class ToggleSoundFeature extends SimpleFeature {
   public ListSetting sound = new ListSetting(
      "global.sound", ListSetting.ModeType.SINGLE, "togglesound.default", "togglesound.default", "togglesound.variant1", "togglesound.variant2"
   );

   public ToggleSoundFeature() {
      super("ToggleSound", MenuCategory.MISC);
      this.setting(this.sound);
      this.setEnabled(true, true);
   }
}
