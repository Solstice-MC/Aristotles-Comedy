package org.solstice.aristotlesComedy.registry;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import org.solstice.aristotlesComedy.content.humor.Humor;
import net.minecraft.registry.*;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.aristotlesComedy.content.research.content.ResearchContent;
import org.solstice.aristotlesComedy.content.research.effect.ResearchEffect;

public class AristotlesRegistries {

    public static void init() {
		DynamicRegistries.registerSynced(AristotlesRegistryKeys.RESEARCHABLE, Researchable.CODEC, DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY);
	}

    public static final Registry<Humor> HUMOR = new SimpleRegistry<>(AristotlesRegistryKeys.HUMOR, Lifecycle.stable());
	public static final Registry<Researchable> RESEARCHABLE = new SimpleRegistry<>(AristotlesRegistryKeys.RESEARCHABLE, Lifecycle.stable());
	public static final Registry<ResearchContent.Type> PAGE_CONTENT_TYPE = new SimpleRegistry<>(AristotlesRegistryKeys.PAGE_CONTENT_TYPE, Lifecycle.stable());
	public static final Registry<ResearchEffect.Type> PAGE_EFFECT_TYPE = new SimpleRegistry<>(AristotlesRegistryKeys.PAGE_EFFECT_TYPE, Lifecycle.stable());

}
