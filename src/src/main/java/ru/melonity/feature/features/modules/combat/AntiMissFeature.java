package ru.melonity.feature.features.modules.combat;

import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_239;
import net.minecraft.class_239.class_240;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.DoAttackEvent;
import ru.melonity.util.modules.SyncUtil;

@Environment(EnvType.CLIENT)
public class AntiMissFeature extends SimpleFeature {
   private final ListSetting antiMissSetting = new ListSetting(
      "antimiss.settings", ListSetting.ModeType.MULTI, "antimiss.onlycrit", "antimiss.onlycrit", "antimiss.donthitcd"
   );
   private final EventListener<DoAttackEvent> onAttack = event -> {
      class_239 local2_0 = event.getCrosshairTarget();
      if (local2_0 != null) {
         boolean local3_1 = this.antiMissSetting.contains("antimiss.onlycrit");
         boolean local4_2 = this.antiMissSetting.contains("antimiss.donthitcd");
         boolean local5_3 = mc.field_1724.field_6017 > 0.0F && (!mc.field_1724.method_24828() || mc.field_1724.field_3913.field_3904);
         boolean local6_4 = SyncUtil.outedCooldownWithSync(false, 0.0F, 0.0F);
         if (local2_0.method_17783() != class_240.field_1331) {
            event.setCancelled(true);
         } else {
            if ((local3_1 && !local5_3 || local4_2 && !local6_4) && local2_0.method_17783() == class_240.field_1331) {
               event.setCancelled(true);
            }
         }
      }
   };

   public AntiMissFeature() {
      super("AntiMiss", MenuCategory.COMBAT);
      this.settings(new MenuFeatureView[]{this.antiMissSetting});
   }

   @Generated
   public ListSetting getAntiMissSetting() {
      return this.antiMissSetting;
   }

   @Generated
   public EventListener<DoAttackEvent> getOnAttack() {
      return this.onAttack;
   }
}
