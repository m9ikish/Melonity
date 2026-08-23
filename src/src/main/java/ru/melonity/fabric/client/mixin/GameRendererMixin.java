package ru.melonity.fabric.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1799;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_5912;
import net.minecraft.class_757;
import net.minecraft.class_7833;
import net.minecraft.class_9779;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IGameRenderer;
import ru.melonity.feature.features.modules.render.NoRenderFeature;
import ru.melonity.ui.menu.clickui.view.MenuGira;
import ru.melonity.util.event.events.RenderGameWorldEvent;
import ru.melonity.util.render.ProjectUtil;

@Environment(EnvType.CLIENT)
@Mixin({class_757.class})
public class GameRendererMixin implements IGameRenderer {
   @Shadow
   private float field_4005;
   @Shadow
   private float field_3988;
   @Shadow
   private float field_4004;

   @Inject(
      method = {"loadPrograms"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/GameRenderer;loadBlurPostProcessor(Lnet/minecraft/resource/ResourceFactory;)V",
         shift = Shift.BEFORE
      )}
   )
   void loadPrograms(class_5912 factory, CallbackInfo callbackInfo) {
      Melonity.INSTANCE.init(factory);
   }

   @Shadow
   private double method_3196(class_4184 camera, float tickDelta, boolean changingFov) {
      return 0.0;
   }

   @Shadow
   public float method_32796() {
      return 0.0F;
   }

   @Override
   public double getFovSafe(class_4184 camera, float partialTicks, boolean useFOVSetting) {
      return this.method_3196(camera, partialTicks, useFOVSetting);
   }

   @Inject(
      method = {"showFloatingItem"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void showFloatingItem(class_1799 floatingItem, CallbackInfo callbackInfo) {
      if (Melonity.INSTANCE.getFeatureManager().getFeature(NoRenderFeature.class).get().isEnabled()
         && Melonity.INSTANCE.getFeatureManager().getFeature(NoRenderFeature.class).get().getModeSetting().getMode().contains("norender.totem")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"getBasicProjectionMatrix"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getBasicProjectionMatrix(double fov, CallbackInfoReturnable<Matrix4f> cir) {
      Matrix4f matrix4f = new Matrix4f();
      if (this.field_4005 != 1.0F) {
         matrix4f.translate(this.field_3988, -this.field_4004, 0.0F);
         matrix4f.scale(this.field_4005, this.field_4005, 1.0F);
      }

      cir.setReturnValue(matrix4f.perspective((float)(fov * (float) (Math.PI / 180.0)), MenuGira.aspectRatio, 0.05F, this.method_32796()));
   }

   @Inject(
      method = {"renderWorld"},
      at = {@At(
         value = "FIELD",
         target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
         opcode = 180,
         ordinal = 0
      )}
   )
   public void renderWorld(class_9779 renderTickCounter, CallbackInfo callbackInfo) {
      class_4184 camera = class_310.method_1551().field_1773.method_19418();
      class_4587 matrixStack = new class_4587();
      RenderSystem.getModelViewStack().pushMatrix().mul(matrixStack.method_23760().method_23761());
      matrixStack.method_22907(class_7833.field_40714.rotationDegrees(camera.method_19329()));
      matrixStack.method_22907(class_7833.field_40716.rotationDegrees(camera.method_19330() + 180.0F));
      RenderSystem.applyModelViewMatrix();
      ProjectUtil.updateMatrices(
         RenderSystem.getProjectionMatrix(),
         new Matrix4f(RenderSystem.getModelViewMatrix()),
         matrixStack.method_23760().method_23761(),
         class_310.method_1551().method_22683()
      );
      Melonity.INSTANCE
         .getEventBus()
         .fire(
            new RenderGameWorldEvent(
               matrixStack, matrixStack.method_23760().method_23761(), renderTickCounter.method_60637(true), camera, RenderSystem.getProjectionMatrix()
            )
         );
      RenderSystem.getModelViewStack().popMatrix();
      RenderSystem.applyModelViewMatrix();
   }

   @Inject(
      method = {"findCrosshairTarget"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void findCrosshairTarget(
      class_1297 camera, double blockInteractionRange, double entityInteractionRange, float tickDelta, CallbackInfoReturnable<class_239> cir
   ) {
      if (Melonity.INSTANCE.getFeatureManager().noPlayerTraceFeature.isEnabled()) {
         class_243 cameraPos = camera.method_5836(tickDelta);
         cir.setReturnValue(class_3965.method_17778(cameraPos, class_2350.field_11043, class_2338.method_49638(cameraPos)));
      }
   }
}
