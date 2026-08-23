package ru.melonity.util.event.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2338;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public record EventObsidianPlace(class_2338 pos) implements Event {
}
