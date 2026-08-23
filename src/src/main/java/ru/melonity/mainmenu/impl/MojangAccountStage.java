package ru.melonity.mainmenu.impl;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.altmanager.AltService;
import ru.melonity.util.altmanager.Profile;
import ru.melonity.util.altmanager.ProfileType;
import ru.melonity.util.altmanager.UsernameGenerator;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class MojangAccountStage implements MainMenuStage {
   private final class_442 mainMenuScreen;
   private final boolean cracked;
   private final AltManagerHeaderView headerView = new AltManagerHeaderView();
   private final AltManagerOuterView outerView;
   private final MelonityTextField loginTextField;
   private final MelonityTextField passwordTextField;
   private String error;

   public MojangAccountStage(class_442 mainMenuScreen, boolean cracked) {
      this.mainMenuScreen = mainMenuScreen;
      this.cracked = cracked;
      Runnable local3_0 = () -> {
         AltService local1_0 = Melonity.INSTANCE.componentManager.service(AltService.class);
         Profile local2_1;
         if ((local2_1 = local1_0.createProfile(ProfileType.CRACK, UsernameGenerator.generateRandomUsername(), null)) != null) {
            local1_0.getProfiles().add(local2_1);
            local1_0.save();
            ((IMainMenuStage)mainMenuScreen).setStage(new AltManagerStage(mainMenuScreen));
         }
      };
      if (!cracked) {
         local3_0 = null;
      }

      this.outerView = new AltManagerOuterView(() -> ((IMainMenuStage)mainMenuScreen).setStage(new AltManagerAddStage(mainMenuScreen)), () -> {
      }, local3_0);
      this.loginTextField = new MelonityTextField(cracked ? "Username" : "Username or Email", false, 178.0F, 16.0F);
      this.passwordTextField = new MelonityTextField("Password", false, 178.0F, 16.0F);
      this.error = "";
   }

   @Override
   public void render(float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      MenuStyle local10_0 = Melonity.INSTANCE.styleManager.getStyle();
      this.headerView.insert(x, y, mouseX, mouseY, renderSystem, poseStack);
      boolean local11_1 = false;
      renderSystem.addText(Fonts.INTER_MEDIUM_15, (this.cracked ? "Cracked" : "Mojang") + this.error, x + 12.0F, y + 50.0F, Color.decode("#888888"), poseStack);
      if (this.loginTextField.getText().length() > 16) {
         this.loginTextField.setText(this.loginTextField.getText().substring(0, 16));
      }

      this.loginTextField.insert(local10_0, x + 12.0F, y + 50.0F + 18.0F, mouseX, mouseY, renderSystem, poseStack);
      if (MathUtil.isMouseHoveringRect(
         (double)(x + 12.0F), (double)(y + 50.0F + 18.0F), (double)this.loginTextField.getWidth(), (double)this.loginTextField.getHeight(), mouseX, mouseY
      )) {
         local11_1 = true;
      }

      if (!this.cracked) {
         this.passwordTextField.insert(local10_0, x + 12.0F, y + 50.0F + 36.0F + 5.0F, mouseX, mouseY, renderSystem, poseStack);
         if (MathUtil.isMouseHoveringRect(
            (double)(x + 12.0F),
            (double)(y + 50.0F + 36.0F + 5.0F),
            (double)this.passwordTextField.getWidth(),
            (double)this.passwordTextField.getHeight(),
            mouseX,
            mouseY
         )) {
            local11_1 = true;
         }
      }

      this.outerView.insert(x, y + this.getHeight() - 42.0F, mouseX, mouseY, renderSystem, poseStack);
      renderSystem.addRect(x, y + this.getHeight() - 38.0F, width, 1.0F, Color.decode("#222222"), poseStack);
      if (local11_1) {
         GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.IBEAM);
      } else {
         GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.ARROW);
      }
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      this.outerView.click(x, y + this.getHeight() - 42.0F, mouseX, mouseY, mouseButton);
      boolean local9_0 = MathUtil.isMouseHoveringRect(
         (double)(x + width - 13.0F - 42.0F), (double)(y + this.getHeight() - 12.0F - 16.0F), 42.0, 16.0, mouseX, mouseY
      );
      if (local9_0) {
         if (this.loginTextField.getText().isEmpty() || !this.cracked && this.passwordTextField.getText().isEmpty()) {
            this.error = " (Username or password is empty)";
            return;
         }

         Melonity.INSTANCE
            .componentManager
            .service(AltService.class)
            .getProfiles()
            .add(
               Melonity.INSTANCE
                  .componentManager
                  .service(AltService.class)
                  .createProfile(this.cracked ? ProfileType.CRACK : ProfileType.MOJANG, this.loginTextField.getText(), this.passwordTextField.getText())
            );
         ((IMainMenuStage)this.mainMenuScreen).setStage(new AltManagerStage(this.mainMenuScreen));
      }

      this.loginTextField.click(x, y + 50.0F + 18.0F, mouseX, mouseY, mouseButton);
      if (!this.cracked) {
         this.passwordTextField.click(x, y + 50.0F + 36.0F + 5.0F, mouseX, mouseY, mouseButton);
         if (MathUtil.isMouseHoveringRect((double)(x + 12.0F), (double)(y + 50.0F + 18.0F), 186.0, 16.0, mouseX, mouseY)) {
            this.loginTextField.setFocus(true);
            this.passwordTextField.setFocus(false);
         } else if (MathUtil.isMouseHoveringRect((double)(x + 12.0F), (double)(y + 50.0F + 36.0F + 5.0F), 186.0, 16.0, mouseX, mouseY)) {
            this.loginTextField.setFocus(false);
            this.passwordTextField.setFocus(true);
         }
      } else {
         this.loginTextField.setFocus(true);
      }
   }

   @Override
   public void key(int keyCode, int scanCode, int modifiers) {
      this.loginTextField.key(keyCode, scanCode, modifiers);
      this.passwordTextField.key(keyCode, scanCode, modifiers);
   }

   @Override
   public void scroll(float x, float y, float width, double amount, double mouseX, double mouseY) {
   }

   @Override
   public float getHeight() {
      return this.cracked ? 140.0F : 165.0F;
   }
}
