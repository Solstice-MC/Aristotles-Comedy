package org.solstice.aristotlesComedy.registry;

import com.mojang.serialization.Codec;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.euclidsElements.mapTag.api.MapTagKey;

public class AristotlesMapTags {

	public static void init() {}

//	public static final MapTagKey<Block, Text> SLEUTHING_MESSAGE = of("sleuthing_message",
//		RegistryKeys.BLOCK, TextCodecs.CODEC
//	);

	protected static <T, R> MapTagKey<T, R> of(String name, RegistryKey<Registry<T>> key, Codec<R> codec) {
		return MapTagKey.of(key, codec, AristotlesComedy.of(name));
	}

}
