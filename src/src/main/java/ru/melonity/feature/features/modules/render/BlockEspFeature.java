package ru.melonity.feature.features.modules.render;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1923;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_290;
import net.minecraft.class_5944;
import net.minecraft.class_638;
import net.minecraft.class_746;
import net.minecraft.class_757;
import net.minecraft.class_293.class_5596;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderGameWorldEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.render.BoxRenderer;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0000\n\u0002\u0010\"\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0003¢\u0006\u0004\b\u0006\u0010\u0004J\u001f\u0010\n\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H\u0017¢\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\u000e\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\fH\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ4\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011\"\u0006\b\u0000\u0010\u0010\u0018\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u00112\u0006\u0010\u0014\u001a\u00020\u0013H\u0082\b¢\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u0011H\u0003¢\u0006\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001b\u001a\u00020\u001a8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u001a8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001d\u0010\u001cR8\u0010!\u001a&\u0012\f\u0012\n \u001f*\u0004\u0018\u00010\u00170\u0017 \u001f*\u0012\u0012\f\u0012\n \u001f*\u0004\u0018\u00010\u00170\u0017\u0018\u00010 0\u001e8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b!\u0010\"R:\u0010#\u001a&\u0012\f\u0012\n \u001f*\u0004\u0018\u00010\u00170\u0017 \u001f*\u0012\u0012\f\u0012\n \u001f*\u0004\u0018\u00010\u00170\u0017\u0018\u00010\u00110\u00118\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b#\u0010$R8\u0010%\u001a&\u0012\f\u0012\n \u001f*\u0004\u0018\u00010\u00170\u0017 \u001f*\u0012\u0012\f\u0012\n \u001f*\u0004\u0018\u00010\u00170\u0017\u0018\u00010\u00110\u00118\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b%\u0010$R\u0018\u0010&\u001a\u0004\u0018\u00010\u00178\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b&\u0010'R\u0018\u0010)\u001a\u0004\u0018\u00010(8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b)\u0010*R\u0016\u0010+\u001a\u00020\u00138\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b+\u0010,R\u0016\u0010-\u001a\u00020\u00138\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b-\u0010,R\u0016\u0010.\u001a\u00020\u00078\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b.\u0010/R\u001d\u00102\u001a\b\u0012\u0004\u0012\u000201008\u0006¢\u0006\f\n\u0004\b2\u00103\u001a\u0004\b4\u00105R\"\u00107\u001a\u0010\u0012\f\u0012\n \u001f*\u0004\u0018\u00010606008\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b7\u00103R\u0014\u0010;\u001a\u0002088\u0016X\u0096\u0005¢\u0006\u0006\u001a\u0004\b9\u0010:¨\u0006<"},
   d2 = {"Lru/melonity/feature/features/modules/render/BlockEspFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "Lkotlinx/coroutines/CoroutineScope;", "<init>", "()V", "", "handleLazyModeTick", "", "value", "silent", "setEnabled", "(ZZ)V", "Lnet/minecraft/class_2248;", "block", "checkBlock", "(Lnet/minecraft/class_2248;)Z", "T", "Ljava/util/concurrent/CopyOnWriteArrayList;", "inList", "", "offset", "removeEveryOther", "(Ljava/util/concurrent/CopyOnWriteArrayList;I)Ljava/util/concurrent/CopyOnWriteArrayList;", "Lnet/minecraft/class_2338;", "getBlocks", "()Ljava/util/concurrent/CopyOnWriteArrayList;", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "blocks", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "mode", "", "kotlin.jvm.PlatformType", "", "foundBlocks", "Ljava/util/Set;", "queuePositions", "Ljava/util/concurrent/CopyOnWriteArrayList;", "clicked", "clicking", "Lnet/minecraft/class_2338;", "Lkotlinx/coroutines/Job;", "fastJob", "Lkotlinx/coroutines/Job;", "tickCounter", "I", "currentQueueIndex", "isLazyModeActive", "Z", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/UpdateLivingEvent;", "onUpdate", "Lru/melonity/util/event/EventListener;", "getOnUpdate", "()Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/RenderGameWorldEvent;", "on3D", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "coroutineContext", "melonity_client"}
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\nBlockEspFeature.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BlockEspFeature.kt\nru/melonity/feature/features/modules/render/BlockEspFeature\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,251:1\n219#1,4:252\n224#1:259\n1878#2,3:256\n1878#2,3:260\n1869#2,2:263\n*S KotlinDebug\n*F\n+ 1 BlockEspFeature.kt\nru/melonity/feature/features/modules/render/BlockEspFeature\n*L\n121#1:252,4\n121#1:259\n121#1:256,3\n222#1:260,3\n180#1:263,2\n*E\n"})
public final class BlockEspFeature extends SimpleFeature implements CoroutineScope {
   /** Half-edge of the cube {@link #getBlocks()} sweeps for the tick-driven "lazy" mode. */
   private static final int SCAN_RADIUS = 24;
   private final CoroutineScope $$delegate_0;
   @NotNull
   private final ListSetting blocks;
   @NotNull
   private final ListSetting mode;
   private final Set<class_2338> foundBlocks;
   private CopyOnWriteArrayList<class_2338> queuePositions;
   private final CopyOnWriteArrayList<class_2338> clicked;
   @Nullable
   private class_2338 clicking;
   @Nullable
   private Job fastJob;
   private int tickCounter;
   private int currentQueueIndex;
   private boolean isLazyModeActive;
   @NotNull
   private final EventListener<UpdateLivingEvent> onUpdate;
   @NotNull
   private final EventListener<RenderGameWorldEvent> on3D;

   public BlockEspFeature() {
      super("BlockESP", MenuCategory.RENDER);
      this.$$delegate_0 = CoroutineScopeKt.MainScope();
      ListSetting.ModeType var10004 = ListSetting.ModeType.MULTI;
      String[] var1 = new String[]{
         "blockesp.blocks.chest",
         "blockesp.blocks.ender_chest",
         "blockesp.blocks.hopper",
         "blockesp.blocks.obsidian",
         "blockesp.blocks.shulker",
         "blockesp.blocks.gold_ore",
         "blockesp.blocks.iron_ore",
         "blockesp.blocks.coal_ore",
         "blockesp.blocks.nether_gold_ore",
         "blockesp.blocks.lapis_ore",
         "blockesp.blocks.diamond_ore",
         "blockesp.blocks.redstone_ore",
         "blockesp.blocks.emerald_ore",
         "blockesp.blocks.ancient_debris"
      };
      this.blocks = new ListSetting("blockesp.blocks", var10004, "blockesp.blocks.chest", CollectionsKt.mutableListOf(var1));
      var10004 = ListSetting.ModeType.SINGLE;
      var1 = new String[]{"blockesp.mode.fast", "blockesp.mode.lazy"};
      this.mode = new ListSetting("global.mode", var10004, "blockesp.mode.fast", CollectionsKt.mutableListOf(var1));
      this.foundBlocks = Collections.newSetFromMap(new ConcurrentHashMap<>());
      this.queuePositions = Lists.newCopyOnWriteArrayList();
      this.clicked = Lists.newCopyOnWriteArrayList();
      this.onUpdate = event -> onUpdate$lambda$0(this, event);
      this.on3D = event -> on3D$lambda$4(this, event);
      this.setting(this.mode);
      this.setting(this.blocks);
   }

   @NotNull
   public final EventListener<UpdateLivingEvent> getOnUpdate() {
      return this.onUpdate;
   }

   private final void handleLazyModeTick() {
      class_638 world = SimpleFeature.mc.field_1687;
      class_746 player = SimpleFeature.mc.field_1724;
      if (world == null || player == null) {
         return;
      }

      // Queue exhausted: idle for a second, drop everything that the player has walked away
      // from and only then pay for a fresh scan. That pause is what makes this mode "lazy".
      if (this.currentQueueIndex >= this.queuePositions.size()) {
         this.clicking = null;
         if (++this.tickCounter < 20) {
            return;
         }

         class_2338 center = player.method_24515();
         this.clicked
            .removeIf(
               pos -> Math.abs(pos.method_10263() - center.method_10263()) > SCAN_RADIUS
                  || Math.abs(pos.method_10264() - center.method_10264()) > SCAN_RADIUS
                  || Math.abs(pos.method_10260() - center.method_10260()) > SCAN_RADIUS
            );
         this.tickCounter = 0;
         this.currentQueueIndex = 0;
         this.queuePositions = this.removeEveryOther(this.getBlocks(), 2);
         return;
      }

      int endIndex = Math.min(this.currentQueueIndex + 256, this.queuePositions.size());

      while (this.currentQueueIndex < endIndex) {
         class_2338 pos = this.queuePositions.get(this.currentQueueIndex++);
         this.clicking = pos;
         if (this.checkBlock(world.method_8320(pos).method_26204())) {
            this.clicked.addIfAbsent(pos);
         }
      }
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      if (value) {
         this.foundBlocks.clear();
         this.clicked.clear();
         this.clicking = null;
         this.currentQueueIndex = 0;
         this.tickCounter = 0;
         if (this.mode.contains("blockesp.mode.lazy")) {
            this.isLazyModeActive = true;
            this.queuePositions = this.removeEveryOther(this.getBlocks(), 2);
         } else {
            // Verbatim restoration of the original scanner: a full 251^3 cube around the player,
            // re-swept every 50 ms off the render thread. It is expensive by design.
            Function2<CoroutineScope, Continuation<? super Unit>, Object> scanLoop = (scope, continuation) -> {
               while (CoroutineScopeKt.isActive(scope) && this.isEnabled()) {
                  class_746 player = SimpleFeature.mc.field_1724;
                  class_638 world = SimpleFeature.mc.field_1687;
                  if (player != null && world != null) {
                     Set<class_2338> found = new LinkedHashSet<>();
                     class_2338 center = player.method_24515();

                     for (int offsetX = -125; offsetX < 126; offsetX++) {
                        for (int offsetZ = -125; offsetZ < 126; offsetZ++) {
                           for (int offsetY = -125; offsetY < 126; offsetY++) {
                              class_2338 pos = new class_2338(
                                 center.method_10263() + offsetX, center.method_10264() + offsetY, center.method_10260() + offsetZ
                              );
                              if (this.checkBlock(world.method_8320(pos).method_26204())) {
                                 found.add(pos);
                              }
                           }
                        }
                     }

                     this.foundBlocks.clear();
                     this.foundBlocks.addAll(found);
                  }

                  try {
                     Thread.sleep(50L);
                  } catch (InterruptedException var9) {
                     Thread.currentThread().interrupt();
                     break;
                  }
               }

               return Unit.INSTANCE;
            };
            this.fastJob = BuildersKt.launch(this, Dispatchers.getIO(), CoroutineStart.DEFAULT, scanLoop);
         }
      } else {
         Job runningJob = this.fastJob;
         if (runningJob != null) {
            runningJob.cancel(null);
         }

         this.fastJob = null;
         this.isLazyModeActive = false;
         this.foundBlocks.clear();
         this.queuePositions.clear();
         this.clicked.clear();
         this.clicking = null;
         this.currentQueueIndex = 0;
         this.tickCounter = 0;
      }
   }

   private final boolean checkBlock(class_2248 block) {
      boolean var5;
      if (this.blocks.mode.contains("blockesp.blocks.chest") && block == class_2246.field_10034) {
         var5 = true;
      } else if (this.blocks.mode.contains("blockesp.blocks.ender_chest") && block == class_2246.field_10443) {
         var5 = true;
      } else if (this.blocks.mode.contains("blockesp.blocks.hopper") && block == class_2246.field_10312) {
         var5 = true;
      } else if (this.blocks.mode.contains("blockesp.blocks.obsidian") && block == class_2246.field_10540) {
         var5 = true;
      } else {
         if (this.blocks.mode.contains("blockesp.blocks.shulker")) {
            String var10000 = block.method_9539();
            Intrinsics.checkNotNullExpressionValue(var10000, "getTranslationKey(...)");
            String var2 = var10000;
            Locale var3 = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(var3, "getDefault(...)");
            String var4 = var2.toLowerCase(var3);
            Intrinsics.checkNotNullExpressionValue(var4, "toLowerCase(...)");
            if (StringsKt.contains((CharSequence)var4, (CharSequence)"shulker", false)) {
               return true;
            }
         }

         var5 = this.blocks.mode.contains("blockesp.blocks.gold_ore") && block == class_2246.field_10571
            ? true
            : (
               this.blocks.mode.contains("blockesp.blocks.iron_ore") && block == class_2246.field_10212
                  ? true
                  : (
                     this.blocks.mode.contains("blockesp.blocks.coal_ore") && block == class_2246.field_10418
                        ? true
                        : (
                           this.blocks.mode.contains("blockesp.blocks.nether_gold_ore") && block == class_2246.field_23077
                              ? true
                              : (
                                 this.blocks.mode.contains("blockesp.blocks.lapis_ore") && block == class_2246.field_10090
                                    ? true
                                    : (
                                       this.blocks.mode.contains("blockesp.blocks.diamond_ore") && block == class_2246.field_10442
                                          ? true
                                          : (
                                             this.blocks.mode.contains("blockesp.blocks.redstone_ore") && block == class_2246.field_10080
                                                ? true
                                                : (
                                                   this.blocks.mode.contains("blockesp.blocks.emerald_ore") && block == class_2246.field_10013
                                                      ? true
                                                      : this.blocks.mode.contains("blockesp.blocks.ancient_debris") && block == class_2246.field_22109
                                                )
                                          )
                                    )
                              )
                        )
                  )
            );
      }

      return var5;
   }

   private final <T> CopyOnWriteArrayList<T> removeEveryOther(CopyOnWriteArrayList<T> inList, int offset) {
      if (offset == 1) {
         return inList;
      } else {
         CopyOnWriteArrayList<T> result = Lists.newCopyOnWriteArrayList();
         int index = 0;

         for (T element : inList) {
            if (index++ % offset == 0) {
               result.add(element);
            }
         }

         return result;
      }
   }

   private final CopyOnWriteArrayList<class_2338> getBlocks() {
      List<class_2338> positions = new ArrayList<>();
      class_746 player = SimpleFeature.mc.field_1724;
      class_638 world = SimpleFeature.mc.field_1687;
      if (player != null && world != null) {
         class_2338 center = player.method_24515();
         int bottomY = world.method_31607();
         int topY = world.method_31600();

         for (int offsetX = -SCAN_RADIUS; offsetX <= SCAN_RADIUS; offsetX++) {
            for (int offsetZ = -SCAN_RADIUS; offsetZ <= SCAN_RADIUS; offsetZ++) {
               for (int offsetY = -SCAN_RADIUS; offsetY <= SCAN_RADIUS; offsetY++) {
                  int y = center.method_10264() + offsetY;
                  if (y >= bottomY && y < topY) {
                     class_2338 pos = new class_2338(center.method_10263() + offsetX, y, center.method_10260() + offsetZ);
                     if (this.checkBlock(world.method_8320(pos).method_26204())) {
                        positions.add(pos);
                     }
                  }
               }
            }
         }
      }

      return Lists.newCopyOnWriteArrayList(positions);
   }

   @NotNull
   public CoroutineContext getCoroutineContext() {
      return this.$$delegate_0.getCoroutineContext();
   }

   private static final void onUpdate$lambda$0(BlockEspFeature local0_0, UpdateLivingEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "<unused var>");
      List var10000 = local0_0.mode.mode;
      Intrinsics.checkNotNullExpressionValue(local0_0.mode.mode, "mode");
      if (Intrinsics.areEqual(CollectionsKt.first(var10000), "blockesp.mode.lazy") && local0_0.isLazyModeActive) {
         local0_0.handleLazyModeTick();
      }
   }

   private static final class_5944 on3D$lambda$4$lambda$1() {
      return class_757.method_34540();
   }

   private static final void on3D$lambda$4(BlockEspFeature this$0, RenderGameWorldEvent event) {
      Intrinsics.checkNotNull(event);
      Matrix4f local2_7 = event.poseStack().method_23760().method_23761();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableDepthTest();
      RenderSystem.setShader(BlockEspFeature::on3D$lambda$4$lambda$1);
      class_289 local3_8 = class_289.method_1348();
      class_287 local4_9 = local3_8.method_60827(class_5596.field_27382, class_290.field_1576);
      List var10000 = this$0.mode.mode;
      Intrinsics.checkNotNullExpressionValue(this$0.mode.mode, "mode");
      String var5 = (String)CollectionsKt.first(var10000);
      if (Intrinsics.areEqual(var5, "blockesp.mode.fast")) {
         for (class_2338 local7_0 : this$0.foundBlocks) {
            BoxRenderer var15 = BoxRenderer.INSTANCE;
            Intrinsics.checkNotNull(local7_0);
            Color var10002 = Melonity.INSTANCE.styleManager.style.ascent();
            Intrinsics.checkNotNullExpressionValue(var10002, "ascent(...)");
            Intrinsics.checkNotNull(local4_9);
            var15.drawSelectionBoundingBoxGlowBatched(local7_0, var10002, local2_7, local4_9);
         }
      } else if (Intrinsics.areEqual(var5, "blockesp.mode.lazy")) {
         if (this$0.clicking != null) {
            class_2338 local8_2 = this$0.clicking;
            int local9_1 = 0;
            BoxRenderer var16 = BoxRenderer.INSTANCE;
            Color var20 = new Color(255, 207, 72);
            Intrinsics.checkNotNull(local4_9);
            var16.drawSelectionBoundingBoxGlowBatched(local8_2, var20, local2_7, local4_9);
         }

         CopyOnWriteArrayList var17 = this$0.clicked;
         Intrinsics.checkNotNullExpressionValue(this$0.clicked, "clicked");
         Iterable local6_6 = var17;
         int local7_5 = 0;

         for (Object local9_4 : local6_6) {
            class_2338 local10_3 = (class_2338)local9_4;
            class_638 var10001 = SimpleFeature.mc.field_1687;
            Intrinsics.checkNotNull(SimpleFeature.mc.field_1687);
            class_2248 var19 = var10001.method_8320(local10_3).method_26204();
            Intrinsics.checkNotNullExpressionValue(var19, "getBlock(...)");
            if (this$0.checkBlock(var19)) {
               BoxRenderer var18 = BoxRenderer.INSTANCE;
               Intrinsics.checkNotNull(local10_3);
               Color var21 = Melonity.INSTANCE.styleManager.style.ascent();
               Intrinsics.checkNotNullExpressionValue(var21, "ascent(...)");
               Intrinsics.checkNotNull(local4_9);
               var18.drawSelectionBoundingBoxGlowBatched(local10_3, var21, local2_7, local4_9);
            }
         }
      }

      class_286.method_43433(local4_9.method_60800());
      RenderSystem.disableBlend();
      RenderSystem.enableDepthTest();
   }
}
