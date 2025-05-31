package org.solstice.aristotlesComedy.content.research;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.euclidsElements.util.type.Vec2i;

public class PositionedResearchable {

	public static final Codec<PositionedResearchable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Researchable.ENTRY_CODEC.fieldOf("entries").forGetter(PositionedResearchable::getEntry),
		Vec2i.CODEC.fieldOf("pos").forGetter(PositionedResearchable::getPos)
	).apply(instance, PositionedResearchable::new));

	public RegistryEntry<Researchable> getEntry() {
		return this.entry;
	}

	public Vec2i getPos() {
		return this.pos;
	}

	public void setPos(Vec2i pos) {
		this.pos = pos;
	}

	public final RegistryEntry<Researchable> entry;
	public Vec2i pos;

	public PositionedResearchable(RegistryEntry<Researchable> entry, Vec2i pos) {
		this.entry = entry;
		this.pos = pos;
	}

}
