package ru.melonity.fabric.client.mixin;

import java.util.Iterator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import net.minecraft.class_312;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.feature.features.modules.player.MiddleClickPearlFeature;
import ru.melonity.ui.drag.Drag;
import ru.melonity.ui.menu.clickui.Menu;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.event.events.KeyEvent;
import ru.melonity.util.event.events.LookEvent;
import ru.melonity.util.event.events.MiddleClickEvent;

@Environment(EnvType.CLIENT)
@Mixin({class_312.class})
public class MouseMixin {
   @Inject(
      method = {"onMouseButton"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/Window;getScaledWidth()I"
      )}
   )
   private void onMouseButton(long window, int button, int action, int mods, CallbackInfo callbackInfo) {
      if (action != 1) {
         Melonity.INSTANCE.getDragService().getDrags().stream().filter(Drag::canDraw).forEach(Drag::release);
      }
   }

   @Inject(
      method = {"onMouseButton"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onMouseButton_head(long window, int button, int action, int mods, CallbackInfo callbackInfo) {
      if (!(class_310.method_1551().field_1755 instanceof Menu)) {
         if (Melonity.INSTANCE.getFeatureManager() != null) {
            if (action == 1) {
               if (button == 2) {
                  MiddleClickPearlFeature middleClickPearl = null;
                  Feature conflictFeature = null;

                  label58:
                  for (Feature feature : Melonity.INSTANCE.getFeatureManager().getFeatures()) {
                     if (feature instanceof MiddleClickPearlFeature) {
                        middleClickPearl = (MiddleClickPearlFeature)feature;
                     } else {
                        Iterator var11 = feature.getMenuObject().getElementViews().iterator();

                        while (true) {
                           if (var11.hasNext()) {
                              MenuFeatureView view = (MenuFeatureView)var11.next();
                              if (!(view instanceof BindSetting bindSetting) || bindSetting.bind != 2) {
                                 continue;
                              }

                              conflictFeature = feature;
                           }

                           if (conflictFeature != null) {
                              break label58;
                           }
                           break;
                        }
                     }
                  }

                  if (middleClickPearl != null && middleClickPearl.isEnabled()) {
                     if (conflictFeature != null) {
                        Melonity.INSTANCE
                           .getNotificationManager()
                           .add(new Notification("Keybind conflict", "MOUSE3 в двух функциях одновременно", "feature-disabled.png"));
                     } else {
                        Melonity.INSTANCE.getEventBus().fire(new MiddleClickEvent());
                     }
                  }

                  if (conflictFeature != null) {
                     conflictFeature.setEnabled(!conflictFeature.isEnabled(), false);
                     return;
                  }
               } else {
                  Melonity.INSTANCE.getEventBus().fire(new KeyEvent(button));
               }
            }
         }
      }
   }

   @Redirect(
      method = {"updateMouse"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V"
      )
   )
   private void changeLookDirection(class_746 player, double yaw, double pitch) {
      LookEvent lookEvent = new LookEvent(yaw, pitch);
      if (Melonity.INSTANCE.getEventBus() != null) {
         Melonity.INSTANCE.getEventBus().fire(lookEvent);
      }

      if (!lookEvent.isCancelled()) {
         player.method_5872(lookEvent.yaw, lookEvent.pitch);
      }
   }
}
