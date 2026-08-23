package ru.melonity.feature.features.modules.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_638;
import net.minecraft.class_7833;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.melonity.feature.Feature;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.AttackEvent;
import ru.melonity.util.event.events.RenderWorldPassEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.render.shader.Uniform;
import ru.melonity.util.render.texture.WavefrontTexture;
import ru.melonity.util.render.wavefront.Model;
import ru.melonity.util.render.wavefront.Wavefront;

@Environment(EnvType.CLIENT)
public class DamageParticlesFeature extends SimpleFeature {
   private final ListSetting pack = new ListSetting("Pack", ListSetting.ModeType.SINGLE, "MelonityVerse", "MelonityVerse", "Purple", "Swear words", "Weapons");
   private static final float SPIN_WEIGHT = 30.0F;
   private static final float HALF_SPIN_WEIGHT = 15.0F;
   private static final List<Model> BRANDS = List.of(
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_mc.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/mc_particle.png")
      ),
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_mlt.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/mlt_particle.png")
      ),
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_rmpg.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/rmpg_particle.png")
      )
   );
   private static final List<Model> PURPLE = List.of(
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_heart.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/particle_regular.png")
      ),
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_morgenstern.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/particle_regular.png")
      ),
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_star.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/particle_regular.png")
      )
   );
   private static final List<Model> SWEAR = List.of(
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_exclamation.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/particle_swearwords.png")
      ),
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_hash.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/particle_swearwords.png")
      ),
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_question.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/particle_swearwords.png")
      ),
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_questionexsclam.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/particle_swearwords.png")
      ),
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_strongword.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/particle_swearwords.png")
      )
   );
   private static final List<Model> WEAPONS = List.of(
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_arrow.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/Particles_Weapon.png")
      ),
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_spear.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/Particles_Weapon.png")
      ),
      new Model(
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/models/particle_sword.obj"),
         DamageParticlesFeature.class.getResourceAsStream("/assets/minecraft/melonity/images/models/Particles_Weapon.png")
      )
   );
   private final List<DamageParticlesFeature.Particle> particles = Collections.synchronizedList(new ArrayList<>());
   private Wavefront model;
   private WavefrontTexture texture;
   int previousTexture;
   private int previousSlot = -1;
   private class_638 particleWorld;
   private boolean reportedTickFailure;
   private boolean reportedSpawnFailure;
   private final EventListener<RenderWorldPassEvent> onWorldPass = event -> this.drawParticles(event.partialTicks(), new Matrix4f(event.modelView()));
   private final EventListener<UpdateLivingEvent> onUpdate = event -> this.updateParticles();
   private final EventListener<AttackEvent> onAttack = event -> {
      if (mc.field_1724.method_5805()) {
         class_1297 local2_2 = event.target();
         if (local2_2 instanceof class_1309 local3_1) {
            if (local2_2 == mc.field_1724) {
               return;
            }

            for (int local4_0 = 0; local4_0 < 12; local4_0++) {
               this.createParticle(
                  local3_1.method_23317(), local3_1.method_23318() + (double)local3_1.method_18381(local3_1.method_18376()), local3_1.method_23321()
               );
            }
         }
      }
   };

   public DamageParticlesFeature() {
      super("3D Particles", MenuCategory.RENDER);
      this.setting(this.pack);
   }

   // NOTE: this method is `private native void drawParticles(float, Matrix4f)` in o.jar - the
   // obfuscator stripped the body, so nothing here is verifiable against the original bytecode. The
   // body is reconstructed from call-site evidence and from TargetESPFeature, the sibling that
   // drives the same shader off the same event. Do not cite it as ground truth.
   private void drawParticles(float partialTick, Matrix4f modelView2) {
      this.discardStaleParticles();
      if (!this.particles.isEmpty()) {
         List<DamageParticlesFeature.Particle> sortedParticles = new ArrayList<>(this.particles);
         sortedParticles.sort(Comparator.naturalOrder());
         // Every piece of GL state this method touches has to be saved and put back. Previously the
         // depth test and cull face were restored to a hard-coded `true` and the depth mask, VAO,
         // active texture unit and 2D binding were not restored at all, which left GlStateManager's
         // caches disagreeing with the driver for the rest of the frame. Order and set of saved
         // state match TargetESPFeature, which drives the same shader off the same event.
         int previousTextureUnit = GL30.glGetInteger(34016);
         int previousTexture2d = GL30.glGetInteger(32873);
         boolean previousDepthMask = GL20.glGetBoolean(2930);
         boolean previousDepthTest = GL20.glIsEnabled(2929);
         boolean previousCullFace = GL20.glIsEnabled(2884);
         int previousVertexArray = GL20.glGetInteger(34229);
         // Particle.draw translates by absolute world coordinates but the event matrix is camera
         // relative, so the camera position has to be subtracted once up front - without it every
         // particle is drawn thousands of blocks off and the feature looks like it does nothing.
         Matrix4f cameraRelative = new Matrix4f(modelView2);
         class_243 camera = mc.field_1773.method_19418().method_19326();
         cameraRelative.translate((float)(-camera.field_1352), (float)(-camera.field_1351), (float)(-camera.field_1350));
         state(2929, true);
         state(2884, false);
         GL20.glDepthMask(true);
         TargetESPFeature.coreShader.bind();
         this.resetTextureCache();

         for (DamageParticlesFeature.Particle particle : sortedParticles) {
            particle.draw(TargetESPFeature.coreShaderModelViewUniform, this::cachedBindToSlot, partialTick, new Matrix4f(cameraRelative), true);
         }

         TargetESPFeature.coreShader.unbind();
         GL20.glDepthMask(previousDepthMask);
         state(2929, previousDepthTest);
         state(2884, previousCullFace);
         GL30.glBindVertexArray(previousVertexArray);
         GL30.glActiveTexture(previousTextureUnit);
         GL30.glBindTexture(3553, previousTexture2d);
      }
   }

   public static void state(int name, boolean value) {
      if (value) {
         GL20.glEnable(name);
      } else {
         GL20.glDisable(name);
      }
   }

   // The bind that this cache is meant to skip was never recorded - nothing ever wrote
   // previousTexture outside resetTextureCache() - and glGenTextures never returns 0, so the test
   // was always true and every particle rebound its texture. Record the bind so the sort by texture
   // in drawParticles actually batches. All call sites bind slot 0, but key on the slot too so a
   // future non-zero slot cannot be skipped on a cache hit.
   private void cachedBindToSlot(WavefrontTexture texture, int slot) {
      int name = texture.texture();
      if (name != this.previousTexture || slot != this.previousSlot) {
         texture.bindToSlot(slot);
         this.previousTexture = name;
         this.previousSlot = slot;
      }
   }

   private void updateParticles() {
      this.discardStaleParticles();
      this.particles.removeIf(DamageParticlesFeature.Particle::isDead);

      // The whole loop used to sit inside an empty catch (Throwable): one failure - an NPE from the
      // world raycast during a dimension change, say - silently skipped every remaining particle
      // for that tick, and since isDead() is a pure function of `tick` a repeating failure froze
      // them in mid-air forever. Guard each particle instead, drop the one that threw, and report
      // the first failure once (this runs at tick rate, so an unguarded log would flood the file).
      for (Iterator<DamageParticlesFeature.Particle> iterator = this.particles.iterator(); iterator.hasNext();) {
         DamageParticlesFeature.Particle local2_0 = iterator.next();

         try {
            local2_0.tick();
         } catch (Throwable var3) {
            iterator.remove();
            if (!this.reportedTickFailure) {
               this.reportedTickFailure = true;
               System.err.println("[Melonity] 3D Particles: particle tick failed, particle dropped (further failures suppressed):");
               var3.printStackTrace();
            }
         }
      }
   }

   private void createParticle(double x, double y, double z) {
      // The catch stays wide on purpose: it also covers the static initialisation of the model
      // lists, and letting that escape would break the attack path itself. What it must not do is
      // stay silent - a bad pack value or a failed model upload used to make the whole feature a
      // no-op with nothing in the log. This is called 12 times per hit, so report only once.
      try {
         this.particles
            .add(
               new DamageParticlesFeature.Particle(
                  this.randomModel(),
                  x,
                  y,
                  z,
                  this.random() * SPIN_WEIGHT,
                  this.random() * HALF_SPIN_WEIGHT,
                  this.random() * SPIN_WEIGHT,
                  this.random() * 360.0F,
                  -90.0F + 180.0F * this.random()
               )
            );
         this.particles.sort(Comparator.naturalOrder());
      } catch (Throwable var8) {
         if (!this.reportedSpawnFailure) {
            this.reportedSpawnFailure = true;
            System.err.println("[Melonity] 3D Particles: failed to spawn a particle for pack " + this.pack.getMode() + " (further failures suppressed):");
            var8.printStackTrace();
         }
      }
   }

   // Particles keep absolute world coordinates and Particle.tick() raycasts against the current
   // world, so a batch that survives a dimension change or a reconnect would be drawn at meaningless
   // coordinates and bounce off unrelated terrain. Drop the batch when the world instance changes.
   private void discardStaleParticles() {
      class_638 world = mc.field_1687;
      if (world != this.particleWorld) {
         this.particleWorld = world;
         this.particles.clear();
      }
   }

   @Override
   public void onDisable() {
      // Event dispatch is gated on isEnabled(), so toggling the feature off mid-burst left the live
      // particles in the list with their tick frozen; re-enabling resumed them from that stale tick
      // at coordinates captured before the toggle. Drop them on the way out.
      this.particles.clear();
   }

   private void resetTextureCache() {
      this.previousTexture = 0;
      this.previousSlot = -1;
   }

   private float random() {
      return (float)Math.random();
   }

   private Model randomModel() {
      // The switch had no default arm and the mode list was read with a bare get(0): a pack value
      // that is not one of the four literals left the list null and NPEd on every spawn, and an
      // empty list threw IndexOutOfBounds - both reachable from a hand-edited config, and both
      // previously hidden by the empty catch in createParticle. Fall back to the setting's default.
      List<String> local1_1 = this.pack.getMode();
      String var2 = local1_1.isEmpty() ? "MelonityVerse" : local1_1.get(0);
      List<Model> local1_0;
      switch (var2) {
         case "Purple":
            local1_0 = PURPLE;
            break;
         case "Swear words":
            local1_0 = SWEAR;
            break;
         case "Weapons":
            local1_0 = WEAPONS;
            break;
         case "MelonityVerse":
         default:
            local1_0 = BRANDS;
      }

      return local1_0.get(ThreadLocalRandom.current().nextInt(local1_0.size()));
   }

   @Environment(EnvType.CLIENT)
   private static class Particle implements Comparable<DamageParticlesFeature.Particle> {
      private static final int LIFE_TIME = 20;
      private static final int EASING_TIME = 10;
      private static final int EASING_BARRIER = 10;
      private static final float VELOCITY_PER_TICK_MULTIPLIER = 0.75F;
      double previousX;
      double previousY;
      double previousZ;
      double x;
      double y;
      double z;
      double velocityX;
      double velocityY;
      double velocityZ;
      final Wavefront model;
      final WavefrontTexture texture;
      int tick;
      final float yawWeight;
      final float pitchWeight;
      final float rollWeight;
      float previousYaw;
      float previousPitch;
      float previousRoll;
      float yaw;
      float pitch;
      float roll;
      final float deg2Rad = (float) (Math.PI / 180.0);

      public Particle(Model model, double x, double y, double z, float yawWeight, float pitchWeight, float rollWeight, float yaw, float pitch) {
         this.model = model.wavefront();
         this.texture = model.texture();
         this.computeOrigin(x, y, z);
         this.computeVelocity(yaw, pitch, this.computeInitialSpeed());
         this.yaw = yaw;
         this.pitch = pitch;
         this.yawWeight = yawWeight;
         this.pitchWeight = pitchWeight;
         this.rollWeight = rollWeight;
      }

      public void draw(Uniform matrixUniform, BiConsumer<WavefrontTexture, Integer> bindToSlot, float partialTick, Matrix4f modelView, boolean rotate) {
         double local6_0 = this.interpolate(this.previousX, this.x, (double)partialTick);
         double local8_1 = this.interpolate(this.previousY, this.y, (double)partialTick);
         double local10_2 = this.interpolate(this.previousZ, this.z, (double)partialTick);
         float local12_3 = this.computeSize(partialTick);
         modelView.translate((float)local6_0, (float)local8_1, (float)local10_2);
         if (rotate) {
            modelView.rotate(class_7833.field_40714.rotationDegrees(this.interpolate(this.previousPitch, this.pitch, partialTick)));
            modelView.rotate(class_7833.field_40716.rotationDegrees(this.interpolate(this.previousYaw, this.yaw, partialTick)));
            modelView.rotate(class_7833.field_40718.rotationDegrees(this.interpolate(this.previousRoll, this.roll, partialTick)));
         }

         modelView.scale(local12_3, local12_3, local12_3);
         matrixUniform.uploadMatrix4f(modelView);
         TargetESPFeature.coreShaderMixIUniform.uploadFloat(0.0F);
         bindToSlot.accept(this.texture, 0);
         this.model.drawAllParts();
      }

      public void tick() {
         this.capturePreviousPosition();
         this.capturePreviousRotation();
         this.velocityX *= VELOCITY_PER_TICK_MULTIPLIER;
         this.velocityY *= VELOCITY_PER_TICK_MULTIPLIER;
         this.velocityZ *= VELOCITY_PER_TICK_MULTIPLIER;
         this.yaw = this.yaw + this.yawWeight;
         this.pitch = this.pitch + this.pitchWeight;
         this.roll = this.roll + this.rollWeight;
         double local1_6 = this.x + this.velocityX;
         double local3_7 = this.y + this.velocityY;
         double local5_8 = this.z + this.velocityZ;
         class_3965 local7_9 = class_310.method_1551()
            .field_1687
            .method_17742(
               new class_3959(
                  new class_243(this.x, this.y, this.z),
                  new class_243(local1_6, local3_7, local5_8),
                  class_3960.field_17558,
                  class_242.field_1348,
                  Feature.mc.field_1724
               )
            );
         if (local7_9.method_17783() != class_240.field_1333) {
            float local8_0 = this.computeReflectSpeedMultiplier();
            class_2382 local9_1 = local7_9.method_17780().method_10163();
            Vector3f local10_2 = new Vector3f((float)local9_1.method_10263(), (float)local9_1.method_10264(), (float)local9_1.method_10260());
            Vector3f local11_3 = new Vector3f((float)this.velocityX, (float)this.velocityY, (float)this.velocityZ);
            Vector3f local12_4 = local11_3.reflect(local10_2).mul(local8_0);
            this.velocityX = (double)local12_4.x;
            this.velocityY = (double)local12_4.y;
            this.velocityZ = (double)local12_4.z;
            class_243 local13_5 = local7_9.method_17784();
            local1_6 = local13_5.field_1352;
            local3_7 = local13_5.field_1351;
            local5_8 = local13_5.field_1350;
         }

         this.x = local1_6;
         this.y = local3_7;
         this.z = local5_8;
         this.tick++;
      }

      public boolean isDead() {
         return this.computeSize(1.0F) <= 0.0F;
      }

      protected void computeOrigin(double x, double y, double z) {
         this.x = x;
         this.y = y;
         this.z = z;
         this.previousX = x;
         this.previousY = y;
         this.previousZ = z;
      }

      protected float computeReflectSpeedMultiplier() {
         return 1.0F;
      }

      protected float computeInitialSpeed() {
         return 1.0F;
      }

      protected float computeSize(float partialTick) {
         if (this.tick > EASING_BARRIER) {
            float local2_0 = this.easing(this.tick, partialTick);
            float local3_1 = Math.min(this.easing(this.tick - 1, partialTick), 1.0F);
            return this.animate(this.interpolate(local3_1, local2_0, partialTick)) * 1.0F;
         } else {
            return 1.0F;
         }
      }

      private double interpolate(double from, double to, double value) {
         return from + (to - from) * value;
      }

      private float interpolate(float from, float to, float value) {
         return from + (to - from) * value;
      }

      private float easing(int easingTick, float partialTick) {
         return ((float)LIFE_TIME - (float)easingTick) / (float)EASING_TIME;
      }

      private float animate(float x) {
         float local2_0 = 1.70158F;
         float local3_1 = local2_0 + 1.0F;
         return (float)(1.0 + (double)local3_1 * Math.pow((double)(x - 1.0F), 3.0) + (double)local2_0 * Math.pow((double)(x - 1.0F), 2.0));
      }

      protected void computeVelocity(float yaw, float pitch, float speed) {
         float local4_0 = (float) (Math.PI / 180.0) * yaw + (float) (Math.PI / 2);
         float local5_1 = (float) (Math.PI / 180.0) * pitch;
         float local6_2 = class_3532.method_15362(local5_1);
         this.velocityX = (double)(class_3532.method_15362(local4_0) * local6_2 * speed);
         this.velocityZ = (double)(class_3532.method_15374(local4_0) * local6_2 * speed);
         this.velocityY = (double)(-class_3532.method_15374(local5_1) * speed);
      }

      private void capturePreviousPosition() {
         this.previousX = this.x;
         this.previousY = this.y;
         this.previousZ = this.z;
      }

      private void capturePreviousRotation() {
         this.previousYaw = this.yaw;
         this.previousPitch = this.pitch;
         this.previousRoll = this.roll;
      }

      public int compareTo(DamageParticlesFeature.Particle o) {
         return Integer.compare(o.texture.hashCode(), this.texture.hashCode());
      }
   }
}
