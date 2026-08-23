package ru.melonity.util.altmanager.impl.crack;

import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_320.class_321;
import ru.melonity.util.altmanager.Profile;
import ru.melonity.util.altmanager.ProfileProvider;
import ru.melonity.util.altmanager.ProfileType;
import ru.melonity.util.altmanager.impl.SimpleProfile;

@Environment(EnvType.CLIENT)
public class CrackedProfileProvider implements ProfileProvider {
   @Override
   public Profile createProfile(String username, String password) {
      return new SimpleProfile(username, UUID.randomUUID(), "0", class_321.field_1990, ProfileType.CRACK);
   }
}
