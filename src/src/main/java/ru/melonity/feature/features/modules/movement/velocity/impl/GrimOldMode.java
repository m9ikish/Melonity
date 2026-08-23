package ru.melonity.feature.features.modules.movement.velocity.impl;

import kotlin.Metadata;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2596;
import net.minecraft.class_2664;
import net.minecraft.class_2743;
import net.minecraft.class_2846;
import net.minecraft.class_638;
import net.minecraft.class_746;
import net.minecraft.class_2828.class_2829;
import net.minecraft.class_2846.class_2847;
import org.jetbrains.annotations.Nullable;
import ru.melonity.feature.features.modules.movement.velocity.VelocityMode;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.EventPlayerMove;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.time.Timer;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0019\u0010\u0007\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\u000b\u001a\u00020\n2\b\u0010\u0005\u001a\u0004\u0018\u00010\tH\u0017¢\u0006\u0004\b\u000b\u0010\fJ\u0019\u0010\u000e\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\rH\u0016¢\u0006\u0004\b\u000e\u0010\u000fJ\u0019\u0010\u0011\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0010H\u0016¢\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\u0013\u0010\u0003J\u000f\u0010\u0014\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\u0014\u0010\u0003J\u0017\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0015H\u0002¢\u0006\u0004\b\u0017\u0010\u0018R\u0016\u0010\u0019\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001d\u001a\n \u001c*\u0004\u0018\u00010\u001b0\u001b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001d\u0010\u001e¨\u0006\u001f"},
   d2 = {"Lru/melonity/feature/features/modules/movement/velocity/impl/GrimOldMode;", "Lru/melonity/feature/features/modules/movement/velocity/VelocityMode;", "<init>", "()V", "Lru/melonity/util/event/events/UpdateLivingEvent;", "event", "", "onUpdate", "(Lru/melonity/util/event/events/UpdateLivingEvent;)V", "Lru/melonity/util/event/events/PacketEvent;", "", "onPacket", "(Lru/melonity/util/event/events/PacketEvent;)Z", "Lru/melonity/util/event/events/EventPlayerMove;", "onPlayerMove", "(Lru/melonity/util/event/events/EventPlayerMove;)V", "Lru/melonity/util/event/events/EventInput;", "onInput", "(Lru/melonity/util/event/events/EventInput;)V", "onEnable", "onDisable", "Lnet/minecraft/class_2338;", "pos", "checkBlock", "(Lnet/minecraft/class_2338;)Z", "canRemoveVelocity", "Z", "Lru/melonity/util/time/Timer;", "kotlin.jvm.PlatformType", "flagTimer", "Lru/melonity/util/time/Timer;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class GrimOldMode extends VelocityMode {
   private boolean canRemoveVelocity;
   private final Timer flagTimer = Timer.create();

   public GrimOldMode() {
      super("Grim Old");
   }

   @Override
   public void onUpdate(@Nullable UpdateLivingEvent event) {
   }

   @Override
   public boolean onPacket(@Nullable PacketEvent event) {
      if (event == null || !event.isReceive()) {
         return false;
      }

      class_746 player = this.getMc().field_1724;
      if (player == null) {
         return false;
      }

      class_2596<?> packet = event.packet;
      boolean knockback = packet instanceof class_2743 && ((class_2743)packet).method_11818() == player.method_5628();
      if (!knockback && !(packet instanceof class_2664)) {
         return false;
      }

      // Old Grim builds stop predicting knockback for the tick in which they have to
      // resolve a finished block break that the client claims to have done in thin air.
      // The flag is only worth sending a few times a second, otherwise it is the spam
      // itself that gets picked up.
      if (this.flagTimer.hasReached(400L)) {
         this.flagTimer.reset();
         this.canRemoveVelocity = false;
         class_2338 origin = player.method_24515();

         for (int offset = 2; offset >= 0 && !this.canRemoveVelocity; offset--) {
            this.canRemoveVelocity = this.checkBlock(origin.method_10086(offset));
         }
      }

      // Without a landed flag the impulse has to be taken, dropping it blind is exactly
      // what old Grim setbacks on.
      return this.canRemoveVelocity;
   }

   @Override
   public void onPlayerMove(@Nullable EventPlayerMove event) {
   }

   @Override
   public void onInput(@Nullable EventInput event) {
   }

   @Override
   public void onEnable() {
      this.canRemoveVelocity = false;
   }

   @Override
   public void onDisable() {
      this.canRemoveVelocity = false;
   }

   private final boolean checkBlock(class_2338 pos) {
      class_746 var10000 = this.getMc().field_1724;
      if (var10000 != null) {
         class_746 local2_0 = var10000;
         class_638 var3 = this.getMc().field_1687;
         if (var3 != null ? var3.method_22347(pos) : false) {
            local2_0.field_3944
               .method_52787((class_2596)(new class_2829(local2_0.method_23317(), local2_0.method_23318(), local2_0.method_23321(), local2_0.method_24828())));
            local2_0.field_3944.method_52787((class_2596)(new class_2846(class_2847.field_12973, pos, class_2350.field_11033)));
            class_638 var4 = this.getMc().field_1687;
            if (var4 != null) {
               var4.method_22352(pos, false);
            }

            return true;
         }
      }

      return false;
   }
}
