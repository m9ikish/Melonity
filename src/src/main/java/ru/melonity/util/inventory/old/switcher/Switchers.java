package ru.melonity.util.inventory.old.switcher;

import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class Switchers {
   private static final Switcher legit = new LegitSwitcher();
   private static final Switcher packet = new PacketSwitcher();
   private static final Set<Switcher> switchers = Set.of(legit, packet);

   public static Switcher legit() {
      return legit;
   }

   public static Switcher packet() {
      return packet;
   }

   public static Set<Switcher> all() {
      return switchers;
   }

   public static Switcher fromName(String name) {
      if (name.toLowerCase().contains("legit")) {
         return legit;
      } else if (name.toLowerCase().contains("packet")) {
         return packet;
      } else {
         throw new IllegalArgumentException("Invalid switcher: ".concat(name));
      }
   }
}
