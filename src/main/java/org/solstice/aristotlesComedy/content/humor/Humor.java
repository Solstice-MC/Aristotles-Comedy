package org.solstice.aristotlesComedy.content.humor;

import com.mojang.serialization.Codec;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.registry.ModRegistries;

public class Humor {

	public static final Codec<RegistryEntry<Humor>> ENTRY_CODEC = ModRegistries.HUMOR.getEntryCodec();

	public static int getValue(RegistryEntry<Humor> entry) {
		return 0;
	}

}
