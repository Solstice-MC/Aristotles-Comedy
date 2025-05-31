package org.solstice.aristotlesComedy.content.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Function;

public class SpecializedSlot extends Slot {

	private final Function<ItemStack, Boolean> function;

	public SpecializedSlot(Inventory inventory, int index, int x, int y, Function<ItemStack, Boolean> function) {
		super(inventory, index, x, y);
		this.function = function;
	}

	public boolean canInsert(ItemStack stack) {
		return this.function.apply(stack);
	}

}
