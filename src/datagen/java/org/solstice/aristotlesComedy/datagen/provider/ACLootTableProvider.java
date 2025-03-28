package org.solstice.aristotlesComedy.datagen.provider;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.data.server.loottable.LootTableProvider;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.solstice.aristotlesComedy.datagen.AristotlesComedyDataGenerator;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ACLootTableProvider extends LootTableProvider {

	public ACLootTableProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> future) {
		super(output, Set.of(), List.of(), future);
	}

	@Override
	public List<LootTypeGenerator> getTables() {
		return ImmutableList.of(
			new LootTypeGenerator(Test::new, LootContextTypes.BLOCK)
		);
	}

	public static class Test extends BlockLootTableGenerator {

		public Test(RegistryWrapper.WrapperLookup lookup) {
			super(Set.of(), FeatureSet.empty(), lookup);
		}

		protected void generate() {
			this.registryLookup
				.getWrapperOrThrow(RegistryKeys.BLOCK)
				.streamEntries()
				.filter(AristotlesComedyDataGenerator::ownsRegistry)
				.map(RegistryEntry.Reference::value)
				.forEach(this::addDrop);
		}

	}

}
