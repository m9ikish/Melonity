package ru.melonity.util.macro.actions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EnableFunctionAction extends AbstractToggleFunction {
   public EnableFunctionAction() {
      super(true);
   }

   @Override
   public String getName() {
      return "Enable function";
   }
}
