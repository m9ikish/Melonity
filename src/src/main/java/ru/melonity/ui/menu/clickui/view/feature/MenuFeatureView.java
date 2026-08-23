package ru.melonity.ui.menu.clickui.view.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.language.LanguageService;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public interface MenuFeatureView {
   void insert(MenuStyle var1, float var2, float var3, float var4, double var5, double var7, RenderSystem var9, class_4587 var10, class_332 var11);

   void click(float var1, float var2, float var3, double var4, double var6, int var8);

   boolean key(int var1, int var2, int var3);

   void release();

   float getHeight();

   void resetAnimations();

   default LanguageService getLangService() {
      return Melonity.INSTANCE.languageService;
   }
}
