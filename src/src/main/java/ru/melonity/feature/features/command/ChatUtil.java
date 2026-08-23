package ru.melonity.feature.features.command;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_124;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_310;
import net.minecraft.class_5250;
import ru.melonity.Melonity;
import ru.melonity.ui.menu.clickui.style.MenuStyle;

@Environment(EnvType.CLIENT)
public class ChatUtil {
   public static void topSplitter() {
      print(" ");
      print("§7// ----------------------------------------------- \\\\");
   }

   public static void bottomSplitter() {
      print("§7\\\\ ----------------------------------------------- //");
      print(" ");
   }

   public static void print(String message) {
      class_310.method_1551().field_1705.method_1743().method_1812(class_2561.method_43470(message));
   }

   public static void prefix(String message) {
      class_310.method_1551().field_1705.method_1743().method_1812(createMelonityPrefix().method_10852(class_2561.method_43470(message)));
   }

   public static class_5250 createGradientText(String text, int startColor, int endColor) {
      if (text.isEmpty()) {
         return class_2561.method_43470("");
      } else {
         class_5250 local3_5 = class_2561.method_43470("");
         int local4_6 = text.length();

         for (int local5_4 = 0; local5_4 < local4_6; local5_4++) {
            char local6_0 = text.charAt(local5_4);
            float local7_1 = local4_6 == 1 ? 0.0F : (float)local5_4 / (float)(local4_6 - 1);
            int local8_2 = interpolateColor(startColor, endColor, local7_1);
            class_2583 local9_3 = class_2583.field_24360.method_36139(local8_2);
            local3_5.method_10852(class_2561.method_43470(String.valueOf(local6_0)).method_10862(local9_3));
         }

         return local3_5;
      }
   }

   public static class_5250 createMelonityPrefix() {
      int[] local0_0 = resolveGradientColors();
      return createGradientText("Melonity", local0_0[0], local0_0[1])
         .method_27692(class_124.field_1067)
         .method_10852(class_2561.method_43470(" ").method_27692(class_124.field_1063))
         .method_10852(class_2561.method_43470("»").method_27692(class_124.field_1080))
         .method_10852(class_2561.method_43470(" ").method_27694(style -> class_2583.field_24360));
   }

   private static int[] resolveGradientColors() {
      MenuStyle local0_2 = Melonity.INSTANCE != null ? Melonity.INSTANCE.styleManager.getStyle() : null;
      if (local0_2 != null) {
         Color[] local1_0 = local0_2.slider();
         if (local1_0 != null && local1_0.length >= 2) {
            return new int[]{local1_0[0].getRGB() & 16777215, local1_0[local1_0.length - 1].getRGB() & 16777215};
         }

         Color local2_1 = local0_2.ascent();
         if (local2_1 != null) {
            return new int[]{local2_1.getRGB() & 16777215, local2_1.darker().getRGB() & 16777215};
         }
      }

      return new int[]{13364571, 10929465};
   }

   private static int interpolateColor(int color1, int color2, float progress) {
      int local3_0 = color1 >> 16 & 0xFF;
      int local4_1 = color1 >> 8 & 0xFF;
      int local5_2 = color1 & 0xFF;
      int local6_3 = color2 >> 16 & 0xFF;
      int local7_4 = color2 >> 8 & 0xFF;
      int local8_5 = color2 & 0xFF;
      int local9_6 = (int)((float)local3_0 + (float)(local6_3 - local3_0) * progress);
      int local10_7 = (int)((float)local4_1 + (float)(local7_4 - local4_1) * progress);
      int local11_8 = (int)((float)local5_2 + (float)(local8_5 - local5_2) * progress);
      return local9_6 << 16 | local10_7 << 8 | local11_8;
   }
}
