package ru.melonity.ui.drag;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface DragService {
   List<Drag> getDrags();

   void registerDrag(Drag var1);

   <T extends Drag> T getDrag(Class<? extends Drag> var1);

   Drag getDragById(int var1);
}
