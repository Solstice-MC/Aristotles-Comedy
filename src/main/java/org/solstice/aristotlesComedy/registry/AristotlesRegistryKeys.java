package org.solstice.aristotlesComedy.registry;

import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.humor.Humor;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.aristotlesComedy.content.research.content.ResearchContent;

public class AristotlesRegistryKeys {

    public static void init() {}

    public static final RegistryKey<Registry<Humor>> HUMOR = RegistryKey.ofRegistry(AristotlesComedy.of("humor"));
	public static final RegistryKey<Registry<Researchable>> RESEARCHABLE = RegistryKey.ofRegistry(AristotlesComedy.of("researchable"));
	public static final RegistryKey<Registry<ResearchContent.Type>> PAGE_CONTENT_TYPE = RegistryKey.ofRegistry(AristotlesComedy.of("page_content_type"));

}
