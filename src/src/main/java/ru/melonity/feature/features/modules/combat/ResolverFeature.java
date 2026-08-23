package ru.melonity.feature.features.modules.combat;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_238;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;

@Environment(EnvType.CLIENT)
public class ResolverFeature extends SimpleFeature {
   private final NumberSetting backTick = new NumberSetting("global.ticks", value -> String.format("%d", value.intValue()), 1, 50, 2);
   public class_238 resolvedBox;

   public ResolverFeature() {
      super("Backtrack", MenuCategory.COMBAT, false);
      this.setting(this.backTick);
   }

   @Generated
   public NumberSetting getBackTick() {
      return this.backTick;
   }

   @Generated
   public class_238 getResolvedBox() {
      return this.resolvedBox;
   }

   @Environment(EnvType.CLIENT)
   public static class Position {
      private final double x;
      private final double y;
      private final double z;
      public int ticks;

      public Position(double x, double y, double z) {
         this.x = x;
         this.y = y;
         this.z = z;
      }

      public boolean shouldRemove() {
         return this.ticks++ > Melonity.INSTANCE.getFeatureManager().getFeature(ResolverFeature.class).get().backTick.getValue().intValue();
      }

      @Generated
      public double getX() {
         return this.x;
      }

      @Generated
      public double getY() {
         return this.y;
      }

      @Generated
      public double getZ() {
         return this.z;
      }
   }
}
