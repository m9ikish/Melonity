package ru.melonity.feature.features.modules.render.hud;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.util.font.GlyphData;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003JK\u0010\u0010\u001a\u00020\u000f2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000bH\u0007¢\u0006\u0004\b\u0010\u0010\u0011J\u0019\u0010\u0013\u001a\u00020\u00042\b\u0010\u0012\u001a\u0004\u0018\u00010\u0004H\u0007¢\u0006\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0016\u001a\u00020\u00158\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0016\u0010\u0017¨\u0006\u0018"},
   d2 = {"Lru/melonity/feature/features/modules/render/hud/DynamicIslandMeasure;", "", "<init>", "()V", "", "title", "detail", "", "highlightDetail", "Lru/melonity/util/font/GlyphData;", "font", "", "baseHeight", "paddingX", "paddingY", "Lru/melonity/feature/features/modules/render/hud/ContentSnapshot;", "snapshot", "(Ljava/lang/String;Ljava/lang/String;ZLru/melonity/util/font/GlyphData;FFF)Lru/melonity/feature/features/modules/render/hud/ContentSnapshot;", "text", "stabilizeDigitWidth", "(Ljava/lang/String;)Ljava/lang/String;", "Lkotlin/text/Regex;", "digitRegex", "Lkotlin/text/Regex;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class DynamicIslandMeasure {
   @NotNull
   public static final DynamicIslandMeasure INSTANCE = new DynamicIslandMeasure();
   @NotNull
   private static final Regex digitRegex = new Regex("\\d");

   private DynamicIslandMeasure() {
   }

   @JvmStatic
   @NotNull
   public static final ContentSnapshot snapshot(
      @Nullable String title, @Nullable String detail, boolean highlightDetail, @NotNull GlyphData font, float baseHeight, float paddingX, float paddingY
   ) {
      Intrinsics.checkNotNullParameter(font, "font");
      String var10000 = title;
      if (title == null) {
         var10000 = "";
      }

      String local7_0 = var10000;
      float local8_1 = font.getStringWidth(stabilizeDigitWidth(local7_0));
      float local9_2 = detail != null ? font.getStringWidth(stabilizeDigitWidth(detail)) : 0.0F;
      float local10_3 = local8_1 + (detail != null ? local9_2 + 5.0F : 0.0F);
      float local11_4 = font.getStringHeight(local7_0);
      float local12_5 = Math.max(baseHeight, paddingY * (float)2 + local11_4);
      float local13_6 = Math.max(local12_5, local10_3 + paddingX * (float)2);
      return new ContentSnapshot(local7_0, detail, highlightDetail, local13_6, local12_5);
   }

   @JvmStatic
   @NotNull
   public static final String stabilizeDigitWidth(@Nullable String text) {
      String var10000 = text;
      if (text == null) {
         var10000 = "";
      }

      String local1_0 = var10000;
      return local1_0.length() == 0 ? "" : digitRegex.replace(local1_0, "8");
   }
}
