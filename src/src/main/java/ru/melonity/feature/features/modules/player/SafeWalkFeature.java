package ru.melonity.feature.features.modules.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2338;
import net.minecraft.class_315;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventPreInput;

@Environment(EnvType.CLIENT)
public class SafeWalkFeature extends SimpleFeature {
   private static final double STEP = 0.75;
   private final EventListener<EventPreInput> onPreInput = event -> {
      if (!mc.field_1724.method_31549().field_7479 && !mc.field_1724.method_6128()) {
         class_315 local2_0 = event.getSettings();
         if (!local2_0.field_1903.method_1434()) {
            double local3_1 = mc.field_1724.method_23317();
            double local5_2 = mc.field_1724.method_23318();
            double local7_3 = mc.field_1724.method_23321();
            float local9_4 = mc.field_1724.method_36454();
            if (local2_0.field_1894.method_1434() && this.isEdge(local3_1, local5_2, local7_3, (double)local9_4)) {
               event.setForward(false);
            }

            if (local2_0.field_1881.method_1434() && this.isEdge(local3_1, local5_2, local7_3, (double)(local9_4 + 180.0F))) {
               event.setBack(false);
            }

            if (local2_0.field_1913.method_1434() && this.isEdge(local3_1, local5_2, local7_3, (double)(local9_4 - 90.0F))) {
               event.setLeft(false);
            }

            if (local2_0.field_1849.method_1434() && this.isEdge(local3_1, local5_2, local7_3, (double)(local9_4 + 90.0F))) {
               event.setRight(false);
            }
         }
      }
   };

   public SafeWalkFeature() {
      super("SafeWalk", MenuCategory.PLAYER);
   }

   private boolean isEdge(double x, double y, double z, double angleDeg) {
      double local9_0 = Math.toRadians(angleDeg);
      double local11_1 = -Math.sin(local9_0) * 0.75;
      double local13_2 = Math.cos(local9_0) * 0.75;
      class_2338 local15_3 = class_2338.method_49637(x + local11_1, y - 1.0, z + local13_2);
      return mc.field_1687.method_8320(local15_3).method_26215();
   }
}
