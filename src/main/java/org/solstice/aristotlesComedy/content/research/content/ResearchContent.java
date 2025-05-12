package org.solstice.aristotlesComedy.content.research.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.aristotlesComedy.registry.AristotlesRegistries;
import org.solstice.aristotlesComedy.util.Vec2i;

public interface ResearchContent {

	Codec<ResearchContent> CODEC = AristotlesRegistries.PAGE_CONTENT_TYPE.getCodec()
		.dispatch("type", ResearchContent::getType, ResearchContent.Type::codec);

	Type getType();
	Definition getDefinition();

	void render(RegistryEntry<Researchable> entry, Screen screen, DrawContext context, Vec2i start, Vec2i mouse, float delta);

	record Type(MapCodec<? extends ResearchContent> codec) {}

	record Definition (String test) {

		public static final MapCodec<Definition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.STRING.optionalFieldOf("test", "test").forGetter(Definition::test)
		).apply(instance, Definition::new));

	}

}
