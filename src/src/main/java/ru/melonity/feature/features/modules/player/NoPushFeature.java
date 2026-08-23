package ru.melonity.feature.features.modules.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;

@Environment(EnvType.CLIENT)
public class NoPushFeature extends SimpleFeature {
   public final ListSetting noPushSetting = new ListSetting("nopush.src", ListSetting.ModeType.MULTI, "nopush.ent", "nopush.ent", "nopush.block");

   public NoPushFeature() {
      super("NoPush", MenuCategory.PLAYER);
      this.setting(this.noPushSetting);
   }
}
