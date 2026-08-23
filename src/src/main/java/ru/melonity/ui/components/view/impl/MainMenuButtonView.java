package ru.melonity.ui.components.view.impl;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import org.jetbrains.annotations.NotNull;
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
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\r\u0018\u0000 (2\u00020\u0001:\u0001(B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004¢\u0006\u0004\b\u0007\u0010\bJ?\u0010\u0013\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0010H\u0017¢\u0006\u0004\b\u0013\u0010\u0014J7\u0010\u0017\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u0015H\u0016¢\u0006\u0004\b\u0017\u0010\u0018J'\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0015H\u0016¢\u0006\u0004\b\u001d\u0010\u001eJ\u000f\u0010\u001f\u001a\u00020\u0012H\u0016¢\u0006\u0004\b\u001f\u0010 R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010!R\"\u0010\"\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'¨\u0006)"},
   d2 = {"Lru/melonity/ui/components/view/impl/MainMenuButtonView;", "Lru/melonity/ui/components/view/View;", "", "text", "", "width", "height", "<init>", "(Ljava/lang/String;FF)V", "x", "y", "", "mouseX", "mouseY", "Lru/melonity/util/render/RenderSystem;", "renderSystem", "Lnet/minecraft/class_4587;", "poseStack", "", "insert", "(FFDDLru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "", "mouseButton", "click", "(FFDDI)V", "keyCode", "scanCode", "modifiers", "", "key", "(III)Z", "release", "()V", "Ljava/lang/String;", "hovered", "Z", "getHovered", "()Z", "setHovered", "(Z)V", "Companion", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class MainMenuButtonView extends View {
   @NotNull
   private static final MainMenuButtonView.Companion Companion = new MainMenuButtonView.Companion(null);
   @NotNull
   private final String text;
   private boolean hovered;
   private static final float THICKNESS = 0.5F;

   public MainMenuButtonView(@NotNull String text, float width, float height) {
      super(width, height);
      Intrinsics.checkNotNullParameter(text, "text");
      this.text = text;
   }

   public final boolean getHovered() {
      return this.hovered;
   }

   public final void setHovered(boolean value) {
      this.hovered = value;
   }

   @Override
   public void insert(float x, float y, double mouseX, double mouseY, @NotNull RenderSystem renderSystem, @NotNull class_4587 poseStack) {
      Intrinsics.checkNotNullParameter(renderSystem, "renderSystem");
      Intrinsics.checkNotNullParameter(poseStack, "poseStack");
      float width = this.getWidth();
      float height = this.getHeight();
      this.hovered = MathUtil.isMouseHoveringRect((double)x, (double)y, (double)width, (double)height, mouseX, mouseY);
      MenuStyle style = Melonity.INSTANCE.styleManager.getStyle();
      renderSystem.addRect(x, y, width, height, 8.0F, style.background().brighter(), poseStack);
      renderSystem.addRect(
         x + THICKNESS,
         y + THICKNESS,
         width - THICKNESS * 2.0F,
         height - THICKNESS * 2.0F,
         8.0F,
         this.hovered ? style.background().brighter() : style.background(),
         poseStack
      );
      GlyphData font = Fonts.INTER_MEDIUM_14;
      renderSystem.addText(
         font,
         this.text,
         x + width / 2.0F - font.getStringWidth(this.text) / 2.0F,
         y + height / 2.0F - font.getStringHeight(this.text) / 2.0F,
         this.hovered ? Color.WHITE : Color.decode("#888888"),
         poseStack
      );
      if (this.hovered) {
         Melonity.hand = true;
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

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
      d2 = {"Lru/melonity/ui/components/view/impl/MainMenuButtonView$Companion;", "", "<init>", "()V", "", "THICKNESS", "F", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   private static final class Companion {
      private Companion() {
      }

      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
