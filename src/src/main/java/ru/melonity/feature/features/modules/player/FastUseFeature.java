package ru.melonity.feature.features.modules.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1799;
import net.minecraft.class_746;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateLivingEvent;

@Environment(EnvType.CLIENT)
public class FastUseFeature extends SimpleFeature {
   public final EventListener<UpdateLivingEvent> onUpdate = e -> {
      if (mc.field_1724 != null) {
         class_746 local1_0 = mc.field_1724;
         class_1799 local2_1 = local1_0.method_6047();
         class_1799 local3_2 = local1_0.method_6079();
         local1_0.method_7357().method_7906(local2_1.method_7909(), 0);
         local1_0.method_7357().method_7906(local3_2.method_7909(), 0);
      }
   };

   public FastUseFeature() {
      super("FastUse", MenuCategory.PLAYER);
   }
}
