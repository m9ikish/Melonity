package ru.melonity.feature.rotation;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import org.joml.Vector2f;

@Environment(EnvType.CLIENT)
public class Rotation {
   private float yaw;
   private float pitch;
   private static final class_310 mc = class_310.method_1551();

   public Rotation(class_1297 entity) {
      this.yaw = entity.method_36454();
      this.pitch = entity.method_36455();
   }

   public static Vector2f camera() {
      return new Vector2f(cameraYaw(), cameraPitch());
   }

   public static float cameraYaw() {
      return class_3532.method_15393(mc.field_1773.method_19418().method_19330() + (float)(mc.field_1773.method_19418().method_19333() ? 180 : 0));
   }

   public static float cameraPitch() {
      return (float)(mc.field_1773.method_19418().method_19333() ? -1 : 1) * mc.field_1773.method_19418().method_19329();
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
   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   @Generated
   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   @Generated
   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Rotation local2_0)) {
         return false;
      } else if (!local2_0.canEqual(this)) {
         return false;
      } else {
         return Float.compare(this.getYaw(), local2_0.getYaw()) != 0 ? false : Float.compare(this.getPitch(), local2_0.getPitch()) == 0;
      }
   }

   @Generated
   protected boolean canEqual(Object other) {
      return other instanceof Rotation;
   }

   @Generated
   @Override
   public int hashCode() {
      int local2_0 = 1;
      local2_0 = local2_0 * 59 + Float.floatToIntBits(this.getYaw());
      return local2_0 * 59 + Float.floatToIntBits(this.getPitch());
   }

   @Generated
   @Override
   public String toString() {
      return "Rotation(yaw=" + this.getYaw() + ", pitch=" + this.getPitch() + ")";
   }

   @Generated
   public Rotation() {
   }

   @Generated
   public Rotation(float yaw, float pitch) {
      this.yaw = yaw;
      this.pitch = pitch;
   }
}
