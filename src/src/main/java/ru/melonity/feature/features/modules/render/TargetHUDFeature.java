package ru.melonity.feature.features.modules.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_3966;
import net.minecraft.class_408;
import net.minecraft.class_239.class_240;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.hud.TargetHUDView;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.drag.Drag;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class TargetHUDFeature extends SimpleFeature {
   private final ListSetting targethudType = new ListSetting("global.mode", ListSetting.ModeType.SINGLE, "Informative", "Default", "Informative");
   private final BooleanSetting hover = new BooleanSetting("killaura.hover", true);
   private final Drag drag = new Drag(4, 415.0F, 400.0F, 110.0F, 45.0F, () -> true);
   private final TargetHUDView targetHUDView = new TargetHUDView(110.0F, 45.0F, TargetHUDView.Type.TYPE1);
   private final EventListener<RenderOverlayEvent> onRender = event -> {
      class_1309 local2_3 = null;
      class_1309 local3_4 = this.getAurasTarget();
      if (local3_4 != null && Melonity.INSTANCE.featureManager.auraFeature.isEnabled()) {
         local2_3 = local3_4;
      } else if (mc.field_1755 instanceof class_408) {
         local2_3 = mc.field_1724;
      } else if (this.hover.get() && mc.field_1765 != null && mc.field_1765.method_17783() == class_240.field_1331) {
         class_3966 local4_2 = (class_3966)mc.field_1765;
         if (local4_2.method_17782() instanceof class_1309 local5_1) {
            local2_3 = local5_1;
         }
      }

      this.targetHUDView.setType(this.targethudType.getMode().getFirst().equals("Default") ? TargetHUDView.Type.TYPE1 : TargetHUDView.Type.TYPE2);
      if (local2_3 != null && local2_3.method_5805()) {
         this.targetHUDView.setTarget(local2_3);
         this.targetHUDView.setBackward(false);
      } else {
         this.targetHUDView.setBackward(true);
      }

      RenderSystem local4_5 = Melonity.INSTANCE.getRenderSystem();
      event.matrixStack().method_22903();
      event.matrixStack().method_46416(0.0F, 0.0F, 190.0F);
      this.drag.setWidth(this.targethudType.contains("Default") ? 120.0F : 106.0F);
      this.targetHUDView.insert(this.drag.getX(), this.drag.getY(), 0.0, 0.0, local4_5, event.graphics());
      event.matrixStack().method_22909();
   };

   public TargetHUDFeature() {
      super("TargetHUD", MenuCategory.RENDER);
      this.settings(new MenuFeatureView[]{this.targethudType, this.hover});
   }

   private class_1309 getAurasTarget() {
      return Melonity.INSTANCE.getFeatureManager().auraFeature.getTarget();
   }
}
