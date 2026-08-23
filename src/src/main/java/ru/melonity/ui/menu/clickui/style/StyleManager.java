package ru.melonity.ui.menu.clickui.style;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.ui.menu.clickui.style.impl.CalmGrayStyle;
import ru.melonity.ui.menu.clickui.style.impl.DeepOceanStyle;
import ru.melonity.ui.menu.clickui.style.impl.GradientNoirStyle;
import ru.melonity.ui.menu.clickui.style.impl.MainStyle;
import ru.melonity.ui.menu.clickui.style.impl.PickMeStyle;
import ru.melonity.ui.menu.clickui.style.impl.ViolentRedStyle;

@Environment(EnvType.CLIENT)
public class StyleManager {
   public final List<MenuStyle> styles = Arrays.asList(
      new MainStyle(), new GradientNoirStyle(), new DeepOceanStyle(), new CalmGrayStyle(), new PickMeStyle(), new ViolentRedStyle()
   );
   public MenuStyle style = this.styles.getFirst();

   public Optional<MenuStyle> findStyleByName(String name) {
      return this.styles.stream().filter(it -> it.name().equals(name)).findFirst();
   }

   @Generated
   public List<MenuStyle> getStyles() {
      return this.styles;
   }

   @Generated
   public MenuStyle getStyle() {
      return this.style;
   }

   @Generated
   public void setStyle(MenuStyle style) {
      this.style = style;
   }
}
