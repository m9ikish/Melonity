package ru.melonity.ui.menu.clickui.view.feature.setting;

import java.awt.Color;
import java.util.Arrays;
import java.util.function.BooleanSupplier;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import org.lwjgl.glfw.GLFW;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.feature.features.modules.player.MiddleClickPearlFeature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.minecraft.KeyUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class BindSetting implements MenuFeatureView {
   public int bind;
   public boolean sliding;
   private final String text;
   private final class_1799 hint;
   private BooleanSupplier[] visibilityConditions = new BooleanSupplier[]{() -> true};

   public BindSetting(String text, int bind, class_1799 hint) {
      this.bind = bind;
      this.text = text;
      this.sliding = false;
      this.hint = hint;
   }

   public BindSetting(String text, int bind) {
      this(text, bind, null);
   }

   public BindSetting(String text) {
      this(text, -1);
   }

   public BindSetting(int bind) {
      this("global.keybind", bind);
   }

   public BindSetting setVisible(BooleanSupplier condition) {
      this.visibilityConditions = new BooleanSupplier[]{condition};
      return this;
   }

   public BindSetting setVisible(BooleanSupplier... conditions) {
      this.visibilityConditions = conditions;
      return this;
   }

   private boolean isVisible() {
      return Arrays.stream(this.visibilityConditions).allMatch(BooleanSupplier::getAsBoolean);
   }

   @Override
   public void insert(
      MenuStyle style, float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack, class_332 context
   ) {
      if (this.isVisible()) {
         boolean local12_2 = this.hint != null;
         if (local12_2) {
            poseStack.method_22903();
            poseStack.method_46416(x, y, 0.0F);
            poseStack.method_22905(0.5F, 0.5F, 0.5F);
            poseStack.method_46416(-x, -y, 0.0F);
            context.method_51427(this.hint, (int)x - 4, (int)y - 4);
            poseStack.method_22909();
         }

         renderSystem.addText(Fonts.INTER_MEDIUM_14, Melonity.INSTANCE.languageService.get(this.text), x + (float)(local12_2 ? 9 : 0), y, poseStack);
         GlyphData local13_3 = Fonts.INTER_REGULAR_14;
         String local14_4 = KeyUtil.getKeyName(this.bind);
         float local15_5 = local13_3.getStringWidth(local14_4) + 8.0F;
         boolean local16_6 = MathUtil.isMouseHoveringRect((double)(x + width - 16.0F - local15_5), (double)(y - 3.0F), (double)local15_5, 10.0, mouseX, mouseY);
         Color local17_7 = style.previewValues();
         Color local18_8 = Color.decode("#888888");
         if (this.sliding) {
            local17_7 = style.previewValues().darker();
            local18_8 = Color.WHITE;
         } else if (local16_6) {
            local17_7 = style.previewValues().brighter();
         }

         if (this.sliding && GLFW.glfwGetKey(class_310.method_1551().method_22683().method_4490(), 342) == 1) {
            this.setBind(342);
            this.sliding = false;
         }

         if (this.sliding && GLFW.glfwGetKey(class_310.method_1551().method_22683().method_4490(), 346) == 1) {
            this.setBind(346);
            this.sliding = false;
         }

         if (this.bind == 2) {
            MiddleClickPearlFeature local19_1 = null;

            for (Feature local21_0 : Melonity.INSTANCE.getFeatureManager().getFeatures()) {
               if (local21_0 instanceof MiddleClickPearlFeature) {
                  local19_1 = (MiddleClickPearlFeature)local21_0;
                  break;
               }
            }

            if (local19_1 != null && local19_1.isEnabled()) {
               local17_7 = new Color(255, 100, 100, 180);
               local18_8 = Color.WHITE;
            }
         }

         renderSystem.addRect(x + width - 16.0F - local15_5 + 0.2F, y - 5.0F, local15_5 - 1.0F, 12.8F, 3.5F, new Color(136, 136, 136, 34), poseStack);
         renderSystem.addRect(x + width - 16.0F - local15_5, y - 5.0F, local15_5, 12.0F, 3.5F, local17_7, poseStack);
         renderSystem.addText(
            local13_3, local14_4, x + width - 16.0F - local15_5 + local15_5 / 2.0F - local13_3.getStringWidth(local14_4) / 2.0F, y - 0.5F, local18_8, poseStack
         );
         if (this.sliding) {
            Melonity.text = true;
         } else if (MathUtil.isMouseHoveringRect((double)(x + width - 16.0F - local15_5), (double)(y - 3.0F), (double)local15_5, 10.0, mouseX, mouseY)) {
            Melonity.hand = true;
         }
      }
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      if (this.isVisible()) {
         if (this.sliding) {
            if (mouseButton == 3) {
               this.setBind(3);
               this.sliding = false;
            } else if (mouseButton == 4) {
               this.setBind(4);
               this.sliding = false;
            } else if (mouseButton == 2) {
               this.setBind(2);
               this.sliding = false;
            }
         }

         GlyphData local9_0 = Fonts.INTER_REGULAR_14;
         String local10_1 = KeyUtil.getKeyName(this.bind);
         float local11_2 = local9_0.getStringWidth(local10_1) + 8.0F;
         if (MathUtil.isMouseHoveringRect((double)(x + width - 16.0F - local11_2), (double)(y - 3.0F), (double)local11_2, 10.0, mouseX, mouseY)) {
            this.sliding = !this.sliding;
         }
      }
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      if (!this.isVisible()) {
         return false;
      } else if (this.sliding) {
         if (keyCode == 256) {
            this.setBind(-1);
            this.sliding = false;
            return true;
         } else {
            this.setBind(keyCode);
            this.sliding = false;
            return true;
         }
      } else {
         return false;
      }
   }

   public void setBind(int newBind) {
      if (newBind == 2) {
         MiddleClickPearlFeature local2_1 = null;

         for (Feature local4_0 : Melonity.INSTANCE.getFeatureManager().getFeatures()) {
            if (local4_0 instanceof MiddleClickPearlFeature) {
               local2_1 = (MiddleClickPearlFeature)local4_0;
               break;
            }
         }

         if (local2_1 != null && local2_1.isEnabled()) {
            Melonity.INSTANCE.getNotificationManager().add(new Notification("Keybind conflict", "MOUSE3 в двух функциях одновременно", "feature-disabled.png"));
         }
      }

      this.bind = newBind;
   }

   @Override
   public void release() {
   }

   @Override
   public float getHeight() {
      return this.isVisible() ? 20.0F : 0.0F;
   }

   @Override
   public void resetAnimations() {
   }

   @Generated
   public int getBind() {
      return this.bind;
   }

   @Generated
   public boolean isSliding() {
      return this.sliding;
   }

   @Generated
   public String getText() {
      return this.text;
   }

   @Generated
   public class_1799 getHint() {
      return this.hint;
   }

   @Generated
   public BooleanSupplier[] getVisibilityConditions() {
      return this.visibilityConditions;
   }

   @Generated
   public void setSliding(boolean sliding) {
      this.sliding = sliding;
   }

   @Generated
   public void setVisibilityConditions(BooleanSupplier[] visibilityConditions) {
      this.visibilityConditions = visibilityConditions;
   }
}
