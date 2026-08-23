package ru.melonity.feature.features.baritone.menu;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1747;
import net.minecraft.class_1799;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_7923;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.StyledView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.animation.Easings;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScissorUtil;

@Environment(EnvType.CLIENT)
public class MenuBlocksSelector extends StyledView {
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(370L);
   private final StateAnimation animation = new StateAnimation();
   private final FrameWeightCalculator fwc2 = FrameWeightCalculator.milliseconds(500L);
   private final StateAnimation switchAnimation = new StateAnimation();
   private float scale;
   private float x;
   private float y;
   private boolean dragging;
   private float prevX;
   private float prevY;
   private boolean selector = false;
   private final List<class_2248> showableBlocks = makeBlocks();
   private static final List<class_2248> BLOCKS = makeBlocks();
   private final List<class_2248> selectedBlocks = Lists.newLinkedList();
   private float scroll = 0.0F;
   private float scrollCurrent = 0.0F;
   private boolean scrollInit = false;
   private final FrameWeightCalculator scrollFwc = FrameWeightCalculator.milliseconds(450L);
   private boolean opened = false;
   private boolean newSwitch = false;
   private boolean switchh = false;
   private float selectorOffsetCurrent = 0.0F;
   private boolean selectorInit = false;
   private final FrameWeightCalculator selectorFwc = FrameWeightCalculator.milliseconds(500L);

   public MenuBlocksSelector(float menuX, float menuY) {
      super(155.0F, 230.0F);
      this.x = menuX + this.width;
      this.y = menuY;
   }

   private static List<class_2248> makeBlocks() {
      return new LinkedList<>(
         List.of(
            class_2246.field_10418,
            class_2246.field_10442,
            class_2246.field_10013,
            class_2246.field_10212,
            class_2246.field_10571,
            class_2246.field_10090,
            class_2246.field_10080,
            class_2246.field_23077,
            class_2246.field_10213,
            class_2246.field_10153,
            class_2246.field_22108,
            class_2246.field_22109,
            class_2246.field_10114
         )
      );
   }

   @Override
   public void insert(MenuStyle style, float xx, float yy, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      this.animation.state(this.opened);
      this.animation.update(this.fwc.elapsedUnits());
      this.scale = (float)Easings.CUBIC_BOTH.ease((double)this.animation.animation());
      if (this.scale <= 0.01F) {
         this.dragging = false;
      } else {
         if (this.dragging) {
            this.x = (float)((int)mouseX) - this.prevX;
            this.y = (float)((int)mouseY) - this.prevY;
         }

         this.switchAnimation.update(this.fwc2.elapsedUnits());
         if (this.switchh && this.switchAnimation.animation() >= 0.99F) {
            this.selector = this.newSwitch;
            this.switchAnimation.state(false);
            this.switchh = false;
         }

         if (!this.scrollInit) {
            this.scrollCurrent = this.scroll;
            this.scrollInit = true;
         }

         this.scrollCurrent = this.scrollCurrent + (this.scroll - this.scrollCurrent) * 14.0F * this.scrollFwc.elapsedUnits();
         float selectorTarget = this.selector ? 1.0F : 0.0F;
         if (!this.selectorInit) {
            this.selectorOffsetCurrent = selectorTarget;
            this.selectorInit = true;
         }

         this.selectorOffsetCurrent = this.selectorOffsetCurrent + (selectorTarget - this.selectorOffsetCurrent) * 14.0F * this.selectorFwc.elapsedUnits();
         float pivotX = this.x + this.width / 2.0F;
         float pivotY = this.y + this.height / 2.0F;
         poseStack.method_22903();
         poseStack.method_46416(pivotX, pivotY, 0.0F);
         poseStack.method_22905(this.scale, this.scale, 1.0F);
         poseStack.method_46416(-pivotX, -pivotY, 0.0F);
         renderSystem.addRect(this.x, this.y, this.width, this.height, 8.0F, style.background(), poseStack);
         if (style.headerBlocks()) {
            renderSystem.addTexture(
               class_2960.method_60656("melonity/images/ui/overlay_blocks.png"), this.x, this.y, this.width, 17.0F, style.blocksColor(), poseStack
            );
         }

         renderSystem.addText(Fonts.INTER_MEDIUM_14, this.selector ? "Add block" : "Blocks", this.x + 10.0F, this.y + 12.0F, poseStack);
         renderSystem.addText(
            Fonts.INTER_REGULAR_12,
            this.selector ? "Pick blocks to place" : "Blocks used by baritone",
            this.x + 10.0F,
            this.y + 24.0F,
            Color.decode("#888888"),
            poseStack
         );
         renderSystem.addRect(this.x, this.y + 36.0F, this.width, 1.0F, Color.decode("#222222"), poseStack);
         int listAlpha = Math.max(0, Math.min(255, (int)((1.0F - this.switchAnimation.animation()) * 255.0F)));
         List<class_2248> blocks = this.selector ? BLOCKS : this.showableBlocks;
         boolean insideList = MathUtil.isMouseHoveringRect((double)this.x, (double)(this.y + 40.0F), (double)this.width, 125.0, mouseX, mouseY);
         ScissorUtil.beginScissor((double)this.x, (double)(this.y + 40.0F), (double)this.width, 125.0);
         float rowY = this.y + 40.0F + this.scrollCurrent;

         for (class_2248 block : blocks) {
            boolean selected = this.selectedBlocks.contains(block);
            boolean rowHovered = insideList
               && MathUtil.isMouseHoveringRect((double)(this.x + 10.0F), (double)rowY, (double)(this.width - 20.0F), 27.0, mouseX, mouseY);
            renderSystem.addRect(
               this.x + 10.0F,
               rowY,
               this.width - 20.0F,
               27.0F,
               6.0F,
               RenderSystem.setAlpha(rowHovered ? style.background().brighter().brighter() : style.previewValues(), listAlpha),
               poseStack
            );
            if (selected) {
               renderSystem.addRect(this.x + 10.0F, rowY, this.width - 20.0F, 27.0F, 6.0F, RenderSystem.setAlpha(style.ascent(), listAlpha), poseStack);
            }

            String blockPath = class_7923.field_41175.method_10221(block).method_12832();
            class_2960 texture = class_2960.method_60656("textures/block/" + blockPath + ".png");
            if (class_310.method_1551().method_1478().method_14486(texture).isEmpty()) {
               texture = class_2960.method_60656("textures/block/" + blockPath + "_top.png");
            }

            renderSystem.addTexture(texture, this.x + 18.0F, rowY + 5.5F, 16.0F, 16.0F, RenderSystem.setAlpha(Color.WHITE, listAlpha), poseStack);
            String blockName = block.method_9518().getString();
            renderSystem.addText(
               Fonts.INTER_REGULAR_14,
               Fonts.INTER_REGULAR_14.trimToWidth(blockName, (int)(this.width - 74.0F)),
               this.x + 40.0F,
               rowY + 11.0F,
               RenderSystem.setAlpha(selected ? Color.decode("#141414") : Color.WHITE, listAlpha),
               poseStack
            );
            if (!this.selector) {
               boolean deleteHovered = insideList
                  && MathUtil.isMouseHoveringRect((double)(this.x + this.width - 24.0F), (double)(rowY + 11.0F), 8.0, 8.0, mouseX, mouseY);
               renderSystem.addTexture(
                  class_2960.method_60656("melonity/images/ui/baritone/delete.png"),
                  this.x + this.width - 24.0F,
                  rowY + 11.0F,
                  8.0F,
                  8.0F,
                  RenderSystem.setAlpha(deleteHovered ? Color.WHITE : Color.decode("#888888"), listAlpha),
                  poseStack
               );
            }

            if (rowHovered) {
               Melonity.hand = true;
            }

            rowY += 32.0F;
         }

         ScissorUtil.endScissor();
         if (!this.selector) {
            float switchWidth = this.width - 16.0F;
            boolean switchHovered = MathUtil.isMouseHoveringRect((double)(this.x + 8.0F), (double)(this.y + 172.0F), (double)switchWidth, 18.0, mouseX, mouseY);
            renderSystem.addRect(this.x + 8.0F, this.y + 172.0F, switchWidth, 18.0F, 6.0F, style.background().brighter(), poseStack);
            renderSystem.addRect(
               this.x + 8.5F,
               this.y + 172.5F,
               switchWidth - 1.0F,
               17.0F,
               6.0F,
               switchHovered ? style.background().brighter() : style.previewValues(),
               poseStack
            );
            renderSystem.addTexture(
               class_2960.method_60656("melonity/images/ui/baritone/add_big.png"),
               this.x + 8.0F + switchWidth / 2.0F - Fonts.INTER_REGULAR_14.getStringWidth("Add block") / 2.0F - 12.0F,
               this.y + 177.0F,
               8.0F,
               8.0F,
               Color.decode("#888888"),
               poseStack
            );
            renderSystem.addText(
               Fonts.INTER_REGULAR_14,
               "Add block",
               this.x + 8.0F + switchWidth / 2.0F - Fonts.INTER_REGULAR_14.getStringWidth("Add block") / 2.0F,
               this.y + 178.0F,
               switchHovered ? Color.WHITE : Color.decode("#888888"),
               poseStack
            );
            String addHand = Melonity.INSTANCE.languageService.get("blocks_selector.add_hand");
            float addHandWidth = Fonts.INTER_REGULAR_12.getStringWidth(addHand) + 20.0F;
            boolean addHandHovered = MathUtil.isMouseHoveringRect(
               (double)(this.x + 8.0F), (double)(this.y + 207.0F), (double)addHandWidth, 13.0, mouseX, mouseY
            );
            renderSystem.addRect(this.x + 8.0F, this.y + 207.0F, addHandWidth, 13.0F, 4.0F, style.previewValues(), poseStack);
            renderSystem.addTexture(
               class_2960.method_60656("melonity/images/ui/baritone/add.png"), this.x + 13.0F, this.y + 210.0F, 7.0F, 7.0F, Color.decode("#888888"), poseStack
            );
            renderSystem.addText(
               Fonts.INTER_REGULAR_12,
               addHand,
               this.x + 23.0F,
               this.y + 210.5F,
               addHandHovered ? Color.WHITE : Color.decode("#888888"),
               poseStack
            );
            boolean closeHovered = MathUtil.isMouseHoveringRect((double)(this.x + this.width - 32.0F), (double)(this.y + 207.0F), 24.0, 13.0, mouseX, mouseY);
            renderSystem.addRect(this.x + this.width - 32.0F, this.y + 207.0F, 24.0F, 13.0F, 4.0F, style.previewValues(), poseStack);
            renderSystem.addText(
               Fonts.INTER_REGULAR_12,
               "Close",
               this.x + this.width - 32.0F + 12.0F - Fonts.INTER_REGULAR_12.getStringWidth("Close") / 2.0F,
               this.y + 210.5F,
               closeHovered ? Color.WHITE : Color.decode("#888888"),
               poseStack
            );
            if (switchHovered || addHandHovered || closeHovered) {
               Melonity.hand = true;
            }
         } else {
            boolean backHovered = MathUtil.isMouseHoveringRect((double)(this.x + this.width - 42.0F), (double)(this.y + 185.0F), 34.0, 13.0, mouseX, mouseY);
            boolean cancelHovered = MathUtil.isMouseHoveringRect((double)(this.x + this.width - 76.0F), (double)(this.y + 185.0F), 28.0, 13.0, mouseX, mouseY);
            renderSystem.addRect(this.x + this.width - 76.0F, this.y + 185.0F, 28.0F, 13.0F, 4.0F, style.previewValues(), poseStack);
            renderSystem.addText(
               Fonts.INTER_REGULAR_12,
               "Back",
               this.x + this.width - 76.0F + 14.0F - Fonts.INTER_REGULAR_12.getStringWidth("Back") / 2.0F,
               this.y + 188.5F,
               cancelHovered ? Color.WHITE : Color.decode("#888888"),
               poseStack
            );
            renderSystem.addRect(this.x + this.width - 42.0F, this.y + 185.0F, 34.0F, 13.0F, 4.0F, style.ascent(), poseStack);
            renderSystem.addText(
               Fonts.INTER_REGULAR_12,
               "Apply",
               this.x + this.width - 42.0F + 17.0F - Fonts.INTER_REGULAR_12.getStringWidth("Apply") / 2.0F,
               this.y + 188.5F,
               Color.decode("#141414"),
               poseStack
            );
            if (backHovered || cancelHovered) {
               Melonity.hand = true;
            }
         }

         poseStack.method_22909();
      }
   }

   public boolean isHovered(double mouseX, double mouseY) {
      return MathUtil.isMouseHoveringRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, mouseX, mouseY);
   }

   @Override
   public void click(float xx, float yy, double mouseX, double mouseY, int mouseButton) {
      if (MathUtil.isMouseHoveringRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, mouseX, mouseY)) {
         this.dragging = true;
         this.prevX = (float)((int)mouseX) - this.x;
         this.prevY = (float)((int)mouseY) - this.y;
      }

      List<class_2248> local8_5 = this.selector ? BLOCKS : this.showableBlocks;
      float local9_6 = this.y + 40.0F;
      local9_6 += this.scrollCurrent;
      if (MathUtil.isMouseHoveringRect((double)this.x, (double)(this.y + 40.0F), (double)this.width, 125.0, mouseX, mouseY)) {
         try {
            // The remove() below mutates local8_5 while it is being iterated, which
            // threw ConcurrentModificationException straight into the empty catch -
            // clicking the delete cross aborted the rest of the click handler.
            for (class_2248 local11_0 : new java.util.ArrayList<>(local8_5)) {
               if (this.selector
                  && MathUtil.isMouseHoveringRect((double)(this.x + 10.0F), (double)local9_6, (double)(this.width - 20.0F), 27.0, mouseX, mouseY)) {
                  if (this.selectedBlocks.contains(local11_0)) {
                     this.selectedBlocks.remove(local11_0);
                  } else {
                     this.selectedBlocks.add(local11_0);
                  }
               } else if (MathUtil.isMouseHoveringRect((double)(this.x + this.width - 24.0F), (double)(local9_6 + 11.0F), 8.0, 8.0, mouseX, mouseY)
                  && !this.selectedBlocks.contains(local11_0)) {
                  local8_5.remove(local11_0);
               }

               local9_6 += 32.0F;
            }
         } catch (Throwable var15) {
         }
      }

      float local10_7 = this.width - 16.0F;
      if (MathUtil.isMouseHoveringRect((double)(this.x + 8.0F), (double)(this.y + 172.0F), (double)local10_7, 18.0, mouseX, mouseY)) {
         this.scroll = 0.0F;
         this.switchAnimation.state(true);
         this.switchh = true;
         this.newSwitch = true;
      }

      if (!this.selector) {
         float local11_3 = this.y + 172.0F;
         if (MathUtil.isMouseHoveringRect(
            (double)(this.x + 8.0F),
            (double)(local11_3 + 35.0F),
            (double)(Fonts.INTER_REGULAR_12.getStringWidth(Melonity.INSTANCE.languageService.get("blocks_selector.add_hand")) + 20.0F),
            13.0,
            mouseX,
            mouseY
         )) {
            class_1799 local12_2 = class_310.method_1551().field_1724.method_6047();
            if (local12_2.method_7909() instanceof class_1747 local13_1 && !local8_5.contains(local13_1.method_7711())) {
               this.showableBlocks.add(local13_1.method_7711());
               BLOCKS.add(local13_1.method_7711());
            }
         }

         if (MathUtil.isMouseHoveringRect((double)(this.x + this.width - 24.0F - 8.0F), (double)(local11_3 + 35.0F), 24.0, 13.0, mouseX, mouseY)) {
            this.opened = false;
         }
      } else {
         float local11_4 = this.y + 150.0F;
         if (MathUtil.isMouseHoveringRect((double)(this.x + this.width - 34.0F - 8.0F), (double)(local11_4 + 35.0F), 34.0, 13.0, mouseX, mouseY)
            || MathUtil.isMouseHoveringRect((double)(this.x + this.width - 76.0F), (double)(local11_4 + 35.0F), 28.0, 13.0, mouseX, mouseY)) {
            this.scroll = 0.0F;
            this.switchAnimation.state(true);
            this.switchh = true;
            this.newSwitch = false;
         }
      }
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public void release() {
      this.dragging = false;
   }

   public void scroll(double delta, double mouseX, double mouseY) {
      if (MathUtil.isMouseHoveringRect((double)this.x, (double)this.y, (double)this.width, (double)this.height, mouseX, mouseY)) {
         this.scroll += (float)delta * 20.0F;
      }

      if (this.scroll > 0.0F) {
         this.scroll = 0.0F;
      }

      float local7_0 = (float)(((this.selector ? BLOCKS : this.showableBlocks).size() - 4) * 32);
      if (this.scroll < -local7_0) {
         this.scroll = -local7_0;
      }
   }

   @Generated
   public boolean isSelector() {
      return this.selector;
   }

   @Generated
   public List<class_2248> getSelectedBlocks() {
      return this.selectedBlocks;
   }

   @Generated
   public void setOpened(boolean opened) {
      this.opened = opened;
   }

   @Generated
   public boolean isOpened() {
      return this.opened;
   }
}
