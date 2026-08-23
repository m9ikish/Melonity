package ru.melonity.util.macro.actions;

import java.awt.Color;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1935;
import net.minecraft.class_2561;
import net.minecraft.class_2868;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_5321;
import net.minecraft.class_7923;
import org.json.JSONObject;
import ru.melonity.Melonity;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.inventory.InventoryUtil;
import ru.melonity.util.macro.Action;
import ru.melonity.util.macro.ActionCategory;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class UseItemAction implements Action {
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(300L);
   private final StateAnimation animation = new StateAnimation();
   private boolean needUpdate = true;
   private class_1792 itemSuggested = null;
   private String suggested = "";
   private final MelonityTextField textField = new MelonityTextField("Item", false, 156.0F, 16.0F);

   public UseItemAction() {
      this.textField.setOverrideTextColor(Color.WHITE);
      this.textField.setSmartSuggestion(text -> {
         if (this.needUpdate) {
            this.suggested = null;
            boolean local2_3 = false;

            for (Entry<class_5321<class_1792>, class_1792> local4_2 : class_7923.field_41178.method_29722()) {
               class_1799 local5_0 = new class_1799((class_1935)local4_2.getValue());
               String local6_1 = local4_2.getValue().method_7864(local5_0).getString();
               if (local6_1.toLowerCase().startsWith(text.toLowerCase())) {
                  this.suggested = local4_2.getValue().method_7864(local5_0).getString();
                  this.itemSuggested = local4_2.getValue();
                  local2_3 = true;
               } else if (local6_1.contains(text) && !local2_3) {
                  this.suggested = local4_2.getValue().method_7864(local5_0).getString();
                  this.itemSuggested = local4_2.getValue();
               }
            }

            this.needUpdate = false;
         }

         return this.suggested;
      });
      this.textField.setItemIconSuggestion(text -> this.suggested != null && this.itemSuggested != null ? new class_1799(this.itemSuggested) : null);
   }

   @Override
   public String getName() {
      return "Use item";
   }

   @Override
   public ActionCategory getCategory() {
      return ActionCategory.PLAYER;
   }

   @Override
   public float getDelay() {
      return 0.0F;
   }

   @Override
   public JSONObject toJson() {
      JSONObject local1_0 = new JSONObject();
      local1_0.put("id", "use_item");
      local1_0.put("item", this.textField.getText());
      return local1_0;
   }

   public static UseItemAction fromJson(JSONObject jsonObject) {
      UseItemAction local1_0 = new UseItemAction();
      local1_0.textField.setText(jsonObject.getString("item"));
      local1_0.textField.setWritable(true);
      local1_0.suggested = local1_0.textField.getText();
      return local1_0;
   }

   @Override
   public void renderSettings(float x, float y, float width, float height, int mouseX, int mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      MenuStyle local9_0 = Melonity.INSTANCE.styleManager.getStyle();
      renderSystem.addRectGradientCorners(
         x - 0.5F + 1.0F,
         y + 10.0F - 0.5F,
         width + 1.0F - 2.0F,
         30.0F,
         8.0F,
         0.0F,
         0.0F,
         8.0F,
         local9_0.previewValues(),
         local9_0.previewValues(),
         local9_0.previewValues(),
         local9_0.previewValues(),
         poseStack
      );
      renderSystem.addRectGradientCorners(
         x + 1.0F,
         y + 10.0F,
         width - 2.0F,
         29.0F,
         8.0F,
         0.0F,
         0.0F,
         8.0F,
         local9_0.autobuyPanel(),
         local9_0.autobuyPanel(),
         local9_0.autobuyPanel(),
         local9_0.autobuyPanel(),
         poseStack
      );
      this.textField.insert(Melonity.INSTANCE.styleManager.getStyle(), x + 4.0F, y + 20.0F, (double)mouseX, (double)mouseY, renderSystem, poseStack);
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
      this.textField
         .setFocus(
            MathUtil.isMouseHoveringRect(
               (double)(x + 4.0F), (double)(y + 20.0F), (double)this.textField.getWidth(), (double)this.textField.getHeight(), mouseX, mouseY
            )
         );
      this.textField.click(x + 4.0F, y + 20.0F, mouseX, mouseY, mouseButton);
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      this.needUpdate = true;
      return this.textField.key(keyCode, scanCode, modifiers);
   }

   @Override
   public void release() {
      this.textField.release();
   }

   @Override
   public float getOffset() {
      return 25.0F;
   }

   @Override
   public StateAnimation getAnimation() {
      return this.animation;
   }

   @Override
   public FrameWeightCalculator getFwc() {
      return this.fwc;
   }

   @Override
   public void run() {
      class_1792 local1_2 = null;

      for (Entry<class_5321<class_1792>, class_1792> local3_1 : class_7923.field_41178.method_29722()) {
         String local4_0 = local3_1.getValue().method_7864(new class_1799((class_1935)local3_1.getValue())).getString();
         if (local4_0.equalsIgnoreCase(this.textField.getText())) {
            local1_2 = local3_1.getValue();
         } else if (local4_0.startsWith(this.textField.getText())) {
            local1_2 = local3_1.getValue();
         }
      }

      class_310 local2_3 = class_310.method_1551();
      int local3_4 = InventoryUtil.getHotbarItem(local2_3.field_1724, local1_2);
      if (local3_4 == -1) {
         local3_4 = InventoryUtil.getHotbarItem(local2_3.field_1724, this.textField.getText());
      }

      if (local3_4 == -1) {
         local2_3.field_1705
            .method_1743()
            .method_1812(class_2561.method_43470("Item not found in hotbar: " + local1_2.method_7864(new class_1799(local1_2)).getString()));
      } else {
         local2_3.field_1724.field_3944.method_52787(new class_2868(local3_4));
         local2_3.field_1761.method_2919(local2_3.field_1724, class_1268.field_5808);
         local2_3.field_1724.method_6104(class_1268.field_5808);
         local2_3.field_1724.field_3944.method_52787(new class_2868(local2_3.field_1724.method_31548().field_7545));
      }
   }
}
