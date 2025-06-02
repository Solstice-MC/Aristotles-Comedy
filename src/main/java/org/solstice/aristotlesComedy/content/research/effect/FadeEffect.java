package org.solstice.aristotlesComedy.content.research.effect;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import org.solstice.aristotlesComedy.content.research.ResearchableRenderContext;
import org.solstice.euclidsElements.util.type.Vec2i;

public record FadeEffect (
	int fadeOutDelay,
	int startOpacity,
	int endOpacity,
	Definition definition
) implements ResearchEffect {

	public static final int TICK_SPEED = 2;

	public static final MapCodec<FadeEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.INT.optionalFieldOf("fade_out_delay", 50).forGetter(FadeEffect::fadeOutDelay),
		Codec.INT.fieldOf("start_opacity").forGetter(FadeEffect::startOpacity),
		Codec.INT.fieldOf("end_opacity").forGetter(FadeEffect::endOpacity),
		Definition.CODEC.forGetter(FadeEffect::definition)
	).apply(instance, FadeEffect::new));

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
		RenderSystem.setShader(GameRenderer::getPositionColorProgram);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.getAlpha(renderContext));
	}

	public float getAlpha(ResearchableRenderContext renderContext) {
		int tick = renderContext.renderTick * TICK_SPEED;
		int delay = this.fadeOutDelay * TICK_SPEED;
//		if (this.startOpacity > this.endOpacity) return Math.clamp(this.startOpacity + delay - tick, this.endOpacity, this.startOpacity) / 255F;
		return 1;
//		return Math.clamp(this.startOpacity - delay + tick, this.startOpacity, this.endOpacity) / 255F;
	}

}
