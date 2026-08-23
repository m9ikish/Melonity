package ru.melonity.feature.features.modules.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1802;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateLivingEvent;

@Environment(EnvType.CLIENT)
public class AutoGappleFeature extends SimpleFeature {
   private final NumberSetting healthSetting = new NumberSetting("global.health", value -> String.format("%.2f", value.floatValue()), 1, 20, 16);
   private boolean active;
   private final EventListener<UpdateLivingEvent> onUpdate = event -> {
      if ((!mc.field_1724.method_6115() || mc.field_1724.method_6030().method_7909() == class_1802.field_8463)
         && !mc.field_1724.method_7357().method_7904(class_1802.field_8463)) {
         if (mc.field_1724.method_6079().method_7909() == class_1802.field_8463 && mc.field_1724.method_6032() < this.healthSetting.getValue().floatValue()) {
            this.active = true;
            mc.field_1690.field_1904.method_23481(true);
         }

         if (this.active && mc.field_1724.method_6032() >= this.healthSetting.getValue().floatValue()) {
            mc.field_1690.field_1904.method_23481(false);
            this.active = false;
         }
      }
   };

   public AutoGappleFeature() {
      super("AutoGapple", MenuCategory.COMBAT);
      this.setting(this.healthSetting);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      this.active = false;
   }
}
