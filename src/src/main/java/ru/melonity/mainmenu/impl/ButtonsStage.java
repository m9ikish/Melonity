package ru.melonity.mainmenu.impl;

import com.google.common.collect.Maps;
import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import net.minecraft.class_429;
import net.minecraft.class_442;
import net.minecraft.class_4587;
import net.minecraft.class_500;
import net.minecraft.class_526;
import org.lwjgl.glfw.GLFW;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IMainMenuStage;
import ru.melonity.font.Fonts;
import ru.melonity.mainmenu.MainMenuStage;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class ButtonsStage implements MainMenuStage {
   private final Map<String, Runnable> buttons;

   public ButtonsStage(class_442 mainMenuScreen) {
      class_310 local2_0 = class_310.method_1551();
      this.buttons = Maps.newLinkedHashMap();
      this.buttons.put("SinglePlayer", () -> local2_0.method_1507(new class_526(mainMenuScreen)));
      this.buttons.put("MultiPlayer", () -> local2_0.method_1507(new class_500(mainMenuScreen)));
      this.buttons.put("AltManager", () -> ((IMainMenuStage)mainMenuScreen).setStage(new AltManagerStage(mainMenuScreen)));
      this.buttons.put("Proxy", () -> ((IMainMenuStage)mainMenuScreen).setStage(new ProxyStage(mainMenuScreen)));
      this.buttons.put("Options", () -> local2_0.method_1507(new class_429(mainMenuScreen, local2_0.field_1690)));
      this.buttons.put("Leave", local2_0::close);
   }

   @Override
   public void render(float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      float local10_4 = y + 9.0F;
      MenuStyle local11_5 = Melonity.INSTANCE.styleManager.getStyle();
      boolean local12_6 = false;

      for (Entry<String, Runnable> local14_3 : this.buttons.entrySet()) {
         boolean local15_0 = MathUtil.isMouseHoveringRect((double)(x + 4.0F), (double)local10_4, (double)(width - 8.0F), 28.0, mouseX, mouseY);
         Color local16_1 = new Color(local11_5.background().getRed() + 4, local11_5.background().getGreen() + 4, local11_5.background().getBlue() + 4);
         renderSystem.addRect(x + 4.0F, local10_4, width - 8.0F, 28.0F, 4.0F, local15_0 ? local16_1.darker() : local16_1, poseStack);
         if (local15_0) {
            local12_6 = true;
         }

         GlyphData local17_2 = Fonts.INTER_MEDIUM_18;
         renderSystem.addText(
            local17_2,
            local14_3.getKey(),
            x + width / 2.0F - local17_2.getStringWidth(local14_3.getKey()) / 2.0F,
            local10_4 + 14.0F - local17_2.getStringHeight(local14_3.getKey()) / 2.0F + 2.0F,
            poseStack
         );
         local10_4 += 32.0F;
      }

      if (local12_6) {
         GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.HAND);
      } else {
         GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.ARROW);
      }
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      float local9_2 = y + 9.0F;

      for (Entry<String, Runnable> local11_1 : this.buttons.entrySet()) {
         boolean local12_0 = MathUtil.isMouseHoveringRect((double)(x + 4.0F), (double)local9_2, (double)(width - 8.0F), 28.0, mouseX, mouseY);
         if (local12_0) {
            local11_1.getValue().run();
            return;
         }

         local9_2 += 32.0F;
      }
   }

   @Override
   public void key(int keyCode, int scanCode, int modifiers) {
   }

   @Override
   public void scroll(float x, float y, float width, double amount, double mouseX, double mouseY) {
   }

   @Override
   public float getHeight() {
      return 218.4F;
   }
}
