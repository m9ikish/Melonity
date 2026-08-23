package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_5900;
import net.minecraft.class_634;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin({class_634.class})
public class ClientPlayNetworkHandlerMixin {
   @Inject(
      method = {"onTeam"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void handleTeamPacketError(class_5900 packet, CallbackInfo ci) {
      try {
         if (packet.method_34177().contains("collideRule_")) {
            ci.cancel();
         }
      } catch (Exception var4) {
         ci.cancel();
      }
   }
}
