package ru.melonity.feature.features.modules.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.util.minecraft.RotationUtil;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderGameWorldEvent;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.render.BoxRenderer;

@Environment(EnvType.CLIENT)
public class NukerFeature extends SimpleFeature {
   private static final Map<class_2248, Integer> ORE_PRIORITIES = new HashMap<class_2248, Integer>() {
      {
         this.put(class_2246.field_22109, Integer.valueOf(100));
         this.put(class_2246.field_10442, Integer.valueOf(90));
         this.put(class_2246.field_10013, Integer.valueOf(85));
         this.put(class_2246.field_23077, Integer.valueOf(80));
         this.put(class_2246.field_10571, Integer.valueOf(75));
         this.put(class_2246.field_10212, Integer.valueOf(70));
         this.put(class_2246.field_10080, Integer.valueOf(65));
         this.put(class_2246.field_10090, Integer.valueOf(60));
         this.put(class_2246.field_10418, Integer.valueOf(55));
         this.put(class_2246.field_10213, Integer.valueOf(50));
      }
   };
   private final NumberSetting radiusXZ = new NumberSetting("nuker.radius_xz", value -> String.format("%d", value.intValue()), 1, 5, 3);
   private final NumberSetting radiusY = new NumberSetting("nuker.radius_y", value -> String.format("%d", value.intValue()), 0, 6, 1);
   private final ListSetting priority = new ListSetting(
      "nuker.priority", ListSetting.ModeType.SINGLE, "nuker.on_ores", "nuker.on_ores", "nuker.only_on_ores", "nuker.no_priority"
   );
   private final BooleanSetting ignoreY = new BooleanSetting("nuker.ignore_y", true);
   private final BooleanSetting rotate = new BooleanSetting("nuker.rotate", false);
   private final BooleanSetting raytraceCheck = new BooleanSetting("nuker.raytrace", false);
   private class_238 box;
   private class_2338 pos;
   private final EventListener<RenderGameWorldEvent> on3D = event -> {
      if (this.pos != null && this.box != null) {
         BoxRenderer.blockEsp(this.box, Melonity.INSTANCE.styleManager.getStyle().ascent(), event.poseStack().method_23760().method_23761());
      }
   };
   private final EventListener<UpdateEvent> onUpdate = event -> this.updateNuker();

   public NukerFeature() {
      super("Nuker", MenuCategory.PLAYER, false);
      this.setting(this.radiusXZ);
      this.setting(this.radiusY);
      this.setting(this.priority);
      this.setting(this.ignoreY);
      this.setting(this.rotate);
      this.setting(this.raytraceCheck);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      if (this.rotate.get()) {
         RotationManager.INSTANCE.reset();
      }
   }

   public void updateNuker() {
      if (this.inInvalidState() || mc.field_1761 == null) {
         this.pos = null;
         this.box = null;
      } else {
         this.pos = this.find();
         if (this.pos == null) {
            this.box = null;
            mc.field_1761.method_2925();
            if (this.rotate.get()) {
               RotationManager.INSTANCE.reset();
            }
         } else {
            this.box = new class_238(this.pos);
            class_243 eyes = mc.field_1724.method_33571();
            class_243 blockCenter = this.pos.method_46558();
            if (this.rotate.get()) {
               float[] angles = RotationUtil.calculateAngle(eyes, blockCenter);
               RotationManager.INSTANCE.rotate(angles[0], angles[1]);
            }

            class_2350 face = class_2350.method_10142(
               eyes.field_1352 - blockCenter.field_1352, eyes.field_1351 - blockCenter.field_1351, eyes.field_1350 - blockCenter.field_1350
            );
            mc.field_1761.method_2902(this.pos, face);
            mc.field_1724.method_6104(class_1268.field_5808);
         }
      }
   }

   private class_2338 find() {
      ArrayList<class_2338> local1_4 = new ArrayList<>();
      ArrayList<class_2338> local2_5 = new ArrayList<>();

      for (int local3_3 = (int)(-this.radiusXZ.getValue().floatValue()); (float)local3_3 < this.radiusXZ.getValue().floatValue(); local3_3++) {
         for (int local4_2 = this.ignoreY.get() ? 0 : (int)(-this.radiusY.getValue().floatValue());
            (float)local4_2 < this.radiusY.getValue().floatValue();
            local4_2++
         ) {
            for (int local5_1 = (int)(-this.radiusXZ.getValue().floatValue()); (float)local5_1 < this.radiusXZ.getValue().floatValue(); local5_1++) {
               class_2338 local6_0 = class_2338.method_49638(mc.field_1724.method_19538().method_1031((double)local3_3, (double)local4_2, (double)local5_1));
               if (this.validBlock(local6_0)) {
                  if (this.raytraceCheck.get()) {
                     if (this.isBlockVisible(local6_0, mc.field_1724.method_33571())) {
                        this.processBlock(local6_0, local1_4, local2_5);
                     }
                  } else {
                     this.processBlock(local6_0, local1_4, local2_5);
                  }
               }
            }
         }
      }

      if (this.priority.contains("nuker.on_ores") && !local2_5.isEmpty()) {
         local2_5.sort((p1, p2) -> {
            class_2248 local2_0 = mc.field_1687.method_8320(p1).method_26204();
            class_2248 local3_1 = mc.field_1687.method_8320(p2).method_26204();
            int local4_2x = Integer.compare(ORE_PRIORITIES.getOrDefault(local3_1, 0), ORE_PRIORITIES.getOrDefault(local2_0, 0));
            if (local4_2x != 0) {
               return local4_2x;
            } else {
               double local5_3 = p1.method_40081(0.5, 0.5, 0.5);
               double local7_4 = p2.method_40081(0.5, 0.5, 0.5);
               return Double.compare(local5_3, local7_4);
            }
         });
         return local2_5.getFirst();
      } else if (this.priority.contains("nuker.only_on_ores") && !local2_5.isEmpty()) {
         local2_5.sort((p1, p2) -> {
            class_2248 local2_0 = mc.field_1687.method_8320(p1).method_26204();
            class_2248 local3_1 = mc.field_1687.method_8320(p2).method_26204();
            int local4_2x = Integer.compare(ORE_PRIORITIES.getOrDefault(local3_1, 0), ORE_PRIORITIES.getOrDefault(local2_0, 0));
            if (local4_2x != 0) {
               return local4_2x;
            } else {
               double local5_3 = p1.method_40081(0.5, 0.5, 0.5);
               double local7_4 = p2.method_40081(0.5, 0.5, 0.5);
               return Double.compare(local5_3, local7_4);
            }
         });
         return local2_5.getFirst();
      } else if (!local1_4.isEmpty()) {
         local1_4.sort(Comparator.comparingDouble(p -> p.method_40081(0.5, 0.5, 0.5)));
         return local1_4.getFirst();
      } else {
         return null;
      }
   }

   private boolean validBlock(class_2338 pos) {
      class_2680 local2_0 = mc.field_1687.method_8320(pos);
      return !local2_0.method_26215()
         && local2_0.method_26204() != class_2246.field_10543
         && local2_0.method_26204() != class_2246.field_10382
         && local2_0.method_26204() != class_2246.field_10164
         && local2_0.method_26204() != class_2246.field_9987
         && local2_0.method_26204() != class_2246.field_10499;
   }

   private boolean isOre(class_2248 block) {
      return block == class_2246.field_10418
         || block == class_2246.field_10212
         || block == class_2246.field_10571
         || block == class_2246.field_10442
         || block == class_2246.field_10013
         || block == class_2246.field_10090
         || block == class_2246.field_10080
         || block == class_2246.field_10213
         || block == class_2246.field_23077;
   }

   private void processBlock(class_2338 pos, List<class_2338> validBlocks, List<class_2338> oreBlocks) {
      class_2248 local4_0 = mc.field_1687.method_8320(pos).method_26204();
      if (this.isOre(local4_0)) {
         oreBlocks.add(pos);
      }

      if (!this.priority.contains("nuker.only_on_ores")) {
         validBlocks.add(pos);
      }
   }

   private boolean isBlockVisible(class_2338 targetPos, class_243 eyePos) {
      class_243 local3_0 = new class_243((double)targetPos.method_10263() + 0.5, (double)targetPos.method_10264() + 0.5, (double)targetPos.method_10260() + 0.5);
      class_3959 local4_1 = new class_3959(eyePos, local3_0, class_3960.field_17558, class_242.field_1348, mc.field_1724);
      class_3965 local5_2 = mc.field_1687.method_17742(local4_1);
      return local5_2.method_17783() == class_240.field_1332
         && local5_2.method_17784().field_1352 == (double)targetPos.method_10263()
         && local5_2.method_17784().field_1350 == (double)targetPos.method_10260()
         && local5_2.method_17784().field_1351 == (double)targetPos.method_10264();
   }
}
