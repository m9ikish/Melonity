package ru.melonity.feature.features.modules.render.esp;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1657;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import org.joml.Vector4d;
import ru.melonity.Melonity;
import ru.melonity.service.friend.FriendService;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class BoxESPComponent implements ESPComponent {
   @Override
   public void render(class_1657 entity, Vector4d position, class_4587 poseStack, class_332 graphics, RenderSystem renderSystem) {
      float local6_0 = (float)position.x;
      float local7_1 = (float)position.y;
      float local8_2 = (float)(position.z - position.x);
      float local9_3 = (float)(position.w - position.y);
      float local10_4 = local8_2 / 4.0F;
      float local11_5 = local9_3 / 4.0F;
      MenuStyle local12_6 = Melonity.INSTANCE.styleManager.getStyle();
      Color local13_7 = local12_6.ascent();
      if (Melonity.INSTANCE.componentManager.service(FriendService.class).hasFriend(entity.method_5477().getString())) {
         local13_7 = Color.ORANGE;
      }

      renderSystem.addRectSilent(local6_0, local7_1, local10_4, 1.5F, Color.decode("#222222"), poseStack);
      renderSystem.addRectSilent(local6_0, local7_1, 1.5F, local11_5, Color.decode("#222222"), poseStack);
      renderSystem.addRectSilent(local6_0 + 0.5F, local7_1 + 0.5F, local10_4 - 1.0F, 0.5F, local13_7, poseStack);
      renderSystem.addRectSilent(local6_0 + 0.5F, local7_1 + 0.5F, 0.5F, local11_5 - 1.0F, local13_7, poseStack);
      renderSystem.addRectSilent(local6_0 + local8_2 - local10_4, local7_1, local10_4, 1.5F, Color.decode("#222222"), poseStack);
      renderSystem.addRectSilent(local6_0 + local8_2, local7_1, 1.5F, local11_5, Color.decode("#222222"), poseStack);
      renderSystem.addRectSilent(local6_0 + local8_2 - local10_4 + 0.5F, local7_1 + 0.5F, local10_4, 0.5F, local13_7, poseStack);
      renderSystem.addRectSilent(local6_0 + local8_2 + 0.5F, local7_1 + 0.5F, 0.5F, local11_5 - 1.0F, local13_7, poseStack);
      renderSystem.addRectSilent(local6_0, local7_1 + local9_3, local10_4, 1.5F, Color.decode("#222222"), poseStack);
      renderSystem.addRectSilent(local6_0, local7_1 + local9_3 - local11_5, 1.5F, local11_5, Color.decode("#222222"), poseStack);
      renderSystem.addRectSilent(local6_0 + 0.5F, local7_1 + local9_3 + 0.5F, local10_4, 0.5F, local13_7, poseStack);
      renderSystem.addRectSilent(local6_0 + 0.5F, local7_1 + local9_3 - local11_5 + 0.5F, 0.5F, local11_5, local13_7, poseStack);
      renderSystem.addRectSilent(local6_0 + local8_2 - local10_4, local7_1 + local9_3, local10_4 + 1.0F, 1.5F, Color.decode("#222222"), poseStack);
      renderSystem.addRectSilent(local6_0 + local8_2, local7_1 + local9_3 - local11_5, 1.5F, local11_5 + 1.0F, Color.decode("#222222"), poseStack);
      renderSystem.addRectSilent(local6_0 + local8_2 - local10_4 + 0.5F, local7_1 + local9_3 + 0.5F, local10_4, 0.5F, local13_7, poseStack);
      renderSystem.addRectSilent(local6_0 + local8_2 + 0.5F, local7_1 + local9_3 - local11_5 + 0.5F, 0.5F, local11_5, local13_7, poseStack);
      renderSystem.addRectGradient(
         local6_0,
         local7_1,
         local8_2 + 1.0F,
         local9_3 + 1.0F,
         0.0F,
         RenderSystem.setAlpha(local13_7, 0),
         RenderSystem.setAlpha(local13_7, 128),
         RenderSystem.setAlpha(local13_7, 128),
         RenderSystem.setAlpha(local13_7, 0),
         poseStack
      );
   }

   @Override
   public String name() {
      return "esp.elements.box";
   }
}
