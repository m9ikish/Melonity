package ru.melonity.util.animation;

import java.util.concurrent.TimeUnit;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class FrameWeightCalculator {
   private long previousSnapshotTime = System.nanoTime();
   private final double unit;

   public float elapsedUnits() {
      long local1_0 = System.nanoTime();
      long local3_1 = local1_0 - this.previousSnapshotTime;
      this.previousSnapshotTime = local1_0;
      return (float)((double)local3_1 / this.unit);
   }

   public static FrameWeightCalculator nanoUnit(long unitDurationNS) {
      return new FrameWeightCalculator((double)unitDurationNS);
   }

   public static FrameWeightCalculator milliseconds(long unitDurationMS) {
      return nanoUnit(TimeUnit.MILLISECONDS.toNanos(unitDurationMS));
   }

   public static FrameWeightCalculator seconds(long unitDurationS) {
      return milliseconds(1000L * unitDurationS);
   }

   @Generated
   public FrameWeightCalculator(double unit) {
      this.unit = unit;
   }
}
