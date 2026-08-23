package ru.melonity.util.modules;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1743;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1810;
import net.minecraft.class_1821;
import net.minecraft.class_1829;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_3966;
import net.minecraft.class_638;
import net.minecraft.class_746;
import net.minecraft.class_9362;
import org.jetbrains.annotations.NotNull;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.misc.TPSSyncFeature;
import ru.melonity.util.minecraft.MinecraftInstance;
import ru.melonity.util.time.Timer;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J/\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0007¢\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\f\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000e\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u000e\u0010\rJ\u001f\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0003¢\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\u0013\u0010\u0014J'\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0007¢\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\u0018\u0010\u0014J\u000f\u0010\u0019\u001a\u00020\u0007H\u0007¢\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u001bH\u0003¢\u0006\u0004\b\u001d\u0010\u001eR\u001c\u0010!\u001a\n  *\u0004\u0018\u00010\u001f0\u001f8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b!\u0010\"¨\u0006#"},
   d2 = {"Lru/melonity/util/modules/SyncUtil;", "", "<init>", "()V", "", "space", "oldAttack", "", "min", "max", "canAttack", "(ZZFF)Z", "validateHitboxTarget", "()Z", "canAttackInWaterOrFluid", "", "updateDelays", "(FF)I", "cpsValue", "applyResearchBasedAdjustments", "(F)F", "outedCooldownWithSync", "(ZFF)Z", "baseCooldown", "calculateEnhancedCooldownThreshold", "getSyncedCooldownBT", "()F", "Lnet/minecraft/class_1799;", "activeStack", "getCooldownPick", "(Lnet/minecraft/class_1799;)F", "Lru/melonity/util/time/Timer;", "kotlin.jvm.PlatformType", "oldATK", "Lru/melonity/util/time/Timer;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class SyncUtil {
   @NotNull
   public static final SyncUtil INSTANCE = new SyncUtil();
   private static final Timer oldATK = Timer.create();

   private SyncUtil() {
   }

   @JvmStatic
   public static final boolean canAttack(boolean space, boolean oldAttack, float min, float max) {
      if (MinecraftInstance.mc.field_1724 != null && MinecraftInstance.mc.field_1687 != null) {
         class_746 var10000 = MinecraftInstance.mc.field_1724;
         Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
         boolean local4_0 = var10000.method_24828() && !MinecraftInstance.mc.field_1690.field_1903.method_1434() && space;
         var10000 = MinecraftInstance.mc.field_1724;
         Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
         boolean local5_1 = var10000.field_6017 > 0.0F;
         boolean local6_2 = INSTANCE.canAttackInWaterOrFluid();
         boolean local7_3 = outedCooldownWithSync(oldAttack, min, max);
         boolean local8_4 = INSTANCE.validateHitboxTarget();
         return (local4_0 || local5_1 || local6_2) && local7_3 && local8_4;
      } else {
         return false;
      }
   }

   private final boolean validateHitboxTarget() {
      if (MinecraftInstance.mc.field_1765 instanceof class_3966) {
         class_239 var6 = MinecraftInstance.mc.field_1765;
         Intrinsics.checkNotNull(MinecraftInstance.mc.field_1765, "null cannot be cast to non-null type net.minecraft.util.hit.EntityHitResult");
         class_3966 local1_0 = (class_3966)var6;
         return local1_0.method_17782() != null;
      } else {
         class_746 var10000 = MinecraftInstance.mc.field_1724;
         Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
         class_243 local1_1 = var10000.method_19538();
         class_238 local2_2 = new class_238(local1_1.method_1031(-3.0, -3.0, -3.0), local1_1.method_1031(3.0, 3.0, 3.0));
         class_638 var4 = MinecraftInstance.mc.field_1687;
         Intrinsics.checkNotNull(MinecraftInstance.mc.field_1687);
         class_746 var10001 = MinecraftInstance.mc.field_1724;
         Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
         List var5 = var4.method_8335((class_1297)var10001, local2_2);
         Intrinsics.checkNotNullExpressionValue(var5, "getOtherEntities(...)");
         return !var5.isEmpty();
      }
   }

   private final boolean canAttackInWaterOrFluid() {
      class_746 var10000 = MinecraftInstance.mc.field_1724;
      Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
      if (!var10000.method_5681()) {
         var10000 = MinecraftInstance.mc.field_1724;
         Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
         if (!(var10000.field_6017 > 0.0F)) {
            return false;
         }
      }

      var10000 = MinecraftInstance.mc.field_1724;
      Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
      if (!var10000.method_52535() || !MinecraftInstance.mc.field_1690.field_1903.method_1434()) {
         return true;
      } else {
         var10000 = MinecraftInstance.mc.field_1724;
         Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
         return var10000.field_6017 > 0.0F;
      }
   }

   /**
    * Turns a CPS window into the delay, in milliseconds, that has to pass between two 1.8 style
    * attacks. A click rate is rolled inside {@code [min, max]} and then bent by
    * {@link #applyResearchBasedAdjustments(float)}, so the resulting pattern follows the player's
    * state instead of being perfectly uniform.
    */
   private final int updateDelays(float min, float max) {
      float low = Math.min(min, max);
      float high = Math.max(min, max);
      float cps = low + (float)Math.random() * (high - low);
      float adjusted = this.applyResearchBasedAdjustments(cps);
      return adjusted <= 0.0F ? 1000 : (int)(1000.0F / adjusted);
   }

   private final float applyResearchBasedAdjustments(float cpsValue) {
      float local2_0;
      label25: {
         local2_0 = 1.0F;
         class_746 var10000 = MinecraftInstance.mc.field_1724;
         Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
         if (var10000.field_6017 > 0.0F) {
            var10000 = MinecraftInstance.mc.field_1724;
            Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
            if (var10000.field_6017 < 1.5F) {
               local2_0 *= 1.05F;
               break label25;
            }
         }

         var10000 = MinecraftInstance.mc.field_1724;
         Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
         if (var10000.field_6017 > 1.5F) {
            local2_0 *= 0.95F;
         }
      }

      class_746 var7 = MinecraftInstance.mc.field_1724;
      Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
      if (!var7.method_24828()) {
         local2_0 *= 0.98F;
      }

      var7 = MinecraftInstance.mc.field_1724;
      Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
      class_1799 local3_1 = var7.method_6047();
      class_1792 local4_2 = local3_1.method_7909();
      if (local4_2 instanceof class_1829) {
         local2_0 *= 0.97F;
      } else if (local4_2 instanceof class_1743) {
         local2_0 *= 1.03F;
      }

      return cpsValue * local2_0;
   }

   /**
    * Tells whether the attack cooldown is over.
    * In 1.8 mode ({@code oldAttack}) the gate is the {@link #oldATK} timer driven by the CPS window,
    * otherwise the vanilla 1.9+ charge {@code method_7261} is compared against the item threshold,
    * measured with the TPS synchronised base time from {@link #getSyncedCooldownBT()}.
    */
   @JvmStatic
   public static final boolean outedCooldownWithSync(boolean oldAttack, float min, float max) {
      class_746 player = MinecraftInstance.mc.field_1724;
      if (player == null) {
         return false;
      } else if (oldAttack) {
         if (!oldATK.hasTimeElapsed((double)INSTANCE.updateDelays(min, max))) {
            return false;
         } else {
            oldATK.reset();
            return true;
         }
      } else {
         float threshold = INSTANCE.calculateEnhancedCooldownThreshold(INSTANCE.getCooldownPick(player.method_6047()));
         return player.method_7261(getSyncedCooldownBT()) >= Math.min(threshold, 1.0F);
      }
   }

   private final float calculateEnhancedCooldownThreshold(float baseCooldown) {
      class_746 var10000 = MinecraftInstance.mc.field_1724;
      Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
      class_1799 local2_0 = var10000.method_6047();
      class_1792 local3_1 = local2_0.method_7909();
      float local4_2 = 0.0F;
      if (local3_1 instanceof class_1829) {
         local4_2 = 0.02F;
      } else if (local3_1 instanceof class_1743) {
         local4_2 = 0.01F;
      } else if (local3_1 instanceof class_1810 || local3_1 instanceof class_1821) {
         local4_2 = 0.03F;
      } else if (local3_1 instanceof class_9362) {
         local4_2 = 0.05F;
      }

      var10000 = MinecraftInstance.mc.field_1724;
      Intrinsics.checkNotNull(MinecraftInstance.mc.field_1724);
      if (var10000.field_6017 > 0.0F) {
         local4_2 += 0.02F;
      }

      return baseCooldown + local4_2;
   }

   @JvmStatic
   public static final float getSyncedCooldownBT() {
      TPSSyncFeature local0_1 = Melonity.INSTANCE.featureManager.tpsSyncFeature;
      if (local0_1 != null && local0_1.isEnabled()) {
         float local1_0 = local0_1.getAdjustTicks();
         return Math.max(0.5F, Math.min(2.0F, local1_0));
      } else {
         return 1.5F;
      }
   }

   /**
    * Base share of the vanilla attack charge that the held stack has to reach before it may swing.
    * {@link #calculateEnhancedCooldownThreshold(float)} adds the item/state surcharge on top, which
    * brings every weapon class back to roughly the same effective threshold.
    */
   private final float getCooldownPick(class_1799 activeStack) {
      class_1792 item = activeStack.method_7909();
      if (item instanceof class_1829) {
         return 0.92F;
      } else if (item instanceof class_1743) {
         return 0.92F;
      } else if (item instanceof class_1810 || item instanceof class_1821) {
         return 0.9F;
      } else {
         return item instanceof class_9362 ? 0.88F : 0.93F;
      }
   }
}
