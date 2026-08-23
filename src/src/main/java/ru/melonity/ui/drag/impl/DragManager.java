package ru.melonity.ui.drag.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.melonity.ui.drag.Drag;
import ru.melonity.ui.drag.DragService;

@Environment(EnvType.CLIENT)
public class DragManager implements DragService {
   private final List<Drag> drags = Lists.newLinkedList();

   @Override
   public List<Drag> getDrags() {
      return ImmutableList.copyOf(this.drags);
   }

   @Override
   public void registerDrag(Drag drag) {
      this.drags.add(drag);
   }

   @Override
   public <T extends Drag> T getDrag(Class<? extends Drag> clazz) {
      return this.drags.stream().filter(drag -> drag.getClass().equals(clazz)).map(drag -> (T)drag).findFirst().orElse(null);
   }

   @Override
   public Drag getDragById(int id) {
      for (Drag local3_0 : this.drags) {
         if (local3_0.getId() == id) {
            return local3_0;
         }
      }

      return null;
   }
}
