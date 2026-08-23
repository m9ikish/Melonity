package ru.melonity.feature.features.joiner;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import ru.melonity.util.time.Timer;

@Environment(EnvType.CLIENT)
public abstract class BaseJoinLogic implements JoinLogic {
   protected final class_310 mc = class_310.method_1551();
   protected final Timer timer = Timer.create();
   private final String name;

   public BaseJoinLogic(String name) {
      this.name = name;
   }

   @Override
   public String name() {
      return this.name;
   }

   @Override
   public void enabled() {
      this.timer.reset();
   }
}
