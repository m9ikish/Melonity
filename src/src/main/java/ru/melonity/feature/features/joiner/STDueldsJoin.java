package ru.melonity.feature.features.joiner;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1707;
import net.minecraft.class_1713;

@Environment(EnvType.CLIENT)
public class STDueldsJoin extends BaseJoinLogic {
   public STDueldsJoin() {
      super("Duels SpookyTime");
   }

   @Override
   public void tick() {
      if (this.mc.field_1755 == null) {
         if (this.mc.field_1724.field_6012 % 24 == 0) {
            this.mc.field_1724.method_31548().field_7545 = 4;
            this.mc.field_1761.method_2919(this.mc.field_1724, class_1268.field_5808);
         }
      } else if (this.mc.field_1724.field_7512 instanceof class_1707 local1_0 && this.timer.hasReached(50L, true)) {
         this.mc.field_1761.method_2906(local1_0.field_7763, 14, 0, class_1713.field_7790, this.mc.field_1724);
      }
   }

   @Override
   public void handleMessage(String message) {
   }

   @Override
   public boolean need() {
      return this.mc.field_1687.method_8428().method_1151().isEmpty();
   }
}
