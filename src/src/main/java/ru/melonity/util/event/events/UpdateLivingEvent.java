package ru.melonity.util.event.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.event.CancelableEvent;

@Environment(EnvType.CLIENT)
public class UpdateLivingEvent extends CancelableEvent {
}
