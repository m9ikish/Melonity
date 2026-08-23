package ru.melonity.feature.features.command;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface Prefix {
   String prefix();

   void setPrefix(String var1);
}
