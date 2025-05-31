package org.solstice.aristotlesComedy.client.content.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.euclidsElements.util.type.Vec2i;

public record ResearchableRenderContext (
	PlayerEntity player,
	ItemStack stack,
	RegistryEntry<Researchable> entry,
	Screen screen,
	DrawContext drawContext,
	Vec2i startPos,
	Vec2i mousePos,
	float delta,
	int renderTick
) {}
