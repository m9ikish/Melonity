package ru.melonity.ui.menu.clickui.view.feature.setting;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class MenuBooleanRowSetting implements MenuFeatureView {
   private final BooleanSetting setting1;
   private final BooleanSetting setting2;

   public MenuBooleanRowSetting(BooleanSetting setting1, BooleanSetting setting2) {
      this.setting1 = setting1;
      this.setting2 = setting2;
   }

   @Override
   public void insert(
      MenuStyle style, float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack, class_332 context
   ) {
      this.setting1.insert(style, x, y, width, mouseX, mouseY, renderSystem, poseStack, context);
      this.setting2.insert(style, x + 75.0F, y, width, mouseX, mouseY, renderSystem, poseStack, context);
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      this.setting1.click(x, y, width, mouseX, mouseY, mouseButton);
      this.setting2.click(x + 75.0F, y, width, mouseX, mouseY, mouseButton);
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public void release() {
      this.setting1.release();
      this.setting2.release();
   }

   @Override
   public float getHeight() {
      return this.setting1.getHeight();
   }

   @Override
   public void resetAnimations() {
      this.setting1.resetAnimations();
      this.setting2.resetAnimations();
   }

   @Generated
   public BooleanSetting getSetting1() {
      return this.setting1;
   }

   @Generated
   public BooleanSetting getSetting2() {
      return this.setting2;
   }
}
