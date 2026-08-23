package ru.melonity.feature.features.modules.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2720;
import net.minecraft.class_2856;
import net.minecraft.class_2856.class_2857;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PacketEvent;

@Environment(EnvType.CLIENT)
public class RPSpooferFeature extends SimpleFeature {
   private final EventListener<PacketEvent> onPacket = event -> {
      if (event.isReceive() && event.getPacket() instanceof class_2720 local1_1) {
         event.cancel();
         mc.method_1562().method_52787(new class_2856(local1_1.comp_2158(), class_2857.field_13016));
         mc.method_1562().method_52787(new class_2856(local1_1.comp_2158(), class_2857.field_47704));
         mc.method_1562().method_52787(new class_2856(local1_1.comp_2158(), class_2857.field_13017));
         if (mc.field_1755 != null) {
            mc.field_1724.method_7346();
         }
      }
   };

   public RPSpooferFeature() {
      super("RPSpoofer", MenuCategory.MISC);
   }
}
