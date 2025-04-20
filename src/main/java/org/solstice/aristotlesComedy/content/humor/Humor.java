package org.solstice.aristotlesComedy.content.humor;

import com.mojang.serialization.Codec;
import org.solstice.aristotlesComedy.registry.AristotlesRegistries;
import net.minecraft.registry.entry.RegistryEntry;

public class Humor {

	public static final Codec<RegistryEntry<Humor>> ENTRY_CODEC = AristotlesRegistries.HUMOR.getEntryCodec();

	public static int getValue(RegistryEntry<Humor> entry) {
		return 0;
	}

}
