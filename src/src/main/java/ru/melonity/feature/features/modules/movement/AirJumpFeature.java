package ru.melonity.feature.features.modules.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventPlayerMove;
import ru.melonity.util.math.MathUtil;

@Environment(EnvType.CLIENT)
public class AirJumpFeature extends SimpleFeature {
   private final ListSetting modeSetting = new ListSetting(
      "airjump.mode", ListSetting.ModeType.SINGLE, "airjump.mode.default", "airjump.mode.default", "Matrix Old"
   );
   public final EventListener<EventPlayerMove> onMotion = e -> {
      if (mc.field_1690.field_1903.method_1434()) {
         if (mc.field_1724.method_18798().field_1351 < 0.0) {
            if (this.modeSetting.contains("Matrix Old")) {
               mc.field_1724.method_24830(true);
               mc.field_1724.field_6017 = 0.0F;
               mc.field_1724.method_6043();
               if (mc.field_1724.field_34927 && (double)MathUtil.random() < 0.2) {
                  mc.field_1724.field_6017 = 0.0F;
               }
            } else {
               mc.field_1724.field_6017 = 0.0F;
               mc.field_1724.method_24830(true);
               mc.field_1724.method_6043();
            }
         }
      }
   };

   public AirJumpFeature() {
      super("AirJump", MenuCategory.MOVEMENT);
      this.settings(new MenuFeatureView[]{this.modeSetting});
   }
}
