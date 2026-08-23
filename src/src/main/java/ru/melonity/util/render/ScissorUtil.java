package ru.melonity.util.render;

import java.util.Stack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;

@Environment(EnvType.CLIENT)
public class ScissorUtil {
   private static final Stack<ScissorUtil.ScissorBox> scissorStack = new Stack<>();

   public static void beginScissor(double x, double y, double width, double height) {
      class_310 local8_1 = class_310.method_1551();
      double local9_2 = (double)ScaleUtils.getCurrentScale();
      int local11_3 = local8_1.method_22683().method_4507();
      int local12_4 = (int)(x * local9_2);
      int local13_5 = (int)((double)local11_3 - (y + height) * local9_2);
      int local14_6 = (int)(width * local9_2);
      int local15_7 = (int)(height * local9_2);
      local14_6 = Math.max(0, local14_6);
      local15_7 = Math.max(0, local15_7);
      ScissorUtil.ScissorBox local16_8 = new ScissorUtil.ScissorBox(local12_4, local13_5, local14_6, local15_7);
      if (!scissorStack.isEmpty()) {
         ScissorUtil.ScissorBox local17_0 = scissorStack.peek();
         local16_8 = local17_0.intersect(local16_8);
      }

      scissorStack.push(local16_8);
      com.mojang.blaze3d.systems.RenderSystem.enableScissor(local16_8.x, local16_8.y, local16_8.width, local16_8.height);
   }

   public static void endScissor() {
      if (!scissorStack.isEmpty()) {
         scissorStack.pop();
         if (!scissorStack.isEmpty()) {
            ScissorUtil.ScissorBox local0_0 = scissorStack.peek();
            com.mojang.blaze3d.systems.RenderSystem.enableScissor(local0_0.x, local0_0.y, local0_0.width, local0_0.height);
         } else {
            com.mojang.blaze3d.systems.RenderSystem.disableScissor();
         }
      } else {
         com.mojang.blaze3d.systems.RenderSystem.disableScissor();
      }
   }

   public static void clear() {
      scissorStack.clear();
      com.mojang.blaze3d.systems.RenderSystem.disableScissor();
   }

   public static boolean hasActiveScissor() {
      return !scissorStack.isEmpty();
   }

   public static int getStackDepth() {
      return scissorStack.size();
   }

   @Environment(EnvType.CLIENT)
   private static class ScissorBox {
      final int x;
      final int y;
      final int width;
      final int height;

      ScissorBox(int x, int y, int width, int height) {
         this.x = x;
         this.y = y;
         this.width = width;
         this.height = height;
      }

      int right() {
         return this.x + this.width;
      }

      int bottom() {
         return this.y + this.height;
      }

      ScissorUtil.ScissorBox intersect(ScissorUtil.ScissorBox other) {
         int local2_0 = Math.max(this.x, other.x);
         int local3_1 = Math.max(this.y, other.y);
         int local4_2 = Math.min(this.right(), other.right());
         int local5_3 = Math.min(this.bottom(), other.bottom());
         int local6_4 = Math.max(0, local4_2 - local2_0);
         int local7_5 = Math.max(0, local5_3 - local3_1);
         return new ScissorUtil.ScissorBox(local2_0, local3_1, local6_4, local7_5);
      }
   }
}
