package ru.melonity.feature.features.command.impl;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import net.minecraft.class_3966;
import ru.melonity.Melonity;
import ru.melonity.feature.features.command.Advice;
import ru.melonity.feature.features.command.AdviceCommand;
import ru.melonity.feature.features.command.ArgumentPool;
import ru.melonity.feature.features.command.ChatUtil;
import ru.melonity.feature.features.command.Command;
import ru.melonity.service.friend.FriendService;

@Environment(EnvType.CLIENT)
public class FriendCommand implements Command, AdviceCommand {
   @Override
   public List<Advice> advice() {
      return List.of(
         new Advice("friend clear", "Очистить список друзей"),
         new Advice("friend add <name>", "Добавить друга по нику"),
         new Advice("friend remove <name>", "Удалить друга по нику"),
         new Advice("friend raycast", "Добавить друга на которого смотришь")
      );
   }

   @Override
   public void execute(ArgumentPool argumentPool) {
      FriendService local2_8 = Melonity.INSTANCE.componentManager.service(FriendService.class);
      String local3_9 = argumentPool.get(0).asString();
      switch (local3_9) {
         case "clear":
            if (local2_8.friends().isEmpty()) {
               ChatUtil.prefix("У вас и не было друзей. Держите банан сочуствия \ud83c\udf4c");
            } else {
               local2_8.clear();
               ChatUtil.prefix("У вас больше нету друзей");
            }
            break;
         case "add":
            String local6_0 = argumentPool.get(1).asString();
            local2_8.add(local6_0);
            ChatUtil.prefix("Игрок %s добавлен в друзья".formatted(local6_0));
            break;
         case "remove":
            String local6_1 = argumentPool.get(1).asString();
            local2_8.remove(local6_1);
            ChatUtil.prefix("Игрок %s удалён из друзей".formatted(local6_1));
            break;
         case "raycast":
            if (!(class_310.method_1551().field_1765 instanceof class_3966 local7_2)) {
               ChatUtil.prefix("Наведитесь на игрока");
               return;
            }

            if (!(local7_2.method_17782() instanceof class_1657 local8_3)) {
               ChatUtil.prefix("Сущность %s это не игрок".formatted(local7_2.method_17782().method_5864().method_5897().getString()));
               return;
            }

            String var12 = local8_3.method_5477().getString();
            local2_8.add(var12);
            ChatUtil.prefix("§aИгрок %s добавлен в друзья".formatted(var12));
            break;
         default:
            throw new IllegalArgumentException("Такой опции нет");
      }
   }

   @Override
   public String name() {
      return "friend";
   }

   @Override
   public String description() {
      return "Взаимодействие с друзьями";
   }
}
