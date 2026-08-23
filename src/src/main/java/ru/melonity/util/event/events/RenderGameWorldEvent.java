package ru.melonity.util.event.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import org.joml.Matrix4f;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public record RenderGameWorldEvent(class_4587 poseStack, Matrix4f matrixStack, float partialTicks, class_4184 camera, Matrix4f projectionMatrix)
   implements Event {
}
