package org.solstice.aristotlesComedy.content.research.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.solstice.aristotlesComedy.client.content.screen.ResearchableRenderContext;
import org.solstice.aristotlesComedy.registry.AristotlesRegistries;
import org.solstice.euclidsElements.util.type.Vec2i;

public interface ResearchContent {

	Codec<ResearchContent> CODEC = AristotlesRegistries.PAGE_CONTENT_TYPE.getCodec()
		.dispatch("type", ResearchContent::getType, ResearchContent.Type::codec);

	Type getType();
	Definition getDefinition();

	@Environment(EnvType.CLIENT)
	void render(ResearchableRenderContext renderContext);

	record Type(MapCodec<? extends ResearchContent> codec) {}

	record Definition(Vec2i offset) {

		public static final MapCodec<Definition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Vec2i.CODEC.optionalFieldOf("offset", Vec2i.ZERO).forGetter(Definition::offset)
		).apply(instance, Definition::new));

	}

}
