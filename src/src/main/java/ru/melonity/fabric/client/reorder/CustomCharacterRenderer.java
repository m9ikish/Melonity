package ru.melonity.fabric.client.reorder;

import com.google.common.collect.Lists;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2583;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_377;
import net.minecraft.class_379;
import net.minecraft.class_382;
import net.minecraft.class_384;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_5224;
import net.minecraft.class_5251;
import net.minecraft.class_327.class_6415;
import net.minecraft.class_382.class_328;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import ru.melonity.fabric.client.model.ITextRenderer;

@Environment(EnvType.CLIENT)
public class CustomCharacterRenderer implements class_5224 {
   public int index = 0;
   private final int nickname;
   final class_4597 vertexConsumers;
   private final boolean shadow;
   private final float brightnessMultiplier;
   private final float red;
   private final float green;
   private final float blue;
   private final float alpha;
   private final Matrix4f matrix;
   private final class_327 textRenderer;
   private final class_6415 layerType;
   private final int light;
   float x;
   float y;
   @Nullable
   private List<class_328> rectangles;
   private static final String REPLACEMENT_STRING = "Melonity";
   public int replacementIndex = 0;
   public int charactersDisplayed = 0;

   private void addRectangle(class_328 rectangle) {
      if (this.rectangles == null) {
         this.rectangles = Lists.newArrayList();
      }

      this.rectangles.add(rectangle);
   }

   public CustomCharacterRenderer(
      class_327 textRenderer,
      class_4597 vertexConsumers,
      float x,
      float y,
      int color,
      boolean shadow,
      Matrix4f matrix,
      class_6415 layerType,
      int light,
      int nickname
   ) {
      this.textRenderer = textRenderer;
      this.nickname = nickname;
      this.vertexConsumers = vertexConsumers;
      this.x = x;
      this.y = y;
      this.shadow = shadow;
      this.brightnessMultiplier = shadow ? 0.25F : 1.0F;
      this.red = (float)(color >> 16 & 0xFF) / 255.0F * this.brightnessMultiplier;
      this.green = (float)(color >> 8 & 0xFF) / 255.0F * this.brightnessMultiplier;
      this.blue = (float)(color & 0xFF) / 255.0F * this.brightnessMultiplier;
      this.alpha = (float)(color >> 24 & 0xFF) / 255.0F;
      this.matrix = matrix;
      this.layerType = layerType;
      this.light = light;
   }

   public boolean accept(int i, class_2583 style, int charInt) {
      if (class_310.method_1551().field_1724 != null) {
         String playerName = class_310.method_1551().field_1724.method_5477().getString();
         if (this.nickname != -1 && this.index >= this.nickname && this.index < this.nickname + playerName.length()) {
            if (this.replacementIndex >= "Melonity".length()) {
               this.index++;
               return true;
            }

            charInt = "Melonity".charAt(this.replacementIndex);
            this.replacementIndex++;
         }

         this.index++;
      }

      class_377 fontStorage = ((ITextRenderer)this.textRenderer).fontStorage(style.method_27708());
      class_379 glyph = fontStorage.method_2011(charInt, ((ITextRenderer)this.textRenderer).validateAdvance());
      class_382 glyphRenderer = style.method_10987() && charInt != 32 ? fontStorage.method_2013(glyph) : fontStorage.method_2014(charInt);
      boolean bl = style.method_10984();
      float f = this.alpha;
      class_5251 textColor = style.method_10973();
      float g;
      float h;
      float l;
      if (textColor != null) {
         int k = textColor.method_27716();
         g = (float)(k >> 16 & 0xFF) / 255.0F * this.brightnessMultiplier;
         h = (float)(k >> 8 & 0xFF) / 255.0F * this.brightnessMultiplier;
         l = (float)(k & 0xFF) / 255.0F * this.brightnessMultiplier;
      } else {
         g = this.red;
         h = this.green;
         l = this.blue;
      }

      if (!(glyphRenderer instanceof class_384)) {
         float m = bl ? glyph.method_16799() : 0.0F;
         float n = this.shadow ? glyph.method_16800() : 0.0F;
         class_4588 vertexConsumer = this.vertexConsumers.getBuffer(glyphRenderer.method_24045(this.layerType));
         ((ITextRenderer)this.textRenderer)
            .myDrawGlyph(glyphRenderer, bl, style.method_10966(), m, this.x + n, this.y + n, this.matrix, vertexConsumer, g, h, l, f, this.light);
      }

      float m = glyph.method_16798(bl);
      float n = this.shadow ? 1.0F : 0.0F;
      if (style.method_10986()) {
         this.addRectangle(new class_328(this.x + n - 1.0F, this.y + n + 4.5F, this.x + n + m, this.y + n + 4.5F - 1.0F, 0.01F, g, h, l, f));
      }

      if (style.method_10965()) {
         this.addRectangle(new class_328(this.x + n - 1.0F, this.y + n + 9.0F, this.x + n + m, this.y + n + 9.0F - 1.0F, 0.01F, g, h, l, f));
      }

      this.x += m;
      return true;
   }

   public float drawLayer(int underlineColor, float x) {
      if (underlineColor != 0) {
         float f = (float)(underlineColor >> 24 & 0xFF) / 255.0F;
         float g = (float)(underlineColor >> 16 & 0xFF) / 255.0F;
         float h = (float)(underlineColor >> 8 & 0xFF) / 255.0F;
         float i = (float)(underlineColor & 0xFF) / 255.0F;
         this.addRectangle(new class_328(x - 1.0F, this.y + 9.0F, this.x + 1.0F, this.y - 1.0F, 0.01F, g, h, i, f));
      }

      if (this.rectangles != null) {
         class_382 glyphRenderer = ((ITextRenderer)this.textRenderer).fontStorage(class_2583.field_24359).method_22943();
         class_4588 vertexConsumer = this.vertexConsumers.getBuffer(glyphRenderer.method_24045(this.layerType));

         for (class_328 rectangle : this.rectangles) {
            glyphRenderer.method_22944(rectangle, this.matrix, vertexConsumer, this.light);
         }
      }

      return this.x;
   }
}
