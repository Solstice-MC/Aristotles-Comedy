package org.solstice.aristotlesComedy.client.content.research;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.solstice.aristotlesComedy.content.research.Researchable;
import org.solstice.aristotlesComedy.util.Vec2i;

public record ResearchableRenderContext (
	PlayerEntity player,
	ItemStack stack,
	RegistryEntry<Researchable> entry,
	Screen screen,
	DrawContext drawContext,
	Vec2i start,
	Vec2i mouse,
	float delta,
	int renderTick
) {}
