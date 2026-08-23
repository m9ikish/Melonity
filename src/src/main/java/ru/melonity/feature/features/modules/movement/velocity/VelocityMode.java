package ru.melonity.feature.features.modules.movement.velocity;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.EventPlayerMove;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0019\u0010\t\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H&¢\u0006\u0004\b\t\u0010\nJ\u0019\u0010\r\u001a\u00020\f2\b\u0010\u0007\u001a\u0004\u0018\u00010\u000bH&¢\u0006\u0004\b\r\u0010\u000eJ\u0019\u0010\u0010\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u000fH&¢\u0006\u0004\b\u0010\u0010\u0011J\u0019\u0010\u0013\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0012H&¢\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0015\u001a\u00020\bH&¢\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0017\u001a\u00020\bH&¢\u0006\u0004\b\u0017\u0010\u0016J\u000f\u0010\u0018\u001a\u00020\fH\u0004¢\u0006\u0004\b\u0018\u0010\u0019R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u001c\u0010!\u001a\n \u001e*\u0004\u0018\u00010\u001d0\u001d8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u0006\""},
   d2 = {"Lru/melonity/feature/features/modules/movement/velocity/VelocityMode;", "", "", "name", "<init>", "(Ljava/lang/String;)V", "Lru/melonity/util/event/events/UpdateLivingEvent;", "event", "", "onUpdate", "(Lru/melonity/util/event/events/UpdateLivingEvent;)V", "Lru/melonity/util/event/events/PacketEvent;", "", "onPacket", "(Lru/melonity/util/event/events/PacketEvent;)Z", "Lru/melonity/util/event/events/EventPlayerMove;", "onPlayerMove", "(Lru/melonity/util/event/events/EventPlayerMove;)V", "Lru/melonity/util/event/events/EventInput;", "onInput", "(Lru/melonity/util/event/events/EventInput;)V", "onEnable", "()V", "onDisable", "hasVelocityHandle", "()Z", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "Lnet/minecraft/class_310;", "kotlin.jvm.PlatformType", "getMc", "()Lnet/minecraft/class_310;", "mc", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public abstract class VelocityMode {
   @NotNull
   private final String name;

   public VelocityMode(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      this.name = name;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   public abstract void onUpdate(@Nullable UpdateLivingEvent var1);

   public abstract boolean onPacket(@Nullable PacketEvent var1);

   public abstract void onPlayerMove(@Nullable EventPlayerMove var1);

   public abstract void onInput(@Nullable EventInput var1);

   public abstract void onEnable();

   public abstract void onDisable();

   protected final class_310 getMc() {
      return class_310.method_1551();
   }

   protected final boolean hasVelocityHandle() {
      class_746 var10000 = this.getMc().field_1724;
      boolean var2;
      if (var10000 != null) {
         class_746 local1_0 = var10000;
         var2 = local1_0.field_6235 > 0 && !local1_0.method_5799() && !local1_0.method_5771() && !local1_0.method_6128();
      } else {
         var2 = false;
      }

      return var2;
   }
}
