package ru.melonity.util.altmanager.impl;

import lombok.SneakyThrows;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_320.class_321;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.melonity.util.altmanager.AltService;
import ru.melonity.util.altmanager.Profile;
import ru.melonity.util.altmanager.ProfileProvider;
import ru.melonity.util.altmanager.ProfileType;

@Environment(EnvType.CLIENT)
public class AltManager implements AltService {
   private static final File CONFIGS_DIR = new File("C:/Melonity Minecraft", "alts");
   private final Map<ProfileType, ProfileProvider> providers = Maps.newLinkedHashMap();
   private final List<Profile> profiles = Lists.newLinkedList();

   @Override
   public List<Profile> getProfiles() {
      return this.profiles;
   }

   @Override
   public void removeProfile(Profile profile) {
      boolean local2_0 = this.profiles.removeIf(profile1 -> profile1.getName().equals(profile.getName()));
      System.out.println(local2_0);
   }

   @Override
   public void registerProvider(ProfileType profileType, ProfileProvider provider) {
      this.providers.put(profileType, provider);
   }

   @Override
   public Optional<ProfileProvider> getProvider(ProfileType profileType) {
      return this.providers.containsKey(profileType) ? Optional.of(this.providers.get(profileType)) : Optional.empty();
   }

   @Override
   public Profile createProfile(ProfileType profileType, String username, String password) {
      return this.getProvider(profileType).map(profileProvider -> profileProvider.createProfile(username, password)).orElse(null);
   }

   @Override
   @SneakyThrows
   public void save() {
      File local1_2 = new File(CONFIGS_DIR, "alts.json");
      JSONObject local2_3 = new JSONObject();
      JSONArray local3_4 = new JSONArray();

      for (Profile local5_1 : this.profiles) {
         JSONObject local6_0 = new JSONObject();
         local6_0.put("name", local5_1.getName());
         local6_0.put("profiletype", local5_1.getProfileType().name());
         local6_0.put("accesstoken", local5_1.getAccessToken());
         local6_0.put("uuid", local5_1.getUUID().toString());
         local6_0.put("sessiontype", local5_1.getSessionType().name());
         local6_0.put("gpname", local5_1.getGameProfile().getName());
         local6_0.put("gpid", local5_1.getGameProfile().getId().toString());
         local3_4.put(local6_0);
      }

      local2_3.put("profiles", local3_4);
      FileUtils.deleteQuietly(local1_2);
      FileUtils.writeStringToFile(local1_2, local2_3.toString(4));
   }

   @Override
   public void load() {
      File local1_7 = new File(CONFIGS_DIR, "alts.json");
      if (local1_7.exists()) {
         try {
            JSONObject local2_3 = new JSONObject(FileUtils.readFileToString(local1_7, StandardCharsets.UTF_8));
            if (!local2_3.has("profiles")) {
               return;
            }

            JSONArray local3_4 = local2_3.getJSONArray("profiles");
            if (local3_4.isEmpty()) {
               return;
            }

            for (Object local5_2 : local3_4) {
               JSONObject local6_1 = (JSONObject)local5_2;

               try {
                  Profile local7_0 = new SimpleProfile(
                     local6_1.getString("name"),
                     UUID.fromString(local6_1.getString("uuid")),
                     local6_1.getString("accesstoken"),
                     class_321.method_1679(local6_1.getString("sessiontype")),
                     ProfileType.fromName(local6_1.getString("profiletype"))
                  );
                  this.profiles.add(local7_0);
               } catch (Throwable var9) {
               }
            }
         } catch (Exception var10) {
            try {
               FileUtils.delete(local1_7);
            } catch (IOException var8) {
               throw new RuntimeException(var8);
            }
         }
      }
   }
}
