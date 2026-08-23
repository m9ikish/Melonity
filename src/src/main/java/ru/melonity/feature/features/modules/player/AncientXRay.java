package ru.melonity.feature.features.modules.player;

import java.awt.Color;
import java.util.ArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2626;
import net.minecraft.class_2637;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeaturePrefix;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.RenderGameWorldEvent;
import ru.melonity.util.render.BoxRenderer;

@Environment(EnvType.CLIENT)
public class AncientXRay extends SimpleFeature {
   private final ArrayList<class_2338> ores = new ArrayList<>();
   public final EventListener<PacketEvent> onPacket = event -> {
      if (event.getPacket() instanceof class_2637 local2_1) {
         local2_1.method_30621((pos, state) -> {
            if (state.method_27852(class_2246.field_22109)) {
               this.ores.add(pos.method_10062());
            }
         });
      }

      if (event.getPacket() instanceof class_2626 local2_4) {
         class_2338 local3_3 = local2_4.method_11309();
         if (mc.field_1687.method_8320(local3_3).method_27852(class_2246.field_22109)) {
            this.ores.add(local3_3.method_10062());
         }
      }
   };
   public final EventListener<RenderGameWorldEvent> onRender = event -> {
      this.ores.removeIf(pos -> mc.field_1687.method_8320(pos).method_26204() != class_2246.field_22109);
      this.ores.forEach(pos -> BoxRenderer.boxWOffset(pos.method_10062(), Color.BLACK, event.poseStack().method_23760().method_23761(), 1.0));
   };

   public AncientXRay() {
      super("AncientXRay", MenuCategory.PLAYER);
      this.prefix(new MenuFeaturePrefix[]{MenuFeaturePrefix.Funtime});
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      this.ores.clear();
   }
}
