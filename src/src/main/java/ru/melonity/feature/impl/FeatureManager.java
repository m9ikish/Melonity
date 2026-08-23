package ru.melonity.feature.impl;

import lombok.SneakyThrows;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.Melonity;
import ru.melonity.feature.Feature;
import ru.melonity.feature.FeatureService;
import ru.melonity.feature.features.baritone.BaritoneFarmFeature;
import ru.melonity.feature.features.baritone.BaritoneGotoFeature;
import ru.melonity.feature.features.baritone.BaritoneHuntFeature;
import ru.melonity.feature.features.baritone.BaritoneMineFeature;
import ru.melonity.feature.features.modules.combat.AimAssistFeature;
import ru.melonity.feature.features.modules.combat.AntiMissFeature;
import ru.melonity.feature.features.modules.combat.AuraFeature;
import ru.melonity.feature.features.modules.combat.AutoGappleFeature;
import ru.melonity.feature.features.modules.combat.AutoPotionFeature;
import ru.melonity.feature.features.modules.combat.AutoTotemFeature;
import ru.melonity.feature.features.modules.combat.AutoWindCharge;
import ru.melonity.feature.features.modules.combat.BowSpammerFeature;
import ru.melonity.feature.features.modules.combat.ElytraHelperFeature;
import ru.melonity.feature.features.modules.combat.HitboxFeature;
import ru.melonity.feature.features.modules.combat.NoFriendDamageFeature;
import ru.melonity.feature.features.modules.combat.PearlTargetFeature;
import ru.melonity.feature.features.modules.combat.ProjectileHelperFeature;
import ru.melonity.feature.features.modules.combat.ResolverFeature;
import ru.melonity.feature.features.modules.combat.SurroundFeature;
import ru.melonity.feature.features.modules.combat.TargetStrafeFeature;
import ru.melonity.feature.features.modules.combat.TriggerBotFeature;
import ru.melonity.feature.features.modules.misc.AntiAFK;
import ru.melonity.feature.features.modules.misc.AuctionHelperFeature;
import ru.melonity.feature.features.modules.misc.AutoAcceptFeature;
import ru.melonity.feature.features.modules.misc.AutoAuthFeature;
import ru.melonity.feature.features.modules.misc.AutoBuyFeature;
import ru.melonity.feature.features.modules.misc.ClanUpgradeFeature;
import ru.melonity.feature.features.modules.misc.CreeperFarmFeature;
import ru.melonity.feature.features.modules.misc.ItemScrollerFeature;
import ru.melonity.feature.features.modules.misc.ItemSwapFixFeature;
import ru.melonity.feature.features.modules.misc.NoPlayerTraceFeature;
import ru.melonity.feature.features.modules.misc.PotionBotFeature;
import ru.melonity.feature.features.modules.misc.PotionsHighlightFeature;
import ru.melonity.feature.features.modules.misc.ServerAssistFeature;
import ru.melonity.feature.features.modules.misc.TPSSyncFeature;
import ru.melonity.feature.features.modules.misc.ToggleSoundFeature;
import ru.melonity.feature.features.modules.movement.AirJumpFeature;
import ru.melonity.feature.features.modules.movement.AutoSprintFeature;
import ru.melonity.feature.features.modules.movement.DragonFlyFeature;
import ru.melonity.feature.features.modules.movement.ElytraRecastFeature;
import ru.melonity.feature.features.modules.movement.JesusFeature;
import ru.melonity.feature.features.modules.movement.NoEatSlowFeature;
import ru.melonity.feature.features.modules.movement.NoVelocityFeature;
import ru.melonity.feature.features.modules.movement.ParkourFeature;
import ru.melonity.feature.features.modules.movement.SpeedFeature;
import ru.melonity.feature.features.modules.movement.SpiderFeature;
import ru.melonity.feature.features.modules.movement.WaterSpeedFeature;
import ru.melonity.feature.features.modules.player.AncientXRay;
import ru.melonity.feature.features.modules.player.AutoLeaveFeature;
import ru.melonity.feature.features.modules.player.AutoRespawnFeature;
import ru.melonity.feature.features.modules.player.AutoSaveFeature;
import ru.melonity.feature.features.modules.player.AutoToolFeature;
import ru.melonity.feature.features.modules.player.ChestStealerFeature;
import ru.melonity.feature.features.modules.player.DeathCoordsFeature;
import ru.melonity.feature.features.modules.player.FastBreakFeature;
import ru.melonity.feature.features.modules.player.FastUseFeature;
import ru.melonity.feature.features.modules.player.FreeCamFeature;
import ru.melonity.feature.features.modules.player.GappleTimerFeature;
import ru.melonity.feature.features.modules.player.GuiMoveFeature;
import ru.melonity.feature.features.modules.player.InstaRespawnFeature;
import ru.melonity.feature.features.modules.player.Joiner;
import ru.melonity.feature.features.modules.player.JumpCountFeature;
import ru.melonity.feature.features.modules.player.MiddleClickPearlFeature;
import ru.melonity.feature.features.modules.player.NameProtectFeature;
import ru.melonity.feature.features.modules.player.NoInteractFeature;
import ru.melonity.feature.features.modules.player.NoPushFeature;
import ru.melonity.feature.features.modules.player.NoServerRotationFeature;
import ru.melonity.feature.features.modules.player.NukerFeature;
import ru.melonity.feature.features.modules.player.RegionViewerFeature;
import ru.melonity.feature.features.modules.player.SafeWalkFeature;
import ru.melonity.feature.features.modules.player.TimerFeature;
import ru.melonity.feature.features.modules.render.BlockEspFeature;
import ru.melonity.feature.features.modules.render.CirclesFeature;
import ru.melonity.feature.features.modules.render.CustomInventory;
import ru.melonity.feature.features.modules.render.DamageParticlesFeature;
import ru.melonity.feature.features.modules.render.ESPFeature;
import ru.melonity.feature.features.modules.render.FogColorFeature;
import ru.melonity.feature.features.modules.render.FullBrightFeature;
import ru.melonity.feature.features.modules.render.HudFeature;
import ru.melonity.feature.features.modules.render.NimbusFeature;
import ru.melonity.feature.features.modules.render.NoRenderFeature;
import ru.melonity.feature.features.modules.render.PredictionsFeature;
import ru.melonity.feature.features.modules.render.SchedulesFeature;
import ru.melonity.feature.features.modules.render.SeeInvisiblesFeature;
import ru.melonity.feature.features.modules.render.ShaderEspFeature;
import ru.melonity.feature.features.modules.render.SwingAnimationFeature;
import ru.melonity.feature.features.modules.render.TNTTimerFeature;
import ru.melonity.feature.features.modules.render.TargetESPFeature;
import ru.melonity.feature.features.modules.render.TargetHUDFeature;
import ru.melonity.feature.features.modules.render.ViewModelFeature;
import ru.melonity.feature.features.modules.render.WorldTimeFeature;
import ru.melonity.feature.wheel.WheelFeature;
import ru.melonity.ui.menu.clickui.MenuCategory;

@Environment(EnvType.CLIENT)
public class FeatureManager implements FeatureService {
   public AimAssistFeature aimAssistFeature = new AimAssistFeature();
   public AuraFeature auraFeature = new AuraFeature();
   public WaterSpeedFeature waterSpeedFeature = new WaterSpeedFeature();
   public HudFeature hudFeature = new HudFeature();
   public TimerFeature timerFeature = new TimerFeature();
   public NoServerRotationFeature noServerRotationFeature = new NoServerRotationFeature();
   public GuiMoveFeature guiMoveFeature = new GuiMoveFeature();
   public GappleTimerFeature gappleTimerFeature = new GappleTimerFeature();
   public TargetStrafeFeature targetStrafeFeature = new TargetStrafeFeature();
   public ItemSwapFixFeature itemSwapFixFeature = new ItemSwapFixFeature();
   public ElytraHelperFeature elytraHelperFeature = new ElytraHelperFeature();
   public AutoAcceptFeature autoAcceptFeature = new AutoAcceptFeature();
   public ESPFeature espFeature = new ESPFeature();
   public BowSpammerFeature bowSpammerFeature = new BowSpammerFeature();
   public TNTTimerFeature tntTimerFeature = new TNTTimerFeature();
   public AutoBuyFeature autoBuyFeature = new AutoBuyFeature();
   public NoFriendDamageFeature noFriendDamageFeature = new NoFriendDamageFeature();
   public JesusFeature jesusFeature = new JesusFeature();
   public SpeedFeature speedFeature = new SpeedFeature();
   public AutoLeaveFeature autoLeaveFeature = new AutoLeaveFeature();
   public SchedulesFeature schedulesFeature = new SchedulesFeature();
   public SafeWalkFeature safeWalkFeature = new SafeWalkFeature();
   public RegionViewerFeature regionViewerFeature = new RegionViewerFeature();
   public MiddleClickPearlFeature middleClickPearlFeature = new MiddleClickPearlFeature();
   public NukerFeature nukerFeature = new NukerFeature();
   public ResolverFeature resolverFeature = new ResolverFeature();
   public NameProtectFeature nameProtectFeature = new NameProtectFeature();
   public ItemScrollerFeature itemScrollerFeature = new ItemScrollerFeature();
   public DeathCoordsFeature deathCoordsFeature = new DeathCoordsFeature();
   public PearlTargetFeature pearlTargetFeature = new PearlTargetFeature();
   public CirclesFeature circlesFeature = new CirclesFeature();
   public DamageParticlesFeature damageParticlesFeature = new DamageParticlesFeature();
   public PredictionsFeature predictionsFeature = new PredictionsFeature();
   public NimbusFeature nimbusFeature = new NimbusFeature();
   public PotionBotFeature potionBotFeature = new PotionBotFeature();
   public SwingAnimationFeature swingAnimationFeature = new SwingAnimationFeature();
   public AutoRespawnFeature autoRespawnFeature = new AutoRespawnFeature();
   public ShaderEspFeature shaderEspFeature = new ShaderEspFeature();
   public AutoTotemFeature autoTotemFeature = new AutoTotemFeature();
   public AutoAuthFeature autoAuthFeature = new AutoAuthFeature();
   public SeeInvisiblesFeature seeInvisiblesFeature = new SeeInvisiblesFeature();
   public NoRenderFeature noRenderFeature = new NoRenderFeature();
   public TriggerBotFeature triggerBotFeature = new TriggerBotFeature();
   public ViewModelFeature viewModelFeature = new ViewModelFeature();
   public NoPlayerTraceFeature noPlayerTraceFeature = new NoPlayerTraceFeature();
   public AutoToolFeature autoToolFeature = new AutoToolFeature();
   public NoInteractFeature noInteractFeature = new NoInteractFeature();
   public AntiAFK antiAFK = new AntiAFK();
   public BlockEspFeature blockEspFeature = new BlockEspFeature();
   public AutoGappleFeature autoGappleFeature = new AutoGappleFeature();
   public HitboxFeature hitboxFeature = new HitboxFeature();
   public ProjectileHelperFeature projectileHelperFeature = new ProjectileHelperFeature();
   public DragonFlyFeature dragonFlyFeature = new DragonFlyFeature();
   public Joiner joiner = new Joiner();
   public TargetHUDFeature targetHudFeature = new TargetHUDFeature();
   public TargetESPFeature targetEspFeature = new TargetESPFeature();
   public ChestStealerFeature chestStealerFeature = new ChestStealerFeature();
   public WorldTimeFeature worldTimeFeature = new WorldTimeFeature();
   public AutoWindCharge autoWindCharge = new AutoWindCharge();
   public AutoPotionFeature autoPotionFeature = new AutoPotionFeature();
   public FogColorFeature fogColorFeature = new FogColorFeature();
   public FreeCamFeature freeCamFeature = new FreeCamFeature();
   public PotionsHighlightFeature potionsHighlightFeature = new PotionsHighlightFeature();
   public AntiMissFeature antiMissFeature = new AntiMissFeature();
   public SurroundFeature surroundFeature = new SurroundFeature();
   public AutoSaveFeature autoSaveFeature = new AutoSaveFeature();
   public CreeperFarmFeature creeperFarmFeature = new CreeperFarmFeature();
   public AuctionHelperFeature auctionHelperFeature = new AuctionHelperFeature();
   public InstaRespawnFeature instaRespawnFeature = new InstaRespawnFeature();
   public ElytraRecastFeature elytraRecastFeature = new ElytraRecastFeature();
   public ToggleSoundFeature toggleSoundFeature = new ToggleSoundFeature();
   public SpiderFeature spiderFeature = new SpiderFeature();
   public ClanUpgradeFeature clanUpgradeFeature = new ClanUpgradeFeature();
   public AncientXRay ancientXRay = new AncientXRay();
   public ServerAssistFeature serverAssistFeature = new ServerAssistFeature();
   public NoVelocityFeature noVelocityFeature = new NoVelocityFeature();
   public AirJumpFeature airJumpFeature = new AirJumpFeature();
   public CustomInventory customInventory = new CustomInventory();
   public FastBreakFeature fastBreakFeature = new FastBreakFeature();
   public FastUseFeature fastUseFeature = new FastUseFeature();
   public ParkourFeature parkourFeature = new ParkourFeature();
   public BaritoneMineFeature baritoneMineFeature = new BaritoneMineFeature();
   public BaritoneHuntFeature baritoneHuntFeature = new BaritoneHuntFeature();
   public BaritoneFarmFeature baritoneFarmFeature = new BaritoneFarmFeature();
   public TPSSyncFeature tpsSyncFeature = new TPSSyncFeature();
   public BaritoneGotoFeature baritoneGotoFeature = new BaritoneGotoFeature();
   public WheelFeature wheelFeature = new WheelFeature();
   public NoPushFeature noPushFeature = new NoPushFeature();
   public JumpCountFeature jumpCountFeature = new JumpCountFeature();
   public AutoSprintFeature autoSprintFeature = new AutoSprintFeature();
   public NoEatSlowFeature noEatSlowFeature = new NoEatSlowFeature();
   public FullBrightFeature fullBrightFeature = new FullBrightFeature();
   public final List<Feature> features = Lists.newLinkedList();
   private final Map<Class<? extends Feature>, Feature> featureCache = new HashMap<>();

   public void initFeatures() {
      this.registerFeature(this.aimAssistFeature);
      this.registerFeature(this.noPushFeature);
      this.registerFeature(this.jumpCountFeature);
      this.registerFeature(this.autoSprintFeature);
      this.registerFeature(this.noEatSlowFeature);
      this.registerFeature(this.autoWindCharge);
      this.registerFeature(this.surroundFeature);
      this.registerFeature(this.auraFeature);
      this.registerFeature(this.waterSpeedFeature);
      this.registerFeature(this.hudFeature);
      this.registerFeature(this.autoGappleFeature);
      this.registerFeature(this.timerFeature);
      this.registerFeature(this.noServerRotationFeature);
      this.registerFeature(this.guiMoveFeature);
      this.registerFeature(this.gappleTimerFeature);
      this.registerFeature(this.targetStrafeFeature);
      this.registerFeature(this.itemSwapFixFeature);
      this.registerFeature(this.elytraHelperFeature);
      this.registerFeature(this.autoAcceptFeature);
      this.registerFeature(this.espFeature);
      this.registerFeature(this.bowSpammerFeature);
      this.registerFeature(this.tntTimerFeature);
      this.registerFeature(this.autoBuyFeature);
      this.registerFeature(this.noFriendDamageFeature);
      this.registerFeature(this.jesusFeature);
      this.registerFeature(this.speedFeature);
      this.registerFeature(this.autoLeaveFeature);
      this.registerFeature(this.schedulesFeature);
      this.registerFeature(this.safeWalkFeature);
      this.registerFeature(this.regionViewerFeature);
      this.registerFeature(this.middleClickPearlFeature);
      this.registerFeature(this.nukerFeature);
      this.registerFeature(this.nameProtectFeature);
      this.registerFeature(this.itemScrollerFeature);
      this.registerFeature(this.deathCoordsFeature);
      this.registerFeature(this.pearlTargetFeature);
      this.registerFeature(this.circlesFeature);
      this.registerFeature(this.damageParticlesFeature);
      this.registerFeature(this.predictionsFeature);
      this.registerFeature(this.nimbusFeature);
      this.registerFeature(this.potionBotFeature);
      this.registerFeature(this.swingAnimationFeature);
      this.registerFeature(this.autoRespawnFeature);
      this.registerFeature(this.shaderEspFeature);
      this.registerFeature(this.fullBrightFeature);
      this.registerFeature(this.autoTotemFeature);
      this.registerFeature(this.autoAuthFeature);
      this.registerFeature(this.seeInvisiblesFeature);
      this.registerFeature(this.noRenderFeature);
      this.registerFeature(this.triggerBotFeature);
      this.registerFeature(this.customInventory);
      this.registerFeature(this.viewModelFeature);
      this.registerFeature(this.noPlayerTraceFeature);
      this.registerFeature(this.autoToolFeature);
      this.registerFeature(this.noInteractFeature);
      this.registerFeature(this.antiAFK);
      this.registerFeature(this.blockEspFeature);
      this.registerFeature(this.hitboxFeature);
      this.registerFeature(this.projectileHelperFeature);
      this.registerFeature(this.dragonFlyFeature);
      this.registerFeature(this.joiner);
      this.registerFeature(this.targetHudFeature);
      this.registerFeature(this.targetEspFeature);
      this.registerFeature(this.chestStealerFeature);
      this.registerFeature(this.worldTimeFeature);
      this.registerFeature(this.autoPotionFeature);
      this.registerFeature(this.fogColorFeature);
      this.registerFeature(this.freeCamFeature);
      this.registerFeature(this.potionsHighlightFeature);
      this.registerFeature(this.antiMissFeature);
      this.registerFeature(this.autoSaveFeature);
      this.registerFeature(this.creeperFarmFeature);
      this.registerFeature(this.auctionHelperFeature);
      this.registerFeature(this.instaRespawnFeature);
      this.registerFeature(this.elytraRecastFeature);
      this.registerFeature(this.toggleSoundFeature);
      this.registerFeature(this.spiderFeature);
      this.registerFeature(this.clanUpgradeFeature);
      this.registerFeature(this.ancientXRay);
      this.registerFeature(this.serverAssistFeature);
      this.registerFeature(this.noVelocityFeature);
      this.registerFeature(this.airJumpFeature);
      this.registerFeature(this.fastBreakFeature);
      this.registerFeature(this.fastUseFeature);
      this.registerFeature(this.parkourFeature);
      this.registerFeature(this.baritoneMineFeature);
      this.registerFeature(this.baritoneHuntFeature);
      this.registerFeature(this.baritoneFarmFeature);
      this.registerFeature(this.tpsSyncFeature);
      this.registerFeature(this.baritoneGotoFeature);
      this.registerFeature(this.wheelFeature);
   }

   @Override
   @SneakyThrows
   public void registerFeature(Feature feature) {
      this.features.add(feature);
      this.featureCache.put((Class<? extends Feature>)feature.getClass(), feature);
      Melonity.INSTANCE.getEventBus().listen(feature);
   }

   @Deprecated
   @Override
   @SneakyThrows
   public <T extends Feature> Optional<T> getFeature(Class<T> featureClass) {
      Feature local2_0 = this.featureCache.get(featureClass);
      return local2_0 == null ? Optional.empty() : Optional.of(featureClass.cast(local2_0));
   }

   @Deprecated
   @Override
   public Optional<Feature> getFeature(String name) {
      for (Feature local3_0 : this.features) {
         if (local3_0.getName().equalsIgnoreCase(name)) {
            return Optional.of(local3_0);
         }
      }

      return Optional.empty();
   }

   @Override
   public List<Feature> getFeatures() {
      return ImmutableList.copyOf(this.features);
   }

   @Override
   public List<Feature> getFeaturesInCategory(MenuCategory category) {
      return ImmutableList.copyOf(this.features.stream().filter(feature -> feature.getCategory() == category).toList());
   }

   /**
    * Orders the feature list the way the click-GUI renders it: a handful of "flagship" features are
    * pinned to the very top, everything else is grouped by the first letter of its name.
    *
    * <p>Reconstructed from {@code FeatureManager.lambda$sort$1}, which survived the native
    * conversion: it is a {@code ToDoubleFunction<Feature>} returning {@code -Infinity} for the six
    * pinned names and {@code getName().charAt(0)} otherwise, so the sort was
    * {@code Comparator.comparingDouble(...)} over that function.
    */
   @Override
   public void sort() {
      this.features
         .sort(
            Comparator.comparingDouble(
               feature -> {
                  if (feature.getName().equalsIgnoreCase("KillAura")
                     || feature.getName().equalsIgnoreCase("ESP")
                     || feature.getName().equalsIgnoreCase("Hud")
                     || feature.getName().equalsIgnoreCase("3D Particles")
                     || feature.getName().equalsIgnoreCase("Circles")
                     || feature.getName().equalsIgnoreCase("ItemESP")) {
                     return Double.NEGATIVE_INFINITY;
                  }

                  return feature.getName().charAt(0);
               }
            )
         );
   }
}
