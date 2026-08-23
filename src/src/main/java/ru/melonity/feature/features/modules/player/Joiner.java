package ru.melonity.feature.features.modules.player;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2797;
import ru.melonity.feature.features.joiner.AnarchyJoin;
import ru.melonity.feature.features.joiner.GriefJoin;
import ru.melonity.feature.features.joiner.JoinLogic;
import ru.melonity.feature.features.joiner.ReallyWorldJoin;
import ru.melonity.feature.features.joiner.STDueldsJoin;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.UpdateLivingEvent;

@Environment(EnvType.CLIENT)
public class Joiner extends SimpleFeature {
   private final ListSetting mode = new ListSetting("global.mode", ListSetting.ModeType.SINGLE, "ReallyWorld", "ReallyWorld", "FunTime", "Duels SpookyTime");
   private final NumberSetting griefSelection = new NumberSetting("rwjoiner.grief", value -> String.format("%d", value.intValue()), 1, 28, 1)
      .setVisible(() -> this.mode.contains("ReallyWorld"));
   private final ListSetting funtimeMode = new ListSetting("FunTime Mode", ListSetting.ModeType.SINGLE, "Anarchy", "Anarchy", "Grief")
      .setVisible(() -> this.mode.contains("FunTime"));
   private final ListSetting funtimeAnarchySelection = new ListSetting(
         "ftjoiner.anarchy",
         ListSetting.ModeType.SINGLE,
         "101",
         "101",
         "102",
         "103",
         "104",
         "105",
         "106",
         "107",
         "108",
         "109",
         "110",
         "111",
         "112",
         "201",
         "202",
         "203",
         "204",
         "205",
         "206",
         "207",
         "208",
         "209",
         "210",
         "211",
         "212",
         "213",
         "214",
         "215",
         "216",
         "217",
         "218",
         "219",
         "220",
         "221",
         "222",
         "223",
         "224",
         "225",
         "226",
         "227",
         "228",
         "229",
         "230",
         "231",
         "232",
         "301",
         "302",
         "303",
         "304",
         "305",
         "306",
         "307",
         "308",
         "309",
         "310",
         "311",
         "312",
         "313",
         "314",
         "315",
         "316",
         "317",
         "318",
         "319",
         "501",
         "502",
         "503",
         "504",
         "505",
         "506",
         "507",
         "508",
         "509",
         "510",
         "511",
         "601",
         "602",
         "603",
         "604",
         "605",
         "606"
      )
      .setVisible(() -> this.mode.contains("FunTime") && this.funtimeMode.contains("Anarchy"));
   private final NumberSetting funtimeGriefSelection = new NumberSetting("ftjoiner.grief", value -> String.format("%d", value.intValue()), 1, 5, 1)
      .setVisible(() -> this.mode.contains("FunTime") && this.funtimeMode.contains("Grief"));
   private final List<JoinLogic> JOINERS;
   private final EventListener<UpdateLivingEvent> onUpdate = event -> {
      if (mc.field_1724 != null) {
         AtomicBoolean local2_0 = new AtomicBoolean(false);
         this.findJoinersForName(this.mode.getMode().getFirst()).forEach(joinLogic -> {
            if (joinLogic.need()) {
               local2_0.set(true);
            }

            joinLogic.tick();
         });
         if (!local2_0.get()) {
            this.setEnabled(false);
         }
      }
   };
   private final EventListener<PacketEvent> onPacket = event -> {
      if (mc.field_1724 != null) {
         if (event.getPacket() instanceof class_2797 local2_1) {
            this.findJoinersForName(this.mode.getMode().getFirst()).forEach(joinLogic -> joinLogic.handleMessage(local2_1.comp_945()));
         }
      }
   };

   public Joiner() {
      super("Joiner", MenuCategory.PLAYER);
      this.setting(this.mode);
      this.setting(this.griefSelection);
      this.settings(new MenuFeatureView[]{this.funtimeMode, this.funtimeAnarchySelection, this.funtimeGriefSelection});
      this.JOINERS = List.of(
         new ReallyWorldJoin(this.griefSelection),
         new STDueldsJoin(),
         new AnarchyJoin(this.funtimeMode, this.funtimeAnarchySelection),
         new GriefJoin(this.funtimeMode, this.funtimeGriefSelection)
      );
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      super.setEnabled(value, silent);
      if (mc.field_1724 != null) {
         if (value) {
            this.findJoinersForName(this.mode.getMode().getFirst()).forEach(JoinLogic::enabled);
         }
      }
   }

   private List<JoinLogic> findJoinersForName(String name) {
      return this.JOINERS.stream().filter(joinLogic -> joinLogic.name().equals(name)).toList();
   }
}
