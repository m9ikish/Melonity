package ru.melonity.feature.features.modules.player;

import kotlin.Metadata;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_418;
import org.jetbrains.annotations.NotNull;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.PreMotionEvent;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0006\u0010\u0007¨\u0006\b"},
   d2 = {"Lru/melonity/feature/features/modules/player/AutoRespawnFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/PreMotionEvent;", "onPreMotion", "Lru/melonity/util/event/EventListener;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class AutoRespawnFeature extends SimpleFeature {
   @NotNull
   private final EventListener<PreMotionEvent> onPreMotion = AutoRespawnFeature::onPreMotion$lambda$0;

   public AutoRespawnFeature() {
      super("AutoRespawn", MenuCategory.PLAYER);
   }

   private static final void onPreMotion$lambda$0(PreMotionEvent event) {
      if (SimpleFeature.mc.field_1755 instanceof class_418 && (SimpleFeature.mc.field_1724 != null ? SimpleFeature.mc.field_1724.field_6213 == 1 : false)) {
         if (SimpleFeature.mc.field_1724 != null) {
            SimpleFeature.mc.field_1724.method_7331();
         }

         SimpleFeature.mc.method_1507(null);
      }
   }
}
