package ru.melonity.feature.features.modules.misc.autobuy;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1747;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import net.minecraft.class_7923;
import net.minecraft.class_9334;
import org.joml.Vector2i;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IItemStack;
import ru.melonity.feature.features.modules.misc.AutoBuyFeature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.impl.ButtonView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;
import ru.melonity.util.render.ScissorUtil;

@Environment(EnvType.CLIENT)
public class AutobuyScreen extends class_437 {
   private int offset = 0;
   private int offset2 = 0;
   private int selected = 1;
   private final AutobuyScreen.AutobuyButton buttonView = new AutobuyScreen.AutobuyButton(
      class_2960.method_60656("melonity/images/ui/autobuy/autobuy_add.png"), "Add item", 172.5F, 16.0F, 8.5F, 8.0F
   );
   private final AutobuyScreen.AutobuyButton startButtonView = new AutobuyScreen.AutobuyButton(
      class_2960.method_60656("melonity/images/ui/autobuy/autobuy_start.png"), "Start", 172.5F, 16.0F, 8.5F, 8.0F
   );
   private final List<class_1799> autoItems = Lists.newLinkedList();
   private ModificationsScreen dialog = null;
   private boolean contextMenu;
   private int dialogI2;
   private float clickedX;
   private float clickedY;
   private static final List<class_1799> items = class_7923.field_41178
      .method_10220()
      .filter(item -> !(item instanceof class_1747))
      .distinct()
      .<class_1799>map(class_1799::new)
      .collect(Collectors.toList());

   public AutobuyScreen() {
      super(class_2561.method_43470("Autobuy"));
      items.add(new class_1799(class_1802.field_8574));
      items.add(new class_1799(class_1802.field_8436));
   }

   public void method_25394(class_332 drawContext, int mouseX, int mouseY, float partialTicks) {
      super.method_25394(drawContext, mouseX, mouseY, partialTicks);
      class_4587 local5_8 = drawContext.method_51448();
      Vector2i local6_9 = ScaleUtils.getMouseDouble((double)mouseX, (double)mouseY);
      mouseX = local6_9.x;
      mouseY = local6_9.y;
      ScaleUtils.rescale(2.0);
      this.offset2 = Math.max(0, this.offset2);
      float local7_10 = 377.0F;
      float local8_11 = 282.0F;
      float local9_12 = (float)this.field_22787.method_22683().method_4480() / 4.0F - local7_10 / 2.0F;
      float local10_13 = (float)this.field_22787.method_22683().method_4507() / 4.0F - local8_11 / 2.0F;
      RenderSystem local11_14 = Melonity.INSTANCE.getRenderSystem();
      MenuStyle local12_15 = Melonity.INSTANCE.styleManager.getStyle();
      local11_14.addRect(local9_12, local10_13, local7_10, local8_11, local12_15.background(), local5_8);
      if (this.dialog != null) {
         this.dialog.insert(local9_12, local10_13, mouseX, mouseY, local11_14, local5_8, drawContext);
      } else {
         local11_14.addTexture(
            class_2960.method_60656("melonity/images/ui/overlay_blocks.png"),
            local9_12,
            local10_13 - 17.0F + 2.0F,
            local7_10,
            17.0F,
            local12_15.background(),
            local5_8
         );
         local11_14.addRect(local9_12, local10_13 + 36.0F, local7_10, 1.0F, Color.decode("#222222"), local5_8);
         local11_14.addText(Fonts.INTER_MEDIUM_14, "AutoBuy", local9_12 + 8.0F, local10_13 + 14.0F, local5_8);
         local11_14.addText(
            Fonts.INTER_MEDIUM_13, "Automatically buy items from auction", local9_12 + 8.0F, local10_13 + 24.0F, Color.decode("#888888"), local5_8
         );
         if (AutoBuyFeature.enabled) {
            this.startButtonView.setText("Stop");
         } else {
            if (!AutoBuyFeature.valid.isEmpty()) {
               AutoBuyFeature.valid.clear();
            }

            this.startButtonView.setText("Start");
         }

         local11_14.addRect(local9_12 + 6.0F, local10_13 + 36.0F + 6.0F, local7_10 / 2.0F - 8.0F, local8_11 - 72.0F, 8.0F, local12_15.autobuyPanel(), local5_8);
         local11_14.addText(Fonts.INTER_MEDIUM_14, "All items", local9_12 + 12.0F, local10_13 + 36.0F + 15.0F, local5_8);
         local11_14.addRect(local9_12 + 6.0F, local10_13 + 36.0F + 6.0F + 21.0F, local7_10 / 2.0F - 8.0F, 1.0F, Color.decode("#222222"), local5_8);
         local11_14.addRect(
            local9_12 + 6.0F + local7_10 / 2.0F - 4.0F,
            local10_13 + 36.0F + 6.0F,
            local7_10 / 2.0F - 8.0F,
            local8_11 - 72.0F,
            8.0F,
            local12_15.autobuyPanel(),
            local5_8
         );
         local11_14.addText(Fonts.INTER_MEDIUM_14, "Selected items", local9_12 - 4.0F + 6.0F + local7_10 / 2.0F + 6.0F, local10_13 + 36.0F + 15.0F, local5_8);
         local11_14.addRect(
            local9_12 + 6.0F + local7_10 / 2.0F - 4.0F, local10_13 + 36.0F + 6.0F + 21.0F, local7_10 / 2.0F - 8.0F, 1.0F, Color.decode("#222222"), local5_8
         );
         ScissorUtil.beginScissor((double)local9_12, (double)(local10_13 + 40.0F), (double)local7_10, (double)(local8_11 - 72.0F));
         float local13_16 = local9_12 + 14.0F;
         float local14_17 = local10_13 + 73.0F;

         for (int local15_1 = 1 + this.offset * 4; local15_1 < items.size(); local15_1++) {
            if (local15_1 == this.selected) {
               local11_14.addRect(local13_16 - 0.5F, local14_17 - 0.5F, 41.0F, 41.0F, 8.0F, local12_15.ascent(), local5_8);
            }

            local11_14.addRect(
               local13_16,
               local14_17,
               40.0F,
               40.0F,
               8.0F,
               local15_1 == this.selected ? local12_15.ascent().darker().darker().darker() : local12_15.background().brighter(),
               local5_8
            );
            class_1799 local16_0 = items.get(local15_1);
            if (local16_0.method_57824(class_9334.field_49631) != null) {
               local11_14.addText(
                  Fonts.INTER_REGULAR_10,
                  local16_0.method_7964().getString(),
                  local13_16 + 20.5F - Fonts.INTER_REGULAR_10.getStringWidth(local16_0.method_7964().getString()) / 2.0F - 1.0F,
                  local14_17 + 6.0F,
                  local5_8
               );
            }

            local5_8.method_22903();
            local5_8.method_46416(local13_16, local14_17, 0.0F);
            local5_8.method_22905(1.4F, 1.4F, 1.4F);
            local5_8.method_46416(-local13_16, -local14_17, 0.0F);
            drawContext.method_51427(local16_0, (int)(local13_16 + 6.0F), (int)(local14_17 + 6.0F));
            local5_8.method_22909();
            local13_16 += 40.0F;
            if (local15_1 != 0 && local15_1 != 2 && local15_1 % 4 == 0) {
               local13_16 = local9_12 + 14.0F;
               local14_17 += 40.5F;
            }
         }

         ScissorUtil.endScissor();
         local11_14.addTexture(
            class_2960.method_60656("melonity/images/ui/autobuy/bucket_text.png"), local9_12 + local7_10 - 58.0F, local10_13 + 46.0F, 41.5F, 14.5F, local5_8
         );
         if (MathUtil.isMouseHoveringRect((double)(local9_12 + local7_10 - 58.0F), (double)(local10_13 + 46.0F), 41.5, 14.5, (double)mouseX, (double)mouseY)) {
            local11_14.addTexture(
               class_2960.method_60654("melonity/images/ui/autobuy/bucket_text.png"),
               local9_12 + local7_10 - 58.0F,
               local10_13 + 46.0F,
               41.5F,
               14.5F,
               new Color(255, 255, 255, 100),
               local5_8
            );
         }

         ScissorUtil.beginScissor((double)local9_12, (double)(local10_13 + 40.0F), (double)local7_10, (double)(local8_11 - 72.0F));
         local13_16 = local9_12 + 185.0F + 14.0F;
         local14_17 = local10_13 + 73.0F;

         for (int local15_4 = 1 + this.offset2 * 4; local15_4 < this.autoItems.size(); local15_4++) {
            local11_14.addRect(local13_16, local14_17, 40.0F, 40.0F, 8.0F, local12_15.background().brighter(), local5_8);

            try {
               class_1799 local16_2 = this.autoItems.get(local15_4);
               if (local16_2.method_57824(class_9334.field_49631) != null) {
                  local11_14.addText(
                     Fonts.INTER_REGULAR_10,
                     local16_2.method_7964().getString(),
                     local13_16 + 20.5F - Fonts.INTER_REGULAR_10.getStringWidth(local16_2.method_7964().getString()) / 2.0F - 1.0F,
                     local14_17 + 6.0F,
                     local5_8
                  );
               }

               local5_8.method_22903();
               local5_8.method_46416(local13_16, local14_17, 0.0F);
               local5_8.method_22905(1.4F, 1.4F, 1.4F);
               local5_8.method_46416(-local13_16, -local14_17, 0.0F);
               drawContext.method_51427(local16_2, (int)(local13_16 + 6.0F), (int)(local14_17 + 6.0F));
               local5_8.method_22909();
            } catch (Throwable var21) {
               var21.printStackTrace();
               local5_8.method_22909();
            }

            local13_16 += 40.0F;
            if (local15_4 != 0 && local15_4 != 2 && local15_4 % 4 == 0) {
               local13_16 = local9_12 + 185.0F + 14.0F;
               local14_17 += 40.5F;
            }
         }

         ScissorUtil.endScissor();
         float local15_18 = 73.0F;
         local11_14.addRect(
            local9_12 + 181.0F,
            local10_13 + 72.0F + (float)this.offset / local15_18 * 95.0F,
            3.0F,
            83.5F,
            4.0F,
            local12_15.background().brighter().brighter(),
            local5_8
         );
         float local16_19 = (float)this.autoItems.size() / 4.0F - 4.0F;
         local11_14.addRect(
            local9_12 + 181.0F + 185.0F,
            local10_13 + 72.0F + (float)this.offset2 / local16_19 * 100.0F,
            3.0F,
            83.5F,
            4.0F,
            local12_15.background().brighter().brighter(),
            local5_8
         );
         local11_14.addRect(local9_12 + 6.0F, local10_13 + local8_11 - 25.0F, local7_10 - 12.0F, 22.0F, 8.0F, local12_15.autobuyPanel(), local5_8);
         this.buttonView.insert(local9_12 + 12.0F, local10_13 + local8_11 - 36.0F + 14.0F, (double)mouseX, (double)mouseY, local11_14, local5_8);
         this.startButtonView
            .insert(
               local9_12 + this.buttonView.getWidth() + 6.0F + 14.0F,
               local10_13 + local8_11 - 36.0F + 14.0F,
               (double)mouseX,
               (double)mouseY,
               local11_14,
               local5_8
            );
         if (this.contextMenu) {
            com.mojang.blaze3d.systems.RenderSystem.disableDepthTest();
            com.mojang.blaze3d.systems.RenderSystem.disableBlend();
            local11_14.addRect(this.clickedX, this.clickedY, 62.0F, 42.0F, 8.0F, local12_15.background().brighter(), local5_8);
            List<String> local17_6 = Arrays.asList("Settings", "Delete");
            float local18_7 = this.clickedY + 4.0F;

            for (String local20_5 : local17_6) {
               local11_14.addRect(this.clickedX + 3.5F, local18_7 - 0.5F, 55.0F, 17.0F, 5.0F, local12_15.background().brighter().brighter(), local5_8);
               local11_14.addRect(this.clickedX + 4.0F, local18_7, 54.0F, 16.0F, 5.0F, local12_15.background().brighter(), local5_8);
               if (MathUtil.isMouseHoveringRect((double)(this.clickedX + 4.0F), (double)local18_7, 54.0, 16.0, (double)mouseX, (double)mouseY)) {
                  local11_14.addRect(this.clickedX + 4.0F, local18_7, 54.0F, 16.0F, 5.0F, new Color(255, 255, 255, 10), local5_8);
               }

               local11_14.addText(Fonts.INTER_REGULAR_13, local20_5, this.clickedX + 8.0F, local18_7 + 7.0F, Color.decode("#888888"), local5_8);
               local18_7 += 19.0F;
            }

            com.mojang.blaze3d.systems.RenderSystem.enableBlend();
            com.mojang.blaze3d.systems.RenderSystem.enableDepthTest();
         }

         ScaleUtils.rescaleMC();
      }
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      Vector2i local6_8 = ScaleUtils.getMouseDouble(mouseX, mouseY);
      mouseX = (double)local6_8.x;
      mouseY = (double)local6_8.y;
      float local7_9 = 377.0F;
      float local8_10 = 282.0F;
      float local9_11 = (float)this.field_22787.method_22683().method_4480() / 4.0F - local7_9 / 2.0F;
      float local10_12 = (float)this.field_22787.method_22683().method_4507() / 4.0F - local8_10 / 2.0F;
      if (MathUtil.isMouseHoveringRect(
         (double)(local9_11 + this.buttonView.getWidth() + 6.0F + 10.0F),
         (double)(local10_12 + local8_10 - 36.0F + 13.0F),
         (double)this.startButtonView.getWidth(),
         (double)this.startButtonView.getHeight(),
         mouseX,
         mouseY
      )) {
         AutoBuyFeature.valid.clear();

         for (class_1799 local12_1 : this.autoItems) {
            IItemStack local13_0 = (IItemStack)(Object)local12_1;

            try {
               if (local13_0.price() > 0) {
                  AutoBuyFeature.valid.put(local12_1, local13_0.price());
               }
            } catch (Throwable var15) {
            }
         }

         AutoBuyFeature.enabled = true;
         this.method_25419();
         if (!AutoBuyFeature.wasScanned) {
            AutoBuyFeature.scan = true;
            Melonity.INSTANCE.getNotificationManager().add(new Notification("AutoBuy", "Auction not scanned", "tips.png"));
         } else {
            this.field_22787.field_1724.method_43496(class_2561.method_43470("/ah"));
         }
      }

      if (this.contextMenu) {
         List<String> local11_3 = Arrays.asList("Settings", "Delete");
         float local12_4 = this.clickedY + 4.0F;

         for (String local14_2 : local11_3) {
            if (MathUtil.isMouseHoveringRect((double)(this.clickedX + 4.0F), (double)local12_4, 54.0, 16.0, mouseX, mouseY)) {
               if (local11_3.indexOf(local14_2) == 1) {
                  this.autoItems.remove(this.dialogI2);
                  this.dialogI2 = -1;
               } else if (local11_3.indexOf(local14_2) == 0) {
                  this.dialog = new ModificationsScreen(this.autoItems.get(this.dialogI2));
               }

               this.contextMenu = false;
            }

            local12_4 += 19.0F;
         }
      }

      if (this.dialog != null) {
         class_1799 local11_5;
         if ((local11_5 = this.dialog.click(this.autoItems.get(this.dialogI2), local9_11, local10_12, mouseX, mouseY, button)) != null) {
            this.dialog = null;
            if (local11_5.method_7909() == class_1802.field_8077) {
               this.autoItems.remove(this.dialogI2);
            } else {
               this.autoItems.set(this.dialogI2, local11_5);
            }
         }

         return true;
      } else {
         if (MathUtil.isMouseHoveringRect((double)(local9_11 + local7_9 - 58.0F), (double)(local10_12 + 46.0F), 41.5, 14.5, mouseX, mouseY)) {
            this.autoItems.clear();
         }

         float local11_13 = local9_11 + 14.0F;
         float local12_14 = local10_12 + 73.0F;

         for (int local13_6 = 1 + this.offset * 4; local13_6 < items.size(); local13_6++) {
            if (!(local12_14 > local10_12 + local8_10 - 70.0F)) {
               if (MathUtil.isMouseHoveringRect((double)local11_13, (double)local12_14, 40.0, 40.0, mouseX, mouseY)) {
                  this.selected = local13_6;
               }

               local11_13 += 40.0F;
               if (local13_6 != 0 && local13_6 != 2 && local13_6 % 4 == 0) {
                  local11_13 = local9_11 + 14.0F;
                  local12_14 += 40.5F;
               }
            }
         }

         local11_13 = local9_11 + 185.0F + 14.0F;
         local12_14 = local10_12 + 73.0F;

         for (int local13_7 = 1 + this.offset2 * 4; local13_7 < this.autoItems.size(); local13_7++) {
            try {
               if (MathUtil.isMouseHoveringRect((double)local11_13, (double)local12_14, 40.0, 40.0, mouseX, mouseY)) {
                  this.contextMenu = !this.contextMenu;
                  this.dialogI2 = local13_7;
                  this.clickedX = (float)mouseX;
                  this.clickedY = (float)mouseY;
                  break;
               }
            } catch (Throwable var16) {
            }

            local11_13 += 40.0F;
            if (local13_7 != 0 && local13_7 != 2 && local13_7 % 4 == 0) {
               local11_13 = local9_11 + 185.0F + 14.0F;
               local12_14 += 40.5F;
            }
         }

         if (MathUtil.isMouseHoveringRect(
            (double)(local9_11 + 12.0F),
            (double)(local10_12 + local8_10 - 36.0F + 14.0F),
            (double)this.buttonView.getWidth(),
            (double)this.buttonView.getHeight(),
            mouseX,
            mouseY
         )) {
            this.autoItems.add(items.get(this.selected));
         }

         return super.method_25402(mouseX, mouseY, button);
      }
   }

   public boolean method_25404(int keyCode, int scanCode, int modifiers) {
      if (this.dialog != null) {
         this.dialog.key(keyCode, scanCode, modifiers);
      }

      return super.method_25404(keyCode, scanCode, modifiers);
   }

   public boolean method_25406(double mouseX, double mouseY, int button) {
      return super.method_25406(mouseX, mouseY, button);
   }

   public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double delta) {
      Vector2i local9_0 = ScaleUtils.getMouseDouble(mouseX, mouseY);
      mouseX = (double)local9_0.x;
      mouseY = (double)local9_0.y;
      float local10_1 = 377.0F;
      float local11_2 = 282.0F;
      float local12_3 = (float)this.field_22787.method_22683().method_4480() / 4.0F - local10_1 / 2.0F;
      float local13_4 = (float)this.field_22787.method_22683().method_4507() / 4.0F - local11_2 / 2.0F;
      if (MathUtil.isMouseHoveringRect((double)local12_3, (double)(local13_4 + 40.0F), 180.0, (double)(local11_2 - 72.0F), mouseX, mouseY)) {
         if (delta > 0.0) {
            this.offset--;
         } else if (delta < 0.0) {
            this.offset++;
         }

         if (this.offset < 0) {
            this.offset = 0;
         }

         if (this.offset > 78) {
            this.offset = 78;
         }
      } else if (MathUtil.isMouseHoveringRect((double)(local12_3 + 185.0F), (double)(local13_4 + 40.0F), 180.0, (double)(local11_2 - 72.0F), mouseX, mouseY)) {
         if (delta > 0.0) {
            this.offset2--;
         } else if (delta < 0.0) {
            this.offset2++;
         }

         if (this.offset2 < 0) {
            this.offset2 = 0;
         }

         if (this.offset2 > (int)((float)this.autoItems.size() / 4.0F) - 4) {
            this.offset2 = (int)((float)this.autoItems.size() / 4.0F) - 4;
         }
      }

      return super.method_25401(mouseX, mouseY, horizontalAmount, delta);
   }

   public boolean method_25421() {
      return false;
   }

   @Environment(EnvType.CLIENT)
   private static class AutobuyButton extends ButtonView {
      public AutobuyButton(class_2960 icon, String text, float width, float height, float iconWidth, float iconHeight) {
         super(icon, text, width, height, iconWidth, iconHeight);
      }

      @Override
      public void insert(float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
         this.setHoveredState(MathUtil.isMouseHoveringRect((double)x, (double)y, (double)this.getWidth(), (double)this.getHeight(), mouseX, mouseY));
         MenuStyle local9_0 = Melonity.INSTANCE.styleManager.getStyle();
         renderSystem.addRect(x, y, this.getWidth(), this.getHeight(), 6.0F, local9_0.background().brighter().brighter(), poseStack);
         renderSystem.addRect(
            x + 0.5F,
            y + 0.5F,
            this.getWidth() - 1.0F,
            this.getHeight() - 1.0F,
            6.0F,
            this.isHoveredState() ? local9_0.background().brighter() : local9_0.background(),
            poseStack
         );
         renderSystem.addRect(x + 0.5F, y + this.getHeight() - 2.0F, this.getWidth() - 1.0F, 2.0F, 6.0F, local9_0.background().brighter().brighter(), poseStack);
         renderSystem.addText(
            Fonts.INTER_MEDIUM_14,
            this.getText(),
            x + 3.0F + this.getWidth() / 2.0F - Fonts.INTER_MEDIUM_14.getStringWidth(this.getText()) / 2.0F,
            y + 6.5F,
            Color.decode("#888888"),
            poseStack
         );
         renderSystem.addTexture(
            this.getIcon(),
            x + this.getWidth() / 2.0F - Fonts.INTER_MEDIUM_14.getStringWidth(this.getText()) / 2.0F - this.getIconWidth() + 1.0F,
            y + 4.0F,
            this.getIconWidth(),
            this.getIconHeight(),
            poseStack
         );
      }
   }
}
