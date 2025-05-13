package org.solstice.aristotlesComedy.registry;

import net.minecraft.registry.Registry;
import org.solstice.aristotlesComedy.AristotlesComedy;
import org.solstice.aristotlesComedy.content.research.content.*;

public class AristotlesResearchContentTypes {

	public static void init() {}

	public static final ResearchContent.Type TEXT = register("text", TextContent.TYPE);
	public static final ResearchContent.Type TRANSLATED_TEXT = register("translated_text", TranslatedTextContent.TYPE);
	public static final ResearchContent.Type IMAGE = register("image", ImageContent.TYPE);

	private static ResearchContent.Type register(String name, ResearchContent.Type type) {
		return Registry.register(AristotlesRegistries.PAGE_CONTENT_TYPE, AristotlesComedy.of(name), type);
	}

}
