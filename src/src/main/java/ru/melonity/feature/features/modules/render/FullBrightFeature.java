package ru.melonity.feature.features.modules.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_3532;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.BrightnessEvent;
import ru.melonity.util.event.events.DarknessFactorEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;

@Environment(EnvType.CLIENT)
public class FullBrightFeature extends SimpleFeature {
   private final ListSetting type = new ListSetting("global.mode", ListSetting.ModeType.SINGLE, "Lightmap", "Lightmap", "global.potion");
   private final EventListener<UpdateLivingEvent> onUpdate = event -> {
      if (this.type.contains("global.potion")) {
         mc.field_1724.method_6092(new class_1293(class_1294.field_5925, 500));
      }
   };
   private final EventListener<DarknessFactorEvent> onDarkness = event -> {
      if (this.type.contains("Lightmap")) {
         event.cancel();
      }
   };
   private final EventListener<BrightnessEvent> onBrightness = event -> {
      if (this.type.contains("Lightmap")) {
         float local2_0 = (float)event.lightLevel() / 15.0F;
         float local3_1 = local2_0 / (4.0F - 3.0F * local2_0);
         event.brightness(Math.max(class_3532.method_16439(event.type().comp_656(), local3_1, 1.0F), 0.8F));
      }
   };

   public FullBrightFeature() {
      super("FullBright", MenuCategory.RENDER);
      this.setting(this.type);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      if (!value) {
         mc.field_1724.method_6016(class_1294.field_5925);
      }
   }
}
