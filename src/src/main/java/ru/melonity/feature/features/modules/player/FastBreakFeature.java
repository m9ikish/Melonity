package ru.melonity.feature.features.modules.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateEvent;

@Environment(EnvType.CLIENT)
public class FastBreakFeature extends SimpleFeature {
   public final EventListener<UpdateEvent> onUpdate = e -> {
      if (!mc.field_1724.method_6059(class_1294.field_5917)) {
         mc.field_1724.method_6092(new class_1293(class_1294.field_5917));
      }
   };

   public FastBreakFeature() {
      super("FastBreak", MenuCategory.PLAYER);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      if (mc.field_1724.method_6059(class_1294.field_5917)) {
         mc.field_1724.method_6016(class_1294.field_5917);
      }
   }
}
