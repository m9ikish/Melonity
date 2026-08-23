package ru.melonity.util.managers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1792;
import ru.melonity.util.inventory.Filter;
import ru.melonity.util.inventory.InventoryService;

@Environment(EnvType.CLIENT)
public class InventoryManager implements InventoryService {
   private final List<Filter> filters = Arrays.asList(
      new Filter("Default", Maps.newHashMap()), new Filter("Default 2", Maps.newHashMap()), new Filter("Default 3", Maps.newHashMap())
   );
   private Filter filter = this.filters.getFirst();

   @Override
   public Map<Integer, class_1792> getSlots(String filter) {
      for (Filter local3_0 : this.filters) {
         if (local3_0.getName().equals(filter)) {
            return local3_0.getSlots();
         }
      }

      return null;
   }

   @Override
   public ImmutableList<Filter> getFilters() {
      return ImmutableList.copyOf(this.filters);
   }

   @Override
   public Filter currentFilter() {
      return this.filter;
   }

   @Override
   public void setCurrentFilter(Filter filter) {
      this.filter = filter;
   }

   @Override
   public void save(String filter, Map<Integer, class_1792> slots) {
      for (Filter local4_0 : this.filters) {
         if (local4_0.getName().equals(filter)) {
            local4_0.getSlots().clear();
            local4_0.getSlots().putAll(slots);
         }
      }
   }
}
