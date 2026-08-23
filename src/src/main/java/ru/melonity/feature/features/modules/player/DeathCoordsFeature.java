package ru.melonity.feature.features.modules.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_418;
import ru.melonity.feature.features.command.ChatUtil;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PreMotionEvent;

@Environment(EnvType.CLIENT)
public class DeathCoordsFeature extends SimpleFeature {
   private final EventListener<PreMotionEvent> onPreMotion = event -> {
      if (mc.field_1755 instanceof class_418 && mc.field_1724.field_6213 == 1) {
         ChatUtil.topSplitter();
         ChatUtil.prefix(
            String.format(
               "§rВы умерли на координатах: §c§l%d %d %d",
               Math.round((float)mc.field_1724.method_31477()),
               Math.round((float)mc.field_1724.method_31478()),
               Math.round((float)mc.field_1724.method_31479())
            )
         );
         ChatUtil.print(
            String.format(
               "      Поставить метку:§a§l  .gps %d %d", Math.round((float)mc.field_1724.method_31477()), Math.round((float)mc.field_1724.method_31479())
            )
         );
         ChatUtil.bottomSplitter();
      }
   };

   public DeathCoordsFeature() {
      super("DeathCoord", MenuCategory.PLAYER);
   }
}
