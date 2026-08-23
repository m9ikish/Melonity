package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_309;
import net.minecraft.class_310;
import net.minecraft.class_6599;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.ui.menu.clickui.Menu;
import ru.melonity.util.event.events.KeyEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_309.class})
public class KeyboardMixin {
   @Inject(
      method = {"onKey"},
      at = {@At("HEAD")}
   )
   public void onKey(long windowPointer, int key, int scancode, int action, int modifiers, CallbackInfo callbackInfo) {
      if (windowPointer == class_310.method_1551().method_22683().method_4490()
         && action == 1
         && (
            !(class_310.method_1551().field_1755 instanceof class_6599)
               || ((class_6599)class_310.method_1551().field_1755).field_34800 <= System.currentTimeMillis() - 20L
         )
         && (class_310.method_1551().field_1755 == null || class_310.method_1551().field_1755 instanceof Menu && key != -1 && key != 65540 && key != 65539)) {
         Melonity.INSTANCE.getEventBus().fire(new KeyEvent(key));
      }
   }
}
