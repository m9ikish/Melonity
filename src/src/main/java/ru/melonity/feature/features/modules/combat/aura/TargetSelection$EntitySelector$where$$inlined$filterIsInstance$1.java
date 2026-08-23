package ru.melonity.feature.features.modules.combat.aura;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Metadata(
   mv = {2, 2, 0},
   k = 3,
   xi = 48
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\n_Sequences.kt\nKotlin\n*S Kotlin\n*F\n+ 1 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt$filterIsInstance$1\n*L\n1#1,3218:1\n*E\n"})
public final class TargetSelection$EntitySelector$where$$inlined$filterIsInstance$1 implements Function1<Object, Boolean> {
   public static final TargetSelection$EntitySelector$where$$inlined$filterIsInstance$1 INSTANCE = new TargetSelection$EntitySelector$where$$inlined$filterIsInstance$1();

   public final Boolean invoke(Object it) {
      Intrinsics.reifiedOperationMarker(3, "R");
      return it instanceof Object;
   }
}
