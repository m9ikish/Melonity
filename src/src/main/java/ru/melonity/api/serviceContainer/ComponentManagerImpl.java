package ru.melonity.api.serviceContainer;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import ru.melonity.api.components.ComponentManager;
import ru.melonity.api.components.ComponentManagerEx;
import ru.melonity.api.components.ServiceDescriptor;
import ru.melonity.api.instanceContainer.InstanceContainer;
import ru.melonity.api.instanceContainer.InstanceContainerImpl;
import ru.melonity.api.instanceContainer.lazy.LazyInstanceHolder;

@Environment(EnvType.CLIENT)
public class ComponentManagerImpl implements ComponentManager, ComponentManagerEx {
   private final InstanceContainer container = new InstanceContainerImpl("Services");
   private final Map<ServiceDescriptor.LoadMode, List<ComponentManagerImpl.PendingService>> pendingServices = new EnumMap<>(ServiceDescriptor.LoadMode.class);

   public ComponentManagerImpl() {
      for (ServiceDescriptor.LoadMode local4_0 : ServiceDescriptor.LoadMode.values()) {
         this.pendingServices.put(local4_0, Lists.newArrayList());
      }
   }

   @Override
   public <T> T service(@NotNull Class<T> serviceClass) {
      return this.container.instance(serviceClass);
   }

   @Override
   public void registerService(Class<?> serviceInterface, Class<?> implementation, boolean override, ServiceDescriptor.LoadMode loadMode) {
      ServiceDescriptor local5_0 = new ServiceDescriptor(serviceInterface.getName(), implementation.getName(), override, loadMode);
      ServiceInstanceInitializer local6_1 = new ServiceInstanceInitializer(implementation, local5_0);
      ComponentManagerImpl.PendingService local7_2 = new ComponentManagerImpl.PendingService(serviceInterface, local6_1);
      this.pendingServices.get(loadMode).add(local7_2);
   }

   public void loadInOrder(ServiceDescriptor.LoadMode loadMode) {
      List<ComponentManagerImpl.PendingService> local2_2 = this.pendingServices.get(loadMode);

      for (ComponentManagerImpl.PendingService local4_1 : local2_2) {
         LazyInstanceHolder local5_0 = new LazyInstanceHolder(local4_1.initializer);
         this.container.registerHolder(local4_1.serviceInterface, local5_0);
      }

      local2_2.clear();
   }

   @Environment(EnvType.CLIENT)
   private static record PendingService(Class<?> serviceInterface, ServiceInstanceInitializer initializer) {
   }
}
