package ru.melonity.ui.menu.clickui.style.impl;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.style.elements.FeatureStyle;
import ru.melonity.ui.menu.clickui.style.elements.GiraStyle;
import ru.melonity.ui.menu.clickui.style.elements.KeybindStyle;
import ru.melonity.ui.menu.clickui.style.elements.TabStyle;

@Environment(EnvType.CLIENT)
public class GradientNoirStyle implements MenuStyle {
   @Override
   public class_2960 preview() {
      return class_2960.method_60656("melonity/images/themes/preview/gradient_noir.png");
   }

   @Override
   public String name() {
      return "Gradient Noir";
   }

   @Override
   public Color ascent() {
      return Color.decode("#D3F942");
   }

   @Override
   public Color background() {
      return Color.decode("#0D0C0A");
   }

   @Override
   public Color[] header() {
      return new Color[]{Color.decode("#21201E"), Color.decode("#171614")};
   }

   @Override
   public boolean headerBlocks() {
      return false;
   }

   @Override
   public Color blocksColor() {
      return null;
   }

   @Override
   public Color previewValues() {
      return Color.decode("#222222");
   }

   @Override
   public Color[] modeElement() {
      return new Color[]{Color.decode("#222222"), Color.decode("#2C2C2C")};
   }

   @Override
   public TabStyle tab() {
      return new GradientNoirStyle.GradientNoirTabStyle();
   }

   @Override
   public FeatureStyle feature() {
      return new GradientNoirStyle.GradientNoirFeatureStyle();
   }

   @Override
   public Color[] slider() {
      return new Color[]{Color.decode("#21201E"), Color.decode("#171614")};
   }

   @Override
   public Color booleanElement() {
      return Color.WHITE;
   }

   @Override
   public GiraStyle gira() {
      return new GradientNoirStyle.GradientNoirGiraStyle();
   }

   @Override
   public KeybindStyle keybind() {
      return new GradientNoirStyle.GradientNoirKeybindStyle();
   }

   @Override
   public Color autobuyPanel() {
      return this.background().brighter().brighter();
   }

   @Override
   public Color unload() {
      return Color.decode("#222222");
   }

   @Override
   public Color unloadHovered() {
      return Color.decode("#2C2C2C");
   }

   @Environment(EnvType.CLIENT)
   private static class GradientNoirFeatureStyle implements FeatureStyle {
      @Override
      public Color[] enabledToggler() {
         return new Color[]{Color.decode("#2B2A28"), Color.decode("#171614")};
      }

      @Override
      public Color[] disabledToggler() {
         return new Color[]{Color.decode("#222222"), Color.decode("#222222")};
      }

      @Override
      public Color disabledCircle() {
         return Color.decode("#0D0C0A");
      }

      @Override
      public Color enabledCircle() {
         return Color.WHITE;
      }

      @Override
      public Color background() {
         return Color.decode("#171614");
      }
   }

   @Environment(EnvType.CLIENT)
   private static class GradientNoirGiraStyle implements GiraStyle {
      @Override
      public class_2960 preview() {
         return class_2960.method_60656("melonity/images/themes/gira/gradient_noir.png");
      }

      @Override
      public Color langBackground() {
         return Color.decode("#222222");
      }
   }

   @Environment(EnvType.CLIENT)
   private static class GradientNoirKeybindStyle implements KeybindStyle {
      @Override
      public Color background() {
         return Color.decode("#171614");
      }

      @Override
      public Color keyBackground() {
         return Color.decode("#505C25");
      }

      @Override
      public Color shadow() {
         return Color.decode("#D3F942");
      }
   }

   @Environment(EnvType.CLIENT)
   private static class GradientNoirTabStyle implements TabStyle {
      @Override
      public Color textColor() {
         return Color.WHITE;
      }

      @Override
      public Color disabledTextColor() {
         return Color.decode("#888888");
      }

      @Override
      public Color[] background() {
         return new Color[]{Color.decode("#21201E"), Color.decode("#171614")};
      }
   }
}
