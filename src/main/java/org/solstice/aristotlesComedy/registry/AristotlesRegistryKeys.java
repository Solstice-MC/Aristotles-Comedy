package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.humor.Humor;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class AristotlesRegistryKeys {

    public static void init() {}

    public static final RegistryKey<Registry<Humor>> HUMOR = RegistryKey.ofRegistry(AristotlesComedy.of("humor"));

}
