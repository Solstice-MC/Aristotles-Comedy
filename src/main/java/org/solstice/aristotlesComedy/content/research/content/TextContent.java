package org.solstice.aristotlesComedy.content.research.content;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.aristotlesComedy.util.Vec2i;

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

	@Override
	public void render(RegistryEntry<Researchable> entry, Screen screen, DrawContext context, Vec2i start, Vec2i mouse, float delta) {
		Vec2i size = entry.value().size();
		context.drawText(
			screen.textRenderer,
			this.text,
			start.x,
			start.y,
			0xFFFFFF,
			true
		);
	}

}
