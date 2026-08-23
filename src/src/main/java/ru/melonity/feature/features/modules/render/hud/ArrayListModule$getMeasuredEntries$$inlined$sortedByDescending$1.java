package ru.melonity.feature.features.modules.render.hud;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Metadata(
   mv = {2, 2, 0},
   k = 3,
   xi = 48
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareByDescending$1\n+ 2 ArrayListModule.kt\nru/melonity/feature/features/modules/render/hud/ArrayListModule\n*L\n1#1,328:1\n155#2:329\n*E\n"})
public final class ArrayListModule$getMeasuredEntries$$inlined$sortedByDescending$1<T> implements Comparator<T> {
   @Override
   public final int compare(T a, T b) {
      ArrayListModule.Entry local3_0 = (ArrayListModule.Entry)b;
      Comparable var10000 = local3_0.getTextWidth();
      local3_0 = (ArrayListModule.Entry)a;
      Comparable var5 = var10000;
      return ComparisonsKt.compareValues(var5, local3_0.getTextWidth());
   }
}
