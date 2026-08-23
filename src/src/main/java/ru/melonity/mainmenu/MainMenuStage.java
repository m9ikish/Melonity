package ru.melonity.mainmenu;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public interface MainMenuStage {
   void render(float var1, float var2, float var3, double var4, double var6, RenderSystem var8, class_4587 var9);

   void click(float var1, float var2, float var3, double var4, double var6, int var8);

   void key(int var1, int var2, int var3);

   void scroll(float var1, float var2, float var3, double var4, double var6, double var8);

   float getHeight();
}
