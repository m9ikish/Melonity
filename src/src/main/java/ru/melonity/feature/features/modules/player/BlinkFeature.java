package ru.melonity.feature.features.modules.player;

import java.util.ArrayDeque;
import java.util.Queue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2596;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.UpdateEvent;

@Environment(EnvType.CLIENT)
public class BlinkFeature extends SimpleFeature {
   private final ListSetting mode = new ListSetting("blink.mode", ListSetting.ModeType.SINGLE, "blink.mode.default", "blink.mode.default", "blink.mode.timed");
   private final NumberSetting timerTicks = new NumberSetting("blink.timer.ticks", value -> value.intValue() + " ticks", 1, 20, 4)
      .setVisible(() -> "blink.mode.timed".equals(this.mode.getMode().getFirst()));
   private final Queue<class_2596<?>> packetQueue = new ArrayDeque<>();
   private int tickCounter = 0;
   private boolean isSending = false;
   private final EventListener<PacketEvent> onPacket = event -> {
      // method_1576() is the INTEGRATED server: null on every remote server, which is
      // exactly where Blink is supposed to work. Unguarded it NPE'd on every packet.
      if (this.isEnabled() && !this.isSending && (mc.method_1576() == null || !mc.method_1576().method_3724())) {
         if (event.isSend()) {
            event.cancel();
            this.packetQueue.offer(event.getPacket());
         }
      }
   };
   private final EventListener<UpdateEvent> onUpdate = event -> {
      if (this.isEnabled()
         && !this.isSending
         && "blink.mode.timed".equals(this.mode.getMode().getFirst())
         && (mc.method_1576() == null || !mc.method_1576().method_3724())) {
         this.tickCounter++;
         int local2_0 = this.timerTicks.getValue().intValue();
         if (this.tickCounter >= local2_0 && !this.packetQueue.isEmpty()) {
            this.sendAllPackets();
         }
      }
   };

   public BlinkFeature() {
      super("BlinkFeature", MenuCategory.PLAYER);
      this.settings(new MenuFeatureView[]{this.mode, this.timerTicks});
   }

   @Override
   public void onEnable() {
      this.packetQueue.clear();
      this.tickCounter = 0;
      this.isSending = false;
   }

   @Override
   public void onDisable() {
      if (!this.packetQueue.isEmpty()) {
         this.sendAllPackets();
      }
   }

   private void sendAllPackets() {
      if (mc.method_1562() != null && !this.packetQueue.isEmpty() && !this.isSending) {
         this.isSending = true;

         class_2596<?> local1_0;
         try {
            while ((local1_0 = this.packetQueue.poll()) != null) {
               try {
                  mc.method_1562().method_52787(local1_0);
               } catch (Exception var6) {
               }
            }
         } finally {
            this.isSending = false;
            this.tickCounter = 0;
         }
      }
   }
}
