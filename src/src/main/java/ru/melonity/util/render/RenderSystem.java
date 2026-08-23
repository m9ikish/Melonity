package ru.melonity.util.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.class_4534;
import com.mojang.blaze3d.platform.GlStateManager.class_4535;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1011;
import net.minecraft.class_1041;
import net.minecraft.class_1043;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_276;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_6367;
import net.minecraft.class_757;
import net.minecraft.class_293.class_5596;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.player.NameProtectFeature;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.render.shader.ShaderProgram;

@Environment(EnvType.CLIENT)
public class RenderSystem {
   private float scaling;
   private float alphaModifier = 1.0F;
   private final Map<String, ShaderProgram> cachedShaders = new HashMap<>();
   private final class_310 mc = class_310.method_1551();
   private class_1041 window;
   private final Map<class_2960, class_2960> sanitizedTextureCache = new HashMap<>();
   private class_276 blurFramebuffer;

   public void setAlphaModifier(float alphaModifier) {
      this.alphaModifier = alphaModifier;
   }

   public RenderSystem() {
      this.window = this.mc.method_22683();
   }

   public float getFakeScaling() {
      this.scaling = 2.0F;
      return 2.0F / this.scaling;
   }

   public static Color setAlpha(Color color, int alpha) {
      if (alpha == color.getAlpha()) {
         return color;
      } else {
         alpha = class_3532.method_15340(alpha, 0, 255);
         return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
      }
   }

   public void addRectSilent(float x, float y, float width, float height, Color col, class_4587 poseStack) {
      Color local7_0 = setAlpha(col, class_3532.method_15340((int)((float)col.getAlpha() * this.alphaModifier), 0, 255));
      OpenGLUtil.blend(
         () -> {
            com.mojang.blaze3d.systems.RenderSystem.setShader(class_757::method_34540);
            class_289 local6_0 = class_289.method_1348();
            class_287 local7_1 = local6_0.method_60827(class_5596.field_27382, class_290.field_1576);
            local7_1.method_22918(poseStack.method_23760().method_23761(), x, y, 0.0F)
               .method_1336(local7_0.getRed(), local7_0.getGreen(), local7_0.getBlue(), local7_0.getAlpha());
            local7_1.method_22918(poseStack.method_23760().method_23761(), x, y + height, 0.0F)
               .method_1336(local7_0.getRed(), local7_0.getGreen(), local7_0.getBlue(), local7_0.getAlpha());
            local7_1.method_22918(poseStack.method_23760().method_23761(), x + width, y + height, 0.0F)
               .method_1336(local7_0.getRed(), local7_0.getGreen(), local7_0.getBlue(), local7_0.getAlpha());
            local7_1.method_22918(poseStack.method_23760().method_23761(), x + width, y, 0.0F)
               .method_1336(local7_0.getRed(), local7_0.getGreen(), local7_0.getBlue(), local7_0.getAlpha());
            class_286.method_43433(local7_1.method_60800());
         }
      );
   }

   public void addLine(float x, float y, float x1, float y1, float thickness, Color col) {
      Color local7_0 = setAlpha(col, class_3532.method_15340((int)((float)col.getAlpha() * this.alphaModifier), 0, 255));
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      com.mojang.blaze3d.systems.RenderSystem.lineWidth(thickness);
      OpenGLUtil.blend(() -> {
         com.mojang.blaze3d.systems.RenderSystem.setShader(class_757::method_34540);
         class_289 local5_0 = class_289.method_1348();
         class_287 local6_1 = local5_0.method_60827(class_5596.field_27377, class_290.field_1576);
         local6_1.method_22912(x, y, 0.0F).method_39415(local7_0.getRGB());
         local6_1.method_22912(x1, y1, 0.0F).method_39415(local7_0.getRGB());
         class_286.method_43433(local6_1.method_60800());
      });
      com.mojang.blaze3d.systems.RenderSystem.lineWidth(1.0F);
      GL11.glHint(3154, 4352);
      GL11.glDisable(2848);
   }

   public void addRect(float x, float y, float width, float height, Color color, class_4587 poseStack) {
      this.addRect(x, y, width, height, 0.0F, color, poseStack);
   }

   public void addImage(float x, float y, float width, float height, String path, Color color, class_4587 poseStack) {
      this.addTexture(class_2960.method_60656(path), x, y, width, height, color, poseStack);
   }

   public void drawPlayerFace(class_4587 stack, class_1309 entity, float x, float y, float width, float height) {
      this.addHead(entity, x, y, width, height, 4.0F, stack);
   }

   public void addGlow(float x, float y, float width, float height, float glowRadius, Color color, class_4587 stack) {
      this.addShadowRect(x, y, width, height, 0.0F, glowRadius, color, stack);
   }

   public void addRect(float x2, float y2, float width2, float height2, float radius, Color col, class_4587 poseStack) {
      if (radius <= 0.0F) {
         this.addRectSilent(x2, y2, width2, height2, col, poseStack);
      } else {
         Color local8_0 = setAlpha(col, class_3532.method_15340((int)((float)col.getAlpha() * this.alphaModifier), 0, 255));
         ShaderProgram local13_5 = this.getShader("round_rect");
         if (local13_5 != null) {
            local13_5.use();
            local13_5.setUniform2f("location", x2 * 2.0F, (float)this.window.method_4507() / 2.0F - height2 - y2);
            local13_5.setUniform2f("rectSize", width2 * 2.0F, height2 * 2.0F);
            local13_5.setUniform1f("radius", radius);
            local13_5.setUniform4fv("color", local8_0);
            this.renderQuad(x2, y2, width2, height2, poseStack);
            local13_5.deuse();
         }
      }
   }

   public void addColorPickerContainer(float x, float y, float width, float height, float radius, float hue, class_4587 poseStack) {
      ShaderProgram local8_0 = this.getShader("color_picker");
      if (local8_0 != null) {
         local8_0.use();
         local8_0.setUniform2f("location", x * 2.0F, (float)this.window.method_4507() / 2.0F - height - y);
         local8_0.setUniform2f("rectSize", width * 2.0F, height * 2.0F);
         local8_0.setUniform1f("radius", radius);
         local8_0.setUniform1f("hue", hue);
         this.renderQuad(x, y, width, height, poseStack);
         local8_0.deuse();
      }
   }

   private void renderQuad(float x, float y, float width, float height, class_4587 poseStack) {
      OpenGLUtil.blend(() -> {
         class_289 local5_0 = class_289.method_1348();
         class_287 local6_1 = local5_0.method_60827(class_5596.field_27382, class_290.field_1585);
         local6_1.method_22918(poseStack.method_23760().method_23761(), x, y, 0.0F).method_22913(0.0F, 0.0F);
         local6_1.method_22918(poseStack.method_23760().method_23761(), x, y + height, 0.0F).method_22913(0.0F, 1.0F);
         local6_1.method_22918(poseStack.method_23760().method_23761(), x + width, y + height, 0.0F).method_22913(1.0F, 1.0F);
         local6_1.method_22918(poseStack.method_23760().method_23761(), x + width, y, 0.0F).method_22913(1.0F, 0.0F);
         class_286.method_43433(local6_1.method_60800());
      });
   }

   private ShaderProgram getShader(String name) {
      if (this.cachedShaders.containsKey(name)) {
         return this.cachedShaders.get(name);
      } else {
         Optional<ShaderProgram> local2_0 = Melonity.INSTANCE.getShaderManager().getShader(name);
         if (local2_0.isEmpty()) {
            return null;
         } else {
            ShaderProgram local3_1 = local2_0.get();
            this.cachedShaders.put(name, local3_1);
            return local3_1;
         }
      }
   }

   public void addOutlineRect(float x2, float y2, float width2, float height2, float radius, float thickness, Color col, class_4587 poseStack) {
      Color local9_0 = setAlpha(col, class_3532.method_15340((int)((float)col.getAlpha() * this.alphaModifier), 0, 255));
      ShaderProgram local14_5 = this.getShader("round_rect_outline");
      if (local14_5 != null) {
         local14_5.use();
         local14_5.setUniform2f("location", x2 * 2.0F, (float)this.window.method_4507() / 2.0F - height2 - y2);
         local14_5.setUniform2f("rectSize", width2 * 2.0F, height2 * 2.0F);
         local14_5.setUniform1f("radius", radius);
         local14_5.setUniform1f("thickness", thickness);
         local14_5.setUniform4fv("color", new Color(0, 0, 0, 0));
         local14_5.setUniform4fv("outlineColor", local9_0);
         OpenGLUtil.blend(
            () -> {
               class_289 local6_0 = class_289.method_1348();
               class_287 local7_1 = local6_0.method_60827(class_5596.field_27382, class_290.field_1585);
               local7_1.method_22918(poseStack.method_23760().method_23761(), x2 - thickness, y2 - (1.0F + thickness), 0.0F).method_22913(0.0F, 0.0F);
               float local8_2 = y2 - (1.0F + thickness) + height2 + 2.0F + thickness * 2.0F;
               local7_1.method_22918(poseStack.method_23760().method_23761(), x2 - thickness, local8_2, 0.0F).method_22913(0.0F, 1.0F);
               local7_1.method_22918(poseStack.method_23760().method_23761(), x2 - thickness + width2 + thickness * 2.0F, local8_2, 0.0F)
                  .method_22913(1.0F, 1.0F);
               local7_1.method_22918(poseStack.method_23760().method_23761(), x2 - thickness + width2 + thickness * 2.0F, y2 - (1.0F + thickness), 0.0F)
                  .method_22913(1.0F, 0.0F);
               class_286.method_43433(local7_1.method_60800());
            }
         );
         local14_5.deuse();
      }
   }

   public void addShadowRect(float x, float y, float width, float height, float radius, float shadowRadius, Color col, class_4587 poseStack) {
      Color local9_0 = setAlpha(col, class_3532.method_15340((int)((float)col.getAlpha() * this.alphaModifier), 0, 255));
      ShaderProgram local10_1 = this.getShader("outer_shadow");
      if (local10_1 != null) {
         local10_1.use();
         local10_1.setUniform2f("location", x * 2.0F, (float)this.window.method_4507() / 2.0F - height - y);
         local10_1.setUniform2f("size", width * 2.0F, height * 2.0F);
         local10_1.setUniform1f("radius", shadowRadius);
         local10_1.setUniform4f("corners", radius, radius, radius, radius);
         local10_1.setUniform4fv("color", local9_0);
         this.renderQuad(x, y, width, height, poseStack);
         local10_1.deuse();
      }
   }

   public void addTexture(
      class_2960 identifier, float x2, float y2, float width2, float height2, float radius, float u, float v, float w, float h, Color col, class_4587 poseStack
   ) {
      ShaderProgram local17_4 = this.getShader("round_texture");
      if (local17_4 != null) {
         local17_4.use();
         local17_4.setUniform2f("location", x2 * 2.0F, (float)this.window.method_4507() / 2.0F - height2 - y2);
         local17_4.setUniform2f("rectSize", width2 * 2.0F, height2 * 2.0F);
         local17_4.setUniform1f("radius", radius);
         local17_4.setUniform1f("u", u);
         local17_4.setUniform1f("v", v);
         local17_4.setUniform1f("w", w);
         local17_4.setUniform1f("h", h);
         local17_4.setUniform1i("tex", 0);
         local17_4.setUniform1f("alpha", class_3532.method_15363((float)col.getAlpha() * this.alphaModifier / 255.0F, 0.0F, 1.0F));
         this.addTexture(this.mc.method_1531().method_4619(identifier).method_4624(), x2, y2, width2, height2, Color.WHITE, poseStack);
         local17_4.deuse();
      }
   }

   public void addRectVGradientSilent(
      float x2,
      float y2,
      float width2,
      float height2,
      float radius,
      Color bottomLeftCol,
      Color topLeftCol,
      Color topRightCol,
      Color bottomRightCol,
      class_4587 poseStack
   ) {
      Color local11_0 = setAlpha(bottomLeftCol, class_3532.method_15340((int)((float)bottomLeftCol.getAlpha() * this.alphaModifier), 0, 255));
      Color local12_1 = setAlpha(topLeftCol, class_3532.method_15340((int)((float)topLeftCol.getAlpha() * this.alphaModifier), 0, 255));
      Color local13_2 = setAlpha(topRightCol, class_3532.method_15340((int)((float)topRightCol.getAlpha() * this.alphaModifier), 0, 255));
      Color local14_3 = setAlpha(bottomRightCol, class_3532.method_15340((int)((float)bottomRightCol.getAlpha() * this.alphaModifier), 0, 255));
      com.mojang.blaze3d.systems.RenderSystem.setShader(class_757::method_34540);
      OpenGLUtil.blend(() -> {
         class_289 local9_0 = class_289.method_1348();
         class_287 local10_1 = local9_0.method_60827(class_5596.field_27382, class_290.field_1576);
         local10_1.method_22918(poseStack.method_23760().method_23761(), x2, y2, 0.0F).method_39415(local11_0.getRGB());
         local10_1.method_22918(poseStack.method_23760().method_23761(), x2, y2 + height2, 0.0F).method_39415(local12_1.getRGB());
         local10_1.method_22918(poseStack.method_23760().method_23761(), x2 + width2, y2 + height2, 0.0F).method_39415(local13_2.getRGB());
         local10_1.method_22918(poseStack.method_23760().method_23761(), x2 + width2, y2, 0.0F).method_39415(local14_3.getRGB());
         class_286.method_43433(local10_1.method_60800());
      });
   }

   public void addRectGradient(
      float x2,
      float y2,
      float width2,
      float height2,
      float radius,
      Color bottomLeftCol,
      Color topLeftCol,
      Color topRightCol,
      Color bottomRightCol,
      class_4587 poseStack
   ) {
      if (radius <= 0.0F) {
         this.addRectVGradientSilent(x2, y2, width2, height2, radius, bottomLeftCol, topLeftCol, topRightCol, bottomRightCol, poseStack);
      } else {
         Color local11_0 = setAlpha(bottomLeftCol, class_3532.method_15340((int)((float)bottomLeftCol.getAlpha() * this.alphaModifier), 0, 255));
         Color local12_1 = setAlpha(topLeftCol, class_3532.method_15340((int)((float)topLeftCol.getAlpha() * this.alphaModifier), 0, 255));
         Color local13_2 = setAlpha(topRightCol, class_3532.method_15340((int)((float)topRightCol.getAlpha() * this.alphaModifier), 0, 255));
         Color local14_3 = setAlpha(bottomRightCol, class_3532.method_15340((int)((float)bottomRightCol.getAlpha() * this.alphaModifier), 0, 255));
         ShaderProgram local19_8 = this.getShader("round_rect_gradient");
         if (local19_8 != null) {
            local19_8.use();
            local19_8.setUniform2f("location", x2 * 2.0F, (float)this.window.method_4507() / 2.0F - height2 - y2);
            local19_8.setUniform2f("rectSize", width2 * 2.0F, height2 * 2.0F);
            local19_8.setUniform1f("radius", radius);
            local19_8.setUniform4fv("color1", local11_0);
            local19_8.setUniform4fv("color2", local12_1);
            local19_8.setUniform4fv("color3", local14_3);
            local19_8.setUniform4fv("color4", local13_2);
            this.renderQuad(x2, y2, width2, height2, poseStack);
            local19_8.deuse();
         }
      }
   }

   public void addRectGradientCorners(
      float x,
      float y,
      float width,
      float height,
      float radiusTopLeft,
      float radiusBottomLeft,
      float radiusTopRight,
      float radiusBottomRight,
      Color bottomLeftCol,
      Color topLeftCol,
      Color topRightCol,
      Color bottomRightCol,
      class_4587 poseStack
   ) {
      Color local14_0 = setAlpha(bottomLeftCol, class_3532.method_15340((int)((float)bottomLeftCol.getAlpha() * this.alphaModifier), 0, 255));
      Color local15_1 = setAlpha(topLeftCol, class_3532.method_15340((int)((float)topLeftCol.getAlpha() * this.alphaModifier), 0, 255));
      Color local16_2 = setAlpha(topRightCol, class_3532.method_15340((int)((float)topRightCol.getAlpha() * this.alphaModifier), 0, 255));
      Color local17_3 = setAlpha(bottomRightCol, class_3532.method_15340((int)((float)bottomRightCol.getAlpha() * this.alphaModifier), 0, 255));
      ShaderProgram local18_4 = this.getShader("round_rect_gradient_corners");
      if (local18_4 != null) {
         local18_4.use();
         local18_4.setUniform2f("location", x * 2.0F, (float)this.window.method_4507() / 2.0F - height - y);
         local18_4.setUniform2f("size", width * 2.0F, height * 2.0F);
         local18_4.setUniform4f("round", radiusBottomLeft, radiusTopLeft, radiusTopRight, radiusBottomRight);
         local18_4.setUniform4fv("color1", local14_0);
         local18_4.setUniform4fv("color2", local15_1);
         local18_4.setUniform4fv("color3", local17_3);
         local18_4.setUniform4fv("color4", local16_2);
         this.renderQuad(x, y, width, height, poseStack);
         local18_4.deuse();
      }
   }

   public void addTexture(class_2960 path, float x, float y, float width, float height, class_4587 poseStack) {
      this.addTexture(path, x, y, width, height, Color.WHITE, poseStack);
   }

   public void addHueBar(float x, float y, float width, float height, float radius, class_4587 poseStack) {
      ShaderProgram local7_0 = this.getShader("hue_bar");
      if (local7_0 != null) {
         local7_0.use();
         local7_0.setUniform2f("location", x * 2.0F, (float)this.window.method_4507() / 2.0F - height - y);
         local7_0.setUniform2f("rectSize", width * 2.0F, height * 2.0F);
         local7_0.setUniform1f("radius", radius);
         this.renderQuad(x, y, width, height, poseStack);
         local7_0.deuse();
      }
   }

   public void addCircle(float x, float y, float start, float end, float radius, float width, boolean filled, Color col, class_4587 poseStack) {
      Color local10_0 = setAlpha(col, class_3532.method_15340((int)((float)col.getAlpha() * this.alphaModifier), 0, 255));
      ShaderProgram local14_4 = this.getShader("circle_r");
      if (local14_4 != null) {
         local14_4.use();
         local14_4.setUniform2f("rectSize", radius * 2.0F * 2.0F, radius * 2.0F * 2.0F);
         local14_4.setUniform2f("center", x * 2.0F, (float)this.window.method_4507() / 2.0F - y);
         local14_4.setUniform1f("radius", radius);
         local14_4.setUniform1f("startAngle", start);
         local14_4.setUniform1f("endAngle", end);
         local14_4.setUniform1f("lineWidth", width / 3.0F);
         local14_4.setUniform1i("filled", filled ? 1 : 0);
         local14_4.setUniform4fv("color", local10_0);
         OpenGLUtil.blend(() -> {
            class_289 local4_0 = class_289.method_1348();
            class_287 local5_1 = local4_0.method_60827(class_5596.field_27382, class_290.field_1585);
            local5_1.method_22918(poseStack.method_23760().method_23761(), x - radius * 2.0F, y - radius * 2.0F, 0.0F).method_22913(0.0F, 0.0F);
            local5_1.method_22918(poseStack.method_23760().method_23761(), x - radius * 2.0F, y + radius * 2.0F, 0.0F).method_22913(0.0F, 1.0F);
            local5_1.method_22918(poseStack.method_23760().method_23761(), x + radius * 2.0F, y + radius * 2.0F, 0.0F).method_22913(1.0F, 1.0F);
            local5_1.method_22918(poseStack.method_23760().method_23761(), x + radius * 2.0F, y - radius * 2.0F, 0.0F).method_22913(1.0F, 0.0F);
            class_286.method_43433(local5_1.method_60800());
         });
         local14_4.deuse();
      }
   }

   public void addTexture(class_2960 path, float x2, float y2, float width2, float height2, Color col, class_4587 poseStack) {
      Color local8_0 = setAlpha(col, class_3532.method_15340((int)((float)col.getAlpha() * this.alphaModifier), 0, 255));
      OpenGLUtil.blend(
         () -> {
            com.mojang.blaze3d.systems.RenderSystem.setShader(class_757::method_34543);
            com.mojang.blaze3d.systems.RenderSystem.setShaderTexture(0, this.mc.method_1531().method_4619(path).method_4624());
            class_289 local8_0x = class_289.method_1348();
            class_287 local9_1 = local8_0x.method_60827(class_5596.field_27382, class_290.field_1575);
            local9_1.method_22918(poseStack.method_23760().method_23761(), x2, y2, 0.0F).method_39415(local8_0.getRGB()).method_22913(0.0F, 0.0F);
            local9_1.method_22918(poseStack.method_23760().method_23761(), x2, y2 + height2, 0.0F).method_39415(local8_0.getRGB()).method_22913(0.0F, 1.0F);
            local9_1.method_22918(poseStack.method_23760().method_23761(), x2 + width2, y2 + height2, 0.0F)
               .method_39415(local8_0.getRGB())
               .method_22913(1.0F, 1.0F);
            local9_1.method_22918(poseStack.method_23760().method_23761(), x2 + width2, y2, 0.0F).method_39415(local8_0.getRGB()).method_22913(1.0F, 0.0F);
            class_286.method_43433(local9_1.method_60800());
         }
      );
   }

   public void addSanitizedTexture(class_2960 identifier, float x, float y, float width, float height, Color tint, class_4587 poseStack) {
      class_2960 local8_0 = this.getSanitizedTexture(identifier);
      class_2960 local9_1 = local8_0 == null ? identifier : local8_0;
      if (tint == null) {
         this.addTexture(local9_1, x, y, width, height, poseStack);
      } else {
         this.addTexture(local9_1, x, y, width, height, tint, poseStack);
      }
   }

   private class_2960 getSanitizedTexture(class_2960 original) {
      if (original == null) {
         return null;
      } else if (this.sanitizedTextureCache.containsKey(original)) {
         return this.sanitizedTextureCache.get(original);
      } else {
         class_2960 local2_0 = this.createSanitizedTexture(original);
         this.sanitizedTextureCache.put(original, local2_0 == null ? original : local2_0);
         return this.sanitizedTextureCache.get(original);
      }
   }

   private class_2960 createSanitizedTexture(class_2960 original) {
      Optional<class_3298> local2_16 = this.mc.method_1478().method_14486(original);
      if (local2_16.isEmpty()) {
         return null;
      } else {
         class_3298 local3_17 = local2_16.get();

         try {
            class_2960 var25;
            try (InputStream local4_14 = local3_17.method_14482()) {
               class_1011 local5_13 = class_1011.method_4309(local4_14);

               label129: {
                  Object local6_9;
                  try {
                     if (local5_13 != null) {
                        class_1011 local6_9x = new class_1011(local5_13.method_4307(), local5_13.method_4323(), false);

                        for (int local7_8 = 0; local7_8 < local5_13.method_4323(); local7_8++) {
                           for (int local8_7 = 0; local8_7 < local5_13.method_4307(); local8_7++) {
                              int local9_0 = local5_13.method_4315(local8_7, local7_8);
                              int local10_1 = local9_0 >> 24 & 0xFF;
                              if (local10_1 <= 0) {
                                 local6_9x.method_4305(local8_7, local7_8, 0);
                              } else {
                                 int local11_2 = local9_0 & 0xFF;
                                 int local12_3 = local9_0 >> 8 & 0xFF;
                                 int local13_4 = local9_0 >> 16 & 0xFF;
                                 int local14_5 = class_3532.method_15340(
                                    (int)((float)local11_2 * 0.299F + (float)local12_3 * 0.587F + (float)local13_4 * 0.114F), 0, 255
                                 );
                                 int local15_6 = local10_1 << 24 | local14_5 << 16 | local14_5 << 8 | local14_5;
                                 local6_9x.method_4305(local8_7, local7_8, local15_6);
                              }
                           }
                        }

                        String local7_10 = "generated/sanitized/" + original.method_12836() + "_" + original.method_12832().replace('/', '_').replace('.', '_');
                        class_2960 local8_11 = class_2960.method_60655(original.method_12836(), local7_10);
                        class_1043 local9_12 = new class_1043(local6_9x);
                        if (com.mojang.blaze3d.systems.RenderSystem.isOnRenderThreadOrInit()) {
                           local9_12.method_4524();
                           this.mc.method_1531().method_4616(local8_11, local9_12);
                        } else {
                           com.mojang.blaze3d.systems.RenderSystem.recordRenderCall(() -> {
                              local9_12.method_4524();
                              this.mc.method_1531().method_4616(local8_11, local9_12);
                           });
                        }

                        var25 = local8_11;
                        break label129;
                     }

                     local6_9 = null;
                  } catch (Throwable var18) {
                     if (local5_13 != null) {
                        try {
                           local5_13.close();
                        } catch (Throwable var17) {
                           var18.addSuppressed(var17);
                        }
                     }

                     throw var18;
                  }

                  if (local5_13 != null) {
                     local5_13.close();
                  }

                  return (class_2960)local6_9;
               }

               if (local5_13 != null) {
                  local5_13.close();
               }
            }

            return var25;
         } catch (IOException var20) {
            return null;
         }
      }
   }

   public void addTexture(int textureID, float x, float y, float width, float height, Color col, class_4587 poseStack) {
      Color local8_0 = setAlpha(col, class_3532.method_15340((int)((float)col.getAlpha() * this.alphaModifier), 0, 255));
      GlStateManager._texParameter(3553, 10241, 9728);
      GlStateManager._texParameter(3553, 10240, 9728);
      GlStateManager._bindTexture(textureID);
      GlStateManager._texParameter(3553, 10241, 9728);
      GlStateManager._texParameter(3553, 10240, 9728);
      com.mojang.blaze3d.systems.RenderSystem.setShaderTexture(0, textureID);
      OpenGLUtil.blend(() -> {
         class_289 local6_0 = class_289.method_1348();
         class_287 local7_1 = local6_0.method_60827(class_5596.field_27382, class_290.field_1575);
         local7_1.method_22918(poseStack.method_23760().method_23761(), x, y, 0.0F).method_39415(local8_0.getRGB()).method_22913(0.0F, 1.0F);
         local7_1.method_22918(poseStack.method_23760().method_23761(), x, y + height, 0.0F).method_39415(local8_0.getRGB()).method_22913(0.0F, 0.0F);
         local7_1.method_22918(poseStack.method_23760().method_23761(), x + width, y + height, 0.0F).method_39415(local8_0.getRGB()).method_22913(1.0F, 0.0F);
         local7_1.method_22918(poseStack.method_23760().method_23761(), x + width, y, 0.0F).method_39415(local8_0.getRGB()).method_22913(1.0F, 1.0F);
         class_286.method_43433(local7_1.method_60800());
      });
   }

   public void addBlur(float x, float y, float width, float height, float blurRadius, float cornerRadius, class_4587 poseStack) {
      if (!(blurRadius <= 0.0F) && !(cornerRadius <= 0.0F)) {
         class_1041 local8_0 = this.mc.method_22683();
         float local9_1 = ScaleUtils.getCurrentScale();
         float local10_2 = (float)local8_0.method_4489();
         float local11_3 = (float)local8_0.method_4506();
         float local12_4 = x * local9_1;
         float local13_5 = y * local9_1;
         float local14_6 = width * local9_1;
         float local15_7 = height * local9_1;
         float local16_8 = Math.max(local12_4, 0.0F);
         float local17_9 = local11_3 - (local13_5 + local15_7);
         local17_9 = Math.max(local17_9, 0.0F);
         float local18_10 = Math.min(local14_6, local10_2 - local16_8);
         float local19_11 = Math.min(local15_7, local11_3 - local17_9);
         ShaderProgram local20_12 = this.getShader("blur_round");
         if (local20_12 != null) {
            local20_12.use();
            float local21_13 = Math.max(Math.min(local18_10, local19_11) * 0.5F, 0.5F);
            float local22_14 = Math.min(Math.max(cornerRadius * local9_1, 0.5F), local21_13);
            local20_12.setUniform2f("rectMin", local16_8, local17_9);
            local20_12.setUniform2f("rectSize", Math.max(local18_10, 1.0F), Math.max(local19_11, 1.0F));
            local20_12.setUniform1f("cornerRadius", local22_14);
            float local23_15 = Math.max(1.0F, local9_1);
            local20_12.setUniform1f("smoothness", local23_15);
            local20_12.setUniform1f("blurRadius", Math.max(Math.max(blurRadius, 6.0F) * local9_1, 2.0F));
            local20_12.setUniform2f("textureSize", local10_2, local11_3);
            local20_12.setUniform4f("color", 1.0F, 1.0F, 1.0F, 1.0F);
            class_276 local24_16 = this.mc.method_1522();
            local24_16.method_35610();
            GlStateManager._bindTexture(local24_16.method_30277());
            com.mojang.blaze3d.systems.RenderSystem.setShaderTexture(0, local24_16.method_30277());
            local20_12.setUniform1i("Sampler0", 0);
            this.renderQuad(x, y, width, height, poseStack);
            local20_12.deuse();
            GlStateManager._bindTexture(0);
            this.mc.method_1522().method_1235(true);
         }
      }
   }

   private class_276 updateFramebuffer(class_276 fbo) {
      class_1041 local2_0 = this.mc.method_22683();
      int local3_1 = Math.max(local2_0.method_4480(), 10);
      int local4_2 = Math.max(local2_0.method_4507(), 10);
      if (fbo != null && (fbo.field_1480 != local3_1 || fbo.field_1477 != local4_2)) {
         fbo.method_1234(this.mc.method_22683().method_4489(), this.mc.method_22683().method_4506(), false);
      }

      if (fbo == null) {
         fbo = new class_6367(local3_1, local4_2, false, false);
      }

      return fbo;
   }

   public void addText(GlyphData glyphData, String text, float x, float y, class_4587 poseStack) {
      this.addText(glyphData, text, x, y, Color.WHITE, poseStack);
   }

   public void addText(GlyphData glyphData, String text, float x, float y, Color color, class_4587 poseStack) {
      this.addText(glyphData, text, x, y, color, false, poseStack);
   }

   public void addText(GlyphData glyphData, String text, float xx, float y, Color col, boolean shadow, class_4587 poseStack) {
      if (text != null) {
         if (Melonity.INSTANCE.getFeatureManager() != null
            && Melonity.INSTANCE.getFeatureManager().getFeature(NameProtectFeature.class).get().isEnabled()
            && this.mc.field_1724 != null
            && text.contains(this.mc.field_1724.method_5477().getString())) {
            text = text.replace(this.mc.field_1724.method_5477().getString(), "Melonity");
         }

         Color local8_0 = setAlpha(col, class_3532.method_15340((int)((float)col.getAlpha() * this.alphaModifier), 0, 255));
         glyphData.drawString(poseStack, text, (double)xx, (double)y, local8_0.getRGB());
      }
   }

   public void addHead(class_1309 entity, float x, float y, float width, float height, float radius, class_4587 poseStack) {
      if (entity != null) {
         class_2960 local8_0 = this.mc.method_1561().method_3953(entity).method_3931(entity);
         if (entity instanceof class_1657 && this.mc.method_1562() != null && this.mc.method_1562().method_2871(entity.method_5667()) != null) {
            local8_0 = this.mc.method_1562().method_2871(entity.method_5667()).method_52810().comp_1626();
         }

         if (local8_0 == null) {
            local8_0 = class_2960.method_60656("melonity/images/generic/default_head.png");
         }

         float local9_1 = 0.015625F;
         this.addTexture(local8_0, x, y, width, height, radius, local9_1 * 8.0F, local9_1 * 8.0F, 0.125F, 0.125F, Color.WHITE, poseStack);
      }
   }

   public void drawBoundingBox(class_4587 matrix, class_238 bb, int color, float lineWidth, boolean depth, boolean cull, boolean isFilled) {
      if (!depth) {
         com.mojang.blaze3d.systems.RenderSystem.disableDepthTest();
      }

      if (!cull) {
         com.mojang.blaze3d.systems.RenderSystem.disableCull();
      }

      this.drawLineQuad(
         matrix,
         (float)bb.field_1323,
         (float)bb.field_1322,
         (float)bb.field_1321,
         (float)bb.field_1320,
         (float)bb.field_1322,
         (float)bb.field_1324,
         color,
         lineWidth,
         true,
         true
      );
      this.drawLineQuad(
         matrix,
         (float)bb.field_1323,
         (float)bb.field_1325,
         (float)bb.field_1321,
         (float)bb.field_1320,
         (float)bb.field_1325,
         (float)bb.field_1324,
         color,
         lineWidth,
         true,
         true
      );
      if (isFilled) {
         this.drawQuadH(
            matrix,
            (float)bb.field_1323,
            (float)bb.field_1322,
            (float)bb.field_1321,
            (float)bb.field_1320,
            (float)bb.field_1322,
            (float)bb.field_1324,
            color,
            true,
            true
         );
         this.drawQuadH(
            matrix,
            (float)bb.field_1323,
            (float)bb.field_1325,
            (float)bb.field_1321,
            (float)bb.field_1320,
            (float)bb.field_1325,
            (float)bb.field_1324,
            color,
            true,
            true
         );
         this.drawQuad(
            matrix,
            (float)bb.field_1323,
            (float)bb.field_1322,
            (float)bb.field_1321,
            (float)bb.field_1320,
            (float)bb.field_1325,
            (float)bb.field_1321,
            color,
            true,
            true
         );
         this.drawQuad(
            matrix,
            (float)bb.field_1323,
            (float)bb.field_1322,
            (float)bb.field_1324,
            (float)bb.field_1320,
            (float)bb.field_1325,
            (float)bb.field_1324,
            color,
            true,
            true
         );
         this.drawQuad(
            matrix,
            (float)bb.field_1320,
            (float)bb.field_1322,
            (float)bb.field_1321,
            (float)bb.field_1320,
            (float)bb.field_1325,
            (float)bb.field_1324,
            color,
            true,
            true
         );
         this.drawQuad(
            matrix,
            (float)bb.field_1323,
            (float)bb.field_1322,
            (float)bb.field_1321,
            (float)bb.field_1323,
            (float)bb.field_1325,
            (float)bb.field_1324,
            color,
            true,
            true
         );
      }

      this.drawLine(
         matrix,
         (float)bb.field_1323,
         (float)bb.field_1322,
         (float)bb.field_1321,
         (float)bb.field_1323,
         (float)bb.field_1325,
         (float)bb.field_1321,
         color,
         lineWidth,
         true,
         true
      );
      this.drawLine(
         matrix,
         (float)bb.field_1320,
         (float)bb.field_1322,
         (float)bb.field_1321,
         (float)bb.field_1320,
         (float)bb.field_1325,
         (float)bb.field_1321,
         color,
         lineWidth,
         true,
         true
      );
      this.drawLine(
         matrix,
         (float)bb.field_1320,
         (float)bb.field_1322,
         (float)bb.field_1324,
         (float)bb.field_1320,
         (float)bb.field_1325,
         (float)bb.field_1324,
         color,
         lineWidth,
         true,
         true
      );
      this.drawLine(
         matrix,
         (float)bb.field_1323,
         (float)bb.field_1322,
         (float)bb.field_1324,
         (float)bb.field_1323,
         (float)bb.field_1325,
         (float)bb.field_1324,
         color,
         lineWidth,
         true,
         true
      );
      if (!cull) {
         com.mojang.blaze3d.systems.RenderSystem.enableCull();
      }

      if (!depth) {
         com.mojang.blaze3d.systems.RenderSystem.enableDepthTest();
      }
   }

   public void drawLineQuad(
      class_4587 matrix, float x1, float y1, float z1, float x2, float y2, float z2, int color, float lineWidth, boolean depth, boolean cull
   ) {
      this.drawLine(matrix, x1, y1, z1, x2, y1, z1, color, lineWidth, depth, cull);
      this.drawLine(matrix, x2, y1, z1, x2, y2, z2, color, lineWidth, depth, cull);
      this.drawLine(matrix, x2, y1, z2, x1, y2, z2, color, lineWidth, depth, cull);
      this.drawLine(matrix, x1, y1, z2, x1, y2, z1, color, lineWidth, depth, cull);
   }

   public void drawLine(class_4587 matrix, float x1, float y1, float z1, float x2, float y2, float z2, int color, float lineWidth, boolean depth, boolean cull) {
      class_243 local12_0 = class_310.method_1551().field_1773.method_19418().method_19326();
      float local13_1 = (float)((double)x1 - local12_0.field_1352);
      float local14_2 = (float)((double)y1 - local12_0.field_1351);
      float local15_3 = (float)((double)z1 - local12_0.field_1350);
      float local16_4 = (float)((double)x2 - local12_0.field_1352);
      float local17_5 = (float)((double)y2 - local12_0.field_1351);
      float local18_6 = (float)((double)z2 - local12_0.field_1350);
      matrix.method_22903();
      Matrix4f local19_7 = matrix.method_23760().method_23761();
      this.setupWorldRenderer();
      this.enableBlendAndSmoothLines(lineWidth);
      if (!depth) {
         com.mojang.blaze3d.systems.RenderSystem.disableDepthTest();
      }

      if (!cull) {
         com.mojang.blaze3d.systems.RenderSystem.disableCull();
      }

      class_287 local20_8 = class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1576);
      class_289.method_1348().method_60827(class_5596.field_27377, class_290.field_1576);
      local20_8.method_22918(local19_7, local13_1, local14_2, local15_3).method_39415(color);
      local20_8.method_22918(local19_7, local16_4, local17_5, local18_6).method_39415(color);
      class_286.method_43433(local20_8.method_60800());
      if (!cull) {
         com.mojang.blaze3d.systems.RenderSystem.enableCull();
      }

      if (!depth) {
         com.mojang.blaze3d.systems.RenderSystem.enableDepthTest();
      }

      this.disableBlendAndSmoothLines();
      this.cleanupWorldRenderer();
      matrix.method_22909();
   }

   public void drawQuadH(class_4587 matrix, float x1, float y1, float z1, float x2, float y2, float z2, int color, boolean depth, boolean cull) {
      class_243 local11_0 = class_310.method_1551().field_1773.method_19418().method_19326();
      float local12_1 = (float)((double)x1 - local11_0.field_1352);
      float local13_2 = (float)((double)y1 - local11_0.field_1351);
      float local14_3 = (float)((double)z1 - local11_0.field_1350);
      float local15_4 = (float)((double)x2 - local11_0.field_1352);
      float local16_5 = (float)((double)y2 - local11_0.field_1351);
      float local17_6 = (float)((double)z2 - local11_0.field_1350);
      matrix.method_22903();
      Matrix4f local18_7 = matrix.method_23760().method_23761();
      this.setupWorldRenderer();
      if (!depth) {
         com.mojang.blaze3d.systems.RenderSystem.disableDepthTest();
      }

      if (!cull) {
         com.mojang.blaze3d.systems.RenderSystem.disableCull();
      }

      int local19_8 = color & 16777215 | 1509949440;
      class_287 local20_9 = class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1576);
      class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1576);
      local20_9.method_22918(local18_7, local12_1, local13_2, local14_3).method_39415(local19_8);
      local20_9.method_22918(local18_7, local15_4, local13_2, local14_3).method_39415(local19_8);
      local20_9.method_22918(local18_7, local15_4, local16_5, local17_6).method_39415(local19_8);
      local20_9.method_22918(local18_7, local12_1, local16_5, local17_6).method_39415(local19_8);
      class_286.method_43433(local20_9.method_60800());
      if (!cull) {
         com.mojang.blaze3d.systems.RenderSystem.enableCull();
      }

      if (!depth) {
         com.mojang.blaze3d.systems.RenderSystem.enableDepthTest();
      }

      this.cleanupWorldRenderer();
      matrix.method_22909();
   }

   public void drawQuad(class_4587 matrix, float x1, float y1, float z1, float x2, float y2, float z2, int color, boolean depth, boolean cull) {
      class_243 local11_0 = class_310.method_1551().field_1773.method_19418().method_19326();
      float local12_1 = (float)((double)x1 - local11_0.field_1352);
      float local13_2 = (float)((double)y1 - local11_0.field_1351);
      float local14_3 = (float)((double)z1 - local11_0.field_1350);
      float local15_4 = (float)((double)x2 - local11_0.field_1352);
      float local16_5 = (float)((double)y2 - local11_0.field_1351);
      float local17_6 = (float)((double)z2 - local11_0.field_1350);
      matrix.method_22903();
      Matrix4f local18_7 = matrix.method_23760().method_23761();
      this.setupWorldRenderer();
      if (!depth) {
         com.mojang.blaze3d.systems.RenderSystem.disableDepthTest();
      }

      if (!cull) {
         com.mojang.blaze3d.systems.RenderSystem.disableCull();
      }

      int local19_8 = color & 16777215 | 1509949440;
      class_287 local20_9 = class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1576);
      class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1576);
      local20_9.method_22918(local18_7, local12_1, local13_2, local14_3).method_39415(local19_8);
      local20_9.method_22918(local18_7, local12_1, local16_5, local14_3).method_39415(local19_8);
      local20_9.method_22918(local18_7, local15_4, local16_5, local17_6).method_39415(local19_8);
      local20_9.method_22918(local18_7, local15_4, local13_2, local17_6).method_39415(local19_8);
      class_286.method_43433(local20_9.method_60800());
      if (!cull) {
         com.mojang.blaze3d.systems.RenderSystem.enableCull();
      }

      if (!depth) {
         com.mojang.blaze3d.systems.RenderSystem.enableDepthTest();
      }

      this.cleanupWorldRenderer();
      matrix.method_22909();
   }

   private void drawFace(Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2, float[] rgb) {
      class_287 local9_0 = class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1576);
      class_289.method_1348().method_60827(class_5596.field_29345, class_290.field_1576);
      local9_0.method_22918(matrix4f, x1, y1, z1).method_22915(rgb[0], rgb[1], rgb[2], rgb[3]);
      local9_0.method_22918(matrix4f, x2, y1, z1).method_22915(rgb[0], rgb[1], rgb[2], rgb[3]);
      local9_0.method_22918(matrix4f, x2, y1, z2).method_22915(rgb[0], rgb[1], rgb[2], rgb[3]);
      local9_0.method_22918(matrix4f, x1, y1, z2).method_22915(rgb[0], rgb[1], rgb[2], rgb[3]);
      local9_0.method_22918(matrix4f, x1, y1, z1).method_22915(rgb[0], rgb[1], rgb[2], rgb[3]);
      class_286.method_43433(local9_0.method_60800());
   }

   private void drawEdge(Matrix4f matrix4f, float x1, float y1, float z1, float x2, float y2, float z2, float[] rgb) {
      class_287 local9_0 = class_289.method_1348().method_60827(class_5596.field_27382, class_290.field_1576);
      class_289.method_1348().method_60827(class_5596.field_29345, class_290.field_1576);
      local9_0.method_22918(matrix4f, x1, y1, z1).method_22915(rgb[0], rgb[1], rgb[2], rgb[3]);
      local9_0.method_22918(matrix4f, x2, y2, z2).method_22915(rgb[0], rgb[1], rgb[2], rgb[3]);
      class_286.method_43433(local9_0.method_60800());
   }

   public void setupWorldRenderer() {
      com.mojang.blaze3d.systems.RenderSystem.setShader(class_757::method_34540);
      com.mojang.blaze3d.systems.RenderSystem.disableBlend();
      com.mojang.blaze3d.systems.RenderSystem.disableCull();
      setupBlend();
   }

   public void cleanupWorldRenderer() {
      cleanupBlend();
      com.mojang.blaze3d.systems.RenderSystem.enableCull();
      com.mojang.blaze3d.systems.RenderSystem.setShader(class_757::method_34540);
   }

   private static void cleanupBlend() {
      com.mojang.blaze3d.systems.RenderSystem.disableBlend();
      com.mojang.blaze3d.systems.RenderSystem.defaultBlendFunc();
   }

   private static void setupBlend() {
      com.mojang.blaze3d.systems.RenderSystem.enableBlend();
      com.mojang.blaze3d.systems.RenderSystem.blendFunc(class_4535.SRC_ALPHA, class_4534.ONE_MINUS_SRC_ALPHA);
   }

   private void enableBlendAndSmoothLines(float lineWidth) {
      setupBlend();
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glLineWidth(lineWidth);
   }

   private void disableBlendAndSmoothLines() {
      GL11.glDisable(2848);
      GL11.glLineWidth(1.0F);
      cleanupBlend();
   }

   @Generated
   public void setScaling(float scaling) {
      this.scaling = scaling;
   }

   @Generated
   public void setWindow(class_1041 window) {
      this.window = window;
   }

   @Generated
   public void setBlurFramebuffer(class_276 blurFramebuffer) {
      this.blurFramebuffer = blurFramebuffer;
   }

   @Generated
   public float getScaling() {
      return this.scaling;
   }

   @Generated
   public float getAlphaModifier() {
      return this.alphaModifier;
   }

   @Generated
   public Map<String, ShaderProgram> getCachedShaders() {
      return this.cachedShaders;
   }

   @Generated
   public class_310 getMc() {
      return this.mc;
   }

   @Generated
   public class_1041 getWindow() {
      return this.window;
   }

   @Generated
   public Map<class_2960, class_2960> getSanitizedTextureCache() {
      return this.sanitizedTextureCache;
   }

   @Generated
   public class_276 getBlurFramebuffer() {
      return this.blurFramebuffer;
   }
}
