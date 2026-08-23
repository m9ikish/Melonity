package ru.melonity.feature.features.modules.movement.velocity.impl;

import kotlin.Metadata;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2596;
import net.minecraft.class_2664;
import net.minecraft.class_2743;
import net.minecraft.class_746;
import org.jetbrains.annotations.Nullable;
import ru.melonity.feature.features.modules.movement.velocity.VelocityMode;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.EventPlayerMove;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0019\u0010\u0007\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0016¢\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\u000b\u001a\u00020\n2\b\u0010\u0005\u001a\u0004\u0018\u00010\tH\u0017¢\u0006\u0004\b\u000b\u0010\fJ\u0019\u0010\u000e\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\rH\u0016¢\u0006\u0004\b\u000e\u0010\u000fJ\u0019\u0010\u0011\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0010H\u0016¢\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\u0013\u0010\u0003J\u000f\u0010\u0014\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\u0014\u0010\u0003¨\u0006\u0015"},
   d2 = {"Lru/melonity/feature/features/modules/movement/velocity/impl/CancelMode;", "Lru/melonity/feature/features/modules/movement/velocity/VelocityMode;", "<init>", "()V", "Lru/melonity/util/event/events/UpdateLivingEvent;", "event", "", "onUpdate", "(Lru/melonity/util/event/events/UpdateLivingEvent;)V", "Lru/melonity/util/event/events/PacketEvent;", "", "onPacket", "(Lru/melonity/util/event/events/PacketEvent;)Z", "Lru/melonity/util/event/events/EventPlayerMove;", "onPlayerMove", "(Lru/melonity/util/event/events/EventPlayerMove;)V", "Lru/melonity/util/event/events/EventInput;", "onInput", "(Lru/melonity/util/event/events/EventInput;)V", "onEnable", "onDisable", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class CancelMode extends VelocityMode {
   public CancelMode() {
      super("novelocity.cancel");
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
      if (packet instanceof class_2743) {
         // Only our own knockback, everybody else has to keep moving normally.
         return ((class_2743)packet).method_11818() == player.method_5628();
      }

      // The explosion packet carries the knockback for the receiving player only.
      return packet instanceof class_2664;
   }

   @Override
   public void onPlayerMove(@Nullable EventPlayerMove event) {
   }

   @Override
   public void onInput(@Nullable EventInput event) {
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }
}
