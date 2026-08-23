package ru.melonity.feature.features.modules.render;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1294;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.BossbarRenderEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;

@Environment(EnvType.CLIENT)
public class NoRenderFeature extends SimpleFeature {
   private final ListSetting modeSetting = new ListSetting(
      "norender.removes",
      ListSetting.ModeType.MULTI,
      "norender.fire",
      "norender.badeffects",
      "norender.totem",
      "norender.fire",
      "norender.thunder",
      "Boss Bar",
      "norender.chatbg"
   );
   private final EventListener<BossbarRenderEvent> onBossbarRender = event -> {
      if (this.modeSetting.getMode().contains("Boss Bar")) {
         event.cancel();
      }
   };
   private final EventListener<UpdateLivingEvent> onUpdate = event -> {
      if (this.modeSetting.getMode().contains("norender.badeffects")) {
         mc.field_1724.method_6016(class_1294.field_5919);
         mc.field_1724.method_6016(class_1294.field_5911);
      }

      if (this.modeSetting.getMode().contains("norender.thunder")) {
         mc.field_1687.method_8519(0.0F);
         mc.field_1687.method_8496(0.0F);
      }
   };

   public NoRenderFeature() {
      super("NoRender", MenuCategory.RENDER);
      this.setting(this.modeSetting);
   }

   @Generated
   public ListSetting getModeSetting() {
      return this.modeSetting;
   }

   @Generated
   public EventListener<BossbarRenderEvent> getOnBossbarRender() {
      return this.onBossbarRender;
   }

   @Generated
   public EventListener<UpdateLivingEvent> getOnUpdate() {
      return this.onUpdate;
   }
}
