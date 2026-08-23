package ru.melonity.util.altmanager.impl.microsoft;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.json.JSONObject;

@Environment(EnvType.CLIENT)
public record MCProfile(UUID uuid, String name) {
   private static final Pattern UUID_DASHLESS = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");
   private static final String UUID_DASHED = "$1-$2-$3-$4-$5";

   public static MCProfile fromJson(JSONObject json) {
      try {
         String local1_0 = json.getString("id");
         String local2_1 = json.getString("name");
         Matcher local3_2 = UUID_DASHLESS.matcher(local1_0);
         if (!local3_2.matches()) {
            throw new IllegalStateException("Invalid UUID: " + local1_0);
         } else {
            local1_0 = local3_2.replaceAll("$1-$2-$3-$4-$5");
            UUID local4_3 = UUID.fromString(local1_0);
            return new MCProfile(local4_3, local2_1);
         }
      } catch (Throwable var5) {
         throw new RuntimeException("Unable to parse MCProfile: " + json, var5);
      }
   }
}
