package ru.melonity.feature.features.modules.combat.aura;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_243;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\b\u001a\u00020\u0002HÆ\u0003¢\u0006\u0004\b\b\u0010\tJ\u0010\u0010\n\u001a\u00020\u0004HÆ\u0003¢\u0006\u0004\b\n\u0010\u000bJ$\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u0004HÆ\u0001¢\u0006\u0004\b\f\u0010\rJ\u001a\u0010\u0010\u001a\u00020\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0010\u0010\u0011J\u0010\u0010\u0013\u001a\u00020\u0012HÖ\u0001¢\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015HÖ\u0001¢\u0006\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0018\u001a\u0004\b\u0019\u0010\tR\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010\u001a\u001a\u0004\b\u001b\u0010\u000b¨\u0006\u001c"},
   d2 = {"Lru/melonity/feature/features/modules/combat/aura/PointSearchResult;", "", "Lnet/minecraft/class_243;", "point", "", "distance", "<init>", "(Lnet/minecraft/class_243;D)V", "component1", "()Lnet/minecraft/class_243;", "component2", "()D", "copy", "(Lnet/minecraft/class_243;D)Lru/melonity/feature/features/modules/combat/aura/PointSearchResult;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/class_243;", "getPoint", "D", "getDistance", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class PointSearchResult {
   @NotNull
   private final class_243 point;
   private final double distance;

   public PointSearchResult(@NotNull class_243 point, double distance) {
      super();
      Intrinsics.checkNotNullParameter(point, "point");
      this.point = point;
      this.distance = distance;
   }

   @NotNull
   public final class_243 getPoint() {
      return this.point;
   }

   public final double getDistance() {
      return this.distance;
   }

   @NotNull
   public final class_243 component1() {
      return this.point;
   }

   public final double component2() {
      return this.distance;
   }

   @NotNull
   public final PointSearchResult copy(@NotNull class_243 point, double distance) {
      Intrinsics.checkNotNullParameter(point, "point");
      return new PointSearchResult(point, distance);
   }

   @NotNull
   @Override
   public String toString() {
      return "PointSearchResult(point=" + this.point + ", distance=" + this.distance + ")";
   }

   @Override
   public int hashCode() {
      int local1_0 = this.point.hashCode();
      return local1_0 * 31 + Double.hashCode(this.distance);
   }

   @Override
   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof PointSearchResult var2)) {
         return false;
      } else {
         return !Intrinsics.areEqual(this.point, var2.point) ? false : Double.compare(this.distance, var2.distance) == 0;
      }
   }
}
