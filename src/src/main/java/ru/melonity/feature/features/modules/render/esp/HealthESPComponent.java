package ru.melonity.feature.features.modules.render.esp;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import org.joml.Vector4d;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.service.friend.FriendService;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.minecraft.HealthResolveUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class HealthESPComponent implements ESPComponent {
   @Override
   public void render(class_1657 entity, Vector4d position, class_4587 poseStack, class_332 graphics, RenderSystem renderSystem) {
      float local6_0 = (float)position.x;
      float local7_1 = (float)position.y;
      float local8_2 = (float)(position.w - position.y);
      float local9_3 = (float)(position.z - position.x);
      MenuStyle local10_4 = Melonity.INSTANCE.styleManager.getStyle();
      Color local11_5 = local10_4.ascent();
      if (Melonity.INSTANCE.componentManager.service(FriendService.class).hasFriend(entity.method_5477().getString())) {
         local11_5 = Color.ORANGE;
      }

      float local12_6 = Math.min(7.0F, Math.max(4.0F, local9_3 / 14.0F));
      float local13_7 = Math.min(HealthResolveUtil.resolve(entity), entity.method_6063()) / entity.method_6063();
      renderSystem.addRect(local6_0 - local12_6 - 2.0F, local7_1, local12_6, local8_2, 6.0F, Color.decode("#222222"), poseStack);
      renderSystem.addRectGradient(
         local6_0 - local12_6 - 2.0F + 1.0F,
         local7_1 + 0.5F + local8_2 - local8_2 * local13_7,
         local12_6 - 2.0F,
         local8_2 * local13_7 - 1.0F,
         6.0F,
         local11_5,
         Color.WHITE,
         Color.WHITE,
         local11_5,
         poseStack
      );
      renderSystem.addBlur(
         local6_0 - 29.0F,
         local7_1 + local8_2 - 12.0F,
         Fonts.INTER_REGULAR_14.getStringWidth(String.format("%.1f", HealthResolveUtil.resolve(entity))) + 6.0F,
         12.0F,
         15.0F,
         6.0F,
         poseStack
      );
      renderSystem.addRect(
         local6_0 - 29.0F,
         local7_1 + local8_2 - 12.0F,
         Fonts.INTER_REGULAR_14.getStringWidth(String.format("%.1f", HealthResolveUtil.resolve(entity))) + 6.0F,
         12.0F,
         6.0F,
         new Color(24, 24, 24, 90),
         poseStack
      );
      renderSystem.addText(
         Fonts.INTER_REGULAR_14,
         String.format("%.1f", HealthResolveUtil.resolve(entity)).replace(',', '.'),
         local6_0 - 26.0F,
         local7_1 + local8_2 - 12.0F + 5.0F,
         local11_5,
         poseStack
      );
   }

   @Override
   public String name() {
      return "esp.elements.health";
   }
}
