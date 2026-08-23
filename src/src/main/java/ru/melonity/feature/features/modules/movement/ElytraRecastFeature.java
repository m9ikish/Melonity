package ru.melonity.feature.features.modules.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1304;
import net.minecraft.class_1770;
import net.minecraft.class_1802;
import net.minecraft.class_2848;
import net.minecraft.class_2848.class_2849;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateLivingEvent;

@Environment(EnvType.CLIENT)
public class ElytraRecastFeature extends SimpleFeature {
   private boolean wasInAir = false;
   private boolean shouldRecast = false;
   private final EventListener<UpdateLivingEvent> onUpdate = event -> {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         boolean local2_0 = mc.field_1724.method_6118(class_1304.field_6174).method_7909() == class_1802.field_8833;
         boolean local3_1 = local2_0 && class_1770.method_7804(mc.field_1724.method_6118(class_1304.field_6174));
         boolean local4_2 = mc.field_1724.field_3913.field_3905 > 0.0F;
         if (!local2_0) {
            this.wasInAir = false;
            this.shouldRecast = false;
         } else {
            if (mc.field_1724.method_24828()) {
               this.wasInAir = false;
               this.shouldRecast = false;
               if (local3_1 && local4_2) {
                  mc.field_1724.method_6043();
               }
            } else {
               if (!this.wasInAir) {
                  this.wasInAir = true;
                  this.shouldRecast = local3_1 && local4_2;
               }

               if (this.shouldRecast && !mc.field_1724.method_6128()) {
                  mc.method_1562().method_52787(new class_2848(mc.field_1724, class_2849.field_12982));
                  this.shouldRecast = false;
               }
            }
         }
      }
   };

   public ElytraRecastFeature() {
      super("ElytraRecast", MenuCategory.MOVEMENT);
   }
}
