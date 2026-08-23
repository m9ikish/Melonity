package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin({class_1309.class})
public interface LivingEntityAccessor {
   @Accessor("serverX")
   double getServerX();

   @Accessor("serverY")
   double getServerY();

   @Accessor("serverZ")
   double getServerZ();
}
