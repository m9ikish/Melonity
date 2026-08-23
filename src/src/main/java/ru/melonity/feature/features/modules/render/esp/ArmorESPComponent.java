package ru.melonity.feature.features.modules.render.esp;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import net.minecraft.class_1893;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import net.minecraft.class_5321;
import net.minecraft.class_6880;
import net.minecraft.class_9304;
import org.joml.Vector4d;
import ru.melonity.font.Fonts;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class ArmorESPComponent implements ESPComponent {
   private static final Map<class_5321<class_1887>, String> enchantAbbreviations = Maps.newHashMap();

   @Override
   public void render(class_1657 entity, Vector4d position, class_4587 poseStack, class_332 graphics, RenderSystem renderSystem) {
      class_310 local6_6 = class_310.method_1551();
      float local7_7 = (float)position.x;
      float local8_8 = (float)position.y;
      float local9_9 = (float)(position.z - position.x);
      List<class_1799> local10_10 = Lists.newLinkedList();
      local10_10.addAll(Lists.newLinkedList(entity.method_5661()));
      local10_10.add(entity.method_6079());
      local10_10.add(entity.method_6047());
      Collections.reverse(local10_10);
      float local11_11 = 0.0F;

      for (class_1799 local13_0 : local10_10) {
         if (!local13_0.method_7960()) {
            local11_11 += 10.0F;
         }
      }

      float local12_12 = Fonts.INTER_REGULAR_14.getStringWidth(entity.method_5476().getString().replace("§", ""));
      float local13_13 = local7_7 + local9_9 / 2.0F - local11_11 / 2.0F;
      float local14_14 = local8_8 - 28.0F;
      poseStack.method_22903();
      poseStack.method_46416(local13_13, local14_14, 0.0F);
      poseStack.method_22905(0.5F, 0.5F, 0.5F);
      poseStack.method_46416(-local13_13, -local14_14, 0.0F);
      poseStack.method_46416(0.0F, 0.0F, -160.0F);

      for (class_1799 local16_5 : local10_10) {
         if (!local16_5.method_7960()) {
            graphics.method_51427(local16_5, (int)local13_13, (int)local14_14);
            graphics.method_51432(
               local6_6.field_1772, local16_5, (int)local13_13, (int)local14_14, local16_5.method_7947() > 1 ? String.valueOf(local16_5.method_7947()) : ""
            );
            if (local16_5.method_7942()) {
               float local17_3 = local14_14 - 10.0F;
               class_9304 local18_4 = class_1890.method_57532(local16_5);

               for (class_6880<class_1887> local20_2 : local18_4.method_57534()) {
                  if (!local20_2.method_40230().isEmpty()
                     && enchantAbbreviations.containsKey(local20_2.method_40230().get())
                     && local18_4.method_57536(local20_2) != 0) {
                     String local21_1 = enchantAbbreviations.get(local20_2.method_40230().get()) + local18_4.method_57536(local20_2);
                     renderSystem.addText(Fonts.INTER_MEDIUM_18, local21_1, local13_13, local17_3, poseStack);
                     local17_3 -= 12.0F;
                  }
               }
            }

            local13_13 += 19.0F;
         }
      }

      poseStack.method_22909();
   }

   @Override
   public String name() {
      return "esp.elements.armor";
   }

   static {
      enchantAbbreviations.put(class_1893.field_9111, "Pr");
      enchantAbbreviations.put(class_1893.field_9118, "Sh");
      enchantAbbreviations.put(class_1893.field_9131, "Ef");
      enchantAbbreviations.put(class_1893.field_9119, "Un");
      enchantAbbreviations.put(class_1893.field_9103, "Po");
   }
}
