package ru.melonity.ui.kit.widgets.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.ui.kit.State;

@Environment(EnvType.CLIENT)
public final class TabState implements State {
   public TabState.Variant variant = TabState.Variant.DEFAULT;

   @Environment(EnvType.CLIENT)
   public static enum Variant {
      DEFAULT,
      HOVER,
      ACTIVE;
   }
}
