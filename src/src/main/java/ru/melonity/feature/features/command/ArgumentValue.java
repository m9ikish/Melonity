package ru.melonity.feature.features.command;

import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ArgumentValue<T> {
   private static final ArgumentValue<?> NULL_VALUE = new ArgumentValue(null);
   final T value;

   private ArgumentValue(T value) {
      this.value = value;
   }

   public T get() {
      return this.value;
   }

   public T getOrThrowIfNull(Function<String, RuntimeException> exceptionFactory, String exceptionMessage, Object... objects) {
      if (this.value == null) {
         throw (RuntimeException)exceptionFactory.apply(exceptionMessage.formatted(objects));
      } else {
         return this.value;
      }
   }

   public static <T> ArgumentValue<T> of(T value) {
      return (ArgumentValue<T>)(value == null ? NULL_VALUE : new ArgumentValue<>(value));
   }
}
