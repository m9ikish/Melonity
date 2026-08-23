package ru.melonity.feature.features.modules.render.inventory.layers;

import com.google.common.collect.Maps;
import java.awt.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1703;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public final class SaveButton implements FilterRenderLayer {
   @Override
   public void render(float x, float y, int mouseX, int mouseY, MenuStyle style, RenderSystem renderSystem, class_4587 matrixStack) {
      renderSystem.addRect(x + 90.0F - 25.0F + 52.0F, y - 24.0F, 16.5F, 16.5F, 8.0F, style.previewValues(), matrixStack);
      renderSystem.addTexture(
         class_2960.method_60656("melonity/images/ui/inventory/save_icon.png"), x + 90.0F - 25.0F + 54.5F, y - 21.0F, 10.5F, 10.0F, matrixStack
      );
      if (MathUtil.isMouseHoveringRect((double)(x + 90.0F - 25.0F + 52.0F), (double)(y - 24.0F), 16.5, 16.5, (double)mouseX, (double)mouseY)) {
         renderSystem.addRect(x + 90.0F - 25.0F + 52.0F, y - 24.0F, 16.5F, 16.5F, 10.0F, new Color(255, 255, 255, 10), matrixStack);
      }
   }

   @Override
   public boolean mouseClicked(class_1703 handler, float x, float y, double mouseX, double mouseY) {
      class_310 local8_7 = class_310.method_1551();
      if (!MathUtil.isMouseHoveringRect(
         (double)(
            x
               + 180.0F / (float)((Integer)local8_7.field_1690.method_42474().method_41753()).intValue()
               - 50.0F / (float)((Integer)local8_7.field_1690.method_42474().method_41753()).intValue()
               + 104.0F / (float)((Integer)local8_7.field_1690.method_42474().method_41753()).intValue()
         ),
         (double)(y - 48.0F / (float)((Integer)local8_7.field_1690.method_42474().method_41753()).intValue()),
         (double)(33.0F / (float)((Integer)local8_7.field_1690.method_42474().method_41753()).intValue()),
         (double)(33.0F / (float)((Integer)local8_7.field_1690.method_42474().method_41753()).intValue()),
         mouseX,
         mouseY
      )) {
         return false;
      } else {
         Map<Integer, class_1792> local9_5 = Maps.newLinkedHashMap();

         for (int local10_1 = 0; local10_1 < 46; local10_1++) {
            if (local10_1 < 5 || local10_1 > 8) {
               class_1799 local11_0 = local8_7.field_1724.method_31548().method_5438(local10_1);
               if (!local11_0.method_7960() && local11_0.method_7909() != class_1802.field_8162) {
                  local9_5.put(local10_1, local11_0.method_7909());
               }
            }
         }

         for (class_1799 local11_3 : local8_7.field_1724.method_31548().field_7548) {
            String local12_2 = local11_3.method_7909().toString();
            if (local12_2.endsWith("helmet")) {
               local9_5.put(5, local11_3.method_7909());
            } else if (local12_2.endsWith("chestplate")) {
               local9_5.put(6, local11_3.method_7909());
            } else if (local12_2.endsWith("leggings")) {
               local9_5.put(7, local11_3.method_7909());
            } else if (local12_2.endsWith("boots")) {
               local9_5.put(8, local11_3.method_7909());
            }
         }

         Collection<class_1792> local10_6 = local9_5.values();
         Iterator<class_1792> local11_4 = local10_6.iterator();

         while (local11_4.hasNext()) {
            if (Collections.frequency(local10_6, local11_4.next()) > 1) {
               local11_4.remove();
            }
         }

         Melonity.INSTANCE.getInventoryService().save(Melonity.INSTANCE.getInventoryService().currentFilter().getName(), local9_5);
         return true;
      }
   }
}
