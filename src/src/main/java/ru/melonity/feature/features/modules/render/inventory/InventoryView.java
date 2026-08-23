package ru.melonity.feature.features.modules.render.inventory;

import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1041;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_465;
import net.minecraft.class_490;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.misc.PotionsHighlightFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.font.Fonts;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;

@Environment(EnvType.CLIENT)
public final class InventoryView {
   private static class_1799 cursor = null;
   private int hoveredSlot = -1;

   public void insert(class_332 context, class_465<?> handledScreen, class_310 minecraft, int mouseX2, int mouseY2) {
      class_1703 handler = handledScreen.method_17577();
      if (handler != null && handler.field_7761.size() >= 46) {
         RenderSystem renderSystem = Melonity.INSTANCE.getRenderSystem();
         MenuStyle style = Melonity.INSTANCE.getStyleManager().getStyle();
         class_4587 matrices = context.method_51448();
         cursor = null;
         this.hoveredSlot = -1;
         ScaleUtils.rescale(2.0);
         float x = (float)minecraft.method_22683().method_4480() / 4.0F - 90.0F;
         float y = (float)minecraft.method_22683().method_4507() / 4.0F - 85.0F;
         float width = 180.0F;
         float height = 170.0F;
         float headerHeight = 30.0F;
         renderSystem.addRect(x, y - headerHeight, width, height + headerHeight, 10.0F, style.background(), matrices);
         if (style.headerBlocks()) {
            renderSystem.addTexture(
               class_2960.method_60656("melonity/images/ui/inventory/overlay_blocks.png"),
               x,
               y - headerHeight,
               width,
               17.0F,
               style.blocksColor(),
               matrices
            );
         }

         renderSystem.addText(Fonts.INTER_MEDIUM_12, "Inventory", x + 8.0F, y - 20.0F, matrices);
         renderSystem.addRect(x, y - 4.0F, width, 1.0F, Color.decode("#222222"), matrices);
         Color slotColor = style.previewValues();
         this.renderEntityPreview(context, x, y, mouseX2, mouseY2);
         String[] armorIcons = new String[]{"helmet", "chestplate", "leggings", "boots"};

         for (int armorRow = 0; armorRow < 4; armorRow++) {
            int slotIndex = 5 + armorRow;
            float slotX = x + 9.0F;
            float slotY = y + 9.0F + (float)armorRow * 18.0F;
            class_1799 stack = handler.method_7611(slotIndex).method_7677();
            this.drawSlot(slotIndex, stack, slotX, slotY, mouseX2, mouseY2, slotColor, context);
            if (stack.method_7960()) {
               renderSystem.addTexture(
                  class_2960.method_60656("melonity/images/ui/inventory/" + armorIcons[armorRow] + ".png"),
                  slotX + 5.0F,
                  slotY + 5.0F,
                  8.0F,
                  8.0F,
                  new Color(255, 255, 255, 60),
                  matrices
               );
            }
         }

         this.drawSlot(45, handler.method_7611(45).method_7677(), x + 81.0F, y + 63.0F, mouseX2, mouseY2, slotColor, context);

         for (int craftSlot = 0; craftSlot < 4; craftSlot++) {
            float slotX = x + 117.0F + (float)(craftSlot % 2) * 18.0F;
            float slotY = y + 9.0F + (float)(craftSlot / 2) * 18.0F;
            this.drawSlot(craftSlot + 1, handler.method_7611(craftSlot + 1).method_7677(), slotX, slotY, mouseX2, mouseY2, slotColor, context);
         }

         float resultX = x + 126.0F;
         float resultY = y + 47.0F;
         class_1799 result = handler.method_7611(0).method_7677();
         this.drawSlot(0, result, resultX, resultY, mouseX2, mouseY2, slotColor, context);
         if (result.method_7960()) {
            renderSystem.addTexture(
               class_2960.method_60656("melonity/images/ui/inventory/craft_result.png"),
               resultX + 5.0F,
               resultY + 5.0F,
               8.0F,
               8.0F,
               new Color(255, 255, 255, 60),
               matrices
            );
         }

         renderSystem.addRect(x + 9.0F, y + 87.0F, width - 18.0F, 1.0F, Color.decode("#222222"), matrices);

         for (int mainSlot = 9; mainSlot < 36; mainSlot++) {
            int cell = mainSlot - 9;
            float slotX = x + 9.0F + (float)(cell % 9) * 18.0F;
            float slotY = y + 94.0F + (float)(cell / 9) * 18.0F;
            this.drawSlot(mainSlot, handler.method_7611(mainSlot).method_7677(), slotX, slotY, mouseX2, mouseY2, slotColor, context);
         }

         for (int hotbarSlot = 36; hotbarSlot < 45; hotbarSlot++) {
            float slotX = x + 9.0F + (float)(hotbarSlot - 36) * 18.0F;
            float slotY = y + 150.0F;
            this.drawSlot(hotbarSlot, handler.method_7611(hotbarSlot).method_7677(), slotX, slotY, mouseX2, mouseY2, slotColor, context);
         }

         ScaleUtils.rescaleMC();
      }
   }

   private void renderEntityPreview(class_332 context, float x, float y, int mouseX, int mouseY) {
      class_4587 local6_0 = context.method_51448();
      local6_0.method_22903();
      class_1041 local7_1 = class_310.method_1551().method_22683();
      float local8_2 = (float)local7_1.method_4495() / 2.0F;
      local6_0.method_22905(local8_2, local8_2, 1.0F);
      RotationManager.INSTANCE.setInInventory(true);
      class_490.method_2486(
         context,
         (int)((x + 28.0F) / local8_2),
         (int)((y + 8.0F) / local8_2),
         (int)((x + 28.0F + 49.0F) / local8_2),
         (int)((y + 8.0F + 70.0F) / local8_2),
         (int)(30.0F / local8_2),
         0.0625F,
         (float)mouseX / local8_2,
         (float)mouseY / local8_2,
         class_310.method_1551().field_1724
      );
      RotationManager.INSTANCE.setInInventory(false);
      local6_0.method_22909();
   }

   private void drawSlot(int slot, class_1799 itemStack, float x, float y, int mouseX, int mouseY, Color slotColor, class_332 context) {
      float local9_0 = 2.0F;
      float local10_1 = x * local9_0 / (float)((Integer)class_310.method_1551().field_1690.method_42474().method_41753()).intValue();
      float local11_2 = y * local9_0 / (float)((Integer)class_310.method_1551().field_1690.method_42474().method_41753()).intValue();
      RenderSystem local12_3 = Melonity.INSTANCE.getRenderSystem();
      boolean local13_4 = MathUtil.isMouseHoveringRect(
         (double)local10_1,
         (double)local11_2,
         (double)(32.0F / (float)((Integer)class_310.method_1551().field_1690.method_42474().method_41753()).intValue()),
         (double)(32.0F / (float)((Integer)class_310.method_1551().field_1690.method_42474().method_41753()).intValue()),
         (double)mouseX,
         (double)mouseY
      );
      class_4587 local14_5 = context.method_51448();
      local12_3.addRect(x, y, 18.0F, 18.0F, 5.0F, local13_4 ? slotColor.brighter() : slotColor, context.method_51448());
      Melonity.INSTANCE
         .getFeatureManager()
         .getFeature(PotionsHighlightFeature.class)
         .ifPresent(highlightFeature -> highlightFeature.potionSlotHighlight(itemStack, x, y, 18.0F, 5.0F, local14_5));
      local14_5.method_22903();
      local14_5.method_46416(x, y, 0.0F);
      local14_5.method_22905(0.75F, 0.75F, 0.75F);
      local14_5.method_46416(-x, -y, 0.0F);
      context.method_51427(itemStack, (int)x + 4, (int)y + 4);
      context.method_51432(
         class_310.method_1551().field_1772,
         itemStack,
         (int)(x + 5.0F),
         (int)(y + 5.0F),
         itemStack.method_7947() > 1 ? String.valueOf(itemStack.method_7947()) : ""
      );
      local14_5.method_22909();
      if (local13_4) {
         cursor = itemStack;
         this.hoveredSlot = slot;
      }
   }

   public boolean keyPressed(int keyCode, int scanCode) {
      class_310 local3_1 = class_310.method_1551();

      for (int local4_0 = 0; local4_0 < 9; local4_0++) {
         if (local3_1.field_1690.field_1852[local4_0].method_1417(keyCode, scanCode) && this.hoveredSlot != -1) {
            local3_1.field_1761.method_2906(0, this.hoveredSlot, local4_0, class_1713.field_7791, local3_1.field_1724);
            return true;
         }
      }

      if (local3_1.field_1690.field_1869.method_1417(keyCode, scanCode) && this.hoveredSlot != -1) {
         local3_1.field_1761.method_2906(0, this.hoveredSlot, class_437.method_25441() ? 1 : 0, class_1713.field_7795, local3_1.field_1724);
         return true;
      } else if (local3_1.field_1690.field_1831.method_1417(keyCode, scanCode) && this.hoveredSlot != -1) {
         local3_1.field_1761.method_2906(0, this.hoveredSlot, 40, class_1713.field_7791, local3_1.field_1724);
         return true;
      } else {
         return false;
      }
   }

   @Generated
   public static class_1799 cursor() {
      return cursor;
   }

   @Generated
   public int hoveredSlot() {
      return this.hoveredSlot;
   }
}
