package org.solstice.aristotlesComedy.data.recipe;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class TransmutationRecipeJsonBuilder implements CraftingRecipeJsonBuilder {

	private final RecipeCategory category;
	private final Item output;
	private final Ingredient input;
	private final int count;
	private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
	private String group;
	private final CuttingRecipe.RecipeFactory<?> recipeFactory;

	public TransmutationRecipeJsonBuilder(RecipeCategory category, CuttingRecipe.RecipeFactory<?> recipeFactory, Ingredient input, ItemConvertible output, int count) {
		this.category = category;
		this.recipeFactory = recipeFactory;
		this.output = output.asItem();
		this.input = input;
		this.count = count;
	}

	@Override
	public CraftingRecipeJsonBuilder criterion(String name, AdvancementCriterion<?> criterion) {
		this.criteria.put(name, criterion);
		return this;
	}

	@Override
	public CraftingRecipeJsonBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	@Override
	public Item getOutputItem() {
		return this.output;
	}

	@Override
	public void offerTo(RecipeExporter exporter, Identifier recipeId) {
		this.validate(recipeId);
		Advancement.Builder builder = exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
		Objects.requireNonNull(builder);
		this.criteria.forEach(builder::criterion);
		CuttingRecipe cuttingRecipe = this.recipeFactory.create((String)Objects.requireNonNullElse(this.group, ""), this.input, new ItemStack(this.output, this.count));
		exporter.accept(recipeId, cuttingRecipe, builder.build(recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")));
	}

	private void validate(Identifier recipeId) {
		if (this.criteria.isEmpty()) {
			throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(recipeId));
		}
	}

}
