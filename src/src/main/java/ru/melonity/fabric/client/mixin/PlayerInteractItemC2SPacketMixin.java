package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_2540;
import net.minecraft.class_2886;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.feature.rotation.RotationManager;

@Environment(EnvType.CLIENT)
@Mixin({class_2886.class})
public class PlayerInteractItemC2SPacketMixin {
   @Mutable
   @Shadow
   @Final
   private float field_51930;
   @Mutable
   @Shadow
   @Final
   private float field_51931;

   @Inject(
      method = {"<init>(Lnet/minecraft/util/Hand;IFF)V"},
      at = {@At("RETURN")}
   )
   private void onInit(class_1268 hand, int sequence, float yaw, float pitch, CallbackInfo ci) {
      this.field_51930 = RotationManager.INSTANCE.getYaw();
      this.field_51931 = RotationManager.INSTANCE.getPitch();
   }

   @Inject(
      method = {"<init>(Lnet/minecraft/network/PacketByteBuf;)V"},
      at = {@At("RETURN")}
   )
   private void onInit(class_2540 buf, CallbackInfo ci) {
      this.field_51930 = RotationManager.INSTANCE.getYaw();
      this.field_51931 = RotationManager.INSTANCE.getPitch();
   }
}
