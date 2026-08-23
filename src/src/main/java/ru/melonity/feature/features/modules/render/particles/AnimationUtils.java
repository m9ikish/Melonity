package ru.melonity.feature.features.modules.render.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_3532;

@Environment(EnvType.CLIENT)
public class AnimationUtils {
   public static long oldTime;
   long mc;
   public float anim;
   public float to;
   public float speed;

   public AnimationUtils(float anim, float to, float speed) {
      this.anim = anim;
      this.to = to;
      this.speed = speed;
      this.mc = System.currentTimeMillis();
   }

   public float getAnim() {
      int local1_1 = (int)((System.currentTimeMillis() - this.mc) / 5L);
      if (local1_1 > 0) {
         this.mc = System.currentTimeMillis();
      }

      for (int local2_0 = 0; local2_0 < local1_1; local2_0++) {
         this.anim = animateLerp(this.anim, this.to, this.speed);
      }

      return this.anim;
   }

   public static float animateLerp(float current, float target, float speed) {
      boolean local3_0 = target > current;
      float local4_1 = (float)(System.currentTimeMillis() - oldTime) / 10.0F;
      double local5_2 = (double)(Math.max(target, current) - Math.min(target, current));
      double local7_3 = local5_2 * (double)speed * (double)local4_1;
      Number local9_4 = (float)(local3_0 ? (double)current + local7_3 : (double)current - local7_3);
      if (Float.isNaN(current)) {
         ;
      }

      return local9_4.floatValue();
   }

   public float getAngleAnim() {
      int local1_1 = (int)((System.currentTimeMillis() - this.mc) / 5L);
      if (local1_1 > 0) {
         this.mc = System.currentTimeMillis();
      }

      for (int local2_0 = 0; local2_0 < local1_1; local2_0++) {
         this.anim = (float)this.lerpAngle(this.anim, this.to, this.speed);
      }

      return class_3532.method_15393(this.anim);
   }

   public void setAnim(float anim) {
      this.anim = anim;
      this.mc = System.currentTimeMillis();
   }

   double lerpAngle(float start, float end, float amount) {
      float local4_0 = (end - start + 180.0F) % 360.0F - 180.0F;
      return (double)(local4_0 * amount + start);
   }
}
