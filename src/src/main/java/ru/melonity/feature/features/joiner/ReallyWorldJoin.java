package ru.melonity.feature.features.joiner;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_2561;
import net.minecraft.class_9334;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;

@Environment(EnvType.CLIENT)
public final class ReallyWorldJoin extends BaseJoinLogic {
   private final NumberSetting griefSelection;

   public ReallyWorldJoin(NumberSetting griefSelection) {
      super("ReallyWorld");
      this.griefSelection = griefSelection;
   }

   @Override
   public void tick() {
      if (this.mc.field_1755 == null) {
         if (this.mc.field_1724.field_6012 % 24 == 0) {
            this.mc.field_1724.method_31548().field_7545 = 0;
            this.mc.field_1761.method_2919(this.mc.field_1724, class_1268.field_5808);
         }
      } else if (this.mc.field_1724.field_7512 instanceof class_1707 local1_4) {
         int local2_3 = this.griefSelection.getValue().intValue();

         for (int local3_2 = 0; local3_2 < local1_4.method_7629().method_5439(); local3_2++) {
            class_2561 local4_0 = (class_2561)local1_4.method_7629().method_5438(local3_2).method_57824(class_9334.field_49631);
            if (local4_0 != null) {
               String local5_1 = local4_0.getString();
               if (local5_1.contains("ГРИФЕРСКОЕ ВЫЖИВАНИЕ") && this.timer.hasReached(50L, true)) {
                  this.mc.field_1761.method_2906(local1_4.field_7763, local3_2, 0, class_1713.field_7790, this.mc.field_1724);
               }

               if (local5_1.contains("ГРИФ #" + local2_3) && this.timer.hasReached(50L, true)) {
                  this.mc.field_1761.method_2906(local1_4.field_7763, local3_2, 0, class_1713.field_7790, this.mc.field_1724);
               }
            }
         }
      }
   }

   @Override
   public void handleMessage(String message) {
      if (message.contains("К сожалению сервер переполнен") || message.contains("Подождите 20 секунд!") || message.contains("большой поток игроков")) {
         this.mc.field_1724.method_31548().field_7545 = 0;
         this.mc.field_1761.method_2919(this.mc.field_1724, class_1268.field_5808);
      }
   }

   @Override
   public boolean need() {
      return this.mc.field_1687.method_8428().method_1151().isEmpty();
   }
}
