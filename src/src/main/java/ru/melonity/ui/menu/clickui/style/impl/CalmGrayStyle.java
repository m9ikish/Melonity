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
public class CalmGrayStyle implements MenuStyle {
   @Override
   public class_2960 preview() {
      return class_2960.method_60656("melonity/images/themes/preview/calm_gray.png");
   }

   @Override
   public String name() {
      return "Calm Gray";
   }

   @Override
   public Color ascent() {
      return Color.decode("#B0B0B0");
   }

   @Override
   public Color background() {
      return Color.decode("#1E1E1E");
   }

   @Override
   public boolean headerBlocks() {
      return true;
   }

   @Override
   public Color blocksColor() {
      return Color.decode("#262626");
   }

   @Override
   public Color[] header() {
      return new Color[]{Color.decode("#2A2A2A"), Color.decode("#2A2A2A")};
   }

   @Override
   public TabStyle tab() {
      return new CalmGrayStyle.CalmGrayTabStyle();
   }

   @Override
   public FeatureStyle feature() {
      return new CalmGrayStyle.CalmGrayFeatureStyle();
   }

   @Override
   public Color[] slider() {
      return new Color[]{Color.decode("#B0B0B0"), Color.decode("#B0B0B0")};
   }

   @Override
   public Color booleanElement() {
      return this.ascent();
   }

   @Override
   public Color previewValues() {
      return Color.decode("#3C3C3C");
   }

   @Override
   public Color[] modeElement() {
      return new Color[]{Color.decode("#3C3C3C"), Color.decode("#4D4D4D")};
   }

   @Override
   public GiraStyle gira() {
      return new CalmGrayStyle.CalmGrayGiraStyle();
   }

   @Override
   public KeybindStyle keybind() {
      return new CalmGrayStyle.CalmGrayKeybindStyle();
   }

   @Override
   public Color autobuyPanel() {
      return this.background();
   }

   @Override
   public Color unload() {
      return Color.decode("#3C3C3C");
   }

   @Override
   public Color unloadHovered() {
      return this.unload().darker();
   }

   @Environment(EnvType.CLIENT)
   private static class CalmGrayFeatureStyle implements FeatureStyle {
      @Override
      public Color[] enabledToggler() {
         return new Color[]{Color.decode("#B0B0B0"), Color.decode("#B0B0B0")};
      }

      @Override
      public Color[] disabledToggler() {
         return new Color[]{Color.decode("#3C3C3C"), Color.decode("#3C3C3C")};
      }

      @Override
      public Color disabledCircle() {
         return Color.decode("#E8E8E8");
      }

      @Override
      public Color enabledCircle() {
         return Color.decode("#1E1E1E");
      }

      @Override
      public Color background() {
         return Color.decode("#2A2A2A");
      }
   }

   @Environment(EnvType.CLIENT)
   private static class CalmGrayGiraStyle implements GiraStyle {
      @Override
      public class_2960 preview() {
         return class_2960.method_60656("melonity/images/themes/gira/calm_gray.png");
      }

      @Override
      public Color langBackground() {
         return Color.decode("#3C3C3C");
      }
   }

   @Environment(EnvType.CLIENT)
   private static class CalmGrayKeybindStyle implements KeybindStyle {
      @Override
      public Color background() {
         return Color.decode("#2A2A2A");
      }

      @Override
      public Color keyBackground() {
         return Color.decode("#B0B0B0").darker().darker();
      }

      @Override
      public Color shadow() {
         return Color.decode("#B0B0B0");
      }
   }

   @Environment(EnvType.CLIENT)
   private static class CalmGrayTabStyle implements TabStyle {
      @Override
      public Color textColor() {
         return Color.decode("#1E1E1E");
      }

      @Override
      public Color disabledTextColor() {
         return Color.decode("#A0A0A0");
      }

      @Override
      public Color[] background() {
         return new Color[]{Color.decode("#B0B0B0"), Color.decode("#B0B0B0")};
      }
   }
}
