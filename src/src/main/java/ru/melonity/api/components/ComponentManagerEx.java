package ru.melonity.api.components;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface ComponentManagerEx extends ComponentManager {
   void registerService(Class<?> var1, Class<?> var2, boolean var3, ServiceDescriptor.LoadMode var4);

   default void registerService(Class<?> serviceInterface, Class<?> implementation) {
      this.registerService(serviceInterface, implementation, true, ServiceDescriptor.LoadMode.FIRST);
   }
}
