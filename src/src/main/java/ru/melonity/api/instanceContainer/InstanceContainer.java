package ru.melonity.api.instanceContainer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface InstanceContainer {
   <T> T instance(Class<T> var1);

   void registerHolder(Class<?> var1, InstanceHolder var2);
}
