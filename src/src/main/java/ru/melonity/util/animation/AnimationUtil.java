package ru.melonity.util.animation;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J'\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004H\u0007¢\u0006\u0004\b\b\u0010\t¨\u0006\n"},
   d2 = {"Lru/melonity/util/animation/AnimationUtil;", "", "<init>", "()V", "", "target", "current", "speed", "smoothAnimate", "(FFF)F", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class AnimationUtil {
   @NotNull
   public static final AnimationUtil INSTANCE = new AnimationUtil();

   private AnimationUtil() {
   }

   @JvmStatic
   public static final float smoothAnimate(float target, float current, float speed) {
      return (double)Math.abs(target - current) < 0.01
         ? target
         : (float)((double)current + (double)(target - current) * Math.pow((double)(class_310.method_1551().method_60646().method_60636() / speed), 0.5));
   }
}
