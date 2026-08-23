package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_377;
import net.minecraft.class_382;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_5481;
import net.minecraft.class_327.class_6415;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.ITextRenderer;
import ru.melonity.fabric.client.reorder.CustomCharacterRenderer;
import ru.melonity.feature.features.modules.player.NameProtectFeature;

@Environment(EnvType.CLIENT)
@Mixin({class_327.class})
public class TextRendererMixin implements ITextRenderer {
   @Shadow
   @Final
   boolean field_39925;

   @Inject(
      method = {"drawLayer(Lnet/minecraft/text/OrderedText;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)F"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void drawLayer(
      class_5481 text,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrix,
      class_4597 vertexConsumerProvider,
      class_6415 layerType,
      int underlineColor,
      int light,
      CallbackInfoReturnable<Float> cir
   ) {
      class_327 textRenderer = (class_327)(Object)this;
      int nicknameIndex = -1;
      if (class_310.method_1551().field_1724 != null
         && Melonity.INSTANCE != null
         && Melonity.INSTANCE.getFeatureManager() != null
         && Melonity.INSTANCE.getFeatureManager().getFeature(NameProtectFeature.class).isPresent()
         && Melonity.INSTANCE.getFeatureManager().getFeature(NameProtectFeature.class).get().isEnabled()) {
         StringBuilder stringBuilder = new StringBuilder();
         text.accept((unused, style, charInt) -> {
            stringBuilder.append((char)charInt);
            return true;
         });
         nicknameIndex = stringBuilder.indexOf(class_310.method_1551().field_1724.method_5477().getString());
      }

      CustomCharacterRenderer drawer = new CustomCharacterRenderer(
         textRenderer, vertexConsumerProvider, x, y, color, shadow, matrix, layerType, light, nicknameIndex
      );
      drawer.index = drawer.replacementIndex = drawer.charactersDisplayed = 0;
      text.accept(drawer);
      cir.setReturnValue(drawer.drawLayer(underlineColor, x));
   }

   @Shadow
   class_377 method_27526(class_2960 id) {
      return null;
   }

   @Shadow
   void method_1710(
      class_382 glyphRenderer,
      boolean bold,
      boolean italic,
      float weight,
      float x,
      float y,
      Matrix4f matrix,
      class_4588 vertexConsumer,
      float red,
      float green,
      float blue,
      float alpha,
      int light
   ) {
   }

   @Override
   public class_377 fontStorage(class_2960 id) {
      return this.method_27526(id);
   }

   @Override
   public boolean validateAdvance() {
      return this.field_39925;
   }

   @Override
   public void myDrawGlyph(
      class_382 glyphRenderer,
      boolean bold,
      boolean italic,
      float weight,
      float x,
      float y,
      Matrix4f matrix,
      class_4588 vertexConsumer,
      float red,
      float green,
      float blue,
      float alpha,
      int light
   ) {
      this.method_1710(glyphRenderer, bold, italic, weight, x, y, matrix, vertexConsumer, red, green, blue, alpha, light);
   }
}
