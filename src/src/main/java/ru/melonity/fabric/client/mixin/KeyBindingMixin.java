package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_304;
import net.minecraft.class_3675.class_306;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ru.melonity.fabric.client.model.IKeyBinding;

@Environment(EnvType.CLIENT)
@Mixin({class_304.class})
public class KeyBindingMixin implements IKeyBinding {
   @Shadow
   private class_306 field_1655;

   @Override
   public class_306 boundKey() {
      return this.field_1655;
   }
}
