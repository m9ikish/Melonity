package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_329;
import net.minecraft.class_332;
import net.minecraft.class_465;
import net.minecraft.class_9779;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.misc.PotionsHighlightFeature;
import ru.melonity.feature.features.modules.render.HudFeature;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.event.events.RenderOverlayInContainerEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_329.class})
public class InGameHudMixin {
   // A @Shadow of InGameHud.layeredDrawer used to sit here, carrying both @Final and a
   // field initialiser. Mixin cannot honour that combination - it logged the shadow as
   // malformed on every launch - and nothing in this mixin ever read the field.

   @Inject(
      method = {"render"},
      at = {@At("RETURN")}
   )
   private void onRenderPost(class_332 context, class_9779 tickCounter, CallbackInfo ci) {
      this.renderMelonityHud(context, tickCounter);
   }

   @Inject(
      method = {"renderStatusEffectOverlay"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderStatusEffectOverlay(class_332 context, class_9779 tickCounter, CallbackInfo callbackInfo) {
      callbackInfo.cancel();
   }

   @Unique
   private void renderMelonityHud(class_332 graphics, class_9779 tickCounter) {
      if (Melonity.text) {
         GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.IBEAM);
      } else if (Melonity.hand) {
         GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.HAND);
      } else {
         GLFW.glfwSetCursor(class_310.method_1551().method_22683().method_4490(), Melonity.ARROW);
      }

      Melonity.hand = false;
      Melonity.text = false;
      Melonity.INSTANCE.getMenu().tick2();
      Melonity.INSTANCE.featureManager.shaderEspFeature.renderShaderESP(graphics.method_51448());
      Melonity.INSTANCE.getEventBus().fire(new RenderOverlayEvent(graphics.method_51448(), graphics));
      if (!(class_310.method_1551().field_1755 instanceof class_465)) {
         Melonity.INSTANCE.getEventBus().fire(new RenderOverlayInContainerEvent(graphics.method_51448(), graphics));
      }

      Melonity.INSTANCE.getNotificationManager().render(Melonity.INSTANCE.getRenderSystem(), graphics.method_51448());
   }

   @Inject(
      method = {"renderHotbar"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderHotbar(class_332 context, class_9779 tickCounter, CallbackInfo callbackInfo) {
      if (Melonity.INSTANCE.getFeatureManager() != null
         && Melonity.INSTANCE.getFeatureManager().getFeature(HudFeature.class).get().isEnabled()
         && Melonity.INSTANCE.getFeatureManager().getFeature(HudFeature.class).get().getModeSetting().getMode().contains("Hotbar")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderHotbarItem"},
      at = {@At("HEAD")}
   )
   private void renderHotbarItem(
      class_332 context, int x, int y, class_9779 tickCounter, class_1657 player, class_1799 stack, int seed, CallbackInfo callbackInfo
   ) {
      if (Melonity.INSTANCE.getFeatureManager().getFeature(PotionsHighlightFeature.class).get().isEnabled()) {
         Melonity.INSTANCE
            .getFeatureManager()
            .getFeature(PotionsHighlightFeature.class)
            .get()
            .potionSlotHighlight(stack, (float)x, (float)y, 16.0F, 0.0F, context.method_51448());
      }
   }

   @Inject(
      method = {"renderExperienceBar"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderExperienceBar(class_332 context, int x, CallbackInfo callbackInfo) {
      if (Melonity.INSTANCE.getFeatureManager() != null
         && Melonity.INSTANCE.getFeatureManager().getFeature(HudFeature.class).get().isEnabled()
         && Melonity.INSTANCE.getFeatureManager().getFeature(HudFeature.class).get().getModeSetting().getMode().contains("Hotbar")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderExperienceLevel"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderExperienceLevel(class_332 context, class_9779 tickCounter, CallbackInfo callbackInfo) {
      if (Melonity.INSTANCE.getFeatureManager() != null
         && Melonity.INSTANCE.getFeatureManager().getFeature(HudFeature.class).get().isEnabled()
         && Melonity.INSTANCE.getFeatureManager().getFeature(HudFeature.class).get().getModeSetting().getMode().contains("Hotbar")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderStatusBars"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderStatusBars(class_332 context, CallbackInfo callbackInfo) {
      if (Melonity.INSTANCE.getFeatureManager() != null
         && Melonity.INSTANCE.getFeatureManager().getFeature(HudFeature.class).get().isEnabled()
         && Melonity.INSTANCE.getFeatureManager().getFeature(HudFeature.class).get().getModeSetting().getMode().contains("Hotbar")) {
         callbackInfo.cancel();
      }
   }
}
