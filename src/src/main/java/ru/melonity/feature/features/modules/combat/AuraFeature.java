package ru.melonity.feature.features.modules.combat;

import java.security.SecureRandom;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_2848;
import net.minecraft.class_3532;
import net.minecraft.class_746;
import net.minecraft.class_2848.class_2849;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.ILocalPlayer;
import ru.melonity.feature.features.modules.combat.aura.PointSearch;
import ru.melonity.feature.features.modules.combat.aura.PointSearchResult;
import ru.melonity.feature.features.modules.combat.aura.SearchConditions;
import ru.melonity.feature.features.modules.combat.aura.TargetSelection;
import ru.melonity.feature.features.modules.render.HudFeature;
import ru.melonity.feature.features.modules.render.hud.DynamicIslandView;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.PreMotionEvent;
import ru.melonity.util.event.events.StrafeEvent;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.minecraft.RayTraceUtil;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001`B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\r\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0007H\u0016¢\u0006\u0004\b\n\u0010\u000bJ\r\u0010\f\u001a\u00020\t¢\u0006\u0004\b\f\u0010\u0003J\u000f\u0010\r\u001a\u00020\tH\u0003¢\u0006\u0004\b\r\u0010\u0003J\u000f\u0010\u000e\u001a\u00020\tH\u0003¢\u0006\u0004\b\u000e\u0010\u0003J'\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0012H\u0003¢\u0006\u0004\b\u0014\u0010\u0015J1\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00122\b\b\u0002\u0010\u001a\u001a\u00020\u0007H\u0003¢\u0006\u0004\b\u001b\u0010\u001cJ\u0017\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u0012H\u0003¢\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010 \u001a\u00020\tH\u0007¢\u0006\u0004\b \u0010\u0003J\u000f\u0010!\u001a\u00020\tH\u0007¢\u0006\u0004\b!\u0010\u0003J\u000f\u0010\"\u001a\u00020\u0007H\u0007¢\u0006\u0004\b\"\u0010#J\u000f\u0010$\u001a\u00020\u0007H\u0002¢\u0006\u0004\b$\u0010#J\u000f\u0010%\u001a\u00020\tH\u0002¢\u0006\u0004\b%\u0010\u0003R\u0014\u0010 \u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b \u0010&R\u0014\u0010'\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b'\u0010&R\u0014\u0010(\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b(\u0010&R\u0014\u0010)\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b)\u0010&R\u0017\u0010+\u001a\u00020*8\u0006¢\u0006\f\n\u0004\b+\u0010,\u001a\u0004\b-\u0010.R\u0014\u0010/\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b/\u0010&R\u0014\u00101\u001a\u0002008\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b1\u00102R\u0014\u00103\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b3\u0010&R\u0014\u00104\u001a\u0002008\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b4\u00102R\u0014\u00105\u001a\u00020\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b5\u0010&R\u0014\u00106\u001a\u0002008\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b6\u00102R$\u0010\u0011\u001a\u0004\u0018\u0001078\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b\u0011\u00108\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u0018\u0010=\u001a\u0004\u0018\u00010\u000f8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b=\u0010>R\u0016\u0010@\u001a\u00020?8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b@\u0010AR\u0016\u0010B\u001a\u00020?8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bB\u0010AR\u0016\u0010C\u001a\u00020\u00078\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bC\u0010DR\u0014\u0010F\u001a\u00020E8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bF\u0010GR&\u0010I\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u00120H8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bI\u0010JR\u0016\u0010K\u001a\u00020\u00078\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bK\u0010DR\u0016\u0010M\u001a\u00020L8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bM\u0010NR\u0016\u0010P\u001a\u00020O8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bP\u0010QR\u0016\u0010R\u001a\u00020L8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bR\u0010NR\u0016\u0010S\u001a\u00020\u00078\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bS\u0010DR(\u0010W\u001a\u0010\u0012\f\u0012\n V*\u0004\u0018\u00010U0U0T8\u0002X\u0083\u0004¢\u0006\f\n\u0004\bW\u0010X\u0012\u0004\bY\u0010\u0003R\"\u0010[\u001a\u0010\u0012\f\u0012\n V*\u0004\u0018\u00010Z0Z0T8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b[\u0010XR\"\u0010]\u001a\u0010\u0012\f\u0012\n V*\u0004\u0018\u00010\\0\\0T8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b]\u0010XR\"\u0010_\u001a\u0010\u0012\f\u0012\n V*\u0004\u0018\u00010^0^0T8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b_\u0010X¨\u0006a"},
   d2 = {"Lru/melonity/feature/features/modules/combat/AuraFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "getCorrectMovementType", "()Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "", "enabled", "", "onToggle", "(Z)V", "resetResult", "updateNormalRotation", "updateFuntimeRotation", "Lnet/minecraft/class_243;", "current", "target", "", "factor", "interpolatePosition", "(Lnet/minecraft/class_243;Lnet/minecraft/class_243;F)Lnet/minecraft/class_243;", "currentAngle", "", "targetAngle", "alekfactor", "pitch", "alekrotation", "(FDFZ)F", "delta", "getSens", "(F)F", "sprint", "hit", "valid", "()Z", "canPerformCritical", "enforceFuntimeUserPitch", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "attackSetting", "targetsSetting", "sortTargets", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "distanceAim", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "getDistanceAim", "()Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "critSetting", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "client", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "correctMovementType", "userPitch", "rotationMode", "multiPoints", "Lnet/minecraft/class_1309;", "Lnet/minecraft/class_1309;", "getTarget", "()Lnet/minecraft/class_1309;", "setTarget", "(Lnet/minecraft/class_1309;)V", "currentTargetPos", "Lnet/minecraft/class_243;", "", "lastTargetPosUpdate", "J", "lastPositionCheck", "isInitialAim", "Z", "Ljava/security/SecureRandom;", "random", "Ljava/security/SecureRandom;", "Lru/melonity/feature/features/modules/combat/AuraFeature$Pair;", "result", "Lru/melonity/feature/features/modules/combat/AuraFeature$Pair;", "hasActiveRotation", "", "clicked", "I", "Lru/melonity/feature/features/modules/combat/aura/PointSearchResult;", "point", "Lru/melonity/feature/features/modules/combat/aura/PointSearchResult;", "sprintResetTicks", "sprintKeyRestore", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/UpdateEvent;", "kotlin.jvm.PlatformType", "onUpdate", "Lru/melonity/util/event/EventListener;", "getOnUpdate$annotations", "Lru/melonity/util/event/events/PreMotionEvent;", "onPreMotion", "Lru/melonity/util/event/events/StrafeEvent;", "onStrafe", "Lru/melonity/util/event/events/EventInput;", "onInput", "Pair", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class AuraFeature extends SimpleFeature {
   @NotNull
   private final ListSetting sprint;
   @NotNull
   private final ListSetting attackSetting;
   @NotNull
   private final ListSetting targetsSetting;
   @NotNull
   private final ListSetting sortTargets;
   @NotNull
   private final NumberSetting distanceAim;
   @NotNull
   private final ListSetting critSetting;
   @NotNull
   private final BooleanSetting client;
   @NotNull
   private final ListSetting correctMovementType;
   @NotNull
   private final BooleanSetting userPitch;
   @NotNull
   private final ListSetting rotationMode;
   @NotNull
   private final BooleanSetting multiPoints;
   @Nullable
   private class_1309 target;
   @Nullable
   private class_243 currentTargetPos;
   private long lastTargetPosUpdate;
   private long lastPositionCheck;
   private boolean isInitialAim;
   @NotNull
   private final SecureRandom random;
   @NotNull
   private AuraFeature.Pair<Float, Float> result;
   private boolean hasActiveRotation;
   private int clicked;
   @NotNull
   private PointSearchResult point;
   private int sprintResetTicks;
   private boolean sprintKeyRestore;
   @NotNull
   private final EventListener<UpdateEvent> onUpdate;
   @NotNull
   private final EventListener<PreMotionEvent> onPreMotion;
   @NotNull
   private final EventListener<StrafeEvent> onStrafe;
   @NotNull
   private final EventListener<EventInput> onInput;

   public AuraFeature() {
      super("Aura", MenuCategory.COMBAT);
      ListSetting.ModeType var10004 = ListSetting.ModeType.SINGLE;
      String[] var1 = new String[]{"aura.sp_bh_smart", "aura.sp_bh_none"};
      this.sprint = new ListSetting("aura.sp_bh", var10004, "aura.sp_bh_smart", var1);
      var10004 = ListSetting.ModeType.SINGLE;
      var1 = new String[]{"1.8", "1.9+"};
      this.attackSetting = new ListSetting("legitaura.attack_type", var10004, "1.9+", var1);
      var10004 = ListSetting.ModeType.MULTI;
      var1 = new String[]{"legitaura.players", "legitaura.nakeds", "legitaura.naked_invisibles", "legitaura.mobs", "legitaura.invisibles"};
      this.targetsSetting = new ListSetting("legitaura.targets", var10004, "legitaura.players", var1);
      var10004 = ListSetting.ModeType.SINGLE;
      var1 = new String[]{"aura.ts_dist", "aura.ts_simplify", "aura.ts_fov", "aura.ts_health"};
      this.sortTargets = new ListSetting("aura.targets_sort", var10004, "aura.ts_dist", var1);
      this.distanceAim = new NumberSetting("legitaura.aim_distance", AuraFeature::distanceAim$lambda$0, 1.0F, 5.0F, 1.5F);
      var10004 = ListSetting.ModeType.SINGLE;
      var1 = new String[]{"triggerbot.nocrit", "triggerbot.onlycrit", "triggerbot.smartcrit"};
      this.critSetting = new ListSetting("triggerbot.crit_type", var10004, "triggerbot.nocrit", var1);
      this.client = new BooleanSetting("Client", false);
      var10004 = ListSetting.ModeType.SINGLE;
      var1 = new String[]{"None", "Focused", "Free"};
      this.correctMovementType = new ListSetting("Correct Movement Type", var10004, "Focused", var1);
      this.userPitch = new BooleanSetting("User's pitch", false);
      var10004 = ListSetting.ModeType.SINGLE;
      var1 = new String[]{"Funtime", "Normal"};
      this.rotationMode = new ListSetting("Rotation Mode", var10004, "Funtime", var1);
      this.multiPoints = new BooleanSetting("MultiPoints", true);
      this.isInitialAim = true;
      this.random = new SecureRandom();
      this.result = new AuraFeature.Pair<>(0.0F, 0.0F);
      this.clicked = 5;
      this.point = PointSearch.INSTANCE.getMISS();
      this.onUpdate = event -> onUpdate$lambda$1(this, event);
      this.onPreMotion = event -> onPreMotion$lambda$2(this, event);
      this.onStrafe = event -> onStrafe$lambda$3(this, event);
      this.onInput = event -> onInput$lambda$4(this, event);
      MenuFeatureView[] var8 = new MenuFeatureView[]{
         this.rotationMode,
         this.attackSetting,
         this.critSetting,
         this.sprint,
         this.targetsSetting,
         this.sortTargets,
         this.distanceAim,
         this.multiPoints,
         this.client,
         this.correctMovementType,
         this.userPitch
      };
      this.settings(var8);
   }

   @NotNull
   public final NumberSetting getDistanceAim() {
      return this.distanceAim;
   }

   @Nullable
   public final class_1309 getTarget() {
      return this.target;
   }

   public final void setTarget(@Nullable class_1309 value) {
      this.target = value;
   }

   @NotNull
   public final ListSetting getCorrectMovementType() {
      return this.correctMovementType;
   }

   @Override
   public void onToggle(boolean enabled) {
      this.resetResult();
   }

   public final void resetResult() {
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 var10003 = SimpleFeature.mc.field_1724;
         Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
         Float var1 = var10003.method_5705(1.0F);
         class_746 var10004 = SimpleFeature.mc.field_1724;
         Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
         this.result = new AuraFeature.Pair<>(var1, var10004.method_5695(1.0F));
         RotationManager.INSTANCE.reset();
      }

      this.hasActiveRotation = false;
      this.currentTargetPos = null;
      this.lastTargetPosUpdate = 0L;
      this.lastPositionCheck = 0L;
      this.isInitialAim = true;
   }

   private final void updateNormalRotation() {
      class_746 player = SimpleFeature.mc.field_1724;
      class_1309 currentTarget = this.target;
      if (player != null && currentTarget != null) {
         class_243 aimAt = this.trackAimPosition(currentTarget);
         class_243 eyes = player.method_33571();
         double deltaX = aimAt.field_1352 - eyes.field_1352;
         double deltaY = aimAt.field_1351 - eyes.field_1351;
         double deltaZ = aimAt.field_1350 - eyes.field_1350;
         double horizontal = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
         float wantedYaw = (float)(Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90.0);
         float wantedPitch = (float)(-Math.toDegrees(Math.atan2(deltaY, horizontal)));
         Float previousYaw = this.result.getFirst();
         Float previousPitch = this.result.getSecond();
         float currentYaw = previousYaw != null ? previousYaw : player.method_36454();
         float currentPitch = previousPitch != null ? previousPitch : player.method_36455();
         float yawStep = this.getSens(class_3532.method_15393(wantedYaw - currentYaw));
         float newYaw = currentYaw + yawStep;
         float newPitch;
         if (this.userPitch.get()) {
            newPitch = player.method_36455();
         } else {
            newPitch = currentPitch + this.getSens(wantedPitch - currentPitch);
         }

         this.applyRotation(player, newYaw, newPitch);
      }
   }

   private final void updateFuntimeRotation() {
      this.enforceFuntimeUserPitch();
      class_746 player = SimpleFeature.mc.field_1724;
      class_1309 currentTarget = this.target;
      if (player != null && currentTarget != null) {
         class_243 aimAt = this.trackAimPosition(currentTarget);
         class_243 eyes = player.method_33571();
         double deltaX = aimAt.field_1352 - eyes.field_1352;
         double deltaY = aimAt.field_1351 - eyes.field_1351;
         double deltaZ = aimAt.field_1350 - eyes.field_1350;
         double horizontal = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
         double wantedYaw = Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90.0;
         double wantedPitch = -Math.toDegrees(Math.atan2(deltaY, horizontal));
         Float previousYaw = this.result.getFirst();
         Float previousPitch = this.result.getSecond();
         float currentYaw = previousYaw != null ? previousYaw : player.method_36454();
         float currentPitch = previousPitch != null ? previousPitch : player.method_36455();
         float aimSpeed = 0.55F + this.random.nextFloat() * 0.25F;
         if (this.isInitialAim) {
            aimSpeed = Math.min(1.0F, aimSpeed + 0.25F);
         }

         float newYaw = this.alekrotation(currentYaw, wantedYaw, aimSpeed, false);
         float newPitch;
         if (this.userPitch.get()) {
            newPitch = player.method_36455();
         } else {
            newPitch = this.alekrotation(currentPitch, wantedPitch, aimSpeed, true);
         }

         this.applyRotation(player, newYaw, newPitch);
      }
   }

   private final class_243 trackAimPosition(class_1309 entity) {
      class_243 wanted = entity.method_19538().method_1031(0.0, (double)entity.method_17682() * 0.5, 0.0);
      if (this.multiPoints.get()) {
         class_243 searched = this.point.getPoint();
         if (!searched.equals(class_243.field_1353)) {
            wanted = searched;
         }
      }

      long now = System.currentTimeMillis();
      class_243 tracked = this.currentTargetPos;
      if (tracked == null || this.isInitialAim || this.lastTargetPosUpdate == 0L) {
         tracked = wanted;
      } else {
         float factor = class_3532.method_15363((float)(now - this.lastTargetPosUpdate) / 50.0F * 0.6F, 0.1F, 1.0F);
         tracked = this.interpolatePosition(tracked, wanted, factor);
      }

      this.currentTargetPos = tracked;
      this.lastTargetPosUpdate = now;
      return tracked;
   }

   private final void applyRotation(class_746 player, float yaw, float pitch) {
      float clampedPitch = class_3532.method_15363(pitch, -90.0F, 90.0F);
      this.result = new AuraFeature.Pair<>(yaw, clampedPitch);
      RotationManager.INSTANCE.rotate(yaw, clampedPitch);
      if (this.client.get()) {
         player.method_36456(yaw);
         player.method_36457(clampedPitch);
      }

      this.isInitialAim = false;
   }

   private final class_243 interpolatePosition(class_243 current, class_243 target, float factor) {
      float clamped = class_3532.method_15363(factor, 0.0F, 1.0F);
      return new class_243(
         current.field_1352 + (target.field_1352 - current.field_1352) * (double)clamped,
         current.field_1351 + (target.field_1351 - current.field_1351) * (double)clamped,
         current.field_1350 + (target.field_1350 - current.field_1350) * (double)clamped
      );
   }

   private final float alekrotation(float currentAngle, double targetAngle, float alekfactor, boolean pitch) {
      float difference = (float)(targetAngle - (double)currentAngle);
      if (!pitch) {
         difference = class_3532.method_15393(difference);
      }

      float distance = Math.abs(difference);
      if (distance < 0.05F) {
         return currentAngle + difference;
      } else {
         float factor = class_3532.method_15363(alekfactor, 0.05F, 1.0F);
         float easing = distance / (distance + (pitch ? 12.0F : 20.0F));
         float step = distance * factor * (0.35F + 0.65F * easing);
         step += (this.random.nextFloat() - 0.5F) * 0.35F * factor;
         step = class_3532.method_15363(step, 0.0F, distance);
         float mouseStep = this.getSens(step);
         if (mouseStep <= 0.0F) {
            mouseStep = step;
         }

         return currentAngle + (difference < 0.0F ? -mouseStep : mouseStep);
      }
   }

   private final float getSens(float delta) {
      Double sensitivity = SimpleFeature.mc.field_1690.method_42495().method_41753();
      float scaled = (float)(sensitivity.doubleValue() * 0.6 + 0.2);
      float mouseGcd = scaled * scaled * scaled * 8.0F * 0.15F;
      return mouseGcd <= 0.0F ? delta : (float)((int)(delta / mouseGcd)) * mouseGcd;
   }

   public final void sprint() {
      class_746 player = SimpleFeature.mc.field_1724;
      if (player != null && SimpleFeature.mc.method_1562() != null) {
         if (this.sprint.contains("aura.sp_bh_smart")) {
            if (this.target != null && this.sprintResetTicks <= 0) {
               if (player.method_5624()) {
                  if (!(this.point.getDistance() > 3.0 + this.distanceAim.value.doubleValue())) {
                     if (this.attackReady()) {
                        this.sprintKeyRestore = SimpleFeature.mc.field_1690.field_1867.method_1434();
                        SimpleFeature.mc.field_1690.field_1867.method_23481(false);
                        player.method_5728(false);
                        SimpleFeature.mc.method_1562().method_52787(new class_2848(player, class_2849.field_12985));
                        // The hand-sent STOP_SPRINTING above does not touch lastSprinting, so
                        // vanilla's sendSprintingPacket still saw a true -> false transition on
                        // the same tick and sent a second stop packet. GrimAC counts the
                        // duplicate as BadPacketsF (state=false).
                        ((ILocalPlayer)player).setServerSprintState(false);
                        this.sprintResetTicks = 2;
                     }
                  }
               }
            }
         }
      }
   }

   public final void hit() {
      class_746 player = SimpleFeature.mc.field_1724;
      class_1309 currentTarget = this.target;
      if (player != null && SimpleFeature.mc.field_1761 != null && currentTarget != null) {
         SimpleFeature.mc.field_1761.method_2918(player, (class_1297)currentTarget);
         player.method_6104(class_1268.field_5808);
         player.method_7350();
         this.clicked = this.attackSetting.contains("1.8") ? 2 + this.random.nextInt(3) : 1;
      }
   }

   public final boolean valid() {
      class_746 player = SimpleFeature.mc.field_1724;
      class_1309 currentTarget = this.target;
      if (player == null || SimpleFeature.mc.field_1687 == null || SimpleFeature.mc.field_1761 == null) {
         return false;
      } else if (currentTarget == null || !currentTarget.method_5805() || currentTarget.method_6032() <= 0.0F) {
         return false;
      } else {
         double reach = 3.0 + this.distanceAim.value.doubleValue();
         if (this.point.getDistance() > reach) {
            return false;
         } else {
            Float aimedYaw = this.result.getFirst();
            Float aimedPitch = this.result.getSecond();
            if (aimedYaw != null && aimedPitch != null) {
               return !RayTraceUtil.rayTraceEntity(aimedYaw, aimedPitch, reach, (class_1297)currentTarget) ? false : this.attackReady();
            } else {
               return false;
            }
         }
      }
   }

   private final boolean attackReady() {
      class_746 player = SimpleFeature.mc.field_1724;
      if (player == null) {
         return false;
      } else if (this.attackSetting.contains("1.8") ? this.clicked > 0 : player.method_7261(0.5F) < 1.0F) {
         return false;
      } else {
         boolean critical = this.canPerformCritical();
         if (this.critSetting.contains("triggerbot.onlycrit") && !critical) {
            return false;
         } else {
            return !this.critSetting.contains("triggerbot.smartcrit") || critical || player.method_24828();
         }
      }
   }

   private final boolean canPerformCritical() {
      if (SimpleFeature.mc.field_1724 == null) {
         return false;
      } else {
         class_746 local1_0 = SimpleFeature.mc.field_1724;
         if (local1_0.method_24828()) {
            return false;
         } else if (local1_0.field_6017 <= 0.0F) {
            return false;
         } else if (local1_0.method_5799() || local1_0.method_5869()) {
            return false;
         } else if (local1_0.method_5771()) {
            return false;
         } else if (local1_0.method_5765()) {
            return false;
         } else if (local1_0.method_6101()) {
            return false;
         } else if (local1_0.method_6059(class_1294.field_5919)) {
            return false;
         } else {
            return local1_0.method_6128() ? false : !local1_0.method_31549().field_7479;
         }
      }
   }

   private final void enforceFuntimeUserPitch() {
      if (this.rotationMode.contains("Funtime") && !this.userPitch.get()) {
         this.userPitch.setValue(true);
      }
   }

   private static final String distanceAim$lambda$0(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return "3.0 + " + var10000;
   }

   private static final void onUpdate$lambda$1(AuraFeature this$0, UpdateEvent event) {
      if (SimpleFeature.mc.field_1724 != null) {
         if (this$0.sprintResetTicks > 0) {
            int local2_3 = this$0.sprintResetTicks;
            this$0.sprintResetTicks = local2_3 + -1;
            if (this$0.sprintResetTicks == 0 && this$0.sprintKeyRestore) {
               SimpleFeature.mc.field_1690.field_1867.method_23481(true);
            }
         }

         int var12 = this$0.clicked;
         this$0.clicked = var12 + -1;
         double local2_3 = 3.0 + this$0.distanceAim.value.doubleValue();
         TargetSelection var10000 = TargetSelection.INSTANCE;
         Object var10001 = this$0.sortTargets.mode.get(0);
         Intrinsics.checkNotNullExpressionValue(var10001, "get(...)");
         class_1309 local4_4 = var10000.find((String)var10001, new SearchConditions(this$0.targetsSetting, true, local2_3));
         if (local4_4 == null) {
            this$0.target = null;
            this$0.resetResult();
         } else {
            boolean local5_5;
            boolean var16;
            label77: {
               label76: {
                  local5_5 = local4_4 != this$0.target;
                  if (this$0.target != null) {
                     if (System.currentTimeMillis() - this$0.lastPositionCheck > 50L) {
                        break label76;
                     }

                     class_243 var15 = local4_4.method_19538();
                     class_1309 var14 = this$0.target;
                     Intrinsics.checkNotNull(this$0.target);
                     if (var15.method_1022(var14.method_19538()) > 0.1) {
                        break label76;
                     }
                  }

                  var16 = false;
                  break label77;
               }

               var16 = true;
            }

            boolean local6_6 = var16;
            if (local5_5 || local6_6) {
               this$0.isInitialAim = true;
               this$0.currentTargetPos = null;
               this$0.lastPositionCheck = System.currentTimeMillis();
               if (local5_5) {
                  this$0.lastTargetPosUpdate = 0L;
               }
            }

            if (local5_5 && local4_4 instanceof class_1657) {
               HudFeature local7_1 = Melonity.INSTANCE.featureManager.hudFeature;
               DynamicIslandView local8_2 = local7_1 != null ? local7_1.getDynamicIslandView() : null;
               if (local7_1 != null && local7_1.isDynamicIslandEnabled() && local8_2 != null) {
                  class_746 var17 = SimpleFeature.mc.field_1724;
                  Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
                  float local9_0 = var17.method_5739((class_1297)local4_4);
                  var10001 = ((class_1657)local4_4).method_5477().getString();
                  Intrinsics.checkNotNullExpressionValue(var10001, "getString(...)");
                  String var10 = "%.1f";
                  Object[] var11 = new Object[]{local9_0};
                  String var10002 = String.format(var10, Arrays.copyOf(var11, var11.length));
                  Intrinsics.checkNotNullExpressionValue(var10002, "format(...)");
                  local8_2.pushNotification((String)var10001, "Выбрана цель • " + var10002 + "м", true);
               }
            }

            this$0.target = local4_4;
            this$0.sprint();
            this$0.point = PointSearch.INSTANCE.getClosestTargetPoint((class_1297)local4_4);
            if (Intrinsics.areEqual(this$0.point.getPoint(), class_243.field_1353)) {
               this$0.resetResult();
            } else {
               double local7_7 = this$0.point.getDistance();
               if (local7_7 < local2_3) {
                  if (Intrinsics.areEqual("Normal", this$0.rotationMode.mode.get(0))) {
                     this$0.updateNormalRotation();
                  } else {
                     this$0.updateFuntimeRotation();
                  }

                  if (this$0.valid()) {
                     this$0.hit();
                  }

                  this$0.hasActiveRotation = true;
               } else {
                  this$0.resetResult();
               }
            }
         }
      }
   }

   private static final void onPreMotion$lambda$2(AuraFeature this$0, PreMotionEvent event) {
      if (this$0.sprintResetTicks > 0 && SimpleFeature.mc.field_1724 != null) {
         SimpleFeature.mc.field_1690.field_1867.method_23481(false);
         class_746 var10000 = SimpleFeature.mc.field_1724;
         Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
         var10000.method_5728(false);
      }

      if (this$0.hasActiveRotation) {
         Intrinsics.checkNotNull(event);
         Object var10001 = this$0.result.getFirst();
         Intrinsics.checkNotNull(var10001);
         event.setYaw(((Number)var10001).floatValue());
         var10001 = this$0.result.getSecond();
         Intrinsics.checkNotNull(var10001);
         event.setPitch(((Number)var10001).floatValue());
      }
   }

   private static final void onStrafe$lambda$3(AuraFeature this$0, StrafeEvent event) {
      if (this$0.correctMovementType.mode.contains("Focused") && !Intrinsics.areEqual(this$0.result.getFirst(), 0.0F) && this$0.target != null) {
         if (event != null) {
            Object var10001 = this$0.result.getFirst();
            Intrinsics.checkNotNull(var10001);
            event.yaw = ((Number)var10001).floatValue();
         }
      }
   }

   private static final void onInput$lambda$4(AuraFeature this$0, EventInput event) {
      if (this$0.correctMovementType.mode.contains("Free") && !Intrinsics.areEqual(this$0.result.getFirst(), 0.0F) && this$0.target != null) {
         if (event != null) {
            Object var10001 = this$0.result.getFirst();
            Intrinsics.checkNotNull(var10001);
            float var2 = ((Number)var10001).floatValue();
            class_746 var10002 = SimpleFeature.mc.field_1724;
            Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
            event.setYaw(var2, var10002.method_36454());
         }
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\t\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003B\u001b\u0012\b\u0010\u0004\u001a\u0004\u0018\u00018\u0000\u0012\b\u0010\u0005\u001a\u0004\u0018\u00018\u0001¢\u0006\u0004\b\u0006\u0010\u0007R\u0019\u0010\u0004\u001a\u0004\u0018\u00018\u00008\u0006¢\u0006\f\n\u0004\b\u0004\u0010\b\u001a\u0004\b\t\u0010\nR\u0019\u0010\u0005\u001a\u0004\u0018\u00018\u00018\u0006¢\u0006\f\n\u0004\b\u0005\u0010\b\u001a\u0004\b\u000b\u0010\n¨\u0006\f"},
      d2 = {"Lru/melonity/feature/features/modules/combat/AuraFeature$Pair;", "T", "U", "", "first", "second", "<init>", "(Ljava/lang/Object;Ljava/lang/Object;)V", "Ljava/lang/Object;", "getFirst", "()Ljava/lang/Object;", "getSecond", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   public static final class Pair<T, U> {
      @Nullable
      private final T first;
      @Nullable
      private final U second;

      public Pair(@Nullable T first, @Nullable U second) {
         this.first = (T)first;
         this.second = (U)second;
      }

      @Nullable
      public final T getFirst() {
         return this.first;
      }

      @Nullable
      public final U getSecond() {
         return this.second;
      }
   }
}
