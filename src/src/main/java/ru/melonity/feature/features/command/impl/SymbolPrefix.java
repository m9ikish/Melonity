package ru.melonity.feature.features.command.impl;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.features.command.Prefix;

@Environment(EnvType.CLIENT)
public class SymbolPrefix implements Prefix {
   private String symbol;

   @Override
   public String prefix() {
      return this.symbol;
   }

   @Override
   public void setPrefix(String prefix) {
      this.symbol = prefix;
   }

   @Generated
   public SymbolPrefix(String symbol) {
      this.symbol = symbol;
   }
}
