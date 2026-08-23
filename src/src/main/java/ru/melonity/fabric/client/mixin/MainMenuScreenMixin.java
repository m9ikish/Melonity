package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_442;
import net.minecraft.class_4587;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IMainMenuStage;
import ru.melonity.mainmenu.MainMenuStage;
import ru.melonity.mainmenu.impl.ButtonsStage;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;

@Environment(EnvType.CLIENT)
@Mixin({class_442.class})
public abstract class MainMenuScreenMixin extends class_437 implements IMainMenuStage {
   @Unique
   private MainMenuStage stage;
   private static final int STAGE_WIDTH = 202;

   protected MainMenuScreenMixin(class_2561 title) {
      super(title);
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void render(class_332 graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo callbackInfo) {
      if (!class_310.field_1703) {
         if (Melonity.INSTANCE.getRenderSystem() != null) {
            if (this.stage == null) {
               this.stage = new ButtonsStage((class_442)(Object)this);
            }

            class_4587 poseStack = graphics.method_51448();
            ScaleUtils.rescale(2.0);
            float width = (float)class_310.method_1551().method_22683().method_4489() / 2.0F;
            float height = (float)class_310.method_1551().method_22683().method_4506() / 2.0F;
            RenderSystem renderSystem = Melonity.INSTANCE.getRenderSystem();
            renderSystem.addTexture(class_2960.method_60656("melonity/images/ui/mainmenu/background_main_menu.png"), 0.0F, 0.0F, width, height, poseStack);
            MenuStyle style = Melonity.INSTANCE.styleManager.getStyle();
            float stageWidth = 202.0F;
            float stageHeight = this.stage.getHeight();
            float stageX = width / 2.0F - stageWidth / 2.0F;
            float stageY = height / 2.0F - stageHeight / 2.0F;
            int fbHeight = class_310.method_1551().method_22683().method_4506();
            int scissorX = (int)(stageX * 2.0F);
            int scissorY = (int)((float)fbHeight - (stageY + stageHeight) * 2.0F);
            int scissorW = (int)(stageWidth * 2.0F);
            int scissorH = (int)(stageHeight * 2.0F);
            com.mojang.blaze3d.systems.RenderSystem.enableScissor(scissorX, scissorY, scissorW, scissorH);
            renderSystem.addRect(stageX, stageY, stageWidth, stageHeight, 12.0F, style.background(), poseStack);
            if (this.stage instanceof ButtonsStage) {
               this.stage.render(stageX + 8.0F, stageY + 4.0F, stageWidth - 16.0F, (double)mouseX, (double)mouseY, renderSystem, graphics.method_51448());
            } else {
               this.stage.render(stageX, stageY, stageWidth, (double)mouseX, (double)mouseY, renderSystem, graphics.method_51448());
            }

            com.mojang.blaze3d.systems.RenderSystem.disableScissor();
            if (height > 320.0F) {
               renderSystem.addTexture(
                  class_2960.method_60656("melonity/images/ui/mainmenu/main_menu_logo.png"),
                  width / 2.0F - 19.0F,
                  40.0F,
                  38.0F,
                  42.5F,
                  style.ascent(),
                  poseStack
               );
            }

            ScaleUtils.rescaleMC();
            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"mouseClicked"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
      if (!class_310.field_1703) {
         if (this.stage != null) {
            float width = (float)class_310.method_1551().method_22683().method_4489() / 2.0F;
            float height = (float)class_310.method_1551().method_22683().method_4506() / 2.0F;
            double scaleFactor = class_310.method_1551().method_22683().method_4495();
            double adjustedMouseX = mouseX * scaleFactor / 2.0;
            double adjustedMouseY = mouseY * scaleFactor / 2.0;
            float stageHeight = this.stage.getHeight();
            float stageX = width / 2.0F - 101.0F;
            float stageY = height / 2.0F - stageHeight / 2.0F;
            this.stage.click(stageX, stageY, 202.0F, adjustedMouseX, adjustedMouseY, button);
            cir.setReturnValue(true);
         }
      }
   }

   public boolean method_25404(int keyCode, int scanCode, int modifiers) {
      if (class_310.field_1703) {
         return super.method_25404(keyCode, scanCode, modifiers);
      } else if (this.stage == null) {
         return true;
      } else {
         this.stage.key(keyCode, scanCode, modifiers);
         return true;
      }
   }

   public boolean method_25401(double mouseX, double mouseY, double deltaY, double delta) {
      if (class_310.field_1703) {
         return super.method_25401(mouseX, mouseY, deltaY, delta);
      } else if (this.stage == null) {
         return true;
      } else {
         float width = (float)class_310.method_1551().method_22683().method_4489() / 2.0F;
         float height = (float)class_310.method_1551().method_22683().method_4506() / 2.0F;
         double scaleFactor = class_310.method_1551().method_22683().method_4495();
         double adjustedMouseX = mouseX * scaleFactor / 2.0;
         double adjustedMouseY = mouseY * scaleFactor / 2.0;
         float stageX = width / 2.0F - 101.0F;
         float stageY = height / 2.0F - this.stage.getHeight() / 2.0F;
         this.stage.scroll(stageX, stageY, 202.0F, delta, adjustedMouseX, adjustedMouseY);
         return true;
      }
   }

   @Override
   public MainMenuStage getStage() {
      return this.stage;
   }

   @Override
   public void setStage(MainMenuStage stage) {
      this.stage = stage;
   }
}
