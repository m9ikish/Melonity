package ru.melonity.feature.features.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_2561;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import ru.melonity.feature.impl.SimpleFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.events.UpdateEvent;

@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000f\u0010\u0005\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u0005\u0010\u0006R\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f¨\u0006\r"},
   d2 = {"Lru/melonity/feature/features/modules/movement/AutoSprintFeature;", "Lru/melonity/feature/impl/SimpleFeature;", "<init>", "()V", "", "canSprint", "()Z", "Lru/melonity/util/event/EventListener;", "Lru/melonity/util/event/events/UpdateEvent;", "onUpdate", "Lru/melonity/util/event/EventListener;", "getOnUpdate", "()Lru/melonity/util/event/EventListener;", "melonity_client"}
)
@Environment(EnvType.CLIENT)
public final class AutoSprintFeature extends SimpleFeature {
   @NotNull
   private final EventListener<UpdateEvent> onUpdate = e -> onUpdate$lambda$0(this, e);

   public AutoSprintFeature() {
      super("AutoSprint", MenuCategory.MOVEMENT);
   }

   @NotNull
   public final EventListener<UpdateEvent> getOnUpdate() {
      return this.onUpdate;
   }

   private final boolean canSprint() {
      if (SimpleFeature.mc.field_1724 == null) {
         return false;
      } else {
         class_746 local1_0 = SimpleFeature.mc.field_1724;
         if (local1_0.field_3913.field_3903) {
            return false;
         } else if (local1_0.method_6115()) {
            return false;
         } else if (local1_0.method_6059(class_1294.field_5919)) {
            return false;
         } else if (local1_0.method_6128()) {
            return false;
         } else if (local1_0.method_5765() && !local1_0.method_48155()) {
            return false;
         } else if (!local1_0.method_31549().field_7477 && local1_0.method_7344().method_7586() <= 6) {
            return false;
         } else if (local1_0.method_5799() && !local1_0.method_5869() && !local1_0.method_5681()) {
            return false;
         } else {
            boolean var3;
            label69: {
               label68:
               if (!local1_0.method_5869() && !local1_0.method_5681()) {
                  if (local1_0.method_5765()) {
                     class_1297 var10000 = local1_0.method_5854();
                     if (var10000 != null ? var10000.method_24828() : false) {
                        break label68;
                     }
                  }

                  if (!local1_0.field_3913.method_20622()) {
                     var3 = false;
                     break label69;
                  }
               }

               var3 = true;
            }

            boolean local2_1 = var3;
            return (local2_1 || local1_0.method_5624()) && !Intrinsics.areEqual(local1_0.method_6030().method_7964(), class_2561.method_43470("Bow"));
         }
      }
   }

   private static final void onUpdate$lambda$0(AutoSprintFeature this$0, UpdateEvent e) {
      if (this$0.canSprint()) {
         if (SimpleFeature.mc.field_1724 != null) {
            SimpleFeature.mc.field_1724.method_5728(true);
         }
      }
   }
}
