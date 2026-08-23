package ru.melonity.util.time;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class TimerUtil {
   private long lastMS;

   private TimerUtil() {
      this.reset();
   }

   public static TimerUtil create() {
      return new TimerUtil();
   }

   public void reset() {
      this.lastMS = System.currentTimeMillis();
   }

   public long elapsedTime() {
      return System.currentTimeMillis() - this.lastMS;
   }

   public boolean hasReached(long time) {
      return this.elapsedTime() >= time;
   }

   public boolean hasReached(long time, boolean reset) {
      boolean local4_0 = this.elapsedTime() >= time;
      if (local4_0 && reset) {
         this.reset();
      }

      return local4_0;
   }

   public boolean hasReached(double ms) {
      return (double)this.elapsedTime() >= ms;
   }

   public boolean delay(long ms) {
      boolean local3_0 = this.elapsedTime() - ms >= 0L;
      if (local3_0) {
         this.reset();
      }

      return local3_0;
   }
}
