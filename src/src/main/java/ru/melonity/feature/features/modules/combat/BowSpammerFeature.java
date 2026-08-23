package ru.melonity.feature.features.modules.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1753;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PreMotionEvent;

@Environment(EnvType.CLIENT)
public class BowSpammerFeature extends SimpleFeature {
   private final NumberSetting delay = new NumberSetting("global.delay", value -> String.format("%d", value.intValue()), 4, 15, 4);
   private final EventListener<PreMotionEvent> onPreMotion = event -> {
      if (mc.field_1724.method_31548().method_7391().method_7909() instanceof class_1753
         && mc.field_1724.method_6115()
         && mc.field_1724.method_6048() >= this.delay.getValue().intValue()) {
         mc.field_1761.method_2897(mc.field_1724);
      }
   };

   public BowSpammerFeature() {
      super("BowSpammer", MenuCategory.COMBAT);
      this.setting(this.delay);
   }
}
