package org.solstice.aristotlesComedy.content.research.content;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;
import org.solstice.aristotlesComedy.util.Vec2i;

public record ImageContent (
	Identifier path,
	Vec2i size,
	Text description,
	Definition definition
) implements ResearchContent {

	public static final MapCodec<ImageContent> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Identifier.CODEC.fieldOf("path").forGetter(ImageContent::path),
		Vec2i.CODEC.fieldOf("size").forGetter(ImageContent::size),
		TextCodecs.CODEC.fieldOf("description").forGetter(ImageContent::description),
		Definition.CODEC.forGetter(ImageContent::definition)
	).apply(instance, ImageContent::new));

	public static final Type TYPE = new Type(CODEC);

	@Override
	public Type getType() {
		return TYPE;
	}

	@Override
	public Definition getDefinition() {
		return this.definition;
	}

}
