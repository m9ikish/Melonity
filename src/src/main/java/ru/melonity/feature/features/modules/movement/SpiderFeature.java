package ru.melonity.feature.features.modules.movement;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1802;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2354;
import net.minecraft.class_239;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.inventory.InventoryUtil;
import ru.melonity.util.minecraft.RayTraceUtil;

@Environment(EnvType.CLIENT)
public class SpiderFeature extends SimpleFeature {
   private final ListSetting modeSetting = new ListSetting("global.mode", ListSetting.ModeType.SINGLE, "Funtime", "FunTime", "Grim Water");
   public final EventListener<UpdateLivingEvent> onUpdate = event -> {
      if (!mc.field_1724.field_5976) {
         RotationManager.INSTANCE.reset();
      } else {
         int local2_2 = InventoryUtil.getHotbarItem(mc.field_1724, class_1802.field_8705);
         int local3_3 = InventoryUtil.getHotbarItem(mc.field_1724, class_1802.field_8550);
         float local4_4 = 0.0F;
         if (this.modeSetting.contains("FunTime")) {
            local4_4 = (float)(-68.0 + -Math.random());
            if (local2_2 != -1) {
               mc.field_1724.method_31548().field_7545 = local2_2;
               mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
            }

            if (local3_3 != -1) {
               mc.field_1724.method_31548().field_7545 = local3_3;
               mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
            }

            mc.field_1724.method_18800(mc.field_1724.method_18798().field_1352, 0.335F, mc.field_1724.method_18798().field_1350);
         }

         if (this.modeSetting.contains("Grim Water")) {
            local4_4 = 0.0F;
            if (local2_2 != -1) {
               class_239 local5_0 = RayTraceUtil.rayTrace(3.0, mc.field_1724.method_36454(), mc.field_1724.method_36455());
               class_2248 local6_1 = mc.field_1687.method_8320(class_2338.method_49638(local5_0.method_17784())).method_26204();
               if (!(local6_1 instanceof class_2354)) {
                  mc.field_1724.method_31548().field_7545 = local2_2;
                  mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
                  mc.field_1724.method_6104(class_1268.field_5808);
                  if (mc.field_1724.method_52535()) {
                     mc.field_1724.method_18800(mc.field_1724.method_18798().field_1352, 0.4F, mc.field_1724.method_18798().field_1350);
                  }
               }
            }
         }

         RotationManager.INSTANCE.rotate(mc.field_1724.method_36454(), local4_4);
      }
   };

   public SpiderFeature() {
      super("Spider", MenuCategory.MOVEMENT);
      this.setting(this.modeSetting);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      RotationManager.INSTANCE.reset();
   }

   @Generated
   public ListSetting getModeSetting() {
      return this.modeSetting;
   }

   @Generated
   public EventListener<UpdateLivingEvent> getOnUpdate() {
      return this.onUpdate;
   }
}
