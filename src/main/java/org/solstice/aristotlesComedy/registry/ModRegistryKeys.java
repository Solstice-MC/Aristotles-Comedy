package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.solstice.aristotlesComedy.content.humor.Humor;

public class ModRegistryKeys {

    public static final RegistryKey<Registry<Humor>> HUMOR = RegistryKey.ofRegistry(AristotlesComedy.of("humor"));

}
