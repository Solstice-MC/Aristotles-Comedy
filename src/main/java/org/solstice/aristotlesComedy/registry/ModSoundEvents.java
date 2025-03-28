package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {

	public static void init() {}

	public static final SoundEvent ENTITY_ASH_BUNNY_POOF = register("entity.ash_bunny.poof");

	public static SoundEvent register(String name) {
		Identifier id = AristotlesComedy.of(name);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

	public static RegistryEntry<SoundEvent> registerReference(String name) {
		Identifier id = AristotlesComedy.of(name);
		return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

}
