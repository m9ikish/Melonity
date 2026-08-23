package ru.melonity.ui.menu.clickui.view.feature.list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import ru.melonity.feature.Feature;
import ru.melonity.feature.wheel.WheelFeature;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.feature.MenuFeatureFrame;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class MenuList {
   protected float width;
   protected float height;
   private List<Feature> features;
   private boolean search;
   private static final float GAP = 5.0F;
   private final Map<Feature, Integer> columnAssignments = new HashMap<>();
   private boolean columnsInitialized = false;

   public MenuList(float width, float height, List<Feature> features) {
      this.width = width;
      this.height = height;
      this.features = features;
   }

   public void insert(MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack, class_332 context) {
      if (!this.columnsInitialized) {
         this.initializeColumnAssignments();
         this.columnsInitialized = true;
      }

      float local11_2 = 0.0F;

      for (Feature local13_1 : this.features) {
         if (!this.search || !(local13_1 instanceof WheelFeature)) {
            Integer local14_0 = this.columnAssignments.get(local13_1);
            if (local14_0 == null) {
               local14_0 = 0;
               this.columnAssignments.put(local13_1, local14_0);
            }

            local13_1.getMenuObject().insert(style, x, y + local11_2, mouseX, mouseY, renderSystem, this.search, poseStack, context);
            local11_2 += local13_1.getMenuObject().getHeight() + 10.0F;
         }
      }

      this.height = local11_2;
   }

   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
      float local8_1 = 0.0F;

      for (Feature local10_0 : this.features) {
         if (!this.search || !(local10_0 instanceof WheelFeature)) {
            local10_0.getMenuObject().click(x, y + local8_1, mouseX, mouseY, mouseButton);
            local8_1 += local10_0.getMenuObject().getHeight() + 10.0F;
         }
      }
   }

   private void initializeColumnAssignments() {
      int local1_1 = 0;

      for (Feature local3_0 : this.features) {
         if (!this.search || !(local3_0 instanceof WheelFeature)) {
            this.columnAssignments.put(local3_0, local1_1);
         }
      }
   }

   public boolean key(int keyCode, int scanCode, int modifiers) {
      for (Feature local5_0 : this.features) {
         if (local5_0.getMenuObject().key(keyCode, scanCode, modifiers)) {
            return true;
         }
      }

      return false;
   }

   public void release() {
      this.features.stream().map(Feature::getMenuObject).forEach(MenuFeatureFrame::release);
   }

   public void resetColumns() {
      this.columnAssignments.clear();
      this.columnsInitialized = false;
   }

   @Generated
   public float getWidth() {
      return this.width;
   }

   @Generated
   public float getHeight() {
      return this.height;
   }

   @Generated
   public List<Feature> getFeatures() {
      return this.features;
   }

   @Generated
   public boolean isSearch() {
      return this.search;
   }

   @Generated
   public Map<Feature, Integer> getColumnAssignments() {
      return this.columnAssignments;
   }

   @Generated
   public boolean isColumnsInitialized() {
      return this.columnsInitialized;
   }

   @Generated
   public void setWidth(float width) {
      this.width = width;
   }

   @Generated
   public void setHeight(float height) {
      this.height = height;
   }

   @Generated
   public void setFeatures(List<Feature> features) {
      this.features = features;
   }

   @Generated
   public void setSearch(boolean search) {
      this.search = search;
   }

   @Generated
   public void setColumnsInitialized(boolean columnsInitialized) {
      this.columnsInitialized = columnsInitialized;
   }
}
