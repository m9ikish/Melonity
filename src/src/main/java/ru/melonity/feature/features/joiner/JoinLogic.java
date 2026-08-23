package ru.melonity.feature.features.joiner;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface JoinLogic {
   String name();

   void enabled();

   void tick();

   void handleMessage(String var1);

   boolean need();
}
