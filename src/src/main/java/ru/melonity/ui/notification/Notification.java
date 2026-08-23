package ru.melonity.ui.notification;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.animation.StateAnimation;

@Environment(EnvType.CLIENT)
public class Notification {
   public final String title;
   public final String description;
   public final String icon;
   private final StateAnimation animation = new StateAnimation();
   private final long time = System.currentTimeMillis();

   public Notification(String title, String description, String icon) {
      this.title = title;
      this.description = description;
      this.icon = icon;
   }

   @Generated
   public String getTitle() {
      return this.title;
   }

   @Generated
   public String getDescription() {
      return this.description;
   }

   @Generated
   public String getIcon() {
      return this.icon;
   }

   @Generated
   public StateAnimation getAnimation() {
      return this.animation;
   }

   @Generated
   public long getTime() {
      return this.time;
   }
}
