package org.solstice.aristotlesComedy.content.research;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.solstice.euclidsElements.util.type.Vec2i;

public class ResearchableRenderContext {

	public PlayerEntity player;
	public ItemStack stack;
//	public Vec2i startPos;
	public Vec2i mousePos;
	public int renderTick;

	public ResearchableRenderContext(
		PlayerEntity player,
		ItemStack stack,
//		Vec2i startPos,
		Vec2i mousePos,
		int renderTick
	) {
		this.player = player;
		this.stack = stack;
//		this.startPos = startPos;
		this.mousePos = mousePos;
		this.renderTick = renderTick;
	}

	public ResearchableRenderContext copy() {
//		return new ResearchableRenderContext(player, stack, startPos, mousePos, renderTick);
		return new ResearchableRenderContext(player, stack, mousePos, renderTick);
	}

}
