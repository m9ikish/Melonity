package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_239;
import net.minecraft.class_310;
import net.minecraft.class_320;
import net.minecraft.class_437;
import net.minecraft.class_310.class_5859;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IMinecraft;
import ru.melonity.feature.features.modules.render.HudFeature;
import ru.melonity.feature.features.modules.render.hud.WatermarkView;
import ru.melonity.feature.features.modules.render.particles.AnimationUtils;
import ru.melonity.ui.drag.Drag;
import ru.melonity.util.event.events.DoAttackEvent;
import ru.melonity.util.event.events.MiddleClickEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_310.class})
public class MinecraftClientMixin implements IMinecraft {
   @Shadow
   @Mutable
   class_320 field_1726;
   @Shadow
   @Nullable
   public class_239 field_1765;

   @Shadow
   private void method_1583() {
   }

   @Override
   public void tryUseItem() {
      this.method_1583();
   }

   @Inject(
      method = {"isMultiplayerEnabled"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void allowsMultiplayer(CallbackInfoReturnable<Boolean> cir) {
      cir.setReturnValue(true);
   }

   @Inject(
      method = {"isRealmsEnabled"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void allowsRealms(CallbackInfoReturnable<Boolean> cir) {
      cir.setReturnValue(true);
   }

   @Inject(
      method = {"getChatRestriction"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getChatStatus(CallbackInfoReturnable<class_5859> cir) {
      cir.setReturnValue(class_5859.field_28940);
   }

   @Inject(
      method = {"isDemo"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public final void isDemo(CallbackInfoReturnable<Boolean> cir) {
      cir.setReturnValue(false);
   }

   @Inject(
      method = {"doItemPick"},
      at = {@At("HEAD")}
   )
   private void pickBlock(CallbackInfo callbackInfo) {
      MiddleClickEvent eventMiddleClick = new MiddleClickEvent();
      Melonity.INSTANCE.getEventBus().fire(eventMiddleClick);
   }

   @Inject(
      method = {"render"},
      at = {@At(
         value = "FIELD",
         target = "Lnet/minecraft/client/MinecraftClient;fpsCounter:I",
         opcode = 181,
         ordinal = 0
      )}
   )
   private void render(boolean tick, CallbackInfo callbackInfo) {
      if (Melonity.INSTANCE.getFeatureManager() != null && Melonity.INSTANCE.getFeatureManager().getFeature(HudFeature.class).isPresent()) {
         WatermarkView view = Melonity.INSTANCE.getFeatureManager().getFeature(HudFeature.class).get().getWatermarkView();
         view.setTargetFps((float)class_310.method_1551().method_47599());
      }

      AnimationUtils.oldTime = System.currentTimeMillis();
   }

   @Inject(
      method = {"doAttack"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void doAttack(CallbackInfoReturnable<Boolean> cir) {
      DoAttackEvent event = new DoAttackEvent(this.field_1765);
      Melonity.INSTANCE.getEventBus().fire(event);
      if (event.isCancelled()) {
         cir.setReturnValue(false);
         cir.cancel();
      }
   }

   @Inject(
      method = {"setScreen"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screen/Screen;removed()V"
      )}
   )
   public void setScreen(class_437 screen, CallbackInfo callbackInfo) {
      Melonity.INSTANCE.getDragService().getDrags().forEach(Drag::release);
   }

   @Inject(
      method = {"getFramerateLimit"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getFramerateLimit(CallbackInfoReturnable<Integer> cir) {
      cir.setReturnValue(class_310.method_1551().method_22683().method_16000());
   }

   @Inject(
      method = {"onResolutionChanged"},
      at = {@At("HEAD")}
   )
   public void onResolutionChanged(CallbackInfo callbackInfo) {
   }

   @Override
   public void setUser(class_320 user) {
      this.field_1726 = user;
   }
}
