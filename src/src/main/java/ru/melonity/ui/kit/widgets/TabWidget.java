package ru.melonity.ui.kit.widgets;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import org.joml.Vector2f;
import ru.melonity.font.Fonts;
import ru.melonity.ui.kit.Widget;
import ru.melonity.ui.kit.render.RenderEngine;
import ru.melonity.ui.kit.widgets.state.TabState;
import ru.melonity.util.font.GlyphData;

@Environment(EnvType.CLIENT)
public class TabWidget extends Widget<TabState> {
   private final class_2960 icon;
   private final String text;

   public TabWidget(class_2960 icon, String text) {
      super(new Vector2f(94.0F, 22.0F));
      this.icon = icon;
      this.text = text;
   }

   @Override
   public void render(Vector2f position, RenderEngine render) {
      Color background = switch (((TabState)this.state()).variant) {
         case DEFAULT -> new Color(0, 0, 0, 0);
         case HOVER -> Color.decode("#181818");
         case ACTIVE -> Color.decode("#D3F942");
      };
      Color foreground = switch (((TabState)this.state()).variant) {
         case DEFAULT, HOVER -> Color.decode("#888888");
         case ACTIVE -> Color.decode("#141414");
      };
      render.rect(position, this.size, 8.0F, background);
      render.image(
         this.icon,
         new Vector2f(position.x + 8.0F, position.y + 6.0F),
         new Vector2f(9.5F, 9.5F),
         foreground
      );
      render.text(
         this.text,
         Fonts.INTER_MEDIUM_16,
         new Vector2f(position.x + 19.5F, position.y + 9.0F),
         foreground
      );
   }
}
