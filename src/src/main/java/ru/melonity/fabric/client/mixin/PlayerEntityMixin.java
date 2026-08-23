package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1313;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_310;
import org.joml.Vector4d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.ILivingEntity;
import ru.melonity.fabric.client.model.IPlayer;
import ru.melonity.util.event.events.EventPlayerJump;
import ru.melonity.util.event.events.EventPlayerTravel;

@Environment(EnvType.CLIENT)
@Mixin({class_1657.class})
public class PlayerEntityMixin implements IPlayer {
   @Unique
   private Vector4d espPosition;

   @Inject(
      method = {"tickNewAi"},
      at = {@At("RETURN")}
   )
   protected void tickNewAi(CallbackInfo callbackInfo) {
      ILivingEntity livingEntity = (ILivingEntity)this;
      livingEntity.setPitchHead(((class_1657)(Object)this).method_36455());
   }

   @Inject(
      method = {"travel"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void onTravelhookPost(class_243 movementInput, CallbackInfo ci) {
      EventPlayerTravel event = new EventPlayerTravel(movementInput, false);
      Melonity.INSTANCE.getEventBus().fire(event);
      if (event.isCancelled()) {
         class_310.method_1551().field_1724.method_5784(class_1313.field_6308, class_310.method_1551().field_1724.method_18798());
         ci.cancel();
      }
   }

   @Inject(
      method = {"jump"},
      at = {@At("HEAD")}
   )
   private void onJumpPre(CallbackInfo ci) {
      Melonity.INSTANCE.getEventBus().fire(new EventPlayerJump(true));
   }

   @Inject(
      method = {"jump"},
      at = {@At("RETURN")}
   )
   private void onJumpPost(CallbackInfo ci) {
      Melonity.INSTANCE.getEventBus().fire(new EventPlayerJump(false));
   }

   @Override
   public Vector4d espPosition() {
      return this.espPosition;
   }

   @Override
   public void setEspPosition(Vector4d position) {
      this.espPosition = position;
   }
}
