package ru.melonity.feature.features.modules.render;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.List;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_4587;
import net.minecraft.class_4604;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.ILevelRenderer;
import ru.melonity.feature.Feature;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.MenuColorSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.render.ProjectUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class ParticlesFeature extends SimpleFeature {
   private final Color startColor = Melonity.INSTANCE.styleManager.getStyle().ascent();
   private final MenuColorSetting colorSetting = new MenuColorSetting("global.color", this.startColor);
   private final List<ParticlesFeature.Particle> particles = Lists.newLinkedList();
   private final EventListener<RenderOverlayEvent> onRenderOverlay = event -> {
      if (mc.field_1724.field_6012 % 4 == 0) {
         for (int local2_0 = 0; local2_0 < 2; local2_0++) {
            this.particles.add(new ParticlesFeature.Particle(mc.field_1724.method_19538()));
         }
      }

      this.particles.removeIf(ParticlesFeature.Particle::update);
      class_4587 local2_6 = event.matrixStack();

      for (ParticlesFeature.Particle local4_5 : this.particles) {
         class_4604 local5_1 = ((ILevelRenderer)mc.field_1769).getFrustum();
         if (local5_1.method_23093(
            new class_238(
               local4_5.position.field_1352,
               local4_5.position.field_1351,
               local4_5.position.field_1350,
               local4_5.position.field_1352 + 1.0,
               local4_5.position.field_1351 + 1.0,
               local4_5.position.field_1350 + 1.0
            )
         )) {
            double[] local6_2 = ProjectUtil.project(local4_5.position.field_1352, local4_5.position.field_1351, local4_5.position.field_1350);
            if (local6_2 != null) {
               float local7_3 = 1.0F - ((float)System.currentTimeMillis() - local4_5.time) / 5000.0F;
               local7_3 *= 12.0F;
               boolean local8_4 = this.colorSetting.getColor().getRed() == this.startColor.getRed()
                  && this.colorSetting.getColor().getGreen() == this.startColor.getGreen()
                  && this.colorSetting.getColor().getBlue() == this.startColor.getBlue();
               Melonity.INSTANCE
                  .getRenderSystem()
                  .addShadowRect(
                     (float)local6_2[0] - 3.0F * local7_3 - 2.0F,
                     (float)local6_2[1] - 3.0F * local7_3 - 2.0F,
                     local7_3 + 4.0F,
                     local7_3 + 4.0F,
                     10.0F,
                     7.0F,
                     RenderSystem.setAlpha(
                        local8_4 ? Melonity.INSTANCE.styleManager.getStyle().ascent() : this.colorSetting.getColor(), (int)(local4_5.opacity * 255.0F)
                     ),
                     local2_6
                  );
               Melonity.INSTANCE
                  .getRenderSystem()
                  .addTexture(
                     class_2960.method_60656("melonity/images/ui/mainmenu/main_menu_logo.png"),
                     (float)local6_2[0] - 3.0F * local7_3,
                     (float)local6_2[1] - 3.0F * local7_3,
                     local7_3,
                     local7_3,
                     RenderSystem.setAlpha(
                        local8_4 ? Melonity.INSTANCE.styleManager.getStyle().ascent() : this.colorSetting.getColor(), (int)(local4_5.opacity * 255.0F)
                     ),
                     local2_6
                  );
            }
         }
      }
   };

   public ParticlesFeature() {
      super("Particles", MenuCategory.RENDER);
      this.setting(this.colorSetting);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      if (!value) {
         this.particles.clear();
      }
   }

   @Environment(EnvType.CLIENT)
   private static class Particle {
      private class_243 position;
      private float time = (float)System.currentTimeMillis();
      private float opacity;

      public Particle(class_243 position) {
         Random local2_0 = new Random();
         float local3_1 = (local2_0.nextFloat() - 0.5F) * 20.0F * 2.0F;
         float local4_2 = class_3532.method_15355(400.0F - local3_1 * local3_1) * 2.0F;
         float local5_3 = -1.0F;
         float local6_4 = (local2_0.nextFloat() - 0.5F) * local4_2;

         while (local5_3 < 0.0F) {
            local5_3 = (local2_0.nextFloat() - 0.5F) * local4_2;
         }

         this.position = position.method_1031((double)local3_1, (double)local5_3, (double)local6_4);
      }

      public boolean update() {
         this.position = this.fast(this.position, this.position.method_1031(0.0, -5.0, 0.0), 0.75F);
         this.opacity = this.fast(this.opacity, 1.0F, 10.0F);
         class_243 local1_0 = new class_243(Feature.mc.field_1724.method_23317(), Feature.mc.field_1724.method_23320(), Feature.mc.field_1724.method_23321());
         class_243 local2_1 = new class_243(this.position.field_1352, this.position.field_1351, this.position.field_1350);
         return (float)System.currentTimeMillis() - this.time > 5000.0F
            || Feature.mc
                  .field_1687
                  .method_17742(new class_3959(local1_0, local2_1, class_3960.field_17558, class_242.field_1348, Feature.mc.field_1724))
                  .method_17783()
               != class_240.field_1333;
      }

      private float fast(float end, float start, float multiple) {
         return (1.0F - class_3532.method_15363((float)(this.deltaTime() * (double)multiple), 0.0F, 1.0F)) * end
            + class_3532.method_15363((float)(this.deltaTime() * (double)multiple), 0.0F, 1.0F) * start;
      }

      private class_243 fast(class_243 end, class_243 start, float multiple) {
         return new class_243(
            (double)this.fast((float)end.field_1352, (float)start.field_1352, multiple),
            (double)this.fast((float)end.field_1351, (float)start.field_1351, multiple),
            (double)this.fast((float)end.field_1350, (float)start.field_1350, multiple)
         );
      }

      private double deltaTime() {
         return class_310.method_1551().method_47599() > 0 ? 1.0 / (double)class_310.method_1551().method_47599() : 1.0;
      }
   }
}
