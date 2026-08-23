package ru.melonity.feature.features.modules.render.hud.schedules;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.animation.StateAnimation;

@Environment(EnvType.CLIENT)
public class Schedule {
   private final String anarchy;
   private final String name;
   private final String location;
   private long date;
   private final StateAnimation animation = new StateAnimation();

   public Schedule(String anarchy, String name, String location, long date) {
      this.anarchy = anarchy;
      this.name = name;
      this.location = location;
      this.date = date;
   }

   public String anarchy() {
      return this.anarchy;
   }

   public String name() {
      return this.name;
   }

   public String location() {
      return this.location;
   }

   public long date() {
      return this.date;
   }

   public void date(long date) {
      this.date = date;
   }

   public StateAnimation animation() {
      return this.animation;
   }
}
