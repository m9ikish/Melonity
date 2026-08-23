package ru.melonity.feature.features.modules.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1661;
import net.minecraft.class_2596;
import net.minecraft.class_2868;
import net.minecraft.class_634;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import ru.melonity.Melonity;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PacketEvent;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0006\u0010\u0007¨\u0006\b"},
   d2 = {"Lru/melonity/feature/features/modules/misc/ItemSwapFixFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/PacketEvent;", "onPacket", "Lru/melonity/util/event/EventListener;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class ItemSwapFixFeature extends SimpleFeature {
   @NotNull
   private final EventListener<PacketEvent> onPacket = ItemSwapFixFeature::onPacket$lambda$0;

   public ItemSwapFixFeature() {
      super("ItemSwapFix", MenuCategory.MISC);
   }

   private static final void onPacket$lambda$0(PacketEvent event) {
      class_2596 local1_0 = event.packet;
      if (local1_0 instanceof class_2868) {
         boolean var10000;
         label37: {
            class_746 var2 = SimpleFeature.mc.field_1724;
            if (var2 != null) {
               class_1661 var3 = var2.method_31548();
               if (var3 != null) {
                  var10000 = ((class_2868)local1_0).method_12442() == var3.field_7545;
                  break label37;
               }
            }

            var10000 = false;
         }

         if (!var10000) {
            Melonity.packetEvent = false;
            if (SimpleFeature.mc.field_1724 != null) {
               class_634 var4 = SimpleFeature.mc.field_1724.field_3944;
               if (SimpleFeature.mc.field_1724.field_3944 != null) {
                  class_746 var10003 = SimpleFeature.mc.field_1724;
                  Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
                  var4.method_52787((class_2596)(new class_2868(Math.max(var10003.method_31548().field_7545 - 1, 0))));
               }
            }

            if (SimpleFeature.mc.field_1724 != null) {
               class_634 var5 = SimpleFeature.mc.field_1724.field_3944;
               if (SimpleFeature.mc.field_1724.field_3944 != null) {
                  class_746 var6 = SimpleFeature.mc.field_1724;
                  Intrinsics.checkNotNull(SimpleFeature.mc.field_1724);
                  var5.method_52787((class_2596)(new class_2868(var6.method_31548().field_7545)));
               }
            }

            event.cancel();
            Melonity.packetEvent = true;
         }
      }
   }
}
