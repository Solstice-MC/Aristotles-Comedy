package org.solstice.aristotlesComedy.content.research.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.solstice.aristotlesComedy.registry.AristotlesRegistries;

public interface ResearchContent {

	Codec<ResearchContent> CODEC = AristotlesRegistries.PAGE_CONTENT_TYPE.getCodec()
		.dispatch("type", ResearchContent::getType, ResearchContent.Type::codec);

	Type getType();
	Definition getDefinition();

	record Type(MapCodec<? extends ResearchContent> codec) {}

	record Definition (String test) {

		public static final MapCodec<Definition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.STRING.optionalFieldOf("test", "test").forGetter(Definition::test)
		).apply(instance, Definition::new));

	}

}
