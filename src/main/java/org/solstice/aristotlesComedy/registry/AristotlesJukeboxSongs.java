package org.solstice.aristotlesComedy.registry;

import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.solstice.aristotlesComedy.AristotlesComedy;

public class AristotlesJukeboxSongs {

	public static void init() {}

	public static final RegistryKey<JukeboxSong> SCARICO_IN_VINILE = of("scarico_in_vinile");

	private static RegistryKey<JukeboxSong> of(String name) {
		return RegistryKey.of(RegistryKeys.JUKEBOX_SONG, AristotlesComedy.of(name));
	}

}
