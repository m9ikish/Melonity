package ru.melonity.util.event.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Retention(RetentionPolicy.RUNTIME)
@Environment(EnvType.CLIENT)
public @interface EventPriority {
   int priority() default 0;
}
