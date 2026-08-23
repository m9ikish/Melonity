package ru.melonity.util.macro.actions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class DisableFunctionAction extends AbstractToggleFunction {
   public DisableFunctionAction() {
      super(false);
   }

   @Override
   public String getName() {
      return "Disable function";
   }
}
