package ru.melonity.api.instanceContainer;

import com.google.common.collect.Maps;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Nullable;
import ru.melonity.api.instanceContainer.lazy.LazyInstanceHolder;
import ru.melonity.api.serviceContainer.ServiceInstanceInitializer;

@Environment(EnvType.CLIENT)
public class InstanceContainerImpl implements InstanceContainer {
   private final String containerName;
   private final Map<Class<?>, InstanceHolder> byClassHolders;

   public InstanceContainerImpl(String containerName) {
      this.containerName = containerName;
      this.byClassHolders = Maps.newLinkedHashMap();
   }

   @Override
   public <T> T instance(Class<T> keyClass) {
      InstanceHolder local2_1 = this.instanceHolder(keyClass);
      if (local2_1 == null) {
         throw new InstanceNotRegisteredException(keyClass.getName());
      } else {
         try {
            return local2_1.instance(keyClass);
         } catch (Throwable var4) {
            return null;
         }
      }
   }

   @Override
   public void registerHolder(Class<?> keyClass, InstanceHolder holder) {
      this.byClassHolders.put(keyClass, holder);
   }

   @Nullable
   private InstanceHolder instanceHolder(Class<?> keyClass) {
      if (this.byClassHolders.containsKey(keyClass)) {
         return this.byClassHolders.get(keyClass);
      } else {
         InstanceInitializer local2_0 = new ServiceInstanceInitializer(keyClass);
         LazyInstanceHolder local3_1 = new LazyInstanceHolder(local2_0);
         this.byClassHolders.put(keyClass, local3_1);
         return local3_1;
      }
   }

   @Override
   public String toString() {
      return "Container" + this.containerName;
   }
}
