package ru.melonity.mainmenu.impl;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_442;
import net.minecraft.class_4587;
import org.lwjgl.glfw.GLFW;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IMainMenuStage;
import ru.melonity.font.Fonts;
import ru.melonity.mainmenu.AltManagerHeaderView;
import ru.melonity.mainmenu.AltManagerOuterView;
import ru.melonity.mainmenu.MainMenuStage;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.util.altmanager.AltService;
import ru.melonity.util.altmanager.Profile;
import ru.melonity.util.altmanager.ProfileType;
import ru.melonity.util.altmanager.impl.microsoft.RedirectMicrosoftProfileProvider;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class AltManagerAddStage implements MainMenuStage {
   private final class_442 mainMenuScreen;
   private final AltManagerHeaderView headerView = new AltManagerHeaderView();
   private final AltManagerOuterView outerView;
   private boolean logging;
   private final MelonityTextField codeTextField = new MelonityTextField("Code", false, 50.0F, 14.0F);

   public AltManagerAddStage(class_442 mainMenuScreen) {
      this.mainMenuScreen = mainMenuScreen;
      this.logging = false;
      this.outerView = new AltManagerOuterView(() -> {
         if (Melonity.INSTANCE.componentManager.service(AltService.class).getProfiles().isEmpty()) {
            class_310.method_1551().method_1507(new class_442());
         } else {
            ((IMainMenuStage)mainMenuScreen).setStage(new AltManagerStage(mainMenuScreen));
         }
      }, () -> {
      }, null);
   }

   @Override
   public void render(float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      AltService local10_12 = Melonity.INSTANCE.componentManager.service(AltService.class);
      this.headerView.insert(x, y, mouseX, mouseY, renderSystem, poseStack);
      GlyphData local11_13 = Fonts.INTER_REGULAR_14;
      if (!this.logging) {
         boolean local12_0 = MathUtil.isMouseHoveringRect((double)(x + 13.0F), (double)(y + 44.0F), 91.0, 42.0, mouseX, mouseY);
         renderSystem.addRectGradient(x + 13.0F, y + 44.0F, 87.5F, 42.0F, 8.0F, Color.RED, Color.CYAN, Color.YELLOW, Color.GREEN, poseStack);
         renderSystem.addRect(x + 13.0F, y + 44.0F, 87.5F, 42.0F, 8.0F, Color.decode(local12_0 ? "#2C2C2C" : "#181818"), poseStack);
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/altmanager/microsoft.png"),
            x + 13.0F + 43.75F - 6.0F,
            y + 44.0F + 8.0F,
            12.0F,
            12.0F,
            Color.decode("#888888"),
            poseStack
         );
         renderSystem.addText(
            local11_13, "Microsoft", x + 13.0F + 45.5F - local11_13.getStringWidth("Microsoft") / 2.0F, y + 44.0F + 29.0F, Color.decode("#888888"), poseStack
         );
         boolean local13_1 = MathUtil.isMouseHoveringRect((double)(x + 13.0F + 87.5F), (double)(y + 44.0F), 90.0, 42.0, mouseX, mouseY);
         renderSystem.addRectGradient(
            x + 13.0F + 90.0F,
            y + 44.0F,
            87.5F,
            42.0F,
            8.0F,
            Color.decode("#888888"),
            Color.decode("#181818"),
            Color.decode("#181818"),
            Color.decode("#888888"),
            poseStack
         );
         renderSystem.addRect(x + 13.0F + 90.0F, y + 44.0F, 87.5F, 42.0F, 8.0F, Color.decode(local13_1 ? "#2C2C2C" : "#181818"), poseStack);
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/altmanager/mojang.png"),
            x + 13.0F + 90.0F + 3.0F + 43.75F - 8.0F,
            y + 44.0F + 8.0F,
            12.0F,
            12.0F,
            Color.decode("#888888"),
            poseStack
         );
         renderSystem.addText(
            local11_13,
            "Mojang",
            x + 13.0F + 90.0F + 43.75F - local11_13.getStringWidth("Mojang") / 2.0F,
            y + 44.0F + 29.0F,
            Color.decode("#888888"),
            poseStack
         );
         float local14_2 = width - 26.0F;
         boolean local15_3 = MathUtil.isMouseHoveringRect((double)(x + 13.0F), (double)(y + 90.0F), (double)local14_2, 42.0, mouseX, mouseY);
         renderSystem.addRectGradient(
            x + 13.0F,
            y + 90.0F,
            local14_2,
            42.0F,
            8.0F,
            Color.decode("#888888"),
            Color.decode("#181818"),
            Color.decode("#181818"),
            Color.decode("#888888"),
            poseStack
         );
         renderSystem.addRect(x + 13.0F, y + 90.0F, local14_2, 42.0F, 8.0F, Color.decode(local15_3 ? "#2C2C2C" : "#181818"), poseStack);
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/altmanager/cracked.png"),
            x + 13.0F + local14_2 / 2.0F - 6.0F,
            y + 90.0F + 8.0F,
            12.0F,
            12.0F,
            Color.decode("#888888"),
            poseStack
         );
         renderSystem.addText(
            local11_13,
            "Cracked",
            x + 13.0F + local14_2 / 2.0F - local11_13.getStringWidth("Cracked") / 2.0F,
            y + 90.0F + 29.0F,
            Color.decode("#888888"),
            poseStack
         );
         renderSystem.addRect(x, y + 142.0F, width, 1.0F, Color.decode("#222222"), poseStack);
      } else {
         long local12_4 = System.currentTimeMillis();
         float local14_5 = 360.0F - (float)(local12_4 % 1000L) / 1000.0F * 360.0F;
         renderSystem.addCircle(x + width / 2.0F, y + 50.0F, 0.0F, 360.0F, 5.0F, 2.0F, false, Color.DARK_GRAY, poseStack);
         renderSystem.addCircle(
            x + width / 2.0F, y + 50.0F, local14_5, local14_5 + 30.0F, 5.0F, 2.0F, false, Melonity.INSTANCE.styleManager.getStyle().ascent(), poseStack
         );
         this.codeTextField.setFocus(true);
         if (RedirectMicrosoftProfileProvider.CURRENT_AUTH_CODE != null) {
            this.codeTextField.setText(RedirectMicrosoftProfileProvider.CURRENT_AUTH_CODE);
         }

         String local15_6 = "Enter this code:";
         float local16_7 = Fonts.INTER_MEDIUM_14.getStringWidth(local15_6);
         float local17_8 = 6.0F;
         float local18_9 = 50.0F;
         float local19_10 = local16_7 + local17_8 + local18_9;
         float local20_11 = x + width / 2.0F - local19_10 / 2.0F;
         renderSystem.addText(Fonts.INTER_REGULAR_14, local15_6, local20_11, y + 66.0F, poseStack);
         this.codeTextField
            .insert(Melonity.INSTANCE.styleManager.getStyle(), local20_11 + local16_7 + local17_8, y + 62.0F, mouseX, mouseY, renderSystem, poseStack);
      }

      GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.ARROW);
      this.outerView.insert(x, y + this.getHeight() - 42.0F, mouseX, mouseY, renderSystem, poseStack);
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      AltService local9_6 = Melonity.INSTANCE.componentManager.service(AltService.class);
      this.outerView.click(x, y + this.getHeight() - 42.0F, mouseX, mouseY, mouseButton);
      float local10_7 = width - 26.0F;
      boolean local11_8 = MathUtil.isMouseHoveringRect((double)(x + 13.0F), (double)(y + 44.0F), 91.0, 42.0, mouseX, mouseY);
      boolean local12_9 = MathUtil.isMouseHoveringRect((double)(x + 13.0F + 91.0F + 3.0F), (double)(y + 44.0F), 91.0, 42.0, mouseX, mouseY);
      boolean local13_10 = MathUtil.isMouseHoveringRect((double)(x + 13.0F), (double)(y + 90.0F), (double)local10_7, 42.0, mouseX, mouseY);
      if (!this.logging) {
         if (local11_8) {
            new Thread(() -> {
               this.logging = true;
               Profile local2_0;
               if ((local2_0 = local9_6.createProfile(ProfileType.MICROSOFT, null, null)) != null) {
                  local9_6.getProfiles().add(local2_0);
                  local9_6.save();
                  ((IMainMenuStage)this.mainMenuScreen).setStage(new AltManagerStage(this.mainMenuScreen));
               }
            }).start();
         } else if (local12_9) {
            ((IMainMenuStage)this.mainMenuScreen).setStage(new MojangAccountStage(this.mainMenuScreen, false));
         } else if (local13_10) {
            ((IMainMenuStage)this.mainMenuScreen).setStage(new MojangAccountStage(this.mainMenuScreen, true));
         }
      } else {
         String local14_0 = "Enter this code:";
         float local15_1 = Fonts.INTER_MEDIUM_14.getStringWidth(local14_0);
         float local16_2 = 6.0F;
         float local17_3 = 50.0F;
         float local18_4 = local15_1 + local16_2 + local17_3;
         float local19_5 = x + width / 2.0F - local18_4 / 2.0F;
         this.codeTextField.click(local19_5 + local15_1 + local16_2, y + 62.0F, mouseX, mouseY, mouseButton);
      }
   }

   @Override
   public void key(int keyCode, int scanCode, int modifiers) {
      this.codeTextField.key(keyCode, scanCode, modifiers);
   }

   @Override
   public void scroll(float x, float y, float width, double amount, double mouseX, double mouseY) {
   }

   @Override
   public float getHeight() {
      return this.logging ? 120.0F : 180.0F;
   }
}
