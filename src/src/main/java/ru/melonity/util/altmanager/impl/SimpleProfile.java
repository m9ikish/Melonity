package ru.melonity.util.altmanager.impl;

import com.mojang.authlib.GameProfile;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import net.minecraft.class_320;
import net.minecraft.class_320.class_321;
import ru.melonity.fabric.client.model.IMinecraft;
import ru.melonity.util.altmanager.Profile;
import ru.melonity.util.altmanager.ProfileType;

@Environment(EnvType.CLIENT)
public class SimpleProfile implements Profile {
   private final String name;
   private final UUID uuid;
   private final String accessToken;
   private final class_321 sessionType;
   private final ProfileType profileType;

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public UUID getUUID() {
      return this.uuid;
   }

   @Override
   public boolean login() {
      class_320 local1_0 = new class_320(this.name, this.uuid, this.accessToken, Optional.empty(), Optional.empty(), this.sessionType);
      ((IMinecraft)class_310.method_1551()).setUser(local1_0);
      return true;
   }

   @Override
   public String getAccessToken() {
      return this.accessToken;
   }

   @Override
   public class_321 getSessionType() {
      return this.sessionType;
   }

   @Override
   public ProfileType getProfileType() {
      return this.profileType;
   }

   @Override
   public GameProfile getGameProfile() {
      return new GameProfile(this.uuid, this.name);
   }

   @Generated
   public SimpleProfile(String name, UUID uuid, String accessToken, class_321 sessionType, ProfileType profileType) {
      this.name = name;
      this.uuid = uuid;
      this.accessToken = accessToken;
      this.sessionType = sessionType;
      this.profileType = profileType;
   }
}
