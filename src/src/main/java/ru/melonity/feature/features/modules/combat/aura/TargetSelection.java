package ru.melonity.feature.features.modules.combat.aura;

import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1297;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1531;
import net.minecraft.class_1657;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_638;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.service.friend.FriendService;
import ru.melonity.util.minecraft.PlayerUtil;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001+B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001d\u0010\t\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u000e\u0010\u000fJ5\u0010\u0015\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u00102\u001d\u0010\u0014\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0012\u0012\u0004\u0012\u00028\u00000\u0011¢\u0006\u0002\b\u0013H\u0082\b¢\u0006\u0004\b\u0015\u0010\u0016J'\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0017*\b\u0012\u0004\u0012\u00020\u00060\u00172\u0006\u0010\f\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\u0018\u0010\u0019J\u0015\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001a\u001a\u00020\r¢\u0006\u0004\b\u001c\u0010\u001dJ\u0015\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u001a\u001a\u00020\r¢\u0006\u0004\b\u001f\u0010 J\u0017\u0010#\u001a\u00020\"2\u0006\u0010!\u001a\u00020\rH\u0002¢\u0006\u0004\b#\u0010$R4\u0010*\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00060&j\b\u0012\u0004\u0012\u00020\u0006`'0\u00170%*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)¨\u0006,"},
   d2 = {"Lru/melonity/feature/features/modules/combat/aura/TargetSelection;", "", "<init>", "()V", "Lru/melonity/feature/features/modules/combat/aura/SearchConditions;", "search", "Lnet/minecraft/class_1297;", "entity", "", "isValidTarget", "(Lru/melonity/feature/features/modules/combat/aura/SearchConditions;Lnet/minecraft/class_1297;)Z", "", "mode", "Lnet/minecraft/class_1309;", "find", "(Ljava/lang/String;Lru/melonity/feature/features/modules/combat/aura/SearchConditions;)Lnet/minecraft/class_1309;", "T", "Lkotlin/Function1;", "Lru/melonity/feature/features/modules/combat/aura/TargetSelection$EntitySelector;", "Lkotlin/ExtensionFunctionType;", "block", "entitySelection", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "", "smartSort", "(Ljava/util/List;Ljava/lang/String;)Ljava/util/List;", "target", "", "deltaFov", "(Lnet/minecraft/class_1309;)D", "Lorg/joml/Vector4f;", "calculateRotationFromCamera", "(Lnet/minecraft/class_1309;)Lorg/joml/Vector4f;", "entity1", "", "armor", "(Lnet/minecraft/class_1309;)I", "Lkotlin/Function0;", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "getTargetingStrategy", "(Ljava/lang/String;)Lkotlin/jvm/functions/Function0;", "targetingStrategy", "EntitySelector", "melonity_client"}
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\nTargetSelection.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TargetSelection.kt\nru/melonity/feature/features/modules/combat/aura/TargetSelection\n+ 2 TargetSelection.kt\nru/melonity/feature/features/modules/combat/aura/TargetSelection$EntitySelector\n+ 3 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,138:1\n55#1:139\n67#2:140\n68#2:142\n477#3:141\n1803#4,3:143\n*S KotlinDebug\n*F\n+ 1 TargetSelection.kt\nru/melonity/feature/features/modules/combat/aura/TargetSelection\n*L\n48#1:139\n50#1:140\n50#1:142\n50#1:141\n89#1:143,3\n*E\n"})
public final class TargetSelection {
   @NotNull
   public static final TargetSelection INSTANCE = new TargetSelection();

   private TargetSelection() {
   }

   public final boolean isValidTarget(@NotNull SearchConditions search, @NotNull class_1297 entity) {
      Intrinsics.checkNotNullParameter(search, "search");
      Intrinsics.checkNotNullParameter(entity, "entity");
      if (!(entity instanceof class_1309)) {
         return false;
      } else if (!((class_1309)entity).method_5805() || entity == Feature.mc.field_1724 || ((class_1309)entity).method_6032() == 0.0F) {
         return false;
      } else if (entity instanceof class_1531) {
         return false;
      } else if ((!(entity instanceof class_1657) || this.armor((class_1309)entity) != 0 || search.getTargetSetting().contains("legitaura.nakeds"))
         && (!(entity instanceof class_1657) && !((class_1309)entity).method_5767() || search.getTargetSetting().contains("legitaura.players"))) {
         if (entity instanceof class_1308 && !search.getTargetSetting().contains("legitaura.mobs")) {
            return false;
         } else {
            return Melonity.INSTANCE.componentManager.service(FriendService.class).hasFriend(((class_1309)entity).method_5477().getString())
               ? false
               : RayCast.INSTANCE.fire((class_1309)entity, search.getDistance() + (double)3.0F, search.getIgnoreWalls());
         }
      } else {
         return false;
      }
   }

   @Nullable
   public final class_1309 find(@NotNull String mode, @NotNull SearchConditions search) {
      Intrinsics.checkNotNullParameter(mode, "mode");
      Intrinsics.checkNotNullParameter(search, "search");
      TargetSelection.EntitySelector local5_3 = new TargetSelection.EntitySelector();
      local5_3.from(TargetSelection::find$lambda$3$lambda$0);
      Function1 local8_2 = it -> find$lambda$3$lambda$1(search, (class_1309)it);
      Sequence local10_0 = local5_3.entities;
      Sequence var10001 = SequencesKt.filter(local10_0, TargetSelection$find$lambda$3$$inlined$where$1.INSTANCE);
      Intrinsics.checkNotNull(
         var10001, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>"
      );
      local5_3.entities = SequencesKt.filter(var10001, local8_2);
      local5_3.sortBy(() -> find$lambda$3$lambda$2(mode));
      return (class_1309)local5_3.selectFirst();
   }

   private final <T> T entitySelection(Function1<? super TargetSelection.EntitySelector<T>, ? extends T> block) {
      return (T)((Function1)block).invoke(new TargetSelection.EntitySelector());
   }

   private final List<class_1297> smartSort(List<? extends class_1297> $this$smartSort, String mode) {
      Iterable var10000 = $this$smartSort;
      Iterable local3_4 = (Iterable)this.getTargetingStrategy(mode).invoke();
      Comparator local4_5 = new TargetSelection$smartSort$$inlined$compareBy$1();
      Iterable var12 = var10000;
      Comparator local6_3 = local4_5;

      for (Object local8_2 : local3_4) {
         Comparator local9_1 = (Comparator)local8_2;
         Comparator var13 = local6_3.thenComparing(local9_1);
         Intrinsics.checkNotNullExpressionValue(var13, "thenComparing(...)");
         local6_3 = var13;
      }

      return CollectionsKt.sortedWith(var12, (Comparator)local6_3);
   }

   private final Function0<List<Comparator<class_1297>>> getTargetingStrategy(String $this$targetingStrategy) {
      switch ($this$targetingStrategy.hashCode()) {
         case -2052094639:
            if ($this$targetingStrategy.equals("aura.ts_health")) {
               return TargetSelection::_get_targetingStrategy_$lambda$15;
            }
            break;
         case -1059719688:
            if ($this$targetingStrategy.equals("aura.ts_fov")) {
               return TargetSelection::_get_targetingStrategy_$lambda$17;
            }
            break;
         case -471441356:
            if ($this$targetingStrategy.equals("aura.ts_smart")) {
               return TargetSelection::_get_targetingStrategy_$lambda$11;
            }
            break;
         case 920142526:
            if ($this$targetingStrategy.equals("aura.ts_simplify")) {
               return TargetSelection::_get_targetingStrategy_$lambda$19;
            }
            break;
         case 1508362715:
            if ($this$targetingStrategy.equals("aura.ts_dist")) {
               return TargetSelection::_get_targetingStrategy_$lambda$13;
            }
      }

      return TargetSelection::_get_targetingStrategy_$lambda$20;
   }

   public final double deltaFov(@NotNull class_1309 target) {
      Intrinsics.checkNotNullParameter(target, "target");
      Vector4f local2_0 = this.calculateRotationFromCamera(target);
      float local3_1 = local2_0.z;
      float local4_2 = local2_0.w;
      return Math.sqrt((double)(local3_1 * local3_1 + local4_2 * local4_2));
   }

   @NotNull
   public final Vector4f calculateRotationFromCamera(@NotNull class_1309 target) {
      Intrinsics.checkNotNullParameter(target, "target");
      class_243 var10000 = PointSearch.INSTANCE.getClosestTargetPoint((class_1297)target).getPoint();
      class_746 var10001 = Feature.mc.field_1724;
      Intrinsics.checkNotNull(Feature.mc.field_1724);
      class_243 local2_0 = var10000.method_1020(var10001.method_5836(Feature.mc.method_60646().method_60637(true)));
      float local3_1 = (float)class_3532.method_15338(Math.toDegrees(Math.atan2(local2_0.field_1350, local2_0.field_1352)) - (double)90.0F);
      float local4_2 = (float)(
         -Math.toDegrees(Math.atan2(local2_0.field_1351, Math.sqrt(Math.pow(local2_0.field_1352, 2.0) + Math.pow(local2_0.field_1350, 2.0))))
      );
      var10001 = class_310.method_1551().field_1724;
      Intrinsics.checkNotNull(var10001);
      float local5_3 = class_3532.method_15393(local3_1 - var10001.method_36454());
      var10001 = class_310.method_1551().field_1724;
      Intrinsics.checkNotNull(var10001);
      float local6_4 = local4_2 - var10001.method_36455();
      return new Vector4f(local3_1, local4_2, local5_3, local6_4);
   }

   private final int armor(class_1309 entity1) {
      return entity1 instanceof class_1657 ? PlayerUtil.getArmor(entity1) : entity1.method_6096();
   }

   private static final Iterable find$lambda$3$lambda$0() {
      class_638 var10000 = Feature.mc.field_1687;
      Intrinsics.checkNotNull(Feature.mc.field_1687);
      Iterable var0 = var10000.method_18112();
      Intrinsics.checkNotNullExpressionValue(var0, "getEntities(...)");
      return var0;
   }

   private static final boolean find$lambda$3$lambda$1(SearchConditions $search, class_1309 it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return INSTANCE.isValidTarget($search, (class_1297)it);
   }

   private static final String find$lambda$3$lambda$2(String $mode) {
      return $mode;
   }

   private static final List _get_targetingStrategy_$lambda$11() {
      Comparator[] var0 = new Comparator[]{
         new TargetSelection$_get_targetingStrategy_$lambda$11$$inlined$compareBy$1(),
         new TargetSelection$_get_targetingStrategy_$lambda$11$$inlined$compareBy$2(),
         new TargetSelection$_get_targetingStrategy_$lambda$11$$inlined$compareBy$3(),
         new TargetSelection$_get_targetingStrategy_$lambda$11$$inlined$compareBy$4()
      };
      return CollectionsKt.listOf(var0);
   }

   private static final List _get_targetingStrategy_$lambda$13() {
      return CollectionsKt.listOf(new TargetSelection$_get_targetingStrategy_$lambda$13$$inlined$compareBy$1());
   }

   private static final List _get_targetingStrategy_$lambda$15() {
      return CollectionsKt.listOf(new TargetSelection$_get_targetingStrategy_$lambda$15$$inlined$compareBy$1());
   }

   private static final List _get_targetingStrategy_$lambda$17() {
      return CollectionsKt.listOf(new TargetSelection$_get_targetingStrategy_$lambda$17$$inlined$compareBy$1());
   }

   private static final List _get_targetingStrategy_$lambda$19() {
      return CollectionsKt.listOf(new TargetSelection$_get_targetingStrategy_$lambda$19$$inlined$compareBy$1());
   }

   private static final List _get_targetingStrategy_$lambda$20() {
      return CollectionsKt.emptyList();
   }

   public static final List access$smartSort(TargetSelection $this, List $this$smartSort, String mode) {
      return $this.smartSort($this$smartSort, mode);
   }

   public static final int access$armor(TargetSelection $this, class_1309 entity1) {
      return $this.armor(entity1);
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J!\u0010\n\u001a\u00020\t2\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\u0004\b\n\u0010\u000bJ5\u0010\u0010\u001a\u00020\t\"\n\b\u0001\u0010\f\u0018\u0001*\u00020\u00072\u0014\b\b\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u000e0\rH\u0086\bø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0011J\u001b\u0010\u0014\u001a\u00020\t2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0005¢\u0006\u0004\b\u0014\u0010\u000bJ\u000f\u0010\u0015\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\u00178\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0018\u0010\u0019R\"\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000e0\r8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u0016\u0010\u001c\u001a\u00020\u00128\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u001c\u0010\u001d\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001e"},
      d2 = {"Lru/melonity/feature/features/modules/combat/aura/TargetSelection$EntitySelector;", "T", "", "<init>", "()V", "Lkotlin/Function0;", "", "Lnet/minecraft/class_1297;", "provider", "", "from", "(Lkotlin/jvm/functions/Function0;)V", "R", "Lkotlin/Function1;", "", "filter", "where", "(Lkotlin/jvm/functions/Function1;)V", "", "modeProvider", "sortBy", "selectFirst", "()Ljava/lang/Object;", "Lkotlin/sequences/Sequence;", "entities", "Lkotlin/sequences/Sequence;", "predicate", "Lkotlin/jvm/functions/Function1;", "sortMode", "Ljava/lang/String;", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   @SourceDebugExtension({"SMAP\nTargetSelection.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TargetSelection.kt\nru/melonity/feature/features/modules/combat/aura/TargetSelection$EntitySelector\n+ 2 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,138:1\n477#2:139\n1#3:140\n*S KotlinDebug\n*F\n+ 1 TargetSelection.kt\nru/melonity/feature/features/modules/combat/aura/TargetSelection$EntitySelector\n*L\n67#1:139\n*E\n"})
   private static final class EntitySelector<T> {
      @NotNull
      private Sequence<? extends class_1297> entities = SequencesKt.emptySequence();
      @NotNull
      private Function1<? super class_1297, Boolean> predicate = TargetSelection.EntitySelector::predicate$lambda$0;
      @NotNull
      private String sortMode = "";

      public EntitySelector() {
      }

      public final void from(@NotNull Function0<? extends Iterable<? extends class_1297>> provider) {
         Intrinsics.checkNotNullParameter(provider, "provider");
         this.entities = CollectionsKt.asSequence((Iterable)provider.invoke());
      }

      public final void sortBy(@NotNull Function0<String> modeProvider) {
         Intrinsics.checkNotNullParameter(modeProvider, "modeProvider");
         this.sortMode = (String)modeProvider.invoke();
      }

      @Nullable
      public final T selectFirst() {
         List var2 = SequencesKt.toList(this.entities);
         List var1 = !var2.isEmpty() ? var2 : null;
         class_1297 var10000 = var1 != null
            ? (
               var1.size() == 1
                  ? (class_1297)CollectionsKt.first(var1)
                  : (class_1297)CollectionsKt.firstOrNull(TargetSelection.INSTANCE.smartSort(var1, this.sortMode))
            )
            : null;
         if (var10000 == null) {
            var10000 = null;
         }

         return (T)var10000;
      }

      private static final boolean predicate$lambda$0(class_1297 it) {
         Intrinsics.checkNotNullParameter(it, "it");
         return true;
      }
   }
}
