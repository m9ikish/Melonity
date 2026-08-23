package ru.melonity.ui.menu.clickui.view;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.StyledView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.setting.MenuColorSetting;
import ru.melonity.util.animation.Easings;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScissorUtil;

@Environment(EnvType.CLIENT)
public class MenuColor extends StyledView {
   private float pickerX = 0.2F;
   private float pickerY = 0.2F;
   private float hueX = 0.07F;
   private float hue = 0.0F;
   private MenuColorSetting setting = null;
   private Color color = Color.BLUE;
   private boolean draggingPicker = false;
   private boolean draggingHue = false;
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(370L);
   private final StateAnimation animation = new StateAnimation();
   private final List<Color> presets = Collections.synchronizedList(Lists.newLinkedList());
   private final float circleRadius = 6.0F;

   public MenuColor() {
      super(135.0F, 162.0F);
      this.animation.reset();
      this.presets.add(Color.decode("#D3F942"));
   }

   private void updateColor() {
      float local1_0 = this.pickerX;
      float local2_1 = 1.0F - this.pickerY;
      int local3_2 = Color.HSBtoRGB(this.hue, local1_0, local2_1);
      this.color = new Color(local3_2);
      if (this.setting != null) {
         this.setting.setColor(this.color);
      }
   }

   private void setPickerFromMouse(float mx, float my, float ox, float oy) {
      float local5_0 = this.width - 10.0F;
      float local6_1 = 85.0F;
      float local7_2 = 6.0F / local5_0;
      float local8_3 = 6.0F / local6_1;
      this.pickerX = class_3532.method_15363((mx - (ox + 5.0F)) / local5_0, local7_2, 1.0F - local7_2);
      this.pickerY = class_3532.method_15363((my - (oy + 5.0F)) / local6_1, local8_3, 1.0F - local8_3);
      this.updateColor();
   }

   private void setHueFromMouse(float mx, float my, float ox, float oy) {
      float local5_0 = this.width - 10.0F;
      float local6_1 = 6.0F / local5_0;
      this.hueX = class_3532.method_15363((mx - (ox + 5.0F)) / local5_0, local6_1, 1.0F - local6_1);
      this.hue = this.hueX;
      this.updateColor();
   }

   @Override
   public void insert(MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      this.animation.state(this.setting != null);
      this.animation.update(this.fwc.elapsedUnits());
      float local10_1 = (float)Easings.CUBIC_BOTH.ease(this.animation.value);
      if (!(local10_1 <= 0.0F)) {
         if (this.draggingPicker) {
            this.setPickerFromMouse((float)mouseX, (float)mouseY, x, y);
         }

         if (this.draggingHue) {
            this.setHueFromMouse((float)mouseX, (float)mouseY, x, y);
         }

         poseStack.method_22903();
         poseStack.method_46416(x + this.width / 2.0F, y + this.height / 2.0F, 0.0F);
         poseStack.method_22905(local10_1, local10_1, local10_1);
         poseStack.method_46416(-x - this.width / 2.0F, -y - this.height / 2.0F, 0.0F);
         renderSystem.addRect(x, y, this.width, this.height, 8.0F, style.background(), poseStack);
         renderSystem.addColorPickerContainer(x + 5.0F, y + 5.0F, this.width - 10.0F, 85.0F, 6.0F, this.hue, poseStack);
         renderSystem.addHueBar(x + 5.0F, y + 96.0F, this.width - 10.0F, 15.0F, 16.0F, poseStack);
         float local11_2 = this.width - 10.0F;
         float local12_3 = 85.0F;
         float local13_4 = this.width - 10.0F;
         float local14_5 = 7.5F;
         renderSystem.addCircle(
            x + 5.0F + this.pickerX * local11_2, y + 5.0F + this.pickerY * local12_3, 0.0F, 360.0F, 6.0F, 1.0F, false, Color.WHITE, poseStack
         );
         renderSystem.addCircle(x + 5.0F + this.hueX * local13_4, y + 96.0F + local14_5, 0.0F, 360.0F, 6.0F, 1.0F, false, Color.WHITE, poseStack);
         renderSystem.addRect(x + 4.5F, y + 115.5F, 17.0F, 17.0F, 6.0F, Color.decode("#2C2C2C"), poseStack);
         renderSystem.addRect(
            x + 5.0F,
            y + 116.0F,
            16.0F,
            16.0F,
            6.0F,
            Color.decode(MathUtil.isMouseHoveringRect((double)(x + 5.0F), (double)(y + 116.0F), 16.0, 16.0, mouseX, mouseY) ? "#2C2C2C" : "#222222"),
            poseStack
         );
         renderSystem.addTexture(class_2960.method_60656("melonity/images/ui/add.png"), x + 8.0F, y + 119.0F, 10.0F, 10.0F, poseStack);
         renderSystem.addRect(x + 22.5F, y + 115.5F, this.width - 27.0F, 17.0F, 6.0F, Color.decode("#2C2C2C"), poseStack);
         renderSystem.addRect(x + 23.0F, y + 116.0F, this.width - 28.0F, 16.0F, 6.0F, Color.decode("#222222"), poseStack);
         String local15_6 = String.format("%02X%02X%02X", this.color.getRed(), this.color.getGreen(), this.color.getBlue());
         renderSystem.addText(
            Fonts.INTER_REGULAR_14,
            local15_6,
            x + 23.0F + (this.width - 28.0F) / 2.0F - Fonts.INTER_REGULAR_14.getStringWidth(local15_6) / 2.0F,
            y + 122.0F,
            poseStack
         );
         renderSystem.addRect(x, y + 137.0F, this.width, 0.5F, Color.decode("#222222"), poseStack);
         ScissorUtil.beginScissor((double)(x + 5.0F), (double)(y + 139.0F), (double)(this.width - 5.0F), 18.0);
         float local16_7 = x + 5.0F;

         for (Color local18_0 : this.presets) {
            renderSystem.addRect(
               local16_7,
               y + 139.0F,
               16.0F,
               16.0F,
               4.0F,
               MathUtil.isMouseHoveringRect((double)local16_7, (double)(y + 139.0F), 16.0, 16.0, mouseX, mouseY) ? local18_0.darker() : local18_0,
               poseStack
            );
            local16_7 += 18.5F;
         }

         ScissorUtil.endScissor();
         poseStack.method_22909();
      }
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
      float local8_3 = this.width - 10.0F;
      float local9_4 = 85.0F;
      float local10_5 = this.width - 10.0F;
      if (mouseButton == 0) {
         if (MathUtil.isMouseHoveringRect((double)(x + 5.0F), (double)(y + 5.0F), (double)local8_3, (double)local9_4, mouseX, mouseY)) {
            this.draggingPicker = true;
            this.setPickerFromMouse((float)mouseX, (float)mouseY, x, y);
         } else if (MathUtil.isMouseHoveringRect((double)(x + 5.0F), (double)(y + 96.0F), (double)local10_5, 15.0, mouseX, mouseY)) {
            this.draggingHue = true;
            this.setHueFromMouse((float)mouseX, (float)mouseY, x, y);
         } else if (MathUtil.isMouseHoveringRect((double)(x + 5.0F), (double)(y + 116.0F), 16.0, 16.0, mouseX, mouseY) && this.setting != null) {
            this.presets.add(this.color);
         }
      }

      try {
         float local11_2 = x + 5.0F;

         // Right-clicking a preset removes it from this.presets, which is the very
         // list being iterated - that threw ConcurrentModificationException into the
         // silent catch below, so the swatch row stopped drawing for the rest of the
         // frame. Iterate a snapshot instead.
         for (Color local13_1 : new java.util.ArrayList<>(this.presets)) {
            if (MathUtil.isMouseHoveringRect((double)local11_2, (double)(y + 139.0F), 16.0, 16.0, mouseX, mouseY) && this.setting != null) {
               if (mouseButton == 1) {
                  if (this.presets.indexOf(local13_1) != 0) {
                     this.presets.remove(local13_1);
                  }
               } else {
                  this.color = local13_1;
                  float[] local14_0 = Color.RGBtoHSB(local13_1.getRed(), local13_1.getGreen(), local13_1.getBlue(), null);
                  this.hue = local14_0[0];
                  this.hueX = this.hue;
                  this.pickerX = local14_0[1];
                  this.pickerY = 1.0F - local14_0[2];
               }
            }

            local11_2 += 18.5F;
         }
      } catch (Throwable var15) {
      }
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public void release() {
      this.draggingPicker = false;
      this.draggingHue = false;
   }

   @Generated
   public void setPickerX(float pickerX) {
      this.pickerX = pickerX;
   }

   @Generated
   public void setPickerY(float pickerY) {
      this.pickerY = pickerY;
   }

   @Generated
   public void setHueX(float hueX) {
      this.hueX = hueX;
   }

   @Generated
   public void setHue(float hue) {
      this.hue = hue;
   }

   @Generated
   public void setSetting(MenuColorSetting setting) {
      this.setting = setting;
   }

   @Generated
   public void setColor(Color color) {
      this.color = color;
   }

   @Generated
   public void setDraggingPicker(boolean draggingPicker) {
      this.draggingPicker = draggingPicker;
   }

   @Generated
   public void setDraggingHue(boolean draggingHue) {
      this.draggingHue = draggingHue;
   }

   @Generated
   public MenuColorSetting getSetting() {
      return this.setting;
   }

   @Generated
   public Color getColor() {
      return this.color;
   }
}
