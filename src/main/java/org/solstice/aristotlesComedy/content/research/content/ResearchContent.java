package org.solstice.aristotlesComedy.content.research.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.solstice.aristotlesComedy.content.research.ResearchableRenderContext;
import org.solstice.aristotlesComedy.content.research.effect.ResearchEffect;
import org.solstice.aristotlesComedy.registry.AristotlesRegistries;
import org.solstice.euclidsElements.util.type.Vec2i;

import java.util.List;

public interface ResearchContent {

	Codec<ResearchContent> CODEC = AristotlesRegistries.PAGE_CONTENT_TYPE.getCodec()
		.dispatch("type", ResearchContent::getType, ResearchContent.Type::codec);

	Type getType();
	Definition getDefinition();

	@Environment(EnvType.CLIENT)
	default void render(Screen screen, DrawContext drawContext, ResearchableRenderContext renderContext, Vec2i startPos) {
		this.applyEffects(screen, drawContext, renderContext, startPos);
	}

	@Environment(EnvType.CLIENT)
	default void applyEffects(Screen screen, DrawContext drawContext, ResearchableRenderContext renderContext, Vec2i startPos) {
		for (ResearchEffect effect : this.getDefinition().effects) {
			Vec2i effectStartPos = startPos.add(effect.getDefinition().offset());
			effect.apply(screen, drawContext, renderContext, effectStartPos);
		}
	}

    record Type(MapCodec<? extends ResearchContent> codec) {}

	record Definition (
		List<ResearchEffect> effects,
		Vec2i offset
	) {

		public static final MapCodec<Definition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			ResearchEffect.CODEC.listOf().optionalFieldOf("effects", List.of()).forGetter(Definition::effects),
			Vec2i.CODEC.optionalFieldOf("offset", Vec2i.ZERO).forGetter(Definition::offset)
		).apply(instance, Definition::new));

	}

}
