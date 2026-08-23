package ru.melonity.ui.menu.clickui.view.feature.setting;

import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class MenuColorSetting implements MenuFeatureView {
   private final String name;
   private Color color;

   public MenuColorSetting(String name) {
      this.name = name;
      this.color = Melonity.INSTANCE.styleManager.getStyle().ascent();
   }

   public MenuColorSetting(String name, Color color) {
      this.name = name;
      this.color = color;
   }

   @Override
   public void insert(
      MenuStyle style, float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack, class_332 context
   ) {
      renderSystem.addText(Fonts.INTER_MEDIUM_14, this.getLangService().get(this.name), x, y + 3.0F, poseStack);
      float swatchX = x + width - 12.0F - 18.0F;
      renderSystem.addRect(swatchX - 0.5F, y - 0.5F, 13.0F, 13.0F, 4.5F, new Color(136, 136, 136, 34), poseStack);
      renderSystem.addRect(swatchX, y, 12.0F, 12.0F, 4.0F, this.color, poseStack);
      if (MathUtil.isMouseHoveringRect((double)swatchX, (double)y, 12.0, 12.0, mouseX, mouseY)) {
         renderSystem.addRect(swatchX, y, 12.0F, 12.0F, 4.0F, new Color(255, 255, 255, 30), poseStack);
         Melonity.hand = true;
      }
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      if (MathUtil.isMouseHoveringRect((double)(x + width - 12.0F - 18.0F), (double)y, 12.0, 12.0, mouseX, mouseY)) {
         Melonity.INSTANCE.getMenu().getColorView().setColor(this.color);
         Melonity.INSTANCE.getMenu().getColorView().setSetting(Melonity.INSTANCE.getMenu().getColorView().getSetting() == null ? this : null);
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
      return 24.0F;
   }

   @Override
   public void resetAnimations() {
   }

   @Generated
   public void setColor(Color color) {
      this.color = color;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public Color getColor() {
      return this.color;
   }
}
