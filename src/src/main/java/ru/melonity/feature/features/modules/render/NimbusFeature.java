package ru.melonity.feature.features.modules.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_572;
import net.minecraft.class_757;
import net.minecraft.class_7833;
import net.minecraft.class_293.class_5596;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.MenuColorSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderLivingEntityModelEvent;

@Environment(EnvType.CLIENT)
public class NimbusFeature extends SimpleFeature {
   private final MenuColorSetting colorSetting = new MenuColorSetting("global.color");
   private final BooleanSetting followHead = new BooleanSetting("nimbus.follow_head", false);
   private final EventListener<RenderLivingEntityModelEvent> onRenderModel = event -> {
      if (mc.field_1773.method_19418().method_19333()) {
         if (event.model() instanceof class_572 local2_1 && event.entity() instanceof class_1657 && event.entity() == mc.field_1724) {
            class_4587 local3_3 = event.matrixStack();
            local3_3.method_22903();
            if (this.followHead.get()) {
               local2_1.field_3398.method_22703(local3_3);
            }

            local3_3.method_46416(0.0F, -0.63F, 0.0F);
            local3_3.method_22907(class_7833.field_40717.rotationDegrees(180.0F));
            local3_3.method_22907(class_7833.field_40716.rotationDegrees(90.0F));
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            RenderSystem.disableCull();
            RenderSystem.setShader(class_757::method_34540);
            this.render(local3_3.method_23760().method_23761(), 0.55F, 0.02F, 1.0F);
            this.render(local3_3.method_23760().method_23761(), 0.55F, 0.03F, 0.2F);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            local3_3.method_22909();
            return;
         }
      }
   };

   public NimbusFeature() {
      super("Nimbus", MenuCategory.RENDER);
      this.settingClear();
      this.settings(new MenuFeatureView[]{this.colorSetting, this.followHead});
   }

   private void render(Matrix4f matrix, float radius, float width, float alpha) {
      class_287 local5_24 = class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1576);

      for (int local6_23 = 0; local6_23 < 360; local6_23++) {
         float local7_0 = (float)((Math.PI * 2) * (double)local6_23 / 360.0);
         float local8_1 = (float)((Math.PI * 2) * (double)(local6_23 + 1) / 360.0);
         Vector3f local9_2 = new Vector3f(class_3532.method_15374(local7_0) * radius, 0.0F, class_3532.method_15362(local7_0) * radius);
         Vector3f local10_3 = new Vector3f(class_3532.method_15374(local8_1) * radius, 0.0F, class_3532.method_15362(local8_1) * radius);
         float local11_4 = width * 0.5F;
         Color local12_5 = this.getAnimatedColor(local6_23);
         Color local13_6 = this.getAnimatedColor(local6_23 + 1);
         int local14_7 = local12_5.getRed();
         int local15_8 = local12_5.getGreen();
         int local16_9 = local12_5.getBlue();
         int local17_10 = (int)(255.0F * alpha);
         int local18_11 = local13_6.getRed();
         int local19_12 = local13_6.getGreen();
         int local20_13 = local13_6.getBlue();
         int local21_14 = (int)(255.0F * alpha);
         Vector3f local22_15 = new Vector3f(local9_2.x - local11_4, local9_2.y + local11_4, local9_2.z);
         Vector3f local23_16 = new Vector3f(local9_2.x + local11_4, local9_2.y - local11_4, local9_2.z);
         Vector3f local24_17 = new Vector3f(local10_3.x + local11_4, local10_3.y - local11_4, local10_3.z);
         Vector3f local25_18 = new Vector3f(local10_3.x - local11_4, local10_3.y + local11_4, local10_3.z);
         local5_24.method_22918(matrix, local22_15.x, local22_15.y, local22_15.z).method_1336(local14_7, local15_8, local16_9, local17_10);
         local5_24.method_22918(matrix, local23_16.x, local23_16.y, local23_16.z).method_1336(local14_7, local15_8, local16_9, local17_10);
         local5_24.method_22918(matrix, local24_17.x, local24_17.y, local24_17.z).method_1336(local18_11, local19_12, local20_13, local21_14);
         local5_24.method_22918(matrix, local25_18.x, local25_18.y, local25_18.z).method_1336(local18_11, local19_12, local20_13, local21_14);
         Vector3f local26_19 = new Vector3f(local9_2.x, local9_2.y + local11_4, local9_2.z - local11_4);
         Vector3f local27_20 = new Vector3f(local9_2.x, local9_2.y - local11_4, local9_2.z + local11_4);
         Vector3f local28_21 = new Vector3f(local10_3.x, local10_3.y - local11_4, local10_3.z + local11_4);
         Vector3f local29_22 = new Vector3f(local10_3.x, local10_3.y + local11_4, local10_3.z - local11_4);
         local5_24.method_22918(matrix, local26_19.x, local26_19.y, local26_19.z).method_1336(local14_7, local15_8, local16_9, local17_10);
         local5_24.method_22918(matrix, local27_20.x, local27_20.y, local27_20.z).method_1336(local14_7, local15_8, local16_9, local17_10);
         local5_24.method_22918(matrix, local28_21.x, local28_21.y, local28_21.z).method_1336(local18_11, local19_12, local20_13, local21_14);
         local5_24.method_22918(matrix, local29_22.x, local29_22.y, local29_22.z).method_1336(local18_11, local19_12, local20_13, local21_14);
      }

      class_286.method_43433(local5_24.method_60800());
   }

   private Color getAnimatedColor(int step) {
      double local2_0 = (double)System.currentTimeMillis() / 1000.0;
      double local4_1 = local2_0 + 4.0 * ((double)step / 360.0);
      return this.interpolateColors(Color.WHITE, this.colorSetting.getColor(), local4_1);
   }

   private Color interpolateColors(Color color1, Color color2, double speed) {
      double local5_0 = speed / 4.0 % 1.0;
      float local7_1 = class_3532.method_15363((float)(Math.sin((Math.PI * 6) * local5_0) / 2.0 + 0.2), 0.0F, 1.0F);
      float local8_2 = class_3532.method_16439(local7_1, (float)color1.getRed() / 255.0F, (float)color2.getRed() / 255.0F);
      float local9_3 = class_3532.method_16439(local7_1, (float)color1.getGreen() / 255.0F, (float)color2.getGreen() / 255.0F);
      float local10_4 = class_3532.method_16439(local7_1, (float)color1.getBlue() / 255.0F, (float)color2.getBlue() / 255.0F);
      return new Color(local8_2, local9_3, local10_4);
   }

   @Generated
   public MenuColorSetting getColorSetting() {
      return this.colorSetting;
   }

   @Generated
   public BooleanSetting getFollowHead() {
      return this.followHead;
   }

   @Generated
   public EventListener<RenderLivingEntityModelEvent> getOnRenderModel() {
      return this.onRenderModel;
   }
}
