package ru.melonity.util.macro.actions;

import java.awt.Color;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import org.json.JSONObject;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.macro.Action;
import ru.melonity.util.macro.ActionCategory;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public abstract class AbstractToggleFunction implements Action {
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(300L);
   private final StateAnimation animation = new StateAnimation();
   private final boolean state;
   protected final MelonityTextField textField = new MelonityTextField("Feature name", false, 156.0F, 16.0F);

   public AbstractToggleFunction(boolean state) {
      this.state = state;
      this.textField.setOverrideTextColor(Color.WHITE);
      this.textField.setSmartSuggestion(name -> {
         String local1_2 = name.toLowerCase();
         String local2_3 = null;
         String local3_4 = null;
         String local4_5 = null;

         for (Feature local6_1 : Melonity.INSTANCE.getFeatureManager().getFeatures()) {
            String local7_0 = local6_1.getName().toLowerCase();
            if (local7_0.equals(local1_2)) {
               local2_3 = local6_1.getName();
               break;
            }

            if (local7_0.startsWith(local1_2)) {
               if (local3_4 == null) {
                  local3_4 = local6_1.getName();
               }
            } else if (local7_0.contains(local1_2) && local4_5 == null) {
               local4_5 = local6_1.getName();
            }
         }

         if (local2_3 != null) {
            return local2_3;
         } else if (local3_4 != null) {
            return local3_4;
         } else {
            return local4_5 != null ? local4_5 : "";
         }
      });
   }

   @Override
   public ActionCategory getCategory() {
      return ActionCategory.CHEAT;
   }

   @Override
   public float getDelay() {
      return 0.0F;
   }

   @Override
   public JSONObject toJson() {
      JSONObject local1_0 = new JSONObject();
      local1_0.put("id", "function_toggle");
      local1_0.put("state", this.state);
      local1_0.put("name", this.textField.getText());
      return local1_0;
   }

   public static AbstractToggleFunction fromJson(JSONObject jsonObject) {
      if (jsonObject.getBoolean("state")) {
         EnableFunctionAction local1_0 = new EnableFunctionAction();
         local1_0.textField.setText(jsonObject.getString("name"));
         local1_0.textField.setWritable(true);
         return local1_0;
      } else {
         DisableFunctionAction local1_1 = new DisableFunctionAction();
         local1_1.textField.setText(jsonObject.getString("name"));
         local1_1.textField.setWritable(true);
         return local1_1;
      }
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
      Optional<Feature> local1_0 = Melonity.INSTANCE.getFeatureManager().getFeature(this.textField.getText());
      if (local1_0.isEmpty()) {
         Melonity.INSTANCE.getNotificationManager().add(new Notification("Macros builder", "Feature not found", "feature-disabled.png"));
      } else {
         local1_0.get().setEnabled(this.state, false);
      }
   }
}
