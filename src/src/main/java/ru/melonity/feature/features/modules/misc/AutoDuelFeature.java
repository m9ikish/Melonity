package ru.melonity.feature.features.modules.misc;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_437;
import net.minecraft.class_640;
import net.minecraft.class_7439;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeaturePrefix;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.minecraft.PlayerUtil;
import ru.melonity.util.time.Timer;

@Environment(EnvType.CLIENT)
public class AutoDuelFeature extends SimpleFeature {
   private final ListSetting setting = new ListSetting(
      "Duels", ListSetting.ModeType.SINGLE, "Щит", "Щит", "Шипы", "Лук", "Тотемы", "Нодебафф", "Шары", "Классик", "Читерский рай", "Незеритка"
   );
   private static final Pattern pattern = Pattern.compile("^\\w{3,16}$");
   private final List<String> sent = Lists.newArrayList();
   private final Timer timer1 = Timer.create();
   private final Timer timer2 = Timer.create();
   private final Timer timer3 = Timer.create();
   private final Timer timer4 = Timer.create();
   private final Map<String, Integer> modeToSlotMap = this.createModeToSlotMap();
   private final EventListener<UpdateEvent> onUpdate = event -> {
      List<String> local2_7 = this.getOnlinePlayers();
      if (this.timer2.hasReached(400L * (long)local2_7.size())) {
         this.sent.clear();
         this.timer2.reset();
      }

      for (String local4_0 : local2_7) {
         if (!this.sent.contains(local4_0) && !local4_0.equals(mc.method_1548().method_1676()) && this.timer1.hasReached(250L)) {
            PlayerUtil.sendPlayerMessage("/duel " + local4_0);
            this.sent.add(local4_0);
            this.timer1.reset();
         }
      }

      if (mc.field_1724.field_7512 instanceof class_1707 local3_6) {
         class_437 local4_5 = mc.field_1755;
         if (local4_5 != null) {
            String local5_4 = local4_5.method_25440().getString();
            if (local5_4.contains("Выбор набора (1/1)")) {
               String local6_2 = this.setting.getMode().getLast();
               Integer local7_3 = this.modeToSlotMap.get(local6_2);
               if (local7_3 != null && this.timer3.hasReached(50L)) {
                  mc.field_1761.method_2906(local3_6.field_7763, local7_3, 2, class_1713.field_7794, mc.field_1724);
                  this.timer3.reset();
               }
            } else if (local5_4.contains("Настройка поединка") && this.timer4.hasReached(50L)) {
               mc.field_1761.method_2906(local3_6.field_7763, 0, 2, class_1713.field_7794, mc.field_1724);
               this.timer4.reset();
            }
         }
      }
   };
   private final EventListener<PacketEvent> onPacket = event -> {
      if (event.isReceive() && event.getPacket() instanceof class_7439 local3_1) {
         String local4_0 = local3_1.comp_763().getString().toLowerCase();
         if (local4_0.contains("начало") && local4_0.contains("через") && local4_0.contains("секунд!")
            || local4_0.equals("дуэли » во время поединка запрещено использовать команды")) {
            this.setEnabled(false);
         }
      }
   };

   public AutoDuelFeature() {
      super("AutoDuel", MenuCategory.MISC);
      this.prefix(new MenuFeaturePrefix[]{MenuFeaturePrefix.ReallyWorld});
      this.setting(this.setting);
   }

   private Map<String, Integer> createModeToSlotMap() {
      Map<String, Integer> local1_0 = new HashMap<>();
      local1_0.put("Щит", 0);
      local1_0.put("Шипы", 1);
      local1_0.put("Лук", 2);
      local1_0.put("Тотемы", 3);
      local1_0.put("Нодебафф", 4);
      local1_0.put("Шары", 5);
      local1_0.put("Классик", 6);
      local1_0.put("Читерский рай", 7);
      local1_0.put("Незеритка", 8);
      return local1_0;
   }

   private List<String> getOnlinePlayers() {
      return mc.method_1562()
         .method_2880()
         .stream()
         .map(class_640::method_2966)
         .<String>map(GameProfile::getName)
         .filter(profileName -> profileName != null && pattern.matcher(profileName).matches())
         .collect(Collectors.toList());
   }
}
