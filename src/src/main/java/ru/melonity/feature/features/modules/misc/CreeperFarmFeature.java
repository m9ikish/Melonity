package ru.melonity.feature.features.modules.misc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1548;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_3959;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import ru.melonity.feature.features.baritone.setting.MenuButtonSetting;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.util.minecraft.RotationUtil;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeaturePrefix;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.UpdateLivingEvent;

@Environment(EnvType.CLIENT)
public class CreeperFarmFeature extends SimpleFeature {
   private class_2338 first;
   private class_2338 second;
   private class_1297 current;
   private class_2338 target;
   private class_243 need;
   private int currentCorner = 0;
   private final EventListener<UpdateLivingEvent> onUpdate = this::handleUpdate;
   private final EventListener<EventInput> onInput = event -> {
      boolean local1_2 = false;
      boolean local2_3 = false;

      for (class_1297 local4_1 : mc.field_1687.method_18112()) {
         if (local4_1 instanceof class_1548) {
            class_1548 local5_0 = (class_1548)local4_1;
            if (local5_0.method_5805() && local5_0.field_6213 == 0) {
               if ((double)mc.field_1724.method_5739(local4_1) < 3.8) {
                  local1_2 = true;
               }

               if (mc.field_1724.method_5739(local4_1) < 6.0F) {
                  local2_3 = true;
               }
            }
         }
      }

      event.setForward(local1_2 ? -1.0F : 1.0F);
      event.setJump(!local2_3 && !mc.field_1724.field_5976);
      if (mc.field_1724.method_18798().method_1033() < 0.105F && mc.field_1724.field_5976) {
         event.setStrafe(mc.field_1724.field_6012 % 60 < 30 ? -1.0F : 1.0F);
      }

      mc.field_1724.method_5728(true);
   };

   public CreeperFarmFeature() {
      super("CreeperFarm", MenuCategory.MISC);
      MenuButtonSetting local1_0 = new MenuButtonSetting("creeperfarm.firstpos", () -> {
         this.first = mc.field_1724.method_24515();
         mc.field_1705.method_1743().method_1812(class_2561.method_43470("Первая позиция была установлена там, где вы стоите"));
      });
      MenuButtonSetting local2_1 = new MenuButtonSetting("creeperfarm.secondpos", () -> {
         this.second = mc.field_1724.method_24515();
         mc.field_1705.method_1743().method_1812(class_2561.method_43470("Вторая позиция была установлена там, где вы стоите"));
      });
      this.settings(new MenuFeatureView[]{local1_0, local2_1});
      this.prefix(new MenuFeaturePrefix[]{MenuFeaturePrefix.Funtime});
   }

   @Override
   public void setEnabled(boolean enabled) {
      super.setEnabled(enabled);
      this.current = null;
      RotationManager.INSTANCE.reset();
   }

   private void handleUpdate(UpdateLivingEvent event) {
      if (!this.inInvalidState() && this.first != null && this.second != null) {
         this.current = null;
         double closestCreeper = Double.MAX_VALUE;

         for (class_1297 entity : mc.field_1687.method_18112()) {
            if (entity instanceof class_1548) {
               class_1548 creeper = (class_1548)entity;
               if (creeper.method_5805() && creeper.field_6213 == 0 && this.inpos(creeper.method_24515())) {
                  double distance = (double)mc.field_1724.method_5739(entity);
                  if (distance < closestCreeper) {
                     closestCreeper = distance;
                     this.current = entity;
                  }
               }
            }
         }

         class_2338 playerPos = mc.field_1724.method_24515();
         if (this.target == null || playerPos.method_19455(this.target) <= 2) {
            this.target = this.getNextCorner();
         }

         class_243 eyes = mc.field_1724.method_33571();
         if (this.current != null && closestCreeper < 3.8) {
            float[] retreatAngles = RotationUtil.calculateAngle(eyes, this.current.method_33571());
            RotationManager.INSTANCE.rotate(retreatAngles[0], retreatAngles[1]);
         } else {
            class_243 position = mc.field_1724.method_19538();
            List<class_243> reachable = new ArrayList<>();

            for (int angle = 0; angle < 360; angle += 15) {
               double radians = Math.toRadians((double)angle);
               class_243 candidate = new class_243(
                  position.field_1352 + Math.cos(radians) * 3.0, position.field_1351, position.field_1350 + Math.sin(radians) * 3.0
               );
               class_243 rayEnd = new class_243(candidate.field_1352, eyes.field_1351, candidate.field_1350);
               if (this.point(eyes, rayEnd).method_17783() == class_240.field_1333) {
                  reachable.add(candidate);
               }
            }

            this.need = reachable.stream()
               .min(Comparator.comparingDouble(candidate -> candidate.method_1022(this.target.method_46558())))
               .orElse(this.target.method_46558());
            float[] walkAngles = RotationUtil.calculateAngle(eyes, this.need);
            RotationManager.INSTANCE.rotate(walkAngles[0], 0.0F);
         }
      }
   }

   private class_239 point(class_243 start, class_243 end) {
      return mc.field_1687.method_17742(new class_3959(start, end, class_3960.field_17559, class_242.field_1348, mc.field_1724));
   }

   private class_2338 getNextCorner() {
      if (this.first != null && this.second != null) {
         int local1_0 = Math.min(this.first.method_10263(), this.second.method_10263());
         int local2_1 = Math.max(this.first.method_10263(), this.second.method_10263());
         int local3_2 = Math.min(this.first.method_10260(), this.second.method_10260());
         int local4_3 = Math.max(this.first.method_10260(), this.second.method_10260());
         class_2338[] local5_4 = new class_2338[]{
            new class_2338(local1_0, this.first.method_10264(), local3_2),
            new class_2338(local2_1, this.first.method_10264(), local3_2),
            new class_2338(local2_1, this.first.method_10264(), local4_3),
            new class_2338(local1_0, this.first.method_10264(), local4_3)
         };
         this.currentCorner = (this.currentCorner + 1) % 4;
         return local5_4[this.currentCorner];
      } else {
         return mc.field_1724.method_24515();
      }
   }

   private boolean inpos(class_2338 pos) {
      int local2_0 = Math.min(this.first.method_10263(), this.second.method_10263());
      int local3_1 = Math.max(this.first.method_10263(), this.second.method_10263());
      int local4_2 = Math.min(this.first.method_10264() - 2, this.second.method_10264() - 2);
      int local5_3 = Math.max(this.first.method_10264() + 2, this.second.method_10264() + 2);
      int local6_4 = Math.min(this.first.method_10260(), this.second.method_10260());
      int local7_5 = Math.max(this.first.method_10260(), this.second.method_10260());
      return pos.method_10263() >= local2_0
         && pos.method_10263() <= local3_1
         && pos.method_10264() >= local4_2
         && pos.method_10264() <= local5_3
         && pos.method_10260() >= local6_4
         && pos.method_10260() <= local7_5;
   }
}
