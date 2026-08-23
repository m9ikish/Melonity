package ru.melonity.util.event.listeners;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.feature.features.modules.player.MiddleClickPearlFeature;
import ru.melonity.ui.menu.clickui.Menu;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.annotation.ListenerClass;
import ru.melonity.util.event.events.KeyEvent;

@Environment(EnvType.CLIENT)
@ListenerClass
public class BindListener {
   private int lastKey = Integer.MIN_VALUE;
   private long lastKeyTime = 0L;
   private static final long TOGGLE_DEBOUNCE_MS = 150L;
   private final EventListener<KeyEvent> onKey = event -> {
      if (!(class_310.method_1551().field_1755 instanceof Menu)) {
         MiddleClickPearlFeature local2_3 = null;

         for (Feature local4_0 : Melonity.INSTANCE.getFeatureManager().getFeatures()) {
            if (local4_0 instanceof MiddleClickPearlFeature) {
               local2_3 = (MiddleClickPearlFeature)local4_0;
               break;
            }
         }

         long local3_4 = System.currentTimeMillis();
         if (event.key() != this.lastKey || local3_4 - this.lastKeyTime >= 150L) {
            this.lastKey = event.key();
            this.lastKeyTime = local3_4;

            for (Feature local6_2 : Melonity.INSTANCE.getFeatureManager().getFeatures()) {
               BindSetting local7_1 = local6_2.getBindSetting();
               if (local7_1 != null && event.key() == local7_1.bind && local7_1.bind != -1 && event.key() != 301) {
                  if (local7_1.bind == 2 && local2_3 != null && local2_3.isEnabled()) {
                     Melonity.INSTANCE
                        .getNotificationManager()
                        .add(new Notification("Keybind conflict", "MOUSE3 в двух функциях одновременно", "feature-disabled.png"));
                     return;
                  }

                  local6_2.setEnabled(!local6_2.isEnabled(), false);
               }
            }
         }
      }
   };
}
