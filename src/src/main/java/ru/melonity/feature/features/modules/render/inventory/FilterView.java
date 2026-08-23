package ru.melonity.feature.features.modules.render.inventory;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1703;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.inventory.layers.DropButton;
import ru.melonity.feature.features.modules.render.inventory.layers.FilterRenderLayer;
import ru.melonity.feature.features.modules.render.inventory.layers.LayerSelection;
import ru.melonity.feature.features.modules.render.inventory.layers.SaveButton;
import ru.melonity.feature.features.modules.render.inventory.layers.SelectionDropdown;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;

@Environment(EnvType.CLIENT)
public final class FilterView {
   private static final List<FilterRenderLayer> LAYERS = List.of(new LayerSelection(), new DropButton(), new SelectionDropdown(), new SaveButton());

   public void insert(float x, float y, int mouseX, int mouseY, class_4587 matrixStack) {
      RenderSystem local6_0 = Melonity.INSTANCE.getRenderSystem();
      MenuStyle local7_1 = Melonity.INSTANCE.getStyleManager().getStyle();
      ScaleUtils.rescale(2.0);
      LAYERS.forEach(layer -> layer.render(x, y, mouseX, mouseY, local7_1, local6_0, matrixStack));
      ScaleUtils.rescaleMC();
   }

   public boolean mouseClicked(class_1703 handler, double mouseX, double mouseY) {
      class_310 local6_0 = class_310.method_1551();
      float local7_1 = (float)local6_0.method_22683().method_4486() / 2.0F
         - 180.0F / (float)((Integer)local6_0.field_1690.method_42474().method_41753()).intValue();
      float local8_2 = (float)local6_0.method_22683().method_4502() / 2.0F
         - 170.0F / (float)((Integer)local6_0.field_1690.method_42474().method_41753()).intValue();
      return LAYERS.stream().anyMatch(layer -> layer.mouseClicked(handler, local7_1, local8_2, mouseX, mouseY));
   }
}
