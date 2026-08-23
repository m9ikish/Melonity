package ru.melonity.ui.menu.clickui.view.macro;

import java.awt.Color;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.melonity.Melonity;
import ru.melonity.font.Fonts;
import ru.melonity.ui.components.view.StyledView;
import ru.melonity.ui.components.view.impl.ButtonView;
import ru.melonity.ui.menu.clickui.style.MenuStyle;
import ru.melonity.ui.menu.clickui.view.MenuFeatureHeader;
import ru.melonity.ui.notification.Notification;
import ru.melonity.util.macro.Action;
import ru.melonity.util.macro.Macro;
import ru.melonity.util.macro.MacroService;
import ru.melonity.util.macro.actions.AbstractToggleFunction;
import ru.melonity.util.macro.actions.UseItemAction;
import ru.melonity.util.macro.actions.WaitMillisAction;
import ru.melonity.util.macro.ui.MacrosBuilderScreen;
import ru.melonity.util.math.MathUtil;
import ru.melonity.util.render.RenderSystem;

@Environment(EnvType.CLIENT)
public class MenuMacroses extends StyledView {
   private String selectedMacro = "";
   private final MenuFeatureHeader listHeader = new MenuFeatureHeader("Macros list", "Macros you have created or imported");
   private final MenuFeatureHeader newMacro = new MenuFeatureHeader("New macro", "Build or import from clipboard macro", 174.0F, 82.0F);
   private final MenuFeatureHeader selectedMacroHeader = new MenuFeatureHeader("Selected macro", "Managing the selected macro from the list", 174.0F, 127.0F);
   private final ButtonView openBuilderBtn = new ButtonView("Open builder", 158.0F, 16.0F);
   private final ButtonView importFromClipboardBtn;
   private final ButtonView addToWheelBtn;
   private final ButtonView editInBuilderBtn;
   private final ButtonView exportToClipboardBtn;
   private final ButtonView deleteBtn;

   public MenuMacroses(float width, float height) {
      super(width, height);
      this.openBuilderBtn.setCenterText(true);
      this.importFromClipboardBtn = new ButtonView("Import from clipboard", 158.0F, 16.0F);
      this.importFromClipboardBtn.setCenterText(true);
      this.addToWheelBtn = new ButtonView("Add to wheel", 158.0F, 16.0F);
      this.addToWheelBtn.setCenterText(true);
      this.editInBuilderBtn = new ButtonView("Edit in builder", 158.0F, 16.0F);
      this.editInBuilderBtn.setCenterText(true);
      this.exportToClipboardBtn = new ButtonView("Export to clipboard", 158.0F, 16.0F);
      this.exportToClipboardBtn.setCenterText(true);
      this.deleteBtn = new ButtonView("Delete", 158.0F, 16.0F);
      this.deleteBtn.setCenterText(true);
   }

   @Override
   public void insert(MenuStyle style, float x, float y, double mouseX, double mouseY, RenderSystem renderSystem, class_4587 poseStack) {
      this.listHeader.insert(style, x, y, mouseX, mouseY, renderSystem, poseStack);
      MacroService local10_6 = Melonity.INSTANCE.getMacroService();
      float local11_7 = y + 40.0F;

      for (Macro local13_5 : local10_6.getMacroses()) {
         boolean local14_1 = local13_5.getName().equals(this.selectedMacro);
         boolean local15_2 = MathUtil.isMouseHoveringRect((double)(x + 8.0F), (double)local11_7, 164.0, 16.0, mouseX, mouseY);
         local13_5.getAnimation().state(local14_1);
         local13_5.getAnimation().update(local13_5.getFwc().elapsedUnits());
         float local16_3 = 0.0F;

         for (Action local18_0 : local13_5.getActions()) {
            local16_3 += local18_0.getDelay();
         }

         String local17_4 = local13_5.getActions().size() + " actions  " + String.format("%.1f", local16_3 / 1000.0F).replace(',', '.') + " s";
         if (local15_2) {
            renderSystem.addRect(x + 8.0F, local11_7, 164.0F, 16.0F, 8.0F, style.previewValues(), poseStack);
         }

         renderSystem.addRect(
            x + 8.0F, local11_7, 164.0F, 16.0F, 8.0F, RenderSystem.setAlpha(style.ascent(), (int)(local13_5.getAnimation().animation() * 255.0F)), poseStack
         );
         renderSystem.addText(
            Fonts.INTER_REGULAR_14, local13_5.getName(), x + 12.0F, local11_7 + 6.5F, local14_1 ? Color.decode("#141414") : Color.decode("#888888"), poseStack
         );
         renderSystem.addText(
            Fonts.INTER_REGULAR_14,
            local17_4,
            x + 164.0F - Fonts.INTER_REGULAR_14.getStringWidth(local17_4),
            local11_7 + 6.5F,
            local14_1 ? Color.decode("#141414") : Color.decode("#888888"),
            poseStack
         );
         local11_7 += 18.0F;
      }

      float local12_8 = x + 179.0F + 6.0F;
      this.newMacro.insert(style, local12_8, y, mouseX, mouseY, renderSystem, poseStack);
      this.openBuilderBtn.insert(local12_8 + 8.0F, y + 40.0F, mouseX, mouseY, renderSystem, poseStack);
      this.importFromClipboardBtn.insert(local12_8 + 8.0F, y + 60.0F, mouseX, mouseY, renderSystem, poseStack);
      this.selectedMacroHeader.insert(style, local12_8, y + 88.0F, mouseX, mouseY, renderSystem, poseStack);
      this.addToWheelBtn.insert(local12_8 + 8.0F, y + 128.0F, mouseX, mouseY, renderSystem, poseStack);
      this.editInBuilderBtn.insert(local12_8 + 8.0F, y + 152.0F, mouseX, mouseY, renderSystem, poseStack);
      this.exportToClipboardBtn.insert(local12_8 + 8.0F, y + 172.0F, mouseX, mouseY, renderSystem, poseStack);
      this.deleteBtn.insert(local12_8 + 8.0F, y + 195.0F, mouseX, mouseY, renderSystem, poseStack);
   }

   @Override
   public void click(float x, float y, double mouseX, double mouseY, int mouseButton) {
      MacroService local8_15 = Melonity.INSTANCE.getMacroService();
      float local9_16 = y + 40.0F;

      for (Macro local11_1 : local8_15.getMacroses()) {
         boolean local12_0 = MathUtil.isMouseHoveringRect((double)(x + 8.0F), (double)local9_16, 164.0, 16.0, mouseX, mouseY);
         if (local12_0) {
            this.selectedMacro = local11_1.getName();
         }

         local9_16 += 18.0F;
      }

      float local10_17 = x + 179.0F + 6.0F;
      if (MathUtil.isMouseHoveringRect(
         (double)(local10_17 + 8.0F), (double)(y + 40.0F), (double)this.openBuilderBtn.getWidth(), (double)this.openBuilderBtn.getHeight(), mouseX, mouseY
      )) {
         class_310.method_1551().method_1507(new MacrosBuilderScreen(null));
      }

      if (MathUtil.isMouseHoveringRect(
         (double)(local10_17 + 8.0F), (double)(y + 60.0F), (double)this.openBuilderBtn.getWidth(), (double)this.openBuilderBtn.getHeight(), mouseX, mouseY
      )) {
         try {
            String local11_4 = class_310.method_1551().field_1774.method_1460();
            String local12_5 = new String(Base64.getDecoder().decode(local11_4.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
            JSONObject local13_6 = new JSONObject(local12_5);
            String local14_7 = local13_6.getString("name");
            JSONArray local15_8 = local13_6.getJSONArray("actions");
            Macro local16_9 = new Macro(local14_7);

            for (Object local18_3 : local15_8) {
               JSONObject local19_2 = (JSONObject)local18_3;
               String var20 = local19_2.getString("id");
               switch (var20) {
                  case "function_toggle":
                     local16_9.getActions().add(AbstractToggleFunction.fromJson(local19_2));
                     break;
                  case "use_item":
                     local16_9.getActions().add(UseItemAction.fromJson(local19_2));
                     break;
                  case "wait_ms":
                     local16_9.getActions().add(WaitMillisAction.fromJson(local19_2));
                     break;
                  default:
                     class_310.method_1551().field_1705.method_1743().method_1812(class_2561.method_43470("Invalid action: " + local19_2.getString("id")));
               }
            }

            local8_15.addMacro(local16_9);
         } catch (Throwable var22) {
            Melonity.INSTANCE.getNotificationManager().add(new Notification("Error", "Invalid clipboard data", "feature-disabled.png"));
         }
      }

      if (MathUtil.isMouseHoveringRect(
         (double)(local10_17 + 8.0F), (double)(y + 128.0F), (double)this.openBuilderBtn.getWidth(), (double)this.openBuilderBtn.getHeight(), mouseX, mouseY
      )) {
         Optional<Macro> local11_11 = local8_15.getMacro(this.selectedMacro);
         local11_11.ifPresent(macro -> {
            Melonity.INSTANCE.getMenu().setMyMacrosesSelected(false);
            Melonity.INSTANCE.getMenu().getWheel().setPlaceTo(macro);
         });
      }

      if (MathUtil.isMouseHoveringRect(
         (double)(local10_17 + 8.0F), (double)(y + 152.0F), (double)this.openBuilderBtn.getWidth(), (double)this.openBuilderBtn.getHeight(), mouseX, mouseY
      )) {
         Optional<Macro> local11_12 = local8_15.getMacro(this.selectedMacro);
         local11_12.ifPresent(macro -> class_310.method_1551().method_1507(new MacrosBuilderScreen(macro)));
      }

      if (MathUtil.isMouseHoveringRect(
            (double)(local10_17 + 8.0F), (double)(y + 172.0F), (double)this.openBuilderBtn.getWidth(), (double)this.openBuilderBtn.getHeight(), mouseX, mouseY
         )
         && this.selectedMacro != null) {
         Optional<Macro> local11_14 = local8_15.getMacro(this.selectedMacro);
         if (local11_14.isPresent()) {
            String local12_13 = Base64.getEncoder().encodeToString(local11_14.get().toJson().toString().getBytes(StandardCharsets.UTF_8));
            class_310.method_1551().field_1774.method_1455(local12_13);
         }
      }

      if (MathUtil.isMouseHoveringRect(
         (double)(local10_17 + 8.0F), (double)(y + 195.0F), (double)this.openBuilderBtn.getWidth(), (double)this.openBuilderBtn.getHeight(), mouseX, mouseY
      )) {
         local8_15.removeMacro(this.selectedMacro);
      }
   }

   @Override
   public boolean key(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   @Override
   public void release() {
   }
}
