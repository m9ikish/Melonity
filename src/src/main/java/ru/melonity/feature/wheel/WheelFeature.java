package ru.melonity.feature.wheel;

import java.awt.Color;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import org.lwjgl.glfw.GLFW;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.ui.menu.clickui.view.wheel.WheelEntryView;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.MouseClickEvent;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;

@Environment(EnvType.CLIENT)
public class WheelFeature extends SimpleFeature {
   private boolean opened = false;
   private final BindSetting bindSetting = new BindSetting("", -1);
   private final EventListener<MouseClickEvent> onMouseClick = event -> {
      if (this.bindSetting.bind != -1) {
         boolean local2_1 = mc.field_1755 == null && GLFW.glfwGetKey(mc.method_22683().method_4490(), this.bindSetting.bind) == 1;
         List<WheelEntryView> local3_2 = Melonity.INSTANCE.getWheelEntries();
         if (local2_1) {
            event.cancel();

            for (WheelEntryView local5_0 : local3_2) {
               if (local5_0.getMacro() != null && local5_0.isSelected()) {
                  local5_0.getMacro().use();
                  this.unselect();
                  return;
               }
            }
         }
      }
   };
   private final EventListener<RenderOverlayEvent> onRenderOverlay = event -> {
      if (this.bindSetting.bind != -1) {
         ScaleUtils.rescale(2.0);
         MenuStyle local2_4 = Melonity.INSTANCE.styleManager.getStyle();
         RenderSystem local3_5 = Melonity.INSTANCE.getRenderSystem();
         class_4587 local4_6 = event.matrixStack();
         double local5_7 = mc.field_1729.method_1603() / 2.0;
         double local7_8 = mc.field_1729.method_1604() / 2.0;
         float local9_9 = (float)mc.method_22683().method_4480() / 2.0F;
         float local10_10 = (float)mc.method_22683().method_4507() / 2.0F;
         boolean local11_11 = mc.field_1755 == null && GLFW.glfwGetKey(mc.method_22683().method_4490(), this.bindSetting.bind) == 1;
         if (local11_11) {
            mc.field_1729.method_1610();
            this.opened = true;
         }

         List<WheelEntryView> local12_12 = Melonity.INSTANCE.getWheelEntries();
         local4_6.method_22903();
         local4_6.method_46416(local9_9 / 2.0F - 37.0F, local10_10 / 2.0F - 15.0F, 0.0F);
         float local13_13 = 30.0F;
         if (local12_12.getFirst().getAnimation().animation() > 0.0F) {
            local3_5.addRect(
               local13_13 - 8.0F - 0.5F,
               local13_13 / 2.0F - 15.5F - 0.5F,
               local13_13 + 1.0F,
               local13_13 + 1.0F,
               local13_13,
               local2_4.modeElement()[1],
               local4_6
            );
            local3_5.addRect(local13_13 - 8.0F, local13_13 / 2.0F - 15.5F, local13_13, local13_13, local13_13, local2_4.autobuyPanel(), local4_6);
            float local14_0 = (float)Math.atan2(
               local7_8 - (double)(local10_10 / 2.0F - 15.0F + local13_13 - 20.0F), local5_7 - (double)(local9_9 / 2.0F - 37.0F + local13_13 + 2.0F)
            );
            float local15_1 = local13_13 + 2.0F + class_3532.method_15362(local14_0) * 15.0F;
            float local16_2 = local13_13 - 20.0F + class_3532.method_15374(local14_0) * 15.0F;
            local3_5.addRect(local15_1, local16_2, 6.0F, 6.0F, 6.0F, Color.WHITE, local4_6);
         } else if (!mc.field_1729.method_1613() && this.opened) {
            mc.field_1729.method_1612();
            this.opened = false;

            for (WheelEntryView local15_3 : local12_12) {
               if (local15_3.getMacro() != null && local15_3.isSelected()) {
                  local15_3.getMacro().use();
                  this.unselect();
                  break;
               }
            }
         }

         local12_12.get(0)
            .drawWheelStyle(
               local2_4, 0.0F, 0.0F, -1.1F, class_3532.method_15374((float)Math.toRadians(25.0)), local5_7, local7_8, local3_5, local11_11, local4_6
            );
         local12_12.get(1).drawWheelStyle(local2_4, 0.0F, 0.0F, -1.2F, -0.15F, local5_7, local7_8, local3_5, local11_11, local4_6);
         local12_12.get(2)
            .drawWheelStyle(
               local2_4,
               0.0F,
               0.0F,
               -class_3532.method_15362((float)Math.toRadians(45.0)),
               -class_3532.method_15374((float)Math.toRadians(45.0)),
               local5_7,
               local7_8,
               local3_5,
               local11_11,
               local4_6
            );
         local12_12.get(3)
            .drawWheelStyle(
               local2_4,
               0.0F,
               0.0F,
               class_3532.method_15362((float)Math.toRadians(49.0)),
               -class_3532.method_15374((float)Math.toRadians(48.0)),
               local5_7,
               local7_8,
               local3_5,
               local11_11,
               local4_6
            );
         local12_12.get(4).drawWheelStyle(local2_4, 0.0F, 0.0F, 1.15F, -0.15F, local5_7, local7_8, local3_5, local11_11, local4_6);
         local12_12.get(5)
            .drawWheelStyle(
               local2_4, 0.0F, 0.0F, 1.08F, class_3532.method_15374((float)Math.toRadians(25.0)), local5_7, local7_8, local3_5, local11_11, local4_6
            );
         local4_6.method_22909();
         ScaleUtils.rescaleMC();
      }
   };

   public WheelFeature() {
      super("Wheel bind", MenuCategory.MACROS);
      this.settingClear();
      this.setting(this.bindSetting);
   }

   private void unselect() {
      for (WheelEntryView local2_0 : Melonity.INSTANCE.getWheelEntries()) {
         local2_0.setSelected(false);
      }
   }
}
