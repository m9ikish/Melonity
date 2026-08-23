package ru.melonity.ui.menu.clickui.view.feature.setting;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BooleanSupplier;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_1429;
import net.minecraft.class_1531;
import net.minecraft.class_1657;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_746;
import org.joml.Quaternionf;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScissorUtil;

@Environment(EnvType.CLIENT)
public class ListSetting implements MenuFeatureView {
   public final FrameWeightCalculator weightCalculator;
   public final String name;
   public final List<String> modes;
   public final ListSetting.ModeType type;
   public List<String> mode;
   public boolean expanded = false;
   private final StateAnimation animation = new StateAnimation();
   private final StateAnimation triangleAnimation = new StateAnimation();
   private final FrameWeightCalculator triangleWeightCalculator = FrameWeightCalculator.milliseconds(200L);
   private BooleanSupplier visibilityCondition = () -> true;

   public ListSetting(String name, ListSetting.ModeType type, String mode, List<String> modes) {
      this.name = name;
      this.type = type;
      this.modes = modes;
      this.mode = Lists.newLinkedList();
      this.mode.add(mode);
      this.weightCalculator = FrameWeightCalculator.milliseconds((long)(1000 / class_3532.method_15340(modes.size(), 3, 5)));
   }

   public ListSetting(String name, ListSetting.ModeType type, List<String> mode, List<String> modes) {
      this.name = name;
      this.type = type;
      this.modes = modes;
      this.mode = Lists.newLinkedList();
      this.mode.addAll(mode);
      this.weightCalculator = FrameWeightCalculator.milliseconds((long)(1000 / class_3532.method_15340(modes.size(), 3, 5)));
   }

   public ListSetting(String name, ListSetting.ModeType type, List<String> mode, String... modes) {
      this.name = name;
      this.type = type;
      this.modes = Arrays.asList(modes);
      this.mode = Lists.newLinkedList();
      this.mode.addAll(mode);
      this.weightCalculator = FrameWeightCalculator.milliseconds((long)(1000 / class_3532.method_15340(this.modes.size(), 3, 5)));
   }

   public ListSetting(String name, ListSetting.ModeType type, String mode, String... modes) {
      this.name = name;
      this.type = type;
      this.modes = Arrays.asList(modes);
      this.mode = Lists.newLinkedList();
      this.mode.addAll(List.of(mode.split(",")));
      this.weightCalculator = FrameWeightCalculator.milliseconds((long)(1000 / class_3532.method_15340(this.modes.size(), 3, 5)));
   }

   public ListSetting setVisible(BooleanSupplier condition) {
      this.visibilityCondition = condition;
      return this;
   }

   public boolean contains(String mode) {
      boolean var10000 = this.mode.contains(mode);
      boolean var5 = true;
      return var10000;
   }

   @Override
   public void insert(
      MenuStyle style, float x, float y, float width, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack, class_332 context
   ) {
      if (this.visibilityCondition.getAsBoolean()) {
         this.animation.state(this.expanded);
         this.animation.update(this.weightCalculator.elapsedUnits());
         this.triangleAnimation.state(this.expanded);
         this.triangleAnimation.update(this.triangleWeightCalculator.elapsedUnits());
         renderSystem.addText(Fonts.INTER_MEDIUM_14, this.getLangService().get(this.name), x, y, poseStack);
         float boxWidth = width - 16.0F;
         float boxY = y + 10.0F;
         boolean headerHovered = MathUtil.isMouseHoveringRect((double)x, (double)boxY, (double)boxWidth, 14.0, mouseX, mouseY);
         Color[] modeColors = style.modeElement();
         renderSystem.addRect(x - 0.5F, boxY - 0.5F, boxWidth + 1.0F, 15.0F, 4.5F, modeColors[1], poseStack);
         renderSystem.addRect(x, boxY, boxWidth, 14.0F, 4.0F, headerHovered ? modeColors[1] : modeColors[0], poseStack);
         GlyphData valueFont = Fonts.INTER_REGULAR_14;
         String summary = this.getSummary();
         int maxSummaryWidth = (int)(boxWidth - 18.0F);
         if (this.getTextWidth(summary, valueFont) > maxSummaryWidth) {
            summary = valueFont.trimToWidth(summary, maxSummaryWidth);
         }

         renderSystem.addText(
            valueFont, summary, x + 5.0F, boxY + 4.5F, this.expanded ? Color.WHITE : Color.decode("#888888"), poseStack
         );
         float arrowSize = 6.0F;
         float arrowX = x + boxWidth - 5.0F - arrowSize;
         float arrowY = boxY + 7.0F - arrowSize / 2.0F;
         float arrowCenterX = arrowX + arrowSize / 2.0F;
         float arrowCenterY = arrowY + arrowSize / 2.0F;
         poseStack.method_22903();
         poseStack.method_46416(arrowCenterX, arrowCenterY, 0.0F);
         poseStack.method_22907(new Quaternionf().rotateZ((float)Math.toRadians((double)(180.0F * (1.0F - this.triangleAnimation.animation())))));
         poseStack.method_46416(-arrowCenterX, -arrowCenterY, 0.0F);
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/icons/up.png"),
            arrowX,
            arrowY,
            arrowSize,
            arrowSize,
            this.expanded ? Color.WHITE : Color.decode("#888888"),
            poseStack
         );
         poseStack.method_22909();
         if (headerHovered) {
            Melonity.hand = true;
         }

         float openAmount = this.animation.animation();
         if (!(openAmount <= 0.01F)) {
            float listHeight = (float)(this.modes.size() * 14 + 4) * openAmount;
            ScissorUtil.beginScissor((double)x, (double)(y + 26.0F), (double)width, (double)listHeight);
            float offset = 16.0F;

            for (String option : this.modes) {
               float entryY = y + 11.0F + offset;
               boolean selected = this.mode.contains(option);
               boolean entryHovered = MathUtil.isMouseHoveringRect((double)x, (double)entryY, (double)boxWidth, 13.0, mouseX, mouseY);
               int alpha = class_3532.method_15340((int)(openAmount * 255.0F), 0, 255);
               if (selected) {
                  renderSystem.addRect(x, entryY, boxWidth, 13.0F, 4.0F, RenderSystem.setAlpha(style.ascent(), alpha), poseStack);
               } else if (entryHovered) {
                  renderSystem.addRect(x, entryY, boxWidth, 13.0F, 4.0F, RenderSystem.setAlpha(modeColors[1], alpha), poseStack);
                  Melonity.hand = true;
               }

               String label = this.getLangService().get(option);
               if (label == null || label.equals(option)) {
                  label = option.substring(option.lastIndexOf(46) + 1);
               }

               renderSystem.addText(
                  valueFont,
                  valueFont.trimToWidth(label, (int)(boxWidth - 10.0F)),
                  x + 5.0F,
                  entryY + 4.0F,
                  RenderSystem.setAlpha(selected ? Color.decode("#141414") : Color.decode("#888888"), alpha),
                  poseStack
               );
               offset += 14.0F * openAmount;
            }

            ScissorUtil.endScissor();
         }
      }
   }

   private String getSummary() {
      if (this.mode.isEmpty()) {
         return "None";
      } else {
         StringBuilder builder = new StringBuilder();

         for (String selected : this.mode) {
            String label = this.getLangService().get(selected);
            if (label == null || label.equals(selected)) {
               label = selected.substring(selected.lastIndexOf(46) + 1);
            }

            if (builder.length() > 0) {
               builder.append(", ");
            }

            builder.append(label);
         }

         return builder.toString();
      }
   }

   @Override
   public void click(float x, float y, float width, double mouseX, double mouseY, int mouseButton) {
      boolean var10000 = this.visibilityCondition.getAsBoolean();
      boolean var16 = true;
      if (var10000) {
         var10000 = MathUtil.isMouseHoveringRect((double)x, (double)(y + 10.0F), (double)(width - 16.0F), 14.0, mouseX, mouseY);
         boolean var20 = true;
         if (var10000) {
            this.expanded = !this.expanded;
         }

         if (this.expanded) {
            float var9 = 16.0F;
            ArrayList var10001 = new ArrayList<>(this.mode);
            boolean var24 = true;
            this.mode = var10001;
            Iterator var67 = this.modes.iterator();
            boolean var28 = true;
            Iterator var10 = var67;

            while (true) {
               var10000 = var10.hasNext();
               boolean var32 = true;
               if (!var10000) {
                  break;
               }

               Object var69 = var10.next();
               boolean var36 = true;
               String var11 = (String)var69;

               try {
                  var10000 = MathUtil.isMouseHoveringRect((double)x, (double)(y + 11.0F + var9), (double)width, 13.0, mouseX, mouseY);
                  boolean var40 = true;
                  if (var10000 && mouseButton == 0) {
                     var10000 = this.mode.contains(var11);
                     boolean var44 = true;
                     if (var10000) {
                        if (this.type != ListSetting.ModeType.SINGLE) {
                           this.mode.remove(var11);
                           boolean var48 = true;
                        }
                     } else {
                        if (this.type == ListSetting.ModeType.SINGLE) {
                           this.mode.clear();
                           boolean var52 = true;
                        }

                        this.mode.add(var11);
                        boolean var56 = true;
                     }
                  }
               } catch (Throwable var65) {
                  var65.printStackTrace();
                  boolean var60 = true;
               }

               float var10002 = this.animation.animation();
               boolean var64 = true;
               var9 += 14.0F * var10002;
            }
         }
      }
   }

   @Override
   public void release() {
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public float getHeight() {
      boolean var10000 = this.visibilityCondition.getAsBoolean();
      boolean var4 = true;
      if (!var10000) {
         return 0.0F;
      } else {
         int var10001 = this.modes.size();
         boolean var8 = true;
         float var13 = (float)(var10001 * 14 + 4);
         float var10002 = this.animation.animation();
         boolean var12 = true;
         return 34.0F + var13 * var10002;
      }
   }

   @Override
   public void resetAnimations() {
      this.animation.reset();
      boolean var4 = true;
   }

   private int getTextWidth(String text, GlyphData font) {
      float var10000 = font.getStringWidth(text);
      boolean var6 = true;
      return (int)var10000;
   }

   public boolean testEntity(class_1309 entity, float distance) {
      if (!(entity instanceof class_746) && entity != null && Feature.mc.field_1724 != null) {
         float var10000 = Feature.mc.field_1724.method_5739(entity);
         boolean var13 = true;
         if (var10000 >= distance) {
            return false;
         } else {
            boolean var70 = entity.method_5805();
            boolean var17 = true;
            if (!var70) {
               return false;
            } else {
               boolean var71 = entity.equals(Feature.mc.field_1724);
               boolean var21 = true;
               if (var71) {
                  return false;
               } else {
                  return switch (entity) {
                     case class_1657 var5 when !this.contains("legitaura.players") -> false;
                     case class_1657 var6 when entity.method_6096() == 0 && !this.contains("legitaura.nakeds") -> false;
                     case class_1657 var7 when entity.method_5767()
                           && entity.method_6096() == 0
                           && !this.contains("legitaura.naked_invisibles") -> false;
                     case class_1657 var8 when entity.method_5767() && !this.contains("legitaura_invisibles") -> false;
                     case class_1429 var9 when !this.contains("legitaura.mobs") -> false;
                     default -> !entity.method_5655() && entity.method_5805() && !(entity instanceof class_1531);
                  };
               }
            }
         }
      } else {
         return false;
      }
   }

   @Generated
   public FrameWeightCalculator getWeightCalculator() {
      return this.weightCalculator;
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public List<String> getModes() {
      return this.modes;
   }

   @Generated
   public ListSetting.ModeType getType() {
      return this.type;
   }

   @Generated
   public List<String> getMode() {
      return this.mode;
   }

   @Generated
   public boolean isExpanded() {
      return this.expanded;
   }

   @Generated
   public StateAnimation getAnimation() {
      return this.animation;
   }

   @Generated
   public StateAnimation getTriangleAnimation() {
      return this.triangleAnimation;
   }

   @Generated
   public FrameWeightCalculator getTriangleWeightCalculator() {
      return this.triangleWeightCalculator;
   }

   @Generated
   public BooleanSupplier getVisibilityCondition() {
      return this.visibilityCondition;
   }

   @Generated
   public void setMode(List<String> mode) {
      this.mode = mode;
   }

   @Generated
   public void setExpanded(boolean expanded) {
      this.expanded = expanded;
   }

   @Generated
   public void setVisibilityCondition(BooleanSupplier visibilityCondition) {
      this.visibilityCondition = visibilityCondition;
   }

   @Environment(EnvType.CLIENT)
   public static enum ModeType {
      SINGLE,
      MULTI;
   }
}
