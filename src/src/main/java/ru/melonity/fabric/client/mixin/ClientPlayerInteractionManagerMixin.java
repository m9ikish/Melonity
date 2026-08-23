package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2848;
import net.minecraft.class_2848.class_2849;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import net.minecraft.class_636;
import net.minecraft.class_638;
import net.minecraft.class_7204;
import net.minecraft.class_746;
import net.minecraft.class_7923;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.melonity.Melonity;
import ru.melonity.fabric.client.model.IClientPlayerInteractionManager;
import ru.melonity.fabric.client.model.ILocalPlayer;
import ru.melonity.feature.features.modules.misc.ServerAssistFeature;
import ru.melonity.feature.features.modules.player.NoInteractFeature;
import ru.melonity.util.event.events.AttackEvent;
import ru.melonity.util.event.events.EventObsidianPlace;

@Environment(EnvType.CLIENT)
@Mixin({class_636.class})
public class ClientPlayerInteractionManagerMixin implements IClientPlayerInteractionManager {
   @Shadow
   private void method_41931(class_638 world, class_7204 packetCreator) {
   }

   @Inject(
      method = {"clickSlot"},
      at = {@At("HEAD")}
   )
   public void clickSlot(int syncId, int slotId, int button, class_1713 actionType, class_1657 player, CallbackInfo callbackInfo) {
      // This injection point was empty, which left every silent inventory action (ChestStealer,
      // AutoBuy, the inventory filter, MineHelper's trash dropping, the joiners, ...) sending a
      // window click while the server still believed the player was sprinting. A real player
      // cannot do that - opening a container clears the sprint state first - and GrimAC reports
      // it as MultiActionsC with verbose "sprinting".
      //
      // Release the sprint right here. lastSprinting is synced so vanilla's sendSprintingPacket
      // does not add a second stop packet, and if the sprint key is still held vanilla puts the
      // player back into a sprint (and sends START_SPRINTING) on the very next tick by itself.
      class_310 client = class_310.method_1551();
      class_746 localPlayer = client.field_1724;
      if (localPlayer != null && player == localPlayer && localPlayer.method_5624()) {
         localPlayer.field_3944.method_52787(new class_2848(localPlayer, class_2849.field_12985));
         ((ILocalPlayer)localPlayer).setServerSprintState(false);
         localPlayer.method_5728(false);
      }
   }

   @Inject(
      method = {"interactBlock"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onInteractBlock(class_746 player, class_1268 hand, class_3965 hitResult, CallbackInfoReturnable<class_1269> cir) {
      class_1799 stack = player.method_5998(hand);
      ServerAssistFeature sa = Melonity.INSTANCE.getFeatureManager().getFeature(ServerAssistFeature.class).get();
      if (sa.isEnabled()
         && (
            sa.getMode().contains("Funtime") && sa.getSettingFuntime().contains("Don't put the ball")
               || sa.getMode().contains("HolyWorld") && sa.getSettingHolyWorld().contains("Don't put the ball")
               || sa.getMode().contains("ReallyWorld") && sa.getSettingReallyWorld().contains("Don't put the ball")
         )
         && stack.method_31574(class_1802.field_8575)) {
         cir.setReturnValue(class_1269.field_5811);
         cir.cancel();
      }
   }

   @Inject(
      method = {"interactBlock"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void interactBlock(class_746 player, class_1268 hand, class_3965 blockHitResult, CallbackInfoReturnable<class_1269> cir) {
      if (player.method_6047().method_7909() == class_1802.field_8281) {
         Melonity.INSTANCE.getEventBus().fire(new EventObsidianPlace(blockHitResult.method_17777()));
      }

      NoInteractFeature noInteract = Melonity.INSTANCE.getFeatureManager().getFeature(NoInteractFeature.class).orElse(null);
      if (noInteract != null && noInteract.isEnabled()) {
         class_2248 block = player.field_17892.method_8320(blockHitResult.method_17777()).method_26204();
         String descriptionId = this.makeDescriptionId(class_7923.field_41175.method_10221(block));
         boolean flag = noInteract.getAllBlocks().get()
            || noInteract.getIgnores().contains("nointeract.chest") && block == class_2246.field_10034
            || noInteract.getIgnores().contains("nointeract.door") && descriptionId.contains("door")
            || noInteract.getIgnores().contains("nointeract.button") && descriptionId.contains("button")
            || noInteract.getIgnores().contains("nointeract.dispenser") && block == class_2246.field_10200
            || noInteract.getIgnores().contains("nointeract.crafttable") && block == class_2246.field_9980
            || noInteract.getIgnores().contains("nointeract.trapdoor") && descriptionId.contains("trapdoor")
            || noInteract.getIgnores().contains("nointeract.furnace") && block == class_2246.field_10181
            || noInteract.getIgnores().contains("nointeract.lever") && block == class_2246.field_10363
            || noInteract.getIgnores().contains("nointeract.anvil") && block == class_2246.field_10535
            || noInteract.getIgnores().contains("nointeract.noteblock") && block == class_2246.field_10179
            || noInteract.getIgnores().contains("nointeract.hopper") && block == class_2246.field_10312;
         if (flag) {
            cir.setReturnValue(class_1269.field_5811);
         }
      }
   }

   @Unique
   private String makeDescriptionId(@Nullable class_2960 p_137494_) {
      return p_137494_ == null ? "block.unregistered_sadface" : "block." + p_137494_.method_12836() + "." + p_137494_.method_12832().replace('/', '.');
   }

   @Override
   public void trySendSequencedPacket(class_638 world, class_7204 packetCreator) {
      this.method_41931(world, packetCreator);
   }

   @Inject(
      method = {"attackEntity"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void attackEntity(class_1657 player, class_1297 target, CallbackInfo callbackInfo) {
      AttackEvent attackEvent = new AttackEvent(target);
      Melonity.INSTANCE.getEventBus().fire(attackEvent);
      if (attackEvent.isCancelled()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"attackEntity"},
      at = {@At("RETURN")}
   )
   public void attackEntity_ret(class_1657 player, class_1297 target, CallbackInfo callbackInfo) {
      class_310.method_1551().field_1690.field_1867.method_23481(((ILocalPlayer)class_310.method_1551().field_1724).serverSprintState());
   }
}
