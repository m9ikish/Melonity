package ru.melonity.feature;

import java.util.List;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.ui.menu.clickui.MenuCategory;

@Environment(EnvType.CLIENT)
public interface FeatureService {
   void registerFeature(Feature var1);

   <T extends Feature> Optional<T> getFeature(Class<T> var1);

   Optional<Feature> getFeature(String var1);

   List<Feature> getFeatures();

   List<Feature> getFeaturesInCategory(MenuCategory var1);

   void sort();
}
