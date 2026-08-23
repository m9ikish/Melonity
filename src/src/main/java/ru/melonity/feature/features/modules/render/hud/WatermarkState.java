package ru.melonity.feature.features.modules.render.hud;

import kotlin.Metadata;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.util.animation.FrameWeightCalculator;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0015\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0007\u0010\bJ\r\u0010\t\u001a\u00020\u0004¢\u0006\u0004\b\t\u0010\nR\u001c\u0010\r\u001a\n \f*\u0004\u0018\u00010\u000b0\u000b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0016\u0010\u000f\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0012\u001a\u00020\u00118\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0012\u0010\u0013¨\u0006\u0014"},
   d2 = {"Lru/melonity/feature/features/modules/render/hud/WatermarkState;", "", "<init>", "()V", "", "targetFps", "", "updateFps", "(F)V", "getCurrentFps", "()F", "Lru/melonity/util/animation/FrameWeightCalculator;", "kotlin.jvm.PlatformType", "fpsFwc", "Lru/melonity/util/animation/FrameWeightCalculator;", "currentFps", "F", "", "fpsInit", "Z", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class WatermarkState {
   private final FrameWeightCalculator fpsFwc = FrameWeightCalculator.milliseconds(500L);
   private float currentFps;
   private boolean fpsInit;

   public final void updateFps(float targetFps) {
      if (!this.fpsInit) {
         this.currentFps = targetFps;
         this.fpsInit = true;
      }

      this.currentFps = this.currentFps + (targetFps - this.currentFps) * (float)8 * this.fpsFwc.elapsedUnits();
      if (this.currentFps > 10000.0F) {
         this.currentFps = 60.0F;
      }
   }

   public final float getCurrentFps() {
      return this.currentFps;
   }
}
