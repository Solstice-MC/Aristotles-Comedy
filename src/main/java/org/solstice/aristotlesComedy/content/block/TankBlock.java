package org.solstice.aristotlesComedy.content.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import org.solstice.aristotlesComedy.content.block.entity.TankBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class TankBlock extends BlockWithEntity {

	public static final BooleanProperty TOP = BooleanProperty.of("top");
	public static final BooleanProperty BOTTOM = BooleanProperty.of("bottom");

	public static final Map<Direction, BooleanProperty> PROPERTY_MAP = Map.of(
		Direction.UP, TOP,
		Direction.DOWN, BOTTOM
	);

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return createCodec(TankBlock::new);
	}

	public TankBlock(Settings settings) {
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
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) return ItemActionResult.CONSUME;
		TankBlockEntity entity = getBlockEntity(world, pos);
		if (entity == null) return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

		boolean result = FluidStorageUtil.interactWithFluidStorage(entity.storage, player, hand);
		return result ? ItemActionResult.SUCCESS : ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
	}



	public static TankBlockEntity getBlockEntity(World world, BlockPos pos) {
		return (TankBlockEntity) world.getBlockEntity(pos);
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new TankBlockEntity(pos, state);
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

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

}
