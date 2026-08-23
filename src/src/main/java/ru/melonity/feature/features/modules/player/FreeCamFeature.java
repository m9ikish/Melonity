package ru.melonity.feature.features.modules.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_243;
import net.minecraft.class_5498;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.minecraft.MovementUtil;

@Environment(EnvType.CLIENT)
public class FreeCamFeature extends SimpleFeature {
   public class_243 oldRenderPos;
   public class_243 prevRenderPos;
   public class_243 renderPos;
   private class_5498 oldPerspective;
   private final NumberSetting speed = new NumberSetting("global.speed", value -> String.format("%.2f", value.floatValue()), 0.1F, 5.0F, 0.6F);
   private final NumberSetting motionY = new NumberSetting("dragonfly.speed_y", value -> String.format("%.2f", value.floatValue()), 0.05F, 1.0F, 0.4F);
   public float yaw = 0.0F;
   public float pitch = 0.0F;
   private final EventListener<EventInput> onInput = event -> {
      if (mc.field_1690 != null && mc.field_1690.method_31044() != null) {
         mc.field_1690.method_31043(class_5498.field_26665);
         if (mc.field_1724 != null) {
            double[] local2_0 = MovementUtil.forward((double)this.speed.getValue().floatValue());
            this.prevRenderPos = new class_243(this.renderPos.field_1352, this.renderPos.field_1351, this.renderPos.field_1350);
            this.renderPos = this.renderPos.method_1031(local2_0[0], 0.0, local2_0[1]);
            if (mc.field_1690.field_1903.method_1434()) {
               this.renderPos = this.renderPos.method_1031(0.0, (double)this.motionY.getValue().floatValue(), 0.0);
            }

            if (mc.field_1690.field_1832.method_1434()) {
               this.renderPos = this.renderPos.method_1031(0.0, (double)(-this.motionY.getValue().floatValue()), 0.0);
            }
         }

         RotationManager.INSTANCE.rotate(this.yaw, this.pitch);
         event.setForward(0.0F);
         event.setStrafe(0.0F);
         event.setJump(false);
         event.setSneaking(false);
      }
   };

   public FreeCamFeature() {
      super("FreeCam", MenuCategory.PLAYER);
      this.setting(this.speed);
      this.setting(this.motionY);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      this.oldRenderPos = this.prevRenderPos = this.renderPos = mc.field_1724.method_19538();
      if (this.isEnabled()) {
         this.yaw = RotationManager.INSTANCE.getYaw();
         this.pitch = mc.field_1724.method_36455();
      } else {
         RotationManager.INSTANCE.reset();
      }

      if (value && mc.field_1690 != null && mc.field_1690.method_31044() != null) {
         this.oldPerspective = mc.field_1690.method_31044();
      } else if (!value && this.oldPerspective != null) {
         mc.field_1690.method_31043(this.oldPerspective);
      }
   }
}
