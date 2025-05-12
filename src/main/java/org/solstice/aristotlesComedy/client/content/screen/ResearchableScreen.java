package org.solstice.aristotlesComedy.client.content.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.aristotlesComedy.util.Vec2i;

public class ResearchableScreen extends Screen {

	private final RegistryEntry<Researchable> entry;

	public ResearchableScreen(RegistryEntry<Researchable> entry) {
		super(NarratorManager.EMPTY);
		this.entry = entry;
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		this.entry.value().render(entry, this, context, Vec2i.ZERO, new Vec2i(mouseX, mouseY), delta);
	}

}
