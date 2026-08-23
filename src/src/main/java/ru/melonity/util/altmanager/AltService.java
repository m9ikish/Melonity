package ru.melonity.util.altmanager;

import java.util.List;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface AltService {
   List<Profile> getProfiles();

   void removeProfile(Profile var1);

   void registerProvider(ProfileType var1, ProfileProvider var2);

   Optional<ProfileProvider> getProvider(ProfileType var1);

   Profile createProfile(ProfileType var1, String var2, String var3);

   void save();

   void load();
}
