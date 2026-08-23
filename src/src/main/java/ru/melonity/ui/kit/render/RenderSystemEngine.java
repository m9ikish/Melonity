package ru.melonity.ui.kit.render;

import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import org.joml.Vector2f;
import ru.melonity.Melonity;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class RenderSystemEngine implements RenderEngine {
   private final RenderSystem renderSystem;
   private float scaling = 1.0F;
   private final class_4587 matrixStack;

   public RenderSystemEngine(class_4587 matrixStack) {
      this.renderSystem = Melonity.INSTANCE.getRenderSystem();
      this.matrixStack = matrixStack;
   }

   @Override
   public void rect(Vector2f position, Vector2f size, float cornerRounding, Color color) {
      this.renderSystem.addRect(position.x, position.y, size.x * this.scaling, size.y * this.scaling, cornerRounding, color, this.matrixStack);
   }

   @Override
   public void image(class_2960 identifier, Vector2f position, Vector2f size, Color color) {
      this.renderSystem.addTexture(identifier, position.x, position.y, size.x * this.scaling, size.y * this.scaling, color, this.matrixStack);
   }

   @Override
   public void text(String text, GlyphData font, Vector2f position, Color color) {
      this.renderSystem.addText(font, text, position.x, position.y, color, this.matrixStack);
   }

   @Generated
   public float scaling() {
      return this.scaling;
   }

   @Generated
   public RenderSystemEngine scaling(float scaling) {
      this.scaling = scaling;
      return this;
   }
}
