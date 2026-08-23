package ru.melonity.ui.menu.clickui.view;

import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.StyledView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class MenuFeatureHeader extends StyledView {
   private String title;
   private String subtitle;

   public MenuFeatureHeader(String title, String subtitle, float width, float height) {
      super(width, height);
      this.title = title;
      this.subtitle = subtitle;
   }

   public MenuFeatureHeader(String title, String subtitle) {
      this(title, subtitle, 179.0F, 215.0F);
   }

   @Override
   public void insert(MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      renderSystem.addRect(x - 0.5F, y - 0.5F, this.width + 1.0F, this.height + 1.0F, 8.5F, style.background().brighter(), poseStack);
      renderSystem.addRect(x, y, this.width, this.height, 8.0F, style.feature().background(), poseStack);
      renderSystem.addText(Fonts.INTER_MEDIUM_14, this.title, x + 8.0F, y + 11.0F, poseStack);
      renderSystem.addText(Fonts.INTER_REGULAR_13, this.subtitle, x + 8.0F, y + 22.0F, Color.decode("#888888"), poseStack);
      renderSystem.addRect(x + 8.0F, y + 33.0F, this.width - 16.0F, 1.0F, Color.decode("#222222"), poseStack);
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public void release() {
   }

   @Generated
   public void setTitle(String title) {
      this.title = title;
   }

   @Generated
   public void setSubtitle(String subtitle) {
      this.subtitle = subtitle;
   }

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public String getSubtitle() {
      return this.subtitle;
   }
}
