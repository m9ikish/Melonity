package ru.melonity.fabric.client.mixin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2535;
import net.minecraft.class_2596;
import net.minecraft.class_7648;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.util.event.events.PacketEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_2535.class})
public class ClientConnectionMixin {
   @Shadow
   private Channel field_11651;

   @Inject(
      method = {"channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   protected void channelRead0(ChannelHandlerContext channelHandlerContext, class_2596<?> packet, CallbackInfo callbackInfo) {
      if (this.field_11651.isOpen()) {
         PacketEvent event = new PacketEvent(packet, PacketEvent.PacketType.RECIEVE);
         Melonity.INSTANCE.getEventBus().fire(event);
         if (event.isCancelled()) {
            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;Z)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void send(class_2596<?> packet, @Nullable class_7648 callbacks, boolean flush, CallbackInfo callbackInfo) {
      PacketEvent event = new PacketEvent(packet, PacketEvent.PacketType.SEND);
      if (Melonity.packetEvent) {
         Melonity.INSTANCE.getEventBus().fire(event);
      }

      if (event.isCancelled()) {
         callbackInfo.cancel();
      }
   }
}
