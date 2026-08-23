package ru.melonity.feature.features.modules.misc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1263;
import net.minecraft.class_1293;
import net.minecraft.class_1703;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_1842;
import net.minecraft.class_1844;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import net.minecraft.class_3544;
import net.minecraft.class_476;
import net.minecraft.class_6880;
import net.minecraft.class_9304;
import net.minecraft.class_9334;
import ru.melonity.fabric.client.model.IItemStack;
import ru.melonity.feature.features.modules.misc.autobuy.AutobuyScreen;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PreMotionEvent;
import ru.melonity.util.minecraft.PlayerUtil;

@Environment(EnvType.CLIENT)
public class AutoBuyFeature extends SimpleFeature {
   private int nextSlot = -1;
   private int clicks = 0;
   private long delay = -1L;
   private boolean buy = false;
   private class_1703 container = null;
   private AutobuyScreen screen = new AutobuyScreen();
   private final NumberSetting delaySetting = new NumberSetting("global.delay", value -> String.format("%d", value.intValue()), 0, 3000, 200);
   private final CopyOnWriteArrayList<AutoBuyFeature.ItemPrice> prices = Lists.newCopyOnWriteArrayList();
   public static boolean scan = false;
   public static boolean wasScanned = false;
   private int scanI = 0;
   public static boolean enabled = false;
   public static Map<class_1799, Integer> valid = Maps.newLinkedHashMap();
   private static int nextArrowSlot = -1;
   private static int nexttArrowSlot = -1;
   private long last = System.currentTimeMillis();
   private final EventListener<PreMotionEvent> onPreMotion = this::preMotionProceed;

   public AutoBuyFeature() {
      super("AutoBuy", MenuCategory.MISC);
      this.setting(this.delaySetting);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      if (value) {
         mc.method_1507(this.screen);
         enabled = false;
      }
   }

   private void preMotionProceed(PreMotionEvent event) {
      if (mc.field_1724 == null || mc.field_1687 == null || mc.field_1761 == null) {
         return;
      }

      if (System.currentTimeMillis() - this.last < this.delaySetting.getValue().longValue()) {
         return;
      }

      // Outside of the auction GUI the only thing left to do is to hand the items the user
      // flagged as "autosell" over to the server side /ah sell command.
      if (!(mc.field_1755 instanceof class_476)) {
         this.container = null;
         this.nextSlot = -1;
         this.buy = false;
         nextArrowSlot = -1;
         nexttArrowSlot = -1;
         if (!enabled) {
            return;
         }

         for (Entry<class_1799, Integer> wanted : valid.entrySet()) {
            class_1799 template = wanted.getKey();
            if (!((IItemStack)(Object)template).autosell()) {
               continue;
            }

            int selectedSlot = mc.field_1724.method_31548().field_7545;
            class_1799 held = mc.field_1724.method_31548().method_5438(selectedSlot);
            if (!held.method_7960() && held.method_7909().equals(template.method_7909()) && this.checkSellItem(held)) {
               PlayerUtil.sendPlayerMessage("/ah sell " + wanted.getValue());
               this.last = System.currentTimeMillis();
               return;
            }

            for (int inventorySlot = 0; inventorySlot < 36; inventorySlot++) {
               class_1799 stored = mc.field_1724.method_31548().method_5438(inventorySlot);
               if (!stored.method_7960() && stored.method_7909().equals(template.method_7909()) && this.checkSellItem(stored)) {
                  mc.field_1761
                     .method_2906(0, inventorySlot < 9 ? inventorySlot + 36 : inventorySlot, selectedSlot, class_1713.field_7791, mc.field_1724);
                  this.last = System.currentTimeMillis();
                  return;
               }
            }
         }

         return;
      }

      class_476 auctionScreen = (class_476)mc.field_1755;
      String title = class_3544.method_15440(auctionScreen.method_25440().getString()).toLowerCase();
      if (!title.contains("аукцион") && !title.contains("поиск")) {
         return;
      }

      class_1707 handler = (class_1707)auctionScreen.method_17577();
      this.container = handler;
      class_1263 containerInventory = handler.method_7629();
      nextArrowSlot = this.findNextArrow(containerInventory);
      nexttArrowSlot = this.findNexttArrow(containerInventory);
      int listingSlots = Math.min(44, handler.field_7761.size());

      // Price census: walk every auction page once and remember what each listing costs, so
      // that templates flagged with "best price" have something to compare against later.
      if (scan) {
         if (this.scanI == 0) {
            this.prices.clear();
         }

         for (class_1735 slot : handler.field_7761.subList(0, listingSlots)) {
            class_1799 listed = slot.method_7677();
            if (listed.method_7960()) {
               continue;
            }

            int listedPrice = AuctionHelperFeature.price(listed);
            if (listedPrice > 0) {
               this.prices.add(new AutoBuyFeature.ItemPrice(listed.method_7972(), listedPrice));
            }
         }

         this.scanI++;
         this.last = System.currentTimeMillis();
         if (nexttArrowSlot != -1 && this.scanI < 64) {
            mc.field_1761.method_2906(handler.field_7763, nexttArrowSlot, 0, class_1713.field_7790, mc.field_1724);
            return;
         }

         scan = false;
         wasScanned = true;
         this.scanI = 0;
         int previousPageSlot = this.findPrevArrow(containerInventory);
         if (previousPageSlot != -1) {
            mc.field_1761.method_2906(handler.field_7763, previousPageSlot, 0, class_1713.field_7790, mc.field_1724);
         }

         return;
      }

      if (!enabled || valid.isEmpty()) {
         return;
      }

      int balance = PlayerUtil.getBalance();
      if (balance < 0) {
         balance = Integer.MAX_VALUE;
      }

      this.nextSlot = -1;
      this.buy = false;
      int cheapestPrice = Integer.MAX_VALUE;

      for (class_1735 slot : handler.field_7761.subList(0, listingSlots)) {
         class_1799 listed = slot.method_7677();
         if (listed.method_7960()) {
            continue;
         }

         int listedPrice = AuctionHelperFeature.price(listed);
         if (listedPrice > 0 && listedPrice < cheapestPrice && this.checkItem(listed, listedPrice, balance)) {
            cheapestPrice = listedPrice;
            this.nextSlot = slot.field_7874;
            this.buy = true;
         }
      }

      this.last = System.currentTimeMillis();
      if (this.buy && this.nextSlot != -1) {
         mc.field_1761.method_2906(handler.field_7763, this.nextSlot, 0, class_1713.field_7790, mc.field_1724);
         this.clicks++;
         this.buy = false;
         this.nextSlot = -1;
      } else if (nextArrowSlot != -1) {
         mc.field_1761.method_2906(handler.field_7763, nextArrowSlot, 0, class_1713.field_7790, mc.field_1724);
      }
   }

   private int getBestPrice(class_1799 stack) {
      int local2_2 = -1;

      for (AutoBuyFeature.ItemPrice local4_1 : this.prices) {
         class_1799 local5_0 = local4_1.item();
         if ((
               local5_0.method_57824(class_9334.field_49631) == null
                  || stack.method_7964().getString().toLowerCase().contains(local5_0.method_7964().getString().toLowerCase())
            )
            && (local5_0.method_7947() <= 1 || stack.method_7947() == local5_0.method_7947())
            && (local2_2 == -1 || stack.method_7909() == local4_1.item().method_7909() && local4_1.price < local2_2)) {
            local2_2 = local4_1.price;
         }
      }

      return local2_2;
   }

   private boolean checkSellItem(class_1799 stack) {
      for (Entry<class_1799, Integer> local3_5 : valid.entrySet()) {
         class_1799 local4_3 = local3_5.getKey();
         if (!local4_3.method_7909().equals(stack.method_7909())) {
            return false;
         }

         if (local4_3.method_57824(class_9334.field_49631) != null
            && !stack.method_7964().getString().toLowerCase().contains(local4_3.method_7964().getString().toLowerCase())) {
            return false;
         }

         if (local4_3.method_7947() > 1 && stack.method_7947() != local4_3.method_7947()) {
            return false;
         }

         for (class_6880<class_1887> local6_0 : class_1890.method_57532(local4_3).method_57534()) {
            if (!this.findEnchant(stack, (class_1887)local6_0.comp_349())) {
               return false;
            }
         }

         Optional<class_6880<class_1842>> local5_4 = ((class_1844)local4_3.method_57825(class_9334.field_49651, class_1844.field_49274)).comp_2378();
         if (local5_4.isPresent()) {
            class_6880<class_1842> local6_2 = local5_4.get();

            for (class_1293 local8_1 : ((class_1842)local6_2.comp_349()).method_8049()) {
               if (!this.findEffect(stack, local8_1)) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private boolean checkItem(class_1799 stack, int price, int balance) {
      IItemStack local4_8 = (IItemStack)(Object)stack;
      if (local4_8.autosell()) {
         return false;
      } else {
         Iterator var5 = valid.entrySet().iterator();
         if (!var5.hasNext()) {
            return false;
         } else {
            Entry<class_1799, Integer> local6_7 = (Entry<class_1799, Integer>)var5.next();
            class_1799 local7_4 = local6_7.getKey();
            if (!local7_4.method_7909().equals(stack.method_7909())) {
               return false;
            } else if (local7_4.method_57824(class_9334.field_49631) != null
               && !stack.method_7964().getString().toLowerCase().contains(local7_4.method_7964().getString().toLowerCase())) {
               return false;
            } else if (local7_4.method_7947() > 1 && stack.method_7947() != local7_4.method_7947()) {
               return false;
            } else {
               for (class_6880<class_1887> local9_0 : class_1890.method_57532(local7_4).method_57534()) {
                  if (!this.findEnchant(stack, (class_1887)local9_0.comp_349())) {
                     return false;
                  }
               }

               Optional<class_6880<class_1842>> local8_5 = ((class_1844)local7_4.method_57825(class_9334.field_49651, class_1844.field_49274)).comp_2378();
               if (local8_5.isPresent()) {
                  class_6880<class_1842> local9_2 = local8_5.get();

                  for (class_1293 local11_1 : ((class_1842)local9_2.comp_349()).method_8049()) {
                     if (!this.findEffect(stack, local11_1)) {
                        return false;
                     }
                  }
               }

               if (balance == Integer.MAX_VALUE) {
                  return true;
               } else {
                  int local9_6 = local6_7.getValue();
                  if (local4_8.bestPrice()) {
                     int local10_3 = this.getBestPrice(local7_4);
                     if (local10_3 != -1) {
                        local9_6 = local10_3;
                     }
                  }

                  return balance < price ? false : price <= local9_6;
               }
            }
         }
      }
   }

   private boolean findEnchant(class_1799 itemStack, class_1887 enchantment) {
      class_9304 local3_1 = class_1890.method_57532(itemStack);

      for (class_6880<class_1887> local5_0 : local3_1.method_57534()) {
         if (((class_1887)local5_0.comp_349()).comp_2686().getString().equals(enchantment.comp_2686().getString())) {
            return true;
         }
      }

      return false;
   }

   private boolean findEffect(class_1799 itemStack, class_1293 effectInstance) {
      Optional<class_6880<class_1842>> local3_2 = ((class_1844)itemStack.method_57825(class_9334.field_49651, class_1844.field_49274)).comp_2378();
      if (local3_2.isPresent()) {
         class_6880<class_1842> local4_1 = local3_2.get();

         for (class_1293 local6_0 : ((class_1842)local4_1.comp_349()).method_8049()) {
            if (local6_0.method_5586().equals(effectInstance.method_5586())) {
               return true;
            }
         }
      }

      return false;
   }

   private int findPrevArrow(class_1263 container) {
      for (int local2_1 = 0; local2_1 < container.method_5439(); local2_1++) {
         class_1799 local3_0 = container.method_5438(local2_1);
         if (!local3_0.method_7960() && local3_0.method_7964().getString().toLowerCase().contains("предыдущая")) {
            return local2_1;
         }
      }

      return -1;
   }

   private int findNextArrow(class_1263 container) {
      for (int local2_1 = 0; local2_1 < container.method_5439(); local2_1++) {
         class_1799 local3_0 = container.method_5438(local2_1);
         if (!local3_0.method_7960() && local3_0.method_7964().getString().toLowerCase().contains("обновить")
            || local3_0.method_7964().getString().equals("[?] Обновить")) {
            return local2_1;
         }
      }

      return -1;
   }

   private int findNexttArrow(class_1263 container) {
      for (int local2_1 = 0; local2_1 < container.method_5439(); local2_1++) {
         class_1799 local3_0 = container.method_5438(local2_1);
         if (!local3_0.method_7960()
            && (local3_0.method_7964().getString().toLowerCase().contains("следующая") || local3_0.method_7964().getString().toLowerCase().contains("следущая"))
            )
          {
            return local2_1;
         }
      }

      return -1;
   }

   @Environment(EnvType.CLIENT)
   private static record ItemPrice(class_1799 item, int price) {
      @Override
      public String toString() {
         return "ItemPrice{item=" + this.item + ", price=" + this.price + "}";
      }
   }
}
