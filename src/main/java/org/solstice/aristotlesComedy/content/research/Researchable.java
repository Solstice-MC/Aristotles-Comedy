package org.solstice.aristotlesComedy.content.research;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import org.solstice.aristotlesComedy.content.research.content.ResearchContent;
import org.solstice.aristotlesComedy.registry.AristotlesRegistryKeys;
import org.solstice.aristotlesComedy.util.Vec2i;

import java.util.List;

public record Researchable (
	Civilization civilization,
	Vec2i size,
	List<ResearchContent> contents
) {

	public static final Codec<Researchable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Civilization.CODEC.fieldOf("civilization").forGetter(Researchable::civilization),
		Vec2i.CODEC.fieldOf("size").forGetter(Researchable::size),
		ResearchContent.CODEC.listOf().fieldOf("contents").forGetter(Researchable::contents)
	).apply(instance, Researchable::new));

	public static final Codec<RegistryEntry<Researchable>> ENTRY_CODEC = RegistryElementCodec.of(AristotlesRegistryKeys.RESEARCHABLE, CODEC);

	public static final PacketCodec<RegistryByteBuf, Researchable> PACKET_CODEC = PacketCodecs.unlimitedRegistryCodec(CODEC);
	public static final PacketCodec<RegistryByteBuf, RegistryEntry<Researchable>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(AristotlesRegistryKeys.RESEARCHABLE, PACKET_CODEC);

	enum Civilization implements StringIdentifiable {

		VILLAGER("villager");

		public static final Codec<Civilization> CODEC = StringIdentifiable.createCodec(Civilization::values);

		private final String name;

		Civilization(String name) {
			this.name = name;
		}

		@Override
		public String asString() {
			return this.name;
		}

	}

	public void render(RegistryEntry<Researchable> entry, Screen screen, DrawContext context, Vec2i start, Vec2i mouse, float delta) {
		Identifier background = getFrontTexture(entry);
		Vec2i size = entry.value().size();
		start = start.add(
			(screen.width - size.x) / 2,
			(screen.height - size.y) / 2
		);
		context.drawTexture(
			background,
			start.x,
			start.y,
			0, 0,
			size.x, size.y,
			size.x, size.y
		);

		Researchable researchable = entry.value();
		Vec2i contentStart = start;
		researchable.contents().forEach(content ->
			content.render(entry, screen, context, contentStart, mouse, delta)
		);
	}

	public static Identifier getFrontTexture(RegistryEntry<Researchable> entry) {
		return entry.getKey().orElseThrow().getValue().withPrefixedPath("gui/researchable/").withSuffixedPath("_front.png");
	}

}
