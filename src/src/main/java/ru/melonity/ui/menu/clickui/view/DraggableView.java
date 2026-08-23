package ru.melonity.ui.menu.clickui.view;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1041;
import net.minecraft.class_310;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public abstract class DraggableView {
   protected final float width;
   protected final float height;
   protected float x;
   protected float y;
   private float prevX;
   private float prevY;
   private boolean drag = false;

   public void insert(double mouseX, double mouseY, RenderSystem renderSystem) {
      if (this.drag) {
         this.x = (float)mouseX - this.prevX;
         this.y = (float)mouseY - this.prevY;
         class_1041 local6_0 = class_310.method_1551().method_22683();
         if (this.x < 0.0F) {
            this.x = 0.0F;
         }

         if (this.y < 0.0F) {
            this.y = 0.0F;
         }

         if (this.x > (float)local6_0.method_4486() - this.width) {
            this.x = (float)local6_0.method_4486() - this.width;
         }

         if (this.y > (float)local6_0.method_4502() - this.height) {
            this.y = (float)local6_0.method_4502() - this.height;
         }
      }
   }

   public void click(double mouseX, double mouseY, int mouseButton) {
      if (MathUtil.isMouseHoveringRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, mouseX, mouseY)) {
         this.prevX = (float)mouseX - this.x;
         this.prevY = (float)mouseY - this.y;
         this.drag = true;
      }
   }

   public void release() {
      this.drag = false;
   }

   @Generated
   public DraggableView(float width, float height) {
      this.width = width;
      this.height = height;
   }

   @Generated
   public float getWidth() {
      return this.width;
   }

   @Generated
   public float getHeight() {
      return this.height;
   }

   @Generated
   public float getX() {
      return this.x;
   }

   @Generated
   public float getY() {
      return this.y;
   }

   @Generated
   public float getPrevX() {
      return this.prevX;
   }

   @Generated
   public float getPrevY() {
      return this.prevY;
   }

   @Generated
   public boolean isDrag() {
      return this.drag;
   }

   @Generated
   public void setX(float x) {
      this.x = x;
   }

   @Generated
   public void setY(float y) {
      this.y = y;
   }

   @Generated
   public void setPrevX(float prevX) {
      this.prevX = prevX;
   }

   @Generated
   public void setPrevY(float prevY) {
      this.prevY = prevY;
   }

   @Generated
   public void setDrag(boolean drag) {
      this.drag = drag;
   }
}
