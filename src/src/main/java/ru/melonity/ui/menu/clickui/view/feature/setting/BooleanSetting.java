package ru.melonity.ui.menu.clickui.view.feature.setting;

import java.util.function.BooleanSupplier;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class BooleanSetting implements MenuFeatureView {
   private final String name;
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(250L);
   private final StateAnimation animation = new StateAnimation();
   private boolean value;
   private BooleanSupplier visibilityCondition = () -> true;

   public BooleanSetting(String name, boolean value) {
      this.name = name;
      this.value = value;
   }

   public BooleanSetting setVisible(BooleanSupplier condition) {
      this.visibilityCondition = condition;
      return this;
   }

   public void setValue(boolean value) {
      this.value = value;
   }

   @Override
   public void insert(
      MenuStyle style, float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack, class_332 context
   ) {
      if (this.visibilityCondition.getAsBoolean()) {
         renderSystem.addTexture(class_2960.method_60656("melonity/images/ui/checkbox.png"), x, y - 4.0F, 8.0F, 8.5F, poseStack);
         this.animation.state(this.value);
         this.animation.update(this.fwc.elapsedUnits());
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/checkbox_toggled.png"),
            x,
            y - 4.0F,
            8.0F,
            8.5F,
            RenderSystem.setAlpha(style.booleanElement(), class_3532.method_15340((int)(this.animation.animation() * 255.0F), 0, 255)),
            poseStack
         );
         renderSystem.addText(Fonts.INTER_MEDIUM_14, this.getLangService().get(this.name), x + 12.0F, y - 1.0F, poseStack);
         if (MathUtil.isMouseHoveringRect((double)x, (double)(y - 4.0F), 8.0, 8.5, mouseX, mouseY)) {
            Melonity.hand = true;
         }
      }
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      if (this.visibilityCondition.getAsBoolean()) {
         if (MathUtil.isMouseHoveringRect((double)x, (double)(y - 4.0F), 8.0, 8.5, mouseX, mouseY)) {
            this.value = !this.value;
         }
      }
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public void release() {
   }

   @Override
   public float getHeight() {
      return this.visibilityCondition.getAsBoolean() ? 16.0F : 0.0F;
   }

   @Override
   public void resetAnimations() {
      this.animation.reset();
   }

   public boolean get() {
      return this.value;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public FrameWeightCalculator getFwc() {
      return this.fwc;
   }

   @Generated
   public StateAnimation getAnimation() {
      return this.animation;
   }

   @Generated
   public boolean isValue() {
      return this.value;
   }

   @Generated
   public BooleanSupplier getVisibilityCondition() {
      return this.visibilityCondition;
   }

   @Generated
   public void setVisibilityCondition(BooleanSupplier visibilityCondition) {
      this.visibilityCondition = visibilityCondition;
   }
}
