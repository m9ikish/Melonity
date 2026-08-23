package ru.melonity.feature.features.command.impl;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.features.command.Advice;
import ru.melonity.feature.features.command.AdviceCommand;
import ru.melonity.feature.features.command.ArgumentPool;
import ru.melonity.feature.features.command.ChatUtil;
import ru.melonity.feature.features.command.Command;
import ru.melonity.feature.features.command.CommandProvider;

@Environment(EnvType.CLIENT)
public class HelpCommand implements Command, AdviceCommand {
   final CommandProvider commandProvider;

   public HelpCommand(CommandProvider commandProvider) {
      this.commandProvider = commandProvider;
   }

   @Override
   public void execute(ArgumentPool argumentPool) {
      String local2_7 = argumentPool.get(0).asString();
      switch (local2_7) {
         case "list":
            List<Command> local5_1 = this.commandProvider.getCommands();
            ChatUtil.topSplitter();

            for (Command local7_0 : local5_1) {
               ChatUtil.print("  .%s - §7%s".formatted(local7_0.name(), local7_0.description()));
            }

            ChatUtil.bottomSplitter();
            break;
         case "with":
            String local5_5 = argumentPool.get(1).asString();
            Command local6_6 = this.commandProvider.getCommand(local5_5);
            ChatUtil.topSplitter();
            ChatUtil.prefix(local6_6.description());
            if (local6_6 instanceof AdviceCommand local7_4) {
               List<Advice> local8_3 = local7_4.advice();
               ChatUtil.print("    Примеры использования:");

               for (Advice local10_2 : local8_3) {
                  ChatUtil.print("  .%s - %s".formatted(local10_2.getLine(), local10_2.getDescription()));
               }
            } else {
               ChatUtil.print("Команда не содержит примеров использования");
            }

            ChatUtil.bottomSplitter();
            break;
         default:
            throw new IllegalArgumentException("Такой опции нет");
      }
   }

   @Override
   public String name() {
      return "help";
   }

   @Override
   public String description() {
      return "Помощь с командами";
   }

   @Override
   public List<Advice> advice() {
      return List.of(new Advice("help with <Название команды>", "Примеры использования команды"), new Advice("help list", "Список всех команд"));
   }
}
