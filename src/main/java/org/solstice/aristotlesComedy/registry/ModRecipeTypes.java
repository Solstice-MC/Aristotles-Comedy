package org.solstice.aristotlesComedy.registry;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import org.solstice.aristotlesComedy.util.IdentifiableRecipeType;

public class ModRecipeTypes {

	public static final DeferredRegister<RecipeType<?>> REGISTRY = DeferredRegister
		.create(Registries.RECIPE_TYPE, AristotlesComedy.MOD_ID);

	public static final DeferredHolder<RecipeType<?>, IdentifiableRecipeType<Recipe<?>>> TRANSMUTATION = register("transmutation");

	static DeferredHolder<RecipeType<?>, IdentifiableRecipeType<Recipe<?>>> register(String name) {
		return REGISTRY.register(name, IdentifiableRecipeType::new);
	}

}
