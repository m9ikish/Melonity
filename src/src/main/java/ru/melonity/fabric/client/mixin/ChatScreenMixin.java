package ru.melonity.fabric.client.mixin;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_408;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.melonity.Melonity;
import ru.melonity.feature.features.command.CommandExecutionResult;
import ru.melonity.ui.drag.Drag;
import ru.melonity.util.event.events.ChatSendEvent;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.ScaleUtils;

@Environment(EnvType.CLIENT)
@Mixin({class_408.class})
public class ChatScreenMixin {
   @Inject(
      method = {"mouseClicked"},
      at = {@At("HEAD")}
   )
   public void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
      Vector2i fixed = ScaleUtils.getMouseDouble(mouseX, mouseY);
      // The decompiler reassigned the parameters here; lambdas need effectively-final captures.
      double fixedX = fixed.x;
      double fixedY = fixed.y;
      Melonity.INSTANCE
         .getDragService()
         .getDrags()
         .stream()
         .filter(Drag::canDraw)
         .filter(
            drag -> MathUtil.isMouseHoveringRect((double)drag.getX(), (double)drag.getY(), (double)drag.getWidth(), (double)drag.getHeight(), fixedX, fixedY)
         )
         .findFirst()
         .ifPresent(drag -> drag.click(fixedX, fixedY));
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   public void render(class_332 graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
      Vector2i fixed = ScaleUtils.getMouseDouble((double)mouseX, (double)mouseY);
      // Same as mouseClicked: capture the corrected coordinates in effectively-final locals.
      int fixedX = fixed.x;
      int fixedY = fixed.y;
      Melonity.INSTANCE.getDragService().getDrags().stream().filter(Drag::canDraw).forEach(drag -> drag.draw((double)fixedX, (double)fixedY));
      int w = class_310.method_1551().method_22683().method_4480();
      int h = class_310.method_1551().method_22683().method_4507();
      float centerX = (float)w / 4.0F;
      float centerY = (float)h / 4.0F;
      float snap = 30.0F;
      float thickness = 0.5F;
      Color color = Color.WHITE;
      ScaleUtils.rescale(2.0);
      Melonity.INSTANCE.getDragService().getDrags().stream().filter(Drag::canDraw).filter(Drag::isDragging).findFirst().ifPresent(drag -> {
         float dragCenterX = drag.getX() + drag.getWidth() / 2.0F;
         float dragCenterY = drag.getY() + drag.getHeight() / 2.0F;
         if (Math.abs(dragCenterX - centerX) <= snap) {
            Melonity.INSTANCE.getRenderSystem().addRect(centerX - thickness / 2.0F, 0.0F, thickness, (float)h, color, graphics.method_51448());
         }

         if (Math.abs(dragCenterY - centerY) <= snap) {
            Melonity.INSTANCE.getRenderSystem().addRect(0.0F, centerY - thickness / 2.0F, (float)w, thickness, color, graphics.method_51448());
         }
      });
      ScaleUtils.rescaleMC();
   }

   @Inject(
      method = {"sendMessage"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void sendMessage(String message, boolean addToHistory, CallbackInfo callbackInfo) {
      Melonity.INSTANCE.getEventBus().fire(new ChatSendEvent(message));
      CommandExecutionResult result = Melonity.INSTANCE.getCommandService().execute(message);
      if (result != CommandExecutionResult.NONE) {
         class_310.method_1551().field_1705.method_1743().method_1803(message);
         callbackInfo.cancel();
      }
   }
}
