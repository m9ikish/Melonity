package ru.melonity.util.altmanager.impl.mojang;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_320.class_321;
import org.json.JSONObject;
import ru.melonity.util.altmanager.Profile;
import ru.melonity.util.altmanager.ProfileProvider;
import ru.melonity.util.altmanager.ProfileType;
import ru.melonity.util.altmanager.impl.SimpleProfile;
import ru.melonity.util.java.IOUtil;

@Environment(EnvType.CLIENT)
public class MojangProfileProvider implements ProfileProvider {
   @Override
   public Profile createProfile(String username, String password) {
      UUID local3_4 = UUID.randomUUID();

      try {
         URL local4_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
         HttpURLConnection local5_1 = (HttpURLConnection)local4_0.openConnection();
         local3_4 = UUID.fromString(
            new JSONObject(local5_1.getInputStream()).getString("id").replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5")
         );
         local5_1.getInputStream().close();
         local5_1.disconnect();
         local4_0 = new URL("https://authserver.mojang.com/authenticate");
         local5_1 = (HttpURLConnection)local4_0.openConnection();
         local5_1.setRequestMethod("POST");
         local5_1.setDoOutput(true);
         local5_1.setRequestProperty("Content-Type", "application/json");
         JSONObject local6_2 = new JSONObject();
         local6_2.put("username", username);
         local6_2.put("password", password);
         JSONObject local7_3 = new JSONObject();
         local7_3.put("name", "Minecraft");
         local6_2.put("agent", local7_3);
         local5_1.getOutputStream().write(local6_2.toString().getBytes());
         System.out.println(IOUtil.readString(local5_1.getInputStream()));
         local5_1.disconnect();
      } catch (Throwable var8) {
      }

      return new SimpleProfile(username, local3_4, "", class_321.field_1988, ProfileType.MOJANG);
   }
}
