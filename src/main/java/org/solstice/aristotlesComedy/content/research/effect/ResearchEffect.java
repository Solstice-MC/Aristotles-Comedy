package org.solstice.aristotlesComedy.content.research.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.solstice.aristotlesComedy.content.research.ResearchableRenderContext;
import org.solstice.aristotlesComedy.registry.AristotlesRegistries;
import org.solstice.euclidsElements.util.type.Vec2i;

public interface ResearchEffect {

	Codec<ResearchEffect> CODEC = AristotlesRegistries.PAGE_EFFECT_TYPE.getCodec()
		.dispatch("type", ResearchEffect::getType, ResearchEffect.Type::codec);

	Type getType();
	Definition getDefinition();

	@Environment(EnvType.CLIENT)
	default void apply(Screen screen, DrawContext drawContext, ResearchableRenderContext renderContext, Vec2i startPos) {}

	record Type(MapCodec<? extends ResearchEffect> codec) {}

	record Definition(Vec2i offset) {

		public static final MapCodec<Definition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Vec2i.CODEC.optionalFieldOf("offset", Vec2i.ZERO).forGetter(Definition::offset)
		).apply(instance, Definition::new));

	}

}
