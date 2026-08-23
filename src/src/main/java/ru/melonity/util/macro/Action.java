package ru.melonity.util.macro;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import org.json.JSONObject;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public interface Action extends Runnable {
   String getName();

   JSONObject toJson();

   ActionCategory getCategory();

   float getDelay();

   void renderSettings(float var1, float var2, float var3, float var4, int var5, int var6, RenderSystem var7, class_4587 var8);

   void click(float var1, float var2, double var3, double var5, int var7);

   boolean key(int var1, int var2, int var3);

   void release();

   float getOffset();

   StateAnimation getAnimation();

   FrameWeightCalculator getFwc();
}
