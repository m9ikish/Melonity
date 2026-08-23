package ru.melonity.ui.menu.clickui.view;

import lombok.SneakyThrows;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.feature.config.Config;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.ui.components.view.StyledView;
import ru.melonity.ui.components.view.impl.ButtonView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.web.CloudConfig;
import ru.melonity.util.web.StatusWithMessage;

@Environment(EnvType.CLIENT)
public class MenuConfigs extends StyledView {
   private List<CloudConfig> configs;
   private final MenuFeatureHeader header;
   private final ButtonView createButton;
   private final MelonityTextField textField;
   private final ButtonView addButton;
   private boolean last = false;

   public MenuConfigs(float width, float height) {
      super(width, height);
      this.updateConfigs(false);
      this.header = new MenuFeatureHeader("All configs", String.format("%d configs", this.configs == null ? 0 : this.configs.size()), width - 140.0F, 215.0F);
      this.createButton = new ButtonView(class_2960.method_60656("melonity/images/ui/add8x8.png"), "Create", 42.0F, 18.0F, 8.0F, 8.0F);
      this.addButton = new ButtonView("Add", 23.0F, 16.0F);
      this.textField = new MelonityTextField("Config code (XXXX-XXXX-XXXX-XXXX)", false, 314.0F, 16.0F);
   }

   @Override
   public void insert(MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      if (this.configs != null && this.configs.isEmpty()) {
         renderSystem.addText(
            Fonts.INTER_REGULAR_12,
            "No configs found",
            x + 110.0F + (this.width - 110.0F) / 2.0F - Fonts.INTER_REGULAR_12.getStringWidth("No configs found") / 2.0F,
            y + (this.height - 100.0F) / 2.0F,
            Color.decode("#888888"),
            poseStack
         );
      }

      if (this.configs == null) {
         long local10_0 = System.currentTimeMillis();
         float local12_1 = 360.0F - (float)(local10_0 % 1000L) / 1000.0F * 360.0F;
         renderSystem.addCircle(x + 50.0F + this.width / 2.0F, y - 45.0F + this.height / 2.0F, 0.0F, 360.0F, 10.0F, 3.0F, false, Color.DARK_GRAY, poseStack);
         renderSystem.addCircle(
            x + 50.0F + this.width / 2.0F, y - 45.0F + this.height / 2.0F, local12_1, local12_1 + 20.0F, 10.0F, 3.0F, false, style.ascent(), poseStack
         );
      } else {
         try {
            this.header.insert(style, x + 120.0F, y, mouseX, mouseY, renderSystem, poseStack);
            this.createButton.insert(x + this.width - 68.0F, y + 8.0F, mouseX, mouseY, renderSystem, poseStack);
            float local10_6 = y + 40.0F;

            for (CloudConfig local12_5 : this.configs) {
               if (!local12_5._id.equals("invalid")) {
                  ButtonView local13_2 = new ButtonView(class_2960.method_60656("melonity/images/ui/icons/play.png"), "Load", 36.0F, 17.0F, 8.0F, 8.0F);
                  local13_2.setTextColor(Color.WHITE);
                  if (Melonity.INSTANCE.getConfig() != null && Melonity.INSTANCE.getConfig().uuid.equals(local12_5.uuid)) {
                     local13_2.setIcon(class_2960.method_60656("melonity/images/ui/icons/navbar/save.png"));
                     local13_2.setText("Save");
                  }

                  boolean local14_3 = Melonity.INSTANCE.getConfig() != null && Melonity.INSTANCE.getConfig().uuid.equals(local12_5.uuid);
                  boolean local15_4 = MathUtil.isMouseHoveringRect((double)(x + 120.0F), (double)local10_6, 360.0, 24.0, mouseX, mouseY) || local14_3;
                  if (local15_4) {
                     renderSystem.addRect(x + 125.0F, local10_6, this.width - 150.0F, 24.0F, 6.0F, style.previewValues(), poseStack);
                  }

                  if (local14_3) {
                     renderSystem.addRect(x + 125.0F, local10_6, this.width - 150.0F, 24.0F, 6.0F, style.ascent(), poseStack);
                  }

                  local12_5.editField.setWritable(true);
                  local12_5.editField.setUndecorated(true);
                  local12_5.editField.insert(style, x + 130.0F, local10_6 + 7.0F, mouseX, mouseY, renderSystem, poseStack);
                  if (!local12_5.editField.isFocus()) {
                     renderSystem.addText(
                        Fonts.INTER_REGULAR_14,
                        local12_5.name,
                        x + 125.0F + 4.0F,
                        local10_6 + 11.0F,
                        local14_3 ? Color.decode("#141414") : (local15_4 ? Color.WHITE : Color.decode("#888888")),
                        poseStack
                     );
                  }

                  local13_2.insert(x + this.width - 66.0F, local10_6 + 4.0F, mouseX, mouseY, renderSystem, poseStack);
                  renderSystem.addTexture(
                     class_2960.method_60656("melonity/images/ui/icons/copy.png"),
                     x + this.width - 80.0F,
                     local10_6 + 8.0F,
                     8.0F,
                     8.5F,
                     local14_3
                        ? Color.decode("#181818")
                        : (
                           MathUtil.isMouseHoveringRect((double)(x + this.width - 80.0F), (double)(local10_6 + 8.0F), 8.0, 8.5, mouseX, mouseY)
                              ? Color.WHITE
                              : Color.decode("#888888")
                        ),
                     poseStack
                  );
                  renderSystem.addTexture(
                     class_2960.method_60656("melonity/images/ui/icons/delete.png"),
                     x + this.width - 93.0F,
                     local10_6 + 8.0F,
                     8.0F,
                     8.5F,
                     local14_3
                        ? Color.decode("#181818")
                        : (
                           MathUtil.isMouseHoveringRect((double)(x + this.width - 93.0F), (double)(local10_6 + 8.0F), 8.0, 8.5, mouseX, mouseY)
                              ? Color.WHITE
                              : Color.decode("#888888")
                        ),
                     poseStack
                  );
                  renderSystem.addTexture(
                     class_2960.method_60656(local12_5.isLiked() ? "melonity/images/ui/icons/heart_crossed.png" : "melonity/images/ui/icons/heartoutline.png"),
                     x + this.width - 105.0F,
                     local10_6 + 8.0F,
                     8.0F,
                     8.5F,
                     local14_3
                        ? Color.decode("#181818")
                        : (
                           MathUtil.isMouseHoveringRect((double)(x + this.width - 105.0F), (double)(local10_6 + 8.0F), 8.0, 8.5, mouseX, mouseY)
                              ? Color.WHITE
                              : Color.decode("#888888")
                        ),
                     poseStack
                  );
                  local10_6 += 25.0F;
               }
            }
         } catch (Throwable var16) {
         }
      }

      float local10_7 = y + this.height - 110.0F;
      renderSystem.addRect(x + 120.0F, local10_7, this.width - 140.0F, 1.0F, Color.decode("#222222"), poseStack);
      this.addButton.insert(x + this.width - 50.0F, local10_7 + 5.0F, mouseX, mouseY, renderSystem, poseStack);
      this.textField.insert(style, x + 127.0F, local10_7 + 5.0F, mouseX, mouseY, renderSystem, poseStack);
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
      int local8_4 = Melonity.INSTANCE.getUserInfo().userId();
      if (this.configs != null) {
         for (CloudConfig local10_0 : this.configs) {
            if (local10_0.editField.isFocus()) {
               return;
            }
         }

         for (CloudConfig local10_1 : this.configs) {
            local10_1.editField.setFocus(false);
         }

         if (MathUtil.isMouseHoveringRect(
            (double)(x + this.width - 68.0F), (double)(y + 8.0F), (double)this.createButton.getWidth(), (double)this.createButton.getHeight(), mouseX, mouseY
         )) {
            CompletableFuture.runAsync(() -> {
               try {
                  Melonity.INSTANCE.setConfig(CloudConfig.createNew(local8_4, "New config", Config.save()));
               } catch (Throwable var3) {
                  var3.printStackTrace();
               }

               this.updateConfigs(this.last);
            });
         }

         float local9_3 = y + 40.0F;

         for (CloudConfig local11_2 : this.configs) {
            if (!local11_2._id.equals("invalid")) {
               if (MathUtil.isMouseHoveringRect(
                     (double)(x + 130.0F),
                     (double)(local9_3 + 7.0F),
                     (double)local11_2.editField.getWidth(),
                     (double)local11_2.editField.getHeight(),
                     mouseX,
                     mouseY
                  )
                  && !local11_2.editField.isFocus()) {
                  local11_2.editField.setText(local11_2.name);
                  local11_2.editField.setFocus(true);
               }

               if (MathUtil.isMouseHoveringRect((double)(x + this.width - 66.0F), (double)(local9_3 + 4.0F), 36.0, 17.0, mouseX, mouseY)) {
                  CompletableFuture.runAsync(() -> {
                     try {
                        if (Melonity.INSTANCE.getConfig() != null && Melonity.INSTANCE.getConfig().uuid.equals(local11_2.uuid)) {
                           local11_2.updateConfig(local11_2.userId, local11_2.uuid, Config.save().toString());
                           Melonity.INSTANCE.getNotificationManager().add(new Notification("Config saved", "Successfully", "save.png"));
                        } else {
                           Config.load(local11_2.content);
                           Melonity.INSTANCE.setConfig(local11_2);
                           Melonity.INSTANCE.getNotificationManager().add(new Notification("Config loaded", "Successfully", "loaded.png"));
                        }
                     } catch (Throwable var2) {
                        var2.printStackTrace();
                     }
                  });
               }

               if (MathUtil.isMouseHoveringRect((double)(x + this.width - 80.0F), (double)(local9_3 + 8.0F), 8.0, 8.5, mouseX, mouseY)) {
                  CompletableFuture.runAsync(() -> {
                     try {
                        class_310.method_1551().field_1774.method_1455(local11_2.uuid);
                     } catch (Throwable var3) {
                        var3.printStackTrace();
                     }

                     this.updateConfigs(this.last);
                  });
               }

               if (MathUtil.isMouseHoveringRect((double)(x + this.width - 93.0F), (double)(local9_3 + 8.0F), 8.0, 8.5, mouseX, mouseY)) {
                  CompletableFuture.runAsync(() -> {
                     try {
                        CloudConfig.deleteConfig(local11_2.userId, local11_2.uuid);
                     } catch (Throwable var3) {
                        var3.printStackTrace();
                     }

                     this.updateConfigs(this.last);
                  });
               }

               if (MathUtil.isMouseHoveringRect((double)(x + this.width - 105.0F), (double)(local9_3 + 8.0F), 8.0, 8.5, mouseX, mouseY)) {
                  CompletableFuture.runAsync(() -> {
                     local11_2.setLiked(!local11_2.isLiked());
                     this.updateConfigs(this.last);
                  });
               }

               local9_3 += 25.0F;
            }
         }
      }

      float local9_5 = y + this.height - 110.0F;
      this.textField.setWritable(true);
      this.textField
         .setFocus(
            MathUtil.isMouseHoveringRect(
               (double)(x + 127.0F), (double)(local9_5 + 5.0F), (double)this.textField.getWidth(), (double)this.textField.getHeight(), mouseX, mouseY
            )
         );
      this.textField.click(x + 127.0F, local9_5 + 5.0F, mouseX, mouseY, mouseButton);
      if (MathUtil.isMouseHoveringRect(
            (double)(x + this.width - 50.0F), (double)(local9_5 + 5.0F), (double)this.addButton.getWidth(), (double)this.addButton.getHeight(), mouseX, mouseY
         )
         && this.textField.getText() != null
         && !this.textField.getText().isBlank()) {
         CompletableFuture.runAsync(() -> {
            try {
               StatusWithMessage local2_0 = CloudConfig.copyConfig(local8_4, this.textField.getText());
               if (local2_0.status()) {
                  Melonity.INSTANCE.getNotificationManager().add(new Notification("Config copied", "Copied in clipboard", "copy.png"));
               } else {
                  Melonity.INSTANCE.getNotificationManager().add(new Notification("Copy error", local2_0.message(), "error.png"));
               }
            } catch (Throwable var3) {
               var3.printStackTrace();
            }

            this.updateConfigs(this.last);
         });
      }
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      if (this.configs != null) {
         for (CloudConfig local5_0 : this.configs) {
            if (local5_0.editField.isWritable() && local5_0.editField.isFocus() && keyCode == 257) {
               local5_0.editField.setFocus(false);
               CompletableFuture.runAsync(() -> {
                  try {
                     StatusWithMessage local2_0 = local5_0.rename(local5_0.editField.getText());
                     if (!local2_0.status()) {
                        Melonity.INSTANCE.getNotificationManager().add(new Notification("Rename error", local2_0.message(), "error.png"));
                     }
                  } catch (Throwable var3) {
                     var3.printStackTrace();
                  }

                  this.updateConfigs(this.last);
               });
            }

            local5_0.editField.key(keyCode, scanCode, modifiers);
         }
      }

      return this.textField.key(keyCode, scanCode, modifiers);
   }

   @Override
   public void release() {
   }

   @SneakyThrows
   public void updateConfigs(boolean favourites) {
      int local2_0 = Melonity.INSTANCE.getUserInfo().userId();
      this.configs = null;
      this.last = favourites;
      CompletableFuture.runAsync(() -> {
         try {
            this.configs = CloudConfig.listConfigs(local2_0, 50);
         } catch (Throwable var4) {
            var4.printStackTrace();
         }

         if (this.configs != null && favourites) {
            this.configs = this.configs.stream().filter(CloudConfig::isLiked).toList();
         }

         this.header.setSubtitle(String.format("%d configs", this.configs == null ? 0 : this.configs.size()));
      });
   }
}
