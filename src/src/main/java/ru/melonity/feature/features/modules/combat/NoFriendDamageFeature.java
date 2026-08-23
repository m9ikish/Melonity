package ru.melonity.feature.features.modules.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_239;
import net.minecraft.class_3966;
import net.minecraft.class_239.class_240;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.service.friend.FriendService;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.AttackEvent;
import ru.melonity.util.event.events.DoAttackEvent;

@Environment(EnvType.CLIENT)
public class NoFriendDamageFeature extends SimpleFeature {
   private class_1309 entity;
   private final EventListener<AttackEvent> onAttack = event -> {
      if (Melonity.INSTANCE.componentManager.service(FriendService.class).hasFriend(event.target().method_5477().getString())) {
         event.cancel();
      }

      this.entity = (class_1309)event.target();
   };
   private final EventListener<DoAttackEvent> doAttackEvent = event -> {
      if (this.entity != null) {
         class_239 local2_3 = event.getCrosshairTarget();
         if (local2_3 != null) {
            if (local2_3.method_17783() == class_240.field_1331) {
               class_3966 local3_2 = (class_3966)local2_3;
               if (local3_2.method_17782() instanceof class_1309) {
                  class_1309 local4_0 = (class_1309)local3_2.method_17782();
                  String local5_1 = local4_0.method_5477().getString();
                  if (Melonity.INSTANCE.componentManager.service(FriendService.class).hasFriend(local5_1)) {
                     event.setCancelled(true);
                  }
               }
            }
         }
      }
   };

   public NoFriendDamageFeature() {
      super("NoFriendDamage", MenuCategory.COMBAT);
   }
}
