package ru.melonity.feature.features.modules.render;

import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_276;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.ILevelRenderer;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.render.shader.ShaderProgram;

@Environment(EnvType.CLIENT)
public class ShaderEspFeature extends SimpleFeature {
   private final ListSetting modeSetting = new ListSetting("global.mode", ListSetting.ModeType.SINGLE, "Galaxy 1", "Galaxy 1", "Galaxy 2", "Galaxy 3");
   private static final long time = System.currentTimeMillis();
   public class_276 glowFramebuffer;

   public ShaderEspFeature() {
      super("ShaderESP", MenuCategory.RENDER);
      this.setting(this.modeSetting);
   }

   public void renderShaderESP(class_4587 poseStack) {
      MenuStyle local2_5 = Melonity.INSTANCE.styleManager.getStyle();
      class_310 local3_6 = class_310.method_1551();
      if (this.isEnabled() && this.glowFramebuffer != null) {
         String local4_0 = null;
         String local5_1 = this.modeSetting.getMode().getFirst();
         switch (local5_1) {
            case "Galaxy 1":
               local4_0 = "galaxy1";
               break;
            case "Galaxy 2":
               local4_0 = "galaxy2";
               break;
            case "Galaxy 3":
               local4_0 = "galaxy3";
         }

         if (local4_0 == null) {
            return;
         }

         Color local5_1a = local2_5.ascent();
         Color local6_2 = local2_5.ascent();
         class_276 local7_3 = ((ILevelRenderer)local3_6.field_1769).entitiesFramebuffer();
         if (local7_3 == null) {
            return;
         }

         local7_3.method_35610();
         ShaderProgram local8_4 = Melonity.INSTANCE.getShaderManager().getShader(local4_0).get();
         local8_4.use();
         local8_4.setUniform2f("resolution", (float)local3_6.method_22683().method_4480(), (float)local3_6.method_22683().method_4507());
         local8_4.setUniform1i("tex", 0);
         local8_4.setUniform1f("time", (float)(System.currentTimeMillis() - time) / 1000.0F);
         local8_4.setUniform2f("texelSize", 1.0F / (float)local3_6.method_22683().method_4480(), 1.0F / (float)local3_6.method_22683().method_4507());
         local8_4.setUniform2f("direction", 1.0F, 1.0F);
         local8_4.setUniform4f(
            "color", (float)local5_1a.getRed() / 255.0F, (float)local5_1a.getGreen() / 255.0F, (float)local5_1a.getBlue() / 255.0F, 1.0F
         );
         local8_4.setUniform4f("outlineColor", (float)local6_2.getRed() / 255.0F, (float)local6_2.getGreen() / 255.0F, (float)local6_2.getBlue() / 255.0F, 1.0F);
         local8_4.setUniform1f("exposure", 1.5F);
         local8_4.setUniform1i("avoidTexture", 0);
         Melonity.INSTANCE
            .getRenderSystem()
            .addTexture(
               local7_3.method_30277(),
               0.0F,
               0.0F,
               (float)local3_6.method_22683().method_4486(),
               (float)local3_6.method_22683().method_4502(),
               Color.WHITE,
               poseStack
            );
         local8_4.deuse();
         local3_6.method_1522().method_1235(true);
      }
   }

   @Generated
   public ListSetting getModeSetting() {
      return this.modeSetting;
   }

   @Generated
   public class_276 getGlowFramebuffer() {
      return this.glowFramebuffer;
   }
}
