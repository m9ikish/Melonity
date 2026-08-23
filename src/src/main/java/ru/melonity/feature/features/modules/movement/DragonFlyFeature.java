package ru.melonity.feature.features.modules.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.MoveEvent;

@Environment(EnvType.CLIENT)
public class DragonFlyFeature extends SimpleFeature {
   private final NumberSetting dragonFlySpeed = new NumberSetting("global.speed", value -> String.format("%.1f", value.floatValue()), 0.1F, 15.0F, 3.0F);
   private final NumberSetting dragonFlyMotionY = new NumberSetting("dragonfly.speed_y", value -> String.format("%.1f", value.floatValue()), 0.1F, 5, 0.5F);
   private final EventListener<MoveEvent> onPreMotion = event -> {
      if (mc.field_1724.method_31549().field_7479) {
         if (!mc.field_1724.method_5715() && mc.field_1690.field_1903.method_1434()) {
            event.motion = new class_243(event.motion.field_1352, (double)this.dragonFlyMotionY.getValue().floatValue(), event.motion.field_1350);
         }

         if (mc.field_1690.field_1832.method_1434()) {
            event.motion = new class_243(event.motion.field_1352, (double)(-this.dragonFlyMotionY.getValue().floatValue()), event.motion.field_1350);
         }

         this.setMoveMotion(event, (double)this.dragonFlySpeed.getValue().floatValue());
      }
   };

   public DragonFlyFeature() {
      super("DragonFly", MenuCategory.MOVEMENT);
      this.setting(this.dragonFlySpeed);
      this.setting(this.dragonFlyMotionY);
   }

   private void setMoveMotion(MoveEvent move, double motion) {
      double local4_0 = (double)mc.field_1724.field_3913.field_3905;
      double local6_1 = (double)mc.field_1724.field_3913.field_3907;
      float local8_2 = mc.field_1724.method_36454();
      if (local4_0 == 0.0 && local6_1 == 0.0) {
         move.motion = new class_243(0.0, move.motion.field_1351, 0.0);
      } else {
         if (local4_0 != 0.0) {
            if (local6_1 > 0.0) {
               local8_2 += (float)(local4_0 > 0.0 ? -45 : 45);
            } else if (local6_1 < 0.0) {
               local8_2 += (float)(local4_0 > 0.0 ? 45 : -45);
            }

            local6_1 = 0.0;
            if (local4_0 > 0.0) {
               local4_0 = 1.0;
            } else if (local4_0 < 0.0) {
               local4_0 = -1.0;
            }
         }

         move.motion = new class_243(
            local4_0 * motion * (double)class_3532.method_15362((float)Math.toRadians((double)(local8_2 + 90.0F)))
               + local6_1 * motion * (double)class_3532.method_15374((float)Math.toRadians((double)(local8_2 + 90.0F))),
            move.motion.field_1351,
            local4_0 * motion * (double)class_3532.method_15374((float)Math.toRadians((double)(local8_2 + 90.0F)))
               - local6_1 * motion * (double)class_3532.method_15362((float)Math.toRadians((double)(local8_2 + 90.0F)))
         );
      }
   }
}
