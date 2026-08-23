package ru.melonity.feature;

import lombok.NonNull;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import org.jetbrains.annotations.Nullable;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureFrame;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.event.annotation.ListenerClass;

@Environment(EnvType.CLIENT)
@ListenerClass
public interface Feature {
   class_310 mc = class_310.method_1551();

   @NonNull
   String getName();

   @NonNull
   MenuCategory getCategory();

   @NonNull
   MenuFeatureFrame getMenuObject();

   @Nullable
   BindSetting getBindSetting();

   boolean isEnabled();

   void setEnabled(boolean var1, boolean var2);

   boolean isSafe();

   StateAnimation getBindsAnimation();
}
