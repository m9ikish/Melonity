package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1685;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin({class_1685.class})
public class TridentEntityMixin {
   @Shadow
   private boolean field_7648;
   @Unique
   private long lastDodgeAttempt = 0L;

   @Inject(
      method = {"tick"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/projectile/TridentEntity;getOwner()Lnet/minecraft/entity/Entity;"
      )}
   )
   public void tick(CallbackInfo callbackInfo) {
      if (!this.field_7648) {
         long currentTime = System.currentTimeMillis();
         if (currentTime - this.lastDodgeAttempt >= 500L) {
            class_310 mc = class_310.method_1551();
            if (mc.field_1724 != null && mc.field_1687 != null) {
               class_1685 trident = (class_1685)(Object)this;
               if (trident.method_24921() != mc.field_1724) {
                  class_243 playerPos = mc.field_1724.method_19538();
                  class_243 tridentPos = trident.method_19538();
                  class_243 tridentVelocity = trident.method_18798();
                  double distanceToPlayer = playerPos.method_1022(tridentPos);
                  if (!(distanceToPlayer > 35.0)) {
                     if (!(tridentVelocity.method_1027() < 0.01)) {
                        class_243 normalizedVelocity = tridentVelocity.method_1029();
                        class_243 toPlayer = playerPos.method_1020(tridentPos).method_1029();
                        double alignment = normalizedVelocity.method_1026(toPlayer);
                        if (!(alignment < 0.5)) {
                           class_243 predictedImpactPoint = this.calculateImpactPoint(tridentPos, tridentVelocity, playerPos, mc.field_1724.method_18798());
                           if (predictedImpactPoint != null) {
                              double impactDistance = playerPos.method_1022(predictedImpactPoint);
                              double timeToImpact = distanceToPlayer / tridentVelocity.method_1033();
                              if (impactDistance < 2.5 && timeToImpact < 2.0 && timeToImpact > 0.3) {
                                 this.lastDodgeAttempt = currentTime;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Unique
   private class_243 calculateImpactPoint(class_243 tridentPos, class_243 tridentVelocity, class_243 playerPos, class_243 playerVelocity) {
      double steps = 40.0;
      class_243 currentTridentPos = tridentPos;
      class_243 currentPlayerPos = playerPos;
      double gravity = 0.03;
      double drag = 0.99;
      class_243 currentVelocity = tridentVelocity;

      for (int i = 0; (double)i < steps; i++) {
         currentTridentPos = currentTridentPos.method_1019(currentVelocity);
         currentPlayerPos = currentPlayerPos.method_1019(playerVelocity.method_1021(0.05));
         currentVelocity = currentVelocity.method_1021(drag);
         currentVelocity = currentVelocity.method_1023(0.0, gravity, 0.0);
         double distanceToPlayer = currentTridentPos.method_1022(currentPlayerPos);
         if (distanceToPlayer < 1.5) {
            return currentTridentPos;
         }

         class_3959 context = new class_3959(tridentPos, currentTridentPos, class_3960.field_17558, class_242.field_1348, (class_1685)(Object)this);
         class_3965 hitResult = class_310.method_1551().field_1687.method_17742(context);
         if (hitResult.method_17783() == class_240.field_1332) {
            return null;
         }

         if (currentTridentPos.field_1351 < (double)class_310.method_1551().field_1687.method_31607()) {
            return null;
         }
      }

      return null;
   }
}
