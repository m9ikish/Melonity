package ru.melonity.feature.features.modules.render.hud;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001d\u0010\b\u001a\u00020\u00072\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0007¢\u0006\u0004\b\b\u0010\t¨\u0006\n"},
   d2 = {"Lru/melonity/feature/features/modules/render/hud/WatermarkLayout;", "", "<init>", "()V", "", "Lru/melonity/feature/features/modules/render/hud/WatermarkView$MiniView;", "subviews", "", "totalWidth", "(Ljava/util/List;)F", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class WatermarkLayout {
   @NotNull
   public static final WatermarkLayout INSTANCE = new WatermarkLayout();

   private WatermarkLayout() {
   }

   @JvmStatic
   public static final float totalWidth(@NotNull List<WatermarkView.MiniView> subviews) {
      Intrinsics.checkNotNullParameter(subviews, "subviews");
      float local1_1 = 22.0F;

      for (WatermarkView.MiniView local3_0 : subviews) {
         local1_1 += local3_0.getWidth() + 1.0F;
      }

      return local1_1;
   }
}
