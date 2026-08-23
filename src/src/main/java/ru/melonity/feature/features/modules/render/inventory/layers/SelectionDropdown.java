package ru.melonity.feature.features.modules.render.inventory.layers;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1703;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.inventory.Filter;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public final class SelectionDropdown implements FilterRenderLayer {
   private boolean showFilters = false;

   @Override
   public void render(float x, float y, int mouseX, int mouseY, MenuStyle style, RenderSystem renderSystem, class_4587 matrixStack) {
      if (this.showFilters) {
         for (int local8_1 = 0; local8_1 < 3; local8_1++) {
            renderSystem.addRect(
               x + 90.0F - 21.5F,
               y - 70.0F + (float)(local8_1 * 27) / 2.0F,
               43.0F,
               14.5F,
               3.0F,
               local8_1 % 2 != 0 ? style.previewValues().brighter() : style.previewValues(),
               matrixStack
            );
            if (MathUtil.isMouseHoveringRect(
               (double)(x + 90.0F - 21.5F), (double)(y - 70.0F + (float)(local8_1 * 27) / 2.0F), 43.0, 14.5, (double)mouseX, (double)mouseY
            )) {
               renderSystem.addRect(x + 90.0F - 21.5F, y - 70.0F + (float)(local8_1 * 27) / 2.0F, 43.0F, 14.5F, 3.0F, new Color(255, 255, 255, 20), matrixStack);
            }

            String local9_0 = ((Filter)Melonity.INSTANCE.getInventoryService().getFilters().get(local8_1)).getName();
            renderSystem.addText(
               Fonts.INTER_REGULAR_13,
               local9_0,
               x + 90.0F - 21.5F + 5.0F,
               y - 70.0F + (float)(local8_1 * 27) / 2.0F + 5.5F,
               Melonity.INSTANCE.getInventoryService().currentFilter().getName().equals(local9_0) ? Color.WHITE : Color.decode("#888888"),
               matrixStack
            );
         }
      }
   }

   @Override
   public boolean mouseClicked(class_1703 handler, float x, float y, double mouseX, double mouseY) {
      class_310 local8_1 = class_310.method_1551();
      if (MathUtil.isMouseHoveringRect(
         (double)(
            x
               + 180.0F / (float)((Integer)local8_1.field_1690.method_42474().method_41753()).intValue()
               - 50.0F / (float)((Integer)local8_1.field_1690.method_42474().method_41753()).intValue()
               - 36.0F / (float)((Integer)local8_1.field_1690.method_42474().method_41753()).intValue()
         ),
         (double)(y - 48.0F / (float)((Integer)local8_1.field_1690.method_42474().method_41753()).intValue()),
         (double)(33.0F / (float)((Integer)local8_1.field_1690.method_42474().method_41753()).intValue()),
         (double)(33.0F / (float)((Integer)local8_1.field_1690.method_42474().method_41753()).intValue()),
         mouseX,
         mouseY
      )) {
         this.showFilters = !this.showFilters;
         return true;
      } else {
         if (this.showFilters) {
            for (int local9_0 = 0; local9_0 < 3; local9_0++) {
               if (MathUtil.isMouseHoveringRect(
                  (double)(
                     x
                        + 180.0F / (float)((Integer)local8_1.field_1690.method_42474().method_41753()).intValue()
                        - 43.0F / (float)((Integer)local8_1.field_1690.method_42474().method_41753()).intValue()
                  ),
                  (double)(
                     y
                        - 140.0F / (float)((Integer)local8_1.field_1690.method_42474().method_41753()).intValue()
                        + (float)(local9_0 * 27) / (float)((Integer)local8_1.field_1690.method_42474().method_41753()).intValue()
                  ),
                  (double)(86.0F / (float)((Integer)local8_1.field_1690.method_42474().method_41753()).intValue()),
                  (double)(29.0F / (float)((Integer)local8_1.field_1690.method_42474().method_41753()).intValue()),
                  mouseX,
                  mouseY
               )) {
                  Melonity.INSTANCE.getInventoryService().setCurrentFilter((Filter)Melonity.INSTANCE.getInventoryService().getFilters().get(local9_0));
                  Melonity.INSTANCE.getFilter().setEnabled(true);
                  this.showFilters = false;
                  return true;
               }
            }
         }

         return false;
      }
   }
}
