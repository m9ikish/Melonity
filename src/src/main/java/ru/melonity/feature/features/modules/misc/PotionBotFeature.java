package ru.melonity.feature.features.modules.misc;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1707;
import net.minecraft.class_1708;
import net.minecraft.class_1713;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;
import net.minecraft.class_2828.class_2831;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import ru.melonity.fabric.client.model.IBrewingStandScreenHandler;
import ru.melonity.feature.ActionScheduler;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.MiddleClickEvent;

@Environment(EnvType.CLIENT)
public class PotionBotFeature extends SimpleFeature {
   private final BooleanSetting splashPotion = new BooleanSetting("global.splash", true);
   private final ListSetting mode = new ListSetting(
      "global.mode",
      ListSetting.ModeType.SINGLE,
      "Сила I",
      "Сила I",
      "Сила II",
      "Скорость I",
      "Скорость II",
      "Огнестойкость I",
      "Огнестойкость II",
      "Невидимость I",
      "Невидимость II"
   );
   private class_2338 chestPos = null;
   private class_2338 brewingStandPos = null;
   private final PotionBotFeature.PotionBotRunnable runnable = new PotionBotFeature.PotionBotRunnable(this);
   private final EventListener<MiddleClickEvent> onMiddleClick = event -> {
      if (mc.field_1765 != null && mc.field_1765.method_17783() == class_240.field_1332) {
         class_3965 local2_0 = (class_3965)mc.field_1765;
         class_2338 local3_1 = local2_0.method_17777();
         class_2248 local4_2 = mc.field_1687.method_8320(local3_1).method_26204();
         if (this.chestPos == null && local4_2 == class_2246.field_10034) {
            mc.field_1705.method_1743().method_1812(class_2561.method_43470("Позиция сундука установлена"));
            this.chestPos = local3_1;
         }

         if (this.brewingStandPos == null && local4_2 == class_2246.field_10333) {
            mc.field_1705.method_1743().method_1812(class_2561.method_43470("Позиция зельеварки установлена"));
            this.brewingStandPos = local3_1;
         }
      }
   };

   public PotionBotFeature() {
      super("PotionBot", MenuCategory.MISC);
      this.setting(this.splashPotion);
      this.setting(this.mode);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      ActionScheduler.schedule(this.runnable::tick, 10);
   }

   @Environment(EnvType.CLIENT)
   private static class PotionBotRunnable {
      private final PotionBotFeature feature;
      private PotionBotFeature.PotionBotRunnable.BrewingStep currentStep = PotionBotFeature.PotionBotRunnable.BrewingStep.NONE;
      private int brewCheckTicks = 0;
      private int currentBrewingStage = 0;
      private static final int SLOT_BOTTLE_1 = 0;
      private static final int SLOT_BOTTLE_2 = 1;
      private static final int SLOT_BOTTLE_3 = 2;
      private static final int SLOT_FUEL = 4;

      public PotionBotRunnable(PotionBotFeature feature) {
         this.feature = feature;
      }

      public void tick() {
         class_310 local1_9 = class_310.method_1551();
         if (this.feature == null || !this.feature.isEnabled()) {
            this.currentStep = PotionBotFeature.PotionBotRunnable.BrewingStep.NONE;
         } else if (this.feature.chestPos == null) {
            local1_9.field_1705.method_1743().method_1812(class_2561.method_43470("Установите позицию сундука, нажав колёсиком мыши на сундук"));
            ActionScheduler.schedule(() -> this.tick(), 100);
         } else if (this.feature.brewingStandPos == null) {
            local1_9.field_1705.method_1743().method_1812(class_2561.method_43470("Установите позицию зельеварки, нажав колёсиком мыши на зельеварку"));
            ActionScheduler.schedule(() -> this.tick(), 100);
         } else {
            switch (this.currentStep) {
               case NONE:
                  this.currentStep = PotionBotFeature.PotionBotRunnable.BrewingStep.GET_INGREDIENTS;
                  ActionScheduler.schedule(() -> this.tick(), 10);
                  break;
               case GET_INGREDIENTS:
                  if (local1_9.field_1724.field_7512 instanceof class_1707 local2_0) {
                     this.takeRequiredItemsFromChest(local1_9, local2_0, this.feature);
                     RenderSystem.recordRenderCall(() -> local1_9.field_1724.method_7346());
                     this.currentStep = PotionBotFeature.PotionBotRunnable.BrewingStep.OPEN_BREWING;
                     this.currentBrewingStage = 0;
                     ActionScheduler.schedule(() -> this.tick(), 10);
                  } else {
                     this.openBlock(local1_9, this.feature.chestPos);
                     ActionScheduler.schedule(() -> this.tick(), 10);
                  }
                  break;
               case OPEN_BREWING:
                  if (!(local1_9.field_1724.field_7512 instanceof class_1708)) {
                     this.openBlock(local1_9, this.feature.brewingStandPos);
                     ActionScheduler.schedule(() -> this.tick(), 10);
                  } else {
                     this.currentStep = PotionBotFeature.PotionBotRunnable.BrewingStep.PUT_INGREDIENTS;
                     ActionScheduler.schedule(() -> this.tick(), 5);
                  }
                  break;
               case PUT_INGREDIENTS:
                  if (local1_9.field_1724.field_7512 instanceof class_1708 local2_2) {
                     if (this.currentBrewingStage == 0) {
                        this.setupBottlesAndFuel(local1_9, local2_2);
                     }

                     this.putNextIngredient(local1_9, local2_2);
                     this.currentStep = PotionBotFeature.PotionBotRunnable.BrewingStep.WAITING_FOR_BREW;
                     this.brewCheckTicks = 0;
                     ActionScheduler.schedule(() -> this.tick(), 5);
                  } else {
                     this.openBlock(local1_9, this.feature.brewingStandPos);
                     ActionScheduler.schedule(() -> this.tick(), 10);
                  }
                  break;
               case PUT_POTIONS_IN_CHEST:
                  if (local1_9.field_1724.field_7512 instanceof class_1707 local2_7) {
                     this.putPotionsInChest(local1_9, local2_7);
                     RenderSystem.recordRenderCall(() -> local1_9.field_1724.method_7346());
                     this.currentStep = PotionBotFeature.PotionBotRunnable.BrewingStep.CHECK_CONTINUE;
                     ActionScheduler.schedule(() -> this.tick(), 10);
                  } else {
                     this.openBlock(local1_9, this.feature.chestPos);
                     ActionScheduler.schedule(() -> this.tick(), 10);
                  }
                  break;
               case CHECK_CONTINUE:
                  if (this.hasEnoughIngredients(local1_9, this.feature)) {
                     local1_9.field_1705.method_1743().method_1812(class_2561.method_43470("Продолжаем готовить зелья..."));
                     this.currentStep = PotionBotFeature.PotionBotRunnable.BrewingStep.GET_INGREDIENTS;
                  } else {
                     local1_9.field_1705.method_1743().method_1812(class_2561.method_43470("Нужно больше ингредиентов, получаем из сундука..."));
                     this.currentStep = PotionBotFeature.PotionBotRunnable.BrewingStep.GET_INGREDIENTS;
                  }

                  ActionScheduler.schedule(this::tick, 10);
                  break;
               case WAITING_FOR_BREW:
                  if (!(local1_9.field_1724.field_7512 instanceof class_1708 local2_3)) {
                     this.currentStep = PotionBotFeature.PotionBotRunnable.BrewingStep.PUT_INGREDIENTS;
                     ActionScheduler.schedule(() -> this.tick(), 10);
                     return;
                  }

                  IBrewingStandScreenHandler local3_5 = (IBrewingStandScreenHandler)local2_3;
                  int local4_6 = local3_5.propertyDelegate().method_17390(0);
                  if (this.brewCheckTicks == 0 && local4_6 == 0) {
                     ActionScheduler.schedule(() -> this.tick(), 5);
                     this.brewCheckTicks++;
                  } else if (local4_6 > 0) {
                     ActionScheduler.schedule(() -> this.tick(), 5);
                     this.brewCheckTicks++;
                  } else if (this.brewCheckTicks > 0 && local4_6 == 0) {
                     this.currentBrewingStage++;
                     if (this.isBrewingComplete()) {
                        this.takeAllPotionsFromBrewingStand(local1_9, local2_3);
                        RenderSystem.recordRenderCall(() -> local1_9.field_1724.method_7346());
                        this.currentStep = PotionBotFeature.PotionBotRunnable.BrewingStep.PUT_POTIONS_IN_CHEST;
                        ActionScheduler.schedule(() -> this.tick(), 10);
                     } else {
                        this.currentStep = PotionBotFeature.PotionBotRunnable.BrewingStep.PUT_INGREDIENTS;
                        ActionScheduler.schedule(() -> this.tick(), 5);
                     }
                  }
            }
         }
      }

      private boolean isBrewingComplete() {
         int local1_0 = this.getTotalBrewingStages();
         return this.currentBrewingStage >= local1_0;
      }

      private int getTotalBrewingStages() {
         int local1_0 = 2;
         if (this.feature.mode.contains("II")) {
            local1_0++;
         }

         if (this.feature.splashPotion.get()) {
            local1_0++;
         }

         return local1_0;
      }

      private void putNextIngredient(class_310 mc, class_1708 container) {
         switch (this.currentBrewingStage) {
            case 0:
               this.putItemInSlot(mc, container, class_1802.field_8790, 3);
               break;
            case 1:
               if (this.feature.mode.contains("Сила I") || this.feature.mode.contains("Сила II")) {
                  this.putItemInSlot(mc, container, class_1802.field_8183, 3);
               } else if (this.feature.mode.contains("Скорость I") || this.feature.mode.contains("Скорость II")) {
                  this.putItemInSlot(mc, container, class_1802.field_8479, 3);
               } else if (this.feature.mode.contains("Огнестойкость I") || this.feature.mode.contains("Огнестойкость II")) {
                  this.putItemInSlot(mc, container, class_1802.field_8135, 3);
               } else if (this.feature.mode.contains("Невидимость I") || this.feature.mode.contains("Невидимость II")) {
                  this.putItemInSlot(mc, container, class_1802.field_8071, 3);
               }
               break;
            case 2:
               if (this.feature.mode.contains("Невидимость I") || this.feature.mode.contains("Невидимость II")) {
                  this.putItemInSlot(mc, container, class_1802.field_8711, 3);
               } else if (this.feature.mode.contains("II")) {
                  if (this.feature.mode.contains("Сила II") || this.feature.mode.contains("Скорость II")) {
                     this.putItemInSlot(mc, container, class_1802.field_8601, 3);
                  } else if (this.feature.mode.contains("Огнестойкость II") || this.feature.mode.contains("Невидимость II")) {
                     this.putItemInSlot(mc, container, class_1802.field_8725, 3);
                  }
               } else if (this.feature.splashPotion.get()) {
                  this.putItemInSlot(mc, container, class_1802.field_8054, 3);
               }
               break;
            case 3:
               if (this.feature.mode.contains("Невидимость II")) {
                  this.putItemInSlot(mc, container, class_1802.field_8725, 3);
               } else if (this.feature.splashPotion.get()) {
                  this.putItemInSlot(mc, container, class_1802.field_8054, 3);
               }
               break;
            case 4:
               if (this.feature.splashPotion.get()) {
                  this.putItemInSlot(mc, container, class_1802.field_8054, 3);
               }
         }
      }

      private void setupBottlesAndFuel(class_310 mc, class_1708 container) {
         this.putItemInSlot(mc, container, class_1802.field_8574, 0);
         this.putItemInSlot(mc, container, class_1802.field_8574, 1);
         this.putItemInSlot(mc, container, class_1802.field_8574, 2);
         if (container.method_7611(4).method_7677().method_7960()) {
            this.putOneItemInSlot(mc, container, class_1802.field_8183, 4);
         }
      }

      private void putPotionsInChest(class_310 mc, class_1707 container) {
         for (int local3_1 = 9; local3_1 < 45; local3_1++) {
            if (mc.field_1724.method_31548().method_5438(local3_1 - 9).method_7909().toString().contains("potion")) {
               int local4_0 = this.findEmptySlotInContainer(container);
               if (local4_0 != -1) {
                  mc.field_1761.method_2906(container.field_7763, local3_1 + container.field_7761.size() - 45, 0, class_1713.field_7794, mc.field_1724);
               }
            }
         }
      }

      private int findEmptySlotInContainer(class_1707 container) {
         for (int local2_0 = 0; local2_0 < container.field_7761.size() - 36; local2_0++) {
            if (!container.method_7611(local2_0).method_7681()) {
               return local2_0;
            }
         }

         return -1;
      }

      private boolean hasEnoughIngredients(class_310 mc, PotionBotFeature feature) {
         int local3_9 = this.countItemInInventory(mc, class_1802.field_8574);
         int local4_10 = this.countItemInInventory(mc, class_1802.field_8790);
         int local5_11 = this.countItemInInventory(mc, class_1802.field_8183);
         boolean local6_12 = local3_9 >= 3 && local4_10 >= 3 && local5_11 >= 1;
         if (!local6_12) {
            return false;
         } else if (feature.mode.contains("Сила I") || feature.mode.contains("Сила II")) {
            int local7_0 = 3;
            int local8_1 = feature.mode.contains("Сила II") ? 3 : 0;
            return local5_11 >= local7_0 && (local8_1 == 0 || this.countItemInInventory(mc, class_1802.field_8601) >= local8_1);
         } else if (feature.mode.contains("Скорость I") || feature.mode.contains("Скорость II")) {
            int local7_2 = 3;
            int local8_3 = feature.mode.contains("Скорость II") ? 3 : 0;
            return this.countItemInInventory(mc, class_1802.field_8479) >= local7_2
               && (local8_3 == 0 || this.countItemInInventory(mc, class_1802.field_8601) >= local8_3);
         } else if (feature.mode.contains("Огнестойкость I") || feature.mode.contains("Огнестойкость II")) {
            int local7_4 = 3;
            int local8_5 = feature.mode.contains("Огнестойкость II") ? 3 : 0;
            return this.countItemInInventory(mc, class_1802.field_8135) >= local7_4
               && (local8_5 == 0 || this.countItemInInventory(mc, class_1802.field_8725) >= local8_5);
         } else if (feature.mode.contains("Невидимость I") || feature.mode.contains("Невидимость II")) {
            int local7_6 = 3;
            int local8_7 = 3;
            int local9_8 = feature.mode.contains("Невидимость II") ? 3 : 0;
            return this.countItemInInventory(mc, class_1802.field_8071) >= local7_6
               && this.countItemInInventory(mc, class_1802.field_8711) >= local8_7
               && (local9_8 == 0 || this.countItemInInventory(mc, class_1802.field_8725) >= local9_8);
         } else {
            return feature.splashPotion.get() ? this.countItemInInventory(mc, class_1802.field_8054) >= 3 : true;
         }
      }

      private int countItemInInventory(class_310 mc, class_1792 item) {
         int local3_1 = 0;

         for (int local4_0 = 0; local4_0 < mc.field_1724.method_31548().method_5439(); local4_0++) {
            if (mc.field_1724.method_31548().method_5438(local4_0).method_7909() == item) {
               local3_1 += mc.field_1724.method_31548().method_5438(local4_0).method_7947();
            }
         }

         return local3_1;
      }

      private void openBlock(class_310 mc, class_2338 pos) {
         class_243 local3_0 = new class_243(
            mc.field_1724.method_23317(),
            mc.field_1724.method_23318() + (double)mc.field_1724.method_18381(mc.field_1724.method_18376()),
            mc.field_1724.method_23321()
         );
         class_243 local4_1 = pos.method_46558();
         class_3959 local5_2 = new class_3959(local3_0, local4_1, class_3960.field_17558, class_242.field_1348, mc.field_1724);
         class_3965 local6_3 = mc.field_1687.method_17742(local5_2);
         float local7_4 = this.getYawToLook(local3_0, local4_1);
         float local8_5 = this.getPitchToLook(local3_0, local4_1);
         mc.field_1724.field_3944.method_52787(new class_2831(local7_4, local8_5, mc.field_1724.method_24828()));
         mc.field_1761.method_2896(mc.field_1724, class_1268.field_5808, local6_3);
         mc.field_1724.method_6104(class_1268.field_5808);
      }

      private void takeRequiredItemsFromChest(class_310 mc, class_1707 container, PotionBotFeature feature) {
         this.takeMultipleItems(mc, container, class_1802.field_8574, 3);
         this.takeMultipleItems(mc, container, class_1802.field_8790, 3);
         this.takeMultipleItems(mc, container, class_1802.field_8183, 4);
         if (feature.mode.contains("Сила I") || feature.mode.contains("Сила II")) {
            this.takeMultipleItems(mc, container, class_1802.field_8183, 3);
            if (feature.mode.contains("Сила II")) {
               this.takeMultipleItems(mc, container, class_1802.field_8601, 3);
            }
         } else if (feature.mode.contains("Скорость I") || feature.mode.contains("Скорость II")) {
            this.takeMultipleItems(mc, container, class_1802.field_8479, 3);
            if (feature.mode.contains("Скорость II")) {
               this.takeMultipleItems(mc, container, class_1802.field_8601, 3);
            }
         } else if (feature.mode.contains("Огнестойкость I") || feature.mode.contains("Огнестойкость II")) {
            this.takeMultipleItems(mc, container, class_1802.field_8135, 3);
            if (feature.mode.contains("Огнестойкость II")) {
               this.takeMultipleItems(mc, container, class_1802.field_8725, 3);
            }
         } else if (feature.mode.contains("Невидимость I") || feature.mode.contains("Невидимость II")) {
            this.takeMultipleItems(mc, container, class_1802.field_8071, 3);
            this.takeMultipleItems(mc, container, class_1802.field_8711, 3);
            if (feature.mode.contains("Невидимость II")) {
               this.takeMultipleItems(mc, container, class_1802.field_8725, 3);
            }
         }

         if (feature.splashPotion.get()) {
            this.takeMultipleItems(mc, container, class_1802.field_8054, 3);
         }
      }

      private void takeMultipleItems(class_310 mc, class_1707 container, class_1792 item, int needed) {
         int local5_1 = 0;

         for (int local6_0 = 0; local6_0 < container.field_7761.size() - 36 && local5_1 < needed; local6_0++) {
            if (container.method_7611(local6_0).method_7681() && container.method_7611(local6_0).method_7677().method_7909() == item) {
               mc.field_1761.method_2906(container.field_7763, local6_0, 0, class_1713.field_7794, mc.field_1724);
               local5_1 += container.method_7611(local6_0).method_7677().method_7947();
            }
         }
      }

      private void takeAllPotionsFromBrewingStand(class_310 mc, class_1708 container) {
         for (int local3_0 = 0; local3_0 <= 2; local3_0++) {
            if (container.method_7611(local3_0).method_7681()) {
               mc.field_1761.method_2906(container.field_7763, local3_0, 0, class_1713.field_7794, mc.field_1724);
            }
         }
      }

      private void putOneItemInSlot(class_310 mc, class_1708 container, class_1792 item, int brewingSlotIndex) {
         int local5_0 = this.findItemSlotInPlayerInventory(container, item);
         if (local5_0 != -1) {
            mc.field_1761.method_2906(container.field_7763, local5_0, 0, class_1713.field_7790, mc.field_1724);
            mc.field_1761.method_2906(container.field_7763, brewingSlotIndex, 1, class_1713.field_7790, mc.field_1724);
            mc.field_1761.method_2906(container.field_7763, local5_0, 0, class_1713.field_7790, mc.field_1724);
         }
      }

      private void putItemInSlot(class_310 mc, class_1708 container, class_1792 item, int brewingSlotIndex) {
         if (item != null) {
            int local5_0 = this.findItemSlotInPlayerInventory(container, item);
            if (local5_0 != -1) {
               mc.field_1761.method_2906(container.field_7763, local5_0, 0, class_1713.field_7790, mc.field_1724);
               mc.field_1761.method_2906(container.field_7763, brewingSlotIndex, 0, class_1713.field_7790, mc.field_1724);
            }
         }
      }

      private int findItemSlotInPlayerInventory(class_1708 container, class_1792 item) {
         for (int local3_0 = 5; local3_0 < container.field_7761.size(); local3_0++) {
            if (container.method_7611(local3_0).method_7681() && container.method_7611(local3_0).method_7677().method_7909() == item) {
               return local3_0;
            }
         }

         return -1;
      }

      private float getYawToLook(class_243 from, class_243 to) {
         double local3_0 = to.field_1352 - from.field_1352;
         double local5_1 = to.field_1350 - from.field_1350;
         return (float)class_3532.method_15338(Math.toDegrees(Math.atan2(local5_1, local3_0)) - 90.0);
      }

      private float getPitchToLook(class_243 from, class_243 to) {
         double local3_0 = to.field_1352 - from.field_1352;
         double local5_1 = to.field_1351 - from.field_1351;
         double local7_2 = to.field_1350 - from.field_1350;
         double local9_3 = Math.sqrt(local3_0 * local3_0 + local7_2 * local7_2);
         return (float)(-Math.toDegrees(Math.atan2(local5_1, local9_3)));
      }

      @Environment(EnvType.CLIENT)
      private static enum BrewingStep {
         NONE,
         GET_INGREDIENTS,
         OPEN_BREWING,
         PUT_INGREDIENTS,
         PUT_POTIONS_IN_CHEST,
         CHECK_CONTINUE,
         WAITING_FOR_BREW;
      }
   }
}
