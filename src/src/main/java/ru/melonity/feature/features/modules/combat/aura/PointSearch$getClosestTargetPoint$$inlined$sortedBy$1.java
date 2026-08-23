package ru.melonity.feature.features.modules.combat.aura;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.Pair;
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
@SourceDebugExtension({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 PointSearch.kt\nru/melonity/feature/features/modules/combat/aura/PointSearch\n*L\n1#1,328:1\n116#2:329\n*E\n"})
public final class PointSearch$getClosestTargetPoint$$inlined$sortedBy$1<T> implements Comparator<T> {
   @Override
   public final int compare(T a, T b) {
      Pair local3_0 = (Pair)a;
      Comparable var10000 = (Double)local3_0.getSecond();
      local3_0 = (Pair)b;
      Comparable var5 = var10000;
      return ComparisonsKt.compareValues(var5, (Double)local3_0.getSecond());
   }
}
