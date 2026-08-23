package ru.melonity.feature.features.modules.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1263;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_1738;
import net.minecraft.class_1744;
import net.minecraft.class_1776;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1812;
import net.minecraft.class_1829;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.time.Timer;

@Environment(EnvType.CLIENT)
public class ChestStealerFeature extends SimpleFeature {
   private final NumberSetting delay = new NumberSetting("global.delay", value -> String.format("%d", value.intValue()), 1, 1000, 100);
   private final BooleanSetting filterItems = new BooleanSetting("cheststealer.filter", false);
   private final BooleanSetting closeScreenSetting = new BooleanSetting("cheststealer.closescreen", false);
   private final Timer stopWatch = Timer.create();
   private final List<class_1792> ingotItemList = List.of(
      class_1802.field_8620, class_1802.field_8695, class_1802.field_22020, class_1802.field_8477, class_1802.field_22021
   );
   private Set<Integer> processedSlots = new HashSet<>();
   private int containerSyncId = -1;
   private final EventListener<UpdateLivingEvent> onUpdate = event -> {
      if (mc.field_1724.field_7512 instanceof class_1707 local2_7) {
         if (local2_7.field_7763 != this.containerSyncId) {
            this.containerSyncId = local2_7.field_7763;
            this.processedSlots.clear();
         }

         class_1263 local3_4 = local2_7.method_7629();
         boolean local4_5 = false;
         List<Integer> local5_6 = new ArrayList<>();

         for (int local6_2 = 0; local6_2 < local3_4.method_5439(); local6_2++) {
            if (!this.processedSlots.contains(local6_2)) {
               class_1799 local7_1 = local3_4.method_5438(local6_2);
               if (this.shouldMoveItem(local3_4, local6_2) && (!this.filterItems.get() || this.isWhiteListItem(local7_1))) {
                  local5_6.add(local6_2);
                  local4_5 = true;
               }
            }
         }

         Collections.shuffle(local5_6);
         if (!local5_6.isEmpty()) {
            int local6_3 = local5_6.get(0);
            if (this.delay.getValue().floatValue() == 0.0F) {
               this.moveItem(local2_7, local6_3, 1);
               this.processedSlots.add(local6_3);
            } else if (this.stopWatch.hasReached(this.delay.getValue().longValue())) {
               mc.field_1761.method_2906(local2_7.field_7763, local6_3, 0, class_1713.field_7794, mc.field_1724);
               this.stopWatch.reset();
               this.processedSlots.add(local6_3);
            }
         }

         if (this.closeScreenSetting.get() && !local4_5 && this.isContainerEmpty(local3_4)) {
            mc.field_1724.method_7346();
            this.containerSyncId = -1;
            this.processedSlots.clear();
         }
      } else {
         this.containerSyncId = -1;
         this.processedSlots.clear();
      }
   };

   public ChestStealerFeature() {
      super("ChestStealer", MenuCategory.PLAYER);
      this.settings(new MenuFeatureView[]{this.delay, this.filterItems, this.closeScreenSetting});
   }

   private boolean shouldMoveItem(class_1263 container, int index) {
      class_1799 local3_0 = container.method_5438(index);
      return !local3_0.method_7960();
   }

   private void moveItem(class_1707 container, int index, int count) {
      mc.field_1761.method_2906(container.field_7763, index, 0, class_1713.field_7794, mc.field_1724);
   }

   private boolean isWhiteListItem(class_1799 itemStack) {
      class_1792 local2_0 = itemStack.method_7909();
      return itemStack.method_7942()
         || this.ingotItemList.contains(local2_0)
         || local2_0 == class_1802.field_8575
         || local2_0 instanceof class_1738
         || local2_0 instanceof class_1776
         || local2_0 instanceof class_1829
         || local2_0 instanceof class_1812
         || local2_0 instanceof class_1744;
   }

   private boolean isContainerEmpty(class_1263 inventory) {
      for (int local2_0 = 0; local2_0 < inventory.method_5439(); local2_0++) {
         if (!inventory.method_5438(local2_0).method_7960()) {
            return false;
         }
      }

      return true;
   }
}
