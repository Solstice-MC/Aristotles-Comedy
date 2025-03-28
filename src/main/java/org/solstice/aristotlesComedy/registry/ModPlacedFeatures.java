package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
//import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
//import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModPlacedFeatures {

//	public static void init() {
//		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, ORE_LEAD_NETHER_UPPER);
//		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, ORE_LEAD_NETHER_LOWER);
//	}

	public static final RegistryKey<PlacedFeature> ORE_LEAD_NETHER_UPPER = of("ore_lead_nether_upper");
	public static final RegistryKey<PlacedFeature> ORE_LEAD_NETHER_LOWER = of("ore_lead_nether_lower");


	public static RegistryKey<PlacedFeature> of(String name) {
		return RegistryKey.of(RegistryKeys.PLACED_FEATURE, AristotlesComedy.of(name));
	}

}
