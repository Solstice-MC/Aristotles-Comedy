package org.solstice.aristotlesComedy.content.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.solstice.aristotlesComedy.content.research.PositionedResearchable;
import org.solstice.aristotlesComedy.registry.AristotlesBlockEntities;

import java.util.ArrayList;
import java.util.List;

public class MarbleBustBlockEntity extends BlockEntity {

	public MarbleBustBlockEntity(BlockPos pos, BlockState state) {
		super(AristotlesBlockEntities.MARBLE_BUST, pos, state);
	}

}
