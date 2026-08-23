package ru.melonity.feature.features.command;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface AdviceCommand {
   List<Advice> advice();
}
