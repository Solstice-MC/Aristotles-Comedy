package org.solstice.aristotlesComedy.content.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public record DistillingRecipe (

) implements Recipe<SingleStackRecipeInput> {

	@Override
	public boolean matches(SingleStackRecipeInput input, World world) {
		return false;
	}

	@Override
	public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
		return null;
	}

	@Override
	public boolean fits(int width, int height) {
		return false;
	}

	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
		return null;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return null;
	}

	@Override
	public RecipeType<?> getType() {
		return null;
	}

}
