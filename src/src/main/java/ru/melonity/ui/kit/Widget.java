package ru.melonity.ui.kit;

import lombok.SneakyThrows;
import java.lang.reflect.ParameterizedType;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Vector2f;
import ru.melonity.ui.kit.render.RenderEngine;

@Environment(EnvType.CLIENT)
public abstract class Widget<T extends State> {
   protected final Vector2f size;
   private final T state;

   @SneakyThrows
   public Widget(Vector2f size) {
      this.size = size;
      Class<T> local2_0 = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
      this.state = local2_0.getDeclaredConstructor().newInstance();
   }

   public abstract void render(Vector2f var1, RenderEngine var2);

   @Generated
   public Vector2f size() {
      return this.size;
   }

   @Generated
   public T state() {
      return this.state;
   }
}
