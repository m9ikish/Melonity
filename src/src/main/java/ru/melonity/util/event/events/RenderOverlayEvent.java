package ru.melonity.util.event.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public record RenderOverlayEvent(class_4587 matrixStack, class_332 graphics) implements Event {
}
