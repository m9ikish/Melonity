package ru.melonity.util.event.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import org.joml.Matrix4f;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public record PostRenderWorldEvent(class_4587 matrixStack, float tickDelta, Matrix4f projection) implements Event {
}
