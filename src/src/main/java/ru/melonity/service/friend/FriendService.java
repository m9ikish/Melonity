package ru.melonity.service.friend;

import java.util.Collection;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

@Environment(EnvType.CLIENT)
public interface FriendService {
   void add(@NotNull String var1);

   void remove(@NotNull String var1);

   boolean hasFriend(@NotNull String var1);

   void clear();

   @Unmodifiable
   Collection<String> friends();
}
