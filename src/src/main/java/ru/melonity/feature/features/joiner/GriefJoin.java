package ru.melonity.feature.features.joiner;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;

@Environment(EnvType.CLIENT)
public class GriefJoin extends BaseJoinLogic {
   private final ListSetting funtimeMode;
   private final NumberSetting funtimeGriefSelection;

   public GriefJoin(ListSetting funtimeMode, NumberSetting funtimeGriefSelection) {
      super("FunTime");
      this.funtimeMode = funtimeMode;
      this.funtimeGriefSelection = funtimeGriefSelection;
   }

   @Override
   public void tick() {
      int local1_1 = this.funtimeGriefSelection.getValue().intValue();
      if (this.mc.field_1755 == null) {
         if (this.mc.field_1724.field_6012 % 24 == 0) {
            this.mc.field_1724.method_31548().field_7545 = 4;
            this.mc.field_1761.method_2919(this.mc.field_1724, class_1268.field_5808);
         }
      } else if (this.mc.field_1724.field_7512 instanceof class_1707 local2_0) {
         if (this.timer.hasReached(60L) && !this.timer.isSleeping()) {
            this.mc.field_1761.method_2906(local2_0.field_7763, 14, 0, class_1713.field_7790, this.mc.field_1724);
            this.timer.sleep(40L, true);
         } else if (this.timer.hasReached(40L) && !this.timer.isSleeping()) {
            this.mc.field_1761.method_2906(local2_0.field_7763, 29 + (local1_1 == 5 ? 0 : local1_1 + 1), 0, class_1713.field_7790, this.mc.field_1724);
            this.timer.reset();
         }
      }
   }

   @Override
   public void handleMessage(String message) {
   }

   @Override
   public boolean need() {
      return this.mc.field_1687.method_8428().method_1151().isEmpty() && this.funtimeMode.contains("Grief");
   }
}
