package ru.melonity.feature.features.modules.misc.autobuy;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_437;

@Environment(EnvType.CLIENT)
public class AutobuyAddScreen extends class_437 {
   public AutobuyAddScreen() {
      super(class_2561.method_43470("Autobuy"));
   }

   public void method_25394(class_332 graphics, int mouseX, int mouseY, float partialTicks) {
      super.method_25394(graphics, mouseX, mouseY, partialTicks);
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      return super.method_25402(mouseX, mouseY, button);
   }

   public boolean method_25406(double mouseX, double mouseY, int button) {
      return super.method_25406(mouseX, mouseY, button);
   }

   public boolean method_25421() {
      return false;
   }
}
