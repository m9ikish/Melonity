package ru.melonity.ui.components.view;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public abstract class StyledView {
   protected float width;
   protected float height;

   public abstract void insert(MenuStyle var1, float var2, float var3, double var4, double var6, RenderSystem var8, class_4587 var9);

   public abstract void click(float var1, float var2, double var3, double var5, int var7);

   public abstract boolean key(int var1, int var2, int var3);

   public abstract void release();

   @Generated
   public StyledView(float width, float height) {
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
   public void setWidth(float width) {
      this.width = width;
   }

   @Generated
   public void setHeight(float height) {
      this.height = height;
   }
}
