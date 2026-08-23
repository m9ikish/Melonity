package ru.melonity.util.altmanager.impl.microsoft;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.json.JSONObject;

@Environment(EnvType.CLIENT)
public record MSTokens(String access, String refresh) {
   public static MSTokens fromJson(JSONObject json) {
      try {
         String local1_0 = json.getString("access_token");
         String local2_1 = json.getString("refresh_token");
         return new MSTokens(local1_0, local2_1);
      } catch (Throwable var3) {
         throw new RuntimeException("Unable to parse MSTokens: " + json, var3);
      }
   }
}
