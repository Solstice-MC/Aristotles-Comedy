package org.solstice.aristotlesComedy.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ACAdvancementProvider extends FabricAdvancementProvider {

	public ACAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateAdvancement(RegistryWrapper.WrapperLookup lookup, Consumer<AdvancementEntry> consumer) {
//		this.generateRecipeAdvancements(lookup, consumer);
	}

	public void generateRecipeAdvancements(RegistryWrapper.WrapperLookup lookup, Consumer<AdvancementEntry> consumer) {
		lookup.getWrapperOrThrow(RegistryKeys.RECIPE).streamEntries().forEach(entry -> {
			Identifier id = entry.getKey().orElseThrow().getValue();
			Recipe<?> recipe = entry.value();
			Advancement.Builder builder = Advancement.Builder.create()
				.criterion("has_item",
					InventoryChangedCriterion.Conditions.items(this.findKeyItem(recipe.getIngredients()))
				)
				.criterion("has_recipe",
					RecipeUnlockedCriterion.create(id)
				);
			consumer.accept(builder.build(id));
		});
	}

	public Item findKeyItem(List<Ingredient> ingredients) {
		HashMap<ItemStack, Integer> values = new HashMap<>();
		ingredients.forEach(ingredient -> {
			ItemStack stack = ingredient.getMatchingStacks()[0];
			if (!values.containsKey(stack)) values.put(stack, 0);
			values.merge(stack, stack.getRarity().ordinal() + 1, Integer::sum);
		});
		return values.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElseThrow().getItem();
	}

}
