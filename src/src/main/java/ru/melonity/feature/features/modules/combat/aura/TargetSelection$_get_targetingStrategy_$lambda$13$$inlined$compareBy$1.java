package ru.melonity.feature.features.modules.combat.aura;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_746;
import ru.melonity.feature.Feature;

@Metadata(
   mv = {2, 2, 0},
   k = 3,
   xi = 48
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 TargetSelection.kt\nru/melonity/feature/features/modules/combat/aura/TargetSelection\n*L\n1#1,328:1\n109#2:329\n*E\n"})
public final class TargetSelection$_get_targetingStrategy_$lambda$13$$inlined$compareBy$1<T> implements Comparator<T> {
   @Override
   public final int compare(T a, T b) {
      class_1297 local3_0 = (class_1297)a;
      class_746 var10000 = Feature.mc.field_1724;
      Intrinsics.checkNotNull(Feature.mc.field_1724);
      Comparable var7 = (double)var10000.method_5739(local3_0);
      local3_0 = (class_1297)b;
      Comparable var5 = var7;
      var10000 = Feature.mc.field_1724;
      Intrinsics.checkNotNull(Feature.mc.field_1724);
      return ComparisonsKt.compareValues(var5, (double)var10000.method_5739(local3_0));
   }
}
