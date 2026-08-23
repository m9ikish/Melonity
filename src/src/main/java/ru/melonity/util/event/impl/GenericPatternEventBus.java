package ru.melonity.util.event.impl;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_310;
import ru.melonity.feature.Feature;
import ru.melonity.util.event.Event;
import ru.melonity.util.event.EventBus;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.annotation.EventPriority;

@Environment(EnvType.CLIENT)
public final class GenericPatternEventBus implements EventBus {
   private final Object2ObjectOpenHashMap<Class<? extends Event>, Int2ObjectOpenHashMap<ObjectArrayList<GenericPatternEventBus.ListenerData>>> eventMap = new Object2ObjectOpenHashMap();
   private final Reference2ObjectOpenHashMap<Object, ObjectArrayList<GenericPatternEventBus.WeakListenerReference>> ownerMap = new Reference2ObjectOpenHashMap();
   /** How many times each distinct listener failure has been seen; see {@link #reportListenerFailure}. */
   private static final ConcurrentHashMap<String, Long> FAILURE_COUNTS = new ConcurrentHashMap<>();

   @Override
   public void listen(Object listener) {
      Field[] local2_10 = listener.getClass().getDeclaredFields();
      ObjectArrayList<GenericPatternEventBus.WeakListenerReference> local3_11 = null;

      for (Field local7_9 : local2_10) {
         if (EventListener.class.isAssignableFrom(local7_9.getType())) {
            local7_9.setAccessible(true);
            int local8_8 = local7_9.isAnnotationPresent(EventPriority.class) ? local7_9.getAnnotation(EventPriority.class).priority() : 0;

            try {
               EventListener<Event> local9_1 = (EventListener<Event>)local7_9.get(listener);
               if (local9_1 != null && local7_9.getGenericType() instanceof ParameterizedType local11_0) {
                  Class<? extends Event> local12_4 = (Class<? extends Event>)local11_0.getActualTypeArguments()[0];
                  String local13_5 = listener.getClass().getSimpleName() + "." + local7_9.getName();
                  Int2ObjectOpenHashMap<ObjectArrayList<GenericPatternEventBus.ListenerData>> local14_6 = (Int2ObjectOpenHashMap<ObjectArrayList<GenericPatternEventBus.ListenerData>>)this.eventMap
                     .get(local12_4);
                  if (local14_6 == null) {
                     local14_6 = new Int2ObjectOpenHashMap();
                     this.eventMap.put(local12_4, local14_6);
                  }

                  ObjectArrayList<GenericPatternEventBus.ListenerData> local15_7 = (ObjectArrayList<GenericPatternEventBus.ListenerData>)local14_6.get(local8_8);
                  if (local15_7 == null) {
                     local15_7 = new ObjectArrayList();
                     local14_6.put(local8_8, local15_7);
                  }

                  local15_7.add(new GenericPatternEventBus.ListenerData(listener, local9_1, local8_8, local13_5));
                  if (local3_11 == null) {
                     local3_11 = new ObjectArrayList();
                  }

                  local3_11.add(new GenericPatternEventBus.WeakListenerReference(listener, local12_4, local9_1));
               }
            } catch (IllegalAccessException var16) {
            }
         }
      }

      if (local3_11 != null && !local3_11.isEmpty()) {
         this.ownerMap.put(listener, local3_11);
      }
   }

   @Override
   public void unlisten(Object listener) {
      ObjectArrayList<GenericPatternEventBus.WeakListenerReference> local2_3 = (ObjectArrayList<GenericPatternEventBus.WeakListenerReference>)this.ownerMap
         .remove(listener);
      if (local2_3 != null) {
         ObjectListIterator var3 = local2_3.iterator();

         while (var3.hasNext()) {
            GenericPatternEventBus.WeakListenerReference local4_2 = (GenericPatternEventBus.WeakListenerReference)var3.next();
            Int2ObjectOpenHashMap<ObjectArrayList<GenericPatternEventBus.ListenerData>> local5_1 = (Int2ObjectOpenHashMap<ObjectArrayList<GenericPatternEventBus.ListenerData>>)this.eventMap
               .get(local4_2.eventClass);
            if (local5_1 != null) {
               ObjectIterator var6 = local5_1.values().iterator();

               while (var6.hasNext()) {
                  ObjectArrayList<GenericPatternEventBus.ListenerData> local7_0 = (ObjectArrayList<GenericPatternEventBus.ListenerData>)var6.next();
                  local7_0.removeIf(d -> d.listener() == local4_2.listener);
               }

               if (local5_1.values().stream().allMatch(ObjectArrayList::isEmpty)) {
                  this.eventMap.remove(local4_2.eventClass);
               }
            }
         }

         this.cleanup();
      }
   }

   @Override
   public void fire(Event event) {
      if (class_310.method_1551().field_1724 != null) {
         Int2ObjectOpenHashMap<ObjectArrayList<GenericPatternEventBus.ListenerData>> local2_6 = (Int2ObjectOpenHashMap<ObjectArrayList<GenericPatternEventBus.ListenerData>>)this.eventMap
            .get(event.getClass());
         if (local2_6 != null && !local2_6.isEmpty()) {
            int[] local3_7 = local2_6.keySet().toIntArray();
            Arrays.sort(local3_7);

            for (int local7_5 : local3_7) {
               ObjectArrayList<GenericPatternEventBus.ListenerData> local8_4 = (ObjectArrayList<GenericPatternEventBus.ListenerData>)local2_6.get(local7_5);
               ObjectListIterator var9 = local8_4.iterator();

               while (var9.hasNext()) {
                  GenericPatternEventBus.ListenerData local10_3 = (GenericPatternEventBus.ListenerData)var9.next();
                  if (!(local10_3.owner() instanceof Feature local12_0) || local12_0.isEnabled()) {
                     try {
                        local10_3.listener().callEvent(event);
                     } catch (Throwable var13) {
                        reportListenerFailure(local10_3, var13);
                     }
                  }
               }
            }
         }
      }
   }

   /**
    * A render or tick listener that throws does so on every single frame. The old handler was a
    * bare {@code printStackTrace()}, so one broken listener wrote a full stack trace 60+ times a
    * second - a single session produced a 429 MB latest.log and the disk writes alone stalled the
    * render thread. Print the first occurrence of each distinct failure in full, then keep only a
    * periodic one-line reminder with the running count.
    */
   private static void reportListenerFailure(GenericPatternEventBus.ListenerData data, Throwable failure) {
      Throwable root = failure;
      while (root.getCause() != null && root.getCause() != root) {
         root = root.getCause();
      }

      StackTraceElement[] frames = failure.getStackTrace();
      String key = data.debugInfo()
         + '|'
         + failure.getClass().getName()
         + '|'
         + root.getClass().getName()
         + '|'
         + (frames.length == 0 ? "?" : frames[0].toString());

      long seen = FAILURE_COUNTS.merge(key, 1L, Long::sum);
      if (seen == 1L) {
         System.err.println("[Melonity] Listener " + data.debugInfo() + " threw:");
         failure.printStackTrace();
      } else if (seen % 600L == 0L) {
         System.err.println(
            "[Melonity] Listener " + data.debugInfo() + " has now thrown " + failure.getClass().getSimpleName() + " " + seen + " times (trace suppressed)"
         );
      }
   }

   private void cleanup() {
      this.ownerMap
         .entrySet()
         .removeIf(
            e -> {
               ObjectArrayList<GenericPatternEventBus.WeakListenerReference> local2_3 = (ObjectArrayList<GenericPatternEventBus.WeakListenerReference>)e.getValue();
               local2_3.removeIf(r -> !r.alive());
               ObjectListIterator var3 = local2_3.iterator();

               while (var3.hasNext()) {
                  GenericPatternEventBus.WeakListenerReference local4_2 = (GenericPatternEventBus.WeakListenerReference)var3.next();
                  if (!local4_2.alive()) {
                     Int2ObjectOpenHashMap<ObjectArrayList<GenericPatternEventBus.ListenerData>> local5_1 = (Int2ObjectOpenHashMap<ObjectArrayList<GenericPatternEventBus.ListenerData>>)this.eventMap
                        .get(local4_2.eventClass);
                     if (local5_1 != null) {
                        ObjectIterator var6 = local5_1.values().iterator();

                        while (var6.hasNext()) {
                           ObjectArrayList<GenericPatternEventBus.ListenerData> local7_0 = (ObjectArrayList<GenericPatternEventBus.ListenerData>)var6.next();
                           local7_0.removeIf(d -> d.listener() == local4_2.listener);
                        }

                        if (local5_1.values().stream().allMatch(ObjectArrayList::isEmpty)) {
                           this.eventMap.remove(local4_2.eventClass);
                        }
                     }
                  }
               }

               return local2_3.isEmpty();
            }
         );
   }

   @Environment(EnvType.CLIENT)
   private static record ListenerData(Object owner, EventListener<Event> listener, int priority, String debugInfo) {
   }

   @Environment(EnvType.CLIENT)
   private static final class WeakListenerReference {
      final WeakReference<Object> ownerRef;
      final Class<? extends Event> eventClass;
      final EventListener<Event> listener;

      WeakListenerReference(Object owner, Class<? extends Event> eventClass, EventListener<Event> listener) {
         this.ownerRef = new WeakReference<>(owner);
         this.eventClass = eventClass;
         this.listener = listener;
      }

      boolean alive() {
         return this.ownerRef.get() != null;
      }
   }
}
