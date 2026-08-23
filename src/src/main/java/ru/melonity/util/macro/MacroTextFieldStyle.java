package ru.melonity.util.macro;

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
public class MacroTextFieldStyle implements MenuStyle {
   @Override
   public class_2960 preview() {
      return null;
   }

   @Override
   public String name() {
      return "";
   }

   @Override
   public Color ascent() {
      return null;
   }

   @Override
   public Color background() {
      return Color.decode("#181818");
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
   public Color[] header() {
      return new Color[0];
   }

   @Override
   public TabStyle tab() {
      return new MacroTextFieldStyle.MacroTextFieldTabStyle();
   }

   @Override
   public FeatureStyle feature() {
      return null;
   }

   @Override
   public Color[] slider() {
      return new Color[0];
   }

   @Override
   public Color booleanElement() {
      return null;
   }

   @Override
   public Color previewValues() {
      return this.background();
   }

   @Override
   public Color[] modeElement() {
      return new Color[0];
   }

   @Override
   public GiraStyle gira() {
      return null;
   }

   @Override
   public KeybindStyle keybind() {
      return null;
   }

   @Override
   public Color autobuyPanel() {
      return null;
   }

   @Override
   public Color unload() {
      return null;
   }

   @Override
   public Color unloadHovered() {
      return null;
   }

   @Environment(EnvType.CLIENT)
   private static class MacroTextFieldTabStyle implements TabStyle {
      @Override
      public Color textColor() {
         return Color.decode("#ffffff");
      }

      @Override
      public Color disabledTextColor() {
         return Color.decode("#ffffff");
      }

      @Override
      public Color[] background() {
         return new Color[0];
      }
   }
}
