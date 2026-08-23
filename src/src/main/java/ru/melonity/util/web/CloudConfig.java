package ru.melonity.util.web;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.SneakyThrows;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.json.JSONObject;
import ru.melonity.ui.components.MelonityTextField;

@Environment(EnvType.CLIENT)
public class CloudConfig {
   public String _id;
   public int userId;
   public String content;
   public String name;
   public String uuid;
   private boolean favourite = false;
   public MelonityTextField editField = new MelonityTextField("", false, 100.0F, 11.0F);
   private static final byte[] RC4_KEY = "b2fK9zXpEpQ3TbVn".getBytes(StandardCharsets.UTF_8);
   /**
    * Configs used to live on the Melonity licence server. That server is gone, so every config is
    * a plain JSON file in this directory, named after its uuid. The on-disk JSON carries exactly
    * the fields the constructor below reads, so a file is a verbatim copy of the old server reply.
    */
   private static final File CONFIGS_DIRECTORY = new File("C:/Melonity Minecraft", "configs");

   public CloudConfig(JSONObject response) {
      this._id = response.optString("_id");
      this.userId = response.optInt("user_id");
      this.content = response.optString("content");
      this.name = response.optString("name");
      this.uuid = response.optString("uuid");

      try {
         JSONObject local2_0 = new JSONObject(new String(Base64.getDecoder().decode(this.content), StandardCharsets.UTF_8));
         this.favourite = local2_0.optBoolean("favourite");
      } catch (Throwable var3) {
      }

      if (response.toString().contains("AdaptiveCoordinates")) {
         this._id = "invalid";
      }
   }

   public static CloudConfig fetchConfig(String uuid, int userId) {
      if (uuid == null || uuid.isBlank()) {
         return null;
      }

      File file = new File(CONFIGS_DIRECTORY, uuid + ".json");
      if (!file.isFile()) {
         return null;
      }

      try {
         return new CloudConfig(new JSONObject(Files.readString(file.toPath(), StandardCharsets.UTF_8)));
      } catch (Throwable error) {
         return null;
      }
   }

   public static CloudConfig createNew(int userId, String name, JSONObject jsonObject) {
      JSONObject response = new JSONObject();
      response.put("_id", UUID.randomUUID().toString().replace("-", ""));
      response.put("user_id", userId);
      response.put("content", Base64.getEncoder().encodeToString((jsonObject == null ? new JSONObject() : jsonObject).toString().getBytes(StandardCharsets.UTF_8)));
      response.put("name", name == null || name.isBlank() ? "New config" : name);
      response.put("uuid", UUID.randomUUID().toString());
      CloudConfig config = new CloudConfig(response);

      try {
         config.saveToDisk();
      } catch (IOException error) {
         return null;
      }

      return config;
   }

   public static CloudConfig defaultConfig(int userId) {
      CloudConfig existing = listConfigs(userId, Integer.MAX_VALUE)
         .stream()
         .filter(config -> config.name.equalsIgnoreCase("Default"))
         .findFirst()
         .orElse(null);
      return existing != null ? existing : createNew(userId, "Default", new JSONObject());
   }

   @SneakyThrows
   public void setLiked(boolean liked) {
      try {
         JSONObject local2_0 = new JSONObject(new String(Base64.getDecoder().decode(this.content), StandardCharsets.UTF_8));
         local2_0.put("favourite", liked);
         this.favourite = liked;
         this.updateConfig(this.userId, this.uuid, local2_0.toString());
      } catch (Throwable var3) {
      }
   }

   public boolean isLiked() {
      return this.favourite;
   }

   public StatusWithMessage updateConfig(int userId, String configUuid, String content) {
      if (content == null || content.isBlank()) {
         return new StatusWithMessage(false, "Config content is empty");
      }

      try {
         this.userId = userId;
         if (configUuid != null && !configUuid.isBlank()) {
            this.uuid = configUuid;
         }

         String payload = content;

         try {
            JSONObject decorated = new JSONObject(content);
            decorated.put("favourite", this.favourite);
            payload = decorated.toString();
         } catch (Throwable notJson) {
         }

         this.content = Base64.getEncoder().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
         this.saveToDisk();
         return new StatusWithMessage(true, "Config saved");
      } catch (Throwable error) {
         return new StatusWithMessage(false, error.getMessage() == null ? error.toString() : error.getMessage());
      }
   }

   public StatusWithMessage rename(String newName) {
      if (newName == null || newName.isBlank()) {
         return new StatusWithMessage(false, "Config name is empty");
      }

      String previousName = this.name;

      try {
         this.name = newName.trim();
         this.saveToDisk();
         return new StatusWithMessage(true, "Config renamed");
      } catch (Throwable error) {
         this.name = previousName;
         return new StatusWithMessage(false, error.getMessage() == null ? error.toString() : error.getMessage());
      }
   }

   public static StatusWithMessage copyConfig(int userId, String configUuid) {
      if (configUuid == null || configUuid.isBlank()) {
         return new StatusWithMessage(false, "Config uuid is empty");
      }

      CloudConfig source = fetchConfig(configUuid.trim(), userId);
      if (source == null) {
         return new StatusWithMessage(false, "Config " + configUuid.trim() + " not found");
      }

      JSONObject response = new JSONObject();
      response.put("_id", UUID.randomUUID().toString().replace("-", ""));
      response.put("user_id", userId);
      response.put("content", source.content);
      response.put("name", source.name + " (copy)");
      response.put("uuid", UUID.randomUUID().toString());

      try {
         new CloudConfig(response).saveToDisk();
         return new StatusWithMessage(true, "Config copied");
      } catch (Throwable error) {
         return new StatusWithMessage(false, error.getMessage() == null ? error.toString() : error.getMessage());
      }
   }

   public static StatusWithMessage deleteConfig(int userId, String configUuid) {
      if (configUuid == null || configUuid.isBlank()) {
         return new StatusWithMessage(false, "Config uuid is empty");
      }

      File file = new File(CONFIGS_DIRECTORY, configUuid + ".json");
      if (!file.isFile()) {
         return new StatusWithMessage(false, "Config not found");
      }

      return file.delete() ? new StatusWithMessage(true, "Config deleted") : new StatusWithMessage(false, "Config file is locked");
   }

   public static List<CloudConfig> listConfigs(int userId, int limit) {
      List<CloudConfig> configs = new ArrayList<>();
      File[] files = CONFIGS_DIRECTORY.listFiles((directory, fileName) -> fileName.endsWith(".json"));
      if (files == null) {
         return configs;
      }

      for (File file : files) {
         try {
            configs.add(new CloudConfig(new JSONObject(Files.readString(file.toPath(), StandardCharsets.UTF_8))));
         } catch (Throwable error) {
         }
      }

      configs.sort(Comparator.comparing(config -> config.name == null ? "" : config.name.toLowerCase()));
      return limit >= 0 && configs.size() > limit ? new ArrayList<>(configs.subList(0, limit)) : configs;
   }

   private void saveToDisk() throws IOException {
      CONFIGS_DIRECTORY.mkdirs();
      JSONObject response = new JSONObject();
      response.put("_id", this._id);
      response.put("user_id", this.userId);
      response.put("content", this.content);
      response.put("name", this.name);
      response.put("uuid", this.uuid);
      Files.writeString(new File(CONFIGS_DIRECTORY, this.uuid + ".json").toPath(), response.toString(), StandardCharsets.UTF_8);
   }

   /**
    * Symmetric RC4 over {@link #RC4_KEY}. It used to wrap the config payload on the wire; local
    * files are stored as plain JSON, so nothing calls this any more. Kept because the key and the
    * method are part of the original class.
    */
   private static byte[] rc4Encrypt(byte[] data) {
      int[] sbox = new int[256];

      for (int i = 0; i < 256; i++) {
         sbox[i] = i;
      }

      int j = 0;

      for (int i = 0; i < 256; i++) {
         j = (j + sbox[i] + (RC4_KEY[i % RC4_KEY.length] & 0xFF)) & 0xFF;
         int swapped = sbox[i];
         sbox[i] = sbox[j];
         sbox[j] = swapped;
      }

      byte[] result = new byte[data.length];
      int x = 0;
      int y = 0;

      for (int index = 0; index < data.length; index++) {
         x = x + 1 & 0xFF;
         y = y + sbox[x] & 0xFF;
         int swapped = sbox[x];
         sbox[x] = sbox[y];
         sbox[y] = swapped;
         result[index] = (byte)(data[index] ^ sbox[sbox[x] + sbox[y] & 0xFF]);
      }

      return result;
   }

   @Override
   public String toString() {
      return "CloudConfig{_id='"
         + this._id
         + "', userId="
         + this.userId
         + ", content='"
         + this.content
         + "', name='"
         + this.name
         + "', uuid="
         + this.uuid
         + "}";
   }
}
