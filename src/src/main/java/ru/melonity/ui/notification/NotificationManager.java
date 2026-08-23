package ru.melonity.ui.notification;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.HudFeature;
import ru.melonity.feature.features.modules.render.hud.DynamicIslandView;
import ru.melonity.font.Fonts;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;

@Environment(EnvType.CLIENT)
public class NotificationManager {
   private final List<Notification> notifications = Lists.newLinkedList();
   private static final FrameWeightCalculator weightCalculator = FrameWeightCalculator.milliseconds(400L);
   public static boolean features = true;

   public void add(Notification notification) {
      if (notification != null) {
         if (Melonity.INSTANCE.getFeatureManager() != null) {
            HudFeature local2_1 = Melonity.INSTANCE.getFeatureManager().hudFeature;
            if (local2_1 != null && local2_1.isDynamicIslandEnabled()) {
               DynamicIslandView local3_0 = local2_1.getDynamicIslandView();
               if (local3_0 != null && local3_0.pushNotification(notification.getTitle(), notification.getDescription(), true)) {
                  return;
               }
            }
         }

         this.notifications.add(notification);
      }
   }

   public void render(RenderSystem renderSystem, class_4587 poseStack) {
      float local3_8 = (float)class_310.method_1551().method_22683().method_4507() / 2.0F;
      float local4_9 = (float)class_310.method_1551().method_22683().method_4480() / 2.0F;
      float local5_10 = local3_8 - 6.0F;
      float local6_11 = 32.0F;
      float local7_12 = 6.0F;
      ScaleUtils.rescale(2.0);
      MenuStyle local8_13 = Melonity.INSTANCE.styleManager.getStyle();
      float local9_14 = weightCalculator.elapsedUnits();

      try {
         if (this.notifications.size() > 5) {
            this.notifications.removeFirst();
         }

         for (Notification local11_7 : this.notifications) {
            // Both operands used to measure the description, making the Math.max below
            // a max of a value with itself - the title was never sized, so long titles
            // ran off the right edge of the panel.
            float local12_0 = Fonts.INTER_REGULAR_14.getStringWidth(local11_7.getTitle());
            float local13_1 = Fonts.INTER_REGULAR_14.getStringWidth(local11_7.getDescription());
            float local14_2 = Math.max(160.0F, Math.max(local13_1, local12_0) + 60.0F);
            float local15_3 = (float)(System.currentTimeMillis() - local11_7.getTime()) / 1000.0F;
            boolean local16_4 = local15_3 > 5.0F;
            local11_7.getAnimation().state(!local16_4);
            local11_7.getAnimation().update(local9_14);
            if (!(local11_7.getAnimation().animation() < 0.08F)) {
               local5_10 -= (local6_11 + local7_12) * local11_7.getAnimation().animation();
               float local17_5 = local4_9 - local14_2 - 6.0F;
               poseStack.method_22903();
               poseStack.method_46416(local17_5 + local14_2 / 2.0F, local5_10 + local6_11 / 2.0F, 0.0F);
               poseStack.method_46416(-local17_5 - local14_2 / 2.0F, -local5_10 - local6_11 / 2.0F, 0.0F);
               poseStack.method_46416(local14_2, 0.0F, 0.0F);
               poseStack.method_46416(-(local11_7.getAnimation().animation() * local14_2), 0.0F, 0.0F);
               float local18_6 = local14_2 - local15_3 / 5.3F * local14_2;
               renderSystem.addRect(local17_5, local5_10, local14_2, local6_11, 5.0F, local8_13.background(), poseStack);
               renderSystem.addRect(local17_5, local5_10 + local6_11 - 2.5F, local14_2, 2.5F, 3.0F, local8_13.background().brighter().brighter(), poseStack);
               renderSystem.addRect(local17_5, local5_10 + local6_11 - 2.5F, local18_6, 2.5F, 3.0F, local8_13.ascent(), poseStack);
               renderSystem.addTexture(
                  class_2960.method_60656("melonity/images/ui/notifications/".concat(local11_7.getIcon())),
                  local17_5 + 6.0F,
                  local5_10 + 8.5F,
                  12.0F,
                  12.0F,
                  poseStack
               );
               renderSystem.addText(Fonts.INTER_REGULAR_14, local11_7.getTitle(), local17_5 + 24.0F, local5_10 + 9.0F, poseStack);
               renderSystem.addText(Fonts.INTER_REGULAR_14, local11_7.getDescription(), local17_5 + 24.0F, local5_10 + 17.0F, Color.GRAY, poseStack);
               poseStack.method_22909();
            }
         }
      } catch (Throwable var19) {
      }

      ScaleUtils.rescaleMC();
   }
}
