package ru.melonity.feature.features.modules.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_7439;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.service.friend.FriendService;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.minecraft.PlayerUtil;

@Environment(EnvType.CLIENT)
public class AutoAcceptFeature extends SimpleFeature {
   private final BooleanSetting onlyFriends = new BooleanSetting("autoaccept.onlyfriends", true);
   private final EventListener<PacketEvent> onPacket = event -> {
      if (event.getPacket() instanceof class_7439 local2_4) {
         String local3_3 = local2_4.comp_763().getString().toLowerCase();
         if (local3_3.contains("просит телепорт") || local3_3.contains("хочет телепортироваться") || local3_3.contains("/tpaccept")) {
            boolean local4_2 = false;

            for (String local6_1 : Melonity.INSTANCE.componentManager.service(FriendService.class).friends()) {
               if (local3_3.contains(local6_1)) {
                  local4_2 = true;
                  break;
               }
            }

            if (this.onlyFriends.get() && local4_2) {
               PlayerUtil.sendPlayerMessage("/tpaccept");
            }
         }
      }
   };

   public AutoAcceptFeature() {
      super("AutoAccept", MenuCategory.MISC);
      this.setting(this.onlyFriends);
   }
}
