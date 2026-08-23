package ru.melonity.feature.features.modules.combat.aura;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1309;

@Metadata(
   mv = {2, 2, 0},
   k = 3,
   xi = 48
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 TargetSelection.kt\nru/melonity/feature/features/modules/combat/aura/TargetSelection\n*L\n1#1,328:1\n105#2:329\n*E\n"})
public final class TargetSelection$_get_targetingStrategy_$lambda$11$$inlined$compareBy$3<T> implements Comparator<T> {
   @Override
   public final int compare(T a, T b) {
      class_1297 local3_0 = (class_1297)a;
      PointSearch var10000 = PointSearch.INSTANCE;
      Intrinsics.checkNotNull(local3_0, "null cannot be cast to non-null type net.minecraft.entity.LivingEntity");
      Comparable var7 = var10000.getClosestTargetPoint((class_1297)((class_1309)local3_0)).getPoint().method_1033();
      local3_0 = (class_1297)b;
      Comparable var5 = var7;
      var10000 = PointSearch.INSTANCE;
      Intrinsics.checkNotNull(local3_0, "null cannot be cast to non-null type net.minecraft.entity.LivingEntity");
      return ComparisonsKt.compareValues(var5, var10000.getClosestTargetPoint((class_1297)((class_1309)local3_0)).getPoint().method_1033());
   }
}
