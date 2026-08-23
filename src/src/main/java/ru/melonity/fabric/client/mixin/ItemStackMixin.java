package ru.melonity.fabric.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1799;
import org.spongepowered.asm.mixin.Mixin;
import ru.melonity.fabric.client.model.IItemStack;

@Environment(EnvType.CLIENT)
@Mixin({class_1799.class})
public class ItemStackMixin implements IItemStack {
   private int price = -1;
   private boolean autoSell = false;
   private boolean bestPrice = false;

   @Override
   public int price() {
      return this.price;
   }

   @Override
   public void setPrice(int price) {
      this.price = price;
   }

   @Override
   public boolean autosell() {
      return this.autoSell;
   }

   @Override
   public void setAutosell(boolean autosell) {
      this.autoSell = autosell;
   }

   @Override
   public boolean bestPrice() {
      return this.bestPrice;
   }

   @Override
   public void setBestPrice(boolean bestPrice) {
      this.bestPrice = bestPrice;
   }
}
