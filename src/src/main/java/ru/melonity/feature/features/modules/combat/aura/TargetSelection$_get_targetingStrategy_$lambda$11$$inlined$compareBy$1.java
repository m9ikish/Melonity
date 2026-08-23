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
@SourceDebugExtension({"SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 TargetSelection.kt\nru/melonity/feature/features/modules/combat/aura/TargetSelection\n*L\n1#1,328:1\n100#2,3:329\n*E\n"})
public final class TargetSelection$_get_targetingStrategy_$lambda$11$$inlined$compareBy$1<T> implements Comparator<T> {
   @Override
   public final int compare(T a, T b) {
      class_1297 local3_1 = (class_1297)a;
      Intrinsics.checkNotNull(local3_1, "null cannot be cast to non-null type net.minecraft.entity.LivingEntity");
      class_1309 local5_0 = (class_1309)local3_1;
      Comparable var10000 = -((double)TargetSelection.access$armor(TargetSelection.INSTANCE, local5_0));
      local3_1 = (class_1297)b;
      Comparable var7 = var10000;
      Intrinsics.checkNotNull(local3_1, "null cannot be cast to non-null type net.minecraft.entity.LivingEntity");
      local5_0 = (class_1309)local3_1;
      return ComparisonsKt.compareValues(var7, -((double)TargetSelection.access$armor(TargetSelection.INSTANCE, local5_0)));
   }
}
