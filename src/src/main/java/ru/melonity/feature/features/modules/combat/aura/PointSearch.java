package ru.melonity.feature.features.modules.combat.aura;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1934;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_241;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_3966;
import net.minecraft.class_636;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.util.minecraft.RayTraceUtil;
import ru.melonity.util.minecraft.RotationUtil;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J7\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u0005\u001a\u00020\u00042\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0004\b\t\u0010\nJ\u001d\u0010\r\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007¢\u0006\u0004\b\r\u0010\u000eJ\u001c\u0010\u0011\u001a\u00020\u000f*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0082\u0004¢\u0006\u0004\b\u0011\u0010\u0012J\u0011\u0010\u0014\u001a\u00020\u0013*\u00020\u0013¢\u0006\u0004\b\u0014\u0010\u0015J(\u0010\u0017\u001a\u00020\u0007*\u00020\u00072\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006H\u0082\u0004¢\u0006\u0004\b\u0017\u0010\u0018J\u0014\u0010\u0019\u001a\u00020\u0013*\u00020\u000fH\u0082\u0002¢\u0006\u0004\b\u0019\u0010\u001aJ\u0014\u0010\u001b\u001a\u00020\u0013*\u00020\u000fH\u0082\u0002¢\u0006\u0004\b\u001b\u0010\u001aJ\u0014\u0010\u001c\u001a\u00020\u0013*\u00020\u000fH\u0082\u0002¢\u0006\u0004\b\u001c\u0010\u001aJ\u0015\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u001d\u001a\u00020\u0004¢\u0006\u0004\b\u001f\u0010 J'\u0010\u001f\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u000f2\b\u0010\u001d\u001a\u0004\u0018\u00010\u00042\u0006\u0010\"\u001a\u00020\u0007¢\u0006\u0004\b\u001f\u0010#J\u000f\u0010$\u001a\u00020\u0007H\u0002¢\u0006\u0004\b$\u0010%J7\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u0005\u001a\u00020&2\u0014\b\u0002\u0010'\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0004\b(\u0010)R\u0017\u0010*\u001a\u00020\u001e8\u0006¢\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-¨\u0006."},
   d2 = {"Lru/melonity/feature/features/modules/combat/aura/PointSearch;", "", "<init>", "()V", "Lnet/minecraft/class_1297;", "target", "Lkotlin/Pair;", "", "original", "rotationToClosest", "(Lnet/minecraft/class_1297;Lkotlin/Pair;)Lkotlin/Pair;", "currentAngle", "targetAngle", "findClosestAngle", "(FF)F", "Lnet/minecraft/class_243;", "other", "subtract", "(Lnet/minecraft/class_243;Lnet/minecraft/class_243;)Lnet/minecraft/class_243;", "", "toDegrees", "(D)D", "range", "clampedBetween", "(FLkotlin/Pair;)F", "component1", "(Lnet/minecraft/class_243;)D", "component2", "component3", "entity", "Lru/melonity/feature/features/modules/combat/aura/PointSearchResult;", "getClosestTargetPoint", "(Lnet/minecraft/class_1297;)Lru/melonity/feature/features/modules/combat/aura/PointSearchResult;", "vec", "point", "(Lnet/minecraft/class_243;Lnet/minecraft/class_1297;F)Lru/melonity/feature/features/modules/combat/aura/PointSearchResult;", "attackDist", "()F", "Lnet/minecraft/class_1309;", "previous", "delta", "(Lnet/minecraft/class_1309;Lkotlin/Pair;)Lkotlin/Pair;", "MISS", "Lru/melonity/feature/features/modules/combat/aura/PointSearchResult;", "getMISS", "()Lru/melonity/feature/features/modules/combat/aura/PointSearchResult;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\nPointSearch.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PointSearch.kt\nru/melonity/feature/features/modules/combat/aura/PointSearch\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,144:1\n1#2:145\n1056#3:146\n*S KotlinDebug\n*F\n+ 1 PointSearch.kt\nru/melonity/feature/features/modules/combat/aura/PointSearch\n*L\n116#1:146\n*E\n"})
public final class PointSearch {
   @NotNull
   public static final PointSearch INSTANCE = new PointSearch();
   @NotNull
   private static final PointSearchResult MISS;

   private PointSearch() {
   }

   @NotNull
   public final Pair<Float, Float> rotationToClosest(@NotNull class_1297 target, @NotNull Pair<Float, Float> original) {
      Intrinsics.checkNotNullParameter(target, "target");
      Intrinsics.checkNotNullParameter(original, "original");
      class_243 var10001 = this.getClosestTargetPoint(target).getPoint();
      class_746 var10002 = Feature.mc.field_1724;
      Intrinsics.checkNotNull(Feature.mc.field_1724);
      class_243 var15 = var10002.method_33571();
      Intrinsics.checkNotNullExpressionValue(var15, "getEyePos(...)");
      class_243 var3 = this.subtract(var10001, var15).method_1029();
      PointSearch var10000 = INSTANCE;
      Intrinsics.checkNotNull(var3);
      double local5_3 = var10000.component1(var3);
      double local7_4 = INSTANCE.component2(var3);
      double local9_5 = INSTANCE.component3(var3);
      Intrinsics.checkNotNull(Feature.mc.field_1724);
      float local13_0 = (float)(INSTANCE.toDegrees(Math.atan2(local9_5, local5_3)) - 90.0);
      float local14_1 = INSTANCE.findClosestAngle(((Number)original.getFirst()).floatValue(), local13_0);
      return TuplesKt.to(
         local14_1, INSTANCE.clampedBetween((float)(-INSTANCE.toDegrees(Math.atan2(local7_4, Math.hypot(local9_5, local5_3)))), TuplesKt.to(-90.0F, 90.0F))
      );
   }

   public final float findClosestAngle(float currentAngle, float targetAngle) {
      float local3_0 = targetAngle - currentAngle;

      while (local3_0 > 180.0F) {
         local3_0 -= 360.0F;
      }

      while (local3_0 < -180.0F) {
         local3_0 += 360.0F;
      }

      return currentAngle + local3_0;
   }

   private final class_243 subtract(class_243 $this$subtract, class_243 other) {
      class_243 var10000 = $this$subtract.method_1020(other);
      Intrinsics.checkNotNullExpressionValue(var10000, "subtract(...)");
      return var10000;
   }

   public final double toDegrees(double $this$toDegrees) {
      return Math.toDegrees($this$toDegrees);
   }

   private final float clampedBetween(float $this$clampedBetween, Pair<Float, Float> range) {
      return class_3532.method_15363($this$clampedBetween, ((Number)range.getFirst()).floatValue(), ((Number)range.getSecond()).floatValue());
   }

   private final double component1(class_243 $this$component1) {
      Intrinsics.checkNotNullParameter($this$component1, "<this>");
      return $this$component1.field_1352;
   }

   private final double component2(class_243 $this$component2) {
      Intrinsics.checkNotNullParameter($this$component2, "<this>");
      return $this$component2.field_1351;
   }

   private final double component3(class_243 $this$component3) {
      Intrinsics.checkNotNullParameter($this$component3, "<this>");
      return $this$component3.field_1350;
   }

   @NotNull
   public final PointSearchResult getClosestTargetPoint(@NotNull class_1297 entity) {
      Intrinsics.checkNotNullParameter(entity, "entity");
      class_746 var10001 = Feature.mc.field_1724;
      Intrinsics.checkNotNull(Feature.mc.field_1724);
      class_243 var2 = var10001.method_33571();
      Intrinsics.checkNotNullExpressionValue(var2, "getEyePos(...)");
      return this.getClosestTargetPoint(var2, entity, Math.min(entity.method_17681(), entity.method_17682()) / (float)4);
   }

   @NotNull
   public final PointSearchResult getClosestTargetPoint(@NotNull class_243 vec, @Nullable class_1297 entity, float point) {
      Intrinsics.checkNotNullParameter(vec, "vec");
      if (entity == null) {
         return MISS;
      } else {
         class_238 local4_14 = entity.method_5829().method_1014(-((double)point));
         class_243 local5_15 = local4_14.method_1005();
         List local6_16 = new ArrayList();

         for (double local7_17 = 0.0; local7_17 <= (local4_14.field_1320 - local4_14.field_1323) / (double)2; local7_17 += 0.15) {
            for (double local9_11 = 0.0; local9_11 <= (local4_14.field_1325 - local4_14.field_1322) / (double)2; local9_11 += 0.15) {
               for (double local11_10 = 0.0; local11_10 <= (local4_14.field_1324 - local4_14.field_1321) / (double)2; local11_10 += 0.15) {
                  int[] var14 = new int[]{-1, 1};
                  int[] local13_20 = var14;
                  int var39 = 0;

                  for (int local15_21 = var14.length; var39 < local15_21; var39++) {
                     int local16_9 = local13_20[var39];
                     int[] local18_13 = new int[]{-1, 1};
                     int[] var17 = local18_13;
                     int var43 = 0;

                     for (int local19_12 = local18_13.length; var43 < local19_12; var43++) {
                        int local20_8 = var17[var43];
                        int[] var22 = new int[]{-1, 1};
                        int[] var21 = var22;
                        int var47 = 0;

                        for (int var23 = var22.length; var47 < var23; var47++) {
                           int local24_7 = var21[var47];
                           double local25_1 = local5_15.field_1352 + (double)local16_9 * local7_17;
                           double local27_2 = local5_15.field_1351 + (double)local20_8 * local9_11;
                           double local29_3 = local5_15.field_1350 + (double)local24_7 * local11_10;
                           class_243 local31_4 = new class_243(local25_1, local27_2, local29_3);
                           class_241 local32_5 = RotationUtil.calculate(local31_4);
                           class_239 local33_6 = RayTraceUtil.calculateRayTrace(
                              (double)this.attackDist(), local32_5.field_1343, local32_5.field_1342, (class_1297)Feature.mc.field_1724, false
                           );
                           if (local33_6 instanceof class_3966 && Intrinsics.areEqual(((class_3966)local33_6).method_17782(), entity)) {
                              double local34_0 = vec.method_1022(local31_4);
                              local6_16.add(TuplesKt.to(local31_4, local34_0));
                           }
                        }
                     }
                  }
               }
            }
         }

         class_243 var10000;
         label40: {
            double local9_18 = class_3532.method_15350(vec.field_1352, local4_14.field_1323, local4_14.field_1320);
            double local11_19 = class_3532.method_15350(vec.field_1351, local4_14.field_1322, local4_14.field_1325);
            double local13_20 = class_3532.method_15350(vec.field_1350, local4_14.field_1321, local4_14.field_1324);
            class_243 local15_21 = new class_243(local9_18, local11_19, local13_20);
            Iterable var44 = local6_16;
            int local19_12 = 0;
            Pair var42 = (Pair)CollectionsKt.randomOrNull(
               CollectionsKt.take(CollectionsKt.sortedWith(var44, new PointSearch$getClosestTargetPoint$$inlined$sortedBy$1()), 5), (Random)Random.Default
            );
            if (var42 != null) {
               class_243 var45 = (class_243)var42.getFirst();
               if (var45 != null) {
                  var10000 = var45;
                  break label40;
               }
            }

            var10000 = local15_21;
         }

         class_243 local16_22 = var10000;
         class_746 var10004 = Feature.mc.field_1724;
         Intrinsics.checkNotNull(Feature.mc.field_1724);
         return new PointSearchResult(local16_22, local16_22.method_1022(var10004.method_33571()));
      }
   }

   private final float attackDist() {
      class_636 var10000 = Feature.mc.field_1761;
      Intrinsics.checkNotNull(Feature.mc.field_1761);
      // distanceAim is an ADDEND on top of 3.0 everywhere else in the aura
      // (AuraFeature lines 337/371/455 and the setting's own display lambda).
      // Losing the "3.0 +" made this a degenerate max() that always returned 3.0,
      // so point raycasts were verified at 3.0 while attacks happened at 3.0 + aim.
      return (float)Math.max(
         var10000.method_2920() == class_1934.field_9220 ? 6.0 : 3.0,
         3.0 + Melonity.INSTANCE.featureManager.auraFeature.getDistanceAim().value.doubleValue()
      );
   }

   @NotNull
   public final PointSearchResult getMISS() {
      return MISS;
   }

   @NotNull
   public final Pair<Float, Float> delta(@NotNull class_1309 target, @NotNull Pair<Float, Float> previous) {
      Intrinsics.checkNotNullParameter(target, "target");
      Intrinsics.checkNotNullParameter(previous, "previous");
      Pair local3_0 = this.rotationToClosest((class_1297)target, previous);
      return TuplesKt.to(
         ((Number)local3_0.getFirst()).floatValue() - ((Number)previous.getFirst()).floatValue(),
         -(((Number)previous.getSecond()).floatValue() - ((Number)local3_0.getSecond()).floatValue())
      );
   }

   static {
      class_243 var10002 = class_243.field_1353;
      Intrinsics.checkNotNullExpressionValue(class_243.field_1353, "ZERO");
      MISS = new PointSearchResult(var10002, 0.0);
   }
}
