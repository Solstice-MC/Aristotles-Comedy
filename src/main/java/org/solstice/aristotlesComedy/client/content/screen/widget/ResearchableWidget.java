package org.solstice.aristotlesComedy.client.content.screen.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.client.content.screen.ResearchableRenderContext;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.euclidsElements.util.type.Vec2i;

public class ResearchableWidget extends ClickableWidget {

	protected final Screen screen;
	protected final PlayerEntity player;
	protected final ItemStack stack;
	protected final RegistryEntry<Researchable> entry;

	private int renderTick = 0;

	protected ResearchableWidget (
		int x, int y,
		int width, int height,
		Screen screen,
		PlayerEntity player,
		ItemStack stack,
		RegistryEntry<Researchable> entry
	) {
		super(x, y, width, height, NarratorManager.EMPTY);
		this.screen = screen;
		this.player = player;
		this.stack = stack;
		this.entry = entry;
	}

	public static ResearchableWidget create (
		Screen screen,
		PlayerEntity player,
		ItemStack stack,
		RegistryEntry<Researchable> entry
	) {
		Researchable researchable = entry.value();
		Vec2i size = researchable.size();
		return new ResearchableWidget(
			(screen.width - size.x) / 2,
			(screen.height - size.y) / 2,
			size.x, size.y,
			screen, player, stack, entry
		);
	}

	@Override
	public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderTick++;

		Vec2i startingPos = new Vec2i(this.getX(), this.getY());
		ResearchableRenderContext renderContext = new ResearchableRenderContext(
			this.player, this.stack, this.entry, this.screen, context, startingPos, new Vec2i(mouseX, mouseY), delta, this.renderTick
		);
		entry.value().render(renderContext);
	}

	@Override
	protected void appendClickableNarrations(NarrationMessageBuilder builder) {
		builder.put(NarrationPart.TITLE, Researchable.getName(this.entry));
	}

	@Override
	public void playDownSound(SoundManager soundManager) {}

}
