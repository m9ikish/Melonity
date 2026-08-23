package ru.melonity.api.instanceContainer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public interface InstanceInitializer {
   String instanceClassName();

   @NotNull
   Class<?> loadInstanceClass(Class<?> var1);

   Object createInstance(Class<?> var1) throws Exception;
}
