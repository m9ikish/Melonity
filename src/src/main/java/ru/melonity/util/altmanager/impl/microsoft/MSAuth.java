package ru.melonity.util.altmanager.impl.microsoft;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.json.JSONArray;
import org.json.JSONObject;

@Environment(EnvType.CLIENT)
public final class MSAuth {
   private static final String CLIENT_ID = "54fd49e4-2103-4044-9603-2b028c814ec3";
   private static final Duration TIMEOUT = Duration.ofSeconds(30L);
   private static final HttpClient CLIENT = HttpClient.newBuilder().connectTimeout(TIMEOUT).version(Version.HTTP_2).followRedirects(Redirect.NEVER).build();

   private MSAuth() {
      throw new AssertionError("No instances.");
   }

   public static CompletableFuture<DeviceAuth> requestDac() {
      String local0_0 = "client_id=54fd49e4-2103-4044-9603-2b028c814ec3&scope=XboxLive.signin%20XboxLive.offline_access";
      return CLIENT.sendAsync(
            HttpRequest.newBuilder()
               .uri(URI.create("https://login.microsoftonline.com/consumers/oauth2/v2.0/devicecode"))
               .header("Accept", "application/json")
               .header("Content-Type", "application/x-www-form-urlencoded")
               .timeout(TIMEOUT)
               .POST(BodyPublishers.ofString(local0_0))
               .build(),
            BodyHandlers.ofString()
         )
         .thenApply(response -> {
            try {
               int local1_0 = response.statusCode();
               if (local1_0 != 200) {
                  throw new IllegalArgumentException("Invalid status code: " + local1_0);
               } else {
                  JSONObject local2_1 = new JSONObject(response.body());
                  Objects.requireNonNull(local2_1, "Response is null");
                  return DeviceAuth.fromJson(local2_1);
               }
            } catch (Throwable var3) {
               String local2_2 = "Unable to request Device Auth Code (DAC) from (" + response + " with " + response.headers() + "): " + response.body();
               throw new RuntimeException(local2_2, var3);
            }
         });
   }

   public static MSTokens dacToMsaMsr(String code) {
      String local1_9 = "grant_type=urn:ietf:params:oauth:grant-type:device_code&client_id=54fd49e4-2103-4044-9603-2b028c814ec3&device_code="
         + URLEncoder.encode(code, StandardCharsets.UTF_8);

      HttpResponse<String> local2_0;
      try {
         local2_0 = CLIENT.send(
            HttpRequest.newBuilder()
               .uri(URI.create("https://login.microsoftonline.com/consumers/oauth2/v2.0/token"))
               .header("Accept", "application/json")
               .header("Content-Type", "application/x-www-form-urlencoded")
               .timeout(TIMEOUT)
               .POST(BodyPublishers.ofString(local1_9))
               .build(),
            BodyHandlers.ofString()
         );
      } catch (Throwable var8) {
         throw new RuntimeException("Unable to send DAC request.", var8);
      }

      try {
         int local3_5 = local2_0.statusCode();
         if (local3_5 != 200) {
            try {
               JSONObject local4_2 = new JSONObject(local2_0.body());
               String local5_3 = local4_2.getString("error");
               if ("authorization_declined".equals(local5_3)) {
                  throw new RuntimeException("Cancelled: " + local4_2);
               } else if ("authorization_pending".equals(local5_3)) {
                  throw new MSAuth.DevicePendingException("Pending auth: " + local4_2);
               } else {
                  throw new IllegalStateException("Not pending auth.");
               }
            } catch (Throwable var6) {
               throw new IllegalArgumentException("Invalid status code: " + local3_5, var6);
            }
         } else {
            JSONObject local4_6 = new JSONObject(local2_0.body());
            Objects.requireNonNull(local4_6, "Response is null");
            return MSTokens.fromJson(local4_6);
         }
      } catch (Throwable var7) {
         String local4_7 = "Unable to convert Device Auth Code (DAC) to Microsoft Access (MSA) and Microsoft Refresh (MSR) tokens ("
            + local2_0
            + " with "
            + local2_0.headers()
            + "): "
            + local2_0.body();
         local4_7 = local4_7.replace(code, "[DAC]");
         throw new RuntimeException(local4_7, var7);
      }
   }

   public static CompletableFuture<MSTokens> msacToMsaMsr(String code, String redirect) {
      String local2_0 = "client_id=54fd49e4-2103-4044-9603-2b028c814ec3&code="
         + URLEncoder.encode(code, StandardCharsets.UTF_8)
         + "&grant_type=authorization_code&redirect_uri="
         + URLEncoder.encode(redirect, StandardCharsets.UTF_8)
         + "&scope=XboxLive.signin%20XboxLive.offline_access";
      return CLIENT.sendAsync(
            HttpRequest.newBuilder()
               .uri(URI.create("https://login.live.com/oauth20_token.srf"))
               .header("Accept", "application/json")
               .header("Content-Type", "application/x-www-form-urlencoded")
               .timeout(TIMEOUT)
               .POST(BodyPublishers.ofString(local2_0))
               .build(),
            BodyHandlers.ofString()
         )
         .thenApply(
            response -> {
               try {
                  int local2_0x = response.statusCode();
                  if (local2_0x != 200) {
                     throw new IllegalArgumentException("Invalid status code: " + local2_0x);
                  } else {
                     JSONObject local3_1 = new JSONObject(response.body());
                     Objects.requireNonNull(local3_1, "Response is null");
                     return MSTokens.fromJson(local3_1);
                  }
               } catch (Throwable var4) {
                  String local3_2 = "Unable to convert Microsoft Authentication Code (MSAC) to Microsoft Access (MSA) and Microsoft Refresh (MSR) tokens ("
                     + response
                     + " with "
                     + response.headers()
                     + "): "
                     + response.body();
                  local3_2 = local3_2.replace(code, "[MSAC]");
                  throw new RuntimeException(local3_2, var4);
               }
            }
         );
   }

   public static CompletableFuture<MSTokens> msrToMsaMsr(String refresh) {
      String local1_0 = "client_id=54fd49e4-2103-4044-9603-2b028c814ec3&refresh_token="
         + URLEncoder.encode(refresh, StandardCharsets.UTF_8)
         + "&grant_type=refresh_token&scope=XboxLive.signin%20XboxLive.offline_access";
      return CLIENT.sendAsync(
            HttpRequest.newBuilder()
               .uri(URI.create("https://login.live.com/oauth20_token.srf"))
               .header("Accept", "application/json")
               .header("Content-Type", "application/x-www-form-urlencoded")
               .timeout(TIMEOUT)
               .POST(BodyPublishers.ofString(local1_0))
               .build(),
            BodyHandlers.ofString()
         )
         .thenApply(
            response -> {
               try {
                  int local2_0 = response.statusCode();
                  if (local2_0 != 200) {
                     throw new IllegalArgumentException("Invalid status code: " + local2_0);
                  } else {
                     JSONObject local3_1 = new JSONObject(response.body());
                     Objects.requireNonNull(local3_1, "Response is null");
                     return MSTokens.fromJson(local3_1);
                  }
               } catch (Throwable var4) {
                  String local3_2 = "Unable to convert Microsoft Refresh (MSR) token to Microsoft Access (MSA) and Microsoft Refresh (MSR) tokens ("
                     + response
                     + " with "
                     + response.headers()
                     + "): "
                     + response.body();
                  local3_2 = local3_2.replace(refresh, "[MSR]");
                  throw new RuntimeException(local3_2, var4);
               }
            }
         );
   }

   public static CompletableFuture<XHashedToken> msaToXbl(String authToken) {
      JSONObject local1_0 = new JSONObject();
      JSONObject local2_1 = new JSONObject();
      local2_1.put("AuthMethod", "RPS");
      local2_1.put("SiteName", "user.auth.xboxlive.com");
      local2_1.put("RpsTicket", "d=" + authToken);
      local1_0.put("Properties", local2_1);
      local1_0.put("RelyingParty", "http://auth.xboxlive.com");
      local1_0.put("TokenType", "JWT");
      String local3_2 = local1_0.toString();
      return CLIENT.sendAsync(
            HttpRequest.newBuilder()
               .uri(URI.create("https://user.auth.xboxlive.com/user/authenticate"))
               .header("Content-Type", "application/json")
               .header("Accept", "application/json")
               .timeout(TIMEOUT)
               .POST(BodyPublishers.ofString(local3_2))
               .build(),
            BodyHandlers.ofString()
         )
         .thenApply(
            response -> {
               try {
                  int local2_0 = response.statusCode();
                  if (local2_0 != 200) {
                     throw new IllegalArgumentException("Invalid status code: " + local2_0);
                  } else {
                     JSONObject local3_1 = new JSONObject(response.body());
                     Objects.requireNonNull(local3_1, "Response is null");
                     return XHashedToken.fromJson(local3_1);
                  }
               } catch (Throwable var4) {
                  String local3_2x = "Unable to convert Microsoft Access (MSA) token to Xbox Live (XBL) token ("
                     + response
                     + " with "
                     + response.headers()
                     + "): "
                     + response.body();
                  local3_2x = local3_2x.replace(authToken, "[MSA]");
                  throw new RuntimeException(local3_2x, var4);
               }
            }
         );
   }

   public static CompletableFuture<XHashedToken> xblToXsts(String xbl, String hash) {
      JSONObject local2_0 = new JSONObject();
      JSONObject local3_1 = new JSONObject();
      JSONArray local4_2 = new JSONArray();
      local4_2.put(xbl);
      local3_1.put("UserTokens", local4_2);
      local3_1.put("SandboxId", "RETAIL");
      local2_0.put("Properties", local3_1);
      local2_0.put("RelyingParty", "rp://api.minecraftservices.com/");
      local2_0.put("TokenType", "JWT");
      String local5_3 = local2_0.toString();
      return CLIENT.sendAsync(
            HttpRequest.newBuilder()
               .uri(URI.create("https://xsts.auth.xboxlive.com/xsts/authorize"))
               .header("Content-Type", "application/json")
               .header("Accept", "application/json")
               .timeout(TIMEOUT)
               .POST(BodyPublishers.ofString(local5_3))
               .build(),
            BodyHandlers.ofString()
         )
         .thenApply(
            response -> {
               try {
                  int local3_3 = response.statusCode();
                  if (local3_3 == 401) {
                     try {
                        JSONObject local4_0 = new JSONObject(response.body());
                        long local5_1 = local4_0.getLong("XErr");
                        if (local5_1 == 2148916233L) {
                           throw new RuntimeException("XErr from 401 status: 2148916233 (No Xbox linked)");
                        } else if (local5_1 == 2148916235L) {
                           throw new RuntimeException("XErr from 401 status: 2148916235 (Xbox not available)");
                        } else if (local5_1 != 2148916236L && local5_1 != 2148916237L && local5_1 != 2148916238L) {
                           throw new RuntimeException("Unknown XErr from 401 status: " + local5_1);
                        } else {
                           throw new RuntimeException("XErr from 401 status: " + local5_1 + " (Non-adult)");
                        }
                     } catch (Throwable var7) {
                        throw new IllegalArgumentException("Invalid status code: 401", var7);
                     }
                  } else if (local3_3 != 200) {
                     throw new IllegalArgumentException("Invalid status code: " + local3_3);
                  } else {
                     JSONObject local4_4 = new JSONObject(response.body());
                     Objects.requireNonNull(local4_4, "Response is null");
                     XHashedToken local5_5 = XHashedToken.fromJson(local4_4);
                     if (hash != null && !hash.equals(local5_5.hash())) {
                        throw new IllegalStateException("Mismatching XBL and XSTS user hashes.");
                     } else {
                        return local5_5;
                     }
                  }
               } catch (Throwable var8) {
                  String local4_6 = "Unable to convert Xbox Live (XBL) token to Xbox Secure Token Service (XSTS) token ("
                     + response
                     + " with "
                     + response.headers()
                     + "): "
                     + response.body();
                  local4_6 = local4_6.replace(xbl, "[XBL]");
                  if (hash != null) {
                     local4_6 = local4_6.replace(hash, "[HASH]");
                  }

                  throw new RuntimeException(local4_6, var8);
               }
            }
         );
   }

   public static CompletableFuture<String> xstsToMca(String xsts, String hash) {
      JSONObject local2_0 = new JSONObject();
      local2_0.put("identityToken", "XBL3.0 x=" + hash + ";" + xsts);
      String local3_1 = local2_0.toString();
      return CLIENT.sendAsync(
            HttpRequest.newBuilder()
               .uri(URI.create("https://api.minecraftservices.com/authentication/login_with_xbox"))
               .header("Content-Type", "application/json")
               .header("Accept", "application/json")
               .timeout(TIMEOUT)
               .POST(BodyPublishers.ofString(local3_1))
               .build(),
            BodyHandlers.ofString()
         )
         .thenApply(
            response -> {
               try {
                  int local3_0 = response.statusCode();
                  if (local3_0 != 200) {
                     throw new IllegalArgumentException("Invalid status code: " + local3_0);
                  } else {
                     JSONObject local4_1 = new JSONObject(response.body());
                     Objects.requireNonNull(local4_1, "Response is null");
                     return local4_1.getString("access_token");
                  }
               } catch (Throwable var5) {
                  String local4_2 = "Unable to convert Xbox Secure Token Service (XSTS) token to Minecraft Access (MCA) token ("
                     + response
                     + " with "
                     + response.headers()
                     + "): "
                     + response.body();
                  local4_2 = local4_2.replace(xsts, "[XSTS]");
                  local4_2 = local4_2.replace(hash, "[HASH]");
                  throw new RuntimeException(local4_2, var5);
               }
            }
         );
   }

   public static CompletableFuture<MCProfile> mcaToMcp(String access) {
      return CLIENT.sendAsync(
            HttpRequest.newBuilder()
               .uri(URI.create("https://api.minecraftservices.com/minecraft/profile"))
               .header("Authorization", "Bearer " + access)
               .timeout(TIMEOUT)
               .GET()
               .build(),
            BodyHandlers.ofString()
         )
         .thenApply(
            response -> {
               try {
                  int local2_0 = response.statusCode();
                  if (local2_0 == 404) {
                     throw new RuntimeException("Profile 404 (No Minecraft account)");
                  } else if (local2_0 != 200) {
                     throw new IllegalArgumentException("Invalid status code: " + local2_0);
                  } else {
                     JSONObject local3_1 = new JSONObject(response.body());
                     Objects.requireNonNull(local3_1, "Response is null");
                     return MCProfile.fromJson(local3_1);
                  }
               } catch (Throwable var4) {
                  String local3_2 = "Unable to convert Minecraft Access (MCA) token to Minecraft Profile (MCP) ("
                     + response
                     + " with "
                     + response.headers()
                     + "): "
                     + response.body();
                  local3_2 = local3_2.replace(access, "[MCA]");
                  throw new RuntimeException(local3_2, var4);
               }
            }
         );
   }

   public static CompletableFuture<MCProfile> nameToMcp(String name) {
      return CLIENT.sendAsync(
            HttpRequest.newBuilder()
               .uri(URI.create("https://api.mojang.com/users/profiles/minecraft/" + URLEncoder.encode(name, StandardCharsets.UTF_8)))
               .timeout(TIMEOUT)
               .GET()
               .build(),
            BodyHandlers.ofString()
         )
         .thenApply(
            response -> {
               try {
                  int local2_0 = response.statusCode();
                  if (local2_0 != 200) {
                     throw new IllegalArgumentException("Invalid status code: " + local2_0);
                  } else {
                     JSONObject local3_1 = new JSONObject(response.body());
                     Objects.requireNonNull(local3_1, "Response is null");
                     return MCProfile.fromJson(local3_1);
                  }
               } catch (Throwable var4) {
                  throw new RuntimeException(
                     "Unable to obtain Minecraft profile by name '" + name + "' (" + response + " with " + response.headers() + "): " + response.body(), var4
                  );
               }
            }
         );
   }

   @Environment(EnvType.CLIENT)
   public static class DevicePendingException extends Exception {
      public DevicePendingException(String message) {
         super(message);
      }
   }
}
