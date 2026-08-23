package ru.melonity.feature.features.modules.combat;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_636;
import net.minecraft.class_638;
import net.minecraft.class_742;
import net.minecraft.class_746;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.jetbrains.annotations.NotNull;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.inventory.InventoryUtil;
import ru.melonity.util.math.MathUtil;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 *2\u00020\u0001:\u0004+,-*B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0019\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0003¢\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0002¢\u0006\u0004\b\n\u0010\u0003J\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u000bH\u0003¢\u0006\u0004\b\u000e\u0010\u000fJ)\u0010\u0016\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0013H\u0003¢\u0006\u0004\b\u0016\u0010\u0017J\u000f\u0010\u0018\u001a\u00020\tH\u0016¢\u0006\u0004\b\u0018\u0010\u0003R\u0016\u0010\u001a\u001a\u00020\u00198\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001d\u001a\u00020\u001c8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020\u001c8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001f\u0010\u001eR\u0014\u0010!\u001a\u00020 8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020 8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b#\u0010\"R\u0014\u0010$\u001a\u00020 8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b$\u0010\"R\"\u0010(\u001a\u0010\u0012\f\u0012\n '*\u0004\u0018\u00010&0&0%8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b(\u0010)¨\u0006."},
   d2 = {"Lru/melonity/feature/features/modules/combat/SurroundFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "Lnet/minecraft/class_2338;", "toPlace", "Lru/melonity/feature/features/modules/combat/SurroundFeature$PlacementInfo;", "findValidPlacement", "(Lnet/minecraft/class_2338;)Lru/melonity/feature/features/modules/combat/SurroundFeature$PlacementInfo;", "", "restoreSlotIfNeeded", "Lnet/minecraft/class_243;", "hit", "Lru/melonity/feature/features/modules/combat/SurroundFeature$Rot;", "calcRotations", "(Lnet/minecraft/class_243;)Lru/melonity/feature/features/modules/combat/SurroundFeature$Rot;", "", "yaw", "pitch", "", "reach", "Lnet/minecraft/class_3965;", "raycastWith", "(FFD)Lnet/minecraft/class_3965;", "onDisable", "", "oldSlot", "I", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "placer", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "distance", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "keep", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "doubleUp", "useObsidian", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/UpdateEvent;", "kotlin.jvm.PlatformType", "onPreMotion", "Lru/melonity/util/event/EventListener;", "Companion", "PlacementInfo", "Rot", "CandidatePos", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class SurroundFeature extends SimpleFeature {
   @NotNull
   public static final SurroundFeature.Companion Companion = new SurroundFeature.Companion(null);
   private int oldSlot = -1;
   @NotNull
   private final NumberSetting placer = new NumberSetting("surround.pertick", SurroundFeature::placer$lambda$0, 1, 10, 1);
   @NotNull
   private final NumberSetting distance = new NumberSetting("surround.reach", SurroundFeature::distance$lambda$1, 2, 5, 4.3F);
   @NotNull
   private final BooleanSetting keep = new BooleanSetting("surround.keep", true);
   @NotNull
   private final BooleanSetting doubleUp = new BooleanSetting("surround.double_up", false);
   @NotNull
   private final BooleanSetting useObsidian = new BooleanSetting("surround.obsidian", false);
   @NotNull
   private final EventListener<UpdateEvent> onPreMotion = e -> onPreMotion$lambda$3(this, e);

   public SurroundFeature() {
      super("Surround", MenuCategory.COMBAT);
      MenuFeatureView[] var1 = new MenuFeatureView[]{this.placer, this.distance, this.doubleUp, this.useObsidian};
      this.settings(var1);
   }

   private final SurroundFeature.PlacementInfo findValidPlacement(class_2338 toPlace) {
      class_746 player = SimpleFeature.mc.field_1724;
      class_638 world = SimpleFeature.mc.field_1687;
      if (player != null && world != null) {
         if (!world.method_8320(toPlace).method_26215()) {
            return null;
         } else {
            class_238 placeBox = new class_238(toPlace);
            List<class_1297> blockers = world.method_8333(
               (class_1297)null, placeBox, entity -> entity instanceof class_1309 && entity.method_5805()
            );
            if (!blockers.isEmpty()) {
               return null;
            } else {
               class_243 eyes = player.method_33571();
               SurroundFeature.PlacementInfo best = null;
               double bestDistance = Double.MAX_VALUE;

               for (class_2350 side : class_2350.values()) {
                  class_2338 support = toPlace.method_10093(side);
                  class_2680 supportState = world.method_8320(support);
                  if (!supportState.method_26215() && supportState.method_26227().method_15769() && supportState.method_26234(world, support)) {
                     class_2350 clickSide = side.method_10153();
                     double distance = eyes.method_1022(Companion.faceCenter(support, clickSide));
                     if (distance < bestDistance) {
                        bestDistance = distance;
                        best = new SurroundFeature.PlacementInfo(support, clickSide);
                     }
                  }
               }

               return best;
            }
         }
      } else {
         return null;
      }
   }

   private final void restoreSlotIfNeeded() {
      if (this.oldSlot != -1) {
         class_746 player = SimpleFeature.mc.field_1724;
         if (player != null) {
            player.method_31548().field_7545 = this.oldSlot;
         }
         this.oldSlot = -1;
      }
   }

   private final SurroundFeature.Rot calcRotations(class_243 hit) {
      class_746 player = SimpleFeature.mc.field_1724;
      if (player == null) {
         return new SurroundFeature.Rot(0.0F, 0.0F);
      } else {
         class_243 eyes = player.method_33571();
         double deltaX = hit.field_1352 - eyes.field_1352;
         double deltaY = hit.field_1351 - eyes.field_1351;
         double deltaZ = hit.field_1350 - eyes.field_1350;
         double horizontal = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
         float yaw = class_3532.method_15393((float)(Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90.0));
         float pitch = class_3532.method_15363((float)(-Math.toDegrees(Math.atan2(deltaY, horizontal))), -90.0F, 90.0F);
         return new SurroundFeature.Rot(yaw, pitch);
      }
   }

   private final class_3965 raycastWith(float yaw, float pitch, double reach) {
      class_746 player = SimpleFeature.mc.field_1724;
      class_638 world = SimpleFeature.mc.field_1687;
      if (player != null && world != null) {
         class_243 start = player.method_33571();
         class_243 end = start.method_1019(Companion.directionFromYawPitch(yaw, pitch).method_1021(reach));
         return world.method_17742(new class_3959(start, end, class_3960.field_17558, class_242.field_1348, (class_1297)player));
      } else {
         return null;
      }
   }

   @Override
   public void onDisable() {
      this.restoreSlotIfNeeded();
   }

   private static final String placer$lambda$0(Number value) {
      Intrinsics.checkNotNull(value);
      return String.valueOf(value.intValue());
   }

   private static final String distance$lambda$1(Number value) {
      Intrinsics.checkNotNull(value);
      return String.valueOf(MathUtil.round((double)value.floatValue(), 2));
   }

   private static final int onPreMotion$lambda$3$lambda$2(SurroundFeature.CandidatePos c1, SurroundFeature.CandidatePos c2) {
      Intrinsics.checkNotNull(c1);
      int var10000 = c1.getPriority();
      Intrinsics.checkNotNull(c2);
      int local2_0 = Intrinsics.compare(var10000, c2.getPriority());
      if (local2_0 != 0) {
         return local2_0;
      } else {
         class_746 var7 = SimpleFeature.mc.field_1724;
         Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
         double local3_1 = var7.method_24515().method_10262((class_2382)c1.getPos());
         class_746 var8 = SimpleFeature.mc.field_1724;
         Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
         double local5_2 = var8.method_24515().method_10262((class_2382)c2.getPos());
         return Double.compare(local5_2, local3_1);
      }
   }

   private static final void onPreMotion$lambda$3(SurroundFeature this$0, UpdateEvent event) {
      if (SimpleFeature.mc.field_1724 != null && SimpleFeature.mc.field_1687 != null) {
         class_1309 local2_13 = null;
         float local3_14 = Float.MAX_VALUE;
         class_638 var10000 = SimpleFeature.mc.field_1687;
         Intrinsics.checkNotNull(SimpleFeature.mc.field_1687);

         for (class_742 local5_1 : var10000.method_18456()) {
            if (local5_1 != SimpleFeature.mc.field_1724) {
               float local6_0 = local5_1.method_5739((class_1297)SimpleFeature.mc.field_1724);
               if (local6_0 < local3_14) {
                  local3_14 = local6_0;
                  local2_13 = (class_1309)local5_1;
               }
            }
         }

         if (local2_13 != null && !((double)local3_14 > this$0.distance.value.doubleValue())) {
            class_1792 local4_15 = this$0.useObsidian.get() ? class_1802.field_8281 : class_1802.field_8786;
            int local5_16 = InventoryUtil.getHotbarItem((class_1657)SimpleFeature.mc.field_1724, local4_15);
            if (local5_16 != -1) {
               if (this$0.keep.get()) {
                  class_746 var25 = SimpleFeature.mc.field_1724;
                  Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
                  if (var25.method_31548().field_7545 != local5_16) {
                     if (this$0.oldSlot == -1) {
                        class_746 var10001 = SimpleFeature.mc.field_1724;
                        Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
                        this$0.oldSlot = var10001.method_31548().field_7545;
                     }

                     class_746 var26 = SimpleFeature.mc.field_1724;
                     Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
                     var26.method_31548().field_7545 = local5_16;
                  }
               }

               class_2338 local6_17 = local2_13.method_24515();
               List<SurroundFeature.CandidatePos> local7_18 = new ArrayList<>();
               if (this$0.useObsidian.get()) {
                  for (int local8_4 = -1; local8_4 < 2; local8_4++) {
                     for (int local9_3 = -1; local9_3 < 2; local9_3++) {
                        if (local8_4 != 0 || local9_3 != 0) {
                           int local10_2 = 3;
                           if (local8_4 == 0 && local9_3 != 0 || local8_4 != 0 && local9_3 == 0) {
                              local10_2 = 1;
                           }

                           class_2338 var10003 = local6_17.method_10069(local8_4, 0, local9_3);
                           Intrinsics.checkNotNullExpressionValue(var10003, "add(...)");
                           local7_18.add(new SurroundFeature.CandidatePos(var10003, local10_2));
                           var10003 = local6_17.method_10069(local8_4, 1, local9_3);
                           Intrinsics.checkNotNullExpressionValue(var10003, "add(...)");
                           local7_18.add(new SurroundFeature.CandidatePos(var10003, local10_2));
                           if (this$0.doubleUp.get()) {
                              var10003 = local6_17.method_10069(local8_4, 2, local9_3);
                              Intrinsics.checkNotNullExpressionValue(var10003, "add(...)");
                              local7_18.add(new SurroundFeature.CandidatePos(var10003, local10_2 + 1));
                           }
                        }
                     }
                  }

                  Intrinsics.checkNotNull(local6_17);
                  local7_18.add(new SurroundFeature.CandidatePos(local6_17, 2));
                  class_2338 var36 = local6_17.method_10084();
                  Intrinsics.checkNotNullExpressionValue(var36, "up(...)");
                  local7_18.add(new SurroundFeature.CandidatePos(var36, 2));
               } else {
                  class_2338 var37 = local6_17.method_10095();
                  Intrinsics.checkNotNullExpressionValue(var37, "north(...)");
                  local7_18.add(new SurroundFeature.CandidatePos(var37, 1));
                  var37 = local6_17.method_10072();
                  Intrinsics.checkNotNullExpressionValue(var37, "south(...)");
                  local7_18.add(new SurroundFeature.CandidatePos(var37, 1));
                  var37 = local6_17.method_10078();
                  Intrinsics.checkNotNullExpressionValue(var37, "east(...)");
                  local7_18.add(new SurroundFeature.CandidatePos(var37, 1));
                  var37 = local6_17.method_10067();
                  Intrinsics.checkNotNullExpressionValue(var37, "west(...)");
                  local7_18.add(new SurroundFeature.CandidatePos(var37, 1));
                  Intrinsics.checkNotNull(local6_17);
                  local7_18.add(new SurroundFeature.CandidatePos(local6_17, 2));
                  var37 = local6_17.method_10084();
                  Intrinsics.checkNotNullExpressionValue(var37, "up(...)");
                  local7_18.add(new SurroundFeature.CandidatePos(var37, 2));
                  if (this$0.doubleUp.get()) {
                     var37 = local6_17.method_10095().method_10084();
                     Intrinsics.checkNotNullExpressionValue(var37, "up(...)");
                     local7_18.add(new SurroundFeature.CandidatePos(var37, 2));
                     var37 = local6_17.method_10072().method_10084();
                     Intrinsics.checkNotNullExpressionValue(var37, "up(...)");
                     local7_18.add(new SurroundFeature.CandidatePos(var37, 2));
                     var37 = local6_17.method_10078().method_10084();
                     Intrinsics.checkNotNullExpressionValue(var37, "up(...)");
                     local7_18.add(new SurroundFeature.CandidatePos(var37, 2));
                     var37 = local6_17.method_10067().method_10084();
                     Intrinsics.checkNotNullExpressionValue(var37, "up(...)");
                     local7_18.add(new SurroundFeature.CandidatePos(var37, 2));
                     var37 = local6_17.method_10086(2);
                     Intrinsics.checkNotNullExpressionValue(var37, "up(...)");
                     local7_18.add(new SurroundFeature.CandidatePos(var37, 3));
                  }
               }

               CollectionsKt.sortWith(local7_18, SurroundFeature::onPreMotion$lambda$3$lambda$2);
               int local8_19 = 0;

               for (SurroundFeature.CandidatePos local10_12 : local7_18) {
                  class_2338 local11_6 = local10_12.getPos();
                  if (local8_19 >= this$0.placer.value.intValue()) {
                     break;
                  }

                  var10000 = SimpleFeature.mc.field_1687;
                  Intrinsics.checkNotNull(SimpleFeature.mc.field_1687);
                  if (var10000.method_8320(local11_6).method_26215()) {
                     SurroundFeature.PlacementInfo local12_7 = this$0.findValidPlacement(local11_6);
                     if (local12_7 != null) {
                        class_243 local13_8 = Companion.faceCenter(local12_7.getClickPos(), local12_7.getClickSide());
                        class_746 var28 = SimpleFeature.mc.field_1724;
                        Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
                        double local14_9 = var28.method_33571().method_1022(local13_8);
                        if (!(local14_9 > this$0.distance.value.doubleValue())) {
                           SurroundFeature.Rot local16_10 = this$0.calcRotations(local13_8);
                           RotationManager.INSTANCE.rotate(local16_10.getYaw(), local16_10.getPitch());
                           class_3965 local17_11 = this$0.raycastWith(local16_10.getYaw(), local16_10.getPitch(), this$0.distance.value.doubleValue());
                           if (local17_11 != null
                              && Intrinsics.areEqual(local17_11.method_17777(), local12_7.getClickPos())
                              && local17_11.method_17780() == local12_7.getClickSide()) {
                              if (!this$0.keep.get()) {
                                 if (this$0.oldSlot == -1) {
                                    class_746 var33 = SimpleFeature.mc.field_1724;
                                    Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
                                    this$0.oldSlot = var33.method_31548().field_7545;
                                 }

                                 class_746 var29 = SimpleFeature.mc.field_1724;
                                 Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
                                 var29.method_31548().field_7545 = local5_16;
                              }

                              class_636 var30 = SimpleFeature.mc.field_1761;
                              Intrinsics.checkNotNull(SimpleFeature.mc.field_1761);
                              class_1269 local18_5 = var30.method_2896(SimpleFeature.mc.field_1724, class_1268.field_5808, local17_11);
                              if (local18_5.method_23666()) {
                                 class_746 var31 = SimpleFeature.mc.field_1724;
                                 Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
                                 var31.method_6104(class_1268.field_5808);
                              }

                              var10000 = SimpleFeature.mc.field_1687;
                              Intrinsics.checkNotNull(SimpleFeature.mc.field_1687);
                              if (!var10000.method_8320(local11_6).method_26215()) {
                                 local8_19++;
                              }
                           }
                        }
                     }
                  }
               }

               if (!this$0.keep.get()) {
                  this$0.restoreSlotIfNeeded();
               }
            }
         }
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010\u000b\u001a\u0004\b\f\u0010\r¨\u0006\u000e"},
      d2 = {"Lru/melonity/feature/features/modules/combat/SurroundFeature$CandidatePos;", "", "Lnet/minecraft/class_2338;", "pos", "", "priority", "<init>", "(Lnet/minecraft/class_2338;I)V", "Lnet/minecraft/class_2338;", "getPos", "()Lnet/minecraft/class_2338;", "I", "getPriority", "()I", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   private static final class CandidatePos {
      @NotNull
      private final class_2338 pos;
      private final int priority;

      public CandidatePos(@NotNull class_2338 pos, int priority) {
         super();
         Intrinsics.checkNotNullParameter(pos, "pos");
         this.pos = pos;
         this.priority = priority;
      }

      @NotNull
      public final class_2338 getPos() {
         return this.pos;
      }

      public final int getPriority() {
         return this.priority;
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001f\u0010\t\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u000e\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\u000e\u0010\u000f¨\u0006\u0010"},
      d2 = {"Lru/melonity/feature/features/modules/combat/SurroundFeature$Companion;", "", "<init>", "()V", "Lnet/minecraft/class_2338;", "pos", "Lnet/minecraft/class_2350;", "face", "Lnet/minecraft/class_243;", "faceCenter", "(Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)Lnet/minecraft/class_243;", "", "yaw", "pitch", "directionFromYawPitch", "(FF)Lnet/minecraft/class_243;", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   public static final class Companion {
      private Companion() {
      }

      public Companion(kotlin.jvm.internal.DefaultConstructorMarker marker) {
         this();
      }

      private final class_243 faceCenter(class_2338 pos, class_2350 face) {
         class_243 var10000 = class_243.method_24953((class_2382)pos)
            .method_1031((double)face.method_10148() * 0.5, (double)face.method_10164() * 0.5, (double)face.method_10165() * 0.5);
         Intrinsics.checkNotNullExpressionValue(var10000, "add(...)");
         return var10000;
      }

      private final class_243 directionFromYawPitch(float yaw, float pitch) {
         double local3_0 = Math.toRadians((double)yaw);
         double local5_1 = Math.toRadians((double)pitch);
         double local7_2 = -Math.sin(local3_0) * Math.cos(local5_1);
         double local9_3 = -Math.sin(local5_1);
         double local11_4 = Math.cos(local3_0) * Math.cos(local5_1);
         return new class_243(local7_2, local9_3, local11_4);
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010\u000b\u001a\u0004\b\f\u0010\r¨\u0006\u000e"},
      d2 = {"Lru/melonity/feature/features/modules/combat/SurroundFeature$PlacementInfo;", "", "Lnet/minecraft/class_2338;", "clickPos", "Lnet/minecraft/class_2350;", "clickSide", "<init>", "(Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)V", "Lnet/minecraft/class_2338;", "getClickPos", "()Lnet/minecraft/class_2338;", "Lnet/minecraft/class_2350;", "getClickSide", "()Lnet/minecraft/class_2350;", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   private static final class PlacementInfo {
      @NotNull
      private final class_2338 clickPos;
      @NotNull
      private final class_2350 clickSide;

      public PlacementInfo(@NotNull class_2338 clickPos, @NotNull class_2350 clickSide) {
         super();
         Intrinsics.checkNotNullParameter(clickPos, "clickPos");
         Intrinsics.checkNotNullParameter(clickSide, "clickSide");
         this.clickPos = clickPos;
         this.clickSide = clickSide;
      }

      @NotNull
      public final class_2338 getClickPos() {
         return this.clickPos;
      }

      @NotNull
      public final class_2350 getClickSide() {
         return this.clickSide;
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\t\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0004\u0010\u0007\u001a\u0004\b\n\u0010\t¨\u0006\u000b"},
      d2 = {"Lru/melonity/feature/features/modules/combat/SurroundFeature$Rot;", "", "", "yaw", "pitch", "<init>", "(FF)V", "F", "getYaw", "()F", "getPitch", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   private static final class Rot {
      private final float yaw;
      private final float pitch;

      public Rot(float yaw, float pitch) {
         this.yaw = yaw;
         this.pitch = pitch;
      }

      public final float getYaw() {
         return this.yaw;
      }

      public final float getPitch() {
         return this.pitch;
      }
   }
}
