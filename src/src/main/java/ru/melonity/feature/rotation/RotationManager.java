package ru.melonity.feature.rotation;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_746;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.feature.features.modules.player.FreeCamFeature;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.EventRelativeMove;
import ru.melonity.util.event.events.RenderGameWorldEvent;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.minecraft.MovementUtil;

@Environment(EnvType.CLIENT)
public class RotationManager {
   public static RotationManager INSTANCE;
   public static boolean aiming = false;
   private float yaw;
   private float pitch;
   private float visualYaw;
   private float visualPitch;
   private float targetYaw;
   private float targetPitch;
   private boolean inInventory;
   private boolean rotating = false;
   private RotationManager.Task task = RotationManager.Task.RESET;
   public RotationManager.MoveFix moveFix;
   private final EventListener<UpdateEvent> onUpdate = event -> this.process();
   private final EventListener<RenderGameWorldEvent> onRender = event -> {
      FreeCamFeature local2_0 = Melonity.INSTANCE.featureManager.freeCamFeature;
      if (this.task == RotationManager.Task.AIM) {
         this.visualYaw = class_3532.method_16439(Feature.mc.method_60646().method_60637(true), this.visualYaw, this.yaw);
         this.visualPitch = class_3532.method_16439(Feature.mc.method_60646().method_60637(true), this.visualPitch, this.pitch);
      } else {
         this.visualYaw = this.yaw;
         this.visualPitch = this.pitch;
      }

      if (local2_0.isEnabled()) {
         this.visualYaw = local2_0.yaw;
         this.visualPitch = local2_0.pitch;
      }
   };
   public final EventListener<EventRelativeMove> onRelative = event -> {
      if (!Melonity.INSTANCE.featureManager.auraFeature.isEnabled()
         || Melonity.INSTANCE.featureManager.auraFeature.getTarget() == null
         || !Melonity.INSTANCE.featureManager.auraFeature.getCorrectMovementType().contains("None")) {
         if (this.rotating) {
            event.velocity = MovementUtil.fix(this.yaw, event.movementInput, event.speed);
            event.cancel();
         }
      }
   };
   public final EventListener<EventInput> onInput = event -> {
      if (this.rotating && this.moveFix == RotationManager.MoveFix.SILENT) {
         MovementUtil.fixMovement(event, this.yaw);
      }
   };

   public RotationManager() {
      INSTANCE = this;
   }

   /**
    * Tick handler for the server side rotation, driven by {@link #onUpdate}.
    *
    * <p>A rotation request lives for exactly one tick: {@link #rotate(float, float)} only records
    * the target, this method publishes it into {@link #yaw}/{@link #pitch} on the next tick and
    * immediately drops the task again. Features that want to keep aiming call {@code rotate} every
    * tick (Surround, PearlTarget, Spider, FreeCam, ClanUpgrade, AutoWindCharge, AutoSave, ...); as
    * soon as one of them stops - and several of them never call {@link #reset()} at all - control
    * is handed straight back to the player instead of leaving the server rotation stuck.
    *
    * <p>The one tick of delay is part of the contract: {@code PearlTargetFeature} calls
    * {@code rotate(yaw, pitch)} and only fires once {@code getYaw() == yaw && getPitch() == pitch},
    * which is only ever true if the target is copied verbatim (no smoothing) and applied a tick
    * later.
    */
   private void process() {
      class_746 player = Feature.mc.field_1724;
      if (player == null) {
         this.task = RotationManager.Task.RESET;
         this.rotating = false;
         aiming = false;
         return;
      }

      if (this.task == RotationManager.Task.AIM) {
         this.yaw = this.targetYaw;
         this.pitch = class_3532.method_15363(this.targetPitch, -90.0F, 90.0F);
         this.rotating = true;
         aiming = true;
         this.moveFix = RotationManager.MoveFix.SILENT;
         this.task = RotationManager.Task.RESET;
      } else {
         this.yaw = player.method_36454();
         this.pitch = player.method_36455();
         this.rotating = false;
         aiming = false;
      }
   }

   public void rotate(float yawTo, float pitchTo) {
      this.targetYaw = yawTo;
      this.targetPitch = class_3532.method_15363(pitchTo, -90.0F, 90.0F);
      this.task = RotationManager.Task.AIM;
      aiming = true;
   }

   public void reset() {
      this.task = RotationManager.Task.RESET;
   }

   public final class_243 toVector() {
      float local1_0 = this.pitch * MathUtil.deg2rad;
      float local2_1 = -this.yaw * MathUtil.deg2rad;
      float local3_2 = class_3532.method_15362(local2_1);
      float local4_3 = class_3532.method_15374(local2_1);
      float local5_4 = class_3532.method_15362(local1_0);
      float local6_5 = class_3532.method_15374(local1_0);
      return new class_243((double)(local4_3 * local5_4), (double)(-local6_5), (double)(local3_2 * local5_4));
   }

   @Generated
   public float getYaw() {
      return this.yaw;
   }

   @Generated
   public float getPitch() {
      return this.pitch;
   }

   @Generated
   public float getVisualYaw() {
      return this.visualYaw;
   }

   @Generated
   public float getVisualPitch() {
      return this.visualPitch;
   }

   @Generated
   public float getTargetYaw() {
      return this.targetYaw;
   }

   @Generated
   public float getTargetPitch() {
      return this.targetPitch;
   }

   @Generated
   public boolean isRotating() {
      return this.rotating;
   }

   @Generated
   public RotationManager.Task getTask() {
      return this.task;
   }

   @Generated
   public RotationManager.MoveFix getMoveFix() {
      return this.moveFix;
   }

   @Generated
   public EventListener<UpdateEvent> getOnUpdate() {
      return this.onUpdate;
   }

   @Generated
   public EventListener<RenderGameWorldEvent> getOnRender() {
      return this.onRender;
   }

   @Generated
   public EventListener<EventRelativeMove> getOnRelative() {
      return this.onRelative;
   }

   @Generated
   public EventListener<EventInput> getOnInput() {
      return this.onInput;
   }

   @Generated
   public boolean isInInventory() {
      return this.inInventory;
   }

   @Generated
   public void setInInventory(boolean inInventory) {
      this.inInventory = inInventory;
   }

   @Generated
   public void setTask(RotationManager.Task task) {
      this.task = task;
   }

   @Generated
   public void setMoveFix(RotationManager.MoveFix moveFix) {
      this.moveFix = moveFix;
   }

   @Environment(EnvType.CLIENT)
   public static enum MoveFix {
      FULL,
      SILENT;
   }

   @Environment(EnvType.CLIENT)
   public static enum Task {
      AIM,
      RESET;
   }
}
