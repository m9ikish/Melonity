package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.mainmenu.MainMenuStage;

@Environment(EnvType.CLIENT)
public interface IMainMenuStage {
   MainMenuStage getStage();

   void setStage(MainMenuStage var1);
}
