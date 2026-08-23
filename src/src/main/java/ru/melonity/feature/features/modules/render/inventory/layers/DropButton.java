package ru.melonity.feature.features.modules.render.inventory.layers;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import ru.melonity.font.Fonts;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public final class DropButton implements FilterRenderLayer {
   @Override
   public void render(float x, float y, int mouseX, int mouseY, MenuStyle style, RenderSystem renderSystem, class_4587 matrixStack) {
      renderSystem.addRect(x + 90.0F - 25.0F, y - 24.0F, 50.0F, 16.5F, 8.0F, style.previewValues(), matrixStack);
      if (MathUtil.isMouseHoveringRect((double)(x + 90.0F - 25.0F), (double)(y - 24.0F), 50.0, 16.5, (double)mouseX, (double)mouseY)) {
         renderSystem.addRect(x + 90.0F - 25.0F, y - 24.0F, 50.0F, 16.5F, 8.0F, new Color(255, 255, 255, 10), matrixStack);
      }

      renderSystem.addText(
         Fonts.INTER_REGULAR_12, "Drop", x + 90.0F - Fonts.INTER_REGULAR_12.getStringWidth("Drop") / 2.0F, y - 17.0F, Color.decode("#888888"), matrixStack
      );
   }

   @Override
   public boolean mouseClicked(class_1703 handler, float x, float y, double mouseX, double mouseY) {
      class_310 local8_0 = class_310.method_1551();
      if (MathUtil.isMouseHoveringRect(
         (double)(
            x
               + 180.0F / (float)((Integer)local8_0.field_1690.method_42474().method_41753()).intValue()
               - 50.0F / (float)((Integer)local8_0.field_1690.method_42474().method_41753()).intValue()
         ),
         (double)(y - 48.0F / (float)((Integer)local8_0.field_1690.method_42474().method_41753()).intValue()),
         (double)(100.0F / (float)((Integer)local8_0.field_1690.method_42474().method_41753()).intValue()),
         (double)(33.0F / (float)((Integer)local8_0.field_1690.method_42474().method_41753()).intValue()),
         mouseX,
         mouseY
      )) {
         handler.field_7761.forEach(slot -> local8_0.field_1761.method_2906(0, slot.field_7874, 1, class_1713.field_7795, local8_0.field_1724));
         return true;
      } else {
         return false;
      }
   }
}
