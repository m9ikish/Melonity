package ru.melonity.feature.features.modules.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_7439;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.minecraft.PlayerUtil;

@Environment(EnvType.CLIENT)
public class AutoAuthFeature extends SimpleFeature {
   private final EventListener<PacketEvent> onPacket = event -> {
      if (event.getType() != PacketEvent.PacketType.SEND) {
         if (event.getPacket() instanceof class_7439 local1_2) {
            String local2_1 = local1_2.comp_763().getString();
            if (local2_1 == null) {
               return;
            }

            if (local2_1.contains("/l") || local2_1.contains("/login")) {
               PlayerUtil.sendPlayerMessage("/login pQoMwtlaze");
            } else if (local2_1.contains("/reg") || local2_1.contains("/register")) {
               PlayerUtil.sendPlayerMessage("/register pQoMwtlaze pQoMwtlaze");
               PlayerUtil.sendPlayerMessage("/register pQoMwtlaze");
            }
         }
      }
   };

   public AutoAuthFeature() {
      super("AutoAuth", MenuCategory.MISC);
   }
}
