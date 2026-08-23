package ru.melonity.feature.features.modules.combat;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1304;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import org.jetbrains.annotations.NotNull;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001\u001aB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0015\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\n\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\n\u0010\bJ\u0019\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0006\u0010\f\u001a\u00020\u000bH\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ\u001f\u0010\u0011\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\rH\u0002¢\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\u0013\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\rH\u0002¢\u0006\u0004\b\u0013\u0010\u0012J\u001f\u0010\u0014\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\rH\u0002¢\u0006\u0004\b\u0014\u0010\u0012R\u0017\u0010\u0016\u001a\u00020\u00158\u0006¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001b"},
   d2 = {"Lru/melonity/feature/features/modules/combat/AntiBotFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "Lnet/minecraft/class_1657;", "entity", "", "isValidTarget", "(Lnet/minecraft/class_1657;)Z", "player", "hasValidArmorSet", "Lnet/minecraft/class_1792;", "item", "Lru/melonity/feature/features/modules/combat/AntiBotFeature$ArmorType;", "armorTypeFromHelmet", "(Lnet/minecraft/class_1792;)Lru/melonity/feature/features/modules/combat/AntiBotFeature$ArmorType;", "type", "isChestplateOfType", "(Lnet/minecraft/class_1792;Lru/melonity/feature/features/modules/combat/AntiBotFeature$ArmorType;)Z", "isLeggingsOfType", "isBootsOfType", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "armor", "Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "getArmor", "()Lru/melonity/ui/menu/clickui/view/feature/setting/BooleanSetting;", "ArmorType", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class AntiBotFeature extends SimpleFeature {
   @NotNull
   private final BooleanSetting armor = new BooleanSetting("Armor", false);

   public AntiBotFeature() {
      super("AntiBot", MenuCategory.COMBAT);
      MenuFeatureView[] var1 = new MenuFeatureView[]{this.armor};
      this.settings(var1);
   }

   @NotNull
   public final BooleanSetting getArmor() {
      return this.armor;
   }

   public final boolean isValidTarget(@NotNull class_1657 entity) {
      Intrinsics.checkNotNullParameter(entity, "entity");
      return !this.armor.get() ? true : this.hasValidArmorSet(entity);
   }

   private final boolean hasValidArmorSet(class_1657 player) {
      class_1792 local2_0 = player.method_6118(class_1304.field_6169).method_7909();
      class_1792 local3_1 = player.method_6118(class_1304.field_6174).method_7909();
      class_1792 local4_2 = player.method_6118(class_1304.field_6172).method_7909();
      class_1792 local5_3 = player.method_6118(class_1304.field_6166).method_7909();
      Intrinsics.checkNotNull(local2_0);
      AntiBotFeature.ArmorType var10000 = this.armorTypeFromHelmet(local2_0);
      if (var10000 == null) {
         return false;
      } else {
         AntiBotFeature.ArmorType local6_4 = var10000;
         Intrinsics.checkNotNull(local4_2);
         if (!this.isLeggingsOfType(local4_2, local6_4)) {
            return false;
         } else {
            Intrinsics.checkNotNull(local5_3);
            if (!this.isBootsOfType(local5_3, local6_4)) {
               return false;
            } else {
               if (!Intrinsics.areEqual(local3_1, class_1802.field_8833)) {
                  Intrinsics.checkNotNull(local3_1);
                  if (!this.isChestplateOfType(local3_1, local6_4)) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   private final AntiBotFeature.ArmorType armorTypeFromHelmet(class_1792 item) {
      return Intrinsics.areEqual(item, class_1802.field_8743)
         ? AntiBotFeature.ArmorType.IRON
         : (
            Intrinsics.areEqual(item, class_1802.field_8805)
               ? AntiBotFeature.ArmorType.DIAMOND
               : (Intrinsics.areEqual(item, class_1802.field_22027) ? AntiBotFeature.ArmorType.NETHERITE : null)
         );
   }

   private final boolean isChestplateOfType(class_1792 item, AntiBotFeature.ArmorType type) {
      return switch (type) {
         case IRON -> Intrinsics.areEqual(item, class_1802.field_8523);
         case DIAMOND -> Intrinsics.areEqual(item, class_1802.field_8058);
         case NETHERITE -> Intrinsics.areEqual(item, class_1802.field_22028);
         default -> throw new NoWhenBranchMatchedException();
      };
   }

   private final boolean isLeggingsOfType(class_1792 item, AntiBotFeature.ArmorType type) {
      return switch (type) {
         case IRON -> Intrinsics.areEqual(item, class_1802.field_8396);
         case DIAMOND -> Intrinsics.areEqual(item, class_1802.field_8348);
         case NETHERITE -> Intrinsics.areEqual(item, class_1802.field_22029);
         default -> throw new NoWhenBranchMatchedException();
      };
   }

   private final boolean isBootsOfType(class_1792 item, AntiBotFeature.ArmorType type) {
      return switch (type) {
         case IRON -> Intrinsics.areEqual(item, class_1802.field_8660);
         case DIAMOND -> Intrinsics.areEqual(item, class_1802.field_8285);
         case NETHERITE -> Intrinsics.areEqual(item, class_1802.field_22030);
         default -> throw new NoWhenBranchMatchedException();
      };
   }

   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"},
      d2 = {"Lru/melonity/feature/features/modules/combat/AntiBotFeature$ArmorType;", "", "<init>", "(Ljava/lang/String;I)V", "IRON", "DIAMOND", "NETHERITE", "melonity_client"}
   )
   @Environment(EnvType.CLIENT)
   private static enum ArmorType {
      IRON,
      DIAMOND,
      NETHERITE;

      private static final EnumEntries<AntiBotFeature.ArmorType> $ENTRIES = EnumEntriesKt.enumEntries(values());

      @NotNull
      public static EnumEntries<AntiBotFeature.ArmorType> getEntries() {
         return $ENTRIES;
      }
   }
}
