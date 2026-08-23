package ru.melonity.util.event.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.event.Event;

@Environment(EnvType.CLIENT)
public record KeyEvent(int key) implements Event {
}
