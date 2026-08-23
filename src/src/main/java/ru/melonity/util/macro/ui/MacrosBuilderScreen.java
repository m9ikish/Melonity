package ru.melonity.util.macro.ui;

import lombok.SneakyThrows;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import org.joml.Vector2i;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.ui.components.view.impl.ButtonView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.animation.Easings;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.macro.Action;
import ru.melonity.util.macro.ActionCategory;
import ru.melonity.util.macro.Macro;
import ru.melonity.util.macro.MacroService;
import ru.melonity.util.macro.actions.AbstractFlexAction;
import ru.melonity.util.macro.actions.AbstractToggleFunction;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;
import ru.melonity.util.render.ScissorUtil;

@Environment(EnvType.CLIENT)
public class MacrosBuilderScreen extends class_437 {
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(370L);
   private final StateAnimation scaleAnimation = new StateAnimation();
   private float x;
   private float y;
   private final float width = 376.0F;
   private final float height = 270.0F;
   private final List<Action> structureActions = Lists.newCopyOnWriteArrayList();
   private float leftScroll = 0.0F;
   private float leftScrollCurrent = 0.0F;
   private boolean leftScrollInit = false;
   private final FrameWeightCalculator leftScrollFwc = FrameWeightCalculator.milliseconds(500L);
   private Action drag = null;
   private int dragIndex = -1;
   private float dragX;
   private float dragY;
   private float prevX;
   private float prevY;
   private final MelonityTextField nameTextField = new MelonityTextField("Macro name", false, 115.0F, 16.0F);
   private final ButtonView saveButton = new ButtonView(class_2960.method_60656("melonity/images/ui/icons/navbar/save.png"), "Save", 114.0F, 16.0F, 8.0F, 8.0F);
   private final ButtonView saveAndWheel = new ButtonView(
      class_2960.method_60656("melonity/images/ui/add.png"), "Save and add to wheel", 115.0F, 16.0F, 10.0F, 10.0F
   );
   private final Macro macro;

   public MacrosBuilderScreen(Macro macro) {
      super(class_2561.method_43470("Macro builder"));
      this.saveButton.setIconColor(Color.decode("#888888"));
      this.saveButton.setCenterText(true);
      this.saveButton.setCenterIcon(true);
      this.saveAndWheel.setCenterText(true);
      this.saveAndWheel.setCenterIcon(true);
      this.macro = macro;
      if (macro != null) {
         this.nameTextField.setWritable(true);
         this.nameTextField.setText(macro.getName());
         this.structureActions.addAll(macro.getActions());

         for (Action local3_1 : this.structureActions) {
            if (local3_1 instanceof AbstractFlexAction local4_0) {
               local4_0.setPlaced(true);
            }
         }
      }
   }

   private void clearAllFocusExcept(MelonityTextField exceptField) {
      if (this.nameTextField != exceptField) {
         this.nameTextField.setFocus(false);
      }

      for (Action local3_0 : this.structureActions) {
         this.clearActionFocus(local3_0, exceptField);
      }
   }

   private void clearActionFocus(Action action, MelonityTextField exceptField) {
      try {
         Field[] local3_4 = action.getClass().getDeclaredFields();

         for (Field local7_1 : local3_4) {
            if (local7_1.getType().equals(MelonityTextField.class)) {
               local7_1.setAccessible(true);
               MelonityTextField local8_0 = (MelonityTextField)local7_1.get(action);
               if (local8_0 != null && local8_0 != exceptField) {
                  local8_0.setFocus(false);
               }
            }
         }

         Field[] local4_5 = action.getClass().getSuperclass().getDeclaredFields();

         for (Field local8_3 : local4_5) {
            if (local8_3.getType().equals(MelonityTextField.class)) {
               local8_3.setAccessible(true);
               MelonityTextField local9_2 = (MelonityTextField)local8_3.get(action);
               if (local9_2 != null && local9_2 != exceptField) {
                  local9_2.setFocus(false);
               }
            }
         }
      } catch (Exception var10) {
      }
   }

   public void method_25394(class_332 graphics, int mouseX, int mouseY, float partialTicks) {
      super.method_25394(graphics, mouseX, mouseY, partialTicks);
      class_4587 poseStack = graphics.method_51448();
      Vector2i scaledMouse = ScaleUtils.getMouseDouble((double)mouseX, (double)mouseY);
      int mx = scaledMouse.x;
      int my = scaledMouse.y;
      ScaleUtils.rescale(2.0);
      RenderSystem renderSystem = Melonity.INSTANCE.getRenderSystem();
      MenuStyle style = Melonity.INSTANCE.styleManager.getStyle();
      MacroService macroService = Melonity.INSTANCE.getMacroService();
      this.x = (float)this.field_22787.method_22683().method_4480() / 4.0F - this.width / 2.0F;
      this.y = (float)this.field_22787.method_22683().method_4507() / 4.0F - this.height / 2.0F;
      this.scaleAnimation.state(true);
      this.scaleAnimation.update(this.fwc.elapsedUnits());
      float scale = (float)Easings.CUBIC_BOTH.ease((double)this.scaleAnimation.animation());
      float pivotX = this.x + this.width / 2.0F;
      float pivotY = this.y + this.height / 2.0F;
      poseStack.method_22903();
      poseStack.method_46416(pivotX, pivotY, 0.0F);
      poseStack.method_22905(scale, scale, 1.0F);
      poseStack.method_46416(-pivotX, -pivotY, 0.0F);
      renderSystem.addRect(this.x, this.y, this.width, this.height, 10.0F, style.background(), poseStack);
      if (style.headerBlocks()) {
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/overlay_blocks.png"), this.x, this.y, this.width, 17.0F, style.blocksColor(), poseStack
         );
      }

      boolean backHovered = MathUtil.isMouseHoveringRect((double)(this.x + 8.0F), (double)(this.y + 8.0F), 16.0, 16.0, (double)mx, (double)my);
      renderSystem.addRect(this.x + 8.0F, this.y + 8.0F, 16.0F, 16.0F, 6.0F, backHovered ? style.background().brighter() : style.previewValues(), poseStack);
      renderSystem.addTexture(
         class_2960.method_60656("melonity/images/ui/autobuy/back_button.png"),
         this.x + 12.0F,
         this.y + 12.0F,
         8.0F,
         8.0F,
         backHovered ? Color.WHITE : Color.decode("#888888"),
         poseStack
      );
      renderSystem.addText(Fonts.INTER_MEDIUM_14, "Macro builder", this.x + 30.0F, this.y + 10.0F, poseStack);
      renderSystem.addText(Fonts.INTER_MEDIUM_13, "Drag actions to build your macro", this.x + 30.0F, this.y + 20.0F, Color.decode("#888888"), poseStack);
      renderSystem.addRect(this.x, this.y + 36.0F, this.width, 1.0F, Color.decode("#222222"), poseStack);
      renderSystem.addRect(this.x + 6.0F, this.y + 70.0F, 182.0F, 158.0F, 8.0F, style.feature().background(), poseStack);
      renderSystem.addRect(this.x + 190.0F, this.y + 70.0F, 180.0F, 158.0F, 8.0F, style.feature().background(), poseStack);
      renderSystem.addText(Fonts.INTER_MEDIUM_14, "Structure", this.x + 12.0F, this.y + 56.0F, poseStack);
      renderSystem.addText(Fonts.INTER_MEDIUM_14, "Actions", this.x + 199.0F, this.y + 56.0F, poseStack);
      if (!this.leftScrollInit) {
         this.leftScrollCurrent = this.leftScroll;
         this.leftScrollInit = true;
      }

      this.leftScrollCurrent = this.leftScrollCurrent + (this.leftScroll - this.leftScrollCurrent) * 14.0F * this.leftScrollFwc.elapsedUnits();
      boolean overStructure = MathUtil.isMouseHoveringRect((double)this.x, (double)(this.y + 74.0F), 188.0, 150.0, (double)mx, (double)my);
      this.dragIndex = -1;
      ScissorUtil.beginScissor((double)this.x, (double)(this.y + 74.0F), 188.0, 150.0);
      float rowY = this.y + 75.0F + this.leftScrollCurrent;
      int slot = 0;

      for (Action action : this.structureActions) {
         float rowHeight = 20.0F + action.getOffset();
         if (this.drag != null && this.dragIndex == -1 && overStructure && (double)my < (double)(rowY + rowHeight / 2.0F)) {
            this.dragIndex = slot;
            renderSystem.addRect(this.x + 12.0F, rowY - 2.0F, 164.0F, 1.5F, 1.0F, style.ascent(), poseStack);
         }

         this.renderActionWithSettings(action, this.x + 12.0F, rowY, mx, my, renderSystem, poseStack);
         boolean deleteHovered = overStructure
            && MathUtil.isMouseHoveringRect((double)(this.x + 163.0F), (double)(rowY + 3.5F), 8.0, 8.0, (double)mx, (double)my);
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/icons/delete.png"),
            this.x + 163.0F,
            rowY + 3.5F,
            8.0F,
            8.0F,
            deleteHovered ? Color.WHITE : Color.decode("#888888"),
            poseStack
         );
         if (deleteHovered) {
            Melonity.hand = true;
         }

         rowY += rowHeight;
         slot++;
      }

      if (this.drag != null && this.dragIndex == -1 && overStructure) {
         this.dragIndex = this.structureActions.size();
         renderSystem.addRect(this.x + 12.0F, rowY - 2.0F, 164.0F, 1.5F, 1.0F, style.ascent(), poseStack);
      }

      if (this.structureActions.isEmpty()) {
         String hint = "Drag an action here";
         renderSystem.addText(
            Fonts.INTER_REGULAR_12,
            hint,
            this.x + 97.0F - Fonts.INTER_REGULAR_12.getStringWidth(hint) / 2.0F,
            this.y + 145.0F,
            Color.decode("#888888"),
            poseStack
         );
      }

      ScissorUtil.endScissor();
      ScissorUtil.beginScissor((double)(this.x + 190.0F), (double)(this.y + 74.0F), 180.0, 150.0);
      float paletteY = this.y + 75.0F;

      for (ActionCategory category : ActionCategory.values()) {
         String categoryKey = category.name().toLowerCase();
         String categoryLabel = category.name().charAt(0) + category.name().substring(1).toLowerCase();
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/macros/category/" + categoryKey + ".png"),
            this.x + 199.0F,
            paletteY + 3.0F,
            8.0F,
            8.0F,
            style.ascent(),
            poseStack
         );
         renderSystem.addText(Fonts.INTER_REGULAR_12, categoryLabel, this.x + 211.0F, paletteY + 3.5F, Color.decode("#888888"), poseStack);
         paletteY += 15.0F;

         for (Action action : macroService.getActionByCategory(category)) {
            this.renderAction(action, this.x + 199.0F, paletteY, mx, my, renderSystem, poseStack);
            if (MathUtil.isMouseHoveringRect((double)(this.x + 199.0F), (double)paletteY, 164.0, 16.0, (double)mx, (double)my)) {
               renderSystem.addRect(this.x + 199.0F, paletteY, 164.0F, 16.0F, 8.0F, new Color(255, 255, 255, 10), poseStack);
               Melonity.hand = true;
            }

            paletteY += 20.0F;
         }

         paletteY += 5.0F;
      }

      ScissorUtil.endScissor();
      float bottomY = this.y + this.height - 36.0F;
      renderSystem.addRect(this.x + 6.0F, bottomY, this.width - 12.0F, 30.0F, 8.0F, style.feature().background(), poseStack);
      this.nameTextField.insert(style, this.x + 13.0F, bottomY + 5.0F, (double)mx, (double)my, renderSystem, poseStack);
      this.saveButton.insert(this.x + 131.0F, bottomY + 5.0F, (double)mx, (double)my, renderSystem, poseStack);
      this.saveAndWheel.insert(this.x + 248.0F, bottomY + 5.0F, (double)mx, (double)my, renderSystem, poseStack);
      if (this.drag != null) {
         this.dragX = (float)((int)mx) - this.prevX;
         this.dragY = (float)((int)my) - this.prevY;
         this.renderAction(this.drag, this.dragX, this.dragY, mx, my, renderSystem, poseStack);
      }

      if (backHovered) {
         Melonity.hand = true;
      }

      poseStack.method_22909();
      ScaleUtils.rescaleMC();
   }

   private void renderActionWithSettings(Action action, float x, float y, int mouseX, int mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      if (!(action instanceof AbstractFlexAction)) {
         action.renderSettings(x, y, 164.0F, 16.0F, mouseX, mouseY, renderSystem, poseStack);
      }

      this.renderAction(action, x, y, mouseX, mouseY, renderSystem, poseStack);
      if (action instanceof AbstractFlexAction) {
         action.renderSettings(x, y, 164.0F, 16.0F, mouseX, mouseY, renderSystem, poseStack);
      }
   }

   private void renderAction(Action action, float x, float y, int mouseX, int mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      renderSystem.addRect(x, y, 164.0F, 16.0F, 8.0F, Melonity.INSTANCE.styleManager.getStyle().previewValues(), poseStack);
      renderSystem.addTexture(class_2960.method_60656("melonity/images/ui/macros/grip.png"), x + 4.0F, y + 3.5F, 8.0F, 8.5F, poseStack);
      renderSystem.addText(
         Fonts.INTER_REGULAR_14, action.getName(), x + 15.0F, y + 6.5F, Melonity.INSTANCE.styleManager.getStyle().tab().disabledTextColor(), poseStack
      );
   }

   public boolean method_25402(double mouseX, double mouseY, int button) {
      Vector2i local6_5 = ScaleUtils.getMouseDouble(mouseX, mouseY);
      mouseX = (double)local6_5.x;
      mouseY = (double)local6_5.y;
      MacroService local7_6 = Melonity.INSTANCE.getMacroService();
      if (MathUtil.isMouseHoveringRect((double)(this.x + 8.0F), (double)(this.y + 8.0F), 16.0, 16.0, mouseX, mouseY)) {
         this.field_22787.method_1507(Melonity.INSTANCE.getMenu());
      }

      float local8_7 = this.y + 270.0F - 36.0F;
      boolean local9_8 = MathUtil.isMouseHoveringRect(
         (double)(this.x + 248.0F), (double)(local8_7 + 5.0F), (double)this.saveButton.getWidth(), (double)this.saveButton.getHeight(), mouseX, mouseY
      );
      if ((
            MathUtil.isMouseHoveringRect(
                  (double)(this.x + 131.0F), (double)(local8_7 + 5.0F), (double)this.saveButton.getWidth(), (double)this.saveButton.getHeight(), mouseX, mouseY
               )
               || local9_8
         )
         && !this.nameTextField.getText().isBlank()) {
         if (this.macro != null) {
            this.macro.getActions().clear();
            this.macro.getActions().addAll(this.structureActions);
         } else {
            if (local7_6.getMacro(this.nameTextField.getText()).isPresent()) {
               Melonity.INSTANCE.getNotificationManager().add(new Notification("Error", "Macros already exists", "feature-disabled.png"));
               return true;
            }

            Macro local10_0 = new Macro(this.nameTextField.getText());
            local10_0.getActions().addAll(this.structureActions);
            local7_6.addMacro(local10_0);
         }

         this.field_22787.method_1507(Melonity.INSTANCE.getMenu());
      }

      boolean local10_9 = MathUtil.isMouseHoveringRect(
         (double)(this.x + 13.0F), (double)(local8_7 + 5.0F), (double)this.nameTextField.getWidth(), (double)this.nameTextField.getHeight(), mouseX, mouseY
      );
      if (local10_9) {
         this.clearAllFocusExcept(this.nameTextField);
         this.nameTextField.setFocus(true);
      }

      this.nameTextField.click(this.x + 13.0F, local8_7 + 5.0F, mouseX, mouseY, button);
      float local11_10 = this.y + 75.0F + this.leftScrollCurrent;
      boolean local12_11 = false;

      for (Action local14_2 : this.structureActions) {
         if (MathUtil.isMouseHoveringRect((double)this.x, (double)(this.y + 75.0F - 1.0F), 188.0, 150.0, mouseX, mouseY)) {
            MelonityTextField local15_1 = this.getClickedTextField(local14_2, this.x + 12.0F, local11_10, mouseX, mouseY);
            if (local15_1 != null) {
               this.clearAllFocusExcept(local15_1);
               local15_1.setFocus(true);
               local12_11 = true;
            }

            local14_2.click(this.x + 12.0F, local11_10, mouseX, mouseY, button);
         }

         if (MathUtil.isMouseHoveringRect((double)(this.x + 168.0F - 5.0F), (double)(local11_10 + 3.5F), 8.0, 8.0, mouseX, mouseY)) {
            this.structureActions.remove(local14_2);
         }

         local11_10 += 20.0F + local14_2.getOffset();
      }

      if (!local10_9 && !local12_11) {
         this.clearAllFocusExcept(null);
      }

      float local13_12 = this.x + 2.0F + 188.0F;
      float local14_13 = this.y + 75.0F;

      for (ActionCategory local18_4 : ActionCategory.values()) {
         local14_13 += 15.0F;

         for (Action local20_3 : local7_6.getActionByCategory(local18_4)) {
            if (MathUtil.isMouseHoveringRect((double)(local13_12 + 9.0F), (double)local14_13, 164.0, 16.0, mouseX, mouseY)) {
               this.drag = local20_3;
               this.dragX = local13_12 + 9.0F;
               this.dragY = local14_13;
               this.prevX = (float)((int)mouseX) - this.dragX;
               this.prevY = (float)((int)mouseY) - this.dragY;
            }

            local14_13 += 20.0F;
         }

         local14_13 += 5.0F;
      }

      return true;
   }

   private MelonityTextField getClickedTextField(Action action, float actionX, float actionY, double mouseX, double mouseY) {
      try {
         Field[] local8_4 = action.getClass().getDeclaredFields();

         for (Field local12_1 : local8_4) {
            if (local12_1.getType().equals(MelonityTextField.class)) {
               local12_1.setAccessible(true);
               MelonityTextField local13_0 = (MelonityTextField)local12_1.get(action);
               if (local13_0 != null && this.isTextFieldClicked(local13_0, actionX, actionY, action, mouseX, mouseY)) {
                  return local13_0;
               }
            }
         }

         Field[] local9_5 = action.getClass().getSuperclass().getDeclaredFields();

         for (Field local13_3 : local9_5) {
            if (local13_3.getType().equals(MelonityTextField.class)) {
               local13_3.setAccessible(true);
               MelonityTextField local14_2 = (MelonityTextField)local13_3.get(action);
               if (local14_2 != null && this.isTextFieldClicked(local14_2, actionX, actionY, action, mouseX, mouseY)) {
                  return local14_2;
               }
            }
         }
      } catch (Exception var15) {
      }

      return null;
   }

   private boolean isTextFieldClicked(MelonityTextField textField, float actionX, float actionY, Action action, double mouseX, double mouseY) {
      if (action instanceof AbstractToggleFunction
         || action.getClass().getSimpleName().equals("UseItemAction")
         || action.getClass().getSimpleName().equals("ChatAction")) {
         return MathUtil.isMouseHoveringRect(
            (double)(actionX + 4.0F), (double)(actionY + 20.0F), (double)textField.getWidth(), (double)textField.getHeight(), mouseX, mouseY
         );
      } else {
         return action.getClass().getSimpleName().equals("WaitMillisAction")
            ? MathUtil.isMouseHoveringRect(
               (double)(actionX + 36.0F), (double)(actionY + 2.5F), (double)textField.getWidth(), (double)textField.getHeight(), mouseX, mouseY
            )
            : false;
      }
   }

   @SneakyThrows
   public boolean method_25406(double mouseX, double mouseY, int button) {
      if (this.drag != null && this.dragIndex != -1) {
         Action local6_1 = (Action)this.drag.getClass().getConstructor().newInstance();
         if (local6_1 instanceof AbstractFlexAction local7_0) {
            local7_0.setPlaced(true);
         }

         this.structureActions.add(this.dragIndex, local6_1);
      }

      this.drag = null;
      this.dragIndex = -1;

      for (Action local7_2 : this.structureActions) {
         local7_2.release();
      }

      this.nameTextField.release();
      return super.method_25406(mouseX, mouseY, button);
   }

   public boolean method_25404(int keyCode, int scanCode, int modifiers) {
      for (Action local5_0 : this.structureActions) {
         local5_0.key(keyCode, scanCode, modifiers);
      }

      this.nameTextField.key(keyCode, scanCode, modifiers);
      return super.method_25404(keyCode, scanCode, modifiers);
   }

   public boolean method_25401(double mouseX, double mouseY, double deltaHorizontal, double delta) {
      this.leftScroll += (float)delta * 20.0F;
      if (this.leftScroll > 0.0F) {
         this.leftScroll = 0.0F;
      }

      float local9_1 = 0.0F;

      for (Action local11_0 : this.structureActions) {
         local9_1 += 20.0F + local11_0.getOffset();
      }

      local9_1 -= 40.0F;
      if (this.leftScroll < -local9_1) {
         this.leftScroll = -local9_1;
      }

      return true;
   }
}
