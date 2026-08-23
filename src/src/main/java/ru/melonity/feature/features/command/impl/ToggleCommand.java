package ru.melonity.feature.features.command.impl;

import java.util.List;
import java.util.Locale;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.feature.features.command.Advice;
import ru.melonity.feature.features.command.AdviceCommand;
import ru.melonity.feature.features.command.ArgumentPool;
import ru.melonity.feature.features.command.ChatUtil;
import ru.melonity.feature.features.command.Command;
import ru.melonity.feature.features.command.MultiNamedCommand;

@Environment(EnvType.CLIENT)
public class ToggleCommand implements Command, AdviceCommand, MultiNamedCommand {
   @Override
   public void execute(ArgumentPool argumentPool) {
      String local2_0 = argumentPool.get(1).asString();
      if (local2_0 != null && !local2_0.isEmpty()) {
         Feature local3_1 = Melonity.INSTANCE
            .getFeatureManager()
            .getFeatures()
            .stream()
            .filter(f -> this.namesEqual(local2_0, f.getName()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown feature " + local2_0));
         boolean local4_2 = !local3_1.isEnabled();
         local3_1.setEnabled(local4_2, false);
         ChatUtil.prefix("Feature %s: %s".formatted(local4_2 ? "enabled" : "disabled", local3_1.getName()));
      } else {
         throw new IllegalArgumentException("Feature name is required");
      }
   }

   private String normalize(String value) {
      return value.toLowerCase(Locale.ROOT).replace(" ", "");
   }

   private boolean namesEqual(String left, String right) {
      return this.normalize(left).equals(this.normalize(right));
   }

   @Override
   public String name() {
      return "toggle";
   }

   @Override
   public List<String> references() {
      return List.of("t");
   }

   @Override
   public String description() {
      return "Toggles feature by name";
   }

   @Override
   public List<Advice> advice() {
      return List.of(new Advice("toggle <feature>", "Toggle feature on/off"));
   }
}
