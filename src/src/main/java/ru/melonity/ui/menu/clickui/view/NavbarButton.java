package ru.melonity.ui.menu.clickui.view;

import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.StyledView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class NavbarButton extends StyledView {
   protected final class_2960 icon;
   protected float iconWidth;
   protected float iconHeight;
   protected String text;
   protected boolean hovered = false;
   public static final int ICON_SIZE = 16;
   public static final int GAP = 4;
   public static final float THICKNESS = 0.5F;
   private boolean centerText = false;

   public NavbarButton(float width, float height) {
      super(width, height);
      this.icon = null;
      this.text = null;
   }

   public NavbarButton(class_2960 icon, float width, float height, float iconWidth, float iconHeight) {
      super(width, height);
      this.icon = icon;
      this.text = null;
      this.iconWidth = iconWidth;
      this.iconHeight = iconHeight;
   }

   public NavbarButton(class_2960 icon, String text, float width, float height, float iconWidth, float iconHeight) {
      super(width, height);
      this.icon = icon;
      this.text = text;
      this.iconWidth = iconWidth;
      this.iconHeight = iconHeight;
   }

   public NavbarButton(String text, float width, float height) {
      super(width, height);
      this.icon = null;
      this.text = text;
   }

   @Override
   public void insert(MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      this.hovered = MathUtil.isMouseHoveringRect((double)x, (double)y, (double)this.width, (double)this.height, mouseX, mouseY);
      renderSystem.addRect(x, y, this.width, this.height, 6.0F, style.background().brighter(), poseStack);
      renderSystem.addRect(
         x + 0.5F,
         y + 0.5F,
         this.width - 1.0F,
         this.height - 1.0F,
         6.0F,
         this.hovered
            ? new Color(
               Math.min(style.background().getRed() + 10, 255),
               Math.min(style.background().getGreen() + 10, 255),
               Math.min(style.background().getBlue() + 10, 255)
            )
            : style.background(),
         poseStack
      );
      renderSystem.addRect(x + 0.5F, y + this.height - 2.0F, this.width - 1.0F, 2.0F, 6.0F, style.background().brighter(), poseStack);
      float local10_1 = 4.0F;
      if (this.icon != null) {
         renderSystem.addTexture(
            this.icon,
            x + local10_1,
            y + this.height / 2.0F - this.iconHeight / 2.0F,
            this.iconWidth,
            this.iconHeight,
            style.tab().disabledTextColor(),
            poseStack
         );
         local10_1 += 10.0F;
      }

      if (this.text != null) {
         GlyphData local11_0 = Fonts.INTER_REGULAR_14;
         if (this.centerText) {
            renderSystem.addText(
               local11_0,
               this.text,
               x + this.width / 2.0F - local11_0.getStringWidth(this.text) / 2.0F,
               y + this.height / 2.0F - local11_0.getStringHeight(this.text) / 2.0F,
               style.tab().disabledTextColor(),
               poseStack
            );
         } else {
            renderSystem.addText(
               local11_0,
               this.text,
               x + local10_1,
               y + this.height / 2.0F - local11_0.getStringHeight(this.text) / 2.0F,
               style.tab().disabledTextColor(),
               poseStack
            );
         }
      }

      if (MathUtil.isMouseHoveringRect((double)x, (double)y, (double)this.width, (double)this.height, mouseX, mouseY)) {
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

   @Generated
   public void setIconWidth(float iconWidth) {
      this.iconWidth = iconWidth;
   }

   @Generated
   public void setIconHeight(float iconHeight) {
      this.iconHeight = iconHeight;
   }

   @Generated
   public void setText(String text) {
      this.text = text;
   }

   @Generated
   public void setHovered(boolean hovered) {
      this.hovered = hovered;
   }

   @Generated
   public void setCenterText(boolean centerText) {
      this.centerText = centerText;
   }
}
