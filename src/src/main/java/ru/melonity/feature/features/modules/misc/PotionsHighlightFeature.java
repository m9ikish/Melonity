package ru.melonity.feature.features.modules.misc;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1799;
import net.minecraft.class_1812;
import net.minecraft.class_1844;
import net.minecraft.class_310;
import net.minecraft.class_3544;
import net.minecraft.class_4081;
import net.minecraft.class_4587;
import net.minecraft.class_476;
import net.minecraft.class_9334;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.MenuColorSetting;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class PotionsHighlightFeature extends SimpleFeature {
   private final MenuColorSetting harmfulColorSetting = new MenuColorSetting("Harmful", Color.RED);
   private final MenuColorSetting healthfulColorSetting = new MenuColorSetting("Healthful", Color.MAGENTA);
   private final MenuColorSetting beneficialColorSetting = new MenuColorSetting("Beneficial", Color.GREEN);

   public PotionsHighlightFeature() {
      super("PotionsHighlight", MenuCategory.MISC);
      this.settings(new MenuFeatureView[]{this.harmfulColorSetting, this.healthfulColorSetting, this.beneficialColorSetting});
   }

   public void potionSlotHighlight(class_1799 stack, float x, float y, float size, float radius, class_4587 matrices) {
      if (this.isEnabled()) {
         RenderSystem local7_3 = Melonity.INSTANCE.getRenderSystem();
         if (!(class_310.method_1551().field_1755 instanceof class_476)
            || !class_3544.method_15440(class_310.method_1551().field_1755.method_25440().getString()).toLowerCase().contains("аукцион")
               && !class_3544.method_15440(class_310.method_1551().field_1755.method_25440().getString()).toLowerCase().contains("поиск")) {
            if (stack.method_7909() instanceof class_1812) {
               class_1844 local8_1 = (class_1844)stack.method_57825(class_9334.field_49651, class_1844.field_49274);
               Color local9_2 = null;

               for (class_1293 local11_0 : local8_1.method_57397()) {
                  if (((class_1291)local11_0.method_5579().comp_349()).method_18792() == class_4081.field_18272) {
                     local9_2 = this.harmfulColorSetting.getColor();
                  } else if (local11_0.method_5579() == class_1294.field_5914
                     || local11_0.method_5579() == class_1294.field_5915
                     || local11_0.method_5579() == class_1294.field_5924
                     || local11_0.method_5579() == class_1294.field_5898) {
                     local9_2 = this.healthfulColorSetting.getColor();
                  } else if (((class_1291)local11_0.method_5579().comp_349()).method_18792() == class_4081.field_18271) {
                     local9_2 = this.beneficialColorSetting.getColor();
                  }
               }

               if (local9_2 != null) {
                  local7_3.addRect(x, y, size, size, radius, RenderSystem.setAlpha(local9_2, 100), matrices);
               }
            }
         }
      }
   }
}
