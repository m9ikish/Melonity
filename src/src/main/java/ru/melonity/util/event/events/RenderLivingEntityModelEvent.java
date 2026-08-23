package ru.melonity.util.event.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_4587;
import net.minecraft.class_583;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public record RenderLivingEntityModelEvent(class_583 model, class_4587 matrixStack, class_1309 entity) implements Event {
}
