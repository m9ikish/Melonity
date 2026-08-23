package ru.melonity.feature.features.modules.movement.velocity.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_241;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2664;
import net.minecraft.class_2743;
import net.minecraft.class_3532;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.feature.features.modules.movement.velocity.VelocityMode;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.EventPlayerMove;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005J\u0019\u0010\t\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016¢\u0006\u0004\b\t\u0010\nJ\u0019\u0010\r\u001a\u00020\f2\b\u0010\u0007\u001a\u0004\u0018\u00010\u000bH\u0017¢\u0006\u0004\b\r\u0010\u000eJ\u0019\u0010\u0010\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u000fH\u0016¢\u0006\u0004\b\u0010\u0010\u0011J\u0019\u0010\u0013\u001a\u00020\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0012H\u0017¢\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0015\u001a\u00020\bH\u0016¢\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0017\u001a\u00020\bH\u0016¢\u0006\u0004\b\u0017\u0010\u0016J\u0017\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00020\u0018H\u0002¢\u0006\u0004\b\u001b\u0010\u001cJ!\u0010\u001b\u001a\u00020\u001a2\b\u0010\u001d\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u0018H\u0002¢\u0006\u0004\b\u001b\u0010\u001eR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0003\u0010\u001fR\u0016\u0010 \u001a\u00020\u00188\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b \u0010!¨\u0006\""},
   d2 = {"Lru/melonity/feature/features/modules/movement/velocity/impl/CompensationMode;", "Lru/melonity/feature/features/modules/movement/velocity/VelocityMode;", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "compensationMode", "<init>", "(Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;)V", "Lru/melonity/util/event/events/UpdateLivingEvent;", "event", "", "onUpdate", "(Lru/melonity/util/event/events/UpdateLivingEvent;)V", "Lru/melonity/util/event/events/PacketEvent;", "", "onPacket", "(Lru/melonity/util/event/events/PacketEvent;)Z", "Lru/melonity/util/event/events/EventPlayerMove;", "onPlayerMove", "(Lru/melonity/util/event/events/EventPlayerMove;)V", "Lru/melonity/util/event/events/EventInput;", "onInput", "(Lru/melonity/util/event/events/EventInput;)V", "onEnable", "()V", "onDisable", "Lnet/minecraft/class_243;", "to", "Lnet/minecraft/class_241;", "calculate", "(Lnet/minecraft/class_243;)Lnet/minecraft/class_241;", "from", "(Lnet/minecraft/class_243;Lnet/minecraft/class_243;)Lnet/minecraft/class_241;", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "lastMotion", "Lnet/minecraft/class_243;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class CompensationMode extends VelocityMode {
   @NotNull
   private final ListSetting compensationMode;
   @NotNull
   private class_243 lastMotion;

   public CompensationMode(@NotNull ListSetting compensationMode) {
      super("novelocity.compensation");
      Intrinsics.checkNotNullParameter(compensationMode, "compensationMode");
      this.compensationMode = compensationMode;
      this.lastMotion = new class_243(0.0, 0.0, 0.0);
   }

   @Override
   public void onUpdate(@Nullable UpdateLivingEvent event) {
   }

   @Override
   public boolean onPacket(@Nullable PacketEvent event) {
      if (event == null || !event.isReceive()) {
         return false;
      }

      class_746 player = this.getMc().field_1724;
      if (player == null) {
         return false;
      }

      class_2596<?> packet = event.packet;
      class_243 impulse;
      if (packet instanceof class_2743) {
         class_2743 knockback = (class_2743)packet;
         if (knockback.method_11818() != player.method_5628()) {
            return false;
         }

         impulse = new class_243(knockback.method_11815(), knockback.method_11816(), knockback.method_11819());
      } else if (packet instanceof class_2664) {
         class_2664 explosion = (class_2664)packet;
         impulse = player.method_18798()
            .method_1031((double)explosion.method_11472(), (double)explosion.method_11473(), (double)explosion.method_11474());
      } else {
         return false;
      }

      // Remembered for onInput(), which answers the impulse with movement instead of
      // with a dropped packet.
      this.lastMotion = impulse;
      if (!this.compensationMode.contains("novelocity.compensation.all") && !this.compensationMode.contains("novelocity.compensation.default")) {
         // "Jump reset" and "Directional" take the hit as it is and fight it afterwards.
         return false;
      }

      // Take the impulse, but only a part of it. A dropped packet is what gets flagged,
      // a damped one still moves the player in the direction the server expects.
      player.method_18800(impulse.field_1352 * 0.6, impulse.field_1351 * 0.85, impulse.field_1350 * 0.6);
      return true;
   }

   @Override
   public void onPlayerMove(@Nullable EventPlayerMove event) {
   }

   @Override
   public void onInput(@Nullable EventInput event) {
      if (event == null || !this.hasVelocityHandle()) {
         return;
      }

      class_746 player = this.getMc().field_1724;
      if (player == null) {
         return;
      }

      boolean everything = this.compensationMode.contains("novelocity.compensation.all");
      boolean jumpReset = everything || this.compensationMode.contains("novelocity.compensation.jump");
      boolean directional = everything || this.compensationMode.contains("novelocity.compensation.directional");
      if (!jumpReset && !directional) {
         return;
      }

      // NoVelocityFeature re-selects the mode before every event and that runs onEnable(),
      // which wipes lastMotion. The velocity the impulse just wrote is the same vector,
      // so it is used whenever the remembered one has been cleared.
      class_243 push = new class_243(this.lastMotion.field_1352, 0.0, this.lastMotion.field_1350);
      if (push.method_1027() < 1.0E-4) {
         class_243 velocity = player.method_18798();
         push = new class_243(velocity.field_1352, 0.0, velocity.field_1350);
      }

      if (push.method_1027() < 1.0E-4) {
         return;
      }

      if (jumpReset && player.method_24828()) {
         // Leaving the ground on the hit tick cuts the ground friction phase out of the
         // knockback, which is what a hand played jump reset does.
         event.setJump(true);
      }

      if (directional && (event.getForward() != 0.0F || event.getStrafe() != 0.0F)) {
         // Point the movement input straight against the impulse: the eye level target is
         // two blocks upwind of the player, calculate() turns it into a yaw and setYaw()
         // picks the forward/strafe pair that walks there from the real head rotation.
         class_243 eyes = player.method_19538().method_1031(0.0, (double)player.method_18381(player.method_18376()), 0.0);
         class_243 against = eyes.method_1020(push.method_1029().method_1021(2.0));
         float counterYaw = this.calculate(against).field_1343;
         event.setForward(1.0F);
         event.setStrafe(0.0F);
         event.setYaw(player.method_36454(), counterYaw);
      }
   }

   @Override
   public void onEnable() {
      this.lastMotion = new class_243(0.0, 0.0, 0.0);
   }

   @Override
   public void onDisable() {
      this.lastMotion = new class_243(0.0, 0.0, 0.0);
   }

   private final class_241 calculate(class_243 to) {
      class_746 var10000 = this.getMc().field_1724;
      if (var10000 == null) {
         return new class_241(0.0F, 0.0F);
      } else {
         class_746 local2_0 = var10000;
         class_243 local3_1 = local2_0.method_19538().method_1031(0.0, (double)local2_0.method_18381(local2_0.method_18376()), 0.0);
         class_243 local4_2 = new class_243(local3_1.field_1352, local3_1.field_1351, local3_1.field_1350);
         return this.calculate(local4_2, to);
      }
   }

   private final class_241 calculate(class_243 from, class_243 to) {
      class_243 local3_0 = to.method_1020(from);
      double local4_1 = Math.hypot(local3_0.field_1352, local3_0.field_1350);
      float local6_2 = (float)(class_3532.method_15349(local3_0.field_1350, local3_0.field_1352) * (double)180.0F / Math.PI) - 90.0F;
      float local7_3 = (float)(-(class_3532.method_15349(local3_0.field_1351, local4_1) * (double)180.0F / Math.PI));
      return new class_241(local6_2, local7_3);
   }
}
