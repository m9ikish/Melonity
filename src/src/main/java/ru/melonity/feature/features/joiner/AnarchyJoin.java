package ru.melonity.feature.features.joiner;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.util.minecraft.PlayerUtil;

@Environment(EnvType.CLIENT)
public class AnarchyJoin extends BaseJoinLogic {
   private final ListSetting funtimeMode;
   private final ListSetting funtimeAnarchySelection;

   public AnarchyJoin(ListSetting funtimeMode, ListSetting funtimeAnarchySelection) {
      super("FunTime");
      this.funtimeMode = funtimeMode;
      this.funtimeAnarchySelection = funtimeAnarchySelection;
   }

   @Override
   public void tick() {
      if (this.mc.field_1724.field_6012 % 28 == 0) {
         PlayerUtil.sendPlayerMessage("/an".concat(this.funtimeAnarchySelection.getMode().getFirst()));
      }
   }

   @Override
   public void handleMessage(String message) {
   }

   @Override
   public boolean need() {
      return this.mc.field_1687.method_8428().method_1151().isEmpty() && this.funtimeMode.contains("Anarchy");
   }
}
