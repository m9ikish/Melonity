package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Vector4d;

@Environment(EnvType.CLIENT)
public interface IPlayer {
   Vector4d espPosition();

   void setEspPosition(Vector4d var1);
}
