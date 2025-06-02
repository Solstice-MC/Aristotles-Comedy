package org.solstice.aristotlesComedy.registry.research;

import net.minecraft.registry.Registry;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.research.content.ImageContent;
import org.solstice.aristotlesComedy.content.research.content.ResearchContent;
import org.solstice.aristotlesComedy.content.research.content.TextContent;
import org.solstice.aristotlesComedy.content.research.effect.FadeEffect;
import org.solstice.aristotlesComedy.content.research.effect.ResearchEffect;
import org.solstice.aristotlesComedy.content.research.effect.TranslateEffect;
import org.solstice.aristotlesComedy.registry.AristotlesRegistries;

public class AristotlesResearchEffectTypes {

	public static void init() {}

	public static final ResearchEffect.Type FADE = register("fade", FadeEffect.TYPE);
	public static final ResearchEffect.Type TRANSLATE = register("translate", TranslateEffect.TYPE);

	private static ResearchEffect.Type register(String name, ResearchEffect.Type type) {
		return Registry.register(AristotlesRegistries.PAGE_EFFECT_TYPE, AristotlesComedy.of(name), type);
	}

}
