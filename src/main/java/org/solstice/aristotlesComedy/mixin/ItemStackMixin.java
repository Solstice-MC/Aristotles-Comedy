package org.solstice.aristotlesComedy.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.component.ComponentHolder;
import net.minecraft.item.ItemStack;
import org.solstice.aristotlesComedy.access.AristotlesItemStack;
import org.solstice.aristotlesComedy.registry.AristotlesComponentTypes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ComponentHolder, AristotlesItemStack {

	@Override
	public boolean isFuel() {
		ItemStack self = (ItemStack)(Object)this;
		int fuelTime = self.getOrDefault(AristotlesComponentTypes.FUEL_TIME, 0);
		if (fuelTime > 0) return true;
		return AbstractFurnaceBlockEntity.canUseAsFuel(self);
	}

	@Override
	public int getFuelTime() {
		ItemStack self = (ItemStack)(Object)this;
		int fuelTime = self.getOrDefault(AristotlesComponentTypes.FUEL_TIME, 0);
		if (fuelTime > 0) return fuelTime;
		return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(self.getItem(), 0);
	}

}
