package ru.melonity.util.minecraft;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_745;
import ru.melonity.fabric.client.model.IOtherClientPlayerEntity;

@Environment(EnvType.CLIENT)
public class ResolveUtil extends MinecraftInstance {
   public static void resolvePlayers() {
      for (class_1657 local1_0 : mc.field_1687.method_18456()) {
         if (local1_0 instanceof class_745) {
            ((IOtherClientPlayerEntity)local1_0).resolve();
         }
      }
   }

   public static void releaseResolver() {
      for (class_1657 local1_0 : mc.field_1687.method_18456()) {
         if (local1_0 instanceof class_745) {
            ((IOtherClientPlayerEntity)local1_0).releaseResolver();
         }
      }
   }
}
