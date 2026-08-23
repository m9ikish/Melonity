package ru.melonity.fabric.client.mixin;

import java.awt.Color;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_124;
import net.minecraft.class_156;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_465;
import net.minecraft.class_490;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.misc.AuctionHelperFeature;
import ru.melonity.feature.features.modules.misc.ItemScrollerFeature;
import ru.melonity.feature.features.modules.misc.PotionsHighlightFeature;
import ru.melonity.feature.features.modules.render.CustomInventory;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.event.events.RenderOverlayInContainerEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_465.class})
public class HandledScreenMixin {
   @Unique
   private StateAnimation animation = new StateAnimation();
   @Unique
   private FrameWeightCalculator frameWeightCalculator = FrameWeightCalculator.milliseconds(300L);
   @Unique
   private boolean closing = false;
   @Shadow
   private class_1799 field_2782;
   @Final
   @Shadow
   protected class_1703 field_2797;
   @Shadow
   @Final
   protected Set<class_1735> field_2793;
   @Shadow
   protected class_1735 field_2787;
   @Shadow
   private int field_2803;
   @Shadow
   protected boolean field_2794;
   @Shadow
   private boolean field_2789;
   @Shadow
   private class_1799 field_2785;
   @Shadow
   private long field_2795;
   @Shadow
   private class_1735 field_2802;
   @Shadow
   private int field_2784;
   @Shadow
   private int field_2796;

   @Shadow
   protected void method_2389(class_332 context, float delta, int mouseX, int mouseY) {
   }

   @Shadow
   private void method_2382(class_332 context, class_1799 stack, int x, int y, String amountText) {
   }

   @Shadow
   public void method_25420(class_332 context, int mouseX, int mouseY, float delta) {
   }

   @Shadow
   private boolean method_2387(class_1735 slot, double pointX, double pointY) {
      return false;
   }

   @Shadow
   protected void method_2383(class_1735 slot, int slotId, int button, class_1713 actionType) {
   }

   @Inject(
      method = {"render"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawSlotHighlight(Lnet/minecraft/client/gui/DrawContext;III)V",
         ordinal = 0
      )},
      cancellable = true
   )
   public void render_drawSlotHighlight(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
      if ((class_437)(Object)this instanceof class_490 && Melonity.INSTANCE.featureManager.customInventory.isEnabled()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"render"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/gui/DrawContext;IIF)V",
         ordinal = 0
      )},
      cancellable = true
   )
   public void render_superRender(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
      if ((class_437)(Object)this instanceof class_490 && Melonity.INSTANCE.featureManager.customInventory.isEnabled()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"close"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void close(CallbackInfo callbackInfo) {
      this.closing = true;
      callbackInfo.cancel();
   }

   @Inject(
      method = {"render"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screen/Screen;render(Lnet/minecraft/client/gui/DrawContext;IIF)V",
         shift = Shift.AFTER
      )}
   )
   public void render_return(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
      Melonity.INSTANCE.getEventBus().fire(new RenderOverlayInContainerEvent(context.method_51448(), context));
   }

   @Inject(
      method = {"drawSlot"},
      at = {@At("HEAD")}
   )
   protected void drawSlot(class_332 context, class_1735 slot, CallbackInfo callbackInfo) {
      if (slot.method_7681()) {
         AuctionHelperFeature auc = Melonity.INSTANCE.getFeatureManager().getFeature(AuctionHelperFeature.class).get();
         if (Melonity.INSTANCE.getFeatureManager().getFeature(PotionsHighlightFeature.class).get().isEnabled()) {
            Melonity.INSTANCE
               .getFeatureManager()
               .getFeature(PotionsHighlightFeature.class)
               .get()
               .potionSlotHighlight(slot.method_7677(), (float)slot.field_7873, (float)slot.field_7872, 16.0F, 0.0F, context.method_51448());
         }

         if (auc.isEnabled() && class_310.method_1551().field_1724 != null) {
            int slotIndex = -1;

            for (int i = 0; i < auc.getCheapestSlotIDs().length; i++) {
               if (slot.field_7874 == auc.getCheapestSlotIDs()[i]) {
                  slotIndex = i;
                  break;
               }
            }

            if (slotIndex >= 0) {
               Color color = switch (slotIndex) {
                  case 0 -> new Color(0, 255, 0, 144);
                  case 1 -> new Color(255, 255, 0, 144);
                  case 2 -> new Color(255, 0, 0, 144);
                  default -> null;
               };
               if (color != null) {
                  auc.auctionSlotHighlight(slot.method_7677(), (float)slot.field_7873, (float)slot.field_7872, 16.0F, 2.0F, context.method_51448(), color);
               }
            }
         }
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   public void render(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
      if (this.animation == null) {
         this.animation = new StateAnimation();
      }

      if (this.frameWeightCalculator == null) {
         this.frameWeightCalculator = FrameWeightCalculator.milliseconds(300L);
      }

      this.animation.state(!this.closing);
      this.animation.update(this.frameWeightCalculator.elapsedUnits());
      float val = this.animation.animation();
      class_4587 matrixStack = context.method_51448();
      class_310 minecraft = class_310.method_1551();
      class_465<?> handledScreen = (class_465<?>)(Object)this;
      CustomInventory customInventory = Melonity.INSTANCE.featureManager.customInventory;
      if (handledScreen instanceof class_490 && customInventory.isEnabled()) {
         this.method_25420(context, mouseX, mouseY, delta);
      }

      boolean needClose = val <= 0.1F || !(handledScreen instanceof class_490) || !customInventory.isEnabled();
      if (this.closing && needClose) {
         this.animation.reset();
         minecraft.field_1724.method_7346();
         minecraft.method_1507(null);
      }

      for (int k = 0; k < this.field_2797.field_7761.size(); k++) {
         class_1735 slot = (class_1735)this.field_2797.field_7761.get(k);
         if (this.method_2387(slot, (double)mouseX, (double)mouseY) && slot.method_7682()) {
            ItemScrollerFeature itemScroller = Melonity.INSTANCE.getFeatureManager().getFeature(ItemScrollerFeature.class).get();
            if (itemScroller.isEnabled()
               && GLFW.glfwGetMouseButton(class_310.method_1551().method_22683().method_4490(), 0) == 1
               && GLFW.glfwGetKey(class_310.method_1551().method_22683().method_4490(), 340) == 1
               && slot.method_7677().method_7909() != class_1802.field_8162) {
               this.method_2383(slot, slot.method_34266(), 0, class_1713.field_7794);
            }
         }
      }

      if (handledScreen instanceof class_490 && customInventory.isEnabled()) {
         matrixStack.method_22903();
         matrixStack.method_46416((float)minecraft.method_22683().method_4480() / 4.0F, (float)minecraft.method_22683().method_4507() / 4.0F, 0.0F);
         matrixStack.method_22905(val, val, val);
         matrixStack.method_46416(-((float)minecraft.method_22683().method_4480() / 4.0F), -((float)minecraft.method_22683().method_4507() / 4.0F), 0.0F);
         float scaleFactor = 2.0F;
         float x = (float)class_310.method_1551().method_22683().method_4480() / scaleFactor / 2.0F - 90.0F;
         float y = (float)class_310.method_1551().method_22683().method_4507() / scaleFactor / 2.0F - 85.0F;
         customInventory.view().insert(context, handledScreen, minecraft, mouseX, mouseY);
         customInventory.filterView().insert(x, y, mouseX, mouseY, matrixStack);
         class_1799 itemStack = this.field_2782.method_7960() ? this.field_2797.method_34255() : this.field_2782;
         if (!itemStack.method_7960()) {
            int l = this.field_2782.method_7960() ? 8 : 16;
            String string = null;
            if (!this.field_2782.method_7960() && this.field_2789) {
               itemStack = itemStack.method_46651(class_3532.method_15386((float)itemStack.method_7947() / 2.0F));
            } else if (this.field_2794 && this.field_2793.size() > 1) {
               itemStack = itemStack.method_46651(this.field_2803);
               if (itemStack.method_7960()) {
                  string = class_124.field_1054 + "0";
               }
            }

            this.method_2382(context, itemStack, mouseX - 3, mouseY - l + 3, string);
         }

         if (!this.field_2785.method_7960()) {
            float f = (float)(class_156.method_658() - this.field_2795) / 100.0F;
            if (f >= 1.0F) {
               f = 1.0F;
               this.field_2785 = class_1799.field_8037;
            }

            int l = this.field_2802.field_7873 - this.field_2784;
            int m = this.field_2802.field_7872 - this.field_2796;
            int o = this.field_2784 + (int)((float)l * f);
            int p = this.field_2796 + (int)((float)m * f);
            this.method_2382(context, this.field_2785, o, p, null);
         }

         matrixStack.method_22909();
         if (customInventory.view().hoveredSlot() != -1) {
            this.field_2787 = this.field_2797.method_7611(customInventory.view().hoveredSlot());
         } else {
            this.field_2787 = null;
         }
      }
   }

   @Inject(
      method = {"getSlotAt"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void getSlotAt(double x, double y, CallbackInfoReturnable<class_1735> cir) {
      CustomInventory customInventory = Melonity.INSTANCE.featureManager.customInventory;
      if ((class_437)(Object)this instanceof class_490 && customInventory.isEnabled()) {
         cir.setReturnValue(customInventory.view().hoveredSlot() == -1 ? null : this.field_2797.method_7611(customInventory.view().hoveredSlot()));
         cir.cancel();
      }
   }

   @Inject(
      method = {"mouseClicked"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
      if (Melonity.INSTANCE.featureManager.customInventory.isEnabled()
         && Melonity.INSTANCE.featureManager.customInventory.filterView().mouseClicked(this.field_2797, mouseX, mouseY)) {
         cir.setReturnValue(true);
      }
   }

   @Inject(
      method = {"keyPressed"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
      if (Melonity.INSTANCE.featureManager.customInventory.isEnabled() && Melonity.INSTANCE.featureManager.customInventory.view().keyPressed(keyCode, scanCode)
         )
       {
         cir.setReturnValue(true);
      }
   }

   @Inject(
      method = {"drawForeground"},
      at = {@At("HEAD")},
      cancellable = true
   )
   protected void drawForeground(class_332 context, int mouseX, int mouseY, CallbackInfo callbackInfo) {
      if ((class_437)(Object)this instanceof class_490 && Melonity.INSTANCE.featureManager.customInventory.isEnabled()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderBackground"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderBackground_inj(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
      class_437 screen = (class_437)(Object)this;
      screen.method_52752(context);
      RotationManager.INSTANCE.setInInventory(true);
      if (!(screen instanceof class_490) || !Melonity.INSTANCE.featureManager.customInventory.isEnabled()) {
         this.method_2389(context, delta, mouseX, mouseY);
      }

      RotationManager.INSTANCE.setInInventory(false);
      callbackInfo.cancel();
   }
}
