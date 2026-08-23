package ru.melonity.util.sound;

import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_3414;
import net.minecraft.class_7923;
import org.jetbrains.annotations.NotNull;
import ru.melonity.util.minecraft.MinecraftInstance;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b'\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000f\u0010\u0005\u001a\u00020\u0004H\u0007¢\u0006\u0004\b\u0005\u0010\u0003J!\u0010\n\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\bH\u0007¢\u0006\u0004\b\n\u0010\u000bJ\u0019\u0010\u000e\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ\u001f\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0011H\u0007¢\u0006\u0004\b\u0013\u0010\u0014R(\u0010\u0015\u001a\u00020\u00068\u0006@\u0006X\u0087.¢\u0006\u0018\n\u0004\b\u0015\u0010\u0016\u0012\u0004\b\u001b\u0010\u0003\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR(\u0010\u001c\u001a\u00020\u00068\u0006@\u0006X\u0087.¢\u0006\u0018\n\u0004\b\u001c\u0010\u0016\u0012\u0004\b\u001f\u0010\u0003\u001a\u0004\b\u001d\u0010\u0018\"\u0004\b\u001e\u0010\u001aR(\u0010 \u001a\u00020\u00068\u0006@\u0006X\u0087.¢\u0006\u0018\n\u0004\b \u0010\u0016\u0012\u0004\b#\u0010\u0003\u001a\u0004\b!\u0010\u0018\"\u0004\b\"\u0010\u001aR(\u0010$\u001a\u00020\u00068\u0006@\u0006X\u0087.¢\u0006\u0018\n\u0004\b$\u0010\u0016\u0012\u0004\b'\u0010\u0003\u001a\u0004\b%\u0010\u0018\"\u0004\b&\u0010\u001aR(\u0010(\u001a\u00020\u00068\u0006@\u0006X\u0087.¢\u0006\u0018\n\u0004\b(\u0010\u0016\u0012\u0004\b+\u0010\u0003\u001a\u0004\b)\u0010\u0018\"\u0004\b*\u0010\u001aR(\u0010,\u001a\u00020\u00068\u0006@\u0006X\u0087.¢\u0006\u0018\n\u0004\b,\u0010\u0016\u0012\u0004\b/\u0010\u0003\u001a\u0004\b-\u0010\u0018\"\u0004\b.\u0010\u001aR(\u00100\u001a\u00020\u00068\u0006@\u0006X\u0087.¢\u0006\u0018\n\u0004\b0\u0010\u0016\u0012\u0004\b3\u0010\u0003\u001a\u0004\b1\u0010\u0018\"\u0004\b2\u0010\u001aR(\u00104\u001a\u00020\u00068\u0006@\u0006X\u0087.¢\u0006\u0018\n\u0004\b4\u0010\u0016\u0012\u0004\b7\u0010\u0003\u001a\u0004\b5\u0010\u0018\"\u0004\b6\u0010\u001a¨\u00068"},
   d2 = {"Lru/melonity/util/sound/SoundManager;", "", "<init>", "()V", "", "init", "Lnet/minecraft/class_3414;", "event", "", "volume", "playSound", "(Lnet/minecraft/class_3414;F)V", "", "name", "registerSound", "(Ljava/lang/String;)Lnet/minecraft/class_3414;", "soundVariant", "", "isEnable", "getSoundEvent", "(Ljava/lang/String;Z)Lnet/minecraft/class_3414;", "enable", "Lnet/minecraft/class_3414;", "getEnable", "()Lnet/minecraft/class_3414;", "setEnable", "(Lnet/minecraft/class_3414;)V", "getEnable$annotations", "disable", "getDisable", "setDisable", "getDisable$annotations", "variant1Enable", "getVariant1Enable", "setVariant1Enable", "getVariant1Enable$annotations", "variant1Disable", "getVariant1Disable", "setVariant1Disable", "getVariant1Disable$annotations", "variant2Enable", "getVariant2Enable", "setVariant2Enable", "getVariant2Enable$annotations", "variant2Disable", "getVariant2Disable", "setVariant2Disable", "getVariant2Disable$annotations", "variant2EnableAlt", "getVariant2EnableAlt", "setVariant2EnableAlt", "getVariant2EnableAlt$annotations", "variant2DisableAlt", "getVariant2DisableAlt", "setVariant2DisableAlt", "getVariant2DisableAlt$annotations", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class SoundManager {
   @NotNull
   public static final SoundManager INSTANCE = new SoundManager();
   public static class_3414 enable;
   public static class_3414 disable;
   public static class_3414 variant1Enable;
   public static class_3414 variant1Disable;
   public static class_3414 variant2Enable;
   public static class_3414 variant2Disable;
   public static class_3414 variant2EnableAlt;
   public static class_3414 variant2DisableAlt;

   private SoundManager() {
   }

   @NotNull
   public static final class_3414 getEnable() {
      if (enable != null) {
         return enable;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("enable");
         return null;
      }
   }

   public static final void setEnable(@NotNull class_3414 value) {
      Intrinsics.checkNotNullParameter(value, "value");
      enable = value;
   }

   @NotNull
   public static final class_3414 getDisable() {
      if (disable != null) {
         return disable;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("disable");
         return null;
      }
   }

   public static final void setDisable(@NotNull class_3414 value) {
      Intrinsics.checkNotNullParameter(value, "value");
      disable = value;
   }

   @NotNull
   public static final class_3414 getVariant1Enable() {
      if (variant1Enable != null) {
         return variant1Enable;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("variant1Enable");
         return null;
      }
   }

   public static final void setVariant1Enable(@NotNull class_3414 value) {
      Intrinsics.checkNotNullParameter(value, "value");
      variant1Enable = value;
   }

   @NotNull
   public static final class_3414 getVariant1Disable() {
      if (variant1Disable != null) {
         return variant1Disable;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("variant1Disable");
         return null;
      }
   }

   public static final void setVariant1Disable(@NotNull class_3414 value) {
      Intrinsics.checkNotNullParameter(value, "value");
      variant1Disable = value;
   }

   @NotNull
   public static final class_3414 getVariant2Enable() {
      if (variant2Enable != null) {
         return variant2Enable;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("variant2Enable");
         return null;
      }
   }

   public static final void setVariant2Enable(@NotNull class_3414 value) {
      Intrinsics.checkNotNullParameter(value, "value");
      variant2Enable = value;
   }

   @NotNull
   public static final class_3414 getVariant2Disable() {
      if (variant2Disable != null) {
         return variant2Disable;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("variant2Disable");
         return null;
      }
   }

   public static final void setVariant2Disable(@NotNull class_3414 value) {
      Intrinsics.checkNotNullParameter(value, "value");
      variant2Disable = value;
   }

   @NotNull
   public static final class_3414 getVariant2EnableAlt() {
      if (variant2EnableAlt != null) {
         return variant2EnableAlt;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("variant2EnableAlt");
         return null;
      }
   }

   public static final void setVariant2EnableAlt(@NotNull class_3414 value) {
      Intrinsics.checkNotNullParameter(value, "value");
      variant2EnableAlt = value;
   }

   @NotNull
   public static final class_3414 getVariant2DisableAlt() {
      if (variant2DisableAlt != null) {
         return variant2DisableAlt;
      } else {
         Intrinsics.throwUninitializedPropertyAccessException("variant2DisableAlt");
         return null;
      }
   }

   public static final void setVariant2DisableAlt(@NotNull class_3414 value) {
      Intrinsics.checkNotNullParameter(value, "value");
      variant2DisableAlt = value;
   }

   @JvmStatic
   public static final void init() {
      setEnable(INSTANCE.registerSound("module_enable"));
      setDisable(INSTANCE.registerSound("module_disable"));
      setVariant1Enable(INSTANCE.registerSound("variant1_enable"));
      setVariant1Disable(INSTANCE.registerSound("variant1_disable"));
      setVariant2Enable(INSTANCE.registerSound("variant2_enable"));
      setVariant2Disable(INSTANCE.registerSound("variant2_disable"));
      // variant2_enable_alt / variant2_disable_alt used to be registered here as well.
      // Neither the original jar nor this tree ships a sounds.json entry or an .ogg for
      // them, and getSoundEvent() never returns them, so all the registration achieved
      // was a "Missing sound for event" warning from the vanilla sound loader on every
      // resource reload. The lateinit fields and their accessors are kept as-is: nothing
      // reads them, and if something ever does it will fail loudly instead of silently.
   }

   @JvmStatic
   @JvmOverloads
   public static final void playSound(@NotNull class_3414 event, float volume) {
      Intrinsics.checkNotNullParameter(event, "event");
      MinecraftInstance.mc.method_1483().method_4873((class_1113)class_1109.method_4757(event, 1.0F, volume));
   }

   public static void playSound$default(class_3414 event, float volume, int mask, Object marker) {
      if ((mask & 2) != 0) {
         volume = 0.5F;
      }

      playSound(event, volume);
   }

   private final class_3414 registerSound(String name) {
      class_2960 local2_0 = class_2960.method_60655("melonity", name);
      Object var10000 = class_2378.method_10230(class_7923.field_41172, local2_0, class_3414.method_47908(local2_0));
      Intrinsics.checkNotNullExpressionValue(var10000, "register(...)");
      return (class_3414)var10000;
   }

   @JvmStatic
   @NotNull
   public static final class_3414 getSoundEvent(@NotNull String soundVariant, boolean isEnable) {
      Intrinsics.checkNotNullParameter(soundVariant, "soundVariant");
      return Intrinsics.areEqual(soundVariant, "togglesound.variant1")
         ? (isEnable ? getVariant1Enable() : getVariant1Disable())
         : (
            Intrinsics.areEqual(soundVariant, "togglesound.variant2")
               ? (isEnable ? getVariant2Enable() : getVariant2Disable())
               : (isEnable ? getEnable() : getDisable())
         );
   }

   @JvmStatic
   @JvmOverloads
   public static final void playSound(@NotNull class_3414 event) {
      Intrinsics.checkNotNullParameter(event, "event");
      playSound$default(event, 0.0F, 2, null);
   }
}
