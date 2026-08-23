package ru.melonity.feature.features.modules.combat;

import java.util.Comparator;
import java.util.HashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1667;
import net.minecraft.class_1684;
import net.minecraft.class_1776;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2868;
import net.minecraft.class_2879;
import net.minecraft.class_2886;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.jetbrains.annotations.NotNull;
import ru.melonity.Melonity;
import ru.melonity.feature.ActionScheduler;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventEntitySpawn;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.time.Timer;

@Environment(EnvType.CLIENT)
public class PearlTargetFeature extends SimpleFeature {
   private final BooleanSetting auraTargetOnly = new BooleanSetting("pearltarget.auratargetonly", true);
   Runnable runnableAction;
   private final Timer delayTimer = Timer.create();
   private class_2338 targetBlock;
   private int lastPearlId;
   private int lastOurPearlId;
   private int originalSlot = -1;
   private HashMap<class_1657, Long> targets = new HashMap<>();
   private boolean isThrowingPearl = false;
   private final EventListener<EventEntitySpawn> onEntitySpawn = e -> {
      if (e.target() instanceof class_1684) {
         mc.field_1687.method_18456().stream().min(Comparator.comparingDouble(p -> p.method_5707(e.target().method_19538()))).ifPresent(player -> {
            if (player.equals(mc.field_1724)) {
               this.lastOurPearlId = e.target().method_5628();
            }
         });
      }
   };
   private final EventListener<UpdateEvent> onUpdate = event -> {
      if (this.runnableAction != null) {
         this.runnableAction.run();
      } else {
         AuraFeature local2_3 = Melonity.INSTANCE.getFeatureManager().auraFeature;
         if (this.auraTargetOnly.get()) {
            class_1309 local3_1 = local2_3.getTarget();
            if (local3_1 != null && local2_3.isEnabled() && local3_1 instanceof class_1657 local4_0 && !this.targets.containsKey(local4_0)) {
               this.targets.put(local4_0, System.currentTimeMillis());
            }

            new HashMap<>(this.targets).forEach((k, v) -> {
               if (System.currentTimeMillis() - v > 10000L) {
                  this.targets.remove(k);
               }
            });
         }

         if (!(mc.field_1724.method_6032() < 5.0F) && this.delayTimer.hasReached(1000L) && !this.isThrowingPearl) {
            for (class_1297 local4_2 : mc.field_1687.method_18112()) {
               if (local4_2 instanceof class_1684 && local4_2.method_5628() != this.lastPearlId && local4_2.method_5628() != this.lastOurPearlId) {
                  mc.field_1687
                     .method_18456()
                     .stream()
                     .filter(e -> this.targets.containsKey(e) || !this.auraTargetOnly.get())
                     .min(Comparator.comparingDouble(p -> p.method_5707(local4_2.method_19538())))
                     .ifPresent(player -> {
                        if (!player.equals(mc.field_1724)) {
                           this.targetBlock = this.calcTrajectory(local4_2);
                           this.lastPearlId = local4_2.method_5628();
                        }
                     });
               }
            }

            if (this.targetBlock != null && !(mc.field_1724.method_5707(this.targetBlock.method_46558()) < 49.0)) {
               float local3_4 = (float)(-Math.toDegrees((double)this.calcTrajectory(this.targetBlock)));
               float local4_5 = (float)Math.toDegrees(
                     Math.atan2(
                        (double)((float)this.targetBlock.method_10260() + 0.5F) - mc.field_1724.method_23321(),
                        (double)((float)this.targetBlock.method_10263() + 0.5F) - mc.field_1724.method_23317()
                     )
                  )
                  - 90.0F;
               class_2338 local5_6 = this.checkTrajectory(local4_5, local3_4);
               if (local5_6 != null && !(this.targetBlock.method_19770(local5_6.method_46558()) > 36.0)) {
                  RotationManager.INSTANCE.rotate(local4_5, class_3532.method_15363(local3_4, -90.0F, 90.0F));
                  int local6_7 = this.findEPSlot();
                  if (local6_7 != -1 && RotationManager.INSTANCE.getYaw() == local4_5 && RotationManager.INSTANCE.getPitch() == local3_4) {
                     this.throwPearl(local6_7);
                     RotationManager.INSTANCE.reset();
                  }

                  this.targetBlock = null;
                  this.delayTimer.reset();
               }
            }
         }
      }
   };

   public PearlTargetFeature() {
      super("PearlTarget", MenuCategory.COMBAT);
      this.setting(this.auraTargetOnly);
   }

   private void throwPearl(int pearlSlot) {
      this.isThrowingPearl = true;
      class_1268 local2_0 = mc.field_1724.method_6079().method_7909() instanceof class_1776 ? class_1268.field_5810 : class_1268.field_5808;
      this.originalSlot = mc.field_1724.method_31548().field_7545;
      ActionScheduler.safeSchedule(() -> {
         if (local2_0 == class_1268.field_5808 && pearlSlot != this.originalSlot) {
            mc.field_1724.method_31548().field_7545 = pearlSlot;
            mc.field_1724.field_3944.method_52787(new class_2868(pearlSlot));
         }
      }, 1);
      ActionScheduler.safeSchedule(() -> {
         mc.field_1761.method_2919(mc.field_1724, local2_0);
         mc.field_1724.method_6104(local2_0);
         mc.field_1724.field_3944.method_52787(new class_2886(local2_0, 0, RotationManager.INSTANCE.getYaw(), RotationManager.INSTANCE.getPitch()));
         mc.field_1724.field_3944.method_52787(new class_2879(local2_0));
      }, 2);
      ActionScheduler.safeSchedule(() -> {
         if (local2_0 == class_1268.field_5808 && this.originalSlot >= 0 && this.originalSlot <= 8) {
            mc.field_1724.method_31548().field_7545 = this.originalSlot;
            mc.field_1724.field_3944.method_52787(new class_2868(this.originalSlot));
         }
      }, 3);
      ActionScheduler.safeSchedule(() -> {
         this.isThrowingPearl = false;
         RotationManager.INSTANCE.reset();
      }, 4);
   }

   private int findEPSlot() {
      if (mc.field_1724.method_6047().method_7909() == class_1802.field_8634) {
         return mc.field_1724.method_31548().field_7545;
      } else if (mc.field_1724.method_6079().method_7909() == class_1802.field_8634) {
         return -2;
      } else {
         for (int local1_0 = 0; local1_0 < 9; local1_0++) {
            if (mc.field_1724.method_31548().method_5438(local1_0).method_7909() == class_1802.field_8634) {
               return local1_0;
            }
         }

         return -1;
      }
   }

   private float calcTrajectory(@NotNull class_2338 bp) {
      double local2_0 = Math.hypot(
         (double)((float)bp.method_10263() + 0.5F) - mc.field_1724.method_23317(), (double)((float)bp.method_10260() + 0.5F) - mc.field_1724.method_23321()
      );
      double local4_1 = 6.125
         * ((double)((float)bp.method_10264() + 1.0F) - (mc.field_1724.method_23318() + (double)mc.field_1724.method_18381(mc.field_1724.method_18376())));
      local4_1 = 0.05F * (0.05F * local2_0 * local2_0 + local4_1);
      local4_1 = Math.sqrt(9.378906F - local4_1);
      double local6_2 = 3.0625 - local4_1;
      local4_1 = Math.atan2(local6_2 * local6_2 + local4_1, 0.05F * local2_0);
      local6_2 = Math.atan2(local6_2, 0.05F * local2_0);
      return (float)Math.min(local4_1, local6_2);
   }

   private class_2338 calcTrajectory(class_1297 e) {
      return this.traceTrajectory(
         e.method_23317(), e.method_23318(), e.method_23321(), e.method_18798().field_1352, e.method_18798().field_1351, e.method_18798().field_1350
      );
   }

   private class_2338 checkTrajectory(float yaw, float pitch) {
      if (Float.isNaN(pitch)) {
         return null;
      } else {
         float local3_0 = yaw / 180.0F * (float) Math.PI;
         float local4_1 = pitch / 180.0F * (float) Math.PI;
         double local5_2 = mc.field_1724.method_23317() - (double)(class_3532.method_15362(local3_0) * 0.16F);
         double local7_3 = mc.field_1724.method_23318() + (double)mc.field_1724.method_18381(mc.field_1724.method_18376()) - 0.1000000014901161;
         double local9_4 = mc.field_1724.method_23321() - (double)(class_3532.method_15374(local3_0) * 0.16F);
         double local11_5 = (double)(-class_3532.method_15374(local3_0) * class_3532.method_15362(local4_1) * 0.4F);
         double local13_6 = (double)(-class_3532.method_15374(local4_1) * 0.4F);
         double local15_7 = (double)(class_3532.method_15362(local3_0) * class_3532.method_15362(local4_1) * 0.4F);
         float local17_8 = class_3532.method_15355((float)(local11_5 * local11_5 + local13_6 * local13_6 + local15_7 * local15_7));
         local11_5 /= (double)local17_8;
         local13_6 /= (double)local17_8;
         local15_7 /= (double)local17_8;
         local11_5 *= 1.5;
         local13_6 *= 1.5;
         local15_7 *= 1.5;
         if (!mc.field_1724.method_24828()) {
            local13_6 += mc.field_1724.method_18798().method_10214();
         }

         return this.traceTrajectory(local5_2, local7_3, local9_4, local11_5, local13_6, local15_7);
      }
   }

   private class_2338 traceTrajectory(double x, double y, double z, double mx, double my, double mz) {
      for (int local14_4 = 0; local14_4 < 300; local14_4++) {
         class_243 local13_3 = new class_243(x, y, z);
         x += mx;
         y += my;
         z += mz;
         mx *= 0.99;
         my *= 0.99;
         mz *= 0.99;
         my -= 0.03F;
         class_243 local15_1 = new class_243(x, y, z);
         class_3965 local16_2 = mc.field_1687.method_17742(new class_3959(local13_3, local15_1, class_3960.field_17559, class_242.field_1348, mc.field_1724));
         if (local16_2 != null && local16_2.method_17783() == class_240.field_1332) {
            return local16_2.method_17777();
         }

         for (class_1297 local18_0 : mc.field_1687.method_18112()) {
            if (!(local18_0 instanceof class_1667)
               && local18_0 != mc.field_1724
               && !(local18_0 instanceof class_1684)
               && local18_0.method_5829().method_994(new class_238(x - 0.3, y - 0.3, z - 0.3, x + 0.3, y + 0.3, z + 0.2))) {
               return null;
            }
         }

         if (y <= -65.0) {
            break;
         }
      }

      return null;
   }
}
