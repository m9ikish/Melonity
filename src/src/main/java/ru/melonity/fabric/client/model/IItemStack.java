package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface IItemStack {
   int price();

   void setPrice(int var1);

   boolean autosell();

   void setAutosell(boolean var1);

   boolean bestPrice();

   void setBestPrice(boolean var1);
}
