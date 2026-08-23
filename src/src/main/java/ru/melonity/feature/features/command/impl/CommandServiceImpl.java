package ru.melonity.feature.features.command.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.feature.features.command.AdviceCommand;
import ru.melonity.feature.features.command.Argument;
import ru.melonity.feature.features.command.ArgumentPool;
import ru.melonity.feature.features.command.ChatUtil;
import ru.melonity.feature.features.command.Command;
import ru.melonity.feature.features.command.CommandExecutionResult;
import ru.melonity.feature.features.command.CommandProvider;
import ru.melonity.feature.features.command.CommandService;
import ru.melonity.feature.features.command.MultiNamedCommand;
import ru.melonity.feature.features.command.Prefix;
import ru.melonity.pair.Pair;

@Environment(EnvType.CLIENT)
public class CommandServiceImpl implements CommandService, CommandProvider {
   final Map<String, Command> commands;
   final Prefix prefix;
   final List<Command> onlyCommands;

   public CommandServiceImpl(Prefix prefix) {
      List<Command> local2_0 = new ArrayList<>();
      local2_0.add(new HelpCommand(this));
      local2_0.add(new PrefixCommand(prefix));
      local2_0.add(new FriendCommand());
      local2_0.add(new GPSCommand());
      local2_0.add(new ToggleCommand());
      this.commands = local2_0.stream()
         .flatMap(CommandServiceImpl::convertCommandToStreamOfReferenceAndCommandPair)
         .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
      this.prefix = prefix;
      this.onlyCommands = local2_0;
   }

   @Override
   public CommandExecutionResult execute(String rawMessage) {
      String local2_7 = this.prefix.prefix();
      if (rawMessage.startsWith(local2_7)) {
         String local3_1 = rawMessage.substring(local2_7.length());
         String[] local4_2 = local3_1.split(" ");
         String local5_3 = local4_2[0];
         Command local6_4 = this.getCommand(local5_3);
         String[] local7_5 = new String[local4_2.length - 1];
         System.arraycopy(local4_2, 1, local7_5, 0, local7_5.length);
         ArgumentPool local8_6 = new ArgumentPool(Arrays.stream(local7_5).map(Argument::new).toList());

         try {
            local6_4.execute(local8_6);
            return CommandExecutionResult.SUCCESS;
         } catch (Exception var10) {
            ChatUtil.topSplitter();
            ChatUtil.prefix("Произошла ошибка во время выполнения команды");
            ChatUtil.print(" Это не значит что команды не существует!");
            ChatUtil.print(" Напишите %shelp list, чтобы увидеть список команд".formatted(local2_7));
            if (local6_4 instanceof AdviceCommand) {
               ChatUtil.print(" Напишите '%shelp with %s' для примера использования команды".formatted(local2_7, local6_4.name()));
            }

            ChatUtil.bottomSplitter();
            return CommandExecutionResult.DISCARD;
         }
      } else {
         return CommandExecutionResult.NONE;
      }
   }

   @Override
   public Command getCommand(String commandName) {
      try {
         return this.commands.get(commandName);
      } catch (Exception var3) {
         throw new IllegalArgumentException("Команда %s не найдена".formatted(commandName));
      }
   }

   @Override
   public List<Command> getCommands() {
      return this.onlyCommands;
   }

   private static Stream<Pair<String, Command>> convertCommandToStreamOfReferenceAndCommandPair(Command command) {
      Stream<Pair<String, Command>> local1_1 = Stream.of(new Pair<>(command.name(), command));
      if (command instanceof MultiNamedCommand local2_0) {
         local1_1 = Stream.concat(local1_1, local2_0.references().stream().map(reference -> new Pair<>(reference, command)));
      }

      return local1_1;
   }
}
