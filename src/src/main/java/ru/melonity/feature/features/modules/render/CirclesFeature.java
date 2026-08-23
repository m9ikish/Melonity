package ru.melonity.feature.features.modules.render;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_243;
import net.minecraft.class_310;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.JumpEvent;
import ru.melonity.util.event.events.RenderWorldPassEvent;
import ru.melonity.util.render.texture.WavefrontTexture;
import ru.melonity.util.render.wavefront.Model;

@Environment(EnvType.CLIENT)
public class CirclesFeature extends SimpleFeature {
   private final CopyOnWriteArrayList<CirclesFeature.Circle> circles = Lists.newCopyOnWriteArrayList();
   private boolean jumped;
   private final EventListener<JumpEvent> onJump = event -> this.circles
         .add(new CirclesFeature.Circle(mc.field_1724.method_30950(mc.method_60646().method_60637(true)).method_1031(0.0, 0.05, 0.0)));
   private final Model jumpWaveL = new Model(
      CirclesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/jumpc_wave_l.obj"),
      CirclesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/jumpcircle_waves.png")
   );
   private final Model jumpWaveM = new Model(
      CirclesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/jumpc_wave_m.obj"),
      CirclesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/jumpcircle_waves.png")
   );
   private final Model jumpWaveS = new Model(
      CirclesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/jumpc_wave_s.obj"),
      CirclesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/jumpcircle_waves.png")
   );
   private final Model jumpFloor = new Model(
      CirclesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/jumpc_floor.obj"),
      CirclesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/jumpcircle_floor.png")
   );
   private final EventListener<RenderWorldPassEvent> onWorldPass = event -> {
      Matrix4f local2_1 = new Matrix4f(event.modelView());
      class_243 local3_2 = class_310.method_1551().field_1773.method_19418().method_19326();
      boolean local4_3 = GL30.glGetBoolean(2930);
      boolean local5_4 = GL30.glIsEnabled(2929);
      boolean local6_5 = GL30.glIsEnabled(2884);
      boolean local7_6 = GL30.glIsEnabled(3042);
      int local8_7 = GL30.glGetInteger(34229);
      int local9_8 = GL30.glGetInteger(32873);
      int local10_9 = GL30.glGetInteger(34016);
      int local11_10 = GL30.glGetInteger(35725);
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glDisable(2884);
      TargetESPFeature.coreShader.bind();
      this.circles.removeIf(CirclesFeature.Circle::shouldRemove);

      for (CirclesFeature.Circle local13_0 : this.circles) {
         this.drawModel(this.jumpWaveL, local2_1, local13_0.position, local3_2, local13_0.wave1Anim, 1);
         this.drawModel(this.jumpWaveM, local2_1, local13_0.position, local3_2, local13_0.wave2Anim, 2);
         this.drawModel(this.jumpWaveS, local2_1, local13_0.position, local3_2, local13_0.wave3Anim, 3);
         if (local13_0.wave3Anim.back || local13_0.wave3Anim.anim() >= 0.2F) {
            this.drawModel(this.jumpFloor, local2_1, local13_0.position, local3_2, local13_0.circleAnim, 4);
         }

         GlStateManager._bindTexture(0);
      }

      TargetESPFeature.coreShader.unbind();
      GL30.glDepthMask(local4_3);
      state(2929, local5_4);
      state(2884, local6_5);
      state(3042, local7_6);
      GL30.glActiveTexture(local10_9);
      GL30.glBindTexture(3553, local9_8);
      GL30.glBindVertexArray(local8_7);
      GL30.glUseProgram(local11_10);
      GL30.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
   };

   public CirclesFeature() {
      super("JumpCircles", MenuCategory.RENDER);
   }

   private void cachedBindToSlot(WavefrontTexture texture, int slot) {
      texture.bindToSlot(slot);
   }

   private void drawModel(Model model, Matrix4f modelView, class_243 position, class_243 cameraPos, CirclesFeature.Anim anim, int index) {
      float local7_0 = 0.0F;
      switch (index) {
         case 1:
            local7_0 = anim.update(!anim.back) * 1.1F;
            if (local7_0 >= 1.09F) {
               anim.back = true;
            }
            break;
         case 2:
            local7_0 = anim.update(!anim.back) * 0.7F;
            if (local7_0 >= 0.69F) {
               anim.back = true;
            }
            break;
         case 3:
            local7_0 = anim.update(!anim.back) * 0.62F;
            if (local7_0 >= 0.61F) {
               anim.back = true;
            }
            break;
         case 4:
            local7_0 = anim.update(!anim.back);
            if (local7_0 >= 0.99F) {
               anim.back = true;
            }
      }

      double local8_1 = position.field_1352 - cameraPos.field_1352;
      double local10_2 = position.field_1351 - cameraPos.field_1351;
      double local12_3 = position.field_1350 - cameraPos.field_1350;
      if (!(Math.abs(local8_1) > 1000000.0) && !(Math.abs(local10_2) > 1000000.0) && !(Math.abs(local12_3) > 1000000.0)) {
         float local14_4 = 1.0F;
         Matrix4f local15_5 = new Matrix4f(modelView);
         local15_5.translate((float)local8_1, (float)local10_2, (float)local12_3);
         local15_5.scale(index == 4 ? local7_0 : local14_4, local7_0, index == 4 ? local7_0 : local14_4);
         TargetESPFeature.coreShaderModelViewUniform.uploadMatrix4f(local15_5);
         BiConsumer<WavefrontTexture, Integer> local16_6 = this::cachedBindToSlot;
         local16_6.accept(model.texture(), 0);
         TargetESPFeature.coreShaderMixIUniform.uploadFloat(0.0F);
         model.wavefront().drawAllParts();
      }
   }

   public static void state(int name, boolean value) {
      if (value) {
         GL20.glEnable(name);
      } else {
         GL20.glDisable(name);
      }
   }

   @Environment(EnvType.CLIENT)
   private static class Anim {
      private final FrameWeightCalculator fwc;
      private final StateAnimation animation;
      public boolean back = false;

      public Anim(long millis) {
         this.fwc = FrameWeightCalculator.milliseconds(millis);
         this.animation = new StateAnimation();
      }

      public float update(boolean state) {
         this.animation.state(state);
         this.animation.update(this.fwc.elapsedUnits());
         return this.animation.animation();
      }

      public float anim() {
         return this.animation.animation();
      }

      public float drop() {
         return this.animation.dropAnimation();
      }
   }

   @Environment(EnvType.CLIENT)
   private static class Circle {
      private final class_243 position;
      private final CirclesFeature.Anim wave1Anim = new CirclesFeature.Anim(350L);
      private final CirclesFeature.Anim wave2Anim = new CirclesFeature.Anim(560L);
      private final CirclesFeature.Anim wave3Anim = new CirclesFeature.Anim(720L);
      private final CirclesFeature.Anim circleAnim = new CirclesFeature.Anim(1000L);
      private final long creationTime;

      public Circle(class_243 position) {
         this.position = position;
         this.creationTime = System.currentTimeMillis();
      }

      public boolean shouldRemove() {
         return this.circleAnim.back && this.circleAnim.anim() <= 0.0F || System.currentTimeMillis() - this.creationTime > 5000L;
      }
   }
}
