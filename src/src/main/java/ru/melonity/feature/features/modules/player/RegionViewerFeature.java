package ru.melonity.feature.features.modules.player;

import java.awt.Color;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_3965;
import net.minecraft.class_638;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.animation.AnimationUtil;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderGameWorldEvent;
import ru.melonity.util.render.BoxRenderer;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0011\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0002¢\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\n\u0010\u000bR\u0016\u0010\r\u001a\u00020\f8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0016\u0010\u000f\u001a\u00020\f8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u000f\u0010\u000eR\"\u0010\u0010\u001a\u00020\f8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\u0010\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R(\u0010\u0018\u001a\u0016\u0012\f\u0012\n \u0017*\u0004\u0018\u00010\u00160\u0016\u0012\u0004\u0012\u00020\u00040\u00158\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0018\u0010\u0019R\"\u0010\u001c\u001a\u0010\u0012\f\u0012\n \u0017*\u0004\u0018\u00010\u001b0\u001b0\u001a8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001c\u0010\u001d¨\u0006\u001e"},
   d2 = {"Lru/melonity/feature/features/modules/player/RegionViewerFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "", "calculateTargetRadius", "()Ljava/lang/Integer;", "Lorg/joml/Matrix4f;", "matrixStack", "", "renderRegionBoxes", "(Lorg/joml/Matrix4f;)V", "", "smoothRadius", "F", "successed", "wasTarget", "getWasTarget", "()F", "setWasTarget", "(F)V", "", "Lnet/minecraft/class_1792;", "kotlin.jvm.PlatformType", "itemToRadius", "Ljava/util/Map;", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/RenderGameWorldEvent;", "on3D", "Lru/melonity/util/event/EventListener;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class RegionViewerFeature extends SimpleFeature {
   private float smoothRadius;
   private float successed;
   private float wasTarget;
   @NotNull
   private final Map<class_1792, Integer> itemToRadius;
   @NotNull
   private final EventListener<RenderGameWorldEvent> on3D;

   public RegionViewerFeature() {
      super("RegionViewer", MenuCategory.PLAYER);
      Pair[] var1 = new Pair[]{
         TuplesKt.to(class_1802.field_8773, 2),
         TuplesKt.to(class_1802.field_8494, 3),
         TuplesKt.to(class_1802.field_8603, 5),
         TuplesKt.to(class_1802.field_8733, 7),
         TuplesKt.to(class_1802.field_8787, 10),
         TuplesKt.to(class_1802.field_8837, 15)
      };
      this.itemToRadius = MapsKt.mapOf(var1);
      this.on3D = event -> on3D$lambda$0(this, event);
   }

   public final float getWasTarget() {
      return this.wasTarget;
   }

   public final void setWasTarget(float value) {
      this.wasTarget = value;
   }

   private final Integer calculateTargetRadius() {
      class_239 local3_2 = SimpleFeature.mc.field_1765;
      class_3965 var10000 = local3_2 instanceof class_3965 ? (class_3965)local3_2 : null;
      if ((local3_2 instanceof class_3965 ? (class_3965)local3_2 : null) == null) {
         return null;
      } else {
         class_3965 local1_0 = var10000;
         if (SimpleFeature.mc.field_1724 == null) {
            return null;
         } else {
            class_746 local2_1 = SimpleFeature.mc.field_1724;
            if (SimpleFeature.mc.field_1687 == null) {
               return null;
            } else {
               class_638 local3_2x = SimpleFeature.mc.field_1687;
               class_2338 local4_3 = local1_0.method_17777();
               return local3_2x.method_8320(local4_3).method_26215() ? null : this.itemToRadius.get(local2_1.method_6047().method_7909());
            }
         }
      }
   }

   private final void renderRegionBoxes(Matrix4f matrixStack) {
      class_239 local4_2 = SimpleFeature.mc.field_1765;
      class_3965 var10000 = local4_2 instanceof class_3965 ? (class_3965)local4_2 : null;
      if ((local4_2 instanceof class_3965 ? (class_3965)local4_2 : null) != null) {
         class_3965 local2_0 = var10000;
         class_2338 local3_1 = local2_0.method_17777();
         Color local4_2x = Melonity.INSTANCE.styleManager.style.ascent();
         Color local5_3 = new Color(local4_2x.getRed(), local4_2x.getGreen(), local4_2x.getBlue(), (int)(this.successed * (float)255));
         class_2338 local6_4 = local3_1.method_10084();
         int local7_5 = (int)(this.smoothRadius / (float)2);
         class_2338 local8_6 = local6_4.method_10069(local7_5, local7_5, local7_5);
         Intrinsics.checkNotNull(local6_4);
         BoxRenderer.boxWOffset(local6_4, local5_3, matrixStack, (double)this.smoothRadius);
         Intrinsics.checkNotNull(local8_6);
         BoxRenderer.boxWOffset(local8_6, local5_3, matrixStack, (double)this.successed);
      }
   }

   private static final void on3D$lambda$0(RegionViewerFeature this$0, RenderGameWorldEvent event) {
      Integer var10000 = this$0.calculateTargetRadius();
      int local2_0 = var10000 != null ? var10000 : 0;
      if ((double)this$0.smoothRadius > 0.001) {
         Intrinsics.checkNotNull(event);
         Matrix4f var10001 = event.matrixStack();
         Intrinsics.checkNotNullExpressionValue(var10001, "matrixStack(...)");
         this$0.renderRegionBoxes(var10001);
      }

      this$0.smoothRadius = AnimationUtil.smoothAnimate((float)local2_0, this$0.smoothRadius, 20.0F);
      this$0.successed = RangesKt.coerceAtMost(this$0.smoothRadius / this$0.wasTarget, 1.0F);
      if (local2_0 != 0) {
         this$0.wasTarget = (float)local2_0;
      }
   }
}
