package org.solstice.aristotlesComedy.content.recipe;

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
import org.solstice.aristotlesComedy.registry.ModRecipeTypes;
import org.solstice.aristotlesComedy.util.CodecRecipeSerializer;

public record TransmutationRecipe (
	Ingredient ingredient,
	ItemStack result
) implements Recipe<SingleStackRecipeInput> {

	public static final MapCodec<TransmutationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(TransmutationRecipe::ingredient),
		ItemStack.CODEC.fieldOf("result").forGetter(TransmutationRecipe::result)
	).apply(instance, TransmutationRecipe::new));

	public static final RecipeSerializer<TransmutationRecipe> SERIALIZER = new CodecRecipeSerializer<>(CODEC);

	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.TRANSMUTATION.get();
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}


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

}
