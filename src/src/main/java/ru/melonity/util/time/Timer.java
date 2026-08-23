package ru.melonity.util.time;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Timer {
   private long lastMS;
   private long sleepStartMS;
   private long sleepDuration;
   private boolean sleeping;

   private Timer() {
      this.reset();
   }

   public static Timer create() {
      return new Timer();
   }

   public void reset() {
      this.lastMS = System.currentTimeMillis();
      this.sleeping = false;
      this.sleepDuration = 0L;
      this.sleepStartMS = 0L;
   }

   public long elapsedTime() {
      return System.currentTimeMillis() - this.lastMS;
   }

   public boolean hasTimeElapsed() {
      return this.lastMS < System.currentTimeMillis();
   }

   public boolean hasTimeElapsed(double milliseconds) {
      return (double)(System.currentTimeMillis() - this.lastMS) > milliseconds;
   }

   public boolean hasReached(long time) {
      if (this.sleeping) {
         long local3_0 = System.currentTimeMillis() - this.sleepStartMS;
         if (local3_0 >= this.sleepDuration) {
            this.sleeping = false;
         }

         return false;
      } else {
         return this.elapsedTime() >= time;
      }
   }

   public boolean hasReached(long time, boolean reset) {
      boolean local4_0 = this.hasReached(time);
      if (local4_0 && reset) {
         this.reset();
      }

      return local4_0;
   }

   public void setLastMS(long newValue) {
      this.lastMS = System.currentTimeMillis() + newValue;
   }

   public void sleep(long ms) {
      this.sleeping = true;
      this.sleepStartMS = System.currentTimeMillis();
      this.sleepDuration = ms;
   }

   public void sleep(long ms, boolean reset) {
      this.sleep(ms);
      if (reset) {
         this.lastMS = System.currentTimeMillis();
      }
   }

   public boolean isSleeping() {
      if (this.sleeping) {
         long local1_0 = System.currentTimeMillis() - this.sleepStartMS;
         if (local1_0 >= this.sleepDuration) {
            this.sleeping = false;
            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean delay(long ms) {
      boolean local3_0 = this.elapsedTime() - ms >= 0L;
      if (local3_0) {
         this.reset();
      }

      return local3_0;
   }
}
