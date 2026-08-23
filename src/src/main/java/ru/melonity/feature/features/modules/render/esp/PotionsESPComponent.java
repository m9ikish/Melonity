package ru.melonity.feature.features.modules.render.esp;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1058;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_4074;
import net.minecraft.class_4587;
import net.minecraft.class_6880;
import org.joml.Vector4d;
import ru.melonity.font.Fonts;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.time.TimeFormatter;

@Environment(EnvType.CLIENT)
public class PotionsESPComponent implements ESPComponent {
   @Override
   public void render(class_1657 entity, Vector4d position, class_4587 poseStack, class_332 graphics, RenderSystem renderSystem) {
      class_310 local6_4 = class_310.method_1551();
      class_4074 local7_5 = local6_4.method_18505();
      if (entity.method_6026() != null && !entity.method_6026().isEmpty()) {
         float local8_6 = (float)position.x;
         float local9_7 = (float)position.y;
         float local10_8 = (float)(position.z - position.x);
         float local11_9 = (float)(position.w - position.y);
         float local12_10 = 20.0F;

         for (class_1293 local14_0 : entity.method_6026()) {
            local12_10 = Math.max(local12_10, 20.0F + Fonts.INTER_MEDIUM_15.getStringWidth(TimeFormatter.ticksToTime(local14_0.method_5584())) + 3.0F);
         }

         int local13_11 = Math.min(6, entity.method_6026().size());
         float local14_12 = (float)(local13_11 * 16 + 3);
         float local15_13 = 0.8F;
         float local17_15 = local14_12 * local15_13;
         float local18_16 = local15_13;
         if (local17_15 > local11_9) {
            local18_16 = local11_9 / local14_12;
         }

         float local19_17 = local8_6 + local10_8 + 5.0F;
         poseStack.method_22903();
         poseStack.method_46416(local19_17, local9_7, 0.0F);
         poseStack.method_22905(local18_16, local18_16, local18_16);
         poseStack.method_46416(-local19_17, -local9_7, 0.0F);
         renderSystem.addBlur(local19_17, local9_7, local12_10, local14_12, 15.0F, 4.0F, poseStack);
         renderSystem.addRect(local19_17, local9_7, local12_10, local14_12, 4.0F, new Color(24, 24, 24, 90), poseStack);
         float local21_19 = local9_7 + 1.5F;
         int local22_20 = 0;

         for (class_1293 local24_3 : entity.method_6026()) {
            if (local22_20 >= local13_11) {
               break;
            }

            class_6880<class_1291> local25_1 = local24_3.method_5579();
            class_1058 local26_2 = local7_5.method_18663(local25_1);
            graphics.method_25298((int)(local19_17 + 2.0F), (int)local21_19, 0, 15, 15, local26_2);
            renderSystem.addText(Fonts.INTER_MEDIUM_15, TimeFormatter.ticksToTime(local24_3.method_5584()), local19_17 + 18.0F, local21_19 + 4.0F, poseStack);
            local21_19 += 16.0F;
            local22_20++;
         }

         poseStack.method_22909();
      }
   }

   @Override
   public String name() {
      return "esp.elements.potions";
   }
}
