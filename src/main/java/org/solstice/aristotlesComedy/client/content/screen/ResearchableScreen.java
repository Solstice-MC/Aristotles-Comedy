package org.solstice.aristotlesComedy.client.content.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.client.content.research.ResearchableRenderContext;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.aristotlesComedy.util.Vec2i;

public class ResearchableScreen extends Screen {

	private final PlayerEntity player;
	private final ItemStack stack;
	private final RegistryEntry<Researchable> entry;

	private int renderTick = 0;

	public ResearchableScreen(PlayerEntity player, ItemStack stack, RegistryEntry<Researchable> entry) {
		super(NarratorManager.EMPTY);
		this.player = player;
		this.stack = stack;
		this.entry = entry;
	}

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
		super.render(drawContext, mouseX, mouseY, delta);
		this.renderTick++;
		ResearchableRenderContext renderContext = new ResearchableRenderContext(
			this.player, this.stack, this.entry, this, drawContext, Vec2i.ZERO, new Vec2i(mouseX, mouseY), delta, this.renderTick
		);
		this.entry.value().render(renderContext);
	}

}
