package ru.melonity.ui.components;

import com.google.common.collect.Sets;
import java.awt.Color;
import java.util.Set;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_4587;
import org.lwjgl.glfw.GLFW;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.StyledView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class MelonityTextField extends StyledView {
   public static int totalFields = 0;
   public int ID = 0;
   public static int selectedField = -1;
   private final String hint;
   private final boolean search;
   private String text;
   private boolean writable;
   private boolean focus;
   private long ticks = 0L;
   private boolean line = true;
   private final StateAnimation animation = new StateAnimation();
   private final FrameWeightCalculator frameWeightCalculator = FrameWeightCalculator.milliseconds(400L);
   private boolean alwaysHint = false;
   private boolean undecorated = false;
   private String suggestion = null;
   private boolean number;
   private int selectionStart = 0;
   private int selectionEnd = 0;
   private boolean dragging = false;
   private int cursorPos = 0;
   private double lastMouseX = 0.0;
   private double lastMouseY = 0.0;
   private static final Set<Integer> ALLOWED_KEYS = Sets.newHashSet();
   private MelonityTextField.SmartSuggestion smartSuggestion = null;
   private MelonityTextField.ItemIconSuggestion itemIconSuggestion = null;
   private Color overrideTextColor = null;

   public void setFocus(boolean focus) {
      if (focus) {
         selectedField = this.ID;
      } else {
         selectedField = -1;
      }

      this.focus = focus;
   }

   public MelonityTextField(String hint, boolean search, float width, float height, boolean number) {
      super(width, height);
      this.hint = hint;
      this.search = search;
      this.text = "";
      this.number = number;
      this.ID = totalFields + 1;
      totalFields++;
   }

   public MelonityTextField(String hint, boolean search, float width, float height) {
      this(hint, search, width, height, false);
   }

   public static boolean isAllowedKey(int keyCode) {
      return !ALLOWED_KEYS.contains(keyCode);
   }

   private int charPosAtX(float x, float fieldX) {
      String local3_4 = this.getDisplayText();
      float local4_5 = fieldX + (float)(this.search ? 16 : 6) - (float)(this.undecorated ? 7 : 0);
      float local5_6 = x - local4_5;
      if (local5_6 <= 0.0F) {
         return 0;
      } else {
         for (int local6_3 = 0; local6_3 <= local3_4.length(); local6_3++) {
            float local7_2 = Fonts.INTER_REGULAR_14.getStringWidth(local3_4.substring(0, local6_3));
            if (local7_2 >= local5_6) {
               if (local6_3 > 0) {
                  float local8_0 = Fonts.INTER_REGULAR_14.getStringWidth(local3_4.substring(0, local6_3 - 1));
                  float local9_1 = local8_0 + (local7_2 - local8_0) / 2.0F;
                  return local5_6 < local9_1 ? local6_3 - 1 : local6_3;
               }

               return local6_3;
            }
         }

         return local3_4.length();
      }
   }

   private String getDisplayText() {
      String local1_0 = this.text == null ? "" : this.text;
      float available = this.width - (float)(this.search ? 25 : 15);

      // The isEmpty() guard matters: on a narrow field `available` goes negative,
      // and an empty string still "overflows" it, so the trim would run off the end.
      while (!local1_0.isEmpty() && Fonts.INTER_REGULAR_14.getStringWidth(local1_0) > available) {
         local1_0 = local1_0.substring(0, local1_0.length() - 1);
      }

      return local1_0;
   }

   private boolean hasSelection() {
      return this.selectionStart != this.selectionEnd;
   }

   private void clearSelection() {
      if (this.text == null) {
         this.text = "";
      }

      if (this.hasSelection()) {
         int length = this.text.length();
         // Clamp before slicing: the selection can outlive the text it was made
         // against (external setText, suggestion accept, config reload).
         int from = Math.max(0, Math.min(Math.min(this.selectionStart, this.selectionEnd), length));
         int to = Math.max(from, Math.min(Math.max(this.selectionStart, this.selectionEnd), length));
         this.text = this.text.substring(0, from) + this.text.substring(to);
         this.cursorPos = from;
      }

      this.cursorPos = Math.max(0, Math.min(this.cursorPos, this.text.length()));
      this.selectionStart = this.selectionEnd = this.cursorPos;
   }

   @Override
   public void insert(MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      if (this.text == null) {
         this.text = "";
      }

      this.lastMouseX = mouseX;
      this.lastMouseY = mouseY;
      this.animation.state(this.focus);
      this.animation.update(this.frameWeightCalculator.elapsedUnits());
      if (this.dragging && this.writable) {
         int dragged = Math.min(this.charPosAtX((float)mouseX, x), this.text.length());
         this.cursorPos = dragged;
         this.selectionEnd = dragged;
      }

      GlyphData font = Fonts.INTER_REGULAR_14;
      String displayText = this.getDisplayText();
      float textX = x + (float)(this.search ? 16 : 6) - (float)(this.undecorated ? 7 : 0);
      float textY = y + this.height / 2.0F - 1.5F;
      if (!this.undecorated) {
         Color border = RenderSystem.setAlpha(style.ascent(), (int)(this.animation.animation() * 255.0F));
         renderSystem.addRect(x - 0.5F, y - 0.5F, this.width + 1.0F, this.height + 1.0F, 6.5F, style.background().brighter(), poseStack);
         renderSystem.addRect(x - 0.5F, y - 0.5F, this.width + 1.0F, this.height + 1.0F, 6.5F, border, poseStack);
         renderSystem.addRect(x, y, this.width, this.height, 6.0F, style.background(), poseStack);
      }

      if (this.search) {
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/search_icon.png"),
            x + 5.0F,
            y + this.height / 2.0F - 4.25F,
            8.5F,
            8.5F,
            Color.decode("#888888"),
            poseStack
         );
      }

      if (this.text.isEmpty() && (this.alwaysHint || !this.focus)) {
         renderSystem.addText(font, this.hint, textX, textY, Color.decode("#888888"), poseStack);
      }

      if (this.hasSelection()) {
         int selectionFrom = Math.min(Math.min(this.selectionStart, this.selectionEnd), displayText.length());
         int selectionTo = Math.min(Math.max(this.selectionStart, this.selectionEnd), displayText.length());
         float selectionX = textX + font.getStringWidth(displayText.substring(0, selectionFrom));
         float selectionWidth = font.getStringWidth(displayText.substring(selectionFrom, selectionTo));
         renderSystem.addRect(selectionX, y + 3.0F, selectionWidth, this.height - 6.0F, 1.0F, RenderSystem.setAlpha(style.ascent(), 90), poseStack);
      }

      Color textColor = this.overrideTextColor;
      if (textColor == null) {
         textColor = this.focus ? Color.WHITE : Color.decode("#888888");
      }

      renderSystem.addText(font, displayText, textX, textY, textColor, poseStack);
      if (this.smartSuggestion == null) {
         this.suggestion = null;
      } else {
         this.suggestion = this.text.isEmpty() ? null : this.smartSuggestion.suggest(this.text);
         if (this.suggestion != null && this.suggestion.length() > this.text.length() && this.suggestion.startsWith(this.text)) {
            renderSystem.addText(
               font,
               this.suggestion.substring(this.text.length()),
               textX + font.getStringWidth(displayText),
               textY,
               Color.decode("#565656"),
               poseStack
            );
         }
      }

      if (this.itemIconSuggestion != null) {
         this.drawItemIcon(this.itemIconSuggestion.suggest(this.text), x + this.width - 14.0F, y + this.height / 2.0F - 4.0F, poseStack);
      }

      if (this.focus && this.writable) {
         if (System.currentTimeMillis() - this.ticks > 500L) {
            this.line = !this.line;
            this.ticks = System.currentTimeMillis();
         }

         if (this.line) {
            int caret = Math.min(this.cursorPos, displayText.length());
            renderSystem.addRect(
               textX + font.getStringWidth(displayText.substring(0, caret)), y + 3.0F, 0.5F, this.height - 6.0F, Color.WHITE, poseStack
            );
         }
      }

      if (MathUtil.isMouseHoveringRect((double)x, (double)y, (double)this.width, (double)this.height, mouseX, mouseY)) {
         Melonity.text = true;
      }
   }

   private void drawItemIcon(class_1799 stack, float x, float y, class_4587 poseStack) {
      if (stack != null && !stack.method_7960()) {
         try {
            class_310 minecraft = class_310.method_1551();
            class_332 context = new class_332(minecraft, minecraft.method_22940().method_23000());
            context.method_51448().method_34425(poseStack.method_23760().method_23761());
            context.method_51448().method_46416(x, y, 0.0F);
            context.method_51448().method_22905(0.5F, 0.5F, 0.5F);
            context.method_51427(stack, 0, 0);
            context.method_51452();
         } catch (Throwable var7) {
         }
      }
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
      if (MathUtil.isMouseHoveringRect((double)x, (double)y, (double)this.width, (double)this.height, mouseX, mouseY)) {
         this.writable = true;
         int local8_0 = this.charPosAtX((float)mouseX, x);
         local8_0 = Math.min(local8_0, this.text.length());
         if (!class_437.method_25442()) {
            this.cursorPos = local8_0;
            this.selectionStart = this.selectionEnd = this.cursorPos;
            this.dragging = true;
         } else {
            this.cursorPos = local8_0;
            this.selectionEnd = this.cursorPos;
         }
      } else {
         this.writable = false;
         this.dragging = false;
      }
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      if (!this.writable || !this.focus) {
         return false;
      } else if (this.smartSuggestion != null
         && this.text != null
         && !this.text.isEmpty()
         && this.suggestion != null
         && !this.suggestion.isEmpty()
         && keyCode == 257) {
         this.setText(this.suggestion);
         this.cursorPos = this.text.length();
         this.selectionStart = this.selectionEnd = this.cursorPos;
         return true;
      } else if (keyCode == 65 && !this.text.isEmpty() && class_437.method_25441()) {
         this.selectionStart = 0;
         this.selectionEnd = this.text.length();
         this.cursorPos = this.text.length();
         return true;
      } else if (keyCode == 67 && !this.text.isEmpty() && class_437.method_25441()) {
         if (this.hasSelection()) {
            int local4_0 = Math.min(this.selectionStart, this.selectionEnd);
            int local5_1 = Math.max(this.selectionStart, this.selectionEnd);
            String local6_2 = this.text.substring(local4_0, local5_1);
            GLFW.glfwSetClipboardString(class_310.method_1551().method_22683().method_4490(), local6_2);
         } else {
            GLFW.glfwSetClipboardString(class_310.method_1551().method_22683().method_4490(), this.text);
         }

         return false;
      } else if (keyCode == 86 && class_437.method_25441()) {
         String local4_6 = GLFW.glfwGetClipboardString(class_310.method_1551().method_22683().method_4490());
         if (local4_6 != null) {
            if (this.hasSelection()) {
               this.clearSelection();
            }

            if (this.number) {
               StringBuilder local5_5 = new StringBuilder(local4_6.length());

               for (int local6_4 = 0; local6_4 < local4_6.length(); local6_4++) {
                  char local7_3 = local4_6.charAt(local6_4);
                  if (local7_3 >= '0' && local7_3 <= '9') {
                     local5_5.append(local7_3);
                  }
               }

               local4_6 = local5_5.toString();
            }

            if (local4_6.isEmpty()) {
               return false;
            }

            this.text = this.text.substring(0, this.cursorPos) + local4_6 + this.text.substring(this.cursorPos);
            this.cursorPos = this.cursorPos + local4_6.length();
            this.selectionStart = this.selectionEnd = this.cursorPos;
         }

         return false;
      } else if (keyCode == 88 && class_437.method_25441() && this.hasSelection()) {
         int local4_7 = Math.min(this.selectionStart, this.selectionEnd);
         int local5_8 = Math.max(this.selectionStart, this.selectionEnd);
         String local6_9 = this.text.substring(local4_7, local5_8);
         GLFW.glfwSetClipboardString(class_310.method_1551().method_22683().method_4490(), local6_9);
         this.clearSelection();
         return false;
      } else if (keyCode == 259) {
         if (this.hasSelection()) {
            this.clearSelection();
            return false;
         } else if (!this.text.isEmpty() && this.cursorPos != 0) {
            try {
               if (class_437.method_25441()) {
                  this.text = this.text.substring(this.cursorPos);
                  this.cursorPos = 0;
               } else {
                  this.text = this.text.substring(0, this.cursorPos - 1) + this.text.substring(this.cursorPos);
                  this.cursorPos--;
               }
            } catch (Throwable var8) {
            }

            this.selectionStart = this.selectionEnd = this.cursorPos;
            return false;
         } else {
            return false;
         }
      } else if (keyCode == 261) {
         if (this.hasSelection()) {
            this.clearSelection();
            return false;
         } else if (!this.text.isEmpty() && this.cursorPos < this.text.length()) {
            if (class_437.method_25441()) {
               this.text = this.text.substring(0, this.cursorPos);
            } else {
               this.text = this.text.substring(0, this.cursorPos) + this.text.substring(this.cursorPos + 1);
            }

            this.selectionStart = this.selectionEnd = this.cursorPos;
            return false;
         } else {
            return false;
         }
      } else if (keyCode == 263) {
         if (class_437.method_25442()) {
            if (this.cursorPos > 0) {
               this.cursorPos--;
               this.selectionEnd = this.cursorPos;
            }
         } else {
            if (this.hasSelection()) {
               this.cursorPos = Math.min(this.selectionStart, this.selectionEnd);
            } else if (this.cursorPos > 0) {
               this.cursorPos--;
            }

            this.selectionStart = this.selectionEnd = this.cursorPos;
         }

         return false;
      } else if (keyCode == 262) {
         if (class_437.method_25442()) {
            if (this.cursorPos < this.text.length()) {
               this.cursorPos++;
               this.selectionEnd = this.cursorPos;
            }
         } else {
            if (this.hasSelection()) {
               this.cursorPos = Math.max(this.selectionStart, this.selectionEnd);
            } else if (this.cursorPos < this.text.length()) {
               this.cursorPos++;
            }

            this.selectionStart = this.selectionEnd = this.cursorPos;
         }

         return false;
      } else if (keyCode == 268) {
         if (class_437.method_25442()) {
            this.cursorPos = 0;
            this.selectionEnd = this.cursorPos;
         } else {
            this.cursorPos = 0;
            this.selectionStart = this.selectionEnd = this.cursorPos;
         }

         return false;
      } else if (keyCode == 269) {
         if (class_437.method_25442()) {
            this.cursorPos = this.text.length();
            this.selectionEnd = this.cursorPos;
         } else {
            this.cursorPos = this.text.length();
            this.selectionStart = this.selectionEnd = this.cursorPos;
         }

         return false;
      } else if (isAllowedKey(keyCode)) {
         return false;
      } else {
         String local4_10 = GLFW.glfwGetKeyName(keyCode, 0);
         if (keyCode == 32) {
            local4_10 = " ";
         }

         if (local4_10 == null) {
            return false;
         } else {
            local4_10 = class_437.method_25442() ? local4_10.toUpperCase() : local4_10.toLowerCase();
            if (local4_10.equals(";") && class_437.method_25442()) {
               local4_10 = ":";
            }

            if (local4_10.equals("-") && class_437.method_25442()) {
               local4_10 = "_";
            }

            if (!this.number || local4_10.length() == 1 && local4_10.charAt(0) >= '0' && local4_10.charAt(0) <= '9') {
               if (this.hasSelection()) {
                  this.clearSelection();
               }

               try {
                  if (this.cursorPos >= this.text.length()) {
                     this.text = this.text + local4_10;
                  } else {
                     this.text = this.text.substring(0, this.cursorPos) + local4_10 + this.text.substring(this.cursorPos);
                  }
               } catch (Throwable var9) {
               }

               this.cursorPos++;
               this.selectionStart = this.selectionEnd = this.cursorPos;
               return false;
            } else {
               return false;
            }
         }
      }
   }

   @Override
   public void release() {
      this.dragging = false;
   }

   @Generated
   public int getID() {
      return this.ID;
   }

   @Generated
   public String getHint() {
      return this.hint;
   }

   @Generated
   public boolean isSearch() {
      return this.search;
   }

   @Generated
   public String getText() {
      return this.text;
   }

   @Generated
   public boolean isWritable() {
      return this.writable;
   }

   @Generated
   public boolean isFocus() {
      return this.focus;
   }

   @Generated
   public long getTicks() {
      return this.ticks;
   }

   @Generated
   public boolean isLine() {
      return this.line;
   }

   @Generated
   public StateAnimation getAnimation() {
      return this.animation;
   }

   @Generated
   public FrameWeightCalculator getFrameWeightCalculator() {
      return this.frameWeightCalculator;
   }

   @Generated
   public boolean isAlwaysHint() {
      return this.alwaysHint;
   }

   @Generated
   public boolean isUndecorated() {
      return this.undecorated;
   }

   @Generated
   public String getSuggestion() {
      return this.suggestion;
   }

   @Generated
   public boolean isNumber() {
      return this.number;
   }

   @Generated
   public int getSelectionStart() {
      return this.selectionStart;
   }

   @Generated
   public int getSelectionEnd() {
      return this.selectionEnd;
   }

   @Generated
   public boolean isDragging() {
      return this.dragging;
   }

   @Generated
   public int getCursorPos() {
      return this.cursorPos;
   }

   @Generated
   public double getLastMouseX() {
      return this.lastMouseX;
   }

   @Generated
   public double getLastMouseY() {
      return this.lastMouseY;
   }

   @Generated
   public MelonityTextField.SmartSuggestion getSmartSuggestion() {
      return this.smartSuggestion;
   }

   @Generated
   public MelonityTextField.ItemIconSuggestion getItemIconSuggestion() {
      return this.itemIconSuggestion;
   }

   @Generated
   public Color getOverrideTextColor() {
      return this.overrideTextColor;
   }

   @Generated
   public void setID(int ID) {
      this.ID = ID;
   }

   public void setText(String text) {
      this.text = text == null ? "" : text;
      // Replacing the contents invalidates any caret/selection the user had:
      // leaving them stale lets substring() run past the new length.
      this.cursorPos = this.text.length();
      this.selectionStart = this.selectionEnd = this.cursorPos;
   }

   @Generated
   public void setWritable(boolean writable) {
      this.writable = writable;
   }

   @Generated
   public void setTicks(long ticks) {
      this.ticks = ticks;
   }

   @Generated
   public void setLine(boolean line) {
      this.line = line;
   }

   @Generated
   public void setAlwaysHint(boolean alwaysHint) {
      this.alwaysHint = alwaysHint;
   }

   @Generated
   public void setUndecorated(boolean undecorated) {
      this.undecorated = undecorated;
   }

   @Generated
   public void setSuggestion(String suggestion) {
      this.suggestion = suggestion;
   }

   @Generated
   public void setNumber(boolean number) {
      this.number = number;
   }

   public void setSelectionStart(int selectionStart) {
      this.selectionStart = this.clampToText(selectionStart);
   }

   public void setSelectionEnd(int selectionEnd) {
      this.selectionEnd = this.clampToText(selectionEnd);
   }

   private int clampToText(int index) {
      int length = this.text == null ? 0 : this.text.length();
      return Math.max(0, Math.min(index, length));
   }

   @Generated
   public void setDragging(boolean dragging) {
      this.dragging = dragging;
   }

   public void setCursorPos(int cursorPos) {
      this.cursorPos = this.clampToText(cursorPos);
   }

   @Generated
   public void setLastMouseX(double lastMouseX) {
      this.lastMouseX = lastMouseX;
   }

   @Generated
   public void setLastMouseY(double lastMouseY) {
      this.lastMouseY = lastMouseY;
   }

   @Generated
   public void setSmartSuggestion(MelonityTextField.SmartSuggestion smartSuggestion) {
      this.smartSuggestion = smartSuggestion;
   }

   @Generated
   public void setItemIconSuggestion(MelonityTextField.ItemIconSuggestion itemIconSuggestion) {
      this.itemIconSuggestion = itemIconSuggestion;
   }

   @Generated
   public void setOverrideTextColor(Color overrideTextColor) {
      this.overrideTextColor = overrideTextColor;
   }

   static {
      for (int local0_0 = 32; local0_0 <= 126; local0_0++) {
         ALLOWED_KEYS.add(local0_0);
      }

      ALLOWED_KEYS.add(259);
   }

   @Environment(EnvType.CLIENT)
   public interface ItemIconSuggestion {
      class_1799 suggest(String var1);
   }

   @Environment(EnvType.CLIENT)
   public interface SmartSuggestion {
      String suggest(String var1);
   }
}
