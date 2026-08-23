package ru.melonity.feature.config;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.ui.drag.Drag;
import ru.melonity.ui.menu.clickui.view.MenuGira;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureView;
import ru.melonity.ui.menu.clickui.view.feature.setting.BindSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.BooleanSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.ListSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.MenuBooleanRowSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.MenuColorSetting;
import ru.melonity.ui.menu.clickui.view.feature.setting.NumberSetting;

@Environment(EnvType.CLIENT)
public class Config {
   public static JSONObject save() {
      JSONObject local0_11 = new JSONObject();
      JSONObject local1_12 = new JSONObject();

      for (Feature local3_8 : Melonity.INSTANCE.getFeatureManager().getFeatures()) {
         JSONObject local4_7 = new JSONObject();
         local4_7.put("enabled", local3_8.isEnabled());

         for (MenuFeatureView local6_6 : local3_8.getMenuObject().getElementViews()) {
            if (local6_6 instanceof BindSetting local7_0) {
               local4_7.put(local7_0.getText(), local7_0.bind);
            } else if (local6_6 instanceof MenuBooleanRowSetting local8_1) {
               local4_7.put(local8_1.getSetting1().getName(), local8_1.getSetting1().get());
               local4_7.put(local8_1.getSetting2().getName(), local8_1.getSetting2().get());
            } else if (local6_6 instanceof BooleanSetting local9_2) {
               local4_7.put(local9_2.getName(), local9_2.get());
            } else if (local6_6 instanceof MenuColorSetting local10_3) {
               local4_7.put(local10_3.getName(), local10_3.getColor().getRGB());
            } else if (local6_6 instanceof ListSetting local11_4) {
               local4_7.put(local11_4.getName(), String.join(",", local11_4.getMode()));
            } else if (local6_6 instanceof NumberSetting local12_5) {
               local4_7.put(local12_5.getName(), local12_5.getValue().floatValue());
            }
         }

         local1_12.put(local3_8.getName(), local4_7);
      }

      JSONArray local2_13 = new JSONArray();

      for (Drag local4_10 : Melonity.INSTANCE.getDragService().getDrags()) {
         JSONObject local5_9 = new JSONObject();
         local5_9.put("x", local4_10.getX());
         local5_9.put("y", local4_10.getY());
         local5_9.put("id", local4_10.getId());
         local2_13.put(local5_9);
      }

      JSONObject local3_14 = new JSONObject();
      local3_14.put("aspectRatio", MenuGira.aspectRatio);
      local3_14.put("style", Melonity.INSTANCE.styleManager.getStyle().name());
      local0_11.put("features", local1_12);
      local0_11.put("drags", local2_13);
      local0_11.put("gira", local3_14);
      return local0_11;
   }

   public static void load(String content) {
      JSONObject local1_17 = new JSONObject(new String(Base64.getDecoder().decode(content), StandardCharsets.UTF_8));
      if (local1_17.has("features")) {
         JSONObject local2_11 = local1_17.getJSONObject("features");

         for (Feature local4_10 : Melonity.INSTANCE.getFeatureManager().getFeatures()) {
            if (local2_11.has(local4_10.getName())) {
               JSONObject local5_9 = local2_11.getJSONObject(local4_10.getName());
               if (local5_9.has("enabled")) {
                  boolean local6_0 = local5_9.getBoolean("enabled");
                  if (local4_10.isEnabled() != local6_0) {
                     local4_10.setEnabled(local6_0, true);
                  }
               }

               for (MenuFeatureView local7_8 : local4_10.getMenuObject().getElementViews()) {
                  if (local7_8 instanceof BindSetting local8_1 && local5_9.has(local8_1.getText())) {
                     local8_1.setBind(local5_9.getInt(local8_1.getText()));
                     continue;
                  }

                  if (local7_8 instanceof MenuBooleanRowSetting local9_2) {
                     if (local5_9.has(local9_2.getSetting1().getName())) {
                        local9_2.getSetting1().setValue(local5_9.getBoolean(local9_2.getSetting1().getName()));
                     }

                     if (local5_9.has(local9_2.getSetting2().getName())) {
                        local9_2.getSetting2().setValue(local5_9.getBoolean(local9_2.getSetting2().getName()));
                     }
                  } else {
                     if (local7_8 instanceof BooleanSetting local10_3 && local5_9.has(local10_3.getName())) {
                        local10_3.setValue(local5_9.getBoolean(local10_3.getName()));
                        continue;
                     }

                     if (local7_8 instanceof MenuColorSetting local11_4 && local5_9.has(local11_4.getName())) {
                        try {
                           local11_4.setColor(new Color(local5_9.getInt(local11_4.getName())));
                        } catch (Throwable var15) {
                        }
                        continue;
                     }

                     if (local7_8 instanceof ListSetting local12_6 && local5_9.has(local12_6.getName())) {
                        List<String> local14_5 = Lists.newArrayList();
                        local14_5.addAll(Arrays.asList(local5_9.getString(local12_6.getName()).split(",")));
                        local12_6.setMode(local14_5);
                        continue;
                     }

                     if (local7_8 instanceof NumberSetting local13_7 && local5_9.has(local13_7.getName())) {
                        local13_7.setValue(local5_9.getFloat(local13_7.getName()));
                     }
                  }
               }
            }
         }
      }

      if (local1_17.has("drags")) {
         for (Object local4_14 : local1_17.getJSONArray("drags")) {
            JSONObject local5_12 = (JSONObject)local4_14;
            Drag local6_13 = Melonity.INSTANCE.getDragService().getDragById(local5_12.getInt("id"));
            if (local6_13 != null) {
               local6_13.setX(local5_12.getFloat("x"));
               local6_13.setY(local5_12.getFloat("y"));
            }
         }
      }

      if (local1_17.has("gira")) {
         JSONObject local2_16 = local1_17.getJSONObject("gira");
         if (local2_16.has("aspectRatio")) {
            MenuGira.aspectRatio = local2_16.getFloat("aspectRatio");
         }

         if (local2_16.has("style")) {
            Melonity.INSTANCE.styleManager.findStyleByName(local2_16.getString("style")).ifPresent(style -> Melonity.INSTANCE.styleManager.setStyle(style));
         }
      }
   }
}
