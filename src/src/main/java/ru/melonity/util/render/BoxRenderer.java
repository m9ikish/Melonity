package ru.melonity.util.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_310;
import net.minecraft.class_5944;
import net.minecraft.class_757;
import net.minecraft.class_293.class_5596;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J/\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH\u0007¢\u0006\u0004\b\r\u0010\u000eJ'\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\b¢\u0006\u0004\b\u0012\u0010\u0013J/\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0015\u001a\u00020\u0014¢\u0006\u0004\b\u0016\u0010\u0017J/\u0010\u001a\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0019\u001a\u00020\u0018¢\u0006\u0004\b\u001a\u0010\u001bJ)\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0007¢\u0006\u0004\b\u001d\u0010\u0013J9\u0010\u001e\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u0018H\u0002¢\u0006\u0004\b\u001e\u0010\u001f¨\u0006 "},
   d2 = {"Lru/melonity/util/render/BoxRenderer;", "", "<init>", "()V", "Lnet/minecraft/class_2338;", "blockPos", "Ljava/awt/Color;", "c", "Lorg/joml/Matrix4f;", "matrixStack", "", "offset", "", "boxWOffset", "(Lnet/minecraft/class_2338;Ljava/awt/Color;Lorg/joml/Matrix4f;D)V", "Lnet/minecraft/class_238;", "boundingBox", "matrix4f", "drawSelectionBoundingBoxGlow", "(Lnet/minecraft/class_238;Ljava/awt/Color;Lorg/joml/Matrix4f;)V", "", "alpha", "drawSelectionBoundingBox", "(Lnet/minecraft/class_238;Ljava/awt/Color;Lorg/joml/Matrix4f;F)V", "Lnet/minecraft/class_287;", "bufferBuilder", "drawSelectionBoundingBoxGlowBatched", "(Lnet/minecraft/class_2338;Ljava/awt/Color;Lorg/joml/Matrix4f;Lnet/minecraft/class_287;)V", "box", "blockEsp", "drawSelectionBoundingBoxBatched", "(Lnet/minecraft/class_238;Ljava/awt/Color;Lorg/joml/Matrix4f;FLnet/minecraft/class_287;)V", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class BoxRenderer {
   @NotNull
   public static final BoxRenderer INSTANCE = new BoxRenderer();

   private BoxRenderer() {
   }

   @JvmStatic
   public static final void boxWOffset(@NotNull class_2338 blockPos, @NotNull Color c, @NotNull Matrix4f matrixStack, double offset) {
      Intrinsics.checkNotNullParameter(blockPos, "blockPos");
      Intrinsics.checkNotNullParameter(c, "c");
      Intrinsics.checkNotNullParameter(matrixStack, "matrixStack");
      double local5_0 = (double)blockPos.method_10263() - class_310.method_1551().field_1773.method_19418().method_19326().field_1352;
      double local7_1 = (double)blockPos.method_10264() - class_310.method_1551().field_1773.method_19418().method_19326().field_1351;
      double local9_2 = (double)blockPos.method_10260() - class_310.method_1551().field_1773.method_19418().method_19326().field_1350;
      INSTANCE.drawSelectionBoundingBoxGlow(
         new class_238(local5_0, local7_1, local9_2, local5_0 + offset, local7_1 + offset, local9_2 + offset), c, matrixStack
      );
   }

   public final void drawSelectionBoundingBoxGlow(@NotNull class_238 boundingBox, @NotNull Color c, @Nullable Matrix4f matrix4f) {
      Intrinsics.checkNotNullParameter(boundingBox, "boundingBox");
      Intrinsics.checkNotNullParameter(c, "c");

      for (float local4_1 = 0.025F; local4_1 <= 0.25F; local4_1 += 0.05F) {
         float local5_0 = 0.1F * (1.0F - (local4_1 - 0.05F));
         local5_0 = Math.max(local5_0, 0.01F);
         class_238 var10001 = boundingBox.method_1014((double)local4_1);
         Intrinsics.checkNotNullExpressionValue(var10001, "expand(...)");
         this.drawSelectionBoundingBox(var10001, c, matrix4f, (float)c.getAlpha() / 255.0F * local5_0 * 0.5F);
      }

      this.drawSelectionBoundingBox(boundingBox, c, matrix4f, (float)c.getAlpha() / 255.0F * 0.3F);
   }

   public final void drawSelectionBoundingBox(@NotNull class_238 boundingBox, @NotNull Color c, @Nullable Matrix4f matrix4f, float alpha) {
      Intrinsics.checkNotNullParameter(boundingBox, "boundingBox");
      Intrinsics.checkNotNullParameter(c, "c");
      com.mojang.blaze3d.systems.RenderSystem.enableBlend();
      com.mojang.blaze3d.systems.RenderSystem.defaultBlendFunc();
      com.mojang.blaze3d.systems.RenderSystem.disableDepthTest();
      float local5_0 = -0.005F;
      float local6_1 = (float)boundingBox.field_1323 + local5_0;
      float local7_2 = (float)boundingBox.field_1322 + local5_0;
      float local8_3 = (float)boundingBox.field_1321 + local5_0;
      float local9_4 = (float)boundingBox.field_1320 - local5_0;
      float local10_5 = (float)boundingBox.field_1325 - local5_0;
      float local11_6 = (float)boundingBox.field_1324 - local5_0;
      com.mojang.blaze3d.systems.RenderSystem.setShader(BoxRenderer::drawSelectionBoundingBox$lambda$0);
      class_289 local12_7 = class_289.method_1348();
      class_287 local13_8 = local12_7.method_60827(class_5596.field_27382, class_290.field_1576);
      local13_8.method_22918(matrix4f, local6_1, local7_2, local8_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local6_1, local7_2, local11_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local9_4, local7_2, local11_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local9_4, local7_2, local8_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      class_286.method_43433(local13_8.method_60800());
      local13_8 = local12_7.method_60827(class_5596.field_27382, class_290.field_1576);
      local13_8.method_22918(matrix4f, local6_1, local10_5, local8_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local9_4, local10_5, local8_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local9_4, local10_5, local11_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local6_1, local10_5, local11_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      class_286.method_43433(local13_8.method_60800());
      local13_8 = local12_7.method_60827(class_5596.field_27382, class_290.field_1576);
      local13_8.method_22918(matrix4f, local6_1, local7_2, local8_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local6_1, local10_5, local8_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local6_1, local10_5, local11_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local6_1, local7_2, local11_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      class_286.method_43433(local13_8.method_60800());
      local13_8 = local12_7.method_60827(class_5596.field_27382, class_290.field_1576);
      local13_8.method_22918(matrix4f, local9_4, local7_2, local8_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local9_4, local7_2, local11_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local9_4, local10_5, local11_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local9_4, local10_5, local8_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      class_286.method_43433(local13_8.method_60800());
      local13_8 = local12_7.method_60827(class_5596.field_27382, class_290.field_1576);
      local13_8.method_22918(matrix4f, local6_1, local7_2, local8_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local9_4, local7_2, local8_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local9_4, local10_5, local8_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local6_1, local10_5, local8_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      class_286.method_43433(local13_8.method_60800());
      local13_8 = local12_7.method_60827(class_5596.field_27382, class_290.field_1576);
      local13_8.method_22918(matrix4f, local6_1, local7_2, local11_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local6_1, local10_5, local11_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local9_4, local10_5, local11_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      local13_8.method_22918(matrix4f, local9_4, local7_2, local11_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      class_286.method_43433(local13_8.method_60800());
      com.mojang.blaze3d.systems.RenderSystem.disableBlend();
      com.mojang.blaze3d.systems.RenderSystem.enableDepthTest();
   }

   public final void drawSelectionBoundingBoxGlowBatched(
      @NotNull class_2338 blockPos, @NotNull Color c, @Nullable Matrix4f matrix4f, @NotNull class_287 bufferBuilder
   ) {
      Intrinsics.checkNotNullParameter(blockPos, "blockPos");
      Intrinsics.checkNotNullParameter(c, "c");
      Intrinsics.checkNotNullParameter(bufferBuilder, "bufferBuilder");
      double local5_1 = (double)blockPos.method_10263() - class_310.method_1551().field_1773.method_19418().method_19326().field_1352;
      double local7_2 = (double)blockPos.method_10264() - class_310.method_1551().field_1773.method_19418().method_19326().field_1351;
      double local9_3 = (double)blockPos.method_10260() - class_310.method_1551().field_1773.method_19418().method_19326().field_1350;
      class_238 local11_4 = new class_238(local5_1, local7_2, local9_3, local5_1 + 1.0, local7_2 + 1.0, local9_3 + 1.0);

      for (float local12_5 = 0.05F; local12_5 <= 0.15F; local12_5 += 0.05F) {
         float local13_0 = 0.1F * (1.0F - (local12_5 - 0.05F));
         local13_0 = Math.max(local13_0, 0.01F);
         class_238 var10001 = local11_4.method_1014((double)local12_5);
         Intrinsics.checkNotNullExpressionValue(var10001, "expand(...)");
         this.drawSelectionBoundingBoxBatched(var10001, c, matrix4f, local13_0 * 0.5F, bufferBuilder);
      }

      this.drawSelectionBoundingBoxBatched(local11_4, c, matrix4f, 0.3F, bufferBuilder);
   }

   @JvmStatic
   public static final void blockEsp(@NotNull class_238 box, @NotNull Color c, @Nullable Matrix4f matrixStack) {
      Intrinsics.checkNotNullParameter(box, "box");
      Intrinsics.checkNotNullParameter(c, "c");
      INSTANCE.drawSelectionBoundingBox(box, c, matrixStack, 0.2F);
   }

   private final void drawSelectionBoundingBoxBatched(class_238 boundingBox, Color c, Matrix4f matrix4f, float alpha, class_287 bufferBuilder) {
      float local6_0 = -0.005F;
      float local7_1 = (float)boundingBox.field_1323 + local6_0;
      float local8_2 = (float)boundingBox.field_1322 + local6_0;
      float local9_3 = (float)boundingBox.field_1321 + local6_0;
      float local10_4 = (float)boundingBox.field_1320 - local6_0;
      float local11_5 = (float)boundingBox.field_1325 - local6_0;
      float local12_6 = (float)boundingBox.field_1324 - local6_0;
      bufferBuilder.method_22918(matrix4f, local7_1, local8_2, local9_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local7_1, local8_2, local12_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local10_4, local8_2, local12_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local10_4, local8_2, local9_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local7_1, local11_5, local9_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local10_4, local11_5, local9_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local10_4, local11_5, local12_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local7_1, local11_5, local12_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local7_1, local8_2, local9_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local7_1, local11_5, local9_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local7_1, local11_5, local12_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local7_1, local8_2, local12_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local10_4, local8_2, local9_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local10_4, local8_2, local12_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local10_4, local11_5, local12_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local10_4, local11_5, local9_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local7_1, local8_2, local9_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local10_4, local8_2, local9_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local10_4, local11_5, local9_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local7_1, local11_5, local9_3)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local7_1, local8_2, local12_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local7_1, local11_5, local12_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local10_4, local11_5, local12_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
      bufferBuilder.method_22918(matrix4f, local10_4, local8_2, local12_6)
         .method_22915((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, alpha);
   }

   private static final class_5944 drawSelectionBoundingBox$lambda$0() {
      return class_757.method_34540();
   }
}
