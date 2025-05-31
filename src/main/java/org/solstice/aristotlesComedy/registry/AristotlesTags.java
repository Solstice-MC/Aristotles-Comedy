package org.solstice.aristotlesComedy.registry;

import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.euclidsElements.mapTag.api.MapTagKey;

public class AristotlesTags {

	public static void init() {}

	public static final TagKey<EntityType<?>> CARRIABLE = of("carriable",
		RegistryKeys.ENTITY_TYPE
	);

	protected static <T> TagKey<T> of(String name, RegistryKey<Registry<T>> key) {
		return TagKey.of(key, AristotlesComedy.of(name));
	}

	protected static <T, R> MapTagKey<T, R> ofMap(String name, RegistryKey<Registry<T>> key, Codec<R> codec) {
		return MapTagKey.of(key, codec, AristotlesComedy.of(name));
	}

}
