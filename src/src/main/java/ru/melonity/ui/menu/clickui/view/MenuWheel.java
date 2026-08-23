package ru.melonity.ui.menu.clickui.view;

import java.awt.Color;
import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.StyledView;
import ru.melonity.ui.components.view.impl.ButtonView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.wheel.WheelEntryView;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.macro.Macro;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class MenuWheel extends StyledView {
   private Macro placeTo = null;
   private final ButtonView buttonView = new ButtonView(class_2960.method_60656("melonity/images/ui/baritone/delete.png"), "Remove", 42.0F, 16.0F, 8.0F, 8.0F);
   private Integer removing = null;
   private double removeX;
   private double removeY;
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(300L);
   private final StateAnimation animation = new StateAnimation();
   private static final float[][] ENTRY_COORDINATES = new float[][]{
      {-112.0F, 46.0F}, {-112.0F, -7.0F}, {-56.0F, -63.0F}, {46.0F, -63.0F}, {106.0F, 46.0F}, {106.0F, -12.0F}
   };

   public MenuWheel(float width, float height) {
      super(width, height);
      this.buttonView.setCenterIcon(false);
      this.buttonView.setIconOffset(-1.0F);
      this.buttonView.setTextOffset(3.0F);
      this.buttonView.setCenterText(true);
   }

   @Override
   public void insert(MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      float centerX = x + this.width / 2.0F - 50.0F;
      float centerY = y + this.height / 2.0F - 50.0F;
      List<WheelEntryView> entries = Melonity.INSTANCE.getWheelEntries();
      renderSystem.addRect(centerX + 21.5F, centerY - 1.0F, 31.0F, 31.0F, 30.0F, style.modeElement()[1], poseStack);
      renderSystem.addRect(centerX + 22.0F, centerY - 0.5F, 30.0F, 30.0F, 30.0F, style.autobuyPanel(), poseStack);
      renderSystem.addCircle(centerX + 37.0F, centerY + 14.5F, 0.0F, 360.0F, 3.0F, 3.0F, true, style.ascent(), poseStack);

      for (int index = 0; index < ENTRY_COORDINATES.length && index < entries.size(); index++) {
         WheelEntryView entry = entries.get(index);
         float entryX = centerX + ENTRY_COORDINATES[index][0];
         float entryY = centerY + ENTRY_COORDINATES[index][1];
         boolean hovered = MathUtil.isMouseHoveringRect(
            (double)entryX, (double)entryY, (double)entry.getWidth(), (double)entry.getHeight(), mouseX, mouseY
         );
         entry.insert(style, entryX, entryY, mouseX, mouseY, renderSystem, poseStack);
         if (this.placeTo != null && entry.getMacro() == null) {
            renderSystem.addOutlineRect(entryX, entryY, entry.getWidth(), entry.getHeight(), 6.0F, 1.0F, style.ascent(), poseStack);
         }

         if (hovered) {
            Melonity.hand = true;
         }
      }

      if (this.placeTo != null) {
         String message = "Choose a slot for ".concat(this.placeTo.getName());
         renderSystem.addText(
            Fonts.INTER_REGULAR_12,
            message,
            x + this.width / 2.0F - Fonts.INTER_REGULAR_12.getStringWidth(message) / 2.0F,
            y + this.height - 6.0F,
            Color.decode("#888888"),
            poseStack
         );
      }

      this.animation.state(this.removing != null);
      this.animation.update(this.fwc.elapsedUnits());
      if (this.animation.animation() > 0.0F) {
         this.buttonView.insert((float)this.removeX + 4.0F, (float)this.removeY + 4.0F, mouseX, mouseY, renderSystem, poseStack);
      }
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
      float local8_3 = x + this.width / 2.0F - 50.0F;
      float local9_4 = y + this.height / 2.0F - 50.0F;
      List<WheelEntryView> local10_5 = Melonity.INSTANCE.getWheelEntries();
      if (this.removing != null
         && MathUtil.isMouseHoveringRect(
            (double)((float)this.removeX + 4.0F),
            (double)((float)this.removeY + 4.0F),
            (double)this.buttonView.getWidth(),
            (double)this.buttonView.getHeight(),
            mouseX,
            mouseY
         )) {
         local10_5.get(this.removing).setMacro(null);
         this.removing = null;
      }

      boolean local11_6 = false;

      for (int local12_2 = 0; local12_2 < ENTRY_COORDINATES.length; local12_2++) {
         float local13_0 = local8_3 + ENTRY_COORDINATES[local12_2][0];
         float local14_1 = local9_4 + ENTRY_COORDINATES[local12_2][1];
         if (MathUtil.isMouseHoveringRect(
            (double)local13_0, (double)local14_1, (double)local10_5.get(local12_2).getWidth(), (double)local10_5.get(local12_2).getHeight(), mouseX, mouseY
         )) {
            if (mouseButton == 0 && this.placeTo != null) {
               local10_5.get(local12_2).setMacro(this.placeTo);
               this.placeTo = null;
            }

            if (mouseButton == 1 && local10_5.get(local12_2).getMacro() != null) {
               this.removing = local12_2;
               this.removeX = mouseX;
               this.removeY = mouseY;
               local11_6 = true;
            }
         }
      }

      if (!local11_6) {
         this.removing = null;
      }
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public void release() {
   }

   @Generated
   public void setPlaceTo(Macro placeTo) {
      this.placeTo = placeTo;
   }

   @Generated
   public void setRemoving(Integer removing) {
      this.removing = removing;
   }

   @Generated
   public void setRemoveX(double removeX) {
      this.removeX = removeX;
   }

   @Generated
   public void setRemoveY(double removeY) {
      this.removeY = removeY;
   }
}
