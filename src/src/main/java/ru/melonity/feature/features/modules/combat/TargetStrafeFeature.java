package ru.melonity.feature.features.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3675;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.MoveEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.minecraft.MovementUtil;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\"B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001f\u0010\b\u001a\u00020\u00072\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u0016¢\u0006\u0004\b\b\u0010\tJ'\u0010\u000f\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fH\u0002¢\u0006\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\u0004\u0018\u00010\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0014\u001a\u00020\u00138\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00168\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0017\u0010\u0018R\u0014\u0010\u001a\u001a\u00020\u00198\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001e\u0010\u001fR\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\u001c8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b!\u0010\u001f¨\u0006#"},
   d2 = {"Lru/melonity/feature/features/modules/combat/TargetStrafeFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "", "value", "silent", "", "setEnabled", "(ZZ)V", "Lnet/minecraft/class_1309;", "entity", "", "x", "z", "getYaw", "(Lnet/minecraft/class_1309;DD)D", "target", "Lnet/minecraft/class_1309;", "", "side", "F", "Lru/melonity/feature/features/modules/combat/TargetStrafeFeature$StrafeMovement;", "strafeMovement", "Lru/melonity/feature/features/modules/combat/TargetStrafeFeature$StrafeMovement;", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "distanceSetting", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/UpdateLivingEvent;", "onUpdate", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/MoveEvent;", "onMove", "StrafeMovement", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class TargetStrafeFeature extends SimpleFeature {
   @Nullable
   private class_1309 target;
   private float side = 1.0F;
   @NotNull
   private final TargetStrafeFeature.StrafeMovement strafeMovement = new TargetStrafeFeature.StrafeMovement();
   @NotNull
   private final NumberSetting distanceSetting = new NumberSetting("global.range", TargetStrafeFeature::distanceSetting$lambda$0, 1, 6, 3);
   @NotNull
   private final EventListener<UpdateLivingEvent> onUpdate;
   @NotNull
   private final EventListener<MoveEvent> onMove;

   public TargetStrafeFeature() {
      super("TargetStrafe", MenuCategory.COMBAT, true);
      this.setting(this.distanceSetting);
      this.onUpdate = event -> onUpdate$lambda$3(this, event);
      this.onMove = event -> onMove$lambda$5(this, event);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      if (!value) {
         SimpleFeature.mc.field_1690.field_1849.method_23481(false);
         this.side = 1.0F;
         TargetStrafeFeature.StrafeMovement var3 = this.strafeMovement;
         var3.setOldSpeed(0.0);
         var3.setContextFriction(0.0);
         var3.setNeedSwap(false);
         var3.setNeedSprintState(false);
         var3.setCounter(0);
         var3.setNoSlowTicks(0);
      }
   }

   private final double getYaw(class_1309 entity, double x, double z) {
      return Math.toDegrees(Math.atan2(z - entity.method_23321(), x - entity.method_23317())) - 90.0;
   }

   private static final String distanceSetting$lambda$0(Number value) {
      return String.valueOf(value.intValue());
   }

   private static final void onUpdate$lambda$3(TargetStrafeFeature this$0, UpdateLivingEvent event) {
      if (this$0.target != null) {
         class_1309 local2_0 = this$0.target;
         if (local2_0.method_5805()) {
            class_746 var10000 = SimpleFeature.mc.field_1724;
            Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
            if (var10000.method_24828() && !SimpleFeature.mc.field_1690.field_1903.method_1434()) {
               var10000 = SimpleFeature.mc.field_1724;
               Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
               var10000.method_6043();
            }

            SimpleFeature.mc.field_1690.field_1849.method_23481(true);
         }
      }
   }

   private static final void onMove$lambda$5(TargetStrafeFeature this$0, MoveEvent event) {
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 local2_6 = SimpleFeature.mc.field_1724;
         if (SimpleFeature.mc.field_1687 != null && local2_6.field_6012 >= 10) {
            boolean local3_7 = class_3675.method_15987(class_310.method_1551().method_22683().method_4490(), 65);
            boolean local4_8 = class_3675.method_15987(class_310.method_1551().method_22683().method_4490(), 68);
            class_1309 local5_9 = Melonity.INSTANCE.featureManager.auraFeature.getTarget();
            if (local5_9 == null && this$0.target != null) {
               SimpleFeature.mc.field_1690.field_1849.method_23481(false);
            }

            this$0.target = local5_9;
            if (this$0.target != null) {
               class_1309 local6_5 = this$0.target;
               if (local6_5.method_5805() && local6_5.method_6032() > 0.0F) {
                  if (local2_6.field_5976) {
                     this$0.side *= (float)-1;
                  }

                  if (local3_7) {
                     this$0.side = 1.0F;
                  }

                  if (local4_8) {
                     this$0.side = -1.0F;
                  }

                  double local8_0 = Math.atan2(local2_6.method_23321() - local6_5.method_23321(), local2_6.method_23317() - local6_5.method_23317());
                  local8_0 += MovementUtil.getMotion()
                     / (double)Math.max(local2_6.method_5739((class_1297)local6_5), this$0.distanceSetting.getMin().floatValue())
                     * (double)this$0.side;
                  double local10_1 = local6_5.method_23317() + (double)this$0.distanceSetting.value.floatValue() * Math.cos(local8_0);
                  double local12_2 = local6_5.method_23321() + (double)this$0.distanceSetting.value.floatValue() * Math.sin(local8_0);
                  double local14_3 = this$0.getYaw((class_1309)local2_6, local10_1, local12_2);
                  TargetStrafeFeature.StrafeMovement var10000 = this$0.strafeMovement;
                  Intrinsics.checkNotNull(event);
                  double local16_4 = var10000.calculateSpeed(event, false, false, true, 0.0F);
                  event.motion = new class_243(
                     local16_4 * -Math.sin(Math.toRadians(local14_3)), event.motion.field_1351, local16_4 * Math.cos(Math.toRadians(local14_3))
                  );
               }
            }
         }
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\b\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J7\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\nH\u0007¢\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002¢\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0013\u0010\u0014R\"\u0010\u0015\u001a\u00020\f8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\"\u0010\u001b\u001a\u00020\f8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\u001b\u0010\u0016\u001a\u0004\b\u001c\u0010\u0018\"\u0004\b\u001d\u0010\u001aR\"\u0010\u001e\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\"\u0010$\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b$\u0010\u001f\u001a\u0004\b%\u0010!\"\u0004\b&\u0010#R\"\u0010(\u001a\u00020'8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\"\u0010.\u001a\u00020'8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b.\u0010)\u001a\u0004\b/\u0010+\"\u0004\b0\u0010-¨\u00061"},
      d2 = {"Lru/melonity/feature/features/modules/combat/TargetStrafeFeature$StrafeMovement;", "", "<init>", "()V", "Lru/melonity/util/event/events/MoveEvent;", "move", "", "damageBoost", "hasTime", "autoJump", "", "damageSpeed", "", "calculateSpeed", "(Lru/melonity/util/event/events/MoveEvent;ZZZF)D", "Lnet/minecraft/class_746;", "contextPlayer", "getAIMoveSpeed", "(Lnet/minecraft/class_746;)F", "getFrictionFactor", "(Lnet/minecraft/class_746;Lru/melonity/util/event/events/MoveEvent;)F", "oldSpeed", "D", "getOldSpeed", "()D", "setOldSpeed", "(D)V", "contextFriction", "getContextFriction", "setContextFriction", "needSwap", "Z", "getNeedSwap", "()Z", "setNeedSwap", "(Z)V", "needSprintState", "getNeedSprintState", "setNeedSprintState", "", "counter", "I", "getCounter", "()I", "setCounter", "(I)V", "noSlowTicks", "getNoSlowTicks", "setNoSlowTicks", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   private static final class StrafeMovement {
      private double oldSpeed;
      private double contextFriction;
      private boolean needSwap;
      private boolean needSprintState;
      private int counter;
      private int noSlowTicks;

      public StrafeMovement() {
      }

      public final double getOldSpeed() {
         return this.oldSpeed;
      }

      public final void setOldSpeed(double value) {
         this.oldSpeed = value;
      }

      public final double getContextFriction() {
         return this.contextFriction;
      }

      public final void setContextFriction(double value) {
         this.contextFriction = value;
      }

      public final boolean getNeedSwap() {
         return this.needSwap;
      }

      public final void setNeedSwap(boolean value) {
         this.needSwap = value;
      }

      public final boolean getNeedSprintState() {
         return this.needSprintState;
      }

      public final void setNeedSprintState(boolean value) {
         this.needSprintState = value;
      }

      public final int getCounter() {
         return this.counter;
      }

      public final void setCounter(int value) {
         this.counter = value;
      }

      public final int getNoSlowTicks() {
         return this.noSlowTicks;
      }

      public final void setNoSlowTicks(int value) {
         this.noSlowTicks = value;
      }

      public final double calculateSpeed(@NotNull MoveEvent move, boolean damageBoost, boolean hasTime, boolean autoJump, float damageSpeed) {
         class_746 player = SimpleFeature.mc.field_1724;
         if (player == null) {
            this.oldSpeed = 0.0;
            return 0.0;
         }

         boolean onGround = player.method_24828();
         float aiMoveSpeed = this.getAIMoveSpeed(player);
         double vanillaSpeed = Math.hypot(move.motion.field_1352, move.motion.field_1350);

         // getFrictionFactor() reads whatever block happens to be under the player, which
         // in the middle of a hop is usually nothing at all, so the value of the surface
         // actually jumped from is carried over.
         if (onGround) {
            this.contextFriction = (double)this.getFrictionFactor(player, move);
         }

         if (this.contextFriction <= 0.0) {
            this.contextFriction = 0.6 * 0.91;
         }

         // contextFriction is slipperiness * 0.91, the factor vanilla multiplies the
         // previous horizontal motion by; the acceleration term uses the raw slipperiness.
         double slipperiness = this.contextFriction / 0.91;
         double acceleration = (double)aiMoveSpeed * (0.21600002 / (slipperiness * slipperiness * slipperiness));
         double groundSpeed = acceleration / (1.0 - this.contextFriction);

         // A wall takes the whole horizontal momentum away, the chain cannot be continued
         // from the old speed and has to be rebuilt from the ground speed instead.
         if (player.field_5976) {
            this.needSwap = true;
         }

         if (player.method_6115()) {
            this.noSlowTicks = Math.min(this.noSlowTicks + 1, 20);
         } else if (this.noSlowTicks > 0) {
            this.noSlowTicks--;
         }

         double speed;
         if (onGround) {
            this.counter = 0;
            this.needSprintState = player.method_5624();
            if (this.needSwap) {
               speed = groundSpeed;
               this.needSwap = false;
            } else if (autoJump) {
               // The feature jumps on every ground tick, so the run up and the sprint jump
               // boost that vanilla already added have to survive into the hop.
               speed = Math.max(groundSpeed, vanillaSpeed);
            } else {
               speed = groundSpeed;
            }
         } else {
            this.counter++;
            // Airborne vanilla update: motion * 0.91 plus the off ground acceleration.
            speed = this.oldSpeed * 0.91 + (this.needSprintState ? 0.026 : 0.02);
            if (this.needSwap) {
               speed = vanillaSpeed;
               this.needSwap = false;
            } else {
               speed = Math.max(speed, vanillaSpeed);
            }
         }

         if (this.noSlowTicks > 0 && player.method_6115()) {
            // Still bound by the item use slowdown vanilla applies to the input.
            speed *= 0.2;
         }

         if (damageBoost) {
            double boosted = (double)damageSpeed;
            if (hasTime) {
               // The boost is allowed to be held, but it bleeds off over the hop instead
               // of ending in one tick.
               boosted *= Math.max(0.0, 1.0 - (double)this.counter / 20.0);
            }

            speed = Math.max(speed, boosted);
         }

         this.oldSpeed = speed;
         return speed;
      }

      private final float getAIMoveSpeed(class_746 contextPlayer) {
         boolean local2_0 = contextPlayer.method_5624();
         contextPlayer.method_5728(false);
         float local3_1 = contextPlayer.method_6029() * 1.3F;
         contextPlayer.method_5728(local2_0);
         return local3_1;
      }

      private final float getFrictionFactor(class_746 contextPlayer, MoveEvent move) {
         return contextPlayer.method_37908()
               .method_8320(new class_2338((int)move.from.field_1352, (int)(move.getAabbFrom().field_1322 - 1.0), (int)move.from.field_1350))
               .method_26204()
               .method_9499()
            * 0.91F;
      }
   }
}
