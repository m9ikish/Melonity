package ru.melonity.util.altmanager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface ProfileProvider {
   Profile createProfile(String var1, String var2);
}
