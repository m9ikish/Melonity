package ru.melonity.feature.features.modules.combat.aura;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010\tJ\u0010\u0010\n\u001a\u00020\u0002HÆ\u0003¢\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\f\u001a\u00020\u0004HÆ\u0003¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u0006HÆ\u0003¢\u0006\u0004\b\u000e\u0010\u000fJ.\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u0006HÆ\u0001¢\u0006\u0004\b\u0010\u0010\u0011J\u001a\u0010\u0013\u001a\u00020\u00042\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015HÖ\u0001¢\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0019\u001a\u00020\u0018HÖ\u0001¢\u0006\u0004\b\u0019\u0010\u001aR\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u001b\u001a\u0004\b\u001c\u0010\u000bR\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010\u001d\u001a\u0004\b\u001e\u0010\rR\u0017\u0010\u0007\u001a\u00020\u00068\u0006¢\u0006\f\n\u0004\b\u0007\u0010\u001f\u001a\u0004\b \u0010\u000f¨\u0006!"},
   d2 = {"Lru/melonity/feature/features/modules/combat/aura/SearchConditions;", "", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "targetSetting", "", "ignoreWalls", "", "distance", "<init>", "(Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;ZD)V", "component1", "()Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "component2", "()Z", "component3", "()D", "copy", "(Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;ZD)Lru/melonity/feature/features/modules/combat/aura/SearchConditions;", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "getTargetSetting", "Z", "getIgnoreWalls", "D", "getDistance", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class SearchConditions {
   @NotNull
   private final ListSetting targetSetting;
   private final boolean ignoreWalls;
   private final double distance;

   public SearchConditions(@NotNull ListSetting targetSetting, boolean ignoreWalls, double distance) {
      super();
      Intrinsics.checkNotNullParameter(targetSetting, "targetSetting");
      this.targetSetting = targetSetting;
      this.ignoreWalls = ignoreWalls;
      this.distance = distance;
   }

   @NotNull
   public final ListSetting getTargetSetting() {
      return this.targetSetting;
   }

   public final boolean getIgnoreWalls() {
      return this.ignoreWalls;
   }

   public final double getDistance() {
      return this.distance;
   }

   @NotNull
   public final ListSetting component1() {
      return this.targetSetting;
   }

   public final boolean component2() {
      return this.ignoreWalls;
   }

   public final double component3() {
      return this.distance;
   }

   @NotNull
   public final SearchConditions copy(@NotNull ListSetting targetSetting, boolean ignoreWalls, double distance) {
      Intrinsics.checkNotNullParameter(targetSetting, "targetSetting");
      return new SearchConditions(targetSetting, ignoreWalls, distance);
   }

   @NotNull
   @Override
   public String toString() {
      return "SearchConditions(targetSetting=" + this.targetSetting + ", ignoreWalls=" + this.ignoreWalls + ", distance=" + this.distance + ")";
   }

   @Override
   public int hashCode() {
      int local1_0 = this.targetSetting.hashCode();
      local1_0 = local1_0 * 31 + Boolean.hashCode(this.ignoreWalls);
      return local1_0 * 31 + Double.hashCode(this.distance);
   }

   @Override
   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof SearchConditions var2)) {
         return false;
      } else if (!Intrinsics.areEqual(this.targetSetting, var2.targetSetting)) {
         return false;
      } else {
         return this.ignoreWalls != var2.ignoreWalls ? false : Double.compare(this.distance, var2.distance) == 0;
      }
   }
}
