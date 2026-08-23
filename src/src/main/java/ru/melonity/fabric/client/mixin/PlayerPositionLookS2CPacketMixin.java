package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2708;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import ru.melonity.fabric.client.model.IPlayerPositionLookS2CPacket;

@Environment(EnvType.CLIENT)
@Mixin({class_2708.class})
public class PlayerPositionLookS2CPacketMixin implements IPlayerPositionLookS2CPacket {
   @Shadow
   @Mutable
   private float field_12393;
   @Shadow
   @Mutable
   private float field_12391;

   @Override
   public void setYaw(float yaw) {
      this.field_12393 = yaw;
   }

   @Override
   public void setPitch(float pitch) {
      this.field_12391 = pitch;
   }
}
