package ru.melonity.feature.features.baritone.setting;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class MenuCoordSetting implements MenuFeatureView {
   private final String name;
   private final MelonityTextField xTextField;
   private final MelonityTextField yTextField;
   private final MelonityTextField zTextField;

   public MenuCoordSetting(String name, String x, String y, String z) {
      this.name = name;
      this.xTextField = new MelonityTextField(x, false, 40.0F, 15.0F);
      this.yTextField = new MelonityTextField(y, false, 40.0F, 15.0F);
      this.zTextField = new MelonityTextField(z, false, 40.0F, 15.0F);
      this.xTextField.setWritable(true);
      this.yTextField.setWritable(true);
      this.zTextField.setWritable(true);
      this.xTextField.setAlwaysHint(true);
      this.yTextField.setAlwaysHint(true);
      this.zTextField.setAlwaysHint(true);
   }

   @Override
   public void insert(
      MenuStyle style, float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack, class_332 context
   ) {
      renderSystem.addText(Fonts.INTER_MEDIUM_14, this.getLangService().get(this.name), x, y, poseStack);
      this.xTextField.insert(style, x, y + 10.0F, mouseX, mouseY, renderSystem, poseStack);
      this.yTextField.insert(style, x + 45.0F, y + 10.0F, mouseX, mouseY, renderSystem, poseStack);
      this.zTextField.insert(style, x + 90.0F, y + 10.0F, mouseX, mouseY, renderSystem, poseStack);
      if (this.xTextField.getText().isEmpty() && !this.xTextField.isFocus()) {
         this.xTextField.setText("0");
      }

      if (this.yTextField.getText().isEmpty() && !this.yTextField.isFocus()) {
         this.yTextField.setText("0");
      }

      if (this.zTextField.getText().isEmpty() && !this.zTextField.isFocus()) {
         this.zTextField.setText("0");
      }

      this.xTextField.setText(this.xTextField.getText().length() > 5 ? this.xTextField.getText().substring(0, 4) : this.xTextField.getText());
      this.yTextField.setText(this.yTextField.getText().length() > 5 ? this.yTextField.getText().substring(0, 4) : this.yTextField.getText());
      this.zTextField.setText(this.zTextField.getText().length() > 5 ? this.zTextField.getText().substring(0, 4) : this.zTextField.getText());
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      if (MathUtil.isMouseHoveringRect((double)x, (double)(y + 10.0F), (double)this.xTextField.getWidth(), (double)this.xTextField.getHeight(), mouseX, mouseY)
         )
       {
         this.xTextField.setFocus(true);
         this.yTextField.setFocus(false);
         this.zTextField.setFocus(false);
      } else if (MathUtil.isMouseHoveringRect(
         (double)(x + 45.0F), (double)(y + 10.0F), (double)this.xTextField.getWidth(), (double)this.xTextField.getHeight(), mouseX, mouseY
      )) {
         this.xTextField.setFocus(false);
         this.yTextField.setFocus(true);
         this.zTextField.setFocus(false);
      } else if (MathUtil.isMouseHoveringRect(
         (double)(x + 90.0F), (double)(y + 10.0F), (double)this.xTextField.getWidth(), (double)this.xTextField.getHeight(), mouseX, mouseY
      )) {
         this.xTextField.setFocus(false);
         this.yTextField.setFocus(false);
         this.zTextField.setFocus(true);
      }

      this.xTextField.click(x, y + 10.0F, mouseX, mouseY, mouseButton);
      this.yTextField.click(x + 45.0F, y + 10.0F, mouseX, mouseY, mouseButton);
      this.zTextField.click(x + 90.0F, y + 10.0F, mouseX, mouseY, mouseButton);
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 48
         || keyCode == 49
         || keyCode == 50
         || keyCode == 51
         || keyCode == 52
         || keyCode == 53
         || keyCode == 54
         || keyCode == 55
         || keyCode == 56
         || keyCode == 57
         || keyCode == 259
         || keyCode == 256) {
         this.xTextField.key(keyCode, scanCode, modifiers);
         this.yTextField.key(keyCode, scanCode, modifiers);
         this.zTextField.key(keyCode, scanCode, modifiers);
      }

      if (keyCode == 257) {
         this.handleEnter(this.xTextField);
         this.handleEnter(this.yTextField);
         this.handleEnter(this.zTextField);
      }

      return false;
   }

   private void handleEnter(MelonityTextField textField) {
      if (textField.isWritable() && textField.isFocus()) {
         textField.setFocus(false);

         try {
            textField.setText(String.valueOf(Integer.parseInt(textField.getText().replace(',', '.'))));
         } catch (NumberFormatException var3) {
         }
      }
   }

   @Override
   public void release() {
   }

   @Override
   public float getHeight() {
      return 36.0F;
   }

   @Override
   public void resetAnimations() {
      this.xTextField.setFocus(false);
      this.yTextField.setFocus(false);
      this.zTextField.setFocus(false);
   }

   public int getX() {
      try {
         return Integer.parseInt(this.xTextField.getText());
      } catch (Throwable var2) {
         return 0;
      }
   }

   public int getY() {
      try {
         return Integer.parseInt(this.yTextField.getText());
      } catch (Throwable var2) {
         return 0;
      }
   }

   public int getZ() {
      try {
         return Integer.parseInt(this.zTextField.getText());
      } catch (Throwable var2) {
         return 0;
      }
   }
}
