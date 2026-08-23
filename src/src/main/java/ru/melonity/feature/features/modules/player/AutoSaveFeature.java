package ru.melonity.feature.features.modules.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PreMotionEvent;
import ru.melonity.util.inventory.InventoryUtil;
import ru.melonity.util.inventory.old.switcher.Switcher;
import ru.melonity.util.inventory.old.switcher.Switchers;
import ru.melonity.util.time.Timer;

@Environment(EnvType.CLIENT)
public class AutoSaveFeature extends SimpleFeature {
   private final Timer timer = Timer.create();
   private final Timer rotateTimer = Timer.create();
   private class_1792 item = null;
   private boolean hasRotated = false;
   private final Switcher switcher = Switchers.legit();
   private final EventListener<PreMotionEvent> onPreMotion = event -> {
      class_3965 local2_3 = mc.field_1687
         .method_17742(
            new class_3959(
               mc.field_1724.method_19538(),
               mc.field_1724.method_19538().method_1031(0.0, -2.0, 0.0),
               class_3960.field_17558,
               class_242.field_1348,
               mc.field_1724
            )
         );
      if (local2_3.method_17783() != class_240.field_1332) {
         this.hasRotated = false;
      } else {
         if (mc.field_1724.field_6017 > 2.0F && this.timer.hasReached(500L, true)) {
            class_1792[] local3_2 = new class_1792[]{
               class_1802.field_8828, class_1802.field_17528, class_1802.field_21087, class_1802.field_8705, class_1802.field_8786
            };

            for (class_1792 local7_1 : local3_2) {
               int local8_0 = InventoryUtil.getHotbarItem(mc.field_1724, local7_1);
               if (local8_0 != -1) {
                  if (!this.hasRotated) {
                     event.setPitch(89.0F);
                     RotationManager.INSTANCE.rotate(mc.field_1724.method_36454(), 89.0F);
                     this.switcher.fromHotbar(local8_0);
                     this.item = local7_1;
                     this.hasRotated = true;
                     this.rotateTimer.sleep(10L, true);
                     return;
                  }
                  break;
               }
            }
         }

         if (this.hasRotated && !this.rotateTimer.isSleeping() && this.item != null) {
            event.setPitch(89.0F);
            RotationManager.INSTANCE.rotate(mc.field_1724.method_36454(), 89.0F);
            if (this.item == class_1802.field_8705) {
               mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
            } else {
               mc.field_1761.method_2896(mc.field_1724, class_1268.field_5808, local2_3);
            }

            mc.field_1724.method_6104(class_1268.field_5808);
            this.switcher.returnHotbar();
            this.hasRotated = false;
            this.item = null;
         }
      }
   };

   public AutoSaveFeature() {
      super("AutoSave", MenuCategory.PLAYER);
   }
}
