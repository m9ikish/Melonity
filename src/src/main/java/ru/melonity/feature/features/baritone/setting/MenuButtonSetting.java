package ru.melonity.feature.features.baritone.setting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.melonity.ui.components.view.impl.ButtonView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class MenuButtonSetting implements MenuFeatureView {
   private final String name;
   private final ButtonView buttonView;
   private final Runnable runnable;

   public MenuButtonSetting(String name, Runnable runnable) {
      this.name = name;
      this.buttonView = new ButtonView(name, 165.0F, 16.0F);
      this.buttonView.setCenterText(true);
      this.runnable = runnable;
   }

   @Override
   public void insert(
      MenuStyle style, float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack, class_332 context
   ) {
      this.buttonView.setText(this.getLangService().get(this.name));
      this.buttonView.insert(x, y, mouseX, mouseY, renderSystem, poseStack);
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      if (MathUtil.isMouseHoveringRect((double)x, (double)y, (double)this.buttonView.getWidth(), (double)this.buttonView.getHeight(), mouseX, mouseY)) {
         this.runnable.run();
      }
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public void release() {
   }

   @Override
   public float getHeight() {
      return 30.0F;
   }

   @Override
   public void resetAnimations() {
   }
}
