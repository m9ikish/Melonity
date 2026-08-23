package ru.melonity.feature.features.modules.movement.velocity.impl;

import kotlin.Metadata;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_243;
import net.minecraft.class_2596;
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
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0019\u0010\u0007\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0017¢\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\u000b\u001a\u00020\n2\b\u0010\u0005\u001a\u0004\u0018\u00010\tH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u0019\u0010\u000e\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\rH\u0016¢\u0006\u0004\b\u000e\u0010\u000fJ\u0019\u0010\u0011\u001a\u00020\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u0010H\u0016¢\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\u0013\u0010\u0003J\u000f\u0010\u0014\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\u0014\u0010\u0003R\u0016\u0010\u0016\u001a\u00020\u00158\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0016\u0010\u0017¨\u0006\u0018"},
   d2 = {"Lru/melonity/feature/features/modules/movement/velocity/impl/IntaveOldMode;", "Lru/melonity/feature/features/modules/movement/velocity/VelocityMode;", "<init>", "()V", "Lru/melonity/util/event/events/UpdateLivingEvent;", "event", "", "onUpdate", "(Lru/melonity/util/event/events/UpdateLivingEvent;)V", "Lru/melonity/util/event/events/PacketEvent;", "", "onPacket", "(Lru/melonity/util/event/events/PacketEvent;)Z", "Lru/melonity/util/event/events/EventPlayerMove;", "onPlayerMove", "(Lru/melonity/util/event/events/EventPlayerMove;)V", "Lru/melonity/util/event/events/EventInput;", "onInput", "(Lru/melonity/util/event/events/EventInput;)V", "onEnable", "onDisable", "", "count", "I", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class IntaveOldMode extends VelocityMode {
   private int count;

   public IntaveOldMode() {
      super("Intave Old");
   }

   @Override
   public void onUpdate(@Nullable UpdateLivingEvent event) {
      class_746 player = this.getMc().field_1724;
      if (player == null) {
         this.count = 6;
         return;
      }

      if (player.field_6235 <= 0) {
         // Not hurt: arm the window so the next impulse that arrives is the one dropped.
         this.count = 0;
         return;
      }

      if (this.count < 6) {
         this.count++;
      }

      // While the window is open onPacket() throws the impulse away, but Intave still
      // expects the client to bleed a little speed, so the motion is damped by hand
      // instead of being left completely untouched.
      if (this.count < 4 && this.hasVelocityHandle()) {
         class_243 velocity = player.method_18798();
         player.method_18799(new class_243(velocity.field_1352 * 0.91, velocity.field_1351, velocity.field_1350 * 0.91));
      }
   }

   @Override
   public boolean onPacket(@Nullable PacketEvent event) {
      if (this.hasVelocityHandle() && event != null) {
         class_2596 local2_0 = event.packet;
         if (event.isReceive() && local2_0 instanceof class_2743) {
            class_746 var3 = this.getMc().field_1724;
            if (var3 != null ? ((class_2743)local2_0).method_11818() == var3.method_5628() : false) {
               int var4 = this.count;
               if (0 <= var4 ? var4 < 4 : false) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Override
   public void onPlayerMove(@Nullable EventPlayerMove event) {
      if (this.hasVelocityHandle()) {
         class_746 var10000 = this.getMc().field_1724;
         if (var10000 != null) {
            class_746 local2_1 = var10000;
            class_243 local4_0 = local2_1.method_18798();
            if (local4_0.field_1351 > 0.0 && local4_0.field_1351 < 0.1F || local2_1.field_6017 > 0.3F && local2_1.field_6017 < 0.4F) {
               local2_1.method_60478().method_18805(0.83, 1.0, 0.83);
            }
         }
      }
   }

   @Override
   public void onInput(@Nullable EventInput event) {
   }

   @Override
   public void onEnable() {
      this.count = 6;
      if (this.getMc().method_1542()) {
         System.out.println("This module not required to use in SinglePlayer!");
      }
   }

   @Override
   public void onDisable() {
      this.count = 6;
   }
}
