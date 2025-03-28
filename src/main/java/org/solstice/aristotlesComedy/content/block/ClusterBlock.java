package org.solstice.aristotlesComedy.content.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class ClusterBlock extends Block implements Waterloggable {

	public static final int MAX_STAGE = 2;

	public static final IntProperty STAGE = IntProperty.of("stage", 0, MAX_STAGE);
	public static final DirectionProperty FACING = Properties.FACING;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	public ClusterBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState()
			.with(STAGE, 0)
			.with(FACING, Direction.UP)
			.with(WATERLOGGED, false)
		);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(STAGE, WATERLOGGED, FACING);
	}

	public static void tryIncreaseStage(World world, BlockPos pos, BlockState state) {
		int stage = state.get(STAGE) + 1;
		if (stage > MAX_STAGE) return;
		world.setBlockState(pos, state.with(STAGE, stage));
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		Direction direction = state.get(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
	}

	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		WorldAccess world = ctx.getWorld();
		BlockPos pos = ctx.getBlockPos();
		return this.getDefaultState()
			.with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER)
			.with(FACING, ctx.getSide());
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return direction == state.get(FACING).getOpposite() && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Direction direction = state.get(FACING);
		int height = state.get(STAGE) * 2 + 2;
		int width = 5 - state.get(STAGE);
		return switch (direction) {
			case NORTH -> Block.createCuboidShape(width, width, (16 - height), (16 - width), (16 - width), 16);
			case SOUTH -> Block.createCuboidShape(width, width, 0, (16 - width), (16 - width), height);
			case EAST -> Block.createCuboidShape(0, width, width, height, (16 - width), (16 - width));
			case WEST -> Block.createCuboidShape((16 - height), width, width, 16, (16 - width), (16 - width));
			case UP -> Block.createCuboidShape(width, 0, width, (16 - width), height, (16 - width));
			case DOWN -> Block.createCuboidShape(width, (16 - height), width, (16 - width), 16, (16 - width));
		};
	}

}
