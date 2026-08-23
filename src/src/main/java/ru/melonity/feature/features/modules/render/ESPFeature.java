package ru.melonity.feature.features.modules.render;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import net.minecraft.class_4604;
import net.minecraft.class_465;
import net.minecraft.class_5498;
import net.minecraft.class_7833;
import org.joml.Vector4d;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.ILevelRenderer;
import ru.melonity.fabric.client.model.IPlayer;
import ru.melonity.feature.features.modules.render.esp.ArmorESPComponent;
import ru.melonity.feature.features.modules.render.esp.BoxESPComponent;
import ru.melonity.feature.features.modules.render.esp.ESPComponent;
import ru.melonity.feature.features.modules.render.esp.HealthESPComponent;
import ru.melonity.feature.features.modules.render.esp.NametagESPComponent;
import ru.melonity.feature.features.modules.render.esp.PotionsESPComponent;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.Rotation;
import ru.melonity.font.Fonts;
import ru.melonity.service.friend.FriendService;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderGameWorldEvent;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.event.events.RenderOverlayInContainerEvent;
import ru.melonity.util.minecraft.MovementUtil;
import ru.melonity.util.render.ProjectUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class ESPFeature extends SimpleFeature {
   private static final Predicate<class_1657> IS_VALID = entity -> {
      ESPFeature local1_0 = Melonity.INSTANCE.getFeatureManager().espFeature;
      if (!local1_0.targets.getMode().contains("esp.naked") && entity.method_6096() == 0) {
         return false;
      } else if (!local1_0.targets.getMode().contains("esp.friends")
         && Melonity.INSTANCE.componentManager.service(FriendService.class).hasFriend(entity.method_5477().getString())) {
         return false;
      } else {
         return !local1_0.targets.getMode().contains("esp.invisibles") && entity.method_5767() ? false : entity.method_5805() && entity.field_6213 <= 0;
      }
   };
   private static final List<ESPComponent> components = List.of(
      new BoxESPComponent(), new HealthESPComponent(), new NametagESPComponent(), new ArmorESPComponent(), new PotionsESPComponent()
   );
   private static final Map<String, ESPComponent> COMPONENTS_BY_NAME = components.stream()
      .collect(Collectors.toUnmodifiableMap(ESPComponent::name, component -> (ESPComponent)component));
   private final StateAnimation animation = new StateAnimation();
   private final FrameWeightCalculator frameWeightCalculator = FrameWeightCalculator.milliseconds(400L);
   private final ListSetting espComp = new ListSetting("esp.mode", ListSetting.ModeType.MULTI, "esp.player-esp", "esp.player-esp", "esp.item-esp");
   private final ListSetting elements = new ListSetting(
         "esp.elements",
         ListSetting.ModeType.MULTI,
         Arrays.asList("esp.elements.box", "esp.elements.health", "esp.elements.nametag"),
         "esp.elements.box",
         "esp.elements.health",
         "esp.elements.nametag",
         "esp.elements.armor",
         "esp.elements.potions"
      )
      .setVisible(() -> this.espComp.contains("esp.player-esp"));
   private final ListSetting targets = new ListSetting("esp.targets", ListSetting.ModeType.MULTI, "esp.naked", "esp.invisibles", "esp.naked", "esp.friends")
      .setVisible(() -> this.espComp.contains("esp.player-esp"));
   private final BooleanSetting oof = new BooleanSetting("esp.oof", true).setVisible(() -> this.espComp.contains("esp.player-esp"));
   private final BooleanSetting allItems = new BooleanSetting("global.all", false).setVisible(() -> this.espComp.contains("esp.item-esp"));
   private final ListSetting items = new ListSetting("global.items", ListSetting.ModeType.MULTI, "itemesp.ores", "itemesp.ores", "itemesp.ancient_debris")
      .setVisible(() -> this.espComp.contains("esp.item-esp") && !this.allItems.get());
   private final ListSetting modeShader = new ListSetting("global.mode", ListSetting.ModeType.SINGLE, "Galaxy 1", "Galaxy 1", "Galaxy 2", "Galaxy 3")
      .setVisible(() -> this.espComp.contains("esp.player-shader-esp"));
   private final EventListener<RenderGameWorldEvent> onWorld = event -> {
      if (mc.field_1690 != null) {
         if (this.espComp.contains("esp.player-esp")) {
            float local2_3 = mc.method_60646().method_60637(true);
            class_4604 local3_4 = ((ILevelRenderer)mc.field_1769).getFrustum();

            for (class_1657 local5_2 : mc.field_1687.method_18456()) {
               if (mc.field_1690.method_31044() == null) {
                  return;
               }

               IPlayer local6_0 = (IPlayer)local5_2;
               local6_0.setEspPosition(null);
               if (IS_VALID.test(local5_2)) {
                  Vector4d local7_1 = this.updatePlayerPositions(local5_2, local2_3);
                  if (local3_4.method_23093(local5_2.method_5829()) && (mc.field_1690.method_31044() != class_5498.field_26664 || local5_2 != mc.field_1724)) {
                     local6_0.setEspPosition(local7_1);
                  }
               }
            }
         }
      }
   };
   private final EventListener<RenderOverlayEvent> onRenderOverlay = event -> {
      class_4587 local2_14 = event.matrixStack();
      class_332 local3_15 = event.graphics();
      RenderSystem local4_16 = Melonity.INSTANCE.getRenderSystem();
      if (this.espComp.contains("esp.player-esp")) {
         boolean local5_4 = false;

         for (class_1657 local7_3 : mc.field_1687.method_18456()) {
            Vector4d local8_2 = ((IPlayer)local7_3).espPosition();
            if (local8_2 != null && local7_3 != mc.field_1724) {
               if (!local5_4) {
                  com.mojang.blaze3d.systems.RenderSystem.depthMask(false);
                  local5_4 = true;
               }

               for (String local10_1 : this.elements.getMode()) {
                  ESPComponent local11_0 = COMPONENTS_BY_NAME.get(local10_1);
                  if (local11_0 != null) {
                     if (local11_0.name().equals("esp.elements.armor") && !this.elements.contains("esp.elements.nametag")) {
                        local8_2.y += 16.0;
                     }

                     local11_0.render(local7_3, local8_2, local2_14, local3_15, local4_16);
                  }
               }
            }
         }

         if (local5_4) {
            com.mojang.blaze3d.systems.RenderSystem.depthMask(true);
         }
      }

      if (this.espComp.contains("esp.item-esp")) {
         float local5_12 = mc.method_60646().method_60637(true);
         class_4604 local6_13 = ((ILevelRenderer)mc.field_1769).getFrustum();

         for (class_1297 local8_11 : mc.field_1687.method_18112()) {
            if (local8_11 instanceof class_1542) {
               class_1542 local9_10 = (class_1542)local8_11;
               class_1792 local10_7 = local9_10.method_6983().method_7909();
               if (this.checkBlock(local10_7) && local6_13.method_23093(local9_10.method_5829())) {
                  class_243 local11_8 = local9_10.method_30950(local5_12);
                  double[] local12_9 = ProjectUtil.project(local11_8.field_1352, local11_8.field_1351, local11_8.field_1350);
                  if (local12_9 != null) {
                     float local13_5 = (float)local12_9[0];
                     float local14_6 = (float)local12_9[1];
                     Melonity.INSTANCE
                        .getRenderSystem()
                        .addText(
                           Fonts.INTER_REGULAR_12,
                           local9_10.method_5476().getString(),
                           local13_5 - Fonts.INTER_REGULAR_12.getStringWidth(local9_10.method_5476().getString()) / 2.0F,
                           local14_6 + 10.0F,
                           event.matrixStack()
                        );
                  }
               }
            }
         }
      }
   };
   private final EventListener<RenderOverlayInContainerEvent> onRenderInContainer = event -> {
      if (mc.field_1690 != null && mc.field_1690.method_31044() != null) {
         if (this.oof.get() && this.espComp.contains("esp.player-esp") && mc.field_1690.method_31044() == class_5498.field_26664) {
            boolean local2_12 = MovementUtil.isMoving();
            this.animation.state(local2_12);
            this.animation.update(this.frameWeightCalculator.elapsedUnits());
            float local3_13 = this.animation.animation() * -12.0F;
            class_4587 local4_14 = event.matrixStack();

            for (class_1657 local6_11 : mc.field_1687.method_18456()) {
               if (local6_11 != mc.field_1724 && !((ILevelRenderer)mc.field_1769).getFrustum().method_23093(local6_11.method_5829())) {
                  MenuStyle local7_0 = Melonity.INSTANCE.styleManager.getStyle();
                  Color local8_1 = local7_0.ascent();
                  if (Melonity.INSTANCE.componentManager.service(FriendService.class).hasFriend(local6_11.method_5477().getString())) {
                     local8_1 = Color.ORANGE;
                  }

                  float local9_2 = Rotation.cameraYaw();
                  double local10_3 = local6_11.field_6014
                     + (local6_11.method_23317() - local6_11.field_6014) * (double)mc.method_60646().method_60637(true)
                     - mc.method_31975().field_4344.method_19326().field_1352;
                  double local12_4 = local6_11.field_5969
                     + (local6_11.method_23321() - local6_11.field_5969) * (double)mc.method_60646().method_60637(true)
                     - mc.method_31975().field_4344.method_19326().field_1350;
                  double local14_5 = Math.atan2(local12_4, local10_3);
                  double local16_6 = Math.toRadians((double)local9_2);
                  double local18_7 = local14_5 - local16_6 + Math.toRadians(180.0);
                  local4_14.method_22903();
                  double local20_8 = (double)(40 + (mc.field_1755 instanceof class_465 ? 95 : 0));
                  double local22_9 = (double)mc.method_22683().method_4486() / 2.0 + local20_8 * Math.cos(local18_7);
                  double local24_10 = (double)mc.method_22683().method_4502() / 2.0 + local20_8 * Math.sin(local18_7);
                  local4_14.method_22904(local22_9, local24_10, 0.0);
                  local4_14.method_22907(class_7833.field_40718.rotationDegrees((float)Math.toDegrees(local18_7) + 90.0F));
                  Melonity.INSTANCE
                     .getRenderSystem()
                     .addTexture(
                        class_2960.method_60656("melonity/images/features/arrow.png"), local3_13, local3_13, 16.0F, 16.0F, local8_1, event.matrixStack()
                     );
                  local4_14.method_22909();
               }
            }
         }
      }
   };

   public ESPFeature() {
      super("ESP", MenuCategory.RENDER);
      this.settings(new MenuFeatureView[]{this.espComp, this.elements, this.targets, this.oof, this.items, this.allItems, this.modeShader});
   }

   private Vector4d updatePlayerPositions(class_1297 player, float partialTicks) {
      class_243 local3_2 = player.method_30950(partialTicks);
      double local4_3 = local3_2.field_1352;
      double local6_4 = local3_2.field_1351;
      double local8_5 = local3_2.field_1350;
      class_238 local10_6 = player.method_5829();
      class_238 local11_7 = new class_238(
         local10_6.field_1323 - player.method_23317() + local4_3 - 0.05,
         local10_6.field_1322 - player.method_23318() + local6_4,
         local10_6.field_1321 - player.method_23321() + local8_5 - 0.05,
         local10_6.field_1320 - player.method_23317() + local4_3 + 0.05,
         local10_6.field_1325 - player.method_23318() + local6_4 + 0.15,
         local10_6.field_1324 - player.method_23321() + local8_5 + 0.05
      );
      class_243[] local12_8 = new class_243[]{
         new class_243(local11_7.field_1323, local11_7.field_1322, local11_7.field_1321),
         new class_243(local11_7.field_1323, local11_7.field_1325, local11_7.field_1321),
         new class_243(local11_7.field_1320, local11_7.field_1322, local11_7.field_1321),
         new class_243(local11_7.field_1320, local11_7.field_1325, local11_7.field_1321),
         new class_243(local11_7.field_1323, local11_7.field_1322, local11_7.field_1324),
         new class_243(local11_7.field_1323, local11_7.field_1325, local11_7.field_1324),
         new class_243(local11_7.field_1320, local11_7.field_1322, local11_7.field_1324),
         new class_243(local11_7.field_1320, local11_7.field_1325, local11_7.field_1324)
      };
      Vector4d local13_9 = null;

      for (class_243 local17_1 : local12_8) {
         double[] local18_0 = ProjectUtil.project(local17_1.field_1352, local17_1.field_1351, local17_1.field_1350);
         if (local18_0 != null) {
            local17_1 = new class_243(local18_0[0], local18_0[1], local18_0[2]);
            if (local17_1.field_1350 > 0.0 && local17_1.field_1350 < 1.0) {
               if (local13_9 == null) {
                  local13_9 = new Vector4d(local17_1.field_1352, local17_1.field_1351, local17_1.field_1350, 0.0);
               }

               local13_9.x = Math.min(local17_1.field_1352, local13_9.x);
               local13_9.y = Math.min(local17_1.field_1351, local13_9.y);
               local13_9.z = Math.max(local17_1.field_1352, local13_9.z);
               local13_9.w = Math.max(local17_1.field_1351, local13_9.w);
            }
         }
      }

      return local13_9;
   }

   private boolean checkBlock(class_1792 item) {
      if (this.allItems.get()) {
         return true;
      } else {
         return this.items.contains("itemesp.ores") && item.method_7864(new class_1799(item)).getString().toLowerCase().contains("ore")
            ? true
            : this.items.contains("itemesp.ancient_debris") && item == class_1802.field_22019;
      }
   }
}
