package ru.melonity.util.event.listeners;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1713;
import net.minecraft.class_1792;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import ru.melonity.Melonity;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.annotation.ListenerClass;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.inventory.Filter;
import ru.melonity.util.inventory.InventoryUtil;
import ru.melonity.util.time.Timer;

@Environment(EnvType.CLIENT)
@ListenerClass
public class InventoryFilterListener {
   private boolean enabled;
   private final Timer timer = Timer.create();
   private final Queue<InventoryFilterListener.SlotMove> moveQueue = new LinkedList<>();
   private boolean processingQueue = false;
   private final EventListener<UpdateLivingEvent> onUpdate = event -> {
      if (this.enabled) {
         class_310 local2_3 = class_310.method_1551();
         if (this.processingQueue) {
            if (this.timer.hasReached(40L)) {
               InventoryFilterListener.SlotMove local3_0 = this.moveQueue.poll();
               if (local3_0 != null) {
                  local2_3.field_1761.method_2906(0, local3_0.fromSlot, 0, class_1713.field_7790, local2_3.field_1724);
                  local2_3.field_1761.method_2906(0, local3_0.toSlot, 0, class_1713.field_7790, local2_3.field_1724);
                  this.timer.reset();
               } else {
                  this.processingQueue = false;
                  this.enabled = false;
               }
            }
         } else {
            Filter local3_4 = Melonity.INSTANCE.getInventoryService().currentFilter();
            Map<Integer, class_1792> local4_5 = Melonity.INSTANCE
               .getInventoryService()
               .getSlots(Melonity.INSTANCE.getInventoryService().currentFilter().getName());
            if (local4_5 != null && !local4_5.isEmpty()) {
               this.moveQueue.clear();

               for (Entry<Integer, class_1792> local6_2 : local4_5.entrySet()) {
                  if (!local6_2.getValue().method_7848().getString().toLowerCase().contains("shulker")) {
                     int local7_1 = InventoryUtil.getInventoryItem(local2_3.field_1724, local6_2.getValue());
                     if (local7_1 != -1 && local7_1 != local6_2.getKey()) {
                        this.moveQueue.offer(new InventoryFilterListener.SlotMove(local7_1, local6_2.getKey()));
                     }
                  }
               }

               if (!this.moveQueue.isEmpty()) {
                  this.processingQueue = true;
                  this.timer.reset();
               } else {
                  this.enabled = false;
               }
            } else {
               if (local2_3.field_1724.field_6012 % 42 == 0) {
                  local2_3.field_1705.method_1743().method_1812(class_2561.method_43470("В фильтре не сохранены предметы"));
               }
            }
         }
      }
   };

   @Generated
   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setProcessingQueue(boolean processingQueue) {
      this.processingQueue = processingQueue;
   }

   @Environment(EnvType.CLIENT)
   private static record SlotMove(int fromSlot, int toSlot) {
   }
}
