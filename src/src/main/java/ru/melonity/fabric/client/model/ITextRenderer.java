package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_377;
import net.minecraft.class_382;
import net.minecraft.class_4588;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public interface ITextRenderer {
   class_377 fontStorage(class_2960 var1);

   boolean validateAdvance();

   void myDrawGlyph(
      class_382 var1,
      boolean var2,
      boolean var3,
      float var4,
      float var5,
      float var6,
      Matrix4f var7,
      class_4588 var8,
      float var9,
      float var10,
      float var11,
      float var12,
      int var13
   );
}
