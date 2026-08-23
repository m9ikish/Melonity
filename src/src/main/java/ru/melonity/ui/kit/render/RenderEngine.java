package ru.melonity.ui.kit.render;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import org.joml.Vector2f;
import ru.melonity.util.font.GlyphData;

@Environment(EnvType.CLIENT)
public interface RenderEngine {
   void rect(Vector2f var1, Vector2f var2, float var3, Color var4);

   void image(class_2960 var1, Vector2f var2, Vector2f var3, Color var4);

   void text(String var1, GlyphData var2, Vector2f var3, Color var4);

   default void rect(Vector2f position, Vector2f size, Color color) {
      this.rect(position, size, 0.0F, color);
   }

   default void image(class_2960 identifier, Vector2f position, Vector2f size) {
      this.image(identifier, position, size, Color.WHITE);
   }

   default void text(String text, GlyphData font, Vector2f position) {
      this.text(text, font, position, Color.WHITE);
   }
}
