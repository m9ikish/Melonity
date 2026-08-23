package ru.melonity.feature.features.command;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class Advice {
   private final String line;
   private final String description;

   @Generated
   public Advice(String line, String description) {
      this.line = line;
      this.description = description;
   }

   @Generated
   public String getLine() {
      return this.line;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Advice local2_0)) {
         return false;
      } else {
         Object local3_1 = this.getLine();
         Object local4_2 = local2_0.getLine();
         if (local3_1 == null ? local4_2 == null : local3_1.equals(local4_2)) {
            Object local5_3 = this.getDescription();
            Object local6_4 = local2_0.getDescription();
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
      Object local3_1 = this.getLine();
      local2_0 = local2_0 * 59 + (local3_1 == null ? 43 : local3_1.hashCode());
      Object local4_2 = this.getDescription();
      return local2_0 * 59 + (local4_2 == null ? 43 : local4_2.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "Advice(line=" + this.getLine() + ", description=" + this.getDescription() + ")";
   }
}
