package ru.melonity.feature.features.modules.combat;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\u0006R\u0011\u0010\n\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u000b"},
   d2 = {"Lru/melonity/feature/features/modules/combat/HitboxFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "size", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "", "getExpansion", "()F", "expansion", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class HitboxFeature extends SimpleFeature {
   @NotNull
   private final NumberSetting size = new NumberSetting("global.size", HitboxFeature::size$lambda$0, 0.1F, 1, 1);

   public HitboxFeature() {
      super("Hitbox", MenuCategory.COMBAT);
      this.setting(this.size);
   }

   public final float getExpansion() {
      return this.isEnabled() ? this.size.value.floatValue() : 0.0F;
   }

   private static final String size$lambda$0(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }
}
