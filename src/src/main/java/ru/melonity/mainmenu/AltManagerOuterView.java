package ru.melonity.mainmenu;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.View;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0006\u0010\u0007J?\u0010\u0013\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0010H\u0016¢\u0006\u0004\b\u0013\u0010\u0014J7\u0010\u0017\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0015H\u0016¢\u0006\u0004\b\u0017\u0010\u0018J'\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0015H\u0016¢\u0006\u0004\b\u001d\u0010\u001eJ\u000f\u0010\u001f\u001a\u00020\u0012H\u0016¢\u0006\u0004\b\u001f\u0010 R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010!R\u0014\u0010\u0004\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0004\u0010!R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010!¨\u0006\""},
   d2 = {"Lru/melonity/mainmenu/AltManagerOuterView;", "Lru/melonity/ui/components/view/View;", "Ljava/lang/Runnable;", "cancelRunnable", "continueRunnable", "randomRunnable", "<init>", "(Ljava/lang/Runnable;Ljava/lang/Runnable;Ljava/lang/Runnable;)V", "", "x", "y", "", "mouseX", "mouseY", "Lru/melonity/util/render/RenderSystem;", "renderSystem", "Lnet/minecraft/class_4587;", "poseStack", "", "insert", "(FFDDLru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "", "mouseButton", "click", "(FFDDI)V", "keyCode", "scanCode", "modifiers", "", "key", "(III)Z", "release", "()V", "Ljava/lang/Runnable;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class AltManagerOuterView extends View {
   @NotNull
   private final Runnable cancelRunnable;
   @NotNull
   private final Runnable continueRunnable;
   @Nullable
   private final Runnable randomRunnable;

   public AltManagerOuterView(@NotNull Runnable cancelRunnable, @NotNull Runnable continueRunnable, @Nullable Runnable randomRunnable) {
      super(202.0F, 42.0F);
      Intrinsics.checkNotNullParameter(cancelRunnable, "cancelRunnable");
      Intrinsics.checkNotNullParameter(continueRunnable, "continueRunnable");
      this.cancelRunnable = cancelRunnable;
      this.continueRunnable = continueRunnable;
      this.randomRunnable = randomRunnable;
   }

   @Override
   public void insert(float x, float y, double mouseX, double mouseY, @NotNull RenderSystem renderSystem, @NotNull class_4587 poseStack) {
      Intrinsics.checkNotNullParameter(renderSystem, "renderSystem");
      Intrinsics.checkNotNullParameter(poseStack, "poseStack");
      GlyphData var10000 = Fonts.INTER_REGULAR_13;
      Intrinsics.checkNotNullExpressionValue(Fonts.INTER_REGULAR_13, "INTER_REGULAR_13");
      GlyphData local9_1 = var10000;
      MenuStyle var14 = Melonity.INSTANCE.styleManager.style;
      Intrinsics.checkNotNullExpressionValue(Melonity.INSTANCE.styleManager.style, "style");
      MenuStyle local10_2 = var14;
      boolean local11_3 = MathUtil.isMouseHoveringRect(
         (double)(x + this.getWidth() - 13.0F - 71.0F), (double)(y + this.getHeight() - 12.0F - 15.0F), 30.0, 15.0, mouseX, mouseY
      );
      boolean local12_4 = MathUtil.isMouseHoveringRect(
         (double)(x + this.getWidth() - 13.0F - 36.0F), (double)(y + this.getHeight() - 12.0F - 15.0F), 36.0, 15.0, mouseX, mouseY
      );
      if (this.randomRunnable != null) {
         boolean local13_0 = MathUtil.isMouseHoveringRect(
            (double)(x + this.getWidth() - 8.0F - 115.0F), (double)(y + this.getHeight() - 12.0F - 15.0F), 38.0, 15.0, mouseX, mouseY
         );
         renderSystem.addRect(x + this.getWidth() - 8.5F - 115.0F, y + this.getHeight() - 12.0F - 15.5F, 36.0F, 16.0F, 8.0F, Color.decode("#222222"), poseStack);
         renderSystem.addRect(
            x + this.getWidth() - 8.0F - 115.0F,
            y + this.getHeight() - 12.0F - 15.0F,
            35.0F,
            15.0F,
            8.0F,
            Color.decode(local13_0 ? "#222222" : "#141414"),
            poseStack
         );
         renderSystem.addText(local9_1, "Random", x + this.getWidth() - 9.5F - 108.0F, y + this.getHeight() - 12.0F - 8.5F, Color.WHITE, poseStack);
      }

      renderSystem.addRect(x + this.getWidth() - 13.5F - 71.0F, y + this.getHeight() - 12.0F - 15.5F, 31.0F, 16.0F, 8.0F, Color.decode("#222222"), poseStack);
      renderSystem.addRect(
         x + this.getWidth() - 13.0F - 71.0F,
         y + this.getHeight() - 12.0F - 15.0F,
         30.0F,
         15.0F,
         8.0F,
         Color.decode(local11_3 ? "#222222" : "#141414"),
         poseStack
      );
      renderSystem.addText(local9_1, "Cancel", x + this.getWidth() - 13.0F - 67.0F, y + this.getHeight() - 12.0F - 8.5F, Color.WHITE, poseStack);
      renderSystem.addRect(
         x + this.getWidth() - 13.0F - 36.0F,
         y + this.getHeight() - 12.0F - 15.0F,
         36.0F,
         16.0F,
         6.0F,
         local12_4 ? local10_2.ascent().darker() : local10_2.ascent(),
         poseStack
      );
      renderSystem.addText(local9_1, "Continue", x + this.getWidth() - 13.0F - 32.0F, y + this.getHeight() - 12.0F - 8.0F, Color.BLACK, poseStack);
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
      boolean local8_1 = MathUtil.isMouseHoveringRect(
         (double)(x + this.getWidth() - 13.0F - 71.0F), (double)(y + this.getHeight() - 12.0F - 15.0F), 30.0, 15.0, mouseX, mouseY
      );
      boolean local9_2 = MathUtil.isMouseHoveringRect(
         (double)(x + this.getWidth() - 13.0F - 36.0F), (double)(y + this.getHeight() - 12.0F - 15.0F), 36.0, 15.0, mouseX, mouseY
      );
      if (this.randomRunnable != null) {
         boolean local10_0 = MathUtil.isMouseHoveringRect(
            (double)(x + this.getWidth() - 8.0F - 115.0F), (double)(y + this.getHeight() - 12.0F - 15.0F), 38.0, 15.0, mouseX, mouseY
         );
         if (local10_0) {
            this.randomRunnable.run();
            return;
         }
      }

      if (local8_1) {
         this.cancelRunnable.run();
      } else if (local9_2) {
         this.continueRunnable.run();
      }
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public void release() {
   }
}
