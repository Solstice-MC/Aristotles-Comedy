package org.solstice.aristotlesComedy.content.research.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.math.MathHelper;
import org.solstice.aristotlesComedy.content.research.ResearchableRenderContext;
import org.solstice.euclidsElements.util.type.Vec2i;

import java.awt.*;

public record TranslateEffect (
	Text text,
	int fadeInDelay,
	Definition definition
) implements ResearchEffect {

	public static final int TICK_SPEED = 2;

	public static final MapCodec<TranslateEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		TextCodecs.CODEC.fieldOf("text").forGetter(TranslateEffect::text),
		Codec.INT.optionalFieldOf("fade_in_delay", 50).forGetter(TranslateEffect::fadeInDelay),
		Definition.CODEC.forGetter(TranslateEffect::definition)
	).apply(instance, TranslateEffect::new));

	public static final Type TYPE = new Type(CODEC);

	@Override
	public Type getType() {
		return TYPE;
	}

	@Override
	public Definition getDefinition() {
		return this.definition;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void apply(Screen screen, DrawContext drawContext, ResearchableRenderContext renderContext, Vec2i startPos) {
		MatrixStack matrices = drawContext.getMatrices();
		matrices.push();
		matrices.translate((float) startPos.x / 2, (float) startPos.y / 2, 0);
		matrices.scale(0.5F, 0.5F, 1);

		int tick = renderContext.renderTick * TICK_SPEED;
		int delay = this.fadeInDelay * TICK_SPEED;

		float alphaDelta = Math.clamp(tick - delay, 4, 255) / 255F;
		int alpha = MathHelper.lerp(alphaDelta, 4, 255);
		Color color = new Color(255, 255, 255, alpha);
		screen.textRenderer.draw(
			this.text,
			startPos.x,
			startPos.y,
			color.getRGB(),
			true,
			matrices.peek().getPositionMatrix(),
			drawContext.getVertexConsumers(),
			TextRenderer.TextLayerType.NORMAL,
			0,
			15728880
		);
		matrices.pop();
	}

}
