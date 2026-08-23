package ru.melonity.ui.components.view.impl;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
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
   d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0016\u0018\u0000 ^2\u00020\u0001:\u0001^B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0004\b\u0005\u0010\u0006B1\b\u0016\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0006\u0010\n\u001a\u00020\u0002¢\u0006\u0004\b\u0005\u0010\u000bB9\b\u0016\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0006\u0010\n\u001a\u00020\u0002¢\u0006\u0004\b\u0005\u0010\u000eB!\b\u0016\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0004\b\u0005\u0010\u000fJ\u0015\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010¢\u0006\u0004\b\u0013\u0010\u0014J\u0015\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0002¢\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0002¢\u0006\u0004\b\u0019\u0010\u0017J\u0015\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001a¢\u0006\u0004\b\u001c\u0010\u001dJ\u0015\u0010\u001f\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u001a¢\u0006\u0004\b\u001f\u0010\u001dJ\u0015\u0010!\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u001a¢\u0006\u0004\b!\u0010\u001dJ\r\u0010\"\u001a\u00020\u001a¢\u0006\u0004\b\"\u0010#J\u0017\u0010%\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u001aH\u0004¢\u0006\u0004\b%\u0010\u001dJ\u000f\u0010&\u001a\u00020\u001aH\u0004¢\u0006\u0004\b&\u0010#J?\u00100\u001a\u00020\u00122\u0006\u0010'\u001a\u00020\u00022\u0006\u0010(\u001a\u00020\u00022\u0006\u0010*\u001a\u00020)2\u0006\u0010+\u001a\u00020)2\u0006\u0010-\u001a\u00020,2\u0006\u0010/\u001a\u00020.H\u0017¢\u0006\u0004\b0\u00101J7\u00104\u001a\u00020\u00122\u0006\u0010'\u001a\u00020\u00022\u0006\u0010(\u001a\u00020\u00022\u0006\u0010*\u001a\u00020)2\u0006\u0010+\u001a\u00020)2\u0006\u00103\u001a\u000202H\u0016¢\u0006\u0004\b4\u00105J'\u00109\u001a\u00020\u001a2\u0006\u00106\u001a\u0002022\u0006\u00107\u001a\u0002022\u0006\u00108\u001a\u000202H\u0016¢\u0006\u0004\b9\u0010:J\u000f\u0010;\u001a\u00020\u0012H\u0016¢\u0006\u0004\b;\u0010<R$\u0010\b\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\b\u0010=\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\"\u0010\t\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\t\u0010B\u001a\u0004\bC\u0010D\"\u0004\bE\u0010\u0017R\"\u0010\n\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\n\u0010B\u001a\u0004\bF\u0010D\"\u0004\bG\u0010\u0017R$\u0010\r\u001a\u0004\u0018\u00010\f8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\r\u0010H\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR\"\u0010N\u001a\u00020M8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\bN\u0010O\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\"\u0010T\u001a\u00020M8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\bT\u0010O\u001a\u0004\bU\u0010Q\"\u0004\bV\u0010SR\"\u0010$\u001a\u00020\u001a8\u0004@\u0004X\u0084\u000e¢\u0006\u0012\n\u0004\b$\u0010W\u001a\u0004\bX\u0010#\"\u0004\bY\u0010\u001dR\"\u0010Z\u001a\u00020\u001a8\u0004@\u0004X\u0084\u000e¢\u0006\u0012\n\u0004\bZ\u0010W\u001a\u0004\b[\u0010#\"\u0004\b\\\u0010\u001dR\u0016\u0010\u0011\u001a\u00020\u00108\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0011\u0010]R\u0016\u0010\u0015\u001a\u00020\u00028\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0015\u0010BR\u0016\u0010\u0018\u001a\u00020\u00028\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0018\u0010BR\u0016\u0010\u001b\u001a\u00020\u001a8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001b\u0010WR\u0016\u0010\u001e\u001a\u00020\u001a8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001e\u0010W¨\u0006_"},
   d2 = {"Lru/melonity/ui/components/view/impl/ButtonView;", "Lru/melonity/ui/components/view/View;", "", "width", "height", "<init>", "(FF)V", "Lnet/minecraft/class_2960;", "icon", "iconWidth", "iconHeight", "(Lnet/minecraft/class_2960;FFFF)V", "", "text", "(Lnet/minecraft/class_2960;Ljava/lang/String;FFFF)V", "(Ljava/lang/String;FF)V", "Lru/melonity/util/font/GlyphData;", "font", "", "setFont", "(Lru/melonity/util/font/GlyphData;)V", "iconOffset", "setIconOffset", "(F)V", "textOffset", "setTextOffset", "", "centerText", "setCenterText", "(Z)V", "centerIcon", "setCenterIcon", "active", "setActive", "isActive", "()Z", "hovered", "setHoveredState", "isHoveredState", "x", "y", "", "mouseX", "mouseY", "Lru/melonity/util/render/RenderSystem;", "renderSystem", "Lnet/minecraft/class_4587;", "poseStack", "insert", "(FFDDLru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "", "mouseButton", "click", "(FFDDI)V", "keyCode", "scanCode", "modifiers", "key", "(III)Z", "release", "()V", "Lnet/minecraft/class_2960;", "getIcon", "()Lnet/minecraft/class_2960;", "setIcon", "(Lnet/minecraft/class_2960;)V", "F", "getIconWidth", "()F", "setIconWidth", "getIconHeight", "setIconHeight", "Ljava/lang/String;", "getText", "()Ljava/lang/String;", "setText", "(Ljava/lang/String;)V", "Ljava/awt/Color;", "iconColor", "Ljava/awt/Color;", "getIconColor", "()Ljava/awt/Color;", "setIconColor", "(Ljava/awt/Color;)V", "textColor", "getTextColor", "setTextColor", "Z", "getHovered", "setHovered", "activeFlag", "getActiveFlag", "setActiveFlag", "Lru/melonity/util/font/GlyphData;", "Companion", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public class ButtonView extends View {
   @NotNull
   public static final ButtonView.Companion Companion = new ButtonView.Companion(null);
   @Nullable
   private class_2960 icon;
   private float iconWidth;
   private float iconHeight;
   @Nullable
   private String text;
   @NotNull
   private Color iconColor;
   @NotNull
   private Color textColor;
   private boolean hovered;
   private boolean activeFlag;
   @NotNull
   private GlyphData font;
   private float iconOffset;
   private float textOffset;
   private boolean centerText;
   private boolean centerIcon;
   public static final int ICON_SIZE = 16;
   public static final int GAP = 4;
   public static final float THICKNESS = 0.5F;

   @Nullable
   public final class_2960 getIcon() {
      return this.icon;
   }

   public final void setIcon(@Nullable class_2960 value) {
      this.icon = value;
   }

   public final float getIconWidth() {
      return this.iconWidth;
   }

   public final void setIconWidth(float value) {
      this.iconWidth = value;
   }

   public final float getIconHeight() {
      return this.iconHeight;
   }

   public final void setIconHeight(float value) {
      this.iconHeight = value;
   }

   @Nullable
   public final String getText() {
      return this.text;
   }

   public final void setText(@Nullable String value) {
      this.text = value;
   }

   @NotNull
   public final Color getIconColor() {
      return this.iconColor;
   }

   public final void setIconColor(@NotNull Color value) {
      Intrinsics.checkNotNullParameter(value, "value");
      this.iconColor = value;
   }

   @NotNull
   public final Color getTextColor() {
      return this.textColor;
   }

   public final void setTextColor(@NotNull Color value) {
      Intrinsics.checkNotNullParameter(value, "value");
      this.textColor = value;
   }

   protected final boolean getHovered() {
      return this.hovered;
   }

   protected final void setHovered(boolean value) {
      this.hovered = value;
   }

   protected final boolean getActiveFlag() {
      return this.activeFlag;
   }

   protected final void setActiveFlag(boolean value) {
      this.activeFlag = value;
   }

   public ButtonView(float width, float height) {
      super(width, height);
      Color var10001 = Color.WHITE;
      Intrinsics.checkNotNullExpressionValue(Color.WHITE, "WHITE");
      this.iconColor = var10001;
      var10001 = Color.decode("#888888");
      Intrinsics.checkNotNullExpressionValue(var10001, "decode(...)");
      this.textColor = var10001;
      GlyphData var4 = Fonts.INTER_REGULAR_14;
      Intrinsics.checkNotNullExpressionValue(Fonts.INTER_REGULAR_14, "INTER_REGULAR_14");
      this.font = var4;
   }

   public ButtonView(@NotNull class_2960 icon, float width, float height, float iconWidth, float iconHeight) {
      super(width, height);
      Intrinsics.checkNotNullParameter(icon, "icon");
      Color var10001 = Color.WHITE;
      Intrinsics.checkNotNullExpressionValue(Color.WHITE, "WHITE");
      this.iconColor = var10001;
      var10001 = Color.decode("#888888");
      Intrinsics.checkNotNullExpressionValue(var10001, "decode(...)");
      this.textColor = var10001;
      GlyphData var7 = Fonts.INTER_REGULAR_14;
      Intrinsics.checkNotNullExpressionValue(Fonts.INTER_REGULAR_14, "INTER_REGULAR_14");
      this.font = var7;
      this.icon = icon;
      this.iconWidth = iconWidth;
      this.iconHeight = iconHeight;
   }

   public ButtonView(@NotNull class_2960 icon, @NotNull String text, float width, float height, float iconWidth, float iconHeight) {
      super(width, height);
      Intrinsics.checkNotNullParameter(icon, "icon");
      Intrinsics.checkNotNullParameter(text, "text");
      Color var10001 = Color.WHITE;
      Intrinsics.checkNotNullExpressionValue(Color.WHITE, "WHITE");
      this.iconColor = var10001;
      var10001 = Color.decode("#888888");
      Intrinsics.checkNotNullExpressionValue(var10001, "decode(...)");
      this.textColor = var10001;
      GlyphData var8 = Fonts.INTER_REGULAR_14;
      Intrinsics.checkNotNullExpressionValue(Fonts.INTER_REGULAR_14, "INTER_REGULAR_14");
      this.font = var8;
      this.icon = icon;
      this.text = text;
      this.iconWidth = iconWidth;
      this.iconHeight = iconHeight;
   }

   public ButtonView(@NotNull String text, float width, float height) {
      super(width, height);
      Intrinsics.checkNotNullParameter(text, "text");
      Color var10001 = Color.WHITE;
      Intrinsics.checkNotNullExpressionValue(Color.WHITE, "WHITE");
      this.iconColor = var10001;
      var10001 = Color.decode("#888888");
      Intrinsics.checkNotNullExpressionValue(var10001, "decode(...)");
      this.textColor = var10001;
      GlyphData var5 = Fonts.INTER_REGULAR_14;
      Intrinsics.checkNotNullExpressionValue(Fonts.INTER_REGULAR_14, "INTER_REGULAR_14");
      this.font = var5;
      this.text = text;
   }

   public final void setFont(@NotNull GlyphData font) {
      Intrinsics.checkNotNullParameter(font, "font");
      this.font = font;
   }

   public final void setIconOffset(float iconOffset) {
      this.iconOffset = iconOffset;
   }

   public final void setTextOffset(float textOffset) {
      this.textOffset = textOffset;
   }

   public final void setCenterText(boolean centerText) {
      this.centerText = centerText;
   }

   public final void setCenterIcon(boolean centerIcon) {
      this.centerIcon = centerIcon;
   }

   public final void setActive(boolean active) {
      this.activeFlag = active;
   }

   public final boolean isActive() {
      return this.activeFlag;
   }

   protected final void setHoveredState(boolean hovered) {
      this.hovered = hovered;
   }

   protected final boolean isHoveredState() {
      return this.hovered;
   }

   @Override
   public void insert(float x, float y, double mouseX, double mouseY, @NotNull RenderSystem renderSystem, @NotNull class_4587 poseStack) {
      Intrinsics.checkNotNullParameter(renderSystem, "renderSystem");
      Intrinsics.checkNotNullParameter(poseStack, "poseStack");
      float width = this.getWidth();
      float height = this.getHeight();
      this.hovered = MathUtil.isMouseHoveringRect((double)x, (double)y, (double)width, (double)height, mouseX, mouseY);
      MenuStyle style = Melonity.INSTANCE.styleManager.getStyle();
      Color border = this.activeFlag ? style.ascent() : style.background().brighter();
      Color fill = this.activeFlag ? style.ascent() : style.background();
      renderSystem.addRect(x, y, width, height, 6.0F, border, poseStack);
      renderSystem.addRect(x + THICKNESS, y + THICKNESS, width - THICKNESS * 2.0F, height - THICKNESS * 2.0F, 6.0F, fill, poseStack);
      renderSystem.addRect(x + THICKNESS, y + height - 2.0F, width - THICKNESS * 2.0F, 2.0F, 6.0F, border, poseStack);
      if (this.hovered) {
         renderSystem.addRect(x, y, width, height, 6.0F, new Color(255, 255, 255, 10), poseStack);
         Melonity.hand = true;
      }

      String label = this.text;
      class_2960 iconId = this.icon;
      float labelWidth = label != null ? this.font.getStringWidth(label) : 0.0F;
      float pictureWidth = iconId != null ? this.iconWidth : 0.0F;
      float spacing = label != null && iconId != null ? (float)GAP : 0.0F;
      float groupWidth = pictureWidth + spacing + labelWidth;
      boolean groupCentered = this.centerIcon && this.centerText || this.centerIcon && label == null || this.centerText && iconId == null;
      float iconX;
      float textX;
      if (groupCentered) {
         float groupStart = x + (width - groupWidth) / 2.0F;
         iconX = groupStart;
         textX = groupStart + pictureWidth + spacing;
      } else if (this.centerText && iconId != null) {
         iconX = x + (float)GAP;
         textX = x + width / 2.0F - labelWidth / 2.0F;
      } else {
         iconX = x + (float)GAP;
         textX = x + (float)GAP + pictureWidth + spacing;
      }

      if (iconId != null) {
         float iconY = y + height / 2.0F - this.iconHeight / 2.0F;
         renderSystem.addTexture(iconId, iconX + this.iconOffset, iconY, this.iconWidth, this.iconHeight, this.iconColor, poseStack);
      }

      if (label != null) {
         float textY = y + height / 2.0F - this.font.getStringHeight(label) / 2.0F;
         Color color = this.activeFlag ? Color.decode("#141414") : (this.hovered ? Color.WHITE : this.textColor);
         renderSystem.addText(this.font, label, textX + this.textOffset, textY, color, poseStack);
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
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0014\u0010\u0005\u001a\u00020\u00048\u0006X\u0086T¢\u0006\u0006\n\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048\u0006X\u0086T¢\u0006\u0006\n\u0004\b\u0007\u0010\u0006R\u0014\u0010\t\u001a\u00020\b8\u0006X\u0086T¢\u0006\u0006\n\u0004\b\t\u0010\n¨\u0006\u000b"},
      d2 = {"Lru/melonity/ui/components/view/impl/ButtonView$Companion;", "", "<init>", "()V", "", "ICON_SIZE", "I", "GAP", "", "THICKNESS", "F", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   public static final class Companion {
      private Companion() {
      }

      public Companion(kotlin.jvm.internal.DefaultConstructorMarker marker) {
         this();
      }
   }
}
