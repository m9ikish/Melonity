package ru.melonity.api.instanceContainer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class InstanceNotRegisteredException extends IllegalStateException {
   public InstanceNotRegisteredException(String message) {
      super(message);
   }
}
