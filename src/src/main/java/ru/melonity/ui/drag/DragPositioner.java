package ru.melonity.ui.drag;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1041;
import net.minecraft.class_310;
import ru.melonity.util.math.MathUtil;

@Environment(EnvType.CLIENT)
final class DragPositioner {
   final Drag drag;
   boolean dragging;
   float prevX;
   float prevY;
   float relativeX;
   float relativeY;
   int lastWindowWidth;
   int lastWindowHeight;

   public DragPositioner(Drag drag) {
      this.drag = drag;
      this.dragging = false;
      class_1041 local2_0 = class_310.method_1551().method_22683();
      this.lastWindowWidth = local2_0.method_4480();
      this.lastWindowHeight = local2_0.method_4507();
      this.updateRelativePosition();
   }

   private void updateRelativePosition() {
      class_1041 local1_0 = class_310.method_1551().method_22683();
      this.relativeX = this.drag.getX() / ((float)local1_0.method_4480() / 2.0F);
      this.relativeY = this.drag.getY() / ((float)local1_0.method_4507() / 2.0F);
   }

   private void checkAndHandleResize() {
      class_1041 local1_2 = class_310.method_1551().method_22683();
      int local2_3 = local1_2.method_4480();
      int local3_4 = local1_2.method_4507();
      if (local2_3 != this.lastWindowWidth || local3_4 != this.lastWindowHeight) {
         float local4_0 = (float)local2_3 / 2.0F;
         float local5_1 = (float)local3_4 / 2.0F;
         this.drag.setX(Math.max(0.0F, Math.min(this.relativeX * local4_0, local4_0 - this.drag.getWidth())));
         this.drag.setY(Math.max(0.0F, Math.min(this.relativeY * local5_1, local5_1 - this.drag.getHeight())));
         this.lastWindowWidth = local2_3;
         this.lastWindowHeight = local3_4;
      }
   }

   boolean draw(double mouseX, double mouseY) {
      this.checkAndHandleResize();
      class_1041 local5_7 = class_310.method_1551().method_22683();
      float local6_8 = (float)local5_7.method_4480() / 2.0F;
      float local7_9 = (float)local5_7.method_4507() / 2.0F;
      if (this.dragging) {
         float local8_0 = (float)mouseX - this.prevX;
         float local9_1 = (float)mouseY - this.prevY;
         float local10_2 = local6_8 / 2.0F;
         float local11_3 = local7_9 / 2.0F;
         float local12_4 = 30.0F;
         float local13_5 = this.drag.getWidth() / 2.0F;
         float local14_6 = this.drag.getHeight() / 2.0F;
         if (Math.abs(local8_0 + local13_5 - local10_2) <= local12_4) {
            local8_0 = local10_2 - local13_5;
         }

         if (Math.abs(local9_1 + local14_6 - local11_3) <= local12_4) {
            local9_1 = local11_3 - local14_6;
         }

         this.drag.setX(local8_0);
         this.drag.setY(local9_1);
         this.updateRelativePosition();
      }

      if (this.drag.getX() < 0.0F) {
         this.drag.setX(0.0F);
         this.updateRelativePosition();
      }

      if (this.drag.getY() < 0.0F) {
         this.drag.setY(0.0F);
         this.updateRelativePosition();
      }

      if (this.drag.getX() > local6_8 - this.drag.getWidth()) {
         this.drag.setX(local6_8 - this.drag.getWidth());
         this.updateRelativePosition();
      }

      if (this.drag.getY() > local7_9 - this.drag.getHeight()) {
         this.drag.setY(local7_9 - this.drag.getHeight());
         this.updateRelativePosition();
      }

      return MathUtil.isMouseHoveringRect(
         (double)this.drag.getX(), (double)this.drag.getY(), (double)this.drag.getWidth(), (double)this.drag.getHeight(), mouseX, mouseY
      );
   }

   void click(double mouseX, double mouseY) {
      if (MathUtil.isMouseHoveringRect(
         (double)this.drag.getX(), (double)this.drag.getY(), (double)this.drag.getWidth(), (double)this.drag.getHeight(), mouseX, mouseY
      )) {
         this.dragging = true;
         this.prevX = (float)mouseX - this.drag.getX();
         this.prevY = (float)mouseY - this.drag.getY();
      }
   }

   void release() {
      this.dragging = false;
      this.updateRelativePosition();
   }

   boolean isDragging() {
      return this.dragging;
   }
}
