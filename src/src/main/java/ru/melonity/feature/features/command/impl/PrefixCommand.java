package ru.melonity.feature.features.command.impl;

import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.features.command.Advice;
import ru.melonity.feature.features.command.AdviceCommand;
import ru.melonity.feature.features.command.ArgumentPool;
import ru.melonity.feature.features.command.Command;
import ru.melonity.feature.features.command.Prefix;

@Environment(EnvType.CLIENT)
public class PrefixCommand implements Command, AdviceCommand {
   final Prefix prefix;

   @Override
   public void execute(ArgumentPool argumentPool) {
      String local2_0 = argumentPool.get(1).asString();
      this.prefix.setPrefix(local2_0);
   }

   @Override
   public String name() {
      return "prefix";
   }

   @Override
   public String description() {
      return "Изменение префикса для всех команд";
   }

   @Override
   public List<Advice> advice() {
      return List.of(new Advice("prefix set <prefix>", "Установить префикс для всех команд"));
   }

   @Generated
   public PrefixCommand(Prefix prefix) {
      this.prefix = prefix;
   }
}
