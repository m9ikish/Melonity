package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_638;
import net.minecraft.class_638.class_5271;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IClientWorld;
import ru.melonity.util.event.events.EventEntitySpawn;
import ru.melonity.util.minecraft.MinecraftInstance;

@Environment(EnvType.CLIENT)
@Mixin({class_638.class})
public class ClientWorldMixin implements IClientWorld {
   @Shadow
   @Final
   private class_5271 field_24430;

   @Inject(
      method = {"addEntity"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void addEntityHook(class_1297 entity, CallbackInfo ci) {
      if (MinecraftInstance.mc.field_1724 != null && MinecraftInstance.mc.field_1687 != null) {
         EventEntitySpawn ees = new EventEntitySpawn(entity);
         Melonity.INSTANCE.getEventBus().fire(ees);
         if (ees.isCancelled()) {
            ci.cancel();
         }
      }
   }

   @Override
   public void setTimeOfDay(long time) {
      this.field_24430.method_165(time);
   }
}
