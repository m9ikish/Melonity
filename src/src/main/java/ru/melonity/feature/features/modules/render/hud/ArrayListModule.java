package ru.melonity.feature.features.modules.render.hud;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_4587;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.View;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.animation.AnimationUtil;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\t\u0018\u0000 P2\u00020\u0001:\u0005QRSTPB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0015\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0007\u0010\bJ?\u0010\u0013\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0011H\u0016¢\u0006\u0004\b\u0013\u0010\u0014J7\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0015H\u0016¢\u0006\u0004\b\u0017\u0010\u0018J'\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0015H\u0016¢\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001e\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\u001e\u0010\u0003J\r\u0010\u001f\u001a\u00020\t¢\u0006\u0004\b\u001f\u0010 J\r\u0010!\u001a\u00020\t¢\u0006\u0004\b!\u0010 J\u0015\u0010$\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0002¢\u0006\u0004\b$\u0010%J%\u0010*\u001a\u00020)2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020#0\"2\u0006\u0010(\u001a\u00020'H\u0002¢\u0006\u0004\b*\u0010+J\u000f\u0010,\u001a\u00020\tH\u0002¢\u0006\u0004\b,\u0010 J%\u00100\u001a\u00020\u00062\f\u0010.\u001a\b\u0012\u0004\u0012\u00020-0\"2\u0006\u0010/\u001a\u00020\tH\u0002¢\u0006\u0004\b0\u00101J\u0015\u00103\u001a\b\u0012\u0004\u0012\u0002020\"H\u0002¢\u0006\u0004\b3\u0010%J\u001d\u00105\u001a\u00020\t2\f\u00104\u001a\b\u0012\u0004\u0012\u0002020\"H\u0002¢\u0006\u0004\b5\u00106J\u001f\u0010:\u001a\u0002072\u0006\u00108\u001a\u0002072\u0006\u00109\u001a\u00020\tH\u0002¢\u0006\u0004\b:\u0010;J\u0017\u0010=\u001a\u00020\t2\u0006\u0010<\u001a\u00020\tH\u0002¢\u0006\u0004\b=\u0010>J\u0017\u0010@\u001a\u0002072\u0006\u0010?\u001a\u000207H\u0002¢\u0006\u0004\b@\u0010AJ'\u0010E\u001a\u0002072\u0006\u0010B\u001a\u0002072\u0006\u0010C\u001a\u0002072\u0006\u0010D\u001a\u00020\tH\u0002¢\u0006\u0004\bE\u0010FR0\u0010J\u001a\u001e\u0012\u0004\u0012\u00020H\u0012\u0004\u0012\u0002020Gj\u000e\u0012\u0004\u0012\u00020H\u0012\u0004\u0012\u000202`I8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bJ\u0010KR\u0016\u0010M\u001a\u00020L8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bM\u0010NR\u0016\u0010\u0005\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0005\u0010O¨\u0006U"},
   d2 = {"Lru/melonity/feature/features/modules/render/hud/ArrayListModule;", "Lru/melonity/ui/components/view/View;", "<init>", "()V", "", "alignRight", "", "setAlignRight", "(Z)V", "", "x", "y", "", "mouseX", "mouseY", "Lru/melonity/util/render/RenderSystem;", "renderSystem", "Lnet/minecraft/class_4587;", "poseStack", "insert", "(FFDDLru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "", "mouseButton", "click", "(FFDDI)V", "keyCode", "scanCode", "modifiers", "key", "(III)Z", "release", "measureWidth", "()F", "measureHeight", "", "Lru/melonity/feature/features/modules/render/hud/ArrayListModule$Entry;", "getMeasuredEntries", "()Ljava/util/List;", "entries", "Lru/melonity/util/font/GlyphData;", "font", "Lru/melonity/feature/features/modules/render/hud/ArrayListModule$LayoutMetrics;", "measure", "(Ljava/util/List;Lru/melonity/util/font/GlyphData;)Lru/melonity/feature/features/modules/render/hud/ArrayListModule$LayoutMetrics;", "getDeltaTimeSeconds", "Lru/melonity/feature/features/modules/render/hud/ArrayListModule$RowLayout;", "layouts", "deltaTime", "updateRowAnimations", "(Ljava/util/List;F)V", "Lru/melonity/feature/features/modules/render/hud/ArrayListModule$RowAnimationState;", "getRenderableRows", "rows", "getMaxRowWidth", "(Ljava/util/List;)F", "Ljava/awt/Color;", "color", "alpha", "withAlpha", "(Ljava/awt/Color;F)Ljava/awt/Color;", "t", "easeOutQuad", "(F)F", "baseColor", "animatedAccentColor", "(Ljava/awt/Color;)Ljava/awt/Color;", "from", "to", "tInput", "lerpColor", "(Ljava/awt/Color;Ljava/awt/Color;F)Ljava/awt/Color;", "Ljava/util/LinkedHashMap;", "", "Lkotlin/collections/LinkedHashMap;", "rowAnimations", "Ljava/util/LinkedHashMap;", "", "lastAnimationTime", "J", "Z", "Companion", "Entry", "RowLayout", "LayoutMetrics", "RowAnimationState", "melonity_client"}
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\nArrayListModule.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ArrayListModule.kt\nru/melonity/feature/features/modules/render/hud/ArrayListModule\n+ 2 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,289:1\n622#2:290\n1563#3:291\n1634#3,3:292\n1068#3:302\n382#4,7:295\n*S KotlinDebug\n*F\n+ 1 ArrayListModule.kt\nru/melonity/feature/features/modules/render/hud/ArrayListModule\n*L\n155#1:290\n163#1:291\n163#1:292,3\n243#1:302\n213#1:295,7\n*E\n"})
public final class ArrayListModule extends View {
   @NotNull
   private static final ArrayListModule.Companion Companion = new ArrayListModule.Companion(null);
   @NotNull
   private final LinkedHashMap<String, ArrayListModule.RowAnimationState> rowAnimations = new LinkedHashMap<>();
   private long lastAnimationTime = System.currentTimeMillis();
   private boolean alignRight = true;
   private static final float LEFT_PADDING = 6.0F;
   private static final float RIGHT_PADDING = 4.0F;
   private static final float VERTICAL_PADDING = 3.0F;
   private static final float ENTRY_SPACING = 2.0F;
   private static final float ACCENT_GAP = 4.0F;
   private static final float ACCENT_WIDTH = 2.0F;
   private static final float ACCENT_MARGIN = 3.0F;
   private static final float ACCENT_RADIUS = 1.0F;
   private static final float MIN_TEXT_HEIGHT = 8.0F;

   public ArrayListModule() {
      super(0.0F, 0.0F);
   }

   public final void setAlignRight(boolean alignRight) {
      this.alignRight = alignRight;
   }

   @Override
   public void insert(float x, float y, double mouseX, double mouseY, @NotNull RenderSystem renderSystem, @NotNull class_4587 poseStack) {
      Intrinsics.checkNotNullParameter(renderSystem, "renderSystem");
      boolean var32 = true;
      Intrinsics.checkNotNullParameter(poseStack, "poseStack");
      boolean var36 = true;
      List var10000 = this.getMeasuredEntries();
      boolean var40 = true;
      List var9 = var10000;
      GlyphData var10 = Fonts.INTER_REGULAR_12;
      Intrinsics.checkNotNull(var10);
      boolean var44 = true;
      ArrayListModule.LayoutMetrics var210 = this.measure(var9, var10);
      boolean var48 = true;
      ArrayListModule.LayoutMetrics var11 = var210;
      float var211 = this.getDeltaTimeSeconds();
      boolean var52 = true;
      float var12 = var211;
      List var10001 = var11.getRows();
      boolean var56 = true;
      this.updateRowAnimations(var10001, var12);
      boolean var60 = true;
      var10000 = this.getRenderableRows();
      boolean var64 = true;
      List var13 = var10000;
      boolean var213 = var13.isEmpty();
      boolean var68 = true;
      if (var213) {
         this.setWidth(0.0F);
         boolean var72 = true;
         this.setHeight(0.0F);
         boolean var76 = true;
      } else {
         float var214 = var11.getMaxWidth();
         boolean var80 = true;
         float var228 = this.getMaxRowWidth(var13);
         boolean var84 = true;
         float var215 = Math.max(var214, var228);
         boolean var88 = true;
         float var14 = var215;
         this.setWidth(var14);
         boolean var92 = true;
         ScaleUtils.rescale(2.0);
         boolean var96 = true;
         MenuStyle var216 = Melonity.INSTANCE.styleManager.style;
         Intrinsics.checkNotNullExpressionValue(Melonity.INSTANCE.styleManager.style, "style");
         boolean var100 = true;
         MenuStyle var15 = var216;
         float var16 = y;
         float var17 = 5.0F;
         int var18 = 0;
         int var217 = var13.size();
         boolean var104 = true;

         for (int var19 = var217; var18 < var19; var18++) {
            boolean var108 = true;
            ArrayListModule.RowAnimationState var20 = (ArrayListModule.RowAnimationState)var13.get(var18);
            float var229 = var20.getProgress();
            boolean var112 = true;
            float var219 = this.easeOutQuad(var229);
            boolean var116 = true;
            float var21 = var219;
            if (!(var21 <= 0.001F)) {
               float var220 = var20.getRowWidth();
               boolean var120 = true;
               float var22 = var220;
               float var221 = var20.getRowHeight();
               boolean var124 = true;
               float var23 = var221 * var21;
               float var24 = this.alignRight ? x + (var14 - var22) : x;
               Color var230 = var15.background();
               boolean var128 = true;
               Intrinsics.checkNotNullExpressionValue(var230, "background(...)");
               boolean var132 = true;
               Color var222 = this.withAlpha(var230, 1.0F);
               boolean var136 = true;
               Color var26 = var222;
               renderSystem.addRect(var24, var16, var22, var23 + 1.0F, var17, var26, poseStack);
               boolean var140 = true;
               float var27 = var16 + var23 / 2.0F;
               Color var231 = Color.WHITE;
               Intrinsics.checkNotNullExpressionValue(Color.WHITE, "WHITE");
               boolean var144 = true;
               Color var223 = this.withAlpha(var231, var21);
               boolean var148 = true;
               Color var28 = var223;
               String var10002 = var20.getText();
               boolean var152 = true;
               renderSystem.addText(var10, var10002, var24 + 6.0F, var27, var28, poseStack);
               boolean var156 = true;
               var16 += var23;
            }
         }

         float var205 = this.alignRight ? x + var14 - 4.0F - 2.0F : x + 1.0F;
         float var224 = Math.max(0.0F, var16);
         boolean var160 = true;
         float var206 = var224;
         if (var206 > 0.0F) {
            float var232 = var205 + (!this.alignRight ? -2.0F : 0.0F);
            float var10004 = var206 - y;
            Color var10006 = var15.background();
            boolean var164 = true;
            renderSystem.addRect(var232, y, 6.0F, var10004, var17, var10006, poseStack);
            boolean var168 = true;
            float var225 = Math.max(0.0F, -y + var206 - 6.0F);
            boolean var172 = true;
            float var207 = var225;
            if (var207 > 0.0F) {
               Color var233 = var15.ascent();
               boolean var176 = true;
               Intrinsics.checkNotNullExpressionValue(var233, "ascent(...)");
               boolean var180 = true;
               Color var226 = this.animatedAccentColor(var233);
               boolean var184 = true;
               Color var208 = var226;
               Color var227 = var208.darker();
               boolean var188 = true;
               Color var209 = var227;
               renderSystem.addRectGradient(var205, y + 3.0F, 2.0F, var207, 1.0F, var209, var208, var208, var209, poseStack);
               boolean var192 = true;
            }
         }

         float var234 = Math.max(0.0F, var206 - y);
         boolean var196 = true;
         this.setHeight(var234);
         boolean var200 = true;
         ScaleUtils.rescaleMC();
         boolean var204 = true;
      }
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

   public final float measureWidth() {
      List var10000 = this.getMeasuredEntries();
      boolean var5 = true;
      List var1 = var10000;
      boolean var22 = var1.isEmpty();
      boolean var9 = true;
      if (var22) {
         return 0.0F;
      } else {
         GlyphData var10002 = Fonts.INTER_REGULAR_12;
         Intrinsics.checkNotNullExpressionValue(Fonts.INTER_REGULAR_12, "INTER_REGULAR_12");
         boolean var13 = true;
         ArrayListModule.LayoutMetrics var23 = this.measure(var1, var10002);
         boolean var17 = true;
         float var24 = var23.getMaxWidth();
         boolean var21 = true;
         return var24;
      }
   }

   public final float measureHeight() {
      List var10000 = this.getMeasuredEntries();
      boolean var5 = true;
      List var1 = var10000;
      boolean var22 = var1.isEmpty();
      boolean var9 = true;
      if (var22) {
         return 0.0F;
      } else {
         GlyphData var10002 = Fonts.INTER_REGULAR_12;
         Intrinsics.checkNotNullExpressionValue(Fonts.INTER_REGULAR_12, "INTER_REGULAR_12");
         boolean var13 = true;
         ArrayListModule.LayoutMetrics var23 = this.measure(var1, var10002);
         boolean var17 = true;
         float var24 = var23.getTotalHeight();
         boolean var21 = true;
         return var24;
      }
   }

   private final List<ArrayListModule.Entry> getMeasuredEntries() {
      GlyphData var10000 = Fonts.INTER_REGULAR_12;
      Intrinsics.checkNotNullExpressionValue(Fonts.INTER_REGULAR_12, "INTER_REGULAR_12");
      boolean var7 = true;
      GlyphData var1 = var10000;
      List var36 = Melonity.INSTANCE.featureManager.features;
      Intrinsics.checkNotNullExpressionValue(Melonity.INSTANCE.featureManager.features, "features");
      boolean var11 = true;
      Sequence var37 = CollectionsKt.asSequence(var36);
      Function1<Feature, Boolean> var42 = Feature::isEnabled;
      Sequence var38 = SequencesKt.filter(var37, var42);
      Function1<Feature, ArrayListModule.Entry> var43 = feature -> getMeasuredEntries$lambda$0(var1, feature);
      Sequence var39 = SequencesKt.map(var38, var43);
      Sequence var2 = var39;
      boolean var3 = false;
      ArrayListModule$getMeasuredEntries$$inlined$sortedByDescending$1 var10001 = new ArrayListModule$getMeasuredEntries$$inlined$sortedByDescending$1();
      boolean var27 = true;
      Sequence var40 = SequencesKt.sortedWith(var2, var10001);
      boolean var31 = true;
      List var41 = SequencesKt.toList(var40);
      boolean var35 = true;
      return var41;
   }

   private final ArrayListModule.LayoutMetrics measure(List<ArrayListModule.Entry> entries, GlyphData font) {
      float var3 = 0.0F;
      float var4 = 0.0F;
      Iterable var6 = entries;
      boolean var7 = false;
      int var10002 = CollectionsKt.collectionSizeOrDefault(var6, 10);
      boolean var23 = true;
      ArrayList var10000 = new ArrayList(var10002);
      boolean var27 = true;
      Collection var9 = var10000;
      boolean var10 = false;
      Iterator var98 = var6.iterator();
      boolean var31 = true;
      Iterator var11 = var98;

      while (true) {
         boolean var99 = var11.hasNext();
         boolean var35 = true;
         if (!var99) {
            List var5 = (List)var9;
            int var96 = 0;
            int var105 = var5.size();
            boolean var71 = true;

            for (int var97 = var105; var96 < var97; var96++) {
               boolean var75 = true;
               ArrayListModule.RowLayout var8 = (ArrayListModule.RowLayout)var5.get(var96);
               float var109 = var8.getRowWidth();
               boolean var79 = true;
               float var107 = Math.max(var3, var109);
               boolean var83 = true;
               var3 = var107;
               var109 = var8.getRowHeight();
               boolean var87 = true;
               var4 += var109;
               int var111 = var5.size();
               boolean var91 = true;
               if (var96 != var111 - 1) {
                  var4 += 2.0F;
               }
            }

            ArrayListModule.LayoutMetrics var108 = new ArrayListModule.LayoutMetrics(var5, var3, var4);
            boolean var95 = true;
            return var108;
         }

         boolean var39 = true;
         Object var12 = var11.next();
         ArrayListModule.Entry var13 = (ArrayListModule.Entry)var12;
         boolean var14 = false;
         float var101 = var13.getTextWidth();
         boolean var43 = true;
         float var15 = var101;
         String var10001 = var13.getText();
         boolean var47 = true;
         float var102 = font.getStringHeight(var10001);
         boolean var51 = true;
         float var103 = Math.max(var102, 8.0F);
         boolean var55 = true;
         float var16 = var103;
         float var17 = var16 + 6.0F;
         float var18 = var15 + 6.0F + 4.0F + 4.0F + 2.0F;
         String var112 = var13.getText();
         boolean var59 = true;
         ArrayListModule.RowLayout var104 = new ArrayListModule.RowLayout(var112, var15, var16, var18, var17);
         boolean var63 = true;
         var9.add(var104);
         boolean var67 = true;
      }
   }

   private final float getDeltaTimeSeconds() {
      long var10000 = System.currentTimeMillis();
      boolean var7 = true;
      long var1 = var10000;
      float var3 = (float)(var1 - this.lastAnimationTime) / 1000.0F;
      this.lastAnimationTime = var1;
      float var12 = Math.min(var3, 0.1F);
      boolean var11 = true;
      return var12;
   }

   private final void updateRowAnimations(List<ArrayListModule.RowLayout> layouts, float deltaTime) {
      HashSet var10000 = new HashSet();
      boolean var15 = true;
      HashSet var3 = var10000;
      Iterator var174 = layouts.iterator();
      boolean var19 = true;
      Iterator var4 = var174;

      while (true) {
         boolean var175 = var4.hasNext();
         boolean var23 = true;
         if (!var175) {
            Set var181 = this.rowAnimations.entrySet();
            boolean var87 = true;
            Iterator var182 = var181.iterator();
            boolean var91 = true;
            var4 = var182;

            while (true) {
               boolean var183 = var4.hasNext();
               boolean var95 = true;
               if (!var183) {
                  float var165 = deltaTime * 4.0F;
                  Set var188 = this.rowAnimations.entrySet();
                  boolean var119 = true;
                  Iterator var189 = var188.iterator();
                  boolean var123 = true;
                  Iterator var167 = var189;

                  while (true) {
                     boolean var190 = var167.hasNext();
                     boolean var127 = true;
                     if (!var190) {
                        return;
                     }

                     boolean var131 = true;
                     Map.Entry var169 = (Map.Entry)var167.next();
                     boolean var135 = true;
                     ArrayListModule.RowAnimationState var171 = (ArrayListModule.RowAnimationState)var169.getValue();
                     boolean var193 = var171.getVisible();
                     boolean var139 = true;
                     float var172 = var193 ? 1.0F : 0.0F;
                     float var200 = var171.getProgress();
                     boolean var143 = true;
                     float var199 = AnimationUtil.smoothAnimate(var172, var200, 40.0F);
                     boolean var147 = true;
                     var171.setProgress(var199);
                     boolean var151 = true;
                     boolean var194 = var171.getVisible();
                     boolean var155 = true;
                     if (!var194) {
                        float var195 = var171.getProgress();
                        boolean var159 = true;
                        if (var195 <= 0.001F) {
                           var167.remove();
                           boolean var163 = true;
                        }
                     }
                  }
               }

               boolean var99 = true;
               Map.Entry var166 = (Map.Entry)var4.next();
               boolean var103 = true;
               String var168 = (String)var166.getKey();
               boolean var107 = true;
               ArrayListModule.RowAnimationState var170 = (ArrayListModule.RowAnimationState)var166.getValue();
               boolean var187 = var3.contains(var168);
               boolean var111 = true;
               if (!var187) {
                  var170.setVisible(false);
                  boolean var115 = true;
               }
            }
         }

         boolean var27 = true;
         ArrayListModule.RowLayout var5 = (ArrayListModule.RowLayout)var4.next();
         Map var7 = this.rowAnimations;
         String var177 = var5.getText();
         boolean var31 = true;
         String var8 = var177;
         boolean var9 = false;
         boolean var35 = true;
         Object var10 = var7.get(var8);
         ArrayListModule.RowAnimationState var6;
         if (var10 == null) {
            boolean var11 = false;
            String var10002 = var5.getText();
            boolean var39 = true;
            ArrayListModule.RowAnimationState var179 = new ArrayListModule.RowAnimationState(var10002);
            boolean var43 = true;
            ArrayListModule.RowAnimationState var173 = var179;
            var7.put(var8, var173);
            boolean var47 = true;
            var6 = var173;
         } else {
            var6 = (ArrayListModule.RowAnimationState)var10;
         }

         float var10001 = var5.getRowWidth();
         boolean var51 = true;
         var6.setRowWidth(var10001);
         boolean var55 = true;
         var10001 = var5.getRowHeight();
         boolean var59 = true;
         var6.setRowHeight(var10001);
         boolean var63 = true;
         var10001 = var5.getTextWidth();
         boolean var67 = true;
         var6.setTextWidth(var10001);
         boolean var71 = true;
         var6.setVisible(true);
         boolean var75 = true;
         String var198 = var5.getText();
         boolean var79 = true;
         var3.add(var198);
         boolean var83 = true;
      }
   }

   private final List<ArrayListModule.RowAnimationState> getRenderableRows() {
      Collection var10000 = this.rowAnimations.values();
      boolean var6 = true;
      Intrinsics.checkNotNullExpressionValue(var10000, "<get-values>(...)");
      boolean var10 = true;
      Iterable var1 = var10000;
      boolean var2 = false;
      ArrayListModule$getRenderableRows$$inlined$sortedByDescending$1 var10001 = new ArrayListModule$getRenderableRows$$inlined$sortedByDescending$1();
      boolean var14 = true;
      List var19 = CollectionsKt.sortedWith(var1, var10001);
      boolean var18 = true;
      return var19;
   }

   private final float getMaxRowWidth(List<ArrayListModule.RowAnimationState> rows) {
      float var2 = 0.0F;
      Iterator var10000 = rows.iterator();
      boolean var8 = true;
      Iterator var3 = var10000;

      while (true) {
         boolean var25 = var3.hasNext();
         boolean var12 = true;
         if (!var25) {
            return var2;
         }

         boolean var16 = true;
         ArrayListModule.RowAnimationState var4 = (ArrayListModule.RowAnimationState)var3.next();
         float var10001 = var4.getRowWidth();
         boolean var20 = true;
         float var27 = Math.max(var2, var10001);
         boolean var24 = true;
         var2 = var27;
      }
   }

   private final Color withAlpha(Color color, float alpha) {
      float var10001 = Math.min(1.0F, alpha);
      boolean var7 = true;
      float var10000 = Math.max(0.0F, var10001);
      boolean var11 = true;
      int var3 = (int)(var10000 * 255.0F);
      int var10002 = color.getRed();
      boolean var15 = true;
      int var10003 = color.getGreen();
      boolean var19 = true;
      int var10004 = color.getBlue();
      boolean var23 = true;
      Color var28 = new Color(var10002, var10003, var10004, var3);
      boolean var27 = true;
      return var28;
   }

   private final float easeOutQuad(float t) {
      return 1.0F - (1.0F - t) * (1.0F - t);
   }

   private final Color animatedAccentColor(Color baseColor) {
      Color var10000 = baseColor.darker();
      boolean var11 = true;
      var10000 = var10000.darker();
      boolean var15 = true;
      Color var2 = var10000;
      long var3 = 4000L;
      long var33 = System.currentTimeMillis();
      boolean var19 = true;
      double var5 = (double)(var33 % var3) / (double)var3;
      double var34 = Math.sin(var5 * (double)2 * Math.PI - (Math.PI / 2));
      boolean var23 = true;
      float var7 = (float)((var34 + (double)1) / 2.0);
      Intrinsics.checkNotNull(var2);
      boolean var27 = true;
      var10000 = this.lerpColor(var2, baseColor, var7);
      boolean var31 = true;
      return var10000;
   }

   private final Color lerpColor(Color from, Color to, float tInput) {
      float var10001 = Math.min(1.0F, tInput);
      boolean var12 = true;
      float var10000 = Math.max(0.0F, var10001);
      boolean var16 = true;
      float var4 = var10000;
      int var69 = from.getRed();
      boolean var20 = true;
      var10000 = (float)var69;
      int var78 = to.getRed();
      boolean var24 = true;
      int var10002 = from.getRed();
      boolean var28 = true;
      int var5 = (int)(var10000 + (float)(var78 - var10002) * var4);
      int var71 = from.getGreen();
      boolean var32 = true;
      var10000 = (float)var71;
      int var79 = to.getGreen();
      boolean var36 = true;
      var10002 = from.getGreen();
      boolean var40 = true;
      int var6 = (int)(var10000 + (float)(var79 - var10002) * var4);
      int var73 = from.getBlue();
      boolean var44 = true;
      var10000 = (float)var73;
      int var80 = to.getBlue();
      boolean var48 = true;
      var10002 = from.getBlue();
      boolean var52 = true;
      int var7 = (int)(var10000 + (float)(var80 - var10002) * var4);
      int var75 = from.getAlpha();
      boolean var56 = true;
      var10000 = (float)var75;
      int var81 = to.getAlpha();
      boolean var60 = true;
      var10002 = from.getAlpha();
      boolean var64 = true;
      int var8 = (int)(var10000 + (float)(var81 - var10002) * var4);
      Color var77 = new Color(var5, var6, var7, var8);
      boolean var68 = true;
      return var77;
   }

   private static final ArrayListModule.Entry getMeasuredEntries$lambda$0(GlyphData $font, Feature feature) {
      String var10000 = feature.getName();
      boolean var7 = true;
      Intrinsics.checkNotNullExpressionValue(var10000, "getName(...)");
      boolean var11 = true;
      String var2 = var10000;
      float var20 = $font.getStringWidth(var2);
      boolean var15 = true;
      float var3 = var20;
      ArrayListModule.Entry var21 = new ArrayListModule.Entry(var2, var3);
      boolean var19 = true;
      return var21;
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u000b\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0014\u0010\u0005\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0007\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\t\u0010\u0006R\u0014\u0010\n\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u000b\u0010\u0006R\u0014\u0010\f\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\f\u0010\u0006R\u0014\u0010\r\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\r\u0010\u0006R\u0014\u0010\u000e\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u000e\u0010\u0006¨\u0006\u000f"},
      d2 = {"Lru/melonity/feature/features/modules/render/hud/ArrayListModule$Companion;", "", "<init>", "()V", "", "LEFT_PADDING", "F", "RIGHT_PADDING", "VERTICAL_PADDING", "ENTRY_SPACING", "ACCENT_GAP", "ACCENT_WIDTH", "ACCENT_MARGIN", "ACCENT_RADIUS", "MIN_TEXT_HEIGHT", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   static final class Companion {
      private Companion() {
      }

      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\b\u001a\u00020\u0002HÆ\u0003¢\u0006\u0004\b\b\u0010\tJ\u0010\u0010\n\u001a\u00020\u0004HÆ\u0003¢\u0006\u0004\b\n\u0010\u000bJ$\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u0004HÆ\u0001¢\u0006\u0004\b\f\u0010\rJ\u001a\u0010\u0010\u001a\u00020\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0010\u0010\u0011J\u0010\u0010\u0013\u001a\u00020\u0012HÖ\u0001¢\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\u0002HÖ\u0001¢\u0006\u0004\b\u0015\u0010\tR\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0016\u001a\u0004\b\u0017\u0010\tR\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010\u0018\u001a\u0004\b\u0019\u0010\u000b¨\u0006\u001a"},
      d2 = {"Lru/melonity/feature/features/modules/render/hud/ArrayListModule$Entry;", "", "", "text", "", "textWidth", "<init>", "(Ljava/lang/String;F)V", "component1", "()Ljava/lang/String;", "component2", "()F", "copy", "(Ljava/lang/String;F)Lru/melonity/feature/features/modules/render/hud/ArrayListModule$Entry;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Ljava/lang/String;", "getText", "F", "getTextWidth", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   static final class Entry {
      @NotNull
      private final String text;
      private final float textWidth;

      public Entry(@NotNull String text, float textWidth) {
         super();
         Intrinsics.checkNotNullParameter(text, "text");
         this.text = text;
         this.textWidth = textWidth;
      }

      @NotNull
      public final String getText() {
         return this.text;
      }

      public final float getTextWidth() {
         return this.textWidth;
      }

      @NotNull
      public final String component1() {
         return this.text;
      }

      public final float component2() {
         return this.textWidth;
      }

      @NotNull
      public final ArrayListModule.Entry copy(@NotNull String text, float textWidth) {
         Intrinsics.checkNotNullParameter(text, "text");
         boolean var6 = true;
         ArrayListModule.Entry var10000 = new ArrayListModule.Entry(text, textWidth);
         boolean var10 = true;
         return var10000;
      }

      @NotNull
      @Override
      public String toString() {
         return "Entry(text=" + this.text + ", textWidth=" + this.textWidth + ")";
      }

      @Override
      public int hashCode() {
         int var10000 = this.text.hashCode();
         boolean var5 = true;
         int var1 = var10000;
         var10000 = var1 * 31;
         int var10001 = Float.hashCode(this.textWidth);
         boolean var9 = true;
         return var10000 + var10001;
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof ArrayListModule.Entry var2)) {
            return false;
         } else {
            boolean var10005 = Intrinsics.areEqual(this.text, var2.text);
            boolean var6 = true;
            if (!var10005) {
               return false;
            } else {
               int var10000 = Float.compare(this.textWidth, var2.textWidth);
               boolean var10 = true;
               return var10000 == 0;
            }
         }
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0004\b\b\u0010\tJ\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002HÆ\u0003¢\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\f\u001a\u00020\u0005HÆ\u0003¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u0005HÆ\u0003¢\u0006\u0004\b\u000e\u0010\rJ4\u0010\u000f\u001a\u00020\u00002\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005HÆ\u0001¢\u0006\u0004\b\u000f\u0010\u0010J\u001a\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015HÖ\u0001¢\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0019\u001a\u00020\u0018HÖ\u0001¢\u0006\u0004\b\u0019\u0010\u001aR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006¢\u0006\f\n\u0004\b\u0004\u0010\u001b\u001a\u0004\b\u001c\u0010\u000bR\u0017\u0010\u0006\u001a\u00020\u00058\u0006¢\u0006\f\n\u0004\b\u0006\u0010\u001d\u001a\u0004\b\u001e\u0010\rR\u0017\u0010\u0007\u001a\u00020\u00058\u0006¢\u0006\f\n\u0004\b\u0007\u0010\u001d\u001a\u0004\b\u001f\u0010\r¨\u0006 "},
      d2 = {"Lru/melonity/feature/features/modules/render/hud/ArrayListModule$LayoutMetrics;", "", "", "Lru/melonity/feature/features/modules/render/hud/ArrayListModule$RowLayout;", "rows", "", "maxWidth", "totalHeight", "<init>", "(Ljava/util/List;FF)V", "component1", "()Ljava/util/List;", "component2", "()F", "component3", "copy", "(Ljava/util/List;FF)Lru/melonity/feature/features/modules/render/hud/ArrayListModule$LayoutMetrics;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Ljava/util/List;", "getRows", "F", "getMaxWidth", "getTotalHeight", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   static final class LayoutMetrics {
      @NotNull
      private final List<ArrayListModule.RowLayout> rows;
      private final float maxWidth;
      private final float totalHeight;

      public LayoutMetrics(@NotNull List<ArrayListModule.RowLayout> rows, float maxWidth, float totalHeight) {
         super();
         Intrinsics.checkNotNullParameter(rows, "rows");
         this.rows = rows;
         this.maxWidth = maxWidth;
         this.totalHeight = totalHeight;
      }

      @NotNull
      public final List<ArrayListModule.RowLayout> getRows() {
         return this.rows;
      }

      public final float getMaxWidth() {
         return this.maxWidth;
      }

      public final float getTotalHeight() {
         return this.totalHeight;
      }

      @NotNull
      public final List<ArrayListModule.RowLayout> component1() {
         return this.rows;
      }

      public final float component2() {
         return this.maxWidth;
      }

      public final float component3() {
         return this.totalHeight;
      }

      @NotNull
      public final ArrayListModule.LayoutMetrics copy(@NotNull List<ArrayListModule.RowLayout> rows, float maxWidth, float totalHeight) {
         Intrinsics.checkNotNullParameter(rows, "rows");
         boolean var7 = true;
         ArrayListModule.LayoutMetrics var10000 = new ArrayListModule.LayoutMetrics(rows, maxWidth, totalHeight);
         boolean var11 = true;
         return var10000;
      }

      @NotNull
      @Override
      public String toString() {
         return "LayoutMetrics(rows=" + this.rows + ", maxWidth=" + this.maxWidth + ", totalHeight=" + this.totalHeight + ")";
      }

      @Override
      public int hashCode() {
         int var10000 = this.rows.hashCode();
         boolean var5 = true;
         int var1 = var10000;
         var10000 = var1 * 31;
         int var10001 = Float.hashCode(this.maxWidth);
         boolean var9 = true;
         var1 = var10000 + var10001;
         var10000 = var1 * 31;
         var10001 = Float.hashCode(this.totalHeight);
         boolean var13 = true;
         return var10000 + var10001;
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof ArrayListModule.LayoutMetrics var2)) {
            return false;
         } else {
            boolean var10005 = Intrinsics.areEqual(this.rows, var2.rows);
            boolean var6 = true;
            if (!var10005) {
               return false;
            } else {
               int var10000 = Float.compare(this.maxWidth, var2.maxWidth);
               boolean var10 = true;
               if (var10000 != 0) {
                  return false;
               } else {
                  var10000 = Float.compare(this.totalHeight, var2.totalHeight);
                  boolean var14 = true;
                  return var10000 == 0;
               }
            }
         }
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0006\u001a\u0004\b\u0007\u0010\bR\"\u0010\n\u001a\u00020\t8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\"\u0010\u0010\u001a\u00020\t8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\u0010\u0010\u000b\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000fR\"\u0010\u0013\u001a\u00020\t8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\u0013\u0010\u000b\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u000fR\"\u0010\u0016\u001a\u00020\t8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\u0016\u0010\u000b\u001a\u0004\b\u0017\u0010\r\"\u0004\b\u0018\u0010\u000fR\"\u0010\u001a\u001a\u00020\u00198\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f¨\u0006 "},
      d2 = {"Lru/melonity/feature/features/modules/render/hud/ArrayListModule$RowAnimationState;", "", "", "text", "<init>", "(Ljava/lang/String;)V", "Ljava/lang/String;", "getText", "()Ljava/lang/String;", "", "rowWidth", "F", "getRowWidth", "()F", "setRowWidth", "(F)V", "rowHeight", "getRowHeight", "setRowHeight", "textWidth", "getTextWidth", "setTextWidth", "progress", "getProgress", "setProgress", "", "visible", "Z", "getVisible", "()Z", "setVisible", "(Z)V", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   static final class RowAnimationState {
      @NotNull
      private final String text;
      private float rowWidth;
      private float rowHeight;
      private float textWidth;
      private float progress;
      private boolean visible;

      public RowAnimationState(@NotNull String text) {
         super();
         Intrinsics.checkNotNullParameter(text, "text");
         this.text = text;
      }

      @NotNull
      public final String getText() {
         return this.text;
      }

      public final float getRowWidth() {
         return this.rowWidth;
      }

      public final void setRowWidth(float value) {
         this.rowWidth = value;
      }

      public final float getRowHeight() {
         return this.rowHeight;
      }

      public final void setRowHeight(float value) {
         this.rowHeight = value;
      }

      public final float getTextWidth() {
         return this.textWidth;
      }

      public final void setTextWidth(float value) {
         this.textWidth = value;
      }

      public final float getProgress() {
         return this.progress;
      }

      public final void setProgress(float value) {
         this.progress = value;
      }

      public final boolean getVisible() {
         return this.visible;
      }

      public final void setVisible(boolean value) {
         this.visible = value;
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\b\u0082\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\u0006\u0010\b\u001a\u00020\u0004¢\u0006\u0004\b\t\u0010\nJ\u0010\u0010\u000b\u001a\u00020\u0002HÆ\u0003¢\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\r\u001a\u00020\u0004HÆ\u0003¢\u0006\u0004\b\r\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u0004HÆ\u0003¢\u0006\u0004\b\u000f\u0010\u000eJ\u0010\u0010\u0010\u001a\u00020\u0004HÆ\u0003¢\u0006\u0004\b\u0010\u0010\u000eJ\u0010\u0010\u0011\u001a\u00020\u0004HÆ\u0003¢\u0006\u0004\b\u0011\u0010\u000eJB\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\u0004HÆ\u0001¢\u0006\u0004\b\u0012\u0010\u0013J\u001a\u0010\u0016\u001a\u00020\u00152\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0019\u001a\u00020\u0018HÖ\u0001¢\u0006\u0004\b\u0019\u0010\u001aJ\u0010\u0010\u001b\u001a\u00020\u0002HÖ\u0001¢\u0006\u0004\b\u001b\u0010\fR\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u001c\u001a\u0004\b\u001d\u0010\fR\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010\u001e\u001a\u0004\b\u001f\u0010\u000eR\u0017\u0010\u0006\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0006\u0010\u001e\u001a\u0004\b \u0010\u000eR\u0017\u0010\u0007\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0007\u0010\u001e\u001a\u0004\b!\u0010\u000eR\u0017\u0010\b\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\b\u0010\u001e\u001a\u0004\b\"\u0010\u000e¨\u0006#"},
      d2 = {"Lru/melonity/feature/features/modules/render/hud/ArrayListModule$RowLayout;", "", "", "text", "", "textWidth", "textHeight", "rowWidth", "rowHeight", "<init>", "(Ljava/lang/String;FFFF)V", "component1", "()Ljava/lang/String;", "component2", "()F", "component3", "component4", "component5", "copy", "(Ljava/lang/String;FFFF)Lru/melonity/feature/features/modules/render/hud/ArrayListModule$RowLayout;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Ljava/lang/String;", "getText", "F", "getTextWidth", "getTextHeight", "getRowWidth", "getRowHeight", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   static final class RowLayout {
      @NotNull
      private final String text;
      private final float textWidth;
      private final float textHeight;
      private final float rowWidth;
      private final float rowHeight;

      public RowLayout(@NotNull String text, float textWidth, float textHeight, float rowWidth, float rowHeight) {
         super();
         Intrinsics.checkNotNullParameter(text, "text");
         this.text = text;
         this.textWidth = textWidth;
         this.textHeight = textHeight;
         this.rowWidth = rowWidth;
         this.rowHeight = rowHeight;
      }

      @NotNull
      public final String getText() {
         return this.text;
      }

      public final float getTextWidth() {
         return this.textWidth;
      }

      public final float getTextHeight() {
         return this.textHeight;
      }

      public final float getRowWidth() {
         return this.rowWidth;
      }

      public final float getRowHeight() {
         return this.rowHeight;
      }

      @NotNull
      public final String component1() {
         return this.text;
      }

      public final float component2() {
         return this.textWidth;
      }

      public final float component3() {
         return this.textHeight;
      }

      public final float component4() {
         return this.rowWidth;
      }

      public final float component5() {
         return this.rowHeight;
      }

      @NotNull
      public final ArrayListModule.RowLayout copy(@NotNull String text, float textWidth, float textHeight, float rowWidth, float rowHeight) {
         Intrinsics.checkNotNullParameter(text, "text");
         boolean var9 = true;
         ArrayListModule.RowLayout var10000 = new ArrayListModule.RowLayout(text, textWidth, textHeight, rowWidth, rowHeight);
         boolean var13 = true;
         return var10000;
      }

      @NotNull
      @Override
      public String toString() {
         return "RowLayout(text="
            + this.text
            + ", textWidth="
            + this.textWidth
            + ", textHeight="
            + this.textHeight
            + ", rowWidth="
            + this.rowWidth
            + ", rowHeight="
            + this.rowHeight
            + ")";
      }

      @Override
      public int hashCode() {
         int var10000 = this.text.hashCode();
         boolean var5 = true;
         int var1 = var10000;
         var10000 = var1 * 31;
         int var10001 = Float.hashCode(this.textWidth);
         boolean var9 = true;
         var1 = var10000 + var10001;
         var10000 = var1 * 31;
         var10001 = Float.hashCode(this.textHeight);
         boolean var13 = true;
         var1 = var10000 + var10001;
         var10000 = var1 * 31;
         var10001 = Float.hashCode(this.rowWidth);
         boolean var17 = true;
         var1 = var10000 + var10001;
         var10000 = var1 * 31;
         var10001 = Float.hashCode(this.rowHeight);
         boolean var21 = true;
         return var10000 + var10001;
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof ArrayListModule.RowLayout var2)) {
            return false;
         } else {
            boolean var10005 = Intrinsics.areEqual(this.text, var2.text);
            boolean var6 = true;
            if (!var10005) {
               return false;
            } else {
               int var10000 = Float.compare(this.textWidth, var2.textWidth);
               boolean var10 = true;
               if (var10000 != 0) {
                  return false;
               } else {
                  var10000 = Float.compare(this.textHeight, var2.textHeight);
                  boolean var14 = true;
                  if (var10000 != 0) {
                     return false;
                  } else {
                     var10000 = Float.compare(this.rowWidth, var2.rowWidth);
                     boolean var18 = true;
                     if (var10000 != 0) {
                        return false;
                     } else {
                        var10000 = Float.compare(this.rowHeight, var2.rowHeight);
                        boolean var22 = true;
                        return var10000 == 0;
                     }
                  }
               }
            }
         }
      }
   }
}
