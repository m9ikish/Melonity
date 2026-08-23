package ru.melonity.api.components;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class ServiceDescriptor {
   public final String serviceInterface;
   public final String serviceImplementation;
   public final boolean overrides;
   public final ServiceDescriptor.LoadMode loadMode;

   public ServiceDescriptor(String serviceInterface, String serviceImplementation, boolean overrides, @NotNull ServiceDescriptor.LoadMode loadMode) {
      this.serviceInterface = serviceInterface;
      this.serviceImplementation = serviceImplementation;
      this.overrides = overrides;
      this.loadMode = loadMode;
   }

   @Environment(EnvType.CLIENT)
   public static enum LoadMode {
      FIRST,
      BEFORE_FEATURES,
      AFTER_FEATURES,
      AFTER_EVENTBUS,
      BEFORE_UI;
   }
}
