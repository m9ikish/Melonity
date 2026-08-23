package ru.melonity.feature.features.modules.movement;

import java.util.ArrayDeque;
import java.util.Queue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1747;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_3965;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventRelativeMove;
import ru.melonity.util.event.events.MoveEvent;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.RenderGameWorldEvent;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.minecraft.PlayerUtil;

@Environment(EnvType.CLIENT)
public class FlyFeature extends SimpleFeature {
   private final ListSetting modes = new ListSetting("fly.mode", ListSetting.ModeType.SINGLE, "fly.def", "fly.def", "HolyWorld", "fly.glide");
   private final NumberSetting horizontalSpeed = new NumberSetting("fly.h-speed", value -> String.format("%.1f", value.floatValue()), 0.1F, 5.0F, 0.5F)
      .setVisible(() -> !this.modes.contains("HolyWorld"));
   private final NumberSetting verticalSpeed = new NumberSetting("fly.v-speed", value -> String.format("%.1f", value.floatValue()), 0.1F, 5.0F, 0.5F)
      .setVisible(() -> !this.modes.contains("HolyWorld"));
   private FlyFeature.BlockPosWithFacing currentBlock;
   private int prevY = -999;
   private long towerTime = 0L;
   private final Queue<class_2596<?>> packetQueue = new ArrayDeque<>();
   private int tickCounter = 0;
   private boolean isSending = false;
   private final EventListener<MoveEvent> onPreMotion = event -> {
      String var2 = this.modes.getMode().getFirst();
      switch (var2) {
         case "fly.def":
         case "fly.glide":
            this.handleVanillaFly(event);
      }
   };
   private final EventListener<UpdateEvent> onUpdate = event -> {
      if (this.modes.contains("HolyWorld") && this.isEnabled()) {
         // Null integrated server means we are on a remote server - HolyWorld is one,
         // so this path used to NPE on the very first tick after enabling.
         if (!this.isEnabled() || this.isSending || (mc.method_1576() != null && mc.method_1576().method_3724())) {
            return;
         }

         this.tickCounter++;
         int local2_0 = 2;
         if (this.tickCounter >= local2_0 && !this.packetQueue.isEmpty()) {
            this.sendAllPackets();
         }
      }

      if (this.modes.contains("fly.glide")) {
         if (mc.field_1724.method_24828() && !mc.field_1690.field_1903.method_1434()) {
            mc.field_1724.method_6043();
         }

         mc.field_1724.method_18800(0.0, -0.003, 0.0);
      }
   };
   public final EventListener<MoveEvent> onMove = e -> {
      if (this.modes.contains("HolyWorld")) {
         double local2_1 = e.motion.field_1352;
         double local4_2 = e.motion.field_1351;
         double local6_3 = e.motion.field_1350;
         if (mc.field_1724.method_24828() && !mc.field_1724.field_5960) {
            double local8_0 = 0.05;

            while (local2_1 != 0.0 && this.isOffsetBBEmpty(local2_1, 0.0)) {
               if (local2_1 < local8_0 && local2_1 >= -local8_0) {
                  local2_1 = 0.0;
               } else if (local2_1 > 0.0) {
                  local2_1 -= local8_0;
               } else {
                  local2_1 += local8_0;
               }
            }

            while (local6_3 != 0.0 && this.isOffsetBBEmpty(0.0, local6_3)) {
               if (local6_3 < local8_0 && local6_3 >= -local8_0) {
                  local6_3 = 0.0;
               } else if (local6_3 > 0.0) {
                  local6_3 -= local8_0;
               } else {
                  local6_3 += local8_0;
               }
            }

            while (local2_1 != 0.0 && local6_3 != 0.0 && this.isOffsetBBEmpty(local2_1, local6_3)) {
               if (local2_1 < local8_0 && local2_1 >= -local8_0) {
                  local2_1 = 0.0;
               } else if (local2_1 > 0.0) {
                  local2_1 -= local8_0;
               } else {
                  local2_1 += local8_0;
               }

               if (local6_3 < local8_0 && local6_3 >= -local8_0) {
                  local6_3 = 0.0;
               } else if (local6_3 > 0.0) {
                  local6_3 -= local8_0;
               } else {
                  local6_3 += local8_0;
               }
            }
         }

         e.motion = new class_243(local2_1, local4_2, local6_3);
      }
   };
   public final EventListener<RenderGameWorldEvent> onPre = event -> {
      if (this.modes.contains("HolyWorld")) {
         this.preAction();
      }
   };
   public final EventListener<EventRelativeMove> onPost = e -> {
      if (this.modes.contains("HolyWorld")) {
         this.postAction();
      }
   };
   private final EventListener<PacketEvent> onPacket = event -> {
      if (this.isEnabled()
         && !this.isSending
         && (mc.method_1576() == null || !mc.method_1576().method_3724())
         && !this.modes.contains("HolyWorld")) {
         if (event.isSend()) {
            event.cancel();
            this.packetQueue.offer(event.getPacket());
         }
      }
   };

   public FlyFeature() {
      super("Fly", MenuCategory.MOVEMENT);
      this.setting(this.modes);
      this.settings(new MenuFeatureView[]{this.horizontalSpeed, this.verticalSpeed});
   }

   @Override
   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      this.clearPacketQueue();
   }

   @Override
   public void onEnable() {
      this.prevY = -999;
      this.towerTime = 0L;
      this.packetQueue.clear();
      this.tickCounter = 0;
      this.isSending = false;
   }

   @Override
   public void onDisable() {
      mc.field_1724.method_31549().field_7479 = false;
      mc.field_1724.method_31549().method_7248(0.05F);
      RotationManager.INSTANCE.reset();
      if (!this.packetQueue.isEmpty()) {
         this.sendAllPackets();
      }
   }

   private void clearPacketQueue() {
      this.packetQueue.forEach(packet -> mc.method_1562().method_52787(packet));
      this.packetQueue.clear();
      this.tickCounter = 0;
   }

   private void handleVanillaFly(MoveEvent event) {
      double local2_0 = mc.field_1690.field_1832.method_1434()
         ? (double)(-this.verticalSpeed.getValue().floatValue())
         : (mc.field_1690.field_1903.method_1434() ? (double)this.verticalSpeed.getValue().floatValue() : 0.0);
      class_243 local4_1 = PlayerUtil.calculateDirection((double)this.horizontalSpeed.getValue().floatValue());
      event.motion = new class_243(local4_1.field_1352, local2_0, local4_1.field_1350);
   }

   private void preAction() {
      this.currentBlock = null;
      if (this.prePlace(false) != -1) {
         if (mc.field_1690.field_1903.method_1434() && !this.isMoving()) {
            this.prevY = (int)Math.floor(mc.field_1724.method_23318() - 1.0);
         }

         class_2338 local1_2 = this.prevY == -999
            ? class_2338.method_49637(mc.field_1724.method_23317(), (double)this.prevY, mc.field_1724.method_23321())
            : new class_2338(
               (int)Math.floor(mc.field_1724.method_23317()),
               (int)Math.floor(mc.field_1724.method_23318() - 1.0),
               (int)Math.floor(mc.field_1724.method_23321())
            );
         if (mc.field_1687.method_8320(local1_2).method_45474()) {
            this.currentBlock = this.checkNearBlocksExtended(local1_2);
            if (this.currentBlock != null) {
               class_243 local2_0 = new class_243(
                     (double)this.currentBlock.pos.method_10263() + 0.5,
                     (double)this.currentBlock.pos.method_10264() + 0.5,
                     (double)this.currentBlock.pos.method_10260() + 0.5
                  )
                  .method_1019(new class_243(this.currentBlock.facing.method_23955()).method_1021(0.3));
               float[] local3_1 = this.calculateAngle(local2_0);
               RotationManager.INSTANCE.rotate(local3_1[0], local3_1[1]);
            }
         }
      }
   }

   private void postAction() {
      float local1_3 = 0.3F;
      if (!mc.field_1687
         .method_20812(mc.field_1724, mc.field_1724.method_5829().method_1009((double)(-local1_3), 0.0, (double)(-local1_3)).method_989(0.0, -0.5, 0.0))
         .iterator()
         .hasNext()) {
         if (this.currentBlock != null) {
            int local2_4 = this.prePlace(true);
            if (local2_4 != -1) {
               if (mc.field_1724.field_3913.field_3904 && !this.isMoving()) {
                  if (System.currentTimeMillis() - this.towerTime > 1000L) {
                     this.towerTime = System.currentTimeMillis();
                  }
               } else {
                  this.towerTime = System.currentTimeMillis();
               }

               class_3965 local3_0 = new class_3965(
                  new class_243(
                     (double)this.currentBlock.pos.method_10263() + Math.random(),
                     (double)((float)this.currentBlock.pos.method_10264() + 0.99F),
                     (double)this.currentBlock.pos.method_10260() + Math.random()
                  ),
                  this.currentBlock.facing,
                  this.currentBlock.pos,
                  false
               );
               boolean local4_1 = local2_4 == -2;
               class_1268 local5_2 = local4_1 ? class_1268.field_5810 : class_1268.field_5808;
               mc.field_1761.method_2896(mc.field_1724, local5_2, local3_0);
               mc.field_1724.method_6104(local5_2);
               this.prevY = this.currentBlock.pos.method_10264();
               this.postPlace(local2_4);
            }
         }
      }
   }

   private FlyFeature.BlockPosWithFacing checkNearBlocksExtended(class_2338 blockPos) {
      FlyFeature.BlockPosWithFacing local2_0;
      if ((local2_0 = this.checkNearBlocks(blockPos)) != null) {
         return local2_0;
      } else if ((local2_0 = this.checkNearBlocks(blockPos.method_10069(-1, 0, 0))) != null) {
         return local2_0;
      } else if ((local2_0 = this.checkNearBlocks(blockPos.method_10069(1, 0, 0))) != null) {
         return local2_0;
      } else if ((local2_0 = this.checkNearBlocks(blockPos.method_10069(0, 0, 1))) != null) {
         return local2_0;
      } else if ((local2_0 = this.checkNearBlocks(blockPos.method_10069(0, 0, -1))) != null) {
         return local2_0;
      } else if ((local2_0 = this.checkNearBlocks(blockPos.method_10069(-2, 0, 0))) != null) {
         return local2_0;
      } else if ((local2_0 = this.checkNearBlocks(blockPos.method_10069(2, 0, 0))) != null) {
         return local2_0;
      } else if ((local2_0 = this.checkNearBlocks(blockPos.method_10069(0, 0, 2))) != null) {
         return local2_0;
      } else if ((local2_0 = this.checkNearBlocks(blockPos.method_10069(0, 0, -2))) != null) {
         return local2_0;
      } else if ((local2_0 = this.checkNearBlocks(blockPos.method_10069(0, -1, 0))) != null) {
         return local2_0;
      } else if ((local2_0 = this.checkNearBlocks(blockPos.method_10069(1, -1, 0))) != null) {
         return local2_0;
      } else if ((local2_0 = this.checkNearBlocks(blockPos.method_10069(-1, -1, 0))) != null) {
         return local2_0;
      } else {
         return (local2_0 = this.checkNearBlocks(blockPos.method_10069(0, -1, 1))) != null ? local2_0 : this.checkNearBlocks(blockPos.method_10069(0, -1, -1));
      }
   }

   private FlyFeature.BlockPosWithFacing checkNearBlocks(class_2338 pos) {
      if (!mc.field_1687.method_22347(pos)) {
         return null;
      } else {
         for (class_2350 local5_1 : class_2350.values()) {
            class_2338 local6_0 = pos.method_10093(local5_1);
            if (!mc.field_1687.method_8320(local6_0).method_45474()) {
               return new FlyFeature.BlockPosWithFacing(local6_0, local5_1.method_10153());
            }
         }

         return null;
      }
   }

   private int prePlace(boolean swap) {
      if (mc.field_1724.method_6079().method_7909() instanceof class_1747 local2_0 && !local2_0.method_7711().method_9564().method_45474()) {
         return -2;
      }

      if (mc.field_1724.method_6047().method_7909() instanceof class_1747 local2_1 && !local2_1.method_7711().method_9564().method_45474()) {
         return mc.field_1724.method_31548().field_7545;
      }

      int local2_2 = mc.field_1724.method_31548().field_7545;
      int local3_3 = this.findBlockInHotbar();
      if (swap) {
         mc.field_1724.method_31548().field_7545 = local3_3;
      }

      return local2_2;
   }

   private void postPlace(int prevSlot) {
      if (prevSlot != -1 && prevSlot != -2) {
         mc.field_1724.method_31548().field_7545 = prevSlot;
      }
   }

   private int findBlockInHotbar() {
      for (int local1_1 = 0; local1_1 < 9; local1_1++) {
         if (mc.field_1724.method_31548().method_5438(local1_1).method_7909() instanceof class_1747 local2_0
            && !local2_0.method_7711().method_9564().method_45474()) {
            return local1_1;
         }
      }

      return -1;
   }

   private boolean isOffsetBBEmpty(double x, double z) {
      return !mc.field_1687.method_20812(mc.field_1724, mc.field_1724.method_5829().method_1009(-0.1, 0.0, -0.1).method_989(x, -2.0, z)).iterator().hasNext();
   }

   private boolean isMoving() {
      return mc.field_1724.field_3913.field_3905 != 0.0F || mc.field_1724.field_3913.field_3907 != 0.0F;
   }

   private float[] calculateAngle(class_243 to) {
      class_243 local2_0 = mc.field_1724.method_33571();
      double local3_1 = to.field_1352 - local2_0.field_1352;
      double local5_2 = to.field_1351 - local2_0.field_1351;
      double local7_3 = to.field_1350 - local2_0.field_1350;
      double local9_4 = Math.sqrt(local3_1 * local3_1 + local7_3 * local7_3);
      float local11_5 = (float)Math.toDegrees(Math.atan2(local7_3, local3_1)) - 90.0F;
      float local12_6 = (float)(-Math.toDegrees(Math.atan2(local5_2, local9_4)));
      return new float[]{local11_5, local12_6};
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

   @Environment(EnvType.CLIENT)
   private static class BlockPosWithFacing {
      public final class_2338 pos;
      public final class_2350 facing;

      public BlockPosWithFacing(class_2338 pos, class_2350 facing) {
         this.pos = pos;
         this.facing = facing;
      }
   }
}
