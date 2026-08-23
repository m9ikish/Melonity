package ru.melonity.feature.features.modules.movement;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1738;
import net.minecraft.class_1740;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_744;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.melonity.feature.features.modules.movement.velocity.VelocityMode;
import ru.melonity.feature.features.modules.movement.velocity.impl.CancelMode;
import ru.melonity.feature.features.modules.movement.velocity.impl.CompensationMode;
import ru.melonity.feature.features.modules.movement.velocity.impl.GrimOldMode;
import ru.melonity.feature.features.modules.movement.velocity.impl.IntaveOldMode;
import ru.melonity.feature.features.modules.movement.velocity.impl.MatrixNewMode;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.EventPlayerMove;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.minecraft.MovementUtil;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000f\u0010\u0005\u001a\u00020\u0004H\u0003¢\u0006\u0004\b\u0005\u0010\u0003J\u000f\u0010\u0006\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0006\u0010\u0003J\u000f\u0010\b\u001a\u00020\u0007H\u0003¢\u0006\u0004\b\b\u0010\tJ\u000f\u0010\n\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\n\u0010\tJ\u000f\u0010\u000b\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\u000b\u0010\tJ\u000f\u0010\f\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\f\u0010\tJ\u000f\u0010\r\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\r\u0010\tJ\u000f\u0010\u000e\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\u000e\u0010\tJ\u000f\u0010\u000f\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\u000f\u0010\tJ\u001f\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0007H\u0016¢\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u001f\u0010\u001a\u001a\n \u0019*\u0004\u0018\u00010\u00140\u00148\u0006¢\u0006\f\n\u0004\b\u001a\u0010\u0016\u001a\u0004\b\u001b\u0010\u0018R\u0017\u0010\u001c\u001a\u00020\u00148\u0006¢\u0006\f\n\u0004\b\u001c\u0010\u0016\u001a\u0004\b\u001d\u0010\u0018R \u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001e8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b!\u0010\"R\u0018\u0010#\u001a\u0004\u0018\u00010 8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b#\u0010$R\u001f\u0010'\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010&0%8\u0006¢\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*R\u001f\u0010,\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010+0%8\u0006¢\u0006\f\n\u0004\b,\u0010(\u001a\u0004\b-\u0010*R\u001f\u0010/\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010.0%8\u0006¢\u0006\f\n\u0004\b/\u0010(\u001a\u0004\b0\u0010*R\u001f\u00102\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001010%8\u0006¢\u0006\f\n\u0004\b2\u0010(\u001a\u0004\b3\u0010*R\u0014\u00104\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b4\u0010\t¨\u00065"},
   d2 = {"Lru/melonity/feature/features/modules/movement/NoVelocityFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "", "initializeModes", "updateCurrentMode", "", "shouldHandle", "()Z", "handleNether", "handleSneaking", "handleForwardMove", "handleMoving", "handleAir", "falsePosCheck", "value", "silent", "setEnabled", "(ZZ)V", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "modeSetting", "Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "getModeSetting", "()Lru/melonity/ui/menu/clickui/view/feature/setting/ListSetting;", "kotlin.jvm.PlatformType", "compensationMode", "getCompensationMode", "onlyInActions", "getOnlyInActions", "", "", "Lru/melonity/feature/features/modules/movement/velocity/VelocityMode;", "velocityModes", "Ljava/util/Map;", "currentMode", "Lru/melonity/feature/features/modules/movement/velocity/VelocityMode;", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/UpdateLivingEvent;", "onUpdate", "Lru/melonity/util/event/EventListener;", "getOnUpdate", "()Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/PacketEvent;", "onPacket", "getOnPacket", "Lru/melonity/util/event/events/EventPlayerMove;", "onMotion", "getOnMotion", "Lru/melonity/util/event/events/EventInput;", "onInput", "getOnInput", "isNether", "melonity_client"}
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\nNoVelocityFeature.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NoVelocityFeature.kt\nru/melonity/feature/features/modules/movement/NoVelocityFeature\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,167:1\n1761#2,3:168\n*S KotlinDebug\n*F\n+ 1 NoVelocityFeature.kt\nru/melonity/feature/features/modules/movement/NoVelocityFeature\n*L\n152#1:168,3\n*E\n"})
public final class NoVelocityFeature extends SimpleFeature {
   @NotNull
   private final ListSetting modeSetting;
   private final ListSetting compensationMode;
   @NotNull
   private final ListSetting onlyInActions;
   @NotNull
   private final Map<String, VelocityMode> velocityModes;
   @Nullable
   private VelocityMode currentMode;
   @NotNull
   private final EventListener<UpdateLivingEvent> onUpdate;
   @NotNull
   private final EventListener<PacketEvent> onPacket;
   @NotNull
   private final EventListener<EventPlayerMove> onMotion;
   @NotNull
   private final EventListener<EventInput> onInput;

   public NoVelocityFeature() {
      super("NoVelocity", MenuCategory.MOVEMENT);
      ListSetting.ModeType var10004 = ListSetting.ModeType.SINGLE;
      String[] var1 = new String[]{"novelocity.cancel", "Intave Old", "Grim Old", "Matrix New", "novelocity.compensation"};
      this.modeSetting = new ListSetting("novelocity.mode", var10004, "novelocity.cancel", var1);
      var10004 = ListSetting.ModeType.SINGLE;
      var1 = new String[]{
         "novelocity.compensation.default", "novelocity.compensation.jump", "novelocity.compensation.directional", "novelocity.compensation.all"
      };
      this.compensationMode = new ListSetting("novelocity.compensationmode", var10004, "novelocity.compensation.default", var1)
         .setVisible(() -> compensationMode$lambda$0(this));
      var10004 = ListSetting.ModeType.SINGLE;
      var1 = new String[]{
         "novelocity.actions.none",
         "novelocity.actions.nether",
         "novelocity.actions.sneaking",
         "novelocity.actions.forward",
         "novelocity.actions.moving",
         "novelocity.actions.air"
      };
      this.onlyInActions = new ListSetting("novelocity.actions", var10004, "novelocity.actions.none", var1);
      this.velocityModes = new LinkedHashMap<>();
      MenuFeatureView[] var4 = new MenuFeatureView[]{this.modeSetting, this.compensationMode, this.onlyInActions};
      this.settings(var4);
      this.initializeModes();
      this.updateCurrentMode();
      this.onUpdate = event -> onUpdate$lambda$1(this, event);
      this.onPacket = event -> onPacket$lambda$2(this, event);
      this.onMotion = event -> onMotion$lambda$3(this, event);
      this.onInput = event -> onInput$lambda$4(this, event);
   }

   @NotNull
   public final ListSetting getModeSetting() {
      return this.modeSetting;
   }

   public final ListSetting getCompensationMode() {
      return this.compensationMode;
   }

   @NotNull
   public final ListSetting getOnlyInActions() {
      return this.onlyInActions;
   }

   private final void initializeModes() {
      // The keys have to be the literal entries of modeSetting, updateCurrentMode()
      // looks the selected mode up by modeSetting.mode.get(0).
      this.velocityModes.put("novelocity.cancel", new CancelMode());
      this.velocityModes.put("Intave Old", new IntaveOldMode());
      this.velocityModes.put("Grim Old", new GrimOldMode());
      this.velocityModes.put("Matrix New", new MatrixNewMode());
      this.velocityModes.put("novelocity.compensation", new CompensationMode(this.compensationMode));
   }

   private final void updateCurrentMode() {
      String local1_0 = this.modeSetting.mode.get(0);
      if (!Intrinsics.areEqual(this.currentMode != null ? this.currentMode.getName() : null, local1_0)) {
         if (this.currentMode != null) {
            this.currentMode.onDisable();
         }

         this.currentMode = this.velocityModes.get(local1_0);
         if (this.isEnabled()) {
            if (this.currentMode != null) {
               this.currentMode.onEnable();
            }
         }
      }
   }

   @NotNull
   public final EventListener<UpdateLivingEvent> getOnUpdate() {
      return this.onUpdate;
   }

   @NotNull
   public final EventListener<PacketEvent> getOnPacket() {
      return this.onPacket;
   }

   @NotNull
   public final EventListener<EventPlayerMove> getOnMotion() {
      return this.onMotion;
   }

   @NotNull
   public final EventListener<EventInput> getOnInput() {
      return this.onInput;
   }

   private final boolean shouldHandle() {
      return this.handleNether()
         && this.handleSneaking()
         && this.handleForwardMove()
         && this.handleMoving()
         && this.handleAir()
         && this.falsePosCheck();
   }

   private final boolean handleNether() {
      return this.onlyInActions.contains("novelocity.actions.nether") ? this.isNether() : true;
   }

   private final boolean handleSneaking() {
      if (this.onlyInActions.contains("novelocity.actions.sneaking")) {
         return SimpleFeature.mc.field_1724 != null ? SimpleFeature.mc.field_1724.method_5715() : false;
      } else {
         return true;
      }
   }

   private final boolean handleForwardMove() {
      if (!this.onlyInActions.contains("novelocity.actions.forward")) {
         return true;
      } else {
         boolean var10000;
         if (SimpleFeature.mc.field_1724 != null && SimpleFeature.mc.field_1724.field_3913 != null) {
            class_744 local1_0 = SimpleFeature.mc.field_1724.field_3913;
            var10000 = local1_0.field_3910 && !local1_0.field_3908 && !local1_0.field_3906;
         } else {
            var10000 = false;
         }

         return var10000;
      }
   }

   private final boolean handleMoving() {
      return this.onlyInActions.contains("novelocity.actions.moving") ? MovementUtil.isMoving() : true;
   }

   private final boolean handleAir() {
      if (this.onlyInActions.contains("novelocity.actions.air")) {
         return SimpleFeature.mc.field_1724 != null ? !SimpleFeature.mc.field_1724.method_24828() : false;
      } else {
         return true;
      }
   }

   private final boolean falsePosCheck() {
      boolean var10000;
      if (SimpleFeature.mc.field_1724 != null) {
         class_746 local1_0 = SimpleFeature.mc.field_1724;
         var10000 = !local1_0.method_5799() && !local1_0.method_5771() && !local1_0.method_6128();
      } else {
         var10000 = false;
      }

      return var10000;
   }

   private final boolean isNether() {
      if (SimpleFeature.mc.field_1724 != null) {
         Iterable var10000 = SimpleFeature.mc.field_1724.method_5661();
         if (var10000 != null) {
            Iterable local1_2 = var10000;
            if (!(local1_2 instanceof Collection) || !((Collection)local1_2).isEmpty()) {
               for (Object local4_1 : local1_2) {
                  boolean var8;
                  label29: {
                     class_1799 local5_0 = (class_1799)local4_1;
                     if (local5_0 != null && local5_0.method_7909() instanceof class_1738) {
                        class_1792 var7 = local5_0.method_7909();
                        Intrinsics.checkNotNull(var7, "null cannot be cast to non-null type net.minecraft.item.ArmorItem");
                        if (((class_1738)var7).method_7686() == class_1740.field_21977) {
                           var8 = true;
                           break label29;
                        }
                     }

                     var8 = false;
                  }

                  if (var8) {
                     return true;
                  }
               }

               return false;
            }

            return false;
         }
      }

      return false;
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      if (value) {
         if (this.currentMode != null) {
            this.currentMode.onEnable();
         }
      } else if (this.currentMode != null) {
         this.currentMode.onDisable();
      }
   }

   private static final boolean compensationMode$lambda$0(NoVelocityFeature this$0) {
      return this$0.modeSetting.contains("novelocity.compensation");
   }

   private static final void onUpdate$lambda$1(NoVelocityFeature this$0, UpdateLivingEvent event) {
      if (this$0.shouldHandle()) {
         this$0.updateCurrentMode();
         if (this$0.currentMode != null) {
            this$0.currentMode.onUpdate(event);
         }
      }
   }

   private static final void onPacket$lambda$2(NoVelocityFeature this$0, PacketEvent event) {
      if (this$0.shouldHandle()) {
         this$0.updateCurrentMode();
         boolean local2_0 = this$0.currentMode != null ? this$0.currentMode.onPacket(event) : false;
         if (local2_0 && event != null) {
            event.cancel();
         }
      }
   }

   private static final void onMotion$lambda$3(NoVelocityFeature this$0, EventPlayerMove event) {
      if (this$0.shouldHandle()) {
         this$0.updateCurrentMode();
         if (this$0.currentMode != null) {
            this$0.currentMode.onPlayerMove(event);
         }
      }
   }

   private static final void onInput$lambda$4(NoVelocityFeature this$0, EventInput event) {
      if (this$0.shouldHandle()) {
         this$0.updateCurrentMode();
         if (this$0.currentMode != null) {
            this$0.currentMode.onInput(event);
         }
      }
   }
}
