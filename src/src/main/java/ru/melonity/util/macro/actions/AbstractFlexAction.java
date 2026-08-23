package ru.melonity.util.macro.actions;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.macro.Action;

@Environment(EnvType.CLIENT)
public abstract class AbstractFlexAction implements Action {
   private boolean placed;

   @Generated
   public void setPlaced(boolean placed) {
      this.placed = placed;
   }

   @Generated
   public boolean isPlaced() {
      return this.placed;
   }
}
