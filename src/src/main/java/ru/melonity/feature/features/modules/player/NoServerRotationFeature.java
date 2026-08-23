package ru.melonity.feature.features.modules.player;

import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2708;
import net.minecraft.class_2709;
import ru.melonity.fabric.client.model.IPlayerPositionLookS2CPacket;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PacketEvent;

@Environment(EnvType.CLIENT)
public class NoServerRotationFeature extends SimpleFeature {
   private final EventListener<PacketEvent> onRecievePacket = event -> {
      if (event.getType() == PacketEvent.PacketType.RECIEVE && event.getPacket() instanceof class_2708 local1_2 && mc.field_1724 != null) {
         IPlayerPositionLookS2CPacket local2_1 = (IPlayerPositionLookS2CPacket)event.getPacket();
         Set<class_2709> local3_2 = local1_2.method_11733();
         // The rotation fields are a DELTA when their flag is present and an absolute
         // value when it is absent. Writing the current absolute rotation into a
         // relative packet makes vanilla compute pitch + pitch, which is what put an
         // impossible pitch of ~148 on the wire. Either way we want "no change".
         local2_1.setYaw(local3_2.contains(class_2709.field_12401) ? 0.0F : mc.field_1724.method_36454());
         local2_1.setPitch(local3_2.contains(class_2709.field_12397) ? 0.0F : mc.field_1724.method_36455());
      }
   };

   public NoServerRotationFeature() {
      super("NoServerRotation", MenuCategory.PLAYER);
   }
}
