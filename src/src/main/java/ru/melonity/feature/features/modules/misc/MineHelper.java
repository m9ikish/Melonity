package ru.melonity.feature.features.modules.misc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1531;
import net.minecraft.class_1713;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_1810;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4050;
import net.minecraft.class_408;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.drag.Drag;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.RenderGameWorldEvent;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.render.BoxRenderer;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;
import ru.melonity.util.time.Timer;

@Environment(EnvType.CLIENT)
public class MineHelper extends SimpleFeature {
   private final BooleanSetting glowOre = new BooleanSetting("Подсвечивать руду", true);
   private final BooleanSetting throwTrash = new BooleanSetting("Выкидывать мусор", true);
   private final Drag render = new Drag(12, 415.0F, 400.0F, 110.0F, 45.0F, () -> true);
   private final Map<String, class_1792> itemMap = new HashMap<>();
   private final Timer timer = Timer.create();
   private static final class_2248[] ORES = new class_2248[]{
      class_2246.field_10442,
      class_2246.field_10571,
      class_2246.field_10013,
      class_2246.field_10418,
      class_2246.field_10090,
      class_2246.field_10080,
      class_2246.field_10212,
      class_2246.field_22109
   };
   private final List<class_2338> foundBlocks = new ArrayList<>();
   private String nextMine = "";
   private String updateTime = "";
   Color colorMine;
   int p;
   public final EventListener<RenderGameWorldEvent> onRender3D = event -> {
      if (this.glowOre.get()) {
         this.searchBlocks();
         this.renderBlocks(event);
      }
   };
   private final StateAnimation alphaAnimation = new StateAnimation();
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(200L);
   public final EventListener<RenderOverlayEvent> onRender2D = event -> {
      MenuStyle local2_0 = Melonity.INSTANCE.styleManager.getStyle();
      RenderSystem local3_1 = new RenderSystem();
      float local4_2 = 20.0F;
      ScaleUtils.rescale(2.0);
      this.alphaAnimation.state(local4_2 > 20.0F || class_310.method_1551().field_1755 instanceof class_408);
      this.alphaAnimation.update(this.fwc.elapsedUnits());
      local3_1.setAlphaModifier((float)class_3532.method_15350(this.alphaAnimation.value, 0.0, 1.0));
      local3_1.addRect(
         this.render.getX(), this.render.getY(), this.render.getWidth(), this.render.getHeight(), 6.0F, local2_0.background(), event.matrixStack()
      );
      local3_1.addText(Fonts.INTER_REGULAR_13, "MineHelper", this.render.getX() + 15.0F, this.render.getY() + 8.0F, local2_0.ascent(), event.matrixStack());
      ScaleUtils.rescaleMC();
      local3_1.setAlphaModifier(1.0F);
   };
   public final EventListener<UpdateEvent> onUpdate = event -> {
      if (mc.field_1724.method_6047().method_7909() instanceof class_1810
         && mc.field_1724.method_6047().method_7936() - mc.field_1724.method_6047().method_7919() <= 150
         && mc.field_1724.field_6012 % 100 == 0) {
         Melonity.INSTANCE.getNotificationManager().add(new Notification("MineHelper", "The pickaxe is about to break!", "tips.png"));
      }

      if (this.throwTrash.get() && this.timer.hasReached(100L)) {
         for (String local3_2 : this.itemMap.keySet()) {
            class_1792 local4_0 = this.itemMap.get(local3_2);
            int local5_1 = this.findItem(local4_0);
            if (local5_1 != -1) {
               this.p = 3;
               mc.field_1761.method_2906(0, local5_1, 1, class_1713.field_7795, mc.field_1724);
               this.timer.reset();
               break;
            }
         }
      }
   };
   public final EventListener<EventInput> onInput = event -> {
      if (this.p > 0) {
         event.setForward(0.0F);
         this.p--;
      }
   };

   public MineHelper() {
      super("MineHelper", MenuCategory.MISC);
      this.settings(new MenuFeatureView[]{this.glowOre, this.throwTrash});
      this.itemMap.put("Земля", class_1802.field_8831);
      this.itemMap.put("Булыжник", class_1802.field_20412);
      this.itemMap.put("Камень", class_1802.field_20391);
      this.itemMap.put("Диорит", class_1802.field_20401);
      this.itemMap.put("Андезит", class_1802.field_20407);
      this.itemMap.put("Гранит", class_1802.field_20394);
      this.itemMap.put("Гравий", class_1802.field_8110);
      this.itemMap.put("Песок", class_1802.field_8858);
      this.itemMap.put("Песчаник", class_1802.field_20384);
      this.itemMap.put("Незер-камень", class_1802.field_8328);
   }

   private void fetchMineData() {
      for (class_1297 local2_2 : mc.field_1687.method_18112()) {
         if (local2_2 instanceof class_1531) {
            class_1531 local3_1 = (class_1531)local2_2;
            if (local3_1.method_5767()) {
               String local4_0 = local3_1.method_5477().getString();
               if (local4_0.startsWith("Следующая:")) {
                  // The guard admits the 10-char prefix but this used to slice 11,
                  // so a stand named exactly "Следующая:" threw, and a missing space
                  // ate the first character of the mine name.
                  this.nextMine = local4_0.substring("Следующая:".length()).trim();
                  if (this.nextMine.contains("Обычная")) {
                     this.colorMine = Color.ORANGE;
                  }

                  if (this.nextMine.contains("Мифическая")) {
                     this.colorMine = Color.MAGENTA;
                  }

                  if (this.nextMine.contains("Легендарная")) {
                     this.colorMine = Color.CYAN;
                  }
               } else if (local4_0.matches("\\d{2}:\\d{2}")) {
                  this.updateTime = local4_0;
               }
            }
         }
      }
   }

   private void searchBlocks() {
      this.foundBlocks.clear();
      int local1_8 = 12;
      class_2338 local2_9 = mc.field_1724.method_24515();
      class_243 local3_10 = new class_243(
         (double)local2_9.method_10263() + 0.5,
         (double)((float)local2_9.method_10264() + mc.field_1724.method_18381(class_4050.field_18076)),
         (double)local2_9.method_10260() + 0.5
      );

      for (int local4_7 = -local1_8; local4_7 <= local1_8; local4_7++) {
         for (int local5_6 = -local1_8; local5_6 <= local1_8; local5_6++) {
            for (int local6_5 = -local1_8; local6_5 <= local1_8; local6_5++) {
               class_2338 local7_2 = local2_9.method_10069(local4_7, local5_6, local6_5);
               class_243 local8_3 = new class_243(
                     (double)local7_2.method_10263() + 0.5 - local3_10.field_1352,
                     (double)local7_2.method_10264() + 0.5 - local3_10.field_1351,
                     (double)local7_2.method_10260() + 0.5 - local3_10.field_1350
                  )
                  .method_1029();
               double local9_4 = mc.field_1724.method_5720().method_1026(local8_3);
               if (local9_4 > 0.0) {
                  class_2248 local11_1 = mc.field_1687.method_8320(local7_2).method_26204();

                  for (class_2248 local15_0 : ORES) {
                     if (local11_1 == local15_0) {
                        this.foundBlocks.add(local7_2);
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   private void renderBlocks(RenderGameWorldEvent event) {
      Color local2_3 = null;

      for (class_2338 local4_2 : this.foundBlocks) {
         class_2248 local5_0 = mc.field_1687.method_8320(local4_2).method_26204();
         if (local5_0 == class_2246.field_10442) {
            local2_3 = Color.cyan;
         } else if (local5_0 == class_2246.field_10571) {
            local2_3 = Color.ORANGE;
         } else if (local5_0 == class_2246.field_10013) {
            local2_3 = Color.green;
         } else if (local5_0 == class_2246.field_10418) {
            local2_3 = Color.BLACK;
         } else if (local5_0 == class_2246.field_10090) {
            local2_3 = Color.blue;
         } else if (local5_0 == class_2246.field_10080) {
            local2_3 = Color.RED;
         } else if (local5_0 == class_2246.field_10212) {
            local2_3 = Color.gray;
         } else if (local5_0 == class_2246.field_22109) {
            local2_3 = Color.darkGray;
         }

         class_238 local6_1 = new class_238(local4_2);
         BoxRenderer.blockEsp(local6_1, local2_3, event.poseStack().method_23760().method_23761());
      }
   }

   private int findItem(class_1792 item) {
      if (mc.field_1724 != null && mc.field_1724.method_31548() != null) {
         for (int local2_0 = 0; local2_0 < 45; local2_0++) {
            if (mc.field_1724.method_31548().method_5438(local2_0).method_7909() == item) {
               return local2_0 < 9 ? local2_0 + 36 : local2_0;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }
}
