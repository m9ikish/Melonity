package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2596;
import ru.melonity.util.event.CancelableEvent;

@Environment(EnvType.CLIENT)
public class PacketEvent extends CancelableEvent {
   public final class_2596<?> packet;
   public final PacketEvent.PacketType type;

   public boolean isSend() {
      return this.type == PacketEvent.PacketType.SEND;
   }

   public boolean isReceive() {
      return this.type == PacketEvent.PacketType.RECIEVE;
   }

   @Generated
   public PacketEvent(class_2596<?> packet, PacketEvent.PacketType type) {
      this.packet = packet;
      this.type = type;
   }

   @Generated
   public class_2596<?> getPacket() {
      return this.packet;
   }

   @Generated
   public PacketEvent.PacketType getType() {
      return this.type;
   }

   @Environment(EnvType.CLIENT)
   public static enum PacketType {
      SEND,
      RECIEVE;
   }
}
