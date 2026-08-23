package ru.melonity.feature.features.modules.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_310;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderGameWorldEvent;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.minecraft.DeltaGCDProcessor;
import ru.melonity.util.modules.ProjectileHelperUtil;

@Environment(EnvType.CLIENT)
public class ProjectileHelperFeature extends SimpleFeature {
   public final NumberSetting aimRange = new NumberSetting("global.range", v -> String.format("%.1f", v.floatValue()), 2.8F, 200.0F, 50.0F);
   public final BooleanSetting predictMovement = new BooleanSetting("projectile.predict", true);
   private final NumberSetting strength = new NumberSetting("projectile.rot_speed", value -> String.format("%.2f", value.floatValue()), 0.1F, 8.0F, 5.3F);
   private final NumberSetting smoothness = new NumberSetting("projectile.smooth_value", value -> String.format("%.2f", value.floatValue()), 1.0F, 10.0F, 4.4F);
   private final NumberSetting verticalFactor = new NumberSetting(
      "projectile.vertical_factor", value -> String.format("%.2f", value.floatValue()), 0.1F, 1.0F, 0.6F
   );
   private class_1309 target = null;
   private ProjectileHelperUtil.ProjectileType currentType = null;
   private float rawDeltaYaw = 0.0F;
   private float rawDeltaPitch = 0.0F;
   private float smoothedDeltaYaw = 0.0F;
   private float smoothedDeltaPitch = 0.0F;
   public final EventListener<UpdateEvent> onUpdate = event -> {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         this.currentType = ProjectileHelperUtil.getCurrentProjectileType();
         if (this.currentType == null) {
            this.resetState();
         } else {
            this.target = ProjectileHelperUtil.findTarget(this.aimRange.getValue().floatValue());
            if (this.target == null) {
               this.rawDeltaYaw = this.rawDeltaPitch = 0.0F;
            } else {
               float[] local2_0 = ProjectileHelperUtil.calculateDelta(this.target, this.currentType, this.predictMovement.get());
               if (local2_0 == null) {
                  this.rawDeltaYaw = this.rawDeltaPitch = 0.0F;
               } else {
                  this.rawDeltaYaw = local2_0[0];
                  this.rawDeltaPitch = local2_0[1];
               }
            }
         }
      } else {
         this.resetState();
      }
   };
   public final EventListener<RenderGameWorldEvent> onRender = event -> {
      if (mc.field_1724 != null && this.target != null) {
         float local2_0 = class_310.method_1551().method_60646().method_60636();
         float local3_1 = this.strength.getValue().floatValue();
         float local4_2 = ProjectileHelperUtil.clamp(1.0F / this.smoothness.getValue().floatValue(), 0.05F, 1.0F);
         this.smoothedDeltaYaw = this.smoothedDeltaYaw + (this.rawDeltaYaw - this.smoothedDeltaYaw) * local4_2;
         this.smoothedDeltaPitch = this.smoothedDeltaPitch + (this.rawDeltaPitch - this.smoothedDeltaPitch) * local4_2;
         if (!(Math.abs(this.smoothedDeltaYaw) < 0.0018F) || !(Math.abs(this.smoothedDeltaPitch) < 0.0018F)) {
            float local5_3 = ProjectileHelperUtil.calculateAdaptiveSpeed(this.smoothedDeltaYaw, local3_1);
            float local6_4 = ProjectileHelperUtil.calculateAdaptiveSpeed(this.smoothedDeltaPitch, local3_1 * this.verticalFactor.getValue().floatValue());
            float local7_5 = this.smoothedDeltaYaw > 0.0F ? 1.0F : -1.0F;
            float local8_6 = this.smoothedDeltaPitch > 0.0F ? 1.0F : -1.0F;
            float local9_7 = ProjectileHelperUtil.clamp(local5_3 * local7_5 * local2_0, -Math.abs(this.smoothedDeltaYaw), Math.abs(this.smoothedDeltaYaw));
            float local10_8 = ProjectileHelperUtil.clamp(local6_4 * local8_6 * local2_0, -Math.abs(this.smoothedDeltaPitch), Math.abs(this.smoothedDeltaPitch));
            local9_7 = DeltaGCDProcessor.INSTANCE.getFixedRotation(local9_7);
            local10_8 = DeltaGCDProcessor.INSTANCE.getFixedRotation(local10_8);
            mc.field_1724.method_36456(mc.field_1724.method_36454() + local9_7);
            mc.field_1724.method_36457(ProjectileHelperUtil.clamp(mc.field_1724.method_36455() + local10_8, -90.0F, 90.0F));
            this.smoothedDeltaYaw -= local9_7;
            this.smoothedDeltaPitch -= local10_8;
         }
      } else {
         this.smoothedDeltaYaw = this.smoothedDeltaPitch = 0.0F;
      }
   };

   public ProjectileHelperFeature() {
      super("ProjectileHelper", MenuCategory.COMBAT);
      this.settings(new MenuFeatureView[]{this.aimRange, this.predictMovement, this.strength, this.smoothness, this.verticalFactor});
   }

   @Override
   public void onToggle(boolean enabled) {
      if (!enabled) {
         this.resetState();
      }
   }

   private void resetState() {
      this.target = null;
      this.currentType = null;
      this.rawDeltaYaw = this.rawDeltaPitch = 0.0F;
      this.smoothedDeltaYaw = this.smoothedDeltaPitch = 0.0F;
   }
}
