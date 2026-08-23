package ru.melonity.util.font;

import lombok.SneakyThrows;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1011;
import net.minecraft.class_1043;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_1011.class_1012;
import org.lwjgl.system.MemoryUtil;
import ru.melonity.fabric.client.model.INativeImage;

@Environment(EnvType.CLIENT)
public class GlyphMap {
   public final class_2960 bindToTexture;
   private final Char2ObjectArrayMap<Glyph> glyphs = new Char2ObjectArrayMap();
   private final char fromIncl;
   private final char toExcl;
   private final Font font;
   private final int pixelPadding;
   public int width;
   public int height;
   private boolean generated = false;

   public GlyphMap(char from, char to, Font font, class_2960 identifier, int padding) {
      this.fromIncl = from;
      this.toExcl = to;
      this.font = font;
      this.bindToTexture = identifier;
      this.pixelPadding = padding;
   }

   @SneakyThrows
   public static void registerBufferedImageTexture(class_2960 textureIdentifier, BufferedImage inputImage) {
      int local2_5 = inputImage.getWidth();
      int local3_6 = inputImage.getHeight();
      class_1011 local4_7 = new class_1011(class_1012.field_4997, local2_5, local3_6, false);
      IntBuffer local5_8 = MemoryUtil.memIntBuffer(((INativeImage)(Object)local4_7).pointer(), local2_5 * local3_6);
      int[] local6_9 = inputImage.getRGB(0, 0, local2_5, local3_6, null, 0, local2_5);

      for (int local7_4 = 0; local7_4 < local6_9.length; local7_4++) {
         int local8_0 = local6_9[local7_4] >> 24 & 0xFF;
         int local9_1 = local6_9[local7_4] >> 16 & 0xFF;
         int local10_2 = local6_9[local7_4] >> 8 & 0xFF;
         int local11_3 = local6_9[local7_4] & 0xFF;
         local6_9[local7_4] = local8_0 << 24 | local11_3 << 16 | local10_2 << 8 | local9_1;
      }

      local5_8.put(local6_9).flip();
      class_1043 local7_10 = new class_1043(local4_7);
      local7_10.method_4524();
      if (RenderSystem.isOnRenderThread()) {
         class_310.method_1551().method_1531().method_4616(textureIdentifier, local7_10);
      } else {
         RenderSystem.recordRenderCall(() -> class_310.method_1551().method_1531().method_4616(textureIdentifier, local7_10));
      }
   }

   /**
    * Pulls the whole raster out as one freshly allocated primitive pixel array, ready to be handed
    * to a texture upload.
    *
    * <p>The erased {@code Object} return type is what {@link WritableRaster#getDataElements} gives
    * back: the array type follows the raster's transfer type, so an {@code int[]} for the
    * {@code TYPE_INT_ARGB} atlas image this class builds in {@link #generate()}, a {@code byte[]}
    * for byte-backed rasters. Channel order is therefore exactly the raster's own - no swizzling
    * and no premultiplication are applied here; {@link #registerBufferedImageTexture} is the place
    * that does the ARGB to ABGR swizzle needed by {@code class_1011}.
    */
   private static Object createDataArrayBasedOnRaster(WritableRaster raster) {
      return raster.getDataElements(raster.getMinX(), raster.getMinY(), raster.getWidth(), raster.getHeight(), null);
   }

   public Glyph getGlyph(char c) {
      if (!this.generated) {
         this.generate();
      }

      return (Glyph)this.glyphs.get(c);
   }

   public boolean contains(char c) {
      return c >= this.fromIncl && c < this.toExcl;
   }

   private Font getFontForGlyph(char c) {
      return this.font.canDisplay(c) ? this.font : new Font("SansSerif", 0, this.font.getSize());
   }

   public void generate() {
      if (!this.generated) {
         int local1_7 = this.toExcl - this.fromIncl - 1;
         int local2_8 = (int)(Math.ceil(Math.sqrt((double)local1_7)) * 1.5);
         this.glyphs.clear();
         int local3_9 = 0;
         int local4_10 = 0;
         int local5_11 = 0;
         int local6_12 = 0;
         int local7_13 = 0;
         int local8_14 = 0;
         int local9_15 = 0;
         List<Glyph> local10_16 = new ArrayList<>();
         AffineTransform local11_17 = new AffineTransform();

         for (FontRenderContext local12_18 = new FontRenderContext(local11_17, true, false); local3_9 <= local1_7; local4_10++) {
            char local13_0 = (char)(this.fromIncl + local3_9);
            Font local14_1 = this.getFontForGlyph(local13_0);
            Rectangle2D local15_2 = local14_1.getStringBounds(String.valueOf(local13_0), local12_18);
            int local16_3 = (int)Math.ceil(local15_2.getWidth());
            int local17_4 = (int)Math.ceil(local15_2.getHeight());
            local3_9++;
            local5_11 = Math.max(local5_11, local7_13 + local16_3);
            local6_12 = Math.max(local6_12, local8_14 + local17_4);
            if (local4_10 >= local2_8) {
               local7_13 = 0;
               local8_14 += local9_15 + this.pixelPadding;
               local4_10 = 0;
               local9_15 = 0;
            }

            local9_15 = Math.max(local9_15, local17_4);
            local10_16.add(new Glyph(local7_13, local8_14, local16_3, local17_4, local13_0, this));
            local7_13 += local16_3 + this.pixelPadding;
         }

         BufferedImage local13_19 = new BufferedImage(Math.max(local5_11 + this.pixelPadding, 1), Math.max(local6_12 + this.pixelPadding, 1), 2);
         this.width = local13_19.getWidth();
         this.height = local13_19.getHeight();
         Graphics2D local14_20 = local13_19.createGraphics();
         local14_20.setComposite(AlphaComposite.Src);

         try {
            local14_20.setColor(new Color(255, 255, 255, 0));
            local14_20.fillRect(0, 0, this.width, this.height);
            local14_20.setColor(Color.WHITE);
            local14_20.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            local14_20.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            local14_20.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            local14_20.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            for (Glyph local16_6 : local10_16) {
               local14_20.setFont(this.getFontForGlyph(local16_6.value()));
               FontMetrics local17_5 = local14_20.getFontMetrics();
               local14_20.drawString(String.valueOf(local16_6.value()), local16_6.u(), local16_6.v() + local17_5.getAscent());
               this.glyphs.put(local16_6.value(), local16_6);
            }
         } finally {
            local14_20.dispose();
         }

         registerBufferedImageTexture(this.bindToTexture, local13_19);
         this.generated = true;
      }
   }
}
