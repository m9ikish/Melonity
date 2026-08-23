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
public class ViolentRedStyle implements MenuStyle {
   @Override
   public class_2960 preview() {
      return class_2960.method_60656("melonity/images/themes/preview/red.png");
   }

   @Override
   public String name() {
      return "Violent Red";
   }

   @Override
   public Color ascent() {
      return Color.decode("#F42264");
   }

   @Override
   public Color background() {
      return Color.decode("#141414");
   }

   @Override
   public Color[] header() {
      return null;
   }

   @Override
   public boolean headerBlocks() {
      return true;
   }

   @Override
   public Color blocksColor() {
      return Color.decode("#1c1c1c");
   }

   @Override
   public TabStyle tab() {
      return new ViolentRedStyle.MainTabStyle();
   }

   @Override
   public FeatureStyle feature() {
      return new ViolentRedStyle.MainFeatureStyler();
   }

   @Override
   public Color[] slider() {
      return new Color[]{this.ascent(), this.ascent()};
   }

   @Override
   public Color booleanElement() {
      return this.ascent();
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
   public GiraStyle gira() {
      return new ViolentRedStyle.MainGiraStyle();
   }

   @Override
   public KeybindStyle keybind() {
      return new ViolentRedStyle.MainKeybindStyle();
   }

   @Override
   public Color autobuyPanel() {
      return Color.decode("#181818");
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
   private static class MainFeatureStyler implements FeatureStyle {
      @Override
      public Color[] enabledToggler() {
         return new Color[]{Color.decode("#F42264"), Color.decode("#F42264")};
      }

      @Override
      public Color[] disabledToggler() {
         return new Color[]{Color.decode("#222222"), Color.decode("#222222")};
      }

      @Override
      public Color disabledCircle() {
         return Color.WHITE;
      }

      @Override
      public Color enabledCircle() {
         return Color.decode("#141414");
      }

      @Override
      public Color background() {
         return Color.decode("#181818");
      }
   }

   @Environment(EnvType.CLIENT)
   private static class MainGiraStyle implements GiraStyle {
      @Override
      public class_2960 preview() {
         return class_2960.method_60656("melonity/images/themes/gira/red.png");
      }

      @Override
      public Color langBackground() {
         return Color.decode("#222222");
      }
   }

   @Environment(EnvType.CLIENT)
   private static class MainKeybindStyle implements KeybindStyle {
      @Override
      public Color background() {
         return Color.decode("#181818");
      }

      @Override
      public Color keyBackground() {
         return Color.decode("#6b1931");
      }

      @Override
      public Color shadow() {
         return Color.decode("#F42264");
      }
   }

   @Environment(EnvType.CLIENT)
   private static class MainTabStyle implements TabStyle {
      @Override
      public Color textColor() {
         return Color.decode("#141414");
      }

      @Override
      public Color disabledTextColor() {
         return Color.decode("#888888");
      }

      @Override
      public Color[] background() {
         return new Color[]{Color.decode("#F42264"), Color.decode("#F42264")};
      }
   }
}
