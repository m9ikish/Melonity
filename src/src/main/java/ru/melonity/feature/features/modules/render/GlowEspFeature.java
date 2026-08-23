package ru.melonity.feature.features.modules.render;

import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1041;
import net.minecraft.class_276;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_6367;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.ILevelRenderer;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.MenuColorSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderEntitiesEvent;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.render.Weights;
import ru.melonity.util.render.shader.ShaderProgram;

@Environment(EnvType.CLIENT)
public class GlowEspFeature extends SimpleFeature {
   private final NumberSetting radiusSlider = new NumberSetting("global.radius", value -> String.format("%d", value.intValue()), 5, 20, 15);
   private final MenuColorSetting colorSetting = new MenuColorSetting("global.color", Color.GREEN);
   private class_276 entitiesFBO;
   private class_276 outlineFBO;
   private class_276 glowFBO;
   private final EventListener<RenderEntitiesEvent> onRenderEntities = event -> {
      this.outlineFBO = updateFramebuffer(this.outlineFBO);
      this.glowFBO = updateFramebuffer(this.glowFBO);
   };
   private final EventListener<RenderOverlayEvent> onOverlay = event -> {
      class_4587 local2_7 = event.matrixStack();
      this.entitiesFBO = ((ILevelRenderer)mc.field_1769).entitiesFramebuffer();
      if (this.entitiesFBO != null) {
         Color local3_3 = Melonity.INSTANCE.getFeatureManager().getFeature(GlowEspFeature.class).get().getColorSetting().getColor();
         float[] local4_4 = new float[]{(float)local3_3.getRed() / 255.0F, (float)local3_3.getGreen() / 255.0F, (float)local3_3.getBlue() / 255.0F};
         GL11.glEnable(3042);
         GL30.glBlendFuncSeparate(770, 771, 1, 0);
         ShaderProgram local5_0 = Melonity.INSTANCE.getShaderManager().getShader("glow_outline").get();
         class_1041 local6_1 = mc.method_22683();
         local5_0.use();
         local5_0.setUniform1i("tex", 0);
         local5_0.setUniform2f("texelSize", 1.0F / (float)local6_1.method_4480(), 1.0F / (float)local6_1.method_4507());
         local5_0.setUniform3f("color", local4_4[0], local4_4[1], local4_4[2]);
         local5_0.setUniform1f("toggleAlpha", 1.0F);
         Melonity.INSTANCE
            .getRenderSystem()
            .addTexture(
               this.entitiesFBO.method_30277(),
               0.0F,
               0.0F,
               (float)mc.method_22683().method_4486(),
               (float)mc.method_22683().method_4502(),
               Color.WHITE,
               local2_7
            );
         local5_0.deuse();
         this.outlineFBO.method_1230(false);
         this.outlineFBO.method_1235(true);
         local5_0 = Melonity.INSTANCE.getShaderManager().getShader("outline_glow").get();
         local5_0.use();
         this.setupOutlineUniforms(0.0F, 1.0F);
         Melonity.INSTANCE
            .getRenderSystem()
            .addTexture(
               this.entitiesFBO.method_30277(),
               0.0F,
               0.0F,
               (float)mc.method_22683().method_4486(),
               (float)mc.method_22683().method_4502(),
               Color.WHITE,
               local2_7
            );
         local5_0.use();
         this.setupOutlineUniforms(1.0F, 0.0F);
         Melonity.INSTANCE
            .getRenderSystem()
            .addTexture(
               this.entitiesFBO.method_30277(),
               0.0F,
               0.0F,
               (float)mc.method_22683().method_4486(),
               (float)mc.method_22683().method_4502(),
               Color.WHITE,
               local2_7
            );
         local5_0.deuse();
         this.outlineFBO.method_1240();
         local5_0 = Melonity.INSTANCE.getShaderManager().getShader("shitten_glow").get();
         this.glowFBO.method_1230(false);
         this.glowFBO.method_1235(true);
         local5_0.use();
         this.setupGlowUniforms(1.0F, 0.0F);
         int local6_6 = this.outlineFBO.method_30277();
         Melonity.INSTANCE
            .getRenderSystem()
            .addTexture(local6_6, 0.0F, 0.0F, (float)mc.method_22683().method_4486(), (float)mc.method_22683().method_4502(), Color.WHITE, local2_7);
         local5_0.deuse();
         this.glowFBO.method_1240();
         mc.method_1522().method_1235(true);
         local5_0.use();
         this.setupGlowUniforms(0.0F, 1.0F);
         Melonity.INSTANCE
            .getRenderSystem()
            .addTexture(
               this.glowFBO.method_30277(), 0.0F, 0.0F, (float)mc.method_22683().method_4486(), (float)mc.method_22683().method_4502(), Color.WHITE, local2_7
            );
         local5_0.deuse();
      }
   };

   public GlowEspFeature() {
      super("GlowESP", MenuCategory.RENDER);
      this.setting(this.radiusSlider);
      this.setting(this.colorSetting);
   }

   private void setupGlowUniforms(float firstDirection, float secondDirection) {
      Color local3_0 = Melonity.INSTANCE.getFeatureManager().getFeature(GlowEspFeature.class).get().getColorSetting().getColor();
      float[] local4_1 = new float[]{(float)local3_0.getRed() / 255.0F, (float)local3_0.getGreen() / 255.0F, (float)local3_0.getBlue() / 255.0F};
      ShaderProgram local5_2 = Melonity.INSTANCE.getShaderManager().getShader("shitten_glow").get();
      local5_2.setUniform2f("resolution", (float)mc.method_22683().method_4480(), (float)mc.method_22683().method_4507());
      local5_2.setUniform1i("tex", 0);
      local5_2.setUniform1f("radius", this.radiusSlider.getValue().floatValue());
      local5_2.setUniform2f("texelSize", 1.0F / (float)mc.method_22683().method_4480(), 1.0F / (float)mc.method_22683().method_4507());
      local5_2.setUniform2f("direction", firstDirection, secondDirection);
      local5_2.setUniform4f("color", local4_1[0], local4_1[1], local4_1[2], 1.0F);
      local5_2.setUniform1f("exposure", 1.5F);
      local5_2.setUniform1f("toggleAlpha", 1.0F);
      local5_2.setUniform1fv("weights", Weights.generateGaussWeights(this.radiusSlider.getValue().floatValue()));
   }

   private void setupOutlineUniforms(float firstDirection, float secondDirection) {
      class_1041 local3_0 = mc.method_22683();
      ShaderProgram local4_1 = Melonity.INSTANCE.getShaderManager().getShader("outline_glow").get();
      Color local5_2 = Melonity.INSTANCE.getFeatureManager().getFeature(GlowEspFeature.class).get().getColorSetting().getColor();
      float[] local6_3 = new float[]{(float)local5_2.getRed() / 255.0F, (float)local5_2.getGreen() / 255.0F, (float)local5_2.getBlue() / 255.0F};
      local4_1.setUniform1i("tex", 0);
      local4_1.setUniform2f("texelSize", 1.0F / (float)local3_0.method_4480(), 1.0F / (float)local3_0.method_4507());
      local4_1.setUniform2f("direction", firstDirection, secondDirection);
      local4_1.setUniform1f("radius", this.radiusSlider.getValue().floatValue());
      local4_1.setUniform3f("color", local6_3[0], local6_3[1], local6_3[2]);
      local4_1.setUniform1f("toggleAlpha", 1.0F);
   }

   public static class_276 updateFramebuffer(class_276 fbo) {
      class_1041 local1_0 = class_310.method_1551().method_22683();
      int local2_1 = Math.max(local1_0.method_4480(), 10);
      int local3_2 = Math.max(local1_0.method_4507(), 10);
      if (fbo != null && (fbo.field_1480 != local2_1 || fbo.field_1477 != local3_2)) {
         fbo.method_1234(class_310.method_1551().method_22683().method_4489(), class_310.method_1551().method_22683().method_4506(), false);
      }

      if (fbo == null) {
         fbo = new class_6367(local2_1, local3_2, false, false);
      }

      return fbo;
   }

   @Generated
   public NumberSetting getRadiusSlider() {
      return this.radiusSlider;
   }

   @Generated
   public MenuColorSetting getColorSetting() {
      return this.colorSetting;
   }

   @Generated
   public class_276 getEntitiesFBO() {
      return this.entitiesFBO;
   }

   @Generated
   public class_276 getOutlineFBO() {
      return this.outlineFBO;
   }

   @Generated
   public class_276 getGlowFBO() {
      return this.glowFBO;
   }

   @Generated
   public EventListener<RenderEntitiesEvent> getOnRenderEntities() {
      return this.onRenderEntities;
   }

   @Generated
   public EventListener<RenderOverlayEvent> getOnOverlay() {
      return this.onOverlay;
   }
}
