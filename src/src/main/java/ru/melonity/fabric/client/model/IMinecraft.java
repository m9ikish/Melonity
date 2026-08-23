package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_320;

@Environment(EnvType.CLIENT)
public interface IMinecraft {
   void setUser(class_320 var1);

   void tryUseItem();
}
