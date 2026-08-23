package ru.melonity.util.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Easings {
   public static final double c1 = 1.70158;
   public static final double c2 = 2.5949095;
   public static final Easing BACK_BOTH = value -> value < 0.5
         ? Math.pow(2.0 * value, 2.0) * (7.189819 * value - 2.5949095) / 2.0
         : (Math.pow(2.0 * value - 2.0, 2.0) * (3.5949095 * (value * 2.0 - 2.0) + 2.5949095) + 2.0) / 2.0;
   public static final double c3 = 2.70158;
   public static final Easing BACK_IN = value -> 2.70158 * Math.pow(value, 3.0) - 1.70158 * Math.pow(value, 2.0);
   public static final Easing BACK_OUT = value -> 1.0 + 2.70158 * Math.pow(value - 1.0, 3.0) + 1.70158 * Math.pow(value - 1.0, 2.0);
   public static final double c4 = Math.PI * 2.0 / 3.0;
   public static final double c5 = Math.PI * 4.0 / 9.0;
   public static final Easing LINEAR = value -> value;
   public static final Easing QUAD_IN = powIn(2);
   public static final Easing QUAD_OUT = powOut(2);
   public static final Easing QUAD_BOTH = powBoth(2.0);
   public static final Easing CUBIC_IN = powIn(3);
   public static final Easing CUBIC_OUT = powOut(3);
   public static final Easing CUBIC_BOTH = powBoth(3.0);
   public static final Easing QUART_IN = powIn(4);
   public static final Easing QUART_OUT = powOut(4);
   public static final Easing QUART_BOTH = powBoth(4.0);
   public static final Easing QUINT_IN = powIn(5);
   public static final Easing QUINT_OUT = powOut(5);
   public static final Easing QUINT_BOTH = powBoth(5.0);
   public static final Easing SINE_IN = value -> 1.0 - Math.cos(value * Math.PI / 2.0);
   public static final Easing SINE_OUT = value -> Math.sin(value * Math.PI / 2.0);
   public static final Easing SINE_BOTH = value -> -(Math.cos(Math.PI * value) - 1.0) / 2.0;
   public static final Easing CIRC_IN = value -> 1.0 - Math.sqrt(1.0 - Math.pow(value, 2.0));
   public static final Easing CIRC_OUT = value -> Math.sqrt(1.0 - Math.pow(value - 1.0, 2.0));
   public static final Easing CIRC_BOTH = value -> value < 0.5
         ? (1.0 - Math.sqrt(1.0 - Math.pow(2.0 * value, 2.0))) / 2.0
         : (Math.sqrt(1.0 - Math.pow(-2.0 * value + 2.0, 2.0)) + 1.0) / 2.0;
   public static final Easing ELASTIC_IN = value -> value != 0.0 && value != 1.0
         ? Math.pow(-2.0, 10.0 * value - 10.0) * Math.sin((value * 10.0 - 10.75) * (Math.PI * 2.0 / 3.0))
         : value;
   public static final Easing ELASTIC_OUT = value -> value != 0.0 && value != 1.0
         ? Math.pow(2.0, -10.0 * value) * Math.sin((value * 10.0 - 0.75) * (Math.PI * 2.0 / 3.0)) + 1.0
         : value;
   public static final Easing ELASTIC_BOTH = value -> {
      if (value == 0.0 || value == 1.0) {
         return value;
      } else {
         return value < 0.5
            ? -(Math.pow(2.0, 20.0 * value - 10.0) * Math.sin((20.0 * value - 11.125) * (Math.PI * 4.0 / 9.0))) / 2.0
            : Math.pow(2.0, -20.0 * value + 10.0) * Math.sin((20.0 * value - 11.125) * (Math.PI * 4.0 / 9.0)) / 2.0 + 1.0;
      }
   };
   public static final Easing EXPO_IN = value -> value != 0.0 ? Math.pow(2.0, 10.0 * value - 10.0) : value;
   public static final Easing EXPO_OUT = value -> value != 1.0 ? 1.0 - Math.pow(2.0, -10.0 * value) : value;
   public static final Easing EXPO_BOTH = value -> {
      if (value == 0.0 || value == 1.0) {
         return value;
      } else {
         return value < 0.5 ? Math.pow(2.0, 20.0 * value - 10.0) / 2.0 : (2.0 - Math.pow(2.0, -20.0 * value + 10.0)) / 2.0;
      }
   };
   public static final Easing APPLE = cubicBezier(0.215, 0.61, 0.355, 1.0);
   public static final Easing BOUNCE_OUT = x -> {
      double local2_0 = 7.5625;
      double local4_1 = 2.75;
      if (x < 1.0 / local4_1) {
         return local2_0 * Math.pow(x, 2.0);
      } else if (x < 2.0 / local4_1) {
         return local2_0 * Math.pow(x - 1.5 / local4_1, 2.0) + 0.75;
      } else {
         return x < 2.5 / local4_1 ? local2_0 * Math.pow(x - 2.25 / local4_1, 2.0) + 0.9375 : local2_0 * Math.pow(x - 2.625 / local4_1, 2.0) + 0.984375;
      }
   };
   public static final Easing BOUNCE_IN = value -> 1.0 - BOUNCE_OUT.ease(1.0 - value);
   public static final Easing BOUNCE_BOTH = value -> value < 0.5
         ? (1.0 - BOUNCE_OUT.ease(1.0 - 2.0 * value)) / 2.0
         : (1.0 + BOUNCE_OUT.ease(2.0 * value - 1.0)) / 2.0;

   private Easings() {
   }

   public static Easing cubicBezier(double x1, double y1, double x2, double y2) {
      return value -> {
         double local10_0 = clamp01(value);
         if (local10_0 != 0.0 && local10_0 != 1.0) {
            double local12_1 = solveForT(local10_0, x1, x2);
            return calculateBezier(local12_1, y1, y2);
         } else {
            return local10_0;
         }
      };
   }

   private static double clamp01(double value) {
      return value < 0.0 ? 0.0 : (value > 1.0 ? 1.0 : value);
   }

   private static double calculateBezier(double t, double a1, double a2) {
      double local6_0 = coefficientA(a1, a2);
      double local8_1 = coefficientB(a1, a2);
      double local10_2 = coefficientC(a1);
      return ((local6_0 * t + local8_1) * t + local10_2) * t;
   }

   private static double slope(double t, double a1, double a2) {
      double local6_0 = coefficientA(a1, a2);
      double local8_1 = coefficientB(a1, a2);
      double local10_2 = coefficientC(a1);
      return 3.0 * local6_0 * t * t + 2.0 * local8_1 * t + local10_2;
   }

   private static double coefficientA(double a1, double a2) {
      return 1.0 - 3.0 * a2 + 3.0 * a1;
   }

   private static double coefficientB(double a1, double a2) {
      return 3.0 * a2 - 6.0 * a1;
   }

   private static double coefficientC(double a1) {
      return 3.0 * a1;
   }

   private static double solveForT(double x, double x1, double x2) {
      double local6_7 = x;

      for (int local8_2 = 0; local8_2 < 6; local8_2++) {
         double local9_0 = slope(local6_7, x1, x2);
         if (Math.abs(local9_0) < 1.0E-6) {
            break;
         }

         double local11_1 = calculateBezier(local6_7, x1, x2) - x;
         local6_7 -= local11_1 / local9_0;
      }

      if (local6_7 < 0.0 || local6_7 > 1.0) {
         double local8_5 = 0.0;
         double local10_6 = 1.0;

         while (local10_6 - local8_5 > 1.0E-6) {
            double local12_3 = (local8_5 + local10_6) / 2.0;
            double local14_4 = calculateBezier(local12_3, x1, x2);
            if (local14_4 < x) {
               local8_5 = local12_3;
            } else {
               local10_6 = local12_3;
            }
         }

         local6_7 = (local8_5 + local10_6) / 2.0;
      }

      return clamp01(local6_7);
   }

   public static Easing powIn(double n) {
      return value -> Math.pow(value, n);
   }

   public static Easing powIn(int n) {
      return powIn((double)n);
   }

   public static Easing powOut(double n) {
      return value -> 1.0 - Math.pow(1.0 - value, n);
   }

   public static Easing powOut(int n) {
      return powOut((double)n);
   }

   public static Easing powBoth(double n) {
      return value -> value < 0.5 ? Math.pow(2.0, n - 1.0) * Math.pow(value, n) : 1.0 - Math.pow(-2.0 * value + 2.0, n) / 2.0;
   }
}
