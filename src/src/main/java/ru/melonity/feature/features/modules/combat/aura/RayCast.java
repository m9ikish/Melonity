package ru.melonity.feature.features.modules.combat.aura;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_638;
import net.minecraft.class_746;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.feature.Feature;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J'\u0010\n\u001a\u00020\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b¢\u0006\u0004\b\n\u0010\u000b¨\u0006\f"},
   d2 = {"Lru/melonity/feature/features/modules/combat/aura/RayCast;", "", "<init>", "()V", "Lnet/minecraft/class_1309;", "target", "", "reachDistance", "", "ignoreBlocks", "fire", "(Lnet/minecraft/class_1309;DZ)Z", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class RayCast {
   @NotNull
   public static final RayCast INSTANCE = new RayCast();

   private RayCast() {
   }

   public final boolean fire(@Nullable class_1309 target, double reachDistance, boolean ignoreBlocks) {
      if (Feature.mc.field_1724 != null && target != null && Feature.mc.field_1687 != null) {
         class_238 local5_0 = target.method_5829().method_1014(0.3);
         class_746 var10001 = Feature.mc.field_1724;
         Intrinsics.checkNotNull(Feature.mc.field_1724);
         if (local5_0.method_1006(var10001.method_33571())) {
            return true;
         } else {
            class_243 local6_1 = target.method_19538().method_1031(0.0, (double)target.method_17682() / 2.0, 0.0);
            class_746 var10000 = Feature.mc.field_1724;
            Intrinsics.checkNotNull(Feature.mc.field_1724);
            if (var10000.method_33571().method_1022(local6_1) > reachDistance + 0.5) {
               return false;
            } else {
               class_746 var10002 = Feature.mc.field_1724;
               Intrinsics.checkNotNull(Feature.mc.field_1724);
               class_3959 local7_2 = new class_3959(
                  var10002.method_33571(), local6_1, class_3960.field_17558, class_242.field_1348, (class_1297)Feature.mc.field_1724
               );
               class_638 var9 = Feature.mc.field_1687;
               Intrinsics.checkNotNull(Feature.mc.field_1687);
               class_3965 local8_3 = var9.method_17742(local7_2);
               if (ignoreBlocks && local8_3.method_17783() == class_240.field_1332) {
                  return false;
               } else {
                  var10001 = Feature.mc.field_1724;
                  Intrinsics.checkNotNull(Feature.mc.field_1724);
                  return local5_0.method_992(var10001.method_33571(), local6_1).isPresent();
               }
            }
         }
      } else {
         return false;
      }
   }
}
