package org.solstice.aristotlesComedy.content.research.content;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;
import org.solstice.aristotlesComedy.client.content.screen.ResearchableRenderContext;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.euclidsElements.util.type.Vec2i;

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

	@Override
	@Environment(EnvType.CLIENT)
	public void render(ResearchableRenderContext renderContext) {
		RegistryEntry<Researchable> entry = renderContext.entry();
		Vec2i start = renderContext.startPos();
		Identifier texture = this.path.withPrefixedPath("textures/gui/researchable/").withSuffixedPath(".png");
		Vec2i size = entry.value().size();
		renderContext.drawContext().drawTexture(
			texture,
			start.x + this.definition.offset().x,
			start.y + this.definition.offset().y,
			0, 0,
			size.x, size.y,
			size.x, size.y
		);
	}

}
