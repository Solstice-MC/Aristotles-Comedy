package org.solstice.aristotlesComedy.client.content.screen.widget;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.euclidsElements.util.type.Vec2i;

public class MovableResearchableWidget extends ResearchableWidget {

	private Vec2i offset;

	protected MovableResearchableWidget(
		int x, int y,
		Vec2i offset,
		int width, int height,
		Screen screen,
		PlayerEntity player,
		ItemStack stack,
		RegistryEntry<Researchable> entry
	) {
		super(x, y, width, height, screen, player, stack, entry);
	}

	public static MovableResearchableWidget create (
		Vec2i offset,
		Screen screen,
		PlayerEntity player,
		ItemStack stack,
		RegistryEntry<Researchable> entry
	) {
		Researchable researchable = entry.value();
		Vec2i size = researchable.size();
		return new MovableResearchableWidget(
			(screen.width - size.x) / 2,
			(screen.height - size.y) / 2,
			offset,
			size.x, size.y,
			screen, player, stack, entry
		);
	}

	public Vec2i getOffset() {
		return this.offset;
	}

	@Override
	public int getX() {
		return super.getX() + this.offset.x;
	}

	@Override
	public int getY() {
		return super.getY() + this.offset.y;
	}

	@Override
	protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
		this.offset = new Vec2i(
			Math.clamp(this.getX() + (int) deltaX, 0, this.screen.width - this.entry.value().size().x),
			Math.clamp(this.getY() + (int) deltaY, 0, this.screen.height - this.entry.value().size().y)
		);
//		this.setX(Math.clamp(this.getX() + (int) deltaX, 0, this.screen.width - this.entries.value().size().x));
//		this.setY(Math.clamp(this.getY() + (int) deltaY, 0, this.screen.height - this.entries.value().size().y));
	}

	@Override
	public void onRelease(double mouseX, double mouseY) {
		super.onRelease(mouseX, mouseY);
	}

}
