package ru.melonity.util.macro;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.melonity.util.animation.FrameWeightCalculator;
import ru.melonity.util.animation.StateAnimation;

@Environment(EnvType.CLIENT)
public class Macro {
   private final String name;
   private final List<Action> actions;
   private final FrameWeightCalculator fwc = FrameWeightCalculator.milliseconds(100L);
   private final StateAnimation animation = new StateAnimation();

   public Macro(String name) {
      this.name = name;
      this.actions = Lists.newLinkedList();
   }

   public JSONObject toJson() {
      JSONObject local1_1 = new JSONObject();
      local1_1.put("name", this.name);
      JSONArray local2_2 = new JSONArray();

      for (Action local4_0 : this.actions) {
         local2_2.put(local4_0.toJson());
      }

      local1_1.put("actions", local2_2);
      return local1_1;
   }

   public void use() {
      new Thread(() -> {
         for (Action local2_0 : this.actions) {
            local2_0.run();
         }
      }).start();
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public List<Action> getActions() {
      return this.actions;
   }

   @Generated
   public FrameWeightCalculator getFwc() {
      return this.fwc;
   }

   @Generated
   public StateAnimation getAnimation() {
      return this.animation;
   }
}
