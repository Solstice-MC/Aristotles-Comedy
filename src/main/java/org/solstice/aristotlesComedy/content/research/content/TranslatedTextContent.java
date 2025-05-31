package org.solstice.aristotlesComedy.content.research.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.math.MathHelper;
import org.solstice.aristotlesComedy.client.content.screen.ResearchableRenderContext;
import org.solstice.euclidsElements.util.type.Vec2i;

import java.awt.*;

public record TranslatedTextContent (
	Text originalText,
	Text translatedText,
	Definition definition,
	int translationDelay
) implements ResearchContent {

	public static final int TICK_SPEED = 2;

	public static final MapCodec<TranslatedTextContent> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		TextCodecs.CODEC.fieldOf("original_text").forGetter(TranslatedTextContent::originalText),
		TextCodecs.CODEC.fieldOf("translated_text").forGetter(TranslatedTextContent::translatedText),
		Definition.CODEC.forGetter(TranslatedTextContent::definition),
		Codec.INT.optionalFieldOf("translation_delay", 50).forGetter(TranslatedTextContent::translationDelay)
	).apply(instance, TranslatedTextContent::new));

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
		Screen screen = renderContext.screen();
		Vec2i start = renderContext.startPos();
		MatrixStack matrices = renderContext.drawContext().getMatrices();
		matrices.push();
		matrices.translate((float)start.x / 2, (float)start.y / 2, 0);
		matrices.scale(0.5F, 0.5F, 1);

		int tick = renderContext.renderTick() * TICK_SPEED;
		int delay = this.translationDelay * TICK_SPEED;

		float originalColorDelta = Math.clamp(255 + delay - tick, 63, 255) / 255F;
		int originalColorValue = MathHelper.lerp(originalColorDelta, 63, 255);
		Color originalColor = new Color(originalColorValue, originalColorValue, originalColorValue, originalColorValue);
		screen.textRenderer.draw(
			this.originalText,
			start.x + this.definition.offset().x,
			start.y + this.definition.offset().y,
			originalColor.getRGB(),
			true,
			matrices.peek().getPositionMatrix(),
			renderContext.drawContext().getVertexConsumers(),
			TextRenderer.TextLayerType.NORMAL,
			0,
			15728880
		);

		float translatedColorAlphaDelta = Math.clamp(tick - delay, 4, 255) / 255F;
		int translatedColorAlpha = MathHelper.lerp(translatedColorAlphaDelta, 4, 255);
		Color translatedColor = new Color(255, 255, 255, translatedColorAlpha);
		screen.textRenderer.draw(
			this.translatedText,
			start.x + this.definition.offset().x + 2,
			start.y + this.definition.offset().y + 2,
			translatedColor.getRGB(),
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
