package ru.melonity.ui.menu.clickui.view;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.ui.components.view.StyledView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class MenuNavbar extends StyledView {
   private final List<NavbarButton> buttons;
   private final FrameWeightCalculator frameWeightCalculator = FrameWeightCalculator.milliseconds(400L);
   private final StateAnimation animation = new StateAnimation();
   private final FrameWeightCalculator frameWeightCalculator2 = FrameWeightCalculator.milliseconds(400L);
   private final StateAnimation animation2 = new StateAnimation();
   private final FrameWeightCalculator frameWeightCalculator3 = FrameWeightCalculator.milliseconds(400L);
   private final StateAnimation animation3 = new StateAnimation();

   public MenuNavbar(float width) {
      super(width, 36.0F);
      this.buttons = Arrays.asList(
         new NavbarButton(class_2960.method_60656("melonity/images/ui/icons/navbar/safe.png"), 16.0F, 16.0F, 8.0F, 8.0F),
         new NavbarButton(class_2960.method_60656("melonity/images/ui/icons/navbar/keybinds.png"), 16.0F, 16.0F, 8.0F, 8.0F),
         new NavbarButton(class_2960.method_60656("melonity/images/ui/icons/navbar/misc.png"), 16.0F, 16.0F, 8.0F, 8.0F)
      );
   }

   @Override
   public void insert(MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      Color[] header = style.header();
      if (header == null) {
         renderSystem.addRect(x, y, this.width, this.height, 10.0F, style.background(), poseStack);
      } else {
         renderSystem.addRectGradient(x, y, this.width, this.height, 10.0F, header[0], header[1], header[1], header[0], poseStack);
         renderSystem.addRect(x, y + this.height - 10.0F, this.width, 10.0F, header[1], poseStack);
      }

      if (style.headerBlocks()) {
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/navbar/navbar_blocks.png"), x, y, this.width, this.height, style.blocksColor(), poseStack
         );
      }

      renderSystem.addTexture(class_2960.method_60656("melonity/images/ui/navbar/logo.png"), x + 8.0F, y + 4.0F, 26.5F, 29.0F, poseStack);
      renderSystem.addRect(x, y + this.height, this.width, 0.5F, Color.decode("#222222"), poseStack);
      this.animation.state(Melonity.INSTANCE.isSafe());
      this.animation.update(this.frameWeightCalculator.elapsedUnits());
      this.animation2.state(Melonity.INSTANCE.getMenu() != null && Melonity.INSTANCE.getMenu().isBinds());
      this.animation2.update(this.frameWeightCalculator2.elapsedUnits());
      this.animation3.state(Melonity.INSTANCE.getMenu() != null && Melonity.INSTANCE.getMenu().getGiraView().isOpened());
      this.animation3.update(this.frameWeightCalculator3.elapsedUnits());
      StateAnimation[] animations = new StateAnimation[]{this.animation, this.animation2, this.animation3};
      class_2960[] activeIcons = new class_2960[]{
         class_2960.method_60656("melonity/images/ui/icons/navbar/safe_enabled.png"),
         class_2960.method_60656("melonity/images/ui/icons/navbar/keybinds.png"),
         class_2960.method_60656("melonity/images/ui/icons/navbar/misc_opened.png")
      };
      float buttonX = this.width - 8.0F;

      for (NavbarButton button : this.buttons) {
         buttonX -= button.getWidth() + 4.0F;
      }

      for (int index = 0; index < this.buttons.size(); index++) {
         NavbarButton button = this.buttons.get(index);
         float buttonY = y + 9.0F;
         button.insert(style, x + buttonX, buttonY, mouseX, mouseY, renderSystem, poseStack);
         float active = animations[index].animation();
         if (active > 0.0F) {
            renderSystem.addTexture(
               activeIcons[index],
               x + buttonX + 4.0F,
               buttonY + button.getHeight() / 2.0F - 4.0F,
               8.0F,
               8.0F,
               RenderSystem.setAlpha(style.ascent(), (int)(active * 255.0F)),
               poseStack
            );
         }

         buttonX += button.getWidth() + 4.0F;
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
   public List<NavbarButton> getButtons() {
      return this.buttons;
   }

   @Generated
   public FrameWeightCalculator getFrameWeightCalculator() {
      return this.frameWeightCalculator;
   }

   @Generated
   public StateAnimation getAnimation() {
      return this.animation;
   }

   @Generated
   public FrameWeightCalculator getFrameWeightCalculator2() {
      return this.frameWeightCalculator2;
   }

   @Generated
   public StateAnimation getAnimation2() {
      return this.animation2;
   }

   @Generated
   public FrameWeightCalculator getFrameWeightCalculator3() {
      return this.frameWeightCalculator3;
   }

   @Generated
   public StateAnimation getAnimation3() {
      return this.animation3;
   }
}
