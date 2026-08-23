package ru.melonity.feature.features.modules.player;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2749;
import net.minecraft.class_2828.class_2830;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeaturePrefix;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;

@Environment(EnvType.CLIENT)
public class InstaRespawnFeature extends SimpleFeature {
   public final EventListener<UpdateLivingEvent> onUpdate = event -> {
      if (mc.field_1724.method_6032() <= 0.25F) {
         mc.field_1724.method_5762(mc.field_1724.method_18798().field_1352, 0.5, mc.field_1724.method_18798().field_1350);
         mc.method_1562()
            .method_52787(
               new class_2830(
                  mc.field_1724.method_23317(),
                  1.0E-6,
                  mc.field_1724.method_23321(),
                  mc.field_1724.method_36454(),
                  mc.field_1724.method_36455(),
                  !mc.field_1724.method_24828()
               )
            );
      }
   };
   public final EventListener<PacketEvent> onPacket = event -> {
      if (event.getType() == PacketEvent.PacketType.RECIEVE && event.getPacket() instanceof class_2749 local1_2 && local1_2.method_11833() <= 0.25F) {
         for (int local2_1 = 0; local2_1 < 5; local2_1++) {
            mc.method_1562()
               .method_52787(
                  new class_2830(
                     mc.field_1724.method_23317(),
                     1.0E-6,
                     mc.field_1724.method_23321(),
                     mc.field_1724.method_36454(),
                     mc.field_1724.method_36455(),
                     !mc.field_1724.method_24828()
                  )
               );
         }
      }
   };

   public InstaRespawnFeature() {
      super("InstaRespawn", MenuCategory.MISC);
      this.prefix(new MenuFeaturePrefix[]{MenuFeaturePrefix.Funtime});
   }

   @Generated
   public EventListener<UpdateLivingEvent> getOnUpdate() {
      return this.onUpdate;
   }

   @Generated
   public EventListener<PacketEvent> getOnPacket() {
      return this.onPacket;
   }
}
