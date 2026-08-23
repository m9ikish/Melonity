package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_332;
import net.minecraft.class_337;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IBossBarOffset;
import ru.melonity.util.event.events.BossbarRenderEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_337.class})
public abstract class BossbarHudMixin implements IBossBarOffset {
   @Inject(
      method = {"render"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void render(class_332 context, CallbackInfo callbackInfo) {
      BossbarRenderEvent event = new BossbarRenderEvent(context.method_51448());
      Melonity.INSTANCE.getEventBus().fire(event);
      if (event.isCancelled()) {
         callbackInfo.cancel();
      }
   }
}
