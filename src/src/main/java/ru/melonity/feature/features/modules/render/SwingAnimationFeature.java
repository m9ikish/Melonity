package ru.melonity.feature.features.modules.render;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import org.joml.Quaternionf;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;

@Environment(EnvType.CLIENT)
public class SwingAnimationFeature extends SimpleFeature {
   private final ListSetting modeSetting = new ListSetting("global.mode", ListSetting.ModeType.SINGLE, "Default", "Default", "Down", "Fap");
   private final NumberSetting powerSetting = new NumberSetting("swinganimation.power", value -> String.format("%d", value.intValue()), 1, 10, 5);
   private final BooleanSetting onlyOnSword = new BooleanSetting("swinganimation.sword", true);

   public SwingAnimationFeature() {
      super("SwingAnimation", MenuCategory.RENDER);
      this.setting(this.modeSetting);
      this.setting(this.powerSetting);
      this.setting(this.onlyOnSword);
   }

   public void applyAnimation(class_4587 poseStack, float swingProgress, int i) {
      class_1268 local4_10 = i == 1 ? class_1268.field_5808 : class_1268.field_5810;
      String local5_11 = this.modeSetting.getMode().getFirst();
      float local6_12 = class_3532.method_15374(swingProgress * swingProgress * (float) Math.PI);
      float local7_13 = class_3532.method_15374(class_3532.method_15355(swingProgress) * (float) Math.PI);
      float local8_14 = 0.2F * class_3532.method_15374(class_3532.method_15355(swingProgress) * (float) (Math.PI * 2));
      float local9_15 = (float)Math.sin((double)swingProgress * (Math.PI / 2) * 2.0);
      float local10_16 = (float)(this.powerSetting.getValue().intValue() * 10);
      float local11_17 = (float)((11 - this.powerSetting.getValue().intValue()) * 10);
      float local12_18 = class_3532.method_15374(swingProgress * (float) Math.PI);
      switch (local5_11) {
         case "Default":
            poseStack.method_22907(class_7833.field_40716.rotationDegrees((float)i * (45.0F + local6_12 * (-local10_16 / 4.0F))));
            float local15_0 = class_3532.method_15374(class_3532.method_15355(swingProgress) * (float) Math.PI);
            poseStack.method_22907(class_7833.field_40718.rotationDegrees((float)i * local15_0 * -(local10_16 / 4.0F)));
            poseStack.method_22907(class_7833.field_40714.rotationDegrees(local15_0 * -local10_16));
            poseStack.method_22907(class_7833.field_40716.rotationDegrees((float)i * -45.0F));
            break;
         case "Down":
            poseStack.method_46416(0.4F, 0.0F, -0.5F);
            poseStack.method_22907(class_7833.field_40716.rotationDegrees(90.0F));
            poseStack.method_22907(class_7833.field_40718.rotationDegrees(-30.0F));
            poseStack.method_22907(class_7833.field_40714.rotationDegrees(-90.0F - local10_16 * local9_15));
            break;
         case "Fap":
            float local15_1 = -0.4F * local7_13;
            float local16_2 = 0.2F * local8_14;
            float local17_3 = -0.2F * local12_18;
            poseStack.method_46416(0.96F, -0.02F, -0.72F);
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians(45.0), 0.0F, 1.0F, 0.0F));
            float local18_4 = class_3532.method_15374(0.0F) * -20.0F;
            float local19_5 = class_3532.method_15374(class_3532.method_15355(0.0F) * (float) Math.PI) * -20.0F;
            float local20_6 = class_3532.method_15374(class_3532.method_15355(0.0F) * (float) Math.PI) * -80.0F;
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians((double)local18_4), 0.0F, 1.0F, 0.0F));
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians((double)local19_5), 0.0F, 0.0F, 1.0F));
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians((double)local20_6), 1.0F, 0.0F, 0.0F));
            poseStack.method_46416(-0.5F, 0.2F, 0.0F);
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians(30.0), 0.0F, 1.0F, 0.0F));
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians(-80.0), 1.0F, 0.0F, 0.0F));
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians(60.0), 0.0F, 1.0F, 0.0F));
            int local21_7 = (int)Math.min(
               255L, (System.currentTimeMillis() % 255L > 127L ? Math.abs(System.currentTimeMillis() % 255L - 255L) : System.currentTimeMillis() % 255L) * 2L
            );
            float local22_8 = local16_2 > 0.5F ? 1.0F - local16_2 : local16_2;
            poseStack.method_46416(0.4F, 0.0F, 0.7F);
            poseStack.method_46416(0.0F, 0.5F, 0.0F);
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians(90.0), 1.0F, 0.0F, -1.0F));
            poseStack.method_46416(0.6F, 0.5F, 0.0F);
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians(-90.0), 1.0F, 0.0F, -1.0F));
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians(-10.0), 1.0F, 0.0F, -1.0F));
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians((double)(-local22_8 * 10.0F)), 10.0F, 10.0F, -9.0F));
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians(10.0), -1.0F, 0.0F, 0.0F));
            poseStack.method_22904(0.0, 0.0, -0.5);
            float local23_9 = mc.field_1724.field_6252 ? (float)(-local21_7) / local11_17 : 1.0F;
            poseStack.method_22907(new Quaternionf().rotateAxis((float)Math.toRadians((double)local23_9), 1.0F, 0.0F, 1.0F));
            poseStack.method_22904(0.0, -0.1, 0.5);
            poseStack.method_22905(1.3F, 1.3F, 1.3F);
      }
   }

   @Generated
   public ListSetting getModeSetting() {
      return this.modeSetting;
   }

   @Generated
   public NumberSetting getPowerSetting() {
      return this.powerSetting;
   }

   @Generated
   public BooleanSetting getOnlyOnSword() {
      return this.onlyOnSword;
   }
}
