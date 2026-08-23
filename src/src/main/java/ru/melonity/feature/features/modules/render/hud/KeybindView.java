package ru.melonity.feature.features.modules.render.hud;

import com.google.common.collect.Maps;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.View;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.style.elements.KeybindStyle;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.minecraft.KeyUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;

@Environment(EnvType.CLIENT)
public class KeybindView extends View {
   private final FrameWeightCalculator frameWeightCalculator = FrameWeightCalculator.milliseconds(400L);
   private final StateAnimation alphaAnimation = new StateAnimation();
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(200L);

   public KeybindView() {
      super(93.0F, 1.0F);
   }

   @Override
   public void insert(float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      List<Entry<Feature, String>> entries = this.getEntries();
      Map<Feature, String> bound = Maps.newLinkedHashMap();

      for (Entry<Feature, String> entry : entries) {
         bound.put(entry.getKey(), entry.getValue());
      }

      for (Feature feature : Melonity.INSTANCE.getFeatureManager().getFeatures()) {
         StateAnimation rowAnimation = feature.getBindsAnimation();
         rowAnimation.state(bound.containsKey(feature));
         rowAnimation.update(this.frameWeightCalculator.elapsedUnits());
      }

      this.alphaAnimation.state(!entries.isEmpty());
      this.alphaAnimation.update(this.fwc.elapsedUnits());
      float fade = this.alphaAnimation.animation();
      if (!(fade <= 0.01F)) {
         int alpha = class_3532.method_15340((int)(fade * 255.0F), 0, 255);
         MenuStyle style = Melonity.INSTANCE.getStyleManager().getStyle();
         KeybindStyle keybindStyle = style.keybind();
         float width = this.getWidth();
         float height = this.getHeight();
         GlyphData titleFont = Fonts.INTER_MEDIUM_14;
         GlyphData rowFont = Fonts.INTER_REGULAR_13;
         ScaleUtils.rescale(2.0);
         renderSystem.addRect(x, y, width, height, 6.0F, RenderSystem.setAlpha(keybindStyle.background(), alpha), poseStack);
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/icons/binds.png"),
            x + 6.0F,
            y + 5.5F,
            8.0F,
            8.0F,
            RenderSystem.setAlpha(style.ascent(), alpha),
            poseStack
         );
         renderSystem.addText(titleFont, "Keybinds", x + 18.0F, y + 6.0F, RenderSystem.setAlpha(Color.WHITE, alpha), poseStack);
         renderSystem.addRect(x, y + 19.0F, width, 1.0F, RenderSystem.setAlpha(Color.decode("#222222"), alpha), poseStack);
         float rowY = y + 20.0F;

         for (Entry<Feature, String> entry : entries) {
            float rowFade = entry.getKey().getBindsAnimation().animation();
            if (!(rowFade <= 0.01F)) {
               int rowAlpha = class_3532.method_15340((int)(fade * rowFade * 255.0F), 0, 255);
               String featureName = entry.getKey().getName();
               String keyName = entry.getValue();
               float keyWidth = rowFont.getStringWidth(keyName) + 8.0F;
               float slide = (1.0F - rowFade) * 6.0F;
               renderSystem.addText(
                  rowFont,
                  rowFont.trimToWidth(featureName, (int)(width - keyWidth - 16.0F)),
                  x + 6.0F - slide,
                  rowY + 6.5F,
                  RenderSystem.setAlpha(Color.decode("#888888"), rowAlpha),
                  poseStack
               );
               float keyX = x + width - 6.0F - keyWidth;
               renderSystem.addRect(keyX, rowY + 3.0F, keyWidth, 12.0F, 3.5F, RenderSystem.setAlpha(keybindStyle.keyBackground(), rowAlpha), poseStack);
               renderSystem.addText(
                  rowFont,
                  keyName,
                  keyX + keyWidth / 2.0F - rowFont.getStringWidth(keyName) / 2.0F,
                  rowY + 6.5F,
                  RenderSystem.setAlpha(keybindStyle.shadow(), rowAlpha),
                  poseStack
               );
               rowY += 18.0F * rowFade;
            }
         }

         ScaleUtils.rescaleMC();
      }
   }

   private List<Entry<Feature, String>> getEntries() {
      Map<Feature, String> local1_3 = Maps.newLinkedHashMap();

      for (Feature local3_2 : Melonity.INSTANCE.getFeatureManager().getFeatures()) {
         BindSetting local4_0 = local3_2.getBindSetting();
         if (local4_0 != null) {
            String local5_1 = KeyUtil.getKeyName(local4_0.bind);
            if (!local5_1.equals("?") && !local5_1.equals("NONE")) {
               local1_3.put(local3_2, local5_1);
            }
         }
      }

      return List.copyOf(local1_3.entrySet());
   }

   @Override
   public float getHeight() {
      return (float)(20 + this.getEntries().size() * 18);
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public void release() {
   }
}
