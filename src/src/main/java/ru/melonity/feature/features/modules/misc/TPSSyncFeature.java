package ru.melonity.feature.features.modules.misc;

import kotlin.Metadata;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_3532;
import net.minecraft.class_8913;
import org.jetbrains.annotations.NotNull;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PacketEvent;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000f\u0010\u0005\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0005\u0010\u0003J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\t\u0010\nR$\u0010\f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00068\u0006@BX\u0086\u000e¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00068\u0006@BX\u0086\u000e¢\u0006\f\n\u0004\b\u0010\u0010\r\u001a\u0004\b\u0011\u0010\u000fR\u0016\u0010\u0013\u001a\u00020\u00128\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0013\u0010\u0014R\u001d\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00158\u0006¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR$\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00068\u0006@BX\u0086\u000e¢\u0006\f\n\u0004\b\u001b\u0010\r\u001a\u0004\b\u001c\u0010\u000f¨\u0006\u001d"},
   d2 = {"Lru/melonity/feature/features/modules/misc/TPSSyncFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "", "updateTPS", "", "input", "", "round", "(F)D", "value", "TPS", "F", "getTPS", "()F", "adjustTicks", "getAdjustTicks", "", "timestamp", "J", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/PacketEvent;", "onPacket", "Lru/melonity/util/event/EventListener;", "getOnPacket", "()Lru/melonity/util/event/EventListener;", "maxTPS", "getMaxTPS", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class TPSSyncFeature extends SimpleFeature {
   private float TPS = 20.0F;
   private float adjustTicks;
   private long timestamp;
   @NotNull
   private final EventListener<PacketEvent> onPacket = e -> onPacket$lambda$0(this, e);
   private float maxTPS;

   public TPSSyncFeature() {
      super("TPSSync", MenuCategory.MISC);
   }

   public final float getTPS() {
      return this.TPS;
   }

   public final float getAdjustTicks() {
      return this.adjustTicks;
   }

   @NotNull
   public final EventListener<PacketEvent> getOnPacket() {
      return this.onPacket;
   }

   public final float getMaxTPS() {
      return this.maxTPS;
   }

   private final void updateTPS() {
      long local1_0 = System.nanoTime() - this.timestamp;
      this.maxTPS = 15.0F;
      float local3_1 = this.maxTPS * (1.0E9F / (float)local1_0);
      float local4_2 = class_3532.method_15363(local3_1, 0.0F, this.maxTPS);
      this.TPS = (float)this.round(local4_2);
      this.adjustTicks = local4_2 - this.maxTPS;
      this.timestamp = System.nanoTime();
   }

   private final double round(float input) {
      return (double)Math.round((double)input * 100.0) / 100.0;
   }

   private static final void onPacket$lambda$0(TPSSyncFeature this$0, PacketEvent e) {
      if (e.packet instanceof class_8913) {
         this$0.updateTPS();
      }
   }
}
