package org.solstice.aristotlesComedy.client.content.screen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.client.content.screen.widget.MovableResearchableWidget;
import org.solstice.aristotlesComedy.client.content.screen.widget.ResearchableWidget;
import org.solstice.aristotlesComedy.content.research.PositionedResearchable;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.euclidsElements.util.type.Vec2i;

import java.util.List;

public class ResearchScreen extends Screen {

	private final PlayerEntity player;
	private final ItemStack stack;
	private final List<PositionedResearchable> researchables;

	public ResearchScreen(PlayerEntity player, ItemStack stack, List<PositionedResearchable> researchables) {
		super(NarratorManager.EMPTY);
		this.player = player;
		this.stack = stack;
		this.researchables = researchables;
	}

	@Override
	protected void init() {
		super.init();
		researchables.forEach(data -> {
			Vec2i pos = data.pos;
			RegistryEntry<Researchable> entry = data.entry;
			ResearchableWidget widget = MovableResearchableWidget.create(
				this, player, stack, entry
			);
			this.addSelectableChild(widget);
		});
	}

}
