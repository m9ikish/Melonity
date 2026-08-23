package ru.melonity.feature.features.modules.render.hud;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import org.jetbrains.annotations.NotNull;
import ru.melonity.ui.components.view.View;
import ru.melonity.util.animation.AnimationUtil;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.render.RenderSystem;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\t\u001a\u00020\b¢\u0006\u0004\b\n\u0010\u000bB!\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\n\u0010\fJ\u0015\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u000e\u0010\u000fJ\u0015\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\u0010\u0010\u0011J?\u0010\u001c\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001aH\u0016¢\u0006\u0004\b\u001c\u0010\u001dJ7\u0010 \u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020\u001eH\u0016¢\u0006\u0004\b \u0010!J'\u0010%\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020\u001eH\u0016¢\u0006\u0004\b%\u0010&J\u000f\u0010'\u001a\u00020\rH\u0016¢\u0006\u0004\b'\u0010(R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010)R\u0016\u0010\u0005\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0005\u0010*R\u0016\u0010\u0007\u001a\u00020\u00068\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0007\u0010+R\u0014\u0010\t\u001a\u00020\b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\t\u0010,R\u0016\u0010-\u001a\u00020\u00128\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b-\u0010.R\u0016\u0010/\u001a\u00020\b8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b/\u0010,R\u001c\u00102\u001a\n 1*\u0004\u0018\u000100008\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b2\u00103¨\u00064"},
   d2 = {"Lru/melonity/feature/features/modules/render/hud/BarView;", "Lru/melonity/ui/components/view/View;", "Lru/melonity/feature/features/modules/render/hud/BarConsumer;", "consumer", "Lnet/minecraft/class_2960;", "icon", "Ljava/awt/Color;", "primaryColor", "", "golden", "<init>", "(Lru/melonity/feature/features/modules/render/hud/BarConsumer;Lnet/minecraft/class_2960;Ljava/awt/Color;Z)V", "(Lru/melonity/feature/features/modules/render/hud/BarConsumer;Lnet/minecraft/class_2960;Ljava/awt/Color;)V", "", "setIcon", "(Lnet/minecraft/class_2960;)V", "setPrimaryColor", "(Ljava/awt/Color;)V", "", "x", "y", "", "mouseX", "mouseY", "Lru/melonity/util/render/RenderSystem;", "renderSystem", "Lnet/minecraft/class_4587;", "poseStack", "insert", "(FFDDLru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "", "mouseButton", "click", "(FFDDI)V", "keyCode", "scanCode", "modifiers", "key", "(III)Z", "release", "()V", "Lru/melonity/feature/features/modules/render/hud/BarConsumer;", "Lnet/minecraft/class_2960;", "Ljava/awt/Color;", "Z", "currentProgress", "F", "init", "Lru/melonity/util/animation/FrameWeightCalculator;", "kotlin.jvm.PlatformType", "fwc", "Lru/melonity/util/animation/FrameWeightCalculator;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class BarView extends View {
   @NotNull
   private final BarConsumer consumer;
   @NotNull
   private class_2960 icon;
   @NotNull
   private Color primaryColor;
   private final boolean golden;
   private float currentProgress;
   private boolean init;
   private final FrameWeightCalculator fwc;

   public BarView(@NotNull BarConsumer consumer, @NotNull class_2960 icon, @NotNull Color primaryColor, boolean golden) {
      super(71.0F, 10.0F);
      Intrinsics.checkNotNullParameter(consumer, "consumer");
      Intrinsics.checkNotNullParameter(icon, "icon");
      Intrinsics.checkNotNullParameter(primaryColor, "primaryColor");
      this.consumer = consumer;
      this.icon = icon;
      this.primaryColor = primaryColor;
      this.golden = golden;
      this.fwc = FrameWeightCalculator.milliseconds(250L);
   }

   public BarView(@NotNull BarConsumer consumer, @NotNull class_2960 icon, @NotNull Color primaryColor) {
      this(consumer, icon, primaryColor, false);
      Intrinsics.checkNotNullParameter(consumer, "consumer");
      Intrinsics.checkNotNullParameter(icon, "icon");
      Intrinsics.checkNotNullParameter(primaryColor, "primaryColor");
   }

   public final void setIcon(@NotNull class_2960 icon) {
      Intrinsics.checkNotNullParameter(icon, "icon");
      this.icon = icon;
   }

   public final void setPrimaryColor(@NotNull Color primaryColor) {
      Intrinsics.checkNotNullParameter(primaryColor, "primaryColor");
      this.primaryColor = primaryColor;
   }

   @Override
   public void insert(float x, float y, double mouseX, double mouseY, @NotNull RenderSystem renderSystem, @NotNull class_4587 poseStack) {
      Intrinsics.checkNotNullParameter(renderSystem, "renderSystem");
      Intrinsics.checkNotNullParameter(poseStack, "poseStack");
      float local9_0 = this.consumer.get();
      if (!this.init) {
         this.currentProgress = local9_0;
         this.init = true;
      }

      this.currentProgress = AnimationUtil.smoothAnimate(local9_0, this.currentProgress, 20.0F);
      this.currentProgress = RangesKt.coerceIn(this.currentProgress, 0.0F, 1.0F);
      float local10_1 = this.currentProgress * (this.getWidth() - 2.0F);
      renderSystem.addTexture(this.icon, x, y, 9.0F, 9.0F, poseStack);
      float local11_2 = this.getHeight() / 2.0F;
      float local12_3 = (this.getHeight() - 1.0F) / 2.0F;
      float local13_4 = (this.getHeight() - 2.0F) / 2.0F;
      renderSystem.addRect(x + 12.0F, y, this.getWidth(), this.getHeight(), local11_2, this.primaryColor, poseStack);
      renderSystem.addRect(x + 12.0F + 0.5F, y + 0.5F, this.getWidth() - 1.0F, this.getHeight() - 1.0F, local12_3, Color.decode("#141414"), poseStack);
      renderSystem.addRect(x + 12.0F + 1.0F, y + 1.0F, local10_1, this.getHeight() - 2.0F, local13_4, this.primaryColor, poseStack);
      if (this.golden) {
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/hotbar/golden_effect.png"), x + 12.0F + 1.0F, y + 1.0F, local10_1, this.getHeight() - 2.0F, poseStack
         );
      }
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public void release() {
   }
}
