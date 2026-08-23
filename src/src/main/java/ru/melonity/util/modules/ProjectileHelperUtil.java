package ru.melonity.util.modules;

import com.google.common.collect.Lists;
import java.util.Comparator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_1531;
import net.minecraft.class_1657;
import net.minecraft.class_1753;
import net.minecraft.class_1764;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_4604;
import ru.melonity.fabric.client.model.ILevelRenderer;

@Environment(EnvType.CLIENT)
public final class ProjectileHelperUtil {
   private static final class_310 mc = class_310.method_1551();

   public static ProjectileHelperUtil.ProjectileType getProjectileType(class_1792 item, class_1799 stack) {
      if (item instanceof class_1753) {
         return ProjectileHelperUtil.ProjectileType.BOW;
      } else if (item instanceof class_1764 && class_1764.method_7781(stack)) {
         return ProjectileHelperUtil.ProjectileType.CROSSBOW;
      } else {
         return item == class_1802.field_8547 ? ProjectileHelperUtil.ProjectileType.TRIDENT : null;
      }
   }

   public static ProjectileHelperUtil.ProjectileType getCurrentProjectileType() {
      if (mc.field_1724 == null) {
         return null;
      } else {
         class_1799 local0_1 = mc.field_1724.method_6030();
         if (local0_1 != null && !local0_1.method_7960()) {
            ProjectileHelperUtil.ProjectileType local1_2 = getProjectileType(local0_1.method_7909(), local0_1);
            return local1_2 == ProjectileHelperUtil.ProjectileType.BOW && mc.field_1724.method_6048() < 3 ? null : local1_2;
         } else {
            class_1799 local1_0 = mc.field_1724.method_6047();
            return local1_0.method_7909() instanceof class_1764 && class_1764.method_7781(local1_0) ? ProjectileHelperUtil.ProjectileType.CROSSBOW : null;
         }
      }
   }

   public static float[] calculateAimAngles(class_1309 target, ProjectileHelperUtil.ProjectileType type, boolean predictMovement) {
      if (mc.field_1724 != null && target != null && type != null) {
         class_243 local3_0 = mc.field_1724.method_33571();
         class_243 local4_1 = getTargetPosition(target);
         if (predictMovement) {
            local4_1 = predictTargetPosition(target, local4_1, type);
         }

         double local5_2 = local4_1.field_1352 - local3_0.field_1352;
         double local7_3 = local4_1.field_1351 - local3_0.field_1351;
         double local9_4 = local4_1.field_1350 - local3_0.field_1350;
         double local11_5 = Math.sqrt(local5_2 * local5_2 + local9_4 * local9_4);
         float local13_6 = (float)Math.toDegrees(Math.atan2(-local5_2, local9_4));
         float local14_7 = calculateBallisticPitch(local11_5, local7_3, type);
         return Float.isNaN(local14_7) ? null : new float[]{local13_6, local14_7};
      } else {
         return null;
      }
   }

   public static float[] calculateDelta(class_1309 target, ProjectileHelperUtil.ProjectileType type, boolean predictMovement) {
      float[] local3_0 = calculateAimAngles(target, type, predictMovement);
      if (local3_0 != null && mc.field_1724 != null) {
         float local4_1 = normalizeYaw(local3_0[0] - mc.field_1724.method_36454());
         float local5_2 = local3_0[1] - mc.field_1724.method_36455();
         return new float[]{local4_1, local5_2};
      } else {
         return null;
      }
   }

   public static class_243 predictTargetPosition(class_1309 target, class_243 currentPos, ProjectileHelperUtil.ProjectileType type) {
      class_243 local3_0 = new class_243(
         target.method_23317() - target.field_6014, target.method_23318() - target.field_6036, target.method_23321() - target.field_5969
      );
      double local4_1 = mc.field_1724.method_33571().method_1022(currentPos);
      float local6_2 = getProjectileVelocity(type);
      double local7_3 = local4_1 / (double)(local6_2 * 20.0F);
      return currentPos.method_1019(local3_0.method_1021(local7_3 * 20.0));
   }

   public static float calculateBallisticPitch(double horizontalDist, double dy, ProjectileHelperUtil.ProjectileType type) {
      float local5_0 = getProjectileVelocity(type);
      double local6_1 = 0.006;
      double local8_2 = horizontalDist * horizontalDist * local6_1;
      double local10_3 = dy + local8_2;
      float local12_4 = (float)(-Math.toDegrees(Math.atan2(local10_3, horizontalDist)));
      return clamp(local12_4, -90.0F, 90.0F);
   }

   public static float getProjectileVelocity(ProjectileHelperUtil.ProjectileType type) {
      if (mc.field_1724 == null) {
         return type.velocity;
      } else if (type == ProjectileHelperUtil.ProjectileType.BOW && mc.field_1724.method_6115()) {
         int local1_0 = mc.field_1724.method_6048();
         float local2_1 = class_1753.method_7722(local1_0);
         return local2_1 * 3.0F;
      } else {
         return type.velocity;
      }
   }

   public static class_243 getTargetPosition(class_1309 target) {
      return new class_243(target.method_23317(), target.method_23318() + (double)target.method_17682() * 0.5, target.method_23321());
   }

   public static class_1309 findTarget(float range) {
      if (mc.field_1724 != null && mc.field_1687 != null) {
         class_243 local1_0 = mc.field_1724.method_33571();
         class_243 local2_1 = mc.field_1724.method_5663().method_1029();
         return Lists.newArrayList(mc.field_1687.method_18112())
            .stream()
            .filter(class_1309.class::isInstance)
            .map(class_1309.class::cast)
            .filter(e -> isValidTarget(e, range))
            .min(Comparator.<class_1309>comparingDouble(e -> {
               class_243 local3_0 = e.method_33571().method_1020(local1_0).method_1029();
               return 1.0 - local2_1.method_1026(local3_0);
            }).thenComparingDouble(mc.field_1724::method_5739))
            .orElse(null);
      } else {
         return null;
      }
   }

   public static boolean isValidTarget(class_1309 entity, float range) {
      if (mc.field_1724 == null) {
         return false;
      } else if (entity == mc.field_1724) {
         return false;
      } else if (!entity.method_5805()) {
         return false;
      } else if (entity instanceof class_1531) {
         return false;
      } else if (!(entity instanceof class_1657)) {
         return false;
      } else if (mc.field_1724.method_5739(entity) > range) {
         return false;
      } else {
         if (mc.field_1769 instanceof ILevelRenderer local3_1) {
            class_4604 local4_0 = local3_1.getFrustum();
            if (local4_0 != null && !local4_0.method_23093(entity.method_5829())) {
               return false;
            }
         }

         return true;
      }
   }

   public static float calculateAdaptiveSpeed(float delta, float maxSpeed) {
      float local2_0 = Math.abs(delta);
      if (local2_0 > 45.0F) {
         return maxSpeed * 2.0F;
      } else if (local2_0 > 20.0F) {
         return maxSpeed * 1.5F;
      } else if (local2_0 > 10.0F) {
         return maxSpeed;
      } else {
         return local2_0 > 3.0F ? maxSpeed * 0.6F : maxSpeed * 0.3F;
      }
   }

   public static float normalizeYaw(float yaw) {
      while (yaw > 180.0F) {
         yaw -= 360.0F;
      }

      while (yaw < -180.0F) {
         yaw += 360.0F;
      }

      return yaw;
   }

   public static float clamp(float value, float min, float max) {
      return Math.max(min, Math.min(max, value));
   }

   @Environment(EnvType.CLIENT)
   public static enum ProjectileType {
      BOW(3.0F, 0.05F),
      CROSSBOW(3.15F, 0.05F),
      TRIDENT(2.5F, 0.05F);

      public final float velocity;
      public final float gravity;

      private ProjectileType(float velocity, float gravity) {
         this.velocity = velocity;
         this.gravity = gravity;
      }
   }
}
