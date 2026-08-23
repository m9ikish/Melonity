package ru.melonity.feature.features.modules.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_2886;
import net.minecraft.class_7202;
import ru.melonity.fabric.client.mixin.ClientWorldMixin1;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.StrafeEvent;
import ru.melonity.util.event.events.UsingItemEvent;

@Environment(EnvType.CLIENT)
public class NoEatSlowFeature extends SimpleFeature {
   private final ListSetting mode = new ListSetting(
      "noeatslow.mode", ListSetting.ModeType.SINGLE, "noeatslow.cancel", "noeatslow.cancel", "noeatslow.matrix", "noeatslow.tick", "noeatslow.grim"
   );
   private final BooleanSetting sendPacket = new BooleanSetting("noeatslow.changing", false).setVisible(() -> this.mode.contains("noeatslow.tick"));
   public final EventListener<UsingItemEvent> onSlow = e -> {
      int local2_2 = mc.field_1724.method_6048();
      String var3 = this.mode.getMode().getFirst();
      switch (var3) {
         case "noeatslow.cancel":
         case "noeatslow.matrix":
            e.cancel();
         case "noeatslow.tick":
            if (local2_2 <= 1 || local2_2 >= 29) {
               return;
            } else {
               e.cancel();
               if (this.sendPacket.get()) {
                  class_7202 local5_1 = ((ClientWorldMixin1)mc.field_1687).getPendingManager().method_41937();

                  try {
                     int local6_0 = local5_1.method_41942();
                     mc.method_1562()
                        .method_52787(new class_2886(mc.field_1724.method_6058(), local6_0, mc.field_1724.method_36454(), mc.field_1724.method_36455()));
                     mc.method_1562()
                        .method_52787(
                           new class_2886(
                              mc.field_1724.method_6058() == class_1268.field_5808 ? class_1268.field_5810 : class_1268.field_5808,
                              local6_0,
                              mc.field_1724.method_36454(),
                              mc.field_1724.method_36455()
                           )
                        );
                  } catch (Throwable var9) {
                     if (local5_1 != null) {
                        try {
                           local5_1.close();
                        } catch (Throwable var8) {
                           var9.addSuppressed(var8);
                        }
                     }

                     throw var9;
                  }

                  if (local5_1 != null) {
                     local5_1.close();
                  }
               }
            }
         case "noeatslow.grim":
            if (local2_2 >= 23 || local2_2 <= 3) {
               return;
            } else if (mc.field_1724.field_6012 % 2 == 0) {
               e.cancel();
            }
      }
   };
   public final EventListener<StrafeEvent> onSlowMultiply = e -> {
      if (this.mode.contains("noeatslow.matrix") && mc.field_1724.method_6115()) {
         int local2_5 = mc.field_1724.method_6048();
         if (mc.field_1724.method_6115()) {
            boolean local3_4 = (double)mc.field_1724.field_6017 > 0.725;
            if (local2_5 >= 28 || local2_5 <= 3) {
               return;
            }

            if (mc.field_1724.method_24828() && !mc.field_1724.field_3913.field_3904) {
               if (mc.field_1724.field_6012 % 2 == 0) {
                  boolean local4_0 = mc.field_1724.field_3913.field_3907 == 0.0F;
                  float local5_1 = local4_0 ? 0.5F : 0.4F;
                  e.speed *= local5_1;
               }
            } else if (local3_4) {
               boolean local4_2 = (double)mc.field_1724.field_6017 > 1.4;
               float local5_3 = local4_2 ? 0.95F : 0.97F;
               e.speed *= local5_3;
            }
         }
      }
   };

   public NoEatSlowFeature() {
      super("NoEatSlow", MenuCategory.MOVEMENT);
      this.settings(new MenuFeatureView[]{this.mode, this.sendPacket});
   }
}
