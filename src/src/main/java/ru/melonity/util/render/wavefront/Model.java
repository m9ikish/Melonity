package ru.melonity.util.render.wavefront;

import java.io.InputStream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.render.texture.TextureSource;
import ru.melonity.util.render.texture.WavefrontTexture;

@Environment(EnvType.CLIENT)
public record Model(Wavefront wavefront, WavefrontTexture texture, WavefrontTexture texture2) {
   public Model(String wavefrontPath, String wavefrontTexturePath) {
      this(
         Wavefront.fromPath(wavefrontPath),
         new WavefrontTexture(TextureSource.fromFile(wavefrontTexturePath)),
         new WavefrontTexture(TextureSource.fromFile(wavefrontTexturePath))
      );
   }

   // One PNG feeds both WavefrontTextures here, and the original handed the SAME InputStream to
   // two lazy TextureSources: whichever decoded second would find the stream already drained (now
   // also closed) and ImageIO.read would hand back null, NPEing in argbImage(). Only texture() is
   // read today so it never fired, but the trap was armed. Read the bytes once and give each
   // texture its own re-decodable source. The byte[] hop also frees the jar inflater immediately.
   public Model(InputStream modelInputStream, InputStream textureInputStream) {
      this(modelInputStream, TextureSource.drain(textureInputStream));
   }

   private Model(InputStream modelInputStream, byte[] textureBytes) {
      this(
         Wavefront.fromInputStream(modelInputStream),
         new WavefrontTexture(TextureSource.fromBytes(textureBytes)),
         new WavefrontTexture(TextureSource.fromBytes(textureBytes))
      );
   }

   public Model(InputStream modelInputStream, InputStream textureInputStream, InputStream texture2InputStream) {
      this(
         Wavefront.fromInputStream(modelInputStream),
         new WavefrontTexture(TextureSource.fromInputStream(textureInputStream)),
         new WavefrontTexture(TextureSource.fromInputStream(texture2InputStream))
      );
   }
}
