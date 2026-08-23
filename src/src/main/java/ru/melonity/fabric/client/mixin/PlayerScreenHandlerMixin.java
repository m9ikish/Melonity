package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1723;
import net.minecraft.class_1731;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ru.melonity.fabric.client.model.IPlayerScreenHandler;

@Environment(EnvType.CLIENT)
@Mixin({class_1723.class})
public class PlayerScreenHandlerMixin implements IPlayerScreenHandler {
   @Shadow
   @Final
   private class_1731 field_7830;

   @Override
   public class_1731 craftingResult() {
      return this.field_7830;
   }
}
