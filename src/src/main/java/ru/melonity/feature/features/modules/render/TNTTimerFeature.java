package ru.melonity.feature.features.modules.render;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1541;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_243;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.ILevelRenderer;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.render.ProjectUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class TNTTimerFeature extends SimpleFeature {
   private final EventListener<RenderOverlayEvent> onOverlay = event -> {
      for (class_1297 local2_9 : mc.field_1687.method_18112()) {
         if (local2_9 instanceof class_1541) {
            class_1541 local3_8 = (class_1541)local2_9;
            if (((ILevelRenderer)mc.field_1769).getFrustum().method_23093(local3_8.method_5829())) {
               String local4_0 = String.format("%.1f сек", (float)local3_8.method_6969() / 20.0F).replace(',', '.');
               class_243 local5_1 = local3_8.method_19538();
               double[] local6_2 = ProjectUtil.project(local5_1.field_1352, local5_1.field_1351 + (double)local3_8.method_17682() + 0.5, local5_1.field_1350);
               if (local6_2 != null) {
                  RenderSystem local7_3 = Melonity.INSTANCE.getRenderSystem();
                  class_4587 local8_4 = event.matrixStack();
                  float local9_5 = 12.0F + Fonts.INTER_MEDIUM_12.getStringWidth(local4_0) + 2.0F;
                  int local10_6 = (int)Math.round(local6_2[0] - (double)(local9_5 / 2.0F));
                  int local11_7 = (int)Math.round(local6_2[1]);
                  local7_3.addRect((float)local10_6, (float)local11_7, local9_5, 12.0F, 4.0F, Color.decode("#181818"), local8_4);
                  local8_4.method_22903();
                  local8_4.method_46416(0.0F, 0.0F, 0.0F);
                  local8_4.method_22905(0.5F, 0.5F, 0.5F);
                  local8_4.method_46416((float)local10_6, (float)local11_7, 0.0F);
                  event.graphics().method_51427(new class_1799(class_1802.field_8626), local10_6 + 4, local11_7 + 4);
                  local8_4.method_22909();
                  local7_3.addText(Fonts.INTER_MEDIUM_12, local4_0, (float)(local10_6 + 12), (float)(local11_7 + 5), local8_4);
               }
            }
         }
      }
   };

   public TNTTimerFeature() {
      super("TNTTimer", MenuCategory.RENDER);
   }
}
