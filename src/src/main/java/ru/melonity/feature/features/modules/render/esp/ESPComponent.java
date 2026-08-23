package ru.melonity.feature.features.modules.render.esp;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import org.joml.Vector4d;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public interface ESPComponent {
   void render(class_1657 var1, Vector4d var2, class_4587 var3, class_332 var4, RenderSystem var5);

   String name();
}
