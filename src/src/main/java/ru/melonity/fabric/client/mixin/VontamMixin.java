package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_8685;
import net.minecraft.class_8685.class_7920;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin({class_8685.class})
public class VontamMixin {
   @Mutable
   @Shadow
   @Final
   private class_2960 comp_1626;
   @Mutable
   @Shadow
   @Final
   private String comp_1911;

   @Inject(
      method = {"<init>"},
      at = {@At("RETURN")}
   )
   public void sos(class_2960 identifier, String string, class_2960 identifier2, class_2960 identifier3, class_7920 model, boolean bl, CallbackInfo ci) {
      if (class_310.method_1551().field_1724 != null
         && class_310.method_1551().method_1562().method_45734() != null
         && class_310.method_1551().method_1562().method_45734().field_3761.contains("funtime")) {
         this.comp_1626 = class_2960.method_60656("melonity/images/models/skin.png");
         this.comp_1911 = "https://textures.minecraft.net/texture/b53eb8f0d5580ce510444de3f456855a4da7eee8c7ddce3043648604bd84e918";
      }
   }
}
