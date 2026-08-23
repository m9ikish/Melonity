package ru.melonity.feature.features.modules.render.inventory.layers;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1703;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public final class LayerSelection implements FilterRenderLayer {
   @Override
   public void render(float x, float y, int mouseX, int mouseY, MenuStyle style, RenderSystem renderSystem, class_4587 matrixStack) {
      renderSystem.addRect(x + 90.0F - 25.0F - 18.0F, y - 24.0F, 16.5F, 16.5F, 8.0F, style.previewValues(), matrixStack);
      renderSystem.addTexture(class_2960.method_60656("melonity/images/ui/inventory/filter.png"), x + 90.0F - 25.0F - 14.0F, y - 20.0F, 8.5F, 8.0F, matrixStack);
      if (MathUtil.isMouseHoveringRect((double)(x + 90.0F - 25.0F - 18.0F), (double)(y - 24.0F), 16.5, 16.5, (double)mouseX, (double)mouseY)) {
         renderSystem.addRect(x + 90.0F - 25.0F - 18.0F, y - 24.0F, 16.5F, 16.5F, 10.0F, new Color(255, 255, 255, 10), matrixStack);
      }
   }

   @Override
   public boolean mouseClicked(class_1703 handler, float x, float y, double mouseX, double mouseY) {
      return false;
   }
}
