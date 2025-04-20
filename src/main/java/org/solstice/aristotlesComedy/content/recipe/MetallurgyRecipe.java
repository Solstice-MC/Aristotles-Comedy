package org.solstice.aristotlesComedy.content.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.solstice.aristotlesComedy.content.recipe.input.MetallurgyRecipeInput;

public record MetallurgyRecipe (
	Ingredient base,
	Ingredient addition,
	ItemStack result,
	float experience,
	int cookingTime
) implements Recipe<MetallurgyRecipeInput> {

	public static final MapCodec<MetallurgyRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("base").forGetter(MetallurgyRecipe::base),
		Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("addition").forGetter(MetallurgyRecipe::addition),
		ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(MetallurgyRecipe::result),
		Codec.FLOAT.optionalFieldOf("experience", 0F).forGetter(MetallurgyRecipe::experience),
		Codec.INT.optionalFieldOf("cooking_ime", 200).forGetter(MetallurgyRecipe::cookingTime)
	).apply(instance, MetallurgyRecipe::new));

	@Override
	public boolean matches(MetallurgyRecipeInput input, World world) {
		return false;
	}

	@Override
	public ItemStack craft(MetallurgyRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
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
