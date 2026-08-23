package ru.melonity.util.event;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EventBenchmark {
   final Map<String, EventBenchmark.EventStats> eventStatistics = new ConcurrentHashMap<>();
   final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
      Thread local1_0 = new Thread(r, "EventBenchmark-Reporter");
      local1_0.setDaemon(true);
      return local1_0;
   });
   volatile boolean running = true;

   public EventBenchmark() {
      this.startReporting();
   }

   public void recordEventCall(Class<? extends Event> eventClass, String listenerInfo, long durationNanos) {
      String local5_0 = eventClass.getSimpleName() + " -> " + listenerInfo;
      this.eventStatistics.computeIfAbsent(local5_0, k -> new EventBenchmark.EventStats()).recordCall(durationNanos);
   }

   private void startReporting() {
      this.scheduler.scheduleAtFixedRate(this::writeReport, 15L, 15L, TimeUnit.SECONDS);
   }

   private void writeReport() {
      if (this.running && !this.eventStatistics.isEmpty()) {
         try {
            String local1_3 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            StringBuilder local2_4 = new StringBuilder();
            local2_4.append("=== Event Performance Report - ").append(local1_3).append(" ===\n");
            local2_4.append(
               String.format("%-60s %8s %12s %12s %12s %12s %12s\n", "Event -> Listener", "Calls", "Avg(ns)", "Min(ns)", "Max(ns)", "P95(ns)", "Total(ms)")
            );
            local2_4.append("-".repeat(150)).append("\n");

            for (Entry<String, EventBenchmark.EventStats> local5_2 : this.eventStatistics
               .entrySet()
               .stream()
               .filter(entry -> entry.getValue().totalCalls.get() > 0L)
               .sorted((e1, e2) -> Long.compare(e2.getValue().totalTime.get(), e1.getValue().totalTime.get()))
               .toList()) {
               String local6_0 = local5_2.getKey();
               EventBenchmark.EventStats local7_1 = local5_2.getValue();
               local2_4.append(
                  String.format(
                     "%-60s %8d %12d %12d %12d %12.0f %12.2f\n",
                     local6_0.length() > 60 ? local6_0.substring(0, 57) + "..." : local6_0,
                     local7_1.totalCalls.get(),
                     local7_1.getAverageTime(),
                     local7_1.minTime.get() == Long.MAX_VALUE ? 0L : local7_1.minTime.get(),
                     local7_1.maxTime.get(),
                     local7_1.getPercentile95(),
                     (double)local7_1.totalTime.get() / 1000000.0
                  )
               );
            }

            local2_4.append("\n");
            Files.write(Paths.get("event_times.txt"), local2_4.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
         } catch (IOException var8) {
            System.err.println("Failed to write benchmark report: " + var8.getMessage());
         }
      }
   }

   public void clearStatistics() {
      this.eventStatistics.clear();
   }

   public void stop() {
      this.running = false;
      this.writeReport();
      this.scheduler.shutdown();

      try {
         if (!this.scheduler.awaitTermination(5L, TimeUnit.SECONDS)) {
            this.scheduler.shutdownNow();
         }
      } catch (InterruptedException var2) {
         this.scheduler.shutdownNow();
         Thread.currentThread().interrupt();
      }
   }

   @Environment(EnvType.CLIENT)
   private static class EventStats {
      final AtomicLong totalCalls = new AtomicLong(0L);
      final AtomicLong totalTime = new AtomicLong(0L);
      final AtomicLong maxTime = new AtomicLong(0L);
      final AtomicLong minTime = new AtomicLong(Long.MAX_VALUE);
      final Queue<Long> recentTimes = new ArrayDeque<>();
      final Object recentTimesLock = new Object();

      void recordCall(long duration) {
         this.totalCalls.incrementAndGet();
         this.totalTime.addAndGet(duration);
         this.maxTime.updateAndGet(current -> Math.max(current, duration));
         this.minTime.updateAndGet(current -> Math.min(current, duration));
         synchronized (this.recentTimesLock) {
            this.recentTimes.offer(duration);
            if (this.recentTimes.size() > 1000) {
               this.recentTimes.poll();
            }
         }
      }

      long getAverageTime() {
         long local1_0 = this.totalCalls.get();
         return local1_0 == 0L ? 0L : this.totalTime.get() / local1_0;
      }

      double getPercentile95() {
         synchronized (this.recentTimesLock) {
            if (this.recentTimes.isEmpty()) {
               return 0.0;
            } else {
               List<Long> local2_0 = new ArrayList<>(this.recentTimes);
               local2_0.sort(Long::compareTo);
               int local3_1 = (int)Math.ceil((double)local2_0.size() * 0.95) - 1;
               return local3_1 >= 0 ? (double)local2_0.get(local3_1).longValue() : 0.0;
            }
         }
      }
   }
}
