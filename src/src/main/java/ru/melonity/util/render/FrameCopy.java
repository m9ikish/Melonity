package ru.melonity.util.render;

import java.nio.ByteBuffer;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_276;
import net.minecraft.class_310;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class FrameCopy {
   private static int textureId = -1;
   private static int lastWidth = -1;
   private static int lastHeight = -1;

   public static void updateCopy() {
      class_310 local0_0 = class_310.method_1551();
      class_276 local1_1 = local0_0.method_1522();
      if (local1_1 != null) {
         int local2_2 = local1_1.field_1482;
         int local3_3 = local1_1.field_1481;
         if (textureId == -1 || local2_2 != lastWidth || local3_3 != lastHeight) {
            if (textureId != -1) {
               com.mojang.blaze3d.systems.RenderSystem.deleteTexture(textureId);
            }

            textureId = GL11.glGenTextures();
            com.mojang.blaze3d.systems.RenderSystem.bindTexture(textureId);
            GL11.glTexImage2D(3553, 0, 32856, local2_2, local3_3, 0, 6408, 5121, (ByteBuffer)null);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            com.mojang.blaze3d.systems.RenderSystem.texParameter(3553, 10242, 33071);
            com.mojang.blaze3d.systems.RenderSystem.texParameter(3553, 10243, 33071);
            lastWidth = local2_2;
            lastHeight = local3_3;
         }

         local1_1.method_35610();
         com.mojang.blaze3d.systems.RenderSystem.bindTexture(textureId);
         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, local2_2, local3_3);
         com.mojang.blaze3d.systems.RenderSystem.bindTexture(0);
         local1_1.method_1235(true);
      }
   }

   @Generated
   public static int getTextureId() {
      return textureId;
   }
}
