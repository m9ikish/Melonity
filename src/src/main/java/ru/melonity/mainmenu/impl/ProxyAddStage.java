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
import ru.melonity.mainmenu.MainMenuStage;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class ProxyAddStage implements MainMenuStage {
   private final class_442 mainMenuScreen;
   private final MelonityTextField textField;
   private String error = "";

   public ProxyAddStage(class_442 mainMenuScreen) {
      this.mainMenuScreen = mainMenuScreen;
      this.textField = new MelonityTextField("Your link", false, 186.0F, 16.0F);
      this.textField.setFocus(true);
   }

   @Override
   public void render(float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      MenuStyle local10_0 = Melonity.INSTANCE.styleManager.getStyle();
      renderSystem.addRectGradientCorners(
         x,
         y,
         width,
         30.0F,
         0.0F,
         12.0F,
         12.0F,
         0.0F,
         local10_0.background(),
         local10_0.background(),
         local10_0.background(),
         local10_0.background(),
         poseStack
      );
      renderSystem.addText(Fonts.INTER_MEDIUM_15, "Add proxy", x + 6.0F, y + 14.0F, poseStack);
      renderSystem.addRect(x, y + 30.0F, width, 1.0F, Color.decode("#222222"), poseStack);
      renderSystem.addText(Fonts.INTER_REGULAR_13, this.error, x + 8.0F, y + 57.0F, Color.decode("#888888"), poseStack);
      this.textField.insert(local10_0, x + 8.0F, y + 50.0F + 18.0F, mouseX, mouseY, renderSystem, poseStack);
      boolean local11_1 = false;
      if (MathUtil.isMouseHoveringRect(
         (double)(x + 8.0F), (double)(y + 50.0F + 18.0F), (double)this.textField.getWidth(), (double)this.textField.getHeight(), mouseX, mouseY
      )) {
         local11_1 = true;
      }

      renderSystem.addRect(x, y + 116.0F, width, 34.0F, 12.0F, local10_0.background(), poseStack);
      boolean local12_2 = MathUtil.isMouseHoveringRect((double)(x + 8.0F), (double)(y + 116.0F + 9.0F), 92.0, 15.0, mouseX, mouseY);
      renderSystem.addRect(x + 8.0F - 0.3F, y + 116.0F + 9.0F - 0.3F, 92.6F, 17.0F, 8.0F, Color.decode("#2C2C2C"), poseStack);
      renderSystem.addRect(x + 8.0F, y + 116.0F + 9.0F, 92.0F, 15.0F, 8.0F, local12_2 ? Color.decode("#2C2C2C") : Color.decode("#222222"), poseStack);
      renderSystem.addText(
         Fonts.INTER_REGULAR_13,
         "Cancel",
         x + 8.0F + 46.0F - Fonts.INTER_REGULAR_13.getStringWidth("Cancel") / 2.0F,
         y + 116.0F + 16.0F,
         Color.decode("#888888"),
         poseStack
      );
      boolean local13_3 = MathUtil.isMouseHoveringRect((double)(x + 8.0F + 96.0F), (double)(y + 116.0F + 9.0F), 92.0, 17.0, mouseX, mouseY);
      renderSystem.addRect(x + 8.0F + 96.0F, y + 116.0F + 9.0F, 92.0F, 17.0F, 8.0F, local13_3 ? local10_0.ascent().darker() : local10_0.ascent(), poseStack);
      renderSystem.addText(
         Fonts.INTER_REGULAR_13,
         "Add",
         x + 96.0F + 8.0F + 46.0F - Fonts.INTER_REGULAR_13.getStringWidth("Add") / 2.0F,
         y + 116.0F + 17.0F,
         Color.BLACK,
         poseStack
      );
      if (local11_1) {
         GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.IBEAM);
      } else {
         GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.ARROW);
      }
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      this.textField.click(x, y + 50.0F + 18.0F, mouseX, mouseY, mouseButton);
      boolean local9_1 = MathUtil.isMouseHoveringRect((double)(x + 8.0F), (double)(y + 116.0F + 9.0F), 92.0, 15.0, mouseX, mouseY);
      if (local9_1) {
         ((IMainMenuStage)this.mainMenuScreen).setStage(new ProxyStage(this.mainMenuScreen));
      }

      boolean local10_2 = MathUtil.isMouseHoveringRect((double)(x + 8.0F + 96.0F), (double)(y + 116.0F + 9.0F), 92.0, 17.0, mouseX, mouseY);
      if (local10_2) {
         try {
            Melonity.INSTANCE.getProxyManager().addProxy(this.textField.getText(), true);
         } catch (Exception var12) {
            this.error = var12.getMessage();
            return;
         }

         ((IMainMenuStage)this.mainMenuScreen).setStage(new ProxyStage(this.mainMenuScreen));
      }
   }

   @Override
   public void key(int keyCode, int scanCode, int modifiers) {
      this.textField.key(keyCode, scanCode, modifiers);
   }

   @Override
   public void scroll(float x, float y, float width, double amount, double mouseX, double mouseY) {
   }

   @Override
   public float getHeight() {
      return 150.0F;
   }
}
