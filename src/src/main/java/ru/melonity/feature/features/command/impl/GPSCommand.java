package ru.melonity.feature.features.command.impl;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.Melonity;
import ru.melonity.feature.features.command.Advice;
import ru.melonity.feature.features.command.AdviceCommand;
import ru.melonity.feature.features.command.ArgumentPool;
import ru.melonity.feature.features.command.ChatUtil;
import ru.melonity.feature.features.command.Command;
import ru.melonity.util.event.listeners.GPSListener;

@Environment(EnvType.CLIENT)
public class GPSCommand implements Command, AdviceCommand {
   @Override
   public List<Advice> advice() {
      return List.of(new Advice("gps <x> <z>", "Ставит метку на координаты"), new Advice("gps off", "Выключает GPS"));
   }

   @Override
   public void execute(ArgumentPool argumentPool) {
      GPSListener local2_0 = Melonity.INSTANCE.getGps();
      if (argumentPool.get(0).asString().equalsIgnoreCase("off")) {
         local2_0.setEnabled(false);
         ChatUtil.prefix("GPS Выключен");
      } else {
         int local3_1 = argumentPool.get(0).asInteger().get();
         int local4_2 = argumentPool.get(1).asInteger().get();
         local2_0.setEnabled(true);
         local2_0.setX(local3_1);
         local2_0.setZ(local4_2);
      }
   }

   @Override
   public String name() {
      return "gps";
   }

   @Override
   public String description() {
      return "Визуальная метка на координаты";
   }
}
