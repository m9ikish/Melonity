package ru.melonity.feature.features.modules.misc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1707;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_2561;
import net.minecraft.class_3544;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_476;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeaturePrefix;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.minecraft.PlayerUtil;

@Environment(EnvType.CLIENT)
public class AuctionHelperFeature extends SimpleFeature {
   private final BooleanSetting uchityvatBalance = new BooleanSetting("auctionhelper.balance", false);
   private final BooleanSetting checkDurability = new BooleanSetting("auctionhelper.check_durability", false);
   private final BooleanSetting checkEnchants = new BooleanSetting("auctionhelper.check_enchants", false);
   private final BooleanSetting checkItem = new BooleanSetting("auctionhelper.filter", false);
   private int[] cheapestSlotIDs = new int[]{-1, -1, -1};
   public final EventListener<UpdateEvent> onUpdate = event -> {
      if (mc.field_1755 != null) {
         if (mc.field_1755 instanceof class_476 local2_7
            && (
               class_3544.method_15440(mc.field_1755.method_25440().getString()).toLowerCase().contains("аукцион")
                  || class_3544.method_15440(mc.field_1755.method_25440().getString()).toLowerCase().contains("поиск")
            )) {
            List<AuctionHelperFeature.SlotPrice> local3_5 = new ArrayList<>();
            int local4_6 = this.uchityvatBalance.get() ? PlayerUtil.getBalance() : Integer.MAX_VALUE;

            for (class_1735 local6_3 : ((class_1707)local2_7.method_17577()).field_7761.subList(0, 44)) {
               class_1799 local7_1 = local6_3.method_7677();
               int local8_2 = price(local7_1);
               if (local8_2 > 0 && local8_2 <= local4_6 && this.meetsCriteria(local7_1)) {
                  local3_5.add(new AuctionHelperFeature.SlotPrice(local6_3.field_7874, local8_2));
               }
            }

            local3_5.sort(Comparator.comparingInt(sp -> sp.price));
            this.cheapestSlotIDs = new int[]{-1, -1, -1};

            for (int local5_4 = 0; local5_4 < Math.min(3, local3_5.size()); local5_4++) {
               this.cheapestSlotIDs[local5_4] = local3_5.get(local5_4).slotID;
            }
         }
      } else {
         this.cheapestSlotIDs = new int[]{-1, -1, -1};
      }
   };

   public AuctionHelperFeature() {
      super("AuctionHelper", MenuCategory.MISC);
      this.prefix(new MenuFeaturePrefix[]{MenuFeaturePrefix.Funtime, MenuFeaturePrefix.SpookyTime});
      this.settings(new MenuFeatureView[]{this.uchityvatBalance, this.checkDurability, this.checkEnchants});
   }

   private boolean meetsCriteria(class_1799 stack) {
      if (stack.method_7960()) {
         return false;
      } else {
         return this.checkDurability.get() && stack.method_7963() && stack.method_7919() == 0
            ? false
            : !this.checkEnchants.get() || !stack.method_58657().method_57543();
      }
   }

   public void auctionSlotHighlight(class_1799 stack, float x, float y, float size, float radius, class_4587 matrices, Color color) {
      if (!stack.method_7960()) {
         Melonity.INSTANCE.getRenderSystem().addRect(x, y, size, size, radius, color, matrices);
      }
   }

   public static int price(class_1799 stack) {
      List<class_2561> local1_2 = class_437.method_25408(mc, stack);

      // getTooltip returns an EMPTY list for any stack carrying minecraft:hide_tooltip,
      // and Math.min(1, 0 - 1) is -1, so one decorated filler item threw
      // IndexOutOfBoundsException and killed the whole scan.
      for (class_2561 local3_1 : local1_2.subList(Math.max(0, Math.min(1, local1_2.size() - 1)), local1_2.size())) {
         String local4_0 = class_3544.method_15440(local3_1.getString()).toLowerCase();
         if (local4_0.contains("$")) {
            return Integer.parseInt(local4_0.replaceAll("\\D", ""));
         }
      }

      return -1;
   }

   @Generated
   public BooleanSetting getUchityvatBalance() {
      return this.uchityvatBalance;
   }

   @Generated
   public BooleanSetting getCheckDurability() {
      return this.checkDurability;
   }

   @Generated
   public BooleanSetting getCheckEnchants() {
      return this.checkEnchants;
   }

   @Generated
   public BooleanSetting getCheckItem() {
      return this.checkItem;
   }

   @Generated
   public int[] getCheapestSlotIDs() {
      return this.cheapestSlotIDs;
   }

   @Generated
   public EventListener<UpdateEvent> getOnUpdate() {
      return this.onUpdate;
   }

   @Environment(EnvType.CLIENT)
   private static class SlotPrice {
      int slotID;
      int price;

      SlotPrice(int slotID, int price) {
         this.slotID = slotID;
         this.price = price;
      }
   }
}
