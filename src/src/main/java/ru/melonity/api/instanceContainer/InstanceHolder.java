package ru.melonity.api.instanceContainer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface InstanceHolder {
   String instanceClassName() throws Throwable;

   Class<?> instanceClass() throws Throwable;

   <T> T instance(Class<?> var1) throws Throwable;
}
