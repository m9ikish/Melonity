package ru.melonity.feature.features.modules.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PreMotionEvent;

@Environment(EnvType.CLIENT)
public class SpeedFeature extends SimpleFeature {
   private final ListSetting modeSetting = new ListSetting("speed.mode", ListSetting.ModeType.SINGLE, "speed.timer", "speed.timer", "speed.accelerate");
   int count = 0;
   private final EventListener<PreMotionEvent> onPreMotion = event -> {
      if (mc.field_1724.method_24828()) {
         this.count++;
      }

      Melonity.timerSpeed = 1.0F;
      if (this.modeSetting.contains("Timer")) {
         if (mc.field_1724.method_24828()) {
            mc.field_1724.method_6043();
            mc.field_1690.field_1903.method_23481(false);
         }

         if ((double)mc.field_1724.field_6017 <= 0.1) {
            Melonity.timerSpeed = 1.2F;
         }

         if (mc.field_1724.field_6017 > 1.0F) {
            Melonity.timerSpeed = 0.45F;
         }

         if ((double)mc.field_1724.field_6017 >= 1.3) {
            Melonity.timerSpeed = 1.0F;
         }
      } else {
         if (mc.field_1724.method_24828()) {
            if (this.count < 4) {
               Melonity.timerSpeed = 0.93F;
            } else {
               Melonity.timerSpeed = 1.06F;
            }
         } else if ((double)mc.field_1724.field_6017 < 0.2) {
            Melonity.timerSpeed = 1.02F;
         } else if ((double)mc.field_1724.field_6017 > 0.2 || (double)mc.field_1724.field_6017 < 0.5) {
            Melonity.timerSpeed = 1.08F;
         } else if ((double)mc.field_1724.field_6017 > 0.5) {
            Melonity.timerSpeed = 1.0F;
         }

         if (this.count > 6) {
            this.count = 0;
         }
      }
   };

   public SpeedFeature() {
      super("Speed", MenuCategory.MOVEMENT);
      this.setting(this.modeSetting);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      this.count = 0;
      if (!value) {
         Melonity.timerSpeed = 1.0F;
      }
   }
}
