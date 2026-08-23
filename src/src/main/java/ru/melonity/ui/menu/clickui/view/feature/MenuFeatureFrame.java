package ru.melonity.ui.menu.clickui.view.feature;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.wheel.WheelFeature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.util.animation.Easings;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScissorUtil;

@Environment(EnvType.CLIENT)
public class MenuFeatureFrame {
   private static final float SETTINGS_START_OFFSET_SINGLE = 40.0F;
   private static final float SETTINGS_START_OFFSET_MULTILINE = 45.0F;
   private static final float SETTINGS_ADDITIONAL_OFFSET = 5.0F;
   private static final float SETTINGS_ITEM_INITIAL_OFFSET = 6.0F;
   protected float width;
   protected float height;
   private final Feature feature;
   public final List<MenuFeatureView> elementViews;
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(350L);
   private final StateAnimation toggleAnimation = new StateAnimation();
   private final StateAnimation expandAnimation = new StateAnimation();
   private boolean onlyBinds;
   private boolean expanded = false;
   private float calculated = -1.0F;
   boolean search = false;

   public MenuFeatureFrame(Feature feature) {
      this.width = feature instanceof WheelFeature ? 358.0F : 179.0F;
      this.height = 36.0F;
      this.feature = feature;
      this.elementViews = Lists.newLinkedList();
      this.onlyBinds = false;
   }

   public void insert(
      MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, boolean search, class_4587 poseStack, class_332 context
   ) {
      if (!this.onlyBinds || !(this.feature instanceof WheelFeature)) {
         this.search = search;
         if (this.feature instanceof WheelFeature) {
            this.calculated = 32.0F;
         } else if (this.calculated <= 20.0F) {
            this.calculated = 30.0F;
         }

         if (search) {
            GlyphData local12_0 = Fonts.INTER_REGULAR_14;
            renderSystem.addText(local12_0, this.feature.getCategory().displayName, x - 1.0F, y - 11.0F, Color.decode("#888888"), poseStack);
            renderSystem.addTexture(
               class_2960.method_60656("melonity/images/ui/clickgui/text_arrow.png"),
               x - 1.0F + local12_0.getStringWidth(this.feature.getCategory().displayName),
               y - 11.5F,
               5.5F,
               4.5F,
               poseStack
            );
            renderSystem.addText(
               local12_0,
               this.feature.getName(),
               x - 1.0F + local12_0.getStringWidth(this.feature.getCategory().displayName) + 7.0F,
               y - 11.0F,
               Color.decode("#888888"),
               poseStack
            );
         }

         float local12_16 = this.getHeight();
         if (search) {
            local12_16 -= 20.0F;
         }

         renderSystem.addRect(x, y, this.width + 1.0F, local12_16 + 5.0F, 8.0F, style.feature().background(), poseStack);
         float local13_17 = 10.0F;
         String local14_18 = Melonity.INSTANCE.languageService.get(this.feature.getName().toLowerCase().concat(".name"));
         String local15_19 = local14_18 != null && !local14_18.equalsIgnoreCase(this.feature.getName().concat(".name")) ? local14_18 : this.feature.getName();
         String local16_20 = "";
         if (this.feature instanceof SimpleFeature local17_1 && local17_1.hasAnyPrefix()) {
            local16_20 = local17_1.getCombinedPrefixes();
         }

         if (this.feature.isEnabled()) {
            renderSystem.addText(Fonts.INTER_SEMIBOLD_16, local15_19, x + 8.0F, y + local13_17, Melonity.INSTANCE.styleManager.style.ascent(), poseStack);
         } else {
            renderSystem.addText(Fonts.INTER_REGULAR_16, local15_19, x + 8.0F, y + local13_17, Color.WHITE.darker().darker(), poseStack);
         }

         if (!local16_20.isEmpty()) {
            float local17_2 = this.feature.isEnabled() ? Fonts.INTER_SEMIBOLD_16.getStringWidth(local15_19) : Fonts.INTER_REGULAR_16.getStringWidth(local15_19);
            renderSystem.addText(
               Fonts.INTER_MEDIUM_12, local16_20, x + 7.5F + local17_2 + 2.0F, y + local13_17 - 1.0F, Melonity.INSTANCE.styleManager.style.ascent(), poseStack
            );
         }

         float local17_21 = x + this.width - 15.0F - 10.0F;
         this.toggleAnimation.state(this.feature.isEnabled());
         this.toggleAnimation.speed = 2.0F;
         this.toggleAnimation.update(this.fwc.elapsedUnits());
         this.expandAnimation.state(this.expanded);
         this.expandAnimation.speed = 0.5F;
         this.expandAnimation.update(this.fwc.elapsedUnits());
         boolean local18_22 = MathUtil.isMouseHoveringRect((double)local17_21, (double)(y + local13_17 - 3.0F), 17.0, 10.0, mouseX, mouseY);
         Color[] local19_23 = Melonity.INSTANCE.styleManager.getStyle().feature().disabledToggler();
         if (this.feature.isEnabled()) {
            local19_23 = style.feature().enabledToggler();
         }

         if (!(this.feature instanceof WheelFeature)) {
            float local20_3 = local17_21 + 2.0F + (float)Easings.CUBIC_BOTH.ease((double)this.toggleAnimation.animation()) * 5.0F;
            float local21_4 = y + local13_17 - 2.0F;
            renderSystem.addRectGradient(
               local17_21, y + local13_17 - 3.0F, 17.0F, 10.0F, 9.5F, local19_23[0], local19_23[1], local19_23[1], local19_23[0], poseStack
            );
            renderSystem.addRect(
               local20_3,
               local21_4,
               8.0F,
               8.0F,
               8.0F,
               this.feature.isEnabled()
                  ? style.feature().enabledCircle()
                  : (local18_22 ? RenderSystem.setAlpha(style.feature().disabledCircle(), 200) : style.feature().disabledCircle()),
               poseStack
            );
         }

         local13_17 += Fonts.INTER_MEDIUM_16.getStringHeight("1234") + 2.0F;
         String local20_24 = Melonity.INSTANCE.languageService.get(this.feature.getName().toLowerCase().concat(".description"));
         if (local20_24 != null) {
            List<String> local21_6 = Arrays.stream(local20_24.split("\n")).toList();

            for (String local23_5 : local21_6) {
               renderSystem.addText(
                  Fonts.INTER_REGULAR_13, local23_5, x + 8.0F, y + local13_17 + (float)(local21_6.indexOf(local23_5) * 7), Color.decode("#888888"), poseStack
               );
            }
         }

         local13_17 = this.height - 5.0F + (float)(local20_24 != null && local20_24.contains("\n") ? 22 : 15);
         if (this.feature instanceof WheelFeature) {
            local13_17 -= 32.0F;
         }

         if (!this.elementViews.isEmpty() && !(this.feature instanceof WheelFeature)) {
            float local21_7 = y + (float)(local20_24.contains("\n") ? 40 : 35) - 1.0F;
            int local22_8 = this.getVisibleSettingsCount();
            String local23_9 = getSettingsText(local22_8);
            float local24_10 = Fonts.INTER_REGULAR_12.getStringWidth(local23_9);
            renderSystem.addText(Fonts.INTER_REGULAR_12, local23_9, x + this.width - 8.0F - local24_10, local21_7 - 1.0F, Color.decode("#888888"), poseStack);
            renderSystem.addRect(x + 8.0F, local21_7, this.width - 16.0F - local24_10 - 4.0F, 1.0F, Color.decode("#222222"), poseStack);
         }

         if (!this.elementViews.isEmpty() && (double)this.expandAnimation.animation() > 0.01) {
            float local21_13 = y + (local20_24 != null && local20_24.contains("\n") ? 45.0F : 40.0F) + 5.0F;
            float local22_14 = this.getSettingsHeight();
            float local23_15 = local22_14 * this.expandAnimation.animation();
            if (local23_15 > 0.0F) {
               ScissorUtil.beginScissor(0.0, (double)local21_13, (double)class_310.method_1551().method_22683().method_4480(), (double)local23_15);
               float local24_12 = 6.0F;

               for (MenuFeatureView local26_11 : this.elementViews) {
                  if (!this.onlyBinds || local26_11 instanceof BindSetting && ((BindSetting)local26_11).bind != -1) {
                     local26_11.insert(style, x + 8.0F, local21_13 + local24_12, this.width, mouseX, mouseY, renderSystem, poseStack, context);
                     local24_12 += local26_11.getHeight();
                  }
               }

               ScissorUtil.endScissor();
            }
         }
      }
   }

   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
      float local8_3 = x + this.width - 15.0F - 10.0F;
      if (!MathUtil.isMouseHoveringRect((double)local8_3, (double)(y + 10.0F - 3.0F), 17.0, 10.0, mouseX, mouseY) || this.feature instanceof WheelFeature) {
         String local9_4 = Melonity.INSTANCE.languageService.get(this.feature.getName().toLowerCase().concat(".description"));
         float local10_5 = y + (float)(local9_4 != null && local9_4.contains("\n") ? 40 : 35);
         if (!this.elementViews.isEmpty()
            && !(this.feature instanceof WheelFeature)
            && MathUtil.isMouseHoveringRect((double)x, (double)(local10_5 - 8.0F), (double)this.width, 16.0, mouseX, mouseY)) {
            this.expanded = !this.expanded;
         } else {
            if (this.expanded && (double)this.expandAnimation.animation() > 0.8) {
               float local11_1 = y + (local9_4 != null && local9_4.contains("\n") ? 45.0F : 40.0F) + 5.0F;
               float local12_2 = 6.0F;

               for (MenuFeatureView local14_0 : this.elementViews) {
                  if (!this.onlyBinds || local14_0 instanceof BindSetting && ((BindSetting)local14_0).bind != -1) {
                     local14_0.click(x + 8.0F, local11_1 + local12_2, this.width, mouseX, mouseY, mouseButton);
                     local12_2 += local14_0.getHeight();
                  }
               }
            }
         }
      } else if (Melonity.INSTANCE.safe && this.feature.isSafe()) {
         class_310.method_1551().field_1705.method_1743().method_1812(class_2561.method_43470("Включен melonity safe, функция помечена как небезопасная"));
      } else {
         this.feature.setEnabled(!this.feature.isEnabled(), false);
      }
   }

   public boolean key(int keyCode, int scanCode, int modifiers) {
      if (!this.expanded) {
         return false;
      } else {
         String local4_1 = Melonity.INSTANCE.languageService.get(this.feature.getName().toLowerCase().concat(".description"));
         float local5_2 = this.height + (float)(local4_1.contains("\n") ? 15 : 6);

         for (MenuFeatureView local7_0 : this.elementViews) {
            if (local7_0 instanceof BindSetting) {
               if (local7_0.key(keyCode, scanCode, modifiers)) {
                  return true;
               }

               local5_2 += local7_0.getHeight();
            }
         }

         return false;
      }
   }

   public void release() {
      this.elementViews.forEach(MenuFeatureView::release);
   }

   public float getHeight() {
      String local1_1 = Melonity.INSTANCE.languageService.get(this.feature.getName().toLowerCase().concat(".description"));
      float local2_2 = this.getAFloat(local1_1) + 14.0F;
      if (local2_2 <= 20.0F) {
         local2_2 = 30.0F;
      }

      this.calculated = local2_2;
      float local3_3 = 0.0F;
      if (!this.elementViews.isEmpty()) {
         float local4_0 = this.expandAnimation.animation();
         local3_3 = this.getSettingsHeight() * Math.max(0.0F, Math.min(1.0F, local4_0)) - 6.0F;
      }

      if (this.search) {
         local2_2 += 20.0F;
      }

      return local2_2 + local3_3;
   }

   private float getSettingsHeight() {
      float local1_1 = 0.0F;

      for (MenuFeatureView local3_0 : this.elementViews) {
         if (!this.onlyBinds || local3_0 instanceof BindSetting && ((BindSetting)local3_0).bind != -1) {
            local1_1 += local3_0.getHeight();
         }
      }

      return local1_1 + 2.0F;
   }

   private float getBaseHeight(String translated) {
      float local2_0 = (float)(translated != null && !translated.contains("\n") ? 6 : 12);
      float local3_1 = this.elementViews.isEmpty() ? 0.0F : 16.0F;
      local3_1 += local2_0;
      return local3_1 + 18.0F;
   }

   private float getAFloat(String translated) {
      return this.getBaseHeight(translated) - 8.0F;
   }

   private int getVisibleSettingsCount() {
      int local1_1 = 0;

      for (MenuFeatureView local3_0 : this.elementViews) {
         if (!this.onlyBinds || local3_0 instanceof BindSetting && ((BindSetting)local3_0).bind != -1) {
            local1_1++;
         }
      }

      return local1_1;
   }

   private static String getSettingsText(int count) {
      String local1_0 = Melonity.INSTANCE.languageService.get("melonity.settings.singular");
      String local2_1 = Melonity.INSTANCE.languageService.get("melonity.settings.plural_few");
      String local3_2 = Melonity.INSTANCE.languageService.get("melonity.settings.plural_many");
      if (local1_0 != null && !local1_0.equals("melonity.settings.singular")) {
         boolean local4_3 = isRussianText(local1_0);
         return local4_3 ? count + " " + getRussianForm(count, local1_0, local2_1, local3_2) : count + " " + (count == 1 ? local1_0 : local3_2);
      } else {
         return count + " settings";
      }
   }

   private static boolean isRussianText(String text) {
      return text != null && text.matches(".*[а-яё].*");
   }

   private static String getRussianForm(int count, String form1, String form2, String form5) {
      count = Math.abs(count) % 100;
      if (count >= 11 && count <= 19) {
         return form5;
      } else {
         int local4_0 = count % 10;
         if (local4_0 == 1) {
            return form1;
         } else {
            return local4_0 >= 2 && local4_0 <= 4 ? form2 : form5;
         }
      }
   }

   @Generated
   public float getWidth() {
      return this.width;
   }

   @Generated
   public Feature getFeature() {
      return this.feature;
   }

   @Generated
   public List<MenuFeatureView> getElementViews() {
      return this.elementViews;
   }

   @Generated
   public FrameWeightCalculator getFwc() {
      return this.fwc;
   }

   @Generated
   public StateAnimation getToggleAnimation() {
      return this.toggleAnimation;
   }

   @Generated
   public StateAnimation getExpandAnimation() {
      return this.expandAnimation;
   }

   @Generated
   public boolean isOnlyBinds() {
      return this.onlyBinds;
   }

   @Generated
   public boolean isExpanded() {
      return this.expanded;
   }

   @Generated
   public float getCalculated() {
      return this.calculated;
   }

   @Generated
   public boolean isSearch() {
      return this.search;
   }

   @Generated
   public void setWidth(float width) {
      this.width = width;
   }

   @Generated
   public void setHeight(float height) {
      this.height = height;
   }

   @Generated
   public void setOnlyBinds(boolean onlyBinds) {
      this.onlyBinds = onlyBinds;
   }

   @Generated
   public void setExpanded(boolean expanded) {
      this.expanded = expanded;
   }

   @Generated
   public void setCalculated(float calculated) {
      this.calculated = calculated;
   }

   @Generated
   public void setSearch(boolean search) {
      this.search = search;
   }
}
