package ru.melonity.feature.features.modules.render.esp;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import org.joml.Vector4d;
import ru.melonity.font.Fonts;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class ItemsESPComponent implements ESPComponent {
   @Override
   public void render(class_1657 entity, Vector4d position, class_4587 poseStack, class_332 graphics, RenderSystem renderSystem) {
      if (!entity.method_6079().method_7960()) {
         float local6_0 = (float)position.x;
         float local7_1 = 6.0F;
         float local8_2 = (float)position.y + local7_1;
         float local9_3 = (float)(position.z - position.x);
         float local10_4 = (float)(position.w - position.y) + local7_1;
         GlyphData local11_5 = Fonts.INTER_REGULAR_14;
         String local12_6 = entity.method_6079().method_7964().getString();
         float local13_7 = local11_5.getStringWidth(local12_6);
         float local14_8 = local6_0 + local9_3 / 2.0F - local13_7 / 2.0F - 2.0F;
         renderSystem.addRect(local14_8, local8_2 + local10_4 - 5.0F, local13_7 + 6.0F, 13.0F, 3.0F, new Color(24, 24, 24, 90), poseStack);
         renderSystem.addText(local11_5, local12_6, local14_8 + 2.0F, local8_2 + local10_4, poseStack);
      }
   }

   @Override
   public String name() {
      return "esp.elements.items";
   }
}
