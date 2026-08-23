package ru.melonity.util.time;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class TimeFormatter {
   public static String ticksToTime(int ticks) {
      int local1_0 = ticks / 20;
      int local2_1 = local1_0 / 3600;
      int local3_2 = local1_0 % 3600 / 60;
      int local4_3 = local1_0 % 60;
      return local2_1 > 0 ? String.format("%d:%d:%02d", local2_1, local3_2, local4_3) : String.format("%d:%02d", local3_2, local4_3);
   }

   @Generated
   private TimeFormatter() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
