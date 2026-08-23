package ru.melonity.util.minecraft;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import net.minecraft.class_746;

@Environment(EnvType.CLIENT)
public class MinecraftInstance {
   public static class_310 mc = class_310.method_1551();

   public class_746 player() {
      return mc.field_1724;
   }
}
