package ru.melonity.fabric.client.mixin;

import java.util.Map;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_337;
import net.minecraft.class_345;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin({class_337.class})
public interface BossBarHudAccessor {
   @Accessor("bossBars")
   Map<UUID, class_345> getBossBars();
}
