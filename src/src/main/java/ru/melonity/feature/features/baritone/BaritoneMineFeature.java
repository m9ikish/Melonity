package ru.melonity.feature.features.baritone;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2248;
import net.minecraft.class_310;
import ru.melonity.Melonity;
import ru.melonity.feature.features.baritone.setting.MenuButtonSetting;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;

@Environment(EnvType.CLIENT)
public class BaritoneMineFeature extends SimpleFeature {
   private final MenuButtonSetting button = new MenuButtonSetting(
      "mine.choose", () -> Melonity.INSTANCE.getMenu().getBlocksSelector().setOpened(!Melonity.INSTANCE.getMenu().getBlocksSelector().isOpened())
   );

   public BaritoneMineFeature() {
      super("Mine", MenuCategory.BARITONE);
      this.settingClear();
      this.setting(this.button);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      class_310.method_1551().field_1724.field_3944.method_45729("#stop");
      if (value) {
         StringBuilder local3_2 = new StringBuilder();

         for (class_2248 local5_1 : Melonity.INSTANCE.getMenu().getBlocksSelector().getSelectedBlocks()) {
            String local6_0 = local5_1.method_9539().substring("block.minecraft.".length());
            local3_2.append(" ").append(String.format("minecraft:%s", local6_0));
         }

         if (local3_2.isEmpty()) {
            return;
         }

         class_310.method_1551().field_1724.field_3944.method_45729("#mine" + local3_2);
         mc.method_1507(null);
      }
   }

   @Generated
   public MenuButtonSetting getButton() {
      return this.button;
   }
}
