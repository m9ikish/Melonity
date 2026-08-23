package ru.melonity.feature.features.modules.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.hud.SchedulersView;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.drag.Drag;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class SchedulesFeature extends SimpleFeature {
   private final SchedulersView schedulersView = new SchedulersView();
   private final Drag schedulersDrag = new Drag(99, 10.0F, 350.0F, 1.0F, 1.0F, () -> true);
   private final EventListener<RenderOverlayEvent> onRenderOverlay = event -> {
      RenderSystem local2_0 = Melonity.INSTANCE.getRenderSystem();
      this.schedulersDrag.setWidth(this.schedulersView.getWidth());
      this.schedulersDrag.setHeight(this.schedulersView.getHeight());
      if (this.schedulersDrag.canDraw()) {
         this.schedulersView.insert(this.schedulersDrag.getX(), this.schedulersDrag.getY(), 0.0, 0.0, local2_0, event.matrixStack());
      }
   };

   public SchedulesFeature() {
      super("Schedules", MenuCategory.RENDER);
   }
}
