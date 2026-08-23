package ru.melonity.feature.features.modules.render.hud;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.HudFeature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.View;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0002+,B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\u0006\u001a\u00020\u0002¢\u0006\u0004\b\u0006\u0010\u0007J?\u0010\u0012\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016¢\u0006\u0004\b\u0012\u0010\u0013J7\u0010\u0016\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0014H\u0016¢\u0006\u0004\b\u0016\u0010\u0017J'\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u0014H\u0016¢\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001e\u001a\u00020\u0011H\u0016¢\u0006\u0004\b\u001e\u0010\u001fR\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020!0 8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\"\u0010#R\u0014\u0010%\u001a\u00020$8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b%\u0010&R\"\u0010'\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b'\u0010(\u001a\u0004\b)\u0010\u0007\"\u0004\b*\u0010\u0005¨\u0006-"},
   d2 = {"Lru/melonity/feature/features/modules/render/hud/WatermarkView;", "Lru/melonity/ui/components/view/View;", "", "height", "<init>", "(F)V", "measureWidth", "()F", "x", "y", "", "mouseX", "mouseY", "Lru/melonity/util/render/RenderSystem;", "renderSystem", "Lnet/minecraft/class_4587;", "poseStack", "", "insert", "(FFDDLru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "", "mouseButton", "click", "(FFDDI)V", "keyCode", "scanCode", "modifiers", "", "key", "(III)Z", "release", "()V", "", "Lru/melonity/feature/features/modules/render/hud/WatermarkView$MiniView;", "subviews", "Ljava/util/List;", "Lru/melonity/feature/features/modules/render/hud/WatermarkState;", "state", "Lru/melonity/feature/features/modules/render/hud/WatermarkState;", "targetFps", "F", "getTargetFps", "setTargetFps", "Stringer", "MiniView", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class WatermarkView extends View {
   @NotNull
   private final List<WatermarkView.MiniView> subviews;
   @NotNull
   private final WatermarkState state;
   private float targetFps;

   public WatermarkView(float height) {
      super(1.0F, height);
      WatermarkView.MiniView[] var2 = new WatermarkView.MiniView[]{
         new WatermarkView.MiniView("fps.png", () -> subviews$lambda$0(this)), new WatermarkView.MiniView("clock.png", WatermarkView::subviews$lambda$1)
      };
      this.subviews = CollectionsKt.listOf(var2);
      this.state = new WatermarkState();
   }

   public final float getTargetFps() {
      return this.targetFps;
   }

   public final void setTargetFps(float value) {
      this.targetFps = value;
   }

   public final float measureWidth() {
      return WatermarkLayout.totalWidth(this.subviews);
   }

   @Override
   public void insert(float x, float y, double mouseX, double mouseY, @NotNull RenderSystem renderSystem, @NotNull class_4587 poseStack) {
      Intrinsics.checkNotNullParameter(renderSystem, "renderSystem");
      Intrinsics.checkNotNullParameter(poseStack, "poseStack");
      MenuStyle var10000 = Melonity.INSTANCE.styleManager.style;
      Intrinsics.checkNotNullExpressionValue(Melonity.INSTANCE.styleManager.style, "style");
      MenuStyle local9_1 = var10000;
      HudFeature local10_2 = Melonity.INSTANCE.featureManager.hudFeature;
      this.state.updateFps(this.targetFps);
      this.setWidth(this.measureWidth());
      ScaleUtils.rescale(2.0);
      renderSystem.addRect(x, y, this.getWidth(), this.getHeight(), 6.0F, local9_1.background(), poseStack);
      renderSystem.addTexture(
         class_2960.method_60656("melonity/images/ui/watermark/overlay_blocks.png"),
         x + 0.3F,
         y + this.getHeight() - 3.0F,
         this.getWidth() - 0.6F,
         7.5F,
         local9_1.background(),
         poseStack
      );
      renderSystem.addTexture(class_2960.method_60656("melonity/images/ui/watermark/logo.png"), x + 3.5F, y + 3.5F, 9.5F, 10.5F, local9_1.ascent(), poseStack);
      float local11_3 = x + 18.0F;

      for (WatermarkView.MiniView local13_0 : this.subviews) {
         local13_0.insert(local11_3, y + 3.0F, mouseX, mouseY, renderSystem, poseStack);
         local11_3 += local13_0.getWidth() + 1.0F;
      }

      ScaleUtils.rescaleMC();
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

   private static final String subviews$lambda$0(WatermarkView this$0) {
      String var1 = "%.0f FPS";
      Object[] var2 = new Object[]{this$0.state.getCurrentFps()};
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String subviews$lambda$1() {
      Calendar local0_0 = Calendar.getInstance();
      int local1_1 = local0_0.get(11);
      int local2_2 = local0_0.get(12);
      String var3 = "%02d:%02d";
      Object[] var4 = new Object[]{local1_1, local2_2};
      String var10000 = String.format(var3, Arrays.copyOf(var4, var4.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\r\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J=\u0010\u0013\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0010¢\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\u0002HÆ\u0003¢\u0006\u0004\b\u0015\u0010\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004HÆ\u0003¢\u0006\u0004\b\u0017\u0010\u0018J*\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00022\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004HÆ\u0001¢\u0006\u0004\b\u0019\u0010\u001aJ\u001a\u0010\u001d\u001a\u00020\u001c2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u001d\u0010\u001eJ\u0010\u0010 \u001a\u00020\u001fHÖ\u0001¢\u0006\u0004\b \u0010!J\u0010\u0010\"\u001a\u00020\u0002HÖ\u0001¢\u0006\u0004\b\"\u0010\u0016R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010#\u001a\u0004\b$\u0010\u0016R\u001d\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010%\u001a\u0004\b&\u0010\u0018R\u0011\u0010)\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0011\u0010+\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b*\u0010(¨\u0006,"},
      d2 = {"Lru/melonity/feature/features/modules/render/hud/WatermarkView$MiniView;", "", "", "title", "Lru/melonity/feature/features/modules/render/hud/WatermarkView$Stringer;", "consumer", "<init>", "(Ljava/lang/String;Lru/melonity/feature/features/modules/render/hud/WatermarkView$Stringer;)V", "", "x", "y", "", "mouseX", "mouseY", "Lru/melonity/util/render/RenderSystem;", "renderSystem", "Lnet/minecraft/class_4587;", "poseStack", "", "insert", "(FFDDLru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "component1", "()Ljava/lang/String;", "component2", "()Lru/melonity/feature/features/modules/render/hud/WatermarkView$Stringer;", "copy", "(Ljava/lang/String;Lru/melonity/feature/features/modules/render/hud/WatermarkView$Stringer;)Lru/melonity/feature/features/modules/render/hud/WatermarkView$MiniView;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Ljava/lang/String;", "getTitle", "Lru/melonity/feature/features/modules/render/hud/WatermarkView$Stringer;", "getConsumer", "getWidth", "()F", "width", "getHeight", "height", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   public static final class MiniView {
      @NotNull
      private final String title;
      @NotNull
      private final WatermarkView.Stringer<String> consumer;

      public MiniView(@NotNull String title, @NotNull WatermarkView.Stringer<String> consumer) {
         Intrinsics.checkNotNullParameter(title, "title");
         Intrinsics.checkNotNullParameter(consumer, "consumer");
         this.title = title;
         this.consumer = consumer;
      }

      @NotNull
      public final String getTitle() {
         return this.title;
      }

      @NotNull
      public final WatermarkView.Stringer<String> getConsumer() {
         return this.consumer;
      }

      public final float getWidth() {
         return 50.0F;
      }

      public final float getHeight() {
         return 11.5F;
      }

      public final void insert(float x, float y, double mouseX, double mouseY, @NotNull RenderSystem renderSystem, @NotNull class_4587 poseStack) {
         Intrinsics.checkNotNullParameter(renderSystem, "renderSystem");
         Intrinsics.checkNotNullParameter(poseStack, "poseStack");
         MenuStyle var10000 = Melonity.INSTANCE.styleManager.style;
         Intrinsics.checkNotNullExpressionValue(Melonity.INSTANCE.styleManager.style, "style");
         MenuStyle local9_0 = var10000;
         HudFeature local10_1 = Melonity.INSTANCE.featureManager.hudFeature;
         float local11_2 = this.getWidth();
         float local12_3 = this.getHeight();
         renderSystem.addRect(x, y, local11_2, local12_3, 4.0F, local9_0.background().brighter(), poseStack);
         renderSystem.addRect(x + 12.0F, y, 1.0F, local12_3, local9_0.background(), poseStack);
         String local14_5 = "melonity/images/ui/watermark/mini/%s";
         Object[] var15 = new Object[]{this.title};
         String var10001 = String.format(local14_5, Arrays.copyOf(var15, var15.length));
         Intrinsics.checkNotNullExpressionValue(var10001, "format(...)");
         renderSystem.addTexture(class_2960.method_60656(var10001), x + 2.5F, y + local12_3 / 2.0F - 3.75F, 7.5F, 7.5F, local9_0.ascent(), poseStack);
         GlyphData var17 = Fonts.INTER_MEDIUM_12;
         Intrinsics.checkNotNullExpressionValue(Fonts.INTER_MEDIUM_12, "INTER_MEDIUM_12");
         GlyphData local13_4 = var17;
         local14_5 = this.consumer.get();
         renderSystem.addText(local13_4, local14_5, x + 5.0F + local11_2 / 2.0F - local13_4.getStringWidth(local14_5) / 2.0F, y + 5.0F, poseStack);
      }

      @NotNull
      public final String component1() {
         return this.title;
      }

      @NotNull
      public final WatermarkView.Stringer<String> component2() {
         return this.consumer;
      }

      @NotNull
      public final WatermarkView.MiniView copy(@NotNull String title, @NotNull WatermarkView.Stringer<String> consumer) {
         Intrinsics.checkNotNullParameter(title, "title");
         Intrinsics.checkNotNullParameter(consumer, "consumer");
         return new WatermarkView.MiniView(title, consumer);
      }

      @NotNull
      @Override
      public String toString() {
         return "MiniView(title=" + this.title + ", consumer=" + this.consumer + ")";
      }

      @Override
      public int hashCode() {
         int local1_0 = this.title.hashCode();
         return local1_0 * 31 + this.consumer.hashCode();
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof WatermarkView.MiniView var2)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.title, var2.title) ? false : Intrinsics.areEqual(this.consumer, var2.consumer);
         }
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\bæ\u0080\u0001\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u000f\u0010\u0003\u001a\u00028\u0000H&¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005À\u0006\u0003"},
      d2 = {"Lru/melonity/feature/features/modules/render/hud/WatermarkView$Stringer;", "T", "", "get", "()Ljava/lang/Object;", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   public interface Stringer<T> {
      T get();
   }
}
