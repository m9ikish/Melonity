package ru.melonity.util.event.listeners;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import net.minecraft.class_9015;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.annotation.ListenerClass;
import ru.melonity.util.event.events.UpdateLivingEvent;

@Environment(EnvType.CLIENT)
@ListenerClass
public class FunTimeHealthFixerListener {
   private final EventListener<UpdateLivingEvent> onTick = event -> {
      class_310 local1_2 = class_310.method_1551();
      if (local1_2.method_1558() != null) {
         for (class_1657 local3_1 : local1_2.field_1687.method_18456()) {
            class_9015 local4_0 = class_9015.method_55420(local3_1.method_7334());
            local1_2.field_1687.method_8428().method_1166(local4_0).values().stream().mapToInt(i -> i).forEach(local3_1::method_6033);
         }
      }
   };
}
