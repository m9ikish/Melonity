package ru.melonity.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.sound.SoundManager;
@Environment(EnvType.CLIENT)
public class MelonityFabricClient implements ClientModInitializer {
   @Override
   public void onInitializeClient() {
      SoundManager.init();
   }
}
