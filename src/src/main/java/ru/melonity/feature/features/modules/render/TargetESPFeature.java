package ru.melonity.feature.features.modules.render;

import java.util.function.BiConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_7833;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.combat.AuraFeature;
import ru.melonity.feature.features.modules.render.particles.AnimationUtils;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.animation.Easings;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderWorldPassEvent;
import ru.melonity.util.render.shader.Shader;
import ru.melonity.util.render.shader.ShaderSource;
import ru.melonity.util.render.shader.Uniform;
import ru.melonity.util.render.texture.WavefrontTexture;
import ru.melonity.util.render.wavefront.Model;

@Environment(EnvType.CLIENT)
public class TargetESPFeature extends SimpleFeature {
   public static Shader coreShader;
   public static Uniform coreShaderModelViewUniform;
   public static Uniform coreShaderTex2Uniform;
   public static Uniform coreShaderMixIUniform;
   private final Model model1 = new Model(
      AuraFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/target_esp_top.obj"),
      AuraFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/target_esp_regular.png"),
      AuraFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/target_esp_lowhp.png")
   );
   private final Model model2 = new Model(
      AuraFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/target_esp_bottom.obj"),
      AuraFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/target_esp_regular.png"),
      AuraFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/target_esp_lowhp.png")
   );
   private final long startTime = System.currentTimeMillis();
   private class_1309 savedTarget = null;
   private float diedScale;
   private final EventListener<RenderWorldPassEvent> onRenderWorld = this::handleRenderWorldPass;

   public TargetESPFeature() {
      super("TargetESP", MenuCategory.RENDER);
      coreShader = new Shader(ShaderSource.namedInputStreamPair("particles"));
      coreShaderModelViewUniform = coreShader.uniform("modelView");
      coreShaderTex2Uniform = coreShader.uniform("tex2");
      coreShaderMixIUniform = coreShader.uniform("mixI");
   }

   private void cachedBindToSlot(WavefrontTexture texture, int slot) {
      texture.bindToSlot(slot);
   }

   private void drawModel(Model model, Matrix4f modelView, double frameY, float time, class_1309 target) {
      double local7_0 = class_3532.method_16436((double)mc.method_60646().method_60637(true), this.savedTarget.field_6014, this.savedTarget.method_23317());
      double local9_1 = class_3532.method_16436((double)mc.method_60646().method_60637(true), this.savedTarget.field_5969, this.savedTarget.method_23321());
      float local11_2 = 0.75F;
      float local12_3 = Math.min(target.method_17681(), 2.0F);
      modelView.translate((float)local7_0, (float)frameY, (float)local9_1);
      modelView.scale(local11_2, local11_2, local11_2);
      modelView.scale(
         (float)Easings.BACK_OUT.ease((double)this.diedScale),
         (float)Easings.BACK_OUT.ease((double)this.diedScale),
         (float)Easings.BACK_OUT.ease((double)this.diedScale)
      );
      modelView.scale(local12_3, local12_3, local12_3);
      modelView.rotate(class_7833.field_40716.rotation(class_3532.method_15374(time)));
      coreShaderModelViewUniform.uploadMatrix4f(modelView);
      BiConsumer<WavefrontTexture, Integer> local13_4 = this::cachedBindToSlot;
      local13_4.accept(model.texture(), 0);
      local13_4.accept(model.texture2(), 1);
      coreShaderTex2Uniform.uploadInt(1);
      coreShaderMixIUniform.uploadFloat(class_3532.method_15363(1.0F - this.savedTarget.method_6032() / this.savedTarget.method_6063(), 0.0F, 1.0F));
      model.wavefront().drawAllParts();
   }

   private void handleRenderWorldPass(RenderWorldPassEvent event) {
      if (!this.isEnabled()) {
         this.diedScale = 0.0F;
      } else {
         class_1309 local2_0 = this.getAurasTarget();
         if (local2_0 != null && local2_0.method_5805()) {
            this.savedTarget = local2_0;
            this.diedScale = AnimationUtils.animateLerp(this.diedScale, 1.0F, 0.06F);
         } else {
            this.savedTarget = null;
            this.diedScale = AnimationUtils.animateLerp(this.diedScale, 0.0F, 0.06F);
         }

         if (this.savedTarget != null) {
            if (local2_0 != null) {
               int local4_1 = GL30.glGetInteger(34016);
               int local5_2 = GL30.glGetInteger(32873);
               Matrix4f local6_3 = new Matrix4f(event.modelView());
               class_243 local7_4 = mc.field_1773.method_19418().method_19326();
               local6_3.translate((float)(-local7_4.field_1352), (float)(-local7_4.field_1351), (float)(-local7_4.field_1350));
               boolean local8_5 = GL20.glGetBoolean(2930);
               boolean local9_6 = GL20.glIsEnabled(2929);
               boolean local10_7 = GL20.glIsEnabled(2884);
               int local11_8 = GL20.glGetInteger(34229);
               GL20.glDepthMask(true);
               GL20.glEnable(2929);
               GL20.glDisable(2884);
               coreShader.bind();
               float local12_9 = class_3532.method_15374((float)(System.currentTimeMillis() - this.startTime) / 300.0F);
               float local13_10 = class_3532.method_15362((float)(System.currentTimeMillis() - this.startTime) / 300.0F);
               double local14_11 = class_3532.method_16436((double)event.partialTicks(), this.savedTarget.field_6036, this.savedTarget.method_23318())
                  + (double)(this.savedTarget.method_17682() / 2.0F);
               this.drawModel(this.model1, new Matrix4f(local6_3), local14_11 + 0.5, local12_9, local2_0);
               this.drawModel(this.model2, new Matrix4f(local6_3), local14_11 - 0.1F, local13_10, local2_0);
               coreShader.unbind();
               GL20.glDepthMask(local8_5);
               state(2929, local9_6);
               state(2884, local10_7);
               GL30.glBindVertexArray(local11_8);
               GL30.glActiveTexture(local4_1);
               GL30.glBindTexture(3553, local5_2);
            }
         }
      }
   }

   /**
    * The `if (!this.isEnabled())` branch at the top of handleRenderWorldPass is dead code:
    * GenericPatternEventBus.fire already skips listeners whose owner is a disabled Feature
    * (GenericPatternEventBus.java:119), so the handler never runs while this feature is off and
    * the instant `diedScale = 0` reset there can never fire. Result: disabling while an Aura
    * target is locked strands diedScale at ~1.0, and re-enabling snaps the ring in at full size
    * instead of replaying the BACK_OUT grow-in. Reset here instead, where setEnabled(false)
    * actually calls us. savedTarget is cleared too so a disabled feature does not pin the last
    * class_1309 alive. NOTE: this is a deliberate divergence from the original bytecode (both the
    * bus gate and the redundant in-method guard are faithful) - it fixes an upstream Melonity bug.
    * Do NOT copy that in-method guard into DamageParticlesFeature.drawParticles; it is dead there too.
    */
   @Override
   public void onDisable() {
      super.onDisable();
      this.diedScale = 0.0F;
      this.savedTarget = null;
   }

   public static void state(int name, boolean value) {
      if (value) {
         GL20.glEnable(name);
      } else {
         GL20.glDisable(name);
      }
   }

   private class_1309 getAurasTarget() {
      return Melonity.INSTANCE.getFeatureManager().auraFeature.getTarget();
   }
}
