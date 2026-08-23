package ru.melonity.util.macro;

import java.util.List;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface MacroService {
   void addMacro(String var1);

   void addMacro(Macro var1);

   void removeMacro(String var1);

   Optional<Macro> getMacro(String var1);

   List<Action> getActionByCategory(ActionCategory var1);

   List<Macro> getMacroses();
}
