package org.solstice.aristotlesComedy.content.research.content;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;
import org.solstice.aristotlesComedy.content.research.ResearchableRenderContext;
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
	public void render(Screen screen, DrawContext drawContext, ResearchableRenderContext renderContext, Vec2i startPos) {
		Identifier texture = this.path.withPrefixedPath("textures/gui/researchable/").withSuffixedPath(".png");
		MatrixStack matrices = drawContext.getMatrices();

		matrices.push();
		ResearchContent.super.render(screen, drawContext, renderContext, startPos);
		drawContext.drawTexture(
			texture,
			startPos.x,
			startPos.y,
			0, 0,
			this.size.x, this.size.y,
			this.size.x, this.size.y
		);
		matrices.pop();
	}

}
