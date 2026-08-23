package ru.melonity.feature.features.command;

import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ArgumentPool {
   final List<Argument> arguments;

   public Argument get(int index) {
      if (index >= this.arguments.size()) {
         throw new IndexOutOfBoundsException("Не хватает аргументов команды [%d/%d]".formatted(index + 1, this.arguments.size()));
      } else {
         return this.arguments.get(index);
      }
   }

   @Generated
   public ArgumentPool(List<Argument> arguments) {
      this.arguments = arguments;
   }
}
