package org.solstice.aristotlesComedy.content.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.solstice.aristotlesComedy.content.recipe.input.MetallurgyRecipeInput;
import org.solstice.aristotlesComedy.registry.AristotlesRecipeSerializers;
import org.solstice.aristotlesComedy.registry.AristotlesRecipeTypes;

import java.util.HashSet;
import java.util.Set;

public record MetallurgyRecipe (
	Ingredient ingredient,
	QuantitizedIngredient addition,
	ItemStack result,
	float experience,
	int cookingTime
) implements Recipe<MetallurgyRecipeInput> {

	@Override
	public RecipeType<?> getType() {
		return AristotlesRecipeTypes.METALLURGY;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return AristotlesRecipeSerializers.METALLURGY;
	}

	public static final MapCodec<MetallurgyRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(MetallurgyRecipe::ingredient),
		QuantitizedIngredient.CODEC.fieldOf("addition").forGetter(MetallurgyRecipe::addition),
		ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(MetallurgyRecipe::result),
		Codec.FLOAT.optionalFieldOf("experience", 0F).forGetter(MetallurgyRecipe::experience),
		Codec.INT.optionalFieldOf("cooking_ime", 200).forGetter(MetallurgyRecipe::cookingTime)
	).apply(instance, MetallurgyRecipe::new));

	public static final Set<Ingredient> VALID_INGREDIENTS = new HashSet<>();
	public static final Set<Ingredient> VALID_ADDITIONS = new HashSet<>();

	public static boolean isValidIngredient(ItemStack stack) {
		for (Ingredient ingredient : VALID_INGREDIENTS) {
			if (ingredient.test(stack)) return true;
		}
		return false;
	}

	public static boolean isValidAddition(ItemStack stack) {
		for (Ingredient ingredient : VALID_ADDITIONS) {
			if (ingredient.test(stack)) return true;
		}
		return false;
	}

	public static void populateIngredients(MinecraftServer server) {
		VALID_INGREDIENTS.clear();
		VALID_ADDITIONS.clear();
		server.getRecipeManager().listAllOfType(AristotlesRecipeTypes.METALLURGY).forEach(entry -> {
			VALID_INGREDIENTS.add(entry.value().ingredient());
			VALID_ADDITIONS.add(entry.value().addition().ingredient());
		});
	}

	@Override
	public boolean matches(MetallurgyRecipeInput input, World world) {
		return this.ingredient.test(input.ingredient()) && this.addition.test(input.addition());
	}

	@Override
	public ItemStack craft(MetallurgyRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
		return this.result.copy();
	}

	@Override
	public boolean fits(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
		return this.result;
	}

	public record QuantitizedIngredient(Ingredient ingredient, int amount) {

		public static final Codec<QuantitizedIngredient> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(QuantitizedIngredient::ingredient),
			Codec.INT.optionalFieldOf("amount", 1).forGetter(QuantitizedIngredient::amount)
		).apply(instance, QuantitizedIngredient::new));

		public boolean test(@Nullable ItemStack stack) {
			return this.ingredient.test(stack) && stack.getCount() >= this.amount;
		}

	}

}
