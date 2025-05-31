package org.solstice.aristotlesComedy.client.content.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.client.content.screen.widget.ResearchableWidget;
import org.solstice.aristotlesComedy.content.research.Researchable;

public class ResearchableScreen extends Screen {

	private final PlayerEntity player;
	private final ItemStack stack;
	private final RegistryEntry<Researchable> entry;

	public ResearchableScreen(PlayerEntity player, ItemStack stack, RegistryEntry<Researchable> entry) {
		super(NarratorManager.EMPTY);
		this.player = player;
		this.stack = stack;
		this.entry = entry;
	}

	@Override
	protected void init() {
		ResearchableWidget widget = ResearchableWidget.create(this, this.player, this.stack, this.entry);
		this.addDrawableChild(widget);
		this.addSelectableChild(widget);
	}

}
