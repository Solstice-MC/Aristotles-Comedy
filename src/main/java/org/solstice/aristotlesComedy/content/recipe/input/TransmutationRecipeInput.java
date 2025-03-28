package org.solstice.aristotlesComedy.content.recipe.input;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.content.humor.Humor;

import java.util.Map;

public record TransmutationRecipeInput (
        ItemStack ingredient,
		Map<RegistryEntry<Humor>, Integer> humorValues
) implements RecipeInput {

    public static Codec<TransmutationRecipeInput> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ItemStack.CODEC.fieldOf("ingredient").forGetter(TransmutationRecipeInput::ingredient),
		Codec.unboundedMap(Humor.ENTRY_CODEC, Codec.INT).fieldOf("humor_values").forGetter(TransmutationRecipeInput::humorValues)
    ).apply(instance, TransmutationRecipeInput::new));

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.ingredient;
    }

    @Override
    public int getSize() {
        return 0;
    }

}
