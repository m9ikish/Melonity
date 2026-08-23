package ru.melonity.fabric.client.mixin;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_338;
import net.minecraft.class_3532;
import net.minecraft.class_7591;
import net.minecraft.class_303.class_7590;
import net.minecraft.class_7591.class_7592;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.melonity.Melonity;
import ru.melonity.feature.features.modules.render.NoRenderFeature;

@Environment(EnvType.CLIENT)
@Mixin({class_338.class})
public class ChatHudMixin {
   @Shadow
   private boolean field_2067;
   @Shadow
   @Final
   private List<class_7590> field_2064;
   @Shadow
   private int field_2066;

   @Shadow
   private boolean method_23677() {
      return false;
   }

   @Shadow
   public int method_1813() {
      return 0;
   }

   @Shadow
   public double method_1814() {
      return 0.0;
   }

   @Shadow
   public int method_1811() {
      return 0;
   }

   @Shadow
   private int method_45588(double chatLineX, double chatLineY) {
      return 0;
   }

   @Shadow
   private double method_44722(double x) {
      return 0.0;
   }

   @Shadow
   private int method_44752() {
      return 0;
   }

   @Shadow
   private double method_44724(double y) {
      return 0.0;
   }

   @Shadow
   private int method_44720(class_7590 line) {
      return 0;
   }

   @Shadow
   private void method_44719(class_332 context, int x, int y, class_7592 icon) {
   }

   @Shadow
   private static double method_19348(int age) {
      return 0.0;
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void render(class_332 context, int currentTick, int mouseX, int mouseY, boolean focused, CallbackInfo callbackInfo) {
      class_310 client = class_310.method_1551();
      if (!this.method_23677()) {
         int i = this.method_1813();
         int j = this.field_2064.size();
         if (j > 0) {
            client.method_16011().method_15396("chat");
            float f = (float)this.method_1814();
            int k = class_3532.method_15386((float)this.method_1811() / f);
            int l = context.method_51443();
            context.method_51448().method_22903();
            context.method_51448().method_22905(f, f, 1.0F);
            context.method_51448().method_46416(4.0F, 0.0F, 0.0F);
            int m = class_3532.method_15375((float)(l - 40) / f);
            int n = this.method_45588(this.method_44722((double)mouseX), this.method_44724((double)mouseY));
            double d = (Double)client.field_1690.method_42542().method_41753() * 0.9F + 0.1F;
            double e = (Double)client.field_1690.method_42550().method_41753();
            double g = (Double)client.field_1690.method_42546().method_41753();
            int o = this.method_44752();
            int p = (int)Math.round(-8.0 * (g + 1.0) + 4.0 * g);
            int q = 0;

            for (int r = 0; r + this.field_2066 < this.field_2064.size() && r < i; r++) {
               int s = r + this.field_2066;
               class_7590 visible = this.field_2064.get(s);
               if (visible != null) {
                  int t = currentTick - visible.comp_895();
                  if (t < 200 || focused) {
                     double h = focused ? 1.0 : method_19348(t);
                     int u = (int)(255.0 * h * d);
                     int v = (int)(255.0 * h * e);
                     q++;
                     if (u > 3) {
                        int w = 0;
                        int x = m - r * o;
                        int y = x + p;
                        NoRenderFeature noRenderFeature = Melonity.INSTANCE.getFeatureManager().getFeature(NoRenderFeature.class).get();
                        if (!noRenderFeature.isEnabled() || !noRenderFeature.getModeSetting().getMode().contains("norender.chatbg")) {
                           context.method_25294(-4, x - o, k + 4 + 4, x, v << 24);
                        }

                        class_7591 messageIndicator = visible.comp_897();
                        if (messageIndicator != null) {
                           int z = messageIndicator.comp_899() | u << 24;
                           context.method_25294(-4, x - o, -2, x, z);
                           if (s == n && messageIndicator.comp_900() != null) {
                              int aa = this.method_44720(visible);
                              int ab = y + 9;
                              this.method_44719(context, aa, ab, messageIndicator.comp_900());
                           }
                        }

                        context.method_51448().method_22903();
                        context.method_51448().method_46416(0.0F, 0.0F, 50.0F);
                        context.method_35720(client.field_1772, visible.comp_896(), 0, y, 16777215 + (u << 24));
                        context.method_51448().method_22909();
                     }
                  }
               }
            }

            long ac = client.method_44714().method_44944();
            if (ac > 0L) {
               int ad = (int)(128.0 * d);
               int t = (int)(255.0 * e);
               context.method_51448().method_22903();
               context.method_51448().method_46416(0.0F, (float)m, 0.0F);
               context.method_25294(-2, 0, k + 4, 9, t << 24);
               context.method_51448().method_46416(0.0F, 0.0F, 50.0F);
               context.method_27535(client.field_1772, class_2561.method_43469("chat.queue", new Object[]{ac}), 0, 1, 16777215 + (ad << 24));
               context.method_51448().method_22909();
            }

            if (focused) {
               int ad = this.method_44752();
               int t = j * ad;
               int ae = q * ad;
               int af = this.field_2066 * ae / j - m;
               int u = ae * ae / t;
               if (t != ae) {
                  int v = af > 0 ? 170 : 96;
                  int wx = this.field_2067 ? 13382451 : 3355562;
                  int xx = k + 4;
                  context.method_51737(xx, -af, xx + 2, -af - u, 100, wx + (v << 24));
                  context.method_51737(xx + 2, -af, xx + 1, -af - u, 100, 13421772 + (v << 24));
               }
            }

            context.method_51448().method_22909();
            client.method_16011().method_15407();
         }
      }

      callbackInfo.cancel();
   }
}
