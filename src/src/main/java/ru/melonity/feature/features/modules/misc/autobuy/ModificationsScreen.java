package ru.melonity.feature.features.modules.misc.autobuy;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1799;
import net.minecraft.class_1812;
import net.minecraft.class_1842;
import net.minecraft.class_1844;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import net.minecraft.class_5321;
import net.minecraft.class_6880;
import net.minecraft.class_7923;
import net.minecraft.class_7924;
import net.minecraft.class_9304;
import net.minecraft.class_9334;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IItemStack;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.ui.components.view.impl.ButtonView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScissorUtil;

@Environment(EnvType.CLIENT)
public class ModificationsScreen {
   private final List<class_1887> selectedEnchants = Lists.newLinkedList();
   private final List<class_1291> selectedPotions = Lists.newLinkedList();
   private int category = 0;
   private final class_1799 it;
   private final ModificationsScreen.CenteredButton cancelButtonView = new ModificationsScreen.CenteredButton("Add item", 172.5F, 16.0F, false);
   private final ModificationsScreen.CenteredButton createButtonView = new ModificationsScreen.CenteredButton("Create", 172.5F, 16.0F, true);
   private final MelonityTextField displayNameField = new MelonityTextField("", false, 105.0F, 14.0F);
   private final MelonityTextField countField = new MelonityTextField("1", false, 105.0F, 14.0F);
   private final MelonityTextField priceField = new MelonityTextField("", false, 105.0F, 14.0F);
   private final BooleanSetting autoSell = new BooleanSetting("Auto sell", false);
   private final BooleanSetting bestPrice = new BooleanSetting("Best price", false);
   private static final List<class_1887> enchantEntries = new ArrayList<>();
   private static final List<Entry<class_5321<class_1291>, class_1291>> potionEntries = class_7923.field_41174
      .method_29722()
      .stream()
      .filter(entry -> entry != class_1294.field_5908 && entry != class_1294.field_5900 && entry != class_1294.field_16595 && entry != class_1294.field_18980)
      .toList();

   public ModificationsScreen(class_1799 itemStack) {
      Optional<class_6880<class_1842>> local2_1 = ((class_1844)itemStack.method_57825(class_9334.field_49651, class_1844.field_49274)).comp_2378();
      local2_1.ifPresent(potionHolder -> {
         for (class_1293 local3_0 : ((class_1842)potionHolder.comp_349()).method_8049()) {
            this.selectedPotions.add((class_1291)local3_0.method_5579().comp_349());
         }
      });
      class_1890.method_57532(itemStack).method_57534().forEach(enchantmentHolder -> this.selectedEnchants.add((class_1887)enchantmentHolder.comp_349()));
      this.countField.setText("1");
      this.priceField.setText("10000");
      this.countField.setWritable(true);
      this.priceField.setWritable(true);
      this.displayNameField.setWritable(true);
      this.it = itemStack;
      if (enchantEntries.isEmpty()) {
         for (class_1887 local4_0 : class_310.method_1551().field_1687.method_30349().method_30530(class_7924.field_41265)) {
            enchantEntries.add(local4_0);
         }
      }
   }

   public void insert(float x, float y, int mouseX, int mouseY, RenderSystem renderSystem, class_4587 poseStack, class_332 context) {
      MenuStyle style = Melonity.INSTANCE.styleManager.getStyle();
      float panelWidth = 377.0F;
      float panelHeight = 282.0F;
      renderSystem.addTexture(
         class_2960.method_60656("melonity/images/ui/overlay_blocks.png"), x, y - 15.0F, panelWidth, 17.0F, style.background(), poseStack
      );
      boolean backHovered = MathUtil.isMouseHoveringRect((double)(x + 8.0F), (double)(y + 13.0F), 16.0, 16.0, (double)mouseX, (double)mouseY);
      renderSystem.addRect(x + 8.0F, y + 13.0F, 16.0F, 16.0F, 6.0F, backHovered ? style.background().brighter() : style.previewValues(), poseStack);
      renderSystem.addTexture(
         class_2960.method_60656("melonity/images/ui/autobuy/back_button.png"),
         x + 12.0F,
         y + 17.0F,
         8.0F,
         8.0F,
         backHovered ? Color.WHITE : Color.decode("#888888"),
         poseStack
      );
      poseStack.method_22903();
      poseStack.method_46416(x + 30.0F, y + 12.0F, 0.0F);
      poseStack.method_22905(0.75F, 0.75F, 0.75F);
      poseStack.method_46416(-(x + 30.0F), -(y + 12.0F), 0.0F);
      context.method_51427(this.it, (int)(x + 30.0F), (int)(y + 12.0F));
      poseStack.method_22909();
      renderSystem.addText(Fonts.INTER_MEDIUM_14, this.it.method_7964().getString(), x + 46.0F, y + 14.0F, poseStack);
      renderSystem.addText(Fonts.INTER_MEDIUM_13, "Configure item before adding", x + 46.0F, y + 24.0F, Color.decode("#888888"), poseStack);
      renderSystem.addRect(x, y + 36.0F, panelWidth, 1.0F, Color.decode("#222222"), poseStack);
      String[] tabNames = new String[]{"Item", "Enchantments", "Effects"};
      float tabX = x + 10.0F;

      for (int tab = 0; tab < 3; tab++) {
         boolean tabSelected = this.category == tab;
         boolean tabHovered = MathUtil.isMouseHoveringRect((double)tabX, (double)(y + 45.0F), 115.0, 18.0, (double)mouseX, (double)mouseY);
         renderSystem.addRect(tabX, y + 45.0F, 115.0F, 18.0F, 6.0F, tabSelected ? style.ascent() : style.background().brighter(), poseStack);
         if (!tabSelected) {
            renderSystem.addRect(tabX + 0.5F, y + 45.5F, 114.0F, 17.0F, 6.0F, tabHovered ? style.background().brighter() : style.autobuyPanel(), poseStack);
         }

         renderSystem.addText(
            Fonts.INTER_MEDIUM_13,
            tabNames[tab],
            tabX + 57.5F - Fonts.INTER_MEDIUM_13.getStringWidth(tabNames[tab]) / 2.0F,
            y + 50.5F,
            tabSelected ? Color.decode("#141414") : Color.decode("#888888"),
            poseStack
         );
         if (tabHovered) {
            Melonity.hand = true;
         }

         tabX += 121.0F;
      }

      this.priceField.insert(style, x + 13.0F, y + 62.0F, (double)mouseX, (double)mouseY, renderSystem, poseStack);
      this.displayNameField.insert(style, x + 135.0F, y + 62.0F, (double)mouseX, (double)mouseY, renderSystem, poseStack);
      this.countField.insert(style, x + 260.0F, y + 62.0F, (double)mouseX, (double)mouseY, renderSystem, poseStack);
      renderSystem.addText(Fonts.INTER_REGULAR_10, "Price", x + 13.0F, y + 80.0F, Color.decode("#888888"), poseStack);
      renderSystem.addText(Fonts.INTER_REGULAR_10, "Display name", x + 135.0F, y + 80.0F, Color.decode("#888888"), poseStack);
      renderSystem.addText(Fonts.INTER_REGULAR_10, "Count", x + 260.0F, y + 80.0F, Color.decode("#888888"), poseStack);
      renderSystem.addRect(x, y + 90.0F, panelWidth, 1.0F, Color.decode("#222222"), poseStack);
      ScissorUtil.beginScissor((double)x, (double)(y + 94.0F), (double)panelWidth, 84.0);
      float chipX = x + 13.0F;
      float chipY = y + 98.0F;
      if (!(this.it.method_7909() instanceof class_1812)) {
         for (class_1887 enchantment : enchantEntries) {
            String enchantName = enchantment.comp_2686().getString().toLowerCase();
            float chipWidth = Fonts.INTER_MEDIUM_14.getStringWidth(enchantName) + 8.0F;
            boolean chipSelected = this.selectedEnchants.contains(enchantment);
            boolean chipHovered = MathUtil.isMouseHoveringRect((double)chipX, (double)chipY, (double)chipWidth, 12.5, (double)mouseX, (double)mouseY);
            renderSystem.addRect(
               chipX,
               chipY,
               chipWidth,
               12.5F,
               4.0F,
               chipSelected ? style.ascent() : (chipHovered ? style.background().brighter() : style.previewValues()),
               poseStack
            );
            renderSystem.addText(
               Fonts.INTER_MEDIUM_14,
               enchantName,
               chipX + 4.0F,
               chipY + 3.5F,
               chipSelected ? Color.decode("#141414") : Color.decode("#888888"),
               poseStack
            );
            chipX += chipWidth + 0.5F;
            if (chipX > x + 281.0F + 23.0F) {
               chipX = x + 13.0F;
               chipY += 13.0F;
            }
         }
      } else {
         for (Entry<class_5321<class_1291>, class_1291> potion : potionEntries) {
            String potionName = potion.getValue().method_5560().getString().toLowerCase();
            float chipWidth = Fonts.INTER_MEDIUM_14.getStringWidth(potionName) + 8.0F;
            boolean chipSelected = this.selectedPotions.contains(potion.getValue());
            boolean chipHovered = MathUtil.isMouseHoveringRect((double)chipX, (double)chipY, (double)chipWidth, 12.5, (double)mouseX, (double)mouseY);
            renderSystem.addRect(
               chipX,
               chipY,
               chipWidth,
               12.5F,
               4.0F,
               chipSelected ? style.ascent() : (chipHovered ? style.background().brighter() : style.previewValues()),
               poseStack
            );
            renderSystem.addText(
               Fonts.INTER_MEDIUM_14,
               potionName,
               chipX + 4.0F,
               chipY + 3.5F,
               chipSelected ? Color.decode("#141414") : Color.decode("#888888"),
               poseStack
            );
            chipX += chipWidth + 0.5F;
            if (chipX > x + 281.0F + 23.0F) {
               chipX = x + 13.0F;
               chipY += 13.0F;
            }
         }
      }

      ScissorUtil.endScissor();
      this.autoSell.insert(style, x + 14.0F, y + 185.0F, 100.0F, (double)mouseX, (double)mouseY, renderSystem, poseStack, context);
      this.bestPrice.insert(style, x + 64.0F, y + 185.0F, 100.0F, (double)mouseX, (double)mouseY, renderSystem, poseStack, context);
      renderSystem.addRect(x + 6.0F, y + panelHeight - 25.0F, panelWidth - 12.0F, 22.0F, 8.0F, style.autobuyPanel(), poseStack);
      this.cancelButtonView.insert(x + 12.0F, y + panelHeight - 36.0F + 14.0F, (double)mouseX, (double)mouseY, renderSystem, poseStack);
      this.createButtonView
         .insert(x + 12.0F + this.cancelButtonView.getWidth() + 8.0F, y + panelHeight - 36.0F + 14.0F, (double)mouseX, (double)mouseY, renderSystem, poseStack);
      if (backHovered) {
         Melonity.hand = true;
      }
   }

   public class_1799 click(class_1799 itemStack, float x, float y, double mouseX, double mouseY, int mouseButton) {
      float local9_13 = 282.0F;
      this.autoSell.click(x + 14.0F, y + 185.0F, 100.0F, mouseX, mouseY, mouseButton);
      this.bestPrice.click(x + 14.0F + 50.0F, y + 185.0F, 100.0F, mouseX, mouseY, mouseButton);
      if (!MathUtil.isMouseHoveringRect((double)(x + 8.0F), (double)(y + 13.0F), 16.0, 16.0, mouseX, mouseY)
         && !MathUtil.isMouseHoveringRect(
            (double)(x + 12.0F),
            (double)(y + local9_13 - 36.0F + 14.0F),
            (double)this.cancelButtonView.getWidth(),
            (double)this.cancelButtonView.getHeight(),
            mouseX,
            mouseY
         )) {
         if (MathUtil.isMouseHoveringRect(
            (double)(x + 13.0F), (double)(y + 62.0F), (double)this.priceField.getWidth(), (double)this.priceField.getHeight(), mouseX, mouseY
         )) {
            this.priceField.setFocus(true);
            this.countField.setFocus(false);
            this.displayNameField.setFocus(false);
         } else if (MathUtil.isMouseHoveringRect(
            (double)(x + 135.0F), (double)(y + 62.0F), (double)this.displayNameField.getWidth(), (double)this.displayNameField.getHeight(), mouseX, mouseY
         )) {
            this.priceField.setFocus(false);
            this.countField.setFocus(false);
            this.displayNameField.setFocus(true);
         } else if (MathUtil.isMouseHoveringRect(
            (double)(x + 260.0F), (double)(y + 62.0F), (double)this.displayNameField.getWidth(), (double)this.displayNameField.getHeight(), mouseX, mouseY
         )) {
            this.priceField.setFocus(false);
            this.displayNameField.setFocus(false);
            this.countField.setFocus(true);
         }

         float local10_14 = x + 10.0F;

         for (int local11_4 = 0; local11_4 < 3; local11_4++) {
            if (MathUtil.isMouseHoveringRect((double)local10_14, (double)(y + 45.0F), 115.0, 18.0, mouseX, mouseY)) {
               this.category = local11_4;
            }

            local10_14 += 121.0F;
         }

         if (!(this.it.method_7909() instanceof class_1812)) {
            float local11_7 = x + 13.0F;
            float local12_8 = y + 98.0F;

            for (class_1887 local14_6 : enchantEntries) {
               float local15_5 = Fonts.INTER_MEDIUM_14.getStringWidth(local14_6.comp_2686().getString().toLowerCase()) + 8.0F;
               if (MathUtil.isMouseHoveringRect((double)local11_7, (double)local12_8, (double)local15_5, 12.5, mouseX, mouseY)) {
                  if (this.selectedEnchants.contains(local14_6)) {
                     this.selectedEnchants.remove(local14_6);
                  } else {
                     this.selectedEnchants.add(local14_6);
                  }
               }

               local11_7 += local15_5 + 0.5F;
               if (local11_7 > x + 281.0F + 23.0F) {
                  local11_7 = x + 13.0F;
                  local12_8 += 13.0F;
               }
            }
         } else {
            float local11_11 = x + 13.0F;
            float local12_12 = y + 98.0F;

            for (Entry<class_5321<class_1291>, class_1291> local14_10 : potionEntries) {
               float local15_9 = Fonts.INTER_MEDIUM_14.getStringWidth(local14_10.getValue().method_5560().getString().toLowerCase()) + 8.0F;
               if (MathUtil.isMouseHoveringRect((double)local11_11, (double)local12_12, (double)local15_9, 12.5, mouseX, mouseY)) {
                  if (this.selectedPotions.contains(local14_10.getValue())) {
                     this.selectedPotions.remove(local14_10.getValue());
                  } else {
                     this.selectedPotions.add(local14_10.getValue());
                  }
               }

               local11_11 += local15_9 + 0.5F;
               if (local11_11 > x + 281.0F + 23.0F) {
                  local11_11 = x + 13.0F;
                  local12_12 += 13.0F;
               }
            }
         }

         return null;
      } else {
         class_1799 local10_1 = new class_1799(itemStack.method_7909());
         IItemStack local11_2 = (IItemStack)(Object)local10_1;

         int parsedPrice = 100;
         if (!this.priceField.getText().isBlank()) {
            try {
               parsedPrice = Integer.parseInt(this.priceField.getText().trim());
            } catch (NumberFormatException priceFormatFailure) {
               System.err.println(
                  "[Melonity] ModificationsScreen: invalid price '" + this.priceField.getText() + "', falling back to 100"
               );
            }
         }

         local11_2.setPrice(parsedPrice);

         local10_1.method_57379(class_9334.field_49633, class_9304.field_49385);
         this.selectedEnchants.forEach(enchantment -> local10_1.method_7978(class_6880.method_40223(enchantment), 1));
         class_1844 local12_3 = class_1844.field_49274;

         for (class_1291 local14_0 : this.selectedPotions) {
            local12_3 = local12_3.method_57398(new class_1293(class_6880.method_40223(local14_0), 600, 1));
         }

         local10_1.method_57379(class_9334.field_49651, local12_3);

         // countField is built with number=false, so it accepts arbitrary text. The
         // parse used to sit at the top of a try whose catch was empty, which meant a
         // single stray character silently threw away the display name, Auto sell and
         // Best price along with the count. Only the parse can fail - keep it alone.
         int parsedCount = 1;
         if (!this.countField.getText().isBlank()) {
            try {
               parsedCount = Integer.parseInt(this.countField.getText().trim());
            } catch (NumberFormatException countFormatFailure) {
               System.err.println(
                  "[Melonity] ModificationsScreen: invalid count '" + this.countField.getText() + "', falling back to 1"
               );
            }
         }

         local10_1.method_7939(parsedCount);
         if (!this.displayNameField.getText().isBlank()) {
            local10_1.method_57379(class_9334.field_49631, class_2561.method_43470(this.displayNameField.getText()));
         }

         local11_2.setAutosell(this.autoSell.get());
         local11_2.setBestPrice(this.bestPrice.get());

         return local10_1;
      }
   }

   public void key(int keyCode, int scanCode, int modifiers) {
      this.displayNameField.key(keyCode, scanCode, modifiers);
      this.priceField.key(keyCode, scanCode, modifiers);
      this.countField.key(keyCode, scanCode, modifiers);

      try {
         int local4_0 = Integer.parseInt(this.countField.getText());
         if (local4_0 > 64) {
            this.countField.setText("64");
         }
      } catch (Throwable var5) {
      }
   }

   @Environment(EnvType.CLIENT)
   private static class CenteredButton extends ButtonView {
      private final boolean filled;

      public CenteredButton(String text, float width, float height, boolean filled) {
         super(text, width, height);
         this.filled = filled;
      }

      @Override
      public void insert(float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
         this.setHoveredState(MathUtil.isMouseHoveringRect((double)x, (double)y, (double)this.getWidth(), (double)this.getHeight(), mouseX, mouseY));
         renderSystem.addRect(
            x,
            y,
            this.getWidth(),
            this.getHeight(),
            6.0F,
            this.filled ? Melonity.INSTANCE.styleManager.getStyle().ascent() : Color.decode("#2C2C2C"),
            poseStack
         );
         renderSystem.addRect(
            x + 0.5F,
            y + 0.5F,
            this.getWidth() - 1.0F,
            this.getHeight() - 1.0F,
            6.0F,
            this.filled ? Melonity.INSTANCE.styleManager.getStyle().ascent() : Color.decode("#222222"),
            poseStack
         );
         renderSystem.addRect(
            x + 0.5F,
            y + this.getHeight() - 2.0F,
            this.getWidth() - 1.0F,
            2.0F,
            6.0F,
            this.filled ? Melonity.INSTANCE.styleManager.getStyle().ascent() : Color.decode("#2C2C2C"),
            poseStack
         );
         if (MathUtil.isMouseHoveringRect((double)x, (double)y, (double)this.getWidth(), (double)this.getHeight(), mouseX, mouseY)) {
            renderSystem.addRect(x, y, this.getWidth(), this.getHeight(), 6.0F, new Color(255, 255, 255, 10), poseStack);
         }

         renderSystem.addText(
            Fonts.INTER_MEDIUM_14,
            this.getText(),
            x + this.getWidth() / 2.0F - Fonts.INTER_MEDIUM_14.getStringWidth(this.getText()) / 2.0F,
            y + 6.5F,
            this.filled ? Color.BLACK : Color.decode("#888888"),
            poseStack
         );
      }
   }
}
