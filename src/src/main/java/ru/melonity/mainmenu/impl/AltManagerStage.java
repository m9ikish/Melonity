package ru.melonity.mainmenu.impl;

import java.awt.Color;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_442;
import net.minecraft.class_4587;
import org.lwjgl.glfw.GLFW;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IMainMenuStage;
import ru.melonity.font.Fonts;
import ru.melonity.mainmenu.AltManagerHeaderView;
import ru.melonity.mainmenu.AltManagerOuterView;
import ru.melonity.mainmenu.MainMenuStage;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.altmanager.AltService;
import ru.melonity.util.altmanager.Profile;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScissorUtil;

@Environment(EnvType.CLIENT)
public class AltManagerStage implements MainMenuStage {
   private final class_442 mainMenuScreen;
   private int selectedProfile = 0;
   private float scroll = 0.0F;
   private float scrollCurrent = 0.0F;
   private boolean scrollInit = false;
   private final FrameWeightCalculator scrollFwc = FrameWeightCalculator.milliseconds(500L);
   private final AltManagerHeaderView headerView = new AltManagerHeaderView();
   private final AltManagerOuterView outerView = new AltManagerOuterView(() -> class_310.method_1551().method_1507(new class_442()), () -> {
   }, null);

   public AltManagerStage(class_442 mainMenuScreen) {
      this.mainMenuScreen = mainMenuScreen;
      List<Profile> local2_2 = Melonity.INSTANCE.componentManager.service(AltService.class).getProfiles();

      for (int local3_1 = 0; local3_1 < local2_2.size(); local3_1++) {
         Profile local4_0 = local2_2.get(local3_1);
         if (local4_0.getName().equals(class_310.method_1551().method_1548().method_1676())) {
            this.selectedProfile = local3_1;
         }
      }
   }

   @Override
   public void render(float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      AltService local10_17 = Melonity.INSTANCE.componentManager.service(AltService.class);
      MenuStyle local11_18 = Melonity.INSTANCE.styleManager.getStyle();
      this.headerView.insert(x, y, mouseX, mouseY, renderSystem, poseStack);
      boolean local12_19 = false;
      if (local10_17.getProfiles().isEmpty()) {
         ((IMainMenuStage)this.mainMenuScreen).setStage(new AltManagerAddStage(this.mainMenuScreen));
      } else {
         float local13_3 = 38.5F;
         float local14_4 = (float)local10_17.getProfiles().size() * local13_3;
         float local15_5 = 125.0F;
         float local16_6 = Math.min(0.0F, local15_5 - local14_4) - 8.0F;
         if (this.scroll > 0.0F) {
            this.scroll = 0.0F;
         }

         if (this.scroll < local16_6) {
            this.scroll = local16_6;
         }

         if (!this.scrollInit) {
            this.scrollCurrent = this.scroll;
            this.scrollInit = true;
         }

         this.scrollCurrent = this.scrollCurrent + (this.scroll - this.scrollCurrent) * 12.0F * this.scrollFwc.elapsedUnits();
         float local17_7 = this.scrollCurrent;
         ScissorUtil.beginScissor((double)x, (double)(y + 40.0F), (double)width, 125.0);
         float local18_8 = y + 50.0F + local17_7;

         for (int local19_2 = 0; local19_2 < local10_17.getProfiles().size(); local19_2++) {
            Profile local20_0 = local10_17.getProfiles().get(local19_2);
            boolean local21_1 = this.selectedProfile == local19_2;
            if (local21_1) {
               renderSystem.addRect(x + 10.0F, local18_8 - 0.6F, width - 23.0F + 1.4F, 34.0F, 10.0F, local11_18.ascent(), poseStack);
            }

            if (MathUtil.isMouseHoveringRect((double)(x + 10.0F), (double)(local18_8 - 0.6F), (double)(width - 23.0F + 1.4F), 34.0, mouseX, mouseY)) {
               local12_19 = true;
            }

            renderSystem.addRect(x + 10.5F, local18_8, width - 23.0F, 32.5F, 8.0F, Color.decode("#181818"), poseStack);
            renderSystem.addTexture(
               class_2960.method_60656("melonity/images/generic/default_head.png"), x + 13.0F + 5.0F, local18_8 + 7.0F, 18.0F, 20.0F, poseStack
            );
            renderSystem.addText(Fonts.INTER_MEDIUM_14, local20_0.getName(), x + 40.0F, local18_8 + 11.0F, poseStack);
            renderSystem.addText(
               Fonts.INTER_MEDIUM_14,
               local20_0.getProfileType().name().toCharArray()[0] + local20_0.getProfileType().name().toLowerCase().substring(1),
               x + 40.0F,
               local18_8 + 11.0F + 10.0F,
               Color.decode("#888888"),
               poseStack
            );
            renderSystem.addTexture(
               class_2960.method_60656("melonity/images/ui/baritone/delete.png"),
               x + width - 28.0F,
               local18_8 + 13.0F,
               8.0F,
               8.0F,
               RenderSystem.setAlpha(
                  Color.WHITE, MathUtil.isMouseHoveringRect((double)(x + width - 28.0F), (double)(local18_8 + 13.0F), 8.0, 8.0, mouseX, mouseY) ? 180 : 255
               ),
               poseStack
            );
            local18_8 += 38.5F;
         }

         ScissorUtil.endScissor();
         float local19_9 = width - 26.0F;
         boolean local20_10 = MathUtil.isMouseHoveringRect((double)(x + 13.0F), (double)(y + this.getHeight() - 65.0F), (double)local19_9, 24.5, mouseX, mouseY);
         if (local20_10) {
            local12_19 = true;
         }

         GlyphData local21_11 = Fonts.INTER_MEDIUM_14;
         renderSystem.addRect(x + 12.5F, y + this.getHeight() - 65.5F, local19_9 + 1.0F, 25.5F, 6.0F, Color.decode("#222222"), poseStack);
         renderSystem.addRect(x + 13.0F, y + this.getHeight() - 65.0F, local19_9, 24.5F, 6.0F, Color.decode(local20_10 ? "#222222" : "#141414"), poseStack);
         this.outerView.insert(x, y + this.getHeight() - 42.0F, mouseX, mouseY, renderSystem, poseStack);
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/altmanager/add_account.png"),
            x + 13.0F + local19_9 / 2.0F - local21_11.getStringWidth("Add account") / 2.0F - 6.0F,
            y + this.getHeight() - 65.0F + 10.0F,
            8.0F,
            8.0F,
            local11_18.ascent(),
            poseStack
         );
         renderSystem.addText(
            local21_11,
            "Add account",
            x + 13.0F + local19_9 / 2.0F - local21_11.getStringWidth("Add account") / 2.0F + 6.0F,
            y + this.getHeight() - 65.0F + 12.0F,
            local11_18.ascent(),
            poseStack
         );
         renderSystem.addRectVGradientSilent(
            x + 10.0F,
            y + 50.0F + local14_4 - local13_3,
            width - 20.0F,
            40.0F,
            0.0F,
            RenderSystem.setAlpha(local11_18.background(), 0),
            local11_18.background(),
            local11_18.background(),
            RenderSystem.setAlpha(local11_18.background(), 0),
            poseStack
         );
      }

      if (!local10_17.getProfiles().isEmpty()) {
         float local13_15 = (float)local10_17.getProfiles().size() * 38.5F - 6.0F;
         float local14_16 = 125.0F;
         if (local13_15 > local14_16) {
            float local15_12 = Math.max(local14_16 / local13_15 * local14_16, 20.0F);
            float local16_13 = class_3532.method_15363(-this.scroll / (local13_15 - local14_16), 0.0F, 1.0F);
            float local17_14 = y + 50.0F + local16_13 * (local14_16 - local15_12);
            renderSystem.addRect(x + width - 5.0F, local17_14, 3.0F, local15_12, 3.0F, Color.decode("#222222"), poseStack);
         }
      }

      if (local12_19) {
         GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.HAND);
      } else {
         GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.ARROW);
      }
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      AltService local9_2 = Melonity.INSTANCE.componentManager.service(AltService.class);
      this.outerView.click(x, y + this.getHeight() - 42.0F, mouseX, mouseY, mouseButton);
      boolean local10_3 = MathUtil.isMouseHoveringRect(
         (double)(x + this.outerView.getWidth() - 13.0F - 42.0F),
         (double)(y + this.getHeight() - 42.0F + this.outerView.getHeight() - 12.0F - 16.0F),
         42.0,
         16.0,
         mouseX,
         mouseY
      );
      if (local10_3) {
         local9_2.getProfiles().get(this.selectedProfile).login();
         local9_2.save();
         class_310.method_1551().method_1507(new class_442());
      }

      float local11_4 = y + 50.0F + this.scroll;

      for (int local12_1 = 0; local12_1 < local9_2.getProfiles().size(); local12_1++) {
         Profile local13_0 = local9_2.getProfiles().get(local12_1);
         if (MathUtil.isMouseHoveringRect((double)(x + 10.0F), (double)local11_4, (double)(width - 23.0F), 34.0, mouseX, mouseY)
            && mouseY < (double)(y + 175.0F)) {
            this.selectedProfile = local12_1;
         }

         if (MathUtil.isMouseHoveringRect((double)(x + width - 28.0F), (double)(local11_4 + 13.0F), 8.0, 8.0, mouseX, mouseY)) {
            local9_2.removeProfile(local13_0);
         }

         local11_4 += 38.5F;
      }

      float local12_5 = width - 26.0F;
      boolean local13_6 = MathUtil.isMouseHoveringRect((double)(x + 13.0F), (double)(y + this.getHeight() - 65.0F), (double)local12_5, 24.5, mouseX, mouseY);
      if (local13_6) {
         ((IMainMenuStage)this.mainMenuScreen).setStage(new AltManagerAddStage(this.mainMenuScreen));
      }
   }

   @Override
   public void key(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         Melonity.INSTANCE.componentManager.service(AltService.class).save();
         class_310.method_1551().method_1507(new class_442());
      }
   }

   @Override
   public void scroll(float x, float y, float width, double amount, double mouseX, double mouseY) {
      this.scroll += (float)(amount * 15.0);
   }

   @Override
   public float getHeight() {
      return (float)(160 + (Math.min(Melonity.INSTANCE.componentManager.service(AltService.class).getProfiles().size(), 3) - 1) * 40);
   }
}
