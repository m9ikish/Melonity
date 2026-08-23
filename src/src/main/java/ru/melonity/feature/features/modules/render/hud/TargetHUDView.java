package ru.melonity.feature.features.modules.render.hud;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.player.NameProtectFeature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.minecraft.HealthResolveUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class TargetHUDView {
   private float width;
   private float height;
   private final FrameWeightCalculator frameWeightCalculator = FrameWeightCalculator.milliseconds(250L);
   private final StateAnimation animation = new StateAnimation();
   private final FrameWeightCalculator healthFwc = FrameWeightCalculator.milliseconds(250L);
   private float currentHealth;
   private boolean healthInit = false;
   private final FrameWeightCalculator oldFwc = FrameWeightCalculator.milliseconds(250L);
   private float currentOldHealth;
   private boolean oldHealthInit = false;
   private class_1309 target;
   private boolean backward;
   private TargetHUDView.Type type;

   public TargetHUDView(float width, float height, TargetHUDView.Type type) {
      this.width = width;
      this.height = height;
      this.type = type;
   }

   public void insert(float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_332 graphics) {
      if (this.target != null) {
         class_4587 local9_10 = graphics.method_51448();
         this.animation.state(!this.backward);
         this.animation.update(this.frameWeightCalculator.elapsedUnits());
         float local10_11 = this.animation.dropAnimation();
         if (!(local10_11 <= 0.0F)) {
            float local11_12 = HealthResolveUtil.resolve(this.target);
            if (!this.healthInit) {
               this.currentHealth = local11_12;
               this.healthInit = true;
            }

            this.currentHealth = this.currentHealth + (local11_12 - this.currentHealth) * 15.0F * this.healthFwc.elapsedUnits();
            this.currentHealth = class_3532.method_15363(this.currentHealth, 0.0F, this.target.method_6063());
            if (!this.oldHealthInit) {
               this.currentOldHealth = local11_12;
               this.oldHealthInit = true;
            }

            this.currentOldHealth = this.currentOldHealth + (local11_12 - this.currentOldHealth) * this.oldFwc.elapsedUnits();
            this.currentOldHealth = class_3532.method_15363(this.currentOldHealth, 0.0F, this.target.method_6063());
            local9_10.method_22903();
            local9_10.method_46416(
               x * renderSystem.getFakeScaling() + this.width * renderSystem.getFakeScaling() / 2.0F,
               y * renderSystem.getFakeScaling() + this.height * renderSystem.getFakeScaling() / 2.0F,
               0.0F
            );
            local9_10.method_22905(local10_11, local10_11, local10_11);
            local9_10.method_46416(
               -(x * renderSystem.getFakeScaling()) - this.width * renderSystem.getFakeScaling() / 2.0F,
               -(y * renderSystem.getFakeScaling()) - this.height * renderSystem.getFakeScaling() / 2.0F,
               0.0F
            );
            this.setWidth(this.type == TargetHUDView.Type.TYPE2 ? 98.0F : 110.0F);
            this.setHeight(this.type == TargetHUDView.Type.TYPE1 ? 45.0F : 48.0F);
            String local12_13 = this.target.method_5477().getString();
            NameProtectFeature local13_14 = Melonity.INSTANCE.getFeatureManager().getFeature(NameProtectFeature.class).get();
            if (local13_14.isEnabled() && this.target == class_310.method_1551().field_1724) {
               local12_13 = "MelonityUser";
            }

            MenuStyle local14_15 = Melonity.INSTANCE.styleManager.getStyle();
            renderSystem.setAlphaModifier(this.animation.animation());
            renderSystem.addRect(x, y, this.width, this.height, 8.0F, local14_15.background(), local9_10);
            switch (this.type) {
               case TYPE1:
                  renderSystem.addHead(this.target, x + 3.5F, y + 4.0F, this.height - 8.0F, this.height - 8.0F, 8.0F, local9_10);
                  renderSystem.addText(
                     Fonts.INTER_REGULAR_14, local12_13.length() > 12 ? local12_13.substring(0, 12) + "..." : local12_13, x + 45.0F, y + 10.0F, local9_10
                  );
                  renderSystem.addText(
                     Fonts.INTER_REGULAR_14, String.format("HP: %.1f", this.currentHealth), x + 45.0F, y + 21.0F, Color.decode("#888888"), local9_10
                  );
                  float local15_0 = 58.0F;
                  float local16_1 = 6.5F;
                  renderSystem.addRect(x + 45.0F, y + this.height - local16_1 - 4.0F, local15_0, local16_1, 4.0F, Color.decode("#222222"), local9_10);
                  renderSystem.addRect(
                     x + 45.0F,
                     y + this.height - local16_1 - 4.0F,
                     local15_0 * class_3532.method_15363(this.currentHealth / this.target.method_6063(), 0.0F, 1.0F),
                     local16_1,
                     4.0F,
                     local14_15.ascent(),
                     local9_10
                  );
                  renderSystem.addRect(
                     x + 45.0F,
                     y + this.height - local16_1 - 4.0F,
                     local15_0 * class_3532.method_15363(this.currentOldHealth / this.target.method_6063(), 0.0F, 1.0F),
                     local16_1,
                     4.0F,
                     RenderSystem.setAlpha(local14_15.ascent(), 60),
                     local9_10
                  );
                  break;
               case TYPE2:
                  renderSystem.addHead(this.target, x + 4.0F, y + 5.5F, 33.0F, 34.0F, 8.0F, local9_10);
                  renderSystem.addRectGradientCorners(
                     x,
                     y + this.height - 3.0F,
                     this.width,
                     3.0F,
                     4.0F,
                     0.0F,
                     0.0F,
                     4.0F,
                     Color.decode("#222222"),
                     Color.decode("#222222"),
                     Color.decode("#222222"),
                     Color.decode("#222222"),
                     local9_10
                  );
                  renderSystem.addRectGradientCorners(
                     x,
                     y + this.height - 3.0F,
                     this.width * (this.currentHealth / this.target.method_6063()),
                     3.0F,
                     4.0F,
                     0.0F,
                     0.0F,
                     4.0F,
                     local14_15.ascent(),
                     local14_15.ascent(),
                     local14_15.ascent(),
                     local14_15.ascent(),
                     local9_10
                  );
                  String local15_7 = local12_13;
                  if (local12_13.length() > 10) {
                     local15_7 = local12_13.substring(0, 10) + "...";
                  }

                  renderSystem.addText(Fonts.INTER_REGULAR_15, local15_7, x + 42.0F, y + 9.0F, local9_10);
                  renderSystem.addText(
                     Fonts.INTER_REGULAR_15,
                     String.format("HP: %.1f", this.target.method_6032()).replace(',', '.'),
                     x + 42.0F,
                     y + 32.5F,
                     Color.decode("#888888"),
                     local9_10
                  );
                  float local16_8 = x + 42.0F;
                  List<class_1799> local17_9 = Lists.newLinkedList(this.target.method_56674());
                  Collections.reverse(local17_9);

                  for (class_1799 local19_6 : local17_9) {
                     if (!local19_6.method_7960()) {
                        local9_10.method_22903();
                        local9_10.method_46416(local16_8, y + 17.0F, 0.0F);
                        local9_10.method_22905(0.7F, 0.7F, 0.7F);
                        local9_10.method_46416(-local16_8, -(y + 17.0F), 0.0F);
                        graphics.method_51427(local19_6, (int)local16_8, (int)y + 17);
                        if (local19_6.method_7986()) {
                           float local20_2 = (float)local19_6.method_7919();
                           float local21_3 = (float)local19_6.method_7936();
                           float local22_4 = Math.max(0.0F, (local21_3 - local20_2) / local21_3);
                           int local23_5 = class_3532.method_15369(local22_4 / 3.0F, 1.0F, 1.0F);
                           renderSystem.addRect(local16_8 + 4.0F, y + 32.0F, 8.0F, 2.5F, new Color(local23_5), local9_10);
                        }

                        local9_10.method_22909();
                        local16_8 += 12.0F;
                     }
                  }
            }

            renderSystem.setAlphaModifier(1.0F);
            local9_10.method_22909();
         }
      }
   }

   private float getHealth() {
      return this.target.method_6032();
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
   public void setCurrentHealth(float currentHealth) {
      this.currentHealth = currentHealth;
   }

   @Generated
   public void setHealthInit(boolean healthInit) {
      this.healthInit = healthInit;
   }

   @Generated
   public void setCurrentOldHealth(float currentOldHealth) {
      this.currentOldHealth = currentOldHealth;
   }

   @Generated
   public void setOldHealthInit(boolean oldHealthInit) {
      this.oldHealthInit = oldHealthInit;
   }

   @Generated
   public void setTarget(class_1309 target) {
      this.target = target;
   }

   @Generated
   public void setBackward(boolean backward) {
      this.backward = backward;
   }

   @Generated
   public void setType(TargetHUDView.Type type) {
      this.type = type;
   }

   @Environment(EnvType.CLIENT)
   public static enum Type {
      TYPE1,
      TYPE2;
   }
}
