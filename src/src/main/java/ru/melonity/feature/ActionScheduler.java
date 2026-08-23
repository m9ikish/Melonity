package ru.melonity.feature;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateLivingEvent;
import ru.melonity.util.minecraft.MinecraftInstance;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001?B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001d\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0003¢\u0006\u0004\b\b\u0010\tJ\u001f\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0004H\u0007¢\u0006\u0004\b\u000e\u0010\u000fJ%\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\u0010H\u0007¢\u0006\u0004\b\u000e\u0010\u0011J\u001f\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\nH\u0007¢\u0006\u0004\b\u0013\u0010\u0014J\u001f\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0004H\u0007¢\u0006\u0004\b\u0016\u0010\u0017J%\u0010\u0016\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\u0010H\u0007¢\u0006\u0004\b\u0016\u0010\u0018J\u001f\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\nH\u0007¢\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001b\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\u0004H\u0007¢\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u001d\u001a\u00020\u0004H\u0003¢\u0006\u0004\b\u001d\u0010\u001eJ!\u0010 \u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\u001f\u001a\u00020\u0004H\u0007¢\u0006\u0004\b \u0010!J'\u0010 \u001a\u00020\u00042\b\b\u0002\u0010\u001f\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\u0010H\u0007¢\u0006\u0004\b \u0010\"J'\u0010&\u001a\u00020%2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\n0#2\b\b\u0002\u0010\u001f\u001a\u00020\u0004H\u0007¢\u0006\u0004\b&\u0010'J9\u0010&\u001a\u00020%2\b\b\u0002\u0010\u001f\u001a\u00020\u00042\u001e\u0010$\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00020\r0\u00100#\"\b\u0012\u0004\u0012\u00020\r0\u0010H\u0007¢\u0006\u0004\b&\u0010(J\u000f\u0010)\u001a\u00020\u0004H\u0007¢\u0006\u0004\b)\u0010\u001eJ\u000f\u0010*\u001a\u00020\rH\u0003¢\u0006\u0004\b*\u0010\u0003J\u001f\u0010+\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0004H\u0007¢\u0006\u0004\b+\u0010\u000fR\u0014\u0010,\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b,\u0010-R\u0014\u0010.\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b.\u0010-R\u0014\u0010/\u001a\u00020\u00048\u0002X\u0082T¢\u0006\u0006\n\u0004\b/\u0010-R\"\u00100\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00060#8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b0\u00101R\"\u00102\u001a\u00020\u00048\u0006@\u0006X\u0086\u000e¢\u0006\u0012\n\u0004\b2\u0010-\u001a\u0004\b3\u0010\u001e\"\u0004\b4\u00105R\u001d\u00108\u001a\b\u0012\u0004\u0012\u000207068\u0006¢\u0006\f\n\u0004\b8\u00109\u001a\u0004\b:\u0010;R\u001a\u0010=\u001a\b\u0012\u0004\u0012\u00020\n0<8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b=\u0010>¨\u0006@"},
   d2 = {"Lru/melonity/feature/ActionScheduler;", "Lru/melonity/util/minecraft/MinecraftInstance;", "<init>", "()V", "", "i", "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;", "Lru/melonity/feature/ActionScheduler$ScheduledTask;", "slot", "(I)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;", "Ljava/lang/Runnable;", "action", "delay", "", "schedule", "(Ljava/lang/Runnable;I)V", "Lkotlin/Function0;", "(ILkotlin/jvm/functions/Function0;)V", "tick", "scheduleAt", "(ILjava/lang/Runnable;)V", "", "safeSchedule", "(Ljava/lang/Runnable;I)Z", "(ILkotlin/jvm/functions/Function0;)Z", "safeScheduleAt", "(ILjava/lang/Runnable;)Z", "hasScheduledAction", "(I)Z", "findLastScheduledTick", "()I", "gap", "queue", "(Ljava/lang/Runnable;I)I", "(ILkotlin/jvm/functions/Function0;)I", "", "actions", "", "queueMultiple", "([Ljava/lang/Runnable;I)[I", "(I[Lkotlin/jvm/functions/Function0;)[I", "getScheduledTasksCount", "process", "scheduleOnce", "WHEEL_BITS", "I", "WHEEL_SIZE", "WHEEL_MASK", "wheel", "[Lit/unimi/dsi/fastutil/objects/ObjectArrayList;", "currentTick", "getCurrentTick", "setCurrentTick", "(I)V", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/UpdateLivingEvent;", "onUpdate", "Lru/melonity/util/event/EventListener;", "getOnUpdate", "()Lru/melonity/util/event/EventListener;", "", "onceActions", "Ljava/util/Set;", "ScheduledTask", "melonity_client"}
)
@Environment(EnvType.CLIENT)
@SourceDebugExtension({"SMAP\nActionScheduler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActionScheduler.kt\nru/melonity/feature/ActionScheduler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,202:1\n1761#2,3:203\n*S KotlinDebug\n*F\n+ 1 ActionScheduler.kt\nru/melonity/feature/ActionScheduler\n*L\n84#1:203,3\n*E\n"})
public final class ActionScheduler extends MinecraftInstance {
   @NotNull
   public static final ActionScheduler INSTANCE = new ActionScheduler();
   private static final int WHEEL_BITS = 12;
   private static final int WHEEL_SIZE = 4096;
   private static final int WHEEL_MASK = 4095;
   @NotNull
   private static final ObjectArrayList<ActionScheduler.ScheduledTask>[] wheel = new ObjectArrayList[4096];
   private static int currentTick;
   @NotNull
   private static final EventListener<UpdateLivingEvent> onUpdate = ActionScheduler::onUpdate$lambda$5;
   @NotNull
   private static final Set<Runnable> onceActions = new LinkedHashSet<>();

   private ActionScheduler() {
   }

   /**
    * Returns the wheel bucket that owns tick {@code i}, creating it on first use.
    * The tick is folded into the wheel with {@link #WHEEL_MASK}, so callers may pass either an
    * absolute tick or an already folded index - masking is idempotent.
    * Callers must hold the {@code wheel} monitor.
    */
   private final ObjectArrayList<ActionScheduler.ScheduledTask> slot(int i) {
      int index = i & WHEEL_MASK;
      ObjectArrayList<ActionScheduler.ScheduledTask> bucket = wheel[index];
      if (bucket == null) {
         bucket = new ObjectArrayList<>();
         wheel[index] = bucket;
      }

      return bucket;
   }

   public final int getCurrentTick() {
      return currentTick;
   }

   public final void setCurrentTick(int value) {
      currentTick = value;
   }

   /**
    * Queues {@code action} to be executed {@code delay} game ticks from now.
    * A delay that would land on (or before) the tick currently being processed is bumped to the
    * next tick, so an action can never be dropped into an already drained bucket.
    */
   @JvmStatic
   public static final void schedule(@NotNull Runnable action, int delay) {
      Intrinsics.checkNotNullParameter(action, "action");
      synchronized (wheel) {
         int tick = currentTick + delay;
         if (tick <= currentTick) {
            tick = currentTick + 1;
         }

         INSTANCE.slot(tick).add(new ActionScheduler.ScheduledTask(tick, action));
      }
   }

   @JvmStatic
   public static final void schedule(int delay, @NotNull Function0<Unit> action) {
      Intrinsics.checkNotNullParameter(action, "action");
      schedule(() -> schedule$lambda$0(action), delay);
   }

   @JvmStatic
   public static final void scheduleAt(int tick, @NotNull Runnable action) {
      Intrinsics.checkNotNullParameter(action, "action");
      schedule(action, tick - currentTick);
   }

   /**
    * Like {@link #schedule(Runnable, int)}, but refuses to stack a second action on a tick that is
    * already taken. Returns {@code true} when the action has been queued.
    */
   @JvmStatic
   public static final boolean safeSchedule(@NotNull Runnable action, int delay) {
      Intrinsics.checkNotNullParameter(action, "action");
      synchronized (wheel) {
         if (hasScheduledAction(delay)) {
            return false;
         } else {
            schedule(action, delay);
            return true;
         }
      }
   }

   @JvmStatic
   public static final boolean safeSchedule(int delay, @NotNull Function0<Unit> action) {
      Intrinsics.checkNotNullParameter(action, "action");
      return safeSchedule(() -> safeSchedule$lambda$1(action), delay);
   }

   @JvmStatic
   public static final boolean safeScheduleAt(int tick, @NotNull Runnable action) {
      Intrinsics.checkNotNullParameter(action, "action");
      synchronized (wheel) {
         return safeSchedule(action, tick - currentTick);
      }
   }

   /** {@code true} when at least one action is already queued for tick {@code currentTick + delay}. */
   @JvmStatic
   public static final boolean hasScheduledAction(int delay) {
      synchronized (wheel) {
         int tick = currentTick + delay;
         ObjectArrayList<ActionScheduler.ScheduledTask> bucket = wheel[tick & WHEEL_MASK];
         if (bucket == null) {
            return false;
         } else {
            for (int i = 0; i < bucket.size(); i++) {
               if (bucket.get(i).getTick() == tick) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   /** Highest tick that currently holds an action, never lower than {@link #currentTick}. */
   private final int findLastScheduledTick() {
      synchronized (wheel) {
         int last = currentTick;

         for (ObjectArrayList<ActionScheduler.ScheduledTask> bucket : wheel) {
            if (bucket != null) {
               for (int i = 0; i < bucket.size(); i++) {
                  int tick = bucket.get(i).getTick();
                  if (tick > last) {
                     last = tick;
                  }
               }
            }
         }

         return last;
      }
   }

   /**
    * Appends {@code action} to the end of the queue: it lands {@code gap} ticks behind the last
    * tick that already holds an action (or behind the current tick when the wheel is empty).
    * Returns the absolute tick the action was placed on, which can be fed back into
    * {@link #scheduleAt(int, Runnable)} / {@link #safeScheduleAt(int, Runnable)}.
    */
   @JvmStatic
   public static final int queue(@NotNull Runnable action, int gap) {
      Intrinsics.checkNotNullParameter(action, "action");
      int step = gap < 1 ? 1 : gap;
      synchronized (wheel) {
         int tick = INSTANCE.findLastScheduledTick() + step;
         scheduleAt(tick, action);
         return tick;
      }
   }

   @JvmStatic
   public static final int queue(int gap, @NotNull Function0<Unit> action) {
      Intrinsics.checkNotNullParameter(action, "action");
      return queue(() -> queue$lambda$3(action), gap);
   }

   @JvmStatic
   @NotNull
   public static final int[] queueMultiple(@NotNull Runnable[] actions, int gap) {
      Intrinsics.checkNotNullParameter(actions, "actions");
      int[] local2_1 = new int[actions.length];
      int local3_0 = 0;

      for (int var4 = actions.length; local3_0 < var4; local3_0++) {
         local2_1[local3_0] = queue(actions[local3_0], gap);
      }

      return local2_1;
   }

   @JvmStatic
   @NotNull
   public static final int[] queueMultiple(int gap, @NotNull Function0<Unit>... actions) {
      Intrinsics.checkNotNullParameter(actions, "actions");
      int[] local2_1 = new int[actions.length];
      int local3_0 = 0;

      for (int var4 = actions.length; local3_0 < var4; local3_0++) {
         int var5 = local3_0;
         local2_1[local3_0] = queue(gap, () -> queueMultiple$lambda$4(actions, var5));
      }

      return local2_1;
   }

   @JvmStatic
   public static final int getScheduledTasksCount() {
      int local0_1 = 0;
      ObjectArrayList[] var1 = wheel;
      int var2 = 0;

      for (int var3 = var1.length; var2 < var3; var2++) {
         ObjectArrayList local4_0 = var1[var2];
         if (local4_0 != null) {
            local0_1 += local4_0.size();
         }
      }

      return local0_1;
   }

   @NotNull
   public final EventListener<UpdateLivingEvent> getOnUpdate() {
      return onUpdate;
   }

   /**
    * Advances the wheel by one tick and runs everything that is due.
    * Due tasks are detached from the wheel while the monitor is held and executed afterwards, so an
    * action is free to schedule new work (or throw) without corrupting the bucket it came from.
    */
   private final void process() {
      ObjectArrayList<ActionScheduler.ScheduledTask> due = null;
      synchronized (wheel) {
         int tick = ++currentTick;
         ObjectArrayList<ActionScheduler.ScheduledTask> bucket = wheel[tick & WHEEL_MASK];
         if (bucket != null && !bucket.isEmpty()) {
            int kept = 0;

            for (int i = 0; i < bucket.size(); i++) {
               ActionScheduler.ScheduledTask task = bucket.get(i);
               if (task.getTick() <= tick) {
                  if (due == null) {
                     due = new ObjectArrayList<>();
                  }

                  due.add(task);
               } else {
                  bucket.set(kept++, task);
               }
            }

            bucket.size(kept);
         }
      }

      if (due != null) {
         for (int i = 0; i < due.size(); i++) {
            due.get(i).getAction().run();
         }
      }
   }

   /**
    * Schedules {@code action} unless an equal action is already pending: the action is registered in
    * {@link #onceActions} until it has run, which collapses repeated calls made while the previous
    * one is still queued.
    */
   @JvmStatic
   public static final void scheduleOnce(@NotNull Runnable action, int delay) {
      Intrinsics.checkNotNullParameter(action, "action");
      synchronized (onceActions) {
         if (!onceActions.add(action)) {
            return;
         }
      }

      schedule(() -> scheduleOnce$lambda$6(action), delay);
   }

   private static final void schedule$lambda$0(Function0 $action) {
      $action.invoke();
   }

   private static final void safeSchedule$lambda$1(Function0 $action) {
      $action.invoke();
   }

   private static final void queue$lambda$3(Function0 $action) {
      $action.invoke();
   }

   private static final Unit queueMultiple$lambda$4(Function0[] $actions, int $i) {
      $actions[$i].invoke();
      return Unit.INSTANCE;
   }

   private static final void onUpdate$lambda$5(UpdateLivingEvent var0) {
      Intrinsics.checkNotNullParameter(var0, "<unused var>");
      INSTANCE.process();
   }

   private static final void scheduleOnce$lambda$6(Runnable $action) {
      try {
         $action.run();
      } finally {
         synchronized (onceActions) {
            onceActions.remove($action);
         }
      }
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\u0003\u001a\u00020\u00028\u0006¢\u0006\f\n\u0004\b\u0003\u0010\b\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0005\u001a\u00020\u00048\u0006¢\u0006\f\n\u0004\b\u0005\u0010\u000b\u001a\u0004\b\f\u0010\r¨\u0006\u000e"},
      d2 = {"Lru/melonity/feature/ActionScheduler$ScheduledTask;", "", "", "tick", "Ljava/lang/Runnable;", "action", "<init>", "(ILjava/lang/Runnable;)V", "I", "getTick", "()I", "Ljava/lang/Runnable;", "getAction", "()Ljava/lang/Runnable;", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   private static final class ScheduledTask {
      private final int tick;
      @NotNull
      private final Runnable action;

      public ScheduledTask(int tick, @NotNull Runnable action) {
         Intrinsics.checkNotNullParameter(action, "action");
         this.tick = tick;
         this.action = action;
      }

      public final int getTick() {
         return this.tick;
      }

      @NotNull
      public final Runnable getAction() {
         return this.action;
      }
   }
}
