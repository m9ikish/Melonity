package ru.melonity.feature.features.modules.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PreMotionEvent;
import ru.melonity.util.minecraft.MovementUtil;

@Environment(EnvType.CLIENT)
public class WaterSpeedFeature extends SimpleFeature {
   private final NumberSetting speed = new NumberSetting("global.speed", value -> String.format("%.2f", value.floatValue()), 0.1F, 0.6F, 0.4F);
   private final EventListener<PreMotionEvent> onTravel = event -> {
      if (!mc.field_1724.field_5992 && !mc.field_1724.field_5976) {
         if (mc.field_1724.method_5681()) {
            float local2_0 = this.speed.getValue().floatValue() / 15.0F;
            MovementUtil.setMotion(MovementUtil.getMotion() + (double)local2_0);
         }
      }
   };

   public WaterSpeedFeature() {
      super("WaterSpeed", MenuCategory.MOVEMENT);
      this.setting(this.speed);
   }
}
