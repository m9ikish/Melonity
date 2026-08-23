package ru.melonity.feature.features.modules.render.hud;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_4587;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.hud.schedules.Schedule;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.View;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.font.GlyphData;
import ru.melonity.util.java.IOUtil;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.ScaleUtils;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 82\u00020\u0001:\u00018B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\r\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\r\u0010\u0007\u001a\u00020\u0004¢\u0006\u0004\b\u0007\u0010\u0006J?\u0010\u0012\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000fH\u0017¢\u0006\u0004\b\u0012\u0010\u0013J%\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00162\b\b\u0002\u0010\u0015\u001a\u00020\u0014H\u0002¢\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u0019\u001a\u00020\u0011H\u0003¢\u0006\u0004\b\u0019\u0010\u0003J\u0017\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002¢\u0006\u0004\b\u001d\u0010\u001eJ7\u0010!\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010 \u001a\u00020\u001fH\u0016¢\u0006\u0004\b!\u0010\"J'\u0010'\u001a\u00020&2\u0006\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\u001fH\u0016¢\u0006\u0004\b'\u0010(J\u000f\u0010)\u001a\u00020\u0011H\u0016¢\u0006\u0004\b)\u0010\u0003R\u0016\u0010*\u001a\u00020\u001c8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b*\u0010+R\u0014\u0010-\u001a\u00020,8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b-\u0010.R\u001c\u00101\u001a\n 0*\u0004\u0018\u00010/0/8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b1\u00102R\u001c\u00103\u001a\n 0*\u0004\u0018\u00010/0/8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b3\u00102R\u001a\u00106\u001a\b\u0012\u0004\u0012\u000205048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b6\u00107¨\u00069"},
   d2 = {"Lru/melonity/feature/features/modules/render/hud/SchedulersView;", "Lru/melonity/ui/components/view/View;", "<init>", "()V", "", "measureWidth", "()F", "measureHeight", "x", "y", "", "mouseX", "mouseY", "Lru/melonity/util/render/RenderSystem;", "renderSystem", "Lnet/minecraft/class_4587;", "poseStack", "", "insert", "(FFDDLru/melonity/util/render/RenderSystem;Lnet/minecraft/class_4587;)V", "Lru/melonity/util/font/GlyphData;", "font", "Lkotlin/Pair;", "calculateSize", "(Lru/melonity/util/font/GlyphData;)Lkotlin/Pair;", "update", "", "str", "", "extractNumber", "(Ljava/lang/String;)J", "", "mouseButton", "click", "(FFDDI)V", "keyCode", "scanCode", "modifiers", "", "key", "(III)Z", "release", "time", "J", "Lru/melonity/util/animation/StateAnimation;", "alphaAnimation", "Lru/melonity/util/animation/StateAnimation;", "Lru/melonity/util/animation/FrameWeightCalculator;", "kotlin.jvm.PlatformType", "fwc", "Lru/melonity/util/animation/FrameWeightCalculator;", "frameWeightCalculator", "", "Lru/melonity/feature/features/modules/render/hud/schedules/Schedule;", "entries", "Ljava/util/List;", "Companion", "melonity_client"}
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\nSchedulersView.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SchedulersView.kt\nru/melonity/feature/features/modules/render/hud/SchedulersView\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,204:1\n295#2,2:205\n295#2,2:207\n*S KotlinDebug\n*F\n+ 1 SchedulersView.kt\nru/melonity/feature/features/modules/render/hud/SchedulersView\n*L\n166#1:205,2\n173#1:207,2\n*E\n"})
public final class SchedulersView extends View {
   @NotNull
   private static final SchedulersView.Companion Companion = new SchedulersView.Companion(null);
   private long time = System.currentTimeMillis();
   @NotNull
   private final StateAnimation alphaAnimation = new StateAnimation();
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(250L);
   private final FrameWeightCalculator frameWeightCalculator = FrameWeightCalculator.milliseconds(400L);
   @NotNull
   private final List<Schedule> entries;
   private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

   public SchedulersView() {
      super(104.0F, 1.0F);
      LinkedList var10001 = Lists.newLinkedList();
      Intrinsics.checkNotNullExpressionValue(var10001, "newLinkedList(...)");
      this.entries = var10001;
      this.update();
   }

   public final float measureWidth() {
      return ((Number)calculateSize$default(this, null, 1, null).getFirst()).floatValue();
   }

   public final float measureHeight() {
      return ((Number)calculateSize$default(this, null, 1, null).getSecond()).floatValue();
   }

   @Override
   public void insert(float x, float y, double mouseX, double mouseY, @NotNull RenderSystem renderSystem, @NotNull class_4587 poseStack) {
      Intrinsics.checkNotNullParameter(renderSystem, "renderSystem");
      Intrinsics.checkNotNullParameter(poseStack, "poseStack");
      long now = System.currentTimeMillis();
      if (now - this.time > 60000L) {
         this.time = now;
         this.update();
      }

      for (Schedule entry : this.entries) {
         StateAnimation rowAnimation = entry.animation();
         rowAnimation.state((int)((double)(entry.date() - now) / 1000.0) >= -40);
         rowAnimation.update(this.frameWeightCalculator.elapsedUnits());
      }

      GlyphData font = Fonts.INTER_MEDIUM_14;
      float width = this.measureWidth();
      float height = this.measureHeight();
      this.setWidth(width);
      this.setHeight(height);
      this.alphaAnimation.state(!this.entries.isEmpty());
      this.alphaAnimation.update(this.fwc.elapsedUnits());
      float fade = this.alphaAnimation.animation();
      if (!(fade <= 0.01F)) {
         MenuStyle style = Melonity.INSTANCE.styleManager.getStyle();
         int alpha = Math.max(0, Math.min(255, (int)(fade * 255.0F)));
         ScaleUtils.rescale(2.0);
         renderSystem.addRect(x, y, width, height, 6.0F, RenderSystem.setAlpha(style.background(), alpha), poseStack);
         renderSystem.addTexture(
            class_2960.method_60656("melonity/images/ui/icons/schedules.png"),
            x + 6.0F,
            y + 5.5F,
            8.0F,
            8.0F,
            RenderSystem.setAlpha(style.ascent(), alpha),
            poseStack
         );
         renderSystem.addText(font, "Schedules", x + 18.0F, y + 6.0F, RenderSystem.setAlpha(Color.WHITE, alpha), poseStack);
         renderSystem.addRect(x, y + 19.0F, width, 1.0F, RenderSystem.setAlpha(Color.decode("#222222"), alpha), poseStack);
         float rowY = y + 20.0F;

         for (Schedule entry : this.entries) {
            float rowFade = entry.animation().animation();
            if (!(rowFade <= 0.01F)) {
               int seconds = (int)((double)(entry.date() - now) / 1000.0);
               String timeText = seconds + "c";
               if (seconds < 0) {
                  timeText = "уже как " + -seconds + "c";
               }

               String title = entry.name();
               if (seconds > 0 && entry.anarchy() != null && !entry.anarchy().isEmpty()) {
                  title = title + "#" + entry.anarchy();
               }

               int rowAlpha = Math.max(0, Math.min(255, (int)(fade * rowFade * 255.0F)));
               renderSystem.addText(font, title, x + 7.0F, rowY + 4.5F, RenderSystem.setAlpha(Color.WHITE, rowAlpha), poseStack);
               float badgeWidth = font.getStringWidth(timeText) + 10.0F;
               float badgeX = x + width - 7.0F - badgeWidth;
               renderSystem.addRect(badgeX, rowY + 1.5F, badgeWidth, 12.0F, 3.5F, RenderSystem.setAlpha(style.previewValues(), rowAlpha), poseStack);
               renderSystem.addText(
                  font,
                  timeText,
                  badgeX + badgeWidth / 2.0F - font.getStringWidth(timeText) / 2.0F,
                  rowY + 4.5F,
                  RenderSystem.setAlpha(seconds < 0 ? Color.decode("#888888") : style.ascent(), rowAlpha),
                  poseStack
               );
               rowY += 16.0F * rowFade;
            }
         }

         ScaleUtils.rescaleMC();
      }
   }

   private final Pair<Float, Float> calculateSize(GlyphData font) {
      float local2_5 = 90.0F;
      float local3_6 = 20.0F;

      for (Schedule local5_4 : this.entries) {
         int local6_0 = (int)((double)(local5_4.date() - System.currentTimeMillis()) / 1000.0);
         if (local6_0 >= -40) {
            String local7_1 = local6_0 + "c";
            if (local6_0 < 0) {
               local7_1 = "уже как " + -local6_0 + "c";
            }

            float local8_2 = font.getStringWidth(local7_1) + 10.0F;
            String local9_3 = local5_4.name();
            if (local6_0 > 0) {
               local9_3 = local9_3 + "#" + local5_4.anarchy();
            }

            local2_5 = Math.max(local2_5, font.getStringWidth(local9_3) + 14.0F + local8_2);
            local3_6 += local5_4.animation().animation() * 16.0F;
         }
      }

      return new Pair(local2_5, local3_6);
   }

   static Pair calculateSize$default(SchedulersView $this, GlyphData font, int mask, Object marker) {
      if ((mask & 1) != 0) {
         font = Fonts.INTER_MEDIUM_14;
         Intrinsics.checkNotNullExpressionValue(Fonts.INTER_MEDIUM_14, "INTER_MEDIUM_14");
      }

      return $this.calculateSize(font);
   }

   private final void update() {
      Thread worker = new Thread(() -> update$lambda$2(this), "Melonity-Schedulers");
      worker.setDaemon(true);
      worker.start();
   }

   private final long extractNumber(String str) {
      Matcher local2_1 = NUMBER_PATTERN.matcher(str);

      long var3;
      try {
         long var7;
         if (local2_1.find()) {
            String var10000 = local2_1.group();
            Intrinsics.checkNotNullExpressionValue(var10000, "group(...)");
            var7 = Long.parseLong(var10000);
         } else {
            var7 = 0L;
         }

         var3 = var7;
      } catch (NumberFormatException var6) {
         var3 = 0L;
      }

      return var3;
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

   private static final void update$lambda$2(SchedulersView this$0) {
      try {
         URL local1_15 = new URI("https://api.newproject.fun/api/v1/events").toURL();
         URLConnection var10000 = local1_15.openConnection();
         Intrinsics.checkNotNull(var10000, "null cannot be cast to non-null type java.net.HttpURLConnection");
         HttpURLConnection local2_16 = (HttpURLConnection)var10000;
         // Without these a blackholed host leaves getInputStream() blocked forever, and
         // insert() keeps spawning a fresh poll thread every 60s on top of it.
         local2_16.setConnectTimeout(3000);
         local2_16.setReadTimeout(5000);
         JSONObject local3_17 = new JSONObject(IOUtil.readString(local2_16.getInputStream()));
         local2_16.disconnect();
         JSONArray var27 = local3_17.getJSONArray("events");
         Intrinsics.checkNotNullExpressionValue(var27, "getJSONArray(...)");
         JSONArray local4_18 = var27;
         int local5_14 = 0;

         for (int var6 = local4_18.length(); local5_14 < var6; local5_14++) {
            JSONObject local7_13 = local4_18.getJSONObject(local5_14);
            if (local7_13.has("name") && local7_13.has("status") && !local7_13.isNull("name") && !local7_13.isNull("status")) {
               String local8_8 = local7_13.getString("name");
               String local9_9 = local7_13.getString("status");
               String local10_10 = local7_13.optString("location", null);
               long local11_11 = local7_13.optLong("expiresAt", 0L);
               Intrinsics.checkNotNull(local9_9);
               long local13_12 = this$0.extractNumber(local9_9);
               if (local13_12 != 0L) {
                  Schedule local15_3 = null;

                  for (Schedule local20_0 : this$0.entries) {
                     if (Intrinsics.areEqual(local20_0.name(), local8_8) && Intrinsics.areEqual(local20_0.location(), local10_10)) {
                        local15_3 = local20_0;
                        break;
                     }
                  }

                  if (local15_3 != null) {
                     local15_3.date(System.currentTimeMillis() + local13_12 * (long)1000);
                  } else {
                     this$0.entries.add(new Schedule("", local8_8, local10_10, System.currentTimeMillis() + local13_12 * (long)1000));
                  }
               } else if (local11_11 > 0L) {
                  Schedule local15_7 = null;

                  for (Schedule local20_4 : this$0.entries) {
                     if (Intrinsics.areEqual(local20_4.name(), local8_8) && Intrinsics.areEqual(local20_4.location(), local10_10)) {
                        local15_7 = local20_4;
                        break;
                     }
                  }

                  if (local15_7 != null) {
                     local15_7.date(local11_11);
                  } else {
                     this$0.entries.add(new Schedule("", local8_8, local10_10, local11_11));
                  }
               }
            }
         }
      } catch (Throwable var21) {
         System.err.println("[Melonity] Schedules poll failed: " + var21);
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001c\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0006\u0010\u0007¨\u0006\b"},
      d2 = {"Lru/melonity/feature/features/modules/render/hud/SchedulersView$Companion;", "", "<init>", "()V", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "NUMBER_PATTERN", "Ljava/util/regex/Pattern;", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   private static final class Companion {
      private Companion() {
      }

      public Companion(kotlin.jvm.internal.DefaultConstructorMarker marker) {
         this();
      }
   }
}
