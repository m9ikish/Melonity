package ru.melonity.util.altmanager.impl.microsoft;

import java.net.URI;
import java.time.Duration;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.json.JSONObject;

@Environment(EnvType.CLIENT)
public record DeviceAuth(String device, String user, URI uri, Duration expire, Duration interval) {
   public static DeviceAuth fromJson(JSONObject json) {
      try {
         String local1_0 = json.getString("device_code");
         String local2_1 = json.getString("user_code");
         String local3_2 = json.getString("verification_uri");
         URI local4_3 = new URI(local3_2).parseServerAuthority();
         if (!"https".equals(local4_3.getScheme())) {
            throw new IllegalStateException("Invalid URL scheme: " + local4_3);
         } else {
            long local5_4 = json.getLong("expires_in");
            Duration local7_5 = Duration.ofSeconds(local5_4);
            if (!local7_5.isNegative() && !local7_5.isZero() && local7_5.toDays() <= 2L) {
               long local8_6 = json.getLong("interval");
               Duration local10_7 = Duration.ofSeconds(local8_6);
               if (!local10_7.isNegative() && !local10_7.isZero() && local10_7.compareTo(local7_5) < 0) {
                  return new DeviceAuth(local1_0, local2_1, local4_3, local7_5, local10_7);
               } else {
                  throw new IllegalStateException("Invalid interval (with expire of " + local7_5 + ": " + local10_7 + " (" + local8_6 + ")");
               }
            } else {
               throw new IllegalStateException("Invalid expire: " + local7_5 + " (" + local5_4 + ")");
            }
         }
      } catch (Throwable var11) {
         throw new RuntimeException("Unable to parse DeviceAuth: " + json, var11);
      }
   }
}
