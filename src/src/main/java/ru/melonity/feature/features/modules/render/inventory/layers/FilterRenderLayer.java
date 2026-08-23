package ru.melonity.feature.features.modules.render.inventory.layers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1703;
import net.minecraft.class_4587;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public interface FilterRenderLayer {
   void render(float var1, float var2, int var3, int var4, MenuStyle var5, RenderSystem var6, class_4587 var7);

   boolean mouseClicked(class_1703 var1, float var2, float var3, double var4, double var6);
}
