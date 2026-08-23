package ru.melonity.feature.features.modules.player;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1657;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1776;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_636;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import ru.melonity.Melonity;
import ru.melonity.feature.ActionScheduler;
import ru.melonity.feature.Feature;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.MiddleClickEvent;
import ru.melonity.util.inventory.InventoryUtil;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000f\u0010\u0005\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0005\u0010\u0003J\u0017\u0010\b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u000b\u0010\tJ\u0017\u0010\r\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\r\u0010\tJ\u0017\u0010\u000e\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u000e\u0010\tJ\u0017\u0010\u000f\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u000f\u0010\tJ\u0017\u0010\u0010\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u0010\u0010\tJ\u000f\u0010\u0011\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0011\u0010\u0003J\u000f\u0010\u0012\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0012\u0010\u0003J!\u0010\u0017\u001a\u00020\u00042\b\u0010\u0014\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0016\u001a\u00020\u0015H\u0002¢\u0006\u0004\b\u0017\u0010\u0018J\u001f\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u0015H\u0002¢\u0006\u0004\b\u001b\u0010\u001cJ\u0011\u0010\u001d\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\u001d\u0010\u001eJ\u000f\u0010\u001f\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u001f\u0010\u0003J\u001f\u0010#\u001a\u00020\u00042\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 H\u0016¢\u0006\u0004\b#\u0010$R\u0014\u0010&\u001a\u00020%8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b&\u0010'R\u0014\u0010)\u001a\u00020(8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b)\u0010*R\u0016\u0010+\u001a\u00020\u00068\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b+\u0010,R\u0016\u0010-\u001a\u00020 8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b-\u0010.R\u001a\u00101\u001a\b\u0012\u0004\u0012\u0002000/8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b1\u00102¨\u00063"},
   d2 = {"Lru/melonity/feature/features/modules/player/MiddleClickPearlFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "", "useFromOffhand", "", "slot", "handleHotbarPearl", "(I)V", "slotId", "handleInventoryPearl", "pearlSlot", "interactHotbar", "interactFromInventory", "swapUseSwapBack", "swapWithSelected", "tryUseItemLegit", "tryUseItemPacket", "Lnet/minecraft/class_1269;", "result", "Lnet/minecraft/class_1268;", "hand", "handleSwing", "(Lnet/minecraft/class_1269;Lnet/minecraft/class_1268;)V", "Lnet/minecraft/class_1792;", "item", "usePacket", "(Lnet/minecraft/class_1792;Lnet/minecraft/class_1268;)V", "findPearlInInventory", "()Ljava/lang/Integer;", "flushGuiMovePackets", "", "value", "silent", "setEnabled", "(ZZ)V", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "switcherType", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "swap", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "originalSlot", "I", "isUsingPearl", "Z", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/MiddleClickEvent;", "onMiddle", "Lru/melonity/util/event/EventListener;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class MiddleClickPearlFeature extends SimpleFeature {
   @NotNull
   private final ListSetting switcherType;
   @NotNull
   private final BooleanSetting swap;
   private int originalSlot;
   private boolean isUsingPearl;
   @NotNull
   private final EventListener<MiddleClickEvent> onMiddle;

   public MiddleClickPearlFeature() {
      super("MiddleClickPearl", MenuCategory.MISC);
      ListSetting.ModeType var10004 = ListSetting.ModeType.SINGLE;
      String[] var1 = new String[]{"global.switcher.packet", "global.switcher.legit"};
      this.switcherType = new ListSetting("global.switcher.type", var10004, "global.switcher.packet", var1);
      this.swap = new BooleanSetting("global.swap_inv", true);
      this.originalSlot = -1;
      MenuFeatureView[] var2 = new MenuFeatureView[]{this.switcherType, this.swap};
      this.settings(var2);
      this.onMiddle = event -> onMiddle$lambda$0(this, event);
   }

   private final void useFromOffhand() {
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 local1_0 = SimpleFeature.mc.field_1724;
         if (SimpleFeature.mc.field_1761 != null) {
            class_636 local2_1 = SimpleFeature.mc.field_1761;
            if (!this.switcherType.contains("global.switcher.legit")) {
               local2_1.method_2919((class_1657)local1_0, class_1268.field_5810);
            } else {
               this.isUsingPearl = true;
               ActionScheduler.schedule(() -> useFromOffhand$lambda$1(local2_1, local1_0, this), 1);
               ActionScheduler.schedule(() -> useFromOffhand$lambda$2(this), 2);
            }
         }
      }
   }

   private final void handleHotbarPearl(int slot) {
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 local2_0 = SimpleFeature.mc.field_1724;
         if (!this.switcherType.contains("global.switcher.legit")) {
            class_1792 var10001 = class_1802.field_8634;
            Intrinsics.checkNotNullExpressionValue(class_1802.field_8634, "ENDER_PEARL");
            this.usePacket(var10001, class_1268.field_5808);
         } else {
            this.isUsingPearl = true;
            this.originalSlot = local2_0.method_31548().field_7545;
            this.interactHotbar(slot);
         }
      }
   }

   private final void handleInventoryPearl(int slotId) {
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 local2_0 = SimpleFeature.mc.field_1724;
         this.flushGuiMovePackets();
         this.isUsingPearl = true;
         this.originalSlot = local2_0.method_31548().field_7545;
         if (!this.switcherType.contains("global.switcher.legit")) {
            this.swapUseSwapBack(slotId);
         } else {
            this.interactFromInventory(slotId);
         }
      }
   }

   private final void interactHotbar(int pearlSlot) {
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 local2_0 = SimpleFeature.mc.field_1724;
         int local3_1 = this.originalSlot;
         ActionScheduler.schedule(() -> interactHotbar$lambda$3(local2_0, pearlSlot), 1);
         ActionScheduler.schedule(this::tryUseItemLegit, 2);
         ActionScheduler.schedule(() -> interactHotbar$lambda$4(local2_0, local3_1), 3);
         ActionScheduler.schedule(() -> interactHotbar$lambda$5(this), 4);
      }
   }

   private final void interactFromInventory(int slotId) {
      ActionScheduler.scheduleOnce(() -> interactFromInventory$lambda$6(this, slotId), 1);
      ActionScheduler.scheduleOnce(this::tryUseItemLegit, 2);
      ActionScheduler.scheduleOnce(() -> interactFromInventory$lambda$7(this, slotId), 3);
      ActionScheduler.scheduleOnce(() -> interactFromInventory$lambda$8(this), 4);
   }

   private final void swapUseSwapBack(int slotId) {
      this.swapWithSelected(slotId);
      this.tryUseItemPacket();
      this.swapWithSelected(slotId);
      this.isUsingPearl = false;
      this.originalSlot = -1;
   }

   private final void swapWithSelected(int slotId) {
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 local2_0 = SimpleFeature.mc.field_1724;
         int local3_1 = local2_0.method_31548().field_7545;
         InventoryUtil.clickSlotId(slotId, local3_1, class_1713.field_7791);
      }
   }

   private final void tryUseItemLegit() {
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 local1_0 = SimpleFeature.mc.field_1724;
         if (SimpleFeature.mc.field_1761 != null) {
            class_636 local2_1 = SimpleFeature.mc.field_1761;
            class_1269 local3_2 = local2_1.method_2919((class_1657)local1_0, class_1268.field_5808);
            this.handleSwing(local3_2, class_1268.field_5808);
         }
      }
   }

   private final void tryUseItemPacket() {
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 local1_0 = SimpleFeature.mc.field_1724;
         if (SimpleFeature.mc.field_1761 != null) {
            class_636 local2_1 = SimpleFeature.mc.field_1761;
            local2_1.method_2919((class_1657)local1_0, class_1268.field_5808);
         }
      }
   }

   private final void handleSwing(class_1269 result, class_1268 hand) {
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 local3_0 = SimpleFeature.mc.field_1724;
         if (result != null) {
            switch (result) {
               case field_5812:
                  local3_0.method_6104(hand);
                  break;
               case field_5811:
                  if (result.method_23666()) {
                     local3_0.method_6104(hand);
                  }
            }
         }
      }
   }

   private final void usePacket(class_1792 item, class_1268 hand) {
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 local3_0 = SimpleFeature.mc.field_1724;
         if (SimpleFeature.mc.field_1761 != null) {
            class_636 local4_1 = SimpleFeature.mc.field_1761;
            int local5_2 = InventoryUtil.getSlotInInventoryOrHotbar(item, true);
            if (hand == class_1268.field_5810) {
               local4_1.method_2919((class_1657)local3_0, class_1268.field_5810);
            } else if (local5_2 != -1) {
               int local6_3 = local3_0.method_31548().field_7545;
               local3_0.method_31548().field_7545 = local5_2;
               local4_1.method_2919((class_1657)local3_0, class_1268.field_5808);
               local3_0.method_31548().field_7545 = local6_3;
            }
         }
      }
   }

   private final Integer findPearlInInventory() {
      if (SimpleFeature.mc.field_1724 == null) {
         return null;
      } else {
         class_746 local1_1 = SimpleFeature.mc.field_1724;
         if (local1_1.field_7512 == null) {
            return null;
         } else {
            class_1703 local2_2 = local1_1.field_7512;
            Iterator var10000 = local2_2.field_7761.iterator();
            Intrinsics.checkNotNullExpressionValue(var10000, "iterator(...)");
            Iterator var3 = var10000;

            while (var3.hasNext()) {
               class_1735 local4_0 = (class_1735)var3.next();
               if (Intrinsics.areEqual(local4_0.field_7871, local1_1.method_31548())
                  && local4_0.method_34266() >= 9
                  && Intrinsics.areEqual(local4_0.method_7677().method_7909(), class_1802.field_8634)) {
                  return local4_0.field_7874;
               }
            }

            return null;
         }
      }
   }

   private final void flushGuiMovePackets() {
      if (Melonity.INSTANCE.featureManager.guiMoveFeature != null) {
         Melonity.INSTANCE.featureManager.guiMoveFeature.flushQueuedPackets();
      }
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      if (value) {
         for (Feature local4_1 : Melonity.INSTANCE.featureManager.features) {
            if (!Intrinsics.areEqual(local4_1, this)) {
               for (MenuFeatureView local6_0 : local4_1.getMenuObject().elementViews) {
                  if (local6_0 instanceof BindSetting && ((BindSetting)local6_0).bind == 2) {
                     Melonity.INSTANCE
                        .notificationManager
                        .add(new Notification("Keybind conflict", "MOUSE3 в двух функциях одновременно", "feature-disabled.png"));
                     super.setEnabled(false);
                     return;
                  }
               }
            }
         }
      }

      super.setEnabled(value, silent);
   }

   private static final void onMiddle$lambda$0(MiddleClickPearlFeature local0_4, MiddleClickEvent var1) {
      if (!local0_4.isUsingPearl && SimpleFeature.mc.field_1755 == null && SimpleFeature.mc.field_1724 != null) {
         if (SimpleFeature.mc.field_1724 != null) {
            class_746 local2_1 = SimpleFeature.mc.field_1724;
            class_1268 local3_2 = local2_1.method_6079().method_7909() instanceof class_1776 ? class_1268.field_5810 : class_1268.field_5808;
            if (local3_2 == class_1268.field_5810) {
               local0_4.useFromOffhand();
            } else {
               int local4_3 = InventoryUtil.getSlotInInventoryOrHotbar(class_1802.field_8634, true);
               if (local4_3 != -1) {
                  local0_4.handleHotbarPearl(local4_3);
               } else {
                  if (local0_4.swap.get()) {
                     Integer local5_0 = local0_4.findPearlInInventory();
                     if (local5_0 != null) {
                        local0_4.handleInventoryPearl(local5_0);
                     }
                  }
               }
            }
         }
      }
   }

   private static final void useFromOffhand$lambda$1(class_636 $im, class_746 $player, MiddleClickPearlFeature this$0) {
      class_1269 local3_0 = $im.method_2919((class_1657)$player, class_1268.field_5810);
      this$0.handleSwing(local3_0, class_1268.field_5810);
   }

   private static final void useFromOffhand$lambda$2(MiddleClickPearlFeature this$0) {
      this$0.isUsingPearl = false;
   }

   private static final void interactHotbar$lambda$3(class_746 $player, int $pearlSlot) {
      $player.method_31548().field_7545 = $pearlSlot;
   }

   private static final void interactHotbar$lambda$4(class_746 $player, int $prev) {
      $player.method_31548().field_7545 = $prev;
   }

   private static final void interactHotbar$lambda$5(MiddleClickPearlFeature this$0) {
      this$0.isUsingPearl = false;
      this$0.originalSlot = -1;
   }

   private static final void interactFromInventory$lambda$6(MiddleClickPearlFeature this$0, int $slotId) {
      this$0.swapWithSelected($slotId);
   }

   private static final void interactFromInventory$lambda$7(MiddleClickPearlFeature this$0, int $slotId) {
      this$0.swapWithSelected($slotId);
   }

   private static final void interactFromInventory$lambda$8(MiddleClickPearlFeature this$0) {
      this$0.isUsingPearl = false;
      this$0.originalSlot = -1;
   }
}
