package ru.melonity.feature.features.modules.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.fabric.client.model.ILivingEntity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateEvent;

@Environment(EnvType.CLIENT)
public class JumpCountFeature extends SimpleFeature {
   public final EventListener<UpdateEvent> onUpdate = e -> {
      if (mc.field_1690.field_1903.method_1434()) {
         ((ILivingEntity)mc.field_1724).resetJumpDelay();
      }
   };

   public JumpCountFeature() {
      super("NoJumpDelay", MenuCategory.PLAYER);
   }
}
