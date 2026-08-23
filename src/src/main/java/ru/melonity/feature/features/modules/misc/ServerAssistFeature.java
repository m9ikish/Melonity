package ru.melonity.feature.features.modules.misc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2797;
import net.minecraft.class_7439;
import net.minecraft.class_7472;
import ru.melonity.Melonity;
import ru.melonity.feature.ActionScheduler;
import ru.melonity.feature.features.modules.player.GuiMoveFeature;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.drag.Drag;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeaturePrefix;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.EventInput;
import ru.melonity.util.event.events.KeyEvent;
import ru.melonity.util.event.events.PacketEvent;
import ru.melonity.util.event.events.RenderGameWorldEvent;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.event.events.UpdateEvent;
import ru.melonity.util.inventory.InventoryUtil;
import ru.melonity.util.minecraft.KeyUtil;
import ru.melonity.util.minecraft.PlayerUtil;
import ru.melonity.util.modules.ServerAssistUtil;
import ru.melonity.util.render.BoxRenderer;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.time.Timer;

@Environment(EnvType.CLIENT)
public class ServerAssistFeature extends SimpleFeature {
   private final ListSetting mode = new ListSetting("global.mode", ListSetting.ModeType.SINGLE, "FunTime", "FunTime", "ReallyWorld", "HolyWorld");
   private final ListSetting settingFuntime = new ListSetting(
         "serverassist.funtime_assist", ListSetting.ModeType.MULTI, "serverassist.binds", "serverassist.autouse", "serverassist.chathelper"
      )
      .setVisible(() -> this.mode.contains("FunTime"));
   private final ListSetting autoUseFuntime = new ListSetting("serverassist.autouse_settings", ListSetting.ModeType.MULTI, "Божья аура")
      .setVisible(() -> this.mode.contains("FunTime") && this.settingFuntime.contains("serverassist.autouse"));
   private final BindSetting disorientation = new BindSetting("serverassist.disorientation", -1, new class_1799(class_1802.field_8449))
      .setVisible(() -> this.isModeAndSetting("FunTime", "serverassist.binds"));
   private final BindSetting trap = new BindSetting("serverassist.trap", -1, new class_1799(class_1802.field_22021))
      .setVisible(() -> this.isModeAndSetting("FunTime", "serverassist.binds"));
   private final BindSetting plast = new BindSetting("serverassist.plast", -1, new class_1799(class_1802.field_8551))
      .setVisible(() -> this.isModeAndSetting("FunTime", "serverassist.binds"));
   private final BindSetting sheerdust = new BindSetting("serverassist.sheerdust", -1, new class_1799(class_1802.field_8479))
      .setVisible(() -> this.isModeAndSetting("FunTime", "serverassist.binds"));
   private final BindSetting godaura = new BindSetting("serverassist.godaura", -1, new class_1799(class_1802.field_8614))
      .setVisible(() -> this.isModeAndSetting("FunTime", "serverassist.binds"));
   private final BindSetting freezball = new BindSetting("serverassist.freezball", -1, new class_1799(class_1802.field_8543))
      .setVisible(() -> this.isModeAndSetting("FunTime", "serverassist.binds"));
   private final BindSetting acid = new BindSetting("serverassist.acid", -1, new class_1799(class_1802.field_8436))
      .setVisible(() -> this.isModeAndSetting("FunTime", "serverassist.binds"));
   private final BindSetting burp = new BindSetting("serverassist.burp", -1, new class_1799(class_1802.field_8436))
      .setVisible(() -> this.isModeAndSetting("FunTime", "serverassist.binds"));
   private final BindSetting flash = new BindSetting("serverassist.flash", -1, new class_1799(class_1802.field_8436))
      .setVisible(() -> this.isModeAndSetting("FunTime", "serverassist.binds"));
   private final BindSetting flare = new BindSetting("serverassist.flare", -1, new class_1799(class_1802.field_8436))
      .setVisible(() -> this.isModeAndSetting("FunTime", "serverassist.binds"));
   private final List<BindSetting> bindSettingsFT = List.of(
      this.disorientation, this.trap, this.plast, this.sheerdust, this.godaura, this.freezball, this.acid, this.burp, this.flash, this.flare
   );
   private final ListSetting settingReallyWorld = new ListSetting(
         "serverassist.reallyworld_assist", ListSetting.ModeType.MULTI, "serverassist.blockbanofwords", "serverassist.chathelper"
      )
      .setVisible(() -> this.mode.contains("ReallyWorld"));
   private final ListSetting settingHolyWorld = new ListSetting(
         "serverassist.holyworld_assist",
         ListSetting.ModeType.MULTI,
         "serverassist.binds",
         "serverassist.highlight_shulker",
         "serverassist.autostopwhenteleporting",
         "serverassist.inventory_swap"
      )
      .setVisible(() -> this.mode.contains("HolyWorld"));
   private final BindSetting trapVHW = new BindSetting("serverassist.hw_trap_v", -1, new class_1799(class_1802.field_8662))
      .setVisible(() -> this.isModeAndSetting("HolyWorld", "serverassist.binds"));
   private final BindSetting trapHW = new BindSetting("serverassist.hw_trap", -1, new class_1799(class_1802.field_8882))
      .setVisible(() -> this.isModeAndSetting("HolyWorld", "serverassist.binds"));
   private final BindSetting snegHW = new BindSetting("serverassist.hw_sneg", -1, new class_1799(class_1802.field_8543))
      .setVisible(() -> this.isModeAndSetting("HolyWorld", "serverassist.binds"));
   private final BindSetting gulHW = new BindSetting("serverassist.hw_gul", -1, new class_1799(class_1802.field_8450))
      .setVisible(() -> this.isModeAndSetting("HolyWorld", "serverassist.binds"));
   private final BindSetting stanHW = new BindSetting("serverassist.hw_stan", -1, new class_1799(class_1802.field_8137))
      .setVisible(() -> this.isModeAndSetting("HolyWorld", "serverassist.binds"));
   private final List<BindSetting> bindSettingsHW = List.of(this.trapVHW, this.trapHW, this.snegHW, this.gulHW, this.stanHW);
   private final Drag drag = new Drag(841, 95.0F, 45.0F, 1.0F, 1.0F);
   private final Timer itemUseCooldown = Timer.create();
   private final List<class_2338> foundBlocks = new ArrayList<>();
   private boolean isUsing = false;
   private int originalSlot = -1;
   private boolean isTeleporting = false;
   private int teleportCounter = 0;
   private final EventListener<UpdateEvent> onUpdate = event -> {
      if (mc.field_1724 != null) {
         if (this.isModeAndSetting("FunTime", "serverassist.autouse")) {
            if (this.autoUseFuntime.contains("Божья аура")) {
               if (ServerAssistUtil.hasGodAuraEffects(mc.field_1724)
                  && !mc.field_1724.method_7357().method_7904(class_1802.field_8614)
                  && !this.isUsing
                  && this.itemUseCooldown.hasReached(500L)) {
                  this.useItemWithSwap(class_1802.field_8614, "Божья аура");
               }
            }
         }
      }
   };
   private final EventListener<PacketEvent> onPacket = event -> {
      if (event.isSend()) {
         this.handleOutgoingPackets(event);
      } else {
         this.handleIncomingPackets(event);
      }
   };
   private final EventListener<RenderGameWorldEvent> on3D = event -> {
      if (this.isModeAndSetting("HolyWorld", "serverassist.highlight_shulker")) {
         if (mc.field_1724 != null && mc.field_1687 != null) {
            class_1937 local2_4 = mc.field_1687;
            class_2338 local3_5 = mc.field_1724.method_24515();
            this.foundBlocks.clear();
            this.foundBlocks.addAll(ServerAssistUtil.findNearbyShulkers(local2_4, local3_5, 16, 32));

            for (class_2338 local5_3 : this.foundBlocks) {
               class_2248 local6_0 = local2_4.method_8320(local5_3).method_26204();
               Color local7_1 = ServerAssistUtil.getShulkerColor(local6_0);
               class_238 local8_2 = new class_238(local5_3);
               BoxRenderer.blockEsp(local8_2, local7_1, event.poseStack().method_23760().method_23761());
            }
         }
      }
   };
   private final EventListener<KeyEvent> onKey = event -> {
      if (!this.isUsing && this.itemUseCooldown.hasReached(500L)) {
         if (this.isModeAndSetting("FunTime", "serverassist.binds")) {
            this.handleFuntimeBinds(event);
         } else if (this.isModeAndSetting("HolyWorld", "serverassist.binds")) {
            this.handleHolyWorldBinds(event);
         }
      }
   };
   private final EventListener<RenderOverlayEvent> onRenderOverlay = event -> {
      if (this.mode.contains("FunTime") || this.mode.contains("HolyWorld")) {
         if (this.settingFuntime.contains("serverassist.binds") || this.settingHolyWorld.contains("serverassist.binds")) {
            RenderSystem local2_5 = Melonity.INSTANCE.getRenderSystem();
            MenuStyle local3_6 = Melonity.INSTANCE.styleManager.getStyle();
            Map<class_1799, String> local4_7 = Maps.newLinkedHashMap();

            for (BindSetting local7_1 : this.mode.contains("FunTime") ? this.bindSettingsFT : this.bindSettingsHW) {
               if (local7_1.bind != -1) {
                  String local8_0 = KeyUtil.getKeyName(local7_1.bind);
                  if (!"?".equals(local8_0) && !"NONE".equals(local8_0)) {
                     local4_7.put(local7_1.getHint(), local8_0);
                  }
               }
            }

            if (!local4_7.isEmpty()) {
               float local6_9 = (float)local4_7.size() * 32.0F;
               float local7_10 = this.drag.getX();
               float local8_11 = this.drag.getY();
               this.drag.setWidth(local6_9);
               this.drag.setHeight(14.0F);
               local2_5.addRect(local7_10, local8_11, local6_9, 14.0F, 4.0F, Color.decode("#181818"), event.matrixStack());
               float local9_12 = local7_10;
               List<Entry<class_1799, String>> local10_13 = Lists.newArrayList(local4_7.entrySet());

               for (int local11_4 = 0; local11_4 < local10_13.size(); local11_4++) {
                  Entry<class_1799, String> local12_2 = local10_13.get(local11_4);
                  float local13_3 = 15.0F;
                  if (local12_2.getKey() != null) {
                     event.matrixStack().method_22903();
                     event.matrixStack().method_46416(local9_12 + 2.5F + 5.33F, local8_11 + 2.0F + 5.33F, 0.0F);
                     event.matrixStack().method_22905(0.6667F, 0.6667F, 1.0F);
                     event.matrixStack().method_46416(-8.0F, -8.0F, 0.0F);
                     event.graphics().method_51427(local12_2.getKey(), 0, 0);
                     event.matrixStack().method_22909();
                  }

                  local2_5.addRect(local9_12 + 14.5F, local8_11 + 2.4F, local13_3 - 1.0F, 10.0F, 4.0F, local3_6.ascent(), event.matrixStack());
                  local2_5.addRect(local9_12 + 14.0F, local8_11 + 1.8F, local13_3, 10.0F, 4.0F, local3_6.keybind().keyBackground(), event.matrixStack());
                  local2_5.addText(Fonts.INTER_REGULAR_13, local12_2.getValue(), local9_12 + 19.0F, local8_11 + 6.0F, local3_6.ascent(), event.matrixStack());
                  if (local11_4 != 0) {
                     local2_5.addRect(local9_12 + 0.5F, local8_11, 0.5F, 14.0F, Color.decode("#222222"), event.matrixStack());
                  }

                  local9_12 += 32.0F;
               }
            }
         }
      }
   };
   private final EventListener<EventInput> onInput = event -> {
      if (this.isModeAndSetting("HolyWorld", "serverassist.autostopwhenteleporting") && this.isTeleporting) {
         if (this.teleportCounter > 0) {
            event.setStrafe(0.0F);
            event.setForward(0.0F);
            event.setJump(false);
            this.teleportCounter--;
         } else {
            this.isTeleporting = false;
         }
      }

      if (this.isUsing) {
         event.setForward(0.0F);
      }
   };

   public ServerAssistFeature() {
      super("ServerAssist", MenuCategory.MISC);
      this.prefix(new MenuFeaturePrefix[]{MenuFeaturePrefix.ReallyWorld, MenuFeaturePrefix.HolyWorld, MenuFeaturePrefix.Funtime});
      this.setting(this.mode);
      this.setting(this.settingFuntime);
      this.setting(this.autoUseFuntime);
      this.settings(
         new MenuFeatureView[]{
            this.disorientation, this.trap, this.plast, this.sheerdust, this.godaura, this.freezball, this.acid, this.burp, this.flash, this.flare
         }
      );
      this.setting(this.settingReallyWorld);
      this.setting(this.settingHolyWorld);
      this.settings(new MenuFeatureView[]{this.trapVHW, this.trapHW, this.snegHW, this.gulHW, this.stanHW});
   }

   private boolean isModeAndSetting(String modeName, String settingKey) {
      if (!this.mode.contains(modeName)) {
         return false;
      } else {
         return switch (modeName) {
            case "FunTime" -> this.settingFuntime.contains(settingKey);
            case "HolyWorld" -> this.settingHolyWorld.contains(settingKey);
            case "ReallyWorld" -> this.settingReallyWorld.contains(settingKey);
            default -> false;
         };
      }
   }

   private void handleIncomingPackets(PacketEvent event) {
      if (this.isModeAndSetting("HolyWorld", "serverassist.autostopwhenteleporting")) {
         if (event.getPacket() instanceof class_7439 local2_1) {
            String local3_0 = local2_1.comp_763().getString().toLowerCase();
            if (local3_0.contains("телепортация") || local3_0.contains("телепортирование")) {
               this.isTeleporting = true;
               this.teleportCounter = 80;
            }
         }
      }
   }

   private void handleOutgoingPackets(PacketEvent event) {
      if (event.getPacket() instanceof class_2797 local2_0
         && this.isModeAndSetting("ReallyWorld", "serverassist.blockbanofwords")
         && ServerAssistUtil.containsBanWord(local2_0.comp_945())) {
         event.cancel();
         Melonity.INSTANCE.getNotificationManager().add(new Notification("Server Assist", Melonity.INSTANCE.languageService.get("sa.msg"), "tips.png"));
      }

      if (event.getPacket() instanceof class_7472(String var5)) {
         if (this.isModeAndSetting("FunTime", "serverassist.chathelper")) {
            this.handleFunTimeCommands(event, var5);
         }

         if (this.isModeAndSetting("ReallyWorld", "serverassist.chathelper")) {
            this.handleReallyWorldCommands(event, var5);
         }
      }
   }

   private void handleFunTimeCommands(PacketEvent event, String cmd) {
      if ("ah me".equalsIgnoreCase(cmd) || "ah my".equalsIgnoreCase(cmd)) {
         event.cancel();
         PlayerUtil.sendPlayerMessage("ah " + mc.field_1724.method_7334().getName());
      } else if ("clan invest full".equals(cmd)) {
         event.cancel();
         PlayerUtil.sendPlayerMessage("/clan invest " + PlayerUtil.getBalance());
      }
   }

   private void handleReallyWorldCommands(PacketEvent event, String cmd) {
      if (cmd.toLowerCase().startsWith("rgc ")) {
         event.cancel();
         String local3_0 = cmd.substring(4).trim();
         PlayerUtil.sendPlayerMessage("/rg claim " + local3_0);
      }
   }

   private void handleFuntimeBinds(KeyEvent event) {
      int local2_0 = event.key();
      if (local2_0 == this.trap.bind) {
         this.useItemWithSwap(class_1802.field_22021, "Трапка");
      } else if (local2_0 == this.plast.bind) {
         this.useItemWithSwap(class_1802.field_8551, "Пласт");
      } else if (local2_0 == this.disorientation.bind) {
         this.useItemWithSwap(class_1802.field_8449, "Дезориентация");
      } else if (local2_0 == this.sheerdust.bind) {
         this.useItemWithSwap(class_1802.field_8479, "Явная пыль");
      } else if (local2_0 == this.godaura.bind) {
         this.useItemWithSwap(class_1802.field_8614, "Божья аура");
      } else if (local2_0 == this.freezball.bind) {
         this.useItemWithSwap(class_1802.field_8543, "Снежок заморозка");
      } else if (local2_0 == this.acid.bind) {
         this.useItemWithSwap(class_1802.field_8436, "Серная кислота");
      } else if (local2_0 == this.burp.bind) {
         this.useItemWithSwap(class_1802.field_8436, "Зелье отрыжки");
      } else if (local2_0 == this.flash.bind) {
         this.useItemWithSwap(class_1802.field_8436, "Моча Флеша");
      } else if (local2_0 == this.flare.bind) {
         this.useItemWithSwap(class_1802.field_8436, "Вспышка");
      }
   }

   private void handleHolyWorldBinds(KeyEvent event) {
      int local2_0 = event.key();
      if (local2_0 == this.trapVHW.bind) {
         this.useItemWithSwap(class_1802.field_8662, "Взрывная трапка");
      } else if (local2_0 == this.trapHW.bind) {
         this.useItemWithSwap(class_1802.field_8882, "Трапка");
      } else if (local2_0 == this.snegHW.bind) {
         this.useItemWithSwap(class_1802.field_8543, "Cнежок");
      } else if (local2_0 == this.gulHW.bind) {
         this.useItemWithSwap(class_1802.field_8450, "Прощальный гул");
      } else if (local2_0 == this.stanHW.bind) {
         this.useItemWithSwap(class_1802.field_8137, "Стан");
      }
   }

   private void useItemWithSwap(class_1792 item, String name) {
      if (mc.field_1724 != null && !this.isUsing) {
         this.flushGuiMovePackets();
         Integer local3_1 = InventoryUtil.findItem(name, false);
         if (local3_1 != null) {
            class_1735 local4_2 = this.getPlayerInventorySlot(local3_1);
            if (local4_2 != null) {
               class_1799 local5_3 = local4_2.method_7677();
               if (!local5_3.method_7960() && local5_3.method_7909() == item) {
                  int local6_4 = local4_2.method_34266();
                  if (local6_4 >= 0) {
                     this.isUsing = true;
                     this.itemUseCooldown.reset();
                     this.originalSlot = mc.field_1724.method_31548().field_7545;
                     Melonity.INSTANCE
                        .getNotificationManager()
                        .add(new Notification(name, Melonity.INSTANCE.languageService.get("sa.useitem"), "feature-on.png"));
                     if (local6_4 < 9) {
                        this.handleHotbarItem(local6_4);
                     } else {
                        boolean local7_0 = this.mode.contains("FunTime") || this.mode.contains("HolyWorld");
                        if (local7_0) {
                           this.handleInventoryItem(local4_2.field_7874);
                        } else {
                           this.resetUsing();
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void handleHotbarItem(int slot) {
      ActionScheduler.scheduleOnce(() -> this.switchToSlot(slot), 1);
      ActionScheduler.scheduleOnce(this::tryUseItem, 2);
      ActionScheduler.scheduleOnce(this::restoreSlot, 3);
      ActionScheduler.scheduleOnce(this::resetUsing, 4);
   }

   private void handleInventoryItem(int slotId) {
      ActionScheduler.scheduleOnce(() -> this.swapWithSelected(slotId), 1);
      ActionScheduler.scheduleOnce(this::tryUseItem, 2);
      ActionScheduler.scheduleOnce(this::resetUsing, 3);
      ActionScheduler.scheduleOnce(() -> this.swapWithSelected(slotId), 5);
   }

   private void switchToSlot(int slot) {
      if (mc.field_1724 != null) {
         if (slot >= 0 && slot <= 8) {
            mc.field_1724.method_31548().field_7545 = slot;
         }
      }
   }

   private void tryUseItem() {
      if (mc.field_1724 != null && mc.field_1761 != null) {
         class_1269 local1_0 = mc.field_1761.method_2919(mc.field_1724, class_1268.field_5808);
         // PASS never reports shouldSwingHand(): the item was used with no swing.
         if (local1_0.method_23665() && local1_0.method_23666()) {
            mc.field_1724.method_6104(class_1268.field_5808);
         }
      }
   }

   private void restoreSlot() {
      if (mc.field_1724 != null) {
         if (this.originalSlot >= 0 && this.originalSlot <= 8) {
            mc.field_1724.method_31548().field_7545 = this.originalSlot;
         }
      }
   }

   private void resetUsing() {
      this.isUsing = false;
      this.originalSlot = -1;
   }

   private void swapWithSelected(int slotId) {
      if (mc.field_1724 != null) {
         int local2_0 = mc.field_1724.method_31548().field_7545;
         InventoryUtil.clickSlotId(slotId, local2_0, class_1713.field_7791);
      }
   }

   private void flushGuiMovePackets() {
      GuiMoveFeature local1_0 = Melonity.INSTANCE.getFeatureManager().guiMoveFeature;
      if (local1_0 != null) {
         local1_0.flushQueuedPackets();
      }
   }

   private class_1735 getPlayerInventorySlot(int slotId) {
      if (mc.field_1724 != null && mc.field_1724.field_7512 != null) {
         for (class_1735 local3_0 : mc.field_1724.field_7512.field_7761) {
            if (local3_0.field_7874 == slotId && local3_0.field_7871 == mc.field_1724.method_31548()) {
               return local3_0;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Generated
   public ListSetting getMode() {
      return this.mode;
   }

   @Generated
   public ListSetting getSettingFuntime() {
      return this.settingFuntime;
   }

   @Generated
   public ListSetting getAutoUseFuntime() {
      return this.autoUseFuntime;
   }

   @Generated
   public BindSetting getDisorientation() {
      return this.disorientation;
   }

   @Generated
   public BindSetting getTrap() {
      return this.trap;
   }

   @Generated
   public BindSetting getPlast() {
      return this.plast;
   }

   @Generated
   public BindSetting getSheerdust() {
      return this.sheerdust;
   }

   @Generated
   public BindSetting getGodaura() {
      return this.godaura;
   }

   @Generated
   public BindSetting getFreezball() {
      return this.freezball;
   }

   @Generated
   public BindSetting getAcid() {
      return this.acid;
   }

   @Generated
   public BindSetting getBurp() {
      return this.burp;
   }

   @Generated
   public BindSetting getFlash() {
      return this.flash;
   }

   @Generated
   public BindSetting getFlare() {
      return this.flare;
   }

   @Generated
   public List<BindSetting> getBindSettingsFT() {
      return this.bindSettingsFT;
   }

   @Generated
   public ListSetting getSettingReallyWorld() {
      return this.settingReallyWorld;
   }

   @Generated
   public ListSetting getSettingHolyWorld() {
      return this.settingHolyWorld;
   }

   @Generated
   public BindSetting getTrapVHW() {
      return this.trapVHW;
   }

   @Generated
   public BindSetting getTrapHW() {
      return this.trapHW;
   }

   @Generated
   public BindSetting getSnegHW() {
      return this.snegHW;
   }

   @Generated
   public BindSetting getGulHW() {
      return this.gulHW;
   }

   @Generated
   public BindSetting getStanHW() {
      return this.stanHW;
   }

   @Generated
   public List<BindSetting> getBindSettingsHW() {
      return this.bindSettingsHW;
   }

   @Generated
   public Drag getDrag() {
      return this.drag;
   }

   @Generated
   public Timer getItemUseCooldown() {
      return this.itemUseCooldown;
   }

   @Generated
   public List<class_2338> getFoundBlocks() {
      return this.foundBlocks;
   }

   @Generated
   public boolean isUsing() {
      return this.isUsing;
   }

   @Generated
   public int getOriginalSlot() {
      return this.originalSlot;
   }

   @Generated
   public boolean isTeleporting() {
      return this.isTeleporting;
   }

   @Generated
   public int getTeleportCounter() {
      return this.teleportCounter;
   }

   @Generated
   public EventListener<UpdateEvent> getOnUpdate() {
      return this.onUpdate;
   }

   @Generated
   public EventListener<PacketEvent> getOnPacket() {
      return this.onPacket;
   }

   @Generated
   public EventListener<RenderGameWorldEvent> getOn3D() {
      return this.on3D;
   }

   @Generated
   public EventListener<KeyEvent> getOnKey() {
      return this.onKey;
   }

   @Generated
   public EventListener<RenderOverlayEvent> getOnRenderOverlay() {
      return this.onRenderOverlay;
   }

   @Generated
   public EventListener<EventInput> getOnInput() {
      return this.onInput;
   }
}
