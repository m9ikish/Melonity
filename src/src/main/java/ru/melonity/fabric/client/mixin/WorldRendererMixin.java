package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_276;
import net.minecraft.class_310;
import net.minecraft.class_3959;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4599;
import net.minecraft.class_4604;
import net.minecraft.class_757;
import net.minecraft.class_761;
import net.minecraft.class_765;
import net.minecraft.class_8921;
import net.minecraft.class_9779;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL30;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.ILevelRenderer;
import ru.melonity.feature.features.modules.render.GlowEspFeature;
import ru.melonity.feature.features.modules.render.ShaderEspFeature;
import ru.melonity.util.event.events.PostRenderWorldEvent;
import ru.melonity.util.event.events.RenderEntitiesEvent;
import ru.melonity.util.event.events.RenderWorldPassEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_761.class})
public class WorldRendererMixin implements ILevelRenderer {
   @Unique
   private class_4604 frustum;
   @Unique
   private class_276 entitiesFramebuffer;
   @Shadow
   @Final
   private class_4599 field_20951;

   @Shadow
   private void method_22977(
      class_1297 entity, double cameraX, double cameraY, double cameraZ, float tickDelta, class_4587 matrices, class_4597 vertexConsumers
   ) {
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   public void render(
      class_9779 tickCounter,
      boolean renderBlockOutline,
      class_4184 camera,
      class_757 gameRenderer,
      class_765 lightmapTextureManager,
      Matrix4f matrix4f,
      Matrix4f projectionIn,
      CallbackInfo callbackInfo
   ) {
      this.frustum = new class_4604(matrix4f, projectionIn);
      this.frustum.method_23088(camera.method_19326().field_1352, camera.method_19326().field_1351, camera.method_19326().field_1350);
   }

   @Inject(
      method = {"render"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/BufferBuilderStorage;getEntityVertexConsumers()Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;"
      )}
   )
   public void renderEntities(
      class_9779 tickCounter,
      boolean renderBlockOutline,
      class_4184 camera,
      class_757 gameRenderer,
      class_765 lightmapTextureManager,
      Matrix4f positionMatrix,
      Matrix4f projectionMatrix,
      CallbackInfo callbackInfo
   ) {
      Melonity.INSTANCE.getEventBus().fire(new RenderEntitiesEvent());
      class_4587 matrixStack = new class_4587();
      GlowEspFeature glowEsp = Melonity.INSTANCE.getFeatureManager().getFeature(GlowEspFeature.class).orElse(null);
      ShaderEspFeature shaderEsp = Melonity.INSTANCE.getFeatureManager().shaderEspFeature;
      if (glowEsp != null && glowEsp.isEnabled() || shaderEsp != null && shaderEsp.isEnabled()) {
         this.entitiesFramebuffer = GlowEspFeature.updateFramebuffer(this.entitiesFramebuffer);
         shaderEsp.glowFramebuffer = GlowEspFeature.updateFramebuffer(shaderEsp.glowFramebuffer);
         this.entitiesFramebuffer.method_1236(0.0F, 0.0F, 0.0F, 0.0F);
         this.entitiesFramebuffer.method_1230(false);
         this.entitiesFramebuffer.method_1235(true);
         boolean shadows = (Boolean)class_310.method_1551().field_1690.method_42435().method_41753();
         class_310.method_1551().field_1690.method_42435().method_41748(false);
         class_243 vec3d = camera.method_19326();
         double d = vec3d.method_10216();
         double e = vec3d.method_10214();
         double f = vec3d.method_10215();
         class_8921 tickManager = class_310.method_1551().field_1687.method_54719();
         Melonity.renderLabel = false;

         for (class_1657 entity : class_310.method_1551().field_1687.method_18456()) {
            if (entity.field_6012 == 0) {
               entity.field_6038 = entity.method_23317();
               entity.field_5971 = entity.method_23318();
               entity.field_5989 = entity.method_23321();
            }

            boolean glowing = entity.method_5851();
            entity.method_5834(false);
            if (class_310.method_1551().field_1724 != entity) {
               class_243 vector3d = new class_243(
                  class_310.method_1551().field_1724.method_23317(),
                  class_310.method_1551().field_1724.method_23320(),
                  class_310.method_1551().field_1724.method_23321()
               );
               class_243 vector3d1 = new class_243(entity.method_23317(), entity.method_23318(), entity.method_23321());
               boolean seen = class_310.method_1551()
                     .field_1687
                     .method_17742(new class_3959(vector3d, vector3d1, class_3960.field_17558, class_242.field_1348, class_310.method_1551().field_1724))
                     .method_17783()
                  == class_240.field_1333;
               float g = tickCounter.method_60637(!tickManager.method_54746(entity));
               GL30.glBindFramebuffer(36160, this.entitiesFramebuffer.field_1476);
               this.method_22977(entity, d, e, f, g, matrixStack, this.field_20951.method_23000());
            }

            entity.method_5834(glowing);
         }

         Melonity.renderLabel = true;
         class_310.method_1551().field_1690.method_42435().method_41748(shadows);
         this.entitiesFramebuffer.method_1240();
         class_310.method_1551().method_1522().method_1235(false);
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("RETURN")}
   )
   public void render_ret(
      class_9779 tickCounter,
      boolean renderBlockOutline,
      class_4184 camera,
      class_757 gameRenderer,
      class_765 lightmapTextureManager,
      Matrix4f view,
      Matrix4f projectionIn,
      CallbackInfo callbackInfo
   ) {
      Matrix4f projection = new Matrix4f(projectionIn);
      Matrix4f modelView = projection.mul(view);
      Melonity.INSTANCE.getEventBus().fire(new RenderWorldPassEvent(modelView, tickCounter.method_60637(true)));
   }

   @Inject(
      method = {"render"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/option/GameOptions;getCloudRenderModeValue()Lnet/minecraft/client/option/CloudRenderMode;",
         shift = Shift.BEFORE
      )}
   )
   public void postRender(
      class_9779 tickCounter,
      boolean renderBlockOutline,
      class_4184 camera,
      class_757 gameRenderer,
      class_765 lightmapTextureManager,
      Matrix4f matrix4f,
      Matrix4f matrix4f2,
      CallbackInfo callbackInfo
   ) {
      Melonity.INSTANCE.getEventBus().fire(new PostRenderWorldEvent(new class_4587(), tickCounter.method_60637(true), matrix4f));
   }

   @Override
   public class_4604 getFrustum() {
      return this.frustum;
   }

   @Override
   public class_276 entitiesFramebuffer() {
      return this.entitiesFramebuffer;
   }
}
