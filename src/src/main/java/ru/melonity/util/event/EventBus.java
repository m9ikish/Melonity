package ru.melonity.util.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface EventBus {
   void fire(Event var1);

   void listen(Object var1);

   void unlisten(Object var1);

   default void registerListeners(Object... listeners) {
      for (Object listener : listeners) {
         this.listen(listener);
      }
   }
}
