package ru.melonity.util.altmanager;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_320.class_321;

@Environment(EnvType.CLIENT)
public interface Profile {
   String getName();

   UUID getUUID();

   String getAccessToken();

   boolean login();

   class_321 getSessionType();

   ProfileType getProfileType();

   GameProfile getGameProfile();
}
