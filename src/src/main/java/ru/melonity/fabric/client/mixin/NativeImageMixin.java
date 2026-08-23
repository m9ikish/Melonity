package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1011;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ru.melonity.fabric.client.model.INativeImage;

@Environment(EnvType.CLIENT)
@Mixin({class_1011.class})
public class NativeImageMixin implements INativeImage {
   @Shadow
   private long field_4988;

   @Override
   public long pointer() {
      return this.field_4988;
   }
}
