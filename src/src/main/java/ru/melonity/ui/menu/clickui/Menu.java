package ru.melonity.ui.menu.clickui;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_3675;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import org.joml.Vector2i;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.feature.features.baritone.menu.MenuBlocksSelector;
import ru.melonity.feature.features.modules.render.HudFeature;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.ui.components.view.impl.ButtonView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.MenuColor;
import ru.melonity.ui.menu.clickui.view.MenuConfigs;
import ru.melonity.ui.menu.clickui.view.MenuGira;
import ru.melonity.ui.menu.clickui.view.MenuNavbar;
import ru.melonity.ui.menu.clickui.view.MenuTab;
import ru.melonity.ui.menu.clickui.view.MenuUser;
import ru.melonity.ui.menu.clickui.view.MenuWheel;
import ru.melonity.ui.menu.clickui.view.NavbarButton;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.list.MenuList;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.ui.menu.clickui.view.macro.MenuMacroses;
import ru.melonity.util.animation.Easings;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;
import ru.melonity.util.render.ScissorUtil;

@Environment(EnvType.CLIENT)
public class Menu extends class_437 {
   private final class_310 mc = class_310.method_1551();
   private final class_310 minecraft = class_310.method_1551();
   private float x = -1.0F;
   private float y = -1.0F;
   private final float width;
   private final float height;
   private final MenuNavbar navbarView;
   private final MenuUser userView;
   private final MenuColor colorView;
   private final MenuGira giraView;
   private boolean binds = false;
   private final List<MenuTab> tabs;
   private final List<MenuList> lists;
   private MenuCategory lastTabCat;
   private final MelonityTextField textField = new MelonityTextField("Search", true, 178.0F, 16.5F);
   private float listOffset = 0.0F;
   private float scrollCurrent = 0.0F;
   private boolean scrollInit = false;
   private final FrameWeightCalculator scrollFwc = FrameWeightCalculator.milliseconds(700L);
   private float alphaCurrent;
   private boolean alphaInit;
   private final FrameWeightCalculator alphaFwc = FrameWeightCalculator.milliseconds(500L);
   private float searchCurrent = 0.0F;
   private boolean searchInit = false;
   private final FrameWeightCalculator searchFwc = FrameWeightCalculator.milliseconds(540L);
   private static final float TAB_GAP = 4.0F;
   private static final float LIST_GAP = 10.0F;
   public boolean closing = false;
   public long delay;
   private final MenuBlocksSelector blocksSelector;
   private final FrameWeightCalculator switchFwc = FrameWeightCalculator.milliseconds(450L);
   private final StateAnimation switchAnimation = new StateAnimation();
   private final MenuMacroses macroses;
   private MenuConfigs configs;
   private boolean scrollHold = false;
   private float scrollHoldOffset = 0.0F;
   private boolean dragging;
   private float prevX;
   private float prevY;
   private final FrameWeightCalculator frameWeightCalculator = FrameWeightCalculator.milliseconds(180L);
   private final StateAnimation scaleAnimation = new StateAnimation();
   private final ButtonView myMacrosBtn = new ButtonView("My macros", 179.0F, 16.0F);
   private final ButtonView wheelBtn = new ButtonView("Wheel", 174.0F, 16.0F);
   private boolean myMacrosesSelected = false;
   private final MenuWheel wheel;

   public Menu() {
      super(class_2561.method_43470("Melonity"));
      this.width = 498.0F;
      this.height = 300.0F;
      this.x = this.y = -1.0F;
      this.navbarView = new MenuNavbar(this.width);
      this.userView = new MenuUser();
      this.colorView = new MenuColor();
      this.giraView = new MenuGira();
      this.blocksSelector = new MenuBlocksSelector(this.x, this.y);
      this.macroses = new MenuMacroses(this.width, 1.0F);

      try {
         this.configs = new MenuConfigs(this.width, this.height);
      } catch (Throwable var5) {
         var5.printStackTrace();
         System.exit(1);
      }

      this.tabs = Lists.newArrayList();

      for (MenuCategory local4_1 : MenuCategory.values()) {
         this.tabs.add(new MenuTab(local4_1));
      }

      this.tabs.getFirst().setSelected(true);
      this.lists = Lists.newLinkedList();
      this.alphaCurrent = 255.0F;
      this.alphaInit = true;
      this.fillList();
      this.myMacrosBtn.setCenterText(true);
      this.wheelBtn.setCenterText(true);
      this.wheel = new MenuWheel(this.width - this.tabs.getFirst().getWidth() - 45.0F, 150.0F);
      this.scaleAnimation.dropEasing(Easings.APPLE);
      this.scaleAnimation.speed = 1.75F;
   }

   public void method_25419() {
      super.method_25419();
      this.textField.setFocus(false);
      this.colorView.setSetting(null);
      this.colorView.release();
      this.releaseMenuMovement();
   }

   private void fillList() {
      this.lists.clear();
      MenuTab local1_4 = this.getSelectedTab();
      List<Feature> local2_5 = Melonity.INSTANCE.getFeatureManager().getFeaturesInCategory(local1_4.getCategory());
      List<Feature> local3_6 = Lists.newLinkedList();
      List<Feature> local4_7 = Lists.newLinkedList();
      float local5_8 = 0.0F;
      float local6_9 = 0.0F;

      for (Feature local8_1 : local2_5) {
         float local9_0 = local8_1.getMenuObject().getHeight();
         if (local5_8 <= local6_9) {
            local3_6.add(local8_1);
            local5_8 += local9_0;
         } else {
            local4_7.add(local8_1);
            local6_9 += local9_0;
         }
      }

      this.lists.add(new MenuList(this.width / 2.0F - 89.5F + 16.0F, this.height, local3_6));
      this.lists.add(new MenuList(this.width / 2.0F, this.height, local4_7));

      for (MenuList local8_3 : this.lists) {
         for (Feature local10_2 : local8_3.getFeatures()) {
            local10_2.getMenuObject().setOnlyBinds(false);
         }
      }
   }

   private MenuTab getSelectedTab() {
      return this.tabs.stream().filter(MenuTab::isSelected).findFirst().orElseThrow();
   }

   public void tick2() {
      this.scaleAnimation.update(this.frameWeightCalculator.elapsedUnits());
      this.scaleAnimation.state(!this.closing);
      this.applyMenuMovement();
   }

   public void method_25394(class_332 graphics, int mouseX, int mouseY, float partialTicks) {
      super.method_25394(graphics, mouseX, mouseY, partialTicks);
      RenderSystem local5_28 = Melonity.INSTANCE.getRenderSystem();
      HudFeature local6_29 = Melonity.INSTANCE.getFeatureManager().hudFeature;
      if (this.x == -1.0F && this.y == -1.0F) {
         float local7_0 = (float)this.mc.method_22683().method_4489() / 2.0F;
         float local8_1 = (float)this.mc.method_22683().method_4506() / 2.0F;
         this.x = (local7_0 - this.width) / 2.0F;
         this.y = (local8_1 - this.height) / 2.0F;
      }

      float local7_30 = this.scaleAnimation.dropAnimation();
      if (this.closing) {
         this.giraView.setOpened(false);
      }

      if (this.closing && local7_30 <= 0.3F && this.delay != 0L && this.giraView.getAnimation().animation() <= 0.0F) {
         this.method_25419();
      } else {
         class_4587 local8_31 = graphics.method_51448();
         MenuStyle local9_32 = Melonity.INSTANCE.styleManager.getStyle();
         ScissorUtil.clear();
         if (local6_29.getShadowInMenu().get()) {
            Color local10_2 = new Color(0, 0, 0, class_3532.method_15340((int)(local7_30 * 130.0F), 0, 255));
            int local11_3 = class_3532.method_15386((float)Math.max(graphics.method_51421(), this.mc.method_22683().method_4486()));
            int local12_4 = class_3532.method_15386((float)Math.max(graphics.method_51443(), this.mc.method_22683().method_4502()));
            int local13_5 = -local11_3;
            int local14_6 = -local12_4;
            int local15_7 = local11_3 * 2;
            int local16_8 = local12_4 * 2;
            graphics.method_25294(local13_5, local14_6, local15_7, local16_8, local10_2.getRGB());
         }

         ScaleUtils.rescale(2.0);
         Vector2i local10_33 = ScaleUtils.getMouseDouble((double)mouseX, (double)mouseY);
         mouseX = local10_33.x();
         mouseY = local10_33.y();
         if (this.dragging) {
            float local11_9 = (float)mouseX - this.prevX;
            float local12_10 = (float)mouseY - this.prevY;
            float local13_11 = Math.max(0.0F, (float)this.mc.method_22683().method_4489() / 2.0F - this.width);
            float local14_12 = Math.max(0.0F, (float)this.mc.method_22683().method_4506() / 2.0F - this.height);
            this.x = class_3532.method_15363(local11_9, 0.0F, local13_11);
            this.y = class_3532.method_15363(local12_10, 0.0F, local14_12);
         }

         local8_31.method_22903();
         local8_31.method_46416(this.x + this.width / 2.0F, this.y + this.height / 2.0F, 0.0F);
         local8_31.method_22905(local7_30, local7_30, local7_30);
         local8_31.method_46416(-(this.x + this.width / 2.0F), -(this.y + this.height / 2.0F), 0.0F);
         float local11_34 = this.x + this.width / 2.0F;
         float local12_35 = this.y + this.height / 2.0F;
         float local13_36 = this.x;
         float local14_37 = this.y + (float)(this.lists.getFirst().isSearch() ? 60 : 50);
         float local15_38 = this.width - 12.0F;
         float local16_39 = this.height - (float)(this.lists.getFirst().isSearch() ? 70 : 60);
         float local17_40 = local13_36 - local11_34;
         float local18_41 = local14_37 - local12_35;
         float local19_42 = local17_40 * local7_30;
         float local20_43 = local18_41 * local7_30;
         float local21_44 = local11_34 + local19_42;
         float local22_45 = local12_35 + local20_43;
         float local23_46 = local15_38 * local7_30;
         float local24_47 = local16_39 * local7_30;
         if (local7_30 < 1.0F) {
            local21_44 = Math.max(this.x, local21_44);
            local22_45 = Math.max(this.y, local22_45);
            local23_46 = Math.min(this.width, local23_46);
            local24_47 = Math.min(this.height, local24_47);
         }

         local5_28.addRect(this.x, this.y, this.width, this.height, 10.0F, local9_32.background(), local8_31);
         this.navbarView.insert(local9_32, this.x, this.y, (double)mouseX, (double)mouseY, local5_28, local8_31);
         local5_28.addRect(this.x + 117.5F, this.y + 8.5F, 179.0F, 18.5F, 8.0F, local9_32.background().brighter(), local8_31);
         this.textField.insert(local9_32, this.x + 118.0F, this.y + 9.0F, (double)mouseX, (double)mouseY, local5_28, local8_31);
         if (this.textField.getText().isBlank()) {
            this.textField.setText("");
         }

         for (int local25_14 = 0; local25_14 < this.tabs.size(); local25_14++) {
            MenuTab local26_13 = this.tabs.get(local25_14);
            local26_13.insert(
               local9_32,
               this.x + 8.0F,
               this.y + this.navbarView.getHeight() + 9.0F + (float)local25_14 * (local26_13.getHeight() + 4.0F),
               (double)mouseX,
               (double)mouseY,
               local5_28,
               local8_31
            );
         }

         local5_28.addRect(
            this.x + 110.0F, this.y + this.navbarView.getHeight(), 0.5F, this.height - this.navbarView.getHeight(), Color.decode("#222222"), local8_31
         );
         ScissorUtil.beginScissor((double)local21_44, (double)local22_45, (double)local23_46, (double)local24_47);
         if (!this.scrollInit) {
            this.scrollCurrent = this.listOffset;
            this.scrollInit = true;
         }

         this.scrollCurrent = this.scrollCurrent + (this.listOffset - this.scrollCurrent) * 12.0F * this.scrollFwc.elapsedUnits();
         if (!this.textField.getText().isBlank()) {
            for (int local25_16 = 0; local25_16 < this.tabs.size(); local25_16++) {
               MenuTab local26_15 = this.tabs.get(local25_16);
               local26_15.setSelected(local25_16 == 0);
            }
         }

         this.lists.forEach(list -> list.setSearch(!this.textField.getText().isBlank()));
         boolean local25_48 = !this.textField.getText().isBlank();
         float local26_49 = local25_48 ? 25.0F : 0.0F;
         if (!this.searchInit) {
            this.searchCurrent = local26_49;
            this.searchInit = true;
         }

         this.searchCurrent = this.searchCurrent + (local26_49 - this.searchCurrent) * 12.0F * this.searchFwc.elapsedUnits();
         float local27_50 = 0.0F;
         this.switchAnimation.update(this.switchFwc.elapsedUnits());
         float local28_51 = this.x - 250.0F;
         float local29_52 = local28_51 + this.switchAnimation.animation() * 368.0F;
         ScissorUtil.endScissor();
         ScissorUtil.beginScissor(
            (double)((int)(this.x + this.width / 2.0F + (this.x + 108.0F - (this.x + this.width / 2.0F)) * local7_30)),
            (double)((int)(this.y + this.height / 2.0F + (this.y + 50.0F - (this.y + this.height / 2.0F)) * local7_30)),
            (double)((int)((this.width - 110.0F) * local7_30)),
            (double)((int)((this.height - 60.0F) * local7_30))
         );
         boolean local30_53 = this.getSelectedTab().getCategory() == MenuCategory.MACROS;
         boolean local31_54 = this.getSelectedTab().getCategory() == MenuCategory.CONFIGS;
         if (!local30_53 && !local31_54) {
            if (!this.lists.isEmpty()) {
               MenuList local32_24 = this.lists.getFirst();
               float local33_25 = local32_24.getHeight();
               float local34_26 = this.height - 60.0F;
               if (local33_25 > local34_26) {
                  float local35_20 = Math.max(local34_26 / local33_25 * local34_26, 20.0F);
                  float local36_21 = class_3532.method_15363(-this.scrollCurrent / (local33_25 - local34_26), 0.0F, 1.0F);
                  float local37_22 = this.y + 50.0F + local36_21 * (local34_26 - local35_20);
                  boolean local38_23 = MathUtil.isMouseHoveringRect(
                     (double)(this.x + this.width - 5.0F), (double)local37_22, 3.0, (double)local35_20, (double)mouseX, (double)mouseY
                  );
                  local5_28.addRect(
                     this.x + this.width - 5.0F,
                     local37_22,
                     3.0F,
                     local35_20,
                     3.0F,
                     !local38_23 && !this.scrollHold ? Color.decode("#222222") : Color.decode("#222222").brighter(),
                     local8_31
                  );
                  if (this.scrollHold) {
                     float local39_17 = (float)mouseY;
                     float local40_18 = local39_17 - this.scrollHoldOffset;
                     float local41_19 = (local40_18 - (this.y + 50.0F)) / (local34_26 - local35_20);
                     local41_19 = class_3532.method_15363(local41_19, 0.0F, 1.0F);
                     this.listOffset = -local41_19 * (local33_25 - local34_26);
                  }
               }
            }
         } else {
            if (local30_53) {
               this.myMacrosBtn.setText("My macros");
               this.wheelBtn.setText("Wheel");
            } else {
               this.myMacrosBtn.setText("Favourites");
               this.wheelBtn.setText("All");
            }

            this.myMacrosBtn.insert(this.x + 118.0F, this.y + 52.0F, (double)mouseX, (double)mouseY, local5_28, local8_31);
            this.wheelBtn.insert(this.x + 118.0F + 179.0F + 6.0F, this.y + 52.0F, (double)mouseX, (double)mouseY, local5_28, local8_31);
            this.myMacrosBtn.setActive(this.myMacrosesSelected);
            this.wheelBtn.setActive(!this.myMacrosesSelected);
            this.switchAnimation.state(this.myMacrosesSelected);
            if (local30_53) {
               this.macroses.insert(local9_32, local29_52, this.y + 74.0F, (double)mouseX, (double)mouseY, local5_28, local8_31);
               this.wheel.insert(local9_32, local29_52 + 250.0F + 134.0F, this.y + 175.0F, (double)mouseX, (double)mouseY, local5_28, local8_31);
            }
         }

         ScissorUtil.endScissor();
         ScissorUtil.beginScissor((double)local21_44, (double)local22_45, (double)local23_46, (double)local24_47);
         if (!this.alphaInit) {
            this.alphaCurrent = 0.0F;
            this.alphaInit = true;
         }

         this.alphaCurrent = this.alphaCurrent + (255.0F - this.alphaCurrent) * 12.0F * this.alphaFwc.elapsedUnits();
         local5_28.setAlphaModifier(this.alphaCurrent / 255.0F);
         float local32_55 = this.x + 118.0F;
         if (local30_53) {
            local32_55 += local29_52 - local28_51;
         }

         float local33_56 = this.y + 52.0F + this.scrollCurrent + this.searchCurrent + (float)(local30_53 ? 20 : 0);

         for (MenuList local35_27 : this.lists) {
            local35_27.insert(local9_32, local32_55 + local27_50, local33_56, (double)mouseX, (double)mouseY, local5_28, local8_31, graphics);
            local27_50 += local35_27.getWidth() + 10.0F;
         }

         this.post(local5_28, local9_32, local31_54, (float)mouseX, (float)mouseY, local8_31);
         local8_31.method_22909();
         ScaleUtils.rescaleMC();
      }
   }

   private void post(RenderSystem renderSystem, MenuStyle menuStyle, boolean isConfigsTab, float mouseX, float mouseY, class_4587 poseStack) {
      ScissorUtil.endScissor();
      renderSystem.setAlphaModifier(1.0F);
      if (isConfigsTab) {
         this.configs.insert(menuStyle, this.x, this.y + 74.0F, (double)mouseX, (double)mouseY, renderSystem, poseStack);
      }

      this.userView
         .insert(
            this.x + 8.0F, this.y + this.height - this.userView.getHeight() - 8.0F, (double)mouseX, (double)mouseY, renderSystem, poseStack
         );
      this.colorView
         .insert(
            menuStyle,
            this.x + this.width + 2.0F,
            this.y + this.height - this.colorView.getHeight(),
            (double)mouseX,
            (double)mouseY,
            renderSystem,
            poseStack
         );
      this.giraView.insert(menuStyle, this.x + this.width + 20.0F, this.y, (double)mouseX, (double)mouseY, renderSystem, poseStack);
      this.blocksSelector.insert(menuStyle, 0.0F, 0.0F, (double)mouseX, (double)mouseY, renderSystem, poseStack);
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      Vector2i local6_34 = ScaleUtils.getMouseDouble(mouseX, mouseY);
      mouseX = (double)local6_34.x();
      mouseY = (double)local6_34.y();
      if (this.blocksSelector.isOpened() && this.blocksSelector.isHovered(mouseX, mouseY)) {
         this.blocksSelector.click(0.0F, 0.0F, mouseX, mouseY, button);
         return true;
      } else {
         if (MathUtil.isMouseHoveringRect(
            (double)this.x, (double)this.y, (double)this.navbarView.getWidth(), (double)this.navbarView.getHeight(), mouseX, mouseY
         )) {
            this.dragging = true;
            this.prevX = (float)mouseX - this.x;
            this.prevY = (float)mouseY - this.y;
         }

         MenuTab local7_35 = null;

         for (int local8_5 = 0; local8_5 < this.tabs.size(); local8_5++) {
            MenuTab local9_3 = this.tabs.get(local8_5);
            boolean local10_4 = local9_3 != this.getSelectedTab();
            if (MathUtil.isMouseHoveringRect(
               (double)(this.x + 8.0F),
               (double)(this.y + this.navbarView.getHeight() + 9.0F + (float)local8_5 * (local9_3.getHeight() + 4.0F)),
               (double)local9_3.getWidth(),
               (double)local9_3.getHeight(),
               mouseX,
               mouseY
            )) {
               local9_3.setSelected(true);
               if (local10_4) {
                  this.binds = false;
                  this.scrollInit = true;
                  this.scrollCurrent = -15.0F;
                  this.alphaInit = false;
                  this.searchInit = false;
                  this.listOffset = 0.0F;

                  for (MenuList local12_2 : this.lists) {
                     for (Feature local14_1 : local12_2.getFeatures()) {
                        for (MenuFeatureView local16_0 : local14_1.getMenuObject().getElementViews()) {
                           local16_0.resetAnimations();
                        }
                     }
                  }
               }

               local7_35 = local9_3;
            }
         }

         float local8_19 = this.navbarView.getWidth() - 8.0F;

         for (NavbarButton local10_6 : this.navbarView.getButtons()) {
            local8_19 -= local10_6.getWidth() + 4.0F;
         }

         if (MathUtil.isMouseHoveringRect((double)(this.x + local8_19), (double)(this.y + 9.0F), 16.0, 16.0, mouseX, mouseY)) {
            Melonity.INSTANCE.safe = !Melonity.INSTANCE.isSafe();

            for (Feature local10_7 : Melonity.INSTANCE.getFeatureManager().getFeatures()) {
               if (Melonity.INSTANCE.isSafe() && local10_7.isSafe() && local10_7.isEnabled()) {
                  local10_7.setEnabled(false, false);
               }
            }
         }

         local8_19 += 20.0F;
         NavbarButton local9_20 = this.navbarView.getButtons().get(1);
         if (MathUtil.isMouseHoveringRect(
               (double)(this.x + local8_19), (double)(this.y + 9.0F), (double)local9_20.getWidth(), (double)local9_20.getHeight(), mouseX, mouseY
            )
            && !this.lists.isEmpty()) {
            if (this.binds) {
               for (MenuTab local11_8 : this.tabs) {
                  local11_8.setSelected(false);
                  if (local11_8.getCategory() == this.lastTabCat) {
                     this.listOffset = 0.0F;
                     local7_35 = local11_8;
                     local11_8.setSelected(true);
                  }
               }

               this.fillList();
               this.binds = false;
            } else {
               List<Feature> local11_15 = Lists.newLinkedList();
               List<Feature> local12_16 = Lists.newLinkedList();
               this.lastTabCat = this.getSelectedTab().getCategory();
               List<Feature> local10_14 = Melonity.INSTANCE
                  .getFeatureManager()
                  .getFeatures()
                  .stream()
                  .filter(
                     feature -> !feature.getMenuObject().getElementViews().isEmpty()
                           && feature.getMenuObject().getElementViews().getFirst() instanceof BindSetting
                  )
                  .filter(feature -> ((BindSetting)feature.getMenuObject().getElementViews().getFirst()).bind != -1)
                  .toList();
               float local13_17 = 0.0F;
               float local14_18 = 0.0F;

               for (Feature local16_10 : local10_14) {
                  float local17_9 = local16_10.getMenuObject().getHeight();
                  if (local13_17 <= local14_18) {
                     local11_15.add(local16_10);
                     local13_17 += local17_9;
                  } else {
                     local12_16.add(local16_10);
                     local14_18 += local17_9;
                  }
               }

               this.lists.get(0).setFeatures(local11_15);
               this.lists.get(1).setFeatures(local12_16);

               for (MenuList local16_12 : this.lists) {
                  for (Feature local18_11 : local16_12.getFeatures()) {
                     local18_11.getMenuObject().setOnlyBinds(true);
                  }
               }

               for (int local15_13 = 0; local15_13 < this.tabs.size(); local15_13++) {
                  this.tabs.get(local15_13).setSelected(local15_13 == 0);
               }

               this.binds = true;
               this.listOffset = 0.0F;
            }
         }

         if (local7_35 != null) {
            // local7_35 is reassigned above, so the lambda needs an effectively-final copy.
            MenuTab selectedTab = local7_35;
            this.tabs.stream().filter(tab -> tab != selectedTab).forEach(tab -> tab.setSelected(false));
            this.fillList();
            this.textField.setText("");
            this.textField.setFocus(false);
         }

         local8_19 = this.navbarView.getWidth() - 8.0F;

         for (NavbarButton local10_22 : this.navbarView.getButtons()) {
            local8_19 -= local10_22.getWidth() + 4.0F;
         }

         for (NavbarButton local10_23 : this.navbarView.getButtons()) {
            if (MathUtil.isMouseHoveringRect(
                  (double)(this.x + local8_19), (double)(this.y + 9.0F), (double)local10_23.getWidth(), (double)local10_23.getHeight(), mouseX, mouseY
               )
               && this.navbarView.getButtons().indexOf(local10_23) == 2) {
               this.giraView.setOpened(!this.giraView.isOpened());
            }

            local8_19 += local10_23.getWidth() + 4.0F;
         }

         MenuList local8_28 = this.lists.getFirst();
         float local9_29 = local8_28.getHeight();
         float local10_30 = this.height - 60.0F;
         if (local9_29 > local10_30) {
            float local11_25 = Math.max(local10_30 / local9_29 * local10_30, 20.0F);
            float local12_26 = class_3532.method_15363(-this.scrollCurrent / (local9_29 - local10_30), 0.0F, 1.0F);
            float local13_27 = this.y + 50.0F + local12_26 * (local10_30 - local11_25);
            if (MathUtil.isMouseHoveringRect((double)(this.x + this.width - 5.0F), (double)local13_27, 3.0, (double)local11_25, mouseX, mouseY)) {
               this.scrollHold = true;
               this.scrollHoldOffset = (float)mouseY - local13_27;
            }
         }

         this.userView.click(this.x + 8.0F, this.y + this.height - this.userView.getHeight() - 8.0F, mouseX, mouseY, button);
         this.colorView.click(this.x + this.width + 2.0F, this.y + this.height - this.colorView.getHeight(), mouseX, mouseY, button);
         if (this.giraView.isOpened()) {
            this.giraView.click(this.x + this.width + 20.0F, this.y, mouseX, mouseY, button);
         }

         boolean local8_36 = this.getSelectedTab().getCategory() == MenuCategory.MACROS;
         boolean local9_37 = this.getSelectedTab().getCategory() == MenuCategory.CONFIGS;
         if (local8_36 || local9_37) {
            if (MathUtil.isMouseHoveringRect(
               (double)(this.x + 118.0F), (double)(this.y + 52.0F), (double)this.myMacrosBtn.getWidth(), (double)this.myMacrosBtn.getHeight(), mouseX, mouseY
            )) {
               this.myMacrosesSelected = true;
               this.configs.updateConfigs(this.myMacrosesSelected);
            } else if (MathUtil.isMouseHoveringRect(
               (double)(this.x + 118.0F + 179.0F + 6.0F),
               (double)(this.y + 52.0F),
               (double)this.wheelBtn.getWidth(),
               (double)this.wheelBtn.getHeight(),
               mouseX,
               mouseY
            )) {
               this.myMacrosesSelected = false;
               this.configs.updateConfigs(this.myMacrosesSelected);
            }

            if (local8_36) {
               local10_30 = this.x - 250.0F + this.switchAnimation.animation() * 368.0F;
               if (this.myMacrosBtn.isActive()) {
                  this.macroses.click(this.x + 118.0F, this.y + 74.0F, mouseX, mouseY, button);
               } else {
                  this.wheel.click(local10_30 + 250.0F + 134.0F, this.y + 175.0F, mouseX, mouseY, button);
               }
            }
         }

         if (!local8_36 || !this.myMacrosBtn.isActive()) {
            local10_30 = 0.0F;

            for (MenuList local12_32 : this.lists) {
               local12_32.click(
                  this.x + 118.0F + local10_30, this.y + 52.0F + this.listOffset + this.searchCurrent + (float)(local8_36 ? 20 : 0), mouseX, mouseY, button
               );
               local10_30 += local12_32.getWidth() + 10.0F;
            }
         }

         if (local9_37) {
            this.configs.click(this.x, this.y + 74.0F, mouseX, mouseY, button);
         }

         this.textField.click(this.x + 118.0F, this.y + 9.0F, mouseX, mouseY, button);
         this.textField
            .setFocus(
               MathUtil.isMouseHoveringRect(
                  (double)(this.x + 118.0F), (double)(this.y + 9.0F), (double)this.textField.getWidth(), (double)this.textField.getHeight(), mouseX, mouseY
               )
            );
         return super.method_25402(mouseX, mouseY, button);
      }
   }

   public void unfocusNumberSettings() {
      for (MenuList local2_3 : this.lists) {
         for (Feature local4_2 : local2_3.getFeatures()) {
            for (MenuFeatureView local6_1 : local4_2.getMenuObject().getElementViews()) {
               if (local6_1 instanceof NumberSetting local7_0) {
                  local7_0.getEditField().setFocus(false);
               }
            }
         }
      }
   }

   public boolean method_25406(double mouseX, double mouseY, int button) {
      this.dragging = false;
      this.lists.forEach(MenuList::release);
      this.navbarView.release();
      this.userView.release();
      this.colorView.release();
      this.giraView.release();
      this.blocksSelector.release();
      this.scrollHold = false;
      return super.method_25406(mouseX, mouseY, button);
   }

   private float getMaxListHeight() {
      if (this.lists.isEmpty()) {
         return 0.0F;
      } else {
         float local1_1 = 0.0F;

         for (MenuList local3_0 : this.lists) {
            local1_1 = Math.max(local1_1, local3_0.getHeight());
         }

         return local1_1;
      }
   }

   public boolean method_25401(double mouseX, double mouseY, double deltaY, double delta) {
      Vector2i scaledMouse = ScaleUtils.getMouseDouble(mouseX, mouseY);
      double scaledMouseX = (double)scaledMouse.x();
      double scaledMouseY = (double)scaledMouse.y();
      if (this.blocksSelector.isOpened() && this.blocksSelector.isHovered(scaledMouseX, scaledMouseY)) {
         this.blocksSelector.scroll(delta, scaledMouseX, scaledMouseY);
         return true;
      } else if (this.giraView.isOpened()
         && MathUtil.isMouseHoveringRect(
            (double)(this.x + this.width + 20.0F),
            (double)this.y,
            (double)this.giraView.getWidth(),
            (double)this.giraView.getHeight(),
            scaledMouseX,
            scaledMouseY
         )) {
         this.giraView.scroll(delta);
         return true;
      } else {
         if (MathUtil.isMouseHoveringRect(
            (double)(this.x + 110.0F),
            (double)(this.y + this.navbarView.getHeight()),
            (double)(this.width - 110.0F),
            (double)(this.height - this.navbarView.getHeight()),
            scaledMouseX,
            scaledMouseY
         )) {
            float visibleHeight = this.height - 60.0F;
            float maxOffset = Math.max(0.0F, this.getMaxListHeight() - visibleHeight);
            this.listOffset = class_3532.method_15363(this.listOffset + (float)delta * 20.0F, -maxOffset, 0.0F);
         }

         return super.method_25401(mouseX, mouseY, deltaY, delta);
      }
   }

   public boolean method_25404(int keyCode, int scanCode, int modifiers) {
      if (this.textField.isFocus() && this.textField.isWritable()) {
         if (keyCode == 256) {
            this.textField.setFocus(false);
            this.textField.setText("");
         } else {
            this.textField.key(keyCode, scanCode, modifiers);
         }

         this.rebuildLists();
         return true;
      } else {
         boolean handled = false;

         for (MenuList list : this.lists) {
            for (Feature feature : list.getFeatures()) {
               for (MenuFeatureView view : feature.getMenuObject().getElementViews()) {
                  if (view.key(keyCode, scanCode, modifiers)) {
                     handled = true;
                  }
               }
            }
         }

         MenuTab selectedTab = this.tabs.stream().filter(MenuTab::isSelected).findFirst().orElse(null);
         if (selectedTab != null && selectedTab.getCategory() == MenuCategory.CONFIGS) {
            this.configs.key(keyCode, scanCode, modifiers);
         }

         if (handled) {
            this.rebuildLists();
            return true;
         } else if (keyCode != 256) {
            return super.method_25404(keyCode, scanCode, modifiers);
         } else if (this.giraView.isOpened()) {
            this.giraView.setOpened(false);
            return true;
         } else if (this.blocksSelector.isOpened()) {
            this.blocksSelector.setOpened(false);
            return true;
         } else {
            this.delay = System.currentTimeMillis();
            this.closing = true;
            return true;
         }
      }
   }

   private void rebuildLists() {
      if (this.lists.size() >= 2) {
         List<Feature> source;
         boolean onlyBinds;
         if (!this.textField.getText().isBlank()) {
            source = Melonity.INSTANCE
               .getFeatureManager()
               .getFeatures()
               .stream()
               .filter(feature -> feature.getName().toLowerCase().contains(this.textField.getText().toLowerCase()))
               .toList();
            onlyBinds = false;
         } else {
            if (!this.binds) {
               this.fillList();
               return;
            }

            source = Melonity.INSTANCE
               .getFeatureManager()
               .getFeatures()
               .stream()
               .filter(
                  feature -> !feature.getMenuObject().getElementViews().isEmpty()
                        && feature.getMenuObject().getElementViews().getFirst() instanceof BindSetting
               )
               .filter(feature -> ((BindSetting)feature.getMenuObject().getElementViews().getFirst()).bind != -1)
               .toList();
            onlyBinds = true;
         }

         List<Feature> leftColumn = Lists.newLinkedList();
         List<Feature> rightColumn = Lists.newLinkedList();
         float leftHeight = 0.0F;
         float rightHeight = 0.0F;

         for (Feature feature : source) {
            float featureHeight = feature.getMenuObject().getHeight();
            if (leftHeight <= rightHeight) {
               leftColumn.add(feature);
               leftHeight += featureHeight;
            } else {
               rightColumn.add(feature);
               rightHeight += featureHeight;
            }
         }

         this.lists.get(0).setFeatures(leftColumn);
         this.lists.get(1).setFeatures(rightColumn);

         for (MenuList list : this.lists) {
            list.resetColumns();

            for (Feature feature : list.getFeatures()) {
               feature.getMenuObject().setOnlyBinds(onlyBinds);
            }
         }
      }
   }

   public boolean method_25421() {
      return false;
   }

   private void applyMenuMovement() {
      if (this.mc.field_1724 != null) {
         if (this.mc.field_1755 == this) {
            if (MelonityTextField.selectedField == -1) {
               long local1_3 = this.mc.method_22683().method_4490();

               for (class_304 local6_2 : this.movementKeyBindings()) {
                  int local7_0 = local6_2.method_1429().method_1444();
                  boolean local8_1 = class_3675.method_15987(local1_3, local7_0);
                  local6_2.method_23481(local8_1);
               }
            }
         }
      }
   }

   private void releaseMenuMovement() {
      for (class_304 local4_0 : this.movementKeyBindings()) {
         local4_0.method_23481(false);
      }
   }

   private class_304[] movementKeyBindings() {
      return new class_304[]{
         this.mc.field_1690.field_1894,
         this.mc.field_1690.field_1881,
         this.mc.field_1690.field_1913,
         this.mc.field_1690.field_1849,
         this.mc.field_1690.field_1903,
         this.mc.field_1690.field_1867,
         this.mc.field_1690.field_1832
      };
   }

   public void method_25420(class_332 context, int mouseX, int mouseY, float delta) {
      HudFeature local5_0 = Melonity.INSTANCE.getFeatureManager().hudFeature;
      if (this.field_22787.field_1687 == null) {
         this.method_57728(context, delta);
      }

      if (local5_0.getBlurInMenu().get()) {
         this.method_57734(delta);
      }

      if (local5_0.getShadowInMenu().get()) {
         this.renderGradientOverlay(context);
      }
   }

   private void renderGradientOverlay(class_332 context) {
      int local2_0 = 16777216;
      int local3_1 = -1442840576;
      ScaleUtils.rescale(5.5);
      context.method_25296(0, 0, (int)this.width, (int)this.height, local2_0, local3_1);
      ScaleUtils.rescaleMC();
   }

   @Generated
   public MenuColor getColorView() {
      return this.colorView;
   }

   @Generated
   public MenuGira getGiraView() {
      return this.giraView;
   }

   @Generated
   public boolean isBinds() {
      return this.binds;
   }

   @Generated
   public MelonityTextField getTextField() {
      return this.textField;
   }

   @Generated
   public MenuBlocksSelector getBlocksSelector() {
      return this.blocksSelector;
   }

   @Generated
   public StateAnimation getScaleAnimation() {
      return this.scaleAnimation;
   }

   @Generated
   public void setMyMacrosesSelected(boolean myMacrosesSelected) {
      this.myMacrosesSelected = myMacrosesSelected;
   }

   @Generated
   public MenuWheel getWheel() {
      return this.wheel;
   }
}
