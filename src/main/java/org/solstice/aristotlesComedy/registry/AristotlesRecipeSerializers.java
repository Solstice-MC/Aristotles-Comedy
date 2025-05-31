package org.solstice.aristotlesComedy.registry;

import com.mojang.serialization.MapCodec;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.recipe.*;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.solstice.euclidsElements.util.recipe.CodecRecipeSerializer;

public class AristotlesRecipeSerializers {

	public static void init() {}

	public static final RecipeSerializer<TransmutationRecipe> TRANSMUTATION = register("transmutation", TransmutationRecipe.CODEC);
	public static final RecipeSerializer<MetallurgyRecipe> METALLURGY = register("metallurgy", MetallurgyRecipe.CODEC);

	private static <T extends Recipe<?>> RecipeSerializer<T> register(String name, MapCodec<T> codec) {
		return register(name, new CodecRecipeSerializer<>(codec));
	}

	private static <T extends Recipe<?>> RecipeSerializer<T> register(String name, RecipeSerializer<T> serializer) {
		return Registry.register(Registries.RECIPE_SERIALIZER, AristotlesComedy.of(name), serializer);
	}

}
