package ru.melonity.feature.features.modules.render.particles;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Animation12 {
   private Easing easing;
   private long duration;
   private long millis;
   private long startTime;
   private double startValue;
   private double destinationValue;
   private double value;
   private boolean finished;

   public Animation12(Easing easing, long duration) {
      this.easing = easing;
      this.startTime = System.currentTimeMillis();
      this.duration = duration;
   }

   public void run(double destinationValue) {
      this.millis = System.currentTimeMillis();
      if (this.destinationValue != destinationValue) {
         this.destinationValue = destinationValue;
         this.reset();
      } else {
         this.finished = this.millis - this.duration > this.startTime;
         if (this.finished) {
            this.value = destinationValue;
            return;
         }
      }

      double local3_0 = this.easing.getFunction().apply(this.getProgress());
      if (this.value > destinationValue) {
         this.value = this.startValue - (this.startValue - destinationValue) * local3_0;
      } else {
         this.value = this.startValue + (destinationValue - this.startValue) * local3_0;
      }
   }

   public double getProgress() {
      return (double)(System.currentTimeMillis() - this.startTime) / (double)this.duration;
   }

   public void reset() {
      this.startTime = System.currentTimeMillis();
      this.startValue = this.value;
      this.finished = false;
   }

   public boolean isPositive() {
      return this.getValue() > 0.0;
   }

   @Generated
   public Easing getEasing() {
      return this.easing;
   }

   @Generated
   public long getDuration() {
      return this.duration;
   }

   @Generated
   public long getMillis() {
      return this.millis;
   }

   @Generated
   public long getStartTime() {
      return this.startTime;
   }

   @Generated
   public double getStartValue() {
      return this.startValue;
   }

   @Generated
   public double getDestinationValue() {
      return this.destinationValue;
   }

   @Generated
   public double getValue() {
      return this.value;
   }

   @Generated
   public boolean isFinished() {
      return this.finished;
   }

   @Generated
   public void setEasing(Easing easing) {
      this.easing = easing;
   }

   @Generated
   public void setDuration(long duration) {
      this.duration = duration;
   }

   @Generated
   public void setMillis(long millis) {
      this.millis = millis;
   }

   @Generated
   public void setStartTime(long startTime) {
      this.startTime = startTime;
   }

   @Generated
   public void setStartValue(double startValue) {
      this.startValue = startValue;
   }

   @Generated
   public void setDestinationValue(double destinationValue) {
      this.destinationValue = destinationValue;
   }

   @Generated
   public void setValue(double value) {
      this.value = value;
   }

   @Generated
   public void setFinished(boolean finished) {
      this.finished = finished;
   }

   @Generated
   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Animation12 local2_0)) {
         return false;
      } else if (!local2_0.canEqual(this)) {
         return false;
      } else if (this.getDuration() != local2_0.getDuration()) {
         return false;
      } else if (this.getMillis() != local2_0.getMillis()) {
         return false;
      } else if (this.getStartTime() != local2_0.getStartTime()) {
         return false;
      } else if (Double.compare(this.getStartValue(), local2_0.getStartValue()) != 0) {
         return false;
      } else if (Double.compare(this.getDestinationValue(), local2_0.getDestinationValue()) != 0) {
         return false;
      } else if (Double.compare(this.getValue(), local2_0.getValue()) != 0) {
         return false;
      } else if (this.isFinished() != local2_0.isFinished()) {
         return false;
      } else {
         Object local3_1 = this.getEasing();
         Object local4_2 = local2_0.getEasing();
         return local3_1 == null ? local4_2 == null : local3_1.equals(local4_2);
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof Animation12;
   }

   @Generated
   @Override
   public int hashCode() {
      int local2_0 = 1;
      long local3_1 = this.getDuration();
      local2_0 = local2_0 * 59 + (int)(local3_1 >>> 32 ^ local3_1);
      long local5_2 = this.getMillis();
      local2_0 = local2_0 * 59 + (int)(local5_2 >>> 32 ^ local5_2);
      long local7_3 = this.getStartTime();
      local2_0 = local2_0 * 59 + (int)(local7_3 >>> 32 ^ local7_3);
      long local9_4 = Double.doubleToLongBits(this.getStartValue());
      local2_0 = local2_0 * 59 + (int)(local9_4 >>> 32 ^ local9_4);
      long local11_5 = Double.doubleToLongBits(this.getDestinationValue());
      local2_0 = local2_0 * 59 + (int)(local11_5 >>> 32 ^ local11_5);
      long local13_6 = Double.doubleToLongBits(this.getValue());
      local2_0 = local2_0 * 59 + (int)(local13_6 >>> 32 ^ local13_6);
      local2_0 = local2_0 * 59 + (this.isFinished() ? 79 : 97);
      Object local15_7 = this.getEasing();
      return local2_0 * 59 + (local15_7 == null ? 43 : local15_7.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "Animation12(easing="
         + this.getEasing()
         + ", duration="
         + this.getDuration()
         + ", millis="
         + this.getMillis()
         + ", startTime="
         + this.getStartTime()
         + ", startValue="
         + this.getStartValue()
         + ", destinationValue="
         + this.getDestinationValue()
         + ", value="
         + this.getValue()
         + ", finished="
         + this.isFinished()
         + ")";
   }
}
