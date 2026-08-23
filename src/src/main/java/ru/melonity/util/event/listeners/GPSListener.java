package ru.melonity.util.event.listeners;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_7833;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.combat.ElytraHelperFeature;
import ru.melonity.font.Fonts;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.event.EventListener;
import ru.melonity.util.event.annotation.ListenerClass;
import ru.melonity.util.event.events.RenderOverlayEvent;
import ru.melonity.util.render.ScaleUtils;

@Environment(EnvType.CLIENT)
@ListenerClass
public class GPSListener {
   private boolean enabled;
   private int x;
   private int z;
   private final EventListener<RenderOverlayEvent> onRenderOverlay = event -> {
      if (this.enabled) {
         ScaleUtils.rescale(2.0);
         class_4587 local2_0 = event.matrixStack();
         class_310 local3_1 = class_310.method_1551();
         float local4_2 = class_3532.method_16439(
            local3_1.method_60646().method_60637(true) * 0.5F, local3_1.field_1724.method_36454(), local3_1.field_1724.field_5982
         );
         double local5_3 = (double)this.x - local3_1.method_31975().field_4344.method_19326().field_1352;
         double local7_4 = (double)this.z - local3_1.method_31975().field_4344.method_19326().field_1350;
         double local9_5 = Math.atan2(local7_4, local5_3);
         double local11_6 = Math.toRadians((double)local4_2);
         double local13_7 = local9_5 - local11_6 + Math.toRadians(180.0);
         local2_0.method_22903();
         RenderSystem.disableBlend();
         double local15_8 = (double)local3_1.method_22683().method_4480() / 4.0 + 40.0 * Math.cos(local13_7);
         double local17_9 = (double)local3_1.method_22683().method_4507() / 4.0 + 40.0 * Math.sin(local13_7);
         local2_0.method_22903();
         local2_0.method_22904(local15_8, local17_9, 0.0);
         local2_0.method_22907(class_7833.field_40718.rotationDegrees((float)Math.toDegrees(local13_7) + 100.0F));
         Melonity.INSTANCE
            .getRenderSystem()
            .addTexture(class_2960.method_60656("melonity/images/features/arrow.png"), 0.0F, 0.0F, 16.0F, 16.0F, Color.WHITE, local2_0);
         local2_0.method_22909();
         double local19_10 = Math.sqrt(local3_1.field_1724.method_5649((double)this.x, local3_1.field_1724.method_23318(), (double)this.z));
         local2_0.method_22904(
            (double)local3_1.method_22683().method_4480() / 4.0 - (double)Fonts.INTER_REGULAR_12.getStringHeight(String.format("%dM", (int)local19_10)),
            (double)local3_1.method_22683().method_4507() / 4.0 - 5.0,
            0.0
         );
         Melonity.INSTANCE
            .getRenderSystem()
            .addRect(
               -1.0F,
               16.0F,
               2.0F + Fonts.INTER_REGULAR_12.getStringWidth(String.format("%dM", (int)local19_10)),
               2.0F + Fonts.INTER_REGULAR_12.getStringHeight(String.format("%dM", (int)local19_10)),
               2.0F,
               Melonity.INSTANCE.styleManager.getStyle().background(),
               local2_0
            );
         Melonity.INSTANCE.getRenderSystem().addText(Fonts.INTER_REGULAR_12, String.format("%dM", (int)local19_10), 0.0F, 20.0F, local2_0);
         if (local19_10 <= 15.0) {
            Melonity.INSTANCE.getNotificationManager().add(new Notification("GPS", "You have arrived", "feature-disabled.png"));
            Melonity.INSTANCE.getFeatureManager().getFeature(ElytraHelperFeature.class).get().getAutoPilot().setValue(false);
            this.enabled = false;
         }

         local2_0.method_22909();
         RenderSystem.enableBlend();
         ScaleUtils.rescaleMC();
      }
   };

   @Generated
   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   @Generated
   public void setX(int x) {
      this.x = x;
   }

   @Generated
   public void setZ(int z) {
      this.z = z;
   }

   @Generated
   public boolean isEnabled() {
      return this.enabled;
   }

   @Generated
   public int getX() {
      return this.x;
   }

   @Generated
   public int getZ() {
      return this.z;
   }

   @Generated
   public EventListener<RenderOverlayEvent> getOnRenderOverlay() {
      return this.onRenderOverlay;
   }
}
