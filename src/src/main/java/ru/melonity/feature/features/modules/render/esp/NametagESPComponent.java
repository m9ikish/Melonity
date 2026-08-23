package ru.melonity.feature.features.modules.render.esp;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import org.joml.Vector4d;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.minecraft.MinecraftInstance;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class NametagESPComponent implements ESPComponent {
   @Override
   public void render(class_1657 entity, Vector4d position, class_4587 poseStack, class_332 graphics, RenderSystem renderSystem) {
      float local6_0 = (float)position.x;
      float local7_1 = (float)position.y;
      float local8_2 = (float)(position.z - position.x);
      boolean local9_3 = Melonity.INSTANCE.getFeatureManager().triggerBotFeature.isEnabled()
         && MinecraftInstance.mc.field_1692 != null
         && MinecraftInstance.mc.field_1692.method_5628() == entity.method_5628();
      GlyphData local10_4 = Fonts.INTER_REGULAR_14;
      float[] local11_5 = new float[]{0.0F};
      entity.method_5476().method_30937().accept((index, style, codePoint) -> {
         String local5_0 = new String(Character.toChars(codePoint));
         local11_5[0] += local10_4.getStringWidth(local5_0);
         return true;
      });
      float local12_6 = local6_0 + local8_2 / 2.0F - local11_5[0] / 2.0F - 2.0F - (local9_3 ? 6.0F : 0.0F);
      renderSystem.addBlur(local12_6, local7_1 - 18.0F, local11_5[0] + 6.0F + (float)(local9_3 ? 12 : 0), 13.0F, 10.0F, 3.0F, poseStack);
      renderSystem.addRect(local12_6, local7_1 - 18.0F, local11_5[0] + 6.0F + (float)(local9_3 ? 12 : 0), 13.0F, 3.0F, new Color(24, 24, 24, 90), poseStack);
      float[] local13_7 = new float[]{local12_6 + (float)(local9_3 ? 12 : 0) + (local11_5[0] + 6.0F) / 2.0F - local11_5[0] / 2.0F};
      entity.method_5476().method_30937().accept((index, style, codePoint) -> {
         Color local8_0 = Color.WHITE;
         if (style.method_10973() != null) {
            local8_0 = new Color(style.method_10973().method_27716());
         }

         String local9_1 = new String(Character.toChars(codePoint));
         renderSystem.addText(local10_4, local9_1, local13_7[0], local7_1 - 13.0F, local8_0, poseStack);
         local13_7[0] += local10_4.getStringWidth(local9_1);
         return true;
      });
      if (local9_3) {
         renderSystem.addTexture(class_2960.method_60656("melonity/images/features/sword.png"), local12_6 + 4.0F, local7_1 - 16.0F, 9.0F, 9.0F, poseStack);
      }
   }

   @Override
   public String name() {
      return "esp.elements.nametag";
   }
}
