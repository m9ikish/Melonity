package ru.melonity.feature.features.modules.combat;

import java.util.function.BooleanSupplier;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_490;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PreMotionEvent;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.modules.AutoPotUtil;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0014¢\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\t\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\t\u0010\u0003J\u000f\u0010\n\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\n\u0010\u0003J\u000f\u0010\f\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\f\u0010\rR\u0014\u0010\u000f\u001a\u00020\u000e8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0013\u001a\n \u0012*\u0004\u0018\u00010\u00110\u00118\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0016\u001a\u00020\u00158\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0018\u001a\u00020\u000b8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0018\u0010\u0019R\u0016\u0010\u001a\u001a\u00020\u000b8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001a\u0010\u0019R\u0016\u0010\u001b\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001b\u0010\u001cR\u0016\u0010\u001d\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001d\u0010\u001cR\u0016\u0010\u001e\u001a\u00020\u000b8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001e\u0010\u0019R\u001d\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\u001f8\u0006¢\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R\u001d\u0010&\u001a\b\u0012\u0004\u0012\u00020%0\u001f8\u0006¢\u0006\f\n\u0004\b&\u0010\"\u001a\u0004\b'\u0010$¨\u0006("},
   d2 = {"Lru/melonity/feature/features/modules/combat/AutoPotionFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "", "enabled", "", "onToggle", "(Z)V", "reset", "handleLegitMode", "", "findPotionToThrow", "()I", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "potMode", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "kotlin.jvm.PlatformType", "minHealthSetting", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "notWorkInInventory", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "pendingSlot", "I", "originalSlot", "isThrowing", "Z", "needRotation", "legitPhase", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/UpdateEvent;", "onUpdate", "Lru/melonity/util/event/EventListener;", "getOnUpdate", "()Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/PreMotionEvent;", "onPreMotion", "getOnPreMotion", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class AutoPotionFeature extends SimpleFeature {
   @NotNull
   private final ListSetting potMode;
   private final NumberSetting minHealthSetting;
   @NotNull
   private final BooleanSetting notWorkInInventory;
   private int pendingSlot;
   private int originalSlot;
   private boolean isThrowing;
   private boolean needRotation;
   private int legitPhase;
   @NotNull
   private final EventListener<UpdateEvent> onUpdate;
   @NotNull
   private final EventListener<PreMotionEvent> onPreMotion;

   public AutoPotionFeature() {
      super("AutoPotion", MenuCategory.COMBAT);
      ListSetting.ModeType var10004 = ListSetting.ModeType.MULTI;
      String[] var1 = new String[]{"autopotion.strength", "autopotion.speed", "autopotion.fr", "autopotion.ih"};
      this.potMode = new ListSetting("autopotion.throwpot", var10004, "autopotion.strength", var1);
      NumberSetting var10001 = new NumberSetting("autopotion.minhp", AutoPotionFeature::minHealthSetting$lambda$0, 1, 20, 6);
      BooleanSupplier[] var2 = new BooleanSupplier[]{() -> minHealthSetting$lambda$1(this)};
      this.minHealthSetting = var10001.setVisible(var2);
      this.notWorkInInventory = new BooleanSetting("autopotion.inventory", false);
      this.pendingSlot = -1;
      this.originalSlot = -1;
      MenuFeatureView[] var3 = new MenuFeatureView[]{this.potMode, this.minHealthSetting, this.notWorkInInventory};
      this.settings(var3);
      this.onUpdate = e -> onUpdate$lambda$2(this, e);
      this.onPreMotion = e -> onPreMotion$lambda$3(this, e);
   }

   @Override
   protected void onToggle(boolean enabled) {
      if (!enabled) {
         this.reset();
      }
   }

   private final void reset() {
      this.isThrowing = false;
      this.needRotation = false;
      this.pendingSlot = -1;
      this.originalSlot = -1;
      this.legitPhase = 0;
      RotationManager.INSTANCE.reset();
   }

   @NotNull
   public final EventListener<UpdateEvent> getOnUpdate() {
      return this.onUpdate;
   }

   @NotNull
   public final EventListener<PreMotionEvent> getOnPreMotion() {
      return this.onPreMotion;
   }

   private final void handleLegitMode() {
      switch (this.legitPhase) {
         case 0:
            int local1_0 = this.findPotionToThrow();
            if (local1_0 != -1 && AutoPotUtil.canThrow()) {
               this.pendingSlot = local1_0;
               class_746 var10001 = SimpleFeature.mc.field_1724;
               Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
               this.originalSlot = var10001.method_31548().field_7545;
               this.isThrowing = true;
               this.needRotation = true;
               RotationManager var10000 = RotationManager.INSTANCE;
               var10001 = SimpleFeature.mc.field_1724;
               Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
               var10000.rotate(var10001.method_36454(), 90.0F);
               this.legitPhase = 1;
            }
            break;
         case 1:
            AutoPotUtil.switchToSlot(this.pendingSlot);
            this.legitPhase = 2;
            break;
         case 2:
            this.legitPhase = 3;
            break;
         case 3:
            AutoPotUtil.useItem();
            this.legitPhase = 4;
            break;
         case 4:
            AutoPotUtil.restoreSlot(this.originalSlot);
            this.reset();
      }
   }

   private final int findPotionToThrow() {
      if (!AutoPotUtil.canThrow()) {
         return -1;
      } else {
         if (this.potMode.contains("autopotion.ih") && AutoPotUtil.needsHeal(this.minHealthSetting.value.floatValue())) {
            int local1_0 = AutoPotUtil.findPotionSlot(AutoPotUtil.PotionType.INSTANT_HEALTH);
            if (local1_0 != -1) {
               return local1_0;
            }
         }

         if (this.potMode.contains("autopotion.strength") && !AutoPotUtil.hasActiveEffect(AutoPotUtil.PotionType.STRENGTH)) {
            int local1_1 = AutoPotUtil.findPotionSlot(AutoPotUtil.PotionType.STRENGTH);
            if (local1_1 != -1) {
               return local1_1;
            }
         }

         if (this.potMode.contains("autopotion.speed") && !AutoPotUtil.hasActiveEffect(AutoPotUtil.PotionType.SPEED)) {
            int local1_2 = AutoPotUtil.findPotionSlot(AutoPotUtil.PotionType.SPEED);
            if (local1_2 != -1) {
               return local1_2;
            }
         }

         if (this.potMode.contains("autopotion.fr") && !AutoPotUtil.hasActiveEffect(AutoPotUtil.PotionType.FIRE_RESISTANCE)) {
            int local1_3 = AutoPotUtil.findPotionSlot(AutoPotUtil.PotionType.FIRE_RESISTANCE);
            if (local1_3 != -1) {
               return local1_3;
            }
         }

         return -1;
      }
   }

   private static final String minHealthSetting$lambda$0(Number value) {
      return String.valueOf(value != null ? value.intValue() : null);
   }

   private static final boolean minHealthSetting$lambda$1(AutoPotionFeature this$0) {
      return this$0.potMode.contains("autopotion.ih");
   }

   private static final void onUpdate$lambda$2(AutoPotionFeature local0_0, UpdateEvent var1) {
      if (SimpleFeature.mc.field_1724 == null) {
         local0_0.reset();
      } else if (SimpleFeature.mc.field_1755 instanceof class_490 && local0_0.notWorkInInventory.get()) {
         local0_0.reset();
      } else {
         local0_0.handleLegitMode();
      }
   }

   private static final void onPreMotion$lambda$3(AutoPotionFeature this$0, PreMotionEvent event) {
      if (this$0.needRotation) {
         event.setPitch(90.0F);
      }
   }
}
