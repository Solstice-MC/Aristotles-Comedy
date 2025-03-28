package org.solstice.aristotlesComedy.datagen.provider;

import org.solstice.aristotlesComedy.registry.ModBlocks;
import org.solstice.aristotlesComedy.registry.ModItems;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ACRecipeProvider extends RecipeProvider {

	public ACRecipeProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookup) {
		super(output, lookup);
	}

	@Override
	public void generate(RecipeExporter exporter) {
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ATHANOR)
			.input('b', ModItems.BRONZE_INGOT)
			.pattern("bbb")
			.pattern("b b")
			.pattern("bbb")
			.criterion(hasItem(ModItems.BRONZE_INGOT), conditionsFromItem(ModItems.BRONZE_INGOT))
			.offerTo(exporter);
	}

}
