package ru.melonity.util.font;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.awt.Color;
import java.awt.Font;
import java.util.UUID;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import net.minecraft.class_757;
import net.minecraft.class_293.class_5596;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class GlyphData {
   private final Object2ObjectMap<class_2960, ObjectList<DrawEntry>> glyphPageCache = new Object2ObjectOpenHashMap();
   private final ObjectList<GlyphMap> maps = new ObjectArrayList();
   private final Char2ObjectMap<Glyph> glyphCache = new Char2ObjectOpenHashMap();
   private final ObjectSet<class_2960> configuredTextures = new ObjectOpenHashSet();
   private Font font;

   public GlyphData(Font font, float sizePx) {
      this.init(font, sizePx);
   }

   private void init(Font font, float sizePx) {
      this.font = font.deriveFont(sizePx * 2.0F);
   }

   @NotNull
   private static class_2960 randomIdentifier() {
      return class_2960.method_60655("reflex", "temp/" + UUID.randomUUID().toString().replace("-", ""));
   }

   private GlyphMap generateMap(char from, char to) {
      GlyphMap local3_0 = new GlyphMap(from, to, this.font, randomIdentifier(), 5);
      this.maps.add(local3_0);
      return local3_0;
   }

   private Glyph locateGlyph(char glyph) {
      Glyph local2_2 = (Glyph)this.glyphCache.get(glyph);
      if (local2_2 != null) {
         return local2_2;
      } else {
         ObjectListIterator local3_3 = this.maps.iterator();

         while (local3_3.hasNext()) {
            GlyphMap local4_1 = (GlyphMap)local3_3.next();
            if (local4_1.contains(glyph)) {
               Glyph local5_0 = local4_1.getGlyph(glyph);
               this.glyphCache.put(glyph, local5_0);
               return local5_0;
            }
         }

         char local3_3x = (char)(glyph & -256);
         Glyph local4_4 = this.generateMap(local3_3x, (char)(local3_3x + 256)).getGlyph(glyph);
         this.glyphCache.put(glyph, local4_4);
         return local4_4;
      }
   }

   public void render(String text, double x, double y, class_332 context) {
      this.render(text, x, y, Color.WHITE, context);
   }

   public void render(String text, double x, double y, Color color, class_332 context) {
      this.drawString(context.method_51448(), text, x, y, color.getRGB());
   }

   public void drawString(class_4587 stack, String text, double x, double y, int color) {
      if (text != null && !text.isEmpty()) {
         stack.method_22903();
         y -= 3.0;
         stack.method_22904(x, y, 0.0);
         stack.method_22905(0.5F, 0.5F, 0.5F);
         int local8_0 = this.setupRenderState();
         Matrix4f local9_1 = stack.method_23760().method_23761();
         this.processText(text, color, local9_1);
         this.resetRenderState(local8_0);
         this.glyphPageCache.clear();
         stack.method_22909();
      }
   }

   private int setupRenderState() {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      int local1_0 = GL11.glGetInteger(3317);
      GL11.glPixelStorei(3317, 1);
      RenderSystem.setShader(class_757::method_34543);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      return local1_0;
   }

   private void resetRenderState(int previousAlignment) {
      GL11.glPixelStorei(3317, previousAlignment);
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }

   private void processText(String text, int color, Matrix4f matrix) {
      float local4_4 = 0.0F;
      float local5_5 = 0.0F;
      int local6_6 = 0;

      for (int local7_3 = 0; local7_3 < text.length(); local7_3++) {
         char local8_1 = text.charAt(local7_3);
         if (local8_1 == '\n') {
            local5_5 += this.getStringHeight(text.substring(local6_6, local7_3)) - 2.0F;
            local4_4 = 0.0F;
            local6_6 = local7_3 + 1;
         } else {
            Glyph local9_2 = this.locateGlyph(local8_1);
            if (local9_2 != null) {
               if (local9_2.value() != ' ') {
                  class_2960 local10_0 = local9_2.owner().bindToTexture;
                  ((ObjectList)this.glyphPageCache.computeIfAbsent(local10_0, k -> new ObjectArrayList()))
                     .add(new DrawEntry(local4_4, local5_5, color, local9_2));
               }

               local4_4 += (float)local9_2.width();
            }
         }
      }

      this.drawGlyphs(matrix);
   }

   public void drawGradientString(class_4587 stack, String text, double x, double y, int colorStart, int colorEnd) {
      if (text != null && !text.isEmpty()) {
         stack.method_22903();
         y -= 3.0;
         stack.method_22904(x, y, 0.0);
         stack.method_22905(0.5F, 0.5F, 0.5F);
         int local9_0 = this.setupRenderState();
         Matrix4f local10_1 = stack.method_23760().method_23761();
         this.processGradientText(text, colorStart, colorEnd, local10_1);
         this.resetRenderState(local9_0);
         this.glyphPageCache.clear();
         stack.method_22909();
      }
   }

   private void processGradientText(String text, int start, int end, Matrix4f matrix) {
      float local5_6 = 0.0F;
      float local6_7 = 0.0F;
      int local7_8 = 0;
      int local8_9 = text.length();

      for (int local9_5 = 0; local9_5 < local8_9; local9_5++) {
         char local10_3 = text.charAt(local9_5);
         if (local10_3 == '\n') {
            local6_7 += this.getStringHeight(text.substring(local7_8, local9_5)) - 2.0F;
            local5_6 = 0.0F;
            local7_8 = local9_5 + 1;
         } else {
            Glyph local11_4 = this.locateGlyph(local10_3);
            if (local11_4 != null) {
               if (local11_4.value() != ' ') {
                  float local12_0 = local8_9 > 1 ? (float)local9_5 / (float)(local8_9 - 1) : 0.0F;
                  int local13_1 = this.interpolateColor(start, end, local12_0);
                  class_2960 local14_2 = local11_4.owner().bindToTexture;
                  ((ObjectList)this.glyphPageCache.computeIfAbsent(local14_2, k -> new ObjectArrayList()))
                     .add(new DrawEntry(local5_6, local6_7, local13_1, local11_4));
               }

               local5_6 += (float)local11_4.width();
            }
         }
      }

      this.drawGlyphs(matrix);
   }

   private int interpolateColor(int cs, int ce, float t) {
      int local4_0 = cs >> 24 & 0xFF;
      int local5_1 = cs >> 16 & 0xFF;
      int local6_2 = cs >> 8 & 0xFF;
      int local7_3 = cs & 0xFF;
      int local8_4 = ce >> 24 & 0xFF;
      int local9_5 = ce >> 16 & 0xFF;
      int local10_6 = ce >> 8 & 0xFF;
      int local11_7 = ce & 0xFF;
      int local12_8 = local4_0 + (int)(t * (float)(local8_4 - local4_0));
      int local13_9 = local5_1 + (int)(t * (float)(local9_5 - local5_1));
      int local14_10 = local6_2 + (int)(t * (float)(local10_6 - local6_2));
      int local15_11 = local7_3 + (int)(t * (float)(local11_7 - local7_3));
      return local12_8 << 24 | local13_9 << 16 | local14_10 << 8 | local15_11;
   }

   private void drawGlyphs(Matrix4f matrix) {
      class_289 local2_4 = class_289.method_1348();
      ObjectIterator var4 = this.glyphPageCache.keySet().iterator();

      while (var4.hasNext()) {
         class_2960 local5_2 = (class_2960)var4.next();
         ObjectList<DrawEntry> local6_1 = (ObjectList<DrawEntry>)this.glyphPageCache.get(local5_2);
         if (!local6_1.isEmpty()) {
            RenderSystem.setShaderTexture(0, local5_2);
            this.configureTexture(local5_2);
            class_287 local3_3 = local2_4.method_60827(class_5596.field_27382, class_290.field_1575);
            ObjectListIterator var7 = local6_1.iterator();

            while (var7.hasNext()) {
               DrawEntry local8_0 = (DrawEntry)var7.next();
               this.drawGlyph(local3_3, matrix, local8_0);
            }

            class_286.method_43433(local3_3.method_60800());
         }
      }
   }

   private void configureTexture(class_2960 id) {
      if (this.configuredTextures.add(id)) {
         GL11.glTexParameteri(3553, 10241, 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
         GL11.glTexParameteri(3553, 10242, 33071);
         GL11.glTexParameteri(3553, 10243, 33071);
         GL11.glTexParameteri(3553, 33084, 0);
         GL11.glTexParameteri(3553, 33085, 0);
      }
   }

   private void drawGlyph(class_287 builder, Matrix4f m, DrawEntry e) {
      float local4_0 = e.atX();
      float local5_1 = e.atY();
      Glyph local6_2 = e.toDraw();
      GlyphMap local7_3 = local6_2.owner();
      float local8_4 = (float)local6_2.width();
      float local9_5 = (float)local6_2.height();
      float local10_6 = (float)local6_2.u() / (float)local7_3.width;
      float local11_7 = (float)local6_2.v() / (float)local7_3.height;
      float local12_8 = ((float)local6_2.u() + local8_4) / (float)local7_3.width;
      float local13_9 = ((float)local6_2.v() + local9_5) / (float)local7_3.height;
      int local14_10 = e.color();
      builder.method_22918(m, local4_0, local5_1 + local9_5, 0.0F).method_22913(local10_6, local13_9).method_39415(local14_10);
      builder.method_22918(m, local4_0 + local8_4, local5_1 + local9_5, 0.0F).method_22913(local12_8, local13_9).method_39415(local14_10);
      builder.method_22918(m, local4_0 + local8_4, local5_1, 0.0F).method_22913(local12_8, local11_7).method_39415(local14_10);
      builder.method_22918(m, local4_0, local5_1, 0.0F).method_22913(local10_6, local11_7).method_39415(local14_10);
   }

   public void drawCenteredString(class_4587 stack, String s, double x, double y, int color) {
      this.drawString(stack, s, x - (double)(this.getStringWidth(s) / 2.0F), y, color);
   }

   public float getStringWidth(String text) {
      if (text != null && !text.isEmpty()) {
         float local2_2 = 0.0F;
         float local3_3 = 0.0F;

         for (char local7_1 : text.toCharArray()) {
            if (local7_1 == '\n') {
               local3_3 = Math.max(local2_2, local3_3);
               local2_2 = 0.0F;
            } else {
               Glyph local8_0 = this.locateGlyph(local7_1);
               local2_2 += (float)(local8_0 == null ? 0 : local8_0.width());
            }
         }

         return Math.max(local2_2, local3_3) / 2.0F;
      } else {
         return 0.0F;
      }
   }

   public float getStringHeight(String text) {
      if (text != null && !text.isEmpty()) {
         float local2_2 = 0.0F;
         float local3_3 = 0.0F;

         for (char local7_1 : text.toCharArray()) {
            if (local7_1 == '\n') {
               local3_3 += local2_2 > 0.0F ? local2_2 : (float)this.locateGlyph(' ').height();
               local2_2 = 0.0F;
            } else {
               Glyph local8_0 = this.locateGlyph(local7_1);
               if (local8_0 != null) {
                  local2_2 = Math.max(local2_2, (float)local8_0.height());
               }
            }
         }

         return (local3_3 + local2_2) / 2.0F;
      } else {
         return (float)this.locateGlyph(' ').height() / 2.0F;
      }
   }

   public String trimToWidth(String text, int maxWidth, boolean backwards) {
      return backwards ? this.trimToWidthBackwards(text, maxWidth) : this.trimToWidth(text, maxWidth);
   }

   public String trimToWidth(String text, int maxWidth) {
      return text.substring(0, this.getTrimmedLength(text, maxWidth));
   }

   private int getTrimmedLength(String text, int maxWidth) {
      float local3_2 = 0.0F;
      int local4_3 = 0;

      for (int local5_1 = 0; local5_1 < text.length(); local5_1++) {
         Glyph local6_0 = this.locateGlyph(text.charAt(local5_1));
         if (local6_0 != null) {
            local3_2 += (float)local6_0.width();
            if (local3_2 > (float)maxWidth) {
               break;
            }

            local4_3 = local5_1 + 1;
         }
      }

      return local4_3;
   }

   public String trimToWidthBackwards(String text, int maxWidth) {
      float local3_2 = 0.0F;
      int local4_3 = text.length();

      for (int local5_1 = text.length() - 1; local5_1 >= 0; local5_1--) {
         Glyph local6_0 = this.locateGlyph(text.charAt(local5_1));
         if (local6_0 != null) {
            local3_2 += (float)local6_0.width();
            if (local3_2 > (float)maxWidth) {
               local4_3 = local5_1 + 1;
               break;
            }
         }
      }

      return text.substring(local4_3);
   }

   @Generated
   public GlyphData setFont(Font font) {
      this.font = font;
      return this;
   }
}
