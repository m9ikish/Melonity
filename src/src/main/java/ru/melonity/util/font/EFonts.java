package ru.melonity.util.font;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum EFonts {
   ARIAL("arial.ttf"),
   INTER_REGULAR("inter_regular.ttf"),
   INTER_MEDIUM("inter_medium.ttf"),
   INTER_SEMIBOLD("inter_semibold.ttf"),
   INTER_LIGHT("inter_light.ttf"),
   INTER_BOLD("inter_bold.ttf");

   private final String path;

   private EFonts(String path) {
      this.path = path;
   }

   @Generated
   public String getPath() {
      return this.path;
   }
}
