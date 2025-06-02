package org.solstice.aristotlesComedy.content.block.tank;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class StackingTankBlock extends TankBlock {

	public static final BooleanProperty TOP = BooleanProperty.of("top");
	public static final BooleanProperty BOTTOM = BooleanProperty.of("bottom");

	public static final Map<Direction, BooleanProperty> PROPERTY_MAP = Map.of(
		Direction.UP, TOP,
		Direction.DOWN, BOTTOM
	);

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return createCodec(StackingTankBlock::new);
	}

	public StackingTankBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState()
			.with(TOP, true)
			.with(BOTTOM, true)
		);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(TOP, BOTTOM);
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return this.updateDirection(world, pos, state, direction);
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState state = this.getDefaultState();
		for (Direction direction : Direction.Type.VERTICAL) {
			state = this.updateDirection(ctx.getWorld(), ctx.getBlockPos(), state, direction);
		}
		return state;
	}

	private BlockState updateDirection(WorldAccess world, BlockPos pos, BlockState state, Direction direction) {
		if (direction.getAxis() != Direction.Axis.Y) return state;
		BlockPos offsetPos = pos.offset(direction);
		BlockState offsetState = world.getBlockState(offsetPos);
		BooleanProperty property = PROPERTY_MAP.get(direction);
		return state.with(property, !offsetState.isOf(this));
	}

}
