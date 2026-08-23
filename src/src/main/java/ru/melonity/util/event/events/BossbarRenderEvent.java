package ru.melonity.util.event.events;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import ru.melonity.util.event.CancelableEvent;

@Environment(EnvType.CLIENT)
public class BossbarRenderEvent extends CancelableEvent {
   private final class_4587 matrixStack;

   @Generated
   public BossbarRenderEvent(class_4587 matrixStack) {
      this.matrixStack = matrixStack;
   }

   @Generated
   public class_4587 matrixStack() {
      return this.matrixStack;
   }
}
