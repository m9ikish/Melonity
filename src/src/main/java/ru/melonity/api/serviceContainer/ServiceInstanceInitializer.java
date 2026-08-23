package ru.melonity.api.serviceContainer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;
import ru.melonity.api.components.ServiceDescriptor;
import ru.melonity.api.instanceContainer.InstanceInitializer;

@Environment(EnvType.CLIENT)
public class ServiceInstanceInitializer implements InstanceInitializer {
   private final Class<?> instanceClass;
   private final ServiceDescriptor descriptor;

   public ServiceInstanceInitializer(Class<?> instanceClass, ServiceDescriptor descriptor) {
      this.instanceClass = instanceClass;
      this.descriptor = descriptor;
   }

   public ServiceInstanceInitializer(Class<?> instanceClass) {
      this.instanceClass = instanceClass;
      this.descriptor = new ServiceDescriptor(instanceClass.getName(), instanceClass.getName(), false, ServiceDescriptor.LoadMode.FIRST);
   }

   @Override
   public String instanceClassName() {
      return this.descriptor.serviceImplementation;
   }

   @NotNull
   @Override
   public Class<?> loadInstanceClass(Class<?> keyClass) {
      return this.instanceClass;
   }

   @Override
   public Object createInstance(Class<?> instanceClass) throws Exception {
      return instanceClass.getConstructor().newInstance();
   }
}
