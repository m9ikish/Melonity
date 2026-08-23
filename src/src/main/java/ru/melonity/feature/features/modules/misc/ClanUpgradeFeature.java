package ru.melonity.feature.features.modules.misc;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_239;
import net.minecraft.class_2846;
import net.minecraft.class_2868;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;
import net.minecraft.class_2846.class_2847;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.Rotation;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeaturePrefix;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.inventory.InventoryUtil;
import ru.melonity.util.minecraft.RayTraceUtil;
import ru.melonity.util.time.Timer;

@Environment(EnvType.CLIENT)
public class ClanUpgradeFeature extends SimpleFeature {
   private final Timer timer = Timer.create();
   public final EventListener<UpdateLivingEvent> onUpdate = event -> {
      int local1_0 = InventoryUtil.getHotbarItem(mc.field_1724, class_1802.field_8810);
      if (mc.field_1687.method_27983().method_41185().method_12832().equals("lobby")) {
         if (mc.field_1724.field_6012 % 200 == 0) {
            Melonity.INSTANCE.getNotificationManager().add(new Notification("Error", "Тепнитесь на РТП", "feature-disabled.png"));
         }
      } else if (local1_0 == -1) {
         if (mc.field_1724.field_6012 % 240 == 0) {
            Melonity.INSTANCE.getNotificationManager().add(new Notification("Error", "Нужен редстоун или факел в хотбаре!", "feature-disabled.png"));
         }
      } else {
         class_2338 local2_1 = mc.field_1724.method_24515();
         RotationManager.INSTANCE.rotate(mc.field_1724.method_36454(), 90.0F);
         class_239 local3_2 = RayTraceUtil.rayTrace(3.0, Rotation.cameraYaw(), 90.0F);
         if (local3_2.method_17783() == class_240.field_1332) {
            mc.method_1562().method_52787(new class_2868(local1_0));
            mc.field_1724.method_31548().field_7545 = local1_0;
            if (mc.field_1687.method_8320(local2_1).method_26215()) {
               mc.field_1761.method_2896(mc.field_1724, class_1268.field_5808, (class_3965)local3_2);
            } else {
               mc.method_1562().method_52787(new class_2846(class_2847.field_12968, mc.field_1724.method_24515(), class_2350.field_11036));
               mc.field_1724.method_6104(class_1268.field_5808);
            }
         }
      }
   };

   public ClanUpgradeFeature() {
      super("ClanUpgrade", MenuCategory.MISC);
      this.prefix(new MenuFeaturePrefix[]{MenuFeaturePrefix.Funtime, MenuFeaturePrefix.SpookyTime});
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      RotationManager.INSTANCE.reset();
   }

   @Generated
   public Timer getTimer() {
      return this.timer;
   }

   @Generated
   public EventListener<UpdateLivingEvent> getOnUpdate() {
      return this.onUpdate;
   }
}
