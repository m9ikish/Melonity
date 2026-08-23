package ru.melonity.util.macro.actions;

import lombok.SneakyThrows;
import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import org.json.JSONObject;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.MelonityTextField;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.macro.ActionCategory;
import ru.melonity.util.macro.MacroTextFieldStyle;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class WaitMillisAction extends AbstractFlexAction {
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(300L);
   private final StateAnimation animation = new StateAnimation();
   private MelonityTextField textField = new MelonityTextField("100", false, 20.0F, 12.0F);
   private final MacroTextFieldStyle style = new MacroTextFieldStyle();

   @Override
   public String getName() {
      return this.isPlaced() ? "Wait" : "Wait ... ms";
   }

   @Override
   public ActionCategory getCategory() {
      return ActionCategory.CHEAT;
   }

   @Override
   public float getDelay() {
      try {
         return (float)Integer.parseInt(this.textField.getText());
      } catch (Throwable var2) {
         return 0.0F;
      }
   }

   @Override
   public void renderSettings(float x, float y, float width, float height, int mouseX, int mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      if (!this.textField.getText().isEmpty()) {
         String local9_0 = this.textField.getText().replaceAll("[^0-9]", "");
         this.textField.setText(local9_0);
      }

      this.textField.setWidth(Math.max(26.0F, Fonts.INTER_REGULAR_14.getStringWidth(this.textField.getText()) + 14.0F));
      this.textField.insert(this.style, x + 36.0F, y + 2.5F, (double)mouseX, (double)mouseY, renderSystem, poseStack);
      renderSystem.addText(Fonts.INTER_REGULAR_14, "ms", x + 36.0F + this.textField.getWidth() + 4.0F, y + 6.5F, Color.decode("#888888"), poseStack);
   }

   @Override
   public JSONObject toJson() {
      JSONObject local1_0 = new JSONObject();
      local1_0.put("id", "wait_ms");
      local1_0.put("millis", (int)this.getDelay());
      return local1_0;
   }

   public static WaitMillisAction fromJson(JSONObject jsonObject) {
      WaitMillisAction local1_0 = new WaitMillisAction();
      local1_0.textField.setText(String.valueOf(jsonObject.getInt("millis")));
      local1_0.textField.setWritable(true);
      return local1_0;
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
      this.textField
         .setFocus(
            MathUtil.isMouseHoveringRect(
               (double)(x + 36.0F), (double)(y + 2.5F), (double)this.textField.getWidth(), (double)this.textField.getHeight(), mouseX, mouseY
            )
         );
      this.textField.click(x + 36.0F, y + 2.5F, mouseX, mouseY, mouseButton);
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
      return 0.0F;
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
   @SneakyThrows
   public void run() {
      Thread.sleep((long)((int)this.getDelay()));
   }
}
