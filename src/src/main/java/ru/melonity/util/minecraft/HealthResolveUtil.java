package ru.melonity.util.minecraft;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1309;
import net.minecraft.class_266;
import net.minecraft.class_269;
import net.minecraft.class_310;
import net.minecraft.class_8646;
import net.minecraft.class_9013;

@Environment(EnvType.CLIENT)
public class HealthResolveUtil {
   public static float resolve(class_1309 entity) {
      class_269 local1_2 = class_310.method_1551().field_1687.method_8428();
      if (local1_2 != null) {
         class_266 local2_1 = class_310.method_1551().field_1687.method_8428().method_1189(class_8646.field_45158);
         if (local2_1 != null) {
            class_9013 local3_0 = local1_2.method_55430(entity, local2_1);
            if (local3_0 != null) {
               return (float)local3_0.method_55397();
            }
         }
      }

      return (float)(Math.floor((double)(entity.method_6032() * 10.0F)) / 10.0);
   }
}
