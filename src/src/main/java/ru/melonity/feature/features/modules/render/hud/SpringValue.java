package ru.melonity.feature.features.modules.render.hud;

import kotlin.Metadata;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0002¢\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\r\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0002¢\u0006\u0004\b\r\u0010\u000bJ\r\u0010\u000e\u001a\u00020\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ\u0015\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0002¢\u0006\u0004\b\u0011\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u0012R\u0014\u0010\u0004\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010\u0012R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\u0012R\u0016\u0010\u0013\u001a\u00020\u00028\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0013\u0010\u0012R\u0016\u0010\u0014\u001a\u00020\u00028\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0014\u0010\u0012R\u0016\u0010\u0015\u001a\u00020\u00028\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0015\u0010\u0012¨\u0006\u0016"},
   d2 = {"Lru/melonity/feature/features/modules/render/hud/SpringValue;", "", "", "initial", "tension", "friction", "<init>", "(FFF)V", "newTarget", "", "setTarget", "(F)V", "deltaTime", "update", "get", "()F", "force", "impulse", "F", "value", "target", "velocity", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class SpringValue {
   private final float initial;
   private final float tension;
   private final float friction;
   private float value;
   private float target;
   private float velocity;

   public SpringValue(float initial, float tension, float friction) {
      this.initial = initial;
      this.tension = tension;
      this.friction = friction;
      this.value = this.initial;
      this.target = this.initial;
   }

   public final void setTarget(float newTarget) {
      this.target = newTarget;
   }

   public final void update(float deltaTime) {
      float local2_0 = this.value - this.target;
      float local3_1 = -this.tension * local2_0;
      float local4_2 = -this.friction * this.velocity;
      float local5_3 = local3_1 + local4_2;
      this.velocity += local5_3 * deltaTime;
      this.value = this.value + this.velocity * deltaTime;
      if (Math.abs(local2_0) < 0.01F && Math.abs(this.velocity) < 0.1F) {
         this.value = this.target;
         this.velocity = 0.0F;
      }
   }

   public final float get() {
      return this.value;
   }

   public final void impulse(float force) {
      this.velocity += force;
   }
}
