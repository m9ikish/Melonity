package ru.melonity.feature.features.modules.combat.aura;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.SourceDebugExtension;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;

@Metadata(
   mv = {2, 2, 0},
   k = 3,
   xi = 48
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\n_Sequences.kt\nKotlin\n*S Kotlin\n*F\n+ 1 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt$filterIsInstance$1\n*L\n1#1,3218:1\n*E\n"})
public final class TargetSelection$find$lambda$3$$inlined$where$1 implements Function1<Object, Boolean> {
   public static final TargetSelection$find$lambda$3$$inlined$where$1 INSTANCE = new TargetSelection$find$lambda$3$$inlined$where$1();

   public final Boolean invoke(Object it) {
      return it instanceof class_1309;
   }
}
