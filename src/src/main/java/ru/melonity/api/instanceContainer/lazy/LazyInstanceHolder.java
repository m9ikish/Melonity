package ru.melonity.api.instanceContainer.lazy;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;
import ru.melonity.api.instanceContainer.InstanceHolder;
import ru.melonity.api.instanceContainer.InstanceInitializer;

@Environment(EnvType.CLIENT)
public class LazyInstanceHolder implements InstanceHolder {
   private Object state;

   public LazyInstanceHolder(@NotNull InstanceInitializer initializer) {
      this.state = new LazyInstanceHolder.Initial(initializer);
   }

   @Override
   public String instanceClassName() throws Throwable {
      Object state = this.state;
      return switch (state) {
         case LazyInstanceHolder.Initial initial -> initial.initializer.instanceClassName();
         case LazyInstanceHolder.CannotLoadClass cannotLoadClass -> throw cannotLoadClass.classLoadingError;
         default -> this.state.getClass().getName();
      };
   }

   @Override
   public Class<?> instanceClass() throws Throwable {
      Object state = this.state;
      return switch (state) {
         case LazyInstanceHolder.Initial initial -> initial.initializer.loadInstanceClass(null);
         case LazyInstanceHolder.CannotLoadClass cannotLoadClass -> throw cannotLoadClass.classLoadingError;
         default -> this.state.getClass();
      };
   }

   @Override
   public <T> T instance(Class<?> keyClass) throws Throwable {
      Object state = this.state;
      switch (state) {
         case LazyInstanceHolder.Initial initial -> {
            InstanceInitializer initializer = initial.initializer;
            Class<?> instanceClass = null;

            try {
               instanceClass = initializer.loadInstanceClass(keyClass);
            } catch (Throwable classLoadingError) {
               this.state = new LazyInstanceHolder.CannotLoadClass(initializer.instanceClassName(), classLoadingError);
               throw classLoadingError;
            }

            this.state = initializer.createInstance(instanceClass);
            return (T)this.state;
         }
         case LazyInstanceHolder.CannotLoadClass cannotLoadClass -> throw cannotLoadClass.classLoadingError;
         default -> {
            return (T)this.state;
         }
      }
   }

   @Environment(EnvType.CLIENT)
   private static record CannotLoadClass(String instanceClassName, Throwable classLoadingError) {
   }

   @Environment(EnvType.CLIENT)
   private static record Initial(InstanceInitializer initializer) {
   }
}
