package ru.melonity.util.macro.actions;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import org.json.JSONObject;
import ru.melonity.Melonity;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.macro.Action;
import ru.melonity.util.macro.ActionCategory;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class ChatAction implements Action {
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(300L);
   private final StateAnimation animation = new StateAnimation();
   protected final MelonityTextField textField = new MelonityTextField("Message text", false, 156.0F, 16.0F);

   public ChatAction() {
      this.textField.setOverrideTextColor(Color.WHITE);
      this.textField.setItemIconSuggestion(text -> text.contains("/") ? new class_1799(class_1802.field_8866) : null);
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
   public String getName() {
      return "Chat message";
   }

   @Override
   public JSONObject toJson() {
      JSONObject local1_0 = new JSONObject();
      local1_0.put("id", "chat");
      local1_0.put("text", this.textField.getText());
      return local1_0;
   }

   public static ChatAction fromJson(JSONObject jsonObject) {
      ChatAction local1_0 = new ChatAction();
      local1_0.textField.setText(jsonObject.getString("text"));
      local1_0.textField.setWritable(true);
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
      class_310.method_1551().field_1724.method_7353(class_2561.method_43470(this.textField.getText()), false);
   }
}
