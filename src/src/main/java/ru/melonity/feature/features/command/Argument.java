package ru.melonity.feature.features.command;

import java.util.function.Function;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Argument {
   final String value;

   public String asString() {
      return this.value;
   }

   public ArgumentValue<Integer> asInteger() {
      return this.convert(Integer::parseInt);
   }

   public ArgumentValue<Float> asFloat() {
      return this.convert(Float::parseFloat);
   }

   public <T> ArgumentValue<T> convert(Function<String, T> mapper) {
      T local2_0 = null;

      try {
         local2_0 = mapper.apply(this.value);
      } catch (NumberFormatException var4) {
      }

      return ArgumentValue.of(local2_0);
   }

   @Generated
   public Argument(String value) {
      this.value = value;
   }
}
