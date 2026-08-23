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
import ru.melonity.proxy.MelonityProxy;
import ru.melonity.proxy.ProxyManager;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScissorUtil;

@Environment(EnvType.CLIENT)
public class ProxyStage implements MainMenuStage {
   private final class_442 mainMenuScreen;
   private float scrollMy = 0.0F;

   public ProxyStage(class_442 mainMenuScreen) {
      this.mainMenuScreen = mainMenuScreen;
   }

   @Override
   public void render(float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      MenuStyle local10_2 = Melonity.INSTANCE.styleManager.getStyle();
      renderSystem.addRectGradientCorners(
         x,
         y,
         width,
         30.0F,
         0.0F,
         12.0F,
         12.0F,
         0.0F,
         local10_2.background(),
         local10_2.background(),
         local10_2.background(),
         local10_2.background(),
         poseStack
      );
      renderSystem.addText(Fonts.INTER_MEDIUM_15, "Proxy List", x + 6.0F, y + 14.0F, poseStack);
      renderSystem.addRect(x, y + 30.0F, width, 1.0F, Color.decode("#222222"), poseStack);
      ProxyManager local11_3 = Melonity.INSTANCE.getProxyManager();
      float local12_4 = y + 50.0F + (float)(local11_3.getMyProxy().isEmpty() ? -8 : 0);
      if (!local11_3.getMyProxy().isEmpty()) {
         renderSystem.addText(Fonts.INTER_REGULAR_12, "Custom proxys", x + 6.0F, y + 41.0F, Color.decode("#888888"), poseStack);
         ScissorUtil.beginScissor((double)x, (double)local12_4, (double)width, (double)(local11_3.getMyProxy().size() * 22 - 2));

         for (MelonityProxy local14_0 : local11_3.getMyProxy()) {
            if (local11_3.getSelectedProxy() != null && local14_0.raw().equals(local11_3.getSelectedProxy().raw())) {
               renderSystem.addRect(
                  x + 8.0F - 0.5F,
                  local12_4 + this.scrollMy - 0.5F,
                  width - 16.0F + 1.0F,
                  18.0F,
                  6.0F,
                  Melonity.INSTANCE.styleManager.getStyle().ascent(),
                  poseStack
               );
            }

            renderSystem.addRect(x + 8.0F, local12_4 + this.scrollMy, width - 16.0F, 17.0F, 6.0F, Color.decode("#222222"), poseStack);
            renderSystem.addText(Fonts.INTER_REGULAR_13, local14_0.raw(), x + 12.0F, local12_4 + 7.0F + this.scrollMy, Color.decode("#888888"), poseStack);
            renderSystem.addText(
               Fonts.INTER_REGULAR_13,
               String.format("%dms", local14_0.ping()),
               x + width - 10.0F - Fonts.INTER_REGULAR_13.getStringWidth(String.format("%dms", local14_0.ping())) - 4.0F,
               local12_4 + 7.0F + this.scrollMy,
               Color.GRAY,
               poseStack
            );
            local12_4 += 22.0F;
         }

         ScissorUtil.endScissor();
         local12_4 += 5.0F;
      }

      renderSystem.addText(Fonts.INTER_REGULAR_12, "Free proxys", x + 6.0F, local12_4, Color.decode("#888888"), poseStack);
      local12_4 += 10.0F;
      ScissorUtil.beginScissor(
         (double)x, (double)local12_4, (double)width, local11_3.getMyProxy().isEmpty() ? 140.0 : (double)(local11_3.getMyProxy().size() * 22)
      );

      for (MelonityProxy local14_1 : local11_3.getProxy()) {
         if (local11_3.getSelectedProxy() != null && local14_1.raw().equals(local11_3.getSelectedProxy().raw())) {
            renderSystem.addRect(x + 8.0F - 0.5F, local12_4 + this.scrollMy - 0.5F, width - 16.0F + 1.0F, 18.0F, 6.0F, local10_2.ascent(), poseStack);
         }

         renderSystem.addRect(x + 8.0F, local12_4 + this.scrollMy, width - 16.0F, 17.0F, 6.0F, local10_2.background().darker(), poseStack);
         renderSystem.addText(Fonts.INTER_REGULAR_13, local14_1.raw(), x + 12.0F, local12_4 + 7.0F + this.scrollMy, Color.decode("#888888"), poseStack);
         renderSystem.addText(
            Fonts.INTER_REGULAR_13,
            String.format("%dms", local14_1.ping()),
            x + width - 10.0F - Fonts.INTER_REGULAR_13.getStringWidth(String.format("%dms", local14_1.ping())) - 4.0F,
            local12_4 + 7.0F + this.scrollMy,
            Color.GRAY,
            poseStack
         );
         local12_4 += 22.0F;
      }

      ScissorUtil.endScissor();
      renderSystem.addRect(x, y + 166.0F, width, 34.0F, 12.0F, local10_2.background(), poseStack);
      boolean local13_5 = MathUtil.isMouseHoveringRect((double)(x + 8.0F), (double)(y + 166.0F + 9.0F), 60.0, 15.0, mouseX, mouseY);
      renderSystem.addRect(x + 8.0F - 0.3F, y + 166.0F + 9.0F - 0.3F, 60.6F, 17.0F, 8.0F, Color.decode("#2C2C2C"), poseStack);
      renderSystem.addRect(x + 8.0F, y + 166.0F + 9.0F, 60.0F, 15.0F, 8.0F, local13_5 ? Color.decode("#2C2C2C") : Color.decode("#222222"), poseStack);
      renderSystem.addText(
         Fonts.INTER_REGULAR_13,
         "Add",
         x + 8.0F + 30.0F - Fonts.INTER_REGULAR_13.getStringWidth("Add") / 2.0F,
         y + 166.0F + 16.0F,
         Color.decode("#888888"),
         poseStack
      );
      boolean local14_6 = MathUtil.isMouseHoveringRect((double)(x + 8.0F + 64.0F), (double)(y + 166.0F + 9.0F), 60.0, 15.0, mouseX, mouseY);
      renderSystem.addRect(x + 8.0F + 64.0F, y + 166.0F + 9.0F, 60.0F, 15.0F, 8.0F, local14_6 ? local10_2.ascent().darker() : local10_2.ascent(), poseStack);
      renderSystem.addText(
         Fonts.INTER_REGULAR_13,
         "Choose",
         x + 64.0F + 8.0F + 30.0F - Fonts.INTER_REGULAR_13.getStringWidth("Choose") / 2.0F,
         y + 166.0F + 16.0F,
         Color.BLACK,
         poseStack
      );
      boolean local15_7 = MathUtil.isMouseHoveringRect((double)(x + 8.0F + 128.0F), (double)(y + 166.0F + 9.0F), 60.0, 15.0, mouseX, mouseY);
      renderSystem.addRect(x + 8.0F + 128.0F - 0.3F, y + 166.0F + 9.0F - 0.3F, 60.6F, 17.0F, 8.0F, Color.decode("#2C2C2C"), poseStack);
      renderSystem.addRect(x + 8.0F + 128.0F, y + 166.0F + 9.0F, 60.0F, 15.0F, 8.0F, local15_7 ? Color.decode("#2C2C2C") : Color.decode("#222222"), poseStack);
      renderSystem.addText(
         Fonts.INTER_REGULAR_13,
         "Cancel",
         x + 8.0F + 128.0F + 30.0F - Fonts.INTER_REGULAR_13.getStringWidth("Cancel") / 2.0F,
         y + 166.0F + 16.0F,
         Color.decode("#888888"),
         poseStack
      );
      GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.ARROW);
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      ProxyManager local9_2 = Melonity.INSTANCE.getProxyManager();
      float local10_3 = y + 50.0F + (float)(local9_2.getMyProxy().isEmpty() ? -8 : 0);
      if (!local9_2.getMyProxy().isEmpty()) {
         if (MathUtil.isMouseHoveringRect((double)x, (double)local10_3, (double)width, 44.0, mouseX, mouseY)) {
            for (MelonityProxy local12_0 : local9_2.getMyProxy()) {
               if (MathUtil.isMouseHoveringRect((double)(x + 8.0F), (double)(local10_3 + this.scrollMy), (double)(width - 16.0F), 17.0, mouseX, mouseY)) {
                  local9_2.setSelectedProxy(local12_0);
               }

               local10_3 += 22.0F;
            }
         }

         local10_3 = y + 50.0F + 44.0F + 15.0F;
      }

      if (MathUtil.isMouseHoveringRect(
         (double)x, (double)local10_3, (double)width, local9_2.getMyProxy().isEmpty() ? 140.0 : (double)(local9_2.getMyProxy().size() * 22), mouseX, mouseY
      )) {
         for (MelonityProxy local12_1 : local9_2.getProxy()) {
            if (MathUtil.isMouseHoveringRect((double)(x + 8.0F), (double)(local10_3 + this.scrollMy), (double)(width - 16.0F), 17.0, mouseX, mouseY)) {
               local9_2.setSelectedProxy(local12_1);
            }

            local10_3 += 22.0F;
         }
      }

      boolean local11_4 = MathUtil.isMouseHoveringRect((double)(x + 8.0F), (double)(y + 166.0F + 9.0F), 60.0, 15.0, mouseX, mouseY);
      if (local11_4) {
         ((IMainMenuStage)this.mainMenuScreen).setStage(new ProxyAddStage(this.mainMenuScreen));
      }

      boolean local12_5 = MathUtil.isMouseHoveringRect((double)(x + 8.0F + 64.0F), (double)(y + 166.0F + 9.0F), 60.0, 15.0, mouseX, mouseY);
      if (local12_5) {
         ((IMainMenuStage)this.mainMenuScreen).setStage(new ButtonsStage(this.mainMenuScreen));
      }

      boolean local13_6 = MathUtil.isMouseHoveringRect((double)(x + 8.0F + 128.0F), (double)(y + 166.0F + 9.0F), 60.0, 15.0, mouseX, mouseY);
      if (local13_6) {
         ((IMainMenuStage)this.mainMenuScreen).setStage(new ButtonsStage(this.mainMenuScreen));
      }
   }

   @Override
   public void key(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         ((IMainMenuStage)this.mainMenuScreen).setStage(new ButtonsStage(this.mainMenuScreen));
      }
   }

   @Override
   public void scroll(float x, float y, float width, double amount, double mouseX, double mouseY) {
      if (Melonity.INSTANCE.getProxyManager().getProxy().size() + Melonity.INSTANCE.getProxyManager().getMyProxy().size() > 5) {
         this.scrollMy += (float)(amount * 4.0);
      }

      if (this.scrollMy > 0.0F) {
         this.scrollMy = 0.0F;
      }
   }

   @Override
   public float getHeight() {
      return 200.0F;
   }
}
