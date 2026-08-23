package ru.melonity.ui.menu.clickui.view;

import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.StyledView;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class MenuTab extends StyledView {
   private final MenuCategory category;
   private final StateAnimation animation;
   private final FrameWeightCalculator frameWeightCalculator;
   private boolean selected;

   public MenuTab(MenuCategory category) {
      super(94.0F, 22.0F);
      this.category = category;
      this.animation = new StateAnimation();
      this.frameWeightCalculator = FrameWeightCalculator.milliseconds(200L);
      this.selected = false;
   }

   @Override
   public void insert(MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      boolean hovered = MathUtil.isMouseHoveringRect((double)x, (double)y, (double)this.width, (double)this.height, mouseX, mouseY);
      this.animation.state(this.selected);
      this.animation.update(this.frameWeightCalculator.elapsedUnits());
      float progress = class_3532.method_15363(this.animation.animation(), 0.0F, 1.0F);
      if (hovered && progress < 1.0F) {
         renderSystem.addRect(x, y, this.width, this.height, 8.0F, style.previewValues(), poseStack);
      }

      if (progress > 0.0F) {
         Color[] background = style.tab().background();
         int alpha = (int)(progress * 255.0F);
         renderSystem.addRectGradient(
            x,
            y,
            this.width,
            this.height,
            8.0F,
            RenderSystem.setAlpha(background[0], alpha),
            RenderSystem.setAlpha(background[1], alpha),
            RenderSystem.setAlpha(background[1], alpha),
            RenderSystem.setAlpha(background[0], alpha),
            poseStack
         );
      }

      class_2960 icon = class_2960.method_60656("melonity/images/ui/clickgui/category/".concat(this.category.getIcon()));
      GlyphData font = Fonts.INTER_MEDIUM_14;
      String title = this.category.getDisplayName();
      float iconY = y + this.height / 2.0F - 4.75F;
      float textY = y + this.height / 2.0F - 1.5F;
      Color idleColor = hovered ? Color.WHITE : style.tab().disabledTextColor();
      renderSystem.addTexture(icon, x + 9.0F, iconY, 9.5F, 9.5F, idleColor, poseStack);
      renderSystem.addText(font, title, x + 25.0F, textY, idleColor, poseStack);
      if (progress > 0.0F) {
         Color activeColor = RenderSystem.setAlpha(style.tab().textColor(), (int)(progress * 255.0F));
         renderSystem.addTexture(icon, x + 9.0F, iconY, 9.5F, 9.5F, activeColor, poseStack);
         renderSystem.addText(font, title, x + 25.0F, textY, activeColor, poseStack);
      }

      if (hovered) {
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
   public MenuCategory getCategory() {
      return this.category;
   }

   @Generated
   public StateAnimation getAnimation() {
      return this.animation;
   }

   @Generated
   public FrameWeightCalculator getFrameWeightCalculator() {
      return this.frameWeightCalculator;
   }

   @Generated
   public boolean isSelected() {
      return this.selected;
   }

   @Generated
   public void setSelected(boolean selected) {
      this.selected = selected;
   }
}
