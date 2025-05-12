package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class AristotlesPlacedFeatures {

	public static void init() {
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, ORE_TIN_LARGE);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, ORE_TIN_SMALL);
		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, ORE_LEAD_BLACKSTONE_UPPER);
		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, ORE_LEAD_BLACKSTONE_LOWER);
	}

	public static final RegistryKey<PlacedFeature> ORE_TIN_LARGE = of("ore_tin_large");
	public static final RegistryKey<PlacedFeature> ORE_TIN_SMALL = of("ore_tin_small");
	public static final RegistryKey<PlacedFeature> ORE_LEAD_BLACKSTONE_UPPER = of("ore_lead_blackstone_upper");
	public static final RegistryKey<PlacedFeature> ORE_LEAD_BLACKSTONE_LOWER = of("ore_lead_blackstone_lower");

	public static RegistryKey<PlacedFeature> of(String name) {
		return RegistryKey.of(RegistryKeys.PLACED_FEATURE, AristotlesComedy.of(name));
	}

}
