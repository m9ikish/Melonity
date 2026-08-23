package ru.melonity.feature.features.modules.combat;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1304;
import net.minecraft.class_1735;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_746;
import ru.melonity.Melonity;
import ru.melonity.feature.ActionScheduler;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.KeyEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.event.listeners.GPSListener;
import ru.melonity.util.inventory.InventoryUtil;
import ru.melonity.util.time.Timer;

@Environment(EnvType.CLIENT)
public class ElytraHelperFeature extends SimpleFeature {
   private final ListSetting switcherType = new ListSetting(
      "global.switcher.type", ListSetting.ModeType.SINGLE, "global.switcher.packet", "global.switcher.packet", "global.switcher.legit"
   );
   private final BindSetting swapBind = new BindSetting("global.swap", -1);
   private final BindSetting fireworkBind = new BindSetting("elytrahelper.firework", -1);
   private final BooleanSetting autoPilot = new BooleanSetting("elytrahelper.autopilot", false);
   private final Timer timer = Timer.create();
   private int p;
   private int originalSlot = -1;
   private boolean isUsingFirework = false;
   private boolean isSwappingChest = false;
   private final EventListener<UpdateLivingEvent> onUpdate = e -> this.handleUpdate(e);
   private final EventListener<EventInput> onInput = event -> {
      if (this.p > 0) {
         event.setForward(0.0F);
         this.p--;
      }
   };
   private final EventListener<KeyEvent> onKey = event -> {
      if (event.key() == this.swapBind.bind) {
         this.p = 1;
         this.swapChest();
         this.p = 1;
      }

      if (event.key() == this.fireworkBind.bind) {
         this.useFirework();
      }
   };

   public ElytraHelperFeature() {
      super("ElytraHelper", MenuCategory.COMBAT);
      this.settingClear();
      this.settings(new MenuFeatureView[]{this.autoPilot, this.switcherType, this.swapBind, this.fireworkBind});
   }

   private void handleUpdate(UpdateLivingEvent event) {
      class_746 player = mc.field_1724;
      if (player != null && mc.field_1687 != null) {
         if (!this.autoPilot.get()) {
            this.timer.reset();
         } else if (player.method_6128()) {
            class_243 velocity = player.method_18798();
            double horizontalSpeed = Math.sqrt(velocity.field_1352 * velocity.field_1352 + velocity.field_1350 * velocity.field_1350);
            float yaw = player.method_36454();
            GPSListener gps = Melonity.INSTANCE.getGps();
            if (gps != null && gps.isEnabled()) {
               double deltaX = (double)gps.getX() + 0.5 - player.method_23317();
               double deltaZ = (double)gps.getZ() + 0.5 - player.method_23321();
               yaw = (float)(Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90.0);
            }

            float pitch = horizontalSpeed < 0.45 ? 20.0F : -12.0F;
            RotationManager.INSTANCE.rotate(yaw, pitch);
            if (!this.isUsingFirework
               && horizontalSpeed < 0.65
               && !player.method_7357().method_7904(class_1802.field_8639)
               && this.timer.hasReached(1200L)) {
               this.timer.reset();
               this.useFirework();
            }
         } else {
            this.timer.reset();
         }
      }
   }

   private void swapChest() {
      if (!this.isSwappingChest) {
         this.isSwappingChest = true;
         boolean local1_0 = mc.field_1724.method_6118(class_1304.field_6174).method_7909() == class_1802.field_8833;
         class_1735 local2_1 = local1_0 ? this.findChestPlate() : InventoryUtil.getSlot(class_1802.field_8833);
         if (local2_1 == null) {
            mc.field_1705.method_1743().method_1812(class_2561.method_43470("§cНе найдено элитры/нагрудника"));
            this.isSwappingChest = false;
         } else {
            if (!this.switcherType.contains("global.switcher.legit")) {
               InventoryUtil.moveItem(local2_1.method_34266(), 6);
               this.isSwappingChest = false;
            } else {
               ActionScheduler.schedule(() -> InventoryUtil.moveItem(local2_1.method_34266(), 6), 1);
               ActionScheduler.schedule(() -> this.isSwappingChest = false, 2);
            }
         }
      }
   }

   private class_1735 findChestPlate() {
      class_1792[] local1_2 = new class_1792[]{
         class_1802.field_22028, class_1802.field_8058, class_1802.field_8873, class_1802.field_8523, class_1802.field_8678, class_1802.field_8577
      };

      for (class_1792 local5_1 : local1_2) {
         class_1735 local6_0 = InventoryUtil.getSlot(local5_1);
         if (local6_0 != null) {
            return local6_0;
         }
      }

      return null;
   }

   private void useFirework() {
      if (!this.isUsingFirework && mc.field_1755 == null) {
         this.isUsingFirework = true;
         this.originalSlot = mc.field_1724.method_31548().field_7545;
         if (!this.switcherType.contains("global.switcher.legit")) {
            this.useFireworkPacket();
            this.isUsingFirework = false;
         } else {
            this.useFireworkLegit();
         }
      }
   }

   private void useFireworkPacket() {
      int local1_0 = InventoryUtil.getSlotInInventoryOrHotbar(class_1802.field_8639, true);
      if (local1_0 == -1) {
         mc.field_1705.method_1743().method_1812(class_2561.method_43470("§cНет фейерверков в инвентаре"));
      } else if (!mc.field_1724.method_7357().method_7904(class_1802.field_8639)) {
         int local2_1 = mc.field_1724.method_31548().field_7545;
         mc.field_1724.method_31548().field_7545 = local1_0;
         class_1269 local3_2 = mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
         // PASS never reports shouldSwingHand(): the rocket was fired with no swing.
         if (local3_2.method_23665() && local3_2.method_23666()) {
            mc.field_1724.method_6104(class_1268.field_5808);
         }

         mc.field_1724.method_31548().field_7545 = local2_1;
      }
   }

   private void useFireworkLegit() {
      int local1_0 = InventoryUtil.getSlotInInventoryOrHotbar(class_1802.field_8639, true);
      if (local1_0 == -1) {
         mc.field_1705.method_1743().method_1812(class_2561.method_43470("§cНет фейерверков в инвентаре"));
         this.isUsingFirework = false;
         this.originalSlot = -1;
      } else if (mc.field_1724.method_7357().method_7904(class_1802.field_8639)) {
         this.isUsingFirework = false;
         this.originalSlot = -1;
      } else {
         this.interactFirework(local1_0);
      }
   }

   private void interactFirework(int fireworkSlot) {
      int local2_0 = this.originalSlot >= 0 ? this.originalSlot : mc.field_1724.method_31548().field_7545;
      ActionScheduler.schedule(() -> this.switchSlot(fireworkSlot), 1);
      ActionScheduler.schedule(() -> this.tryUseFirework(), 2);
      ActionScheduler.schedule(() -> this.restoreSlotSafe(local2_0), 3);
      ActionScheduler.schedule(() -> {
         this.isUsingFirework = false;
         this.originalSlot = -1;
      }, 4);
   }

   private void switchSlot(int slot) {
      if (mc.field_1724 != null) {
         mc.field_1724.method_31548().field_7545 = slot;
      }
   }

   private void tryUseFirework() {
      if (mc.field_1724 != null) {
         class_1269 local1_0 = mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
         // Same dead conjunction on the legit-switcher path.
         if (local1_0.method_23665() && local1_0.method_23666()) {
            mc.field_1724.method_6104(class_1268.field_5808);
         }
      }
   }

   private void restoreSlotSafe(int slot) {
      if (mc.field_1724 != null) {
         if (slot >= 0 && slot <= 8) {
            mc.field_1724.method_31548().field_7545 = slot;
         }
      }
   }

   @Generated
   public BooleanSetting getAutoPilot() {
      return this.autoPilot;
   }
}
