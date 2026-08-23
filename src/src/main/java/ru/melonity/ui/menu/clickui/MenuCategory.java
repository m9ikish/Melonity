package ru.melonity.ui.menu.clickui;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum MenuCategory {
   COMBAT("Combat", "combat.png"),
   MOVEMENT("Movement", "movement.png"),
   RENDER("Render", "render.png"),
   PLAYER("Player", "player.png"),
   BARITONE("Baritone", "baritone.png"),
   MISC("Misc", "misc.png"),
   MACROS("Macros", "macros.png"),
   CONFIGS("Configs", "configs.png");

   public final String displayName;
   public final String icon;

   private MenuCategory(String displayName, String icon) {
      this.displayName = displayName;
      this.icon = icon;
   }

   @Generated
   public String getDisplayName() {
      return this.displayName;
   }

   @Generated
   public String getIcon() {
      return this.icon;
   }
}
