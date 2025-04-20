package org.solstice.aristotlesComedy.registry;

import com.mojang.serialization.Lifecycle;
import org.solstice.aristotlesComedy.content.humor.Humor;
import net.minecraft.registry.*;

public class AristotlesRegistries {

    public static void init() {
//		DynamicRegistries.register(HUMOR, Humor.CODEC);
	}

    public static final Registry<Humor> HUMOR = new SimpleRegistry<>(AristotlesRegistryKeys.HUMOR, Lifecycle.stable());

}
