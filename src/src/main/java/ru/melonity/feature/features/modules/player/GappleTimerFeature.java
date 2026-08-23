package ru.melonity.feature.features.modules.player;

import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import net.minecraft.class_408;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.drag.Drag;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.animation.Easings;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PreMotionEvent;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class GappleTimerFeature extends SimpleFeature {
   private final StateAnimation animation = new StateAnimation();
   private final FrameWeightCalculator frameWeightCalculator = FrameWeightCalculator.milliseconds(400L);
   private final NumberSetting timeSetting = new NumberSetting("global.time", value -> String.format("%ds", value.intValue()), 1, 6, 4.5);
   private final Drag drag = new Drag(16, 200.0F, 80.0F, 34.5F, 73.0F, () -> true);
   private final EventListener<PreMotionEvent> onUpdate = event -> {
      if (mc.field_1724.method_7357().method_7904(class_1802.field_8463) && mc.field_1724.method_6030().method_7909() == class_1802.field_8463
         || mc.field_1724.method_7357().method_7904(class_1802.field_8634) && mc.field_1724.method_6030().method_7909() == class_1802.field_8634) {
         mc.field_1724.method_6075();
      }
   };
   private final EventListener<RenderOverlayEvent> onRenderOverlay = event -> {
      boolean local2_4 = !(mc.field_1724.method_7357().method_7905(class_1802.field_8463, mc.method_60646().method_60637(true)) <= 0.0F)
         || !(mc.field_1724.method_7357().method_7905(class_1802.field_8367, mc.method_60646().method_60637(true)) <= 0.0F)
         || !(mc.field_1724.method_7357().method_7905(class_1802.field_8634, mc.method_60646().method_60637(true)) <= 0.0F);
      this.animation.state(local2_4 || class_310.method_1551().field_1755 instanceof class_408);
      this.animation.update(this.frameWeightCalculator.elapsedUnits());
      float local3_5 = this.animation.animation();
      if (!(local3_5 <= 0.0F)) {
         local3_5 = (float)Easings.BACK_OUT.ease((double)local3_5);
         class_4587 local4_6 = event.matrixStack();
         local4_6.method_22903();
         local4_6.method_46416(this.drag.getX() + this.drag.getWidth() / 2.0F, this.drag.getY() + this.drag.getHeight() / 2.0F, 0.0F);
         local4_6.method_22905(local3_5, local3_5, local3_5);
         local4_6.method_46416(-(this.drag.getX() + this.drag.getWidth() / 2.0F), -(this.drag.getY() + this.drag.getHeight() / 2.0F), 0.0F);
         RenderSystem local5_7 = Melonity.INSTANCE.getRenderSystem();
         local5_7.addRect(
            this.drag.getX() - 1.0F,
            this.drag.getY() - 1.0F,
            this.drag.getWidth() + 2.0F,
            this.drag.getHeight() + 2.0F,
            6.0F,
            Color.decode("#222222"),
            local4_6
         );
         local5_7.addRect(this.drag.getX(), this.drag.getY(), this.drag.getWidth(), this.drag.getHeight(), 6.0F, Color.decode("#141414"), local4_6);

         for (int local6_3 = 0; local6_3 < 2; local6_3++) {
            class_1792 local7_0 = local6_3 == 0 ? class_1802.field_8634 : class_1802.field_8463;
            class_1799 local8_1 = new class_1799(local7_0);
            float local9_2 = mc.field_1724.method_7357().method_7905(local7_0, mc.method_60646().method_60637(true));
            if (local9_2 <= 0.0F && local7_0 == class_1802.field_8463) {
               local9_2 = mc.field_1724.method_7357().method_7905(class_1802.field_8367, mc.method_60646().method_60637(true));
            }

            local4_6.method_22903();
            local4_6.method_46416(this.drag.getX(), this.drag.getY() + (float)(local6_3 * 35), 0.0F);
            local4_6.method_22905(1.3F, 1.3F, 1.3F);
            local4_6.method_46416(-this.drag.getX(), -this.drag.getY() - (float)(local6_3 * 35), 0.0F);
            event.graphics()
               .method_51427(
                  local8_1,
                  (int)(this.drag.getX() + (local7_0 == class_1802.field_8634 ? 5.0F : 6.0F) - (local6_3 == 1 ? 0.5F : 0.0F)),
                  (int)(this.drag.getY() + (float)(local6_3 * 35) + 5.5F)
               );
            local4_6.method_22909();
            local5_7.addCircle(
               this.drag.getX() + 17.0F, this.drag.getY() + 18.0F + (float)(local6_3 * 35), 0.0F, 360.0F, 13.0F, 4.0F, false, Color.decode("#222222"), local4_6
            );
            local5_7.addCircle(
               this.drag.getX() + 17.0F,
               this.drag.getY() + 18.0F + (float)(local6_3 * 35),
               0.0F,
               local9_2 * 360.0F,
               14.0F,
               2.5F,
               false,
               Melonity.INSTANCE.styleManager.getStyle().ascent(),
               local4_6
            );
         }

         local4_6.method_22909();
      }
   };

   public GappleTimerFeature() {
      super("GappleTimer", MenuCategory.PLAYER);
      this.setting(this.timeSetting);
   }

   @Generated
   public StateAnimation getAnimation() {
      return this.animation;
   }

   @Generated
   public FrameWeightCalculator getFrameWeightCalculator() {
      return this.frameWeightCalculator;
   }

   @Generated
   public NumberSetting getTimeSetting() {
      return this.timeSetting;
   }

   @Generated
   public Drag getDrag() {
      return this.drag;
   }

   @Generated
   public EventListener<PreMotionEvent> getOnUpdate() {
      return this.onUpdate;
   }

   @Generated
   public EventListener<RenderOverlayEvent> getOnRenderOverlay() {
      return this.onRenderOverlay;
   }
}
