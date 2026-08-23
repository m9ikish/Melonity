package ru.melonity.feature.features.modules.render;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.features.modules.render.inventory.FilterView;
import ru.melonity.feature.features.modules.render.inventory.InventoryView;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;

@Environment(EnvType.CLIENT)
public class CustomInventory extends SimpleFeature {
   private final InventoryView view = new InventoryView();
   private final FilterView filterView = new FilterView();

   public CustomInventory() {
      super("CustomInventory", MenuCategory.RENDER);
   }

   @Generated
   public InventoryView view() {
      return this.view;
   }

   @Generated
   public FilterView filterView() {
      return this.filterView;
   }
}
