package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1708;
import net.minecraft.class_3913;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ru.melonity.fabric.client.model.IBrewingStandScreenHandler;

@Environment(EnvType.CLIENT)
@Mixin({class_1708.class})
public class BrewingStandScreenHandlerMixin implements IBrewingStandScreenHandler {
   @Shadow
   @Final
   private class_3913 field_17292;

   @Override
   public class_3913 propertyDelegate() {
      return this.field_17292;
   }
}
