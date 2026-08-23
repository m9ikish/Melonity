package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1313;
import net.minecraft.class_243;
import net.minecraft.class_2848;
import net.minecraft.class_299;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_744;
import net.minecraft.class_746;
import net.minecraft.class_2828.class_2829;
import net.minecraft.class_2828.class_2830;
import net.minecraft.class_2828.class_2831;
import net.minecraft.class_2828.class_5911;
import net.minecraft.class_2848.class_2849;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.ILocalPlayer;
import ru.melonity.feature.features.modules.player.NoPushFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.util.event.events.EventPlayerMove;
import ru.melonity.util.event.events.InventoryCloseEvent;
import ru.melonity.util.event.events.PreMotionEvent;
import ru.melonity.util.event.events.TickEvent;
import ru.melonity.util.event.events.UsingItemEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_746.class})
public abstract class ClientPlayerEntityMixin implements ILocalPlayer {
   @Shadow
   private boolean field_3936;
   @Shadow
   private boolean field_3919;
   @Shadow
   private double field_3926;
   @Shadow
   private double field_3940;
   @Shadow
   private double field_3924;
   @Shadow
   private float field_3941;
   @Shadow
   private float field_3925;
   @Shadow
   private int field_3923;
   @Shadow
   private boolean field_3920;
   @Shadow
   private boolean field_3927;
   @Shadow
   public class_744 field_3913;
   @Shadow
   @Final
   private class_299 field_3930;

   @Shadow
   private void method_46742() {
   }

   @Shadow
   protected boolean method_3134() {
      return false;
   }

   @Shadow
   protected abstract boolean method_48300();

   @Inject(
      method = {"move"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void move(class_1313 type, class_243 position, CallbackInfo callbackInfo) {
      EventPlayerMove event = new EventPlayerMove(type, position.field_1352, position.field_1351, position.field_1350);
      Melonity.INSTANCE.getEventBus().fire(event);
      if (event.isCancelled()) {
         callbackInfo.cancel();
      }

      new class_243(event.x, event.y, event.z);
   }

   @Inject(
      method = {"closeHandledScreen"},
      at = {@At("HEAD")}
   )
   public void closeHandledScreen(CallbackInfo callbackInfo) {
      Melonity.INSTANCE.getEventBus().fire(new InventoryCloseEvent());
   }

   @Inject(
      method = {"sendMovementPackets"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void sendMovementPackets(CallbackInfo callbackInfo) {
      class_746 player = (class_746)(Object)this;
      this.method_46742();
      boolean bl = player.method_5715();
      if (bl != this.field_3936) {
         class_2849 mode = bl ? class_2849.field_12979 : class_2849.field_12984;
         player.field_3944.method_52787(new class_2848(player, mode));
         this.field_3936 = bl;
      }

      if (this.method_3134()) {
         PreMotionEvent eventPreMotion = new PreMotionEvent(
            RotationManager.INSTANCE.getYaw(),
            RotationManager.INSTANCE.getPitch(),
            player.method_23317(),
            player.method_23318(),
            player.method_23321(),
            player.method_24828()
         );
         Melonity.INSTANCE.getEventBus().fire(eventPreMotion);
         if (eventPreMotion.isCancelled()) {
            return;
         }

         double d = eventPreMotion.getPosX() - this.field_3926;
         double e = eventPreMotion.getPosY() - this.field_3940;
         double f = eventPreMotion.getPosZ() - this.field_3924;
         double g = (double)(eventPreMotion.getYaw() - this.field_3941);
         double h = (double)(eventPreMotion.getPitch() - this.field_3925);
         this.field_3923++;
         boolean bl2 = class_3532.method_41190(d, e, f) > class_3532.method_33723(2.0E-4) || this.field_3923 >= 20;
         boolean bl3 = g != 0.0 || h != 0.0;
         if (player.method_5765()) {
            class_243 vec3d = player.method_18798();
            player.field_3944
               .method_52787(
                  new class_2830(vec3d.field_1352, -999.0, vec3d.field_1350, eventPreMotion.getYaw(), eventPreMotion.getPitch(), eventPreMotion.isOnGround())
               );
            bl2 = false;
         } else if (bl2 && bl3) {
            player.field_3944
               .method_52787(
                  new class_2830(
                     eventPreMotion.getPosX(),
                     eventPreMotion.getPosY(),
                     eventPreMotion.getPosZ(),
                     eventPreMotion.getYaw(),
                     eventPreMotion.getPitch(),
                     eventPreMotion.isOnGround()
                  )
               );
         } else if (bl2) {
            player.field_3944
               .method_52787(new class_2829(eventPreMotion.getPosX(), eventPreMotion.getPosY(), eventPreMotion.getPosZ(), eventPreMotion.isOnGround()));
         } else if (bl3) {
            player.field_3944.method_52787(new class_2831(eventPreMotion.getYaw(), eventPreMotion.getPitch(), eventPreMotion.isOnGround()));
         } else if (this.field_3920 != player.method_24828()) {
            player.field_3944.method_52787(new class_5911(eventPreMotion.isOnGround()));
         }

         if (bl2) {
            this.field_3926 = eventPreMotion.getPosX();
            this.field_3940 = eventPreMotion.getPosY();
            this.field_3924 = eventPreMotion.getPosZ();
            this.field_3923 = 0;
         }

         if (bl3) {
            this.field_3941 = eventPreMotion.getYaw();
            this.field_3925 = eventPreMotion.getPitch();
         }

         this.field_3920 = eventPreMotion.isOnGround();
         this.field_3927 = (Boolean)class_310.method_1551().field_1690.method_42423().method_41753();
      }

      callbackInfo.cancel();
   }

   @Redirect(
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z",
         ordinal = 0
      ),
      method = {"tickMovement()V"}
   )
   private boolean isPlayerUsingItem(class_746 player) {
      UsingItemEvent usingItemEvent = new UsingItemEvent();
      Melonity.INSTANCE.getEventBus().fire(usingItemEvent);
      return usingItemEvent.isCancelled() ? false : player.method_6115();
   }

   @Inject(
      method = {"pushOutOfBlocks"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void pushOutOfBlocks(double x, double z, CallbackInfo ci) {
      NoPushFeature noPush = Melonity.INSTANCE.featureManager.noPushFeature;
      if (noPush.isEnabled() && noPush.noPushSetting.contains("nopush.block")) {
         ci.cancel();
      }
   }

   @Inject(
      method = {"tick"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V",
         shift = Shift.BEFORE
      )}
   )
   public void tick(CallbackInfo callbackInfo) {
      Melonity.INSTANCE.getEventBus().fire(new TickEvent());
   }

   @Override
   public boolean serverSprintState() {
      return this.field_3919;
   }

   @Override
   public void setServerSprintState(boolean state) {
      this.field_3919 = state;
   }

   @Override
   public float getXRotLast() {
      return this.field_3925;
   }

   @Override
   public boolean canStartSprint() {
      return this.method_48300();
   }
}
