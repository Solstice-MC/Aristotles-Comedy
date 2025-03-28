package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

public class ModBiomes {

	public static void init() {
//		NetherBiomes.addNetherBiome(
//			ASHFIELDS,
//			RegistryKey.of(RegistryKeys.BIOME, AristotlesComedy.of("ashfields")),
//			MultiNoiseUtil.createNoiseHypercube(-1.0F, -0.5F, 0.0F, 0.5F, 0.0F, 0.5F, 0.0F)
//		);
	}

	public static final RegistryKey<Biome> ASHFIELDS = of("ashfields");

	public static RegistryKey<Biome> of(String name) {
		return RegistryKey.of(RegistryKeys.BIOME, AristotlesComedy.of(name));
	}

}
