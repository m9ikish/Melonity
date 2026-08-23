package ru.melonity.util.event.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Matrix4f;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public record RenderWorldPassEvent(Matrix4f modelView, float partialTicks) implements Event {
}
