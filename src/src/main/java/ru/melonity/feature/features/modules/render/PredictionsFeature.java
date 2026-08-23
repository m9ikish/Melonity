package ru.melonity.feature.features.modules.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1667;
import net.minecraft.class_1674;
import net.minecraft.class_1676;
import net.minecraft.class_1681;
import net.minecraft.class_1683;
import net.minecraft.class_1684;
import net.minecraft.class_1686;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1844;
import net.minecraft.class_1937;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_4081;
import net.minecraft.class_4587;
import net.minecraft.class_757;
import net.minecraft.class_8956;
import net.minecraft.class_9334;
import net.minecraft.class_239.class_240;
import net.minecraft.class_293.class_5596;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.lwjgl.opengl.GL11;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.ILevelRenderer;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PostRenderWorldEvent;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.render.ProjectUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class PredictionsFeature extends SimpleFeature {
   private static final int STEPS = 300;
   private final ListSetting projectilesSetting = new ListSetting(
      "Projectiles",
      ListSetting.ModeType.MULTI,
      "predictions.enderpearl",
      "predictions.enderpearl",
      "predictions.egg",
      "predictions.expbottle",
      "predictions.arrow",
      "Fireball",
      "predictions.wind_charge",
      "predictions.potion"
   );
   private final BooleanSetting enderPearlTail = new BooleanSetting("predictions.eptail", true)
      .setVisible(() -> this.projectilesSetting.contains("predictions.enderpearl"));
   private final EventListener<RenderOverlayEvent> onRenderOverlay = event -> {
      class_4587 local2_14 = event.matrixStack();
      RenderSystem local3_15 = Melonity.INSTANCE.getRenderSystem();

      for (class_1297 local5_13 : mc.field_1687.method_18112()) {
         if (this.projectilesSetting.contains("predictions.enderpearl") && this.enderPearlTail.get() && local5_13 instanceof class_1684) {
            class_1684 local6_12 = (class_1684)local5_13;
            class_243 local7_9 = local6_12.method_18798();
            class_243 local8_10 = local6_12.method_19538();
            double local10_11 = 0.0;

            for (int local12_8 = 0; local12_8 < 300; local12_8++) {
               class_243 local9_7 = local8_10;
               local8_10 = local8_10.method_1019(local7_9);
               local7_9 = this.updateMotion(local7_9, 0.99, 0.03);
               local10_11 += 0.05;
               class_3959 local13_5 = new class_3959(local9_7, local8_10, class_3960.field_17558, class_242.field_1348, local6_12);
               class_3965 local14_6 = mc.field_1687.method_17742(local13_5);
               if (local14_6.method_17783() == class_240.field_1332) {
                  local8_10 = local14_6.method_17784();
                  double[] local15_4 = ProjectUtil.project(local8_10.field_1352, local8_10.field_1351, local8_10.field_1350);
                  if (local15_4 != null
                     && ((ILevelRenderer)mc.field_1769)
                        .getFrustum()
                        .method_23093(new class_238(local14_6.method_17784(), local14_6.method_17784().method_1031(1.0, 1.0, 1.0)))) {
                     String local16_0 = String.format("%.2f сек", local10_11).replace(',', '.');
                     float local17_1 = 12.0F + Fonts.INTER_MEDIUM_12.getStringWidth(local16_0) + 2.0F;
                     int local18_2 = (int)Math.round(local15_4[0] - (double)(local17_1 / 2.0F));
                     int local19_3 = (int)Math.round(local15_4[1]);
                     local3_15.addRect(
                        (float)local18_2, (float)local19_3, local17_1, 12.0F, 4.0F, Melonity.INSTANCE.styleManager.getStyle().background(), local2_14
                     );
                     local2_14.method_22903();
                     local2_14.method_46416(0.0F, 0.0F, 0.0F);
                     local2_14.method_22905(0.5F, 0.5F, 0.5F);
                     local2_14.method_46416((float)local18_2, (float)local19_3, 0.0F);
                     event.graphics().method_51427(new class_1799(class_1802.field_8634), local18_2 + 4, local19_3 + 4);
                     local2_14.method_22909();
                     local3_15.addText(Fonts.INTER_MEDIUM_12, local16_0, (float)(local18_2 + 12), (float)(local19_3 + 5), local2_14);
                  }
                  break;
               }

               if (local8_10.field_1351 < (double)mc.field_1687.method_31607()) {
                  break;
               }
            }
         }
      }
   };
   private final EventListener<PostRenderWorldEvent> onRenderGameWorld = event -> {
      class_4587 local2_17 = event.matrixStack();
      local2_17.method_22903();
      com.mojang.blaze3d.systems.RenderSystem.enableBlend();
      com.mojang.blaze3d.systems.RenderSystem.defaultBlendFunc();
      com.mojang.blaze3d.systems.RenderSystem.disableCull();
      GL11.glEnable(2848);
      class_243 local3_18 = mc.field_1773.method_19418().method_19326();
      local2_17.method_22904(-local3_18.field_1352, -local3_18.field_1351, -local3_18.field_1350);
      com.mojang.blaze3d.systems.RenderSystem.lineWidth(3.0F);
      com.mojang.blaze3d.systems.RenderSystem.setShader(class_757::method_34535);
      class_289 local4_19 = class_289.method_1348();
      class_287 local5_20 = local4_19.method_60827(class_5596.field_27377, class_290.field_1576);
      boolean local6_21 = false;
      int local7_22 = Melonity.INSTANCE.styleManager.getStyle().ascent().getRGB();

      for (class_1297 local9_16 : mc.field_1687.method_18112()) {
         if (local9_16 instanceof class_1676) {
            class_1676 local10_15 = (class_1676)local9_16;
            class_243 local11_11 = local10_15.method_18798();
            class_243 local12_12 = local10_15.method_19538();
            double local14_13 = 0.99;
            double local16_14 = 0.03;
            if (local10_15 instanceof class_1686) {
               local16_14 = 0.05;
            }

            for (int local18_10 = 0; local18_10 < 300; local18_10++) {
               class_243 local13_9 = local12_12;
               local12_12 = local12_12.method_1019(local11_11);
               local11_11 = this.updateMotion(local11_11, local14_13, local16_14);
               if (this.isValid(local10_15)) {
                  local5_20.method_22918(
                        local2_17.method_23760().method_23761(), (float)local13_9.field_1352, (float)local13_9.field_1351, (float)local13_9.field_1350
                     )
                     .method_39415(local7_22);
                  local5_20.method_22918(
                        local2_17.method_23760().method_23761(), (float)local12_12.field_1352, (float)local12_12.field_1351, (float)local12_12.field_1350
                     )
                     .method_39415(local7_22);
                  local6_21 = true;
               }

               class_3959 local19_5 = new class_3959(local13_9, local12_12, class_3960.field_17558, class_242.field_1348, local10_15);
               class_3965 local20_6 = mc.field_1687.method_17742(local19_5);
               boolean local21_7 = local20_6.method_17783() == class_240.field_1332;
               if (local21_7) {
                  local12_12 = local20_6.method_17784();
               }

               double var10001 = (double)((float)local12_12.field_1352);
               double var10002 = (double)((float)local12_12.field_1351);
               boolean local22_8 = !(
                  class_3532.method_15355((float)class_310.method_1551().field_1724.method_5649(var10001, var10002, (double)((float)local12_12.field_1350)))
                     > 1.3F
               );
               if (local10_15.method_24921() == mc.field_1724) {
                  local22_8 = false;
               }

               if (local10_15 instanceof class_1686 local23_4) {
                  class_1799 local24_1 = local23_4.method_7495();
                  class_1844 local25_2 = (class_1844)local24_1.method_57825(class_9334.field_49651, class_1844.field_49274);
                  boolean local26_3 = false;

                  for (class_1293 local28_0 : local25_2.method_57397()) {
                     if (((class_1291)local28_0.method_5579().comp_349()).method_18792() == class_4081.field_18272) {
                        local26_3 = true;
                     }
                  }

                  if (!local26_3) {
                     local22_8 = false;
                  }
               }

               if (local22_8) {
                  this.tryDodge();
               }

               if (local21_7 || local12_12.field_1351 < (double)mc.field_1687.method_31607()) {
                  break;
               }
            }
         }
      }

      if (local6_21) {
         class_286.method_43433(local5_20.method_60800());
      }

      GL11.glDisable(2848);
      com.mojang.blaze3d.systems.RenderSystem.lineWidth(1.0F);
      com.mojang.blaze3d.systems.RenderSystem.disableBlend();
      local2_17.method_22909();
   };

   public PredictionsFeature() {
      super("Predictions", MenuCategory.RENDER);
      this.settingClear();
      this.settings(new MenuFeatureView[]{this.projectilesSetting, this.enderPearlTail});
   }

   private boolean isValid(class_1676 projectile) {
      if (this.projectilesSetting.contains("predictions.enderpearl") && projectile instanceof class_1684) {
         return true;
      } else if (this.projectilesSetting.contains("predictions.egg") && projectile instanceof class_1681) {
         return true;
      } else if (this.projectilesSetting.contains("predictions.expbottle") && projectile instanceof class_1683) {
         return true;
      } else if (this.projectilesSetting.contains("predictions.arrow") && projectile instanceof class_1667) {
         return true;
      } else if (this.projectilesSetting.contains("Fireball") && projectile instanceof class_1674) {
         return true;
      } else {
         return this.projectilesSetting.contains("predictions.wind_charge") && projectile instanceof class_8956
            ? true
            : this.projectilesSetting.contains("predictions.potion") && projectile instanceof class_1686;
      }
   }

   private class_243 updateMotion(class_243 motion, double drag, double gravity) {
      return motion.method_1021(drag).method_1023(0.0, gravity, 0.0);
   }

   public void tryDodge() {
      class_310 local1_6 = class_310.method_1551();
      class_1657 local2_7 = local1_6.field_1724;
      class_1937 local3_8 = local2_7.method_37908();
      class_243 local4_9 = local2_7.method_19538();
      class_243[] local5_10 = new class_243[]{
         new class_243(1.0, 0.0, 0.0), new class_243(-1.0, 0.0, 0.0), new class_243(0.0, 0.0, 1.0), new class_243(0.0, 0.0, -1.0)
      };
      double local6_11 = -1.0;
      int local8_12 = -1;

      for (int local9_5 = 0; local9_5 < local5_10.length; local9_5++) {
         class_243 local10_1 = local5_10[local9_5];
         class_243 local11_2 = local4_9.method_1019(local10_1);
         class_3959 local12_3 = new class_3959(local4_9, local11_2, class_3960.field_17558, class_242.field_1347, local2_7);
         class_3965 local13_4 = local3_8.method_17742(local12_3);
         if (local13_4.method_17783() == class_240.field_1333) {
            double local14_0 = local4_9.method_1022(local11_2);
            if (local6_11 < 0.0 || local14_0 > local6_11) {
               local6_11 = local14_0;
               local8_12 = local9_5;
            }
         }
      }

      if (local8_12 != -1) {
         switch (local8_12) {
            case 0:
               local1_6.field_1690.field_1849.method_23481(true);
               break;
            case 1:
               local1_6.field_1690.field_1913.method_23481(true);
               break;
            case 2:
               local1_6.field_1690.field_1894.method_23481(true);
               break;
            case 3:
               local1_6.field_1690.field_1881.method_23481(true);
         }
      }
   }
}
