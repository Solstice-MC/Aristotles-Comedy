package org.solstice.aristotlesComedy.content.research;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import org.solstice.aristotlesComedy.content.research.content.ResearchContent;
import org.solstice.aristotlesComedy.registry.AristotlesRegistryKeys;
import org.solstice.euclidsElements.util.type.Vec2i;

import java.util.List;

public record Researchable (
	List<ResearchContent> contents,
	Vec2i size
) {

	public static final Codec<Researchable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		ResearchContent.CODEC.listOf().fieldOf("contents").forGetter(Researchable::contents),
		Vec2i.CODEC.fieldOf("size").forGetter(Researchable::size)
	).apply(instance, Researchable::new));

	public static final Codec<RegistryEntry<Researchable>> ENTRY_CODEC = RegistryElementCodec.of(AristotlesRegistryKeys.RESEARCHABLE, CODEC);

	public static final PacketCodec<RegistryByteBuf, Researchable> PACKET_CODEC = PacketCodecs.unlimitedRegistryCodec(CODEC);
	public static final PacketCodec<RegistryByteBuf, RegistryEntry<Researchable>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(AristotlesRegistryKeys.RESEARCHABLE, PACKET_CODEC);

	@Environment(EnvType.CLIENT)
	public void render(Screen screen, DrawContext drawContext, ResearchableRenderContext renderContext, Vec2i startPos) {
		this.renderBackground(drawContext, screen, renderContext);
		for (ResearchContent content : this.contents) {
			Vec2i contentStartPos = startPos.add(content.getDefinition().offset());
			content.render(screen, drawContext, renderContext, contentStartPos);
		}
	}

	@Environment(EnvType.CLIENT)
	public void renderBackground(DrawContext drawContext, Screen screen, ResearchableRenderContext renderContext) {
//		RegistryEntry<Researchable> entry = renderContext.entry();
//		Vec2i start = renderContext.startPos;
//		Identifier texture = this.path.withPrefixedPath("textures/gui/researchable/").withSuffixedPath(".png");
//
//		MatrixStack matrices = drawContext.getMatrices();
//
//		matrices.push();
//		ResearchContent.super.render(drawContext, screen, renderContext);
//		drawContext.drawTexture(
//			texture,
//			start.x,
//			start.y,
//			0, 0,
//			this.size.x, this.size.y,
//			this.size.x, this.size.y
//		);
//		matrices.pop();
	}

	public static Text getName(RegistryEntry<Researchable> entry) {
		String key = entry.getKey().orElseThrow().getValue().toTranslationKey("researchable");
		return Text.translatable(key);
	}

}
