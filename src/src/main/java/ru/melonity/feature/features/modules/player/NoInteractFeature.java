package ru.melonity.feature.features.modules.player;

import java.util.Arrays;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;

@Environment(EnvType.CLIENT)
public class NoInteractFeature extends SimpleFeature {
   private final BooleanSetting allBlocks = new BooleanSetting("nointeract.all", false);
   private final ListSetting ignores = new ListSetting(
      "nointeract.blocks",
      ListSetting.ModeType.MULTI,
      "nointeract.chest",
      Arrays.asList(
         "nointeract.chest",
         "nointeract.door",
         "nointeract.button",
         "nointeract.dispenser",
         "nointeract.crafttable",
         "nointeract.trapdoor",
         "nointeract.furnace",
         "nointeract.lever",
         "nointeract.anvil",
         "nointeract.noteblock",
         "nointeract.hopper"
      )
   );

   public NoInteractFeature() {
      super("NoInteract", MenuCategory.PLAYER);
      this.setting(this.allBlocks);
      this.setting(this.ignores);
   }

   @Generated
   public BooleanSetting getAllBlocks() {
      return this.allBlocks;
   }

   @Generated
   public ListSetting getIgnores() {
      return this.ignores;
   }
}
