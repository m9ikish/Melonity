package ru.melonity.feature.features.modules.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_2680;
import net.minecraft.class_3965;
import ru.melonity.Melonity;
import ru.melonity.feature.ActionScheduler;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.inventory.InventoryUtil;
import ru.melonity.util.time.Timer;

@Environment(EnvType.CLIENT)
public class AutoToolFeature extends SimpleFeature {
   private final BooleanSetting swap = new BooleanSetting("global.swap_inv", true);
   private final Timer swapTimer = Timer.create();
   private boolean isUsingHotbar = false;
   private boolean isUsingInventory = false;
   private int originalSlot = -1;
   private int inventorySwapSlot = -1;
   private class_1799 originalStack = AutoToolFeature.ItemStack.EMPTY;
   private class_2338 lastBlockPos = null;
   private class_2248 lastBlock = null;
   private final EventListener<UpdateLivingEvent> onUpdate = event -> {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         class_3965 local2_4 = this.getBlockHitResult();
         boolean local3_5 = this.isMiningBlock(local2_4);
         if (local3_5) {
            class_2338 local4_0 = local2_4.method_17777();
            class_2680 local5_1 = mc.field_1687.method_8320(local4_0);
            class_2248 local6_2 = local5_1.method_26204();
            boolean local7_3 = this.lastBlock == null
               || !this.lastBlock.equals(local6_2)
               || !local4_0.equals(this.lastBlockPos)
               || !this.isCurrentToolOptimal(local6_2);
            if (local7_3) {
               this.updateToolForBlock(local6_2);
               this.lastBlock = local6_2;
               this.lastBlockPos = local4_0;
            }
         } else if (this.isUsingHotbar || this.isUsingInventory) {
            this.resetTool();
         }
      } else {
         this.resetState();
      }
   };

   public AutoToolFeature() {
      super("AutoTool", MenuCategory.PLAYER);
      this.settings(new MenuFeatureView[]{this.swap});
   }

   private class_3965 getBlockHitResult() {
      class_239 var2 = mc.field_1765;
      return var2 instanceof class_3965 ? (class_3965)var2 : null;
   }

   private boolean isMiningBlock(class_3965 hitResult) {
      return hitResult != null && mc.field_1690.field_1886.method_1434();
   }

   private boolean isCurrentToolOptimal(class_2248 block) {
      if (mc.field_1724 == null) {
         return false;
      } else {
         int local2_4 = mc.field_1724.method_31548().field_7545;
         if (local2_4 >= 0 && local2_4 <= 8) {
            float local3_5 = mc.field_1724.method_31548().method_5438(local2_4).method_7924(block.method_9564());

            for (int local4_1 = 0; local4_1 < 9; local4_1++) {
               float local5_0 = mc.field_1724.method_31548().method_5438(local4_1).method_7924(block.method_9564());
               if (local5_0 > local3_5 * 1.1F) {
                  return false;
               }
            }

            if (this.swap.get() && mc.field_1724.field_7512 != null) {
               for (class_1735 local5_3 : mc.field_1724.field_7512.field_7761) {
                  if (local5_3.field_7871 == mc.field_1724.method_31548() && local5_3.method_34266() >= 9 && !local5_3.method_7677().method_7960()) {
                     float local6_2 = local5_3.method_7677().method_7924(block.method_9564());
                     if (local6_2 > local3_5 * 1.1F) {
                        return false;
                     }
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   private void updateToolForBlock(class_2248 block) {
      if (mc.field_1724 != null) {
         this.resetTool();
         this.swapTimer.reset();
         int local2_1 = this.findBestHotbarTool(block);
         if (local2_1 != -1) {
            this.useHotbarTool(local2_1);
         } else if (this.swap.get()) {
            Integer local3_0 = this.findBestInventoryTool(block);
            if (local3_0 != null) {
               this.useInventoryTool(local3_0);
            }
         }
      }
   }

   private int findBestHotbarTool(class_2248 block) {
      int local2_2 = -1;
      float local3_3 = 1.0F;

      for (int local4_1 = 0; local4_1 < 9; local4_1++) {
         float local5_0 = mc.field_1724.method_31548().method_5438(local4_1).method_7924(block.method_9564());
         if (local5_0 > local3_3) {
            local3_3 = local5_0;
            local2_2 = local4_1;
         }
      }

      return local2_2;
   }

   private Integer findBestInventoryTool(class_2248 block) {
      if (mc.field_1724 != null && mc.field_1724.field_7512 != null) {
         Integer local2_2 = null;
         float local3_3 = 1.0F;

         for (class_1735 local5_1 : mc.field_1724.field_7512.field_7761) {
            if (local5_1.field_7871 == mc.field_1724.method_31548() && local5_1.method_34266() >= 9 && !local5_1.method_7677().method_7960()) {
               float local6_0 = local5_1.method_7677().method_7924(block.method_9564());
               if (local6_0 > local3_3) {
                  local3_3 = local6_0;
                  local2_2 = local5_1.field_7874;
               }
            }
         }

         return local2_2;
      } else {
         return null;
      }
   }

   private void useHotbarTool(int slot) {
      if (mc.field_1724 != null) {
         this.originalSlot = mc.field_1724.method_31548().field_7545;
         this.isUsingHotbar = true;
         ActionScheduler.schedule(() -> {
            if (mc.field_1724 != null && slot >= 0 && slot <= 8) {
               mc.field_1724.method_31548().field_7545 = slot;
            }
         }, 1);
      }
   }

   private void useInventoryTool(int slotId) {
      if (mc.field_1724 != null) {
         this.flushGuiMovePackets();
         int local2_0 = mc.field_1724.method_31548().field_7545;
         this.originalSlot = local2_0;
         this.isUsingInventory = true;
         this.inventorySwapSlot = slotId;
         class_1735 local3_1 = this.getSlotById(slotId);
         if (local3_1 != null && !local3_1.method_7677().method_7960()) {
            this.originalStack = local3_1.method_7677().method_7972();
         }

         ActionScheduler.schedule(() -> {
            if (mc.field_1724 != null) {
               InventoryUtil.clickSlotId(slotId, local2_0, class_1713.field_7791);
            }
         }, 1);
      }
   }

   private class_1735 getSlotById(int id) {
      if (mc.field_1724 != null && mc.field_1724.field_7512 != null) {
         for (class_1735 local3_0 : mc.field_1724.field_7512.field_7761) {
            if (local3_0.field_7874 == id) {
               return local3_0;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private void resetTool() {
      if (mc.field_1724 != null) {
         if (this.isUsingInventory && this.inventorySwapSlot != -1) {
            int local1_0 = mc.field_1724.method_31548().field_7545;
            ActionScheduler.schedule(() -> {
               if (mc.field_1724 != null) {
                  InventoryUtil.clickSlotId(this.inventorySwapSlot, local1_0, class_1713.field_7791);
               }
            }, 1);
            ActionScheduler.schedule(this::resetInventoryState, 2);
         } else if (this.isUsingHotbar && this.originalSlot != -1) {
            ActionScheduler.schedule(() -> {
               if (mc.field_1724 != null && this.originalSlot >= 0 && this.originalSlot <= 8) {
                  mc.field_1724.method_31548().field_7545 = this.originalSlot;
               }

               this.resetHotbarState();
            }, 1);
         } else {
            this.resetState();
         }
      }
   }

   private void resetHotbarState() {
      this.isUsingHotbar = false;
      this.originalSlot = -1;
   }

   private void resetInventoryState() {
      this.isUsingInventory = false;
      this.inventorySwapSlot = -1;
      this.originalSlot = -1;
      this.originalStack = AutoToolFeature.ItemStack.EMPTY;
   }

   private void resetState() {
      this.resetHotbarState();
      this.resetInventoryState();
      this.lastBlock = null;
      this.lastBlockPos = null;
   }

   private void flushGuiMovePackets() {
      GuiMoveFeature local1_0 = Melonity.INSTANCE.getFeatureManager().guiMoveFeature;
      if (local1_0 != null) {
         local1_0.flushQueuedPackets();
      }
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      if (!value) {
         this.resetState();
      }
   }

   @Environment(EnvType.CLIENT)
   private static class ItemStack {
      public static final class_1799 EMPTY = new AutoToolFeature.ItemStack().copy();

      public boolean isEmpty() {
         return true;
      }

      public class_1799 copy() {
         return EMPTY;
      }
   }
}
