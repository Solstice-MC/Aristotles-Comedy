package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class AristotlesSoundEvents {

	public static void init() {}

	public static final SoundEvent ITEM_PLATINUM_GLOVE_THROW = register("entity.egg.throw");

	public static final SoundEvent BLOCK_SABIKA_FIRE_CRACKLE = register("block.sabika.fire_crackle");
	public static final SoundEvent BLOCK_SACRED_HEART_HEARTBEAT = register("block.sacred_heart.heartbeat");

	public static final SoundEvent ENTITY_ASH_BUNNY_POOF = register("entity.ash_bunny.poof");

	public static final SoundEvent MUSIC_DISC_SCARICO_IN_VINILE = register("music_disc.scarico_in_vinile");

	public static SoundEvent register(String name) {
		Identifier id = AristotlesComedy.of(name);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

	public static RegistryEntry<SoundEvent> registerReference(String name) {
		Identifier id = AristotlesComedy.of(name);
		return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

}
