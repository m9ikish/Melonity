package ru.melonity.feature.features.modules.player;

import java.util.Arrays;
import java.util.function.BooleanSupplier;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateEvent;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001f\u0010\b\u001a\u00020\u00072\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u0016¢\u0006\u0004\b\b\u0010\tJ\u000f\u0010\n\u001a\u00020\u0007H\u0016¢\u0006\u0004\b\n\u0010\u0003R\u0014\u0010\f\u001a\u00020\u000b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\f\u0010\rR\u0014\u0010\u000f\u001a\u00020\u000e8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0012\u001a\n \u0011*\u0004\u0018\u00010\u000b0\u000b8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0012\u0010\rR\u0016\u0010\u0014\u001a\u00020\u00138\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u00168\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0018\u0010\u0019¨\u0006\u001a"},
   d2 = {"Lru/melonity/feature/features/modules/player/TimerFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "", "value", "silent", "", "setEnabled", "(ZZ)V", "onDisable", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "timerSpeedSetting", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "limitWork", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "kotlin.jvm.PlatformType", "limitTime", "", "tick", "I", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/UpdateEvent;", "onMotion", "Lru/melonity/util/event/EventListener;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class TimerFeature extends SimpleFeature {
   @NotNull
   private final NumberSetting timerSpeedSetting = new NumberSetting("global.boost", TimerFeature::timerSpeedSetting$lambda$0, 0.5, 4.0, 1.38);
   @NotNull
   private final BooleanSetting limitWork = new BooleanSetting("timer.limit", false);
   private final NumberSetting limitTime;
   private int tick;
   @NotNull
   private final EventListener<UpdateEvent> onMotion;

   public TimerFeature() {
      super("Timer", MenuCategory.PLAYER, true);
      NumberSetting var10001 = new NumberSetting("timer.ticks", TimerFeature::limitTime$lambda$1, 2.0, 50.0, 12.0);
      BooleanSupplier[] var1 = new BooleanSupplier[]{() -> limitTime$lambda$2(this)};
      this.limitTime = var10001.setVisible(var1);
      MenuFeatureView[] var2 = new MenuFeatureView[]{this.timerSpeedSetting, this.limitWork, this.limitTime};
      this.settings(var2);
      this.onMotion = e -> onMotion$lambda$3(this, e);
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      this.tick = 0;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      Melonity.timerSpeed = 1.0F;
   }

   private static final String timerSpeedSetting$lambda$0(Number value) {
      String var1 = "%.1f";
      Object[] var2 = new Object[]{value.floatValue()};
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String limitTime$lambda$1(Number value) {
      return String.valueOf(value.intValue());
   }

   private static final boolean limitTime$lambda$2(TimerFeature this$0) {
      return this$0.limitWork.get();
   }

   private static final void onMotion$lambda$3(TimerFeature this$0, UpdateEvent e) {
      if (!this$0.inInvalidState()) {
         this$0.tick++;
         Melonity.timerSpeed = this$0.timerSpeedSetting.value.floatValue();
         if (this$0.limitWork.get() && this$0.tick > this$0.limitTime.value.intValue()) {
            this$0.setEnabled(false);
         }
      }
   }
}
