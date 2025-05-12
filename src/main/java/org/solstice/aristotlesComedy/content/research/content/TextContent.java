package org.solstice.aristotlesComedy.content.research.content;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;

public record TextContent (
	Text text,
	Definition definition
) implements ResearchContent {

	public static final MapCodec<TextContent> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		TextCodecs.CODEC.fieldOf("text").forGetter(TextContent::text),
		Definition.CODEC.forGetter(TextContent::definition)
	).apply(instance, TextContent::new));

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
