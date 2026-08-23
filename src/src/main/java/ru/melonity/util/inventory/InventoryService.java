package ru.melonity.util.inventory;

import com.google.common.collect.ImmutableList;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1792;

@Environment(EnvType.CLIENT)
public interface InventoryService {
   Map<Integer, class_1792> getSlots(String var1);

   ImmutableList<Filter> getFilters();

   Filter currentFilter();

   void setCurrentFilter(Filter var1);

   void save(String var1, Map<Integer, class_1792> var2);
}
