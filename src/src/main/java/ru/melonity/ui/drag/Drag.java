package ru.melonity.ui.drag;

import java.util.function.Supplier;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.Melonity;
import ru.melonity.util.math.MathUtil;

@Environment(EnvType.CLIENT)
public class Drag {
   private final int id;
   private float x;
   private float y;
   private float width;
   private float height;
   private final Supplier<Boolean> canDraw;
   private final DragPositioner positioner = new DragPositioner(this);

   public Drag(int id, float x, float y, float width, float height, Supplier<Boolean> canDraw) {
      this.id = id;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.canDraw = canDraw;
      Melonity.INSTANCE.getDragService().registerDrag(this);
   }

   public Drag(int id, float x, float y, float width, float height) {
      this(id, x, y, width, height, () -> true);
   }

   public boolean draw(double mouseX, double mouseY) {
      return this.positioner.draw(mouseX, mouseY);
   }

   public boolean hovering(double mouseX, double mouseY) {
      return MathUtil.isMouseHoveringRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, mouseX, mouseY);
   }

   public void click(double mouseX, double mouseY) {
      this.positioner.click(mouseX, mouseY);
   }

   public void release() {
      this.positioner.release();
   }

   public boolean canDraw() {
      return this.canDraw.get();
   }

   public boolean isDragging() {
      return this.positioner.isDragging();
   }

   @Generated
   public int getId() {
      return this.id;
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
   public float getWidth() {
      return this.width;
   }

   @Generated
   public float getHeight() {
      return this.height;
   }

   @Generated
   public Supplier<Boolean> getCanDraw() {
      return this.canDraw;
   }

   @Generated
   public DragPositioner getPositioner() {
      return this.positioner;
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
   public void setWidth(float width) {
      this.width = width;
   }

   @Generated
   public void setHeight(float height) {
      this.height = height;
   }
}
