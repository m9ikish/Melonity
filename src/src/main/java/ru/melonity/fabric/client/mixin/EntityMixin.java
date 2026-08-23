package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1313;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3481;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_5712;
import net.minecraft.class_746;
import net.minecraft.class_1297.class_5799;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.player.NoPushFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.util.event.events.EventRelativeMove;
import ru.melonity.util.event.events.MoveEvent;
import ru.melonity.util.event.events.StrafeEvent;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.minecraft.MinecraftInstance;

@Environment(EnvType.CLIENT)
@Mixin({class_1297.class})
public class EntityMixin {
   @Shadow
   protected class_243 field_17046;
   @Shadow
   private float field_6003;
   @Shadow
   private int field_5956;

   @Shadow
   protected int method_5676() {
      return 1;
   }

   @Shadow
   protected void method_36975() {
   }

   @Shadow
   protected static class_243 method_18795(class_243 movementInput, float speed, float yaw) {
      return null;
   }

   @Shadow
   private boolean method_51701(class_2338 pos, class_2680 state, boolean playSound, boolean emitEvent, class_243 movement) {
      return false;
   }

   @Shadow
   protected void method_51295() {
   }

   @Shadow
   protected void method_33573() {
   }

   @Shadow
   protected class_243 method_18794(class_243 movement) {
      return null;
   }

   @Shadow
   protected class_243 method_18796(class_243 movement, class_1313 type) {
      return null;
   }

   @Shadow
   private class_243 method_17835(class_243 movement) {
      return null;
   }

   @Shadow
   protected boolean method_39759(class_243 adjustedMovement) {
      return false;
   }

   @Shadow
   protected void method_36974() {
   }

   @Shadow
   protected float method_23326() {
      return 0.0F;
   }

   @Shadow
   protected void method_5623(double heightDifference, boolean onGround, class_2680 state, class_2338 landedPosition) {
   }

   @Shadow
   private boolean method_51702(class_2680 state) {
      return false;
   }

   @Shadow
   protected float method_5867() {
      return 0.0F;
   }

   @Shadow
   protected class_5799 method_33570() {
      return null;
   }

   @Inject(
      method = {"updateVelocity"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void updateVelocityHook(float speed, class_243 movementInput, CallbackInfo ci) {
      if (class_310.method_1551() != null && class_310.method_1551().field_1724 != null) {
         if ((Object)this instanceof class_746) {
            EventRelativeMove event = new EventRelativeMove(
               movementInput,
               speed,
               class_310.method_1551().field_1724.method_36454(),
               method_18795(movementInput, speed, class_310.method_1551().field_1724.method_36454())
            );
            Melonity.INSTANCE.getEventBus().fire(event);
            class_310.method_1551().field_1724.method_18799(class_310.method_1551().field_1724.method_18798().method_1019(event.velocity));
            ci.cancel();
         }
      }
   }

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")}
   )
   public void tick(CallbackInfo callbackInfo) {
      if ((Object)this instanceof class_746) {
         Melonity.INSTANCE.getEventBus().fire(new UpdateEvent());
         Melonity.INSTANCE.getEventBus().fire(new UpdateLivingEvent());
      }
   }

   @Inject(
      at = {@At("HEAD")},
      cancellable = true,
      method = {"Lnet/minecraft/entity/Entity;movementInputToVelocity(Lnet/minecraft/util/math/Vec3d;FF)Lnet/minecraft/util/math/Vec3d;"}
   )
   private static void movementInputToVelocity(class_243 movementInput, float speed, float yaw, CallbackInfoReturnable<class_243> cir) {
      if (MinecraftInstance.mc.field_1724 != null && yaw == MinecraftInstance.mc.field_1724.method_36454()) {
         StrafeEvent event = new StrafeEvent(movementInput, speed, yaw);
         Melonity.INSTANCE.getEventBus().fire(event);
         movementInput = event.movementInput;
         speed = event.speed;
         yaw = event.yaw;
         if (event.isCancelled()) {
            cir.setReturnValue(class_243.field_1353);
            return;
         }
      }

      double d = movementInput.method_1027();
      if (d < 1.0E-7) {
         cir.setReturnValue(class_243.field_1353);
      } else {
         class_243 vec3d = (d > 1.0 ? movementInput.method_1029() : movementInput).method_1021((double)speed);
         float f = class_3532.method_15374(yaw * (float) (Math.PI / 180.0));
         float g = class_3532.method_15362(yaw * (float) (Math.PI / 180.0));
         cir.setReturnValue(
            new class_243(
               vec3d.field_1352 * (double)g - vec3d.field_1350 * (double)f, vec3d.field_1351, vec3d.field_1350 * (double)g + vec3d.field_1352 * (double)f
            )
         );
      }
   }

   @Inject(
      method = {"move"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void move(class_1313 movementType, class_243 movement, CallbackInfo callbackInfo) {
      class_1297 entity = (class_1297)(Object)this;
      if (entity.field_5960) {
         entity.method_5814(
            entity.method_23317() + movement.field_1352, entity.method_23318() + movement.field_1351, entity.method_23321() + movement.field_1350
         );
      } else {
         entity.field_28629 = entity.method_5809();
         if (movementType == class_1313.field_6310) {
            movement = this.method_18794(movement);
            if (movement.equals(class_243.field_1353)) {
               return;
            }
         }

         entity.method_37908().method_16107().method_15396("move");
         if (this.field_17046.method_1027() > 1.0E-7) {
            movement = movement.method_18806(this.field_17046);
            this.field_17046 = class_243.field_1353;
            entity.method_18799(class_243.field_1353);
         }

         movement = this.method_18796(movement, movementType);
         class_243 predictPos = this.method_18796(movement, movementType);
         class_243 predictPos3d = this.method_17835(predictPos);
         if (entity instanceof class_746) {
            class_243 predictedPosition = entity.method_19538().method_1019(predictPos3d);
            class_243 fromPosition = entity.method_19538();
            boolean collidedVertically = predictPos.field_1351 != predictPos3d.field_1351;
            boolean collidedHorizontally = !class_3532.method_20390(predictPos.field_1352, predictPos3d.field_1352)
               || !class_3532.method_20390(predictPos.field_1350, predictPos3d.field_1350);
            boolean onGround = collidedVertically && predictPos.field_1351 < 0.0;
            class_238 bb = entity.method_5829();
            MoveEvent move = new MoveEvent(fromPosition, predictedPosition, movement, onGround, collidedHorizontally, collidedVertically, bb);
            Melonity.INSTANCE.getEventBus().fire(move);
            movement = move.getMotion();
         }

         class_243 vec3d = this.method_17835(movement);
         double d = vec3d.method_1027();
         if (d > 1.0E-7) {
            if (entity.field_6017 != 0.0F && d >= 1.0) {
               class_3965 blockHitResult = entity.method_37908()
                  .method_17742(
                     new class_3959(entity.method_19538(), entity.method_19538().method_1019(vec3d), class_3960.field_36337, class_242.field_36338, entity)
                  );
               if (blockHitResult.method_17783() != class_240.field_1333) {
                  entity.method_38785();
               }
            }

            entity.method_5814(entity.method_23317() + vec3d.field_1352, entity.method_23318() + vec3d.field_1351, entity.method_23321() + vec3d.field_1350);
         }

         entity.method_37908().method_16107().method_15407();
         entity.method_37908().method_16107().method_15396("rest");
         boolean bl = !class_3532.method_20390(movement.field_1352, vec3d.field_1352);
         boolean bl2 = !class_3532.method_20390(movement.field_1350, vec3d.field_1350);
         entity.field_5976 = bl || bl2;
         entity.field_5992 = movement.field_1351 != vec3d.field_1351;
         entity.field_36331 = entity.field_5992 && movement.field_1351 < 0.0;
         if (entity.field_5976) {
            entity.field_34927 = this.method_39759(vec3d);
         } else {
            entity.field_34927 = false;
         }

         entity.method_60607(entity.field_36331, vec3d);
         class_2338 blockPos = entity.method_43260();
         class_2680 blockState = entity.method_37908().method_8320(blockPos);
         this.method_5623(vec3d.field_1351, entity.method_24828(), blockState, blockPos);
         if (entity.method_31481()) {
            entity.method_37908().method_16107().method_15407();
         } else {
            if (entity.field_5976) {
               class_243 vec3d2 = entity.method_18798();
               entity.method_18800(bl ? 0.0 : vec3d2.field_1352, vec3d2.field_1351, bl2 ? 0.0 : vec3d2.field_1350);
            }

            class_2248 block = blockState.method_26204();
            if (movement.field_1351 != vec3d.field_1351) {
               block.method_9502(entity.method_37908(), entity);
            }

            if (entity.method_24828()) {
               block.method_9591(entity.method_37908(), blockPos, blockState, entity);
            }

            class_5799 moveEffect = this.method_33570();
            if (moveEffect.method_33576() && !entity.method_5765()) {
               double e = vec3d.field_1352;
               double f = vec3d.field_1351;
               double g = vec3d.field_1350;
               entity.field_28627 = entity.field_28627 + (float)(vec3d.method_1033() * 0.6);
               class_2338 blockPos2 = entity.method_23312();
               class_2680 blockState2 = entity.method_37908().method_8320(blockPos2);
               boolean bl3 = this.method_51702(blockState2);
               if (!bl3) {
                  f = 0.0;
               }

               entity.field_5973 = entity.field_5973 + (float)vec3d.method_37267() * 0.6F;
               entity.field_5994 = entity.field_5994 + (float)Math.sqrt(e * e + f * f + g * g) * 0.6F;
               if (entity.field_5994 > this.field_6003 && !blockState2.method_26215()) {
                  boolean bl4 = blockPos2.equals(blockPos);
                  boolean bl5 = this.method_51701(blockPos, blockState, moveEffect.method_33578(), bl4, movement);
                  if (!bl4) {
                     bl5 |= this.method_51701(blockPos2, blockState2, false, moveEffect.method_33577(), movement);
                  }

                  if (bl5) {
                     this.field_6003 = this.method_5867();
                  } else if (entity.method_5799()) {
                     this.field_6003 = this.method_5867();
                     if (moveEffect.method_33578()) {
                        this.method_51295();
                     }

                     if (moveEffect.method_33577()) {
                        entity.method_32876(class_5712.field_28156);
                     }
                  }
               } else if (blockState2.method_26215()) {
                  this.method_33573();
               }
            }

            this.method_36974();
            float h = this.method_23326();
            entity.method_18799(entity.method_18798().method_18805((double)h, 1.0, (double)h));
            if (entity.method_37908()
               .method_29556(entity.method_5829().method_1011(1.0E-6))
               .noneMatch(state -> state.method_26164(class_3481.field_21952) || state.method_27852(class_2246.field_10164))) {
               if (this.field_5956 <= 0) {
                  entity.method_20803(-this.method_5676());
               }

               if (entity.field_28629 && (entity.field_27857 || entity.method_5637())) {
                  this.method_36975();
               }
            }

            if (entity.method_5809() && (entity.field_27857 || entity.method_5637())) {
               entity.method_20803(-this.method_5676());
            }

            entity.method_37908().method_16107().method_15407();
         }
      }

      callbackInfo.cancel();
   }

   @Inject(
      method = {"pushAwayFrom"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onPlayerCollision(class_1297 entity, CallbackInfo ci) {
      NoPushFeature noPush = Melonity.INSTANCE.featureManager.noPushFeature;
      if (noPush.isEnabled() && noPush.noPushSetting.contains("nopush.ent")) {
         ci.cancel();
      }
   }

   @Inject(
      method = {"getRotationVector(FF)Lnet/minecraft/util/math/Vec3d;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getRotationVector(float pitch, float yaw, CallbackInfoReturnable<class_243> cir) {
      class_1297 entity = (class_1297)(Object)this;
      if (entity instanceof class_746) {
         yaw = RotationManager.INSTANCE.getYaw();
         pitch = RotationManager.INSTANCE.getPitch();
      }

      float f = pitch * (float) (Math.PI / 180.0);
      float g = -yaw * (float) (Math.PI / 180.0);
      float h = class_3532.method_15362(g);
      float i = class_3532.method_15374(g);
      float j = class_3532.method_15362(f);
      float k = class_3532.method_15374(f);
      cir.setReturnValue(new class_243((double)(i * j), (double)(-k), (double)(h * j)));
   }

   @Inject(
      method = {"getTargetingMargin"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onGetTargetingMargin(CallbackInfoReturnable<Float> info) {
      if (Melonity.INSTANCE.featureManager.hitboxFeature.isEnabled() && class_310.method_1551() != null && class_310.method_1551().field_1724 != null) {
         info.setReturnValue(Melonity.INSTANCE.featureManager.hitboxFeature.getExpansion());
      }
   }
}
