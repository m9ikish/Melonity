package ru.melonity.api.components;

import java.util.Collections;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

@Environment(EnvType.CLIENT)
public interface ComponentManager {
   <T> T service(@NotNull Class<T> var1);

   @NotNull
   @Unmodifiable
   default <T> List<T> services(@NotNull Class<T> serviceClass) {
      T service = this.service(serviceClass);
      return service == null ? Collections.emptyList() : Collections.singletonList(service);
   }
}
