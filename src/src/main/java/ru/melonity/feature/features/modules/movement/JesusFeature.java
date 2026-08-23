package ru.melonity.feature.features.modules.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_243;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PreMotionEvent;
import ru.melonity.util.minecraft.MovementUtil;

@Environment(EnvType.CLIENT)
public class JesusFeature extends SimpleFeature {
   private final EventListener<PreMotionEvent> onPreMotion = event -> {
      if (mc.field_1724.method_5799()) {
         mc.field_1724.method_18799(new class_243(mc.field_1724.method_18798().field_1352, 0.0, mc.field_1724.method_18798().field_1350));
         if (MovementUtil.isMoving() && MovementUtil.getMotion() < 0.9F) {
            mc.field_1724
               .method_18799(
                  new class_243(
                     mc.field_1724.method_18798().field_1352 * 1.25, mc.field_1724.method_18798().field_1351, mc.field_1724.method_18798().field_1350 * 1.25
                  )
               );
         }
      }
   };

   public JesusFeature() {
      super("Jesus", MenuCategory.MOVEMENT, true);
   }
}
