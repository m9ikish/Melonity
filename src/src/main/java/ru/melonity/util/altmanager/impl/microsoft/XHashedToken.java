package ru.melonity.util.altmanager.impl.microsoft;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.json.JSONArray;
import org.json.JSONObject;

@Environment(EnvType.CLIENT)
public record XHashedToken(String token, String hash) {
   public static XHashedToken fromJson(JSONObject json) {
      try {
         String local1_0 = json.getString("Token");
         JSONObject local2_1 = json.getJSONObject("DisplayClaims");
         JSONArray local3_2 = local2_1.getJSONArray("xui");
         int local4_3 = local3_2.length();
         if (local4_3 != 1) {
            throw new IllegalArgumentException("Unexpected 'xui' size: " + local4_3);
         } else {
            JSONObject local5_4 = local3_2.getJSONObject(0);
            String local6_5 = local5_4.getString("uhs");
            return new XHashedToken(local1_0, local6_5);
         }
      } catch (Throwable var7) {
         throw new RuntimeException("Unable to parse XHashedToken: " + json, var7);
      }
   }
}
