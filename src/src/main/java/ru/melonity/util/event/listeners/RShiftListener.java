package ru.melonity.util.event.listeners;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import ru.melonity.Melonity;
import ru.melonity.ui.menu.clickui.Menu;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.annotation.ListenerClass;
import ru.melonity.util.event.events.KeyEvent;

@Environment(EnvType.CLIENT)
@ListenerClass
public class RShiftListener {
   private final EventListener<KeyEvent> onKeyPressed = event -> {
      if (event.key() == 344) {
         Melonity.INSTANCE.getMenu().delay = System.currentTimeMillis();
         Melonity.INSTANCE.getMenu().closing = false;
         if (class_310.method_1551().field_1755 instanceof Menu) {
            Melonity.INSTANCE.getMenu().closing = true;
         } else {
            class_310.method_1551().method_1507(Melonity.INSTANCE.getMenu());
         }
      }
   };
}
