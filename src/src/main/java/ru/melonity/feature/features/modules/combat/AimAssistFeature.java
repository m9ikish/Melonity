package ru.melonity.feature.features.modules.combat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1661;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.feature.features.modules.combat.aura.PointSearch;
import ru.melonity.feature.features.modules.combat.aura.SearchConditions;
import ru.melonity.feature.features.modules.combat.aura.TargetSelection;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.RenderGameWorldEvent;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.minecraft.DeltaGCDProcessor;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 n2\u00020\u0001:\u0001nB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000f\u0010\u0005\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0005\u0010\u0003J\u000f\u0010\u0006\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0006\u0010\u0003J\u000f\u0010\b\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\b\u0010\tJ\u001f\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00020\nH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0011\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0011\u0010\u0003J\u0017\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\nH\u0002¢\u0006\u0004\b\u0013\u0010\u0014J\u001b\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u0015H\u0002¢\u0006\u0004\b\u0016\u0010\u0017J\r\u0010\u0018\u001a\u00020\u0007¢\u0006\u0004\b\u0018\u0010\tR\u0017\u0010\u001a\u001a\u00020\u00198\u0006¢\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001f\u001a\u00020\u001e8\u0006¢\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u0017\u0010#\u001a\u00020\u001e8\u0006¢\u0006\f\n\u0004\b#\u0010 \u001a\u0004\b$\u0010\"R\u0017\u0010%\u001a\u00020\u00198\u0006¢\u0006\f\n\u0004\b%\u0010\u001b\u001a\u0004\b&\u0010\u001dR\u0017\u0010'\u001a\u00020\u00198\u0006¢\u0006\f\n\u0004\b'\u0010\u001b\u001a\u0004\b(\u0010\u001dR\u0017\u0010)\u001a\u00020\u00198\u0006¢\u0006\f\n\u0004\b)\u0010\u001b\u001a\u0004\b*\u0010\u001dR\u0017\u0010,\u001a\u00020+8\u0006¢\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/R\u0017\u00100\u001a\u00020\u00198\u0006¢\u0006\f\n\u0004\b0\u0010\u001b\u001a\u0004\b1\u0010\u001dR\u0017\u00102\u001a\u00020\u00198\u0006¢\u0006\f\n\u0004\b2\u0010\u001b\u001a\u0004\b3\u0010\u001dR\u0017\u00104\u001a\u00020\u00198\u0006¢\u0006\f\n\u0004\b4\u0010\u001b\u001a\u0004\b5\u0010\u001dR\u0017\u00106\u001a\u00020\u00198\u0006¢\u0006\f\n\u0004\b6\u0010\u001b\u001a\u0004\b7\u0010\u001dR\u0017\u00108\u001a\u00020\u00198\u0006¢\u0006\f\n\u0004\b8\u0010\u001b\u001a\u0004\b9\u0010\u001dR\u0017\u0010:\u001a\u00020\u00198\u0006¢\u0006\f\n\u0004\b:\u0010\u001b\u001a\u0004\b;\u0010\u001dR\u0017\u0010<\u001a\u00020\u00198\u0006¢\u0006\f\n\u0004\b<\u0010\u001b\u001a\u0004\b=\u0010\u001dR\u0017\u0010>\u001a\u00020\u001e8\u0006¢\u0006\f\n\u0004\b>\u0010 \u001a\u0004\b?\u0010\"R\u0017\u0010@\u001a\u00020\u00198\u0006¢\u0006\f\n\u0004\b@\u0010\u001b\u001a\u0004\bA\u0010\u001dR$\u0010C\u001a\u0004\u0018\u00010B8\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\bC\u0010D\u001a\u0004\bE\u0010F\"\u0004\bG\u0010HR\u0016\u0010J\u001a\u00020I8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bJ\u0010KR\u0016\u0010L\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bL\u0010MR\u0016\u0010N\u001a\u00020\u00078\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bN\u0010OR\u0016\u0010P\u001a\u00020I8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bP\u0010KR\u0018\u0010Q\u001a\u0004\u0018\u00010B8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bQ\u0010DR\u0016\u0010R\u001a\u00020I8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bR\u0010KR\"\u0010S\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\u00158\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bS\u0010TR\u0016\u0010U\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bU\u0010MR\u0016\u0010V\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bV\u0010MR(\u0010Y\u001a\u0014\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020X0W8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bY\u0010ZR(\u0010[\u001a\u0014\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020X0W8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b[\u0010ZR\u0016\u0010\\\u001a\u00020\u00078\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\\\u0010OR\u0016\u0010]\u001a\u00020I8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b]\u0010KR\u0016\u0010^\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b^\u0010MR\u0016\u0010_\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b_\u0010MR\u0016\u0010`\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b`\u0010MR\u0016\u0010a\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\ba\u0010MR\u0016\u0010b\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bb\u0010MR\u0016\u0010c\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bc\u0010MR\u0016\u0010d\u001a\u00020\n8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\bd\u0010MR\u001d\u0010g\u001a\b\u0012\u0004\u0012\u00020f0e8\u0006¢\u0006\f\n\u0004\bg\u0010h\u001a\u0004\bi\u0010jR\u001d\u0010l\u001a\b\u0012\u0004\u0012\u00020k0e8\u0006¢\u0006\f\n\u0004\bl\u0010h\u001a\u0004\bm\u0010j¨\u0006o"},
   d2 = {"Lru/melonity/feature/features/modules/combat/AimAssistFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "", "handleTargetSelection", "performTargetSearch", "", "isTargetLocked", "()Z", "", "delta", "maxSpeed", "calculateHumanLikeSpeed", "(FF)F", "calculatePredictionFactor", "()F", "startSaccade", "frameTime", "updateFocusDrift", "(F)V", "Lkotlin/Pair;", "generatePhysiologicalTremor", "()Lkotlin/Pair;", "isWeapon", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "distanceAim", "Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "getDistanceAim", "()Lru/melonity/ui/menu/clickui/view/feature/setting/NumberSetting;", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "targetsSetting", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "getTargetsSetting", "()Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "sortTargets", "getSortTargets", "strength", "getStrength", "smoothness", "getSmoothness", "verticalFactor", "getVerticalFactor", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "onlyOnWeapon", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "getOnlyOnWeapon", "()Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "assistStrength", "getAssistStrength", "humanAccuracy", "getHumanAccuracy", "reactionDelay", "getReactionDelay", "focusDrift", "getFocusDrift", "eyeFatigue", "getEyeFatigue", "targetSwitchCooldown", "getTargetSwitchCooldown", "aimStability", "getAimStability", "targetLockMode", "getTargetLockMode", "lockTimeout", "getLockTimeout", "Lnet/minecraft/class_1309;", "target", "Lnet/minecraft/class_1309;", "getTarget", "()Lnet/minecraft/class_1309;", "setTarget", "(Lnet/minecraft/class_1309;)V", "", "lastTargetChange", "J", "reactionTimer", "F", "shouldAssist", "Z", "targetAcquiredTime", "lockedTarget", "lockStartTime", "eyePositionOffset", "Lkotlin/Pair;", "focusDriftAccumulator", "fatigueLevel", "Lkotlin/Triple;", "", "previousTargetPos", "Lkotlin/Triple;", "targetVelocity", "saccadeActive", "saccadeEndTime", "rawDeltaYaw", "rawDeltaPitch", "smoothedDeltaYaw", "smoothedDeltaPitch", "microTremorX", "microTremorY", "tremorDecay", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/UpdateEvent;", "onUpdate", "Lru/melonity/util/event/EventListener;", "getOnUpdate", "()Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/RenderGameWorldEvent;", "onRender", "getOnRender", "Companion", "melonity_client"}
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\nAimAssistFeature.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AimAssistFeature.kt\nru/melonity/feature/features/modules/combat/AimAssistFeature\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,465:1\n1761#2,3:466\n*S KotlinDebug\n*F\n+ 1 AimAssistFeature.kt\nru/melonity/feature/features/modules/combat/AimAssistFeature\n*L\n461#1:466,3\n*E\n"})
public final class AimAssistFeature extends SimpleFeature {
   @NotNull
   public static final AimAssistFeature.Companion Companion = new AimAssistFeature.Companion(null);
   @NotNull
   private final NumberSetting distanceAim = new NumberSetting("legitaura.aim_distance", AimAssistFeature::distanceAim$lambda$0, 1.0F, 10.0F, 4.0F);
   @NotNull
   private final ListSetting targetsSetting;
   @NotNull
   private final ListSetting sortTargets;
   @NotNull
   private final NumberSetting strength;
   @NotNull
   private final NumberSetting smoothness;
   @NotNull
   private final NumberSetting verticalFactor;
   @NotNull
   private final BooleanSetting onlyOnWeapon;
   @NotNull
   private final NumberSetting assistStrength;
   @NotNull
   private final NumberSetting humanAccuracy;
   @NotNull
   private final NumberSetting reactionDelay;
   @NotNull
   private final NumberSetting focusDrift;
   @NotNull
   private final NumberSetting eyeFatigue;
   @NotNull
   private final NumberSetting targetSwitchCooldown;
   @NotNull
   private final NumberSetting aimStability;
   @NotNull
   private final ListSetting targetLockMode;
   @NotNull
   private final NumberSetting lockTimeout;
   @Nullable
   private class_1309 target;
   private long lastTargetChange;
   private float reactionTimer;
   private boolean shouldAssist;
   private long targetAcquiredTime;
   @Nullable
   private class_1309 lockedTarget;
   private long lockStartTime;
   @NotNull
   private Pair<Float, Float> eyePositionOffset;
   private float focusDriftAccumulator;
   private float fatigueLevel;
   @NotNull
   private Triple<Double, Double, Double> previousTargetPos;
   @NotNull
   private Triple<Double, Double, Double> targetVelocity;
   private boolean saccadeActive;
   private long saccadeEndTime;
   private float rawDeltaYaw;
   private float rawDeltaPitch;
   private float smoothedDeltaYaw;
   private float smoothedDeltaPitch;
   private float microTremorX;
   private float microTremorY;
   private float tremorDecay;
   @NotNull
   private final EventListener<UpdateEvent> onUpdate;
   @NotNull
   private final EventListener<RenderGameWorldEvent> onRender;
   @NotNull
   private static final Lazy<Set<String>> weaponKeywords$delegate = LazyKt.lazy(AimAssistFeature::weaponKeywords_delegate$lambda$17);

   public AimAssistFeature() {
      super("AimAssist", MenuCategory.COMBAT);
      ListSetting.ModeType var10004 = ListSetting.ModeType.MULTI;
      String[] var1 = new String[]{"legitaura.players", "legitaura.nakeds", "legitaura.naked_invisibles", "legitaura.mobs", "legitaura.invisibles"};
      this.targetsSetting = new ListSetting("legitaura.targets", var10004, "legitaura.players", var1);
      var10004 = ListSetting.ModeType.SINGLE;
      var1 = new String[]{"aura.ts_dist", "aura.ts_health", "aura.ts_fov"};
      this.sortTargets = new ListSetting("aura.targets_sort", var10004, "aura.ts_dist", var1);
      this.strength = new NumberSetting("projectile.rot_speed", AimAssistFeature::strength$lambda$1, 4.0F, 10.0F, 8.0F);
      this.smoothness = new NumberSetting("projectile.smooth_value", AimAssistFeature::smoothness$lambda$2, 4.0F, 10.0F, 8.0F);
      this.verticalFactor = new NumberSetting("projectile.vertical_factor", AimAssistFeature::verticalFactor$lambda$3, 0.3F, 1.0F, 0.6F);
      this.onlyOnWeapon = new BooleanSetting("aimassist.only_on_weapon", true);
      this.assistStrength = new NumberSetting("aimassist.strength", AimAssistFeature::assistStrength$lambda$4, 0.6F, 1.0F, 0.8F);
      this.humanAccuracy = new NumberSetting("aimassist.human_accuracy", AimAssistFeature::humanAccuracy$lambda$5, 0.0F, 1.0F, 0.95F);
      this.reactionDelay = new NumberSetting("aimassist.reaction_delay", AimAssistFeature::reactionDelay$lambda$6, 0.0F, 0.3F, 0.15F);
      this.focusDrift = new NumberSetting("aimassist.focus_drift", AimAssistFeature::focusDrift$lambda$7, 0.0F, 1.0F, 0.2F);
      this.eyeFatigue = new NumberSetting("aimassist.eye_fatigue", AimAssistFeature::eyeFatigue$lambda$8, 0.0F, 1.0F, 0.2F);
      this.targetSwitchCooldown = new NumberSetting("aimassist.target_switch_cooldown", AimAssistFeature::targetSwitchCooldown$lambda$9, 0.0F, 2.0F, 0.5F);
      this.aimStability = new NumberSetting("aimassist.aim_stability", AimAssistFeature::aimStability$lambda$10, 0.0F, 1.0F, 1.0F);
      var10004 = ListSetting.ModeType.SINGLE;
      var1 = new String[]{"aimassist.lock_off", "aimassist.lock_on_attack", "aimassist.lock_auto"};
      this.targetLockMode = new ListSetting("aimassist.target_lock_mode", var10004, "aimassist.lock_off", var1);
      this.lockTimeout = new NumberSetting("aimassist.lock_timeout", AimAssistFeature::lockTimeout$lambda$11, 0.0F, 10.0F, 5.0F);
      this.eyePositionOffset = new Pair(0.0F, 0.0F);
      this.previousTargetPos = new Triple(0.0, 0.0, 0.0);
      this.targetVelocity = new Triple(0.0, 0.0, 0.0);
      MenuFeatureView[] var4 = new MenuFeatureView[]{
         this.targetsSetting,
         this.sortTargets,
         this.distanceAim,
         this.onlyOnWeapon,
         this.strength,
         this.smoothness,
         this.verticalFactor,
         this.assistStrength,
         this.humanAccuracy,
         this.reactionDelay,
         this.focusDrift,
         this.eyeFatigue,
         this.targetSwitchCooldown,
         this.aimStability,
         this.targetLockMode,
         this.lockTimeout
      };
      this.settings(var4);
      this.onUpdate = event -> onUpdate$lambda$13(this, event);
      this.onRender = event -> onRender$lambda$14(this, event);
   }

   @NotNull
   public final NumberSetting getDistanceAim() {
      return this.distanceAim;
   }

   @NotNull
   public final ListSetting getTargetsSetting() {
      return this.targetsSetting;
   }

   @NotNull
   public final ListSetting getSortTargets() {
      return this.sortTargets;
   }

   @NotNull
   public final NumberSetting getStrength() {
      return this.strength;
   }

   @NotNull
   public final NumberSetting getSmoothness() {
      return this.smoothness;
   }

   @NotNull
   public final NumberSetting getVerticalFactor() {
      return this.verticalFactor;
   }

   @NotNull
   public final BooleanSetting getOnlyOnWeapon() {
      return this.onlyOnWeapon;
   }

   @NotNull
   public final NumberSetting getAssistStrength() {
      return this.assistStrength;
   }

   @NotNull
   public final NumberSetting getHumanAccuracy() {
      return this.humanAccuracy;
   }

   @NotNull
   public final NumberSetting getReactionDelay() {
      return this.reactionDelay;
   }

   @NotNull
   public final NumberSetting getFocusDrift() {
      return this.focusDrift;
   }

   @NotNull
   public final NumberSetting getEyeFatigue() {
      return this.eyeFatigue;
   }

   @NotNull
   public final NumberSetting getTargetSwitchCooldown() {
      return this.targetSwitchCooldown;
   }

   @NotNull
   public final NumberSetting getAimStability() {
      return this.aimStability;
   }

   @NotNull
   public final ListSetting getTargetLockMode() {
      return this.targetLockMode;
   }

   @NotNull
   public final NumberSetting getLockTimeout() {
      return this.lockTimeout;
   }

   @Nullable
   public final class_1309 getTarget() {
      return this.target;
   }

   public final void setTarget(@Nullable class_1309 value) {
      this.target = value;
   }

   @NotNull
   public final EventListener<UpdateEvent> getOnUpdate() {
      return this.onUpdate;
   }

   @NotNull
   public final EventListener<RenderGameWorldEvent> getOnRender() {
      return this.onRender;
   }

   private final void handleTargetSelection() {
      long local1_0 = System.currentTimeMillis();
      if (this.lockedTarget != null && local1_0 - this.lockStartTime > (long)(this.lockTimeout.value.floatValue() * (float)1000)) {
         this.lockedTarget = null;
      }

      List var10000 = this.targetLockMode.mode;
      Intrinsics.checkNotNullExpressionValue(this.targetLockMode.mode, "mode");
      String var3 = (String)CollectionsKt.first(var10000);
      if (Intrinsics.areEqual(var3, "aimassist.lock_on_attack")) {
         if (this.lockedTarget == null && SimpleFeature.mc.field_1690.field_1886.method_1434() && this.target != null) {
            this.lockedTarget = this.target;
            this.lockStartTime = local1_0;
         }
      } else if (Intrinsics.areEqual(var3, "aimassist.lock_auto")
         && this.lockedTarget == null
         && this.target != null
         && local1_0 - this.lastTargetChange < 100L) {
         this.lockedTarget = this.target;
         this.lockStartTime = local1_0;
      }

      if (this.lockedTarget != null) {
         class_1309 var4 = this.lockedTarget;
         Intrinsics.checkNotNull(this.lockedTarget);
         if (!var4.method_29504()) {
            class_1309 var5 = this.lockedTarget;
            Intrinsics.checkNotNull(this.lockedTarget);
            if (!(var5.method_6032() <= 0.0F)) {
               class_1309 var6 = this.lockedTarget;
               Intrinsics.checkNotNull(this.lockedTarget);
               class_746 var10001 = SimpleFeature.mc.field_1724;
               Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
               if (!(var6.method_5739((class_1297)var10001) > this.distanceAim.value.floatValue())) {
                  return;
               }
            }
         }

         this.lockedTarget = null;
      }
   }

   private final void performTargetSearch() {
      System.currentTimeMillis();
      if (this.isTargetLocked()) {
         this.target = this.lockedTarget;
         this.targetAcquiredTime = System.currentTimeMillis();
      } else {
         if (System.currentTimeMillis() - this.lastTargetChange > (long)(this.targetSwitchCooldown.value.floatValue() * (float)1000)) {
            TargetSelection var10000 = TargetSelection.INSTANCE;
            List var10001 = this.sortTargets.mode;
            Intrinsics.checkNotNullExpressionValue(this.sortTargets.mode, "mode");
            String sortMode = (String)CollectionsKt.first(var10001);
            Intrinsics.checkNotNullExpressionValue(sortMode, "first(...)");
            class_1309 local1_0 = var10000.find(sortMode, new SearchConditions(this.targetsSetting, true, this.distanceAim.value.doubleValue()));
            if (!Intrinsics.areEqual(local1_0, this.target)) {
               this.target = local1_0;
               this.lastTargetChange = System.currentTimeMillis();
               this.targetAcquiredTime = System.currentTimeMillis();
               if (this.target != null) {
                  class_1309 var10003 = this.target;
                  Intrinsics.checkNotNull(this.target);
                  Double var3 = var10003.method_23317();
                  class_1309 var10004 = this.target;
                  Intrinsics.checkNotNull(this.target);
                  Double var4 = var10004.method_23318();
                  class_1309 var10005 = this.target;
                  Intrinsics.checkNotNull(this.target);
                  this.previousTargetPos = new Triple(var3, var4, var10005.method_23321());
                  this.targetVelocity = new Triple(0.0, 0.0, 0.0);
                  this.microTremorX = (MathUtil.random() - 0.5F) * 0.3F;
                  this.microTremorY = (MathUtil.random() - 0.5F) * 0.3F;
                  this.tremorDecay = 1.0F;
                  this.startSaccade();
               }
            }
         }
      }
   }

   private final boolean isTargetLocked() {
      if (this.lockedTarget != null) {
         List var10000 = this.targetLockMode.mode;
         Intrinsics.checkNotNullExpressionValue(this.targetLockMode.mode, "mode");
         if (!Intrinsics.areEqual(CollectionsKt.first(var10000), "aimassist.lock_off")) {
            return true;
         }
      }

      return false;
   }

   private final float calculateHumanLikeSpeed(float delta, float maxSpeed) {
      float local3_0 = Math.abs(delta);
      return local3_0 > 60.0F
         ? maxSpeed * 2.5F
         : (
            local3_0 > 30.0F
               ? maxSpeed * 1.8F
               : (local3_0 > 15.0F ? maxSpeed * 1.3F : (local3_0 > 8.0F ? maxSpeed * 1.0F : (local3_0 > 3.0F ? maxSpeed * 0.7F : maxSpeed * 0.4F)))
         );
   }

   private final float calculatePredictionFactor() {
      float local1_0 = (float)(System.currentTimeMillis() - this.targetAcquiredTime) / 1000.0F;
      return Math.min(0.2F, local1_0 * 0.05F);
   }

   private final void startSaccade() {
      this.saccadeActive = true;
      this.saccadeEndTime = System.currentTimeMillis() + 20L + (long)(MathUtil.random() * (float)30);
   }

   private final void updateFocusDrift(float frameTime) {
      this.focusDriftAccumulator += frameTime * 0.5F;
      this.eyePositionOffset = new Pair(
         (float)Math.sin((double)(this.focusDriftAccumulator * 0.5F)) * 0.1F, (float)Math.cos((double)(this.focusDriftAccumulator * 0.3F)) * 0.08F
      );
   }

   private final Pair<Float, Float> generatePhysiologicalTremor() {
      float local1_0 = MathUtil.random(8.0F, 12.0F);
      double local2_1 = (double)System.nanoTime() / 1.0E9;
      float local4_2 = (float)(local2_1 * (double)local1_0 * (double)2 * Math.PI);
      return new Pair((float)Math.sin((double)local4_2) * 0.05F, (float)Math.cos((double)(local4_2 + (float) (Math.PI / 4))) * 0.05F);
   }

   public final boolean isWeapon() {
      class_746 var1 = SimpleFeature.mc.field_1724;
      if (var1 != null) {
         class_1661 var2 = var1.method_31548();
         if (var2 != null) {
            class_1799 var3 = var2.method_7391();
            if (var3 != null) {
               class_1792 var4 = var3.method_7909();
               if (var4 != null) {
                  String var5 = var4.method_7876();
                  if (var5 != null) {
                     String local6_3 = var5;
                     Iterable local8_2 = Companion.getWeaponKeywords();
                     if (!(local8_2 instanceof Collection) || !((Collection)local8_2).isEmpty()) {
                        for (Object local11_1 : local8_2) {
                           String local12_0 = (String)local11_1;
                           if (StringsKt.contains(local6_3, local12_0, false)) {
                              return true;
                           }
                        }

                        return false;
                     }

                     return false;
                  }
               }
            }
         }
      }

      return false;
   }

   private static final String distanceAim$lambda$0(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String strength$lambda$1(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String smoothness$lambda$2(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String verticalFactor$lambda$3(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String assistStrength$lambda$4(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String humanAccuracy$lambda$5(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String reactionDelay$lambda$6(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String focusDrift$lambda$7(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String eyeFatigue$lambda$8(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String targetSwitchCooldown$lambda$9(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String aimStability$lambda$10(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final String lockTimeout$lambda$11(Number value) {
      String var1 = "%.2f";
      Object[] var2 = new Object[1];
      Intrinsics.checkNotNull(value);
      var2[0] = value.floatValue();
      String var10000 = String.format(var1, Arrays.copyOf(var2, var2.length));
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   private static final void onUpdate$lambda$13(AimAssistFeature local0_15, UpdateEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "<unused var>");
      if (local0_15.inInvalidState()) {
         local0_15.target = null;
         local0_15.lockedTarget = null;
         local0_15.rawDeltaYaw = 0.0F;
         local0_15.rawDeltaPitch = 0.0F;
         local0_15.reactionTimer = 0.0F;
      } else if (local0_15.onlyOnWeapon.get() && !local0_15.isWeapon()) {
         local0_15.target = null;
         local0_15.lockedTarget = null;
      } else {
         local0_15.handleTargetSelection();
         class_1309 local2_8 = local0_15.target;
         if (local2_8 != null) {
            Pair local3_9 = PointSearch.INSTANCE
               .delta(local2_8, TuplesKt.to(SimpleFeature.mc.field_1724.method_36454(), SimpleFeature.mc.field_1724.method_36455()));
            float local4_2 = ((Number)local3_9.component1()).floatValue();
            float local5_3 = ((Number)local3_9.component2()).floatValue();
            float local6_4 = Math.max(Math.abs(local4_2), Math.abs(local5_3));
            long local7_5 = System.currentTimeMillis();
            if (local7_5 - local0_15.targetAcquiredTime > 1000L) {
               Triple local9_1 = new Triple(local2_8.method_23317(), local2_8.method_23318(), local2_8.method_23321());
               if (local7_5 % (long)50 == 0L) {
                  double local10_0 = (double)(local7_5 - local0_15.targetAcquiredTime) / 1000.0;
                  if (local10_0 > 0.1) {
                     local0_15.targetVelocity = new Triple(
                        (((Number)local9_1.getFirst()).doubleValue() - ((Number)local0_15.previousTargetPos.getFirst()).doubleValue()) / local10_0,
                        (((Number)local9_1.getSecond()).doubleValue() - ((Number)local0_15.previousTargetPos.getSecond()).doubleValue()) / local10_0,
                        (((Number)local9_1.getThird()).doubleValue() - ((Number)local0_15.previousTargetPos.getThird()).doubleValue()) / local10_0
                     );
                     local0_15.previousTargetPos = local9_1;
                  }
               }
            }

            if (local6_4 > 130.0F) {
               local0_15.target = null;
               if (local0_15.isTargetLocked() && Intrinsics.areEqual(local0_15.lockedTarget, local2_8)) {
                  local0_15.lockedTarget = null;
               }

               local0_15.lastTargetChange = local7_5;
            }
         }

         local0_15.performTargetSearch();
         if (local0_15.target == null) {
            int local7_6 = 0;
            local0_15.rawDeltaYaw = 0.0F;
            local0_15.rawDeltaPitch = 0.0F;
            local0_15.reactionTimer = 0.0F;
         } else {
            class_1309 local3_9x = local0_15.target;
            Pair var13 = PointSearch.INSTANCE
               .delta(local3_9x, TuplesKt.to(SimpleFeature.mc.field_1724.method_36454(), SimpleFeature.mc.field_1724.method_36455()));
            float local5_10 = ((Number)var13.component1()).floatValue();
            float local6_11 = ((Number)var13.component2()).floatValue();
            float local7_12 = local0_15.calculatePredictionFactor();
            float local8_13 = local5_10 + (float)((Number)local0_15.targetVelocity.getFirst()).doubleValue() * local7_12;
            float local9_14 = local6_11 + (float)((Number)local0_15.targetVelocity.getSecond()).doubleValue() * local7_12;
            local0_15.rawDeltaYaw = local8_13;
            local0_15.rawDeltaPitch = local9_14;
            local0_15.reactionTimer = local0_15.reactionTimer + class_310.method_1551().method_60646().method_60636();
            local0_15.shouldAssist = local0_15.reactionTimer >= local0_15.reactionDelay.value.floatValue();
            if (local0_15.target != null) {
               local0_15.fatigueLevel = Math.min(1.0F, local0_15.fatigueLevel + local0_15.eyeFatigue.value.floatValue() * 0.001F);
            } else {
               local0_15.fatigueLevel = Math.max(0.0F, local0_15.fatigueLevel - 0.01F);
            }
         }
      }
   }

   private static final void onRender$lambda$14(AimAssistFeature local0_13, RenderGameWorldEvent var1) {
      Intrinsics.checkNotNullParameter(var1, "<unused var>");
      if (local0_13.inInvalidState() || local0_13.target == null || !local0_13.shouldAssist) {
         local0_13.smoothedDeltaYaw = 0.0F;
         local0_13.smoothedDeltaPitch = 0.0F;
      } else if (SimpleFeature.mc.field_1724 != null) {
         class_746 local2_3 = SimpleFeature.mc.field_1724;
         if (!local0_13.onlyOnWeapon.get() || local0_13.isWeapon()) {
            float local3_4 = class_310.method_1551().method_60646().method_60636();
            float local4_5 = local0_13.strength.value.floatValue() * local0_13.assistStrength.value.floatValue();
            local4_5 *= 1.0F - local0_13.fatigueLevel * 0.3F;
            float local5_6 = RangesKt.coerceIn(1.0F / local0_13.smoothness.value.floatValue(), 0.1F, 2.5F);
            local0_13.tremorDecay = Math.max(0.0F, local0_13.tremorDecay - MathUtil.random() / (float)100);
            local0_13.microTremorX *= 0.8F;
            local0_13.microTremorY *= 0.8F;
            local0_13.updateFocusDrift(local3_4);
            local0_13.smoothedDeltaYaw = local0_13.smoothedDeltaYaw + (local0_13.rawDeltaYaw - local0_13.smoothedDeltaYaw) * local5_6;
            local0_13.smoothedDeltaPitch = local0_13.smoothedDeltaPitch + (local0_13.rawDeltaPitch - local0_13.smoothedDeltaPitch) * local5_6;
            float local6_7 = local0_13.calculateHumanLikeSpeed(local0_13.smoothedDeltaYaw, local4_5);
            float local7_8 = local0_13.calculateHumanLikeSpeed(local0_13.smoothedDeltaPitch, local4_5 * local0_13.verticalFactor.value.floatValue());
            float local8_9 = local0_13.smoothedDeltaYaw > 0.0F ? 1.0F : -1.0F;
            float local9_10 = local0_13.smoothedDeltaPitch > 0.0F ? 1.0F : -1.0F;
            float local10_11 = RangesKt.coerceIn(local6_7 * local8_9 * local3_4, -Math.abs(local0_13.smoothedDeltaYaw), Math.abs(local0_13.smoothedDeltaYaw));
            float local11_12 = RangesKt.coerceIn(
               local7_8 * local9_10 * local3_4, -Math.abs(local0_13.smoothedDeltaPitch), Math.abs(local0_13.smoothedDeltaPitch)
            );
            if (local0_13.saccadeActive && System.currentTimeMillis() < local0_13.saccadeEndTime) {
               float local12_0 = 0.5F * (1.0F - (float)(local0_13.saccadeEndTime - System.currentTimeMillis()) / 50.0F);
               local10_11 += (MathUtil.random() - 0.5F) * local12_0;
               local11_12 += (MathUtil.random() - 0.5F) * local12_0;
            }

            if (Math.abs(local0_13.smoothedDeltaYaw) < 10.0F && Math.abs(local0_13.smoothedDeltaPitch) < 10.0F) {
               float local12_1 = (1.0F - local0_13.humanAccuracy.value.floatValue()) * 0.3F * (1.0F - local0_13.aimStability.value.floatValue());
               local10_11 += local0_13.microTremorX * local12_1 * local0_13.tremorDecay;
               local11_12 += local0_13.microTremorY * local12_1 * local0_13.tremorDecay;
               Pair local13_2 = local0_13.generatePhysiologicalTremor();
               local10_11 += ((Number)local13_2.getFirst()).floatValue();
               local11_12 += ((Number)local13_2.getSecond()).floatValue();
            }

            local10_11 += ((Number)local0_13.eyePositionOffset.getFirst()).floatValue() * local0_13.focusDrift.value.floatValue();
            local11_12 += ((Number)local0_13.eyePositionOffset.getSecond()).floatValue() * local0_13.focusDrift.value.floatValue();
            local10_11 = DeltaGCDProcessor.INSTANCE.getFixedRotation(local10_11);
            local11_12 = DeltaGCDProcessor.INSTANCE.getFixedRotation(local11_12);
            local2_3.method_36456(local2_3.method_36454() + local10_11);
            local2_3.method_36457(RangesKt.coerceIn(local2_3.method_36455() + local11_12, -90.0F, 90.0F));
            local0_13.smoothedDeltaYaw -= local10_11;
            local0_13.smoothedDeltaPitch -= local11_12;
         }
      }
   }

   private static final Set weaponKeywords_delegate$lambda$17() {
      String[] var0 = new String[]{"sword", "trident", "_axe", "mace", "stick", "pickaxe", "shovel"};
      return SetsKt.setOf(var0);
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t¨\u0006\u000b"},
      d2 = {"Lru/melonity/feature/features/modules/combat/AimAssistFeature$Companion;", "", "<init>", "()V", "", "", "weaponKeywords$delegate", "Lkotlin/Lazy;", "getWeaponKeywords", "()Ljava/util/Set;", "weaponKeywords", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   public static final class Companion {
      private Companion() {
      }

      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }

      private final Set<String> getWeaponKeywords() {
         Lazy var1 = AimAssistFeature.weaponKeywords$delegate;
         return (Set<String>)var1.getValue();
      }
   }
}
