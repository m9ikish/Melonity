package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_638;
import net.minecraft.class_7202;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin({class_638.class})
public interface ClientWorldMixin1 {
   @Accessor("pendingUpdateManager")
   class_7202 getPendingManager();
}
