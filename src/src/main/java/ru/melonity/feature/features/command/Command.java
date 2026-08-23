package ru.melonity.feature.features.command;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface Command {
   void execute(ArgumentPool var1);

   String name();

   String description();
}
