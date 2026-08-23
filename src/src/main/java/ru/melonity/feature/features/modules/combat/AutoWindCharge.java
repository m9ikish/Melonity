package ru.melonity.feature.features.modules.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1802;
import net.minecraft.class_243;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_746;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import ru.melonity.fabric.client.model.ILivingEntity;
import ru.melonity.feature.ActionScheduler;
import ru.melonity.feature.features.command.ChatUtil;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.inventory.InventoryUtil;

@Environment(EnvType.CLIENT)
public class AutoWindCharge extends SimpleFeature {
   private final ListSetting modeSwap = new ListSetting(
      "windcharge.swapmode", ListSetting.ModeType.SINGLE, "windcharge.def", "windcharge.def", "windcharge.legit"
   );
   private final BooleanSetting autoJump = new BooleanSetting("windcharge.autojump", false);
   private final BooleanSetting disableAfterUse = new BooleanSetting("Выключать после использования", false);
   private int previousSlot = -1;
   private boolean isExecuting = false;
   private final EventListener<UpdateEvent> onUpdate = e -> {
      if (this.isExecuting) {
         RotationManager.INSTANCE.rotate(mc.field_1724.method_36454(), 90.0F);
         ((ILivingEntity)mc.field_1724).setPitchHead(90.0F);
      } else {
         int local2_0 = InventoryUtil.getHotbarItem(mc.field_1724, class_1802.field_49098);
         if (local2_0 == -1) {
            if (mc.field_1724.field_6012 % 64 == 0) {
               ChatUtil.prefix("Не найден Wind Charge в хотбаре");
            }
         } else if (!mc.field_1724.method_7357().method_7904(class_1802.field_49098)) {
            if (mc.field_1724.method_24828() && this.isBlockBelow()) {
               this.executeWindChargeSequence(local2_0);
            }
         }
      }
   };

   public AutoWindCharge() {
      super("AutoWindCharge", MenuCategory.PLAYER);
      this.settings(new MenuFeatureView[]{this.modeSwap, this.autoJump, this.disableAfterUse});
   }

   private boolean isBlockBelow() {
      class_746 player = mc.field_1724;
      if (player == null || mc.field_1687 == null) {
         return false;
      } else {
         class_243 feet = player.method_19538();
         class_243 below = feet.method_1031(0.0, -1.5, 0.0);
         class_3965 hitResult = mc.field_1687.method_17742(new class_3959(feet, below, class_3960.field_17558, class_242.field_1348, player));
         return hitResult != null && hitResult.method_17783() == class_240.field_1332;
      }
   }

   private void executeWindChargeSequence(int windChargeSlot) {
      class_746 player = mc.field_1724;
      if (player != null && !this.isExecuting) {
         this.isExecuting = true;
         this.previousSlot = player.method_31548().field_7545;
         RotationManager.INSTANCE.rotate(player.method_36454(), 90.0F);
         ((ILivingEntity)player).setPitchHead(90.0F);
         if (this.autoJump.get()) {
            player.method_6043();
         }

         boolean legit = this.modeSwap.contains("windcharge.legit");
         this.switchAndUse(legit ? 2 : 1, windChargeSlot);
         this.switchToOld(legit ? 4 : 2);
      }
   }

   private void switchAndUse(int tick, int windChargeSlot) {
      ActionScheduler.schedule(() -> {
         mc.field_1724.method_31548().field_7545 = windChargeSlot;
         RotationManager.INSTANCE.rotate(mc.field_1724.method_36454(), 90.0F);
         class_1269 local1_0 = mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
         // PASS never reports shouldSwingHand(), so this never fired and the wind
         // charge went out with no HandSwingC2SPacket.
         if (local1_0.method_23665() && local1_0.method_23666()) {
            mc.field_1724.method_6104(class_1268.field_5808);
         }
      }, tick);
   }

   private void switchToOld(int tick) {
      ActionScheduler.schedule(() -> {
         if (this.previousSlot != -1) {
            mc.field_1724.method_31548().field_7545 = this.previousSlot;
         }

         this.previousSlot = -1;
         this.isExecuting = false;
         RotationManager.INSTANCE.reset();
         if (this.disableAfterUse.get()) {
            this.setEnabled(false);
         }
      }, tick);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      if (!value) {
         this.isExecuting = false;
         RotationManager.INSTANCE.reset();
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (this.previousSlot != -1) {
         mc.field_1724.method_31548().field_7545 = this.previousSlot;
         this.previousSlot = -1;
      }
   }
}
