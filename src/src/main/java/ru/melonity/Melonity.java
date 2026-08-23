package ru.melonity;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_290;
import net.minecraft.class_293;
import net.minecraft.class_5912;
import org.lwjgl.glfw.GLFW;
import ru.melonity.api.components.ServiceDescriptor;
import ru.melonity.api.serviceContainer.ComponentManagerImpl;
import ru.melonity.feature.ActionScheduler;
import ru.melonity.feature.config.Config;
import ru.melonity.feature.features.command.CommandService;
import ru.melonity.feature.features.command.impl.CommandServiceImpl;
import ru.melonity.feature.features.command.impl.SymbolPrefix;
import ru.melonity.feature.impl.FeatureManager;
import ru.melonity.feature.rotation.RotationManager;
import ru.melonity.proxy.ProxyManager;
import ru.melonity.service.friend.FriendService;
import ru.melonity.service.friend.impl.FriendServiceImpl;
import ru.melonity.ui.drag.DragService;
import ru.melonity.ui.drag.impl.DragManager;
import ru.melonity.ui.menu.clickui.Menu;
import ru.melonity.ui.menu.clickui.style.StyleManager;
import ru.melonity.ui.menu.clickui.view.wheel.WheelEntryView;
import ru.melonity.ui.notification.NotificationManager;
import ru.melonity.util.altmanager.AltService;
import ru.melonity.util.altmanager.ProfileType;
import ru.melonity.util.altmanager.impl.AltManager;
import ru.melonity.util.altmanager.impl.crack.CrackedProfileProvider;
import ru.melonity.util.altmanager.impl.microsoft.RedirectMicrosoftProfileProvider;
import ru.melonity.util.altmanager.impl.mojang.MojangProfileProvider;
import ru.melonity.util.event.EventBus;
import ru.melonity.util.event.impl.GenericPatternEventBus;
import ru.melonity.util.event.listeners.BindListener;
import ru.melonity.util.event.listeners.FunTimeHealthFixerListener;
import ru.melonity.util.event.listeners.GPSListener;
import ru.melonity.util.event.listeners.InventoryFilterListener;
import ru.melonity.util.event.listeners.RShiftListener;
import ru.melonity.util.event.listeners.SwitcherTickListener;
import ru.melonity.util.inventory.InventoryService;
import ru.melonity.util.language.Language;
import ru.melonity.util.language.LanguageService;
import ru.melonity.util.language.impl.LanguageManager;
import ru.melonity.util.language.provider.impl.JsonFileLanguageProvider;
import ru.melonity.util.macro.MacroService;
import ru.melonity.util.macro.impl.MacroManager;
import ru.melonity.util.managers.InventoryManager;
import ru.melonity.util.render.RenderSystem;
import ru.melonity.util.render.shader.ShaderManager;
import ru.melonity.util.render.shader.ShaderProgram;
import ru.melonity.util.web.CloudConfig;
import ru.melonity.util.web.protect.UserInfo;

@Environment(EnvType.CLIENT)
public enum Melonity {
   INSTANCE;

   private static boolean initialized = false;
   public LanguageService languageService;
   public InventoryService inventoryService;
   public EventBus eventBus;
   public GPSListener gps;
   public InventoryFilterListener filter;
   public DragService dragService;
   public FeatureManager featureManager;
   public ShaderManager shaderManager;
   public StyleManager styleManager;
   public Menu menu;
   public RenderSystem renderSystem;
   public CommandService commandService;
   public ProxyManager proxyManager;
   public NotificationManager notificationManager;
   public MacroService macroService;
   public boolean safe;
   CloudConfig config = null;
   UserInfo userInfo;
   public final ComponentManagerImpl componentManager;
   public static boolean renderLabel = true;
   public static boolean packetEvent = true;
   public static boolean hand = false;
   public static boolean text = false;
   public static long ARROW;
   public static long HAND;
   public static long RESIZEH;
   public static long IBEAM;
   final List<WheelEntryView> wheelEntries = Lists.newLinkedList();
   public static float timerSpeed = 1.0F;

   private Melonity() {
      this.componentManager = new ComponentManagerImpl();
      this.componentManager.registerService(FriendService.class, FriendServiceImpl.class, true, ServiceDescriptor.LoadMode.FIRST);
      this.componentManager.registerService(AltService.class, AltManager.class, true, ServiceDescriptor.LoadMode.FIRST);
   }

   /**
    * Client bootstrap, invoked from {@code GameRendererMixin#loadPrograms} - i.e. once at startup
    * and again after every resource reload. The service graph is built exactly once (guarded by
    * {@link #initialized}) while the shader programs are rebuilt on every call, because the vanilla
    * shader reload throws the previous GL programs away.
    *
    * <p>The original body also performed the license handshake (it was annotated {@code @Handshake}
    * and the class constant pool still carries the {@code java.net.http} references it used). That
    * part is intentionally not restored - the client runs fully offline with a local placeholder
    * {@link UserInfo}.
    */
   public void init(class_5912 resourceManager) {
      if (!initialized) {
         ARROW = GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR);
         HAND = GLFW.glfwCreateStandardCursor(GLFW.GLFW_HAND_CURSOR);
         RESIZEH = GLFW.glfwCreateStandardCursor(GLFW.GLFW_HRESIZE_CURSOR);
         IBEAM = GLFW.glfwCreateStandardCursor(GLFW.GLFW_IBEAM_CURSOR);
         this.componentManager.loadInOrder(ServiceDescriptor.LoadMode.FIRST);
         this.languageService = new LanguageManager(new JsonFileLanguageProvider());

         try {
            this.languageService.parseLanguage(Language.RU);
            this.languageService.parseLanguage(Language.EN);
         } catch (Throwable languageFailure) {
            languageFailure.printStackTrace();
         }

         this.eventBus = new GenericPatternEventBus();
         this.inventoryService = new InventoryManager();
         this.gps = new GPSListener();
         this.filter = new InventoryFilterListener();
         this.dragService = new DragManager();
         this.notificationManager = new NotificationManager();
         this.renderSystem = new RenderSystem();
         this.shaderManager = new ShaderManager();
         this.styleManager = new StyleManager();
         this.proxyManager = new ProxyManager();
         this.macroService = new MacroManager();
         // The wheel has exactly six slots, and nothing else ever fills this list.
         // WheelFeature indexes get(0)..get(5) directly and MenuWheel.click walks all of
         // ENTRY_COORDINATES without a bounds check, so it has to be fully populated here,
         // before any feature or screen can reach it.
         for (int wheelSlot = 0; wheelSlot < 6; wheelSlot++) {
            this.wheelEntries.add(new WheelEntryView(80.0F, 46.0F));
         }

         this.commandService = new CommandServiceImpl(new SymbolPrefix("."));
         this.userInfo = new UserInfo(0, "Player", "");
         this.eventBus.listen(new RotationManager());
         this.eventBus.listen(ActionScheduler.INSTANCE);
         this.componentManager.loadInOrder(ServiceDescriptor.LoadMode.BEFORE_FEATURES);
         this.featureManager = new FeatureManager();
         this.featureManager.initFeatures();
         this.featureManager.sort();
         this.componentManager.loadInOrder(ServiceDescriptor.LoadMode.AFTER_FEATURES);
         this.eventBus
            .registerListeners(
               this.gps, this.filter, new RShiftListener(), new SwitcherTickListener(), new FunTimeHealthFixerListener(), new BindListener()
            );
         this.componentManager.loadInOrder(ServiceDescriptor.LoadMode.AFTER_EVENTBUS);
         this.componentManager.loadInOrder(ServiceDescriptor.LoadMode.BEFORE_UI);
         this.menu = new Menu();

         try {
            AltService altService = this.componentManager.service(AltService.class);
            altService.registerProvider(ProfileType.MICROSOFT, new RedirectMicrosoftProfileProvider());
            altService.registerProvider(ProfileType.MOJANG, new MojangProfileProvider());
            altService.registerProvider(ProfileType.CRACK, new CrackedProfileProvider());
            altService.load();
         } catch (Throwable altManagerFailure) {
            altManagerFailure.printStackTrace();
         }

         // Nothing ever assigned this.config, so the guard in the shutdown hook below
         // was always false and every setting was thrown away on exit. Open (or create)
         // the Default config here, after the features and the menu exist - Config.load
         // walks featureManager.getFeatures() and each feature's menu views.
         try {
            this.config = CloudConfig.defaultConfig(this.userInfo.userId());
            if (this.config == null) {
               System.err.println(
                  "[Melonity] Could not open or create the Default config in C:/Melonity Minecraft/configs - settings will NOT persist this session"
               );
            } else if (this.config.content != null && !this.config.content.isBlank()) {
               Config.load(this.config.content);
            }
         } catch (Throwable configFailure) {
            configFailure.printStackTrace();
         }

         Runtime.getRuntime().addShutdownHook(new Thread(this::wtfAreYouDoing));
         Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (this.config != null && this.config.name.equalsIgnoreCase("Default")) {
               this.config.updateConfig(this.userInfo.userId(), this.config.uuid, Config.save().toString());
            }
         }));
         initialized = true;
      }

      this.loadShader(resourceManager, "round_rect", class_290.field_1592);
      this.loadShader(resourceManager, "round_rect_outline", class_290.field_1592);
      this.loadShader(resourceManager, "round_rect_gradient", class_290.field_1592);
      this.loadShader(resourceManager, "round_rect_gradient_corners", class_290.field_1592);
      this.loadShader(resourceManager, "outer_shadow", class_290.field_1592);
      this.loadShader(resourceManager, "color_picker", class_290.field_1592);
      this.loadShader(resourceManager, "hue_bar", class_290.field_1592);
      this.loadShader(resourceManager, "circle_r", class_290.field_1592);
      this.loadShader(resourceManager, "black_and_white", class_290.field_1592);
      this.loadShader(resourceManager, "liquid_glass", class_290.field_1592);
      this.loadShader(resourceManager, "blur_round", class_290.field_1585);
      this.loadShader(resourceManager, "round_texture", class_290.field_1585);
      this.loadShader(resourceManager, "glow_outline", class_290.field_1585);
      this.loadShader(resourceManager, "outline_glow", class_290.field_1585);
      this.loadShader(resourceManager, "shitten_glow", class_290.field_1585);
      this.loadShader(resourceManager, "galaxy1", class_290.field_1585);
      this.loadShader(resourceManager, "galaxy2", class_290.field_1585);
      this.loadShader(resourceManager, "galaxy3", class_290.field_1585);
   }

   /**
    * Compiles {@code assets/minecraft/shaders/core/<name>.json} and hands it to the shader manager.
    * A shader that is already known is replaced instead of re-registered, which is what happens on
    * every resource reload after the first one.
    */
   private void loadShader(class_5912 resourceManager, String name, class_293 format) {
      try {
         ShaderProgram program = new ShaderProgram(resourceManager, name, format);
         if (this.shaderManager.getShader(name).isPresent()) {
            this.shaderManager.reloadShader(name, program);
         } else {
            this.shaderManager.registerShader(name, program);
         }
      } catch (Throwable shaderFailure) {
         shaderFailure.printStackTrace();
      }
   }

   /**
    * Anti-tamper leftover. The original body belonged to the Metafaze protection layer (it was
    * annotated {@code @Compile(referenceCipherStrength = 20)} and talked to the license server -
    * the {@code java.net.http} constants it used are still in this class' constant pool). The
    * protection is stripped, the only caller is the shutdown hook installed by {@link #init}, and
    * nothing reads a result from it, so an empty body is the correct behaviour here.
    */
   public void wtfAreYouDoing() {
   }

   @Generated
   public LanguageService getLanguageService() {
      return this.languageService;
   }

   @Generated
   public InventoryService getInventoryService() {
      return this.inventoryService;
   }

   @Generated
   public EventBus getEventBus() {
      return this.eventBus;
   }

   @Generated
   public GPSListener getGps() {
      return this.gps;
   }

   @Generated
   public InventoryFilterListener getFilter() {
      return this.filter;
   }

   @Generated
   public DragService getDragService() {
      return this.dragService;
   }

   @Generated
   public FeatureManager getFeatureManager() {
      return this.featureManager;
   }

   @Generated
   public ShaderManager getShaderManager() {
      return this.shaderManager;
   }

   @Generated
   public StyleManager getStyleManager() {
      return this.styleManager;
   }

   @Generated
   public Menu getMenu() {
      return this.menu;
   }

   @Generated
   public RenderSystem getRenderSystem() {
      return this.renderSystem;
   }

   @Generated
   public CommandService getCommandService() {
      return this.commandService;
   }

   @Generated
   public ProxyManager getProxyManager() {
      return this.proxyManager;
   }

   @Generated
   public NotificationManager getNotificationManager() {
      return this.notificationManager;
   }

   @Generated
   public MacroService getMacroService() {
      return this.macroService;
   }

   @Generated
   public boolean isSafe() {
      return this.safe;
   }

   @Generated
   public CloudConfig getConfig() {
      return this.config;
   }

   @Generated
   public UserInfo getUserInfo() {
      return this.userInfo;
   }

   @Generated
   public ComponentManagerImpl getComponentManager() {
      return this.componentManager;
   }

   @Generated
   public List<WheelEntryView> getWheelEntries() {
      return this.wheelEntries;
   }

   @Generated
   public void setConfig(CloudConfig config) {
      this.config = config;
   }
}
