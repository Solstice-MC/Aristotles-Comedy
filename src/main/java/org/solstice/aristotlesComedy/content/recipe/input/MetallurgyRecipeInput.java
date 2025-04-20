package org.solstice.aristotlesComedy.content.recipe.input;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record MetallurgyRecipeInput (
	ItemStack base,
	ItemStack addition
) implements RecipeInput {

	public ItemStack getStackInSlot(int slot) {
		return switch (slot) {
			case (0) -> base;
			case (1) -> addition;
			default -> throw new IllegalArgumentException("No item for index " + slot);
		};
	}

	@Override
	public int getSize() {
		return 2;
	}

}
