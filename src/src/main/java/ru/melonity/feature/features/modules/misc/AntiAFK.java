package ru.melonity.feature.features.modules.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_3532;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.minecraft.MovementUtil;

@Environment(EnvType.CLIENT)
public class AntiAFK extends SimpleFeature {
   private final EventListener<UpdateLivingEvent> onUpdate = event -> {
      if (mc.field_1724 != null && !MovementUtil.isMoving()) {
         float local1_0 = (float)Math.toRadians((double)(9 * (mc.field_1724.field_6012 % 40)));
         float local2_1 = (float)class_3532.method_15350(Math.sin((double)local1_0), -1.0, 1.0);
         float local3_2 = (float)class_3532.method_15350(Math.cos((double)local1_0), -1.0, 1.0);
         mc.field_1724.field_3913.field_3905 = (float)Math.round(local2_1);
         mc.field_1724.field_3913.field_3907 = (float)Math.round(local3_2);
      }

      if (mc.field_1724.method_24828()) {
         mc.field_1724.method_6043();
      }
   };

   public AntiAFK() {
      super("AntiAFK", MenuCategory.MISC);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      if (value) {
         mc.field_1690.field_1832.method_23481(true);
      }
   }
}
