package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.recipe.*;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.solstice.euclidsElements.util.IdentifiableRecipeType;

public class AristotlesRecipeTypes {

	public static void init() {}

	public static final RecipeType<TransmutationRecipe> TRANSMUTATION = register("transmutation");
	public static final RecipeType<MetallurgyRecipe> METALLURGY = register("metallurgy");

	static <T extends Recipe<?>> RecipeType<T> register(String name) {
		Identifier id = AristotlesComedy.of(name);
		return Registry.register(Registries.RECIPE_TYPE, id, new IdentifiableRecipeType<>(id));
	}

}
