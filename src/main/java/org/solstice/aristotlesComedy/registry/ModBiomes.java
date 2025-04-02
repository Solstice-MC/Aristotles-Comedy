package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.biome.Biome;

public class ModBiomes {

	public static final RegistryKey<Biome> ASHFIELDS = of("ashfields");

	public static RegistryKey<Biome> of(String name) {
		return RegistryKey.of(RegistryKeys.BIOME, AristotlesComedy.of(name));
	}

}
