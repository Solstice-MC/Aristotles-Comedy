package org.solstice.aristotlesComedy.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public class BuddingClusterBlock extends Block {

	private final Block defaultCrystal;

	public BuddingClusterBlock(Block defaultCrystal, Settings settings) {
		super(settings);
		this.defaultCrystal = defaultCrystal;
	}

	@Override
	protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextInt(5) != 0) return;

		Direction direction = randomDirection(random);
		BlockPos offset = pos.offset(direction);
		BlockState newState = world.getBlockState(offset);

		if (newState.getBlock() instanceof ClusterBlock) ClusterBlock.tryIncreaseStage(world, offset, newState);
		else if (newState.isAir()) world.setBlockState(offset, this.defaultCrystal.getDefaultState().with(ClusterBlock.FACING, direction));
	}

	public static Direction randomDirection(Random random) {
		return Direction.values()[random.nextInt(Direction.values().length)];
	}

}
