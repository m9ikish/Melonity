package ru.melonity.ui.components.view;

import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import org.jetbrains.annotations.NotNull;
import ru.melonity.util.render.RenderSystem;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\f\b&\u0018\u00002\u00020\u0001B\u001d\b\u0007\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0002¢\u0006\u0004\b\u0005\u0010\u0006J?\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000eH&¢\u0006\u0004\b\u0011\u0010\u0012J7\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0013H&¢\u0006\u0004\b\u0015\u0010\u0016J'\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0013H&¢\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u001d\u001a\u00020\u0010H&¢\u0006\u0004\b\u001d\u0010\u001eR\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\u0003\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\"\u0010\u0004\u001a\u00020\u00028\u0016@\u0016X\u0096\u000e¢\u0006\u0012\n\u0004\b\u0004\u0010\u001f\u001a\u0004\b$\u0010!\"\u0004\b%\u0010#¨\u0006&"},
   d2 = {"Lru/melonity/ui/components/view/View;", "", "", "width", "height", "<init>", "(FF)V", "x", "y", "", "mouseX", "mouseY", "Lru/melonity/util/render/RenderSystem;", "renderSystem", "Lnet/minecraft/class_4587;", "poseStack", "", "insert", "(FFDDLru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "", "mouseButton", "click", "(FFDDI)V", "keyCode", "scanCode", "modifiers", "", "key", "(III)Z", "release", "()V", "F", "getWidth", "()F", "setWidth", "(F)V", "getHeight", "setHeight", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public abstract class View {
   private float width;
   private float height;

   @JvmOverloads
   public View(float width, float height) {
      this.width = width;
      this.height = height;
   }

   public View(float width, float height, int mask, DefaultConstructorMarker marker) {
      this((mask & 1) != 0 ? 0.0F : width, (mask & 2) != 0 ? 0.0F : height);
   }

   public final float getWidth() {
      return this.width;
   }

   public final void setWidth(float value) {
      this.width = value;
   }

   public float getHeight() {
      return this.height;
   }

   public void setHeight(float value) {
      this.height = value;
   }

   public abstract void insert(float var1, float var2, double var3, double var5, @NotNull RenderSystem var7, @NotNull class_4587 var8);

   public abstract void click(float var1, float var2, double var3, double var5, int var7);

   public abstract boolean key(int var1, int var2, int var3);

   public abstract void release();

   @JvmOverloads
   public View(float width) {
      this(width, 0.0F, 2, null);
   }

   @JvmOverloads
   public View() {
      this(0.0F, 0.0F, 3, null);
   }
}
