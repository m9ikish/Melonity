package ru.melonity.font;

import lombok.SneakyThrows;
import java.awt.Font;
import java.io.InputStream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import ru.melonity.util.font.EFonts;
import ru.melonity.util.font.GlyphData;

@Environment(EnvType.CLIENT)
public class Fonts {
   public static final GlyphData INTER_MEDIUM_12 = create(EFonts.INTER_MEDIUM, 12.0F);
   public static final GlyphData INTER_MEDIUM_13 = create(EFonts.INTER_MEDIUM, 13.0F);
   public static final GlyphData INTER_MEDIUM_14 = create(EFonts.INTER_MEDIUM, 14.0F);
   public static final GlyphData INTER_MEDIUM_15 = create(EFonts.INTER_MEDIUM, 15.0F);
   public static final GlyphData INTER_MEDIUM_16 = create(EFonts.INTER_MEDIUM, 16.0F);
   public static final GlyphData INTER_MEDIUM_18 = create(EFonts.INTER_MEDIUM, 18.0F);
   public static final GlyphData INTER_REGULAR_8 = create(EFonts.INTER_REGULAR, 8.0F);
   public static final GlyphData INTER_REGULAR_10 = create(EFonts.INTER_REGULAR, 10.0F);
   public static final GlyphData INTER_REGULAR_12 = create(EFonts.INTER_REGULAR, 12.0F);
   public static final GlyphData INTER_REGULAR_13 = create(EFonts.INTER_REGULAR, 13.0F);
   public static final GlyphData INTER_REGULAR_14 = create(EFonts.INTER_REGULAR, 14.0F);
   public static final GlyphData INTER_REGULAR_15 = create(EFonts.INTER_REGULAR, 15.0F);
   public static final GlyphData INTER_REGULAR_16 = create(EFonts.INTER_REGULAR, 16.0F);
   public static final GlyphData INTER_SEMIBOLD_15 = create(EFonts.INTER_SEMIBOLD, 15.0F);
   public static final GlyphData INTER_SEMIBOLD_16 = create(EFonts.INTER_SEMIBOLD, 16.0F);

   @SneakyThrows
   private static GlyphData create(EFonts eFonts, float size) {
      GlyphData var4;
      try (InputStream local2_1 = class_310.method_1551().method_1478().open(class_2960.method_60656("melonity/fonts/".concat(eFonts.getPath())))) {
         Font local3_0 = Font.createFont(0, local2_1).deriveFont(0, size);
         var4 = new GlyphData(local3_0, size / 2.0F);
      }

      return var4;
   }
}
