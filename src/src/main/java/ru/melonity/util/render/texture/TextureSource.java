package ru.melonity.util.render.texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface TextureSource {
   BufferedImage image();

   default BufferedImage argbImage() {
      BufferedImage image = this.image();
      if (image.getType() == 2) {
         return image;
      } else {
         BufferedImage argbImage = new BufferedImage(image.getWidth(), image.getHeight(), 2);
         Graphics graphics = argbImage.createGraphics();
         graphics.drawImage(image, 0, 0, null);
         graphics.dispose();
         return argbImage;
      }
   }

   static TextureSource fromFile(String path) {
      return () -> {
         try {
            return ImageIO.read(new File(path));
         } catch (IOException var2) {
            throw new RuntimeException(var2);
         }
      };
   }

   // The original captured the InputStream in the lambda and never closed it, so every
   // WavefrontTexture pinned a live zip InflaterInputStream (native inflater + ~64KB buffer)
   // over the mod jar for the whole process - they stay strongly reachable from the features'
   // static Model lists, so the Cleaner never reclaims them. Closing in try-with-resources
   // releases the inflater on first render; the stream is only ever read once anyway.
   static TextureSource fromInputStream(InputStream inputStream) {
      return () -> {
         try (InputStream stream = inputStream) {
            return ImageIO.read(stream);
         } catch (IOException var3) {
            throw new RuntimeException(var3);
         }
      };
   }

   // Byte-backed source: unlike fromInputStream this can be decoded any number of times, so it
   // is safe when one PNG feeds two WavefrontTextures (Model's 2-arg ctor) or when
   // WavefrontTexture.reload() drops the cached GL id and has to decode the image again.
   static TextureSource fromBytes(byte[] bytes) {
      return () -> {
         try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
         } catch (IOException var2) {
            throw new RuntimeException(var2);
         }
      };
   }

   // Consumes a jar-entry stream exactly once and closes it, so the caller can hand the bytes
   // to fromBytes instead of keeping the inflater alive for the lifetime of the process.
   static byte[] drain(InputStream inputStream) {
      try (InputStream stream = inputStream) {
         return stream.readAllBytes();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }
}
