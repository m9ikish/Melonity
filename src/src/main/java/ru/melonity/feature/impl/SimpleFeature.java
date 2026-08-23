package ru.melonity.feature.impl;

import java.util.Arrays;
import lombok.Generated;
import lombok.NonNull;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Nullable;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.feature.features.modules.misc.ToggleSoundFeature;
import ru.melonity.feature.features.modules.render.HudFeature;
import ru.melonity.feature.wheel.WheelFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureFrame;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeaturePrefix;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.ui.notification.Notification;
import ru.melonity.ui.notification.NotificationManager;
import ru.melonity.util.animation.StateAnimation;
import ru.melonity.util.sound.SoundManager;

@Environment(EnvType.CLIENT)
public class SimpleFeature implements Feature {
   private final String name;
   private final MenuCategory category;
   private final MenuFeatureFrame menuObject;
   private final boolean safe;
   protected BindSetting bindSetting = new BindSetting(-1);
   private MenuFeaturePrefix[] prefixes = new MenuFeaturePrefix[0];
   private boolean enabled;
   private final StateAnimation stateAnimation = new StateAnimation();

   public SimpleFeature(String name, MenuCategory category, boolean safe) {
      this.name = name;
      this.category = category;
      this.menuObject = new MenuFeatureFrame(this);
      this.safe = safe;
      this.setting(this.bindSetting);
   }

   public boolean inInvalidState() {
      return mc.field_1724 == null || mc.field_1687 == null || !this.isEnabled();
   }

   public SimpleFeature(String name, MenuCategory category) {
      this(name, category, false);
   }

   public SimpleFeature prefix(MenuFeaturePrefix... prefixes) {
      this.prefixes = prefixes != null ? prefixes : new MenuFeaturePrefix[0];
      return this;
   }

   public boolean hasAnyPrefix() {
      return MenuFeaturePrefix.hasAnyPresent(this.prefixes);
   }

   public String getCombinedPrefixes() {
      return MenuFeaturePrefix.combineShortNames(this.prefixes);
   }

   public void setting(MenuFeatureView view) {
      this.getMenuObject().elementViews.add(view);
   }

   public void settings(MenuFeatureView... view) {
      this.getMenuObject().elementViews.addAll(Arrays.asList(view));
   }

   public void settingClear() {
      this.bindSetting = null;
      this.getMenuObject().elementViews.clear();
   }

   @NonNull
   @Override
   public String getName() {
      return this.name;
   }

   @NonNull
   @Override
   public MenuCategory getCategory() {
      return this.category;
   }

   @NonNull
   @Override
   public MenuFeatureFrame getMenuObject() {
      return this.menuObject;
   }

   @Nullable
   @Override
   public BindSetting getBindSetting() {
      return this.bindSetting;
   }

   @Override
   public boolean isEnabled() {
      return this.enabled;
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   protected void onToggle(boolean enabled) {
   }

   @Override
   public void setEnabled(boolean value, boolean silent) {
      this.enabled = value;
      if (this instanceof WheelFeature) {
         this.enabled = false;
      }

      if (!(this instanceof WheelFeature) && NotificationManager.features && !silent) {
         HudFeature local3_0 = Melonity.INSTANCE.getFeatureManager().hudFeature;
         if (local3_0 != null && local3_0.isDynamicIslandEnabled()) {
            local3_0.getDynamicIslandView().notifyFeatureToggle(this.name, value);
         } else {
            Melonity.INSTANCE
               .notificationManager
               .add(new Notification(this.name, "Feature " + (value ? "enabled" : "disabled"), value ? "feature-on.png" : "feature-disabled.png"));
         }

         ToggleSoundFeature local4_1 = Melonity.INSTANCE.featureManager.toggleSoundFeature;
         if (local4_1.isEnabled()) {
            SoundManager.playSound(SoundManager.getSoundEvent(local4_1.sound.getMode().getFirst(), value));
         }
      }

      if (value) {
         this.onToggle(true);
         this.onEnable();
      } else {
         this.onToggle(false);
         this.onDisable();
      }
   }

   private void playSound(String soundPath, float volume) {
   }

   public void setEnabled(boolean enabled) {
      this.setEnabled(enabled, false);
   }

   @Override
   public boolean isSafe() {
      return this.safe;
   }

   @Override
   public StateAnimation getBindsAnimation() {
      return this.stateAnimation;
   }

   @Generated
   public MenuFeaturePrefix[] getPrefixes() {
      return this.prefixes;
   }
}
