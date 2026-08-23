package ru.melonity.fabric.client.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Optional;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_156;
import net.minecraft.class_1921;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4011;
import net.minecraft.class_425;
import net.minecraft.class_4587;
import net.minecraft.class_757;
import net.minecraft.class_293.class_5596;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.util.render.OpenGLUtil;

@Environment(EnvType.CLIENT)
@Mixin({class_425.class})
public class SplashOverlayMixin {
   @Shadow
   @Final
   private class_310 field_18217;
   @Shadow
   @Final
   private class_4011 field_17767;
   @Shadow
   @Final
   private Consumer<Optional<Throwable>> field_18218;
   @Shadow
   @Final
   private boolean field_18219;
   @Shadow
   private float field_17770;
   @Shadow
   private long field_17771 = -1L;
   @Shadow
   private long field_18220 = -1L;

   @Shadow
   private static int method_35732(int color, int alpha) {
      return 0;
   }

   @Shadow
   private void method_18103(class_332 drawContext, int minX, int minY, int maxX, int maxY, float opacity) {
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void render(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
      int i = context.method_51421();
      int j = context.method_51443();
      long l = class_156.method_658();
      if (this.field_18219 && this.field_18220 == -1L) {
         this.field_18220 = l;
      }

      float f = this.field_17771 > -1L ? (float)(l - this.field_17771) / 1000.0F : -1.0F;
      float g = this.field_18220 > -1L ? (float)(l - this.field_18220) / 500.0F : -1.0F;
      float h;
      if (f >= 1.0F) {
         if (this.field_18217.field_1755 != null) {
            this.field_18217.field_1755.method_25394(context, 0, 0, delta);
         }

         int kk = class_3532.method_15386((1.0F - class_3532.method_15363(f - 1.0F, 0.0F, 1.0F)) * 255.0F);
         context.method_51739(class_1921.method_51785(), 0, 0, i, j, method_35732(new Color(458773).getRGB(), kk));
         h = 1.0F - class_3532.method_15363(f - 1.0F, 0.0F, 1.0F);
      } else if (this.field_18219) {
         if (this.field_18217.field_1755 != null && g < 1.0F) {
            this.field_18217.field_1755.method_25394(context, mouseX, mouseY, delta);
         }

         int kk = class_3532.method_15384(class_3532.method_15350((double)g, 0.15, 1.0) * 255.0);
         context.method_51739(class_1921.method_51785(), 0, 0, i, j, method_35732(new Color(458773).getRGB(), kk));
         h = class_3532.method_15363(g, 0.0F, 1.0F);
      } else {
         int kk = new Color(458773).getRGB();
         float m = (float)(kk >> 16 & 0xFF) / 255.0F;
         float n = (float)(kk >> 8 & 0xFF) / 255.0F;
         float o = (float)(kk & 0xFF) / 255.0F;
         GlStateManager._clearColor(m, n, o, 1.0F);
         GlStateManager._clear(16384, class_310.field_1703);
         h = 1.0F;
      }

      int k = (int)((double)context.method_51421() * 0.5);
      int p = (int)((double)context.method_51443() * 0.5);
      double d = Math.min((double)context.method_51421() * 0.75, (double)context.method_51443()) * 0.25;
      int q = (int)(d * 0.5);
      double e = d * 4.0;
      int r = (int)(e * 0.5);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(770, 1);
      int s = (int)((double)context.method_51443() * 0.8325);
      float t = this.field_17767.method_18229();
      this.field_17770 = class_3532.method_15363(this.field_17770 * 0.95F + t * 0.050000012F, 0.0F, 1.0F);
      class_310 mc = this.field_18217;
      GlStateManager._clearColor(
         (float)Color.decode("#141414").getRed() / 255.0F,
         (float)Color.decode("#141414").getGreen() / 255.0F,
         (float)Color.decode("#141414").getBlue() / 255.0F,
         1.0F
      );
      GlStateManager._clear(16384, class_310.field_1703);
      context.method_51422(1.0F, 1.0F, 1.0F, h);
      this.addTexture(
         class_2960.method_60656("melonity/images/loading.png"),
         (float)mc.method_22683().method_4486() / 2.0F - 37.0F,
         (float)mc.method_22683().method_4502() / 2.0F - 41.0F,
         74.0F,
         82.0F,
         new Color(1.0F, 1.0F, 1.0F, 0.1F),
         context.method_51448()
      );
      this.addTexture(
         class_2960.method_60656("melonity/images/loading.png"),
         (float)mc.method_22683().method_4486() / 2.0F - 37.0F,
         (float)mc.method_22683().method_4502() / 2.0F - 41.0F,
         74.0F,
         82.0F,
         new Color(1.0F, 1.0F, 1.0F, h),
         context.method_51448()
      );
      context.method_51422(1.0F, 1.0F, 1.0F, 1.0F);
      if (f < 1.0F) {
         this.method_18103(context, i / 2 - r, s - 5, i / 2 + r, s + 5, 1.0F - class_3532.method_15363(f, 0.0F, 1.0F));
      }

      if (f >= 2.0F) {
         this.field_18217.method_18502(null);
      }

      if (this.field_17771 == -1L && this.field_17767.method_18787() && (!this.field_18219 || g >= 2.0F)) {
         try {
            this.field_17767.method_18849();
            this.field_18218.accept(Optional.empty());
         } catch (Throwable var26) {
            this.field_18218.accept(Optional.of(var26));
         }

         this.field_17771 = class_156.method_658();
         if (this.field_18217.field_1755 != null) {
            this.field_18217.field_1755.method_25423(this.field_18217, context.method_51421(), context.method_51443());
         }
      }

      callbackInfo.cancel();
   }

   @Unique
   private void addTexture(class_2960 path, float x, float y, float width, float height, Color color, class_4587 poseStack) {
      OpenGLUtil.blend(() -> {
         RenderSystem.setShader(class_757::method_34543);
         RenderSystem.setShaderTexture(0, class_310.method_1551().method_1531().method_4619(path).method_4624());
         class_289 tessellator = class_289.method_1348();
         class_287 bufferBuilder = tessellator.method_60827(class_5596.field_27382, class_290.field_1575);
         bufferBuilder.method_22918(poseStack.method_23760().method_23761(), x, y, 0.0F).method_39415(color.getRGB()).method_22913(0.0F, 0.0F);
         bufferBuilder.method_22918(poseStack.method_23760().method_23761(), x, y + height, 0.0F).method_39415(color.getRGB()).method_22913(0.0F, 1.0F);
         bufferBuilder.method_22918(poseStack.method_23760().method_23761(), x + width, y + height, 0.0F).method_39415(color.getRGB()).method_22913(1.0F, 1.0F);
         bufferBuilder.method_22918(poseStack.method_23760().method_23761(), x + width, y, 0.0F).method_39415(color.getRGB()).method_22913(1.0F, 0.0F);
         class_286.method_43433(bufferBuilder.method_60800());
      });
   }
}
