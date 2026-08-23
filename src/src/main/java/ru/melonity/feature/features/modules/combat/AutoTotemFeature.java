package ru.melonity.feature.features.modules.combat;

import java.util.Arrays;
import java.util.function.BooleanSupplier;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1799;
import net.minecraft.class_2561;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.hud.DynamicIslandView;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.modules.AutoTotemUtil;
import ru.melonity.util.time.Timer;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u00014B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000f\u0010\u0005\u001a\u00020\u0004H\u0016¢\u0006\u0004\b\u0005\u0010\u0003J\u000f\u0010\u0006\u001a\u00020\u0004H\u0016¢\u0006\u0004\b\u0006\u0010\u0003J\u000f\u0010\u0007\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0007\u0010\u0003J\u000f\u0010\b\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\b\u0010\u0003J\u000f\u0010\n\u001a\u00020\tH\u0002¢\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\f\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\f\u0010\u0003J\u000f\u0010\r\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\r\u0010\u0003J\u000f\u0010\u000e\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u000e\u0010\u0003J\u000f\u0010\u000f\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u000f\u0010\u0003J\u000f\u0010\u0010\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0010\u0010\u0003J\u000f\u0010\u0011\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0011\u0010\u0003R\u0014\u0010\u0013\u001a\u00020\u00128\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0016\u001a\u00020\u00158\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00158\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0018\u0010\u0017R\u0014\u0010\u0019\u001a\u00020\u00128\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0019\u0010\u0014R\u0014\u0010\u001b\u001a\u00020\u001a8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u00158\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001d\u0010\u0017R\u0014\u0010\u001e\u001a\u00020\u00128\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001e\u0010\u0014R\u0014\u0010\u001f\u001a\u00020\u00128\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001f\u0010\u0014R\u0016\u0010!\u001a\u00020 8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b!\u0010\"R\u0016\u0010$\u001a\u00020#8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b$\u0010%R\u0016\u0010&\u001a\u00020\t8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b&\u0010'R\u001c\u0010*\u001a\n )*\u0004\u0018\u00010(0(8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b*\u0010+R\u0016\u0010,\u001a\u00020#8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b,\u0010%R\u0016\u0010.\u001a\u00020-8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b.\u0010/R\u001a\u00102\u001a\b\u0012\u0004\u0012\u000201008\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b2\u00103¨\u00065"},
   d2 = {"Lru/melonity/feature/features/modules/combat/AutoTotemFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "", "onEnable", "onDisable", "updateMode", "resetState", "", "shouldEquipTotem", "()Z", "equipTotem", "equipOffhandTotem", "equipMainHandTotem", "restoreOriginalSlot", "flushGuiMovePackets", "resetSprintAndUse", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "onlyHotbarSetting", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "healthSetting", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "healthOnElytraSetting", "absorptionSetting", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "conditionsSetting", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "crystalRangeSetting", "ballSetting", "noTalismanSetting", "Lru/melonity/feature/features/modules/combat/AutoTotemFeature$Mode;", "currentMode", "Lru/melonity/feature/features/modules/combat/AutoTotemFeature$Mode;", "", "originalSlot", "I", "isSwapped", "Z", "Lru/melonity/util/time/Timer;", "kotlin.jvm.PlatformType", "swapTimer", "Lru/melonity/util/time/Timer;", "mainHandTotemSlot", "", "lastHealth", "F", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/UpdateLivingEvent;", "onUpdate", "Lru/melonity/util/event/EventListener;", "Mode", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class AutoTotemFeature extends SimpleFeature {
   @NotNull
   private final BooleanSetting onlyHotbarSetting = new BooleanSetting("autototem.only_hotbar", false);
   @NotNull
   private final NumberSetting healthSetting = new NumberSetting("global.health", AutoTotemFeature::healthSetting$lambda$0, 1, 20, 3.5F);
   @NotNull
   private final NumberSetting healthOnElytraSetting = new NumberSetting("autototem.on_elytra", AutoTotemFeature::healthOnElytraSetting$lambda$1, 1, 20, 8.0F);
   @NotNull
   private final BooleanSetting absorptionSetting = new BooleanSetting("autototem.absorption", false);
   @NotNull
   private final ListSetting conditionsSetting;
   @NotNull
   private final NumberSetting crystalRangeSetting;
   @NotNull
   private final BooleanSetting ballSetting;
   @NotNull
   private final BooleanSetting noTalismanSetting;
   @NotNull
   private AutoTotemFeature.Mode currentMode;
   private int originalSlot;
   private boolean isSwapped;
   private final Timer swapTimer;
   private int mainHandTotemSlot;
   private float lastHealth;
   @NotNull
   private final EventListener<UpdateLivingEvent> onUpdate;

   public AutoTotemFeature() {
      super("AutoTotem", MenuCategory.COMBAT);
      ListSetting.ModeType var10004 = ListSetting.ModeType.MULTI;
      String[] var1 = new String[]{"autototem.conditions.fall", "autototem.end_crystal"};
      this.conditionsSetting = new ListSetting("autototem.conditions", var10004, "autototem.conditions.fall", var1);
      NumberSetting var6 = new NumberSetting("autototem.crystal_range", AutoTotemFeature::crystalRangeSetting$lambda$2, 1, 10, 5.0F);
      BooleanSupplier[] var4 = new BooleanSupplier[]{() -> crystalRangeSetting$lambda$4$lambda$3(this)};
      var6.setVisible(var4);
      this.crystalRangeSetting = var6;
      this.ballSetting = new BooleanSetting("autototem.no_ball_switch", true);
      this.noTalismanSetting = new BooleanSetting("autototem.no_talisman", false);
      this.currentMode = AutoTotemFeature.Mode.OFFHAND;
      this.originalSlot = -1;
      this.swapTimer = Timer.create();
      this.mainHandTotemSlot = -1;
      MenuFeatureView[] var7 = new MenuFeatureView[]{
         this.onlyHotbarSetting,
         this.healthSetting,
         this.healthOnElytraSetting,
         this.absorptionSetting,
         this.conditionsSetting,
         this.crystalRangeSetting,
         this.ballSetting,
         this.noTalismanSetting
      };
      this.settings(var7);
      this.onUpdate = event -> onUpdate$lambda$5(this, event);
   }

   @Override
   public void onEnable() {
      this.originalSlot = -1;
      this.isSwapped = false;
      this.mainHandTotemSlot = -1;
      this.swapTimer.reset();
      this.updateMode();
   }

   @Override
   public void onDisable() {
      this.resetState();
   }

   private final void updateMode() {
      this.currentMode = this.onlyHotbarSetting.get() ? AutoTotemFeature.Mode.MAIN_HAND : AutoTotemFeature.Mode.OFFHAND;
   }

   private final void resetState() {
      if (SimpleFeature.mc.field_1724 != null) {
         switch (this.currentMode) {
            case OFFHAND:
               if (this.isSwapped && this.originalSlot != -1) {
                  this.flushGuiMovePackets();
                  AutoTotemUtil.swapSlots(45, this.originalSlot);
               }
               break;
            case MAIN_HAND:
               if (this.originalSlot != -1) {
                  int var10000 = this.originalSlot;
                  class_746 var10001 = SimpleFeature.mc.field_1724;
                  Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
                  if (var10000 != var10001.method_31548().field_7545) {
                     class_746 var1 = SimpleFeature.mc.field_1724;
                     Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
                     var1.method_31548().field_7545 = this.originalSlot;
                  }
               }
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }

         this.originalSlot = -1;
         this.isSwapped = false;
         this.mainHandTotemSlot = -1;
         this.swapTimer.reset();
      }
   }

   private final boolean shouldEquipTotem() {
      boolean local1_0 = this.absorptionSetting.get();
      class_746 var10000 = SimpleFeature.mc.field_1724;
      Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
      float var3 = var10000.method_6032();
      float var4;
      if (local1_0) {
         class_746 var10001 = SimpleFeature.mc.field_1724;
         Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
         var4 = var10001.method_6067();
      } else {
         var4 = 0.0F;
      }

      float local2_1 = var3 + var4;
      if (local2_1 <= this.healthSetting.value.floatValue()) {
         return true;
      } else if (AutoTotemUtil.isWearingElytra() && local2_1 <= this.healthOnElytraSetting.value.floatValue()) {
         return true;
      } else if (this.ballSetting.get() && AutoTotemUtil.isBallInOffhand()) {
         return false;
      } else {
         return this.conditionsSetting.contains("autototem.conditions.fall") && AutoTotemUtil.isFalling(6.0F)
            ? true
            : this.conditionsSetting.contains("autototem.end_crystal") && AutoTotemUtil.isCrystalNearby(this.crystalRangeSetting.value.floatValue());
      }
   }

   private final void equipTotem() {
      switch (this.currentMode) {
         case OFFHAND:
            this.equipOffhandTotem();
            break;
         case MAIN_HAND:
            this.equipMainHandTotem();
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }
   }

   private final void equipOffhandTotem() {
      if (!AutoTotemUtil.hasTotemInOffhand()) {
         int local1_4 = AutoTotemUtil.findTotemSlot(this.noTalismanSetting.get());
         if (local1_4 != -1) {
            if (!this.isSwapped) {
               this.originalSlot = local1_4;
               this.isSwapped = true;
               this.swapTimer.reset();
            }

            if (this.swapTimer.hasReached(100L)) {
               this.resetSprintAndUse();
               this.flushGuiMovePackets();
               if (SimpleFeature.mc.field_1724 == null) {
                  return;
               }

               class_746 local2_0;
               String var7;
               label56: {
                  local2_0 = SimpleFeature.mc.field_1724;
                  class_1799 var10000 = local2_0.method_6079();
                  if (var10000 != null) {
                     class_2561 var6 = var10000.method_7964();
                     if (var6 != null) {
                        var7 = var6.getString();
                        if (var7 != null) {
                           break label56;
                        }
                     }
                  }

                  var7 = "Пусто";
               }

               String local3_1;
               label50: {
                  local3_1 = var7;
                  class_1799 var8 = local2_0.method_31548().method_5438(local1_4);
                  if (var8 != null) {
                     class_2561 var9 = var8.method_7964();
                     if (var9 != null) {
                        var7 = var9.getString();
                        if (var7 != null) {
                           break label50;
                        }
                     }
                  }

                  var7 = "Тотем";
               }

               String local4_2 = var7;
               AutoTotemUtil.swapToOffhand(local1_4);
               this.resetSprintAndUse();
               String local5_3 = (0 <= local1_4 ? local1_4 < 9 : false) ? "Хотбар " + (local1_4 + 1) : "Слот " + local1_4;
               if (Melonity.INSTANCE.featureManager.hudFeature != null) {
                  DynamicIslandView var11 = Melonity.INSTANCE.featureManager.hudFeature.getDynamicIslandView();
                  if (var11 != null) {
                     var11.pushNotification("AutoTotem", "Оффхенд: " + local3_1 + " → " + local4_2 + " (" + local5_3 + ")", true);
                  }
               }
            }
         }
      }
   }

   private final void equipMainHandTotem() {
      if (this.originalSlot == -1) {
         class_746 var10001 = SimpleFeature.mc.field_1724;
         Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
         this.originalSlot = var10001.method_31548().field_7545;
      }

      if (this.mainHandTotemSlot == -1) {
         this.mainHandTotemSlot = AutoTotemUtil.findTotemInHotbar(this.noTalismanSetting.get());
         if (this.mainHandTotemSlot == -1) {
            return;
         }
      }

      if (!this.isSwapped) {
         this.isSwapped = true;
         this.swapTimer.reset();
      }

      if (this.swapTimer.hasReached(50L)) {
         class_746 var10000 = SimpleFeature.mc.field_1724;
         Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
         var10000.method_31548().field_7545 = this.mainHandTotemSlot;
      }
   }

   private final void restoreOriginalSlot() {
      switch (this.currentMode) {
         case OFFHAND:
            if (this.originalSlot != -1 && AutoTotemUtil.hasTotemInOffhand() && this.swapTimer.hasReached(200L)) {
               this.resetSprintAndUse();
               this.flushGuiMovePackets();
               AutoTotemUtil.swapFromOffhand(this.originalSlot);
               this.resetSprintAndUse();
               this.resetState();
            }
            break;
         case MAIN_HAND:
            if (this.originalSlot != -1 && !this.shouldEquipTotem() && this.swapTimer.hasReached(150L)) {
               this.resetSprintAndUse();
               class_746 var10000 = SimpleFeature.mc.field_1724;
               Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
               var10000.method_31548().field_7545 = this.originalSlot;
               this.resetState();
            }
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }
   }

   private final void flushGuiMovePackets() {
      if (Melonity.INSTANCE.featureManager.guiMoveFeature != null) {
         Melonity.INSTANCE.featureManager.guiMoveFeature.flushQueuedPackets();
      }
   }

   private final void resetSprintAndUse() {
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 local1_0 = SimpleFeature.mc.field_1724;
         if (local1_0.method_5624()) {
            local1_0.method_5728(false);
         }

         local1_0.method_5728(false);
         if (local1_0.method_6115()) {
            local1_0.method_6075();
         }
      }
   }

   private static final String healthSetting$lambda$0(Number value) {
      String var1 = "%.1f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String healthOnElytraSetting$lambda$1(Number value) {
      String var1 = "%.1f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String crystalRangeSetting$lambda$2(Number value) {
      Intrinsics.checkNotNull(value);
      return value.toString();
   }

   private static final boolean crystalRangeSetting$lambda$4$lambda$3(AutoTotemFeature this$0) {
      return this$0.conditionsSetting.contains("autototem.end_crystal");
   }

   private static final void onUpdate$lambda$5(AutoTotemFeature local0_0, UpdateLivingEvent var1) {
      if (SimpleFeature.mc.field_1724 != null && SimpleFeature.mc.field_1687 != null) {
         local0_0.updateMode();
         class_746 var10001 = SimpleFeature.mc.field_1724;
         Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
         float var2 = var10001.method_6032();
         float var3;
         if (local0_0.absorptionSetting.get()) {
            class_746 var10002 = SimpleFeature.mc.field_1724;
            Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
            var3 = var10002.method_6067();
         } else {
            var3 = 0.0F;
         }

         local0_0.lastHealth = var2 + var3;
         if (local0_0.shouldEquipTotem()) {
            local0_0.equipTotem();
         } else if (local0_0.isSwapped) {
            local0_0.restoreOriginalSlot();
         }
      } else {
         local0_0.resetState();
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
      d2 = {"Lru/melonity/feature/features/modules/combat/AutoTotemFeature$Mode;", "", "<init>", "(Ljava/lang/String;I)V", "OFFHAND", "MAIN_HAND", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   private static enum Mode {
      OFFHAND,
      MAIN_HAND;

      private static final EnumEntries<AutoTotemFeature.Mode> $ENTRIES = EnumEntriesKt.enumEntries(values());

      @NotNull
      public static EnumEntries<AutoTotemFeature.Mode> getEntries() {
         return $ENTRIES;
      }
   }
}
