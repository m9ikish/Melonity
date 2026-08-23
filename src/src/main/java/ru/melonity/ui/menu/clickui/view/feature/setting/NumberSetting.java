package ru.melonity.ui.menu.clickui.view.feature.setting;

import java.awt.Color;
import java.util.Arrays;
import java.util.function.BooleanSupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class NumberSetting implements MenuFeatureView {
   private final Number min;
   private final Number max;
   private final String name;
   private final NumberSetting.FormatConsumer format;
   public Number value;
   private final MelonityTextField editField = new MelonityTextField("", false, 100.0F, 11.0F, true);
   private boolean sliding = false;
   private float currentX;
   private boolean initialized;
   private BooleanSupplier visibilityCondition = () -> true;
   private final FrameWeightCalculator fwCalc = FrameWeightCalculator.seconds(1L);

   public MelonityTextField getEditField() {
      return this.editField;
   }

   public boolean isSliding() {
      return this.sliding;
   }

   public float getCurrentX() {
      return this.currentX;
   }

   public boolean isInitialized() {
      return this.initialized;
   }

   public BooleanSupplier getVisibilityCondition() {
      return this.visibilityCondition;
   }

   public NumberSetting setVisible(BooleanSupplier... conditions) {
      this.visibilityCondition = () -> Arrays.stream(conditions).allMatch(BooleanSupplier::getAsBoolean);
      return this;
   }

   public NumberSetting(String name, NumberSetting.FormatConsumer format, Number min, Number max, Number value) {
      this.min = min;
      this.max = max;
      this.name = name;
      this.format = format;
      this.setValue(value);
      this.initialized = false;
      this.editField.setWritable(true);
      this.editField.setUndecorated(true);
   }

   @Override
   public void resetAnimations() {
      this.editField.setFocus(false);
   }

   public Number getMin() {
      return this.min;
   }

   public Number getMax() {
      return this.max;
   }

   public String getName() {
      return this.name;
   }

   public NumberSetting.FormatConsumer getFormat() {
      return this.format;
   }

   public Number getValue() {
      return this.value;
   }

   @Override
   public void insert(
      MenuStyle style, float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack, class_332 context
   ) {
      if (this.visibilityCondition.getAsBoolean()) {
         renderSystem.addText(Fonts.INTER_MEDIUM_14, this.getLangService().get(this.name), x, y, poseStack);
         float local12_1 = this.max.floatValue() - this.min.floatValue();
         float local13_2 = (width - 16.0F) / local12_1;
         float local14_3 = class_3532.method_15363((this.value.floatValue() - this.min.floatValue()) * local13_2, 0.0F, width - 16.0F);
         if (!this.initialized) {
            this.currentX = local14_3;
            this.initialized = true;
         }

         renderSystem.addRect(x, y + 12.0F, width - 16.0F, 4.0F, 3.0F, style.previewValues(), poseStack);
         this.currentX = this.currentX + (local14_3 - this.currentX) * 14.0F * this.fwCalc.elapsedUnits();
         renderSystem.addRectGradient(
            x, y + 12.0F, this.currentX, 4.0F, 3.0F, style.slider()[0], style.slider()[1], style.slider()[1], style.slider()[0], poseStack
         );
         float local15_4 = x + this.currentX - 3.5F;
         renderSystem.addRect(local15_4, y + 10.0F, 7.0F, 8.0F, 1.0F, new Color(136, 136, 136, 34), poseStack);
         renderSystem.addRect(local15_4, y + 10.0F, 7.0F, 7.0F, 1.0F, Color.WHITE, poseStack);

         for (int local16_0 = 1; local16_0 <= 2; local16_0++) {
            renderSystem.addRect(local15_4 + (float)(local16_0 * 2), y + 11.5F, 1.2F, 4.0F, Color.decode("#565656"), poseStack);
         }

         if (this.sliding || MathUtil.isMouseHoveringRect((double)local15_4, (double)(y + 10.0F), 7.0, 7.0, mouseX, mouseY)) {
            renderSystem.addRect(local15_4, y + 10.0F, 7.0F, 7.0F, 1.0F, new Color(10, 10, 10, 50), poseStack);
         }

         GlyphData local16_5 = Fonts.INTER_REGULAR_14;
         String local17_6 = this.format.format(this.value).replace(',', '.');
         if (this.editField.isFocus()) {
            local17_6 = this.editField.getText();
         }

         float local18_7 = local16_5.getStringWidth(local17_6) + 10.0F;
         renderSystem.addRect(x + width - 16.0F - local18_7, y - 5.0F, local18_7, 12.0F, 3.5F, style.previewValues(), poseStack);
         if (MathUtil.isMouseHoveringRect((double)(x + width - 16.0F - local18_7), (double)(y - 5.0F), (double)local18_7, 12.0, mouseX, mouseY)) {
            Melonity.text = true;
         }

         if (this.editField.isFocus()) {
            this.editField
               .insert(
                  style,
                  x + width - 16.0F - local18_7 + local18_7 / 2.0F - local16_5.getStringWidth(local17_6) / 2.0F + 1.0F,
                  y - 3.5F,
                  mouseX,
                  mouseY,
                  renderSystem,
                  poseStack
               );
         } else {
            renderSystem.addText(
               local16_5,
               local17_6,
               x + width - 16.0F - local18_7 + local18_7 / 2.0F - local16_5.getStringWidth(local17_6) / 2.0F,
               y,
               Color.decode("#888888"),
               poseStack
            );
         }

         if (this.sliding) {
            this.setValue(
               (mouseX - (double)(x * renderSystem.getFakeScaling())) * (double)local12_1 / (double)((width - 16.0F) * renderSystem.getFakeScaling())
                  + (double)this.min.floatValue()
            );
            Melonity.hand = true;
         }
      }
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      if (this.visibilityCondition.getAsBoolean()) {
         if (MathUtil.isMouseHoveringRect((double)x, (double)(y + 6.0F), (double)(width - 16.0F), 10.0, mouseX, mouseY) && mouseButton == 0) {
            this.sliding = true;
         }

         GlyphData local9_0 = Fonts.INTER_REGULAR_14;
         String local10_1 = this.format.format(this.value).replace(',', '.');
         float local11_2 = local9_0.getStringWidth(local10_1) + 10.0F;
         if (MathUtil.isMouseHoveringRect((double)(x + width - 16.0F - local11_2), (double)(y - 5.0F), (double)local11_2, 12.0, mouseX, mouseY)
            && !this.editField.isFocus()) {
            Melonity.INSTANCE.getMenu().unfocusNumberSettings();
            this.editField.setText(local10_1);
            this.editField.setFocus(true);
         }

         float local12_3 = x + width - 16.0F - local11_2 + local11_2 / 2.0F - local9_0.getStringWidth(local10_1) / 2.0F + 1.0F;
         if (this.editField.isFocus()
            && this.editField.isWritable()
            && MathUtil.isMouseHoveringRect(
               (double)local12_3, (double)(y - 3.5F), (double)this.editField.getWidth(), (double)this.editField.getHeight(), mouseX, mouseY
            )) {
            this.editField.click(local12_3, y - 3.5F, mouseX, mouseY, mouseButton);
         }
      }
   }

   @Override
   public void release() {
      this.sliding = false;
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      if (this.editField.isWritable() && this.editField.isFocus() && keyCode == 257) {
         this.editField.setFocus(false);

         try {
            this.setValue(Float.parseFloat(this.editField.getText().replace(',', '.')));
         } catch (NumberFormatException var5) {
         }

         return true;
      } else if (this.editField.getText().length() > 10) {
         return false;
      } else {
         this.editField.key(keyCode, scanCode, modifiers);
         return false;
      }
   }

   public void setValue(Number value) {
      this.value = class_3532.method_15363(value.floatValue(), this.min.floatValue(), this.max.floatValue());
      if (this.value.floatValue() == -0.0F) {
         this.value = 0.0F;
      }
   }

   @Override
   public float getHeight() {
      return this.visibilityCondition.getAsBoolean() ? 30.0F : 0.0F;
   }

   @Environment(EnvType.CLIENT)
   public interface FormatConsumer {
      String format(Number var1);
   }
}
