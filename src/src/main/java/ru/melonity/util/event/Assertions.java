package ru.melonity.util.event;

import java.util.function.Function;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Assertions {
   public static <T> void requireFalse(boolean probeResult, Function<String, RuntimeException> exceptionFactory, String message, Object... messageArguments) {
      if (probeResult) {
         throw (RuntimeException)exceptionFactory.apply(message.formatted(messageArguments));
      }
   }

   public static <T> void requireFalse(
      T value, Predicate<T> validator, Function<String, RuntimeException> exceptionFactory, String message, Object... messageArguments
   ) {
      boolean local5_0 = validator.test(value);
      requireFalse(local5_0, exceptionFactory, message, messageArguments);
   }

   public static <T> void requireTrue(boolean probeResult, Function<String, RuntimeException> exceptionFactory, String message, Object... messageArguments) {
      requireFalse(!probeResult, exceptionFactory, message, messageArguments);
   }

   public static <T> void requireTrue(
      T value, Predicate<T> validator, Function<String, RuntimeException> exceptionFactory, String message, Object... messageArguments
   ) {
      requireFalse(value, validator.negate(), exceptionFactory, message, messageArguments);
   }
}
