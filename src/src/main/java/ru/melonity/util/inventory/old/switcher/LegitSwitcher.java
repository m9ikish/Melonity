package ru.melonity.util.inventory.old.switcher;

import com.google.common.collect.Queues;
import java.util.Queue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import ru.melonity.util.inventory.old.clicker.Clicker;
import ru.melonity.util.time.Timer;

@Environment(EnvType.CLIENT)
public class LegitSwitcher implements Switcher {
   private final class_310 mc = class_310.method_1551();
   private final Clicker clicker = new Clicker();
   private final Timer processTimer = Timer.create();
   private final Timer operationTimer = Timer.create();
   private int lastSlot = -1;
   private int lastHotbar = -1;
   private boolean waitingForOperation = false;
   private Runnable pendingCallback = null;
   private final Queue<LegitSwitcher.TickOperation> queue = Queues.newConcurrentLinkedQueue();

   @Override
   public void fromHotbar(int slot) {
      this.lastHotbar = this.mc.field_1724.method_31548().field_7545;
      this.mc.field_1724.method_31548().field_7545 = slot;
   }

   @Override
   public void fromInventory(int slot, Runnable callback) {
      int local3_0 = this.mc.field_1724.method_31548().field_7545 + 36;
      this.lastSlot = slot;
      this.lastHotbar = -1;
      this.queue.add(new LegitSwitcher.TickOperation(() -> this.clicker.move(slot, local3_0), 60L, callback));
   }

   @Override
   public void tick() {
      if (this.waitingForOperation) {
         if (!this.operationTimer.isSleeping()) {
            this.waitingForOperation = false;
            if (this.pendingCallback != null) {
               this.pendingCallback.run();
               this.pendingCallback = null;
            }
         }
      } else {
         if (this.processTimer.hasReached(65L) && !this.queue.isEmpty()) {
            LegitSwitcher.TickOperation local1_1 = this.queue.poll();

            try {
               local1_1.operation.run();
               if (local1_1.delay > 0L) {
                  this.pendingCallback = local1_1.callback;
                  this.operationTimer.reset();
                  this.operationTimer.sleep(local1_1.delay);
                  this.waitingForOperation = true;
               } else if (local1_1.callback != null) {
                  local1_1.callback.run();
               }

               this.processTimer.reset();
            } catch (Exception var3) {
               var3.printStackTrace();
            }
         }
      }
   }

   @Override
   public void returnHotbar() {
      this.queue.add(new LegitSwitcher.TickOperation(() -> {
         if (this.lastSlot != -1) {
            int local1_0 = this.mc.field_1724.method_31548().field_7545 + 36;
            this.clicker.move(local1_0, this.lastSlot);
            this.lastSlot = -1;
         }

         if (this.lastHotbar != -1) {
            this.mc.field_1724.method_31548().field_7545 = this.lastHotbar;
            this.lastHotbar = -1;
         }
      }, 70L, null));
   }

   @Environment(EnvType.CLIENT)
   private static record TickOperation(Runnable operation, long delay, Runnable callback) {
   }
}
