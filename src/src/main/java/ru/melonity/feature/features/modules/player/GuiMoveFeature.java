package ru.melonity.feature.features.modules.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2596;
import net.minecraft.class_2813;
import net.minecraft.class_2868;
import net.minecraft.class_2885;
import net.minecraft.class_304;
import net.minecraft.class_3675;
import net.minecraft.class_408;
import net.minecraft.class_490;
import net.minecraft.class_498;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.feature.ActionScheduler;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.ui.menu.clickui.Menu;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.InventoryCloseEvent;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.minecraft.MovementUtil;
import ru.melonity.util.time.Timer;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0014¢\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\t\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\t\u0010\u0003J\u000f\u0010\n\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\n\u0010\u0003J\u0017\u0010\r\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u000f\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\u000f\u0010\u000eJ\u000f\u0010\u0010\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u0010\u0010\u0003J\u000f\u0010\u0011\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u0011\u0010\u0003J\u000f\u0010\u0012\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u0012\u0010\u0003J\u000f\u0010\u0013\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u0013\u0010\u0003J\u000f\u0010\u0014\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u0014\u0010\u0003J\r\u0010\u0015\u001a\u00020\u0006¢\u0006\u0004\b\u0015\u0010\u0003J\u000f\u0010\u0016\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u0016\u0010\u0003J\u000f\u0010\u0017\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u0017\u0010\u0003J\u0017\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0018H\u0002¢\u0006\u0004\b\u001a\u0010\u001bJ\u000f\u0010\u001c\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001e\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u001e\u0010\u001dJ\u000f\u0010\u001f\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u001f\u0010\u001dJ\u0017\u0010\"\u001a\u00020\u00062\b\u0010!\u001a\u0004\u0018\u00010 ¢\u0006\u0004\b\"\u0010#R\u0014\u0010%\u001a\u00020$8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b%\u0010&R\u001e\u0010)\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030(0'8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b)\u0010*R\u001c\u0010-\u001a\n ,*\u0004\u0018\u00010+0+8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b-\u0010.R\u001c\u0010/\u001a\n ,*\u0004\u0018\u00010+0+8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b/\u0010.R\u001c\u00100\u001a\n ,*\u0004\u0018\u00010+0+8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b0\u0010.R\u0016\u00101\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b1\u00102R\u0016\u00103\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b3\u00102R\u0016\u00104\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b4\u00102R\u0016\u00105\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b5\u00102R\u0016\u00107\u001a\u0002068\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b7\u00108R\u001e\u0010:\u001a\n\u0012\u0004\u0012\u00020\u0018\u0018\u0001098\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b:\u0010;R\u001d\u0010>\u001a\b\u0012\u0004\u0012\u00020=0<8\u0006¢\u0006\f\n\u0004\b>\u0010?\u001a\u0004\b@\u0010AR\u001d\u0010C\u001a\b\u0012\u0004\u0012\u00020B0<8\u0006¢\u0006\f\n\u0004\bC\u0010?\u001a\u0004\bD\u0010AR\u001d\u0010E\u001a\b\u0012\u0004\u0012\u00020\u000b0<8\u0006¢\u0006\f\n\u0004\bE\u0010?\u001a\u0004\bF\u0010AR\u001d\u0010H\u001a\b\u0012\u0004\u0012\u00020G0<8\u0006¢\u0006\f\n\u0004\bH\u0010?\u001a\u0004\bI\u0010A¨\u0006J"},
   d2 = {"Lru/melonity/feature/features/modules/player/GuiMoveFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "", "enabled", "", "onToggle", "(Z)V", "initMovementKeys", "resetState", "Lru/melonity/util/event/events/PacketEvent;", "event", "handleFunTimePackets", "(Lru/melonity/util/event/events/PacketEvent;)V", "handlePacketDelayPackets", "activatePacketDelay", "handleDefaultClose", "handleFunTimeClose", "handlePacketDelayClose", "sendQueuedPackets", "flushQueuedPackets", "updateKeyStates", "releaseAllKeys", "Lnet/minecraft/class_304;", "key", "isKeyPressed", "(Lnet/minecraft/class_304;)Z", "isTextInputScreen", "()Z", "isFunTimeMode", "isPacketDelayMode", "Ljava/lang/Runnable;", "action", "runWithoutQueueing", "(Ljava/lang/Runnable;)V", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "mode", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "Ljava/util/ArrayList;", "Lnet/minecraft/class_2596;", "packetQueue", "Ljava/util/ArrayList;", "Lru/melonity/util/time/Timer;", "kotlin.jvm.PlatformType", "waitTimer", "Lru/melonity/util/time/Timer;", "freezeTimer", "packetDelayTimer", "shouldFreeze", "Z", "wasInventoryOpen", "isProcessingPackets", "packetDelayActive", "", "packetDelayRemaining", "J", "", "movementKeys", "[Lnet/minecraft/class_304;", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/UpdateLivingEvent;", "onUpdate", "Lru/melonity/util/event/EventListener;", "getOnUpdate", "()Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/EventInput;", "onInput", "getOnInput", "onPacket", "getOnPacket", "Lru/melonity/util/event/events/InventoryCloseEvent;", "onClose", "getOnClose", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class GuiMoveFeature extends SimpleFeature {
   @NotNull
   private final ListSetting mode;
   @NotNull
   private final ArrayList<class_2596<?>> packetQueue;
   private final Timer waitTimer;
   private final Timer freezeTimer;
   private final Timer packetDelayTimer;
   private boolean shouldFreeze;
   private boolean wasInventoryOpen;
   private boolean isProcessingPackets;
   private boolean packetDelayActive;
   private long packetDelayRemaining;
   @Nullable
   private class_304[] movementKeys;
   @NotNull
   private final EventListener<UpdateLivingEvent> onUpdate;
   @NotNull
   private final EventListener<EventInput> onInput;
   @NotNull
   private final EventListener<PacketEvent> onPacket;
   @NotNull
   private final EventListener<InventoryCloseEvent> onClose;

   public GuiMoveFeature() {
      super("GuiMove", MenuCategory.PLAYER);
      ListSetting.ModeType var10004 = ListSetting.ModeType.SINGLE;
      String[] var1 = new String[]{"guimove.default", "FunTime", "PacketDelay"};
      this.mode = new ListSetting("guimove.mode", var10004, "guimove.default", var1);
      this.packetQueue = new ArrayList<>();
      this.waitTimer = Timer.create();
      this.freezeTimer = Timer.create();
      this.packetDelayTimer = Timer.create();
      this.settingClear();
      this.setting(this.mode);
      this.onUpdate = event -> onUpdate$lambda$0(this, event);
      this.onInput = event -> onInput$lambda$1(this, event);
      this.onPacket = event -> onPacket$lambda$2(this, event);
      this.onClose = event -> onClose$lambda$3(this, event);
   }

   @Override
   protected void onToggle(boolean enabled) {
      if (enabled) {
         this.initMovementKeys();
      } else {
         this.resetState();
      }
   }

   private final void initMovementKeys() {
      class_304[] var1 = new class_304[6];
      class_304 var10003 = SimpleFeature.mc.field_1690.field_1894;
      Intrinsics.checkNotNullExpressionValue(SimpleFeature.mc.field_1690.field_1894, "forwardKey");
      var1[0] = var10003;
      var10003 = SimpleFeature.mc.field_1690.field_1881;
      Intrinsics.checkNotNullExpressionValue(SimpleFeature.mc.field_1690.field_1881, "backKey");
      var1[1] = var10003;
      var10003 = SimpleFeature.mc.field_1690.field_1913;
      Intrinsics.checkNotNullExpressionValue(SimpleFeature.mc.field_1690.field_1913, "leftKey");
      var1[2] = var10003;
      var10003 = SimpleFeature.mc.field_1690.field_1849;
      Intrinsics.checkNotNullExpressionValue(SimpleFeature.mc.field_1690.field_1849, "rightKey");
      var1[3] = var10003;
      var10003 = SimpleFeature.mc.field_1690.field_1903;
      Intrinsics.checkNotNullExpressionValue(SimpleFeature.mc.field_1690.field_1903, "jumpKey");
      var1[4] = var10003;
      var10003 = SimpleFeature.mc.field_1690.field_1867;
      Intrinsics.checkNotNullExpressionValue(SimpleFeature.mc.field_1690.field_1867, "sprintKey");
      var1[5] = var10003;
      this.movementKeys = var1;
   }

   private final void resetState() {
      this.packetQueue.clear();
      this.shouldFreeze = false;
      this.wasInventoryOpen = false;
      this.isProcessingPackets = false;
      this.packetDelayActive = false;
      this.packetDelayRemaining = 0L;
   }

   @NotNull
   public final EventListener<UpdateLivingEvent> getOnUpdate() {
      return this.onUpdate;
   }

   @NotNull
   public final EventListener<EventInput> getOnInput() {
      return this.onInput;
   }

   @NotNull
   public final EventListener<PacketEvent> getOnPacket() {
      return this.onPacket;
   }

   private final void handleFunTimePackets(PacketEvent event) {
      if (event.packet instanceof class_2813 && SimpleFeature.mc.field_1755 instanceof class_490) {
         this.packetQueue.add(event.packet);
         event.cancel();
      }
   }

   private final void handlePacketDelayPackets(PacketEvent event) {
      if ((event.packet instanceof class_2813 || event.packet instanceof class_2885 || event.packet instanceof class_2868)
         && SimpleFeature.mc.field_1755 instanceof class_490) {
         this.packetQueue.add(event.packet);
         event.cancel();
         this.activatePacketDelay();
      }
   }

   private final void activatePacketDelay() {
      this.packetDelayActive = true;
      this.packetDelayRemaining = (long)((double)50 + Math.random() * (double)75);
      this.packetDelayTimer.reset();
   }

   @NotNull
   public final EventListener<InventoryCloseEvent> getOnClose() {
      return this.onClose;
   }

   private final void handleDefaultClose() {
      if (this.wasInventoryOpen) {
         this.shouldFreeze = true;
         this.freezeTimer.reset();
      }
   }

   private final void handleFunTimeClose() {
      if (SimpleFeature.mc.field_1755 instanceof class_490) {
         if (!this.packetQueue.isEmpty()) {
            this.shouldFreeze = true;
            this.freezeTimer.reset();
            this.waitTimer.reset();
            if (!MovementUtil.isMoving()) {
               this.sendQueuedPackets();
            }

            ActionScheduler.scheduleOnce(this::sendQueuedPackets, 3);
         }
      }
   }

   private final void handlePacketDelayClose() {
      if (SimpleFeature.mc.field_1755 instanceof class_490) {
         if (!this.packetQueue.isEmpty()) {
            this.shouldFreeze = true;
            this.freezeTimer.reset();
            if (!MovementUtil.isMoving()) {
               this.sendQueuedPackets();
            }

            ActionScheduler.scheduleOnce(this::sendQueuedPackets, 2);
         }
      }
   }

   private final void sendQueuedPackets() {
      if (SimpleFeature.mc.field_1724 == null) {
         return;
      }

      this.isProcessingPackets = true;

      try {
         // Drain into a snapshot first: dispatching a packet can re-enter this
         // feature through the packet event bus, which would otherwise mutate
         // packetQueue while we are iterating it.
         List<class_2596<?>> pending = new ArrayList<>(this.packetQueue);
         this.packetQueue.clear();

         for (class_2596<?> packet : pending) {
            class_746 player = SimpleFeature.mc.field_1724;
            if (player == null) {
               break;
            }

            player.field_3944.method_52787(packet);
         }
      } finally {
         this.isProcessingPackets = false;
      }
   }

   public final void flushQueuedPackets() {
      if (!this.packetQueue.isEmpty()) {
         this.sendQueuedPackets();
      }
   }

   private final void updateKeyStates() {
      if (this.movementKeys != null) {
         class_304[] local1_1 = this.movementKeys;
         int var2 = 0;

         for (int var3 = local1_1.length; var2 < var3; var2++) {
            class_304 local4_0 = local1_1[var2];
            local4_0.method_23481(this.isKeyPressed(local4_0));
         }
      }
   }

   private final void releaseAllKeys() {
      if (this.movementKeys != null) {
         class_304[] local1_1 = this.movementKeys;
         int var2 = 0;

         for (int var3 = local1_1.length; var2 < var3; var2++) {
            class_304 local4_0 = local1_1[var2];
            local4_0.method_23481(false);
         }
      }
   }

   private final boolean isKeyPressed(class_304 key) {
      return class_3675.method_15987(SimpleFeature.mc.method_22683().method_4490(), key.method_1429().method_1444());
   }

   private final boolean isTextInputScreen() {
      return SimpleFeature.mc.field_1755 instanceof class_408 || SimpleFeature.mc.field_1755 instanceof class_498;
   }

   private final boolean isFunTimeMode() {
      List var10000 = this.mode.mode;
      Intrinsics.checkNotNullExpressionValue(this.mode.mode, "mode");
      return Intrinsics.areEqual(CollectionsKt.first(var10000), "FunTime");
   }

   private final boolean isPacketDelayMode() {
      List var10000 = this.mode.mode;
      Intrinsics.checkNotNullExpressionValue(this.mode.mode, "mode");
      return Intrinsics.areEqual(CollectionsKt.first(var10000), "PacketDelay");
   }

   public final void runWithoutQueueing(@Nullable Runnable action) {
      if (action != null) {
         boolean local2_0 = this.isProcessingPackets;
         this.isProcessingPackets = true;

         try {
            action.run();
         } finally {
            this.isProcessingPackets = local2_0;
         }
      }
   }

   private static final void onUpdate$lambda$0(GuiMoveFeature this$0, UpdateLivingEvent event) {
      if (SimpleFeature.mc.field_1724 != null) {
         if (this$0.movementKeys == null) {
            this$0.initMovementKeys();
         }

         if (!(SimpleFeature.mc.field_1755 instanceof Menu) || MelonityTextField.selectedField == -1) {
            if (!this$0.isTextInputScreen()) {
               if (this$0.shouldFreeze && !this$0.freezeTimer.hasReached(70L)) {
                  this$0.releaseAllKeys();
               } else {
                  if (this$0.shouldFreeze) {
                     this$0.shouldFreeze = false;
                  }

                  if (!this$0.waitTimer.hasReached(80L)) {
                     this$0.releaseAllKeys();
                  } else {
                     if (this$0.isPacketDelayMode() && this$0.packetDelayActive && this$0.packetDelayTimer.hasReached(this$0.packetDelayRemaining)) {
                        this$0.packetDelayActive = false;
                     }

                     if (this$0.isPacketDelayMode() && this$0.packetDelayActive) {
                        this$0.releaseAllKeys();
                     } else {
                        this$0.updateKeyStates();
                        this$0.wasInventoryOpen = SimpleFeature.mc.field_1755 instanceof class_490;
                     }
                  }
               }
            }
         }
      }
   }

   private static final void onInput$lambda$1(GuiMoveFeature this$0, EventInput event) {
      if (this$0.shouldFreeze && !this$0.freezeTimer.hasReached(70L)) {
         event.forward = 0.0F;
         event.strafe = 0.0F;
      }
   }

   private static final void onPacket$lambda$2(GuiMoveFeature this$0, PacketEvent event) {
      if (!this$0.isProcessingPackets) {
         if (this$0.isFunTimeMode()) {
            Intrinsics.checkNotNull(event);
            this$0.handleFunTimePackets(event);
         } else if (this$0.isPacketDelayMode()) {
            Intrinsics.checkNotNull(event);
            this$0.handlePacketDelayPackets(event);
         }
      }
   }

   private static final void onClose$lambda$3(GuiMoveFeature this$0, InventoryCloseEvent event) {
      if (this$0.isFunTimeMode()) {
         this$0.handleFunTimeClose();
      } else if (this$0.isPacketDelayMode()) {
         this$0.handlePacketDelayClose();
      } else {
         this$0.handleDefaultClose();
      }
   }
}
