package ru.melonity.util.minecraft;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000f\u0010\u0005\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004¢\u0006\u0004\b\b\u0010\t¨\u0006\n"},
   d2 = {"Lru/melonity/util/minecraft/DeltaGCDProcessor;", "Lru/melonity/util/minecraft/MinecraftInstance;", "<init>", "()V", "", "getSensitivityMultiplier", "()F", "r", "getFixedRotation", "(F)F", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class DeltaGCDProcessor extends MinecraftInstance {
   @NotNull
   public static final DeltaGCDProcessor INSTANCE = new DeltaGCDProcessor();

   private DeltaGCDProcessor() {
   }

   private final float getSensitivityMultiplier() {
      Object var10000 = MinecraftInstance.mc.field_1690.method_42495().method_41753();
      Intrinsics.checkNotNull(var10000, "null cannot be cast to non-null type kotlin.Double");
      float local1_0 = (float)((Double)var10000).doubleValue() * 0.6F + 0.2F;
      return local1_0 * local1_0 * local1_0 * 8.0F * 0.15F;
   }

   public final float getFixedRotation(float r) {
      float local2_0 = this.getSensitivityMultiplier();
      return (float)((int)(r / local2_0)) * local2_0;
   }
}
