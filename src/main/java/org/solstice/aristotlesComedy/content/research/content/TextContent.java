package org.solstice.aristotlesComedy.content.research.content;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import org.solstice.aristotlesComedy.client.content.screen.ResearchableRenderContext;
import org.solstice.euclidsElements.util.type.Vec2i;

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
	@Environment(EnvType.CLIENT)
	public void render(ResearchableRenderContext renderContext) {
		Vec2i start = renderContext.startPos();
		MatrixStack matrices = renderContext.drawContext().getMatrices();
		matrices.push();
		matrices.translate((float)start.x / 2, (float)start.y / 2, 0);
		matrices.scale(0.5F, 0.5F, 1);
		renderContext.screen().textRenderer.draw(
			this.text,
			start.x + this.definition.offset().x,
			start.y + this.definition.offset().y,
			0xFFFFFF,
			true,
			matrices.peek().getPositionMatrix(),
			renderContext.drawContext().getVertexConsumers(),
			TextRenderer.TextLayerType.NORMAL,
			0,
			15728880
		);
		matrices.pop();
	}

}
