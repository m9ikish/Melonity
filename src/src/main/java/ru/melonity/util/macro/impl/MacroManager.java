package ru.melonity.util.macro.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.Melonity;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.macro.Action;
import ru.melonity.util.macro.ActionCategory;
import ru.melonity.util.macro.Macro;
import ru.melonity.util.macro.MacroService;
import ru.melonity.util.macro.actions.ChatAction;
import ru.melonity.util.macro.actions.DisableFunctionAction;
import ru.melonity.util.macro.actions.EnableFunctionAction;
import ru.melonity.util.macro.actions.UseItemAction;
import ru.melonity.util.macro.actions.WaitMillisAction;

@Environment(EnvType.CLIENT)
public class MacroManager implements MacroService {
   private final List<Macro> macroses;
   private final ImmutableList<Action> actions = ImmutableList.of(
      new EnableFunctionAction(), new DisableFunctionAction(), new WaitMillisAction(), new UseItemAction(), new ChatAction()
   );

   public MacroManager() {
      this.macroses = Lists.newLinkedList();
   }

   @Override
   public void addMacro(String name) {
      if (this.getMacro(name).isPresent()) {
         Melonity.INSTANCE.getNotificationManager().add(new Notification("Error", "Macros already exists", "feature-disabled.png"));
      } else {
         this.macroses.add(new Macro(name));
      }
   }

   @Override
   public void addMacro(Macro macro) {
      if (this.getMacro(macro.getName()).isPresent()) {
         Melonity.INSTANCE.getNotificationManager().add(new Notification("Error", "Macros already exists", "feature-disabled.png"));
      } else {
         this.macroses.add(macro);
      }
   }

   @Override
   public void removeMacro(String name) {
      this.macroses.removeIf(m -> m.getName().equals(name));
   }

   @Override
   public Optional<Macro> getMacro(String name) {
      return this.macroses.stream().filter(m -> m.getName().equals(name)).findFirst();
   }

   @Override
   public List<Action> getActionByCategory(ActionCategory category) {
      return this.actions.stream().filter(action -> action.getCategory() == category).toList();
   }

   @Override
   public List<Macro> getMacroses() {
      return ImmutableList.copyOf(this.macroses);
   }
}
