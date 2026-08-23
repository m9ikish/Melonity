package ru.melonity.service.friend.impl;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;
import ru.melonity.service.friend.FriendService;

@Environment(EnvType.CLIENT)
public class FriendServiceImpl implements FriendService {
   private final Set<String> friends = Sets.newLinkedHashSet();

   @Override
   public void add(@NotNull String name) {
      this.friends.add(name);
   }

   @Override
   public void remove(@NotNull String name) {
      this.friends.remove(name);
   }

   @Override
   public boolean hasFriend(@NotNull String name) {
      return this.friends.contains(name);
   }

   @Override
   public void clear() {
      this.friends.clear();
   }

   @Override
   public Collection<String> friends() {
      return Collections.unmodifiableSet(this.friends);
   }
}
