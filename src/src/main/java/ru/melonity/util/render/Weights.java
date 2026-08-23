package ru.melonity.util.render;

import com.google.common.collect.Maps;
import java.nio.FloatBuffer;
import java.util.Map;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_3532;
import org.lwjgl.BufferUtils;

@Environment(EnvType.CLIENT)
public final class Weights {
   private static final Map<Float, FloatBuffer> weights = Maps.newLinkedHashMap();

   public static FloatBuffer generateGaussWeights(float radius) {
      if (weights.containsKey(radius)) {
         return weights.get(radius);
      } else {
         FloatBuffer local1_1 = BufferUtils.createFloatBuffer(256);

         for (int local2_0 = 1; (float)local2_0 <= radius; local2_0++) {
            local1_1.put(calculateGaussianValue((float)local2_0, radius / 2.0F));
         }

         local1_1.rewind();
         weights.put(radius, local1_1);
         return generateGaussWeights(radius);
      }
   }

   private static float calculateGaussianValue(float inputValue, float multiplier) {
      float local2_0 = 1.0F / class_3532.method_15355((float) (Math.PI * 2) * multiplier * multiplier);
      return (float)((double)local2_0 * Math.exp((double)(-(inputValue * inputValue)) / (2.0 * (double)(multiplier * multiplier))));
   }

   @Generated
   private Weights() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
