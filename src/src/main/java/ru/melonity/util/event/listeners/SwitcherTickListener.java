package ru.melonity.util.event.listeners;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.annotation.ListenerClass;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.inventory.old.switcher.Switcher;
import ru.melonity.util.inventory.old.switcher.Switchers;

@Environment(EnvType.CLIENT)
@ListenerClass
public class SwitcherTickListener {
   private final EventListener<UpdateEvent> onUpdate = event -> Switchers.all().forEach(Switcher::tick);
}
