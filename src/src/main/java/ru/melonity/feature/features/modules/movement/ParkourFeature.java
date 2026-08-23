package ru.melonity.feature.features.modules.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2338;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventInput;

@Environment(EnvType.CLIENT)
public class ParkourFeature extends SimpleFeature {
   public final EventListener<EventInput> onInput = e -> {
      if (!mc.field_1690.field_1903.method_1434() && mc.field_1724.method_24828() && !mc.field_1724.method_6128()) {
         if (this.isSoonFalling(mc.field_1724.method_23317(), mc.field_1724.method_23318(), mc.field_1724.method_23321(), (double)mc.field_1724.method_36454())
            )
          {
            mc.field_1724.method_6043();
         }
      }
   };

   public ParkourFeature() {
      super("Parkour", MenuCategory.MOVEMENT);
   }

   private boolean isSoonFalling(double x, double y, double z, double angleDeg) {
      float local9_0 = 0.01F;
      double local10_1 = Math.toRadians(angleDeg);
      double local12_2 = -Math.sin(local10_1) * (double)local9_0;
      double local14_3 = Math.cos(local10_1) * (double)local9_0;
      class_2338 local16_4 = class_2338.method_49637(x + local12_2, y - 1.0, z + local14_3);
      return mc.field_1687.method_8320(local16_4).method_26215();
   }
}
