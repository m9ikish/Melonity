package ru.melonity.util.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class StateAnimation {
   public double value;
   private float animation;
   private float dropAnimation;
   boolean state;
   public float speed = 1.0F;
   private Easing dropEasing = Easings.BACK_OUT;

   public float animation() {
      return this.animation;
   }

   public float dropAnimation() {
      return this.dropAnimation;
   }

   public StateAnimation() {
   }

   public StateAnimation(float animation) {
      this.animation = animation;
   }

   public StateAnimation(boolean state) {
      this.state = state;
   }

   public void state(boolean state) {
      this.state = state;
   }

   public void update(float weight) {
      weight /= 1.5F;
      this.value = clamp(this.value + (double)(weight * (this.state ? 1.0F : -1.0F) * this.speed), 0.0, 1.0);
      this.animation = AnimationUtil.smoothAnimate(this.state ? 1.0F : 0.0F, this.animation, 20.0F * this.speed);
      this.dropAnimation = (float)this.makeDropAnimation(this.value);
   }

   public StateAnimation dropEasing(Easing easing) {
      this.dropEasing = easing == null ? Easings.BACK_OUT : easing;
      return this;
   }

   public void reset() {
      this.value = 0.0;
      this.state = false;
      this.animation = this.dropAnimation = 0.0F;
   }

   public boolean notOne() {
      return !this.one();
   }

   public boolean one() {
      return this.value == 1.0;
   }

   public boolean notZero() {
      return !this.zero();
   }

   public boolean zero() {
      return this.value == 0.0;
   }

   private static double clamp(double value, double min, double max) {
      return Math.max(min, Math.min(max, value));
   }

   private static double makeAnimation(double x) {
      return Easings.QUAD_BOTH.ease(x);
   }

   private double makeDropAnimation(double x) {
      return this.dropEasing.ease(x);
   }
}
