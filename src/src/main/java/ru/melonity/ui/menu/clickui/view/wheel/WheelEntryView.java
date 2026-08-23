package ru.melonity.ui.menu.clickui.view.wheel;

import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.StyledView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.macro.Action;
import ru.melonity.util.macro.Macro;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class WheelEntryView extends StyledView {
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(150L);
   private final StateAnimation animation = new StateAnimation();
   private final FrameWeightCalculator selectedFwc = FrameWeightCalculator.milliseconds(250L);
   private final StateAnimation selectedAnimation = new StateAnimation();
   private Macro macro = null;
   private boolean selected;

   public WheelEntryView(float width, float height) {
      super(width, height);
   }

   @Override
   public void insert(MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      renderSystem.addRect(x - 0.5F, y - 0.5F, this.width + 1.0F, this.height + 2.0F, 6.0F, style.modeElement()[1], poseStack);
      renderSystem.addRect(x, y, this.width, this.height, 6.0F, style.modeElement()[0], poseStack);
      if (this.macro == null) {
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/add.png"), x + this.width / 2.0F - 5.0F, y + this.height / 2.0F - 4.5F, 10.0F, 10.0F, poseStack
         );
      } else {
         float local10_1 = 0.0F;

         for (Action local12_0 : this.macro.getActions()) {
            local10_1 += local12_0.getDelay();
         }

         String local11_2 = this.macro.getActions().size() + " actions  " + String.format("%.1f", local10_1 / 1000.0F).replace(',', '.') + " s";
         renderSystem.addText(
            Fonts.INTER_REGULAR_14,
            this.macro.getName(),
            x + this.width / 2.0F - Fonts.INTER_REGULAR_14.getStringWidth(this.macro.getName()) / 2.0F,
            y + 9.0F,
            poseStack
         );
         renderSystem.addText(
            Fonts.INTER_REGULAR_12,
            local11_2,
            x + this.width / 2.0F - Fonts.INTER_REGULAR_12.getStringWidth(local11_2) / 2.0F,
            y + 21.0F,
            Color.decode("#888888"),
            poseStack
         );
      }
   }

   public void drawWheelStyle(
      MenuStyle style,
      float x,
      float y,
      float xOffset,
      float yOffset,
      double mouseX,
      double mouseY,
      RenderSystem renderSystem,
      boolean state,
      class_4587 poseStack
   ) {
      class_310 local13_5 = class_310.method_1551();
      this.animation.state(state);
      this.animation.update(this.fwc.elapsedUnits());
      if (!(this.animation.animation() <= 0.0F)) {
         float local14_6 = 22.0F;
         float local15_7 = this.animation.animation() * 90.0F;
         poseStack.method_22903();
         poseStack.method_46416(xOffset * local15_7, yOffset * local15_7, 0.0F);
         if (local15_7 >= 90.0F) {
            float local16_0 = (float)local13_5.method_22683().method_4480() / 2.0F;
            float local17_1 = (float)local13_5.method_22683().method_4507() / 2.0F;
            this.selected = MathUtil.isMouseHoveringRect(
               (double)(local16_0 / 2.0F + 30.0F - 37.0F + xOffset * local15_7 - 30.0F),
               (double)(local17_1 / 2.0F - 15.0F + yOffset * local15_7),
               (double)this.width,
               (double)local14_6,
               mouseX,
               mouseY
            );
         }

         this.selectedAnimation.state(this.selected);
         this.selectedAnimation.update(this.selectedFwc.elapsedUnits());
         renderSystem.addRect(x - 2.0F, y - 2.0F, this.width + 4.0F, local14_6 + 4.0F, 6.0F, new Color(24, 24, 24, 140), poseStack);
         renderSystem.addOutlineRect(
            x,
            y,
            this.width,
            local14_6,
            6.0F,
            5.0F,
            RenderSystem.setAlpha(Color.decode("#181818"), (int)(this.selectedAnimation.animation() * 255.0F)),
            poseStack
         );
         renderSystem.addRect(x, y, this.width, local14_6, 6.0F, new Color(24, 24, 24, (int)(this.selectedAnimation.animation() * 210.0F)), poseStack);
         if (this.macro == null) {
            renderSystem.addText(
               Fonts.INTER_MEDIUM_15,
               "Empty",
               x + this.width / 2.0F - Fonts.INTER_MEDIUM_15.getStringWidth("Empty") / 2.0F,
               y + 10.0F,
               new Color(255, 255, 255, 153),
               poseStack
            );
         } else {
            float local16_3 = 0.0F;

            for (Action local18_2 : this.macro.getActions()) {
               local16_3 += local18_2.getDelay();
            }

            String local17_4 = this.macro.getActions().size() + " actions  " + String.format("%.1f", local16_3 / 1000.0F).replace(',', '.') + " s";
            renderSystem.addText(
               Fonts.INTER_REGULAR_14,
               this.macro.getName(),
               x + this.width / 2.0F - Fonts.INTER_REGULAR_14.getStringWidth(this.macro.getName()) / 2.0F,
               y + 6.0F,
               poseStack
            );
            renderSystem.addText(
               Fonts.INTER_REGULAR_12,
               local17_4,
               x + this.width / 2.0F - Fonts.INTER_REGULAR_12.getStringWidth(local17_4) / 2.0F,
               y + 16.0F,
               Color.decode("#888888"),
               poseStack
            );
         }

         poseStack.method_22909();
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
   public StateAnimation getAnimation() {
      return this.animation;
   }

   @Generated
   public Macro getMacro() {
      return this.macro;
   }

   @Generated
   public void setMacro(Macro macro) {
      this.macro = macro;
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
