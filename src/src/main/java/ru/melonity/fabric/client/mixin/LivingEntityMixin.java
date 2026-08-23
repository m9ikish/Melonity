package ru.melonity.fabric.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1293;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.ILivingEntity;
import ru.melonity.fabric.client.model.IMobEffectInstance;
import ru.melonity.feature.features.modules.player.GappleTimerFeature;
import ru.melonity.feature.features.modules.render.SwingAnimationFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.util.event.events.JumpEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_1309.class})
public class LivingEntityMixin implements ILivingEntity {
   @Shadow
   private int field_6228;
   @Shadow
   protected double field_6224;
   @Shadow
   protected double field_6245;
   @Shadow
   protected double field_6263;
   @Shadow
   protected double field_6284;
   @Shadow
   protected double field_6221;
   @Shadow
   protected int field_6210;
   @Unique
   private float rotationPitchHead;
   @Unique
   private float prevRotationPitchHead;

   @Override
   public void resetJumpDelay() {
      this.field_6228 = 0;
   }

   @Override
   public void setPitchHead(float rotationPitchHead) {
      this.rotationPitchHead = rotationPitchHead;
   }

   @Override
   public float prevPitchHead() {
      return this.prevRotationPitchHead;
   }

   @Override
   public float pitchHead() {
      return this.rotationPitchHead;
   }

   @Override
   public double serverX() {
      return this.field_6224;
   }

   @Override
   public double serverY() {
      return this.field_6245;
   }

   @Override
   public double serverZ() {
      return this.field_6263;
   }

   @Override
   public void setServerX(double newX) {
      this.field_6224 = newX;
   }

   @Override
   public void setServerY(double newY) {
      this.field_6245 = newY;
   }

   @Override
   public void setServerZ(double newZ) {
      this.field_6263 = newZ;
   }

   @Override
   public void setServerYaw(float newYaw) {
      this.field_6284 = (double)newYaw;
   }

   @Override
   public void setServerPitch(float newPitch) {
      this.field_6221 = (double)newPitch;
   }

   @Override
   public void setBodyTrackingIncrements(int newBTI) {
      this.field_6210 = newBTI;
   }

   @Inject(
      method = {"baseTick"},
      at = {@At("RETURN")}
   )
   public void baseTick(CallbackInfo callbackInfo) {
      this.prevRotationPitchHead = this.rotationPitchHead;
   }

   @Inject(
      method = {"getHandSwingDuration"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getHandSwingDuration(CallbackInfoReturnable<Integer> cir) {
      SwingAnimationFeature swingAnimation = Melonity.INSTANCE.getFeatureManager().getFeature(SwingAnimationFeature.class).get();
      if (swingAnimation.isEnabled() && (class_1309)(Object)this == class_310.method_1551().field_1724) {
         cir.setReturnValue(10);
      }
   }

   @Inject(
      method = {"consumeItem"},
      at = {@At("HEAD")}
   )
   protected void consumeItem(CallbackInfo callbackInfo) {
      GappleTimerFeature gappleTimerFeature = Melonity.INSTANCE.getFeatureManager().getFeature(GappleTimerFeature.class).orElseThrow();
      int ticks = gappleTimerFeature.getTimeSetting().getValue().intValue() * 20;
      if ((class_1309)(Object)this == class_310.method_1551().field_1724 && gappleTimerFeature.isEnabled()) {
         class_1657 player = (class_1657)(Object)this;
         class_1792 item = player.method_6030().method_7909();
         if ((item == class_1802.field_8463 || item == class_1802.field_8367) && !player.method_7357().method_7904(item)) {
            player.method_7357().method_7906(item, ticks);
         }
      }
   }

   @Inject(
      method = {"onStatusEffectApplied"},
      at = {@At("HEAD")}
   )
   protected void onStatusEffectApplied(class_1293 mobEffectInstance, class_1297 source, CallbackInfo ci) {
      ((IMobEffectInstance)mobEffectInstance).setTimes((long)mobEffectInstance.method_5584());
   }

   @Inject(
      method = {"onStatusEffectUpgraded"},
      at = {@At("HEAD")}
   )
   protected void onStatusEffectUpgraded(class_1293 mobEffectInstance, boolean reapplyEffect, class_1297 source, CallbackInfo ci) {
      ((IMobEffectInstance)mobEffectInstance).setTimes((long)mobEffectInstance.method_5584());
   }

   @Inject(
      method = {"onStatusEffectRemoved"},
      at = {@At("HEAD")}
   )
   protected void onStatusEffectRemoved(class_1293 mobEffectInstance, CallbackInfo ci) {
      ((IMobEffectInstance)mobEffectInstance).setTimes(-1L);
   }

   @Inject(
      method = {"jump"},
      at = {@At("HEAD")}
   )
   public void jump(CallbackInfo callbackInfo) {
      class_1309 living = (class_1309)(Object)this;
      if (living == class_310.method_1551().field_1724) {
         Melonity.INSTANCE.getEventBus().fire(new JumpEvent());
      }
   }

   @Redirect(
      method = {"turnHead"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"
      )
   )
   public float getYawHead(class_1309 instance) {
      return (Object)this instanceof class_746 ? RotationManager.INSTANCE.getVisualYaw() : instance.method_36454();
   }

   @ModifyExpressionValue(
      method = {"jump", "tick", "baseTick", "getVelocityMultiplier"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"
      )}
   )
   private float modifyYaw(float original) {
      return (Object)this instanceof class_746 ? RotationManager.INSTANCE.getYaw() : original;
   }

   @Redirect(
      method = {"jump", "tick", "baseTick", "getVelocityMultiplier"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;getPitch()F"
      )
   )
   public float MELgetPitch(class_1309 instance) {
      return instance instanceof class_746 ? RotationManager.INSTANCE.getPitch() : instance.method_36455();
   }
}
