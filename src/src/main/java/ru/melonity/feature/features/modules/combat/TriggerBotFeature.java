package ru.melonity.feature.features.modules.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_3966;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.modules.SyncUtil;

@Environment(EnvType.CLIENT)
public class TriggerBotFeature extends SimpleFeature {
   private final ListSetting attackSetting = new ListSetting("triggerbot.attack_type", ListSetting.ModeType.SINGLE, "1.9+", "1.9+", "1.8");
   private final NumberSetting cpsMinSetting = new NumberSetting("triggerbot.mincps", value -> String.valueOf(value.intValue()), 1, 20, 3)
      .setVisible(() -> this.attackSetting.contains("1.8"));
   private final NumberSetting cpsMaxSetting = new NumberSetting("triggerbot.maxcps", value -> String.valueOf(value.intValue()), 1, 20, 5)
      .setVisible(() -> this.attackSetting.contains("1.8"));
   private final BooleanSetting spaceCrit = new BooleanSetting("global.space_crit", true).setVisible(() -> this.attackSetting.contains("1.9+"));
   public final EventListener<UpdateLivingEvent> onUpdate = event -> {
      if (SyncUtil.canAttack(
            this.spaceCrit.get(), this.attackSetting.contains("1.8"), this.cpsMinSetting.value.floatValue(), this.cpsMaxSetting.value.floatValue()
         )
         && mc.field_1765 instanceof class_3966 local2_1) {
         mc.field_1761.method_2918(mc.field_1724, local2_1.method_17782());
         mc.field_1724.method_6104(class_1268.field_5808);
      }
   };

   public TriggerBotFeature() {
      super("TriggerBot", MenuCategory.COMBAT);
      this.settings(new MenuFeatureView[]{this.attackSetting, this.cpsMinSetting, this.cpsMaxSetting, this.spaceCrit});
   }
}
