package ru.melonity.feature.features.modules.render;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1058;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1799;
import net.minecraft.class_1934;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4074;
import net.minecraft.class_4587;
import net.minecraft.class_6880;
import org.lwjgl.opengl.GL11;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IMobEffectInstance;
import ru.melonity.feature.features.modules.misc.PotionsHighlightFeature;
import ru.melonity.feature.features.modules.render.hud.ArrayListModule;
import ru.melonity.feature.features.modules.render.hud.BarView;
import ru.melonity.feature.features.modules.render.hud.DynamicIslandView;
import ru.melonity.feature.features.modules.render.hud.KeybindView;
import ru.melonity.feature.features.modules.render.hud.WatermarkView;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.drag.Drag;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.annotation.EventPriority;
import ru.melonity.util.event.events.BossbarRenderEvent;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.minecraft.KeyNameUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;

@Environment(EnvType.CLIENT)
public class HudFeature extends SimpleFeature {
   private final ListSetting modeSetting = new ListSetting(
      "hud.elements",
      ListSetting.ModeType.MULTI,
      "Watermark,Tips",
      "Watermark",
      "Dynamic Island",
      "ArrayList",
      "KeyBinds",
      "Armor Alert",
      "Potions",
      "Hotbar",
      "Tips"
   );
   private final BooleanSetting blurInMenu = new BooleanSetting("menu.blur", false);
   private final BooleanSetting shadowInMenu = new BooleanSetting("menu.shadow", true);
   private final WatermarkView watermarkView = new WatermarkView(17.5F);
   private final Drag watermarkDrag = new Drag(1, 9.0F, 13.0F, 1.0F, 1.0F, () -> this.modeSetting.getMode().contains("Watermark"));
   private final KeybindView keybindView = new KeybindView();
   private final Drag keybindDrag = new Drag(3, 594.0F, 172.0F, 1.0F, 1.0F, () -> this.modeSetting.getMode().contains("KeyBinds"));
   private final BarView healthView = new BarView(
      () -> mc.field_1724.method_6032() / mc.field_1724.method_6063(),
      class_2960.method_60656("melonity/images/ui/hotbar/heart_hotbar.png"),
      Color.decode("#F94263")
   );
   private final BarView feedView = new BarView(
      () -> (float)mc.field_1724.method_7344().method_7586() / 20.0F,
      class_2960.method_60656("melonity/images/ui/hotbar/feed_hotbar.png"),
      Color.decode("#E2D5AA")
   );
   private final BarView goldenView = new BarView(
      () -> class_3532.method_15363(mc.field_1724.method_6067() / 16.0F, 0.0F, 1.0F),
      class_2960.method_60656("melonity/images/ui/hotbar/golden_apple.png"),
      Color.decode("#EAEE57"),
      true
   );
   private final Drag armorAlertDrag = new Drag(16, 180.0F, 376.0F, 120.0F, 20.0F, () -> this.modeSetting.getMode().contains("Armor Alert"));
   private final Drag potionsDrag = new Drag(5, 200.0F, 160.0F, 400.0F, 40.0F, () -> this.modeSetting.getMode().contains("Potions"));
   private final Map<class_1293, Integer> effectInstanceMap = Maps.newLinkedHashMap();
   private final FrameWeightCalculator potionsFWC = FrameWeightCalculator.milliseconds(320L);
   private final StateAnimation potionsAnimation = new StateAnimation();
   private final FrameWeightCalculator frameWeightCalculator = FrameWeightCalculator.seconds(1L);
   private final StateAnimation stateAnimation = new StateAnimation();
   private final ArrayListModule arrayListModule = new ArrayListModule();
   private final DynamicIslandView dynamicIslandView = new DynamicIslandView();
   public static long timeOut = System.currentTimeMillis();
   private static final long TIPS_AUTO_DISABLE_DELAY_MS = 180000L;
   private long tipsManualEnableAt = 0L;
   private boolean tipsEnabledLastFrame = false;
   private final FrameWeightCalculator tipsFWC = FrameWeightCalculator.seconds(1L);
   private final StateAnimation tipsAnimation = new StateAnimation();
   @EventPriority(
      priority = 100
   )
   private final EventListener<RenderOverlayEvent> onRenderOverlay = this::handleRender;
   private final EventListener<UpdateEvent> onUpdate = event -> this.dynamicIslandView.handleUpdateTick(mc.field_1724);
   private final EventListener<BossbarRenderEvent> onBossbarRender = event -> {
   };

   public boolean isDynamicIslandEnabled() {
      return this.modeSetting.contains("Dynamic Island");
   }

   public DynamicIslandView getDynamicIslandView() {
      return this.dynamicIslandView;
   }

   public HudFeature() {
      super("HUD", MenuCategory.RENDER);
      this.settings(new MenuFeatureView[]{this.modeSetting, this.blurInMenu, this.shadowInMenu});
      this.setEnabled(true, true);
   }

   private void updateTipsAutoDisable() {
      List<String> local1_1 = this.modeSetting.getMode();
      boolean local2_2 = local1_1.contains("Tips");
      long local3_3 = System.currentTimeMillis();
      if (local2_2 && !this.tipsEnabledLastFrame) {
         this.tipsManualEnableAt = local3_3;
      }

      if (local2_2 && this.tipsManualEnableAt > 0L && local3_3 - this.tipsManualEnableAt >= 180000L) {
         List<String> local5_0 = Lists.newLinkedList(local1_1);
         if (local5_0.remove("Tips")) {
            this.modeSetting.setMode(local5_0);
         }

         local2_2 = false;
         this.tipsManualEnableAt = 0L;
      }

      if (!local2_2) {
         this.tipsManualEnableAt = 0L;
      }

      this.tipsEnabledLastFrame = local2_2;
   }

   private void handleRender(RenderOverlayEvent event) {
      RenderSystem renderSystem = Melonity.INSTANCE.getRenderSystem();
      MenuStyle style = Melonity.INSTANCE.getStyleManager().getStyle();
      class_4587 poseStack = event.matrixStack();
      class_332 graphics = event.graphics();
      this.updateTipsAutoDisable();
      this.renderWatermark(renderSystem, poseStack);
      this.renderDynamicIsland(renderSystem, poseStack);
      this.renderArrayList(renderSystem, poseStack);
      this.renderKeybinds(renderSystem, poseStack);
      this.renderArmorAlert(graphics, poseStack);
      this.renderHotbar(renderSystem, style, poseStack, graphics);
      this.renderPotions(renderSystem, style, poseStack, graphics);
      this.renderTips(renderSystem, style, poseStack);
   }

   private void renderTips(RenderSystem renderSystem, MenuStyle style, class_4587 poseStack) {
      ScaleUtils.rescale(2.0);
      boolean local4_0 = System.currentTimeMillis() - timeOut <= 15000L || this.modeSetting.getMode().contains("Tips");
      this.tipsAnimation.state(local4_0);
      this.tipsAnimation.update(this.tipsFWC.elapsedUnits());
      float local5_1 = this.tipsAnimation.animation();
      local5_1 = local5_1 == 1.0F ? 1.0F : (float)(1.0 - Math.pow(2.0, (double)(-10.0F * local5_1)));
      if (local5_1 < 0.01F) {
         ScaleUtils.rescaleMC();
      } else {
         Map<String, String> local6_2 = this.createTipsMap();
         float local7_3 = (float)mc.method_22683().method_4507() / 4.0F - (float)local6_2.size() * 18.0F / 2.0F;
         HudFeature.TipRenderConstants local8_4 = new HudFeature.TipRenderConstants();
         Color local9_5 = new Color(0, 0, 0, (int)(159.0F * local5_1));
         this.renderTipEntries(renderSystem, style, poseStack, local6_2, local7_3, local5_1, local9_5, local8_4);
         ScaleUtils.rescaleMC();
      }
   }

   private Map<String, String> createTipsMap() {
      Map<String, String> local1_0 = Maps.newLinkedHashMap();
      String local2_1 = KeyNameUtil.getKeyName(mc.field_1690.field_1890.method_1429());
      local1_0.put("R SHIFT", "Открыть/скрыть меню");
      local1_0.put(local2_1.toUpperCase(), "Настройка HUD элементов");
      return local1_0;
   }

   private void renderTipEntries(
      RenderSystem renderSystem,
      MenuStyle style,
      class_4587 poseStack,
      Map<String, String> tips,
      float startOffset,
      float alpha,
      Color backgroundColor,
      HudFeature.TipRenderConstants constants
   ) {
      float local9_1 = startOffset;

      for (Entry<String, String> local11_0 : tips.entrySet()) {
         this.renderSingleTip(renderSystem, style, poseStack, local11_0.getKey(), local11_0.getValue(), local9_1, alpha, backgroundColor, constants);
         local9_1 += 18.0F + 5.0F;
      }
   }

   private void renderSingleTip(
      RenderSystem renderSystem,
      MenuStyle style,
      class_4587 poseStack,
      String keyText,
      String valueText,
      float yOffset,
      float alpha,
      Color backgroundColor,
      HudFeature.TipRenderConstants constants
   ) {
      float local10_0 = Fonts.INTER_SEMIBOLD_15.getStringWidth(keyText);
      float local11_1 = local10_0 + 10.0F;
      float local12_2 = 3.0F + local11_1 + 5.0F + Fonts.INTER_REGULAR_14.getStringWidth(valueText) + 4.0F;
      renderSystem.addRect(10.0F, yOffset, local12_2, 18.0F, 6.0F, backgroundColor, poseStack);
      renderSystem.addRect(10.0F + 2.0F, yOffset + 2.0F, local11_1, 14.0F, 4.0F, RenderSystem.setAlpha(style.ascent(), (int)(alpha * 255.0F)), poseStack);
      float local13_3 = 10.0F + 2.0F + local11_1 / 2.0F - local10_0 / 2.0F;
      renderSystem.addText(
         Fonts.INTER_SEMIBOLD_16, keyText, local13_3, yOffset + 7.5F, RenderSystem.setAlpha(Color.decode("#181818"), (int)(alpha * 255.0F)), poseStack
      );
      float local14_4 = 10.0F + 2.0F + local11_1 + 5.0F;
      renderSystem.addText(Fonts.INTER_REGULAR_14, valueText, local14_4, yOffset + 7.5F, new Color(255, 255, 255, (int)(alpha * 255.0F)), poseStack);
   }

   private void renderWatermark(RenderSystem renderSystem, class_4587 poseStack) {
      this.watermarkDrag.setWidth(this.watermarkView.getWidth());
      this.watermarkDrag.setHeight(this.watermarkView.getHeight());
      if (this.watermarkDrag.canDraw()) {
         this.watermarkView.insert(this.watermarkDrag.getX(), this.watermarkDrag.getY(), 0.0, 0.0, renderSystem, poseStack);
      }
   }

   private void renderDynamicIsland(RenderSystem renderSystem, class_4587 poseStack) {
      if (this.isDynamicIslandEnabled()) {
         this.dynamicIslandView.update(mc.field_1724);
         float local3_0 = this.dynamicIslandView.estimateWidth();
         if (!(local3_0 <= 0.0F)) {
            float local4_1 = (float)mc.method_22683().method_4480() / 4.0F - local3_0 / 2.0F;
            float local5_2 = 6.0F;
            this.dynamicIslandView.insert(local4_1, local5_2, 0.0, 0.0, renderSystem, poseStack);
         }
      }
   }

   private void renderArrayList(RenderSystem renderSystem, class_4587 poseStack) {
      if (this.modeSetting.getMode().contains("ArrayList")) {
         float local3_0 = this.arrayListModule.getWidth();
         if (!(local3_0 <= 0.0F)) {
            float local4_1 = (float)mc.method_22683().method_4480() / 2.0F;
            float local5_2 = this.watermarkDrag.getX();
            float local6_3 = this.watermarkDrag.getY();
            float local7_4 = local4_1 - local3_0 - local5_2;
            this.arrayListModule.insert(local7_4, local6_3, 0.0, 0.0, renderSystem, poseStack);
         }
      }
   }

   private void renderKeybinds(RenderSystem renderSystem, class_4587 poseStack) {
      if (this.modeSetting.getMode().contains("KeyBinds")) {
         this.keybindView.insert(this.keybindDrag.getX(), this.keybindDrag.getY(), 0.0, 0.0, renderSystem, poseStack);
         this.keybindDrag.setWidth(this.keybindView.getWidth());
         this.keybindDrag.setHeight(this.keybindView.getHeight());
      }
   }

   private void renderArmorAlert(class_332 graphics, class_4587 poseStack) {
      if (this.modeSetting.getMode().contains("Armor Alert")) {
         ScaleUtils.rescale(2.0);
         float local3_1 = this.armorAlertDrag.getX();
         float local4_2 = this.armorAlertDrag.getY();
         List<class_1799> local5_3 = Lists.newArrayList(mc.field_1724.method_56674());
         Collections.reverse(local5_3);

         for (class_1799 local7_0 : local5_3) {
            if (local7_0 != null && !local7_0.method_7960()) {
               graphics.method_51427(local7_0, (int)local3_1, (int)local4_2);
               graphics.method_51432(mc.field_1772, local7_0, (int)local3_1, (int)local4_2, "");
               local3_1 += 18.0F;
            }
         }

         ScaleUtils.rescaleMC();
      }
   }

   private void renderHotbar(RenderSystem renderSystem, MenuStyle style, class_4587 poseStack, class_332 graphics) {
      if (this.modeSetting.getMode().contains("Hotbar")) {
         ScaleUtils.rescale(2.0);
         float local5_0 = 183.0F;
         float local6_1 = 23.5F;
         float local7_2 = (float)mc.method_22683().method_4480() / 4.0F - local5_0 / 2.0F;
         float local8_3 = 4.0F;
         float local9_4 = (float)mc.method_22683().method_4507() / 2.0F - local6_1 - local8_3;
         float local10_5 = 19.0F;
         float local11_6 = local6_1 / 2.0F;
         renderSystem.addRect(local7_2, local9_4, local5_0, local6_1, local11_6, style.background(), poseStack);
         this.renderOffhandItem(renderSystem, style, poseStack, graphics, local7_2, local9_4, local6_1, local10_5);
         this.renderHotbarItems(renderSystem, style, poseStack, graphics, local7_2, local9_4, local10_5);
         this.renderHealthBars(renderSystem, style, poseStack, local7_2, local9_4);
         ScaleUtils.rescaleMC();
      }
   }

   private void renderOffhandItem(
      RenderSystem renderSystem, MenuStyle style, class_4587 poseStack, class_332 graphics, float x, float y, float height, float itemSize
   ) {
      class_1799 local9_1 = mc.field_1724.method_6079();
      float local10_2 = height / 2.0F;
      float local11_3 = itemSize / 2.0F;
      if (local9_1.method_7947() > 0) {
         renderSystem.addRect(x - height - 2.0F, y, height, height, local10_2, style.background(), poseStack);
         renderSystem.addRect(x - height, y + 2.5F, itemSize, itemSize, local11_3, style.background().darker(), poseStack);
      }

      poseStack.method_22903();
      poseStack.method_46416(x, y, 0.0F);
      poseStack.method_22905(1.0F, 1.0F, 1.0F);
      poseStack.method_46416(-x, -y, 0.0F);
      graphics.method_51427(local9_1, (int)(x - 22.0F), (int)(y + 4.0F));
      graphics.method_51432(mc.field_1772, local9_1, (int)(x - 22.0F), (int)(y + 3.0F), "");
      poseStack.method_22909();
      if (local9_1.method_7947() > 1) {
         boolean local12_0 = GL11.glIsEnabled(2929);
         if (local12_0) {
            com.mojang.blaze3d.systems.RenderSystem.disableDepthTest();
         }

         renderSystem.addText(
            Fonts.INTER_MEDIUM_14,
            String.valueOf(local9_1.method_7947()),
            x - 22.0F + itemSize - Fonts.INTER_MEDIUM_14.getStringWidth(String.valueOf(local9_1.method_7947())) - 4.0F,
            y + 14.5F,
            Color.WHITE,
            poseStack
         );
         if (!local12_0) {
            com.mojang.blaze3d.systems.RenderSystem.enableDepthTest();
         }
      }
   }

   private void renderHotbarItems(RenderSystem renderSystem, MenuStyle style, class_4587 poseStack, class_332 graphics, float x, float y, float itemSize) {
      float local8_4 = x + 2.0F;
      float local9_5 = itemSize / 2.0F;
      float local10_6 = itemSize + 0.6F;
      float local11_7 = local10_6 / 2.0F;

      for (int local12_3 = 0; local12_3 < 9; local12_3++) {
         if (mc.field_1724.method_31548().field_7545 == local12_3) {
            renderSystem.addRect(local8_4 - 0.3F, y + 2.2F, local10_6, local10_6, local11_7, style.ascent(), poseStack);
         }

         renderSystem.addRect(local8_4, y + 2.5F, itemSize, itemSize, local9_5, style.background().darker(), poseStack);
         class_1799 local13_1 = mc.field_1724.method_31548().method_5438(local12_3);
         float local14_2 = local8_4;
         Melonity.INSTANCE
            .getFeatureManager()
            .getFeature(PotionsHighlightFeature.class)
            .ifPresent(highlightFeature -> highlightFeature.potionSlotHighlight(local13_1, local14_2, y + 2.5F, itemSize, local9_5, poseStack));
         graphics.method_51427(local13_1, (int)(local8_4 + 1.5F), (int)(y + 4.0F));
         graphics.method_51432(mc.field_1772, local13_1, (int)(local8_4 + 1.5F), (int)y + 4, "");
         if (local13_1.method_7947() > 1) {
            boolean local15_0 = GL11.glIsEnabled(2929);
            if (local15_0) {
               com.mojang.blaze3d.systems.RenderSystem.disableDepthTest();
            }

            renderSystem.addText(
               Fonts.INTER_MEDIUM_14,
               String.valueOf(local13_1.method_7947()),
               local8_4 + itemSize - Fonts.INTER_MEDIUM_14.getStringWidth(String.valueOf(local13_1.method_7947())) - 2.0F,
               y + 14.5F,
               Color.WHITE,
               poseStack
            );
            if (!local15_0) {
               com.mojang.blaze3d.systems.RenderSystem.enableDepthTest();
            }
         }

         local8_4 += itemSize + 1.0F;
      }
   }

   private void renderHealthBars(RenderSystem renderSystem, MenuStyle style, class_4587 poseStack, float x, float y) {
      if (mc.field_1724.method_6059(class_1294.field_5920)) {
         this.healthView.setPrimaryColor(Color.decode("#6F3F4D"));
         this.healthView.setIcon(class_2960.method_60656("melonity/images/ui/hotbar/wither_hp.png"));
      } else if (mc.field_1724.method_6059(class_1294.field_5899)) {
         this.healthView.setPrimaryColor(Color.decode("#947818"));
         this.healthView.setIcon(class_2960.method_60656("melonity/images/ui/hotbar/poisoned_hp.png"));
      } else {
         this.healthView.setPrimaryColor(Color.decode("#F94263"));
         this.healthView.setIcon(class_2960.method_60656("melonity/images/ui/hotbar/heart_hotbar.png"));
      }

      if (mc.field_1761.method_2920() != class_1934.field_9220) {
         this.healthView.insert(x, y - 13.0F, 0.0, 0.0, renderSystem, poseStack);
         this.feedView.insert(x + this.healthView.getWidth() + 9.0F + 18.0F, y - 13.0F, 0.0, 0.0, renderSystem, poseStack);
         if (mc.field_1724.method_6067() > 0.0F) {
            this.goldenView.insert(x, y - 26.0F, 0.0, 0.0, renderSystem, poseStack);
         }
      }
   }

   private void renderPotions(RenderSystem renderSystem, MenuStyle style, class_4587 poseStack, class_332 graphics) {
      if (this.modeSetting.getMode().contains("Potions")) {
         ScaleUtils.rescale(2.0);
         this.potionsAnimation.state(mc.field_1724.method_6067() > 0.0F);
         this.potionsAnimation.update(this.potionsFWC.elapsedUnits());
         class_4074 local5_5 = mc.method_18505();
         int local14_6 = mc.field_1724.method_6088().values().size();
         float local15_7 = local14_6 > 0 ? (float)local14_6 * 38.0F - 12.0F : 0.0F;
         float local16_8 = this.potionsDrag.getWidth();
         this.potionsDrag.setWidth(local15_7);
         this.potionsDrag.setHeight(41.0F);
         if (!this.potionsDrag.isDragging()) {
            this.potionsDrag.setX(this.potionsDrag.getX() - (local15_7 - local16_8) / 2.0F);
         }

         float local17_9 = this.potionsDrag.getX();
         float local18_10 = this.potionsDrag.getY();

         for (class_1293 local20_4 : mc.field_1724.method_6088().values()) {
            IMobEffectInstance local21_0 = (IMobEffectInstance)local20_4;
            local21_0.getScale().state((float)local20_4.method_5584() / (float)local21_0.getTimes() > 0.01F);
            local21_0.getScale().update(local21_0.getFWC().elapsedUnits());
            float local22_1 = local21_0.getScale().animation();
            if (!(local22_1 <= 0.0F)) {
               class_6880<class_1291> local23_2 = local20_4.method_5579();
               class_1058 local24_3 = local5_5.method_18663(local23_2);
               poseStack.method_22903();
               poseStack.method_46416(local17_9 + 13.0F, local18_10 + 12.5F, 0.0F);
               poseStack.method_22905(local22_1, local22_1, local22_1);
               poseStack.method_46416(-(local17_9 + 13.0F), -(local18_10 + 12.5F), 0.0F);
               poseStack.method_46416(0.0F, this.potionsAnimation.animation() * -15.0F, 0.0F);
               renderSystem.addRect(local17_9, local18_10 - 0.5F, 27.0F, 27.0F, 25.0F, Color.decode("#222222"), poseStack);
               renderSystem.addRect(local17_9 + 1.5F, local18_10 + 1.5F, 24.5F, 24.5F, 20.0F, Color.decode("#141414"), poseStack);
               renderSystem.addCircle(
                  local17_9 + 13.0F,
                  local18_10 + 13.0F,
                  0.0F,
                  class_3532.method_15363((float)local20_4.method_5584() / (float)local21_0.getTimes() * 360.0F, 0.0F, 360.0F),
                  13.5F,
                  2.0F,
                  false,
                  style.ascent(),
                  poseStack
               );
               graphics.method_25298((int)(local17_9 + 6.0F), (int)(local18_10 + 6.5F), 0, 14, 14, local24_3);
               this.renderPotionDuration(renderSystem, local20_4, local17_9, local18_10, 26.0F, poseStack);
               poseStack.method_22909();
               local17_9 += 38.0F;
            }
         }

         ScaleUtils.rescaleMC();
      }
   }

   private void renderPotionDuration(RenderSystem renderSystem, class_1293 effectInstance, float x, float y, float width, class_4587 poseStack) {
      String local7_0 = this.formatDuration(effectInstance.method_5584());
      float local8_1 = Fonts.INTER_MEDIUM_12.getStringWidth(local7_0);
      renderSystem.addRect(x + (width - local8_1 * 1.8F) / 2.0F - 1.0F, y + 32.0F, local8_1 * 1.8F + 2.0F, 14.0F, 8.0F, Color.decode("#141414"), poseStack);
      renderSystem.addText(Fonts.INTER_MEDIUM_12, local7_0, x + (width - local8_1) / 2.0F, y + 38.0F, Color.WHITE, poseStack);
   }

   private String formatDuration(int ticks) {
      int local2_0 = ticks / 20;
      int local3_1 = local2_0 / 60;
      local2_0 %= 60;
      return local3_1 > 0 ? String.format("%d:%02d", local3_1, local2_0) : String.format("%d с", local2_0);
   }

   @Generated
   public ListSetting getModeSetting() {
      return this.modeSetting;
   }

   @Generated
   public BooleanSetting getBlurInMenu() {
      return this.blurInMenu;
   }

   @Generated
   public BooleanSetting getShadowInMenu() {
      return this.shadowInMenu;
   }

   @Generated
   public WatermarkView getWatermarkView() {
      return this.watermarkView;
   }

   @Generated
   public Drag getWatermarkDrag() {
      return this.watermarkDrag;
   }

   @Generated
   public KeybindView getKeybindView() {
      return this.keybindView;
   }

   @Generated
   public Drag getKeybindDrag() {
      return this.keybindDrag;
   }

   @Generated
   public BarView getHealthView() {
      return this.healthView;
   }

   @Generated
   public BarView getFeedView() {
      return this.feedView;
   }

   @Generated
   public BarView getGoldenView() {
      return this.goldenView;
   }

   @Generated
   public Drag getArmorAlertDrag() {
      return this.armorAlertDrag;
   }

   @Generated
   public Drag getPotionsDrag() {
      return this.potionsDrag;
   }

   @Generated
   public Map<class_1293, Integer> getEffectInstanceMap() {
      return this.effectInstanceMap;
   }

   @Generated
   public FrameWeightCalculator getPotionsFWC() {
      return this.potionsFWC;
   }

   @Generated
   public StateAnimation getPotionsAnimation() {
      return this.potionsAnimation;
   }

   @Generated
   public FrameWeightCalculator getFrameWeightCalculator() {
      return this.frameWeightCalculator;
   }

   @Generated
   public StateAnimation getStateAnimation() {
      return this.stateAnimation;
   }

   @Generated
   public ArrayListModule getArrayListModule() {
      return this.arrayListModule;
   }

   @Generated
   public long getTipsManualEnableAt() {
      return this.tipsManualEnableAt;
   }

   @Generated
   public boolean isTipsEnabledLastFrame() {
      return this.tipsEnabledLastFrame;
   }

   @Generated
   public FrameWeightCalculator getTipsFWC() {
      return this.tipsFWC;
   }

   @Generated
   public StateAnimation getTipsAnimation() {
      return this.tipsAnimation;
   }

   @Generated
   public EventListener<RenderOverlayEvent> getOnRenderOverlay() {
      return this.onRenderOverlay;
   }

   @Generated
   public EventListener<UpdateEvent> getOnUpdate() {
      return this.onUpdate;
   }

   @Generated
   public EventListener<BossbarRenderEvent> getOnBossbarRender() {
      return this.onBossbarRender;
   }

   @Environment(EnvType.CLIENT)
   private static class TipRenderConstants {
      final float BASE_X = 10.0F;
      final float RECT_HEIGHT = 18.0F;
      final float RECT_SPACING = 5.0F;
      final float MARGIN_LEFT = 3.0F;
      final float MARGIN_RIGHT = 4.0F;
      final float INNER_MARGIN = 2.0F;
      final float INNER_HEIGHT = 14.0F;
      final float INNER_Y_OFFSET = 2.0F;
      final float TEXT_Y_OFFSET = 7.5F;
      final float TEXT_SPACING = 5.0F;
      final float KEY_PADDING = 10.0F;
      final int CORNER_RADIUS = 6;
      final int INNER_CORNER_RADIUS = 4;
   }
}
