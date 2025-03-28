package org.solstice.aristotlesComedy.registry;

import net.neoforged.neoforge.registries.DeferredRegister;
import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import org.solstice.aristotlesComedy.content.recipe.TransmutationRecipe;

public class ModRecipeSerializers {

	public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister
		.create(Registries.RECIPE_SERIALIZER, AristotlesComedy.MOD_ID);

	public static final RecipeSerializer<TransmutationRecipe> TRANSMUTATION = register("transmutation", TransmutationRecipe.SERIALIZER);

	static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String name, S serializer) {
		REGISTRY.register(name, () -> serializer);
		return serializer;
	}

}
