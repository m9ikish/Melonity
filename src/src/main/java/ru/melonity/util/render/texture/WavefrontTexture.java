package ru.melonity.util.render.texture;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

@Environment(EnvType.CLIENT)
public class WavefrontTexture {
   private static final int MIPMAP_LEVEL = 4;
   private final TextureSource textureSource;
   private Integer texture;

   public void bindToSlot(int slot) {
      GL33.glActiveTexture(33984 + slot);
      GL33.glBindTexture(3553, this.texture());
   }

   public void reload() {
      GL33.glDeleteTextures(this.texture);
      this.texture = null;
   }

   public int texture() {
      return this.texture != null ? this.texture : (this.texture = this.buildTexture());
   }

   private int buildTexture() {
      BufferedImage local1_3 = this.textureSource.argbImage();
      int local2_4 = local1_3.getWidth();
      int local3_5 = local1_3.getHeight();
      ByteBuffer local4_6 = MemoryUtil.memAlloc(local2_4 * local3_5 * 4).order(ByteOrder.BIG_ENDIAN);

      for (int local5_2 = local3_5 - 1; local5_2 >= 0; local5_2--) {
         for (int local6_1 = 0; local6_1 < local2_4; local6_1++) {
            int local7_0 = local1_3.getRGB(local6_1, local5_2);
            local4_6.putInt(local7_0 << 8 | local7_0 >>> 24);
         }
      }

      int local5_7 = GL33.glGenTextures();
      GL33.glBindTexture(3553, local5_7);
      GL33.glTexParameteri(3553, 33084, 0);
      GL33.glTexParameteri(3553, 33085, MIPMAP_LEVEL);
      GL33.glTexParameteri(3553, 10241, 9987);
      GL33.glTexParameteri(3553, 10240, 9729);
      // The original never reset the pixel-store unpack state before uploading. buildTexture()
      // runs lazily mid-frame, so it inherits whatever class_1011 left behind - vanilla sets
      // SKIP_ROWS/SKIP_PIXELS/ROW_LENGTH when uploading sub-regions and never clears them, which
      // would make glTexImage2D read the wrong rows (skewed texture, or past the end of the buffer).
      GL33.glPixelStorei(GL33.GL_UNPACK_ROW_LENGTH, 0); // 3314
      GL33.glPixelStorei(GL33.GL_UNPACK_SKIP_ROWS, 0); // 3315
      GL33.glPixelStorei(GL33.GL_UNPACK_SKIP_PIXELS, 0); // 3316
      GL33.glTexImage2D(3553, 0, 6408, local2_4, local3_5, 0, 6408, 5121, local4_6.position(0));
      GL33.glGenerateMipmap(3553);
      MemoryUtil.memFree(local4_6);
      return local5_7;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         WavefrontTexture local2_0 = (WavefrontTexture)o;
         return Objects.equals(this.texture, local2_0.texture);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.texture);
   }

   @Generated
   public WavefrontTexture(TextureSource textureSource) {
      this.textureSource = textureSource;
   }
}
