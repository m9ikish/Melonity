package ru.melonity.ui.menu.clickui.view;

import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.StyledView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.language.Language;
import ru.melonity.util.language.LanguageService;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScissorUtil;

@Environment(EnvType.CLIENT)
public class MenuGira extends StyledView {
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(370L);
   private final StateAnimation animation = new StateAnimation();
   private boolean opened = false;
   private boolean expanded = false;
   private boolean sliding = false;
   private final StateAnimation triangleAnimation = new StateAnimation();
   private final FrameWeightCalculator triangleWeightCalculator = FrameWeightCalculator.milliseconds(200L);
   private float scroll = 0.0F;
   private float scrollCurrent = 0.0F;
   private boolean scrollInit = false;
   private final FrameWeightCalculator scrollFwcManual = FrameWeightCalculator.milliseconds(370L);
   public static float aspectRatio = 1.7777778F;

   public MenuGira() {
      super(125.0F, 220.0F);
   }

   @Override
   public void insert(MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      this.animation.update(this.fwc.elapsedUnits());
      this.animation.state(this.opened);
      if (!this.scrollInit) {
         this.scrollCurrent = this.scroll;
         this.scrollInit = true;
      }

      this.scrollCurrent = this.scrollCurrent + (this.scroll - this.scrollCurrent) * 12.0F * this.scrollFwcManual.elapsedUnits();
      float local10_18 = this.scrollCurrent;
      poseStack.method_22903();
      poseStack.method_46416(
         x * renderSystem.getFakeScaling() + this.width * renderSystem.getFakeScaling() / 2.0F,
         y * renderSystem.getFakeScaling() + this.height * renderSystem.getFakeScaling() / 2.0F,
         0.0F
      );
      poseStack.method_22905(this.animation.dropAnimation(), this.animation.dropAnimation(), this.animation.dropAnimation());
      poseStack.method_46416(
         -(x * renderSystem.getFakeScaling()) - this.width * renderSystem.getFakeScaling() / 2.0F,
         -(y * renderSystem.getFakeScaling()) - this.height * renderSystem.getFakeScaling() / 2.0F,
         0.0F
      );
      renderSystem.addRect(x, y, this.width, this.height, 8.0F, style.background(), poseStack);
      renderSystem.addRect(x + 5.0F, y + 5.0F, 27.5F, 14.0F, 3.0F, style.gira().langBackground(), poseStack);
      renderSystem.addTexture(style.gira().preview(), x + 6.0F, y + 26.0F, 113.0F, 62.5F, poseStack);
      float local11_1 = 1.0F;
      float local12_2 = 2.0F;
      float local13_3 = aspectRatio;
      if (local13_3 == 1.7777778F) {
         aspectRatio = (float)class_310.method_1551().method_22683().method_4480() / (float)class_310.method_1551().method_22683().method_4507();
      }

      if (aspectRatio < 0.0F || Float.isNaN(aspectRatio) || Float.isInfinite(aspectRatio)) {
         aspectRatio = 1.7777778F;
      }

      if (aspectRatio > 2.0F) {
         aspectRatio = 2.0F;
      }

      float local14_4 = local12_2 - local11_1;
      float local15_5 = (this.width - 10.0F) / local14_4;
      float local16_6 = Math.abs(local13_3 * local15_5 - local11_1 * local15_5);
      String local17_7 = String.format("%.2f", local13_3);
      if (local13_3 >= 1.3333334F && local13_3 <= 1.38F) {
         local17_7 = "4:3";
      } else if (local13_3 == 1.0F) {
         local17_7 = "1:1";
      } else if (local13_3 >= 1.8F) {
         local17_7 = "18:9";
      } else if (local13_3 >= 1.7777778F) {
         local17_7 = "16:9";
      } else if (local13_3 >= 1.6F) {
         local17_7 = "16:10";
      }

      renderSystem.addText(Fonts.INTER_REGULAR_14, "Aspect Ratio", x + 5.0F, y + 94.0F, poseStack);
      renderSystem.addRect(x + 5.0F, y + 104.0F, this.width - 10.0F, 4.0F, 3.0F, Color.decode("#222222"), poseStack);
      renderSystem.addRectGradient(
         x + 5.0F, y + 104.0F, local16_6, 4.0F, 3.0F, style.slider()[0], style.slider()[1], style.slider()[1], style.slider()[0], poseStack
      );
      renderSystem.addText(Fonts.INTER_REGULAR_14, local17_7, x + this.width - Fonts.INTER_REGULAR_14.getStringWidth(local17_7) - 5.0F, y + 94.0F, poseStack);
      float local18_8 = x + local16_6 - 1.0F;
      local18_8 = Math.max(local18_8, x + 5.0F);
      renderSystem.addRect(local18_8, y + 102.0F, 7.0F, 7.0F, 1.0F, Color.WHITE, poseStack);

      for (int local19_0 = 1; local19_0 <= 2; local19_0++) {
         renderSystem.addRect(local18_8 + (float)(local19_0 * 2), y + 103.0F, 1.2F, 5.0F, Color.decode("#565656"), poseStack);
      }

      if (this.sliding) {
         aspectRatio = class_3532.method_15363(
            (float)((mouseX - (double)x - 5.0) * (double)local14_4 / (double)(this.width - 10.0F) + (double)local11_1), local11_1, local12_2
         );
      }

      LanguageService local11_19 = Melonity.INSTANCE.languageService;
      String local12_20 = local11_19.getLanguage().getIcon();
      renderSystem.addTexture(class_2960.method_60656("melonity/images/ui/languages/".concat(local12_20)), x + 10.0F, y + 8.0F, 8.0F, 8.5F, poseStack);
      this.triangleAnimation.state(this.expanded);
      this.triangleAnimation.update(this.triangleWeightCalculator.elapsedUnits());
      local13_3 = this.triangleAnimation.animation() * 180.0F;
      poseStack.method_22903();
      poseStack.method_46416(x + 21.0F + 3.75F, y + 8.0F + 3.75F, 0.0F);
      poseStack.method_49278(class_7833.field_40718.rotationDegrees(local13_3), 0.0F, 0.0F, 1.0F);
      poseStack.method_46416(-(x + 21.0F + 3.75F), -(y + 8.0F + 3.75F), 0.0F);
      renderSystem.addTexture(
         class_2960.method_60656("melonity/images/ui/icons/up.png"),
         x + 21.0F,
         y + 8.0F,
         7.5F,
         7.5F,
         new Color(
            class_3532.method_15340(255 - (int)(local13_3 / 180.0F * 136.0F), 0, 255),
            class_3532.method_15340(255 - (int)(local13_3 / 180.0F * 136.0F), 0, 255),
            class_3532.method_15340(255 - (int)(local13_3 / 180.0F * 136.0F), 0, 255)
         ),
         poseStack
      );
      poseStack.method_22909();
      if (this.expanded) {
         local14_4 = y + 25.0F;

         for (Language local18_10 : Language.values()) {
            renderSystem.addRect(
               x + 5.0F, local14_4, 75.0F, 15.0F, local11_19.getLanguage() == local18_10 ? style.background().brighter() : style.background(), poseStack
            );
            String local19_9 = local18_10.getIcon();
            renderSystem.addTexture(
               class_2960.method_60656("melonity/images/ui/languages/".concat(local19_9)), x + 9.0F, local14_4 + 3.0F, 8.0F, 8.5F, poseStack
            );
            renderSystem.addText(Fonts.INTER_REGULAR_13, local18_10.name(), x + 20.0F, local14_4 + 6.0F, poseStack);
            local14_4 += 14.0F;
         }
      }

      local14_4 = y + 119.0F;
      local15_5 = this.height - 119.0F - 5.0F;
      local16_6 = local14_4 + local10_18;
      ScissorUtil.beginScissor((double)x, (double)(local14_4 - 5.0F), (double)(this.width - 10.0F), (double)(local15_5 + 10.0F));

      for (MenuStyle local19_13 : Melonity.INSTANCE.styleManager.getStyles()) {
         boolean local20_12 = local19_13 == Melonity.INSTANCE.styleManager.getStyle();
         if (local16_6 + 22.0F > local14_4 && local16_6 < local14_4 + local15_5) {
            renderSystem.addTexture(class_2960.method_60656("melonity/images/ui/checkbox.png"), x + 6.0F, local16_6, 8.0F, 8.5F, poseStack);
            if (local20_12) {
               renderSystem.addTexture(
                  class_2960.method_60656("melonity/images/ui/checkbox_toggled.png"), x + 6.0F, local16_6, 8.0F, 8.5F, style.booleanElement(), poseStack
               );
            }

            class_310.method_1551().method_1531().method_4619(local19_13.preview()).method_23207();
            renderSystem.addTexture(local19_13.preview(), x + 22.0F, local16_6 - 4.0F, 32.0F, 18.0F, 6.0F, 0.0F, 0.0F, 1.0F, 1.0F, Color.WHITE, poseStack);
            renderSystem.addText(Fonts.INTER_REGULAR_15, local19_13.name(), x + 56.0F, local16_6 + (float)(local20_12 ? 0 : 4), Color.WHITE, poseStack);
            if (local20_12) {
               renderSystem.addText(Fonts.INTER_REGULAR_12, "Active", x + 56.0F, local16_6 + 9.0F, style.ascent(), poseStack);
            }
         }

         local16_6 += 22.0F;
      }

      ScissorUtil.endScissor();
      poseStack.method_22909();
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
      if (this.opened) {
         LanguageService languageService = Melonity.INSTANCE.languageService;
         if (MathUtil.isMouseHoveringRect((double)(x + 5.0F), (double)(y + 5.0F), 27.5, 14.0, mouseX, mouseY)) {
            this.expanded = !this.expanded;
         } else {
            if (this.expanded) {
               float languageY = y + 25.0F;

               for (Language language : Language.values()) {
                  if (MathUtil.isMouseHoveringRect((double)(x + 5.0F), (double)languageY, 75.0, 15.0, mouseX, mouseY)) {
                     languageService.setLanguage(language);
                     this.expanded = false;
                  }

                  languageY += 14.0F;
               }
            }

            if (MathUtil.isMouseHoveringRect((double)(x + 5.0F), (double)(y + 100.0F), (double)(this.width - 10.0F), 12.0, mouseX, mouseY)) {
               this.sliding = true;
            }

            float listTop = y + 119.0F;
            float listHeight = this.height - 119.0F - 5.0F;
            float entryY = listTop + this.scrollCurrent;

            for (MenuStyle menuStyle : Melonity.INSTANCE.styleManager.getStyles()) {
               if (entryY + 22.0F > listTop
                  && entryY < listTop + listHeight
                  && MathUtil.isMouseHoveringRect((double)(x + 5.0F), (double)(entryY - 4.0F), (double)(this.width - 10.0F), 20.0, mouseX, mouseY)) {
                  Melonity.INSTANCE.styleManager.setStyle(menuStyle);
               }

               entryY += 22.0F;
            }
         }
      }
   }

   public void scroll(double delta) {
      float local3_0 = this.height - 119.0F - 5.0F;
      float local4_1 = (float)(Melonity.INSTANCE.styleManager.getStyles().size() * 22);
      float local5_2 = Math.max(0.0F, local4_1 - local3_0);
      this.scroll = class_3532.method_15363(this.scroll - (float)delta * -12.0F, -local5_2, 0.0F);
   }

   @Override
   public void release() {
      this.sliding = false;
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Generated
   public StateAnimation getAnimation() {
      return this.animation;
   }

   @Generated
   public void setOpened(boolean opened) {
      this.opened = opened;
   }

   @Generated
   public boolean isOpened() {
      return this.opened;
   }
}
