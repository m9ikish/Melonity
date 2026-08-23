package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_3532;
import ru.melonity.util.event.CancelableEvent;
import ru.melonity.util.minecraft.MovementUtil;

@Environment(EnvType.CLIENT)
public class EventInput extends CancelableEvent {
   public float forward;
   public float strafe;
   public boolean jump;
   public boolean sneaking;

   public void setYaw(float yaw, float direction) {
      double local3_7 = class_3532.method_15338(Math.toDegrees(MovementUtil.direction(direction, (double)this.forward, (double)this.strafe)));
      if (this.forward != 0.0F || this.strafe != 0.0F) {
         float local5_4 = 0.0F;
         float local6_5 = 0.0F;
         float local7_6 = Float.MAX_VALUE;

         for (float local8_3 = -1.0F; local8_3 <= 1.0F; local8_3++) {
            for (float local9_2 = -1.0F; local9_2 <= 1.0F; local9_2++) {
               if (local9_2 != 0.0F || local8_3 != 0.0F) {
                  double local10_0 = class_3532.method_15338(Math.toDegrees(MovementUtil.direction(yaw, (double)local8_3, (double)local9_2)));
                  double local12_1 = Math.abs(local3_7 - local10_0);
                  if (local12_1 < (double)local7_6) {
                     local7_6 = (float)local12_1;
                     local5_4 = local8_3;
                     local6_5 = local9_2;
                  }
               }
            }
         }

         this.forward = local5_4;
         this.strafe = local6_5;
      }
   }

   @Generated
   public EventInput(float forward, float strafe, boolean jump, boolean sneaking) {
      this.forward = forward;
      this.strafe = strafe;
      this.jump = jump;
      this.sneaking = sneaking;
   }

   @Generated
   public float getForward() {
      return this.forward;
   }

   @Generated
   public float getStrafe() {
      return this.strafe;
   }

   @Generated
   public boolean isJump() {
      return this.jump;
   }

   @Generated
   public boolean isSneaking() {
      return this.sneaking;
   }

   @Generated
   public void setForward(float forward) {
      this.forward = forward;
   }

   @Generated
   public void setStrafe(float strafe) {
      this.strafe = strafe;
   }

   @Generated
   public void setJump(boolean jump) {
      this.jump = jump;
   }

   @Generated
   public void setSneaking(boolean sneaking) {
      this.sneaking = sneaking;
   }
}
