package ru.melonity.pair;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class Pair<F, S> {
   private final F first;
   private final S second;

   @Generated
   public Pair(F first, S second) {
      this.first = first;
      this.second = second;
   }

   @Generated
   public F getFirst() {
      return this.first;
   }

   @Generated
   public S getSecond() {
      return this.second;
   }

   @Generated
   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Pair<?, ?> local2_0)) {
         return false;
      } else {
         Object local3_1 = this.getFirst();
         Object local4_2 = local2_0.getFirst();
         if (local3_1 == null ? local4_2 == null : local3_1.equals(local4_2)) {
            Object local5_3 = this.getSecond();
            Object local6_4 = local2_0.getSecond();
            return local5_3 == null ? local6_4 == null : local5_3.equals(local6_4);
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   public int hashCode() {
      int local2_0 = 1;
      Object local3_1 = this.getFirst();
      local2_0 = local2_0 * 59 + (local3_1 == null ? 43 : local3_1.hashCode());
      Object local4_2 = this.getSecond();
      return local2_0 * 59 + (local4_2 == null ? 43 : local4_2.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "Pair(first=" + this.getFirst() + ", second=" + this.getSecond() + ")";
   }
}
