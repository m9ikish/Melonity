package ru.melonity.feature.features.modules.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_424;
import net.minecraft.class_4325;
import net.minecraft.class_442;
import net.minecraft.class_500;
import net.minecraft.class_642;
import ru.melonity.Melonity;
import ru.melonity.feature.ActionScheduler;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.TickEvent;
import ru.melonity.util.minecraft.PlayerUtil;

@Environment(EnvType.CLIENT)
public class AutoLeaveFeature extends SimpleFeature {
   private static final class_2561 SAVING_LEVEL_TEXT = class_2561.method_43471("menu.savingLevel");
   private final ListSetting modeSetting = new ListSetting(
      "autoleave.action",
      ListSetting.ModeType.SINGLE,
      "autoleave.action.server_leave",
      "autoleave.action.server_leave",
      "autoleave.action.spawn",
      "autoleave.action.home"
   );
   private final NumberSetting rangeSetting = new NumberSetting("autoleave.range", value -> String.format("%d blocks", value.intValue()), 1, 100, 40);
   private final EventListener<TickEvent> onUpdate = event -> {
      if (mc.method_1562() != null) {
         float local2_0 = this.rangeSetting.getValue().floatValue();
         boolean local3_1 = mc.field_1687
            .method_18456()
            .stream()
            .filter(player -> player != mc.field_1724)
            .anyMatch(player -> mc.field_1724.method_5739(player) <= local2_0);
         if (local3_1) {
            String local4_2 = this.modeSetting.getMode().getFirst();
            if (local4_2.equals("autoleave.action.server_leave")) {
               mc.method_44377().method_46552(mc, mc.field_1755, this::disconnect, true);
               this.setEnabled(false);
            } else if (local4_2.equals("autoleave.action.spawn")) {
               ActionScheduler.scheduleOnce(() -> PlayerUtil.sendPlayerMessage("/spawn"), 20);
               Melonity.INSTANCE.getNotificationManager().add(new Notification("AutoLeave", "autoleave.off", "feature-on.png"));
               this.setEnabled(false);
            } else {
               ActionScheduler.scheduleOnce(() -> PlayerUtil.sendPlayerMessage("/home"), 20);
               Melonity.INSTANCE.getNotificationManager().add(new Notification("AutoLeave", "autoleave.off", "feature-on.png"));
               this.setEnabled(false);
            }
         }
      }
   };

   public AutoLeaveFeature() {
      super("AutoLeave", MenuCategory.PLAYER);
      this.settings(new MenuFeatureView[]{this.modeSetting, this.rangeSetting});
   }

   private void disconnect() {
      boolean local1_0 = mc.method_1542();
      class_642 local2_1 = mc.method_1558();
      mc.field_1687.method_8525();
      if (local1_0) {
         mc.method_56134(new class_424(SAVING_LEVEL_TEXT));
      } else {
         mc.method_18099();
      }

      class_442 local3_2 = new class_442();
      if (local1_0) {
         mc.method_1507(local3_2);
      } else if (local2_1 != null && local2_1.method_52811()) {
         mc.method_1507(new class_4325(local3_2));
      } else {
         mc.method_1507(new class_500(local3_2));
      }
   }
}
