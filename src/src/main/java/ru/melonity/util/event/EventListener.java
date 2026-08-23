package ru.melonity.util.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface EventListener<T extends Event> {
   void callEvent(T var1);
}
