package ru.melonity.feature.features.modules.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2761;
import ru.melonity.fabric.client.model.IClientWorld;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.animation.AnimationUtil;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.RenderGameWorldEvent;

@Environment(EnvType.CLIENT)
public class WorldTimeFeature extends SimpleFeature {
   private final ListSetting timeMode = new ListSetting(
      "global.time", ListSetting.ModeType.SINGLE, "worldtime.day", "worldtime.day", "worldtime.afternoon", "worldtime.night", "worldtime.sunset"
   );
   private float currentTime = 0.0F;
   private final EventListener<RenderGameWorldEvent> onRenderWorld = event -> {
      String local2_0 = this.timeMode.getMode().getFirst();
      float local3_1 = this.resolveTime(local2_0);
      this.currentTime = AnimationUtil.smoothAnimate(local3_1, this.currentTime, 60.0F);
      ((IClientWorld)mc.field_1687).setTimeOfDay((long)this.currentTime);
   };
   private final EventListener<PacketEvent> onPacket = event -> {
      if (this.isEnabled() && event.getPacket() instanceof class_2761) {
         event.cancel();
      }
   };

   public WorldTimeFeature() {
      super("WorldTime", MenuCategory.RENDER);
      this.setting(this.timeMode);
   }

   private float resolveTime(String mode) {
      return switch (mode) {
         case "worldtime.day" -> 1800.0F;
         case "worldtime.afternoon" -> 12700.0F;
         case "worldtime.night" -> 21400.0F;
         case "worldtime.sunset" -> 23000.0F;
         default -> 0.0F;
      };
   }

   @Override
   public void onEnable() {
      if (mc.field_1687 != null) {
         this.currentTime = (float)mc.field_1687.method_8532();
      }
   }
}
